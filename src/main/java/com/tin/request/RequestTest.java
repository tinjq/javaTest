package com.tin.request;

import org.nutz.http.Header;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;

public class RequestTest extends Thread {

	private static String name = null;
	
	public static void main(String[] args) {
		
//		for (int i = 0; i < 10; i++) {
//			name = "aaa" + i;
			new RequestTest().start();
//		}
	}
	
	@Override
	public void run() {
		System.out.println(buildPost(name));
	}

	public static String buildPost(String name) {
		NutMap map = new NutMap("account", "admin").addv("password", "justdoit");
		String url = "http://192.168.0.89:8080/jderp/test3.do";
		
		Request req = Request.create(url, Request.METHOD.POST);
		req.setHeader(Header.create().set("Content-type", "application/json;charset=UTF-8"));
		req.setData(Json.toJson(map));
		
//		Request req = Request.post(url);
//		req.setParams(map);
		
		Response resp = Sender.create(req).send();
		
		return resp.getContent();
	}
	
}
