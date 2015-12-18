package demo.everything.disruptor;

import com.lmax.disruptor.RingBuffer;

public class LongEntityProducer {
	private final RingBuffer<LongEntity> ringBuffer;

	public LongEntityProducer(RingBuffer<LongEntity> ringBuffer) {
		super();
		this.ringBuffer = ringBuffer;
	}

	public void onData(long long1){
		long sequence = ringBuffer.next();
		try {
			LongEntity entity = ringBuffer.get(sequence);
			entity.setLong1(long1);
		} finally{
			ringBuffer.publish(sequence);
		}
	}
}
