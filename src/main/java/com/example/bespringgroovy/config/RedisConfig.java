package com.example.bespringgroovy.config;

import io.lettuce.core.ClientOptions;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * RedisConfig Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Configuration
public class RedisConfig {

  @Bean
  protected LettuceConnectionFactory redisConnectionFactory() {
    RedisProperties properties = redisProperties();
    final ClientOptions clientOptions = ClientOptions.builder().build();
    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
      .commandTimeout(properties.getTimeout())
      .clientOptions(clientOptions).build();
    RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(properties.getHost(),
      properties.getPort());
    return new LettuceConnectionFactory(serverConfig, clientConfig);
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {
    final RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setEnableTransactionSupport(true);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }

  @Bean
  @Primary
  public RedisProperties redisProperties() {
    return new RedisProperties();
  }
}
