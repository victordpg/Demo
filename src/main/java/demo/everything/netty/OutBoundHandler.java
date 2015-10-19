package demo.everything.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class OutBoundHandler extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		
		if (msg instanceof String) {
			ByteBuf b = ctx.alloc().buffer(); 
			System.out.println("in write and MSG is : "+msg);
			//super.write(ctx, msg, promise);
			 
	        b.writeBytes("message from OutBoundHandler".getBytes()); 
			ctx.writeAndFlush(b).addListener(new ChannelFutureListener(){  
	            @Override  
	            public void operationComplete(ChannelFuture future)  
	                    throws Exception {  
	                // TODO Auto-generated method stub  
	            	System.out.println("enter addListener");
	                future.channel().read();
	            }  
	        });
		}
		
		if (msg instanceof byte[]) {
			byte[] bytesWrite = (byte[])msg;
			ByteBuf buf = ctx.alloc().buffer(bytesWrite.length); 
			System.out.println("要写入的信息为："+HexConversion.str2HexStr(new String(bytesWrite)));
			buf.writeBytes(bytesWrite); 
			ctx.writeAndFlush(buf);
		}
	}
	
}
