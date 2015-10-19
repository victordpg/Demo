package demo.everything.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP Server
 * 
 * @author DIAOPG
 * @date 2015年8月18日
 */
public class TCPServer{
	public static void main(String[] args){
		try {
			new Thread(new TCPServer_Inner(7777)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class TCPServer_Inner implements Runnable {
	private ServerSocket serverSocket;
	private Socket clntSock; 
	
	public TCPServer_Inner(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		System.out.println("TCP SERVER STARTED!");
		while (true) {
			try {
				clntSock = serverSocket.accept();
				System.out.println(clntSock.getRemoteSocketAddress());
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
	/**
	 * @param clntSock
	 */
	public static void handleClientSockets(Socket clntSock){
		InputStream is;
		try {
			 is = clntSock.getInputStream();
			 DataInputStream dis = new DataInputStream(is);  
			 //string = dis.readUTF();
			 byte[] bytes = new byte[is.available()];
			 dis.read(bytes);
             System.out.println("TCP SERVER接收到的数据为：\n"+bytes);  
             
             OutputStream os = clntSock.getOutputStream();  
             DataOutputStream dos = new DataOutputStream(os); 
             //dos.writeChars("hello world!");
             dos.writeUTF("This message from TCP Server!\nHello, Client.");
             dos.flush();
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
		/*	try {
				clntSock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}
}
