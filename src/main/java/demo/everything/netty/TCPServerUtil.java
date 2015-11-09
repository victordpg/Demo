package demo.everything.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
			return "[A1:][A2:]";
		}
		if(sizePN!=2){
			logger.error("传入的测量点号数组长度非法，长度应该为2！");
			return "[PN:]";
		}
		String tmp = bytesToHexString(addressDomain);
		String A1 = tmp.substring(0, 5);
		String A2 = tmp.substring(6, 11);
		//String A3 = tmp.substring(12, 14);
		String tmpPN = bytesToHexString(pnBytes);
		String PN = tmpPN.substring(0, 5);
		//resultString = "[A1:"+A1+"][A2:"+A2+"][A3:"+A3+"][PN:"+PN+"]";
		resultString = "[A1:"+A1+"][A2:"+A2+"][PN:"+PN+"]";
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
	 * 取字节的某个位，返回0或者1
	 */
	public static int getBit(byte value, int position) {
		assert position >= 0 && position <= 7;
		return (value >> position) & 1;
	}

	/**
	 * 返回字节中第一个target的位数
	 */
	public static int bitIndexOf(byte value, int target) {
		assert target == 0 || target == 1;
		for (int i = 0; i < 8; i++) {
			if (getBit(value, i) == target) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 取整数的某个字节
	 */
	public static byte getByte(int value, int position) {
		return (byte) ((value >> (position * 8)) & 0xFF);
	}

	/**
	 * 计算校验和
	 */
	public static byte makeChecksum(byte[] bytes) {
		return makeChecksum(bytes, bytes.length - 1);
	}

	public static byte makeChecksum(byte[] bytes, int length) {
		int sum = 0;
		for (int i = 0; i < length; i++) {
			sum += bytes[i];
		}
		return (byte) (sum % 256);
	}

	/**
	 * 将字节型转为1字节无符号整型
	 */
	public static int toUnsignedInt1(byte x) {
		return ((int) x) & 0xff;
	}

	/**
	 * 将高低两字节转为无符号整型
	 */
	public static int toUnsignedInt2(byte high, byte low) {
		return (toUnsignedInt1(high) << 8) | toUnsignedInt1(low);
	}

	/**
	 * 将高中低三字节转为无符号整型
	 */
	public static int toUnsignedInt3(byte high, byte median, byte low) {
		return (toUnsignedInt1(high) << 16) | (toUnsignedInt1(median) << 8)
				| toUnsignedInt1(low);
	}

	/**
	 * 将高低两字节转为有符号整型
	 */
	public static int toInt2(byte high, byte low) {
		return (high << 8) | toUnsignedInt1(low);
	}

	/**
	 * 获得两字符的字符串<br>
	 * 如byte值<10返回字符串前面加"0"凑足两位
	 * 
	 * @author DIAOPG
	 * @date 2015年7月27日
	 */
	public static String get2CharString(byte bt) {
		String string;
		Integer int1 = (int) bt;
		if (int1 < 10)
			string = "0" + int1.toString();
		else {
			string = int1.toString();
		}
		return string;
	}

	/**
	 * 获得曲线冻结时间间隔
	 * 
	 * @param bt
	 *            冻结密度
	 * @return
	 * @author DIAOPG
	 * @date 2015年7月28日
	 */
	public static String getFreezeIntervalTime(byte bt) {
		String string = "";
		Integer int1 = new Integer(toUnsignedInt1(bt));
		switch (int1) {
		case 0:
			string = "00";
			break;
		case 1:
			string = "15";
			break;
		case 2:
			string = "30";
			break;
		case 3:
			string = "60";
			break;
		case 254:
			string = "05";
			break;
		case 255:
			string = "01";
			break;

		default:
			break;
		}
		return string;
	}

	/**
	 * 获得曲线冻结点数
	 * 
	 * @param bt
	 *            冻结密度
	 * @return
	 * @author DIAOPG
	 * @date 2015年7月28日
	 */
	public static int getCurveFreezeNCount(byte bt) {
		int result = 0;
		Integer int1 = new Integer(toUnsignedInt1(bt));
		switch (int1) {
		case 1:
			result = 96;
			break;
		case 2:
			result = 48;
			break;
		case 3:
			result = 24;
			break;
		case 254:
			result = 288;
			break;
		case 255:
			result = 1440;
			break;

		default:
			break;
		}
		return result;
	}

	/**
	 * 获得曲线冻结点数对应的字节
	 * 
	 * @param strM
	 *            冻结密度
	 * @return
	 * @author DIAOPG
	 * @date 2015年7月29日
	 */
	public static byte getCurveFreezeNByte(String strM) {
		int result = 0;
		Integer int1 = new Integer(strM);
		switch (int1) {
		case 15:
			result = 96;
			break;
		case 30:
			result = 48;
			break;
		case 60:
			result = 24;
			break;
		case 5:
			result = 288;
			break;
		case 1:
			result = 1440;
			break;
		default:
			break;
		}
		return intToByteArray(result, 1)[0];
	}

	/**
	 * 返回intValue对应的长度为count的byte数组<br>
	 * 都会转换成地位在前，高位在后
	 * 
	 * @param intValue
	 * @param count
	 * @return
	 * @author DIAOPG
	 * @date 2015年7月28日
	 */
	public static byte[] intToByteArray(int intValue, int count) {
		byte[] result = new byte[count];
		for (int i = 0; i < count; i++) {
			result[i] = (byte) ((intValue >> 8 * (count - 1 - i)) & 0xFF);
		}
		return reverseByteArray(result);
	}

	/**
	 * 将byte数组转换成整型
	 * 
	 * @param bytes
	 * @return
	 * @author DIAOPG
	 * @date 2015年7月30日
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		int count = bytes.length;
		// 因为整型长度为32位，所以此处count不应该超过4。
		if (count > 4) {
			throw new IllegalArgumentException("入参byte数组长度超过合法长度4。");
		}
		for (int i = 0; i < count; i++) {
			int shift = (count - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}

	public static byte intConcat(int high, int low) {
		return (byte) (high << 4 | low);
	}

	/**
	 * 将值转化为指定长度的二进制字符串，长度不够在字符串前面补0
	 * 
	 * @param value
	 * @param length
	 * @return
	 * @author zhangjian
	 * @date 2015年8月20日
	 */
	public static String getBinaryString(int value, int length) {
		String binaryString = Integer.toBinaryString(value);
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < length - binaryString.length(); i++) {
			result.append("0");
		}
		return result.append(binaryString).toString();
	}
	/**
	 * 将long型值转化为指定长度的二进制字符串，长度不够在字符串前面补0
	 * 
	 * @param value
	 * @param length
	 * @return
	 * @author zhangjian
	 * @date 2015年9月1日
	 */
	public static String getBinaryString(long value, int length){
		String binaryString = Long.toBinaryString(value);
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < length - binaryString.length(); i++) {
			result.append("0");
		}
		return result.append(binaryString).toString();
	}
	
	/**
	 * 从map中获取key值包含template的map的子集
	 * 
	 * @param map
	 * @param template
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月2日
	 */
	public static Map<String, Object> getSubMapByTemplate(Map<String, Object> map, String template){
		Map<String, Object> subMap = new HashMap<String, Object>();
		Set<String> set = map.keySet();
		Iterator<String> iterator=set.iterator();

		while (iterator.hasNext()) {
			String string = iterator.next();
			if (string.contains(template)) {
				subMap.put(string, map.get(string));
			}
		}
		return subMap;
	}
	
	/**
     * 合并两个字节数组
     * @param bt1
     * @param bt2
     * @return
     * @author DIAOPG
     * @date 2015年8月28日
     */
    public static byte[] combine2ByteArrays(byte[] bt1, byte[] bt2){
    	byte[] byteResult = new byte[bt1.length+bt2.length];
    	System.arraycopy(bt1, 0, byteResult, 0, bt1.length);
    	System.arraycopy(bt2, 0, byteResult, bt1.length, bt2.length);
    	return byteResult;
    }
    
	/**
	 * 获得校验和
	 * @param bytes
	 * @return
	 * @author DIAOPG
	 * @date 2015年10月22日
	 */
	public static int getCheckSum(byte[] bytes){
		int result = 0;
		int temp = 0;
		for (int i = 0; i < bytes.length; i++) {
			temp += bytes[i];
		}
		result = temp%256;
		return result;
	}    
	
    /**
     * 数组反转
     * @param bytes
     * @return
     * @author DIAOPG
     * @date 2015年9月23日
     */
	public static byte[] reverseByteArray(byte[] bytes){
		int length = bytes.length;
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[length-1-i] = bytes[i];
		}
		return result;
	}	
}
