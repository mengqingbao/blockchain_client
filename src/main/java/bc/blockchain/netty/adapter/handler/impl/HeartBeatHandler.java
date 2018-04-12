package bc.blockchain.netty.adapter.handler.impl;

import io.netty.channel.Channel;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bc.blockchain.client.BlockChainContext;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.common.response.Response;
import bc.blockchain.netty.adapter.handler.AbstractHandler;

public class HeartBeatHandler extends AbstractHandler {

	private Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public void doProcess(Request request, Response response) {
		if(request.getrequestType()!=RequestType.HEARTBEAT){
			return;
		}
		response.setCode("200");
		logger.info("收到心跳");
	}

}
