package demo.everything.test;
import java.util.Arrays;

public class Test6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String string = "[{\"FQN\":\"P1\",\"METRIC_LIST\":[\"F82\",\"F83\",\"84\"]},{\"FQN\":\"P2\",\"METRIC_LIST\":[\"F85\",\"F86\",\"F87\"]},{\"FQN\":\"P3\",\"METRIC_LIST\":[\"F82\",\"F83\",\"84\"]}]}";
		System.out.println(string);
		System.out.println(getCorrectionString(string));*/
		
		System.out.println(Arrays.toString(intToDBytes(0, true)));
		System.out.println(Arrays.toString(intToDBytes(185, false)));
	}

	private static String getCorrectionString(String str){
		String result,result2="";
		
		int fqnCount = 0;
		while(str.indexOf("FQN")!=-1){
			fqnCount++;
            str=str.substring("FQN".length()+1);
        }
		
		for (int i = 0; i < fqnCount; i++) {
			int start = str.indexOf("\"[");
			int end = str.indexOf("]\"");
			if (start==-1 || end==-1) {
				return str;
			}
			result = str.substring(0, start) + str.substring(start+1, end+1)+str.substring(end+2);
			
			int start2 = result.indexOf("\"{");
			int end2 = result.indexOf("}\"");
			if (start2==-1 || end2==-1) {
				return str;
			}
			result2 = result.substring(0, start2) + result.substring(start2+1, end2+1)+result.substring(end2+2);
		}
		
		return result2.replaceAll("\\\\", "");
	}
	
	static byte[] intToDBytes(int value, boolean isPn) {
        assert value <= 2040: "DA或DT不能大于2040";
        byte[] result = new byte[2];
        int mod = (value - 1) % 8;
        result[0] = (byte) (1 << mod);
        int quotient = (value - 1) / 8;
        result[1] = (byte) (isPn ? quotient + 1 : quotient); // Fn的高字节从0开始，Pn从1开始
        return result;
    }
	
}
