package web;

import java.util.HashMap;
import java.util.Map;

public class TestJsonConvertor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String returnObject= "[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/t_c2817bb543f24c62342351664c987235/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/t_c2817bb543f24c62342351664c987235/t_c2817bb543f24c688d4241664c987aaf/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/t_c2817bb543f24c62342351664c987235/t_c2817bb543f24c688d4241664c987aaf/t_71e425134f6d4614822f1c924ea3f424/\",\"t\":\"a\",\"type\":\"71e425134f6d4614822f1c924ea3f424\"},\"children\":[],\"iconCls\":\"icon-device\",\"id\":\"25c0a58a8fd842808de7daa5b124e4db\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"空调2021(OPC)(信息港一楼)\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"71e425134f6d4614822f1c924ea3f424\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"艾默生空调U480\"},{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c62342351664c987235/t_c2817bb543f24c688d4241664c987aaf/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c62342351664c987235/t_c2817bb543f24c688d4241664c987aaf/t_c2817bb543f24c688d4241664c98897/\",\"t\":\"a\",\"type\":\"c2817bb543f24c688d4241664c98897\"},\"children\":[],\"iconCls\":\"icon-device\",\"id\":\"08d3be2420d14ff799dbc2ba03878316\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"空调2023(SNMP)(信息港一楼)\"},{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c62342351664c987235/t_c2817bb543f24c688d4241664c987aaf/t_c2817bb543f24c688d4241664c98897/\",\"t\":\"a\",\"type\":\"c2817bb543f24c688d4241664c98897\"},\"children\":[],\"iconCls\":\"icon-device\",\"id\":\"759a790d758241df9ea3fc9a569a3b81\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"空调2022(SNMP)(信息港一楼)\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"c2817bb543f24c688d4241664c98897\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"美的空调Q812\"},{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c62342351664c987235/t_c2817bb543f24c688d4241664c987aaf/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c62342351664c987235/t_c2817bb543f24c688d4241664c987aaf/t_d9b5680bc3de4ad2a5e4eb61b661dbcc/\",\"t\":\"a\",\"type\":\"d9b5680bc3de4ad2a5e4eb61b661dbcc\"},\"children\":[],\"iconCls\":\"icon-device\",\"id\":\"925d18baed424632905862eafa18ec48\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"安静(信息港一楼)\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"d9b5680bc3de4ad2a5e4eb61b661dbcc\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"美的空调T1413\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"c2817bb543f24c688d4241664c987aaf\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"空调\"},{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c62342351664c987235/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c62342351664c987235/t_e164b30771134e75882b107a5c42e61a/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c62342351664c987235/t_e164b30771134e75882b107a5c42e61a/t_fe52cc420b7c4e63859a61a11de3af6b/\",\"t\":\"a\",\"type\":\"fe52cc420b7c4e63859a61a11de3af6b\"},\"children\":[],\"iconCls\":\"icon-device\",\"id\":\"c787f985f2a24b8f8677ec8f563119d1\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"列头柜01(信息港一楼)\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"fe52cc420b7c4e63859a61a11de3af6b\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"艾默生列头柜T100\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"e164b30771134e75882b107a5c42e61a\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"列头柜\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"c2817bb543f24c62342351664c987235\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"基础设施\"},{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/t_c2817bb543f24c63536351664c987657/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c63536351664c987657/t_c2817bb543f24c63536351664c987878/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c63536351664c987657/t_c2817bb543f24c63536351664c987878/t_24f8ec17a49d11e5a1dc6c0b8409f355/\",\"t\":\"a\",\"type\":\"24f8ec17a49d11e5a1dc6c0b8409f355\"},\"children\":[],\"iconCls\":\"icon-device\",\"id\":\"7f22fc0ea67c4d3690f51a54a54891fe\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"机柜B01(信息港一楼)\"},{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_39172c34119e493f8073f1b23ba06f73/t_c2817bb543f24c63536351664c987657/t_c2817bb543f24c63536351664c987878/t_24f8ec17a49d11e5a1dc6c0b8409f355/\",\"t\":\"a\",\"type\":\"24f8ec17a49d11e5a1dc6c0b8409f355\"},\"children\":[],\"iconCls\":\"icon-device\",\"id\":\"fe307af7de9546278ef956fd5f8e534b\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"机柜B02(信息港一楼)\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"24f8ec17a49d11e5a1dc6c0b8409f355\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"大唐机柜001\"},{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/t_c2817bb543f24c63536351664c987657/t_c2817bb543f24c63536351664c987878/\",\"t\":\"t\"},\"children\":[{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/t_c2817bb543f24c63536351664c987657/t_c2817bb543f24c63536351664c987878/t_c2817bb543f24c63536351664c987531/\",\"t\":\"a\",\"type\":\"c2817bb543f24c63536351664c987531\"},\"children\":[],\"iconCls\":\"icon-device\",\"id\":\"07e36f88fb354e2f861423aaa32dbe37\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"机柜01(信息港一楼)\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"c2817bb543f24c63536351664c987531\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"大唐机柜8842\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"c2817bb543f24c63536351664c987878\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"机柜\"}],\"iconCls\":\"icon-devicetype\",\"id\":\"c2817bb543f24c63536351664c987657\",\"order\":0,\"state\":\"closed\",\"target\":\"\",\"text\":\"纯资产\"}]";
		//String returnObject = "{\"attributes\":{\"path\":\"c_19c2a6c85e154ce79b06c79c578ee57a/l_b81081619ff54d2eb4dd6cf5628685f5/l_b6feb00c623c4bb78567285f6465d571/t_c2817bb543f24c63536351664c987657/t_c2817bb543f24c63536351664c987878/t_c2817bb543f24c63536351664c987531/\",\"t\":\"a\",\"type\":\"c2817bb543f24c63536351664c987531\"},\"children\":{},\"iconCls\":\"icon-device\",\"id\":\"07e36f88fb354e2f861423aaa32dbe37\",\"order\":0,\"state\":\"open\",\"target\":\"\",\"text\":\"机柜01(信息港一楼)\"}";
		//System.out.println(removeSpecialSubString(returnObject,"attributes","children"));
		//System.out.println(removeSpecialSubString(returnObject,"attributes","children").replace(" ", ""));
		System.out.println(convertEasyTree2BootTree(returnObject));
	}
	
	/** 
	* 将easyUI tree的json串转换成bootstrap tree的json串
	* @author Victor_Diao
	* @param easyUITree
	* @return String
	*/
	public static String convertEasyTree2BootTree(String easyUITree){
		String bootstrapTreeString="";
		//去掉attributes到children之间内容（保留children）
		bootstrapTreeString = removeSpecialSubString(easyUITree,"attributes","children").replace(" ", "");
		//去掉order到target之间内容（保留target）
		bootstrapTreeString = removeSpecialSubString(bootstrapTreeString,"order","text").replace(" ", "");
		//替换iconCls为icon并添加样式glyphicon 
		bootstrapTreeString = bootstrapTreeString.replace("iconCls\":\"", "icon\":\"glyphicon ");
		//替换id为href
		bootstrapTreeString = bootstrapTreeString.replace("\"id\"", "\"href\"");		
		//替换children为nodes
		bootstrapTreeString = bootstrapTreeString.replace("children", "nodes");	
		//替换空的nodes:[]
		bootstrapTreeString = bootstrapTreeString.replace("\"nodes\":[],", "");	
		return bootstrapTreeString;
	}
	
	/** 
 	* 返回包含特定串的个数
	* @param source
	* @param template
	* @return int
	*/
	public static int getCount(String source, String template){
		int count=0;
		int index = 0;
		while(index!=-1){
			index = source.indexOf(template);
			if(index!=-1){
				count++;
				source = source.substring(index+1);
			}
		}
		return count;
	}
	
	/** 
	* 获得指定的template在str中的次数对应位置的MAP
	* @author Victor_Diao
	* @param str
	* @param template
	* @return Map<Integer,Integer>
	*/
	public static Map<Integer,Integer> getPositionMap(String str, String template){
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		int count=0;
		int index = 0;
		int tmp=0;
		while(index!=-1){
			index = str.indexOf(template);
			if(index!=-1){
				tmp = index+1+tmp;
				map.put(count, tmp-1);
				count++;
				str = str.substring(index+1);
			}
		}
		return map;
	}
	
	/** 
	* 删除字符串str中from到to的字符串
	* @author Victor_Diao
	* @param str
	* @param from
	* @param to
	* @return String
	*/
	public static String removeSpecialSubString(String str, String from, String to){
		Map<Integer,Integer> fromMap = getPositionMap(str,from);
		Map<Integer,Integer> toMap = getPositionMap(str,to);
		if (fromMap.size()!=toMap.size()) {
			throw new IllegalArgumentException("串from的个数应该与to的个数保持一致！");
		}
		int times = fromMap.size();
		int a = 0,b;
		String temp;
		for(int i=0; i< times; i++){
			a = fromMap.get(i);
			b = toMap.get(i);
			if (a!=-1) {				
				temp = str.substring(a-1, b-1);
				str = str.replace(temp, getBlankSpace(b-a));
			}
		}
		return str;
	}
	
	/** 
	* 获得指定长度的空串
	* @author Victor_Diao
	* @param length
	* @return String
	*/
	public static String getBlankSpace(int length){
		String str = "";
		for (int i = 0; i < length; i++) {
			str = " "+str;
		}
		return str;
	}

}
