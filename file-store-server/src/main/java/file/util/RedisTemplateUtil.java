package file.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;

public class RedisTemplateUtil {

	private static RedisTemplate<String, String> redisTemplate = (RedisTemplate) SpringHelper.getBean("redisTemplate");
	private static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	/**
	 * 添加缓存数据
	 * @param key
	 * @param obj
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> boolean setNX(String key, T obj) throws Exception {
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = SerializerUtil.serializeObj(obj);
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.setNX(bkey, bvalue);
			}
		});
		return result;
	}

	/**
	 * 添加缓存数据，设定缓存失效时间
	 * @param key
	 * @param obj
	 * @param expireTime
	 * @param <T>
	 * @throws Exception
	 */
	public static <T> void setEx(String key, T obj, final long expireTime) throws Exception {
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = SerializerUtil.serializeObj(obj);
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setEx(bkey, expireTime, bvalue);
				return true;
			}
		});
	}

	public static <T> void delKey(String key, T obj) throws Exception {
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = SerializerUtil.serializeObj(obj);
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.del(bkey, bvalue);
				return true;
			}
		});
	}

	public static Boolean hasKey(String key) throws Exception {
		final byte[] bkey = key.getBytes();
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(bkey);

			}

		});
	}

	/**
	 * 根据key取缓存数据
	 * @param key
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T get(final String key) throws Exception {
		byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
		if (result == null) {
			return null;
		}
		return (T) SerializerUtil.deserializeObj(result);
	}

	public static <T> T getList(final String key, final long start, final long end) throws Exception {
		byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.getRange(key.getBytes(), start, end);
			}
		});
		if (result == null) {
			return null;
		}
		return (T) SerializerUtil.deserializeObj(result);
	}

	public static Long setList(final String key, Collection values) {
		final byte[] serializeKey = stringRedisSerializer.serialize(key);
		final byte[][] serializeValues = rawValues(values);
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				return connection.lPush(serializeKey, serializeValues);
			}
		}, true);
	}

	public static byte[][] rawValues(Object... values) {
		GenericJackson2JsonRedisSerializer g2jes = new GenericJackson2JsonRedisSerializer();
		byte[][] rawValues = new byte[values.length][];
		int i = 0;
		Object[] var4 = values;
		int length = values.length;

		for (int j = 0; j < length; ++j) {
			Object value = var4[j];
			rawValues[i++] = g2jes.serialize(value);
		}

		return rawValues;
	}

	public static long incr(String key) {
		final byte[] serializeKey = stringRedisSerializer.serialize(key);
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				return connection.incr(serializeKey);
			}
		}, true);
	}

	public static long decr(String key) {
		final byte[] serializeKey = stringRedisSerializer.serialize(key);
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				return connection.decr(serializeKey);
			}
		}, true);
	}



/*	public static <T> T getListR(String key, final long start, final long end) {
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		final byte[] rawKey = stringRedisSerializer.serialize(key);

		List result = (List) redisTemplate.execute(new RedisCallback<List<byte[]>>() {
			public List<byte[]> doInRedis(RedisConnection connection) {
				return connection.lRange(rawKey, start, end);
			}
		}, true);
		return (T) SerializerUtil.deserializeObj(result);

	}*/

}

