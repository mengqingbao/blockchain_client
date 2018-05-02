package bc.blockchain.client.handler;

import java.net.InetSocketAddress;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import bc.blockchain.callback.client.impl.ClientCallBack;
import bc.blockchain.callback.server.impl.SimpleServerCallBack;
import bc.blockchain.client.BlockChainContext;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.common.response.Response;
import bc.blockchain.peer.Peer;
import bc.blockchain.util.DispatherUtil;
import bc.blockchain.util.RequestUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

public class ClientAdapter extends io.netty.channel.ChannelInboundHandlerAdapter{
	private Logger logger=LoggerFactory.getLogger(getClass());
	private ClientCallBack simpleCallBack;
	private Request request;
	public ClientAdapter(ClientCallBack simpleCallBack) {
		this.simpleCallBack=simpleCallBack;
		if(request!=null){
			this.request=request;
		}else{
			request=new Request();
			request.putHeader(RequestType.HI);
		}
	}
	@Override  
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
	        //super.channelActive(ctx);
	    }  
	  
	    @Override  
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
	    	Request req = RequestUtil.create(msg.toString());
			SocketChannel clientChannel = (SocketChannel) ctx.channel();
			InetSocketAddress isd = clientChannel.remoteAddress();
			Response response= DispatherUtil.getInstance(simpleCallBack).doService(req);
	    }  
	  
	    @Override  
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
	        cause.printStackTrace();  
	        ctx.close();  
	    }  
	    
}
