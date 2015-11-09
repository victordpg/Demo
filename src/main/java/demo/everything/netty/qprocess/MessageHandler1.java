package demo.everything.netty.qprocess;

import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import demo.everything.netty.TCPServerUtil;

/**
 * 
 * @author DIAOPG
 * @date 2015年11月4日
 */
public class MessageHandler1 implements Runnable {
	private static final Logger logger = Logger.getLogger(MessageHandler1.class);
	private BlockingQueue<MessageCtxBean> blockingQueue;

	public MessageHandler1() {}

	public MessageHandler1(BlockingQueue<MessageCtxBean> blockingQueue) {
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
				byteArray = msgBean.getMessageArray();
				ctx = msgBean.getChhContext();

				//count++;
				
				logger.info("设备 "+TCPServerUtil.getIPString(ctx)+" 主动上传的报文：【"+TCPServerUtil.bytesToHexString(byteArray)+"】");
	            /**
	             *  多帧报文处理：
	             *  多帧报文的传输形式，分批次多次传输
	             *  FIR	FIN	应用说明
				 *  0	0	多帧：中间帧
				 *  0	1	多帧：结束帧
				 *  1	0	多帧：第1帧，有后续帧。
				 *  1	1	单帧
				 *  处理方式：前后帧报文连接在一起，在我们程序里将多帧报文拼接成单帧报文，之后当作单帧报文在程序里处理。
				 *  拼接规则：去掉后一帧报文的第一个数据单元标识，与之前一帧报文进行拼接。
	             */
	            String firFin = TCPServerUtil.to8BitString(Integer.toBinaryString(byteArray[13])).substring(1, 3); //单帧、首、末帧标志
	            if (!"11".equals(firFin)) {
				} else {
					//处理单帧报文
				}
	            
	            byte[] confirmBytes = getConfirmMessage(byteArray);
	            ctx.channel().writeAndFlush(confirmBytes); //下发确认报文
				
			} catch (Exception e) {
				logger.error("解析报文时出现异常：", e);
			}
		}
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
