package demo.everything.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TCP协议主动上传模拟器
 * 
 * @author DIAOPG
 * @date 2015年8月18日
 */
public class TCP_Server_Initiative {

	public static void main(String[] args) {
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(5);
		Thread thread1 = new Thread(new ClientSocketsThread_Initiative());
		Thread thread2 = new Thread(new ClientSocketsThread_Initiative());
		Thread thread3 = new Thread(new ClientSocketsThread_Initiative());
		Thread thread4 = new Thread(new ClientSocketsThread_Initiative());
		Thread thread5 = new Thread(new ClientSocketsThread_Initiative());
		exec.scheduleAtFixedRate(thread1, 1000, 200, TimeUnit.MILLISECONDS);
		exec.scheduleAtFixedRate(thread2, 1000, 200, TimeUnit.MILLISECONDS);
		exec.scheduleAtFixedRate(thread3, 1000, 200, TimeUnit.MILLISECONDS);
		exec.scheduleAtFixedRate(thread4, 1000, 200, TimeUnit.MILLISECONDS);
		exec.scheduleAtFixedRate(thread5, 1000, 200, TimeUnit.MILLISECONDS);
		System.out.println("TCP 主动上传模拟器启动成功！");
	}
}

class ClientSocketsThread_Initiative implements Runnable {
	/*private Socket clientSocket; // Socket connect to client
	
	public ClientSocketsThread_Initiative(Socket clientSocket) {
		super();
		synchronized (clientSocket) {
			this.clientSocket = clientSocket;
		}
	}*/

	public ClientSocketsThread_Initiative() {
		super();
	}	
	
	@Override
	public void run() {
		handleClientSockets();
	}	
	
	/**
	 * 主动上传
	 * @param clientSocket
	 * @author DIAOPG
	 * @date 2015年8月18日
	 */
	public static void handleClientSockets(){
		OutputStream os = null;
		DataOutputStream dos = null;
		Socket clientSocket = null;
		try {
			byte[] byteArray = getRandomInitiativeByteArray();
			//byte[] byteArray = getMultiFResposeBytes();
			clientSocket = new Socket("10.1.5.197", 10000);
			//clientSocket.setSoLinger(true, 0);
			os = clientSocket.getOutputStream();
			dos = new DataOutputStream(os);
			dos.write(byteArray);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if (os!=null)
					os.close();
				if (dos!=null)
					dos.close();
				if (clientSocket!=null)
					clientSocket.close();	//关闭clientSocket避免阻塞
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据随机数获取上传报文
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月17日
	 */
	private synchronized static byte[] getRandomInitiativeByteArray() {
		int random = getRandom();
		byte[] byteArray = null;
		switch (random) {
		case 1:
			byteArray = getMultiFResposeBytes();
			System.out.println("byteArray = getMultiFResposeBytes();");
			break;
		case 2:
			byteArray = getSingle_F5Similar_Bytes();
			System.out.println("byteArray = getSingle_F5Similar_Bytes();");
			break;
		case 3:
			byteArray = getSingle_F81Similar_Bytes();
			System.out.println("byteArray = getSingle_F81Similar_Bytes();");
			break;
		case 4:
			byteArray = getSingle_F121Similar_Bytes();
			System.out.println("byteArray = getSingle_F121Similar_Bytes();");
			break;
		case 5:
			byteArray = getSingle_F185Similar_Bytes();
			System.out.println("byteArray = getSingle_F185Similar_Bytes();");
			break;
		case 6:
			byteArray = getERC5Bytes();
			System.out.println("byteArray = getERC5Bytes();");
			break;
		case 7:
			byteArray = getERC910Bytes();
			System.out.println("byteArray = getERC910Bytes();");
			break;
		case 8:
			byteArray = getResposeClass1Bytes();
			System.out.println("byteArray = getResposeClass1Bytes();");
			break;				
		default:
			byteArray = getSingle_F5Similar_Bytes();
			System.out.println("Default");
			break;
		}
		return byteArray;
	}

	/**
	 * 获取1-8的随机数
	 * @return
	 * @author DIAOPG
	 * @date 2015年9月17日
	 */
	private static int getRandom() {
		Random random = new Random();
		int i = random.nextInt(8)+1;
		return i;
	}

	/**
	 * 一类数据主动上报
	 * F2 F4 Data测试
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月31日
	 */
	private static byte[] getResposeClass1Bytes() {
			// TODO Auto-generated method stub
		    byte[] bytes = new byte[31];
			bytes[0] = 0x68;
			bytes[1] = 0x42;
			bytes[2] = 0x00;
			bytes[3] = 0x68;
			bytes[4] = 0x00;
			
			bytes[5] = 33;
			bytes[6] = 0x00;
			bytes[7] = 23;
			bytes[8] = 0x00;
			bytes[9] = 4;
			
			bytes[10] = 0x0c;
			bytes[11] = 0x60;
		
			//数据单元标识： F2
			bytes[12] = 0x00;
			bytes[13] = 0x01;
			bytes[14] = 0x01;
			bytes[15] = 0x00;
			
			//数据单元：
			//日冻结类数据时标Td_d
			bytes[16] = 0; // 秒取值范围：十进制数0~59
			bytes[17] = 0; // 分取值范围：十进制数0~59
			bytes[18] = 8; // 时取值范围：十进制数0~23
			bytes[19] = 28; // 日取值范围：十进制数1~31
			bytes[20] = 84; // 高四位为星期，取值范围：0~7, 0：无效，1～7依次表示星期一至星期日	低四位为月，取值范围：十进制数1~12
			bytes[21] = 15; //年的低两位，取值范围：十进制数1~99

			//数据单元标识： F4
			bytes[22] = 0x01;
			bytes[23] = 0x01;
			bytes[24] = 0x03;
			bytes[25] = 0x00;
			
			bytes[26] = 3; 
			
			bytes[27] = 0x00;
			bytes[28] = 0x01;
			bytes[29] = 0x01;
			bytes[30] = 0x16;
			return bytes;
		}	

	/**
	 * 事件主动上报报文
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月28日
	 */
	private static byte[] getERC910Bytes() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[76];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 30;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 23;
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
		
		//ERC=9
		bytes[20] = 9;
		//长度Le
		bytes[21] = 24;
		//跳闸时间：分时日月年
		bytes[22] = 45;
		bytes[23] = 10;
		bytes[24] = 30;
		bytes[25] = 8;
		bytes[26] = 15;
		//D15～D12：备用	D11～D0：pn（测量点号1～2048）	BIN	2
		bytes[27] = 0;
		bytes[28] = 11;
		//异常标志
		bytes[29] = 0x57;
		//发生时的Ua/Uab
		bytes[30] = 0;
		bytes[31] = 100;
		//发生时的Ub
		bytes[32] = 0x00;
		bytes[33] = 50;
		//发生时的Uc/Ucb
		bytes[34] = 0x00;
		bytes[35] = 70;
		//发生时的Ia
		bytes[36] = 0x00;
		bytes[37] = 40;
		//发生时的Ib
		bytes[38] = 0x00;
		bytes[39] = 60;
		//发生时的Ic
		bytes[40] = 0x00;
		bytes[41] = 80;
		//发生时数字配电终端正向有功总电能示值
		bytes[42] = 0x00;
		bytes[43] = 0;
		bytes[44] = 0x00;
		bytes[45] = (byte) 240;
		
		//ERC=10
		bytes[46] = 10;
		//长度Le
		bytes[47] = 24;
		//跳闸时间：分时日月年
		bytes[48] = 45;
		bytes[49] = 15;
		bytes[50] = 30;
		bytes[51] = 8;
		bytes[52] = 15;
		//D15～D12：备用	D11～D0：pn（测量点号1～2048）	BIN	2
		bytes[53] = 0;
		bytes[54] = 8;
		//异常标志
		bytes[55] = 0x57;
		//发生时的Ua/Uab
		bytes[56] = 0;
		bytes[57] = 15;
		//发生时的Ub
		bytes[58] = 0x00;
		bytes[59] = 25;
		//发生时的Uc/Ucb
		bytes[60] = 0x00;
		bytes[61] = 35;
		//发生时的Ia
		bytes[62] = 0x00;
		bytes[63] = 20;
		//发生时的Ib
		bytes[64] = 0x00;
		bytes[65] = 40;
		//发生时的Ic
		bytes[66] = 0x00;
		bytes[67] = 60;
		//发生时数字配电终端正向有功总电能示值
		bytes[68] = 0x00;
		bytes[69] = 0;
		bytes[70] = 0x00;
		bytes[71] = (byte) 120;
		

		//附加信息AUX
		bytes[72] = 0x00;
		bytes[73] = 0x01;
		//帧校验和
		bytes[74] = 0x2b;
		
		bytes[75] = 0x16;
		return bytes;
	}	
	
	/**
	 * 事件主动上报报文
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月28日
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
		bytes[7] = 23;
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
	 * F5类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月13日
	 */
	private static byte[] getSingle_F5Similar_Bytes() {
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
		bytes[7] = 23;
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
		bytes[16] = 0x0f;
		bytes[17] = 0x08;
		bytes[18] = 0x18;
		bytes[19] = 0x0f;
		
		//数据单元：
		//日正向有功总电能量
		bytes[20] = 0x00;
		bytes[21] = 0x00;
		bytes[22] = 0x00;
		bytes[23] = 0x54;
		
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
	 * F81类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月13日
	 */
	private static byte[] getSingle_F81Similar_Bytes() {
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
		bytes[7] = 23;
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
	 * F121类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月13日
	 */
	private static byte[] getSingle_F121Similar_Bytes() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[102];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 28;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 23;
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
		bytes[21] = 19;

		//A相总畸变电压含有率越限日累计时间
		bytes[22] = 0x00;
		bytes[23] = 110;
		
		//A相2次谐波电压含有率越限日累计时间
		bytes[24] = 0x00;
		bytes[25] = 0x05;
		
		//A相3次谐波电压含有率越限日累计时间
		bytes[26] = 0x00;
		bytes[27] = 0x06;
		
		//A相4次谐波电压含有率越限日累计时间
		bytes[28] = 0x00;
		bytes[29] = 0x05;
		
		//A相5次谐波电压含有率越限日累计时间
		bytes[30] = 0x00;
		bytes[31] = 0x06;
		
		//A相6次谐波电压含有率越限日累计时间
		bytes[32] = 0x00;
		bytes[33] = 0x05;
		
		//A相7次谐波电压含有率越限日累计时间
		bytes[34] = 0x00;
		bytes[35] = 0x06;
		
		//A相8次谐波电压含有率越限日累计时间
		bytes[36] = 0x00;
		bytes[37] = 0x05;
		
		//A相9次谐波电压含有率越限日累计时间
		bytes[38] = 0x00;
		bytes[39] = 0x06;		
		
		//A相10次谐波电压含有率越限日累计时间
		bytes[40] = 0x00;
		bytes[41] = 0x05;
		
		//A相11次谐波电压含有率越限日累计时间
		bytes[42] = 0x00;
		bytes[43] = 0x06;
		
		//A相12次谐波电压含有率越限日累计时间
		bytes[44] = 0x00;
		bytes[45] = 0x05;
		
		//A相13次谐波电压含有率越限日累计时间
		bytes[46] = 0x00;
		bytes[47] = 0x06;
		
		//A相14次谐波电压含有率越限日累计时间
		bytes[48] = 0x00;
		bytes[49] = 0x05;
		
		//A相15次谐波电压含有率越限日累计时间
		bytes[50] = 0x00;
		bytes[51] = 0x06;
		
		//A相16次谐波电压含有率越限日累计时间
		bytes[52] = 0x00;
		bytes[53] = 0x05;
		
		//A相17次谐波电压含有率越限日累计时间
		bytes[54] = 0x00;
		bytes[55] = 0x06;
		
		//A相18次谐波电压含有率越限日累计时间
		bytes[56] = 0x00;
		bytes[57] = 18;
		
		//A相19次谐波电压含有率越限日累计时间
		bytes[58] = 0x00;
		bytes[59] = 19;
		
		//A相总畸变电流越限日累计时间
		bytes[60] = 0x00;
		bytes[61] = 15;
		
		//A相2次谐波电流越限日累计时间
		bytes[62] = 0x00;
		bytes[63] = 0x07;
		
		//A相3次谐波电流越限日累计时间
		bytes[64] = 0x00;
		bytes[65] = 0x08;
		
		//A相4次谐波电流越限日累计时间
		bytes[66] = 0x00;
		bytes[67] = 0x07;
		
		//A相5次谐波电流越限日累计时间
		bytes[68] = 0x00;
		bytes[69] = 0x08;

		//A相6次谐波电流越限日累计时间
		bytes[70] = 0x00;
		bytes[71] = 0x07;
		
		//A相7次谐波电流越限日累计时间
		bytes[72] = 0x00;
		bytes[73] = 0x08;
		
		//A相8次谐波电流越限日累计时间
		bytes[74] = 0x00;
		bytes[75] = 0x07;
		
		//A相9次谐波电流越限日累计时间
		bytes[76] = 0x00;
		bytes[77] = 0x08;
		
		//A相10次谐波电流越限日累计时间
		bytes[78] = 0x00;
		bytes[79] = 0x07;
		
		//A相11次谐波电流越限日累计时间
		bytes[80] = 0x00;
		bytes[81] = 0x08;
		
		//A相12次谐波电流越限日累计时间
		bytes[82] = 0x00;
		bytes[83] = 0x07;
		
		//A相13次谐波电流越限日累计时间
		bytes[84] = 0x00;
		bytes[85] = 0x08;
		
		//A相14次谐波电流越限日累计时间
		bytes[86] = 0x00;
		bytes[87] = 0x07;
		
		//A相15次谐波电流越限日累计时间
		bytes[88] = 0x00;
		bytes[89] = 0x08;
		
		//A相16次谐波电流越限日累计时间
		bytes[90] = 0x00;
		bytes[91] = 0x07;
		
		//A相17次谐波电流越限日累计时间
		bytes[92] = 0x00;
		bytes[93] = 0x08;
		
		//A相18次谐波电流越限日累计时间
		bytes[94] = 0x00;
		bytes[95] = 0x07;
		
		//A相19次谐波电流越限日累计时间
		bytes[96] = 0x00;
		bytes[97] = 0x08;
		
		//附加信息AUX
		bytes[98] = 0x00;
		bytes[99] = 0x01;
		
		//帧校验和
		bytes[100] = 0x2b;
		
		bytes[101] = 0x16;
		return bytes;
	}	
	
	/**
	 * F185类指标
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月27日
	 */
	private static byte[] getSingle_F185Similar_Bytes() {
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
		bytes[7] = 23;
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
	 * 处理多个F的情况
	 * @return
	 * @author DIAOPG
	 * @date 2015年8月24日
	 */
	private static byte[] getMultiFResposeBytes() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[88];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = (byte) 0xad;
		bytes[3] = 0x68;
		
		//控制域
		bytes[4] = (byte) 0x80; 
		
		//地址域
		bytes[5] = 33;
		bytes[6] = 0x00;
		bytes[7] = 23;
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
		bytes[16] = 0x0f;
		bytes[17] = 0x08;
		bytes[18] = 0x18;
		bytes[19] = 0x0f;
		
		//数据单元：
		//日正向有功总电能量
		bytes[20] = 0x00;
		bytes[21] = 0x00;
		bytes[22] = 0x00;
		bytes[23] = 0x54;
		
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
		
		//数据单元标识2：
		bytes[48] = 0x00;
		bytes[49] = 0x01;
		bytes[50] = 0x05;
		bytes[51] = 0x00;
		
		//数据单元2：
		//日冻结类数据时标Td_d
		bytes[52] = 0x0f;
		bytes[53] = 0x08;
		bytes[54] = 0x18;
		bytes[55] = 0x01;
		
		//数据单元：
		//日正向有功总电能量
		bytes[56] = 0x00;
		bytes[57] = 0x00;
		bytes[58] = 0x00;
		bytes[59] = 0x55;
		
		//1.费率1值
		bytes[60] = 0x00;
		bytes[61] = 0x00;
		bytes[62] = 0x00;
		bytes[63] = 0x21;
		
		//2.费率2值
		bytes[64] = 0x00;
		bytes[65] = 0x00;
		bytes[66] = 0x00;
		bytes[67] = 0x0d;
		
		//3.费率3值
		bytes[68] = 0x00;
		bytes[69] = 0x00;
		bytes[70] = 0x00;
		bytes[71] = 0x0f;	
	    
		//4.费率4值
		bytes[72] = 0x00;
		bytes[73] = 0x00;
		bytes[74] = 0x00;
		bytes[75] = 0x0d;
		
		//5.费率5值
		bytes[76] = 0x00;
		bytes[77] = 0x00;
		bytes[78] = 0x00;
		bytes[79] = 0x05;		
		
		//6.费率6值
		bytes[80] = 0x00;
		bytes[81] = 0x00;
		bytes[82] = 0x00;
		bytes[83] = 0x06;			
		
		//附加信息AUX
		bytes[84] = 0x00;
		bytes[85] = 0x01;
		
		//帧校验和
		bytes[86] = 0x2b;
		
		bytes[87] = 0x16;
		return bytes;
	}
}