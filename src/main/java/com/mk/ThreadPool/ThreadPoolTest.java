package com.mk.ThreadPool;


import org.junit.Test;

import java.util.concurrent.*;

public class ThreadPoolTest {
    @Test
    public void test(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                20,100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0;i<100;i++){
            threadPoolExecutor.submit(new Task(i));
            threadPoolExecutor.execute(new Task(i));
        }
    }
    class  Task implements Runnable{
        private int i;
        public Task(int i){
            this.i = i;
        }
        @Override
        public void run() {
            System.out.println(i+" :"+Thread.currentThread().getName());
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
