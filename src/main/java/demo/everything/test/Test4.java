package demo.everything.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		collectUpwardBytesEasy(list.iterator());
	}
    
	public static void collectUpwardBytesEasy(Iterator<String> dataUpward) {
		while (dataUpward.hasNext()) {
			System.out.println(dataUpward.next());
			resultValue(dataUpward);
		}
	}
	
	public static void resultValue(Iterator<String> dataUpward){
			System.out.println("dataUpward hasNext "+dataUpward.next());
	}
}
