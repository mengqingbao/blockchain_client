package bc.blockchain.netty.adapter.handler.impl;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

import bc.blockchain.client.BlockChainContext;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.common.response.Response;
import bc.blockchain.netty.adapter.handler.AbstractHandler;

public class RegistHandler extends AbstractHandler {
	

	@Override
	public void doProcess(Request request, Response response) {
		if(request.getrequestType()!=RequestType.REG){
			return;
		}
		System.out.print("注册客户端");
		
		response.setCode("200");
	}

}
