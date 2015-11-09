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
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author DIAOPG
 * @date 2015年11月9日
 */
public class NettyTCPServer{
	private int port;
	private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>(); //用于存放客户端连接的channel信息
	private static Map<String, byte[]> messageMap = new ConcurrentHashMap<String, byte[]>(); //用于存储手动采集到的报文
	
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
                        	// Decoder
                        	//ch.pipeline().addLast("bytesDecoder", new ByteArrayDecoder());
                        	ch.pipeline().addLast("bytesDecoder", new Decoder4TCP()); //使用自定义的解码器（此解码器有拆包功能）

                            // Encoder
                        	ch.pipeline().addLast("bytesEncoder", new ByteArrayEncoder());

                        	ch.pipeline().addLast(new OutBoundHandler());
                            ch.pipeline().addLast(new IdleStateHandler(0,0,10), new InBoundHandler());
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
		new NettyTCPServer(10000).start();
		//new ClientThread().run();
	}

	/**
	 * @return the messageMap
	 */
	public static Map<String, byte[]> getMessageMap() {
		return messageMap;
	}

	/**
	 * @param messageMap the messageMap to set
	 */
	public static void setMessageMap(Map<String, byte[]> messageMap) {
		NettyTCPServer.messageMap = messageMap;
	}

	/**
	 * @return the map
	 */
	public static Map<String, Channel> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public static void setMap(Map<String, Channel> map) {
		NettyTCPServer.map = map;
	}
}
