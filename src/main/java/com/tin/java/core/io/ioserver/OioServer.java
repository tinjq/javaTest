package com.tin.java.core.io.ioserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传送IO
 *
 * Created by Tin on 2018/11/23.
 */
public class OioServer {

    public static void main(String[] args) {
        oioThreadPoolTest();
    }

    /**
     * 用线程池可以有多个客户端连接，但是非常消耗性能
     */
    public static void oioThreadPoolTest() {
        ExecutorService newCatchThreadPool = Executors.newCachedThreadPool();
        try {
            ServerSocket server = new ServerSocket(10001);
            while (true) {
                //获取一个套接字（阻塞）
                final Socket socket = server.accept();
                System.out.println("来个一个新客户端！");

                newCatchThreadPool.execute(new Runnable() {
                    public void run() {
                        try {
                            InputStream in = socket.getInputStream();
                            byte[] b = new byte[1024];
                            int len = 0;
                            while ((len = in.read(b)) != -1) {
                                System.out.println(new String(b, 0, len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单线程情况下只能有一个客户端
     */
    public static void oioTest() {
        try {
            //创建socket服务,监听10101端口
            ServerSocket server = new ServerSocket(10001);
            while (true) {
                //获取一个套接字（阻塞）
                Socket socket = server.accept();
                System.out.println("来个一个新客户端！");
                InputStream in = socket.getInputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) != -1) {
                    System.out.println(new String(b, 0, len));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
