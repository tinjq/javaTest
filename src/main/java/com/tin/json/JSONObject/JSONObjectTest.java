package com.tin.json.JSONObject;

import net.sf.json.JSONObject;

/**
 * 需要的六个jar包：
 * 		commons-beanutils-1.7.0.jar, 
 * 		commons-collections-3.2.1.jar, 
 * 		commons-lang-2.3.jar,
 * 		commons-logging.jar,
 * 		ezmorph-1.0.6.jar,
 * 		json-lib-2.2.3-jdk13.jar
 * 
 * @author Tin
 *
 */
public class JSONObjectTest {

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("name", "zwm");
		json.put("url", "https://www.baidu.com/");
		System.out.println(json.toString());
	}
	
}
