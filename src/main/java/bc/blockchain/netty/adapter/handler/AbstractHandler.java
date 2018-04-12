package bc.blockchain.netty.adapter.handler;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

import bc.blockchain.callback.CallBack;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.response.Response;
import bc.blockchain.netty.adapter.handler.chain.HandlerChain;

public abstract class AbstractHandler implements Handler {

	private Handler handler;
	private Map<String, Object> dataMap = new HashMap<String, Object>();

	protected CallBack callBack;
	protected Channel channel;
	protected Request message;

	@Override
	public void process(Request request, Response response,HandlerChain chain){
		doProcess(request,response);
		chain.doHandle(request, response, chain);
	};

	public abstract void doProcess(Request request, Response response);
	@Override
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	public Handler nextHandler() {
		return handler;
	}

	public Object getObject(String key) {
		if (dataMap != null) {
			return dataMap.get(key);
		}
		return null;
	}

	public void addObject(String key, Object obj) {
		dataMap.put(key, obj);
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}


	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setMessage(Request message) {
		this.message = message;
	}
	
	@Override
	public void setCallBack(CallBack callBack) {
		this.callBack=callBack;
	}


}
