package org.sms.core.hash.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.SortedMap;
import org.sms.core.hash.ConsistentHash;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConsistentHashImpl<T> implements ConsistentHash<T> {

  public static final int DEFAULT_VIRRUALNODE_INSTANCE = 5;
  private final HashFunction hashFunction;
  private final NavigableMap<Integer, T> membersMap;
  private List<T> list;

  /**
   * 虚拟节点数目
   */
  private int virtualNodeInstance;

  public ConsistentHashImpl(int virtualNodeInstance, List<T> list) {
    this.virtualNodeInstance = virtualNodeInstance;
    this.membersMap = new ConcurrentSkipListMap<Integer, T>();
    this.hashFunction = SHA1;
    this.list = list;
  }
  
  public ConsistentHashImpl(List<T> list) {
    this(DEFAULT_VIRRUALNODE_INSTANCE, list);
  }
  
  public void addListMember() {
    list.forEach(pere -> {
      addMember(pere);
    });
  }

  @Override
  public void addMember(T t) {
    for (int i = 0; i < virtualNodeInstance; i++) {
      membersMap.put(hashFunction.hash((t.toString() + i).getBytes()), t);
    }
  }

  @Override
  public void removeMember(T t) {
    for (int i = 0; i < virtualNodeInstance; i++) {
      membersMap.remove(hashFunction.hash((t.toString() + i).getBytes()), t);
    }
  }
  
  public T getMember(String key) {
    if (null == key)
      return null;
    if (membersMap.isEmpty())
      return null;
    int hash = hashFunction.hash(key.getBytes());
    if (!membersMap.containsKey(hash)) {
      SortedMap<Integer, T> tailMap = membersMap.tailMap(hash);
      hash = tailMap.isEmpty() ? membersMap.firstKey() : tailMap.firstKey();
    }
    return membersMap.get(hash);
  }

  public int getVirtualNodeNumber() {
    return this.virtualNodeInstance;
  }

  @Override
  public List<T> getAllMembers() {
    List<T> result = new ArrayList<>();
    result.addAll(membersMap.values());
    return result;
  }
}