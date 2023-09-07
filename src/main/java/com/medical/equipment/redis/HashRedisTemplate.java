package com.medical.equipment.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class HashRedisTemplate extends RedisTemplate {
 
    public HashRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        this.setConnectionFactory(redisConnectionFactory);
        this.setKeySerializer(new StringRedisSerializer());
        this.setValueSerializer(new StringRedisSerializer());
        this.setHashKeySerializer(new StringRedisSerializer());
        this.setHashValueSerializer(new StringRedisSerializer());
        this.afterPropertiesSet();
    }
}