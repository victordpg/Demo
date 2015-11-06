package demo.everything.netty;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TCP协议主动上传模拟器
 * <br><b><报文的主站地址为0></b>
 * 
 * @author DIAOPG
 * @date 2015年10月13日
 */
class TCPClient_Initiative implements Runnable {
	private Socket socket;
	
	/**
	 * 用于单独启动该模拟器
	 * @param args
	 * @author DIAOPG
	 * @date 2015年10月14日
	 */
	public static void main(String[] args) {
		try {
			String host = "192.168.1.100"; //主机IP
			int port = 10000; //主机Port
			Socket socket =  new Socket(host, port);
			Socket socket1 = new Socket(host, port);
			Socket socket2 = new Socket(host, port);
			Socket socket3 = new Socket(host, port);
			Socket socket4 = new Socket(host, port);
			Socket socket5 = new Socket(host, port);
			Socket socket6 = new Socket(host, port);
			Socket socket7 = new Socket(host, port);
			Socket socket8 = new Socket(host, port);
			Socket socket9 = new Socket(host, port);

			//主动上传模拟器需要设置发送时间间隔，所以有下面启动方式。
			ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(10);
			Thread thread =  new Thread(new TCPClient_Initiative(socket));
			Thread thread1 = new Thread(new TCPClient_Initiative(socket1));
			Thread thread2 = new Thread(new TCPClient_Initiative(socket2));
			Thread thread3 = new Thread(new TCPClient_Initiative(socket3));
			Thread thread4 = new Thread(new TCPClient_Initiative(socket4));
			Thread thread5 = new Thread(new TCPClient_Initiative(socket5));
			Thread thread6 = new Thread(new TCPClient_Initiative(socket6));
			Thread thread7 = new Thread(new TCPClient_Initiative(socket7));
			Thread thread8 = new Thread(new TCPClient_Initiative(socket8));
			Thread thread9 = new Thread(new TCPClient_Initiative(socket9));
			exec.scheduleAtFixedRate(thread,  3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread1, 3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread2, 3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread3, 3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread4, 3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread5, 3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread6, 3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread7, 3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread8, 3000, 3, TimeUnit.MILLISECONDS);
			exec.scheduleAtFixedRate(thread9, 3000, 3, TimeUnit.MILLISECONDS);
			System.out.println("主动上传模拟器启动！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TCPClient_Initiative() {}	

	public TCPClient_Initiative(Socket socket2) {
		setSocket(socket2);
	}
	
	@Override
	public void run() {
		handleClientSockets();
	}	
	
	public void handleClientSockets(){
		OutputStream os = null;
		DataOutputStream dos = null;
		try {
				os = getSocket().getOutputStream();
				dos = new DataOutputStream(os);

				byte[] byteArray = null;
				byteArray = getClass1_F2F4_new();
				System.out.println("----------"+Arrays.toString(byteArray));
				dos.write(byteArray);
				dos.flush();
				System.out.println("=========="+Arrays.toString(byteArray));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private static byte[] getRandomInitiativeByteArray_new() {
		int random = getRandom();
		byte[] byteArray = null;
		//synchronized (byteArray) {
			switch (random) {
				case 1:
					//byteArray = getMultiFResposeBytes_new();
					byteArray = getClass2_F5_new();
					System.out.println("byteArray = getMultiFResposeBytes_new();");
					break;
				case 2:
					byteArray = getClass2_F5_new();
					System.out.println("byteArray = getClass2_F5_new();");
					break;
				case 3:
					byteArray = getClass2_F81_new();
					System.out.println("byteArray = getClass2_F81_new();");
					break;
				case 4:
					byteArray = getClass2_F121_new();
					System.out.println("byteArray = getClass2_F121_new();");
					break;
				case 5:
					byteArray = getClass2_F185_new();
					System.out.println("byteArray = getClass2_F185_new();");
					break;
				case 6:
					byteArray = getClass3_ERC5_new();
					System.out.println("byteArray = getClass3_ERC5_new();");
					break;
				case 7:
					byteArray = getClass3_ERC910_new();
					System.out.println("byteArray = getClass3_ERC910_new();");
					break;
				case 8:
					byteArray = getClass1_F2F4_new();
					System.out.println("byteArray = getClass1_F2F4_new();");
					break;	
				case 9:
					byteArray = getClass1_F185_new();
					System.out.println("byteArray = getClass1_F185_new();");
					break;	
				case 10:
					byteArray = getClass1_F177_new();
					System.out.println("byteArray = getClass1_F177_new();");
					break;		
				case 11:
					byteArray = getClass1_F25_new();
					System.out.println("byteArray = getClass1_F25_new();");
					break;			
				default:
					byteArray = getClass2_F5_new();
					System.out.println("Default");
					break;
			}
		//}
		return byteArray;
	}	

	/**
	 * 获取随机数
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月17日
	 */
	private static int getRandom() {
		Random random = new Random();
		int i = random.nextInt(10)+1;
		return i;
	}

	
	/**
	 * 
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月23日
	 */
	private static byte[] getClass1_F2F4_new() {
		// TODO Auto-generated method stub
	    byte[] bytes = new byte[33];
	    bytes[0] = 0x68;
		bytes[1] = 0x42;
		bytes[2] = 0x00;
		bytes[3] = 0x42;
		bytes[4] = 0x00;		
		bytes[5] = 0x68;
		bytes[6] = 0x00;
		
		bytes[7] = 0x21;
		bytes[8] = 0x00;
		bytes[9] = 0x17;
		bytes[10] = 0x00;
		bytes[11] = 0x00;
		
		bytes[12] = 0x0c;
		bytes[13] = 0x60;
	
		//数据单元标识： F2
		bytes[14] = 0x03;
		bytes[15] = 0x01;
		bytes[16] = 0x02;
		bytes[17] = 0x00;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[18] = 0x00; // 秒取值范围：十进制数0~59
		bytes[19] = 0x00; // 分取值范围：十进制数0~59
		bytes[20] = 0x08; // 时取值范围：十进制数0~23
		bytes[21] = 0x1a; // 日取值范围：十进制数1~31
		bytes[22] = 0x54; // 高四位为星期，取值范围：0~7, 0：无效，1～7依次表示星期一至星期日	低四位为月，取值范围：十进制数1~12
		bytes[23] = 15; //年的低两位，取值范围：十进制数1~99

		//数据单元标识： F4
		bytes[24] = 0x03;
		bytes[25] = 0x01;
		bytes[26] = 0x04;
		bytes[27] = 0x00;
		
		bytes[28] = 0x03; 
		
		bytes[29] = 0x00;
		bytes[30] = 0x01;
		bytes[31] = 0x01;
		bytes[32] = 0x16;
		return bytes;
	}
	
	/**
	 * 
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月23日
	 */
	private synchronized static byte[] getClass1_F25_new() {
		// TODO Auto-generated method stub
	    byte[] bytes = new byte[74];
		bytes[0] = 0x68;
		bytes[1] = 0x42;
		bytes[2] = 0x00;
		bytes[3] = 0x42;
		bytes[4] = 0x00;		
		bytes[5] = 0x68;
		bytes[6] = 0x00;
		
		//region:25
		bytes[7] = 0x19;
		bytes[8] = 0x00;
		//data_collector:1
		bytes[9] = 0x01;
		bytes[10] = 0x00;
		//main_server:0
		bytes[11] = 0x00;
		
		bytes[12] = 0x0c;
		bytes[13] = 0x60;
	
		//数据单元标识：p1,f25
		bytes[14] = 0x01;
		bytes[15] = 0x01;
		bytes[16] = 0x19;
		bytes[17] = 0x00;
		
		//数据单元：
		//数据采集器抄表时间
		bytes[18] = 0x00; // 秒取值范围：十进制数0~59
		bytes[19] = 0x00; // 分取值范围：十进制数0~59
		bytes[20] = 0x08; // 时取值范围：十进制数0~23
		bytes[21] = 0x1a; // 日取值范围：十进制数1~31
		//周一，19月
		bytes[22] = 0x1a; // 高四位为星期，取值范围：0~7, 0：无效，1～7依次表示星期一至星期日	低四位为月，取值范围：十进制数1~12
		bytes[23] = 15; //年的低两位，取值范围：十进制数1~99

		//当前A相电流
		bytes[24] = 0x03;
		bytes[25] = 0x01;
		//当前B相电流
		bytes[26] = 0x03;
		bytes[27] = 0x01;
		//当前C相电流
		bytes[28] = 0x03;
		bytes[29] = 0x01;
		//当前A相有功功率
		bytes[30] = 0x03;
		bytes[31] = 0x01;
		//当前B相有功功率
		bytes[32] = 0x03;
		bytes[33] = 0x01;
		//当前C相有功功率
		bytes[34] = 0x03;
		bytes[35] = 0x01;
		///当前A相无功功率
		bytes[36] = 0x03;
		bytes[37] = 0x01;
		//当前B相无功功率
		bytes[38] = 0x03;
		bytes[39] = 0x01;
		//当前C相无功功率
		bytes[40] = 0x03;
		bytes[41] = 0x01;
		//当前A相功率因数
		bytes[42] = 0x03;
		bytes[43] = 0x01;
		//当前B相功率因数
		bytes[44] = 0x03;
		bytes[45] = 0x01;
		//当前C相功率因数
		bytes[46] = 0x03;
		bytes[47] = 0x01;
		//当前A相视在功率
		bytes[48] = 0x03;
		bytes[49] = 0x01;
		//当前B相视在功率
		bytes[50] = 0x03;
		bytes[51] = 0x01;
		//当前C相视在功率
		bytes[52] = 0x03;
		bytes[53] = 0x01;
		//当前A相电压
		bytes[54] = 0x03;
		bytes[55] = 0x01;
		//当前B相电压
		bytes[56] = 0x03;
		bytes[57] = 0x01;
		//当前C相电压
		bytes[58] = 0x03;
		bytes[59] = 0x01;
		//当前零序电流
		bytes[60] = 0x03;
		bytes[61] = 0x01;
		//当前总有功功率
		bytes[62] = 0x03;
		bytes[63] = 0x01;
		//当前总无功功率
		bytes[64] = 0x03;
		bytes[65] = 0x01;
		//当前总功率因数
		bytes[66] = 0x03;
		bytes[67] = 0x01;
		//当前总视在功率
		bytes[68] = 0x03;
		bytes[69] = 0x01;
		
		bytes[70] = 0x00;
		bytes[71] = 0x01;
		bytes[72] = 0x01;
		bytes[73] = 0x16;
		return bytes;
	}	
	
	private synchronized static byte[] getClass1_F177_new(){
		byte[] bytes = new byte[70];
		bytes[0] = 0x68;
		bytes[1] = 0x42;
		bytes[2] = 0x00;
		bytes[3] = 0x42;
		bytes[4] = 0x00;
		bytes[5] = 0x68;
		bytes[6] = 0x00;

		//region:177
		bytes[7] = (byte) 0xb1;
		bytes[8] = 0x00;
		//data_collector:1
		bytes[9] = 0x01;
		bytes[10] = 0x00;
		bytes[11] = 0x00;

		bytes[12] = 0x0c;
		bytes[13] = 0x60;

		// 数据单元标识： P1, F177
		bytes[14] = 0x01;
		bytes[15] = 0x01;
		bytes[16] = 0x01;
		bytes[17] = 0x16;
		
		bytes[18] = 0x05; // Flh
		bytes[19] = 0x03; // N boxing
		bytes[20] = 0x06; //z
		bytes[21] = 0x07; //z 
		
		bytes[22] = 0x06; //v
		bytes[23] = 0x00; // v
		
		bytes[24] = 0x07; // v
		bytes[25]=0x00; //v
		
		bytes[26]=0x08; //v
		bytes[27]=0x0C; //v
		
		bytes[28]=0x06; //
		bytes[29]=0x12; //
		bytes[30]=0x08; //
		bytes[31]=0x14; //
		bytes[32]=0x02; //
		bytes[33]=0x09; //
		
		bytes[34]=0x03; 
		bytes[35]=0x06; 
		bytes[36]=0x0C; 
		bytes[37]=0x05; 
		bytes[38]=0x09; 
		bytes[39]=0x04; 
		bytes[40]=0x1A; 
		bytes[41]=0x08; 
		bytes[42]=0x03; 
		bytes[43]=0x18; 
		bytes[44]=0x05; 
		bytes[45]=0x08; 
		bytes[46]=0x04; 
		bytes[47]=0x02; 
		
		bytes[48]=0x07; 
		bytes[49]=0x0D; 
		bytes[50]=0x06; 
		bytes[51]=0x02; 
		
		bytes[52]=0x06; 
		bytes[53]=0x08; 
		bytes[54]=0x03; 
		bytes[55]=0x07; 
		bytes[56]=0x0C; 
		bytes[57]=0x09; 
		
		bytes[58]=0x21; //P
		bytes[59]=0x07; //T
		bytes[60]=0x03; 
		bytes[61]=0x05; 
		
		bytes[62]=0x01; 
		bytes[63]=0x02; 
		bytes[64]=0x03; 
		bytes[65]=0x04; 
		
		bytes[66] = 0x00;
		bytes[67] = 0x01;
		bytes[68] = 0x01;
		bytes[69] = 0x16;
		
		return bytes;
	}
	
	private synchronized static byte[] getClass1_F185_new() {
		// TODO Auto-generated method stub
	    byte[] bytes = new byte[33];
		bytes[0] = 0x68;
		bytes[1] = 0x42;
		bytes[2] = 0x00;
		bytes[1+2] = 0x42;
		bytes[2+2] = 0x00;		
		bytes[3+2] = 0x68;
		bytes[4+2] = 0x00;
		
		//region:185
		bytes[5+2] = (byte) 0xB9;
		bytes[6+2] = 0x00;
		//data_collector:1
		bytes[7+2] = 0x01;
		bytes[8+2] = 0x00;
		//main_server:185
		bytes[9+2] = 0x00;
		
		bytes[10+2] = 0x0c;
		bytes[11+2] = 0x60;
	
		//数据单元标识： P1, F185
		bytes[12+2] = 0x01;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x17;
		
		//数据单元：
		bytes[16+2] = 0x03; // 模块数（分路数 ）
		bytes[17+2] = 0x20; // 模块1设备状态字
		bytes[18+2] = 0x08;  // 模块1状态是否读取
		bytes[19+2] = 0x21; // 模块2设备状态字
		bytes[20+2] = 0x07;  // 模块2状态是否读取
		bytes[21+2] = 0x01;  // 模块3设备状态字
		bytes[22+2] = 0x06;   // 模块3状态是否读取
		
		bytes[23+2] = 0x00;
		bytes[24+2] = 0x0a;
		bytes[25+2] = 0x00;
		bytes[26+2] = 0x14; 
		
		bytes[27+2] = 0x00;
		bytes[28+2] = 0x01;
		bytes[29+2] = 0x01;
		bytes[30+2] = 0x16;
		return bytes;
	}
	
	@SuppressWarnings("unused")
	private synchronized static byte[] getMultiFResposeBytes_new() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[90];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = (byte) 0xad;
		bytes[1+2] = 0x00;
		bytes[2+2] = (byte) 0xad;		
		bytes[3+2] = 0x68;
		
		//控制域
		bytes[4+2] = (byte) 0x80; 
		
		//地址域
		bytes[5+2] = 33;
		bytes[6+2] = 0x00;
		bytes[7+2] = 23;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
		//应用程序功能码
		bytes[10+2] = 0x0d;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 5;
		bytes[15+2] = 0x00;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[16+2] = 47;
		bytes[17+2] = 23;
		bytes[18+2] = 8;
		bytes[19+2] = 15;
		
		//数据单元：
		//日正向有功总电能量
		bytes[20+2] = 60;
		bytes[21+2] = 0x00;
		bytes[22+2] = 0x00;
		bytes[23+2] = 0;
		
		//1.费率1值
		bytes[24+2] = 5;
		bytes[25+2] = 0x00;
		bytes[26+2] = 0x00;
		bytes[27+2] = 0;
		
		//2.费率2值
		bytes[28+2] = 10;
		bytes[29+2] = 0x00;
		bytes[30+2] = 0x00;
		bytes[31+2] = 0;
		
		//3.费率3值
		bytes[32+2] = 15;
		bytes[33+2] = 0x00;
		bytes[34+2] = 0x00;
		bytes[35+2] = 0;	
	    
		//4.费率4值
		bytes[36+2] = 14;
		bytes[37+2] = 0x00;
		bytes[38+2] = 0x00;
		bytes[39+2] = 0;
		
		//5.费率5值
		bytes[40+2] = 6;
		bytes[41+2] = 0x00;
		bytes[42+2] = 0x00;
		bytes[43+2] = 0;		
		
		//6.费率6值
		bytes[44+2] = 10;
		bytes[45+2] = 0x00;
		bytes[46+2] = 0x00;
		bytes[47+2] = 0;	
		
		//数据单元标识2：
		bytes[48+2] = 0x00;
		bytes[49+2] = 0x01;
		bytes[50+2] = 6;
		bytes[51+2] = 0x00;
		
		//数据单元2：
		//日冻结类数据时标Td_d
		bytes[52+2] = 18;
		bytes[53+2] = 23;
		bytes[54+2] = 9;
		bytes[55+2] = 15;
		
		//数据单元：
		//日正向有功总电能量
		bytes[56+2] = 12;
		bytes[57+2] = 0x00;
		bytes[58+2] = 0x00;
		bytes[59+2] = 0;
		
		//1.费率1值
		bytes[60+2] = 2;
		bytes[61+2] = 0x00;
		bytes[62+2] = 0x00;
		bytes[63+2] = 0;
		
		//2.费率2值
		bytes[64+2] = 3;
		bytes[65+2] = 0x00;
		bytes[66+2] = 0x00;
		bytes[67+2] = 0;
		
		//3.费率3值
		bytes[68+2] = 2;
		bytes[69+2] = 0x00;
		bytes[70+2] = 0x00;
		bytes[71+2] = 0;	
	    
		//4.费率4值
		bytes[72+2] = 1;
		bytes[73+2] = 0x00;
		bytes[74+2] = 0x00;
		bytes[75+2] = 0;
		
		//5.费率5值
		bytes[76+2] = 1;
		bytes[77+2] = 0x00;
		bytes[78+2] = 0x00;
		bytes[79+2] = 0;		
		
		//6.费率6值
		bytes[80+2] = 3;
		bytes[81+2] = 0x00;
		bytes[82+2] = 0x00;
		bytes[83+2] = 0;			
		
		//附加信息AUX
		bytes[84+2] = 0x00;
		bytes[85+2] = 0x01;
		
		//帧校验和
		bytes[86+2] = 0x2b;
		
		bytes[87+2] = 0x16;
		return bytes;
	}	
	
	
	private synchronized static byte[] getClass2_F5_new() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[54];
		bytes[0] = 0x68;
		bytes[1] = 0x2e;
		bytes[2] = 0x0;
		bytes[3] = 0x2e;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		bytes[7] = 0x13;
		bytes[8] = 0x00;
		bytes[9] = 0x17;
		bytes[10] = 0x00;
		bytes[11] = 0x00;
		
		//应用程序功能码
		bytes[12] = 0x0d;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		bytes[14] = 0x00;
		bytes[15] = 0x01;
		bytes[16] = 0x05;
		bytes[17] = 0x00;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[18] = 0x10;
		bytes[19] = 0x17;
		bytes[20] = 0x09;
		bytes[21] = 0x0f;
		
		//数据单元：
		//日正向有功总电能量
		bytes[22] = 0x3c;
		bytes[23] = 0x00;
		bytes[24] = 0x00;
		bytes[25] = 0x0;
		
		//1.费率1值
		bytes[26] = 0x0a;
		bytes[27] = 0x00;
		bytes[28] = 0x00;
		bytes[29] = 0x0;
		
		//2.费率2值
		bytes[30] = 0x05;
		bytes[31] = 0x00;
		bytes[32] = 0x00;
		bytes[33] = 0x00;
		
		//3.费率3值
		bytes[34] = 0x0f;
		bytes[35] = 0x00;
		bytes[36] = 0x00;
		bytes[37] = 0x00;	
	    
		//4.费率4值
		bytes[38] = 0x04;
		bytes[39] = 0x00;
		bytes[40] = 0x00;
		bytes[41] = 0x00;
		
		//5.费率5值
		bytes[42] = 0x10;
		bytes[43] = 0x00;
		bytes[44] = 0x00;
		bytes[45] = 0x00;		
		
		//6.费率6值
		bytes[46] = 0x0a;
		bytes[47] = 0x00;
		bytes[48] = 0x00;
		bytes[49] = 0x00;	
		
		//附加信息AUX
		bytes[50] = 0x00;
		bytes[51] = 0x01;
		
		//帧校验和
		bytes[52] = 0x2b;
		
		bytes[53] = 0x16;
		return bytes;
	}	

	private synchronized static byte[] getClass2_F81_new() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[78];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x44; //长度68
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x44; //长度68		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x13;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x00;
		
		//应用程序功能码
		bytes[10+2] = 0x0d;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识{P1,F81}：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x0a;
		
		//数据单元：
		//曲线类数据时标Td_c
		bytes[16+2] = 0x01; //秒
		bytes[17+2] = 0x0f; //分
		bytes[18+2] = 0x09; //时
		bytes[19+2] = 0x19; //日
		bytes[20+2] = 0x28; //星期二8月。高四位为星期，取值范围：0~7, 0：无效，1～7依次表示星期一至星期日	低四位为月，取值范围：十进制数1~12
		bytes[21+2] = 0x0f; //年
		bytes[22+2] = 0x03; //冻结密度为3，即60分钟冻结一次
		bytes[23+2] = 0x18; //此时冻结点数为24
		
		//共24个有功功率
		//有功功率1
		bytes[24+2] = 0x00;
		bytes[25+2] = 0x00;
		//有功功率2		
		bytes[26+2] = 0x00;
		bytes[27+2] = 0x20;
		//有功功率3		
		bytes[28+2] = 0x00;
		bytes[29+2] = 0x00;
		//有功功率4		
		bytes[30+2] = 0x00;
		bytes[31+2] = 0x0d;
		//有功功率5		
		bytes[32+2] = 0x00;
		bytes[33+2] = 0x00;
		//有功功率6
		bytes[34+2] = 0x00;
		bytes[35+2] = 0x0f;	
		//有功功率7	    
		bytes[36+2] = 0x00;
		bytes[37+2] = 0x00;
		//有功功率8
		bytes[38+2] = 0x00;
		bytes[39+2] = 0x0d;
		//有功功率9		
		bytes[40+2] = 0x00;
		bytes[41+2] = 0x00;
		//有功功率10		
		bytes[42+2] = 0x00;
		bytes[43+2] = 0x05;		
		//有功功率11		
		bytes[44+2] = 0x00;
		bytes[45+2] = 0x00;
		//有功功率12		
		bytes[46+2] = 0x00;
		bytes[47+2] = 0x06;	
		
		//有功功率13
		bytes[48+2] = 0x00;
		bytes[49+2] = 0x00;
		//有功功率14		
		bytes[50+2] = 0x00;
		bytes[51+2] = 0x20;
		//有功功率15		
		bytes[52+2] = 0x00;
		bytes[53+2] = 0x00;
		//有功功率16		
		bytes[54+2] = 0x00;
		bytes[55+2] = 0x0d;
		//有功功率17		
		bytes[56+2] = 0x00;
		bytes[57+2] = 0x00;
		//有功功率18
		bytes[58+2] = 0x00;
		bytes[59+2] = 0x0f;	
		//有功功率19	    
		bytes[60+2] = 0x00;
		bytes[61+2] = 0x00;
		//有功功率20
		bytes[62+2] = 0x00;
		bytes[63+2] = 0x0d;
		//有功功率21		
		bytes[64+2] = 0x00;
		bytes[65+2] = 0x00;
		//有功功率22		
		bytes[66+2] = 0x00;
		bytes[67+2] = 0x05;		
		//有功功率23		
		bytes[68+2] = 0x00;
		bytes[69+2] = 0x00;
		//有功功率24		
		bytes[70+2] = 0x00;
		bytes[71+2] = 0x06;
		
		
		
		//附加信息AUX
		bytes[72+2] = 0x00;
		bytes[73+2] = 0x01;
		
		//帧校验和
		bytes[74+2] = 0x2b;
		
		bytes[75+2] = 0x16;
		return bytes;
	}		
	
	private synchronized static byte[] getClass2_F121_new() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[104];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1c;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1c;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x13;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x00;
		
		//应用程序功能码
		bytes[10+2] = 0x0d;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x0f;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[16+2] = 0x0b;
		bytes[17+2] = 0x1a;
		bytes[18+2] = 0x08;
		bytes[19+2] = 0x0f;
		
		//谐波次数N（≤19）
		bytes[20+2] = 0x00;
		bytes[21+2] = 0x13;
	
		//A相总畸变电压含有率越限日累计时间
		bytes[22+2] = 0x00;
		bytes[23+2] = 0x6e;
		
		//A相2次谐波电压含有率越限日累计时间
		bytes[24+2] = 0x00;
		bytes[25+2] = 0x05;
		
		//A相3次谐波电压含有率越限日累计时间
		bytes[26+2] = 0x00;
		bytes[27+2] = 0x06;
		
		//A相4次谐波电压含有率越限日累计时间
		bytes[28+2] = 0x00;
		bytes[29+2] = 0x05;
		
		//A相5次谐波电压含有率越限日累计时间
		bytes[30+2] = 0x00;
		bytes[31+2] = 0x06;
		
		//A相6次谐波电压含有率越限日累计时间
		bytes[32+2] = 0x00;
		bytes[33+2] = 0x05;
		
		//A相7次谐波电压含有率越限日累计时间
		bytes[34+2] = 0x00;
		bytes[35+2] = 0x06;
		
		//A相8次谐波电压含有率越限日累计时间
		bytes[36+2] = 0x00;
		bytes[37+2] = 0x05;
		
		//A相9次谐波电压含有率越限日累计时间
		bytes[38+2] = 0x00;
		bytes[39+2] = 0x06;		
		
		//A相10次谐波电压含有率越限日累计时间
		bytes[40+2] = 0x00;
		bytes[41+2] = 0x05;
		
		//A相11次谐波电压含有率越限日累计时间
		bytes[42+2] = 0x00;
		bytes[43+2] = 0x06;
		
		//A相12次谐波电压含有率越限日累计时间
		bytes[44+2] = 0x00;
		bytes[45+2] = 0x05;
		
		//A相13次谐波电压含有率越限日累计时间
		bytes[46+2] = 0x00;
		bytes[47+2] = 0x06;
		
		//A相14次谐波电压含有率越限日累计时间
		bytes[48+2] = 0x00;
		bytes[49+2] = 0x05;
		
		//A相15次谐波电压含有率越限日累计时间
		bytes[50+2] = 0x00;
		bytes[51+2] = 0x06;
		
		//A相16次谐波电压含有率越限日累计时间
		bytes[52+2] = 0x00;
		bytes[53+2] = 0x05;
		
		//A相17次谐波电压含有率越限日累计时间
		bytes[54+2] = 0x00;
		bytes[55+2] = 0x06;
		
		//A相18次谐波电压含有率越限日累计时间
		bytes[56+2] = 0x00;
		bytes[57+2] = 0x12;
		
		//A相19次谐波电压含有率越限日累计时间
		bytes[58+2] = 0x00;
		bytes[59+2] = 0x13;
		
		//A相总畸变电流越限日累计时间
		bytes[60+2] = 0x00;
		bytes[61+2] = 0x0f;
		
		//A相2次谐波电流越限日累计时间
		bytes[62+2] = 0x00;
		bytes[63+2] = 0x07;
		
		//A相3次谐波电流越限日累计时间
		bytes[64+2] = 0x00;
		bytes[65+2] = 0x08;
		
		//A相4次谐波电流越限日累计时间
		bytes[66+2] = 0x00;
		bytes[67+2] = 0x07;
		
		//A相5次谐波电流越限日累计时间
		bytes[68+2] = 0x00;
		bytes[69+2] = 0x08;
	
		//A相6次谐波电流越限日累计时间
		bytes[70+2] = 0x00;
		bytes[71+2] = 0x07;
		
		//A相7次谐波电流越限日累计时间
		bytes[72+2] = 0x00;
		bytes[73+2] = 0x08;
		
		//A相8次谐波电流越限日累计时间
		bytes[74+2] = 0x00;
		bytes[75+2] = 0x07;
		
		//A相9次谐波电流越限日累计时间
		bytes[76+2] = 0x00;
		bytes[77+2] = 0x08;
		
		//A相10次谐波电流越限日累计时间
		bytes[78+2] = 0x00;
		bytes[79+2] = 0x07;
		
		//A相11次谐波电流越限日累计时间
		bytes[80+2] = 0x00;
		bytes[81+2] = 0x08;
		
		//A相12次谐波电流越限日累计时间
		bytes[82+2] = 0x00;
		bytes[83+2] = 0x07;
		
		//A相13次谐波电流越限日累计时间
		bytes[84+2] = 0x00;
		bytes[85+2] = 0x08;
		
		//A相14次谐波电流越限日累计时间
		bytes[86+2] = 0x00;
		bytes[87+2] = 0x07;
		
		//A相15次谐波电流越限日累计时间
		bytes[88+2] = 0x00;
		bytes[89+2] = 0x08;
		
		//A相16次谐波电流越限日累计时间
		bytes[90+2] = 0x00;
		bytes[91+2] = 0x07;
		
		//A相17次谐波电流越限日累计时间
		bytes[92+2] = 0x00;
		bytes[93+2] = 0x08;
		
		//A相18次谐波电流越限日累计时间
		bytes[94+2] = 0x00;
		bytes[95+2] = 0x07;
		
		//A相19次谐波电流越限日累计时间
		bytes[96+2] = 0x00;
		bytes[97+2] = 0x08;
		
		//附加信息AUX
		bytes[98+2] = 0x00;
		bytes[99+2] = 0x01;
		
		//帧校验和
		bytes[100+2] = 0x2b;
		
		bytes[101+2] = 0x16;
		return bytes;
	}	

	
	private synchronized static byte[] getClass2_F185_new() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[74];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1c;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1c;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x13;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x00;
		
		//应用程序功能码
		bytes[10+2] = 0x0d;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x17;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[16+2] = 0x12;
		bytes[17+2] = 0x18;
		bytes[18+2] = 0x08;
		bytes[19+2] = 0x0f;
		
		//数据采集器抄表时间 秒分时日月年
		bytes[20+2] = 0x3b;
		bytes[21+2] = 0x3a;
		bytes[22+2] = 0x0a;
		bytes[23+2] = 0x1a;
		bytes[24+2] = 0x08;
		bytes[25+2] = 0x0f;
		
		//正向有功总最大需量
		bytes[26+2] = 0x64;
		bytes[27+2] = 0x00;
		
		//正向有功总最大需量发生时间 分时日月
		bytes[28+2] = 0x1e;
		bytes[29+2] = 0x0a;
		bytes[30+2] = 0x1a;
		bytes[31+2] = 0x08;
		
		//费率1正向有功最大需量
		bytes[32+2] = 0x0a;
		bytes[33+2] = 0x00;
		
		//费率1正向有功最大需量发生时间
		bytes[34+2] = 0x01;
		bytes[35+2] = 0x0a;
		bytes[36+2] = 0x18;
		bytes[37+2] = 0x08;
		
		//费率2正向有功最大需量
		bytes[38+2] = 0x0a;
		bytes[39+2] = 0x00;
		
		//费率2正向有功最大需量发生时间
		bytes[40+2] = 0x03;
		bytes[41+2] = 0x09;
		bytes[42+2] = 0x18;
		bytes[43+2] = 0x08;
		
		//费率3正向有功最大需量
		bytes[44+2] = 0x1d;
		bytes[45+2] = 0x00;
		
		//费率3正向有功最大需量发生时间
		bytes[46+2] = 0x03;
		bytes[47+2] = 0x09;
		bytes[48+2] = 0x18;
		bytes[49+2] = 0x08;
		
		//费率4正向有功最大需量
		bytes[50+2] = 0x14;
		bytes[51+2] = 0x00;
		
		//费率4正向有功最大需量发生时间
		bytes[52+2] = 0x03;
		bytes[53+2] = 0x09;
		bytes[54+2] = 0x18;
		bytes[55+2] = 0x08;		
		
		//费率5正向有功最大需量
		bytes[56+2] = 0x14;
		bytes[57+2] = 0x00;
		
		//费率5正向有功最大需量发生时间
		bytes[58+2] = 0x17;
		bytes[59+2] = 0x09;
		bytes[60+2] = 0x18;
		bytes[61+2] = 0x08;		
		
		//费率6正向有功最大需量
		bytes[62+2] = 0x0a;
		bytes[63+2] = 0x00;
		
		//费率6正向有功最大需量发生时间
		bytes[64+2] = 0x1d;
		bytes[65+2] = 0x08;
		bytes[66+2] = 0x18;
		bytes[67+2] = 0x08;				
	
		//附加信息AUX
		bytes[68+2] = 0x1d;
		bytes[69+2] = 0x00;
		
		//帧校验和
		bytes[70+2] = 0x2b;
		
		bytes[71+2] = 0x16;
		return bytes;
	}
	
	private synchronized static byte[] getClass3_ERC5_new() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[40];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1d;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x13;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x00;
		
		//应用程序功能码
		bytes[10+2] = 0x0e;
		//帧序列
		bytes[11+2] = 0x60;
		
		
		//数据单元标识：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
		
		//数据单元：
		//当前重要事件计数器EC1
		bytes[16+2] = 0x00;
		
		//当前一般事件计数器EC2
		bytes[17+2] = 0x02;
		
		//本帧报文传送的事件记录起始指针Pm
		bytes[18+2] = 0x01;
		
		//请求事件记录结束指针Pn
		bytes[19+2] = 0x03;
		
		//ERC=5
		bytes[20+2] = 0x05;
		
		//长度Le
		bytes[21+2] = 0x0c;
		
		//跳闸时间：分时日月年
		bytes[22+2] = 0x24;
		bytes[23+2] = 0x0a;
		bytes[24+2] = 0x0b;
		bytes[25+2] = 0x0a;
		bytes[26+2] = 0x0f;
		
		//D15～D12：备用	D11～D0：pn（测量点号1～2048）	BIN	2
		bytes[27+2] = 0x00;
		bytes[28+2] = 0x0a;
		
		//跳闸轮次
		bytes[29+2] = 0x05;
		
		//跳闸时功率（当前总有功功率）
		bytes[30+2] = 0x64;
		bytes[31+2] = 0x00;
		
		//跳闸后2分钟的功率（当前总有功功率）
		bytes[32+2] = 0x50;
		bytes[33+2] = 0x00;
		
		
		//附加信息AUX
		bytes[34+2] = 0x00;
		bytes[35+2] = 0x01;
		
		//帧校验和
		bytes[36+2] = 0x2b;
		
		bytes[37+2] = 0x16;
		return bytes;
	}
	
	private synchronized static byte[] getClass3_ERC910_new() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[78];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1d;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x13;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x00;
		//应用程序功能码
		bytes[10+2] = 0x0e;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
		
		//数据单元：
		//当前重要事件计数器EC1
		bytes[16+2] = 0x00;
		
		//当前一般事件计数器EC2
		bytes[17+2] = 0x02;
		
		//本帧报文传送的事件记录起始指针Pm
		bytes[18+2] = 0x01;
		
		//请求事件记录结束指针Pn
		bytes[19+2] = 0x03;
		
		//ERC=9
		bytes[20+2] = 0x09;
		//长度Le
		bytes[21+2] = 0x18;
		//跳闸时间：分时日月年
		bytes[22+2] = 0x3b;
		bytes[23+2] = 0x0c;
		bytes[24+2] = 0x0b;
		bytes[25+2] = 0x0a;
		bytes[26+2] = 0x0f;
		//D15～D12：备用	D11～D0：pn（测量点号1～2048）	BIN	2
		bytes[27+2] = 0x00;
		bytes[28+2] = 0x0b;
		//异常标志
		bytes[29+2] = 0x57;
		//发生时的Ua/Uab
		bytes[30+2] = 0x00;
		bytes[31+2] = 0x64;
		//发生时的Ub
		bytes[32+2] = 0x00;
		bytes[33+2] = 0x32;
		//发生时的Uc/Ucb
		bytes[34+2] = 0x00;
		bytes[35+2] = 0x46;
		//发生时的Ia
		bytes[36+2] = 0x00;
		bytes[37+2] = 0x28;
		//发生时的Ib
		bytes[38+2] = 0x00;
		bytes[39+2] = 0x3c;
		//发生时的Ic
		bytes[40+2] = 0x00;
		bytes[41+2] = 0x50;
		//发生时数字配电终端正向有功总电能示值
		bytes[42+2] = 0x00;
		bytes[43+2] = 0x00;
		bytes[44+2] = 0x00;
		bytes[45+2] = (byte) 0xf0;
		
		//ERC=10
		bytes[46+2] = 0x0a;
		//长度Le
		bytes[47+2] = 0x18;
		//跳闸时间：分时日月年
		bytes[48+2] = 0x3a;
		bytes[49+2] = 0x0f;
		bytes[50+2] = 0x09;
		bytes[51+2] = 0x0a;
		bytes[52+2] = 0x0f;
		//D15～D12：备用	D11～D0：pn（测量点号1～2048）	BIN	2
		bytes[53+2] = 0x00;
		bytes[54+2] = 0x08;
		//异常标志
		bytes[55+2] = 0x57;
		//发生时的Ua/Uab
		bytes[56+2] = 0x00;
		bytes[57+2] = 0x0f;
		//发生时的Ub
		bytes[58+2] = 0x00;
		bytes[59+2] = 0x19;
		//发生时的Uc/Ucb
		bytes[60+2] = 0x00;
		bytes[61+2] = 0x23;
		//发生时的Ia
		bytes[62+2] = 0x00;
		bytes[63+2] = 0x14;
		//发生时的Ib
		bytes[64+2] = 0x00;
		bytes[65+2] = 0x28;
		//发生时的Ic
		bytes[66+2] = 0x00;
		bytes[67+2] = 0x3c;
		//发生时数字配电终端正向有功总电能示值
		bytes[68+2] = 0x00;
		bytes[69+2] = 0x00;
		bytes[70+2] = 0x00;
		bytes[71+2] = 0x78;
		
	
		//附加信息AUX
		bytes[72+2] = 0x00;
		bytes[73+2] = 0x01;
		//帧校验和
		bytes[74+2] = 0x2b;
		
		bytes[75+2] = 0x16;
		return bytes;
	}

	@SuppressWarnings("unused")
	private synchronized static byte[] getConfirmDenyBytes_new() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
		//应用程序功能码
		bytes[10] = 0x04;
		//帧序列
		bytes[11] = 0x60;
		
		//数据单元标识：
		bytes[12] = 0x01;
		bytes[13] = 0x01;
		bytes[14] = 0x01;
		bytes[15] = 0x00;
	
		
		//附加信息AUX
		bytes[16] = 0x00;
		bytes[17] = 0x01;
		
		//帧校验和
		bytes[18] = 0x2b;
		
		bytes[19] = 0x16;
		return bytes;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}