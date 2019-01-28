package com.tin.java.core.io.socket.test;

import com.tin.utils.ResourceUtil;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 1000; i++) {
            sb.append("aaaaaaaaaa" + i + "\n");
        }
        String result = send("127.0.0.1", 8888, sb.toString());
        System.out.println("[" + result.length() + "]:" + result);
    }

    public static String send(String host, int port, String msg) {
        Socket socket = null;
        InputStream is = null;
        PrintWriter pw = null;
        try {
            socket = new Socket(host, port);
            pw = new PrintWriter(socket.getOutputStream());
            pw.print(msg);
            pw.flush();
            socket.shutdownOutput();

            StringBuffer sb = new StringBuffer();
            is = socket.getInputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = is.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.closeResource(pw, is, socket);
        }
        return null;
    }

}
