package demo.everything.primitive.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class _TestCollection {

	/**
	 * 实现List排序的两种方法
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ListBean> list = _TestCollection.getList();
		
		//方法一：集合中的元素T实现Comparable接口来实现自然排序的目的
		Collections.sort(list);
		System.out.println(list.toString());
		
		//方法二：继承Comparator类
		Collections.sort(list, new Comparator<ListBean>() {
			@Override
			public int compare(ListBean o1, ListBean o2) {
				// TODO Auto-generated method stub
				int id1 = o1.getId();
				int id2 = o2.getId();
				if (id1>id2) {
					return 1;
				}
				if (id1<id2) {
					return -1;
				}
				return 0;
			}
		});
		System.out.println(list.toString());
	}
	
	public static List<ListBean> getList(){
		List<ListBean> list = new ArrayList<ListBean>();
		ListBean bean1 = new ListBean(12, "Allis");
		ListBean bean2 = new ListBean(9, "Cart");
		ListBean bean3 = new ListBean(4, "Vic");
		ListBean bean4 = new ListBean(23, "Ben");
		ListBean bean5 = new ListBean(3, "Tomas");
		ListBean bean6 = new ListBean(33, null);
		//ListBean bean7 = null;

		list.add(bean1);
		list.add(bean2);
		list.add(bean3);
		list.add(bean4);
		list.add(bean5);
		list.add(bean6);
		//list.add(bean7);
		return list;
	}
}
