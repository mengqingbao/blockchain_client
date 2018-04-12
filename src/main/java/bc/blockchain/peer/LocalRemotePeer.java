package bc.blockchain.peer;

import com.alibaba.fastjson.JSONObject;

public class LocalRemotePeer {
	
	private String ip;
	
	private Integer port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
