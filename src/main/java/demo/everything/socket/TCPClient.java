package demo.everything.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

	public static void main(String[] args) {
		try {  
            @SuppressWarnings("resource")
			//Socket socket = new Socket("10.0.65.244", 999);  
            Socket socket = new Socket("127.0.0.1", 999);
            // 向服务器端发送数据  
            /*OutputStream os =  socket.getOutputStream();  
            DataOutputStream bos = new DataOutputStream(os);  
            bos.writeUTF("This message from client!");  
            bos.flush(); */ 

            // 接收服务器端数据   
            InputStream is = socket.getInputStream();  
            DataInputStream dis = new DataInputStream(is);  
            System.out.println(dis.read());  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}
}
