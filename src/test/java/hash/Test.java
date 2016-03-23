package hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.sms.core.hash.ConsistentHash;
import org.sms.core.hash.impl.ConsistentHashImpl;

/**
 * @author Sunny
 */
public class Test {

  private static final String IP_PREFIX = "192.168.1.";// 机器节点 IP 前缀

  public static void main(String[] args) {
    Map<String, Integer> map = new HashMap<String, Integer>();// 每台真实机器节点上保存的记录条数
    List<Node<String>> nodes = new ArrayList<Node<String>>();// 真实机器节点 // 10                              // 台真实机器节点集群
    for (int i = 1; i <= 10; i++) {
      map.put(IP_PREFIX + i, 0);// 每台真实机器节点上保存的记录条数初始为 0 Node<String> node = new
      Node<String> node = new Node<String>(IP_PREFIX + i, "node" + i);
      nodes.add(node);
    }

    
    ConsistentHash<Node<String>> consistentHash = new ConsistentHashImpl<Node<String>>(10, nodes);
    consistentHash.addListMember();
    for (int i = 0; i < 5000; i++) {
      String data = UUID.randomUUID().toString() + i;
      // 通过记录找到真实机器节点
      Node<String> node = consistentHash.getMember(data);
      // 再这里可以能过其它工具将记录存储真实机器节点上,比如 MemoryCache 等 // ...
      // 每台真实机器节点上保存的记录条数加 1
      map.put(node.getIp(), map.get(node.getIp()) + 1);
    }

    for (int i = 1; i <= 10; i++) {
      System.out.println(IP_PREFIX + i + "节点记录条数:" + map.get("192.168.1." + i));
    }
    System.out.println(consistentHash.getAllMembers().size());
  }
}
