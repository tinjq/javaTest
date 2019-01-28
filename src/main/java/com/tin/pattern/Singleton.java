package com.tin.pattern;

/**
 * 单例多线程问题
 * 
 * @author Tin
 *
 */
public class Singleton {

	// 静态内部类方法实现懒加载单例
	private static class SingletonInstance {
		private static Singleton instance = new Singleton();
	}

	private Singleton() {}

	public static Singleton getInstatnce() {
		return SingletonInstance.instance;
	}
	
	private int i = 10;

	public void pro() {
		System.out.println(Thread.currentThread().getId() + " in...");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getId() + ", i:" + i++);
		System.out.println(Thread.currentThread().getId() + " out...");
	}

}
