package com.tenkent.infrastructure.jedis;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;

/**
 * jedis操作类
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class JedisCacheManager {
    /**
     * redis过期时间单位为秒
     * 默认为不过期
     * 0 - never expire
     */
    private int expire = 0;
    
    /**
     * jedisPool配置对象
     * 通过配置注入
     */
    private JedisPool jedisPool;
    
    public JedisCacheManager(JedisPool jedisPool) {
        this(jedisPool, 0);
    }
    
    public JedisCacheManager(JedisPool jedisPool, int expire) {
        this.expire = expire;
        this.jedisPool = jedisPool;
    }
    
    /**
     * 存储REDIS队列 顺序存储
     * 
     * @param key
     *            reids键名
     * @param value
     *            键值
     */
    public void lpush(byte[] key, byte[] value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(key, value);
        }
    }
    
    /**
     * Pipeline存储REDIS队列 顺序存储
     * 
     * @param key
     *            reids键名
     * @param value
     *            键值
     */
    public void lpushPipeline(byte[] key, List<byte[]> values) {
        try (Jedis jedis = jedisPool.getResource()) {
            Pipeline pl = jedis.pipelined();
            for (byte[] bs : values) {
                pl.lpush(key, bs);
            }
            pl.sync();
        }
    }
    
    /**
     * 存储REDIS队列 反向存储
     * 
     * @param key
     *            reids键名
     * @param value
     *            键值
     */
    public void rpush(byte[] key, byte[] value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush(key, value);
        }
    }
    
    /**
     * 将列表 队列 中的最后一个元素(尾元素)弹出，并返回给客户端
     * 
     * @param key
     *            reids键名
     * @param destination
     *            键值
     */
    public void rpoplpush(byte[] key, byte[] destination) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpoplpush(key, destination);
        }
    }
    
    /**
     * 获取队列数据
     * 
     * @param key
     *            键名
     * @return
     */
    public List<byte[]> lpopList(byte[] key) {
        List<byte[]> list = null;
        try (Jedis jedis = jedisPool.getResource()) {
            list = jedis.lrange(key, 0, -1);
        }
        return list;
    }
    
    /**
     * Pipeline获取队列数据
     * 
     * @param key
     *            键名
     * @param quantity
     *            数量
     * @return
     */
    public List<Object> rpopPipeline(byte[] key, int quantity) {
        List<Object> list = null;
        try (Jedis jedis = jedisPool.getResource()) {
            Pipeline pl = jedis.pipelined();
            for (int i = 0; i < quantity; i++) {
                pl.rpop(key);
            }
            list = pl.syncAndReturnAll();
        }
        return list;
    }
    
    /**
     * 获取队列数据
     * 
     * @param key
     *            键名
     * @return
     */
    public byte[] rpop(byte[] key) {
        
        byte[] bytes = null;
        try (Jedis jedis = jedisPool.getResource()) {
            bytes = jedis.rpop(key);
        }
        return bytes;
    }
    
    /**
     * 获取指定key的值
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        byte[] value = null;
        
        try (Jedis jedis = jedisPool.getResource();) {
            value = jedis.get(key);
        }
        
        return value;
    }
    
    /**
     * 获取指定key的值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String value;
        try (Jedis jedis = jedisPool.getResource();) {
            value = jedis.get(key);
        }
        
        return value;
    }
    
    /**
     * 设置指定key的值为value
     *
     * @param key
     * @param value
     * @return
     */
    public byte[] set(byte[] key, byte[] value) {
        try (Jedis jedis = jedisPool.getResource();) {
            jedis.set(key, value);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
        }
        
        return value;
    }
    
    /**
     * 设置指定key的值为value
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource();) {
            jedis.set(key, value);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
        }
        return value;
    }
    
    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public byte[] set(byte[] key, byte[] value, int expire) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, expire, value);
        }
        return value;
    }
    
    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public String set(String key, String value, int expire) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, expire, value);
        }
        return value;
    }
    
    /**
     * 删除redis中指定key的缓存，key以字节数组方式提供
     *
     * @param key
     */
    public void del(byte[] key) {
        try (Jedis jedis = jedisPool.getResource();) {
            jedis.del(key);
        }
    }
    
    /*
     * 删除redis中指定key的缓存，key以字符串方式提供
     */
    public void del(String key) {
        try (Jedis jedis = jedisPool.getResource();) {
            jedis.del(key);
        }
    }
    
    /**
     * del
     *
     * @param keySet
     */
    public void delKeySet(Set<byte[]> keySet) {
        byte[][] keys = new byte[keySet.size()][];
        
        int keyIndex = 0;
        Iterator<byte[]> it = keySet.iterator();
        while (it.hasNext()) {
            keys[keyIndex] = it.next();
            keyIndex++;
        }
        
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(keys);
        }
    }
    
    /**
     * 删除redis当前DB中所有的key,谨慎使用
     */
    public void flushDB() {
        try (Jedis jedis = jedisPool.getResource();) {
            jedis.flushDB();
        }
    }
    
    /**
     * 获取当前DB中所有key的数量
     */
    public Long dbSize() {
        Long dbSize = 0L;
        try (Jedis jedis = jedisPool.getResource();) {
            dbSize = jedis.dbSize();
        }
        return dbSize;
    }
    
    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource();) {
            return jedis.exists(key);
        }
    }
    
    /**
     * keys
     *
     * @param
     * @return
     */
    public Set<byte[]> keys(String pattern) {
        Set<byte[]> keys = null;
        try (Jedis jedis = jedisPool.getResource();) {
            keys = jedis.keys(pattern.getBytes(Charset.forName("UTF-8")));
        }
        return keys;
    }
    
    /*
     * 当指定的key不存在，则设置key的值为value，如果存在则不设置
     * 如果设置成功返回1，如果没有设置返回0
     */
    public long setnx(String key, String value) {
        try (Jedis jedis = jedisPool.getResource();) {
            long result = jedis.setnx(key, value);
            return result;
        }
    }
    
    /*
     * 给指定的key设置值为value并返回设置之前的值，此操作为原子操作，线程安全
     *
     */
    public String getSet(String key, String value) {
        try (Jedis jedis = jedisPool.getResource();) {
            String result = jedis.getSet(key, value);
            return result;
        }
    }
    
    /*
     * 删除当前redis所有DB中的所有key，谨慎使用
     */
    public String flushAll() {
        try (Jedis jedis = jedisPool.getResource();) {
            String result = jedis.flushAll();
            return result;
        }
    }
    
    /**
     * 设置Redis缓存过期时间
     *
     * @param key
     * @param expire
     * @return 设置后过期时间
     */
    public Long expire(byte[] key, int expire) {
        try (Jedis jedis = jedisPool.getResource();) {
            Long result = jedis.expire(key, expire);
            return result;
        }
    }
    
    /**
     * 设置Redis缓存过期时间
     *
     * @param key
     * @param expire
     * @return 设置后过期时间
     */
    public Long expire(String key, int expire) {
        try (Jedis jedis = jedisPool.getResource();) {
            Long result = jedis.expire(key, expire);
            return result;
        }
    }
    
    /**
     * 订阅
     * @param jedisPubSub
     * @param channels
     * @see [类、类#方法、类#成员]
     */
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.subscribe(jedisPubSub, channels);
        }
    }
    
    /**
     * 发布
     * @param channel
     * @param msg
     * @see [类、类#方法、类#成员]
     */
    public void publish(String channel, String msg) {
        try (Jedis jedis = jedisPool.getResource();) {
            jedis.publish(channel, msg);
        }
    }
    
    /**
     * 给指定key中的值增加1并返回
     * 如果没有key或者key中的值类型不是数字，会先把值设置为0，然后再加1返回
     * @param key
     * @return
     */
    public long incr(String key) {
        try (Jedis jedis = jedisPool.getResource();) {
            Long result = jedis.incr(key);
            return result;
        }
    }
    
    /**
     * 给指定key中的值减1并返回
     * 如果没有key或者key中的值类型不是数字，会先把值设置为0，然后再减1返回
     * @param key
     * @return
     */
    public long decr(String key) {
        try (Jedis jedis = jedisPool.getResource();) {
            Long result = jedis.decr(key);
            return result;
        }
    }
    
    /**
     * redis过期时间单位为秒
     * 默认为不过期
     * 0 - never expire
     * @return
     */
    public int getExpire() {
        return expire;
    }
    
    /**
     * redis过期时间单位为秒
     * 默认为不过期
     * 0 - never expire
     * @param expire
     */
    public void setExpire(int expire) {
        this.expire = expire;
    }
    
    /**
     * jedisPool配置对象
     * 通过配置注入
     * @return
     */
    public JedisPool getJedisPool() {
        return this.jedisPool;
    }
    
    /**
     * jedisPool配置对象
     * 通过配置注入
     * @param jedisPool
     */
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
