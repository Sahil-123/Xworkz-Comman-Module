package com.xworkz.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@PropertySource("classpath:redis.properties")
public class RedisCacheConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.port}")
    private int port;

    public RedisCacheConfig() {
        System.out.println("Redis config initiated.");
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        System.out.println("Redis connection factory is created");
        System.out.println("Host :"+host);
        System.out.println("port :"+port);
        System.out.println("password :"+password);

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use Jackson JSON serializer
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        template.setDefaultSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        System.out.println("Redis Cache Manager is created.");

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//        redisCacheConfiguration.
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
    }

}
