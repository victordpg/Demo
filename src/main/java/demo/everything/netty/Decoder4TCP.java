package demo.everything.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;

import java.util.List;

/**
 * 创建一个Decoder用于处理16进制TCP报文<br>
 * 报文样式参见：TCPClient_Initiative.class
 * @author Victor
 *
 */
public class Decoder4TCP extends ByteArrayDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
        byte[] array = decode(ctx, msg);
        if (array != null) {
            out.add(array);
        }
	}

	private byte[] decode(ChannelHandlerContext ctx, ByteBuf msg) {
        byte[] result = null;
		byte[] array = new byte[msg.readableBytes()];
        msg.getBytes(0, array);

		int total = array.length; //数组总长度
		byte[] appLayerLengthBytes = new byte[2]; //报文长度字段数组
		System.arraycopy(array, 1, appLayerLengthBytes, 0, 2); 
		int headFlag1 = array[0]; //0x68
		int headFlag2 = array[5]; //0x68
		int appLayerLength = byteArrayToInt(reverseByteArray(appLayerLengthBytes)); //获得报文应用层长度
		
		if (headFlag1==0x68 && headFlag2==0x68) {
			if (appLayerLength+8 > total) {
				msg.skipBytes(total);
				return null;
			}else {
				result = new byte[appLayerLength+8];
				System.arraycopy(array, 0, result, 0, appLayerLength+8);
				msg.skipBytes(appLayerLength+8);
			}
		}else {
			return null;
		}
        
		return result;
	}
	
	private byte[] reverseByteArray(byte[] appLayerLengthBytes) {
		// TODO Auto-generated method stub
		int length = appLayerLengthBytes.length;
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[i] = appLayerLengthBytes[length-1-i];
		}
		return result;
	}

    public static int byteArrayToInt(byte[] bytes) {
           int value= 0;
           int count = bytes.length; 
           //因为整型长度为32位，所以此处count不应该超过4。
           if (count>4) {
        	   throw new IllegalArgumentException("入参byte数组长度超过合法长度4。");
           }
           for (int i = 0; i < count; i++) {
               int shift= (count - 1 - i) * 8;
               value +=(bytes[i] & 0x000000FF) << shift;//往高位游
           }
           return value;
     }
    
}
