package demo.everything.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * tcp server
 * 
 * @author DIAOPG
 * @date 2015年8月18日
 */
public class TCP_Server_Test{
	public static void main(String[] args){
		try {
			new Thread(new TCPServer2(9999)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class TCPServer2 implements Runnable {
	private ServerSocket serverSocket;
	private Socket clntSock; 
	
	public TCPServer2(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		System.out.println("TCP SERVER STARTED!");
		while (true) {
			try {
				clntSock = serverSocket.accept();
				handleClientSockets(clntSock);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * 模拟手动采集
	 * @param clntSock
	 * @author DIAOPG
	 * @date 2015年8月18日
	 */
	public static void handleClientSockets(Socket clntSock){
		InputStream is;
		byte[] byteIn = new byte[64];
		
		try {
			 is = clntSock.getInputStream();
			 DataInputStream dis = new DataInputStream(is);  
			 dis.read(byteIn);
             System.out.println("TCP SERVER接收到的数据为：\n"+Arrays.toString(byteIn));  
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			try {
				clntSock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
