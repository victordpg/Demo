package demo.everything.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyTCPServer{
	private int port;
	private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
	
	public NettyTCPServer(int port){
		this.port = port;
	}
	
	public NettyTCPServer(){
	}
	
	public void start() throws Exception{
		// Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                        	 // Decoders
                        	//ch.pipeline().addLast("frameDecoder",
                        	//                  new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
                        	ch.pipeline().addLast("bytesDecoder",
                        	                  new ByteArrayDecoder());

                        	 // Encoder
                        	 //ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
                        	 ch.pipeline().addLast("bytesEncoder", new ByteArrayEncoder());

                        	ch.pipeline().addLast(new OutBoundHandler());
                            ch.pipeline().addLast(new IdleStateHandler(0,0,10), new ServerHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		new NettyTCPServer(999).start();
		//new ClientThread().run();
	}

	public static Map<String, Channel> getMap() {
		return map;
	}

	public static void setMap(Map<String, Channel> map) {
		NettyTCPServer.map = map;
	}

}
/*
class ClientThread implements Runnable {
	public void run() {
		System.out.println("enter clientThread");
		Channel channel = NettyTCPServer.getMap().get(NettyTCPServer.getMap().keySet().iterator().next());
		ChannelFuture cfChannelFuture = channel.writeAndFlush("This is message from server!\n");
	}
}*/
