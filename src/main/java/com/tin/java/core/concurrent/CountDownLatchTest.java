package com.tin.java.core.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Java并发编程：CountDownLatch、CyclicBarrier和 Semaphore
 * http://www.importnew.com/21889.html
 *
 * Created by Tin on 2018/11/12.
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        // countDownLatchTest();
        // cyclicBarrierTest();
        // cyclicBarrierTest1();
        semaphoreTest();
    }

    /**
     * CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。
     * 比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了
     */
    public static void countDownLatchTest() {
        final CountDownLatch latch = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程 " + Thread.currentThread().getName() + " 正在执行...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("子线程 " + Thread.currentThread().getName() + " 执行结束...");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程 " + Thread.currentThread().getName() + " 正在执行...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("子线程 " + Thread.currentThread().getName() + " 执行结束...");
            }
        }).start();

        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已执行完毕...");
            System.out.println("执行主线程...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 字面意思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。
     * 叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用。
     * 我们暂且把这个状态就叫做barrier，当调用await()方法之后，线程就处于barrier了。
     */
    public static void cyclicBarrierTest() {
        int N = 4;
        final CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
                    try {
                        Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                        System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                        barrier.await();
                        System.out.println("所有线程写入完毕，线程 " + Thread.currentThread().getName() + " 继续处理其他任务...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }catch(BrokenBarrierException e){
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    /**
     * 如果说想在所有线程写入操作完之后，进行额外的其他操作可以为CyclicBarrier提供Runnable参数.
     * 从结果可以看出，当四个线程都到达barrier状态后，会从四个线程中选择一个线程去执行Runnable。
     */
    public static void cyclicBarrierTest1() {
        int N = 4;
        final CyclicBarrier barrier  = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程 " + Thread.currentThread().getName());
            }
        });
        for(int i=0;i<N;i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
                    try {
                        Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                        System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                        barrier.await();
                        System.out.println("所有线程写入完毕，线程 " + Thread.currentThread().getName() + " 继续处理其他任务...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }catch(BrokenBarrierException e){
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    public static void semaphoreTest() {
        final Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 8; i++) {
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("工人 " + num + " 正在使用机器...");
                        Thread.sleep(2000);
                        semaphore.release();
                        System.out.println("工人 " + num + " 释放了机器...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

}
