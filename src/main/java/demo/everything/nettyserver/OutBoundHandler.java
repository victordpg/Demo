package demo.everything.nettyserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import org.apache.log4j.Logger;

import com.neusoft.aplus.common.util.SpringUtil;
import com.neusoft.aplus.databus.util.BaseDao;

/**
 * @author DIAOPG
 * @date 2015年10月12日
 */
public class OutBoundHandler extends ChannelOutboundHandlerAdapter {
	private static Logger logger = Logger.getLogger(OutBoundHandler.class);
	
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		if (msg instanceof byte[]) {
			byte[] bytesWrite = (byte[])msg;
			ByteBuf buf = ctx.alloc().buffer(bytesWrite.length); 
			logger.info("向设备 "+TCPServerUtil.getIPString(ctx)+" 下发的报文为：【"+TCPServerUtil.bytesToHexString(bytesWrite)+"】");
			//往设备下发的数据应该是16进制
			//byte[] bytes = {0x68,0x32,0x00,0x32,0x00,0x68,0x4B,0x15,0x12,0x02,0x00,0x01,0x0C,0x61,0x00,0x00,0x02,0x00,(byte) 0xE4,0x16};
			buf.writeBytes(bytesWrite); 
			ctx.writeAndFlush(buf).addListener(new ChannelFutureListener(){  
                @Override  
                public void operationComplete(ChannelFuture future)  
                        throws Exception {  
                	if (future.isSuccess()) {
                		logger.debug("下发成功！");
                		//更新设备连接状态表
                		SpringUtil.getBean(BaseDao.class).update(TCPServerUtil.getIPString(future.channel()), 1);
					}else {
						logger.warn("下发失败！");
                		//更新设备连接状态表
                		SpringUtil.getBean(BaseDao.class).update(TCPServerUtil.getIPString(future.channel()), 0);
					}
                }  
                
            });
		}
	}
}
