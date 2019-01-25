//package com.kargo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * Created by abner.zhang on 2018/8/31.
// */
//@Configuration
//public class RedisConfig {
//
//
//    @Value("${redis_host}")
//    private String host;
//    @Value("${redis_port}")
//    private Integer port;
//    @Value("${redis_password}")
//    private String password;
//
//
//
//
//
//    @Bean(name = "poolConfig")
//    public JedisPoolConfig poolConfig() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal(2048);
//        poolConfig.setMaxIdle(200);
//        poolConfig.setNumTestsPerEvictionRun(1024);
//        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
//        poolConfig.setMinEvictableIdleTimeMillis(-1);
//        poolConfig.setSoftMinEvictableIdleTimeMillis(10000);
//        poolConfig.setMaxWaitMillis(30000);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setTestOnReturn(false);
//        poolConfig.setJmxEnabled(true);
//        poolConfig.setBlockWhenExhausted(false);
//        return poolConfig;
//    }
//
////    @Bean(name="sentinels")
////    public RedisNode sentinels(){
////        RedisNode redisNode = new RedisNode("115.29.191.31",26379);
////        return  redisNode;
////    }
////
////    @Bean(name="sentinelConfiguration")
////    public RedisSentinelConfiguration sentinelConfiguration(@Autowired RedisNode sentinels){
////        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
//////        sentinelConfiguration.setMaster();
////        Set set = new HashSet();
////        set.add(sentinels);
////        sentinelConfiguration.setSentinels(set);
////        return  sentinelConfiguration;
////    }
//
//    @Bean(name = "connectionFactory")
//    @Autowired
//    public RedisConnectionFactory jedisConnectionFactory(JedisPoolConfig poolConfig) {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master("mymaster")
//                .sentinel(host, port);
//        sentinelConfig.setPassword(RedisPassword.of(password));
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig,poolConfig);
//        return jedisConnectionFactory;
//    }
//
//    @Bean(name = "redisTemplate")
//    @Autowired
//    public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
//        StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
//        return redisTemplate;
//    }
//
//    @Bean(name = "cacheManager")
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        return RedisCacheManager.create(connectionFactory);
//    }
//
//
//}
