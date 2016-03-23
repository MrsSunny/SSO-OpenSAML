package org.sms.core.hash;

import java.util.List;

import com.google.common.hash.Hashing;

public interface ConsistentHash<T> {

  void addMember(T t);
  
  void addListMember();

  void removeMember(T t);
  
  T getMember(String key);

  List<T> getAllMembers();

  public static interface BytesConverter<T> {
    byte[] convert(T data);
  }

  public static interface HashFunction {
    int hash(byte[] input);
  }

  public static final HashFunction SHA1 = new SHA1HashFunction();

  public static HashFunction getSHA1HashFunction() {
    return SHA1;
  }

  public static class SHA1HashFunction implements HashFunction {
    
    @Override
    public int hash(byte[] input) {
      return Hashing.sha1().hashBytes(input).asInt();
    }
  }
}
