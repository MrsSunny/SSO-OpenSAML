package hash;

/**
 * @author Sunny
 */
public class Node<T> {
  private String ip;// IP
  private String name;// 名称

  public Node(String ip, String name) {
    this.ip = ip;
    this.name = name;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * 复写 toString 方法,使用节点 IP 当做 hash 的 KEY
   */
  @Override
  public String toString() {
    return ip;
  }
}
