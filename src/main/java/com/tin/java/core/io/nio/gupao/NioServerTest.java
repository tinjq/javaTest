package com.tin.java.core.io.nio.gupao;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Tin on 2018/11/24.
 */
public class NioServerTest {

    private InetSocketAddress local;

    public NioServerTest(int port) {
        this.local = new InetSocketAddress(port);
    }

    public void start() {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(local, 5);

            Selector selector = Selector.open();

            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("server started with address:" + local);

            while (selector.select() > 0) {
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keySet.iterator();

                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();


                    // 若发现异常，说明客户端连接出现问题,但服务器要保持正常
                    try {
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverChannel.accept();
                            socketChannel.configureBlocking(false);

                            socketChannel.register(selector, SelectionKey.OP_READ, new Buffers(1024, 1024));

                            System.out.println("accept from " + socketChannel.getRemoteAddress());
                        } else if (key.isReadable()) {
                            Buffers buffers = (Buffers) key.attachment();
                            ByteBuffer readBuffer = buffers.getReadBuffer();
                            ByteBuffer writeBuffer = buffers.getWriteBuffer();

                            SocketChannel socketChannel = (SocketChannel) key.channel();

                            // TODO
                            int len = socketChannel.read(readBuffer);
                            readBuffer.flip();
                            System.out.println("[server] - " + new String(readBuffer.array(), 0, len));

                            readBuffer.rewind();
                            writeBuffer.put("Hello ".getBytes());
                            writeBuffer.put(readBuffer);
                            readBuffer.clear();

                            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                        } else if (key.isWritable()) {
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            Buffers buffers = (Buffers) key.attachment();
                            ByteBuffer writeBuffer = buffers.getWriteBuffer();

                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            writeBuffer.flip();
                            socketChannel.write(writeBuffer);
                            writeBuffer.clear();

                            key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
                        }
                    } catch (IOException e) {
                        System.out.println("service encounter client error");
                        key.cancel();
                        key.channel().close();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioServerTest(10001).start();
    }

}
