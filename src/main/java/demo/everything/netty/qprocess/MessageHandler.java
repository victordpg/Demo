package demo.everything.netty.qprocess;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;


public class MessageHandler implements Runnable {
	private static final Logger logger = Logger.getLogger(MessageHandler.class);
	private BlockingQueue<MessageCtxBean> blockingQueue;

	public MessageHandler() {}

	public MessageHandler(BlockingQueue<MessageCtxBean> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		MessageCtxBean msgBean = null;

		while(true) {
			try {
				msgBean = blockingQueue.take();
			} catch (Exception e) {
				logger.error("解析报文时出现异常：", e);
			}
		}
	}
}
