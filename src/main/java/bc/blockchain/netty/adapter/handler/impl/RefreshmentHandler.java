package bc.blockchain.netty.adapter.handler.impl;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import bc.blockchain.client.BlockChainContext;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.common.response.Response;
import bc.blockchain.netty.adapter.handler.AbstractHandler;
import bc.blockchain.peer.Peer;

public class RefreshmentHandler extends AbstractHandler {

	private Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public void doProcess(Request request, Response response) {
		if(request.getrequestType()!=RequestType.REFRESHCLIENT){
			return;
		}
		List<String> peers=new ArrayList();
		String srt=request.getContent();
		if(StringUtil.isNullOrEmpty(srt)){
			return;
		}
		List<String> ids=JSONArray.parseArray(srt, String.class);
		for(String id:ids){
			System.out.println(id);
			peers.add(id);
		}
		callBack.setRequest(request);
		callBack.setPeers(peers);
		response = callBack.execute();
		logger.info("收到心跳");
	}

}
