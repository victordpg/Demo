package demo.everything.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericTest {

	public static void main(String[] args) {
		List<GenericBean> list1 = GenericBusiness.getList1();
		GenericBean genericBean = list1.get(0);
		List<Map<String, GenericBean>> list2 = GenericBusiness.getList2();		
		Map<String, GenericBean> list2map1 = list2.get(0);
		GenericBean gBean = list2map1.get("key1");
		
		Map<String, Object> map1 = GenericBusiness.getMap1();
		@SuppressWarnings("unchecked")
		Map<String, String> map3_1 = (Map<String, String>) map1.get("key3");
		
		Map<?, ?> map1_2 = (Map<?, ?>) map1.get("key3");
		String v3_value1 = (String) map1_2.get("v3_key1");
		String v3_value2 = (String) map1_2.get("v3_key2");
		String v3_value3 = (String) map1_2.get("v3_key3");
		
		Map<String, GenericBean> map2 = GenericBusiness.getMap2();
		GenericBean gb1 = (GenericBean) map2.get("key3");
	}

}

class GenericBusiness{
	public static List<GenericBean> getList1(){
		List<GenericBean> list = new ArrayList<GenericBean>();
		GenericBean gb1 = new GenericBean("a1", "a2");
		GenericBean gb2 = new GenericBean("b1", "b2");
		GenericBean gb3 = new GenericBean("c1", "c2");
		list.add(gb1);
		list.add(gb2);
		list.add(gb3);
		return list;
	}
	
	public static List<Map<String, GenericBean>> getList2(){
		List<Map<String, GenericBean>> list = new ArrayList<Map<String, GenericBean>>();
		list.add(GenericBusiness.getMap2());
		list.add(GenericBusiness.getMap2());
		list.add(GenericBusiness.getMap2());
		return list;
	}
	
	public static Map<String, Object> getMap1(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "this is value1");
		map.put("key2", "this is value2");
		Map<String, String> value3 = new HashMap<String, String>();
		value3.put("v3_key1", "v3 this is value1");
		value3.put("v3_key2", "v3 this is value2");
		value3.put("v3_key3", "v3 this is value3");
		map.put("key3", value3);
		return map;
	}
	
	public static Map<String, GenericBean> getMap2(){
		Map<String, GenericBean> map = new HashMap<String, GenericBean>();
		GenericBean gb1 = new GenericBean("a1", "a2");
		GenericBean gb2 = new GenericBean("b1", "b2");
		GenericBean gb3 = new GenericBean("c1", "c2");
		map.put("key1", gb1);
		map.put("key2", gb2);
		map.put("key3", gb3);
		return map;
	}	
}

class GenericBean{
	private String beanAttr1;
	private String beanAttr2;
	
	public GenericBean(String beanAttr1, String beanAttr2) {
		super();
		this.beanAttr1 = beanAttr1;
		this.beanAttr2 = beanAttr2;
	}
	
	public String getBeanAttr1() {
		return beanAttr1;
	}
	
	public void setBeanAttr1(String beanAttr1) {
		this.beanAttr1 = beanAttr1;
	}
	
	public String getBeanAttr2() {
		return beanAttr2;
	}
	
	public void setBeanAttr2(String beanAttr2) {
		this.beanAttr2 = beanAttr2;
	}
}

