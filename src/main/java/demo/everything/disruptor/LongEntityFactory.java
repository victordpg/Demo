package demo.everything.disruptor;

import com.lmax.disruptor.EventFactory;

public class LongEntityFactory implements EventFactory<LongEntity>
{
	public LongEntity newInstance(){
		return new LongEntity();
	}
}
