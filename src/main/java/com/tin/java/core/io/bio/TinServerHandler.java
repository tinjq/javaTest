package com.tin.java.core.io.bio;

import com.tin.utils.ResourceUtil;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Tin on 2018/11/15.
 */
public class TinServerHandler implements Runnable {

    private static final Log log = Logs.get();

    private Socket socket;

    public TinServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        PrintWriter out = null;
        try {
            in = socket.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] b = new byte[1024];
            int len;
            while ((len = in.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            String result = sb.toString();
            log.info("-------> client:" + result);

            out = new PrintWriter(socket.getOutputStream());
            out.print("Hello " + result);
            out.flush();
            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //一些必要的清理工作
            ResourceUtil.closeResource(in, out, socket);
        }
    }

}
