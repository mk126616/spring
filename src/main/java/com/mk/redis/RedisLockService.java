package com.mk.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis实现分布式锁
 */
//@Service
public class RedisLockService
{
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * lua脚本操作redis
     */
    public void operateByLua(){
        //        lua脚本操作redis示例
        String script = "local key = KEYS[1]" +
                "local value = ARGV[1]" +
                "local expireInSecs = ARGV[2]" +
                "return redis.call(\"set\",key,value,\"EX\",expireInSecs,\"NX\")";
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setScriptText(script);
        List keys = Arrays.asList("lock");
        List<String> args = Arrays.asList("my_value1", "15");
        redisTemplate.execute(redisScript, keys, args);
    }

    /**
     * 基于redis的分布式锁实现
     */
    public void lockTest()
    {
        String uuid = UUID.randomUUID().toString();
        //互斥操作上锁并增加过期时间
        boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid,5, TimeUnit.SECONDS);
        if (lock)
        {
            //开启守护线程检查主线程状态并且延长锁的过期时间
            DaemonThread daemonThread = new DaemonThread(Thread.currentThread(), uuid);
            daemonThread.start();

            //业务操作共享资源访问
            try
            {
                Object o = redisTemplate.opsForValue().get("num");
                int num = 0;
                if (o != null)
                {
                    num = (int) o;
                }
                redisTemplate.opsForValue().set("num", ++num);
                System.out.println(num);
                //模拟业务阻塞耗时太久的情况
                Thread.currentThread().sleep(15000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }


            //检查是否为自己加的锁，是则释放锁
            if (uuid.equals(redisTemplate.opsForValue().get("lock")))
            {
                redisTemplate.delete("lock");
            }
            daemonThread.killDaemon();

        }
        else
        {
            try
            {
                Thread.sleep(100);
                lockTest();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * 分布式锁的守护线程
     * 作用是：延长锁的过期时间，防止锁过期出现的多个线程获得了锁，同时访问临界资源
     */
    private class DaemonThread extends Thread
    {
        private Thread thread;

        //锁的唯一标识
        private String uuid;

        private volatile boolean killFlag = false;

        public DaemonThread(Thread thread, String uuid)
        {
            System.out.println("守护线程创建");
            this.thread = thread;
            this.uuid = uuid;
        }

        @Override
        public void run()
        {

            while (!killFlag)
            {
                try
                {
                    thread.sleep(2000);
                    //线程还存活着则延长锁的时间
                    if (thread.isAlive() && uuid.equals(redisTemplate.opsForValue().get("lock")))
                    {
                        System.out.println("守护线程重置了锁过期时间");
                        redisTemplate.expire("lock", 5, TimeUnit.SECONDS);
                    }
                    else
                    {
                        //主线程结束或者非正常挂掉则停止当前线程
                        killFlag = true;
                        System.out.println("守护线程退出");
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        public void killDaemon()
        {
            killFlag = true;
        }
    }

}
