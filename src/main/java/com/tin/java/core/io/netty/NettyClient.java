package com.tin.java.core.io.netty;

import java.io.IOException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyClient {

	public static void send(String content) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
							pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
							pipeline.addLast(new LengthFieldPrepender(4));
							pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
							pipeline.addLast("handler", new MyClient());
						}
					});

			ChannelFuture f = bootstrap.connect("127.0.0.1", 6666).sync();
			f.channel().writeAndFlush(content);
			f.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}

	}

	public static void main(String[] args) throws IOException {
//		for (int i = 0; i < 3; i++) {
//			new Thread(new NettyClient(), ">>> this thread " + i).start();
//		}
		send(CmdUtil.execCmd());
	}
}
