package bc.blockchain.netty.adapter.handler;

import io.netty.channel.Channel;
import bc.blockchain.callback.CallBack;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.response.Response;
import bc.blockchain.netty.adapter.handler.chain.HandlerChain;

public interface Handler {
	
	/**
	 * 处理逻辑
	 */
	public void process(Request request, Response response,HandlerChain chain);
	
	/**
	 * 构架handler链表
	 * @param handler
	 */
	public void setHandler(Handler handler);
	
	
	public Handler nextHandler();
	
	
	public void setChannel(Channel channel);
	
	public void setMessage(Request message);
	
	public void setCallBack(CallBack callBack);
	

}
