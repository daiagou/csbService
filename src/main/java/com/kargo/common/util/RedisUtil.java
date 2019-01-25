package com.kargo.common.util;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author abner.zhang
 *
 */
public class RedisUtil {


    public static Boolean lock (RedisTemplate redisTemplate, String key) {
        Boolean r = redisTemplate.boundValueOps(key).setIfAbsent("1");
        //TODO - if expire fail
        redisTemplate.expire(key, 3, TimeUnit.HOURS);
        return r;
    }

    public static Boolean lock (RedisTemplate redisTemplate, String key, long timeout, TimeUnit timeUnit) {


        Boolean r = redisTemplate.boundValueOps(key).setIfAbsent("1");
        //TODO - if expire fail
        redisTemplate.expire(key, timeout, timeUnit);
        return r;
    }

    public static Boolean lock (RedisTemplate redisTemplate, String key, String value, long timeout, TimeUnit timeUnit) {


        Boolean r = redisTemplate.boundValueOps(key).setIfAbsent(value);
        //TODO - if expire fail
        redisTemplate.expire(key, timeout, timeUnit);
        return r;
    }

    public static void unlock (RedisTemplate redisTemplate, String key) {
        redisTemplate.delete(key);
    }

    public static <V> Long push (RedisTemplate redisTemplate, String key, V object) {
        return redisTemplate.boundListOps(key).leftPush(object);
    }
    public static <V> Long push (RedisTemplate redisTemplate, String key,Set<String> set) {
        BoundListOperations operations = redisTemplate.boundListOps(key);
        Long result = 0L;
        for(Iterator it = set.iterator(); it.hasNext(); )
        {
            long startCacheTime = Calendar.getInstance().getTimeInMillis();
            result+=operations.leftPush(it.next());
            long endCacheTime = Calendar.getInstance().getTimeInMillis();
            System.out.println("genCardNo gen cacheTime:"+(endCacheTime-startCacheTime));
        }
        return result;
    }

    public static String getCache(RedisTemplate redisTemplate,String key){
        return (String)redisTemplate.boundValueOps(key).get();
    }

    public static <V>  void saveCache(RedisTemplate redisTemplate,String key,V object,Long expireInSeconds){
        redisTemplate.boundValueOps(key).set(object, expireInSeconds, TimeUnit.SECONDS);
    }
    public static <V>  void saveCache(RedisTemplate redisTemplate,String key,V object){
        redisTemplate.boundValueOps(key).set(object);
    }

    /**
     * 按天查询缓存
     *
     * @param redisTemplate
     * @param key
     */
    public static String getCacheByDaily(RedisTemplate redisTemplate, String key) {
        return (String)redisTemplate.boundValueOps(key + UtilLocalDate.getDate()).get();
    }

    /**
     * 按键值清除缓存
     * @param redisTemplate
     * @param key
     */
    public static void delete(RedisTemplate redisTemplate,String key){
        redisTemplate.delete(key);
    }


    /**
     * 按天缓存对象
     *
     * @param <V>
     * @param redisTemplate
     * @param key
     * @param object
     * @param expiryInSeconds
     * @return
     */
    public static <V> void saveCacheByDaily(RedisTemplate redisTemplate, String key, V object, Long expiryInSeconds) {
        redisTemplate.boundValueOps(key + UtilLocalDate.getDate()).set(object, expiryInSeconds, TimeUnit.SECONDS);
    }

    public static String pop (RedisTemplate redisTemplate, String key) {
        return (String)redisTemplate.boundListOps(key).rightPop();
    }

    public static  String bpop (RedisTemplate redisTemplate, String key, Long seconds) {
        return (String)redisTemplate.boundListOps(key).rightPop(seconds, TimeUnit.SECONDS);
    }

    /**
     * 原子操作
     * @param redisTemplate
     * @param key
     * @param delta
     * @param timeout
     * @param timeUnit
     * @return
     */
    public static long incrBy(RedisTemplate redisTemplate, String key, long delta, long timeout, TimeUnit timeUnit){
    	Long r = redisTemplate.boundValueOps(key).increment(delta);
    	redisTemplate.expire(key, timeout, timeUnit);
    	return r;
    }
}
