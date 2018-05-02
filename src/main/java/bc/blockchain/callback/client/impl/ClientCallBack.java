package bc.blockchain.callback.client.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bc.blockchain.callback.CallBack;
import bc.blockchain.client.BlockChainContext;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.common.response.Response;
import bc.blockchain.peer.Peer;
import bc.blockchain.util.BeanJsonUtil;

public class ClientCallBack implements CallBack {
	private Logger logger=LoggerFactory.getLogger(getClass());
	private BlockChainContext context;
	private Request request;
	private Response response;
	private Peer peer;
	private String data;
	private List<String> peers;


	public ClientCallBack(BlockChainContext context, Request request) {
		this.context = context;
		this.request = request;
		if(request==null){
			request=new Request();
			request.setrequestType(RequestType.HI);
		}
	}

	@Override
	public Response execute() {
		
		switch (request.getrequestType()) {
		case REG:  //设置localRemotePeer信息 获得本地监听的地址相同。
			
			
			System.out.println(request.toString());
	    	//context.setLocalRemotePeer(lrp);
			break;
		case HI:

			break;

		case COMMON:

			break;
		case REFRESHCLIENT:
			
			//context.refreshPeerTable(peers);
			logger.info("获取在线客户端"+peers);
			break;
		case HEARTBEAT:
			logger.info("自检心跳返回内容。"+data);
			break;

		case SETLOCALREMOTE:
			break;

		default:
			break;
		}
		
		return response;
	}



	public void setData(String string) {
		this.data=string;
	}

	@Override
	public void setRequest(Request command) {
		this.request=command;
		
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public void setPeers(List<String> peers) {
		this.peers = peers;
	}

}
