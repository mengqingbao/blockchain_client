package bc.blockchain.client.handler;

import java.net.InetSocketAddress;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import bc.blockchain.callback.client.impl.ClientCallBack;
import bc.blockchain.common.request.Request;
import bc.blockchain.peer.Peer;
import bc.blockchain.util.RequestUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

public class ClientAdapter extends io.netty.channel.ChannelInboundHandlerAdapter{
	private Logger logger=LoggerFactory.getLogger(getClass());
	private ClientCallBack simpleCallBack;
	private Request request;
	public ClientAdapter(ClientCallBack simpleCallBack, Request request) {
		this.simpleCallBack=simpleCallBack;
		this.request=request;
	}
	@Override  
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
	        ctx.write(request.toString());  
	        ctx.flush();  
	    }  
	  
	    @Override  
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
	    	logger.info("获得返回值："+msg);
	    	Request req = RequestUtil.create(msg.toString());
	    	simpleCallBack.setRequest(req);
	    	simpleCallBack.execute();
	        ctx.close();  
	    }  
	  
	    @Override  
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
	        cause.printStackTrace();  
	        ctx.close();  
	    }  
	    
}
