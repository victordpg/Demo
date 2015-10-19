package demo.everything.test;

import java.util.ArrayList;
import java.util.List;


public class Test5 {
	private List<Object> list;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Test5  test5 = new Test5();
		List<Object> valueList = new ArrayList<Object>();
		System.out.println(valueList.size());
		valueList.add("123");
		System.out.println(valueList.size());
		test5.setList(valueList);;
		valueList = new ArrayList<Object>();
		System.out.println(test5.getList().get(0));*/
		
		List<Object> valueList = new ArrayList<Object>();
		valueList.add("123");
		String string = "456";
		Object object = new Object();
		object = string;
		object = valueList;
		System.out.println(object);
		
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

}
