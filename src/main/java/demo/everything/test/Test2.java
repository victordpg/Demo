package demo.everything.test;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test2 test2 = new Test2();
		/*System.out.println(Arrays.toString("1".getBytes()));
		System.out.println("1".getBytes()[0]);
		System.out.println(test2.getBitStr("1101"));*/
		//System.out.println(new Double(3)/2);
		//System.out.println(test2.convert28BitStr("255"));
		//System.out.println(Arrays.toString(test2.intToDByte(1)));
		//System.out.println(test2.getPFBitString("p0"));
		
		byte[] bt = {(byte) 200,0};
		System.out.println(test2.getValue(bt));
	}

	/**
	 * 获得二进制串，每两位字符转换一下<p>
	 * 如：输入"111"，返回"0000101100000001";<br>
	 * 输入"1101"，返回"0000101100000001"。
	 * @param str 数字字符串
	 * @return
	 */
	private String getBitStr(String str){
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < new Double(str.length())/2; i++) {
			if (i<str.length()/2) {
				sBuffer.append(convert28BitStr(str.substring(i*2, (i+1)*2)));
			} else {
				sBuffer.append(convert28BitStr(str.substring(i*2, i*2+1)));
			}
		}
		return sBuffer.toString();
	}
	
	/**
	 * 返回入参对应的8位二进制串，超过8位的不处理
	 * @param str 数字字符串，0-255。
	 * @return
	 * @author DIAOPG
	 * @date 2015年7月21日
	 */
	private String convert28BitStr(String str){
		String result = Integer.toBinaryString(Integer.parseInt(str));
		int len = result.length();
		if(len!=8){
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < 8-len; i++) {
				sBuffer.append("0");
			}
			result = sBuffer.toString()+result;
		}
		if (len>8) {
			throw new IllegalArgumentException("入参转换后值范围大于8位，不做处理！");
		}
		return result;
	}
	
	
    private byte[] intToDByte(int value) {
        assert value <= 2040: "DA或DT不能大于2040";
        byte[] result = new byte[2];
        int mod = (value - 1) % 8;
        result[0] = (byte) (1 << mod);
        int quotient = (value - 1) / 8;
        result[1] = (byte) (quotient + 1);
        return result;
    }
    
    
	public static String getPFBitString(String str){
		String tmp = "00000000";
		char[] charA1 = tmp.toCharArray();
		char[] charA2 = tmp.toCharArray();
		int row, colum;

		int numb = new Integer(str.substring(1));
		String flag = str.substring(0, 1);
		row = numb % 8;
		colum = numb / 8 + 1;
		
		if (!"P".equalsIgnoreCase(flag)&&!"F".equalsIgnoreCase(flag)) {
			throw new IllegalArgumentException("入参首字母错误，应该以P或F开头（忽略大小写）！");
		}

		if ("P".equalsIgnoreCase(flag)) {
			if (numb < 0) {
				throw new IllegalArgumentException("入参Pn错误，n应该大于等于0，即从0开始！");
			}
			if (numb == 0)
				return "0000000000000000";
			charA1[row - 1] = '1';
			charA2[colum] = '1';
		} else {
			if (numb < 1) {
				throw new IllegalArgumentException("入参Fn错误，n应该大于0，即从1开始！");
			}
			charA1[row - 1] = '1';
			charA2[colum - 1] = '1';
		}

		return new String(charA1) + new String(charA2);
	}
	
	public Object getValue(byte[] bytes) {
		double result = toInt2(bytes[1], bytes[0]);
		return result / 10000 * 1;
	}
	/**
	 * 将高低两字节转为有符号整型
	 */
	public static int toInt2(byte high, byte low) {
		return (high << 8) | toUnsignedInt1(low);
	}
	
	
	/**
	 * 将字节型转为1字节无符号整型
	 */
    public static int toUnsignedInt1(byte x) {
        return ((int) x) & 0xff;
    }

}
