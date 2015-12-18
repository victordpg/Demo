package demo.everything.disruptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lmax.disruptor.EventHandler;

public class LongEntityHandler implements EventHandler<LongEntity> {
	List<Long> list = new ArrayList<Long>();
	static final int BATCH_SIZE = 20;
	static final int BATCH_SIZE_END = 1024;
	
	@Override
	public void onEvent(LongEntity entity, long sequence, boolean endOfBatch) throws Exception {
		// TODO Auto-generated method stub
		list.add(entity.getLong1());
		if ((sequence + 1) % BATCH_SIZE==0) {
			System.out.println(Arrays.toString(list.toArray()));
			list.clear();
		}
		
		/*System.out.println(arg1);
		System.out.println("LongEntity: "+entity.getLong1());*/
		
		if(endOfBatch && sequence!=0){
			System.out.println(Arrays.toString(list.toArray()));
			list.clear();
		}
	}

}
