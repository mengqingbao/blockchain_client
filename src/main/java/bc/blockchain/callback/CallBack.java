package bc.blockchain.callback;

import java.util.List;

import bc.blockchain.common.request.Request;
import bc.blockchain.common.response.Response;
import bc.blockchain.peer.Peer;

public interface CallBack {
	public Response execute();
	public void setRequest(Request command);
	public void setPeers(List<String> peers);
	
}
