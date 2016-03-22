package org.sms.core.cache;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import redis.clients.jedis.Jedis;

/**
 * @author Sunny
 */
public enum Redis {

  INSTANCE;

  public static final JedisUtil jedisUtil = JedisUtil.getInstance();

  public void putString(String key, String value) {
    Jedis jedis = jedisUtil.getJedis();
    jedis.set(key, value);
    jedisUtil.returnResource(jedis);
  }

  public void putMap(String key, Map<String, String> value) {
    Jedis jedis = jedisUtil.getJedis();
    jedis.hmset(key, value);
    jedisUtil.returnResource(jedis);
  }

  public String getString(String key) {
    Jedis jedis = jedisUtil.getJedis();
    try {
      return jedis.get(key);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jedisUtil.returnResource(jedis);
    }
    return null;
  }

  public List<String> getMap(String key, String... eachFiled) {
    Jedis jedis = jedisUtil.getJedis();
    try {
      List<String> rsmap = jedis.hmget(key, eachFiled);
      return rsmap;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jedisUtil.returnResource(jedis);
    }
    return null;
  }

  public void delete(String... key) {
    Jedis jedis = jedisUtil.getJedis();
    if (Objects.nonNull(key))
      jedis.del(key);
    jedisUtil.returnResource(jedis);
  }

  public void putList(String key, String... value) {
    Jedis jedis = jedisUtil.getJedis();
    jedis.lpush(key, value);
    jedisUtil.returnResource(jedis);
  }

  public void putSet(String key, String... value) {
    Jedis jedis = jedisUtil.getJedis();
    jedis.sadd(key, value);
    jedisUtil.returnResource(jedis);
  }

  public Set<String> getSet(String key) {
    Jedis jedis = jedisUtil.getJedis();
    try {
      return jedis.smembers(key);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jedisUtil.returnResource(jedis);
    }
    return null;
  }

  public List<String> getList(String key) {
    Jedis jedis = jedisUtil.getJedis();

    try {
      return jedis.lrange(key, 0, -1);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jedisUtil.returnResource(jedis);
    }
    return null;
  }
}