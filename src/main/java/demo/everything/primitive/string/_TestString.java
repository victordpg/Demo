package demo.everything.primitive.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class _TestString {

	/*public static void main(String[] args) {
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
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string = "11101110111010101011";
		List<int[]> list1 = _TestString.getPositionList(string);
		List<int[]> list2 = _TestString.getPositionList("101010101");
		list1.addAll(list2);
	}
	
	/**
	 * @param source
	 * @return
	 */
	public static List<int[]> getPositionList(String source){
		List<int[]> listResult = new ArrayList<int[]>();
		String startFlag = "01";
		String endFlag = "10";
		List<Integer> listStart = new ArrayList<Integer>();
		List<Integer> listEnd = new ArrayList<Integer>();
		
		for (int i = 0; i < source.length()-1; i++) {
			String temp = source.substring(i, i+2);
			if (startFlag.equals(temp)) {
				listStart.add(i+1);
			}
			if (endFlag.equals(temp)) {
				listEnd.add(i);
			}
		}
		
		System.out.println(listStart.toString());
		System.out.println(listEnd.toString());
		
		if (listStart.size()>0) {
			if (listStart.get(0)>listEnd.get(0)) {
				listEnd.remove(0);
			}
			if (listStart.get(listStart.size()-1)>listEnd.get(listEnd.size()-1)) {
				listStart.remove(listStart.size()-1);
			}
			
			System.out.println(listStart.toString());
			System.out.println(listEnd.toString());
			
			for (int i = 0; i < listStart.size(); i++) {
				int[] ints = {listStart.get(i),listEnd.get(i)};
				listResult.add(ints);
			}
			
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < listResult.size(); i++) {
				sBuffer.append(Arrays.toString(listResult.get(i)));
			}
			System.out.println(sBuffer.toString());
		}
		return listResult;
	}
	
}
