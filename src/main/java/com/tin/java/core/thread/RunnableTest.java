package com.tin.java.core.thread;

/**
 * Runnable和Thread的区别
 * https://blog.csdn.net/jaakov2010/article/details/52744843
 */
public class RunnableTest implements Runnable {

    private int i = 100;

    @Override
    public void run() {
        while (true) {
            if (i > 0) {
                System.out.println(Thread.currentThread().getName() + "\t" + i--);
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        RunnableTest t = new RunnableTest();
        new Thread(t, "aaa").start();
        new Thread(t, "bbb").start();
        new Thread(t, "ccc").start();
    }

}
