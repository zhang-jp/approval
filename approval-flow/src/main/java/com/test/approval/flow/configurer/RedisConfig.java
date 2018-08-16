package com.test.approval.flow.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.tenkent.infrastructure.jedis.JedisCacheManager;
import com.tenkent.infrastructure.log.LoggerManager;
import com.tenkent.infrastructure.utility.JsonUtility;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig
{
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    
    @Autowired
    private RedisProperties redisProperties;
    
    /**
     *  实例化 RedisTemplate 对象
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> intRedisTemplate()
    {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }
    
    @Bean
    public JedisPool jedisPool()
    {
        LoggerManager.error(getClass(), null, "redis config:{}", JsonUtility.toJSONString(redisProperties));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        jedisPoolConfig.setMaxTotal(redisProperties.getPool().getMaxActive());
        JedisPool pool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort());
        return pool;
    }
    
    @Bean
    public JedisCacheManager jedisCacheManager()
    {
        return new JedisCacheManager(jedisPool());
    }
    
    /**
     * 设置数据存入 redis 的序列化方式
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory)
    {
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }
}