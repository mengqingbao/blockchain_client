package bc.blockchain.netty.adapter.handler.chain;

import java.util.LinkedList;

import bc.blockchain.common.request.Request;
import bc.blockchain.common.response.Response;
import bc.blockchain.netty.adapter.handler.Handler;

public class HandlerChain {
	private LinkedList<Handler> link=new LinkedList();
	private Integer point=0;
	public void doHandle(Request request, Response response,HandlerChain chain) {
		if(point<chain.size()){
			Handler handler = link.get(point);
			point++;
			handler.process(request,response,chain);
		}
		
	}
	public void addHandler(Handler handler){
		link.add(handler);
	}
	
	public Integer size(){
		return link.size();
	}
}
