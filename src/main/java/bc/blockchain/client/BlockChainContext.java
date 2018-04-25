package bc.blockchain.client;

import io.netty.channel.Channel;

import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bc.blockchain.callback.client.impl.ClientCallBack;
import bc.blockchain.client.handler.BcClient;
import bc.blockchain.common.request.Request;
import bc.blockchain.common.request.RequestType;
import bc.blockchain.constant.BcConstant;
import bc.blockchain.peer.Peer;
import bc.blockchain.util.PropertiesUtil;

public class BlockChainContext {

	private Logger logger=LoggerFactory.getLogger(getClass());
	private final static Hashtable<String, Peer> peerTable = new Hashtable<String, Peer>();
	private final static Hashtable<String, Peer> dnsTable = new Hashtable<String, Peer>();
	private ScheduledExecutorService scheduledPeerDiscoveryPool = Executors
			.newScheduledThreadPool(1);
	private ScheduledExecutorService scheduledDnsDiscoveryPool = Executors
			.newScheduledThreadPool(1);
	private BcClient client;
	private Peer remotePeer;
	private Peer localRemotePeer=null;
	public void initServer(){
		
		choosePeer();
	}
	private void choosePeer() {
		// TODO Auto-generated method stub
		
	}
	public Channel start() {
		Channel channel=comet(remotePeer);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

			@Override
			public void run() {
				shutdown();
				logger.info("shutdownhook");
			}
			
			
		}));
		
		logger.info("启动完成");
		return channel;
	}

	private Channel comet(Peer peer){
		BcClient client=new BcClient(new ClientCallBack(this, null),remotePeer);
		Channel channel=null;
		try {
			channel=client.comet();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		startScheduleCheckLivePeer(channel);
		return channel;
	}

	//启动定时器监测客户端是否存活。通知客户端是否在线。
	private void startScheduleCheckLivePeer(Channel channel) {
		scheduledDnsDiscoveryPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				//进行自检确认是否在线
				logger.debug("p2p客户端在线自检");
				freshClient(channel);
			}
		}, 3, 15, TimeUnit.SECONDS);
		
	}

	//关闭服务端
	public void shutdown(){
		scheduledDnsDiscoveryPool.shutdown();
	}
	
	//设置环境变量
	protected void setSysEvn(){
		PropertiesUtil prop=new PropertiesUtil(BcConstant.SERVER_CONFIG);
		String ip=prop.getProperty(BcConstant.SERVER_IP);
		Integer port=Integer.valueOf(prop.getProperty(BcConstant.SERVER_PORT));
		remotePeer=new Peer(ip,port,null);
	}
	
	public void regClient(Channel channel){
		Request request = new Request();
		request.putHeader(RequestType.REG);
		channel.write(request.toString());
		channel.flush();
	}
	
	public void freshClient(Channel channel){ //向服务器刷新本地状态
		Request request = new Request();
		request.putHeader(RequestType.REFRESHCLIENT);
		channel.write(request.toString());
		channel.flush();
		
	}
	public void setLocalRemotePeer(Peer localRemotePeer) {
		this.localRemotePeer = localRemotePeer;
	}
	
}
