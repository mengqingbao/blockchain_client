package bc.blockchain.peer;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class Peer {
	
	private String address;
	private String ip;
	private Integer port;
	private Date liveTime;
	public Peer(String ip2, Integer port2, Date time) {
		this.ip=ip2;
		this.port=port2;
		this.liveTime=time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
	public Date getLiveTime() {
		return liveTime;
	}
	public void setLiveTime(Date liveTime) {
		this.liveTime = liveTime;
	}
	
	public String genId(){
		return ip+port;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	

}
