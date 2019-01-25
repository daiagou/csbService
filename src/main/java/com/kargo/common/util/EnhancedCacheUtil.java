package com.kargo.common.util;

import com.kargo.common.constant.CacheKeyConstant;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
public class EnhancedCacheUtil {

    public static RedisTemplate getRedisTemplate(CacheManager cacheManager) {
		Cache cache = cacheManager.getCache(CacheKeyConstant.CACHE_DEAFAULT);
		return (RedisTemplate) cache.getNativeCache();
    }

    public static Boolean lock (CacheManager cacheManager, String key) {
        RedisTemplate<String, String> redisTemplate = getRedisTemplate (cacheManager);

        Boolean r = redisTemplate.boundValueOps(key).setIfAbsent("1");
        //TODO - if expire fail
        redisTemplate.expire(key, 3, TimeUnit.HOURS);
        return r;
    }

    public static Boolean lock (CacheManager cacheManager, String key, long timeout, TimeUnit timeUnit) {
        RedisTemplate<String, String> redisTemplate = getRedisTemplate (cacheManager);

        Boolean r = redisTemplate.boundValueOps(key).setIfAbsent("1");
        //TODO - if expire fail
        redisTemplate.expire(key, timeout, timeUnit);
        return r;
    }
    
    public static Boolean lock (CacheManager cacheManager, String key, String value, long timeout, TimeUnit timeUnit) {
        RedisTemplate<String, String> redisTemplate = getRedisTemplate (cacheManager);

        Boolean r = redisTemplate.boundValueOps(key).setIfAbsent(value);
        //TODO - if expire fail
        redisTemplate.expire(key, timeout, timeUnit);
        return r;
    }
    
    public static void unlock (CacheManager cacheManager, String key) {
        RedisTemplate<String, String> redisTemplate = getRedisTemplate(cacheManager);
        redisTemplate.delete(key);
    }

    public static <V> Long push (CacheManager cacheManager, String key, V object) {
        RedisTemplate<String, V> redisTemplate = getRedisTemplate (cacheManager);
        return redisTemplate.boundListOps(key).leftPush(object);
    }
    public static <V> Long push (CacheManager cacheManager, String key,Set<String> set) {
        RedisTemplate<String, V> redisTemplate = getRedisTemplate (cacheManager);
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

    public static <V> V getCache(CacheManager cacheManager,String key){
        RedisTemplate<String, V> redisTemplate = getRedisTemplate(cacheManager);
        return redisTemplate.boundValueOps(key).get();
    }

    public static <V>  void saveCache(CacheManager cacheManager,String key,V object,Long expireInSeconds){
        RedisTemplate<String, V> redisTemplate = getRedisTemplate(cacheManager);
        redisTemplate.boundValueOps(key).set(object, expireInSeconds, TimeUnit.SECONDS);
    }

    /**
     * 按天查询缓存
     *
     * @param cacheManager
     * @param key
     * @param <V>
     */
    public static <V> V getCacheByDaily(CacheManager cacheManager, String key) {
        RedisTemplate<String, V> redisTemplate = getRedisTemplate(cacheManager);
        return redisTemplate.boundValueOps(key + UtilLocalDate.getDate()).get();
    }

    /**
     * 按键值清除缓存
     * @param cacheManager
     * @param key
     */
    public static void delete(CacheManager cacheManager,String key){
        getRedisTemplate(cacheManager).delete(key);
    }


    /**
     * 按天缓存对象
     *
     * @param <V>
     * @param cacheManager
     * @param key
     * @param object
     * @param expiryInSeconds
     * @return
     */
    public static <V> void saveCacheByDaily(CacheManager cacheManager, String key, V object, Long expiryInSeconds) {
        RedisTemplate<String, V> redisTemplate = getRedisTemplate(cacheManager);
        redisTemplate.boundValueOps(key + UtilLocalDate.getDate()).set(object, expiryInSeconds, TimeUnit.SECONDS);
    }

    public static <V> V pop (CacheManager cacheManager, String key) {
        RedisTemplate<String, V> redisTemplate = getRedisTemplate (cacheManager);
        return redisTemplate.boundListOps(key).rightPop();
    }

    public static <V> V bpop (CacheManager cacheManager, String key, Long seconds) {
        RedisTemplate<String, V> redisTemplate = getRedisTemplate (cacheManager);
        return redisTemplate.boundListOps(key).rightPop(seconds, TimeUnit.SECONDS);
    }
    
    /**
     * 原子操作
     * @param cacheManager
     * @param key
     * @param delta
     * @param timeout
     * @param timeUnit
     * @return
     */
    public static long incrBy(CacheManager cacheManager, String key, long delta, long timeout, TimeUnit timeUnit){
    	RedisTemplate<String, Long> redisTemplate = getRedisTemplate (cacheManager);
    	Long r = redisTemplate.boundValueOps(key).increment(delta);
    	redisTemplate.expire(key, timeout, timeUnit);
    	return r;
    }
}
