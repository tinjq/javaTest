package com.tin.java.core.io.bio;

import com.tin.utils.DateUtil;

public class TinTest {

	public static void main(String[] args) {
		//运行服务器
		new Thread(new Runnable() {
			@Override
			public void run() {
				TinServer.start();
			}
		}).start();
		
		//避免客户端先于服务器启动前执行代码
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//运行客户端
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					System.out.println("-----------------------------------");
					TinClient.send(DateUtil.getNow() + "\naaaa");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}
	
}
