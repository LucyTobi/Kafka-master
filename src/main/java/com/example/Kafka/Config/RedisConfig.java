package com.example.Kafka.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value(value = "${spring.redis.host}")
    private String hostRedis;
    @Value(value = "${spring.redis.port}")
    private int portRedis;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        // Tạo Standalone Connection tới Redis
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(hostRedis);
        config.setPort(portRedis);

        LettuceConnectionFactory factory = new LettuceConnectionFactory(config);

        return factory;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public RedisTemplate strRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory());
        return stringRedisTemplate;
    }
}
