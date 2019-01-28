package com.tin.java.core.io.socket.test;

import com.tin.utils.ResourceUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {
        try {
            int port = 8888;
            ServerSocket server = new ServerSocket(port);
            System.out.println("server started sucess, port:" + port);
            while(true) {
                Socket socket = server.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = null;
                        PrintWriter pw = null;
                        try {
                            StringBuffer sb = new StringBuffer();
                            is = socket.getInputStream();
                            byte[] b = new byte[1024];
                            int len = -1;
                            while ((len = is.read(b)) != -1) {
                                sb.append(new String(b, 0, len));
                            }
                            System.out.println("client:" + sb.toString());

                            pw = new PrintWriter(socket.getOutputStream());
                            pw.print(sb.toString());
                            pw.flush();
                            socket.shutdownOutput();//关闭输出流
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            ResourceUtil.closeResource(pw, is);
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
