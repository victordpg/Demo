package com.roiland.hadoop.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 * 历史行车数据，字段取自 ods_veh_history_traffic。
 * @author dywane.diao
 *
 */
public class HistoryTravelDataWritable implements WritableComparable<HistoryTravelDataWritable> {
	private Text cn; //底盘号
	private Text ct; //采集时间
	private Text st; //系统时间
	private FloatWritable L0002; //经度
	private FloatWritable L0003; //纬度
	private IntWritable L4013; //行车状态
	private IntWritable L0004; //总里程
	private IntWritable L0005; //本次里程
	private IntWritable L0006; //速度
	private IntWritable L0009; //发动机转速
	private IntWritable L0008; //油位
	private IntWritable L0024; //保养里程
	private IntWritable L0025; //保养天数
	private FloatWritable L0014; //机油液位
	private FloatWritable L001f; //机油下限值
	private IntWritable L0028; //续航里程
	private FloatWritable L7d02; //行驶瞬时油耗
	
	// 下面字段是没有进行过清洗的字段
	private FloatWritable  L0007;
//	private IntWritable L000a;
//	private IntWritable L000b;
//	private IntWritable L000d;
//	private FloatWritable L000e;
	private FloatWritable L0010;
	private IntWritable L0011;
//	private IntWritable L0027;
	private IntWritable L0029;
	private IntWritable L002a;
	private IntWritable L002b;
	private IntWritable L002c;
//	private IntWritable L002d;
//	private IntWritable L002e;
//	private IntWritable L002f;
//	private IntWritable L0030;
//	private IntWritable L0031;
//	private IntWritable L0032;
//	private IntWritable L0033;
//	private IntWritable L0034;
//	private IntWritable L0035;
//	private IntWritable L0036;
//	private IntWritable L0037;
//	private IntWritable L0038;	
//	private IntWritable L0039;
//	private IntWritable L0040;
//	private IntWritable L0041;
//	private IntWritable L0042;
	private IntWritable L0043;
//	private IntWritable L0044;
//	private IntWritable L0045;
//	private IntWritable L0046;
//	private IntWritable L0047;
//	private IntWritable L0048;
//	private IntWritable L0049;
//	private IntWritable L004a;
//	private FloatWritable L0050;
//	private FloatWritable L0051;
//	private FloatWritable L0052;
//	private FloatWritable L0053;
//	private FloatWritable L0054;
//	private FloatWritable L0055;
//	private FloatWritable L0056;
//	private FloatWritable L0057;
	private FloatWritable L7d01;
	private IntWritable L7d03;
	private IntWritable L7d04;
	private IntWritable L7d05;
	private IntWritable L7d06;
	private IntWritable L7d07;
	private IntWritable L7d08;
	private FloatWritable L7d09;
	private IntWritable L7d0a;
	private IntWritable L7d0b;
	private FloatWritable L7d0c;
	private IntWritable L7d0d;
//	private IntWritable L7f01;
//	private IntWritable L7f02;
//	private IntWritable L7f03;
//	private IntWritable L7f04;
//	private IntWritable L7f05;
//	private IntWritable L7f06;
//	private IntWritable L7f07;
//	private IntWritable L7f08;
//	private IntWritable L7f09;
//	private IntWritable L7f0a;
//	private IntWritable L7f0b;
	private IntWritable L7f0c;
//	private IntWritable L7f0d;
//	private IntWritable L7f0e;
//	private IntWritable L7f0f;
//	private IntWritable L7f10;
//	private IntWritable L7f11;
//	private IntWritable L7f12;
//	private IntWritable L7f13;
//	private IntWritable L7f14;
//	private IntWritable L7f15;
//	private IntWritable L7f16;
//	private IntWritable L7f17;
//	private IntWritable L7f18;
//	private IntWritable L7f19;
//	private IntWritable L7f1a;
//	private IntWritable L7f1b;
//	private IntWritable L7f1c;
//	private IntWritable L7f1d;
//	private IntWritable L7f1e;
//	private IntWritable L7f1f;
//	private IntWritable L7f20;
//	private IntWritable L7f21;
//	private IntWritable L7f22;
//	private IntWritable L7f23;
//	private IntWritable L7f24;
//	private IntWritable L7f25;
//	private IntWritable L7f26;
//	private IntWritable L7f27;
//	private IntWritable L7f28;
//	private IntWritable L7f29;
//	private IntWritable L7f2a;
//	private IntWritable L7f2b;
//	private IntWritable L7f2c;
//	private IntWritable L7f2d;
//	private IntWritable L7f2e;
//	private IntWritable L7f2f;
//	private IntWritable L7f30;
//	private IntWritable L7f31;
//	private IntWritable L7f32;
//	private Text L8001;
//	private Text L8002;
//	private Text L8003;
//	private Text L8004;
//	private Text L8005;
//	private Text L8006;
//	private Text L8007;
//	private Text L8008;
//	private Text L8009;
//	private Text L800a;
//	private Text L800b;
//	private Text L800c;
//	private Text L800d;
//	private Text L800e;
//	private Text L800f;
//	private Text L8010;
//	private Text L8011;
	private IntWritable L8051;
	private IntWritable L8052;
	private IntWritable L8053;
	private IntWritable L8054;
	private IntWritable L8055;
	private IntWritable L8056;
	private IntWritable L8057;
	private IntWritable L8058;
	private IntWritable L8059;
	private IntWritable L805a;
	private IntWritable L805b;
	private IntWritable L805c;
	private IntWritable L805d;
	private IntWritable L805e;
	private IntWritable L805f;
	private IntWritable L8060;
	private IntWritable L8061;
	private IntWritable L8062;
	private IntWritable L8063;
	private IntWritable L8064;
	private IntWritable L8065;
	private IntWritable L8066;
	private IntWritable L8067;
	private IntWritable L8068;
	private IntWritable L8069;
	private IntWritable L806a;
	private IntWritable L806b;
	private IntWritable L806c;
	private IntWritable L806d;
	private IntWritable L806e;
	private IntWritable L806f;
	private IntWritable L8070;
	private IntWritable L8071;
	private IntWritable L8072;
	private IntWritable L8073;
	private IntWritable L8074;
	private IntWritable L8075;
	private IntWritable L8076;
	private IntWritable L8077;
	private IntWritable L8078;
//	private IntWritable L0100;
//	private IntWritable L0101;
//	private IntWritable L0102;
//	private IntWritable L0103;
//	private IntWritable L8100;
//	private IntWritable L8101;
//	private IntWritable L8102;
//	private IntWritable L8103;
//	private IntWritable L8104;
//	private IntWritable L8105;
//	private IntWritable L8106;
//	private IntWritable L8107;
//	private IntWritable L8108;
//	private IntWritable L8109;
//	private IntWritable L810a;
//	private IntWritable L810b;
//	private FloatWritable L0070;
//	private FloatWritable L0071;
//	private FloatWritable L0072;
//	private Text Lff01;
	private Text Lsid;

	public HistoryTravelDataWritable() {}
	
	@Override
	public int compareTo(HistoryTravelDataWritable other) {
		int result;
		int cnCompare = this.cn.compareTo(other.cn);
		if (cnCompare == 0) {
			result = this.ct.compareTo(other.ct);
		}else 
			result = cnCompare;
		return result;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(cn.toString());
		out.writeUTF(ct.toString());
		out.writeUTF(st.toString());
		
		if (L0002 != null) {
			out.writeBoolean(false);
			out.writeFloat(L0002.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0003 != null) {
			out.writeBoolean(false);
			out.writeFloat(L0003.get());
		} else {
			out.writeBoolean(true);
		}
		if (L4013 != null) {
			out.writeBoolean(false);
			out.writeInt(L4013.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0004 != null) {
			out.writeBoolean(false);
			out.writeInt(L0004.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0005 != null) {
			out.writeBoolean(false);
			out.writeInt(L0005.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0006 != null) {
			out.writeBoolean(false);
			out.writeInt(L0006.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0009 != null) {
			out.writeBoolean(false);
			out.writeInt(L0009.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0008 != null) {
			out.writeBoolean(false);
			out.writeInt(L0008.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0024 != null) {
			out.writeBoolean(false);
			out.writeInt(L0024.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0025 != null) {
			out.writeBoolean(false);
			out.writeInt(L0025.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0014 != null) {
			out.writeBoolean(false);
			out.writeFloat(L0014.get());
		} else {
			out.writeBoolean(true);
		}
		if (L001f != null) {
			out.writeBoolean(false);
			out.writeFloat(L001f.get());
		} else {
			out.writeBoolean(true);
		}
		if (L0028 != null) {
			out.writeBoolean(false);
			out.writeInt(L0028.get());
		} else {
			out.writeBoolean(true);
		}
		if (L7d02 != null) {
			out.writeBoolean(false);
			out.writeFloat(L7d02.get());
		} else {
			out.writeBoolean(true);
		}

		if (L0007 != null) {	out.writeBoolean(false);	out.writeFloat(L0007.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L000a != null) {	out.writeBoolean(false);	out.writeInt(L000a.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L000b != null) {	out.writeBoolean(false);	out.writeInt(L000b.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L000d != null) {	out.writeBoolean(false);	out.writeInt(L000d.get()); 	} else {	out.writeBoolean(true); 	}		
//		if (L000e != null) {	out.writeBoolean(false);	out.writeFloat(L000e.get()); 	} else {	out.writeBoolean(true); 	}
		if (L0010 != null) {	out.writeBoolean(false);	out.writeFloat(L0010.get()); 	} else {	out.writeBoolean(true); 	}
		if (L0011 != null) {	out.writeBoolean(false);	out.writeInt(L0011.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0027 != null) {	out.writeBoolean(false);	out.writeInt(L0027.get()); 	} else {	out.writeBoolean(true); 	}
		if (L0029 != null) {	out.writeBoolean(false);	out.writeInt(L0029.get()); 	} else {	out.writeBoolean(true); 	}
		if (L002a != null) {	out.writeBoolean(false);	out.writeInt(L002a.get()); 	} else {	out.writeBoolean(true); 	}
		if (L002b != null) {	out.writeBoolean(false);	out.writeInt(L002b.get()); 	} else {	out.writeBoolean(true); 	}
		if (L002c != null) {	out.writeBoolean(false);	out.writeInt(L002c.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L002d != null) {	out.writeBoolean(false);	out.writeInt(L002d.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L002e != null) {	out.writeBoolean(false);	out.writeInt(L002e.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L002f != null) {	out.writeBoolean(false);	out.writeInt(L002f.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0030 != null) {	out.writeBoolean(false);	out.writeInt(L0030.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0031 != null) {	out.writeBoolean(false);	out.writeInt(L0031.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0032 != null) {	out.writeBoolean(false);	out.writeInt(L0032.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0033 != null) {	out.writeBoolean(false);	out.writeInt(L0033.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0034 != null) {	out.writeBoolean(false);	out.writeInt(L0034.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0035 != null) {	out.writeBoolean(false);	out.writeInt(L0035.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0036 != null) {	out.writeBoolean(false);	out.writeInt(L0036.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0037 != null) {	out.writeBoolean(false);	out.writeInt(L0037.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0038 != null) {	out.writeBoolean(false);	out.writeInt(L0038.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0039 != null) {	out.writeBoolean(false);	out.writeInt(L0039.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0040 != null) {	out.writeBoolean(false);	out.writeInt(L0040.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0041 != null) {	out.writeBoolean(false);	out.writeInt(L0041.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0042 != null) {	out.writeBoolean(false);	out.writeInt(L0042.get()); 	} else {	out.writeBoolean(true); 	}
		if (L0043 != null) {	out.writeBoolean(false);	out.writeInt(L0043.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0044 != null) {	out.writeBoolean(false);	out.writeInt(L0044.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0045 != null) {	out.writeBoolean(false);	out.writeInt(L0045.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0046 != null) {	out.writeBoolean(false);	out.writeInt(L0046.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0047 != null) {	out.writeBoolean(false);	out.writeInt(L0047.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0048 != null) {	out.writeBoolean(false);	out.writeInt(L0048.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0049 != null) {	out.writeBoolean(false);	out.writeInt(L0049.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L004a != null) {	out.writeBoolean(false);	out.writeInt(L004a.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0050 != null) {	out.writeBoolean(false);	out.writeFloat(L0050.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0051 != null) {	out.writeBoolean(false);	out.writeFloat(L0051.get()); 	} else {	out.writeBoolean(true); 	}	
//		if (L0052 != null) {	out.writeBoolean(false);	out.writeFloat(L0052.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0053 != null) {	out.writeBoolean(false);	out.writeFloat(L0053.get()); 	} else {	out.writeBoolean(true); 	}	
//		if (L0054 != null) {	out.writeBoolean(false);	out.writeFloat(L0054.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0055 != null) {	out.writeBoolean(false);	out.writeFloat(L0055.get()); 	} else {	out.writeBoolean(true); 	}	
//		if (L0056 != null) {	out.writeBoolean(false);	out.writeFloat(L0056.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L0057 != null) {	out.writeBoolean(false);	out.writeFloat(L0057.get()); 	} else {	out.writeBoolean(true); 	}	
//		if (L7d01 != null) {	out.writeBoolean(false);	out.writeFloat(L7d01.get()); 	} else {	out.writeBoolean(true); 	}
		if (L7d03 != null) {	out.writeBoolean(false);	out.writeInt(L7d03.get()); 	} else {	out.writeBoolean(true); 	}	
		if (L7d04 != null) {	out.writeBoolean(false);	out.writeInt(L7d04.get()); 	} else {	out.writeBoolean(true); 	}		
		if (L7d05 != null) {	out.writeBoolean(false);	out.writeInt(L7d05.get()); 	} else {	out.writeBoolean(true); 	}	
		if (L7d06 != null) {	out.writeBoolean(false);	out.writeInt(L7d06.get()); 	} else {	out.writeBoolean(true); 	}
		if (L7d07 != null) {	out.writeBoolean(false);	out.writeInt(L7d07.get()); 	} else {	out.writeBoolean(true); 	}	
		if (L7d08 != null) {	out.writeBoolean(false);	out.writeInt(L7d08.get()); 	} else {	out.writeBoolean(true); 	}
		if (L7d09 != null) {	out.writeBoolean(false);	out.writeFloat(L7d09.get()); 	} else {	out.writeBoolean(true); 	}	
		if (L7d0a != null) {	out.writeBoolean(false);	out.writeInt(L7d0a.get()); 	} else {	out.writeBoolean(true); 	}
		if (L7d0b != null) {	out.writeBoolean(false);	out.writeInt(L7d0b.get()); 	} else {	out.writeBoolean(true); 	}
	 	if (L7d0c != null) {	out.writeBoolean(false);	out.writeFloat(L7d0c.get()); 	} else {	out.writeBoolean(true); 	}
		if (L7d0d != null) {	out.writeBoolean(false);	out.writeInt(L7d0d.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f01 != null) {	out.writeBoolean(false);	out.writeInt(L7f01.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f02 != null) {	out.writeBoolean(false);	out.writeInt(L7f02.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f03 != null) {	out.writeBoolean(false);	out.writeInt(L7f03.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f04 != null) {	out.writeBoolean(false);	out.writeInt(L7f04.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f05 != null) {	out.writeBoolean(false);	out.writeInt(L7f05.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f06 != null) {	out.writeBoolean(false);	out.writeInt(L7f06.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f07 != null) {	out.writeBoolean(false);	out.writeInt(L7f07.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f08 != null) {	out.writeBoolean(false);	out.writeInt(L7f08.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f09 != null) {	out.writeBoolean(false);	out.writeInt(L7f09.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f0a != null) {	out.writeBoolean(false);	out.writeInt(L7f0a.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f0b != null) {	out.writeBoolean(false);	out.writeInt(L7f0b.get()); 	} else {	out.writeBoolean(true); 	}
	 	if (L7f0c != null) {	out.writeBoolean(false);	out.writeInt(L7f0c.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f0d != null) {	out.writeBoolean(false);	out.writeInt(L7f0d.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f0e != null) {	out.writeBoolean(false);	out.writeInt(L7f0e.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f0f != null) {	out.writeBoolean(false);	out.writeInt(L7f0f.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f10 != null) {	out.writeBoolean(false);	out.writeInt(L7f10.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f11 != null) {	out.writeBoolean(false);	out.writeInt(L7f11.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f12 != null) {	out.writeBoolean(false);	out.writeInt(L7f12.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f13 != null) {	out.writeBoolean(false);	out.writeInt(L7f13.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f14 != null) {	out.writeBoolean(false);	out.writeInt(L7f14.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f15 != null) {	out.writeBoolean(false);	out.writeInt(L7f15.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f16 != null) {	out.writeBoolean(false);	out.writeInt(L7f16.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f17 != null) {	out.writeBoolean(false);	out.writeInt(L7f17.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f18 != null) {	out.writeBoolean(false);	out.writeInt(L7f18.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f19 != null) {	out.writeBoolean(false);	out.writeInt(L7f19.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f1a != null) {	out.writeBoolean(false);	out.writeInt(L7f1a.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f1b != null) {	out.writeBoolean(false);	out.writeInt(L7f1b.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f1c != null) {	out.writeBoolean(false);	out.writeInt(L7f1c.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f1d != null) {	out.writeBoolean(false);	out.writeInt(L7f1d.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f1e != null) {	out.writeBoolean(false);	out.writeInt(L7f1e.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f1f != null) {	out.writeBoolean(false);	out.writeInt(L7f1f.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f20 != null) {	out.writeBoolean(false);	out.writeInt(L7f20.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f21 != null) {	out.writeBoolean(false);	out.writeInt(L7f21.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f22 != null) {	out.writeBoolean(false);	out.writeInt(L7f22.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f23 != null) {	out.writeBoolean(false);	out.writeInt(L7f23.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f24 != null) {	out.writeBoolean(false);	out.writeInt(L7f24.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f25 != null) {	out.writeBoolean(false);	out.writeInt(L7f25.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f26 != null) {	out.writeBoolean(false);	out.writeInt(L7f26.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f27 != null) {	out.writeBoolean(false);	out.writeInt(L7f27.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f28 != null) {	out.writeBoolean(false);	out.writeInt(L7f28.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f29 != null) {	out.writeBoolean(false);	out.writeInt(L7f29.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f2a != null) {	out.writeBoolean(false);	out.writeInt(L7f2a.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f2b != null) {	out.writeBoolean(false);	out.writeInt(L7f2b.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f2c != null) {	out.writeBoolean(false);	out.writeInt(L7f2c.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f2d != null) {	out.writeBoolean(false);	out.writeInt(L7f2d.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f2e != null) {	out.writeBoolean(false);	out.writeInt(L7f2e.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f2f != null) {	out.writeBoolean(false);	out.writeInt(L7f2f.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f30 != null) {	out.writeBoolean(false);	out.writeInt(L7f30.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L7f31 != null) {	out.writeBoolean(false);	out.writeInt(L7f31.get()); 	} else {	out.writeBoolean(true); 	}
//	 	if (L7f32 != null) {	out.writeBoolean(false);	out.writeInt(L7f32.get()); 	} else {	out.writeBoolean(true); 	}
//		if (L8001 != null) { out.writeBoolean(false); out.writeUTF(L8001.toString()); } else { out.writeBoolean(true); }	
//		if (L8002 != null) { out.writeBoolean(false); out.writeUTF(L8002.toString()); } else { out.writeBoolean(true); }	
//		if (L8003 != null) { out.writeBoolean(false); out.writeUTF(L8003.toString()); } else { out.writeBoolean(true); }	
//		if (L8004 != null) { out.writeBoolean(false); out.writeUTF(L8004.toString()); } else { out.writeBoolean(true); }	
//		if (L8005 != null) { out.writeBoolean(false); out.writeUTF(L8005.toString()); } else { out.writeBoolean(true); }	
//		if (L8006 != null) { out.writeBoolean(false); out.writeUTF(L8006.toString()); } else { out.writeBoolean(true); }	
//		if (L8007 != null) { out.writeBoolean(false); out.writeUTF(L8007.toString()); } else { out.writeBoolean(true); }	
//		if (L8008 != null) { out.writeBoolean(false); out.writeUTF(L8008.toString()); } else { out.writeBoolean(true); }	
//		if (L8009 != null) { out.writeBoolean(false); out.writeUTF(L8009.toString()); } else { out.writeBoolean(true); }	
//		if (L800a != null) { out.writeBoolean(false); out.writeUTF(L800a.toString()); } else { out.writeBoolean(true); }	
//		if (L800b != null) { out.writeBoolean(false); out.writeUTF(L800b.toString()); } else { out.writeBoolean(true); }	
//		if (L800c != null) { out.writeBoolean(false); out.writeUTF(L800c.toString()); } else { out.writeBoolean(true); }	
//		if (L800d != null) { out.writeBoolean(false); out.writeUTF(L800d.toString()); } else { out.writeBoolean(true); }	
//		if (L800e != null) { out.writeBoolean(false); out.writeUTF(L800e.toString()); } else { out.writeBoolean(true); }	
//		if (L800f != null) { out.writeBoolean(false); out.writeUTF(L800f.toString()); } else { out.writeBoolean(true); }	
//		if (L8010 != null) { out.writeBoolean(false); out.writeUTF(L8010.toString()); } else { out.writeBoolean(true); }	
//		if (L8011 != null) { out.writeBoolean(false); out.writeUTF(L8011.toString()); } else { out.writeBoolean(true); }	
		if (L8051 != null) { out.writeBoolean(false);	out.writeInt(L8051.get());	} else {	out.writeBoolean(true);}
		if (L8052 != null) { out.writeBoolean(false);	out.writeInt(L8052.get());	} else {	out.writeBoolean(true);}
		if (L8053 != null) { out.writeBoolean(false);	out.writeInt(L8053.get());	} else {	out.writeBoolean(true);}
		if (L8054 != null) { out.writeBoolean(false);	out.writeInt(L8054.get());	} else {	out.writeBoolean(true);}
		if (L8055 != null) { out.writeBoolean(false);	out.writeInt(L8055.get());	} else {	out.writeBoolean(true);}
		if (L8056 != null) { out.writeBoolean(false);	out.writeInt(L8056.get());	} else {	out.writeBoolean(true);}
		if (L8057 != null) { out.writeBoolean(false);	out.writeInt(L8057.get());	} else {	out.writeBoolean(true);}
		if (L8058 != null) { out.writeBoolean(false);	out.writeInt(L8058.get());	} else {	out.writeBoolean(true);}
		if (L8059 != null) { out.writeBoolean(false);	out.writeInt(L8059.get());	} else {	out.writeBoolean(true);}
		if (L805a != null) { out.writeBoolean(false);	out.writeInt(L805a.get());	} else {	out.writeBoolean(true);}
		if (L805b != null) { out.writeBoolean(false);	out.writeInt(L805b.get());	} else {	out.writeBoolean(true);}
		if (L805c != null) { out.writeBoolean(false);	out.writeInt(L805c.get());	} else {	out.writeBoolean(true);}
		if (L805d != null) { out.writeBoolean(false);	out.writeInt(L805d.get());	} else {	out.writeBoolean(true);}
		if (L805e != null) { out.writeBoolean(false);	out.writeInt(L805e.get());	} else {	out.writeBoolean(true);}
		if (L805f != null) { out.writeBoolean(false);	out.writeInt(L805f.get());	} else {	out.writeBoolean(true);}
		if (L8060 != null) { out.writeBoolean(false);	out.writeInt(L8060.get());	} else {	out.writeBoolean(true);}
		if (L8061 != null) { out.writeBoolean(false);	out.writeInt(L8061.get());	} else {	out.writeBoolean(true);}
		if (L8062 != null) { out.writeBoolean(false);	out.writeInt(L8062.get());	} else {	out.writeBoolean(true);}
		if (L8063 != null) { out.writeBoolean(false);	out.writeInt(L8063.get());	} else {	out.writeBoolean(true);}
		if (L8064 != null) { out.writeBoolean(false);	out.writeInt(L8064.get());	} else {	out.writeBoolean(true);}
		if (L8065 != null) { out.writeBoolean(false);	out.writeInt(L8065.get());	} else {	out.writeBoolean(true);}
		if (L8066 != null) { out.writeBoolean(false);	out.writeInt(L8066.get());	} else {	out.writeBoolean(true);}
		if (L8067 != null) { out.writeBoolean(false);	out.writeInt(L8067.get());	} else {	out.writeBoolean(true);}
		if (L8068 != null) { out.writeBoolean(false);	out.writeInt(L8068.get());	} else {	out.writeBoolean(true);}
		if (L8069 != null) { out.writeBoolean(false);	out.writeInt(L8069.get());	} else {	out.writeBoolean(true);}
		if (L806a != null) { out.writeBoolean(false);	out.writeInt(L806a.get());	} else {	out.writeBoolean(true);}
		if (L806b != null) { out.writeBoolean(false);	out.writeInt(L806b.get());	} else {	out.writeBoolean(true);}
		if (L806c != null) { out.writeBoolean(false);	out.writeInt(L806c.get());	} else {	out.writeBoolean(true);}
		if (L806d != null) { out.writeBoolean(false);	out.writeInt(L806d.get());	} else {	out.writeBoolean(true);}
		if (L806e != null) { out.writeBoolean(false);	out.writeInt(L806e.get());	} else {	out.writeBoolean(true);}
		if (L806f != null) { out.writeBoolean(false);	out.writeInt(L806f.get());	} else {	out.writeBoolean(true);}
		if (L8070 != null) { out.writeBoolean(false);	out.writeInt(L8070.get());	} else {	out.writeBoolean(true);}
		if (L8071 != null) { out.writeBoolean(false);	out.writeInt(L8071.get());	} else {	out.writeBoolean(true);}
		if (L8072 != null) { out.writeBoolean(false);	out.writeInt(L8072.get());	} else {	out.writeBoolean(true);}
		if (L8073 != null) { out.writeBoolean(false);	out.writeInt(L8073.get());	} else {	out.writeBoolean(true);}
		if (L8074 != null) { out.writeBoolean(false);	out.writeInt(L8074.get());	} else {	out.writeBoolean(true);}
		if (L8075 != null) { out.writeBoolean(false);	out.writeInt(L8075.get());	} else {	out.writeBoolean(true);}
		if (L8076 != null) { out.writeBoolean(false);	out.writeInt(L8076.get());	} else {	out.writeBoolean(true);}
		if (L8077 != null) { out.writeBoolean(false);	out.writeInt(L8077.get());	} else {	out.writeBoolean(true);}
		if (L8078 != null) { out.writeBoolean(false);	out.writeInt(L8078.get());	} else {	out.writeBoolean(true);}
//		if (L0100 != null) { out.writeBoolean(false);	out.writeInt(L0100.get());	} else {	out.writeBoolean(true);}
//		if (L0101 != null) { out.writeBoolean(false);	out.writeInt(L0101.get());	} else {	out.writeBoolean(true);}
//		if (L0102 != null) { out.writeBoolean(false);	out.writeInt(L0102.get());	} else {	out.writeBoolean(true);}
//		if (L0103 != null) { out.writeBoolean(false);	out.writeInt(L0103.get());	} else {	out.writeBoolean(true);}
//		if (L8100 != null) { out.writeBoolean(false);	out.writeInt(L8100.get());	} else {	out.writeBoolean(true);}
//		if (L8101 != null) { out.writeBoolean(false);	out.writeInt(L8101.get());	} else {	out.writeBoolean(true);}
//		if (L8102 != null) { out.writeBoolean(false);	out.writeInt(L8102.get());	} else {	out.writeBoolean(true);}
//		if (L8103 != null) { out.writeBoolean(false);	out.writeInt(L8103.get());	} else {	out.writeBoolean(true);}
//		if (L8104 != null) { out.writeBoolean(false);	out.writeInt(L8104.get());	} else {	out.writeBoolean(true);}
//		if (L8105 != null) { out.writeBoolean(false);	out.writeInt(L8105.get());	} else {	out.writeBoolean(true);}
//		if (L8106 != null) { out.writeBoolean(false);	out.writeInt(L8106.get());	} else {	out.writeBoolean(true);}
//		if (L8107 != null) { out.writeBoolean(false);	out.writeInt(L8107.get());	} else {	out.writeBoolean(true);}
//		if (L8108 != null) { out.writeBoolean(false);	out.writeInt(L8108.get());	} else {	out.writeBoolean(true);}
//		if (L8109 != null) { out.writeBoolean(false);	out.writeInt(L8109.get());	} else {	out.writeBoolean(true);}
//		if (L810a != null) { out.writeBoolean(false);	out.writeInt(L810a.get());	} else {	out.writeBoolean(true);}
//		if (L810b != null) { out.writeBoolean(false);	out.writeInt(L810b.get());	} else {	out.writeBoolean(true);}
//		if (L0070 != null) { out.writeBoolean(false);	out.writeFloat(L0070.get());} else {	out.writeBoolean(true);}
//		if (L0071 != null) { out.writeBoolean(false);	out.writeFloat(L0071.get());} else {	out.writeBoolean(true);}
//		if (L0072 != null) { out.writeBoolean(false);	out.writeFloat(L0072.get());} else {	out.writeBoolean(true);}
//		if (Lff01 != null) { out.writeBoolean(false);	out.writeUTF(Lff01.toString()); } else { out.writeBoolean(true); }
		if (Lsid  != null) { out.writeBoolean(false);	out.writeUTF(Lsid.toString()); } else { out.writeBoolean(true); }
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		cn = new Text(in.readUTF());
		ct = new Text(in.readUTF());
		st = new Text(in.readUTF());
		if (!in.readBoolean()) {
			L0002 = new FloatWritable(in.readFloat());
		} else {
			L0002 = null;
		}
		if (!in.readBoolean()) {
			L0003 = new FloatWritable(in.readFloat());
		} else {
			L0003 = null;
		}
		if (!in.readBoolean()) {
			L4013 = new IntWritable(in.readInt());
		} else {
			L4013 = null;
		}
		if (!in.readBoolean()) {
			L0004 = new IntWritable(in.readInt());
		} else {
			L0004 = null;
		}
		if (!in.readBoolean()) {
			L0005 = new IntWritable(in.readInt());
		} else {
			L0005 = null;
		}
		if (!in.readBoolean()) {
			L0006 = new IntWritable(in.readInt());
		} else {
			L0006 = null;
		}
		if (!in.readBoolean()) {
			L0009 = new IntWritable(in.readInt());
		} else {
			L0009 = null;
		}
		if (!in.readBoolean()) {
			L0008 = new IntWritable(in.readInt());
		} else {
			L0008 = null;
		}
		if (!in.readBoolean()) {
			L0024 = new IntWritable(in.readInt());
		} else {
			L0024 = null;
		}
		if (!in.readBoolean()) {
			L0025 = new IntWritable(in.readInt());
		} else {
			L0025 = null;
		}
		if (!in.readBoolean()) {
			L0014 = new FloatWritable(in.readFloat());
		} else {
			L0014 = null;
		}
		if (!in.readBoolean()) {
			L001f = new FloatWritable(in.readFloat());
		} else {
			L001f = null;
		}
		if (!in.readBoolean()) {
			L0028 = new IntWritable(in.readInt());
		} else {
			L0028 = null;
		}
		if (!in.readBoolean()) {
			L7d02 = new FloatWritable(in.readFloat());
		} else {
			L7d02 = null;
		}
//		
		if (!in.readBoolean()) {	L0007 = new FloatWritable(in.readFloat());	} else {	L0007 = null;	} 
//		if (!in.readBoolean()) {	L000a =   new IntWritable(in.readInt());	} else {	L000a = null;	} 
//		if (!in.readBoolean()) {	L000b =   new IntWritable(in.readInt());	} else {	L000b = null;	} 
//		if (!in.readBoolean()) {	L000d =   new IntWritable(in.readInt());	} else {	L000d = null;	} 
//		if (!in.readBoolean()) {	L000e = new FloatWritable(in.readFloat());	} else {	L000e = null;	} 
		if (!in.readBoolean()) {	L0010 = new FloatWritable(in.readFloat());	} else {	L0010 = null;	} 
		if (!in.readBoolean()) {	L0011 =   new IntWritable(in.readInt());	} else {	L0011 = null;	} 
//		if (!in.readBoolean()) {	L0027 =   new IntWritable(in.readInt());	} else {	L0027 = null;	} 
		if (!in.readBoolean()) {	L0029 =   new IntWritable(in.readInt());	} else {	L0029 = null;	} 
		if (!in.readBoolean()) {	L002a =   new IntWritable(in.readInt());	} else {	L002a = null;	} 
		if (!in.readBoolean()) {	L002b =   new IntWritable(in.readInt());	} else {	L002b = null;	} 
		if (!in.readBoolean()) {	L002c =   new IntWritable(in.readInt());	} else {	L002c = null;	} 
//		if (!in.readBoolean()) {	L002d =   new IntWritable(in.readInt());	} else {	L002d = null;	} 
//		if (!in.readBoolean()) {	L002e =   new IntWritable(in.readInt());	} else {	L002e = null;	} 
//		if (!in.readBoolean()) {	L002f =   new IntWritable(in.readInt());	} else {	L002f = null;	} 
//		if (!in.readBoolean()) {	L0030 =   new IntWritable(in.readInt());	} else {	L0030 = null;	} 
//		if (!in.readBoolean()) {	L0031 =   new IntWritable(in.readInt());	} else {	L0031 = null;	} 
//		if (!in.readBoolean()) {	L0032 =   new IntWritable(in.readInt());	} else {	L0032 = null;	} 
//		if (!in.readBoolean()) {	L0033 =   new IntWritable(in.readInt());	} else {	L0033 = null;	} 
//		if (!in.readBoolean()) {	L0034 =   new IntWritable(in.readInt());	} else {	L0034 = null;	} 
//		if (!in.readBoolean()) {	L0035 =   new IntWritable(in.readInt());	} else {	L0035 = null;	} 
//		if (!in.readBoolean()) {	L0036 =   new IntWritable(in.readInt());	} else {	L0036 = null;	} 
//		if (!in.readBoolean()) {	L0037 =   new IntWritable(in.readInt());	} else {	L0037 = null;	} 
//		if (!in.readBoolean()) {	L0038 =   new IntWritable(in.readInt());	} else {	L0038 = null;	} 
//		if (!in.readBoolean()) {	L0039 =   new IntWritable(in.readInt());	} else {	L0039 = null;	} 
//		if (!in.readBoolean()) {	L0040 =   new IntWritable(in.readInt());	} else {	L0040 = null;	} 
//		if (!in.readBoolean()) {	L0041 =   new IntWritable(in.readInt());	} else {	L0041 = null;	} 
//		if (!in.readBoolean()) {	L0042 =   new IntWritable(in.readInt());	} else {	L0042 = null;	} 
		if (!in.readBoolean()) {	L0043 =   new IntWritable(in.readInt());	} else {	L0043 = null;	} 
//		if (!in.readBoolean()) {	L0044 =   new IntWritable(in.readInt());	} else {	L0044 = null;	} 
//		if (!in.readBoolean()) {	L0045 =   new IntWritable(in.readInt());	} else {	L0045 = null;	} 
//		if (!in.readBoolean()) {	L0046 =   new IntWritable(in.readInt());	} else {	L0046 = null;	} 
//		if (!in.readBoolean()) {	L0047 =   new IntWritable(in.readInt());	} else {	L0047 = null;	} 
//		if (!in.readBoolean()) {	L0048 =   new IntWritable(in.readInt());	} else {	L0048 = null;	} 
//		if (!in.readBoolean()) {	L0049 =   new IntWritable(in.readInt());	} else {	L0049 = null;	} 
//		if (!in.readBoolean()) {	L004a =   new IntWritable(in.readInt());	} else {	L004a = null;	} 
//		if (!in.readBoolean()) {	L0050 = new FloatWritable(in.readFloat());	} else {	L0050 = null;	} 
//		if (!in.readBoolean()) {	L0051 = new FloatWritable(in.readFloat());	} else {	L0051 = null;	} 
//		if (!in.readBoolean()) {	L0052 = new FloatWritable(in.readFloat());	} else {	L0052 = null;	} 
//		if (!in.readBoolean()) {	L0053 = new FloatWritable(in.readFloat());	} else {	L0053 = null;	} 
//		if (!in.readBoolean()) {	L0054 = new FloatWritable(in.readFloat());	} else {	L0054 = null;	} 
//		if (!in.readBoolean()) {	L0055 = new FloatWritable(in.readFloat());	} else {	L0055 = null;	} 
//		if (!in.readBoolean()) {	L0056 = new FloatWritable(in.readFloat());	} else {	L0056 = null;	} 
//		if (!in.readBoolean()) {	L0057 = new FloatWritable(in.readFloat());	} else {	L0057 = null;	} 
//		if (!in.readBoolean()) {	L7d01 = new FloatWritable(in.readFloat());	} else {	L7d01 = null;	} 
		if (!in.readBoolean()) {	L7d03 =   new IntWritable(in.readInt());	} else {	L7d03 = null;	} 
		if (!in.readBoolean()) {	L7d04 =   new IntWritable(in.readInt());	} else {	L7d04 = null;	} 
		if (!in.readBoolean()) {	L7d05 =   new IntWritable(in.readInt());	} else {	L7d05 = null;	} 
		if (!in.readBoolean()) {	L7d06 =   new IntWritable(in.readInt());	} else {	L7d06 = null;	} 
		if (!in.readBoolean()) {	L7d07 =   new IntWritable(in.readInt());	} else {	L7d07 = null;	} 
		if (!in.readBoolean()) {	L7d08 =   new IntWritable(in.readInt());	} else {	L7d08 = null;	} 
		if (!in.readBoolean()) {	L7d09 = new FloatWritable(in.readFloat());	} else {	L7d09 = null;	} 
		if (!in.readBoolean()) {	L7d0a =   new IntWritable(in.readInt());	} else {	L7d0a = null;	} 
		if (!in.readBoolean()) {	L7d0b =   new IntWritable(in.readInt());	} else {	L7d0b = null;	} 
		if (!in.readBoolean()) {	L7d0c = new FloatWritable(in.readFloat());	} else {	L7d0c = null;	} 
		if (!in.readBoolean()) {	L7d0d =   new IntWritable(in.readInt());	} else {	L7d0d = null;	} 
//		if (!in.readBoolean()) {	L7f01 =   new IntWritable(in.readInt());	} else {	L7f01 = null;	} 
//		if (!in.readBoolean()) {	L7f02 =   new IntWritable(in.readInt());	} else {	L7f02 = null;	} 
//		if (!in.readBoolean()) {	L7f03 =   new IntWritable(in.readInt());	} else {	L7f03 = null;	} 
//		if (!in.readBoolean()) {	L7f04 =   new IntWritable(in.readInt());	} else {	L7f04 = null;	} 
//		if (!in.readBoolean()) {	L7f05 =   new IntWritable(in.readInt());	} else {	L7f05 = null;	} 
//		if (!in.readBoolean()) {	L7f06 =   new IntWritable(in.readInt());	} else {	L7f06 = null;	} 
//		if (!in.readBoolean()) {	L7f07 =   new IntWritable(in.readInt());	} else {	L7f07 = null;	} 
//		if (!in.readBoolean()) {	L7f08 =   new IntWritable(in.readInt());	} else {	L7f08 = null;	} 
//		if (!in.readBoolean()) {	L7f09 =   new IntWritable(in.readInt());	} else {	L7f09 = null;	} 
//		if (!in.readBoolean()) {	L7f0a =   new IntWritable(in.readInt());	} else {	L7f0a = null;	} 
//		if (!in.readBoolean()) {	L7f0b =   new IntWritable(in.readInt());	} else {	L7f0b = null;	} 
		if (!in.readBoolean()) {	L7f0c =   new IntWritable(in.readInt());	} else {	L7f0c = null;	} 
//		if (!in.readBoolean()) {	L7f0d =   new IntWritable(in.readInt());	} else {	L7f0d = null;	} 
//		if (!in.readBoolean()) {	L7f0e =   new IntWritable(in.readInt());	} else {	L7f0e = null;	} 
//		if (!in.readBoolean()) {	L7f0f =   new IntWritable(in.readInt());	} else {	L7f0f = null;	} 
//		if (!in.readBoolean()) {	L7f10 =   new IntWritable(in.readInt());	} else {	L7f10 = null;	} 
//		if (!in.readBoolean()) {	L7f11 =   new IntWritable(in.readInt());	} else {	L7f11 = null;	} 
//		if (!in.readBoolean()) {	L7f12 =   new IntWritable(in.readInt());	} else {	L7f12 = null;	} 
//		if (!in.readBoolean()) {	L7f13 =   new IntWritable(in.readInt());	} else {	L7f13 = null;	} 
//		if (!in.readBoolean()) {	L7f14 =   new IntWritable(in.readInt());	} else {	L7f14 = null;	} 
//		if (!in.readBoolean()) {	L7f15 =   new IntWritable(in.readInt());	} else {	L7f15 = null;	} 
//		if (!in.readBoolean()) {	L7f16 =   new IntWritable(in.readInt());	} else {	L7f16 = null;	} 
//		if (!in.readBoolean()) {	L7f17 =   new IntWritable(in.readInt());	} else {	L7f17 = null;	} 
//		if (!in.readBoolean()) {	L7f18 =   new IntWritable(in.readInt());	} else {	L7f18 = null;	} 
//		if (!in.readBoolean()) {	L7f19 =   new IntWritable(in.readInt());	} else {	L7f19 = null;	} 
//		if (!in.readBoolean()) {	L7f1a =   new IntWritable(in.readInt());	} else {	L7f1a = null;	} 
//		if (!in.readBoolean()) {	L7f1b =   new IntWritable(in.readInt());	} else {	L7f1b = null;	} 
//		if (!in.readBoolean()) {	L7f1c =   new IntWritable(in.readInt());	} else {	L7f1c = null;	} 
//		if (!in.readBoolean()) {	L7f1d =   new IntWritable(in.readInt());	} else {	L7f1d = null;	} 
//		if (!in.readBoolean()) {	L7f1e =   new IntWritable(in.readInt());	} else {	L7f1e = null;	} 
//		if (!in.readBoolean()) {	L7f1f =   new IntWritable(in.readInt());	} else {	L7f1f = null;	} 
//		if (!in.readBoolean()) {	L7f20 =   new IntWritable(in.readInt());	} else {	L7f20 = null;	} 
//		if (!in.readBoolean()) {	L7f21 =   new IntWritable(in.readInt());	} else {	L7f21 = null;	} 
//		if (!in.readBoolean()) {	L7f22 =   new IntWritable(in.readInt());	} else {	L7f22 = null;	} 
//		if (!in.readBoolean()) {	L7f23 =   new IntWritable(in.readInt());	} else {	L7f23 = null;	} 
//		if (!in.readBoolean()) {	L7f24 =   new IntWritable(in.readInt());	} else {	L7f24 = null;	} 
//		if (!in.readBoolean()) {	L7f25 =   new IntWritable(in.readInt());	} else {	L7f25 = null;	} 
//		if (!in.readBoolean()) {	L7f26 =   new IntWritable(in.readInt());	} else {	L7f26 = null;	} 
//		if (!in.readBoolean()) {	L7f27 =   new IntWritable(in.readInt());	} else {	L7f27 = null;	} 
//		if (!in.readBoolean()) {	L7f28 =   new IntWritable(in.readInt());	} else {	L7f28 = null;	} 
//		if (!in.readBoolean()) {	L7f29 =   new IntWritable(in.readInt());	} else {	L7f29 = null;	} 
//		if (!in.readBoolean()) {	L7f2a =   new IntWritable(in.readInt());	} else {	L7f2a = null;	} 
//		if (!in.readBoolean()) {	L7f2b =   new IntWritable(in.readInt());	} else {	L7f2b = null;	} 
//		if (!in.readBoolean()) {	L7f2c =   new IntWritable(in.readInt());	} else {	L7f2c = null;	} 
//		if (!in.readBoolean()) {	L7f2d =   new IntWritable(in.readInt());	} else {	L7f2d = null;	} 
//		if (!in.readBoolean()) {	L7f2e =   new IntWritable(in.readInt());	} else {	L7f2e = null;	} 
//		if (!in.readBoolean()) {	L7f2f =   new IntWritable(in.readInt());	} else {	L7f2f = null;	} 
//		if (!in.readBoolean()) {	L7f30 =   new IntWritable(in.readInt());	} else {	L7f30 = null;	} 
//		if (!in.readBoolean()) {	L7f31 =   new IntWritable(in.readInt());	} else {	L7f31 = null;	} 
//		if (!in.readBoolean()) {	L7f32 =   new IntWritable(in.readInt());	} else {	L7f32 = null;	} 
//		if (!in.readBoolean()) {	L8001 =          new Text(in.readUTF());	} else {	L8001 = null;	} 
//		if (!in.readBoolean()) {	L8002 =          new Text(in.readUTF());	} else {	L8002 = null;	} 
//		if (!in.readBoolean()) {	L8003 =          new Text(in.readUTF());	} else {	L8003 = null;	} 
//		if (!in.readBoolean()) {	L8004 =          new Text(in.readUTF());	} else {	L8004 = null;	} 
//		if (!in.readBoolean()) {	L8005 =          new Text(in.readUTF());	} else {	L8005 = null;	} 
//		if (!in.readBoolean()) {	L8006 =          new Text(in.readUTF());	} else {	L8006 = null;	} 
//		if (!in.readBoolean()) {	L8007 =          new Text(in.readUTF());	} else {	L8007 = null;	} 
//		if (!in.readBoolean()) {	L8008 =          new Text(in.readUTF());	} else {	L8008 = null;	} 
//		if (!in.readBoolean()) {	L8009 =          new Text(in.readUTF());	} else {	L8009 = null;	}
//		if (!in.readBoolean()) {	L800a =          new Text(in.readUTF());	} else {	L800a = null;	} 
//		if (!in.readBoolean()) {	L800b =          new Text(in.readUTF());	} else {	L800b = null;	} 
//		if (!in.readBoolean()) {	L800c =          new Text(in.readUTF());	} else {	L800c = null;	} 
//		if (!in.readBoolean()) {	L800d =          new Text(in.readUTF());	} else {	L800d = null;	} 
//		if (!in.readBoolean()) {	L800e =          new Text(in.readUTF());	} else {	L800e = null;	} 
//		if (!in.readBoolean()) {	L800f =          new Text(in.readUTF());	} else {	L800f = null;	} 
//		if (!in.readBoolean()) {	L8010 =          new Text(in.readUTF());	} else {	L8010 = null;	} 
//		if (!in.readBoolean()) {	L8011 =          new Text(in.readUTF());	} else {	L8011 = null;	} 
		if (!in.readBoolean()) {	L8051 =   new IntWritable(in.readInt());	} else {	L8051 = null;	} 
		if (!in.readBoolean()) {	L8052 =   new IntWritable(in.readInt());	} else {	L8052 = null;	} 
		if (!in.readBoolean()) {	L8053 =   new IntWritable(in.readInt());	} else {	L8053 = null;	} 
		if (!in.readBoolean()) {	L8054 =   new IntWritable(in.readInt());	} else {	L8054 = null;	} 
		if (!in.readBoolean()) {	L8055 =   new IntWritable(in.readInt());	} else {	L8055 = null;	} 
		if (!in.readBoolean()) {	L8056 =   new IntWritable(in.readInt());	} else {	L8056 = null;	} 
		if (!in.readBoolean()) {	L8057 =   new IntWritable(in.readInt());	} else {	L8057 = null;	} 
		if (!in.readBoolean()) {	L8058 =   new IntWritable(in.readInt());	} else {	L8058 = null;	} 
		if (!in.readBoolean()) {	L8059 =   new IntWritable(in.readInt());	} else {	L8059 = null;	} 
		if (!in.readBoolean()) {	L805a =   new IntWritable(in.readInt());	} else {	L805a = null;	} 
		if (!in.readBoolean()) {	L805b =   new IntWritable(in.readInt());	} else {	L805b = null;	} 
		if (!in.readBoolean()) {	L805c =   new IntWritable(in.readInt());	} else {	L805c = null;	} 
		if (!in.readBoolean()) {	L805d =   new IntWritable(in.readInt());	} else {	L805d = null;	} 
		if (!in.readBoolean()) {	L805e =   new IntWritable(in.readInt());	} else {	L805e = null;	} 
		if (!in.readBoolean()) {	L805f =   new IntWritable(in.readInt());	} else {	L805f = null;	} 
		if (!in.readBoolean()) {	L8060 =   new IntWritable(in.readInt());	} else {	L8060 = null;	} 
		if (!in.readBoolean()) {	L8061 =   new IntWritable(in.readInt());	} else {	L8061 = null;	} 
		if (!in.readBoolean()) {	L8062 =   new IntWritable(in.readInt());	} else {	L8062 = null;	} 
		if (!in.readBoolean()) {	L8063 =   new IntWritable(in.readInt());	} else {	L8063 = null;	} 
		if (!in.readBoolean()) {	L8064 =   new IntWritable(in.readInt());	} else {	L8064 = null;	} 
		if (!in.readBoolean()) {	L8065 =   new IntWritable(in.readInt());	} else {	L8065 = null;	} 
		if (!in.readBoolean()) {	L8066 =   new IntWritable(in.readInt());	} else {	L8066 = null;	} 
		if (!in.readBoolean()) {	L8067 =   new IntWritable(in.readInt());	} else {	L8067 = null;	} 
		if (!in.readBoolean()) {	L8068 =   new IntWritable(in.readInt());	} else {	L8068 = null;	} 
		if (!in.readBoolean()) {	L8069 =   new IntWritable(in.readInt());	} else {	L8069 = null;	} 
		if (!in.readBoolean()) {	L806a =   new IntWritable(in.readInt());	} else {	L806a = null;	} 
		if (!in.readBoolean()) {	L806b =   new IntWritable(in.readInt());	} else {	L806b = null;	} 
		if (!in.readBoolean()) {	L806c =   new IntWritable(in.readInt());	} else {	L806c = null;	} 
		if (!in.readBoolean()) {	L806d =   new IntWritable(in.readInt());	} else {	L806d = null;	} 
		if (!in.readBoolean()) {	L806e =   new IntWritable(in.readInt());	} else {	L806e = null;	} 
		if (!in.readBoolean()) {	L806f =   new IntWritable(in.readInt());	} else {	L806f = null;	} 
		if (!in.readBoolean()) {	L8070 =   new IntWritable(in.readInt());	} else {	L8070 = null;	} 
		if (!in.readBoolean()) {	L8071 =   new IntWritable(in.readInt());	} else {	L8071 = null;	} 
		if (!in.readBoolean()) {	L8072 =   new IntWritable(in.readInt());	} else {	L8072 = null;	} 
		if (!in.readBoolean()) {	L8073 =   new IntWritable(in.readInt());	} else {	L8073 = null;	} 
		if (!in.readBoolean()) {	L8074 =   new IntWritable(in.readInt());	} else {	L8074 = null;	} 
		if (!in.readBoolean()) {	L8075 =   new IntWritable(in.readInt());	} else {	L8075 = null;	} 
		if (!in.readBoolean()) {	L8076 =   new IntWritable(in.readInt());	} else {	L8076 = null;	} 
		if (!in.readBoolean()) {	L8077 =   new IntWritable(in.readInt());	} else {	L8077 = null;	} 
		if (!in.readBoolean()) {	L8078 =   new IntWritable(in.readInt());	} else {	L8078 = null;	} 
//		if (!in.readBoolean()) {	L0100 =   new IntWritable(in.readInt());	} else {	L0100 = null;	} 
//		if (!in.readBoolean()) {	L0101 =   new IntWritable(in.readInt());	} else {	L0101 = null;	} 
//		if (!in.readBoolean()) {	L0102 =   new IntWritable(in.readInt());	} else {	L0102 = null;	} 
//		if (!in.readBoolean()) {	L0103 =   new IntWritable(in.readInt());	} else {	L0103 = null;	} 
//		if (!in.readBoolean()) {	L8100 =   new IntWritable(in.readInt());	} else {	L8100 = null;	} 
//		if (!in.readBoolean()) {	L8101 =   new IntWritable(in.readInt());	} else {	L8101 = null;	} 
//		if (!in.readBoolean()) {	L8102 =   new IntWritable(in.readInt());	} else {	L8102 = null;	} 
//		if (!in.readBoolean()) {	L8103 =   new IntWritable(in.readInt());	} else {	L8103 = null;	} 
//		if (!in.readBoolean()) {	L8104 =   new IntWritable(in.readInt());	} else {	L8104 = null;	} 
//		if (!in.readBoolean()) {	L8105 =   new IntWritable(in.readInt());	} else {	L8105 = null;	} 
//		if (!in.readBoolean()) {	L8106 =   new IntWritable(in.readInt());	} else {	L8106 = null;	} 
//		if (!in.readBoolean()) {	L8107 =   new IntWritable(in.readInt());	} else {	L8107 = null;	} 
//		if (!in.readBoolean()) {	L8108 =   new IntWritable(in.readInt());	} else {	L8108 = null;	} 
//		if (!in.readBoolean()) {	L8109 =   new IntWritable(in.readInt());	} else {	L8109 = null;	} 
//		if (!in.readBoolean()) {	L810a =   new IntWritable(in.readInt());	} else {	L810a = null;	} 
//		if (!in.readBoolean()) {	L810b =   new IntWritable(in.readInt());	} else {	L810b = null;	} 
//		if (!in.readBoolean()) {	L0070 = new FloatWritable(in.readFloat());	} else {	L0070 = null;	} 
//		if (!in.readBoolean()) {	L0071 = new FloatWritable(in.readFloat());	} else {	L0071 = null;	} 
//		if (!in.readBoolean()) {	L0072 = new FloatWritable(in.readFloat());	} else {	L0072 = null;	}
//		if (!in.readBoolean()) {	Lff01 =          new Text(in.readUTF());	} else {	Lff01 = null;	} 
		if (!in.readBoolean()) {	Lsid  =          new Text(in.readUTF());	} else {	Lsid  = null;	} 
	}
	
	public Text getCn() {
		return cn;
	}

	public void setCn(Text cn) {
		this.cn = cn;
	}

	public Text getCt() {
		return ct;
	}

	public void setCt(Text ct) {
		this.ct = ct;
	}

	public Text getSt() {
		return st;
	}

	public void setSt(Text st) {
		this.st = st;
	}

	public FloatWritable getL0002() {
		return L0002;
	}

	public void setL0002(FloatWritable L0002) {
		this.L0002 = L0002;
	}

	public FloatWritable getL0003() {
		return L0003;
	}

	public void setL0003(FloatWritable L0003) {
		this.L0003 = L0003;
	}

	public IntWritable getL4013() {
		return L4013;
	}

	public void setL4013(IntWritable l4013) {
		this.L4013 = l4013;
	}

	public IntWritable getL0004() {
		return L0004;
	}

	public void setL0004(IntWritable l0004) {
		this.L0004 = l0004;
	}

	public IntWritable getL0005() {
		return L0005;
	}

	public void setL0005(IntWritable l0005) {
		this.L0005 = l0005;
	}

	public IntWritable getL0006() {
		return L0006;
	}

	public void setL0006(IntWritable L0006) {
		this.L0006 = L0006;
	}

	public IntWritable getL0009() {
		return L0009;
	}

	public void setL0009(IntWritable L0009) {
		this.L0009 = L0009;
	}

	public IntWritable getL0008() {
		return L0008;
	}

	public void setL0008(IntWritable L0008) {
		this.L0008 = L0008;
	}

	public IntWritable getL0024() {
		return L0024;
	}

	public void setL0024(IntWritable L0024) {
		this.L0024 = L0024;
	}

	public IntWritable getL0025() {
		return L0025;
	}

	public void setL0025(IntWritable L0025) {
		this.L0025 = L0025;
	}

	public FloatWritable getL0014() {
		return L0014;
	}

	public void setL0014(FloatWritable L0014) {
		this.L0014 = L0014;
	}

	public FloatWritable getL001f() {
		return L001f;
	}

	public void setL001f(FloatWritable L001f) {
		this.L001f = L001f;
	}

	public IntWritable getL0028() {
		return L0028;
	}

	public void setL0028(IntWritable L0028) {
		this.L0028 = L0028;
	}

	public FloatWritable getL7d02() {
		return L7d02;
	}

	public void setL7d02(FloatWritable L7d02) {
		this.L7d02 = L7d02;
	}

	public FloatWritable getL0007() {
		return L0007;
	}

	public void setL0007(FloatWritable l0007) {
		L0007 = l0007;
	}

//	public IntWritable getL000a() {
//		return L000a;
//	}
//
//	public void setL000a(IntWritable l000a) {
//		L000a = l000a;
//	}
//
//	public IntWritable getL000b() {
//		return L000b;
//	}
//
//	public void setL000b(IntWritable l000b) {
//		L000b = l000b;
//	}
//
//	public IntWritable getL000d() {
//		return L000d;
//	}
//
//	public void setL000d(IntWritable l000d) {
//		L000d = l000d;
//	}
//
//	public FloatWritable getL000e() {
//		return L000e;
//	}
//
//	public void setL000e(FloatWritable l000e) {
//		L000e = l000e;
//	}

	public FloatWritable getL0010() {
		return L0010;
	}

	public void setL0010(FloatWritable l0010) {
		L0010 = l0010;
	}

	public IntWritable getL0011() {
		return L0011;
	}

	public void setL0011(IntWritable l0011) {
		L0011 = l0011;
	}

//	public IntWritable getL0027() {
//		return L0027;
//	}
//
//	public void setL0027(IntWritable l0027) {
//		L0027 = l0027;
//	}

	public IntWritable getL0029() {
		return L0029;
	}

	public void setL0029(IntWritable l0029) {
		L0029 = l0029;
	}

	public IntWritable getL002a() {
		return L002a;
	}

	public void setL002a(IntWritable l002a) {
		L002a = l002a;
	}

	public IntWritable getL002b() {
		return L002b;
	}

	public void setL002b(IntWritable l002b) {
		L002b = l002b;
	}

	public IntWritable getL002c() {
		return L002c;
	}

	public void setL002c(IntWritable l002c) {
		L002c = l002c;
	}

//	public IntWritable getL002d() {
//		return L002d;
//	}
//
//	public void setL002d(IntWritable l002d) {
//		L002d = l002d;
//	}
//
//	public IntWritable getL002e() {
//		return L002e;
//	}
//
//	public void setL002e(IntWritable l002e) {
//		L002e = l002e;
//	}
//
//	public IntWritable getL002f() {
//		return L002f;
//	}

//	public void setL002f(IntWritable l002f) {
//		L002f = l002f;
//	}
//
//	public IntWritable getL0030() {
//		return L0030;
//	}
//
//	public void setL0030(IntWritable l0030) {
//		L0030 = l0030;
//	}
//
//	public IntWritable getL0031() {
//		return L0031;
//	}
//
//	public void setL0031(IntWritable l0031) {
//		L0031 = l0031;
//	}
//
//	public IntWritable getL0032() {
//		return L0032;
//	}
//
//	public void setL0032(IntWritable l0032) {
//		L0032 = l0032;
//	}
//
//	public IntWritable getL0033() {
//		return L0033;
//	}
//
//	public void setL0033(IntWritable l0033) {
//		L0033 = l0033;
//	}
//
//	public IntWritable getL0034() {
//		return L0034;
//	}
//
//	public void setL0034(IntWritable l0034) {
//		L0034 = l0034;
//	}
//
//	public IntWritable getL0035() {
//		return L0035;
//	}
//
//	public void setL0035(IntWritable l0035) {
//		L0035 = l0035;
//	}
//
//	public IntWritable getL0036() {
//		return L0036;
//	}
//
//	public void setL0036(IntWritable l0036) {
//		L0036 = l0036;
//	}
//
//	public IntWritable getL0039() {
//		return L0039;
//	}
//
//	public void setL0039(IntWritable l0039) {
//		L0039 = l0039;
//	}
//
//	public IntWritable getL0040() {
//		return L0040;
//	}
//
//	public void setL0040(IntWritable l0040) {
//		L0040 = l0040;
//	}
//
//	public IntWritable getL0041() {
//		return L0041;
//	}
//
//	public void setL0041(IntWritable l0041) {
//		L0041 = l0041;
//	}
//
//	public IntWritable getL0042() {
//		return L0042;
//	}
//
//	public void setL0042(IntWritable l0042) {
//		L0042 = l0042;
//	}

	public IntWritable getL0043() {
		return L0043;
	}

	public void setL0043(IntWritable l0043) {
		L0043 = l0043;
	}

//	public IntWritable getL0044() {
//		return L0044;
//	}
//
//	public void setL0044(IntWritable l0044) {
//		L0044 = l0044;
//	}
//
//	public IntWritable getL0045() {
//		return L0045;
//	}
//
//	public void setL0045(IntWritable l0045) {
//		L0045 = l0045;
//	}
//
//	public IntWritable getL0046() {
//		return L0046;
//	}
//
//	public void setL0046(IntWritable l0046) {
//		L0046 = l0046;
//	}
//
//	public IntWritable getL0047() {
//		return L0047;
//	}
//
//	public void setL0047(IntWritable l0047) {
//		L0047 = l0047;
//	}
//
//	public IntWritable getL0048() {
//		return L0048;
//	}
//
//	public void setL0048(IntWritable l0048) {
//		L0048 = l0048;
//	}
//
//	public IntWritable getL0049() {
//		return L0049;
//	}
//
//	public void setL0049(IntWritable l0049) {
//		L0049 = l0049;
//	}
//
//	public IntWritable getL004a() {
//		return L004a;
//	}
//
//	public void setL004a(IntWritable l004a) {
//		L004a = l004a;
//	}
//
//	public FloatWritable getL0050() {
//		return L0050;
//	}
//
//	public void setL0050(FloatWritable l0050) {
//		L0050 = l0050;
//	}
//
//	public FloatWritable getL0051() {
//		return L0051;
//	}
//
//	public void setL0051(FloatWritable l0051) {
//		L0051 = l0051;
//	}
//
//	public FloatWritable getL0052() {
//		return L0052;
//	}
//
//	public void setL0052(FloatWritable l0052) {
//		L0052 = l0052;
//	}
//
//	public FloatWritable getL0053() {
//		return L0053;
//	}
//
//	public void setL0053(FloatWritable l0053) {
//		L0053 = l0053;
//	}
//
//	public FloatWritable getL0054() {
//		return L0054;
//	}
//
//	public void setL0054(FloatWritable l0054) {
//		L0054 = l0054;
//	}
//
//	public FloatWritable getL0055() {
//		return L0055;
//	}
//
//	public void setL0055(FloatWritable l0055) {
//		L0055 = l0055;
//	}
//
//	public FloatWritable getL0056() {
//		return L0056;
//	}
//
//	public void setL0056(FloatWritable l0056) {
//		L0056 = l0056;
//	}
//
//	public FloatWritable getL0057() {
//		return L0057;
//	}
//
//	public void setL0057(FloatWritable l0057) {
//		L0057 = l0057;
//	}

	public FloatWritable getL7d01() {
		return L7d01;
	}

	public void setL7d01(FloatWritable l7d01) {
		L7d01 = l7d01;
	}

	public IntWritable getL7d03() {
		return L7d03;
	}

	public void setL7d03(IntWritable l7d03) {
		L7d03 = l7d03;
	}

	public IntWritable getL7d04() {
		return L7d04;
	}

	public void setL7d04(IntWritable l7d04) {
		L7d04 = l7d04;
	}

	public IntWritable getL7d05() {
		return L7d05;
	}

	public void setL7d05(IntWritable l7d05) {
		L7d05 = l7d05;
	}

	public IntWritable getL7d06() {
		return L7d06;
	}

	public void setL7d06(IntWritable l7d06) {
		L7d06 = l7d06;
	}

	public IntWritable getL7d07() {
		return L7d07;
	}

	public void setL7d07(IntWritable l7d07) {
		L7d07 = l7d07;
	}

	public IntWritable getL7d08() {
		return L7d08;
	}

	public void setL7d08(IntWritable l7d08) {
		L7d08 = l7d08;
	}

	public FloatWritable getL7d09() {
		return L7d09;
	}

	public void setL7d09(FloatWritable l7d09) {
		L7d09 = l7d09;
	}

	public IntWritable getL7d0a() {
		return L7d0a;
	}

	public void setL7d0a(IntWritable l7d0a) {
		L7d0a = l7d0a;
	}

	public IntWritable getL7d0b() {
		return L7d0b;
	}

	public void setL7d0b(IntWritable l7d0b) {
		L7d0b = l7d0b;
	}

	public FloatWritable getL7d0c() {
		return L7d0c;
	}

	public void setL7d0c(FloatWritable l7d0c) {
		L7d0c = l7d0c;
	}

	public IntWritable getL7d0d() {
		return L7d0d;
	}

	public void setL7d0d(IntWritable l7d0d) {
		L7d0d = l7d0d;
	}

//	public IntWritable getL7f01() {
//		return L7f01;
//	}
//
//	public void setL7f01(IntWritable l7f01) {
//		L7f01 = l7f01;
//	}
//
//	public IntWritable getL7f02() {
//		return L7f02;
//	}
//
//	public void setL7f02(IntWritable l7f02) {
//		L7f02 = l7f02;
//	}
//
//	public IntWritable getL7f03() {
//		return L7f03;
//	}
//
//	public void setL7f03(IntWritable l7f03) {
//		L7f03 = l7f03;
//	}
//
//	public IntWritable getL7f04() {
//		return L7f04;
//	}
//
//	public void setL7f04(IntWritable l7f04) {
//		L7f04 = l7f04;
//	}
//
//	public IntWritable getL7f05() {
//		return L7f05;
//	}
//
//	public void setL7f05(IntWritable l7f05) {
//		L7f05 = l7f05;
//	}
//
//	public IntWritable getL7f06() {
//		return L7f06;
//	}
//
//	public void setL7f06(IntWritable l7f06) {
//		L7f06 = l7f06;
//	}
//
//	public IntWritable getL7f07() {
//		return L7f07;
//	}
//
//	public void setL7f07(IntWritable l7f07) {
//		L7f07 = l7f07;
//	}
//
//	public IntWritable getL7f08() {
//		return L7f08;
//	}
//
//	public void setL7f08(IntWritable l7f08) {
//		L7f08 = l7f08;
//	}
//
//	public IntWritable getL7f09() {
//		return L7f09;
//	}
//
//	public void setL7f09(IntWritable l7f09) {
//		L7f09 = l7f09;
//	}
//
//	public IntWritable getL7f0a() {
//		return L7f0a;
//	}
//
//	public void setL7f0a(IntWritable l7f0a) {
//		L7f0a = l7f0a;
//	}
//
//	public IntWritable getL7f0b() {
//		return L7f0b;
//	}
//
//	public void setL7f0b(IntWritable l7f0b) {
//		L7f0b = l7f0b;
//	}

	public IntWritable getL7f0c() {
		return L7f0c;
	}

	public void setL7f0c(IntWritable l7f0c) {
		L7f0c = l7f0c;
	}

//	public IntWritable getL7f0d() {
//		return L7f0d;
//	}
//
//	public void setL7f0d(IntWritable l7f0d) {
//		L7f0d = l7f0d;
//	}
//
//	public IntWritable getL7f0e() {
//		return L7f0e;
//	}
//
//	public void setL7f0e(IntWritable l7f0e) {
//		L7f0e = l7f0e;
//	}
//
//	public IntWritable getL7f0f() {
//		return L7f0f;
//	}
//
//	public void setL7f0f(IntWritable l7f0f) {
//		L7f0f = l7f0f;
//	}
//
//	public IntWritable getL7f10() {
//		return L7f10;
//	}
//
//	public void setL7f10(IntWritable l7f10) {
//		L7f10 = l7f10;
//	}
//
//	public IntWritable getL7f11() {
//		return L7f11;
//	}
//
//	public void setL7f11(IntWritable l7f11) {
//		L7f11 = l7f11;
//	}
//
//	public IntWritable getL7f12() {
//		return L7f12;
//	}
//
//	public void setL7f12(IntWritable l7f12) {
//		L7f12 = l7f12;
//	}
//
//	public IntWritable getL7f13() {
//		return L7f13;
//	}
//
//	public void setL7f13(IntWritable l7f13) {
//		L7f13 = l7f13;
//	}
//
//	public IntWritable getL7f14() {
//		return L7f14;
//	}
//
//	public void setL7f14(IntWritable l7f14) {
//		L7f14 = l7f14;
//	}
//
//	public IntWritable getL7f15() {
//		return L7f15;
//	}
//
//	public void setL7f15(IntWritable l7f15) {
//		L7f15 = l7f15;
//	}
//
//	public IntWritable getL7f16() {
//		return L7f16;
//	}
//
//	public void setL7f16(IntWritable l7f16) {
//		L7f16 = l7f16;
//	}
//
//	public IntWritable getL7f17() {
//		return L7f17;
//	}
//
//	public void setL7f17(IntWritable l7f17) {
//		L7f17 = l7f17;
//	}
//
//	public IntWritable getL7f18() {
//		return L7f18;
//	}
//
//	public void setL7f18(IntWritable l7f18) {
//		L7f18 = l7f18;
//	}
//
//	public IntWritable getL7f19() {
//		return L7f19;
//	}
//
//	public void setL7f19(IntWritable l7f19) {
//		L7f19 = l7f19;
//	}
//
//	public IntWritable getL7f1a() {
//		return L7f1a;
//	}
//
//	public void setL7f1a(IntWritable l7f1a) {
//		L7f1a = l7f1a;
//	}
//
//	public IntWritable getL7f1b() {
//		return L7f1b;
//	}
//
//	public void setL7f1b(IntWritable l7f1b) {
//		L7f1b = l7f1b;
//	}
//
//	public IntWritable getL7f1c() {
//		return L7f1c;
//	}
//
//	public void setL7f1c(IntWritable l7f1c) {
//		L7f1c = l7f1c;
//	}
//
//	public IntWritable getL7f1d() {
//		return L7f1d;
//	}
//
//	public void setL7f1d(IntWritable l7f1d) {
//		L7f1d = l7f1d;
//	}
//
//	public IntWritable getL7f1e() {
//		return L7f1e;
//	}
//
//	public void setL7f1e(IntWritable l7f1e) {
//		L7f1e = l7f1e;
//	}
//
//	public IntWritable getL7f1f() {
//		return L7f1f;
//	}
//
//	public void setL7f1f(IntWritable l7f1f) {
//		L7f1f = l7f1f;
//	}
//
//	public IntWritable getL7f20() {
//		return L7f20;
//	}
//
//	public void setL7f20(IntWritable l7f20) {
//		L7f20 = l7f20;
//	}
//
//	public IntWritable getL7f21() {
//		return L7f21;
//	}
//
//	public void setL7f21(IntWritable l7f21) {
//		L7f21 = l7f21;
//	}
//
//	public IntWritable getL7f22() {
//		return L7f22;
//	}
//
//	public void setL7f22(IntWritable l7f22) {
//		L7f22 = l7f22;
//	}
//
//	public IntWritable getL7f23() {
//		return L7f23;
//	}
//
//	public void setL7f23(IntWritable l7f23) {
//		L7f23 = l7f23;
//	}
//
//	public IntWritable getL7f24() {
//		return L7f24;
//	}
//
//	public void setL7f24(IntWritable l7f24) {
//		L7f24 = l7f24;
//	}
//
//	public IntWritable getL7f25() {
//		return L7f25;
//	}
//
//	public void setL7f25(IntWritable l7f25) {
//		L7f25 = l7f25;
//	}
//
//	public IntWritable getL7f26() {
//		return L7f26;
//	}
//
//	public void setL7f26(IntWritable l7f26) {
//		L7f26 = l7f26;
//	}
//
//	public IntWritable getL7f27() {
//		return L7f27;
//	}
//
//	public void setL7f27(IntWritable l7f27) {
//		L7f27 = l7f27;
//	}
//
//	public IntWritable getL7f28() {
//		return L7f28;
//	}
//
//	public void setL7f28(IntWritable l7f28) {
//		L7f28 = l7f28;
//	}
//
//	public IntWritable getL7f29() {
//		return L7f29;
//	}
//
//	public void setL7f29(IntWritable l7f29) {
//		L7f29 = l7f29;
//	}
//
//	public IntWritable getL7f2a() {
//		return L7f2a;
//	}
//
//	public void setL7f2a(IntWritable l7f2a) {
//		L7f2a = l7f2a;
//	}
//
//	public IntWritable getL7f2b() {
//		return L7f2b;
//	}
//
//	public void setL7f2b(IntWritable l7f2b) {
//		L7f2b = l7f2b;
//	}
//
//	public IntWritable getL7f2c() {
//		return L7f2c;
//	}
//
//	public void setL7f2c(IntWritable l7f2c) {
//		L7f2c = l7f2c;
//	}
//
//	public IntWritable getL7f2d() {
//		return L7f2d;
//	}
//
//	public void setL7f2d(IntWritable l7f2d) {
//		L7f2d = l7f2d;
//	}
//
//	public IntWritable getL7f2e() {
//		return L7f2e;
//	}
//
//	public void setL7f2e(IntWritable l7f2e) {
//		L7f2e = l7f2e;
//	}
//
//	public IntWritable getL7f2f() {
//		return L7f2f;
//	}
//
//	public void setL7f2f(IntWritable l7f2f) {
//		L7f2f = l7f2f;
//	}
//
//	public IntWritable getL7f30() {
//		return L7f30;
//	}
//
//	public void setL7f30(IntWritable l7f30) {
//		L7f30 = l7f30;
//	}
//
//	public IntWritable getL7f31() {
//		return L7f31;
//	}
//
//	public void setL7f31(IntWritable l7f31) {
//		L7f31 = l7f31;
//	}
//
//	public IntWritable getL7f32() {
//		return L7f32;
//	}
//
//	public void setL7f32(IntWritable l7f32) {
//		L7f32 = l7f32;
//	}
//
//	public Text getL8001() {
//		return L8001;
//	}
//
//	public void setL8001(Text l8001) {
//		L8001 = l8001;
//	}
//
//	public Text getL8002() {
//		return L8002;
//	}
//
//	public void setL8002(Text l8002) {
//		L8002 = l8002;
//	}
//
//	public Text getL8003() {
//		return L8003;
//	}
//
//	public void setL8003(Text l8003) {
//		L8003 = l8003;
//	}
//
//	public Text getL8004() {
//		return L8004;
//	}
//
//	public void setL8004(Text l8004) {
//		L8004 = l8004;
//	}
//
//	public Text getL8005() {
//		return L8005;
//	}
//
//	public void setL8005(Text l8005) {
//		L8005 = l8005;
//	}
//
//	public Text getL8006() {
//		return L8006;
//	}
//
//	public void setL8006(Text l8006) {
//		L8006 = l8006;
//	}
//
//	public Text getL8007() {
//		return L8007;
//	}
//
//	public void setL8007(Text l8007) {
//		L8007 = l8007;
//	}
//
//	public Text getL8008() {
//		return L8008;
//	}
//
//	public void setL8008(Text l8008) {
//		L8008 = l8008;
//	}
//
//	public Text getL8009() {
//		return L8009;
//	}
//
//	public void setL8009(Text l8009) {
//		L8009 = l8009;
//	}
//
//	public Text getL800a() {
//		return L800a;
//	}
//
//	public void setL800a(Text l800a) {
//		L800a = l800a;
//	}
//
//	public Text getL800b() {
//		return L800b;
//	}
//
//	public void setL800b(Text l800b) {
//		L800b = l800b;
//	}
//
//	public Text getL800c() {
//		return L800c;
//	}
//
//	public void setL800c(Text l800c) {
//		L800c = l800c;
//	}

//	public Text getL800d() {
//		return L800d;
//	}
//
//	public void setL800d(Text l800d) {
//		L800d = l800d;
//	}
//
//	public Text getL800e() {
//		return L800e;
//	}
//
//	public void setL800e(Text l800e) {
//		L800e = l800e;
//	}
//
//	public Text getL800f() {
//		return L800f;
//	}
//
//	public void setL800f(Text l800f) {
//		L800f = l800f;
//	}
//
//	public Text getL8010() {
//		return L8010;
//	}
//
//	public void setL8010(Text l8010) {
//		L8010 = l8010;
//	}
//
//	public Text getL8011() {
//		return L8011;
//	}
//
//	public void setL8011(Text l8011) {
//		L8011 = l8011;
//	}

	public IntWritable getL8051() {
		return L8051;
	}

	public void setL8051(IntWritable l8051) {
		L8051 = l8051;
	}

	public IntWritable getL8052() {
		return L8052;
	}

	public void setL8052(IntWritable l8052) {
		L8052 = l8052;
	}

	public IntWritable getL8053() {
		return L8053;
	}

	public void setL8053(IntWritable l8053) {
		L8053 = l8053;
	}

	public IntWritable getL8054() {
		return L8054;
	}

	public void setL8054(IntWritable l8054) {
		L8054 = l8054;
	}

	public IntWritable getL8055() {
		return L8055;
	}

	public void setL8055(IntWritable l8055) {
		L8055 = l8055;
	}

	public IntWritable getL8056() {
		return L8056;
	}

	public void setL8056(IntWritable l8056) {
		L8056 = l8056;
	}

	public IntWritable getL8057() {
		return L8057;
	}

	public void setL8057(IntWritable l8057) {
		L8057 = l8057;
	}

	public IntWritable getL8058() {
		return L8058;
	}

	public void setL8058(IntWritable l8058) {
		L8058 = l8058;
	}

	public IntWritable getL8059() {
		return L8059;
	}

	public void setL8059(IntWritable l8059) {
		L8059 = l8059;
	}

	public IntWritable getL805a() {
		return L805a;
	}

	public void setL805a(IntWritable l805a) {
		L805a = l805a;
	}

	public IntWritable getL805b() {
		return L805b;
	}

	public void setL805b(IntWritable l805b) {
		L805b = l805b;
	}

	public IntWritable getL805c() {
		return L805c;
	}

	public void setL805c(IntWritable l805c) {
		L805c = l805c;
	}

	public IntWritable getL805d() {
		return L805d;
	}

	public void setL805d(IntWritable l805d) {
		L805d = l805d;
	}

	public IntWritable getL805e() {
		return L805e;
	}

	public void setL805e(IntWritable l805e) {
		L805e = l805e;
	}

	public IntWritable getL805f() {
		return L805f;
	}

	public void setL805f(IntWritable l805f) {
		L805f = l805f;
	}

	public IntWritable getL8060() {
		return L8060;
	}

	public void setL8060(IntWritable l8060) {
		L8060 = l8060;
	}

	public IntWritable getL8061() {
		return L8061;
	}

	public void setL8061(IntWritable l8061) {
		L8061 = l8061;
	}

	public IntWritable getL8062() {
		return L8062;
	}

	public void setL8062(IntWritable l8062) {
		L8062 = l8062;
	}

	public IntWritable getL8063() {
		return L8063;
	}

	public void setL8063(IntWritable l8063) {
		L8063 = l8063;
	}

	public IntWritable getL8064() {
		return L8064;
	}

	public void setL8064(IntWritable l8064) {
		L8064 = l8064;
	}

	public IntWritable getL8065() {
		return L8065;
	}

	public void setL8065(IntWritable l8065) {
		L8065 = l8065;
	}

	public IntWritable getL8066() {
		return L8066;
	}

	public void setL8066(IntWritable l8066) {
		L8066 = l8066;
	}

	public IntWritable getL8067() {
		return L8067;
	}

	public void setL8067(IntWritable l8067) {
		L8067 = l8067;
	}

	public IntWritable getL8068() {
		return L8068;
	}

	public void setL8068(IntWritable l8068) {
		L8068 = l8068;
	}

	public IntWritable getL8069() {
		return L8069;
	}

	public void setL8069(IntWritable l8069) {
		L8069 = l8069;
	}

	public IntWritable getL806a() {
		return L806a;
	}

	public void setL806a(IntWritable l806a) {
		L806a = l806a;
	}

	public IntWritable getL806b() {
		return L806b;
	}

	public void setL806b(IntWritable l806b) {
		L806b = l806b;
	}

	public IntWritable getL806c() {
		return L806c;
	}

	public void setL806c(IntWritable l806c) {
		L806c = l806c;
	}

	public IntWritable getL806d() {
		return L806d;
	}

	public void setL806d(IntWritable l806d) {
		L806d = l806d;
	}

	public IntWritable getL806e() {
		return L806e;
	}

	public void setL806e(IntWritable l806e) {
		L806e = l806e;
	}

	public IntWritable getL806f() {
		return L806f;
	}

	public void setL806f(IntWritable l806f) {
		L806f = l806f;
	}

	public IntWritable getL8070() {
		return L8070;
	}

	public void setL8070(IntWritable l8070) {
		L8070 = l8070;
	}

	public IntWritable getL8071() {
		return L8071;
	}

	public void setL8071(IntWritable l8071) {
		L8071 = l8071;
	}

	public IntWritable getL8072() {
		return L8072;
	}

	public void setL8072(IntWritable l8072) {
		L8072 = l8072;
	}

	public IntWritable getL8073() {
		return L8073;
	}

	public void setL8073(IntWritable l8073) {
		L8073 = l8073;
	}

	public IntWritable getL8074() {
		return L8074;
	}

	public void setL8074(IntWritable l8074) {
		L8074 = l8074;
	}

	public IntWritable getL8075() {
		return L8075;
	}

	public void setL8075(IntWritable l8075) {
		L8075 = l8075;
	}

	public IntWritable getL8076() {
		return L8076;
	}

	public void setL8076(IntWritable l8076) {
		L8076 = l8076;
	}

	public IntWritable getL8077() {
		return L8077;
	}

	public void setL8077(IntWritable l8077) {
		L8077 = l8077;
	}

	public IntWritable getL8078() {
		return L8078;
	}

	public void setL8078(IntWritable l8078) {
		L8078 = l8078;
	}

//	public IntWritable getL0100() {
//		return L0100;
//	}
//
//	public void setL0100(IntWritable l0100) {
//		L0100 = l0100;
//	}
//
//	public IntWritable getL0101() {
//		return L0101;
//	}
//
//	public void setL0101(IntWritable l0101) {
//		L0101 = l0101;
//	}
//
//	public IntWritable getL0102() {
//		return L0102;
//	}
//
//	public void setL0102(IntWritable l0102) {
//		L0102 = l0102;
//	}
//
//	public IntWritable getL0103() {
//		return L0103;
//	}
//
//	public void setL0103(IntWritable l0103) {
//		L0103 = l0103;
//	}
//
//	public IntWritable getL8100() {
//		return L8100;
//	}
//
//	public void setL8100(IntWritable l8100) {
//		L8100 = l8100;
//	}
//
//	public IntWritable getL8101() {
//		return L8101;
//	}
//
//	public void setL8101(IntWritable l8101) {
//		L8101 = l8101;
//	}
//
//	public IntWritable getL8102() {
//		return L8102;
//	}
//
//	public void setL8102(IntWritable l8102) {
//		L8102 = l8102;
//	}
//
//	public IntWritable getL8103() {
//		return L8103;
//	}
//
//	public void setL8103(IntWritable l8103) {
//		L8103 = l8103;
//	}
//
//	public IntWritable getL8104() {
//		return L8104;
//	}
//
//	public void setL8104(IntWritable l8104) {
//		L8104 = l8104;
//	}
//
//	public IntWritable getL8105() {
//		return L8105;
//	}
//
//	public void setL8105(IntWritable l8105) {
//		L8105 = l8105;
//	}
//
//	public IntWritable getL8106() {
//		return L8106;
//	}
//
//	public void setL8106(IntWritable l8106) {
//		L8106 = l8106;
//	}
//
//	public IntWritable getL8107() {
//		return L8107;
//	}
//
//	public void setL8107(IntWritable l8107) {
//		L8107 = l8107;
//	}
//
//	public IntWritable getL8108() {
//		return L8108;
//	}
//
//	public void setL8108(IntWritable l8108) {
//		L8108 = l8108;
//	}
//
//	public IntWritable getL8109() {
//		return L8109;
//	}
//
//	public void setL8109(IntWritable l8109) {
//		L8109 = l8109;
//	}
//
//	public IntWritable getL810a() {
//		return L810a;
//	}
//
//	public void setL810a(IntWritable l810a) {
//		L810a = l810a;
//	}
//
//	public IntWritable getL810b() {
//		return L810b;
//	}
//
//	public void setL810b(IntWritable l810b) {
//		L810b = l810b;
//	}
//
//	public FloatWritable getL0070() {
//		return L0070;
//	}
//
//	public void setL0070(FloatWritable l0070) {
//		L0070 = l0070;
//	}
//
//	public FloatWritable getL0071() {
//		return L0071;
//	}
//
//	public void setL0071(FloatWritable l0071) {
//		L0071 = l0071;
//	}
//
//	public FloatWritable getL0072() {
//		return L0072;
//	}
//
//	public void setL0072(FloatWritable l0072) {
//		L0072 = l0072;
//	}
//
//	public IntWritable getL0037() {
//		return L0037;
//	}
//
//	public void setL0037(IntWritable l0037) {
//		L0037 = l0037;
//	}
//
//	public IntWritable getL0038() {
//		return L0038;
//	}
//
//	public void setL0038(IntWritable l0038) {
//		L0038 = l0038;
//	}
//
//	public Text getLff01() {
//		return Lff01;
//	}
//
//	public void setLff01(Text lff01) {
//		Lff01 = lff01;
//	}

	public Text getLsid() {
		return Lsid;
	}

	public void setLsid(Text lsid) {
		Lsid = lsid;
	}
	
}