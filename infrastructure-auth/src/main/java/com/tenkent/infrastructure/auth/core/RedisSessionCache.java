package com.tenkent.infrastructure.auth.core;

import com.tenkent.infrastructure.auth.api.IAuthSessionCache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis缓存
 * @author  qinzhengliang
 * @version  [版本号, 2018年1月23日]
 */
public class RedisSessionCache implements IAuthSessionCache
{
    /**
     * jedis 链接池
     */
    private JedisPool jedisPool;
    
    /**
     * 保存路径
     */
    private String saveKey;
    
    public RedisSessionCache()
    {
    }
    
    public RedisSessionCache(JedisPool jedisPool, String saveKey)
    {
        this.jedisPool = jedisPool;
        this.saveKey = saveKey;
    }
    
    /**
     * 保存用户
     */
    @Override
    public void set(String key, String loginUser, int seconds)
    {
        try (Jedis jedis = jedisPool.getResource())
        {
            jedis.setex(getKey(key), seconds, loginUser);
        }
    }
    
    @Override
    public boolean update(String key, String loginUser, int seconds)
    {
        if (exists(getKey(key)))
        {
            set(key, loginUser, seconds);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean update(String key, int seconds)
    {
        Long result = expire(getKey(key), seconds);
        return result == 1;
    }
    
    @Override
    public String get(String key)
    {
        String value = "";
        try (Jedis jedis = jedisPool.getResource();)
        {
            value = jedis.get(getKey(key));
        }
        
        return value;
    }
    
    @Override
    public void remove(String key)
    {
        del(getKey(key));
    }
    
    private boolean exists(String key)
    {
        try (Jedis jedis = jedisPool.getResource();)
        {
            return jedis.exists(key);
        }
    }
    
    private Long expire(String key, int expire)
    {
        try (Jedis jedis = jedisPool.getResource();)
        {
            Long result = jedis.expire(key, expire);
            return result;
        }
    }
    
    private void del(String key)
    {
        try (Jedis jedis = jedisPool.getResource();)
        {
            jedis.del(key);
        }
    }
    
    public JedisPool getJedisPool()
    {
        return jedisPool;
    }
    
    public void setJedisPool(JedisPool jedisPool)
    {
        this.jedisPool = jedisPool;
    }
    
    public String getSaveKey()
    {
        return saveKey;
    }
    
    public void setSaveKey(String saveKey)
    {
        this.saveKey = saveKey;
    }
    
    private String getKey(String key)
    {
        return "security:session:" + saveKey + ":" + key;
    }
}
