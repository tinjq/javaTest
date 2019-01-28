package com.tin.java.core.io.bio;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import com.tin.utils.ResourceUtil;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class TinClient {

    private static final Log log = Logs.get();

    public static String send(String host, int port, String msg) {
        String result = null;
        Socket socket = null;
        PrintWriter out = null;
        InputStream in = null;
        try {
            socket = new Socket(host, port);

            out = new PrintWriter(socket.getOutputStream());
            out.print(msg);
            out.flush();
            socket.shutdownOutput();

            in = socket.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] b = new byte[1024];
            int len;
            while ((len = in.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }

            result = sb.toString();
        } catch (Exception e) {
            log.info("client error:", e);
        } finally {
            ResourceUtil.closeResource(in, out);
            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String result = send("127.0.0.1", 9999, "aaa\nbbb\nccc");
        System.out.println(result);
    }

}
