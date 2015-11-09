package demo.everything.netty.qprocess;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;


public class MessageManager{
	private static final Logger logger = Logger.getLogger(MessageManager.class);
	
	private static final ExecutorService executor1 = Executors.newCachedThreadPool();
	private static final BlockingQueue<MessageCtxBean> messageQueue1 = new LinkedBlockingQueue<MessageCtxBean>(); //处理主动上传报文
	private static final ExecutorService executor2 = Executors.newCachedThreadPool();
	private static final BlockingQueue<MessageCtxBean> messageQueue2 = new LinkedBlockingQueue<MessageCtxBean>(); //处理手动采集报文

	public void start() {
		executor1.submit(new MessageHandler1(getMessageQueue1()));
		executor2.submit(new MessageHandler2(getMessageQueue2()));
		logger.info("The Queue Task processing message uploading started!");
	}

	public static BlockingQueue<MessageCtxBean> getMessageQueue1() {
		return messageQueue1;
	}
	
	public static BlockingQueue<MessageCtxBean> getMessageQueue2() {
		return messageQueue2;
	}

	public void destroy() {
		executor1.shutdown();
		executor2.shutdown();
		messageQueue1.clear();
		messageQueue2.clear();
	}
	
	public static void main(String[] args){
		MessageManager manager = new MessageManager();
		//启动Queue Task
		manager.start();
	}
	
}
