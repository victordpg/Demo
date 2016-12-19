package com.roiland.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hive.cli.CliSessionState;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hive.hcatalog.common.HCatException;
import org.apache.hive.hcatalog.common.HCatUtil;
import org.apache.hive.hcatalog.data.DefaultHCatRecord;
import org.apache.hive.hcatalog.data.HCatRecord;
import org.apache.hive.hcatalog.data.schema.HCatSchema;
import org.apache.hive.hcatalog.mapreduce.HCatInputFormat;
import org.apache.hive.hcatalog.mapreduce.HCatMultipleInputs;
import org.apache.hive.hcatalog.mapreduce.HCatOutputFormat;
import org.apache.hive.hcatalog.mapreduce.MultiOutputFormat;
import org.apache.hive.hcatalog.mapreduce.MultiOutputFormat.JobConfigurer;
import org.apache.hive.hcatalog.mapreduce.OutputJobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.roiland.hadoop.bean.HistoryTravelDataWritable;

/**
 * 历史行车数据清洗，一天执行一次。<p>
 * 需求说明：<br>
 * 需要将前一天清洗后的每个底盘号最后一条数据与当天要清洗的数据放到一起来做。<br>
 * 注意不要将昨天的数据重复的放入今天清洗结果。<br>
 * 清洗后把当天每个底盘号对应的最后数据放到库中待下次使用。
 * 
 * @author dywane.diao
 */
public class DataWashing extends Configured implements Tool {
	private static final Logger logger = LoggerFactory.getLogger(DataWashing.class);
	
	private static class CombinationKey implements WritableComparable<CombinationKey> {
		private Text cnKey;
		private Text ctKey;

		public CombinationKey() {
			this.cnKey = new Text();
			this.ctKey = new Text();
		}

		@Override
		public void readFields(DataInput dateInput) throws IOException {
			this.cnKey.readFields(dateInput);
			this.ctKey.readFields(dateInput);
		}

		@Override
		public void write(DataOutput outPut) throws IOException {
			this.cnKey.write(outPut);
			this.ctKey.write(outPut);
		}

		@Override
		public int hashCode() {
			return cnKey.hashCode() * 63 + ctKey.hashCode();
		}

		@Override
		public boolean equals(Object right) {
			if (right instanceof CombinationKey) {
				CombinationKey r = (CombinationKey) right;
				return r.cnKey.equals(cnKey)&&r.ctKey.equals(ctKey);
			} else {
				return false;
			}
		}

		@Override
		public int compareTo(CombinationKey thatKey) {
			Text thisCnKey = this.getCnKey();
			Text thatCnKey = thatKey.getCnKey();
			Text thisCtKey = this.getCtKey();
			Text thatCtKey = thatKey.getCtKey();
			
			if (thisCnKey.compareTo(thatCnKey)==0) {
				return thisCtKey.compareTo(thatCtKey);
			}else{
				return thisCnKey.compareTo(thatCnKey);
			}
		}
		
		public Text getCnKey() {
			return cnKey;
		}

		public void setCnKey(Text cnKey) {
			this.cnKey = cnKey;
		}
		
		public Text getCtKey() {
			return ctKey;
		}

		public void setCtKey(Text ctKey) {
			this.ctKey = ctKey;
		}
	}	
	
	private static class FirstPartitioner extends HashPartitioner<CombinationKey, HistoryTravelDataWritable> {
		@Override
		public int getPartition(CombinationKey key, HistoryTravelDataWritable value, int numReduceTasks) {
			return (key.getCnKey().hashCode() & Integer.MAX_VALUE) % numReduceTasks;
		}
	}

	private static class FirstGroupingComparator extends WritableComparator {
		@SuppressWarnings("unused")
		protected FirstGroupingComparator() {
			super(CombinationKey.class, true);
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			CombinationKey c1 = (CombinationKey) a;
			CombinationKey c2 = (CombinationKey) b;
			return c1.getCnKey().compareTo(c2.getCnKey());
		}
	}	
	
	public static class DataWashingMapper extends Mapper<WritableComparable<CombinationKey>, DefaultHCatRecord, CombinationKey, HistoryTravelDataWritable> {
		private HCatSchema recordSchema;
		private CombinationKey outKey = new CombinationKey();

		protected void setup(Context context)
				throws IOException, InterruptedException {
			recordSchema = HCatInputFormat.getTableSchema(context.getConfiguration());
		}

		protected void map(WritableComparable<CombinationKey> key, DefaultHCatRecord value, Context context) throws IOException, InterruptedException {
			HistoryTravelDataWritable historyTravelDataWritable = getWritableData(value, recordSchema);
			if (historyTravelDataWritable == null) {
				return;
			}
			outKey.setCnKey(new Text(historyTravelDataWritable.getCn().toString()));
			outKey.setCtKey(new Text(historyTravelDataWritable.getCt().toString()));
			context.write(outKey, historyTravelDataWritable); 
		}
	}
	
	public static class DataWashing4YesterdayMapper extends Mapper<WritableComparable<CombinationKey>, DefaultHCatRecord, CombinationKey, HistoryTravelDataWritable> {
		private HCatSchema recordSchema;
		private CombinationKey outKey = new CombinationKey();

		protected void setup(Context context)
				throws IOException, InterruptedException {
			recordSchema = HCatInputFormat.getTableSchema(context.getConfiguration());
		}

		protected void map(WritableComparable<CombinationKey> key, DefaultHCatRecord value, Context context) throws IOException, InterruptedException {
			HistoryTravelDataWritable historyTravelDataWritable = getWritableData4Yesterday(value, recordSchema);
			if (historyTravelDataWritable == null) {
				return;
			}
			outKey.setCnKey(new Text(historyTravelDataWritable.getCn().toString()));
			outKey.setCtKey(new Text(historyTravelDataWritable.getCt().toString()));
			context.write(outKey, historyTravelDataWritable); 
		}
	}	
	
	private static HistoryTravelDataWritable getWritableData4Yesterday(DefaultHCatRecord value, HCatSchema recordSchema) throws HCatException {
		String cn = value.getString("vin", recordSchema);
		String ct = value.getString("collect_time", recordSchema);
		String st = value.getString("system_time", recordSchema);
		if (StringUtils.isEmpty(cn) || StringUtils.isEmpty(st) || StringUtils.isEmpty(ct)) {
			logger.error("底盘号:{}，不满足条件。其采集时间为空或系统时间不能为空！", cn);
			return null;
		}
		HistoryTravelDataWritable vht = new HistoryTravelDataWritable();
		
		vht.setCn(new Text(cn)); // 底盘号
		vht.setCt(new Text(ct)); // 采集时间
		vht.setSt(new Text(st)); // 系统时间
		
		if (  value.getFloat("lng", recordSchema)!=null) vht.setL0002(new FloatWritable(value.getFloat("lng", recordSchema))); 
		if (  value.getFloat("lat", recordSchema)!=null) vht.setL0003(new FloatWritable(value.getFloat("lat", recordSchema)));	
		if (value.getInteger("travelling_status", recordSchema)!=null) vht.setL4013(new IntWritable(value.getInteger("travelling_status", recordSchema))); 
		if (value.getInteger("total_mileage", recordSchema)!=null) vht.setL0004(new IntWritable(value.getInteger("total_mileage", recordSchema))); 
		if (value.getInteger("travel_mileage", recordSchema)!=null) vht.setL0005(new IntWritable(value.getInteger("travel_mileage", recordSchema)));
		if (value.getInteger("speed", recordSchema)!=null) vht.setL0006(new IntWritable(value.getInteger("speed", recordSchema)));
		if (value.getInteger("engine_speed", recordSchema)!=null) vht.setL0009(new IntWritable(value.getInteger("engine_speed", recordSchema))); 
		if (value.getInteger("maint_mileage", recordSchema)!=null) vht.setL0008(new IntWritable(value.getInteger("maint_mileage", recordSchema))); 
		if (value.getInteger("maint_day", recordSchema)!=null) vht.setL0024(new IntWritable(value.getInteger("maint_day", recordSchema))); 
		if (value.getInteger("oil_level", recordSchema)!=null) vht.setL0025(new IntWritable(value.getInteger("oil_level", recordSchema)));
		if (  value.getFloat("motor_oil_level", recordSchema)!=null) vht.setL0014(new FloatWritable(value.getFloat("motor_oil_level", recordSchema)));
		if (  value.getFloat("motor_oil_level_low", recordSchema)!=null) vht.setL001f(new FloatWritable(value.getFloat("motor_oil_level_low", recordSchema)));
		if (value.getInteger("mileage", recordSchema)!=null) vht.setL0028(new IntWritable(value.getInteger("mileage", recordSchema))); 
		if (  value.getFloat("instant_oil_consume", recordSchema)!=null) vht.setL7d02(new FloatWritable(value.getFloat("instant_oil_consume", recordSchema)));
		
		if (  value.getFloat("0007", recordSchema)!=null) vht.setL0007(new FloatWritable(value.getFloat("0007", recordSchema)));
		if (  value.getFloat("0010", recordSchema)!=null) vht.setL0010(new FloatWritable(value.getFloat("0010", recordSchema)));
		if (value.getInteger("0011", recordSchema)!=null) vht.setL0011(new IntWritable(value.getInteger("0011", recordSchema))); 
		if (value.getInteger("0029", recordSchema)!=null) vht.setL0029(new IntWritable(value.getInteger("0029", recordSchema))); 
		if (value.getInteger("002a", recordSchema)!=null) vht.setL002a(new IntWritable(value.getInteger("002a", recordSchema))); 
		if (value.getInteger("002b", recordSchema)!=null) vht.setL002b(new IntWritable(value.getInteger("002b", recordSchema))); 
		if (value.getInteger("002c", recordSchema)!=null) vht.setL002c(new IntWritable(value.getInteger("002c", recordSchema))); 
		if (value.getInteger("0043", recordSchema)!=null) vht.setL0043(new IntWritable(value.getInteger("0043", recordSchema))); 
		if (value.getInteger("7d03", recordSchema)!=null) vht.setL7d03(new IntWritable(value.getInteger("7d03", recordSchema))); 
		if (value.getInteger("7d04", recordSchema)!=null) vht.setL7d04(new IntWritable(value.getInteger("7d04", recordSchema))); 
		if (value.getInteger("7d05", recordSchema)!=null) vht.setL7d05(new IntWritable(value.getInteger("7d05", recordSchema))); 
		if (value.getInteger("7d06", recordSchema)!=null) vht.setL7d06(new IntWritable(value.getInteger("7d06", recordSchema))); 
		if (value.getInteger("7d07", recordSchema)!=null) vht.setL7d07(new IntWritable(value.getInteger("7d07", recordSchema))); 
		if (value.getInteger("7d08", recordSchema)!=null) vht.setL7d08(new IntWritable(value.getInteger("7d08", recordSchema))); 
		if (  value.getFloat("7d09", recordSchema)!=null) vht.setL7d09(new FloatWritable(value.getFloat("7d09", recordSchema)));
		if (value.getInteger("7d0a", recordSchema)!=null) vht.setL7d0a(new IntWritable(value.getInteger("7d0a", recordSchema))); 
		if (value.getInteger("7d0b", recordSchema)!=null) vht.setL7d0b(new IntWritable(value.getInteger("7d0b", recordSchema))); 
		if (  value.getFloat("7d0c", recordSchema)!=null) vht.setL7d0c(new FloatWritable(value.getFloat("7d0c", recordSchema)));
		if (value.getInteger("7d0d", recordSchema)!=null) vht.setL7d0d(new IntWritable(value.getInteger("7d0d", recordSchema))); 
		if (value.getInteger("7f0c", recordSchema)!=null) vht.setL7f0c(new IntWritable(value.getInteger("7f0c", recordSchema))); 
		if (value.getInteger("8051", recordSchema)!=null) vht.setL8051(new IntWritable(value.getInteger("8051", recordSchema))); 
		if (value.getInteger("8052", recordSchema)!=null) vht.setL8052(new IntWritable(value.getInteger("8052", recordSchema))); 
		if (value.getInteger("8053", recordSchema)!=null) vht.setL8053(new IntWritable(value.getInteger("8053", recordSchema))); 
		if (value.getInteger("8054", recordSchema)!=null) vht.setL8054(new IntWritable(value.getInteger("8054", recordSchema))); 
		if (value.getInteger("8055", recordSchema)!=null) vht.setL8055(new IntWritable(value.getInteger("8055", recordSchema))); 
		if (value.getInteger("8056", recordSchema)!=null) vht.setL8056(new IntWritable(value.getInteger("8056", recordSchema))); 
		if (value.getInteger("8057", recordSchema)!=null) vht.setL8057(new IntWritable(value.getInteger("8057", recordSchema))); 
		if (value.getInteger("8058", recordSchema)!=null) vht.setL8058(new IntWritable(value.getInteger("8058", recordSchema))); 
		if (value.getInteger("8059", recordSchema)!=null) vht.setL8059(new IntWritable(value.getInteger("8059", recordSchema))); 
		if (value.getInteger("805a", recordSchema)!=null) vht.setL805a(new IntWritable(value.getInteger("805a", recordSchema))); 
		if (value.getInteger("805b", recordSchema)!=null) vht.setL805b(new IntWritable(value.getInteger("805b", recordSchema))); 
		if (value.getInteger("805c", recordSchema)!=null) vht.setL805c(new IntWritable(value.getInteger("805c", recordSchema))); 
		if (value.getInteger("805d", recordSchema)!=null) vht.setL805d(new IntWritable(value.getInteger("805d", recordSchema))); 
		if (value.getInteger("805e", recordSchema)!=null) vht.setL805e(new IntWritable(value.getInteger("805e", recordSchema))); 
		if (value.getInteger("805f", recordSchema)!=null) vht.setL805f(new IntWritable(value.getInteger("805f", recordSchema))); 
		if (value.getInteger("8060", recordSchema)!=null) vht.setL8060(new IntWritable(value.getInteger("8060", recordSchema))); 
		if (value.getInteger("8061", recordSchema)!=null) vht.setL8061(new IntWritable(value.getInteger("8061", recordSchema))); 
		if (value.getInteger("8062", recordSchema)!=null) vht.setL8062(new IntWritable(value.getInteger("8062", recordSchema))); 
		if (value.getInteger("8063", recordSchema)!=null) vht.setL8063(new IntWritable(value.getInteger("8063", recordSchema))); 
		if (value.getInteger("8064", recordSchema)!=null) vht.setL8064(new IntWritable(value.getInteger("8064", recordSchema))); 
		if (value.getInteger("8065", recordSchema)!=null) vht.setL8065(new IntWritable(value.getInteger("8065", recordSchema))); 
		if (value.getInteger("8066", recordSchema)!=null) vht.setL8066(new IntWritable(value.getInteger("8066", recordSchema))); 
		if (value.getInteger("8067", recordSchema)!=null) vht.setL8067(new IntWritable(value.getInteger("8067", recordSchema))); 
		if (value.getInteger("8068", recordSchema)!=null) vht.setL8068(new IntWritable(value.getInteger("8068", recordSchema))); 
		if (value.getInteger("8069", recordSchema)!=null) vht.setL8069(new IntWritable(value.getInteger("8069", recordSchema))); 
		if (value.getInteger("806a", recordSchema)!=null) vht.setL806a(new IntWritable(value.getInteger("806a", recordSchema))); 
		if (value.getInteger("806b", recordSchema)!=null) vht.setL806b(new IntWritable(value.getInteger("806b", recordSchema))); 
		if (value.getInteger("806c", recordSchema)!=null) vht.setL806c(new IntWritable(value.getInteger("806c", recordSchema))); 
		if (value.getInteger("806d", recordSchema)!=null) vht.setL806d(new IntWritable(value.getInteger("806d", recordSchema))); 
		if (value.getInteger("806e", recordSchema)!=null) vht.setL806e(new IntWritable(value.getInteger("806e", recordSchema))); 
		if (value.getInteger("806f", recordSchema)!=null) vht.setL806f(new IntWritable(value.getInteger("806f", recordSchema))); 
		if (value.getInteger("8070", recordSchema)!=null) vht.setL8070(new IntWritable(value.getInteger("8070", recordSchema))); 
		if (value.getInteger("8071", recordSchema)!=null) vht.setL8071(new IntWritable(value.getInteger("8071", recordSchema))); 
		if (value.getInteger("8072", recordSchema)!=null) vht.setL8072(new IntWritable(value.getInteger("8072", recordSchema))); 
		if (value.getInteger("8073", recordSchema)!=null) vht.setL8073(new IntWritable(value.getInteger("8073", recordSchema))); 
		if (value.getInteger("8074", recordSchema)!=null) vht.setL8074(new IntWritable(value.getInteger("8074", recordSchema))); 
		if (value.getInteger("8075", recordSchema)!=null) vht.setL8075(new IntWritable(value.getInteger("8075", recordSchema))); 
		if (value.getInteger("8076", recordSchema)!=null) vht.setL8076(new IntWritable(value.getInteger("8076", recordSchema))); 
		if (value.getInteger("8077", recordSchema)!=null) vht.setL8077(new IntWritable(value.getInteger("8077", recordSchema))); 
		if (value.getInteger("8078", recordSchema)!=null) vht.setL8078(new IntWritable(value.getInteger("8078", recordSchema))); 
		if (value.getString("sid",  recordSchema)!=null) vht.setLsid(new Text( value.getString("sid", recordSchema))); 	
		return vht;
	}
	
	private static HistoryTravelDataWritable getWritableData(DefaultHCatRecord value, HCatSchema recordSchema) throws HCatException {
		String cn = value.getString("cn", recordSchema);
		String ct = value.getString("ct", recordSchema);
		String st = value.getString("st", recordSchema);
		if (StringUtils.isEmpty(cn) || StringUtils.isEmpty(st) || StringUtils.isEmpty(ct)) {
			logger.error("底盘号:{}，不满足条件。其采集时间为空或系统时间不能为空！", cn);
			return null;
		}
		HistoryTravelDataWritable vht = new HistoryTravelDataWritable();
		
		vht.setCn(new Text(cn)); // 底盘号
		vht.setCt(new Text(ct)); // 采集时间
		vht.setSt(new Text(st)); // 系统时间
		
		if (value.getFloat("0002", recordSchema)!=null)   vht.setL0002(new FloatWritable(value.getFloat("0002", recordSchema))); 
		if (value.getFloat("0003", recordSchema)!=null)   vht.setL0003(new FloatWritable(value.getFloat("0003", recordSchema)));	
		if (value.getInteger("4013", recordSchema)!=null) vht.setL4013(new IntWritable(value.getInteger("4013", recordSchema))); 
		if (value.getInteger("0004", recordSchema)!=null) vht.setL0004(new IntWritable(value.getInteger("0004", recordSchema))); 
		if (value.getInteger("0005", recordSchema)!=null) vht.setL0005(new IntWritable(value.getInteger("0005", recordSchema)));
		if (value.getInteger("0006", recordSchema)!=null) vht.setL0006(new IntWritable(value.getInteger("0006", recordSchema)));
		if (value.getInteger("0009", recordSchema)!=null) vht.setL0009(new IntWritable(value.getInteger("0009", recordSchema))); 
		if (value.getInteger("0024", recordSchema)!=null) vht.setL0024(new IntWritable(value.getInteger("0024", recordSchema))); 
		if (value.getInteger("0025", recordSchema)!=null) vht.setL0025(new IntWritable(value.getInteger("0025", recordSchema))); 
		if (value.getInteger("0008", recordSchema)!=null) vht.setL0008(new IntWritable(value.getInteger("0008", recordSchema)));
		if (value.getFloat("0014", recordSchema)!=null)   vht.setL0014(new FloatWritable(value.getFloat("0014", recordSchema)));
		if (value.getFloat("001f", recordSchema)!=null)   vht.setL001f(new FloatWritable(value.getFloat("001f", recordSchema)));
		if (value.getInteger("0028", recordSchema)!=null) vht.setL0028(new IntWritable(value.getInteger("0028", recordSchema))); 
		if (value.getFloat("7d02", recordSchema)!=null)   vht.setL7d02(new FloatWritable(value.getFloat("7d02", recordSchema)));
		if (  value.getFloat("0007", recordSchema)!=null) vht.setL0007(new FloatWritable(value.getFloat("0007", recordSchema)));
		if (  value.getFloat("0010", recordSchema)!=null) vht.setL0010(new FloatWritable(value.getFloat("0010", recordSchema)));
		if (value.getInteger("0011", recordSchema)!=null) vht.setL0011(new IntWritable(value.getInteger("0011", recordSchema))); 
		if (value.getInteger("0029", recordSchema)!=null) vht.setL0029(new IntWritable(value.getInteger("0029", recordSchema))); 
		if (value.getInteger("002a", recordSchema)!=null) vht.setL002a(new IntWritable(value.getInteger("002a", recordSchema))); 
		if (value.getInteger("002b", recordSchema)!=null) vht.setL002b(new IntWritable(value.getInteger("002b", recordSchema))); 
		if (value.getInteger("002c", recordSchema)!=null) vht.setL002c(new IntWritable(value.getInteger("002c", recordSchema))); 
		if (value.getInteger("0043", recordSchema)!=null) vht.setL0043(new IntWritable(value.getInteger("0043", recordSchema))); 
		if (value.getInteger("7d03", recordSchema)!=null) vht.setL7d03(new IntWritable(value.getInteger("7d03", recordSchema))); 
		if (value.getInteger("7d04", recordSchema)!=null) vht.setL7d04(new IntWritable(value.getInteger("7d04", recordSchema))); 
		if (value.getInteger("7d05", recordSchema)!=null) vht.setL7d05(new IntWritable(value.getInteger("7d05", recordSchema))); 
		if (value.getInteger("7d06", recordSchema)!=null) vht.setL7d06(new IntWritable(value.getInteger("7d06", recordSchema))); 
		if (value.getInteger("7d07", recordSchema)!=null) vht.setL7d07(new IntWritable(value.getInteger("7d07", recordSchema))); 
		if (value.getInteger("7d08", recordSchema)!=null) vht.setL7d08(new IntWritable(value.getInteger("7d08", recordSchema))); 
		if (  value.getFloat("7d09", recordSchema)!=null) vht.setL7d09(new FloatWritable(value.getFloat("7d09", recordSchema)));
		if (value.getInteger("7d0a", recordSchema)!=null) vht.setL7d0a(new IntWritable(value.getInteger("7d0a", recordSchema))); 
		if (value.getInteger("7d0b", recordSchema)!=null) vht.setL7d0b(new IntWritable(value.getInteger("7d0b", recordSchema))); 
		if (  value.getFloat("7d0c", recordSchema)!=null) vht.setL7d0c(new FloatWritable(value.getFloat("7d0c", recordSchema)));
		if (value.getInteger("7d0d", recordSchema)!=null) vht.setL7d0d(new IntWritable(value.getInteger("7d0d", recordSchema))); 
		if (value.getInteger("7f0c", recordSchema)!=null) vht.setL7f0c(new IntWritable(value.getInteger("7f0c", recordSchema))); 
		if (value.getInteger("8051", recordSchema)!=null) vht.setL8051(new IntWritable(value.getInteger("8051", recordSchema))); 
		if (value.getInteger("8052", recordSchema)!=null) vht.setL8052(new IntWritable(value.getInteger("8052", recordSchema))); 
		if (value.getInteger("8053", recordSchema)!=null) vht.setL8053(new IntWritable(value.getInteger("8053", recordSchema))); 
		if (value.getInteger("8054", recordSchema)!=null) vht.setL8054(new IntWritable(value.getInteger("8054", recordSchema))); 
		if (value.getInteger("8055", recordSchema)!=null) vht.setL8055(new IntWritable(value.getInteger("8055", recordSchema))); 
		if (value.getInteger("8056", recordSchema)!=null) vht.setL8056(new IntWritable(value.getInteger("8056", recordSchema))); 
		if (value.getInteger("8057", recordSchema)!=null) vht.setL8057(new IntWritable(value.getInteger("8057", recordSchema))); 
		if (value.getInteger("8058", recordSchema)!=null) vht.setL8058(new IntWritable(value.getInteger("8058", recordSchema))); 
		if (value.getInteger("8059", recordSchema)!=null) vht.setL8059(new IntWritable(value.getInteger("8059", recordSchema))); 
		if (value.getInteger("805a", recordSchema)!=null) vht.setL805a(new IntWritable(value.getInteger("805a", recordSchema))); 
		if (value.getInteger("805b", recordSchema)!=null) vht.setL805b(new IntWritable(value.getInteger("805b", recordSchema))); 
		if (value.getInteger("805c", recordSchema)!=null) vht.setL805c(new IntWritable(value.getInteger("805c", recordSchema))); 
		if (value.getInteger("805d", recordSchema)!=null) vht.setL805d(new IntWritable(value.getInteger("805d", recordSchema))); 
		if (value.getInteger("805e", recordSchema)!=null) vht.setL805e(new IntWritable(value.getInteger("805e", recordSchema))); 
		if (value.getInteger("805f", recordSchema)!=null) vht.setL805f(new IntWritable(value.getInteger("805f", recordSchema))); 
		if (value.getInteger("8060", recordSchema)!=null) vht.setL8060(new IntWritable(value.getInteger("8060", recordSchema))); 
		if (value.getInteger("8061", recordSchema)!=null) vht.setL8061(new IntWritable(value.getInteger("8061", recordSchema))); 
		if (value.getInteger("8062", recordSchema)!=null) vht.setL8062(new IntWritable(value.getInteger("8062", recordSchema))); 
		if (value.getInteger("8063", recordSchema)!=null) vht.setL8063(new IntWritable(value.getInteger("8063", recordSchema))); 
		if (value.getInteger("8064", recordSchema)!=null) vht.setL8064(new IntWritable(value.getInteger("8064", recordSchema))); 
		if (value.getInteger("8065", recordSchema)!=null) vht.setL8065(new IntWritable(value.getInteger("8065", recordSchema))); 
		if (value.getInteger("8066", recordSchema)!=null) vht.setL8066(new IntWritable(value.getInteger("8066", recordSchema))); 
		if (value.getInteger("8067", recordSchema)!=null) vht.setL8067(new IntWritable(value.getInteger("8067", recordSchema))); 
		if (value.getInteger("8068", recordSchema)!=null) vht.setL8068(new IntWritable(value.getInteger("8068", recordSchema))); 
		if (value.getInteger("8069", recordSchema)!=null) vht.setL8069(new IntWritable(value.getInteger("8069", recordSchema))); 
		if (value.getInteger("806a", recordSchema)!=null) vht.setL806a(new IntWritable(value.getInteger("806a", recordSchema))); 
		if (value.getInteger("806b", recordSchema)!=null) vht.setL806b(new IntWritable(value.getInteger("806b", recordSchema))); 
		if (value.getInteger("806c", recordSchema)!=null) vht.setL806c(new IntWritable(value.getInteger("806c", recordSchema))); 
		if (value.getInteger("806d", recordSchema)!=null) vht.setL806d(new IntWritable(value.getInteger("806d", recordSchema))); 
		if (value.getInteger("806e", recordSchema)!=null) vht.setL806e(new IntWritable(value.getInteger("806e", recordSchema))); 
		if (value.getInteger("806f", recordSchema)!=null) vht.setL806f(new IntWritable(value.getInteger("806f", recordSchema))); 
		if (value.getInteger("8070", recordSchema)!=null) vht.setL8070(new IntWritable(value.getInteger("8070", recordSchema))); 
		if (value.getInteger("8071", recordSchema)!=null) vht.setL8071(new IntWritable(value.getInteger("8071", recordSchema))); 
		if (value.getInteger("8072", recordSchema)!=null) vht.setL8072(new IntWritable(value.getInteger("8072", recordSchema))); 
		if (value.getInteger("8073", recordSchema)!=null) vht.setL8073(new IntWritable(value.getInteger("8073", recordSchema))); 
		if (value.getInteger("8074", recordSchema)!=null) vht.setL8074(new IntWritable(value.getInteger("8074", recordSchema))); 
		if (value.getInteger("8075", recordSchema)!=null) vht.setL8075(new IntWritable(value.getInteger("8075", recordSchema))); 
		if (value.getInteger("8076", recordSchema)!=null) vht.setL8076(new IntWritable(value.getInteger("8076", recordSchema))); 
		if (value.getInteger("8077", recordSchema)!=null) vht.setL8077(new IntWritable(value.getInteger("8077", recordSchema))); 
		if (value.getInteger("8078", recordSchema)!=null) vht.setL8078(new IntWritable(value.getInteger("8078", recordSchema))); 
		if (value.getString("sid",  recordSchema)!=null) vht.setLsid(new Text( value.getString("sid", recordSchema))); 	
		return vht;
	}

	public static class DataWashingReducer extends Reducer<CombinationKey, HistoryTravelDataWritable, CombinationKey, HCatRecord> {
		private static HCatSchema recordSchemaR;
		private static HCatSchema recordSchemaR4Yesterday;

		protected void setup(Context context) throws IOException, InterruptedException {
			String outputTable = context.getConfiguration().get("outputTable");
			String outputTable4Yesterday = context.getConfiguration().get("outputTable4Yesterday");
			
			JobContext jobContext = MultiOutputFormat.getJobContext(outputTable, context);
			JobContext jobContext4Yesterday = MultiOutputFormat.getJobContext(outputTable4Yesterday, context);
			
			recordSchemaR = HCatOutputFormat.getTableSchema(jobContext.getConfiguration());
			recordSchemaR4Yesterday = HCatOutputFormat.getTableSchema(jobContext4Yesterday.getConfiguration());
		}

		protected void reduce(CombinationKey key, Iterable<HistoryTravelDataWritable> values, Context context)
				throws IOException, InterruptedException {
			long reduceBegin = System.currentTimeMillis();
			
			List<HistoryTravelDataWritable> values0 = new ArrayList<HistoryTravelDataWritable>(); 
			String partition = context.getConfiguration().get("partition"); //获得分区列表
			String outputTable = context.getConfiguration().get("outputTable");
			String outputTable4Yesterday = context.getConfiguration().get("outputTable4Yesterday");

			int count = 0;
			logger.debug("计数器为：{}",count);
			logger.info("REDUCE 计时器1：{}(单位：毫秒)",System.currentTimeMillis()-reduceBegin);
			
			// 若行车状态、总里程、速度、发动机转速、保养里程和保养天数全为空则过滤本条
			for (HistoryTravelDataWritable washingData : values) {
				count++;
				logger.debug("底盘号：{}，采集时间：{}：{}", washingData.getCn(), washingData.getCt(),count);
				
				if (!(washingData.getL4013() == null 
						&& washingData.getL0004() == null && washingData.getL0006() == null
						&& washingData.getL0009() == null && washingData.getL0024() == null
						&& washingData.getL0025() == null)) {
					HistoryTravelDataWritable washingDataTemp = new HistoryTravelDataWritable(); 
					try {
						BeanUtils.copyProperties(washingDataTemp, washingData);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					};
					int recordSize = values0.size();
					//过滤掉采集时间相同的数据，只保留一条
					if (recordSize>0) {
						if (!(values0.get(recordSize-1).getCn().compareTo(washingDataTemp.getCn())==0 && 
								values0.get(recordSize-1).getCt().compareTo((washingDataTemp.getCt()))==0)) {
							values0.add(washingDataTemp);
						}
					}else {
						values0.add(washingDataTemp);
					}
				}
			}
			
			logger.info("REDUCE 计时器2：{}(单位：毫秒)",System.currentTimeMillis()-reduceBegin);
			
			//清洗总里程，本次里程为空的数据；
			//规则：如果第一条就为空，那么向下查询到第一条非空的数据赋给第一条；
			int recordNumb = values0.size();
			if (recordNumb > 0) {
				HistoryTravelDataWritable firstRecord = values0.get(0);
				IntWritable firstNotNullTotalMiles = firstRecord.getL0004(); //总里程
				
				if (firstNotNullTotalMiles == null) {
					for (int i = 0; i < recordNumb; i++) {
						if ( values0.get(i).getL0004() != null) {
							firstNotNullTotalMiles = values0.get(i).getL0004();
							break;
						}
					}
					firstRecord.setL0004(firstNotNullTotalMiles);
					values0.set(0, firstRecord);
				}
			}
			
			logger.info("REDUCE 计时器3：{}(单位：毫秒)",System.currentTimeMillis()-reduceBegin);
			
			// 逐步清洗数据
			int totalSize = values0.size(); 
			int doneSize = 0;
			HistoryTravelDataWritable washingDataLast = null; //用于记录最后一条记录
			
			for(HistoryTravelDataWritable washingData0 :values0){
				fillUpTotalMiles(washingData0, doneSize>0?values0.get(doneSize-1):null);
				travelShuffle(washingData0, doneSize+1<totalSize?values0.get(doneSize+1):null, totalSize, doneSize);
				totalMilesShuffle(washingData0, doneSize>0?values0.get(doneSize-1):null,  doneSize+1<totalSize?values0.get(doneSize+1):null, totalSize, doneSize);
				milesShuffle(washingData0, doneSize>0?values0.get(doneSize-1):null, doneSize);
				maintainsMilesShuffle(washingData0, doneSize>0?values0.get(doneSize-1):null, doneSize);
				maintainsDaysShuffle(washingData0, doneSize>0?values0.get(doneSize-1):null, doneSize);
				speedShuffle(washingData0);
				engineSpeedShuffle(washingData0);
				
				HistoryTravelDataWritable washingDataTemp = new HistoryTravelDataWritable(); 
				try {
					BeanUtils.copyProperties(washingDataTemp, washingData0);
				} catch (Exception e) {
					throw new IOException(e.getMessage());
				};
				
				String collectTime = new String(washingDataTemp.getCt().getBytes());
				String colletcDate = String.format("%s%s%s", collectTime.substring(0, 4),collectTime.substring(5, 7),collectTime.substring(8, 10));
				
				if (partition.equals(colletcDate)) {
					doneSize++;
					HCatRecord record = getHCatRecord(recordSchemaR, washingDataTemp);
					MultiOutputFormat.write(outputTable, null, record, context);
					washingDataLast = washingDataTemp;
				}
			}
			
			if (washingDataLast!=null) {
				HCatRecord record4Yesterday = getHCatRecord(recordSchemaR4Yesterday, washingDataLast);
				MultiOutputFormat.write(outputTable4Yesterday, null, record4Yesterday, context);
			}
			
			logger.info("REDUCE 计时器4：{}(单位：毫秒)",System.currentTimeMillis()-reduceBegin);
		}
	}
	
	/**
	 * 清洗总里程，本次里程为空的数据；</br>
	 * 规则：将上一条不为空的数据赋值给当前空数据；
	 * 
	 * @param currentElement
	 * @param preElement
	 */
	static void fillUpTotalMiles(HistoryTravelDataWritable currentElement, HistoryTravelDataWritable preElement){
		if (preElement != null) {
			if (currentElement.getL0004() == null) {
				currentElement.setL0004(preElement.getL0004());
			}
		}
	}	
	
	/**
	 * 行车状态清洗：
	 * <ul>
	 * <li>若4013为空且（速度>0或者发动机转速>0)，则将4013更改为1</li>
	 * <li>若4013为空且速度=0且发动机转速=0则将4013更改为0</li>
	 * <li>与下一条的数据采集时间相差>=300秒,则将本条的4013改为0</li>
	 * </ul>
	 * @param currentElement	当前记录
	 * @param nextElement	下一条记录
	 * @param totalSize		总共记录条数
	 * @param doneSize		已经清洗的数据条数
	 */
	static void travelShuffle(HistoryTravelDataWritable currentElement, HistoryTravelDataWritable nextElement, int totalSize, int doneSize){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (currentElement.getL0006()!=null){
			if (currentElement.getL4013()==null && currentElement.getL0006().get()>0) {
				currentElement.setL4013(new IntWritable(1));
			}
		}
		if (currentElement.getL0009()!=null){
			if (currentElement.getL4013()==null && currentElement.getL0009().get()>0) {
				currentElement.setL4013(new IntWritable(1));
			}
		}
		if (currentElement.getL0006()!=null && currentElement.getL0009()!=null) {
			if (currentElement.getL4013()==null && (currentElement.getL0006().get()>0 && currentElement.getL0009().get()>0)) {
				currentElement.setL4013(new IntWritable(1));
			}
			if (currentElement.getL4013()==null && currentElement.getL0006().get()==0 && currentElement.getL0009().get()==0) {
				currentElement.setL4013(new IntWritable(0));
			}
		}

		if (nextElement!=null) {
			if (nextElement.getCn().compareTo(currentElement.getCn())==0) {
				String ctNext = new String(nextElement.getCt().getBytes());
				String ctCurrent = new String(currentElement.getCt().getBytes());
				try {
					long lNext = sdf.parse(ctNext).getTime();
					long lCurrent = sdf.parse(ctCurrent).getTime();
					if (lNext-lCurrent>=300*1000) {
						currentElement.setL4013(new IntWritable(0));
					}
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * 总里程清洗：
	 * <ul>
	 * <li>若（本条总里程>上一条总里程)且（本条总里程>下一条总里程）则将本条总里程更改为上一条总里程；</li>
	 * <li>若（本条总里程<上一条总里程)且（本条总里程<下一条总里程）也将本条总里程更改为上一条总里程。</li>
	 * </ul>
	 * @param currentElement	当前记录
	 * @param nextElement	下一条记录
	 * @param totalSize		总共记录条数
	 * @param doneSize		已经清洗的数据条数
	 */
	static void totalMilesShuffle(HistoryTravelDataWritable currentElement, HistoryTravelDataWritable beforeElement, HistoryTravelDataWritable nextElement, int totalSize, int doneSize){
		if (beforeElement != null && nextElement != null) {
			if (beforeElement.getCn().compareTo(currentElement.getCn())==0 && nextElement.getCn().compareTo(currentElement.getCn())==0) {
				if (currentElement.getL0004() != null && beforeElement.getL0004() != null && nextElement.getL0004() != null) {
					int currentTMile = currentElement.getL0004().get();
					int beforeTMile = beforeElement.getL0004().get();
					int nextTMile = nextElement.getL0004().get();
					if (currentTMile>beforeTMile && currentTMile>nextTMile) {
						currentElement.setL0005(beforeElement.getL0004());
					}
					if (currentTMile<beforeTMile && currentTMile<nextTMile) {
						currentElement.setL0005(beforeElement.getL0004());
					}							
				}
			}
		}
	}	
	
	/**
	 * 本次里程:
	 * <ul>
	 * <li>若4013=1且上一条4013=1且本次里程<上一条本次里程则将本次里程更改为上一条本次里程</li>
	 * </ul>
	 * @param currentElement	当前记录
	 * @param nextElement	上一条记录
	 * @param doneSize		已经清洗的数据条数
	 */
	static void milesShuffle(HistoryTravelDataWritable currentElement, HistoryTravelDataWritable beforeElement, int doneSize){
		if (beforeElement != null) {
			if (beforeElement.getCn().compareTo(currentElement.getCn())==0) {
				if (currentElement.getL4013()!=null && beforeElement.getL0005() != null && currentElement.getL0005() != null){
					if (currentElement.getL4013().get()==1 && beforeElement.getL0005().compareTo(currentElement.getL0005())>0) {
						currentElement.setL0005(beforeElement.getL0005());
					}
				}
			}
		}
	}	
	
	/**
	 * 保养里程:
	 * <ul>
	 * <li>若保养里程=0且上一条保养里程>600KM且上一条保养天数大于30天则保养里程更改为上一条保养里程</li>
	 * <li>若200KM<保养里程-上一条保养里程<1800KM且上一条保养里程大于1000KM且上一条保养天数大于30天则将保养里程更改为上一条保养里程</li>
	 * </ul>
	 * @param currentElement	当前记录
	 * @param nextElement	上一条记录
	 * @param doneSize		已经清洗的数据条数
	 */
	static void maintainsMilesShuffle(HistoryTravelDataWritable currentElement, HistoryTravelDataWritable beforeElement, int doneSize){
		if (beforeElement != null) {
			if (beforeElement.getCn().compareTo(currentElement.getCn())==0) {
				if (currentElement.getL0024()!=null && currentElement.getL0025()!=null && beforeElement.getL0024()!=null && beforeElement.getL0025()!=null){
					if (currentElement.getL0024().get()==0 && beforeElement.getL0024().get()>600 && beforeElement.getL0025().get()>30 ) {
						currentElement.setL0024(beforeElement.getL0024());
					}
					if ((currentElement.getL0024().get() - beforeElement.getL0024().get())>200 && (currentElement.getL0024().get() - beforeElement.getL0024().get())<1800 
							&& beforeElement.getL0024().get()> 1000 && beforeElement.getL0025().get()>30 ) {
						currentElement.setL0024(beforeElement.getL0024());
					}
				}
			}
		}
	}
	
	/**
	 * 保养天数:
	 * <ul>
	 * <li>若保养天数=0且上一条保养天数>30天且上一条保养里程>1000KM则将保养天数更改为上一条保养天数</li>
	 * </ul>
	 * @param currentElement	当前记录
	 * @param nextElement	上一条记录
	 * @param doneSize		已经清洗的数据条数
	 */
	static void maintainsDaysShuffle(HistoryTravelDataWritable currentElement, HistoryTravelDataWritable beforeElement, int doneSize){
		if (beforeElement != null) {
			if (beforeElement.getCn().compareTo(currentElement.getCn())==0) {
				if (currentElement.getL0024()!=null && currentElement.getL0025()!=null && beforeElement.getL0024()!=null && beforeElement.getL0025()!=null){
					if (currentElement.getL0025().get()==0 && beforeElement.getL0025().get()>30 && beforeElement.getL0024().get()>1000 ) {
						currentElement.setL0025(beforeElement.getL0025());
					}
				}
			}
		}
	}	
	
	/**
	 * 速度:
	 * <ul>
	 * <li>若速度大于300KM/h则将速度改为空</li>
	 * </ul>
	 * @param currentElement	当前记录
	 */
	static void speedShuffle(HistoryTravelDataWritable currentElement){
		if (currentElement.getL0006()!=null){
			if (currentElement.getL0006().get()>300){
				currentElement.setL0006(null);
			}
		}
	}		
	
	/**
	 * 发动机转速：
	 * <ul>
	 * <li>若发动机转速大于7000转每分钟则将发动机转速更改为空</li>
	 * <li>若4013=1且速度大于0且发动机转速=0则将发动机转速更改为空</li>	 
	 * </ul>
	 * @param currentElement	当前记录
	 */
	static void engineSpeedShuffle(HistoryTravelDataWritable currentElement){
		if (currentElement.getL0009()!=null){
			if (currentElement.getL0009().get()>7000){
				currentElement.setL0009(null);
			}
		}
		if (currentElement.getL4013()!=null && currentElement.getL0006()!=null && currentElement.getL0009()!=null){
			if (currentElement.getL4013().get()==1 && currentElement.getL0006().get()>0 && currentElement.getL0009().get()==0){
				currentElement.setL0009(null);
			}
		}
	}	
	
	/**
	 * 写记录
	 * @param recordSchemaR
	 * @param washingResult
	 * @return
	 * @throws HCatException 
	 */
	static HCatRecord getHCatRecord(HCatSchema recordSchemaR, HistoryTravelDataWritable washingResult) throws HCatException{
		HCatRecord record = new DefaultHCatRecord(80);
		record.set("vin", recordSchemaR, washingResult.getCn());
		record.set("collect_time", recordSchemaR, washingResult.getCt());
		record.set("system_time", recordSchemaR, washingResult.getSt());
		if ( washingResult.getL0002()!=null) {
			record.set("lng", recordSchemaR, washingResult.getL0002().get());
		}else {
			record.set("lng", recordSchemaR, null);
		}
		if ( washingResult.getL0003()!=null) {
			record.set("lat", recordSchemaR, washingResult.getL0003().get());
		}else {
			record.set("lat", recordSchemaR, null);
		}
		if ( washingResult.getL4013()!=null) {
			record.set("travelling_status", recordSchemaR, washingResult.getL4013().get());
		}else {
			record.set("travelling_status", recordSchemaR, null);
		}
		if ( washingResult.getL0004()!=null) {	
			record.set("total_mileage", recordSchemaR, washingResult.getL0004().get());
		}else {
			record.set("total_mileage", recordSchemaR, null);
		}			
		if (washingResult.getL0005() != null) {
			record.set("travel_mileage", recordSchemaR, washingResult.getL0005().get());
		} else {
			record.set("travel_mileage", recordSchemaR, null);
		}
		if (washingResult.getL0006() != null) {
			record.set("speed", recordSchemaR, washingResult.getL0006().get());
		} else {
			record.set("speed", recordSchemaR, null);
		}
		if (washingResult.getL0009() != null) {
			record.set("engine_speed", recordSchemaR, washingResult.getL0009().get());
		} else {
			record.set("engine_speed", recordSchemaR, null);
		}
		if (washingResult.getL0024() != null) {
			record.set("maint_mileage", recordSchemaR, washingResult.getL0024().get());
		} else {
			record.set("maint_mileage", recordSchemaR, null);
		}
		if (washingResult.getL0025() != null) {
			record.set("maint_day", recordSchemaR, washingResult.getL0025().get());
		} else {
			record.set("maint_day", recordSchemaR, null);
		}
		if (washingResult.getL0008() != null) {
			record.set("oil_level", recordSchemaR, washingResult.getL0008().get());
		} else {
			record.set("oil_level", recordSchemaR, null);
		}
		if (washingResult.getL0014() != null) {
			record.set("motor_oil_level", recordSchemaR, washingResult.getL0014().get());
		} else {
			record.set("motor_oil_level", recordSchemaR, null);
		}
		if (washingResult.getL001f() != null) {
			record.set("motor_oil_level_low", recordSchemaR, washingResult.getL001f().get());
		} else {
			record.set("motor_oil_level_low", recordSchemaR, null);
		}
		if (washingResult.getL0028() != null) {
			record.set("mileage", recordSchemaR, washingResult.getL0028().get());
		} else {
			record.set("mileage", recordSchemaR, null);
		}
		if (washingResult.getL7d02() != null) {
			record.set("instant_oil_consume", recordSchemaR, washingResult.getL7d02().get());
		} else {
			record.set("instant_oil_consume", recordSchemaR, null);
		}
		if ( washingResult.getL0007()!=null) { record.set("0007", recordSchemaR, washingResult.getL0007().get()); }else { record.set("0007", recordSchemaR, null); }
		if ( washingResult.getL0010()!=null) { record.set("0010", recordSchemaR, washingResult.getL0010().get()); }else { record.set("0010", recordSchemaR, null); }
		if ( washingResult.getL0011()!=null) { record.set("0011", recordSchemaR, washingResult.getL0011().get()); }else { record.set("0011", recordSchemaR, null); }
		if ( washingResult.getL0029()!=null) { record.set("0029", recordSchemaR, washingResult.getL0029().get()); }else { record.set("0029", recordSchemaR, null); }
		if ( washingResult.getL002a()!=null) { record.set("002a", recordSchemaR, washingResult.getL002a().get()); }else { record.set("002a", recordSchemaR, null); }
		if ( washingResult.getL002b()!=null) { record.set("002b", recordSchemaR, washingResult.getL002b().get()); }else { record.set("002b", recordSchemaR, null); }
		if ( washingResult.getL002c()!=null) { record.set("002c", recordSchemaR, washingResult.getL002c().get()); }else { record.set("002c", recordSchemaR, null); }
		if ( washingResult.getL0043()!=null) { record.set("0043", recordSchemaR, washingResult.getL0043().get()); }else { record.set("0043", recordSchemaR, null); }
		if ( washingResult.getL7d03()!=null) { record.set("7d03", recordSchemaR, washingResult.getL7d03().get()); }else { record.set("7d03", recordSchemaR, null); }
		if ( washingResult.getL7d04()!=null) { record.set("7d04", recordSchemaR, washingResult.getL7d04().get()); }else { record.set("7d04", recordSchemaR, null); }
		if ( washingResult.getL7d05()!=null) { record.set("7d05", recordSchemaR, washingResult.getL7d05().get()); }else { record.set("7d05", recordSchemaR, null); }
		if ( washingResult.getL7d06()!=null) { record.set("7d06", recordSchemaR, washingResult.getL7d06().get()); }else { record.set("7d06", recordSchemaR, null); }
		if ( washingResult.getL7d07()!=null) { record.set("7d07", recordSchemaR, washingResult.getL7d07().get()); }else { record.set("7d07", recordSchemaR, null); }
		if ( washingResult.getL7d08()!=null) { record.set("7d08", recordSchemaR, washingResult.getL7d08().get()); }else { record.set("7d08", recordSchemaR, null); }
		if ( washingResult.getL7d09()!=null) { record.set("7d09", recordSchemaR, washingResult.getL7d09().get()); }else { record.set("7d09", recordSchemaR, null); }
		if ( washingResult.getL7d0a()!=null) { record.set("7d0a", recordSchemaR, washingResult.getL7d0a().get()); }else { record.set("7d0a", recordSchemaR, null); }
		if ( washingResult.getL7d0b()!=null) { record.set("7d0b", recordSchemaR, washingResult.getL7d0b().get()); }else { record.set("7d0b", recordSchemaR, null); }
		if ( washingResult.getL7d0c()!=null) { record.set("7d0c", recordSchemaR, washingResult.getL7d0c().get()); }else { record.set("7d0c", recordSchemaR, null); }
		if ( washingResult.getL7d0d()!=null) { record.set("7d0d", recordSchemaR, washingResult.getL7d0d().get()); }else { record.set("7d0d", recordSchemaR, null); }
		if ( washingResult.getL7f0c()!=null) { record.set("7f0c", recordSchemaR, washingResult.getL7f0c().get()); }else { record.set("7f0c", recordSchemaR, null); }
		if ( washingResult.getL8051()!=null) { record.set("8051", recordSchemaR, washingResult.getL8051().get()); }else { record.set("8051", recordSchemaR, null); }
		if ( washingResult.getL8052()!=null) { record.set("8052", recordSchemaR, washingResult.getL8052().get()); }else { record.set("8052", recordSchemaR, null); }
		if ( washingResult.getL8053()!=null) { record.set("8053", recordSchemaR, washingResult.getL8053().get()); }else { record.set("8053", recordSchemaR, null); }
		if ( washingResult.getL8054()!=null) { record.set("8054", recordSchemaR, washingResult.getL8054().get()); }else { record.set("8054", recordSchemaR, null); }
		if ( washingResult.getL8055()!=null) { record.set("8055", recordSchemaR, washingResult.getL8055().get()); }else { record.set("8055", recordSchemaR, null); }
		if ( washingResult.getL8056()!=null) { record.set("8056", recordSchemaR, washingResult.getL8056().get()); }else { record.set("8056", recordSchemaR, null); }
		if ( washingResult.getL8057()!=null) { record.set("8057", recordSchemaR, washingResult.getL8057().get()); }else { record.set("8057", recordSchemaR, null); }
		if ( washingResult.getL8058()!=null) { record.set("8058", recordSchemaR, washingResult.getL8058().get()); }else { record.set("8058", recordSchemaR, null); }
		if ( washingResult.getL8059()!=null) { record.set("8059", recordSchemaR, washingResult.getL8059().get()); }else { record.set("8059", recordSchemaR, null); }
		if ( washingResult.getL805a()!=null) { record.set("805a", recordSchemaR, washingResult.getL805a().get()); }else { record.set("805a", recordSchemaR, null); }
		if ( washingResult.getL805b()!=null) { record.set("805b", recordSchemaR, washingResult.getL805b().get()); }else { record.set("805b", recordSchemaR, null); }
		if ( washingResult.getL805c()!=null) { record.set("805c", recordSchemaR, washingResult.getL805c().get()); }else { record.set("805c", recordSchemaR, null); }
		if ( washingResult.getL805d()!=null) { record.set("805d", recordSchemaR, washingResult.getL805d().get()); }else { record.set("805d", recordSchemaR, null); }
		if ( washingResult.getL805e()!=null) { record.set("805e", recordSchemaR, washingResult.getL805e().get()); }else { record.set("805e", recordSchemaR, null); }
		if ( washingResult.getL805f()!=null) { record.set("805f", recordSchemaR, washingResult.getL805f().get()); }else { record.set("805f", recordSchemaR, null); }
		if ( washingResult.getL8060()!=null) { record.set("8060", recordSchemaR, washingResult.getL8060().get()); }else { record.set("8060", recordSchemaR, null); }
		if ( washingResult.getL8061()!=null) { record.set("8061", recordSchemaR, washingResult.getL8061().get()); }else { record.set("8061", recordSchemaR, null); }
		if ( washingResult.getL8062()!=null) { record.set("8062", recordSchemaR, washingResult.getL8062().get()); }else { record.set("8062", recordSchemaR, null); }
		if ( washingResult.getL8063()!=null) { record.set("8063", recordSchemaR, washingResult.getL8063().get()); }else { record.set("8063", recordSchemaR, null); }
		if ( washingResult.getL8064()!=null) { record.set("8064", recordSchemaR, washingResult.getL8064().get()); }else { record.set("8064", recordSchemaR, null); }
		if ( washingResult.getL8065()!=null) { record.set("8065", recordSchemaR, washingResult.getL8065().get()); }else { record.set("8065", recordSchemaR, null); }
		if ( washingResult.getL8066()!=null) { record.set("8066", recordSchemaR, washingResult.getL8066().get()); }else { record.set("8066", recordSchemaR, null); }
		if ( washingResult.getL8067()!=null) { record.set("8067", recordSchemaR, washingResult.getL8067().get()); }else { record.set("8067", recordSchemaR, null); }
		if ( washingResult.getL8068()!=null) { record.set("8068", recordSchemaR, washingResult.getL8068().get()); }else { record.set("8068", recordSchemaR, null); }
		if ( washingResult.getL8069()!=null) { record.set("8069", recordSchemaR, washingResult.getL8069().get()); }else { record.set("8069", recordSchemaR, null); }
		if ( washingResult.getL806a()!=null) { record.set("806a", recordSchemaR, washingResult.getL806a().get()); }else { record.set("806a", recordSchemaR, null); }
		if ( washingResult.getL806b()!=null) { record.set("806b", recordSchemaR, washingResult.getL806b().get()); }else { record.set("806b", recordSchemaR, null); }
		if ( washingResult.getL806c()!=null) { record.set("806c", recordSchemaR, washingResult.getL806c().get()); }else { record.set("806c", recordSchemaR, null); }
		if ( washingResult.getL806d()!=null) { record.set("806d", recordSchemaR, washingResult.getL806d().get()); }else { record.set("806d", recordSchemaR, null); }
		if ( washingResult.getL806e()!=null) { record.set("806e", recordSchemaR, washingResult.getL806e().get()); }else { record.set("806e", recordSchemaR, null); }
		if ( washingResult.getL806f()!=null) { record.set("806f", recordSchemaR, washingResult.getL806f().get()); }else { record.set("806f", recordSchemaR, null); }
		if ( washingResult.getL8070()!=null) { record.set("8070", recordSchemaR, washingResult.getL8070().get()); }else { record.set("8070", recordSchemaR, null); }
		if ( washingResult.getL8071()!=null) { record.set("8071", recordSchemaR, washingResult.getL8071().get()); }else { record.set("8071", recordSchemaR, null); }
		if ( washingResult.getL8072()!=null) { record.set("8072", recordSchemaR, washingResult.getL8072().get()); }else { record.set("8072", recordSchemaR, null); }
		if ( washingResult.getL8073()!=null) { record.set("8073", recordSchemaR, washingResult.getL8073().get()); }else { record.set("8073", recordSchemaR, null); }
		if ( washingResult.getL8074()!=null) { record.set("8074", recordSchemaR, washingResult.getL8074().get()); }else { record.set("8074", recordSchemaR, null); }
		if ( washingResult.getL8075()!=null) { record.set("8075", recordSchemaR, washingResult.getL8075().get()); }else { record.set("8075", recordSchemaR, null); }
		if ( washingResult.getL8076()!=null) { record.set("8076", recordSchemaR, washingResult.getL8076().get()); }else { record.set("8076", recordSchemaR, null); }
		if ( washingResult.getL8077()!=null) { record.set("8077", recordSchemaR, washingResult.getL8077().get()); }else { record.set("8077", recordSchemaR, null); }
		if ( washingResult.getL8078()!=null) { record.set("8078", recordSchemaR, washingResult.getL8078().get()); }else { record.set("8078", recordSchemaR, null); }
		if ( washingResult.getLsid()!=null) {  record.set("sid",  recordSchemaR, washingResult.getLsid()); } else {       record.set("sid", recordSchemaR, null); }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		record.set("flow_time", recordSchemaR, sdf.format(new Date()));
		
		return record;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new DataWashing(), args);
		System.exit(exitCode);
	}

	static int printUsage() {
		logger.debug("-dbname cloud_dev -inputTable ods_veh_history_traffic -inputTable4Yesterday bds_veh_history_washing_result_4_yesterday_d -outputTable bds_veh_history_washing_result_dd  -outputTable4Yesterday bds_veh_history_washing_result_4_yesterday_d -filter \"st_date = '20161002'\" -filter4Yesterday \"stat_date = '20161001'\" -partition \"20161002\"");
		ToolRunner.printGenericCommandUsage(System.out);
		return 2;
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		logger.debug("启动参数为：{}",Arrays.toString(args));
		
		String dbname = null;
		String inputTable = null;
		String filter = null;  		
		String inputTable4Yesterday = null;
		String filter4Yesterday = null;  
		String outputTable = null;
		String outputTable4Yesterday = null;
		Integer reduceNum = null;
		String partition = null;
		
		for (int i = 0; i < otherArgs.length; i++) {
			if (otherArgs[i].equals("-dbname")) {
				dbname = otherArgs[++i];
			} else if (otherArgs[i].equals("-inputTable")) {
				inputTable = otherArgs[++i];
			} else if (otherArgs[i].equals("-inputTable4Yesterday")) {
				inputTable4Yesterday = otherArgs[++i];
			} else if (otherArgs[i].equals("-filter")) {
				filter = otherArgs[++i];
			} else if (otherArgs[i].equals("-filter4Yesterday")) {
				filter4Yesterday = otherArgs[++i];
			} else if (otherArgs[i].equals("-outputTable")) {
				outputTable = otherArgs[++i];
			} else if (otherArgs[i].equals("-outputTable4Yesterday")) {
				outputTable4Yesterday = otherArgs[++i];
			} else if (otherArgs[i].equals("-reduceNum")) {
				reduceNum = Integer.parseInt(otherArgs[++i]);
			} else if (otherArgs[i].equals("-partition")){
				partition = otherArgs[++i];
			}
		}

		if (dbname == null || inputTable == null || outputTable == null || inputTable4Yesterday == null || outputTable4Yesterday == null || partition == null) {
			return printUsage();
		}
		
		logger.info("取值范围filter：{}",filter);
		conf.set("outputTable", outputTable);
		conf.set("outputTable4Yesterday", outputTable4Yesterday);
		conf.set("partition", partition);
		
		Driver driver = null;
		try {
			//插入该分区数据前应该先清空一下
			HiveConf hiveConf = HCatUtil.getHiveConf(conf);
			driver = new Driver(hiveConf);
			SessionState.start(new CliSessionState(hiveConf));
			driver.run("ALTER TABLE " + dbname + "." + outputTable + " DROP IF EXISTS PARTITION (stat_date=" + partition + ")").getResponseCode();
			driver.run("ALTER TABLE " + dbname + "." + outputTable4Yesterday + " DROP IF EXISTS PARTITION (stat_date=" + partition + ")").getResponseCode();
		} catch (Exception e) {
			logger.error("清除分区时报错：{}", e.getMessage());
		} finally {
			driver.close();
		}

		Job job = Job.getInstance(conf, "Data_Shuffle_By_Day");
		HCatMultipleInputs.init(job);
		HCatMultipleInputs.addInput(inputTable, dbname, filter, DataWashingMapper.class);
		HCatMultipleInputs.addInput(inputTable4Yesterday, dbname, filter4Yesterday, DataWashing4YesterdayMapper.class);
		HCatMultipleInputs.build();
		
		job.setJarByClass(DataWashing.class);
		job.setMapOutputKeyClass(CombinationKey.class);
		job.setMapOutputValueClass(HistoryTravelDataWritable.class);
		
		job.setPartitionerClass(FirstPartitioner.class);
		job.setGroupingComparatorClass(FirstGroupingComparator.class);
		
		job.setReducerClass(DataWashingReducer.class);
		job.setOutputKeyClass(CombinationKey.class);
		job.setOutputValueClass(DefaultHCatRecord.class);
		if (reduceNum != null) {
			job.setNumReduceTasks(reduceNum);
		}
		
		job.setOutputFormatClass(MultiOutputFormat.class); //设置输出到多分区
		JobConfigurer configurer = MultiOutputFormat.createConfigurer(job);
		
		Map<String, String> pttion = new HashMap<String, String>();
		pttion.put("stat_date", partition);
		
	    configurer.addOutputFormat(outputTable, HCatOutputFormat.class, BytesWritable.class, HCatRecord.class);
	    OutputJobInfo outputJobInfo = OutputJobInfo.create(dbname, outputTable, pttion);
	    Job job1 = configurer.getJob(outputTable);
	    HCatOutputFormat.setOutput(job1, outputJobInfo);
	    HCatSchema schema1 = HCatOutputFormat.getTableSchema(job1.getConfiguration());
	    HCatOutputFormat.setSchema(job1,schema1);
	    
	    configurer.addOutputFormat(outputTable4Yesterday, HCatOutputFormat.class, BytesWritable.class, HCatRecord.class);
	    OutputJobInfo outputJobInfo4Yesterday = OutputJobInfo.create(dbname, outputTable4Yesterday, pttion);
	    Job job2 = configurer.getJob(outputTable4Yesterday);
	    HCatOutputFormat.setOutput(job2, outputJobInfo4Yesterday);
	    HCatSchema schema4Yesterday = HCatOutputFormat.getTableSchema(job2.getConfiguration());
	    HCatOutputFormat.setSchema(job2,schema4Yesterday);
		
		configurer.configure(); //多分区输出配置
		return (job.waitForCompletion(true) ? 0 : 1);
	}

}