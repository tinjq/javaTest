package com.tin.java.core.io.nio.gupao;

import java.net.InetSocketAddress;

/**
 * Created by Tin on 2018/11/24.
 */
public class SocketTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new NioServerTest(10001).start();
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 10001);
                new NioClientTest(addr).send(Thread.currentThread().getName() + "\n 你收到了吗------------------");
            }
        }).start();

    }


}
