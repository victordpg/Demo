package demo.everything.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * TJZL TCP decoder.
 * @author DIAOPG
 * @date 2015年11月6日
 */
public class Decoder4TCP extends ByteArrayDecoder {
	private static final Logger logger = Logger.getLogger(Decoder4TCP.class);
	private static int count = 0;
	private Map<String, byte[]> mapRemain = new ConcurrentHashMap<String, byte[]>();
	
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		count++;
		logger.info("Decoder4TCP当前计数为："+count);
        byte[] result = null;
		byte[] array = new byte[msg.readableBytes()]; //stick-message
        msg.getBytes(0, array);

		int total = array.length; //数组总长度
		byte[] msgLength1 = new byte[2]; //报文长度字段数组
		System.arraycopy(array, 1, msgLength1, 0, 2); 
		int appLayerLength1 = TCPServerUtil.byteArrayToInt(TCPServerUtil.reverseByteArray(msgLength1)); //获得报文应用层长度
		byte[] msgLength2 = new byte[2]; //报文长度字段数组
		System.arraycopy(array, 3, msgLength2, 0, 2); 
		int appLayerLength2 = TCPServerUtil.byteArrayToInt(TCPServerUtil.reverseByteArray(msgLength2)); //获得报文应用层长度
		int headFlag1 = array[0]; //0x68 or not
		int headFlag2 = array[5]; //0x68 or not

		//判断当前报文包是否以完整一帧报文开始
		if (headFlag1==0x68 && headFlag2==0x68 && appLayerLength1==appLayerLength2) {
			if (appLayerLength1+8 > total) {
				logger.error("Client:"+TCPServerUtil.getIPString(ctx)+"一次传输的数据太大，大于Server每次接收最大值!");
			}else {
				while (total>0) {
					if (total>=3) {
						System.arraycopy(array, 1, msgLength1, 0, 2); 
						appLayerLength1 = TCPServerUtil.byteArrayToInt(TCPServerUtil.reverseByteArray(msgLength1)); //获得报文应用层长度
						
						if (total<(appLayerLength1+8)) {
							mapRemain.put(TCPServerUtil.getRemoteAddress(ctx), array); //put the message head part into map.
							break;
						}else {
							result = new byte[appLayerLength1+8];
							System.arraycopy(array, 0, result, 0, appLayerLength1+8);
							out.add(result);
							
							array = Arrays.copyOfRange(array, appLayerLength1+8, total);
							total = array.length;
						}
					}else{
						mapRemain.put(TCPServerUtil.getRemoteAddress(ctx), array); //将上次没有处理完的剩余部分报文放入map
						break;
					}
				}
			}
		}else {
			String key = TCPServerUtil.getRemoteAddress(ctx);
			byte[] headPart = mapRemain.get(key);
			mapRemain.remove(key);
			int startLoaction = getMessageStartLocation(array);
			byte[] tailPart = new byte[startLoaction];
			System.arraycopy(array, 0, tailPart, 0, startLoaction);
			byte[] whole = TCPServerUtil.combine2ByteArrays(headPart, tailPart);
			out.add(whole);
			
			array = Arrays.copyOfRange(array, startLoaction, total);
			total = array.length;
			
			while (total>0) {
				if (total>=3) {
					System.arraycopy(array, 1, msgLength1, 0, 2); 
					appLayerLength1 = TCPServerUtil.byteArrayToInt(TCPServerUtil.reverseByteArray(msgLength1)); //获得报文应用层长度
					
					if (total<(appLayerLength1+8)) {
						mapRemain.put(TCPServerUtil.getRemoteAddress(ctx), array); //put the message segment into map.
						break;
					}else {
						result = new byte[appLayerLength1+8];
						System.arraycopy(array, 0, result, 0, appLayerLength1+8);
						out.add(result);
						
						array = Arrays.copyOfRange(array, appLayerLength1+8, total);
						total = array.length;
					}
				}else{
					mapRemain.put(TCPServerUtil.getRemoteAddress(ctx), array); //将上次没有处理完的剩余部分报文放入map
					break;
				}
			}
		}
	}

	/**
	 * 获得报文起始位置，
	 * @param array 
	 * <br>样式：{0, 1, 43, 22, 104, 25, 0, 25, 0, 104, 0, -71, 0, 1, 0, 0, 12, 96, 1, 1, 1, 23, 3, 32, 8, 33, 7, 1, 6, 0, 10, 0, 20, 0, 1, 1, 22}
	 * @return
	 * @author DIAOPG
	 * @date 2015年11月9日
	 */
	private int getMessageStartLocation(byte[] array){
		int location = 0; 
		byte[] temp = new byte[7];
		for (int i = 0; i < array.length-7; i++) {
			System.arraycopy(array, i, temp, 0, 7);
			if (temp[0]==0x16&&temp[1]==0x68&&temp[2]==temp[4]&&temp[3]==temp[5]&&temp[6]==0x68) {
				location = i+1;
				break;
			}
		}
		return location;
	}
}
