package demo.everything.test;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[] message = {19, 0, 23, 104, 0, 14, 96, 0, 1, 1, 0, 22, 104, 70, 0, 70, 0, 104, -128, 19, 0, 23, 0, 0, 14, 96, 0, 1, 1, 0, 0, 2, 1, 3, 9, 24, 59, 12, 11, 10, 15, 0, 11, 87, 0, 100, 0, 50, 0, 70, 0, 40, 0, 60, 0, 80, 0, 0, 0, -16, 10, 24, 58, 15, 9, 10, 15, 0, 8, 87, 0, 15, 0, 25, 0, 35, 0, 20, 0, 40, 0, 60, 0, 0, 0, 120, 0, 1, 43, 22};
		long startTime = System.currentTimeMillis();
		int location = getLocation(message);
		byte[] next = new byte[message.length-location];
		System.arraycopy(message, location, next, 0, message.length-location);
		System.out.println(Arrays.toString(next));
		System.out.println("耗时："+(System.currentTimeMillis()-startTime));
	} 

	public static int getLocation(byte[] array){
		int location = 0;
		byte[] temp = new byte[7];
		for (int i = 0; i < array.length-7; i++) {
			System.arraycopy(array, i, temp, 0, 7);
			if (temp[0]==0x16&&temp[1]==0x68&&temp[2]==temp[4]&&temp[3]==temp[5]&&temp[6]==0x68) {
				location = i+1;
				break;
			}
		}
		return location;
	}
	
}
