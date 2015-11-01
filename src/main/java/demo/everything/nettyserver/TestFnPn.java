package demo.everything.nettyserver;

import java.util.Arrays;

/**
 * 用于测试FN, PN 对应的值或者数组
 * @author DIAOPG
 * @date 2015年10月22日
 */
public class TestFnPn {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(intToDBytes(177, false))); //获得FN对应的数组
		System.out.println(Arrays.toString(intToDBytes(104, true))); //获得PN对应的数组
		
		byte[] byteFN ={0X01, 0X0B};
		System.out.println(dByteToInt(byteFN, false)); //获得数组对应的FN
		byte[] bytePN ={1, 1};
		System.out.println(dByteToInt(bytePN, true)); //获得数组对应的PN
		
		//测试DA\DT数组对应的FN\PN值
		/*byte[] bt1 = {0, 0};
		byte[] bt2 = {1, 1};
		byte[] bt3 = {2, 1};
		byte[] bt4 = {3, 1};
		byte[] bt5 = {4, 1};
		byte[] bt6 = {5, 1};
		byte[] bt7 = {6, 1};
		byte[] bt8 = {7, 1};
		byte[] bt9 = {0, 2};
		byte[] bt10 ={1, 2};
		byte[] bt11 ={2, 2};
		System.out.println(dByteToInt2(bt1, true));
		System.out.println(dByteToInt2(bt2, true));
		System.out.println(dByteToInt2(bt3, true));
		System.out.println(dByteToInt2(bt4, true));
		System.out.println(dByteToInt2(bt5, true));
		System.out.println(dByteToInt2(bt6, true));
		System.out.println(dByteToInt2(bt7, true));
		System.out.println(dByteToInt2(bt8, true));
		System.out.println(dByteToInt2(bt9, true));
		System.out.println(dByteToInt2(bt10, true));
		System.out.println(dByteToInt2(bt11, true));*/		

		/*byte[] bt1 = {0, 0};
		byte[] bt2 = {1, 0};
		byte[] bt3 = {2, 0};
		byte[] bt4 = {3, 0};
		byte[] bt5 = {4, 0};
		byte[] bt6 = {5, 0};
		byte[] bt7 = {6, 0};
		byte[] bt8 = {7, 0};
		byte[] bt9 = {0, 1};
		byte[] bt10 ={1, 1};
		byte[] bt11 ={2, 1};*/
		/*System.out.println(dByteToInt2(bt1, false));
		System.out.println(dByteToInt2(bt2, false));
		System.out.println(dByteToInt2(bt3, false));
		System.out.println(dByteToInt2(bt4, false));
		System.out.println(dByteToInt2(bt5, false));
		System.out.println(dByteToInt2(bt6, false));
		System.out.println(dByteToInt2(bt7, false));
		System.out.println(dByteToInt2(bt8, false));
		System.out.println(dByteToInt2(bt9, false));
		System.out.println(dByteToInt2(bt10, false));
		System.out.println(dByteToInt2(bt11, false));*/
		
		//输入PN\FN得到对应DADT数组
		/*System.out.println(Arrays.toString(intToDBytes2(0, true)));
		System.out.println(Arrays.toString(intToDBytes2(1, true)));
		System.out.println(Arrays.toString(intToDBytes2(2, true)));
		System.out.println(Arrays.toString(intToDBytes2(3, true)));
		System.out.println(Arrays.toString(intToDBytes2(4, true)));
		System.out.println(Arrays.toString(intToDBytes2(5, true)));
		System.out.println(Arrays.toString(intToDBytes2(6, true)));
		System.out.println(Arrays.toString(intToDBytes2(7, true)));
		System.out.println(Arrays.toString(intToDBytes2(8, true)));
		System.out.println(Arrays.toString(intToDBytes2(9, true)));
		System.out.println(Arrays.toString(intToDBytes2(10, true)));*/
		/*System.out.println(Arrays.toString(intToDBytes2(1, false)));
		System.out.println(Arrays.toString(intToDBytes2(2, false)));
		System.out.println(Arrays.toString(intToDBytes2(3, false)));
		System.out.println(Arrays.toString(intToDBytes2(4, false)));
		System.out.println(Arrays.toString(intToDBytes2(5, false)));
		System.out.println(Arrays.toString(intToDBytes2(6, false)));
		System.out.println(Arrays.toString(intToDBytes2(7, false)));
		System.out.println(Arrays.toString(intToDBytes2(8, false)));
		System.out.println(Arrays.toString(intToDBytes2(31, false)));
		System.out.println(Arrays.toString(intToDBytes2(32, false)));*/
	}

	/**
	 * 将整型转为Pn/Fn的两字节数组
	 */
	protected static byte[] intToDBytes(int value, boolean isPn) {
		assert value <= 2040 : "DA或DT不能大于2040";
		byte[] result = new byte[2];
		// 0为数据采集器
		if (value == 0) {
			return result;
		}
		int mod = (value - 1) % 8;
		//result[0] = (byte) (1 << mod);
		result[0] = (byte) (mod+1);
		//int quotient = (value - 1) / 8;
		int quotient = isPn? ((value-1)/8)+1: (value-1)/ 8;
		result[1] = (byte) quotient; // Fn的高字节从0开始，Pn从1开始
		return result;
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
