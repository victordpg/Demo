package demo.everything.nettyserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * TCP协议手动采集模拟器
 * <br><b><报文的主站地址不为0></b>
 * 
 * @author DIAOPG
 * @date 2015年10月13日
 */
public class TCPClient_Manual  implements Runnable {
	private static Socket socket;
	
	/**
	 * 用于单独启动该模拟器
	 * 
	 * @param args
	 * @author DIAOPG
	 * @date 2015年10月14日
	 */
	public static void main(String[] args) {
		String host = "10.1.5.197"; //主机IP
		int port = 10000; //主机Port
		Socket socket;
		try {
			socket = new Socket(host, port);
			new Thread(new TCPClient_Manual(socket)).run();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TCPClient_Manual(Socket socket2) {
		socket = socket2;
	}
	
	@Override
	public void run() {
		try {  
			System.out.println("手动采集模拟器启动！");  
            while (true) {
            	InputStream is = socket.getInputStream();
		        int availible = is.available();
				if (availible!=0) {
					handleOutputStream(is);
				}
    		}
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}

	private static void handleOutputStream(InputStream is) {
        byte[] byteWrite2Server = null;
       
        try {
	        DataInputStream dis = new DataInputStream(is); 
	        byte[] byteFromServer = new byte[dis.available()];
	        
	        dis.read(byteFromServer);
	        
            byte[] fnArray = new byte[2];
            byte[] pnArray = new byte[2];
            System.arraycopy(byteFromServer, 16, fnArray, 0, 2);
            System.arraycopy(byteFromServer, 14, pnArray, 0, 2);
            int fn = dByteToInt(fnArray,false);
            int pn = dByteToInt(pnArray,true);
            int afn = byteFromServer[12];
            
            System.out.println("收到TCP Server下发的报文"+"（AFN：" + afn + "; PN: "+pn+"; FN："+fn+"）: "+Arrays.toString(byteFromServer));  

			if (afn == 0x00) {
				// 接收到确认报文，不响应！
			}
			if (afn == 0x01 || afn == 0x04 || afn == 0x05) {
				//获得设置参数的确认报文
				if (afn == 0x04) {
					switch (fn) {
					case 7:
						byteWrite2Server = getConfirm04F7();
						break;
					case 10:
						byteWrite2Server = getConfirm04F10();
						break;	
					case 103:
						if (pn==103) {
							byteWrite2Server = getConfirm04F103();
						}else {
							byteWrite2Server = getConfirm04F103_2();
						}
						break;	
					case 104:
						if(pn==104){
							byteWrite2Server = getConfirm04F104();
						}else {
							byteWrite2Server = getConfirm04F104_2();
						}
						break;	
					case 105:
						byteWrite2Server = getConfirm04F105();
						break;		
					case 106:
						byteWrite2Server = getConfirm04F106();
						break;	
					case 110:
						byteWrite2Server = getConfirm04F110();
						break;	
					case 111:
						byteWrite2Server = getConfirm04F111();
						break;								
					default:
						break;
					}
				}
				//获得控制命令的确认报文
				else if (afn == 0x05) {
					switch (fn) {
					case 29:
						byteWrite2Server = getConfirm05F29();
						break;
					case 31:
						byteWrite2Server = getConfirm05F31();
						break;		
					default:
						byteWrite2Server = getConfirmDenyBytes();
						break;
					}
				}else {
					byteWrite2Server = getConfirmDenyBytes();
				}
			} 
			//三类数据重要事件
			else if (afn == 14 && fn == 1) {
				int fakepn = byteFromServer[19];
				switch (fakepn) {
				case 2:
					byteWrite2Server = getClass3_ERC2();
					break;
				case 3:
					byteWrite2Server = getClass3_ERC3();
					break;
				case 5:
					byteWrite2Server = getClass3_ERC5();
					break;
				case 21:
					byteWrite2Server = getClass3_ERC21();
					break;
				case 42:
					byteWrite2Server = getClass3_ERC42();
					break;
				case 46:
					byteWrite2Server = getClass3_ERC46();
					break;
				default:
					break;
				}
			} 
			//三类数据一般事件
			else if (afn == 14 && fn == 2) {
				byteWrite2Server = getClass3_ERC910();
			} 
			//二类数据部分
			else if (afn == 13) {
				switch (fn) {
				case 5:
					byteWrite2Server = getClass2_F5();
					break;
				case 21:
					byteWrite2Server = getClass2_F21();
					break;
				case 81:
					byteWrite2Server = getClass2_F81();
					break;
				case 121:
					byteWrite2Server = getClass2_F121();
					break;
				case 169:
					byteWrite2Server = getClass2_F169();
					break;
				case 185:
					byteWrite2Server = getClass2_F185();
					break;
				case 193:
					byteWrite2Server = getClass2_F193();
					break;
				case 218:
					byteWrite2Server = getClass2_F218();
					break;
				default:
					break;
				}
			} 
			//查询参数部分
			else if (afn == 10) {
				switch (fn) {
				case 7:
					byteWrite2Server = getParameter_F7();
					break;
				case 103:
					if (pn==103) {
						byteWrite2Server = getParameter_F103_SZPDZD();
					}else {
						byteWrite2Server = getParameter_F103_DQHZ();
					}
					break;
				case 104:
					if (pn==104) {
						byteWrite2Server = getParameter_F104_SZPDZD();
					}else {
						byteWrite2Server = getParameter_F104_DQHZ();
					}
					break;
				case 105:
					if (pn==105) 
						byteWrite2Server = getParameter_F105();
					break;	
				case 106:
					if (pn==106) 
						byteWrite2Server = getParameter_F106();
					break;	
				case 110:
					if (pn==110) 
						byteWrite2Server = getParameter_F110();
					break;	
				case 111:
					if (pn==111) 
						byteWrite2Server = getParameter_F111();
					break;						
				default:
					break;
				}
			}
		
			OutputStream os =  socket.getOutputStream(); 
			DataOutputStream bos = new DataOutputStream(os); 
			if (byteWrite2Server!=null) {
				bos.write(byteWrite2Server);
			    bos.flush(); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static byte[] getParameter_F7() {
		byte[] bytes = new byte[28];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region
		bytes[7] = 0x04;
		bytes[8] = 0x00;
		//collector
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server
		bytes[11] = 0x03;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p0
		bytes[14] = 0x00;
		bytes[15] = 0x00;
		//f7
		bytes[16] = 0x07;
		bytes[17] = 0x00;
		
		//数据单元：
		//抄表日-日期
		bytes[18] = (byte) 0xff;
		bytes[19] = 0x00;
		bytes[20] = 0x00;
		bytes[21] = 0x00;
		//抄表日-时间
		bytes[22] = 0x20;
		bytes[23] = 0x0a;
		
		//附加信息AUX
		bytes[24] = 0x00;
		bytes[25] = 0x01;
		
		//帧校验和
		bytes[26] = 0x2b;
		
		bytes[27] = 0x16;
		return bytes;
	}

	private static byte[] getParameter_F103_SZPDZD() {
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region
		bytes[7] = 0x07;
		bytes[8] = 0x04;
		//collector
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server
		bytes[11] = 0x04;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p103
		bytes[14] = 0x07;
		bytes[15] = 0x0d;
		//f103
		bytes[16] = 0x07;
		bytes[17] = 0x0c;
		
		//数据单元：
		//过压保护状态
		bytes[18] = 0x01;
		//断路器脱扣控制&&故障波形记录设置
		bytes[19] = (byte) 0xc0;
		
		//过压保护整定值 1000
		bytes[20] = (byte) 0xe8;
		bytes[21] = 0x03;
		//过压保护动作延时时间 200
		bytes[22] = (byte) 0xc8;
		bytes[23] = 0x00;
		//过压保护返回值 10000
		bytes[24] = 0x10;
		bytes[25] = 0x27;
		//过压保护返回延时时间 1200
		bytes[26] = (byte) 0xb0;
		bytes[27] = 0x04;
		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		
		bytes[31] = 0x16;
		return bytes;
	}
	
	private static byte[] getParameter_F104_SZPDZD() {
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region：1041
		bytes[7] = 0x11;
		bytes[8] = 0x04;
		//collector：4
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server：4
		bytes[11] = 0x04;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p104
		bytes[14] = 0x08;
		bytes[15] = 0x0d;
		//f104
		bytes[16] = 0x08;
		bytes[17] = 0x0c;
		
		//数据单元：
		//欠压保护状态
		bytes[18] = 0x01;
		//断路器脱扣控制&&故障波形记录设置
		bytes[19] = (byte) 0xc0;
		
		//欠压保护整定值 1000
		bytes[20] = (byte) 0xe8;
		bytes[21] = 0x03;
		//欠压保护动作延时时间200
		bytes[22] = (byte) 0xc8;
		bytes[23] = 0x00;
		//欠压保护返回值 10000
		bytes[24] = 0x10;
		bytes[25] = 0x27;
		//欠压保护返回延时时间 1200
		bytes[26] = (byte) 0xb0;
		bytes[27] = 0x04;
		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		
		bytes[31] = 0x16;
		return bytes;
	}	
	
	private static byte[] getParameter_F105() {
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region：105
		bytes[7] = 0x69;
		bytes[8] = 0x00;
		//collector：4
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server：4
		bytes[11] = 0x04;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p105
		bytes[14] = 0x09;
		bytes[15] = 0x0d;
		//f105
		bytes[16] = 0x09;
		bytes[17] = 0x0c;
		
		//数据单元：
		//电流不平衡保护状态
		bytes[18] = 0x01;
		//断路器脱扣控制 1 &&故障波形记录设置 0
		bytes[19] = (byte) 0x40;
		
		//电流不平衡率保护整定值 20
		bytes[20] = 0x14;
		bytes[21] = 0x00;
		//电流不平衡保护动作延时时间 100
		bytes[22] = (byte) 0x64;
		bytes[23] = 0x00;
		//电流不平衡率保护返回值 30
		bytes[24] = 0x1e;
		bytes[25] = 0x00;
		//电流不平衡保护返回延时时间 3600
		bytes[26] = (byte) 0x10;
		bytes[27] = 0x0e;
		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		
		bytes[31] = 0x16;
		return bytes;
	}
	
	private static byte[] getParameter_F106() {
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region：106
		bytes[7] = 0x6a;
		bytes[8] = 0x00;
		//collector：4
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server：4
		bytes[11] = 0x04;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p106
		bytes[14] = 0x0a;
		bytes[15] = 0x0d;
		//f106
		bytes[16] = 0x0a;
		bytes[17] = 0x0c;
		
		//数据单元：
		//电压不平衡保护状态
		bytes[18] = 0x01;
		//断路器脱扣控制 1 &&故障波形记录设置 0
		bytes[19] = (byte) 0x40;
		
		//电压不平衡率保护整定值 20
		bytes[20] = 0x14;
		bytes[21] = 0x00;
		//电压不平衡保护动作延时时间 100
		bytes[22] = (byte) 0x64;
		bytes[23] = 0x00;
		//电压不平衡率保护返回值 30
		bytes[24] = 0x1e;
		bytes[25] = 0x00;
		//电压不平衡保护返回延时时间 3600
		bytes[26] = (byte) 0x10;
		bytes[27] = 0x0e;
		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		
		bytes[31] = 0x16;
		return bytes;
	}	
	
	private static byte[] getParameter_F110() {
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region：110
		bytes[7] = 0x6e;
		bytes[8] = 0x00;
		//collector：4
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server：4
		bytes[11] = 0x04;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p110
		bytes[14] = 0x06;
		bytes[15] = 0x0e;
		//f110
		bytes[16] = 0x06;
		bytes[17] = 0x0d;
		
		//数据单元：
		//最小频率保护状态
		bytes[18] = 0x01;
		//断路器脱扣控制 1 &&故障波形记录设置 0
		bytes[19] = (byte) 0x40;
		
		//最小频率保护设定值 450
		bytes[20] = (byte) 0xc2;
		bytes[21] = 0x01;
		//最小频率保护设定延时 300
		bytes[22] = 0x2c;
		bytes[23] = 0x01;
		//最小频率保护返回值 4400
		bytes[24] = 0x30;
		bytes[25] = 0x11;
		//最小频率保护返回延时 3600
		bytes[26] = (byte) 0x10;
		bytes[27] = 0x0e;
		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		
		bytes[31] = 0x16;
		return bytes;
	}	
	
	private static byte[] getParameter_F111() {
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region：111
		bytes[7] = 0x6f;
		bytes[8] = 0x00;
		//collector：4
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server：4
		bytes[11] = 0x04;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p111
		bytes[14] = 0x07;
		bytes[15] = 0x0e;
		//f111
		bytes[16] = 0x07;
		bytes[17] = 0x0d;
		
		//数据单元：
		//最小频率保护状态
		bytes[18] = 0x01;
		//断路器脱扣控制 1 &&故障波形记录设置 0
		bytes[19] = (byte) 0x40;
		
		//最大频率保护设定值 450
		bytes[20] = (byte) 0xc2;
		bytes[21] = 0x01;
		//最大频率保护设定延时 300
		bytes[22] = 0x2c;
		bytes[23] = 0x01;
		//最大频率保护返回值 4400
		bytes[24] = 0x30;
		bytes[25] = 0x11;
		//最大频率保护返回延时 3600
		bytes[26] = (byte) 0x10;
		bytes[27] = 0x0e;
		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		
		bytes[31] = 0x16;
		return bytes;
	}		
		
	//68 5a 00 5a 00 68 4b 08 04 04 00 04 04 60 01 01 07 0c 01 00 03 e8 00 c8 27 10 04 b0 77 16 
	private static byte[] getParameter_F103_DQHZ() {
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region: 1032
		bytes[7] = 0x08;
		bytes[8] = 0x04;
		//collector: 4
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server: 4
		bytes[11] = 0x04;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p1
		bytes[14] = 0x01;
		bytes[15] = 0x01;
		//f103
		bytes[16] = 0x07;
		bytes[17] = 0x0c;
		
		//数据单元：
		//过压保护状态
		bytes[18] = 0x01;
		//备用
		bytes[19] = 0X00;
		
		//过压保护整定值 2000
		bytes[20] = (byte) 0xd0;
		bytes[21] = 0x07;
		//过压保护动作延时时间 400
		bytes[22] = (byte) 0x90;
		bytes[23] = 0x01;
		//过压保护返回值 10000
		bytes[24] = 0x10;
		bytes[25] = 0x27;
		//过压保护返回延时时间 1200
		bytes[26] = (byte) 0xb0;
		bytes[27] = 0x04;
		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		
		bytes[31] = 0x16;
		return bytes;
	}	
	
	private static byte[] getParameter_F104_DQHZ() {
		byte[] bytes = new byte[32];
		bytes[0] = 0x68;
		bytes[1] = 0x14;
		bytes[2] = 0x0;
		bytes[3] = 0x14;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		//region: 1042
		bytes[7] = 0x12;
		bytes[8] = 0x04;
		//collector: 4
		bytes[9] = 0x04;
		bytes[10] = 0x00;
		//main server: 4
		bytes[11] = 0x04;
		
		//应用程序功能码
		bytes[12] = 0x0a;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		//p1
		bytes[14] = 0x01;
		bytes[15] = 0x01;
		//f104
		bytes[16] = 0x08;
		bytes[17] = 0x0c;
		
		//数据单元：
		//欠压保护状态
		bytes[18] = 0x01;
		//备用
		bytes[19] = 0X00;
		
		//欠压保护整定值 2000
		bytes[20] = (byte) 0xd0;
		bytes[21] = 0x07;
		//欠压保护动作延时时间 400
		bytes[22] = (byte) 0x90;
		bytes[23] = 0x01;
		//欠压保护动作延时时间 10000
		bytes[24] = 0x10;
		bytes[25] = 0x27;
		//欠压保护返回延时时间 1200
		bytes[26] = (byte) 0xb0;
		bytes[27] = 0x04;
		
		//附加信息AUX
		bytes[28] = 0x00;
		bytes[29] = 0x01;
		
		//帧校验和
		bytes[30] = 0x2b;
		bytes[31] = 0x16;
		return bytes;
	}		
	
	private static byte[] getClass2_F5() {
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
		bytes[7] = 0x21;
		bytes[8] = 0x00;
		bytes[9] = 0x17;
		bytes[10] = 0x00;
		bytes[11] = 0x03;
		
		//应用程序功能码
		bytes[12] = 0x0d;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		bytes[14] = 0x01;
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


	private static byte[] getClass2_F21() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[52];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = (byte) 0xad;
		bytes[1+2] = 0x00;
		bytes[2+2] = (byte) 0xad;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
		//应用程序功能码
		bytes[10+2] = 0x0d;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x05;
		bytes[15+2] = 0x02;
		
		//数据单元：
		//月冻结类数据时标Td_m
		bytes[16+2] = 0x09;
		bytes[17+2] = 0x0a;
		
		//月正向有功总电能量
		bytes[18+2] = 0x00;
		bytes[19+2] = 0x00;
		bytes[20+2] = 0x00;
		bytes[21+2] = 0x51;
		
		//1.费率1值
		bytes[22+2] = 0x00;
		bytes[23+2] = 0x00;
		bytes[24+2] = 0x00;
		bytes[25+2] = 0x0a;
		
		//2.费率2值
		bytes[26+2] = 0x00;
		bytes[27+2] = 0x00;
		bytes[28+2] = 0x00;
		bytes[29+2] = 0x14;
		
		//3.费率3值
		bytes[30+2] = 0x00;
		bytes[31+2] = 0x00;
		bytes[32+2] = 0x00;
		bytes[33+2] = 0x0d;	
	    
		//4.费率4值
		bytes[34+2] = 0x00;
		bytes[35+2] = 0x00;
		bytes[36+2] = 0x00;
		bytes[37+2] = 0x17;
		
		//5.费率5值
		bytes[38+2] = 0x00;
		bytes[39+2] = 0x00;
		bytes[40+2] = 0x00;
		bytes[41+2] = 0x0a;		
		
		//6.费率6值
		bytes[42+2] = 0x00;
		bytes[43+2] = 0x00;
		bytes[44+2] = 0x00;
		bytes[45+2] = 0x0a;	
		
		//附加信息AUX
		bytes[46+2] = 0x00;
		bytes[47+2] = 0x01;
		
		//帧校验和
		bytes[48+2] = 0x2b;
		
		bytes[49+2] = 0x16;
		return bytes;
	}		
	
	private static byte[] getClass2_F81() {
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
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
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
	
	private static byte[] getClass2_F218() {
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
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
		//应用程序功能码
		bytes[10+2] = 0x0d;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识{P1,F81}：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x02;
		bytes[15+2] = 0x1b;
		
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
	
	private static byte[] getClass2_F121() {
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
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
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

	
	private static byte[] getClass2_F185() {
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
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
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
	
	private static byte[] getClass2_F193() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[72];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1c;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1c;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
		//应用程序功能码
		bytes[10+2] = 0x0d;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x01;
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x18;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[18] = 0x0a;
		bytes[19] = 0x0f;
		
		//数据采集器抄表时间 秒分时日月年
		bytes[20] = 0x3b;
		bytes[21] = 0x3a;
		bytes[22] = 0x0a;
		bytes[23] = 0x1a;
		bytes[24] = 0x0a;
		bytes[25] = 0x0f;
		
		//正向有功总最大需量
		bytes[26] = 0x64;
		bytes[27] = 0x00;
		
		//正向有功总最大需量发生时间 分时日月
		bytes[28] = 0x1e;
		bytes[29] = 0x0a;
		bytes[30] = 0x1a;
		bytes[31] = 0x08;
		
		//费率1正向有功最大需量
		bytes[32] = 0x0a;
		bytes[33] = 0x00;
		
		//费率1正向有功最大需量发生时间
		bytes[34] = 0x01;
		bytes[35] = 0x0a;
		bytes[36] = 0x18;
		bytes[37] = 0x08;
		
		//费率2正向有功最大需量
		bytes[38] = 0x0a;
		bytes[39] = 0x00;
		
		//费率2正向有功最大需量发生时间
		bytes[40] = 0x03;
		bytes[41] = 0x09;
		bytes[42] = 0x18;
		bytes[43] = 0x08;
		
		//费率3正向有功最大需量
		bytes[44] = 0x1d;
		bytes[45] = 0x00;
		
		//费率3正向有功最大需量发生时间
		bytes[46] = 0x03;
		bytes[47] = 0x09;
		bytes[48] = 0x18;
		bytes[49] = 0x08;
		
		//费率4正向有功最大需量
		bytes[50] = 0x14;
		bytes[51] = 0x00;
		
		//费率4正向有功最大需量发生时间
		bytes[52] = 0x03;
		bytes[53] = 0x09;
		bytes[54] = 0x18;
		bytes[55] = 0x08;		
		
		//费率5正向有功最大需量
		bytes[56] = 0x14;
		bytes[57] = 0x00;
		
		//费率5正向有功最大需量发生时间
		bytes[58] = 0x17;
		bytes[59] = 0x09;
		bytes[60] = 0x18;
		bytes[61] = 0x08;		
		
		//费率6正向有功最大需量
		bytes[62] = 0x0a;
		bytes[63] = 0x00;
		
		//费率6正向有功最大需量发生时间
		bytes[64] = 0x1d;
		bytes[65] = 0x08;
		bytes[66] = 0x18;
		bytes[67] = 0x08;				
	
		//附加信息AUX
		bytes[68] = 0x1d;
		bytes[69] = 0x00;
		
		//帧校验和
		bytes[70] = 0x2b;
		
		bytes[71] = 0x16;
		return bytes;
	}	
	
	private static byte[] getClass2_F169() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[60];
		bytes[0] = 0x68;
		bytes[1] = 0x2e;
		bytes[2] = 0x0;
		bytes[3] = 0x2e;
		bytes[4] = 0x0;		
		bytes[5] = 0x68;
		//控制域
		bytes[6] = (byte) 0x80; 
		//地址域
		bytes[7] = 0x21;
		bytes[8] = 0x00;
		bytes[9] = 0x17;
		bytes[10] = 0x00;
		bytes[11] = 0x03;
		
		//应用程序功能码
		bytes[12] = 0x0d;
		//帧序列
		bytes[13] = 0x60;
		
		//数据单元标识：
		bytes[14] = 0x00;
		bytes[15] = 0x01;
		bytes[16] = 0x01;
		bytes[17] = 0x15;
		
		//数据单元：
		//日冻结类数据时标Td_d
		bytes[18] = 0x10;
		bytes[19] = 0x17;		
		bytes[20] = 0x0a;
		bytes[21] = 0x0f;
		
		bytes[22] = 0x09;
		bytes[23] = 0x0b;		
		bytes[24] = 0x0a;
		bytes[25] = 0x12;
		bytes[26] = 0x0a;
		bytes[27] = 0x0f;
		
		//数据单元：
		//日正向有功总电能量
		bytes[22+6] = 0x3c;
		bytes[23+6] = 0x00;
		bytes[24+6] = 0x00;
		bytes[25+6] = 0x0;
		
		//1.费率1值
		bytes[26+6] = 0x0a;
		bytes[27+6] = 0x00;
		bytes[28+6] = 0x00;
		bytes[29+6] = 0x0;
		
		//2.费率2值
		bytes[30+6] = 0x05;
		bytes[31+6] = 0x00;
		bytes[32+6] = 0x00;
		bytes[33+6] = 0x00;
		
		//3.费率3值
		bytes[34+6] = 0x0f;
		bytes[35+6] = 0x00;
		bytes[36+6] = 0x00;
		bytes[37+6] = 0x00;	
	    
		//4.费率4值
		bytes[38+6] = 0x04;
		bytes[39+6] = 0x00;
		bytes[40+6] = 0x00;
		bytes[41+6] = 0x00;
		
		//5.费率5值
		bytes[42+6] = 0x10;
		bytes[43+6] = 0x00;
		bytes[44+6] = 0x00;
		bytes[45+6] = 0x00;		
		
		//6.费率6值
		bytes[46+6] = 0x0a;
		bytes[47+6] = 0x00;
		bytes[48+6] = 0x00;
		bytes[49+6] = 0x00;	
		
		//附加信息AUX
		bytes[50+6] = 0x00;
		bytes[51+6] = 0x01;
		
		//帧校验和
		bytes[52+6] = 0x2b;
		
		bytes[53+6] = 0x16;
		return bytes;
	}	
	
	private static byte[] getClass3_ERC5() {
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
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x06;
		
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

	private static byte[] getClass3_ERC2() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[34];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1d;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x06;
		
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
		bytes[19+2] = 0x02;
		
		//ERC=2
		bytes[20+2] = 0x02;
		
		//长度Le
		bytes[21+2] = 0x0c;
		
		//跳闸时间：分时日月年
		bytes[22+2] = 0x24;
		bytes[23+2] = 0x0a;
		bytes[24+2] = 0x0b;
		bytes[25+2] = 0x0a;
		bytes[26+2] = 0x0f;
		
		//事件标志
		bytes[27+2] = 0x09;
		
		//附加信息AUX
		bytes[28+2] = 0x00;
		bytes[29+2] = 0x01;
		
		//帧校验和
		bytes[30+2] = 0x2b;
		
		bytes[31+2] = 0x16;
		return bytes;
	}	
	
	private static byte[] getClass3_ERC21() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[34];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1d;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x06;
		
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
		bytes[19+2] = 0x02;
		
		//ERC=21
		bytes[20+2] = 0x15;
		
		//长度Le
		bytes[21+2] = 0x06;
		
		//跳闸时间：分时日月年
		bytes[22+2] = 0x24;
		bytes[23+2] = 0x0f;
		bytes[24+2] = 0x0b;
		bytes[25+2] = 0x0a;
		bytes[26+2] = 0x0f;
		
		//数据采集器故障编码
		bytes[27+2] = 0x01;
		
		//附加信息AUX
		bytes[28+2] = 0x00;
		bytes[29+2] = 0x01;
		
		//帧校验和
		bytes[30+2] = 0x2b;
		
		bytes[31+2] = 0x16;
		return bytes;
	}
	
	private static byte[] getClass3_ERC42() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[44];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1d;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x06;
		
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
		bytes[19+2] = 0x02;
		
		//ERC=42
		bytes[20+2] = 0x2a;
		
		//长度Le
		bytes[21+2] = 0x0e;
		
		//跳闸时间：分时日月年
		bytes[22+2] = 0x24;
		bytes[23+2] = 0x0f;
		bytes[24+2] = 0x02;
		bytes[25+2] = 0x0a;
		bytes[26+2] = 0x0f;
		
		//D15～D12：备用	D11～D0：pn（测量点号1～2048）	BIN	2
		bytes[27+2] = 0x00;
		bytes[28+2] = 0x0a;
		
		//越限标志
		bytes[29+2] = 0x01;
		
		//发生时的中性线电流值In（A）
		bytes[30+2] = 0x0c;
		bytes[31+2] = 0x00;
		
		//发生时的Ia
		bytes[32+2] = 0x03;
		bytes[33+2] = 0x00;
		
		//发生时的Ib
		bytes[34+2] = 0x04;
		bytes[35+2] = 0x00;
		
		//发生时的Ic
		bytes[36+2] = 0x05;
		bytes[37+2] = 0x00;
		
		//附加信息AUX
		bytes[38+2] = 0x00;
		bytes[39+2] = 0x01;
		
		//帧校验和
		bytes[40+2] = 0x2b;
		
		bytes[41+2] = 0x16;
		return bytes;
	}	
		
	private static byte[] getClass3_ERC3() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[42];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1d;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x06;
		
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
		bytes[19+2] = 0x02;
		
		//ERC=3
		bytes[20+2] = 0x03;
		
		//长度Le=14
		bytes[21+2] = 0x0e;
		
		//跳闸时间：分时日月年
		bytes[22+2] = 0x24;
		bytes[23+2] = 0x0a;
		bytes[24+2] = 0x0b;
		bytes[25+2] = 0x0a;
		bytes[26+2] = 0x0f;
		
		//启动站地址
		bytes[27+2] = 0x09;
		
		bytes[30] = 0x00;
		bytes[31] = 0x01;
		bytes[32] = 0x03;
		bytes[33] = 0x00;
		
		bytes[34] = 0x00;
		bytes[35] = 0x01;
		bytes[36] = 0x04;
		bytes[37] = 0x00;
		
		//附加信息AUX
		bytes[39] = 0x00;
		bytes[39] = 0x01;
		
		//帧校验和
		bytes[40] = 0x2b;
		
		bytes[41] = 0x16;
		return bytes;
	}	
	
	private static byte[] getClass3_ERC46() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[39];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[1+2] = 0x00;
		bytes[2+2] = 0x1d;		
		bytes[3+2] = 0x68;
		//控制域
		bytes[4+2] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x07;
		
		//应用程序功能码
		bytes[10+2] = 0x0e;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识
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
		
		//ERC=46
		bytes[20+2] = 0x2e;
		
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
		
		//RoudNo分路号
		bytes[29+2] = 0x01;
		
		//EventNo事件编号
		bytes[30+2] = 0x0b;
		
		//E事件内容（新）
		bytes[31+2] = 0x00;
		bytes[32+2] = 0x50;
		
		//附加信息AUX
		bytes[33+2] = 0x00;
		bytes[34+2] = 0x01;
		
		//帧校验和
		bytes[35+2] = 0x2b;
		
		bytes[36+2] = 0x16;
		return bytes;
	}	
	
	private static byte[] getClass3_ERC910() {
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
		bytes[5+2] = 0x21;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x17;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x06;
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

	private static byte[] getConfirmDenyBytes() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[18];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x17;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x28;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x01;
		
		//应用程序功能码
		bytes[10+2] = 0x04;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//P1
		bytes[12+2] = 0x01;
		bytes[13+2] = 0x01;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}	
	
	private static byte[] getConfirm04F7() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x04;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x03;
		
		//应用程序功能码
		bytes[10+2] = 0x04;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//P0
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x00;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}	

	private static byte[] getConfirm04F10() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x17;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x28;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x01;
		
		//应用程序功能码
		bytes[10+2] = 0x04;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//P0
		bytes[12+2] = 0x00;
		bytes[13+2] = 0x00;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}
	
	private static byte[] getConfirm05F29() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x1d;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x5;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x05;
		
		//应用程序功能码
		bytes[10+2] = 0x00;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//P1
		bytes[12+2] = 0x01;
		bytes[13+2] = 0x01;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}
	
	private static byte[] getConfirm05F31() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x1F;
		bytes[6+2] = 0x00;
		bytes[7+2] = 0x5;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x05;
		
		//应用程序功能码
		bytes[10+2] = 0x00;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//P1
		bytes[12+2] = 0x01;
		bytes[13+2] = 0x01;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}
	
	private static byte[] getConfirm04F103() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x07;
		bytes[6+2] = 0x04;
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x04;
		
		//应用程序功能码
		bytes[10+2] = 0x0a;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//P1
		bytes[12+2] = 0x07;
		bytes[13+2] = 0x0d;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}
	
	private static byte[] getConfirm04F103_2() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		bytes[5+2] = 0x08;
		bytes[6+2] = 0x04;
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		bytes[9+2] = 0x04;
		
		//应用程序功能码
		bytes[10+2] = 0x0a;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//P1
		bytes[12+2] = 0x01;
		bytes[13+2] = 0x01;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}
	
	private static byte[] getConfirm04F104() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		//region: 1041
		bytes[5+2] = 0x11;
		bytes[6+2] = 0x04;
		//collector: 4
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		//mainserver: 4
		bytes[9+2] = 0x04;
		
		//应用程序功能码
		bytes[10+2] = 0x0a;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//p104
		bytes[14] = 0x08;
		bytes[15] = 0x0d;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}	
	
	private static byte[] getConfirm04F104_2() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		//region: 1042
		bytes[5+2] = 0x12;
		bytes[6+2] = 0x04;
		//collector: 4
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		//mainserver: 4
		bytes[9+2] = 0x04;
		
		//应用程序功能码
		bytes[10+2] = 0x0a;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//p1
		bytes[14] = 0x01;
		bytes[15] = 0x01;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}	
	
	private static byte[] getConfirm04F105() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		//region: 105
		bytes[5+2] = 0x69;
		bytes[6+2] = 0X00;
		//collector: 4
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		//mainserver: 4
		bytes[9+2] = 0x04;
		
		//应用程序功能码
		bytes[10+2] = 0x0a;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//p105
		bytes[14] = 0x01;
		bytes[15] = 0x0e;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;
		*/
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}	
	
	private static byte[] getConfirm04F106() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		//region: 106
		bytes[5+2] = 0x6a;
		bytes[6+2] = 0X00;
		//collector: 4
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		//mainserver: 4
		bytes[9+2] = 0x04;
		
		//应用程序功能码
		bytes[10+2] = 0x0a;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//p106
		bytes[14] = 0x02;
		bytes[15] = 0x0e;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}
	
	private static byte[] getConfirm04F110() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		//region: 110
		bytes[5+2] = 0x6e;
		bytes[6+2] = 0X00;
		//collector: 4
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		//mainserver: 4
		bytes[9+2] = 0x04;
		
		//应用程序功能码
		bytes[10+2] = 0x0a;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//p110
		bytes[14] = 0x06;
		bytes[15] = 0x0e;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
		return bytes;
	}	
	
	private static byte[] getConfirm04F111() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		bytes[0] = 0x68;
		bytes[1] = 0x00;
		bytes[2] = 0x1d;
		bytes[3] = 0x68;
		//控制域
		bytes[4] = (byte) 0x80; 
		//地址域
		//region: 111
		bytes[5+2] = 0x6f;
		bytes[6+2] = 0X00;
		//collector: 4
		bytes[7+2] = 0x04;
		bytes[8+2] = 0x00;
		//mainserver: 4
		bytes[9+2] = 0x04;
		
		//应用程序功能码
		bytes[10+2] = 0x0a;
		//帧序列
		bytes[11+2] = 0x60;
		
		//数据单元标识：
		//p111
		bytes[14] = 0x07;
		bytes[15] = 0x0e;
		//F1
		bytes[14+2] = 0x01;
		bytes[15+2] = 0x00;
	
		//附加信息AUX
		/*bytes[16+2] = 0x00;
		bytes[17+2] = 0x01;*/
		
		//帧校验和
		bytes[16+2] = 0x2b;
		bytes[17+2] = 0x16;
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
		return (quotient * 8) + (remainder + 1);
	}
	
	/**
	 * 将字节型转为1字节无符号整型
	 */
	public static int toUnsignedInt1(byte x) {
		return ((int) x) & 0xff;
	}
}
