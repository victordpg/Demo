package demo.everything.netty.qprocess;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import demo.everything.netty.NettyTCPServer;
import demo.everything.netty.TCPServerUtil;

/**
 * 
 * @author DIAOPG
 * @date 2015年11月4日
 */
public class MessageHandler2 implements Runnable {
	private static final Logger logger = Logger.getLogger(MessageHandler2.class);
	private BlockingQueue<MessageCtxBean> blockingQueue;

	public MessageHandler2() {}

	public MessageHandler2(BlockingQueue<MessageCtxBean> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		MessageCtxBean msgBean = null;	
		byte[] byteArray = null;
		ChannelHandlerContext ctx = null;
		
		while(true) {
			try {
				msgBean = blockingQueue.take();
				long startTime = System.currentTimeMillis();
				byteArray = msgBean.getMessageArray();
				ctx = msgBean.getChhContext();

				//count++;
				
				logger.info("从设备 "+TCPServerUtil.getIPString(ctx)+" 获取的报文为：【"+TCPServerUtil.bytesToHexString(byteArray)+"】");
				byte[] addressDomain = new byte[5]; 
				System.arraycopy(byteArray, 7, addressDomain, 0, 5);
	    		byte[] pnBytes = new byte[2]; 
	    		System.arraycopy(byteArray, 14, pnBytes, 0, 2);
				String messageKey = TCPServerUtil.getKeyFromArray(addressDomain,pnBytes); //生成key
				logger.info("上行报文中的Key为："+messageKey);
				
				NettyTCPServer.getMessageMap().put(messageKey, byteArray);
				long stage1 = System.currentTimeMillis();
				logger.debug("本次耗时："+(stage1-startTime));	
				
		        //logger.info("MessageHandler2 处理采集的个数："+count);		        
			} catch (Exception e) {
				logger.error("解析报文时出现异常：", e);
			}
		}
	}
}
