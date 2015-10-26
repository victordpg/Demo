package demo.everything.rxnetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.ByteArrayPipelineConfigurator;
import io.reactivex.netty.server.RxServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RxTCPServer {
	private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
	
	public RxServer<byte[], byte[]> createServer() {
		/*RxServer<byte[], byte[]> server = RxNetty.createTcpServer(9898,
				new ByteArrayPipelineConfigurator(),
				new RxConnHandler());*/
		RxServer<byte[], byte[]> server = RxNetty.createTcpServer(9898,
				new ByteArrayPipelineConfigurator(){
				    @Override
				    public void configureNewPipeline(ChannelPipeline pipeline) {
				    	pipeline.addLast(new IdleStateHandler(0,0,5));
				        pipeline.addLast(new ChannelDuplexHandler() {
				        	@Override
				            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				                boolean handled = false;
		
				                if (ByteBuf.class.isAssignableFrom(msg.getClass())) {
				                    ByteBuf byteBuf = (ByteBuf) msg;
				                    if (byteBuf.isReadable()) {
				                        int readableBytes = byteBuf.readableBytes();
				                        byte[] msgToPass = new byte[readableBytes];
				                        byteBuf.readBytes(msgToPass);
				                        byteBuf.release(); 
				                        handled = true;
				                        ctx.fireChannelRead(msgToPass);
				                    }
				                }
				                if (!handled) {
				                    super.channelRead(ctx, msg);
				                }
				            }
		
				            @Override
				            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
				                if (msg instanceof byte[]) {
				                    byte[] msgAsBytes = (byte[]) msg;
				                    ByteBuf byteBuf = ctx.alloc().buffer(msgAsBytes.length).writeBytes(msgAsBytes);
				                    super.write(ctx, byteBuf, promise);
				                } else {
				                    super.write(ctx, msg, promise); // pass-through
				                }
				            }
				            
				        	@Override
				        	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
				        			throws Exception {
				        		if (evt instanceof IdleStateEvent) {
				                    IdleStateEvent event = (IdleStateEvent) evt;
				                    if (event.state() == IdleState.READER_IDLE) {
				                        System.out.println("READER_IDLE 读超时");
				                        ctx.disconnect();
				                    } else if (event.state() == IdleState.WRITER_IDLE) {
				                        System.out.println("WRITER_IDLE 写超时");
				                        ctx.disconnect();
				                    } else if (event.state() == IdleState.ALL_IDLE) {
				                        System.out.println("ALL_IDLE 总超时");
				                        ctx.disconnect();
				                    }
				                }
				        		ctx.fireChannelRead(evt);
				        	}

				        });
				    }
				},
				new RxConnHandler());
		return server;
	}

	public static Map<String, Channel> getMap() {
		return map;
	}

	public static void setMap(Map<String, Channel> map) {
		RxTCPServer.map = map;
	}	
	
	public static void main(String args[]){
		RxTCPServer rxTcpServer = new RxTCPServer();
		rxTcpServer.createServer().startAndWait();
	}

}
