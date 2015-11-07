package demo.everything.exception;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import com.neusoft.aplus.databus.util.DBUSDatabusUtil;

/**
 * 
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
        /*byte[] array = decode(ctx, msg);
        if (array != null) {
            out.add(array);
        }*/
		count++;
		logger.info("Decoder4TCP当前计数为："+count);
        byte[] result = null;
		byte[] array = new byte[msg.readableBytes()]; //stick-message
        msg.getBytes(0, array);

		int total = array.length; //数组总长度
		byte[] msgLength1 = new byte[2]; //报文长度字段数组
		System.arraycopy(array, 1, msgLength1, 0, 2); 
		byte[] msgLength2 = new byte[2]; //报文长度字段数组
		int appLayerLength1 = DBUSDatabusUtil.byteArrayToInt(DBUSDatabusUtil.reverseByteArray(msgLength1)); //获得报文应用层长度
		System.arraycopy(array, 3, msgLength2, 0, 2); 
		int appLayerLength2 = DBUSDatabusUtil.byteArrayToInt(DBUSDatabusUtil.reverseByteArray(msgLength2)); //获得报文应用层长度
		int headFlag1 = array[0]; //0x68 or not
		int headFlag2 = array[5]; //0x68 or not

		if (headFlag1==0x68 && headFlag2==0x68 && appLayerLength1==appLayerLength2) {
			if (appLayerLength1+8 > total) {
				logger.error("Client:"+TCPServerUtil.getIPString(ctx)+"一次传输的数据太大，大于Server每次接收最大值!");
			}else {
				while (total>0) {
					result = new byte[appLayerLength1+8];
					System.arraycopy(array, 0, result, 0, appLayerLength1+8);
					out.add(result);
					
					array = Arrays.copyOfRange(array, appLayerLength1+8, total);
					total -= (appLayerLength1+8);
					
					if (total>=3) {
						System.arraycopy(array, 1, msgLength1, 0, 2); 
						appLayerLength1 = DBUSDatabusUtil.byteArrayToInt(DBUSDatabusUtil.reverseByteArray(msgLength1)); //获得报文应用层长度
						if (appLayerLength1+8>total) {
							mapRemain.put(TCPServerUtil.getRemoteAddress(ctx), array); //put the message segment into map.
							break;
						}
					}else{
						mapRemain.put(TCPServerUtil.getRemoteAddress(ctx), array); //将上次没有处理完的剩余部分报文放入map
						break;
					}
				}
			}
		}else {
			logger.info("Decoder4TCP return null NO.2: " + "[headFlag1:"
					+ headFlag1 + "][headFlag2:" + headFlag2
					+ "][appLayerLength1:" + appLayerLength1
					+ "][appLayerLength2:" + appLayerLength2 + "]: "
					+ Arrays.toString(array));
			byte[] lastRemain = mapRemain.get(TCPServerUtil.getRemoteAddress(ctx));
			int startLoaction = getNextMessageStartLocation(array);
			
		}
	}

	/**
	 * 获得下一条报文的起始位置
	 * @param array
	 * @return
	 * @author DIAOPG
	 * @date 2015年11月7日
	 */
	private int getNextMessageStartLocation(byte[] array) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unused")
	private byte[] decode(ChannelHandlerContext ctx, ByteBuf msg) {
		count++;
		logger.info("Decoder4TCP当前计数为："+count);
        byte[] result = null;
		byte[] array = new byte[msg.readableBytes()];
        msg.getBytes(0, array);

		int total = array.length; //数组总长度
		byte[] msgLength1 = new byte[2]; //报文长度字段数组
		System.arraycopy(array, 1, msgLength1, 0, 2); 
		byte[] msgLength2 = new byte[2]; //报文长度字段数组
		int appLayerLength1 = DBUSDatabusUtil.byteArrayToInt(DBUSDatabusUtil.reverseByteArray(msgLength1)); //获得报文应用层长度
		System.arraycopy(array, 3, msgLength2, 0, 2); 
		int appLayerLength2 = DBUSDatabusUtil.byteArrayToInt(DBUSDatabusUtil.reverseByteArray(msgLength2)); //获得报文应用层长度
		int headFlag1 = array[0]; //0x68 or not
		int headFlag2 = array[5]; //0x68 or not

		if (headFlag1==0x68 && headFlag2==0x68 && appLayerLength1==appLayerLength2) {
			if (appLayerLength1+8 > total) {
				logger.error("Decoder4TCP return null NO.1: "+Arrays.toString(array));
				msg.skipBytes(total);
				return null;
			}else {
				result = new byte[appLayerLength1+8];
				System.arraycopy(array, 0, result, 0, appLayerLength1+8);
				msg.skipBytes(appLayerLength1+8);
			}
		}else {
			logger.info("Decoder4TCP return null NO.2: " + "[headFlag1:"
					+ headFlag1 + "][headFlag2:" + headFlag2
					+ "][appLayerLength1:" + appLayerLength1
					+ "][appLayerLength2:" + appLayerLength2 + "]: "
					+ Arrays.toString(array));
			return null;
		}
        
		return result;
	}
	
}
