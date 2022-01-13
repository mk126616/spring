package com.mk.springexpression;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = ("classpath:spring-mvc.xml"))
public class RedisTest
{
    @Autowired
    private RedisTemplate redisTemplate;

//    @Test
    public void redisTest(){
//        try
//        {
//            //开启事务
//            redisTemplate.multi();
//            redisTemplate.opsForValue().set("transation:test.key1","v1");
//            redisTemplate.opsForValue().set("transation:test.key2","v2");
//        }catch (Exception e){
//            redisTemplate.discard();
//        }
//        redisTemplate.opsForZSet().add("zset:test.key1","张三",-10);
//        //提交事务
//        redisTemplate.exec();

        redisTemplate.opsForValue().set("int.value",10);
//        JedisSentinelPool
//        redisTemplate.watch("");
//        redisTemplate.multi();
//
//        redisTemplate.exec();

    }
}
