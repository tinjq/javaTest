package com.tin.java.core.io.nio.gupao;

import com.tin.utils.DateUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Tin on 2018/11/24.
 */
public class NioClientTest {

    private InetSocketAddress inetSocketAddress;

    public NioClientTest(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public String send(String content) {
        String result = null;
        Selector selector = null;
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            selector = Selector.open();
            int interest = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
            socketChannel.register(selector, interest, new Buffers(1024, 1024));

            socketChannel.connect(inetSocketAddress);
            while (!socketChannel.finishConnect()) {

            }
            System.out.println(Thread.currentThread().getName() + " finished connection");
            boolean received = false;
            while (!received) {
                selector.select();
                System.out.println(Thread.currentThread().getName() + " selector.selected");

                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> it = keySet.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();

                    Buffers buffers = (Buffers) key.attachment();
                    ByteBuffer readBuffer = buffers.getReadBuffer();
                    ByteBuffer writeBuffer = buffers.getWriteBuffer();

                    if (key.isReadable()) {
                        System.out.println("key.isReadable");
                        // TODO
                        int len = socketChannel.read(readBuffer);
                        readBuffer.flip();
                        result = "[client] - " + new String(readBuffer.array(), 0, len);
                        readBuffer.clear();
                        received = true;
                        // key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        System.out.println("key.isWritable");
                        writeBuffer.put(DateUtil.getNow().getBytes());
                        writeBuffer.put("\t".getBytes());
                        writeBuffer.put(content.getBytes());
                        writeBuffer.flip();
                        socketChannel.write(writeBuffer);
                        writeBuffer.clear();
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 10001);
        System.out.println(new NioClientTest(addr).send(Thread.currentThread().getName() + "\n 你收到了吗------------------"));
    }

}
