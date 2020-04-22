package com.yd.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;

/**
 * redis工具类
 *
 * @see 	 
 */
public class RedisUtil {
	//SIT	10.80.41.19
	//UAT	10.80.41.54
	//PRD	
	private static String REDIS_IP = "10.80.41.19";
	private static int REDIS_PORT = 6379;
	private static int MAX_ACTIVE = 200;
	private static int MAX_IDLE = 100;
	private static int MAX_WAIT = 10000;
	private static int TIMEOUT = 10000;
	private static boolean BORROW = true;
	private static JedisPool pool = null;
	
	/**
	 * 初始化Redis池
	 */
	static
	{
		Properties p = new Properties();
		try{
			InputStream in = new FileInputStream(RedisSyncUtil.class.getClassLoader().getResource("/").getPath()+("redis.properties"));
			p.load(in);
		}catch(Exception e){
			e.printStackTrace();
		}
		REDIS_IP = p.getProperty("REDIS_IP");
		REDIS_PORT = Integer.parseInt(p.getProperty("REDIS_PORT", String.valueOf(REDIS_PORT)));
		MAX_ACTIVE = Integer.parseInt(p.getProperty("MAX_ACTIVE",  String.valueOf(MAX_ACTIVE)));
		MAX_IDLE = Integer.parseInt(p.getProperty("MAX_IDLE",  String.valueOf(MAX_IDLE)));
		MAX_WAIT = Integer.parseInt(p.getProperty("MAX_WAIT",  String.valueOf(MAX_WAIT)));
		TIMEOUT = Integer.parseInt(p.getProperty("TIMEOUT",  String.valueOf(TIMEOUT)));

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(BORROW);
		pool = new JedisPool(config,REDIS_IP,REDIS_PORT,TIMEOUT);
	}
	
	public static synchronized Jedis getJedis(){
		try{
			if(pool!=null){
				return pool.getResource();
			}else{
				return null;
			}
		}catch(Exception ex){
			return null;
		}
	}
	
	public static void closeRedis(Jedis jedis){
		if(jedis!=null){
			jedis.close();
		}
	}
	
	public static void returnResource(Jedis jedis){
		if(jedis!=null){
			pool.returnResource(jedis);
		}
	}
	
	public static void addHashMap(String key,HashMap map){
		Jedis jedis = getJedis();
		jedis.hmset(key, map);
		closeRedis(jedis);
	}
		
	public static List<String> getHashMap(String key,String... fields){
		Jedis jedis = getJedis();
		List<String> list = jedis.hmget(key,fields);
		closeRedis(jedis);	
		return list;
	}
	
	public static Long delHashMap(String key,String... fields){
		Jedis jedis = getJedis();
		Long result = jedis.hdel(key,fields);
		closeRedis(jedis);	
		return result;		
	}
	
	public static void addList(String key,String... strings){
		Jedis jedis = getJedis();		
		Long result = jedis.lpush(key, strings);
		closeRedis(jedis);			
	}
	
	public static List<String> getListRange(String key,int start,int end){
		Jedis jedis = getJedis();
		List<String> list = jedis.lrange(key, start, end);
		closeRedis(jedis);			
		return list;
	}
	
	public static String getListFirst(String key){
		Jedis jedis = getJedis();
		String list = jedis.lpop(key);
		closeRedis(jedis);			
		return list;
	}
	
	public static String getListLast(String key){
		Jedis jedis = getJedis();
		String list = jedis.rpop(key);
		closeRedis(jedis);			
		return list;
	}
	
	public static void sortList(String key,String sortkey){
		Jedis jedis = getJedis();
		Long result = jedis.sort(key, sortkey);			
		closeRedis(jedis);			
	}
	
	public static Long getListLen(String key){
		Jedis jedis = getJedis();
		Long result = jedis.llen(key);
		closeRedis(jedis);
		return result;
	}
	
	public static void sortListByField(String key,String prefix,String sortkey,String field,boolean isDesc){
		Jedis jedis = getJedis();
		SortingParams sortParam = new SortingParams();
		String sortBy = prefix + "*-->"+field;
		sortParam.by(sortBy);
		if(isDesc){
			sortParam.desc();
		}else{
			sortParam.asc();
		}
		Long result = jedis.sort(key, sortParam,sortkey);			
		closeRedis(jedis);		
	}
	
	public static void addSet(String key, String ... strings) {
		Jedis jedis = getJedis();
		jedis.sadd(key, strings);
		closeRedis(jedis);
	}
	
	public static Set<String> getSet(String key) {
		Jedis jedis = getJedis();
		Set<String> set = jedis.smembers(key);
		closeRedis(jedis);
		return set;
	}
	
	public static void clear(String key) {
		Jedis jedis = getJedis();
		Iterator<String> it = jedis.keys(key).iterator();
		while(it.hasNext()){
			jedis.del(it.next());
		}
		closeRedis(jedis);
	}
	
	public static Long delKey(String... keys){
		Jedis jedis = getJedis();
		Long result = jedis.del(keys);
		closeRedis(jedis);
		return result;
	}
}
