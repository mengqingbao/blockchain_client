/**
 * Project Name:baomq
 * File Name:Client.java
 * Package Name:cn.bofowo.cn.test
 * Date:2017年6月3日下午12:03:02
 * Copyright (c) 2017, BOFOWO Technology Co., Ltd. All Rights Reserved.
 *
*/

package cn.bofowo.cn.test;

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
import bc.blockchain.netty.decoder.NettyDecoder;
import bc.blockchain.netty.decoder.NettyEncoder;

/**
 * ClassName:Client <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年6月3日 下午12:03:02 <br/>
 * @author   mqb
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class Client {

	public static void main(String[] args) throws InterruptedException {  
        EventLoopGroup workerGroup = new NioEventLoopGroup(); 
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(workerGroup)  
                    .channel(NioSocketChannel.class)  
                    .option(ChannelOption.SO_KEEPALIVE, true)  
                    .handler(new ChannelInitializer<SocketChannel>() {  
                        @Override  
                        protected void initChannel(SocketChannel ch) throws Exception {  
                        	  ChannelPipeline pipeline = ch.pipeline();  
                        	  pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));  
                              pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));  
                              pipeline.addLast("decoder", new NettyDecoder());  
                              pipeline.addLast("encoder", new NettyEncoder());  
                              pipeline.addLast(new ObjectClientHandler());    
                        }  
                    });  
            ChannelFuture f = b.connect("localhost", 12188).sync();  
            f.channel().write("{\"header\":{\"code\":\"REG\"}}");
            f.channel().closeFuture().sync();  
        } finally {  
            workerGroup.shutdownGracefully();  
        }  
	}
    }  