package demo.everything.netty.qprocess;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author DIAOPG
 * @date 2015年11月4日
 */
public class MessageCtxBean {
	
	private byte[] messageArray;
	private ChannelHandlerContext chhContext;
	
	public byte[] getMessageArray() {
		return messageArray;
	}
	public void setMessageArray(byte[] messageArray) {
		this.messageArray = messageArray;
	}
	public ChannelHandlerContext getChhContext() {
		return chhContext;
	}
	public void setChhContext(ChannelHandlerContext chhContext) {
		this.chhContext = chhContext;
	}
}
