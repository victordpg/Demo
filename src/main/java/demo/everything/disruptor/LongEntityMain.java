package demo.everything.disruptor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class LongEntityMain {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//Executor executor = Executors.newCachedThreadPool();
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		LongEntityFactory factory = new LongEntityFactory();
		int bufferSize = 1024;
		//Disruptor<LongEntity> disruptor = new Disruptor<LongEntity>(factory, bufferSize, executor);
		//using following constructor to create new Disruptor since disruptor 3.x.x 
		Disruptor<LongEntity> disruptor = new Disruptor<LongEntity>(factory, bufferSize, threadFactory);
		disruptor.handleEventsWith(new LongEntityHandler());
		disruptor.start();
		
		RingBuffer<LongEntity> ringBuffer = disruptor.getRingBuffer();
		LongEntityProducer producer = new LongEntityProducer(ringBuffer);
		
		for (long l = 1; true; l++)
        {
			if (l<25) {
	            producer.onData(l);
	            //Thread.sleep(100);
			} else {
				break;
			}
        }
	}

}
