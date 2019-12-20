package com.java.custom;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor {
    public static void main(String[] args){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,
                16,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1000),
                new CustomPolicy());
    }
    static class CustomPolicy implements RejectedExecutionHandler {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.out.println("自定义处理：开始记录日志");
            System.out.println(r.toString());
            System.out.println("自定义处理：记录日志完成");
        }
    }
}
