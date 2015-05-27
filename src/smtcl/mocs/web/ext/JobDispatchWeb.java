package smtcl.mocs.web.ext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;
import javax.servlet.http.HttpServletRequest;







import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.services.jobplan.UpdataJobDispatch;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class JobDispatchWeb {

	private  Gson gson = new GsonBuilder().serializeNulls().create();
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private UpdataJobDispatch updataJobDispat = (UpdataJobDispatch)ServiceFactory.getBean("updataJobDispatch");
	/**
	 * �����ӿ�ʵ��
	 */
	@ManagedProperty("#{jobDispatchService}")
	private IJobDispatchService jobDispatchService;
	
	@ManagedProperty("#{commonService1}")
	private ICommonService commonService;
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	
	/**
	 * ��ȡ�豸��Դ��Ϣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/getDevicesList.action")
	public @ResponseBody Map<String, ? extends Object> getTestsExtData(HttpServletRequest request,String taskNum,String jobstatus, String partid,String equid,String planStime,String planEtime) throws Exception{
		String nodeid=(String)request.getSession().getAttribute("nodeid");
		List<Map<String, Object>> tempData = (List<Map<String,Object>>) jobDispatchService.getDevicesInfo(nodeid,formatTaskNum(taskNum),jobstatus, partid,equid, planStime, planEtime);
		List<Map<String, Object>> newData = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : tempData){
			Map<String,Object> newMap = new HashMap<String,Object>();
			newMap.put("Id", map.get("Id").toString());
			String name = map.get("equName").toString()+"("+map.get("Name").toString()+")";
			newMap.put("Name", name);
			newData.add(newMap);
		}
		return getListMap(newData);
	}

	/**
	 * ��ʽtaskNum
	 * @param taskNum
	 * @return
	 */
	private String formatTaskNum(String taskNum) {
		if(!StringUtils.isEmpty(taskNum)){
			String str[] = taskNum.split(",");
			StringBuilder sb = new StringBuilder();
			for(String val : str){
				sb.append("'").append(val).append("'").append(",");
			}
			return sb.toString().substring(0, sb.toString().length()-1);
		}
		return null;
	}
	
	@RequestMapping(value ="/jobdispatch/checkDispathById.action")
	public @ResponseBody Map<String, Object> checkDispatchById(String dispatchNo){
		Map<String,Object> map = new HashMap<String,Object>();
		List<TEquJobDispatch> mapList = jobDispatchService.getJobDispatchById(dispatchNo);
		boolean flag = false;
		StringBuffer sb = new StringBuffer("");
		for(TEquJobDispatch t : mapList){
			sb.append(t.getEquSerialNo()).append(",");
		}
		map.put("equSerialNo", sb.toString());
		if(mapList.size()>0)
			flag = true;
		map.put("flag", flag);
		return map;
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/getjobDispatchList.action")
	public @ResponseBody Map<String, ? extends Object> getTestsEvent(String taskNum,String jobstatus, String partid,String equid,String planStime,String planEtime,HttpServletRequest request,String ishighlight) throws Exception{
		String nodeid=(String)request.getSession().getAttribute("nodeid");
		List<Map<String, Object>> tempData = new ArrayList<Map<String,Object>>();
		
		String fs=StringUtils.getCookie(request, "fs");
		String fe=StringUtils.getCookie(request, "fe");
		
		if(null!=fs&&!"".equals(fs)){
			planStime=fs;
		}
		if(null!=fe&&!"".equals(fe)){
			planEtime=fe;
		}
		
		List<Map<String, Object>> temp = (List<Map<String,Object>>) jobDispatchService.getJobDispatchsInfo(nodeid,formatTaskNum(taskNum),jobstatus,partid,equid,planStime,planEtime);
		long maxId=0;
		if(temp.size()>0 && temp!=null){
			for(Map<String, Object> map : temp){
				if(map==null ||map.get("Status")==null)
					continue;
			    Integer start = Integer.parseInt(map.get("Status").toString());
			    
			    map.put("StatusName", this.returnStatus(start));
			    if(maxId==0) maxId=Long.parseLong(map.get("Id").toString());	//��ȡ���ֵ
				map.remove("Start");
				switch (start) {
				case 30://����
					if(maxId==((Long)map.get("Id")).longValue())
						if(ishighlight!=null)
						{
					        map.put("Cls", "ext_chuangjian_maxId");
						}
					    else
					    {
					    	map.put("Cls", "ext_chuangjian");
					    }
					else {
					map.put("Cls", "ext_chuangjian");
					}
					break;
				case 40://����
					map.put("Cls", "ext_shangxian");
					break;
				case 50://�ӹ�
					map.put("Cls", "ext_jiagong");
					break;
				case 60://����
					map.put("Cls", "ext_jieshu");
					break;
				case 70://���
					map.put("Cls", "ext_wancheng");
					break;
				case 80://��ͣ/�ָ�
					map.put("Cls", "ext_pause");
					break;
				default:
					map.put("Cls", "ext_defult");
					break;
			}
				tempData.add(map);
			}
		}
		return getListMap(tempData);
	}
	
	public String returnStatus(int status){
		if(20 == status){
			return "���ɹ�";
		}else if(30==status){
			return "���ɹ�";
		}else if(40==status){
			return "����";
		}else if(50==status){
			return "�ӹ�";
		}else if(60==status){
			return "����";
		}else if(70==status){
			return "���";
		}else if(80 == status){
			return "��ͣ";
		}else{
			return "";
		}
	}
	
	/**
	 * ��ȡһ��������Ϣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/getJobDispatchsInfoOne.action")
	public @ResponseBody Map<String, ? extends Object> getJobDispatchsInfoOne(String jobdispatchId) throws Exception{
	
		List<Map<String, Object>> tempData = new ArrayList<Map<String,Object>>();
		//��ʱ����
		//List<Map<String, Object>> temp = (List<Map<String,Object>>) jobDispatchService.getJobDispatchsInfo("8a8abc973f1dc2dc013f1dc3d7dc0001","");
		List<Map<String, Object>> temp = (List<Map<String,Object>>) jobDispatchService.getJobDispatchsInfoOne(jobdispatchId);
		System.out.println(gson.toJson(temp));
		long maxId=0;
		if(temp.size()>0 && temp!=null){
			for(Map<String, Object> map : temp){
			    Integer start = Integer.parseInt(map.get("Status").toString());
			    if(maxId==0) maxId=Long.parseLong(map.get("Id").toString());	//��ȡ���ֵ
				map.remove("Start");
				
				switch (start) {
					/*case 10://����
						map.put("Cls", "ext_chuangjian");
						break;*/
					case 20://���ɹ�
						map.put("Cls", "ext_daipaigong");
						break;
					case 30://���ɹ�
						//map.put("Cls", "ext_yipaigong");
						map.put("Cls", "ext_chuangjian");
						break;
					case 40://����
						map.put("Cls", "ext_shangxian");
						break;
					case 50://�ӹ�
						map.put("Cls", "ext_jiagong");
						break;
					case 60://����
						map.put("Cls", "ext_jieshu");
						break;
					case 70://���
						map.put("Cls", "ext_wancheng");
						break;
					case 80://��ͣ/�ָ�
						map.put("Cls", "ext_pause");
						break;
					default:
						map.put("Cls", "ext_defult");
						break;
				}
				tempData.add(map);
			}
		}
		return getListMap(tempData);
	}
	
	/**
	 * ������󹤵���Ų�����Ӧ����ҵ�ƻ�
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/findJobPlanDetailDefaultBYDispatchIdDefault.action")
	public @ResponseBody Map<String, ? extends Object> getJobplanDetail() throws Exception{
		Map<String, Object> tempData= jobDispatchService.findJobPlanDetailDefaultBYDispatchIdDefault();
		return tempData;
	}
	
	/**
	 * ��ȡ��ҵ�ƻ���ϸ
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/jobplanDetail.action")
	public @ResponseBody Map<String, ? extends Object> getJobplanDetail(String p) throws Exception{
		Map<String, Object> tempData= jobDispatchService.getJobPlanDetail(p);
		return tempData;
	}
	
	/**
	 * ��ӹ���
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/save.action")
	public @ResponseBody Map<String, ? extends Object> saveJobdispatch(String startTime,String startDate,String durationTime,String id,String resourceId,String name,String no,String num,String theoryWorktime,HttpServletRequest request){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			
			//��ȡ�û���Ϣ
			TUser userinfo=(TUser)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
			
			//��ȡʱ��
			Date str = formatDate.parse(startTime);
			
			//��ȡ����	
			Date sDate = formatDate.parse(startDate);
			
			//ƴװ���ڸ�ʱ��
			String temp = StringUtils.formatDate(sDate, 2)+" "+StringUtils.formatDate(str, 5);
			
			//��ʼʱ��
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tempDate = format1.parse(temp);			
			Long end = tempDate.getTime()+(Long.parseLong(durationTime)*3600000);			
			Date endDate = new Date(end);
			Date createDate=format1.parse(format1.format(new Date()));
			
			//TEquipmentInfo tEquipmentInfo=commonService.get(TEquipmentInfo.class,Long.parseLong(resourceId));
			
			TJobdispatchlistInfo jobdispatchlistInfo=new TJobdispatchlistInfo();
			
			//if(StringUtils.isEmpty(jobId)) jobId="-1";
			//TJobInfo tJobInfo=commonService.get(TJobInfo.class,Long.parseLong(jobId));
			jobdispatchlistInfo.setName(name);
			jobdispatchlistInfo.setNo(no);
			//jobdispatchlistInfo.setStatus(10);//����״̬
			jobdispatchlistInfo.setStatus(30);//����״̬
			jobdispatchlistInfo.setPlanEndtime(endDate);
			jobdispatchlistInfo.setPlanStarttime(tempDate);
			//jobdispatchlistInfo.setTEquipmentInfo(tEquipmentInfo);            
			//jobdispatchlistInfo.setTJobInfo(tJobInfo);
			//if(tJobInfo!=null) jobdispatchlistInfo.setJobplanId(tJobInfo.getTJobplanInfo().getId());
			if(StringUtils.isEmpty(num)){
				num="-1";
			} 
			jobdispatchlistInfo.setProcessNum(Integer.parseInt(num));
			jobdispatchlistInfo.setCreateDate(createDate);
			jobdispatchlistInfo.setIsAutoStart(0);//0��������
			jobdispatchlistInfo.setCreator(userinfo.getLoginName());
			jobdispatchlistInfo.setTheoryWorktime(Integer.parseInt(theoryWorktime));//���۹�ʱ
			jobdispatchlistInfo.setFinishNum(0);			
			
			jobDispatchService.addJobDispatchInfo(jobdispatchlistInfo);
			
		    /*���д���Ϊ������ҵ����ҵ�ƻ����״̬*/
			//������ҵ״̬Ϊ���ɹ�������һ��������¼ʱ
			//jobDispatchService.updateJobInfoStatusWhenAddJobPlan(jobId,"30");
		    //ͨ����ҵ���jobplanid�ֶβ����Ӧ��status�ֶ�
			//List<Map<String, Object>> statusList = jobDispatchService.getStatusByJobPlanId(tJobInfo.getTJobplanInfo().getId(),20);
			//System.out.println(gson.toJson(statusList));
	        /*if(statusList.size()>0)
	        {
	        	jobDispatchService.updateJobPlanInfoStatusByjobStatus(Long.toString(tJobInfo.getTJobplanInfo().getId()),"20");	
	        }
	        else
	        {*/
	        	//jobDispatchService.updateJobPlanInfoStatusByjobStatus(Long.toString(tJobInfo.getTJobplanInfo().getId()),"","30","new");	
	        
	        //}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	
	@RequestMapping(value="/jobdispatch/updateBasicJobdispatch.action")
	public @ResponseBody Map<String, ? extends Object> updateBasicJobPlanInfo(String startTime,String startDate,String durationTime,String id,String resourceId,String name,String no,String num,HttpServletRequest request){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			String nodeid=(String)request.getSession().getAttribute("nodeid");
		    //��ȡʱ��
			Date str = formatDate.parse(startTime);
			
			//��ȡ����	
			Date sDate = formatDate.parse(startDate);
			
			//ƴװ���ڸ�ʱ��
			String temp = StringUtils.formatDate(sDate, 2)+" "+StringUtils.formatDate(str, 5);
			
			//��ʼʱ��
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tempDate = format1.parse(temp);		
			Long end = tempDate.getTime()+(Long.parseLong(durationTime)*3600000);	
			Date endDate = new Date(end);
			TJobdispatchlistInfo tJobdispatchlistInfo = commonService.get(TJobdispatchlistInfo.class,Long.valueOf(id));
			
			tJobdispatchlistInfo.setNo(no);
			tJobdispatchlistInfo.setName(name);
			tJobdispatchlistInfo.setPlanEndtime(endDate);
			tJobdispatchlistInfo.setPlanStarttime(tempDate);
			if(StringUtils.isEmpty(num)) num="-1";
			tJobdispatchlistInfo.setProcessNum(Integer.parseInt(num));
			tJobdispatchlistInfo.setNodeid(nodeid);
			jobDispatchService.updateJobDispatchInfo(tJobdispatchlistInfo);
			
		  } catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	
	
	/**
	 * �޸Ĺ���
	 * @param startTime
	 * @param endTime
	 * @param id
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/update.action")
	public @ResponseBody Map<String, ? extends Object> updateJobdispatch(String startTime ,String endTime,String id,String resourceId,String statusId,String num){
		try {
			if(startTime!=null && endTime!=null && id!=null){
				TJobdispatchlistInfo tJobdispatchlistInfo = commonService.get(TJobdispatchlistInfo.class,Long.valueOf(id));
				if(statusId=="40"||statusId=="50")
				{
					tJobdispatchlistInfo.setPlanEndtime(format.parse(endTime));
			    }
				else {
					tJobdispatchlistInfo.setPlanStarttime(format.parse(startTime));
					tJobdispatchlistInfo.setPlanEndtime(format.parse(endTime));
				}
				if(resourceId!=null){
					TEquipmentInfo tEquipmentInfo = commonService.get(TEquipmentInfo.class,Long.valueOf(resourceId));
					tJobdispatchlistInfo.setTEquipmentInfo(tEquipmentInfo);
				}
				if(StringUtils.isEmpty(num)){
					num="-1";
				}
				tJobdispatchlistInfo.setProcessNum(Integer.parseInt(num));
				jobDispatchService.updateJobDispatchInfo(tJobdispatchlistInfo);
				return getMap(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getModelMapError("�����쳣!");
		} 
		return getModelMapError("��������!");
	}
	
	/**
	 * ���Ԥ������
	 * @param id
	 * @param time
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/split.action")
	public @ResponseBody Map<String, Object> SplitJobPlan(String id,String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			TJobdispatchlistInfo tJobdispatchlistInfo = commonService.get(TJobdispatchlistInfo.class,Long.parseLong(id));
			Date tempEndDate = tJobdispatchlistInfo.getPlanEndtime();
			
			//�����޸Ĳ���
			tJobdispatchlistInfo.setPlanEndtime(format.parse(time));
			jobDispatchService.updateJobDispatchInfo(tJobdispatchlistInfo);
			
			
			//���д�������
			TJobdispatchlistInfo addJobdispatchlistInfo=new TJobdispatchlistInfo();
			
			String tempNo = tJobdispatchlistInfo.getNo()+"-1";
			
			//����No�ֶ��账��
			addJobdispatchlistInfo.setNo(tempNo);			
			addJobdispatchlistInfo.setName(tJobdispatchlistInfo.getName()+"-1");
			addJobdispatchlistInfo.setPlanStarttime(format.parse(time));
			addJobdispatchlistInfo.setPlanEndtime(tempEndDate);
			addJobdispatchlistInfo.setStatus(10);
			addJobdispatchlistInfo.setTEquipmentInfo(tJobdispatchlistInfo.getTEquipmentInfo());
			jobDispatchService.addJobDispatchInfo(addJobdispatchlistInfo);
			System.out.println("success!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * ɾ��������Ϣ
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/delete.action")
	public @ResponseBody Map<String, ? extends Object> deleteJobplan(String dispatchId){
		try {
			TJobdispatchlistInfo tJobdispatchlistInfo = commonService.get(TJobdispatchlistInfo.class,Long.parseLong(dispatchId));
			if(tJobdispatchlistInfo!=null) jobDispatchService.deleteJobDispatchInfo(tJobdispatchlistInfo);		
		} catch (NumberFormatException e) {
			return getModelMapError("����ʧ��!");
		}
		return getMap(null);
	}
	
	/**
	 * ��ȡ�������е�ǰ����ID
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/getMaxJobDispatchId.action")
	public @ResponseBody String getMaxJobPlanInfoId(){
		try{
			String mID=jobDispatchService.getMaxJobDispatchId();
			return mID;
		} catch (Exception e) {
			
		}
		
		return null;
	}

	/**
	 * ��ȡ��ҵ�ƻ������б�
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/getJobplanList.action")
	public @ResponseBody  Map<String, ? extends Object> getJobPlanList(){
		try{
			List<Map<String,Object>> tempData=jobDispatchService.getJobPlanList("8a8abc973f1dc2dc013f1dc3d7dc0001");
			return  getListMap(tempData);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * ��ȡ��ҵ�����б�
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/getJobMap.action")
	public @ResponseBody  Map<String, ? extends Object> getJobMap(HttpServletRequest request){
		try{
			String nodeid=(String)request.getSession().getAttribute("nodeid");
			List<Map<String,Object>> tempData=jobDispatchService.getJobInfoMap(nodeid);
			return  getListMap1(tempData);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * ��ȡ�豸��Դ��Ϣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/updateJobdispatchStatus.action")
	public @ResponseBody Boolean updateJobdispatchStatus(String dispatchId,String status,String flag) throws Exception{
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		String nowDateString=formatDate.format(now);
		try
		{
			jobDispatchService.updateJobdispatchStatus(dispatchId,status,nowDateString,flag);
			
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * ���Ĺ���״̬��ֹͣʱ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/updateJobDispatchWhenStop.action")
	public @ResponseBody Boolean updateJobDispatchWhenStop(String jobdispatchId,String nowDate,String status,String flag) throws Exception{
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		String nowDateString=formatDate.format(now);
		try
		{
			jobDispatchService.updateJobDispatchWhenStop(jobdispatchId, nowDateString, status,flag);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ������ͣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/updateJobDispatchWhenPause.action")
	public @ResponseBody Boolean updateJobDispatchWhenPause(String dispatchId,String status,String flag) throws Exception{
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		String nowDateString=formatDate.format(now);
		try
		{
			jobDispatchService.setStatusToOldstatus(dispatchId,status,flag);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * �����ָ�
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobdispatch/updateJobdispatchWhenRecover.action")
	public @ResponseBody Boolean updateJobdispatchWhenRecover(String dispatchId,String status,String flag) throws Exception{
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		String nowDateString=formatDate.format(now);
		try
		{
			jobDispatchService.updateJobdispatchWhenRecover(dispatchId,status,flag);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	@RequestMapping(value="/jobdispatch/setSessionId.action")
	public @ResponseBody void setSessionId(String sessionId){
		try {
			    String id=sessionId; 
			}
		 catch (Exception e) {
			e.printStackTrace();
			
		} 
		
	}
	
	@RequestMapping(value="/jobdispatch/getDispatchStatusByEquId.action")
	public @ResponseBody boolean getDispatchStatusByEquId(String equId){
		boolean bl=false;
		try {
			   bl=jobDispatchService.getDispatchStatusByEquId(equId);
		    }
		 catch (Exception e) {
			e.printStackTrace();
	    }
		return bl;
	}
	
	private Map<String,Object> getListMap1(List<Map<String, Object>> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("total", tempMap.size());
		modelMap.put("root", tempMap);
		return modelMap;
	}
	
	private Map<String,Object> getListMap(List<Map<String, Object>> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("total", tempMap.size());
		modelMap.put("data", tempMap);
		modelMap.put("success", true);	
		return modelMap;
	}
	
	private Map<String,Object> getMap(Map<String, Object> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("data", tempMap);
		modelMap.put("success", true);
		return modelMap;
	}
	
	private Map<String,Object> getModelMapError(String msg){
		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);
		return modelMap;
	} 

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IJobDispatchService getJobDispatchService() {
		return jobDispatchService;
	}

	public void setJobDispatchService(IJobDispatchService jobDispatchService) {
		this.jobDispatchService = jobDispatchService;
	}
	
}
