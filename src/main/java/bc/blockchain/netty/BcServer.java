package bc.blockchain.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bc.blockchain.client.BlockChainContext;
import bc.blockchain.client.handler.ClientAdapter;
import bc.blockchain.netty.adapter.NettyClientAdapter;
import bc.blockchain.netty.decoder.NettyDecoder;
import bc.blockchain.netty.decoder.NettyEncoder;
import bc.blockchain.peer.Peer;

public class BcServer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private NettyClientAdapter nettyServerAdapter;
	/**
	 * 用于分配处理业务线程的线程组个数
	 */
	protected final int BIZGROUPSIZE = 100; // 默认

	/**
	 * 业务出现线程大小
	 */
	protected final int BIZTHREADSIZE = 4;

	private Integer port = 12188;
	private String ip = "localhost";

	/**
	 * NioEventLoopGroup实际上就是个线程池,
	 * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
	 * 每一个NioEventLoop负责处理m个Channel,
	 * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
	 */
	private final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private final EventLoopGroup workerGroup = new NioEventLoopGroup(
			BIZTHREADSIZE);

	public BcServer(Peer peer, BlockChainContext blockChainContext) {
		this.ip = peer.getIp();
		this.port = peer.getPort();
		nettyServerAdapter = new NettyClientAdapter(blockChainContext);
	}

	public Channel run() throws Exception {

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
                             // pipeline.addLast(new ClientAdapter(simpleCallBack,request));    
                        }  
                    });  
            ChannelFuture f = b.connect(ip, port).sync();
        return f.channel();    
        
        } finally {  
            workerGroup.shutdownGracefully();  
        }
	}

	protected void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

}
