package bc.blockchain.callback.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.json.JsonObjectDecoder;

import com.alibaba.fastjson.JSONObject;

import bc.blockchain.callback.CallBack;
import bc.blockchain.client.BlockChainContext;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.peer.LocalRemotePeer;
import bc.blockchain.peer.Peer;

public class SimpleServerCallBack implements CallBack {
	private Logger logger=LoggerFactory.getLogger(getClass());
	private BlockChainContext context;
	private Request request;
	private Peer peer;
	private String data;


	public SimpleServerCallBack(BlockChainContext context, Request request) {
		this.context = context;
		this.request = request;
	}

	@Override
	public void execute() {
		
		switch (request.getrequestType()) {
		case REG:  //设置localRemotePeer信息 获得本地监听的地址相同。
			break;
		case CHECK:

			break;

		case COMMON:

			break;
		case REFRESHCLIENT:
			logger.info("刷新服务器在线状态。"+data);
			break;
		case HEARTBEAT:
			logger.info("自检心跳返回内容。"+data);
			break;

		case SETLOCALREMOTE:
			break;

		default:
			break;
		}
	}



	public void setData(String string) {
		this.data=string;
	}

	@Override
	public void setCommand(Request command) {
		this.request=command;
		
	}

}
