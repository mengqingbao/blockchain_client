package cn.bofowo.cn.test;

import io.netty.channel.ChannelHandlerContext;

public class ObjectClientHandler extends io.netty.channel.ChannelInboundHandlerAdapter {
	  
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
      
        ctx.write("{\"header\":{\"code\":\"REG\"}}");  
        ctx.flush();  
        System.out.println("信息已发送");  
    }  
  
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
    	System.out.println(msg);
        System.out.println("读取返回值");  
        ctx.close();  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        cause.printStackTrace();  
        ctx.close();  
    }  
}
