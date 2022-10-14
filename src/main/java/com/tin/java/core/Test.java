package com.tin.java.core;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.IdentityHashMap;

public class Test {

	public static void main(String[] args) {
//		System.out.println("3".compareTo("20"));
//		DecimalFormat df = new DecimalFormat("00000");
//		for (int i = 0; i < 200; i+=5) {
//			System.out.println(df.format(i));
//		}

		IdentityHashMap<String, String> map1 = new IdentityHashMap<>();
		HashMap<String, String> map2 = new HashMap<>();
		map1.put("aaa", "aaaaaa");
		map1.put("aaa", "aaaaaa1");
		map1.put(new String("bbb"), "bbbb1");
		map1.put(new String("bbb"), "bbbb2");

		map2.put("aaa", "aaaaaa");
		map2.put("aaa", "aaaaaa1");

		System.out.println(map1);
		System.out.println(map2);

		HashMap<MyType, String> map3 = new HashMap<>();
		map3.put(new MyType("aaa", "bbb"), "bbbb");
		map3.put(new MyType("aaa", "bbb"), "bbbb2");
		System.out.println(map3);
	}

	static class MyType {
		private String arga;
		private String argb;

		public MyType(String arga, String argb) {
			this.arga = arga;
			this.argb = argb;
		}

		@Override
		public int hashCode(){
			return this.arga.hashCode() * this.argb.hashCode() ;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof MyType)) {
				return false;
			}
			MyType p = (MyType) obj;
			if (this.arga.equals(p.arga) && this.argb.equals(p.argb)) {
				return true ;
			} else {
				return false ;
			}
		}

		@Override
		public String toString() {
			return "MyType{" +
					"arga='" + arga + '\'' +
					", argb='" + argb + '\'' +
					'}';
		}
	}
	
}
