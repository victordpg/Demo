package demo.everything.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TCP协议主动上传模拟器，模拟多帧主动上传
 * 
 * @author DIAOPG
 * @date 2015年9月8日
 */
public class TCP_Server_Initiative_MultiFrame {

	public static void main(String[] args) {
		try {  
			Socket clientSocket = new Socket("10.1.5.197", 10000);  
			
			ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
			
			System.out.println("TCP协议主动上传模拟器已启动！");

			Thread thread1 = new Thread(new ClientSocketsThread_2Frame(clientSocket));
			exec.scheduleAtFixedRate(thread1, 1000, 10000, TimeUnit.MILLISECONDS); //每隔10秒执行一次
			
			//Thread thread2 = new Thread(new ClientSocketsThread_3Frame(clientSocket));
			//exec.scheduleAtFixedRate(thread2, 1000, 10000, TimeUnit.MILLISECONDS); //每隔10秒执行一次
			
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}

/**
 * 多帧主动上传 两帧
 * 
 * @author DIAOPG
 * @date 2015年9月8日
 */
class ClientSocketsThread_2Frame  implements Runnable  {
	private Socket clntSock; // Socket connect to client
	
	public ClientSocketsThread_2Frame(Socket clntSock) {
		this.clntSock = clntSock;
	}

	@Override
	public void run() {
		handleClientSockets2Frame(clntSock);
	}	
	
	/**
	 * 模拟两帧报文主动上传
	 * @param clientSocket
	 * @author DIAOPG
	 * @date 2015年9月8日
	 */
	public static void handleClientSockets2Frame(Socket clientSocket){
		byte[] byteArray1 = getF81_Frame1();
		byte[] byteArray2 = getF81_Frame2();
		
		try {
			OutputStream os = clientSocket.getOutputStream();  
			DataOutputStream dos = new DataOutputStream(os); 
			
			dos.flush();
			
			dos.write(byteArray1); //第一帧
			dos.flush();
			
			Thread.sleep(3000);

			dos.write(byteArray2); //第二帧
			dos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			try {
				if(!clientSocket.isConnected())
					clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * F81类指标，两帧模拟的第一帧
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月8日
	 */
	private static byte[] getF81_Frame1() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[64];
		bytes[0] = 0x68;
		bytes[1] = 56;
		bytes[2] = 56; //长度56
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 44;
		bytes[6] = 0;
		bytes[7] = 23;
		bytes[8] = 0;
		bytes[9] = 3;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		
		//帧序列（首帧、序列1）:0100 0001
		bytes[11] = 0x47;
		
		//数据单元标识{P1,F81}：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x0a;
		
		//数据单元：
		//曲线类数据时标Td_c
		bytes[16] = 0x01; //秒
		bytes[17] = 0x0f; //分
		bytes[18] = 0x09; //时
		bytes[19] = 25; //日
		bytes[20] = 0x28; //星期二8月。高四位为星期，取值范围：0~7, 0：无效，1～7依次表示星期一至星期日	低四位为月，取值范围：十进制数1~12
		bytes[21] = 0x0f; //年
		bytes[22] = 0x03; //冻结密度为3，即60分钟冻结一次
		bytes[23] = 0x18; //此时冻结点数为24
		
		//共24个有功功率
		//有功功率1
		bytes[24] = 0x00;
		bytes[25] = 0x00;
		//有功功率2		
		bytes[26] = 0x00;
		bytes[27] = 0x20;
		//有功功率3		
		bytes[28] = 0x00;
		bytes[29] = 0x00;
		//有功功率4		
		bytes[30] = 0x00;
		bytes[31] = 0x0d;
		//有功功率5		
		bytes[32] = 0x00;
		bytes[33] = 0x00;
		//有功功率6
		bytes[34] = 0x00;
		bytes[35] = 0x0f;	
		//有功功率7	    
		bytes[36] = 0x00;
		bytes[37] = 0x00;
		//有功功率8
		bytes[38] = 0x00;
		bytes[39] = 0x0d;
		//有功功率9		
		bytes[40] = 0x00;
		bytes[41] = 0x00;
		//有功功率10		
		bytes[42] = 0x00;
		bytes[43] = 0x05;		
		//有功功率11		
		bytes[44] = 0x00;
		bytes[45] = 0x00;
		//有功功率12		
		bytes[46] = 0x00;
		bytes[47] = 0x06;	
		
		//有功功率13
		bytes[48] = 0x00;
		bytes[49] = 0x00;
		//有功功率14		
		bytes[50] = 0x00;
		bytes[51] = 0x20;
		//有功功率15		
		bytes[52] = 0x00;
		bytes[53] = 0x00;
		//有功功率16		
		bytes[54] = 0x00;
		bytes[55] = 0x0d;
		//有功功率17		
		bytes[56] = 0x00;
		bytes[57] = 0x00;
		//有功功率18
		bytes[58] = 0x00;
		bytes[59] = 0x0f;	
		
		
		//附加信息AUX
		bytes[60] = 0x00;
		bytes[61] = 0x01;
		
		//帧校验和
		bytes[62] = 0x2b;
		
		bytes[63] = 0x16;
		return bytes;
	}

	/**
	 * F81类指标，两帧模拟的第二帧
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月8日
	 */
	private static byte[] getF81_Frame2() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 24;
		bytes[2] = 24; //长度24
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 44;
		bytes[6] = 0;
		bytes[7] = 23;
		bytes[8] = 0;
		bytes[9] = 3;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		//帧序列（末帧、序列2）：0010 0010
		bytes[11] = 0x28;
		
		
		//数据单元标识{P1,F81}：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x0a;
		
		//数据单元：
		//共24个有功功率，剩余6个
		//有功功率19
		bytes[16] = 0x00;
		bytes[17] = 0x00;
		//有功功率20		
		bytes[18] = 0x00;
		bytes[19] = 0x20;
		//有功功率21		
		bytes[20] = 0x00;
		bytes[21] = 0x00;
		//有功功率22		
		bytes[22] = 0x00;
		bytes[23] = 0x0d;
		//有功功率23		
		bytes[24] = 0x00;
		bytes[25] = 0x00;
		//有功功率24
		bytes[26] = 0x00;
		bytes[27] = 0x0f;	

		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		
		bytes[31] = 0x16;
		return bytes;
	}
	
}

/**
 * 多帧主动上传 三帧
 * 
 * @author DIAOPG
 * @date 2015年9月8日
 */
class ClientSocketsThread_3Frame  implements Runnable  {
	private Socket clntSock; // Socket connect to client
	
	public ClientSocketsThread_3Frame(Socket clntSock) {
		this.clntSock = clntSock;
	}

	@Override
	public void run() {
		handleClientSockets3Frame(clntSock);
	}	
	
	/**
	 * 模拟三帧报文主动上传
	 * @param clientSocket
	 * @author DIAOPG
	 * @date 2015年9月8日
	 */
	public static void handleClientSockets3Frame(Socket clientSocket){
		byte[] byteArray1 = getF81_3Frame1();
		byte[] byteArray2 = getF81_3Frame2();
		byte[] byteArray3 = getF81_3Frame3();
		
		try {
			OutputStream os = clientSocket.getOutputStream();  
			DataOutputStream dos = new DataOutputStream(os); 
			
			dos.flush();
			
			dos.write(byteArray1); //第一帧
			dos.flush();
			
			Thread.sleep(2000);

			dos.write(byteArray2); //第二帧
			dos.flush();
			
			Thread.sleep(2000);

			dos.write(byteArray3); //第三帧
			dos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			try {
				if(!clientSocket.isConnected())
					clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * F81类指标，三帧模拟的第一帧
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月8日
	 */
	private static byte[] getF81_3Frame1() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[64];
		bytes[0] = 0x68;
		bytes[1] = 56;
		bytes[2] = 56; //长度56
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0;
		bytes[7] = 23;
		bytes[8] = 0;
		bytes[9] = 3;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		
		//帧序列（首帧、序列1）:0100 0001
		bytes[11] = 0x41;
		
		//数据单元标识{P1,F81}：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x0a;
		
		//数据单元：
		//曲线类数据时标Td_c
		bytes[16] = 0x01; //秒
		bytes[17] = 0x0f; //分
		bytes[18] = 0x09; //时
		bytes[19] = 25; //日
		bytes[20] = 0x28; //星期二8月。高四位为星期，取值范围：0~7, 0：无效，1～7依次表示星期一至星期日	低四位为月，取值范围：十进制数1~12
		bytes[21] = 0x0f; //年
		bytes[22] = 0x02; //冻结密度为2，即30分钟冻结一次
		bytes[23] = 48; //此时冻结点数为24
		
		//共48个有功功率，前18个
		//有功功率1
		bytes[24] = 0x00;
		bytes[25] = 1;
		//有功功率2		
		bytes[26] = 0x00;
		bytes[27] = 0x20;
		//有功功率3		
		bytes[28] = 0x00;
		bytes[29] = 2;
		//有功功率4		
		bytes[30] = 0x00;
		bytes[31] = 0x0d;
		//有功功率5		
		bytes[32] = 0x00;
		bytes[33] = 0x00;
		//有功功率6
		bytes[34] = 0x00;
		bytes[35] = 0x0f;	
		//有功功率7	    
		bytes[36] = 0x00;
		bytes[37] = 0x00;
		//有功功率8
		bytes[38] = 0x00;
		bytes[39] = 0x0d;
		//有功功率9		
		bytes[40] = 0x00;
		bytes[41] = 0x00;
		//有功功率10		
		bytes[42] = 0x00;
		bytes[43] = 0x05;		
		//有功功率11		
		bytes[44] = 0x00;
		bytes[45] = 0x00;
		//有功功率12		
		bytes[46] = 0x00;
		bytes[47] = 0x06;	
		//有功功率13
		bytes[48] = 0x00;
		bytes[49] = 0x00;
		//有功功率14		
		bytes[50] = 0x00;
		bytes[51] = 0x20;
		//有功功率15		
		bytes[52] = 0x00;
		bytes[53] = 0x00;
		//有功功率16		
		bytes[54] = 0x00;
		bytes[55] = 0x0d;
		//有功功率17		
		bytes[56] = 0x00;
		bytes[57] = 17;
		//有功功率18
		bytes[58] = 0x00;
		bytes[59] = 18;	
		
		
		//附加信息AUX
		bytes[60] = 0x00;
		bytes[61] = 0x01;
		
		//帧校验和
		bytes[62] = 0x2b;
		
		bytes[63] = 0x16;
		return bytes;
	}

	/**
	 * F81类指标 三帧模拟的第二帧
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月8日
	 */
	private static byte[] getF81_3Frame2() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[64];
		bytes[0] = 0x68;
		bytes[1] = 56;
		bytes[2] = 56; //长度56
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0;
		bytes[7] = 23;
		bytes[8] = 0;
		bytes[9] = 3;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		//帧序列（中间帧、序列2）：0000 0010
		bytes[11] = 0x02;
		
		
		//数据单元标识{P1,F81}：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x0a;
		
		//数据单元：
		//共48个有功功率，中间22个
		//有功功率19
		bytes[16] = 0x00;
		bytes[17] = 19;
		//有功功率20		
		bytes[18] = 0x00;
		bytes[19] = 20;
		//有功功率21		
		bytes[20] = 0x00;
		bytes[21] = 0x00;
		//有功功率22		
		bytes[22] = 0x00;
		bytes[23] = 0x0d;
		//有功功率23		
		bytes[24] = 0x00;
		bytes[25] = 0x00;
		//有功功率24
		bytes[26] = 0x00;
		bytes[27] = 0x0f;	
		//有功功率25		
		bytes[28] = 0x00;
		bytes[29] = 0x00;
		//有功功率26		
		bytes[30] = 0x00;
		bytes[31] = 0x0d;
		//有功功率27		
		bytes[32] = 0x00;
		bytes[33] = 0x00;
		//有功功率28
		bytes[34] = 0x00;
		bytes[35] = 0x0f;	
		//有功功率29	    
		bytes[36] = 0x00;
		bytes[37] = 0x00;
		//有功功率30
		bytes[38] = 0x00;
		bytes[39] = 0x0d;
		//有功功率31		
		bytes[40] = 0x00;
		bytes[41] = 0x00;
		//有功功率32	
		bytes[42] = 0x00;
		bytes[43] = 0x05;		
		//有功功率33		
		bytes[44] = 0x00;
		bytes[45] = 0x00;
		//有功功率34	
		bytes[46] = 0x00;
		bytes[47] = 0x06;	
		//有功功率35
		bytes[48] = 0x00;
		bytes[49] = 0x00;
		//有功功率36	
		bytes[50] = 0x00;
		bytes[51] = 0x20;
		//有功功率37	
		bytes[52] = 0x00;
		bytes[53] = 0x00;
		//有功功率38	
		bytes[54] = 0x00;
		bytes[55] = 0x0d;
		//有功功率39		
		bytes[56] = 0x00;
		bytes[57] = 39;
		//有功功率40
		bytes[58] = 0x00;
		bytes[59] = 40;	
		
		
		//附加信息AUX
		bytes[60] = 0x00;
		bytes[61] = 0x01;
		
		//帧校验和
		bytes[62] = 0x2b;
		
		bytes[63] = 0x16;
		return bytes;
	}
	
	/**
	 * F81类指标，三帧模拟的第三帧
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月8日
	 */
	private static byte[] getF81_3Frame3() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[36];
		bytes[0] = 0x68;
		bytes[1] = 28;
		bytes[2] = 28; //长度28
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0;
		bytes[7] = 23;
		bytes[8] = 0;
		bytes[9] = 3;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		//帧序列（末帧、序列3）：0010 0010
		bytes[11] = 0x23;
		
		
		//数据单元标识{P1,F81}：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x0a;
		
		//数据单元：
		//共48个有功功率，最后8个
		//有功功率41
		bytes[16] = 0x00;
		bytes[17] = 41;
		//有功功率42		
		bytes[18] = 0x00;
		bytes[19] = 42;
		//有功功率43		
		bytes[20] = 0x00;
		bytes[21] = 43;
		//有功功率44		
		bytes[22] = 0x00;
		bytes[23] = 44;
		//有功功率45		
		bytes[24] = 0x00;
		bytes[25] = 45;
		//有功功率46
		bytes[26] = 0x00;
		bytes[27] = 46;	
		//有功功率47		
		bytes[28] = 0x00;
		bytes[29] = 47;
		//有功功率48
		bytes[30] = 0x00;
		bytes[31] = 48;	
		
		
		//附加信息AUX
		bytes[32] = 0x00;
		bytes[33] = 0x01;
		
		//帧校验和
		bytes[34] = 0x2b;
		
		bytes[35] = 0x16;
		return bytes;
	}	
}