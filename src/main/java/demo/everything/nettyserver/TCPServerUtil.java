package demo.everything.nettyserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

/**
 * @author DIAOPG
 * @date 2015年10月13日
 */
public class TCPServerUtil {
	private static Logger logger = Logger.getLogger(TCPServerUtil.class);
	
	/**
	 * 字节数组转换成16进制字符串
	 * @param src
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月13日
	 */
    public static String bytesToHexString(byte[] src){       
        StringBuilder stringBuilder = new StringBuilder();       
        if (src == null || src.length <= 0) {       
            return null;       
        }       
        for (int i = 0; i < src.length; i++) {       
            int v = src[i] & 0xFF;       
            String hv = Integer.toHexString(v);       
            if (hv.length() < 2) {       
                stringBuilder.append(0);       
            }       
            stringBuilder.append(hv); 
            stringBuilder.append(' ');
        }       
        return stringBuilder.toString();       
    }
    
	/**
	 * 获得当前Client对应的IP
	 * @param ctx
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月10日
	 */
	public static String getIPString(ChannelHandlerContext ctx){
		String ipString = "";
		String socketString = ctx.channel().remoteAddress().toString();
		int colonAt = socketString.indexOf(":");
		ipString = socketString.substring(1, colonAt);
		return ipString;
	}
	
	/**
	 * 获得当前Client对应的IP
	 * @param ctx
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月10日
	 */
	public static String getIPString(Channel channel){
		String ipString = "";
		String socketString = channel.remoteAddress().toString();
		int colonAt = socketString.indexOf(":");
		ipString = socketString.substring(1, colonAt);
		return ipString;
	}
	
	/**
	 * 获得当前Client对应的IP
	 * @param ctx
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月10日
	 */
	public static int getPort(ChannelHandlerContext ctx){
		int port = 0;
		String temp="";
		String socketString = ctx.channel().remoteAddress().toString();
		int colonAt = socketString.indexOf(":");
		temp = socketString.substring(colonAt+1, socketString.length());
		port  = new Integer(temp);
		return port;
	}
	
	/**
	 * 获得当前Client对应的IP与端口信息
	 * @param ctx
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月10日
	 */
	public static String getRemoteAddress(ChannelHandlerContext ctx){
		String socketString = "";
		socketString = ctx.channel().remoteAddress().toString();
		return socketString;
	}
	
	/**
	 * 根据地址域字节数组（长度5）+测量点号PN，生成Key<br>
	 * 样式：[A1:00 02][A2:00 03][A3:60][PN:01 01]
	 * @param addressDomain
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月21日
	 */
	public static String getKeyFromArray(byte[] addressDomain, byte[] pnBytes) {
		String resultString="";
		int size = addressDomain.length;
		int sizePN = pnBytes.length;
		if(size!=5){
			logger.error("传入的地址域长度非法，长度应该为5！");
			return "[A1:][A2:][A3:]";
		}
		if(sizePN!=2){
			logger.error("传入的测量点号数组长度非法，长度应该为2！");
			return "[PN:]";
		}
		String tmp = bytesToHexString(addressDomain);
		String A1 = tmp.substring(0, 5);
		String A2 = tmp.substring(6, 11);
		String A3 = tmp.substring(12, 14);
		String tmpPN = bytesToHexString(pnBytes);
		String PN = tmpPN.substring(0, 5);
		resultString = "[A1:"+A1+"][A2:"+A2+"][A3:"+A3+"][PN:"+PN+"]";
		return resultString;
	}

	/**
	 * 根据帧报文获得相应的确认报文
	 * @param received
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月14日
	 */
	public byte[] getConfirmMessage(byte[] received) {
		//这里单独考虑F1\F2确认报文的情况，至于F9\F10\F11暂不做考虑
		byte[] confirmBytes = new byte[20];
		System.arraycopy(received, 0, confirmBytes, 0, 14); //截获地址域+功能码作为key+Pn
		
		//p0,f1
		/*confirmBytes[12] = 0;
		confirmBytes[13] = 0;*/
		confirmBytes[14] = 0;
		confirmBytes[15] = 0;
		
		//附加信息AUX
		confirmBytes[16] = 0;
		confirmBytes[17] = 1;
		
		//帧校验和
		confirmBytes[18] = 0;
		
		confirmBytes[19] = 0x16;
		return confirmBytes;
	}

	/**
	 * 将不足8位的字符串填0补齐8位
	 * @param binaryString
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月14日
	 */
	public static String to8BitString(String binaryString) {
		int len = binaryString.length();
		for (int i = 0; i < 8-len; i++) {
			binaryString = "0"+binaryString;
		}
		return binaryString;
	}

	/**
	 * 判断firFin是否是oldFirFin下一帧
	 * 
	 * @param oldFirFin
	 * @param firFin
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月8日
	 */
	public static boolean isNextFirFin(String oldFirFin, String firFin) {
		// TODO Auto-generated method stub
		boolean result = false;
		if ("10".equals(oldFirFin)&&"00".equals(firFin)) {
			result = true;
		}
		if ("10".equals(oldFirFin)&&"01".equals(firFin)) {
			result = true;
		}
		if ("00".equals(oldFirFin)&&"00".equals(firFin)) {
			result = true;
		}
		if ("00".equals(oldFirFin)&&"01".equals(firFin)) {
			result = true;
		}
		return result;
	}
	
    /**
     * 合并两个字节数组
     * @param bt1
     * @param bt2
     * @return
     * @author DIAOPG
     * @date 2015年8月28日
     */
	public static byte[] combine2Byte(byte[] bt1, byte[] bt2){
    	byte[] byteResult = new byte[bt1.length+bt2.length];
    	System.arraycopy(bt1, 0, byteResult, 0, bt1.length);
    	System.arraycopy(bt2, 0, byteResult, bt1.length, bt2.length);
    	return byteResult;
    }
	
	/**
	 * 将Pn/Fn的两字节数组转为整型
	 */
	public static int dByteToInt(byte[] value, boolean isPn) {
		// DT1、DT2同时为0为数据采集器，整型值为0
		if (value[0]==0 && value[1]==0&&isPn) {
			return 0;
		}
		//int remainder = DBUSDatabusUtil.bitIndexOf(value[0], 1);
		int remainder = toUnsignedInt1(value[0])-1;
		int quotient = isPn ? toUnsignedInt1(value[1]) - 1 : toUnsignedInt1(value[1]);
		return (quotient * 8) + (remainder + 1);
	}
	
	/**
	 * 将字节型转为1字节无符号整型
	 */
	public static int toUnsignedInt1(byte x) {
		return ((int) x) & 0xff;
	}
}
