package bc.blockchain.netty.adapter.handler.impl;

import io.netty.channel.Channel;

import java.util.Map;

import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.common.response.Response;
import bc.blockchain.netty.adapter.handler.AbstractHandler;

public class ServerDecideRateBeatHandler extends AbstractHandler {

	@Override
	public void doProcess(Request request, Response response) {
		if(request.getrequestType()!=RequestType.COMMON){
			return;
		}
	}

}
