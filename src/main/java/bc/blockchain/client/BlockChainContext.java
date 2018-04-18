package bc.blockchain.client;

import io.netty.channel.Channel;

import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bc.blockchain.callback.client.impl.ClientCallBack;
import bc.blockchain.client.handler.Client;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.constant.BcConstant;
import bc.blockchain.netty.BcServer;
import bc.blockchain.peer.LocalRemotePeer;
import bc.blockchain.peer.Peer;
import bc.blockchain.util.PropertiesUtil;

public class BlockChainContext {

	private Logger logger=LoggerFactory.getLogger(getClass());
	private final static Hashtable<String, Peer> peerTable = new Hashtable<String, Peer>();
	private ScheduledExecutorService scheduledThreadPool = Executors
			.newScheduledThreadPool(1);
	private BcServer server;
	private Client client;
	private Peer remotePeer;
	private Peer localRemotePeer=null;
	public void initServer(Peer peer){
		if(server==null){
			server=new BcServer(peer);
			
		}
	}
	public void start(Peer peerA,Peer peerB) {
		logger.info("peerA:"+peerA.toString());
		logger.info("peerB:"+peerB.toString());
		startScheduleCheckLivePeer();
		connA2B(peerB);
		if(server==null){
			initServer(peerA);
		}
		
		try {
			server.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connB2A(peerA);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

			@Override
			public void run() {
				shutdown();
				logger.info("shutdownhook");
			}
			
			
		}));
		
		logger.info("启动完成");
	}

	private void connB2A(Peer peerA) {
		Request request=new Request(RequestType.HI);
		client=new Client(new ClientCallBack(this,request),peerA);
		try {
			client.execute(request);
		} catch (InterruptedException e) {
			logger.error("B------------>A-----erro");
			logger.error(e.getMessage());
		}
	}
	private void connA2B(Peer peerB) {
		Request request=new Request(RequestType.HI);
		client=new Client(new ClientCallBack(this,request),peerB);
		try {
			client.execute(request);
		} catch (InterruptedException e) {
			logger.error("A------------>B-----erro 异常正常");
			logger.error(e.getMessage());
		}
	}
	//启动定时器监测客户端是否存货。通知客户端是否在线。
	private void startScheduleCheckLivePeer() {
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				//进行自检确认是否在线
				logger.debug("p2p客户端在线自检");
				freshClient();
				heartBeat();
			}
		}, 3, 15, TimeUnit.SECONDS);
		
	}

	//关闭服务端
	public void shutdown(){
		scheduledThreadPool.shutdown();
	}
	
	//设置环境变量
	protected void setSysEvn(){
		PropertiesUtil prop=new PropertiesUtil(BcConstant.SERVER_CONFIG);
		String ip=prop.getProperty(BcConstant.SERVER_IP);
		Integer port=Integer.valueOf(prop.getProperty(BcConstant.SERVER_PORT));
		remotePeer=new Peer(ip,port,null);
	}
	
	public void regClient(Peer peer){
		peerTable.put(peer.genId(), peer);
	}
	
	public void freshClient(){ //向服务器刷新本地状态
		Request request=new Request(RequestType.REFRESHCLIENT);
		request.setContent(localRemotePeer.toString());
		client=new Client(new ClientCallBack(this,request),remotePeer);
		try {
			client.execute(request);
		} catch (InterruptedException e) {
			logger.info(e.getMessage());
		}
		
	}
	public void setLocalRemotePeer(Peer localRemotePeer) {
		this.localRemotePeer = localRemotePeer;
	}
	public Peer regPeerAInfo() throws InterruptedException{
		Request request=new Request(RequestType.REG);
		client=new Client(new ClientCallBack(this,request),remotePeer);
		return client.getInternetPeer(request);
	}
	public Peer regPeerBInfo() throws InterruptedException{
		Request request=new Request(RequestType.REG);
		client=new Client(new ClientCallBack(this,request),remotePeer);
		return client.getInternetPeer(request);
	}
	
	public boolean heartBeat(){
		Request request=new Request(RequestType.HEARTBEAT);
		client=new Client(new ClientCallBack(this,request),localRemotePeer);
		try {
			client.execute(request);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}
	
}
