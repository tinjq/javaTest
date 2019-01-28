package com.tin.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import redis.clients.jedis.Jedis;

public class Test {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1"); // 连接
		jedis.auth("justdoit"); // 密码授权
		
		// 检测redis服务是否启动
		System.out.println(jedis.ping());
		
		// 获取所有“键”名称
		System.out.println("获取所有“键”名称");
		Set<String> keys = jedis.keys("*");
		for (String key : keys) {
			System.out.println(key + "\ttype:" + jedis.type(key));
		}
		System.out.println("---------------------");
		
		// 设置 key-value 键值对
		jedis.set("keyTest", "value测试");
		// 取值
		System.out.println("keyTest:" + jedis.get("keyTest"));
		
		jedis.set("keyTest1", "value测试1");
		System.out.println("keyTest1:" + jedis.get("keyTest1"));
		
		long result = jedis.del("keyTest1");
		System.out.println("删除keyTest1, result:"+result+", keyTest1:" + jedis.get("keyTest1") + ", 是否还存在：" + jedis.exists("keyTest1"));
		
		System.out.println("---------------------");
		
		long listLength = jedis.llen("listTest");
		System.out.println("list listTest 的大小:" + listLength);
		if (listLength == 0) {
			List<String> list = getList();
			// 存list
			for (String value : list) {
				jedis.rpush("listTest", value);
			}
		}
		
		// 取list
		List<String> listFetch = jedis.lrange("listTest", 0, listLength);
		for (int i = 0; i < listFetch.size(); i++) {
			System.out.println(i + " " + jedis.lindex("listTest", i));
		}
		jedis.lset("listTest", 0, "listValue0" + System.currentTimeMillis());
		
		System.out.println("---------------------");
		
		long setSize = jedis.scard("setTest");
		System.out.println("setTest 大小：" + setSize);
		if (setSize == 0) {
			Set<String> set = getSet();
			// 存set
			for (String value : set) {
				jedis.sadd("setTest", value);
			}
		}
		
		// 取set
		Set<String> setFetch = jedis.smembers("setTest");
		for (String value : setFetch) {
			System.out.println(value);
		}
		
		// 关闭
		jedis.close();
	}
	
	public static List<String> getList() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			list.add("listValue" + i);
		}
		return list;
	}
	
	public static Set<String> getSet() {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < 5; i++) {
			set.add("setValue" + i);
		}
		return set;
	}
	
	public static Set<String> getTreeSet() {
		Set<String> set = new TreeSet<String>();
		set.add("aaa1");
		set.add("aaa3");
		set.add("aaa9");
		set.add("aaa7");
		return set;
	}
	
}
