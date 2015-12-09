package demo.everything.primitive.string;

import java.util.Arrays;

public class _TestString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string = "0123456789";
		char[] arrayASCII_CHAR = string.toCharArray();
		int[] arrayASCII_INT = new int[arrayASCII_CHAR.length]; 
		byte[] arrayASCII_BYTE = new byte[arrayASCII_CHAR.length]; 
		for (int i = 0; i < arrayASCII_CHAR.length; i++) {
			arrayASCII_INT[i] = arrayASCII_CHAR[i];
			arrayASCII_BYTE[i] = (byte) arrayASCII_CHAR[i];
		}
		System.out.println("array 1:"+ Arrays.toString(arrayASCII_CHAR)+"\narray 2:"+Arrays.toString(arrayASCII_INT)+"\narray 3:"+Arrays.toString(arrayASCII_BYTE));
	}
}
