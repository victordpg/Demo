package demo.everything.test;

public class ByteTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[] bytes = {0x03,(byte)0xe8};
		byte[] bytes2 = {(byte)0xe8,0x03};
		System.out.println(getValue(bytes2));
	}

	public static Object getValue(byte[] bytes) {
		// TODO
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
}
