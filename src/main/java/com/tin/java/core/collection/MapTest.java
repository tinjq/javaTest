package com.tin.java.core.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapTest {
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> linkedMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < 10; i++) {
			map.put("key" + i, "value" + i);
			linkedMap.put("key" + i, "value" + i);
		}
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " --> " + entry.getValue());
		}
		for (Entry<String, String> entry : linkedMap.entrySet()) {
			System.out.println(entry.getKey() + " --> " + entry.getValue());
		}
	}

}
