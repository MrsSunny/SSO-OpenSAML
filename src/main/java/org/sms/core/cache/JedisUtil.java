package org.sms.core.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Sunny
 */
public class JedisUtil {
  private static JedisPool jedisPool = null;
  static {
    JedisPoolConfig config = new JedisPoolConfig();
    try {
      /**
       * 如果你遇到 java.net.SocketTimeoutException: Read timed out exception的异常信息
       * 请尝试在构造JedisPool的时候设置自己的超时值. JedisPool默认的超时时间是2秒(单位毫秒)
       */
      jedisPool = new JedisPool(config, "", 9368, 3000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 私有构造器.
   */
  private JedisUtil() {

  }

  private static class RedisUtilHolder {
    private static JedisUtil instance = new JedisUtil();
  }

  public static JedisUtil getInstance() {
    return RedisUtilHolder.instance;
  }

  /**
   * 获取Jedis实例
   * 
   * @return
   */
  public synchronized Jedis getJedis() {
    try {
      if (jedisPool != null) {
        Jedis resource = jedisPool.getResource();
        return resource;
      } else {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 释放jedis资源
   * 
   * @param jedis
   */
  public void returnResource(final Jedis jedis) {
    if (jedis != null) {
      jedisPool.returnResourceObject(jedis);
    }
  }
}
