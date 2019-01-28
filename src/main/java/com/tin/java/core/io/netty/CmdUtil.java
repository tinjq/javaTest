package com.tin.java.core.io.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdUtil {
	
	public static void main(String[] args) throws IOException {
		System.out.println(execCmd());
	}
	
	public static String execCmd() throws IOException {
		StringBuffer buffer = new StringBuffer();
		String cmd = "tasklist /nh /fo csv /v /fi \"imagename eq chrome.exe\"";
		Process process = Runtime.getRuntime().exec(cmd);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GB2312"));
		String line = null;
		while ((line = br.readLine()) != null) {
			buffer.append(line + "\n");
		}
		return buffer.toString();
	}

}
