package demo.everything.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

//public class ServerHandler extends ChannelInboundHandlerAdapter {
public class ServerHandler extends SimpleChannelInboundHandler<byte[]> {
	//private static ChannelGroup channelGroup = new DefaultChannelGroup();
	//	final ChannelGroup channels = 
	//            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
		
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		String string = ctx.channel().remoteAddress().toString();
		System.out.println(string);
		NettyTCPServer.getMap().put(string, ctx.channel());
		
		ctx.channel().writeAndFlush("message from channelActive.");
	}

	@Override
	public void channelInactive(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("channelInactive");
		
		Map<String, Channel> map = NettyTCPServer.getMap();
		map.remove(arg0.channel());
		arg0.close();
	}

	/*@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof ByteBuf) {
			System.out.println("channelRead");
			ByteBuf buf = (ByteBuf) msg;
			byte[] dst = new byte[buf.readableBytes()];
			buf.readBytes(dst);
			System.out.println(new String(dst));
			
			byte[] bytes = {1,2,3,4,5,6,7};
			ctx.channel().writeAndFlush(bytes);
		}
	}*/

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg)
			throws Exception {
		// TODO Auto-generated method stub
		/*if (msg instanceof ByteBuf) {
			System.out.println("channelRead");
			ByteBuf buf = (ByteBuf) msg;
			byte[] dst = new byte[buf.readableBytes()];
			buf.readBytes(dst);
			System.out.println(new String(dst));
			
			byte[] bytes = {1,2,3,4,5,6,7};
			ctx.channel().writeAndFlush(bytes);
		}*/
		System.out.println("读到信息："+HexConversion.str2HexStr(new String(msg)));
		
		byte[] bytes = {1,2,3,4,5,6,7};
		ctx.channel().writeAndFlush(bytes);
	}

	/*@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		// TODO Auto-generated method stub
		ctx.channel().writeAndFlush("THis is messge");
		if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                读超时
                System.out.println("READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                写超时   
                System.out.println("WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                总超时
                System.out.println("ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }
	}*/

}
