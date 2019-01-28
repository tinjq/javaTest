package com.tin.json.NutzJson;

import java.util.HashMap;
import java.util.Map;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mapl.Mapl;

import com.tin.bean.UserBean;

public class JsonTest {
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 123L);
		map.put("name", "tin");
		
		String jsonStr = Json.toJson(map, JsonFormat.compact());
		System.out.println(jsonStr);
		
		Object obj = Json.fromJson(jsonStr);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> maplist = (Map<String, Object>) obj;
		System.out.println(maplist.get("name"));
		
		UserBean user = (UserBean) Mapl.maplistToObj(obj, UserBean.class);
		System.out.println(user.getName());
	}

}
