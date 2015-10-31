package demo.everything.nettyserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
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

import com.neusoft.aplus.common.config.NettyClientConf;
import com.neusoft.aplus.common.util.SpringUtil;

/**
 * @author DIAOPG
 * @date 2015年10月12日
 */
public class TCPServerNetty{
	private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
	private static Map<String, byte[]> messageMap = new ConcurrentHashMap<String, byte[]>();
    private static final int TCP_SERVER_PORT;
	
    static {
        NettyClientConf exConf = SpringUtil.getBean(NettyClientConf.class);
        TCP_SERVER_PORT = exConf.getTCPPort();
    }
	
	public TCPServerNetty(){}
	
	public void start() throws Exception{
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
	                        //ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
	                        ch.pipeline().addLast("bytesDecoder", new ByteArrayDecoder());
	                       	// Encoder
	                       	//ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
	                       	ch.pipeline().addLast("bytesEncoder", new ByteArrayEncoder());                        	
                        	ch.pipeline().addLast(new OutBoundHandler());
                            ch.pipeline().addLast(new IdleStateHandler(0,0,1800), new InBoundHandler());
                        }
                    });

            b.bind(TCP_SERVER_PORT);
            // Start the server.
            //ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            //f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shut down all event loops to terminate all threads.
            //bossGroup.shutdownGracefully();
            //workerGroup.shutdownGracefully();
        }
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		new TCPServerNetty().start();
	}

	public static Map<String, Channel> getMap() {
		return map;
	}

	public static void setMap(Map<String, Channel> map) {
		TCPServerNetty.map = map;
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
		TCPServerNetty.messageMap = messageMap;
	}
}
