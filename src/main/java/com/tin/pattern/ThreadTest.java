package com.tin.pattern;


public class ThreadTest extends Thread {

	@Override
	public void run() {
		Singleton.getInstatnce().pro();
	}
	
	public static void main(String[] args) {
		try {
			new ThreadTest().start();
			Thread.sleep(1000);
			new ThreadTest().start();
			Thread.sleep(1000);
			new ThreadTest().start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
