package demo.everything.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * Thread ASynchronized test<p>
 * 
 * CASE 1: 2 thread don't keep the data consistency in the application.<br>
 * CASE 2: keep the data to be consistent.
 * 
 * @author Victor
 *
 */
public class ThreadASynchronized {
	public static Map<String, String> map = new ConcurrentHashMap<String, String>();
	
	public static void main(String[] args) {
		QueueManager1 manager1 = new QueueManager1();
		manager1.start(); //start the other thread.
		try {
			manager1.getBlockingqueue1().put("The value put to the QueueHandler1"); //
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean flag = false;
		System.out.println("ThreadASynchronized log1...");
		
		/*
		 * CASE 1:
		 * The flag won't be true, because the map hasn't get the key-valued put yet.
		 * In QueueHandler1 (the other thread) will take sometime (2 seconds) to put the key-value.
		 */
		if (map.containsKey("key1")) {
			flag = true;
		}
		
		/*
		 * CASE 2:
		 * Keep waiting till get the key.
		 */
		/*long startTime = System.currentTimeMillis();
		while (true) {
			if ((System.currentTimeMillis()-startTime)/1000>3) {
				break;
			}
			if (map.containsKey("key1")) {
				flag = true;
				break;
			}
		}*/
		System.out.println("ThreadASynchronized log2...");
		System.out.println(flag);
	}

}
