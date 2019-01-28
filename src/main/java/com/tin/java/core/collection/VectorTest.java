package com.tin.java.core.collection;

import java.util.ArrayList;
import java.util.List;

import org.nutz.lang.util.NutMap;

public class VectorTest {

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("aaa");
		list.add(new Integer(23));
		list.add(new NutMap("s", "sds"));
		System.out.println(list);
	}
	
}
