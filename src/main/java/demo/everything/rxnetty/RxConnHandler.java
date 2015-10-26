package demo.everything.rxnetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.reactivex.netty.channel.ConnectionHandler;
import io.reactivex.netty.channel.ObservableConnection;

import java.net.SocketAddress;
import java.util.Arrays;

import rx.Observable;
import rx.functions.Func1;


public class RxConnHandler implements ConnectionHandler<byte[], byte[]> {

	@Override
	public Observable<Void> handle(
			ObservableConnection<byte[], byte[]> newConnection) {
		// TODO Auto-generated method stub
		final Channel channel = newConnection.getChannel();
		
		SocketAddress address = channel.remoteAddress();
		RxTCPServer.getMap().put(address.toString(), channel);
		System.out.println("in handle ......");
		
		return newConnection.getInput().flatMap(new Func1<byte[], Observable<Void>>(){
			@Override
			public Observable<Void> call(byte[] arg0) {
				//channel.writeAndFlush("Hello Client, this message from Server!".getBytes());
				System.out.println("enter addListener");
				String key = RxTCPServer.getMap().keySet().iterator().next();
				RxTCPServer.getMap().get(key).writeAndFlush("This message from Map Channel!".getBytes()).addListener(new ChannelFutureListener(){  
		            @Override  
		            public void operationComplete(ChannelFuture future)  
		                    throws Exception {  
		                // TODO Auto-generated method stub  
		            	System.out.println("enter addListener");
		                future.channel().read();
		            }  
		        });
				System.out.println("in call ......");
				System.out.println(Arrays.toString(arg0));
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}
