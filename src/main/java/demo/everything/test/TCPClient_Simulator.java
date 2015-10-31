package demo.everything.test;

import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 天津项目TCP模拟器（该模拟器模拟设备，充当TCP Client），包含两部分<br>
 * 1.主动上传模拟器<br>
 * 2.手动采集模拟器
 * @author DIAOPG
 * @date 2015年10月14日
 */
public class TCPClient_Simulator {

	public static void main(String[] args) {
		try {
			String host = "10.1.5.197"; //主机IP
			int port = 10000; //主机Port
			Socket socket = new Socket(host, port);
			
			//1.启动主动上传模拟器
			//主动上传模拟器需要设置发送时间间隔，所以有下面启动方式。
			ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(5);
			Thread thread1 = new Thread(new TCPClient_Initiative(socket));
			exec.scheduleAtFixedRate(thread1, 3000, 20000, TimeUnit.MILLISECONDS);
			System.out.println("主动上传模拟器启动！");
			
			//2. 启动手动采集模拟器
			new Thread(new TCPClient_Manual(socket)).run();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
