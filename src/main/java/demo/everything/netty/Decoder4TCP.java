package demo.everything.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;

import java.util.List;


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
		byte[] msgLength = new byte[2]; //报文长度字段数组
		System.arraycopy(array, 1, msgLength, 0, 2); 
		int headFlag1 = array[0]; //0x68
		int headFlag2 = array[5]; //0x68
//		int appLayerLength = DBUSDatabusUtil.byteArrayToInt(DBUSDatabusUtil.reverseByteArray(msgLength)); //获得报文应用层长度
		int appLayerLength = 0; //获得报文应用层长度
		
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
	
}
