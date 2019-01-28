package com.tin.java.core.io.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import com.tin.utils.ResourceUtil;

public class TinServer {
	//默认的端口号
	private static int PORT = 12345;
	//单例的ServerSocket
	private static ServerSocket server;
	
	//这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了
	public synchronized static void start() {
		if (server != null) return;
		
		try {
			//通过构造函数创建ServerSocket
            //如果端口合法且空闲，服务端就监听成功
			server = new ServerSocket(PORT);
			System.out.println("server start port:" + PORT);
			//通过无线循环监听客户端连接
			while (true) {
	            //如果没有客户端接入，将阻塞在accept操作上。
				Socket socket = server.accept();
				new Thread(new TinServerHandler(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ResourceUtil.closeResource(server);
			System.out.println("server closed");
		}
	}

}
