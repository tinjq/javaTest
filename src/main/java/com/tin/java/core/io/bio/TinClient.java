package com.tin.java.core.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.tin.utils.ResourceUtil;

public class TinClient {
	
	private static int PORT = 12345;
	
	private static String IP = "127.0.0.1";
	
	public static void send(String content) {
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			socket = new Socket(IP, PORT);

			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println("Client say: " + content);

			System.out.println("[client] - " + in.readLine());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ResourceUtil.closeResource(out, socket);
		}
	}

}
