package demo.everything.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * 手动采集模拟器
 * 
 * @author DIAOPG
 * @date 2015年8月18日
 */
public class TCP_Server_Manual{
	public static void main(String[] args){
		try {
			new Thread(new TCP_Server_Manual_Inner(9999)).start();
			//new Thread(new TCPServer(999)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class TCP_Server_Manual_Inner implements Runnable {
	private ServerSocket serverSocket;
	private Socket clntSock; 
	
	public TCP_Server_Manual_Inner(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		byte[] byteIn = new byte[128];
		byte[] byteArray = getResposeBytes_F5();//默认返回F5
		
		try {
			 is = clntSock.getInputStream();
			 DataInputStream dis = new DataInputStream(is);  
             dis.read(byteIn);
             System.out.println("接收到的数据为：\n"+Arrays.toString(byteIn));  
             byte[] fnArray = new byte[2];
             System.arraycopy(byteIn, 14, fnArray, 0, 2);
             int fn = dByteToInt(fnArray,false);
             System.out.println("AFN为：" + byteIn[10] + ";\nFN为："+fn);
             
             if (byteIn[10]==0x01||byteIn[10]==0x04||byteIn[10]==0x05) {
             	byteArray = getConfirmDenyBytes();
 			 }
             if (byteIn[10]==14&&fn==1) {
            	byteArray = getERC5Bytes();
			 }
             if (byteIn[10]==14&&fn==2) {
             	byteArray = getERC3Bytes();
 			 }
             /*if (byteIn[10]==14&&fn==2) {
              	byteArray = getERC15Bytes();
  			 } */            
             if (byteIn[10]==13&&fn==5) {
             	byteArray = getResposeBytes_F5();
 			 }
             if (byteIn[10]==13&&fn==21) {
              	byteArray = getResposeBytes_F21();
  			 }
             if (byteIn[10]==13&&fn==81) {
              	byteArray = getResposeBytes_F81();
  			 }
             if (byteIn[10]==13&&fn==121) {
               	byteArray = getResposeBytes_F121();
   			 }
             if (byteIn[10]==13&&fn==185) {
              	byteArray = getResposeBytes_F185();
  			 }
             
			 //System.out.println(Arrays.toString(byteArray));
             OutputStream os = clntSock.getOutputStream();  
             DataOutputStream dos = new DataOutputStream(os); 
             
             dos.write(byteArray);
             dos.flush();
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

	/**
	 * F5类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月13日
	 */
	private static byte[] getResposeBytes_F5() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[52];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = (byte) 0xad;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		//帧序列
		bytes[11] = 0x60;
		
		//数据单元标识：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x04;
		bytes[15] = 0x00;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[16] = 23;
		bytes[17] = 16;
		bytes[18] = 7;
		bytes[19] = 15;
		
		//数据单元：
		//日正向有功总电能量
		bytes[20] = 0x00;
		bytes[21] = 0x00;
		bytes[22] = 0x00;
		bytes[23] = 81;
		
		//1.费率1值
		bytes[24] = 0x00;
		bytes[25] = 0x00;
		bytes[26] = 0x00;
		bytes[27] = 0x20;
		
		//2.费率2值
		bytes[28] = 0x00;
		bytes[29] = 0x00;
		bytes[30] = 0x00;
		bytes[31] = 0x0d;
		
		//3.费率3值
		bytes[32] = 0x00;
		bytes[33] = 0x00;
		bytes[34] = 0x00;
		bytes[35] = 0x0f;	
	    
		//4.费率4值
		bytes[36] = 0x00;
		bytes[37] = 0x00;
		bytes[38] = 0x00;
		bytes[39] = 0x0d;
		
		//5.费率5值
		bytes[40] = 0x00;
		bytes[41] = 0x00;
		bytes[42] = 0x00;
		bytes[43] = 0x05;		
		
		//6.费率6值
		bytes[44] = 0x00;
		bytes[45] = 0x00;
		bytes[46] = 0x00;
		bytes[47] = 0x06;	
		
		//附加信息AUX
		bytes[48] = 0x00;
		bytes[49] = 0x01;
		
		//帧校验和
		bytes[50] = 0x2b;
		
		bytes[51] = 0x16;
		return bytes;
	}	

	/**
	 * F21类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月9日
	 */
	private static byte[] getResposeBytes_F21() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[50];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = (byte) 0xad;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		//帧序列
		bytes[11] = 0x60;
		
		//数据单元标识：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x04;
		bytes[15] = 0x02;
		
		//数据单元：
		//月冻结类数据时标Td_m
		bytes[16] = 9;
		bytes[17] = 15;
		
		//月正向有功总电能量
		bytes[18] = 0x00;
		bytes[19] = 0x00;
		bytes[20] = 0x00;
		bytes[21] = 81;
		
		//1.费率1值
		bytes[22] = 0x00;
		bytes[23] = 0x00;
		bytes[24] = 0x00;
		bytes[25] = 10;
		
		//2.费率2值
		bytes[26] = 0x00;
		bytes[27] = 0x00;
		bytes[28] = 0x00;
		bytes[29] = 20;
		
		//3.费率3值
		bytes[30] = 0x00;
		bytes[31] = 0x00;
		bytes[32] = 0x00;
		bytes[33] = 13;	
	    
		//4.费率4值
		bytes[34] = 0x00;
		bytes[35] = 0x00;
		bytes[36] = 0x00;
		bytes[37] = 23;
		
		//5.费率5值
		bytes[38] = 0x00;
		bytes[39] = 0x00;
		bytes[40] = 0x00;
		bytes[41] = 15;		
		
		//6.费率6值
		bytes[42] = 0x00;
		bytes[43] = 0x00;
		bytes[44] = 0x00;
		bytes[45] = 16;	
		
		//附加信息AUX
		bytes[46] = 0x00;
		bytes[47] = 0x01;
		
		//帧校验和
		bytes[48] = 0x2b;
		
		bytes[49] = 0x16;
		return bytes;
	}		
	
	/**
	 * F81类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月13日
	 */
	private static byte[] getResposeBytes_F81() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[76];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 68; //长度68
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		//帧序列
		bytes[11] = 0x60;
		
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
		//有功功率19	    
		bytes[60] = 0x00;
		bytes[61] = 0x00;
		//有功功率20
		bytes[62] = 0x00;
		bytes[63] = 0x0d;
		//有功功率21		
		bytes[64] = 0x00;
		bytes[65] = 0x00;
		//有功功率22		
		bytes[66] = 0x00;
		bytes[67] = 0x05;		
		//有功功率23		
		bytes[68] = 0x00;
		bytes[69] = 0x00;
		//有功功率24		
		bytes[70] = 0x00;
		bytes[71] = 0x06;
		
		
		
		//附加信息AUX
		bytes[72] = 0x00;
		bytes[73] = 0x01;
		
		//帧校验和
		bytes[74] = 0x2b;
		
		bytes[75] = 0x16;
		return bytes;
	}
	
	/**
	 * F185类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月13日
	 */
	private static byte[] getResposeBytes_F185() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[72];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 28;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		//帧序列
		bytes[11] = 0x60;
		
		//数据单元标识：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 23;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[16] = 18;
		bytes[17] = 26;
		bytes[18] = 8;
		bytes[19] = 15;
		
		//数据采集器抄表时间 秒分时日月年
		bytes[20] = 59;
		bytes[21] = 58;
		bytes[22] = 11;
		bytes[23] = 26;
		bytes[24] = 8;
		bytes[25] = 15;
		
		//正向有功总最大需量
		bytes[26] = 100;
		bytes[27] = 0;
		
		//正向有功总最大需量发生时间 分时日月
		bytes[28] = 30;
		bytes[29] = 10;
		bytes[30] = 26;
		bytes[31] = 8;
		
		//费率1正向有功最大需量
		bytes[32] = 10;
		bytes[33] = 0;
		
		//费率1正向有功最大需量发生时间
		bytes[34] = 1;
		bytes[35] = 10;
		bytes[36] = 26;
		bytes[37] = 8;
		
		//费率2正向有功最大需量
		bytes[38] = 10;
		bytes[39] = 0;
		
		//费率2正向有功最大需量发生时间
		bytes[40] = 3;
		bytes[41] = 9;
		bytes[42] = 26;
		bytes[43] = 8;
		
		//费率3正向有功最大需量
		bytes[44] = 30;
		bytes[45] = 0;
		
		//费率3正向有功最大需量发生时间
		bytes[46] = 3;
		bytes[47] = 9;
		bytes[48] = 26;
		bytes[49] = 8;
		
		//费率4正向有功最大需量
		bytes[50] = 20;
		bytes[51] = 0;
		
		//费率4正向有功最大需量发生时间
		bytes[52] = 3;
		bytes[53] = 9;
		bytes[54] = 26;
		bytes[55] = 8;		
		
		//费率5正向有功最大需量
		bytes[56] = 20;
		bytes[57] = 0;
		
		//费率5正向有功最大需量发生时间
		bytes[58] = 25;
		bytes[59] = 9;
		bytes[60] = 26;
		bytes[61] = 8;		
		
		//费率6正向有功最大需量
		bytes[62] = 10;
		bytes[63] = 0;
		
		//费率6正向有功最大需量发生时间
		bytes[64] = 30;
		bytes[65] = 8;
		bytes[66] = 26;
		bytes[67] = 8;				

		//附加信息AUX
		bytes[68] = 30;
		bytes[69] = 0;
		
		//帧校验和
		bytes[70] = 0x2b;
		
		bytes[71] = 0x16;
		return bytes;
	}	
	
	/**
	 * 三类数据ERC3
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月9日
	 */
	private static byte[] getERC3Bytes() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[44];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 30;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x0e;
		//帧序列
		bytes[11] = 0x60;
		
		
		//数据单元标识：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x01;
		bytes[15] = 0x00;
		
		//数据单元：
		//当前重要事件计数器EC1
		bytes[16] = 1;
		
		//当前一般事件计数器EC2
		bytes[17] = 0;
		
		//本帧报文传送的事件记录起始指针Pm
		bytes[18] = 1;
		
		//请求事件记录结束指针Pn
		bytes[19] = 2;
		
		//ERC=3
		bytes[20] = 3;
		
		//长度Le
		bytes[21] = 18;
		
		//参数更新时间：分时日月年
		bytes[22] = 30;
		bytes[23] = 11;
		bytes[24] = 9;
		bytes[25] = 9;
		bytes[26] = 15;
		
		//启动站地址
		bytes[27] = 11;
		
		//变更参数数据单元标识1
		bytes[28] = 0;
		bytes[29] = 0;
		bytes[30] = 0;
		bytes[31] = 11;
		
		//变更参数数据单元标识2
		bytes[32] = 0x00;
		bytes[33] = 0;
		bytes[34] = 0x00;
		bytes[35] = 12;
		
		//变更参数数据单元标识3
		bytes[36] = 0x00;
		bytes[37] = 0;
		bytes[38] = 0x00;
		bytes[39] = 30;
		
		
		//附加信息AUX
		bytes[40] = 0x00;
		bytes[41] = 0x01;
		
		//帧校验和
		bytes[42] = 0x2b;
		
		bytes[43] = 0x16;
		return bytes;
	}
	
	/**
	 * 确认否认报文
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月9日
	 */
	private static byte[] getConfirmDenyBytes() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 30;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x04;
		//帧序列
		bytes[11] = 0x60;
		
		//数据单元标识：
		bytes[12] = 0x01;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x00;
	
		
		//附加信息AUX
		bytes[16] = 0x00;
		bytes[17] = 0x01;
		
		//帧校验和
		bytes[18] = 0x2b;
		
		bytes[19] = 0x16;
		return bytes;
	}	
	
	/**
	 * 三类数据ERC5
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月9日
	 */
	private static byte[] getERC5Bytes() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[38];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 30;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x0e;
		//帧序列
		bytes[11] = 0x60;
		
		
		//数据单元标识：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x00;
		
		//数据单元：
		//当前重要事件计数器EC1
		bytes[16] = 0;
		
		//当前一般事件计数器EC2
		bytes[17] = 2;
		
		//本帧报文传送的事件记录起始指针Pm
		bytes[18] = 1;
		
		//请求事件记录结束指针Pn
		bytes[19] = 3;
		
		//ERC=5
		bytes[20] = 5;
		
		//长度Le
		bytes[21] = 12;
		
		//跳闸时间：分时日月年
		bytes[22] = 36;
		bytes[23] = 10;
		bytes[24] = 28;
		bytes[25] = 8;
		bytes[26] = 15;
		
		//D15～D12：备用	D11～D0：pn（测量点号1～2048）	BIN	2
		bytes[27] = 0;
		bytes[28] = 10;
		
		//跳闸轮次
		bytes[29] = 5;
		
		//跳闸时功率（当前总有功功率）
		bytes[30] = 0;
		bytes[31] = 100;
		
		//跳闸后2分钟的功率（当前总有功功率）
		bytes[32] = 0x00;
		bytes[33] = 80;
		
		
		//附加信息AUX
		bytes[34] = 0x00;
		bytes[35] = 0x01;
		
		//帧校验和
		bytes[36] = 0x2b;
		
		bytes[37] = 0x16;
		return bytes;
	}
	
	/**
	 * 三类数据ERC15
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月9日
	 */
	@SuppressWarnings("unused")
	private static byte[] getERC15Bytes() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[73];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 30;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x0e;
		//帧序列
		bytes[11] = 0x60;
		
		
		//数据单元标识：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x00;
		
		//数据单元：
		//当前重要事件计数器EC1
		bytes[16] = 0;
		
		//当前一般事件计数器EC2
		bytes[17] = 1;
		
		//本帧报文传送的事件记录起始指针Pm
		bytes[18] = 1;
		
		//请求事件记录结束指针Pn
		bytes[19] = 2;
		
		//ERC=15
		bytes[20] = 15;
		
		//长度Le
		bytes[21] = 47;
		
		//发生时间：分时日月年
		bytes[22] = 45;
		bytes[23] = 15;
		bytes[24] = 9;
		bytes[25] = 9;
		bytes[26] = 15;
		
		//D15：起/止标志	D14～D12：备用	D11～D0：pn（测量点号1～2048）
		bytes[27] = 0;
		bytes[28] = 10;
		
		//异常标志异常标志
		bytes[29] = 1;
		
		//谐波越限标志
		bytes[30] = 0;
		bytes[31] = 1;
		bytes[32] = 3;
		
		//越限时总畸变电压含有率（％）/电流有效值
		bytes[33] = 0;
		bytes[34] = 119;
		
		//越限时该相2次谐波电压含有率（％）/电流有效值
		bytes[35] = 0;
		bytes[36] = 2;
		
		//越限时该相3次谐波电压含有率（％）/电流有效值
		bytes[37] = 0;
		bytes[38] = 2;
		
		//越限时该相4次谐波电压含有率（％）/电流有效值
		bytes[39] = 0;
		bytes[40] = 2;
		
		//越限时该相5次谐波电压含有率（％）/电流有效值
		bytes[41] = 0;
		bytes[42] = 2;
		
		//越限时该相6次谐波电压含有率（％）/电流有效值
		bytes[43] = 0;
		bytes[44] = 6;
		
		//越限时该相7次谐波电压含有率（％）/电流有效值
		bytes[45] = 0;
		bytes[46] = 7;
		
		//越限时该相8次谐波电压含有率（％）/电流有效值
		bytes[47] = 0;
		bytes[48] = 8;
		
		//越限时该相9次谐波电压含有率（％）/电流有效值
		bytes[49] = 0;
		bytes[50] = 9;
		
		//越限时该相10次谐波电压含有率（％）/电流有效值
		bytes[51] = 0;
		bytes[52] = 10;
		
		//越限时该相11次谐波电压含有率（％）/电流有效值
		bytes[53] = 0;
		bytes[54] = 11;
		
		//越限时该相12次谐波电压含有率（％）/电流有效值
		bytes[55] = 0;
		bytes[56] = 12;
		
		//越限时该相13次谐波电压含有率（％）/电流有效值
		bytes[57] = 0;
		bytes[58] = 13;
		
		//越限时该相14次谐波电压含有率（％）/电流有效值
		bytes[59] = 0;
		bytes[60] = 14;
		
		//越限时该相15次谐波电压含有率（％）/电流有效值
		bytes[61] = 0;
		bytes[62] = 15;
		
		//越限时该相16次谐波电压含有率（％）/电流有效值
		bytes[63] = 0;
		bytes[64] = 16;
		
		//越限时该相17次谐波电压含有率（％）/电流有效值
		bytes[65] = 0;
		bytes[66] = 17;
		
		//越限时该相18次谐波电压含有率（％）/电流有效值
		bytes[67] = 0;
		bytes[68] = 18;
		
		//越限时该相19次谐波电压含有率（％）/电流有效值
		bytes[67] = 0;
		bytes[68] = 19;
		
		
		//附加信息AUX
		bytes[69] = 0x00;
		bytes[70] = 0x01;
		
		//帧校验和
		bytes[71] = 0x2b;
		
		bytes[72] = 0x16;
		return bytes;
	}	
	
	/**
	 * F121类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月9日
	 */
	private static byte[] getResposeBytes_F121() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[38];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 28;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 0x02;
		bytes[8] = 0x00;
		bytes[9] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x0d;
		//帧序列
		bytes[11] = 0x60;
		
		//数据单元标识：
		bytes[12] = 0x00;
		bytes[13] = 0x01;
		bytes[14] = 0x00;
		bytes[15] = 0x0f;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[16] = 11;
		bytes[17] = 26;
		bytes[18] = 8;
		bytes[19] = 15;
		
		//谐波次数N（≤19）
		bytes[20] = 0x00;
		bytes[21] = 0x03;

		//A相总畸变电压含有率越限日累计时间
		bytes[22] = 0x00;
		bytes[23] = 11;
		
		//A相2次谐波电压含有率越限日累计时间
		bytes[24] = 0x00;
		bytes[25] = 0x05;
		
		//A相3次谐波电压含有率越限日累计时间
		bytes[26] = 0x00;
		bytes[27] = 0x06;
		
		//A相总畸变电流越限日累计时间
		bytes[28] = 0x00;
		bytes[29] = 15;
		
		//A相2次谐波电流越限日累计时间
		bytes[30] = 0x00;
		bytes[31] = 0x07;
		
		//A相3次谐波电流越限日累计时间
		bytes[32] = 0x00;
		bytes[33] = 0x08;
		
		
		//附加信息AUX
		bytes[34] = 0x00;
		bytes[35] = 0x01;
		
		//帧校验和
		bytes[36] = 0x2b;
		
		bytes[37] = 0x16;
		return bytes;
	}	
	
	/**
	 * 将Pn/Fn的两字节数组转为整型
	 */
	public static int dByteToInt(byte[] value, boolean isPn) {
		// DT1、DT2同时为0为数据采集器，整型值为0
		if (value[0]==0 && value[1]==0&&isPn) {
			return 0;
		}
		int remainder = toUnsignedInt1(value[0])-1;
		int quotient = isPn ? toUnsignedInt1(value[1]) - 1 : toUnsignedInt1(value[1]);
		return (quotient * 8) + (remainder + 2);
	}
	
	/**
	 * 将字节型转为1字节无符号整型
	 */
	public static int toUnsignedInt1(byte x) {
		return ((int) x) & 0xff;
	}
}
