package com.tin.java.core.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyServer {
	
	private static final int port = 6666;
	private static final int BIZGROUPSIZE =  Runtime.getRuntime().availableProcessors() * 2;
	private static final int BIZTHREADSIZE = 5; // TODO
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workGroup = new NioEventLoopGroup(BIZTHREADSIZE);
	
	public static void main(String[] args) throws InterruptedException {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast(new LengthFieldPrepender(4));
                pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new TcpServerHandler());
            }
        });
		ChannelFuture channelFuture = bootstrap.bind(port).sync();
		System.out.println("server start");

        channelFuture.channel().closeFuture().sync();
    }

    protected static void shutdown(){
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

}
