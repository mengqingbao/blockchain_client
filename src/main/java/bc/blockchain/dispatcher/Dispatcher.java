package bc.blockchain.dispatcher;

import bc.blockchain.callback.CallBack;
import bc.blockchain.client.BlockChainContext;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.response.Response;
import bc.blockchain.netty.adapter.handler.chain.HandlerChain;
import bc.blockchain.netty.adapter.handler.factory.HandlerFactory;

public class Dispatcher {
	
	private BlockChainContext blockChainContext;
	private CallBack callBack;
	public Dispatcher(CallBack callBack) {
		this.callBack=callBack;
	}

	
	public void doService(Request request,Response response) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		HandlerChain chain=HandlerFactory.getInstance(callBack).createChain();
		chain.doHandle(request, response,chain);
	}
	
	public void destory(){
		
	}
}
