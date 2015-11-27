package demo.everything.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Other Thread's Manager.
 * 
 * @author Victor
 *
 */
public class QueueManager1 {
	private static final ExecutorService executor1 = Executors.newCachedThreadPool();
	private static final BlockingQueue<Object> blockingQueue1 = new LinkedBlockingQueue<Object>(); 
	
	public void start(){
		executor1.submit(new QueueHandler1(blockingQueue1));
		System.out.println("QueueManager1 started!");
	}

	public BlockingQueue<Object> getBlockingqueue1() {
		return blockingQueue1;
	}
}
