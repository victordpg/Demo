package demo.everything.primitive;

public class ByteTest {

    public static int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
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
}
