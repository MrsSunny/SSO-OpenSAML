package org.sms.component.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhenxing.Liu
 */
public class JedisUtil {
  private static JedisPool jedisPool = null;
  static {
    // String host = redisJsonConfig.getString("host");
    // int port = redisJsonConfig.getInteger("port");
    JedisPoolConfig config = new JedisPoolConfig();
    // config.setMaxTotal(redisJsonConfig.getInteger("maxTotal"));
    // config.setMaxIdle(redisJsonConfig.getInteger("maxIdle"));
    // config.setMaxWaitMillis(redisJsonConfig.getInteger("maxWaitMillis"));
    // config.setTestOnBorrow(redisJsonConfig.getBoolean("testOnBorrow"));
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
