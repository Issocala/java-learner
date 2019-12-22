package com.java.custom;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class CustomThreadPoolExecutor {
    public static void main(String[] args){
        //　创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,   // 核心线程数
                1, // 最大线程数
                60, // 空闲非核心线程关闭时间
                TimeUnit.SECONDS,   //时间单位
                new ArrayBlockingQueue<Runnable>(1), // 创建工作队列
                new CustomPolicy()); //　拒绝策略
        for(int i = 0; i < 5; i++){
            threadPoolExecutor.execute((Runnable)()->{
                try {
                    System.out.println("成功运行！！");
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 关闭线程池
        threadPoolExecutor.shutdown();
    }
    //　自定义拒绝策略
    static class CustomPolicy implements RejectedExecutionHandler {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.out.println("-------线程任务堆积，出现任务被拒绝！！！！！");
        }
    }
}
