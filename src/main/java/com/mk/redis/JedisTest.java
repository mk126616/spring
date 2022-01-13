package com.mk.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JedisTest
{
    private static ShardedJedisPool pool;

    @Test
    public void test(){
        ShardedJedis jedis = pool.getResource();
//        String类型操作
//        jedis.set("k1", "v11");
//        jedis.set("redis.test:spring.redis.template", "spring");

//        jedis.setnx("k1", "v111");
//        System.out.println(jedis.get("k1"));
////     hash类型操作
//        Map<String,String> map = new HashMap<>();
//        map.put("name","张三");
//        map.put("age","18");
//        map.put("sex","男");
//        jedis.hmset("hash_k1",map);
//        System.out.println(jedis.hget("hash_k1","name"));
//        System.out.println(jedis.hvals("hash_k1"));
//        //list类型操作
//        jedis.lpush("list_k1","张三","李四","王五");
//        System.out.println(jedis.lrange("list_k1",0,10));
////        set类型操作
//        jedis.sadd("set_key1","张三","李四");
//        System.out.println(jedis.smembers("set_key1"));
//        zset操作
        jedis.zadd("zset_k1",50,"wangwu");
        jedis.zadd("zset_k1",80,"zhangsan");
        jedis.zadd("zset_k1",60,"李四");
        jedis.expire("zset_k1",100);
        jedis.close();
    }

    /**
     * 集群操作redis
     */
    public void clusterOperate(){
        HostAndPort hostAndPort1 = new HostAndPort("192.168.62.129",6379);
        JedisCluster jedisCluster = new JedisCluster(hostAndPort1);
        jedisCluster.set("k1","v1");
        try
        {
            jedisCluster.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    static{
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("192.168.62.129",6379);
        jedisShardInfo1.setPassword("123456");
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(jedisShardInfo1);
        pool = new ShardedJedisPool(config, list);
    }
}
