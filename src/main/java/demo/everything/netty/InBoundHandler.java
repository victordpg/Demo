package demo.everything.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import demo.everything.netty.qprocess.MessageCtxBean;
import demo.everything.netty.qprocess.MessageManager;

/**
 * 
 * @author DIAOPG
 * @date 2015年11月9日
 */
public class InBoundHandler extends SimpleChannelInboundHandler<byte[]> {
	private static Logger logger = Logger.getLogger(InBoundHandler.class);
	private static Map<String, Object> map2QProcess = new ConcurrentHashMap<String, Object>();
	

	public static Map<String, Object> getMap2QProcess() {
		return map2QProcess;
	}

	public static void setMap2QProcess(Map<String, Object> map2qProcess) {
		map2QProcess = map2qProcess;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.info("CLIENT "+TCPServerUtil.getRemoteAddress(ctx)+" 接入连接");
		//往channel map中添加channel信息
		NettyTCPServer.getMap().put(TCPServerUtil.getIPString(ctx), ctx.channel());	
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//删除Channel Map中的失效Client
		logger.info("CLIENT "+TCPServerUtil.getRemoteAddress(ctx)+" 释放连接");
		NettyTCPServer.getMessageMap().remove(TCPServerUtil.getIPString(ctx));	
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
		long startTime = System.currentTimeMillis();
		byte byteA3 = msg[11]; //主站地址：0表示主动上传的数据，!=0表示手动采集上来的数据。

		MessageCtxBean msgCtxBean = new MessageCtxBean();
		msgCtxBean.setMessageArray(msg);
		msgCtxBean.setChhContext(ctx);
		
		if (byteA3==0) { 
			//主动上传的交给messageQueue1处理
			MessageManager.getMessageQueue1().put(msgCtxBean);
		}else {
			//手动采集的交给messageQueue2处理
			MessageManager.getMessageQueue2().put(msgCtxBean);
		}
		
		long endTime = System.currentTimeMillis();
		logger.debug("线程："+Thread.currentThread().getName()+ ", 耗时："+(endTime-startTime)+ "【" + Arrays.toString(msg)+"】");
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		String socketString = ctx.channel().remoteAddress().toString();
		
		if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
            	logger.info("Client: "+socketString+" READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
            	logger.info("Client: "+socketString+" WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
            	logger.info("Client: "+socketString+" ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	/**
	 * 根据帧报文获得相应的确认报文，确认报文功能码00H，数据单元分为F1,F9,F10,F11
	 * @param received
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月14日
	 */
	protected byte[] getConfirmMessage(byte[] received) {
		byte[] result = null;
		byte afn = received[12]; //获取功能码
		byte[] tmp = new byte[2];
		
		byte[] fnBytes = new byte[2];
		System.arraycopy(received, 16, fnBytes, 0, 2); 
		int fn = TCPServerUtil.dByteToInt(fnBytes, false);
		logger.debug("上传报文的功能码为："+afn+"，FN为："+fn);
		
		if (afn==0x0e) {
			byte ercn = received[22];
			byte roudNoRoudNo = received[32];
			byte eventNo = received[33];
			if (ercn==0x2e||ercn==0x2f) { //ERC46 || ERC47
				byte[] confirmBytesF9 = new byte[24];
				
				//截获地址域+功能码作为key+Pn
				System.arraycopy(received, 0, confirmBytesF9, 0, 16); 
				
				//设置确认报文长度（地位在前，高位在后，长度为整个报文长度-8）
				tmp = getMessageLength(22-8);
				confirmBytesF9[1] = tmp[1]; 
				confirmBytesF9[2] = tmp[0]; 
				confirmBytesF9[3] = tmp[1]; 
				confirmBytesF9[4] = tmp[0]; 				
				
				//设置确认报文的功能码
				confirmBytesF9[12] = 0x00; 
				
				//确认报文F9
				confirmBytesF9[15] = 0x01;
				confirmBytesF9[16] = 0x01;
				
				//数据单元
				confirmBytesF9[17] = Byte.parseByte(new Byte(roudNoRoudNo).toString(), 16); //需要获得16进制数据
				confirmBytesF9[18] = Byte.parseByte(new Byte(eventNo).toString(), 16); //需要获得16进制数据
				confirmBytesF9[19] = 0x01; //确认
				
				//附加信息AUX
				confirmBytesF9[20] = 0x01;
				confirmBytesF9[21] = 0x01;
				
				//帧校验和
				byte[] temp = new byte[confirmBytesF9.length-8]; 
				System.arraycopy(confirmBytesF9, 6, temp, 0, confirmBytesF9.length-8);
				confirmBytesF9[confirmBytesF9.length-2] = (byte) TCPServerUtil.getCheckSum(temp);
				
				confirmBytesF9[23] = 0x16;
				result = confirmBytesF9;
			} else{ 
				byte[] confirmBytesERC = new byte[21];
				System.arraycopy(received, 0, confirmBytesERC, 0, 16); //截获地址域+功能码作为key+Pn
				
				//设置确认报文长度（地位在前，高位在后）
				tmp = getMessageLength(22-8);
				confirmBytesERC[1] = tmp[1]; 
				confirmBytesERC[2] = tmp[0]; 
				confirmBytesERC[3] = tmp[1]; 
				confirmBytesERC[4] = tmp[0]; 		
				
				//设置确认报文的功能码
				confirmBytesERC[12] = 0x00;
				
				//确认报文F1
				confirmBytesERC[15] = 0x00;
				confirmBytesERC[16] = 0x00;
				
				//附加信息AUX
				confirmBytesERC[17] = 0x00;
				confirmBytesERC[18] = 0x01;
				
				//帧校验和
				byte[] temp = new byte[confirmBytesERC.length-8]; 
				System.arraycopy(confirmBytesERC, 6, temp, 0, confirmBytesERC.length-8);
				confirmBytesERC[confirmBytesERC.length-2] = (byte) TCPServerUtil.getCheckSum(temp);
				
				confirmBytesERC[20] = 0x16;
				result = confirmBytesERC;
			}
		}else if (afn==0x0c && fn==186) {
			byte roudNo = received[18];
			byte waveNo = received[19];
			
			byte[] confirmBytesF10 = new byte[24];
			
			//截获地址域+功能码作为key+Pn
			System.arraycopy(received, 0, confirmBytesF10, 0, 16); 
			
			//设置确认报文长度（地位在前，高位在后）
			tmp = getMessageLength(24-8);
			confirmBytesF10[1] = tmp[1]; 
			confirmBytesF10[2] = tmp[0]; 
			confirmBytesF10[3] = tmp[1]; 
			confirmBytesF10[4] = tmp[0]; 		
			
			//设置确认报文的功能码
			confirmBytesF10[12] = 0x00; 
			
			//确认报文F10
			confirmBytesF10[15] = 0x02;
			confirmBytesF10[16] = 0x01;
			
			//数据单元
			confirmBytesF10[17] = Byte.parseByte(new Byte(roudNo).toString(), 16); //需要获得16进制数据
			confirmBytesF10[18] = Byte.parseByte(new Byte(waveNo).toString(), 16); //需要获得16进制数据
			confirmBytesF10[19] = 0x01; //确认
			
			//附加信息AUX
			confirmBytesF10[20] = 0x01;
			confirmBytesF10[21] = 0x01;
			
			//帧校验和
			byte[] temp = new byte[confirmBytesF10.length-8]; 
			System.arraycopy(confirmBytesF10, 6, temp, 0, confirmBytesF10.length-8);
			confirmBytesF10[confirmBytesF10.length-2] = (byte) TCPServerUtil.getCheckSum(temp);
			
			confirmBytesF10[23] = 0x16;
			result = confirmBytesF10;
		}else if (afn==0x0c && fn==189) {
			byte preM = received[18];
			byte[] confirmBytesF11 = new byte[23];
			
			//截获地址域+功能码作为key+Pn
			System.arraycopy(received, 0, confirmBytesF11, 0, 16); 
			
			//设置确认报文长度（地位在前，高位在后）
			tmp = getMessageLength(23-8);
			confirmBytesF11[1] = tmp[1]; 
			confirmBytesF11[2] = tmp[0]; 
			confirmBytesF11[3] = tmp[1]; 
			confirmBytesF11[4] = tmp[0]; 		
			
			//设置确认报文的功能码
			confirmBytesF11[12] = 0x00; 
			
			//确认报文F11
			confirmBytesF11[15] = 0x02;
			confirmBytesF11[16] = 0x01;
			
			//数据单元
			confirmBytesF11[17] = Byte.parseByte(new Byte(preM).toString(), 16); //需要获得16进制数据
			confirmBytesF11[18] = 0x01; //确认
			
			//附加信息AUX
			confirmBytesF11[19] = 0x01;
			confirmBytesF11[20] = 0x01;
			
			//帧校验和
			byte[] temp = new byte[confirmBytesF11.length-8]; 
			System.arraycopy(confirmBytesF11, 6, temp, 0, confirmBytesF11.length-8);
			confirmBytesF11[confirmBytesF11.length-2] = (byte) TCPServerUtil.getCheckSum(temp);
			
			confirmBytesF11[22] = 0x16;
			result = confirmBytesF11;
		}else {
			byte[] confirmBytes = new byte[21];
			System.arraycopy(received, 0, confirmBytes, 0, 16); //截获地址域+功能码作为key+Pn
			
			//设置确认报文长度（地位在前，高位在后）
			tmp = getMessageLength(22-8);
			confirmBytes[1] = tmp[1]; 
			confirmBytes[2] = tmp[0]; 
			confirmBytes[3] = tmp[1]; 
			confirmBytes[4] = tmp[0]; 		
						
			//设置确认报文的功能码
			confirmBytes[12] = 0x00;
			
			//确认报文F1
			confirmBytes[15] = 0x00;
			confirmBytes[16] = 0x00;
			
			//附加信息AUX
			confirmBytes[17] = 0x00;
			confirmBytes[18] = 0x01;
			
			//帧校验和
			byte[] temp = new byte[confirmBytes.length-8]; 
			System.arraycopy(confirmBytes, 6, temp, 0, confirmBytes.length-8);
			confirmBytes[confirmBytes.length-2] = (byte) TCPServerUtil.getCheckSum(temp);
			
			confirmBytes[20] = 0x16;
			result = confirmBytes;
		}

		return result;
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
	
	/**
	 * 获得报文长度对应的两位字节数组（16进制的）
	 * @param length
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月15日
	 */
	private byte[] getMessageLength(int length){
		byte[] result = new byte[2];
		byte[] tmp = new byte[2];
		tmp = TCPServerUtil.intToByteArray(length, 2);
		result[0] =  Byte.parseByte(new Byte(tmp[0]).toString(), 16);
		result[1] =  Byte.parseByte(new Byte(tmp[1]).toString(), 16);
		return result;
	}
}
