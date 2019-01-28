package com.tin.java.core.thread;

public class ThreadTest extends Thread {

    private int ticket = 100;

    public void run() {
        while (true) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "is saling ticket" + ticket--);
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        ThreadTest t = new ThreadTest();
        new Thread(t).start();
        new Thread(t).start();
        new Thread(t).start();
    }

}
