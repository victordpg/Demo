package demo.everything.netty;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * 
 * @author DIAOPG
 * @date 2015年11月4日
 */
//@Component
public class MessageManager{
	private static final Logger logger = Logger.getLogger(MessageManager.class);
	
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	//private static final BlockingQueue<MessageCtxBean> messageQueue = Queues.newLinkedBlockingQueue();

	public void start() {
		//executor.submit(new MessageHandler(getMessagequeue()));

		/*while(true) {
			MessageCtxBean msgBean = null;
			
			try {
				Map<String, MessageCtxBean>  map = InBoundHandler.getMap2QProcess();
				if (map.containsKey("Key_map2QProcess")) {
					msgBean = InBoundHandler.getMap2QProcess().get("Key_map2QProcess");
					messageQueue.put(msgBean);
				} 
			} catch (Exception e) {
				logger.error("接收报文预处理时发生异常：", e);
			}
		}*/
	}

	/**
	 * @return the messagequeue
	 */
	public static BlockingQueue<MessageCtxBean> getMessagequeue() {
		//return messageQueue;
		return null;
	}

	/*public void destroy() {
		executor.shutdown();
		messageQueue.clear();
	}*/
}
