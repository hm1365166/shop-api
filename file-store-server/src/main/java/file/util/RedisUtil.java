package file.util;/*
package com.shop.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.shop.util.JacksonUtil;

*/
/**
 * Redis的java封装
 *
 * @see https://my.oschina.net/sphl520/blog/312514
 * @author jiangmy
 * @date 2016-08-16 18:14:07
 * @since v1.0.0
 *//*

public class RedisUtil {
	static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	static Map<String, JedisPool> _pool = new ConcurrentHashMap<String, JedisPool>();

	private static final String CONFIG = "redis.properties";
	static JedisPoolConfig config = new JedisPoolConfig();
	static PropertiesConfiguration configuration;

	static String host = "127.0.0.1";
	static int port = 6379;
	static int timeout = 10000;
	static String password = null;
	static {
		try {
			configuration = new PropertiesConfiguration(CONFIG);
			if (!configuration.isEmpty()) {
				config.setMaxTotal(configuration.getInt("redis.maxTotal", 100));
				config.setMaxIdle(configuration.getInt("redis.maxIdle", 50));
				config.setMaxWaitMillis(configuration.getInt("redis.maxWaitMillis", 5000));
				config.setTestOnBorrow(configuration.getBoolean("redis.testOnBorrow", true));

				timeout = configuration.getInt("redis.timeout", 10000);
				password = configuration.getString("redis.password", null);
				password = password == null || password.trim().equals("") ? null : password;
				host = configuration.getString("redis.host", null);
				port = configuration.getInt("redis.port", 6379);
			} else {
				logger.warn("redis.properties not found in classpath !!!");
			}
		} catch (Exception e) {
			logger.warn("redis.properties initial failed !!!", e);
		}
	}

	public static Jedis getJedis() {
		return init(host, port);
	}

	public static <T> T get(String key, Class<T> clzz) {
		try (Jedis jedis = getJedis()) {
			String json = jedis.get(key);
			return JacksonUtil.fromJson(json, clzz);
		}
	}

	public static <T> T get(String key, TypeReference<T> type) {
		try (Jedis jedis = getJedis()) {
			String json = jedis.get(key);
			return JacksonUtil.fromJson(json, type);
		}
	}

	*/
/**
	 * 无论是否存在,均set进去
	 *
	 * @author qinqz
	 * @date 2016-08-19 11:45:46
	 * @since
	 * @param key
	 * @param value
	 * @return
	 *//*

	public static <T> String set(String key, T value) {
		try (Jedis jedis = getJedis()) {
			String set = jedis.set(key, JacksonUtil.toJson(value));
			return set;
		}
	}

	*/
/**
	 * 存在则不设置
	 *
	 * @author qinqz
	 * @date 2016-08-19 11:46:48
	 * @since
	 * @param key
	 * @param value
	 * @return
	 *//*

	public static <T> boolean setnx(String key, T value) {
		try (Jedis jedis = getJedis()) {
			Long set = jedis.setnx(key, JacksonUtil.toJson(value));
			return set == 1;
		}
	}

	*/
/**
	 * 无论是否存在,均set进去, 并设置失效时间,单位:秒
	 *
	 * @author qinqz
	 * @date 2016-08-19 11:47:05
	 * @since 1.0.0
	 * @param key
	 * @param value
	 * @param expxSeconds
	 * @return
	 *//*

	public static <T> String setex(String key, T value, int expxSeconds) {
		try (Jedis jedis = getJedis()) {
			String setex = jedis.setex(key, expxSeconds, JacksonUtil.toJson(value));
			return setex;
		}
	}

	public static long ttl(String key) {
		try (Jedis jedis = getJedis()) {
			return jedis.ttl(key);
		}
	}

	*/
/**
	 * 插入到列表头部
	 * 简单的字符串列表,按照插入顺序排序,最多40亿个
	 *
	 * @user jiangmy
	 * @date 2016-08-16 16:16:03
	 * @since
	 * @param key
	 * @param ts (在Redis 2.4版本以前,这里支持一个值)
	 * @return 队列总个数
	 *//*

	@SuppressWarnings("unchecked")
	public static <T> Long lpush(String key, T... values) {
		try (Jedis jedis = getJedis()) {
			ArrayList<String> list = new ArrayList<>();
			for (T t : values) {
				if (t.getClass() == String.class) {
					list.add(t.toString());
				} else {
					list.add(JacksonUtil.toJson(t));
				}
			}
			Long l = jedis.lpush(key, list.toArray(new String[list.size()]));
			return l;
		}
	}

	public static void batchLpush(String key, String... values) {
		try (Jedis jedis = getJedis()) {
			Pipeline pl = jedis.pipelined();
			pl.lpush(key, values);
		}
	}

	*/
/**
	 * 插入到列表头部
	 * 简单的字符串列表,按照插入顺序排序,最多40亿个
	 *
	 * @user jiangmy
	 * @date 2016-08-16 16:16:03
	 * @since
	 * @param key
	 * @param ts (在Redis 2.4版本以前,这里支持一个值)
	 * @return 队列总个数
	 *//*

	public static <T> Long lpush(String key, List<T> listData) {
		if (listData == null || listData.isEmpty()) {
			return 0l;
		}
		ArrayList<String> list = new ArrayList<>();
		for (T t : listData) {
			if (t.getClass() == String.class) {
				list.add(t.toString());
			} else {
				list.add(JacksonUtil.toJson(t));
			}
		}
		try (Jedis jedis = getJedis()) {
			return jedis.lpush(key, list.toArray(new String[list.size()]));
		}
	}

	*/
/**
	 * 插入到列表尾部
	 * 简单的字符串列表,按照插入顺序排序,最多40亿个
	 *
	 * @user jiangmy
	 * @date 2016-08-16 16:16:03
	 * @since
	 * @param key
	 * @param t
	 * @return
	 *//*

	@SuppressWarnings("unchecked")
	public static <T> Long rpush(String key, T... values) {
		ArrayList<String> list = Lists.newArrayList();
		for (T t : values) {
			if (t.getClass() == String.class) {
				list.add(t.toString());
			} else {
				list.add(JacksonUtil.toJson(t));
			}
		}
		try (Jedis jedis = getJedis()) {
			return jedis.rpush(key, list.toArray(new String[list.size()]));
		}
	}

	*/
/**
	 * 从队列左边取出一条数据
	 *
	 * @user jiangmy
	 * @date 2016-08-16 18:14:10
	 * @since
	 * @param key
	 * @param clazz
	 * @return
	 *//*

	public static <T> T lpop(String key, Class<T> clazz) {
		try (Jedis jedis = getJedis()) {
			String json = jedis.lpop(key);
			return JacksonUtil.fromJson(json, clazz);
		}
	}

	*/
/**
	 * 从队列左边取出一条数据
	 *
	 * @user jiangmy
	 * @date 2016-08-16 18:14:10
	 * @since
	 * @param key
	 * @param clazz
	 * @return
	 *//*

	public static <T> List<T> lrangeAll(String key, int start, int len, Class<T> clazz) {
		try (Jedis jedis = getJedis()) {
			List<String> json = jedis.lrange(key, start, start + len - 1);
			return JacksonUtil.fromJson(JacksonUtil.toJson(json), new TypeReference<ArrayList<T>>() {
			});
		}
	}

	public static <T> List<T> lrangeList(String key, long start, long end, Class<T> clazz) {
		try (Jedis jedis = getJedis()) {
			List<String> json = jedis.lrange(key, start, end);
			return JacksonUtil.fromJson(JacksonUtil.toJson(json), new TypeReference<ArrayList<T>>() {
			});
		}
	}

		public static <T> List<T> batchPop(String key, long start, long end, Class<T> clazz) {
		try (Jedis jedis = getJedis()) {
			List<String> json = jedis.lrange(key, start, end);
			jedis.ltrim(key, 0, start);
			return JacksonUtil.fromJsonList(json, clazz);
		}
	}

	public static void del(String... key) {
		try (Jedis jedis = getJedis()) {
			jedis.del(key);
		}
	}

	*/
/**
	 * 从队列右边取一条数据
	 *
	 * @user jiangmy
	 * @date 2016-08-16 18:25:05
	 * @since
	 * @param key
	 * @param clazz
	 * @return
	 *//*

	public static <T> T rpop(String key, Class<T> clazz) {
		try (Jedis jedis = getJedis()) {
			String json = jedis.rpop(key);
			return JacksonUtil.fromJson(json, clazz);
		}
	}

	public static <T> T brpop(String key, Class<T> clazz, int milli) {
		try (Jedis jedis = getJedis()) {
			List<String> json = jedis.brpop(timeout, key);
			return JacksonUtil.fromJson(json == null || json.isEmpty() ? null : json.get(1), clazz);
		}
	}

	public static <T> T brpop(String key, Class<T> clazz) {
		try (Jedis jedis = getJedis()) {
			List<String> json = jedis.brpop(new String[] { key });
			return JacksonUtil.fromJson(json == null || json.isEmpty() ? null : json.get(0), clazz);
		}
	}

	*/
/**
	 * 队列长度
	 *
	 * @user jiangmy
	 * @date 2016-08-16 18:14:57
	 * @since
	 * @param key
	 * @return
	 *//*

	public static long llen(String key) {
		try (Jedis jedis = getJedis()) {
			Long llen = jedis.llen(key);
			return llen;
		}
	}

	public static Jedis init(String host, int port) {
		String key = host.concat(":" + port);
		JedisPool pool = _pool.get(key);
		if (pool == null) {
			pool = new JedisPool(config, host, port, timeout, password);
			_pool.put(key, pool);
		}
		return pool.getResource();
	}

}*/
