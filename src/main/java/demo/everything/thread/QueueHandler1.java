package demo.everything.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The other Thread.
 * 
 * @author Victor
 *
 */
public class QueueHandler1 implements Runnable {

	private BlockingQueue<Object> blockingQueue1 = new LinkedBlockingQueue<Object>();
	
	public QueueHandler1(BlockingQueue<Object> blockingQueue){
		this.blockingQueue1 = blockingQueue;
	}
	
	@Override
	public void run() {
		String valueString;
		try {
			valueString = (String) blockingQueue1.take();
			System.out.println("QueueHandler1 log1...");
			long startTime = System.currentTimeMillis();
			
			while (true) {
				// put the key-value 2 seconds later.
				if ((System.currentTimeMillis()-startTime)/1000>2) {
					ThreadASynchronized.map.put("key1",valueString);
					System.out.println("QueueHandler1 log2...");
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
