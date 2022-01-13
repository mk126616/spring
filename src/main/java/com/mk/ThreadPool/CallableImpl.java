package com.mk.ThreadPool;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CallableImpl implements Callable {
    @Override
    public Object call() throws Exception {
        return "线程执行结果";
    }
    @Test
    public void test(){
        FutureTask futureTask = new FutureTask(new CallableImpl());
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
