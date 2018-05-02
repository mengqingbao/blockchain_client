/**

 * Project Name:baomq
 * File Name:Client.java
 * Package Name:cn.bofowo.cn.test
 * Date:2017年6月3日下午12:03:02
 * Copyright (c) 2017, BOFOWO Technology Co., Ltd. All Rights Reserved.
 *
*/

package bc.blockchain.client.handler;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import bc.blockchain.callback.client.impl.ClientCallBack;
import bc.blockchain.common.request.Request;
import bc.blockchain.netty.decoder.NettyDecoder;
import bc.blockchain.netty.decoder.NettyEncoder;
import bc.blockchain.peer.Peer;
import bc.blockchain.util.RequestUtil;

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
public class BcClient {
	private Logger logger=LoggerFactory.getLogger(getClass());
	private ClientCallBack simpleCallBack;
	private Peer peer;
	public Peer localNetPeer=null;
	public BcClient(ClientCallBack simpleCallBack,Peer peer) {
		this.simpleCallBack=simpleCallBack;
		this.peer=peer;
	}


	public Channel comet() throws InterruptedException {  
		Peer localNetPeer=null;
		
        EventLoopGroup workerGroup = new NioEventLoopGroup(); 
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(workerGroup)  
                    .channel(NioSocketChannel.class)  
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,2000)  
                    .handler(new ChannelInitializer<SocketChannel>() {  
                        @Override  
                        protected void initChannel(SocketChannel ch) throws Exception {  
                        	  ChannelPipeline pipeline = ch.pipeline();  
                        	  pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));  
                              pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));  
                              pipeline.addLast("decoder", new NettyDecoder());  
                              pipeline.addLast("encoder", new NettyEncoder());  
                              pipeline.addLast(new ClientAdapter(simpleCallBack));    
                        }  
                    });  
            ChannelFuture f = b.connect(peer.getIp(), peer.getPort()).sync();
             
           return f.channel() ;
        } finally {  
            
        }
	}
	
	public Peer getInternetPeer(Request request) throws InterruptedException {  
		
		
        EventLoopGroup workerGroup = new NioEventLoopGroup(); 
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(workerGroup)  
                    .channel(NioSocketChannel.class)  
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,2000)  
                    .handler(new ChannelInitializer<SocketChannel>() {  
                        @Override  
                        protected void initChannel(SocketChannel ch) throws Exception {  
                        	  ChannelPipeline pipeline = ch.pipeline();  
                        	  pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));  
                              pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));  
                              pipeline.addLast("decoder", new NettyDecoder());  
                              pipeline.addLast("encoder", new NettyEncoder());  
                              pipeline.addLast(new io.netty.channel.ChannelInboundHandlerAdapter(){

								@Override
								public void channelRead(
										ChannelHandlerContext ctx, Object msg)
										throws Exception {
									Request req = RequestUtil.create(msg.toString());
									String peerStr=req.getContent();
							    	JSONObject peerObj=JSONObject.parseObject(peerStr);
							    	localNetPeer=new Peer(peerObj.getString("ip"),peerObj.getInteger("port"),null);
									super.channelRead(ctx, msg);
								}
                            	  
                              });    
                        }  
                    });  
            ChannelFuture f = b.connect(peer.getIp(), peer.getPort()).sync();
            f.channel().write(request.toString());
            f.channel().closeFuture().sync(); 
        } finally {  
            workerGroup.shutdownGracefully();  
        }
        logger.info(peer.toString());
		return localNetPeer;  
	}
	
    }  