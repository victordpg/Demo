/**   
* @Title: customerController.java 
* @Package com.neusoft.cloud.web.webpage.customer.controller 
* @author yaobo
* @date 2015年11月19日 下午3:55:26 
*   
*/
package web.bootstraptree;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.neusoft.cloud.phone.rest.alarm.type.AlarmInfosRequest;
import com.neusoft.cloud.web.admin.alarm.condition.CloudAlarmRequest;
import com.neusoft.cloud.web.admin.alarm.service.CloudAlarmService;
import com.neusoft.cloud.web.admin.basequestion.beans.CloudBasequestion;
import com.neusoft.cloud.web.admin.basequestion.beans.CloudPoints;
import com.neusoft.cloud.web.admin.basequestion.condition.CloudBasequestionRequest;
import com.neusoft.cloud.web.admin.basequestion.service.CloudBasequestionService;
import com.neusoft.cloud.web.admin.customer.beans.CloudCustomer;
import com.neusoft.cloud.web.admin.customer.service.CloudCustomerService;
import com.neusoft.cloud.web.admin.customeragreement.beans.CloudCustomerAgreement;
import com.neusoft.cloud.web.admin.customeragreement.condition.CloudCustomerAgreementRequest;
import com.neusoft.cloud.web.admin.customeragreement.service.CloudCustomerAgreementService;
import com.neusoft.cloud.web.admin.customermsg.beans.CloudMessage;
import com.neusoft.cloud.web.admin.customermsg.service.CloudMessageService;
import com.neusoft.cloud.web.admin.evaluation.beans.CloudEvaluation;
import com.neusoft.cloud.web.admin.evaluation.condition.CloudEvaluationRequest;
import com.neusoft.cloud.web.admin.evaluation.service.CloudEvaluationService;
import com.neusoft.cloud.web.admin.eventhandle.beans.CloudEventhandle;
import com.neusoft.cloud.web.admin.eventhandle.condition.CloudEventhandleRequest;
import com.neusoft.cloud.web.admin.eventhandle.service.CloudEventhandleService;
import com.neusoft.cloud.web.admin.failure.beans.CloudCustomerFailure;
import com.neusoft.cloud.web.admin.failure.service.CloudCustomerFailureService;
import com.neusoft.cloud.web.admin.knowledge.beans.CloudKnowledge;
import com.neusoft.cloud.web.admin.knowledge.condition.CloudKnowledgeRequest;
import com.neusoft.cloud.web.admin.knowledge.service.CloudKnowledgeService;
import com.neusoft.cloud.web.webpage.customer.beans.AlarmNumBean;
import com.neusoft.cloud.web.webpage.customer.beans.FaultInfo;
import com.neusoft.cloud.web.webpage.customer.beans.FaultNumBean;
import com.neusoft.cloud.web.webpage.customer.beans.FixIssue;
import com.neusoft.cloud.web.webpage.customer.beans.InspectionLastResult;
import com.neusoft.cloud.web.webpage.customer.beans.InspectionStatUnnormal;
import com.neusoft.cloud.web.webpage.customer.beans.MsgQueryCriteria;
import com.neusoft.cloud.web.webpage.customer.beans.PointDays;
import com.neusoft.cloud.web.webpage.customer.beans.PointState;
import com.neusoft.cloud.web.webpage.customer.beans.PointsDays;
import com.neusoft.cloud.web.webpage.customer.beans.PropertyAssetInfo;
import com.neusoft.cloud.web.webpage.customer.beans.PropertyAssetPointInfo;
import com.neusoft.cloud.web.webpage.customer.beans.VenderContact;
import com.neusoft.cloud.web.webpage.customer.service.CustomerService;
import com.neusoft.common.util.DateConvertUtils;
import com.neusoft.common.util.EasyUITree2BootstrapTree;
import com.neusoft.common.util.MessageInfo;
import com.neusoft.common.util.MobileUtil;
import com.neusoft.common.util.Page;
import com.neusoft.framework.base.BaseController;
import com.neusoft.framework.beans.AjaxDone;
import com.neusoft.framework.cache.EhCacheManager;
import com.neusoft.framework.util.DateUtil;
import com.neusoft.framework.util.UUIDUtil;

/**
 * @author YaoBo
 *
 */
@Controller("CustomerController")
@RequestMapping(value = "/")
public class CustomerController extends BaseController{
	private static final Log log = LogFactory.getLog(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CloudCustomerService cloudCustomerService;
	@Autowired
	private CloudCustomerAgreementService cloudCustomerAgreementService;
	@Autowired
	private CloudEventhandleService cloudEventhandleService;
	@Autowired
	private CloudCustomerFailureService cloudCustomerFailureService;
	@Autowired
	private CloudAlarmService cloudAlarmService;
	@Autowired
	private CloudMessageService cloudMessageService;
	@Autowired
	private CloudKnowledgeService cloudKnowledgeService;
	@Autowired
	private CloudBasequestionService cloudBasequestionService;
	@Autowired
	private CloudEvaluationService cloudEvaluationService;
	
	
	@RequestMapping("customer/index")
	public String index(Page<CloudEventhandle> page,Model model,HttpSession session)  {
		CloudCustomer session_customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");  
	    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar time=Calendar.getInstance();
			String yearStart=String.valueOf(time.get(Calendar.YEAR))+"0101000000"; //今年开始时间
			String yearEnd=String.valueOf(time.get(Calendar.YEAR))+"1231235959";	//今年结束时间
			String startTime=null;
			String endTime=null;
			try {
				startTime=sdf2.format(sdf.parse(yearStart));
				endTime=sdf2.format(sdf.parse(yearEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long fiveDay=System.currentTimeMillis()-(long)24*3600*5000; //前五天
			String fiveDays=sdf2.format(fiveDay); //前五天 用于图形展示
			String oneDays=sdf2.format(new Date()); //当前系统时间 用于查询图形展示结束时间条件
			time.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
			String mothStart=sdf2.format(DateUtil.getStartTimeOfDate(time.getTime()));//当前月第一天
			time.add(Calendar.MONTH,1);//月增加1天
			time.add(Calendar.DAY_OF_MONTH,-1);//日期倒数一日,既得到本月最后一天
			String mothEnd=sdf2.format(time.getTime());//当前月最后一天
			int faultHandling=customerService.searchFaultDay(session_customer.getLinkid());//当天正在处理的故障数
			int faultMoth=customerService.searchFaultMoth(mothStart, mothEnd, session_customer.getLinkid()); //本月累计发生故障
			int faultYear=customerService.searchFaultYear(startTime, endTime, session_customer.getLinkid()); //年度累计发生故障数 
			List<FaultNumBean> listFault=customerService.serachFauleDayChar(fiveDays, oneDays, session_customer.getLinkid()); //图形显示数据
			//用于封装前台图形显示时间
			List<String> listTime=new ArrayList<String>(); 
			List<String> listDetail=new ArrayList<String>();//显示点数故障
			int count=0;
			for (int i = 0; i <5; i++) {
				long five=System.currentTimeMillis()-(long)24*3600*(5000-count);
				count+=1000;
				String times=sdf3.format(five);
				listTime.add(times);
			}
			for (int i = 0; i < listTime.size(); i++) {
				String times=listTime.get(i);	
				boolean f = false;
				for (int j = 0; j < listFault.size(); j++) {
					FaultNumBean bean=listFault.get(j);
					if (times.equals(bean.getTime())) {
						listDetail.add(String.valueOf(bean.getCount()));	
						f = true;
						break;
					}
				}
				if (!f) {
					listDetail.add("0");
				}
			}
			List<String> listAlarmDetail=new ArrayList<String>();//显示点数告警
			List<AlarmNumBean> listAlarm=customerService.serachAlarmDayChar(fiveDays, oneDays, session_customer.getLinkid());
			for (int i = 0; i < listTime.size(); i++) {
				String times=listTime.get(i);	
				boolean f = false;
				for (int j = 0; j < listAlarm.size(); j++) {
					AlarmNumBean bean=listAlarm.get(j);
					if (times.equals(bean.getTime())) {
						listAlarmDetail.add(String.valueOf(bean.getCount()));	
						f = true;
						break;
					}
				}
				if (!f) {
					listAlarmDetail.add("0");
				}
			}
			
			int alarmHandling=customerService.searchAlarmDay(session_customer.getLinkid());//当前正在处理的告警数
			int alarmMoth=customerService.searchAlarmMoth(mothStart, mothEnd, session_customer.getLinkid());// 本月累积的告警数
			int alarmYear=customerService.searchAlarmYear(startTime, endTime, session_customer.getLinkid()); //年度累计的告警数
			
			InspectionLastResult inspection=customerService.searchInspection(session_customer.getLinkid()); //巡检详情 
			InspectionStatUnnormal inspectionStat=customerService.serchInspectionDayOrMothOrYear(session_customer.getLinkid(), sdf1.format(new Date()));
			model.addAttribute("faultHandling",faultHandling);
			model.addAttribute("faultMoth",faultMoth);
			model.addAttribute("faultYear",faultYear);
			model.addAttribute("alarmHandling",alarmHandling);
			model.addAttribute("alarmMoth",alarmMoth);
			model.addAttribute("alarmYear",alarmYear);
			model.addAttribute("inspection",inspection);
			model.addAttribute("inspectionStat",inspectionStat);
			model.addAttribute("listFault",listFault);
			model.addAttribute("listTime",listTime);
			model.addAttribute("listDetail",listDetail);
			model.addAttribute("listAlarmDetail",listAlarmDetail);
			
		return "cloud/web/webpage/customer/user";
	}
	
	/**
	 * 
	* @Title: alarmHandleShow 
	* @author yaobo
	* @Description: 告警分页
	* @param @param page
	* @param @param model
	* @param @return
	* @param @throws UnsupportedEncodingException    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("customer/alarmHandleShow")
	public String alarmHandleShow(Page<AlarmInfosRequest> page,Model model,CloudAlarmRequest request,HttpSession session) throws UnsupportedEncodingException {
		model.addAttribute("page",alarmload(page,request,session));
		model.addAttribute("year",null==request.getYear()?"":request.getYear());
		model.addAttribute("month",null==request.getMonth()?"":request.getMonth()+"月");
		model.addAttribute("status",null==request.getStatus()?"":getRealAlarmStatusname(request.getStatus()));
		return "cloud/web/webpage/customer/alarm";
	}
	
	@RequestMapping("customer/alarmHandleMore")
    @ResponseBody
	public Page<AlarmInfosRequest> alarmHandleMore(Page<AlarmInfosRequest> page,CloudAlarmRequest request,HttpSession session) throws UnsupportedEncodingException {
		return alarmload(page,request,session);
	}
	@SuppressWarnings("unchecked")
	private Page<AlarmInfosRequest> alarmload(Page<AlarmInfosRequest> page,CloudAlarmRequest request,HttpSession session) throws UnsupportedEncodingException{
		    CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		    request.setYear(getUTF8Value(request.getYear()));
	        request.setMonth(getUTF8Value(request.getMonth()));
	    
	        if("已关闭".equals(getUTF8Value(request.getStatus()))){
	        	request.setStatus("1");
	        }
	        else if("处理中".equals(getUTF8Value(request.getStatus()))){
	        	request.setStatus("2");
	        }
	        else if("未处理".equals(getUTF8Value(request.getStatus()))){
	        	request.setStatus("0");
	        }
	        else if("0".equals(request.getStatus())){
	        	request.setStatus("0");
	        }
	        else{
	        	request.setStatus("");
	        }
	        request.setCustomerid(customer.getLinkid());
	        request.setPage(String.valueOf(page.getCurrentPage()+1));
	        request.setRows("10");
			Map<String,Object> map = cloudAlarmService.searchCloudAlarm(request, buildRowBoundsPage(page));
	        page.setItems((List<AlarmInfosRequest>)map.get("list"));
	        page.setTotalRecords(Long.valueOf(map.get("count").toString()));
	        page.setRecordsPerPage(10);
	        return page;
    }
	
	
	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 分页
	* @Title: issuList 
	* @author yaobo
	* @Description: 故障处理  list
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("customer/eventHandleShow")
	public String handleList(Page<CloudEventhandle> page,Model model,CloudEventhandleRequest request,HttpSession session) throws UnsupportedEncodingException {
		model.addAttribute("page",load(page,request,session));
		model.addAttribute("year", request.getYear()==null?"":request.getYear());
		model.addAttribute("month", request.getMonth()==null?"":request.getMonth()+"月");
		model.addAttribute("status", request.getStatus()==null?"":getRealStatusname(request.getStatus()));
		return "cloud/web/webpage/customer/handle";
	}
	/**
	 * 
	* 获取状态显示在页面上
	* @author YaoBo
	* @param str
	* @return String
	 */
	public String getRealStatusname(String str){
		if("1".equals(str)){
			return "未处理";
		}
		if("2".equals(str)){
			return "处理中";
		}
		if("3".equals(str)){
			return "已关闭";
		}
		return "";
	}
	
	public String getRealAlarmStatusname(String str){
		if("0".equals(str)){
			return "未处理";
		}
		if("2".equals(str)){
			return "处理中";
		}
		if("1".equals(str)){
			return "已关闭";
		}
		return "";
	}


	
	/**
	 * 
	* @Title: handle 
	* @author yaobo
	* @Description: 
	* @param @param page
	* @param @param request
	* @param @return  无查询条件
	* @param @throws UnsupportedEncodingException    设定文件 
	* @return Page<CloudEventhandle>    返回类型 
	* @throws
	 */
	@RequestMapping("customer/eventHandleMore")
    @ResponseBody
	public Page<CloudEventhandle> handle(Page<CloudEventhandle> page,CloudEventhandleRequest request,HttpSession session) throws UnsupportedEncodingException {
		return load(page,request,session);
	}
	
	/**
	 * 有查询条件
	* @Title: handleWithSearch 
	* @author yaobo
	* @Description: 
	* @param @param page
	* @param @param request
	* @param @return
	* @param @throws UnsupportedEncodingException    设定文件 
	* @return Page<CloudEventhandle>    返回类型 
	* @throws
	 */
	@RequestMapping("customer/eventHandleMoreWithSearch")
    @ResponseBody
	public Page<CloudEventhandle> handleWithSearch(Page<CloudEventhandle> page,CloudEventhandleRequest request,HttpSession session) throws UnsupportedEncodingException {
		return loadWithSearch(page,request,session);
	}
	
	/**
	 * 
	* @Title: 故障 
	* @author yaobo
	* @Description: 
	* @param @param page
	* @param @return
	* @param @throws UnsupportedEncodingException    设定文件 
	* @return Page<CloudEventhandle>    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	private Page<CloudEventhandle> loadWithSearch(Page<CloudEventhandle> page,CloudEventhandleRequest request,HttpSession session) throws UnsupportedEncodingException{
		 CloudEventhandleRequest searchRequest= new CloudEventhandleRequest();
		 CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		 searchRequest.setYear(getUTF8Value(request.getYear()));
		 searchRequest.setMonth(getUTF8Value(request.getMonth()));
		if("未处理".equals(getUTF8Value(request.getStatus()))){
			 searchRequest.setStatus("1");
		}
		else if("处理中".equals(getUTF8Value(request.getStatus()))){
			searchRequest.setStatus("2");	 
		}
		else if("已关闭".equals(getUTF8Value(request.getStatus()))){
			 searchRequest.setStatus("3");
		}
		else{
			 searchRequest.setStatus("");
		}
		 
		if("网上申报".equals(getUTF8Value(request.getType()))){
			 searchRequest.setType("1");
		}
		else if("电话申报".equals(getUTF8Value(request.getType()))){
			searchRequest.setType("2");	 
		}
		else if("系统保障".equals(getUTF8Value(request.getType()))){
			searchRequest.setType("3");
		}
		else{
			 searchRequest.setType("");
		}
		 searchRequest.setHandler(getUTF8Value(request.getHandler()));
		 searchRequest.setPage(String.valueOf(page.getCurrentPage()+1));
		 searchRequest.setRows("10");
		 searchRequest.setUid(customer.getLinkid());
        //获取分页列表项
        Map<String,Object> items = cloudEventhandleService.searchCloudEventhandle(searchRequest, buildRowBoundsPage(page));
        page.setItems((List<CloudEventhandle>)items.get("list"));;
        page.setTotalRecords(Long.valueOf(items.get("count").toString()));
        page.setRecordsPerPage(10);
        return page;
	
    }
	
	/**
	 * 
	* @Title: getUTF8Value 
	* @author yaobo
	* @Description: UTF8转换
	* @param @param values
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getUTF8Value(String values){
		try {
			if(!"".equals(values)){
				return URLDecoder.decode(values,"utf-8");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 
	* @Title: load 
	* @author yaobo
	* @Description: 
	* @param @param page
	* @param @return  首次加载分页 没有搜索条件
	* @param @throws UnsupportedEncodingException    设定文件 
	* @return Page<CloudEventhandle>    返回类型 
	* @throws
	 */
	private Page<CloudEventhandle> load(Page<CloudEventhandle> page,CloudEventhandleRequest request,HttpSession session) throws UnsupportedEncodingException{
		 CloudEventhandleRequest searchRequest= new CloudEventhandleRequest();
		 CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		 searchRequest.setYear(request.getYear());
		 searchRequest.setMonth(request.getMonth());
		 searchRequest.setStatus(request.getStatus());
		 searchRequest.setUid(customer.getLinkid());
		 searchRequest.setRows("10");
		 searchRequest.setPage(String.valueOf(page.getCurrentPage()+1));
        //获取分页列表项
        Map<String,Object> items = cloudEventhandleService.searchCloudEventhandle(searchRequest, buildRowBoundsPage(page));
        page.setItems((List<CloudEventhandle>)items.get("list"));;
        page.setTotalRecords(Long.valueOf(items.get("count").toString()));
        page.setRecordsPerPage(10);
        return page;
	
    }
    public RowBounds buildRowBoundsPage(Page page)
	    {
		   RowBounds rb = new RowBounds(page.getCurrentPage()*page.getRecordsPerPage(),page.getRecordsPerPage())   ;
	       return rb;
	 }
    
	/**
	 * 
	* @Title: issueDetail 
	* @author yaobo
	* @Description: 故障处理 详细
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
/*	@RequestMapping("customer/fixIssueStatusDetail")
	public String issueDetail(Model model) {
		FixIssue fi = customerService.getFixIssueStatus();
		model.addAttribute("fixissue", fi);
		return "cloud/web/webpage/customer/main";
	}*/
	
	//合同
	/**
	 * 
	* @Title: 协议 
	* @author yaobo
	* @Description: 
	* @param @param model
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@RequestMapping("customer/agreement")
	public ModelAndView agreement(Model model,HttpSession session) {
		ModelAndView mav = new ModelAndView("cloud/web/webpage/customer/agreement");
		CloudCustomerAgreementRequest request = new CloudCustomerAgreementRequest();
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		request.setLinkid(customer.getLinkid());
		List<CloudCustomerAgreement>  ca = cloudCustomerAgreementService.cloudCustomerAgreementfindAll(request);
		mav.addObject("bean", ca);
		return mav;
	}
	
	/**
	 * @throws URISyntaxException 
	 * 
	* @Title: getPointDays 
	* @author yaobo
	* @Description: //远程巡检
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="customer/pointDays") 
	public String getPointDays(Model model, String year, String month,String machineroom,HttpSession session) throws URISyntaxException {
		Calendar ca=Calendar.getInstance();
	
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		List<PointDays> list = customerService.getPointDays(customer.getLinkid(), year, month);
		CloudCustomerAgreementRequest request = new CloudCustomerAgreementRequest();
		if(customer!=null){
			request.setLinkid(customer.getLinkid());
		}
		List<CloudCustomerAgreement> agreement  = cloudCustomerAgreementService.cloudCustomerAgreementfindAll(request);
		model.addAttribute("agreementlist", agreement);
		if(machineroom!=null){
			model.addAttribute("machine", machineroom);
		}
		model.addAttribute("list", list);
		if(list.size()!=0){
			model.addAttribute("head", list.get(0).getDays());
		}
		else{
			model.addAttribute("head", "");
		}
		model.addAttribute("year", year==null?ca.get(Calendar.YEAR):year);
		model.addAttribute("month", month==null?(ca.get(Calendar.MONTH)+1)+"月":month+"月");
		return "cloud/web/webpage/customer/inspection";
	}
	
	/**
	 * 
	* @Title: addfault 
	* @author yaobo
	* @Description: 故障申报转向
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="customer/faultform") 
	public String addfault(Model model,HttpSession session) {
		CloudCustomerAgreementRequest request = new CloudCustomerAgreementRequest();
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		request.setLinkid(customer.getLinkid());
		//根据customerid查询合同信息
		List<CloudCustomerAgreement> list=cloudCustomerAgreementService.searchCloudCustomeAll(request);
		model.addAttribute("bean", list);
		return "cloud/web/webpage/customer/fault-form";
	}
	
	/**
	 * 故障申报编辑下拉框绑定
	 * @param model
	 * @param customerFailure
	 * @return list
	 */
	@RequestMapping(value="customer/contract") 
	@ResponseBody
	public List getAgreement(Model model, CloudCustomerFailure customerFailure) {
		CloudCustomerAgreementRequest bean =new CloudCustomerAgreementRequest();
		bean.setName(customerFailure.getContract());
		List<CloudCustomerAgreement> list=cloudCustomerAgreementService.searchCloudCustomeAll(bean);
		return list;
	}
	
	/**
	 * 巡检明细
	 * @param model
	 * @param customerFailure
	 * @return String
	 */
	@RequestMapping("customer/PointDaysDetail")
	public String getPointDaysDetail(Model model, String year, String month,String day, String machineName) {
		try {
			Map<String, Object> maps = customerService.getMapTimes(year,month,day);
			EhCacheManager.put("manuallyCache","maps", maps);
			PointsDays days = customerService.getPoint();
			model.addAttribute("bean", days);
			model.addAttribute("year",new String(year.getBytes("iso-8859-1"),"UTF-8"));
			model.addAttribute("month",new String(month.getBytes("iso-8859-1"),"UTF-8"));
			model.addAttribute("day", new String(day.getBytes("iso-8859-1"),"UTF-8"));
			model.addAttribute("machineName", new String(machineName.getBytes("iso-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "cloud/web/webpage/customer/inspection-detail";
	}
	
	/**
	 * 查询昨天巡检明细
	 * @param model
	 * @param year
	 * @param month
	 * @param day
	 * @param machineName
	 * @return
	 */
	@RequestMapping("customer/PointDaysData")
	public String getPointDaysData(Model model, String year, String month,String day, String machineName) {
		Calendar c = Calendar.getInstance();   
		c.set(Integer.valueOf(year), Integer.valueOf(month)-1, Integer.valueOf(day));
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date date = c.getTime();
		year = String.valueOf(c.get(Calendar.YEAR));
		month = String.valueOf((c.get(Calendar.MONTH)+1));
		day = String.valueOf((c.get(Calendar.DAY_OF_MONTH)));
		try {
			Map<String, Object> maps = customerService.getMapTimes(year,month,day);
			EhCacheManager.put("manuallyCache","maps", maps);
			PointsDays days = customerService.getPoint();
			model.addAttribute("bean", days);
			model.addAttribute("year",new String(year.getBytes("iso-8859-1"),"UTF-8"));
			model.addAttribute("month",new String(month.getBytes("iso-8859-1"),"UTF-8"));
			model.addAttribute("day", new String(day.getBytes("iso-8859-1"),"UTF-8"));
			model.addAttribute("machineName", new String(machineName.getBytes("iso-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "cloud/web/webpage/customer/inspection-detail";
	}
	
	
	/**
	 *获取缓存中的巡检页面详情信息
	 * @param model
	 * @param times
	 * @return list
	 */
	@RequestMapping("customer/pointName")
	@ResponseBody
	public List getpointName(Model model, String times) {
		Map<String, Object> maps = (Map<String, Object>) EhCacheManager.get("manuallyCache","maps").get();
		List<PointState> list = null;
		if (maps != null) {
			list = (List<PointState>)maps.get(times);
		} else {
			return null;
		}
		return list;
	}
	
	/**
	 * 故障申请编辑保存
	 * @param model
	 * @param session
	 * @param bean
	 * @return ModelAndView
	 */
	@RequestMapping(value="customer/addfault") 
	@ResponseBody
	public  AjaxDone addFault(Model model, HttpSession session,FaultInfo  bean) {
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		bean.setCustomerid(customer.getLinkid());
		int flag = cloudCustomerFailureService.insertCloudCustomerFailure(bean);
		if(flag==1){
			  return ajaxDoneSuccess("【申报故障】 成功", null);
		}
	    return ajaxDoneSuccess("【申报故障】失败", null);
	}

	/**
	 * 故障申报详情
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="customer/faultDetail") 
	public String faultDetail(Model model,String id,HttpSession session){
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		CloudCustomerAgreementRequest request = new CloudCustomerAgreementRequest();
		request.setLinkid(customer.getLinkid());
		List<CloudCustomerAgreement> cloudCustomerAgreementList  =  cloudCustomerAgreementService.cloudCustomerAgreementfindAll(request);
		FixIssue fixIssue=customerService.getFixIssueStatus(id,cloudCustomerAgreementList.get(0));
		model.addAttribute("bean", fixIssue);
		return "cloud/web/webpage/customer/fault-detail";
	}
	
	/**
	 * 厂家联系方式
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="customer/vendercontactway") 
	public String vendercontactway(Model model,HttpSession session){
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		List<VenderContact>  list =  customerService.getInfo(customer.getLinkid());
		model.addAttribute("list", list);
		return "cloud/web/webpage/customer/vender-contact";
	}
	
	/**
	 * 
	* @Title: vendercontactway 
	* @author yaobo
	* @Description: 信息中心
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="customer/informationcenter") 
	public String informationCenter(){
		return "cloud/web/webpage/customer/information";
	}
	
	/** 
	* 跳转到资产管理
	* @author Victor_Diao
	* @param session
	* @param model
	* @return String
	*/
	@RequestMapping(value = "customer/assets")
    public String showAssets(HttpSession session, Model model) {
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		String customerID = customer.getLinkid();
		String easyUIPropertyTree = customerService.getEasyUITree(customerID);
		if (easyUIPropertyTree==null || "".equals(easyUIPropertyTree)) {
			throw new IllegalArgumentException("未得到客户（"+customerID+"）的资源树信息！");
		}
		log.debug("资源树EasyUI Tree对应的串为："+easyUIPropertyTree);
		String bootstrapPropertyTree =  EasyUITree2BootstrapTree.convertEasyTree2BootTree(easyUIPropertyTree);
		model.addAttribute("propertyTree", bootstrapPropertyTree);
        return "cloud/web/webpage/customer/assets";
    }
	
	/** 
	* 获得设备摘要页信息
	* @author Victor_Diao
	* @param deviceID
	* @param session
	* @param model
	* @return String
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "customer/assets/device/{deviceID}")
    public String showDeviceInfo(@PathVariable String deviceID, HttpSession session, Model model) {
		Map<String, Object> propertiesInfo = customerService.getDeviceInfo(deviceID);
		if (propertiesInfo.size()==0) {
			throw new IllegalArgumentException("未得到设备（"+deviceID+"）的摘要页信息！");
		}
		//有无告警
		String alarmStatus = "无告警";
		Integer alarm = (Integer) propertiesInfo.get("alarm"); //是否有告警：0，无告警；1,有告警
		if (alarm==1) {
			alarmStatus = "有告警";
		}
		//设备基本信息
		Map<String, String> assetInfoMap = (Map<String, String>) propertiesInfo.get("assetinfo");
		PropertyAssetInfo assetInfo = new PropertyAssetInfo(assetInfoMap);
		//设备测量点信息
		List<Map<String, Object>> listPointInfoMap = (List<Map<String, Object>>) propertiesInfo.get("infopoint");
		List<PropertyAssetPointInfo> listPointInfo1 = new ArrayList<PropertyAssetPointInfo>(); //状态量
		List<PropertyAssetPointInfo> listPointInfo2 = new ArrayList<PropertyAssetPointInfo>(); //模拟量
		List<PropertyAssetPointInfo> listPointInfo3 = new ArrayList<PropertyAssetPointInfo>(); //综合参数模拟量
		List<PropertyAssetPointInfo> listPointInfo4 = new ArrayList<PropertyAssetPointInfo>(); //综合参数状态量
		for (int i = 0; i < listPointInfoMap.size(); i++) {
			PropertyAssetPointInfo propertyAssetPointInfo = new PropertyAssetPointInfo(listPointInfoMap.get(i));
			switch (propertyAssetPointInfo.getShow()) {
			case "1":
				listPointInfo1.add(propertyAssetPointInfo);
				break;
			case "2":
				listPointInfo2.add(propertyAssetPointInfo);
				break;
			case "3":
				listPointInfo3.add(propertyAssetPointInfo);
				break;
			case "4":
				listPointInfo4.add(propertyAssetPointInfo);
				break;
			}
		}
		model.addAttribute("alarmStatus", alarmStatus);
		model.addAttribute("assetInfo", assetInfo);
		model.addAttribute("listPointInfo1", listPointInfo1);
		model.addAttribute("listPointInfo2", listPointInfo2);
		model.addAttribute("listPointInfo3", listPointInfo3);
		model.addAttribute("listPointInfo4", listPointInfo4);

		return "cloud/web/webpage/customer/assetsinfo";
    }		
	
	/** 
	* 跳转到消息列表页面
	* @author Victor_Diao
	* @param page
	* @param model
	* @param session
	* @return String
	*/
	@RequestMapping(value = "customer/msg")
    public String showMsg(Page<CloudMessage> page,Model model,HttpSession session) {
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		Long userid = customer.getId().longValue(); //获得门户用户ID
        page.setTotalRecords(cloudMessageService.countMsgByID(userid));
        page.setRecordsPerPage(8);
        List<CloudMessage> items = cloudMessageService.searchMsgByID2(userid, buildRowBoundsPage(page));
        page.setItems(items);
        model.addAttribute("page",page);
		//门户查询条件
		MsgQueryCriteria msgQueryCriteria = new MsgQueryCriteria();
		model.addAttribute("msgQueryCriteria",msgQueryCriteria);
        return "cloud/web/webpage/customer/msg";
    }
	
	/** 
	* 跳转到消息详细页面，同时将消息标记为已读
	* @author Victor_Diao
	* @param id
	* @param model
	* @return String
	*/
	@RequestMapping(value = "customer/msgdetail/{id}/{msgType}/{msgStatus}")
    public String getMsgDetail(@PathVariable Long id, @PathVariable String msgType,@PathVariable String msgStatus,Model model) {
		CloudMessage cloudMessage = cloudMessageService.selectCloudMessageByID(id);
		if("0".equalsIgnoreCase(cloudMessage.getMsgstatus()))
			cloudMessageService.updateMsgByIDStatus(id, "1"); //更改消息状态，将未读变成已读
        model.addAttribute("cloudMessage", cloudMessage);
		//门户查询条件
		MsgQueryCriteria msgQueryCriteria = new MsgQueryCriteria(msgType, msgStatus);
		model.addAttribute("msgQueryCriteria",msgQueryCriteria);
        return "cloud/web/webpage/customer/msg-detail";
    }
	
	/** 
	* 根据UserIDs批量修改消息状态为已删
	* @author Victor_Diao
	* @param ids
	* @param status
	* @param type
	* @return String
	*/
	@RequestMapping("customer/msgdelete")	
	@ResponseBody
	public String deleteMsgByIDs(String ids[], String status, String type) {
		String result = "success";
		int length = ids.length;
		try {
			for (int i = 0; i < length; i++) {
				Long id = Long.parseLong(ids[i]);
				cloudMessageService.updateMsgByIDStatus(id, "2"); //更改消息状态，将未读变成已删
			}
		} catch (Exception e) {
			//logger.error("删除消息时产生异常！",e);
			result = "failure";
		}
		return result;
	}	
	
	/** 
	* 根据UserIDs批量修改消息状态为已读
	* @author Victor_Diao
	* @param ids
	* @param status
	* @param type
	* @return String
	*/
	@RequestMapping("customer/msgmark")	
	@ResponseBody
	public String markMsgByIDs(String ids[], String status, String type) {
		String result = "success";
		int length = ids.length;
		try {
			for (int i = 0; i < length; i++) {
				Long id = Long.parseLong(ids[i]);
				cloudMessageService.updateMsgByIDStatus(id, "1"); //更改消息状态，将未读变成已读
			}
		} catch (Exception e) {
			//logger.error("标记消息时产生异常！",e);
			result = "failure";
		}
		return result;
	}	
	
	/** 
	* 根据条件查询消息
	* @author Victor_Diao
	* @param msgStatus
	* @param msgType
	* @param page
	* @param session
	* @return Page<CloudMessage>
	*/
	@RequestMapping("customer/usermsg")
	@ResponseBody
	public Page<CloudMessage> getMsg(
			@RequestParam(value = "msgstatus") String msgStatus,
			@RequestParam(value = "msgtype") String msgType, Page<CloudMessage> page, HttpSession session) {
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		Long userid = customer.getId().longValue(); //获得门户用户ID
		long count = 0; 
		List<CloudMessage> list = null;
		if ("-1".equals(msgStatus)&&"-1".equals(msgType)) {
			list = cloudMessageService.searchMsgByID2(userid,buildRowBoundsPage(page));
			count = cloudMessageService.countMsgByID(userid);
		}
		if (!"-1".equals(msgStatus)&&"-1".equals(msgType)) {
			list = cloudMessageService.searchMsgByIDStatus(userid,msgStatus,buildRowBoundsPage(page));
			count = cloudMessageService.countMsgByIDStatus(userid,msgStatus);
		}
		if ("-1".equals(msgStatus)&&!"-1".equals(msgType)) {
			list = cloudMessageService.searchMsgByIDType(userid,msgType,buildRowBoundsPage(page));
			count = cloudMessageService.countMsgByIDType(userid,msgType);
		}
		if (!"-1".equals(msgStatus)&&!"-1".equals(msgType)) {
			list = cloudMessageService.searchMsgByIDStatusType(userid,msgStatus,msgType,buildRowBoundsPage(page));
			count = cloudMessageService.countMsgByIDStatusType(userid,msgStatus,msgType);
		}	
		page.setTotalRecords(count);
        page.setRecordsPerPage(8);
		page.setItems(list);
		return page;
	}
	
	/** 
	* 从消息详细页面跳转到消息列表页面
	* @author Victor_Diao
	* @param page
	* @param model
	* @param session
	* @return String
	*/
	@RequestMapping(value = "customer/msg/{msgType}/{msgStatus}")
    public String showMsg(Page<CloudMessage> page,Model model,@PathVariable String msgType,@PathVariable String msgStatus,HttpSession session) {
		page.setRecordsPerPage(8);
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		Long userid = customer.getId().longValue(); //获得门户用户ID
		long count = 0; 
		List<CloudMessage> list = null;
		if ("-1".equals(msgStatus)&&"-1".equals(msgType)) {
			list = cloudMessageService.searchMsgByID2(userid,buildRowBoundsPage(page));
			count = cloudMessageService.countMsgByID(userid);
		}
		if (!"-1".equals(msgStatus)&&"-1".equals(msgType)) {
			list = cloudMessageService.searchMsgByIDStatus(userid,msgStatus,buildRowBoundsPage(page));
			count = cloudMessageService.countMsgByIDStatus(userid,msgStatus);
		}
		if ("-1".equals(msgStatus)&&!"-1".equals(msgType)) {
			list = cloudMessageService.searchMsgByIDType(userid,msgType,buildRowBoundsPage(page));
			count = cloudMessageService.countMsgByIDType(userid,msgType);
		}
		if (!"-1".equals(msgStatus)&&!"-1".equals(msgType)) {
			list = cloudMessageService.searchMsgByIDStatusType(userid,msgStatus,msgType,buildRowBoundsPage(page));
			count = cloudMessageService.countMsgByIDStatusType(userid,msgStatus,msgType);
		}	
		page.setTotalRecords(count);
        page.setRecordsPerPage(8);
		page.setItems(list);
		model.addAttribute("page",page);
		
		//门户查询条件
		MsgQueryCriteria msgQueryCriteria = new MsgQueryCriteria(msgType, msgStatus);
		model.addAttribute("msgQueryCriteria",msgQueryCriteria);
        return "cloud/web/webpage/customer/msg";
    }	
	
	/**
	 * 
	* @Title: 知识分享分页
	* @author yaobo
	* @Description: 
	* @param @param page
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "customer/knowledgeShow")
    public String knowledgeShow(Page<CloudKnowledge> page,Model model,HttpSession session) {
        model.addAttribute("page",knowledgeload(page,session));
        return "cloud/web/webpage/customer/knowledge";
    }
	
    @RequestMapping(value = "customer/knowledgeMore")
    @ResponseBody
    public Page<CloudKnowledge> knowledgeMore(Page<CloudKnowledge> page,HttpSession session){
        return knowledgeload(page,session);
    }
    
	private Page<CloudKnowledge> knowledgeload(Page<CloudKnowledge> page,HttpSession session){
		CloudKnowledgeRequest request = new CloudKnowledgeRequest();
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		request.setIsuse(1);
	    request.setLinkid(customer.getLinkid());
        page.setTotalRecords(cloudKnowledgeService.CloudKnowledgefindPageCount(request));
        page.setRecordsPerPage(10);
        //获取分页列表项
        List<CloudKnowledge> items = cloudKnowledgeService.searchCloudKnowledge(request, buildRowBoundsPage(page));
        page.setItems(items);
        return page;
	
    }
	
	/**
	 * 
	* @Title: 个人设置 
	* @author yaobo
	* @Description: 
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "customer/personal")
    public String personal(Model model,HttpSession session) {
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		CloudCustomer bean = cloudCustomerService.selectCloudCustomerByID(Long.valueOf(customer.getId()));
	    model.addAttribute("cloudCustomer", bean);
        return "cloud/web/webpage/customer/setting";
    }
	
	/**
	 * 
	* @Title: 密码更新转向 
	* @author yaobo
	* @Description: 
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "customer/passwordupdate")
    public String passwordupdate(Model model,HttpSession session) {
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		model.addAttribute("customer",customer);
        return "cloud/web/webpage/customer/setting-password";
    }
	/**
	 * 
	* @Title: 邮箱更新转向 
	* @author yaobo
	* @Description: 
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "customer/emailupdate")
    public String emailupdate(Model model,HttpSession session) {
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		model.addAttribute("customer",customer);
        return "cloud/web/webpage/customer/setting-email";
    }
	/**
	 * 手机更新转向
	* @Title: phoneupdate 
	* @author yaobo
	* @Description: 
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "customer/phoneupdate")
    public String phoneupdate(Model model,HttpSession session) {
		CloudCustomer customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		model.addAttribute("customer",customer);
        return "cloud/web/webpage/customer/setting-tel";
    }
	
	/**
	 * 
	* @Title: 用户信息保存修改
	* @author yaobo
	* @Description: 
	* @param @param model
	* @param @param customer
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "customer/personalUpdateSave")
    public String passwordUpdatesave(Model model,CloudCustomer customer,HttpSession session) {
		CloudCustomer session_customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		CloudCustomer bean = cloudCustomerService.selectCloudCustomerByID(Long.valueOf(session_customer.getId()));
		if(customer.getPhone()!=null){
			bean.setPhone(customer.getPhone());
		}
		if(customer.getEmail()!=null){
			bean.setEmail(customer.getEmail());
		}
		if(customer.getPassword()!=null){
			bean.setPassword(MobileUtil.getMD5Pass(customer.getPassword()));
		}
		cloudCustomerService.updateCloudCustomer(bean);
        return "redirect:/customer/personal";
    }
	/**
	 * 
	* @Title: evaluationfirst 
	* @author yaobo
	* @Description: 在线评估第一次进入 
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "customer/evaluationfirst")
    public String evaluationfirst(Model model) {
		CloudBasequestionRequest request = new CloudBasequestionRequest();
		List<CloudBasequestion> list = cloudBasequestionService.searchCloudBasequestionAll(request);
		List<CloudBasequestion> type = cloudBasequestionService.searchCloudBasequestionByType(request);
		model.addAttribute("list",list);
		model.addAttribute("type",type);
        return "cloud/web/webpage/customer/assess-first";
    }

	/**
	 * 
	* TODO
	* @author YaoBo
	* @param model
	* @param session
	* @return String
	 */
	@RequestMapping(value = "customer/evaluationSelectFirstOrSecond")
	public String evaluationSelectFirstOrSecond(Model model,HttpSession session) {
		CloudCustomer session_customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		String username = session_customer.getUsername();
		CloudEvaluationRequest req = new CloudEvaluationRequest();
		req.setSubmiter(username);
		//flag=0 23道题
		req.setFlag(0);
		List<CloudEvaluation> list = cloudEvaluationService.searchCloudEvaluationByUUID(req);
		List<CloudEvaluation> type = cloudEvaluationService.searchCloudEvaluationByType(req);
		if(list.size()>0){
			//flag=1  评价s
			req.setFlag(1);
			List<CloudEvaluation> replyList = cloudEvaluationService.searchCloudEvaluationByUUID(req);
			CloudEvaluation ce = replyList.get(0); 
			model.addAttribute("list",list);
			model.addAttribute("type",type);
			model.addAttribute("reply",ce);
			return "cloud/web/webpage/customer/assess-second";
		}
        return "redirect:/customer/evaluationfirst";
	}
	/**
	 * 
	* 在线评估 信息点修改
	* @author YaoBo
	* @param model
	* @param session
	* @return String
	 */
	@RequestMapping(value = "customer/evaluationSecondSave")
	public String evaluationSecondSave(Model model,HttpSession session,CloudPoints points) {
		String[] answers = points.getAnswers();
		List<CloudEvaluation> list = Lists.newArrayList();
		Integer[] ids = points.getIds();
		for(int i=0;i<ids.length;i++){
			 CloudEvaluation ce = new CloudEvaluation();
			 ce.setId(ids[i]);
			 ce.setAnswer(answers[i]);
			 System.out.println(answers[i]);
			 list.add(ce);
		}
		cloudEvaluationService.batchUpdateCloudEvaluation(list);
		return "redirect:/customer/evaluationSelectFirstOrSecond";
	}
	
	
	/**
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * 
	* @Title: evaluationfirstSave 
	* @author yaobo
	* @Description: 在线评估第一次保存 
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "customer/evaluationfirstSave")
    public String evaluationfirstSave(Model model,HttpSession session,CloudPoints cp) throws IllegalArgumentException, IllegalAccessException {
		 List<CloudEvaluation> list = Lists.newArrayList();
		 CloudCustomer session_customer = (CloudCustomer)session.getAttribute(MessageInfo.CUSTOMER);
		 Integer[] pids = cp.getPids();
		 String[] types = cp.getTypes();
		 String[] points = cp.getPoints();
		 String uuid = UUIDUtil.generateUUID();
		 //批量数据组合  23条数据
		 for (int i=0;i<pids.length;i++) {
			 CloudEvaluation ce = new CloudEvaluation();
			 ce.setCustomerid(session_customer.getLinkid());
			 ce.setCustomername(session_customer.getName());
			 ce.setUuid(uuid);
		     ce.setAnswer(points[i]);
			 ce.setFlag(0);
			 ce.setIfreply(0);
			 ce.setSubmiter(session_customer.getUsername());
			 ce.setCid(session_customer.getId());
			 ce.setQid(pids[i]);
			 ce.setType(types[i]);
			 ce.setSubmittime(DateConvertUtils.getDate(DateConvertUtils.DATEFORMAT1));
			 list.add(ce);
		 }
		 //插入评价
		 CloudEvaluation ce = new CloudEvaluation();
		 ce.setCustomerid(session_customer.getLinkid());
		 ce.setCustomername(session_customer.getName());
		 ce.setUuid(uuid);
		 ce.setCid(session_customer.getId());
		 ce.setFlag(1);
		 ce.setIfreply(0);
		 ce.setQid(pids[0]);
		 ce.setSubmiter(session_customer.getUsername());
		 list.add(ce);
		 //批量插入在线评估数据
		cloudEvaluationService.batchinsertCloudEvaluation(list);
        return "redirect:/customer/evaluationSelectFirstOrSecond";
    }
	
}
