package com.tin.java.core.io.bio;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TinServer {

	private static final Log log = Logs.get();

	//默认的端口号
	private static int PORT = 9999;

	//这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了
	public static void start() {
		ServerSocket server = null;
		try {
			//通过构造函数创建ServerSocket
            //如果端口合法且空闲，服务端就监听成功
			server = new ServerSocket(PORT);
			log.info("server start port:" + PORT);
			//通过无线循环监听客户端连接
			while (true) {
	            //如果没有客户端接入，将阻塞在accept操作上。
				Socket socket = server.accept();
				new Thread(new TinServerHandler(socket)).start();
			}
		} catch (IOException e) {
			log.error("server error:", e);
		} finally {
			if (server != null) {
				try {
					server.close();
					server = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("server closed");
		}
	}

	public static void main(String[] args) {
		start();
	}

}
