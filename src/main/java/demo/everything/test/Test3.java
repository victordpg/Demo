package demo.everything.test;

import java.util.Map;


public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("freezeType", "Curve");
		map.put("freezeTs", "5923160715");
		map.put("freezeM", "254");
		map.put("freezeN", "10");
		System.out.println(Arrays.toString(intFreezeType2Bytes(map)));*/
		
		/*byte[] bytes = getBytes("270715");
		System.out.println(Arrays.toString(bytes));
		System.out.println((String)getValue(bytes));*/
		/*byte bt = 96;
		System.out.println(toUnsignedInt(bt));
		System.out.println(getFreezeIntervalTime(bt));*/
		
		/*String string = "255";
		System.out.println(Arrays.toString(intToByteArray(new Integer(string),3)));*/
		
		/*System.out.println(getCurveFreezeNByte("60"));
		
		System.out.println(new Integer("99").byteValue());
		System.out.println(intToByteArray(99, 1)[0]);*/
		
		byte[] bt = new byte[2];
		bt[0] = (byte) 0x00;
		bt[1] = (byte) 0xFF;
		System.out.println(byteArrayToInt(bt));
	}
	
    /**
     * 将byte数组转换成整型
     * @param bytes
     * @return
     * @author DIAOPG
     * @date 2015年7月30日
     */
    public static Object byteArrayToInt(byte[] bytes) {
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
	
	  /**
     * 获得曲线冻结点数对应的字节
     * @param strM 冻结密度
     * @return
     * @author DIAOPG
     * @date 2015年7月29日
     */
    public static byte getCurveFreezeNByte(String strM){
    	int result = 0;
    	Integer int1 = new Integer(strM);
	    switch (int1) {
		case 15:
			result=96;
			break;
		case 30:
			result=48;
			break;			
		case 60:
			result=24;
			break;
		case 5:
			result=288;
			break;
		case 1:
			result=1440;
			break;			
		default:
			break;
		}
    	return intToByteArray(result,1)[0];
    }
	
	public static byte[] intToByteArray(int i) {   
        byte[] result = new byte[2];   
        result[0] = (byte)((i >> 8) & 0xFF); 
        result[1] = (byte)(i & 0xFF);
        return result;
      }
	
	public static byte[] intToByteArray(int intValue, int count) {   
        byte[] result = new byte[count];   
        for(int i=0;i<count;i++){
        	result[i] = (byte)((intValue >> 8*(count-1-i)) & 0xFF); 
        }
        return result;
    }
	
	@SuppressWarnings("unused")
	private static byte[] intFreezeType2Bytes(Map<String, Object> map) {
		byte[] result = null;

		if (map.get("freezeDMY")!=null) {
			result = new byte[3];
			String freezeTDD = (String) map.get("freezeDMY");
			result[0] = new Integer(freezeTDD.substring(0, 2)).byteValue();
			result[1] = new Integer(freezeTDD.substring(2, 4)).byteValue();
			result[2] = new Integer(freezeTDD.substring(4, 6)).byteValue();
		}
		if (map.get("freezeMY")!=null) {
			result = new byte[2];
			String freezeMY = (String) map.get("freezeMY");
			result[0] = new Integer(freezeMY.substring(0, 2)).byteValue();
			result[1] = new Integer(freezeMY.substring(2, 4)).byteValue();
		}
		if (map.get("freezeTs")!=null) {
			result = new byte[7];
			String freezeTs = (String) map.get("freezeTs");
			String freezeM = (String) map.get("freezeM");
			String freezeN = (String) map.get("freezeN");
			result[0] = new Integer(freezeTs.substring(0, 2)).byteValue();
			result[1] = new Integer(freezeTs.substring(2, 4)).byteValue();
			result[2] = new Integer(freezeTs.substring(4, 6)).byteValue();
			result[3] = new Integer(freezeTs.substring(6, 8)).byteValue();
			result[4] = new Integer(freezeTs.substring(8, 10)).byteValue();
			result[5] = new Integer(freezeM).byteValue();
			result[6] = new Integer(freezeN).byteValue();
		}

		return result;
	}
	
	private static byte[] getBytes(String str) {
		byte[] result = new byte[3];
		result[0] = new Integer(str.substring(0, 2)).byteValue();
		result[1] = new Integer(str.substring(2, 4)).byteValue();
		result[2] = new Integer(str.substring(4, 6)).byteValue();
		/*result[3] = new Integer(str.substring(6, 8)).byteValue();
		result[4] = new Integer(str.substring(8, 10)).byteValue();*/

		return result;
	}
	
    public static Object getValue(byte[] bytes) {
        // TODO
    	if (bytes.length!=3) {
			throw new IllegalArgumentException("入参应该3位！");
		}
    	StringBuffer sbf = new StringBuffer();
    	sbf.append(get2CharString(bytes[0]));
    	sbf.append(get2CharString(bytes[1]));
    	sbf.append(get2CharString(bytes[2]));
        return sbf.toString();
    }
    
    public static String get2CharString(byte bt){
    	String string = "";
    	Integer int1 = new Integer(bt);
    	if(int1<10)
    		string = "0"+int1.toString();
    	else {
			string = int1.toString();
		}
    	return string;
    }
    
    /**
     * 获得冻结时间间隔
     * @param bt 冻结密度
     * @return
     * @author DIAOPG
     * @date 2015年7月28日
     */
    public static String getFreezeIntervalTime(byte bt){
    	String string = "";
    	Integer int1 = new Integer(toUnsignedInt(bt));
	    switch (int1) {
		case 0:
			string = "0";
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
			string = "5";
			break;
		case 255:
			string = "1";
			break;			
		
		default:
			break;
		}
    	return string;
    }
    
    public static int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
    }
}
