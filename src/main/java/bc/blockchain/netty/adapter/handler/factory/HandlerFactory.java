package bc.blockchain.netty.adapter.handler.factory;

import bc.blockchain.callback.CallBack;
import bc.blockchain.client.BlockChainContext;
import bc.blockchain.constant.BcConstant;
import bc.blockchain.netty.adapter.handler.Handler;
import bc.blockchain.netty.adapter.handler.chain.HandlerChain;
import bc.blockchain.netty.adapter.handler.proxy.HandlerProxy;
import bc.blockchain.util.PropertiesUtil;

public class HandlerFactory {
	
	private static HandlerFactory factory;
	private CallBack callBack;
	
	
	public HandlerFactory(CallBack callBack) {
		this.callBack=callBack;
	}


	public static HandlerFactory getInstance(CallBack callBack){
		if(factory==null){
			factory=new HandlerFactory(callBack);
		}
		return factory;
	}
	public HandlerProxy createDefaultProxy() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		PropertiesUtil prop=new PropertiesUtil(BcConstant.SERVER_CONFIG);
		String handlerStr=prop.getProperty(BcConstant.SERVER_HANDLER);
		String[] handlerStrs=handlerStr.split(";");
		HandlerChain handlerChain = new HandlerChain();
		for(String hder:handlerStrs){
			Class clzz=Class.forName(hder);
			Handler handler=(Handler) clzz.newInstance();
			handler.setCallBack(callBack);
			handlerChain.addHandler(handler);
		}
		HandlerProxy proxy = new HandlerProxy(handlerChain);
		return proxy;
	}


	public HandlerChain createChain() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		PropertiesUtil prop=new PropertiesUtil(BcConstant.SERVER_CONFIG);
		String handlerStr=prop.getProperty(BcConstant.SERVER_HANDLER);
		String[] handlerStrs=handlerStr.split(";");
		HandlerChain handlerChain = new HandlerChain();
		for(String hder:handlerStrs){
			Class clzz=this.getClass().getClassLoader().loadClass(hder);
			Handler handler=(Handler) clzz.newInstance();
			handler.setCallBack(callBack);
			handlerChain.addHandler(handler);
		}
		return handlerChain;
	}

}
