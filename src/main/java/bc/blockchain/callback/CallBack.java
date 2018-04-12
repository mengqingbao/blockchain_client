package bc.blockchain.callback;

import bc.blockchain.common.request.Request;
import bc.blockchain.peer.Peer;

public interface CallBack {
	public void execute();
	public void setCommand(Request command);
	
}
