package com.tin.java.core.io.bio;

import com.tin.utils.ResourceUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Tin on 2018/11/15.
 */
public class TinServerHandler implements Runnable {

    private Socket socket;

    public TinServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String content;
            while ((content = in.readLine()) != null) {
                // 通过BufferedReader读取一行
                // 如果已经读到输入流尾部，返回null,退出循环
                // 如果得到非空值，就尝试计算结果并返回
                System.out.println("[server] - " + content);
                // ?? https://blog.csdn.net/lizichen147/article/details/76521417
                // out.write(content);
                out.println("server reply：Hello " + content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //一些必要的清理工作
            ResourceUtil.closeResource(in, out, socket);
        }
    }

}
