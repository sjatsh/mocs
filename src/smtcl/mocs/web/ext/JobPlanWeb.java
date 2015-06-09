package smtcl.mocs.web.ext;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.bean.ManagedProperty;
import javax.servlet.http.HttpServletRequest;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

@Controller
public class JobPlanWeb {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * 设备接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	
	@ManagedProperty("#{commonService1}")
	private ICommonService commonService;
	
	/**
	 * 获取产品名称信息
	 * @param request request
	 * @param partid 零件ID
	 * @param planStatus 作业（批次）计划状态
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 产品名称信息
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/getjobplanName.action")
	public @ResponseBody Map<String, Object> getTestsExtData(HttpServletRequest request,String partid,String planStatus,String startTime,String endTime,String isexpand) throws Exception{
		String nodeid=(String)request.getSession().getAttribute("nodeid");
        Locale locale = SessionHelper.getCurrentLocale(request.getSession());
        return jobPlanService.getAllJobPlanAndPartInfo(nodeid,partid,planStatus,startTime,endTime,isexpand,locale.toString());
	}

    /**
     * 获取作业计划信息
     * @param request request
     * @param ishighlight 是否高亮
     * @param partid 零件ID
     * @param planStatus 作业（批次）计划状态
     * @param startTime 作业（批次）计划开始时间
     * @param endTime 作业（批次）计划结束时间
     * @return 返回查询的作业计划和批次计划
     * @throws Exception
     */
	@RequestMapping(value ="/jobplan/getjobEvent.action")
	@ResponseBody
	public Map<String, Object> getTestsEvent(HttpServletRequest request,
			String ishighlight, String partid, String planStatus,String startTime, String endTime) throws Exception {
        Locale locale = SessionHelper.getCurrentLocale(request.getSession());
		List<Map<String, Object>> tempData = new ArrayList<Map<String, Object>>();
		// 临时数据
		String nodeid = (String) request.getSession().getAttribute("nodeid");
		List<Map<String, Object>> temp = jobPlanService
				.findAllJobPlan(nodeid, partid, planStatus, startTime, endTime);
		if (temp != null && temp.size() > 0) {
            Map<String,String> statusMap = Constants.statusMap;
			for (Map<String, Object> map : temp) {
                if(locale.toString().equals("en") || locale.toString().equals("en_US")){
                    String key = map.get("statusName").toString();
                    map.put("statusName",statusMap.get(key));
                }
				Integer start = Integer.parseInt(map.get("Status").toString());
				map.remove("Start");
                switch (start) {
					case 10:// 创建(点击作业计划，作业计划的初始状态为新建)
						map.put("Cls", "ext_chuangjian");
						break;
					case 20:// 待派工
						map.put("Cls", "ext_daipaigong");
						break;
					case 30:// 已派工
						map.put("Cls", "ext_yipaigong");
						break;
					case 40:// 上线
						map.put("Cls", "ext_shangxian");
						break;
					case 50:// 加工
						map.put("Cls", "ext_jiagong");
						break;
					case 60:// 结束
						map.put("Cls", "ext_jieshu");
						break;
					case 70:// 完成
						map.put("Cls", "ext_wancheng");
						break;
                    case 80://暂停/恢复
                        map.put("Cls", "ext_pause");
                        break;
					default:
						map.put("Cls", "ext_default");
						break;
				}
				tempData.add(map);
			}
		}
		return getListMap(tempData);
	}
	
	/**
	 * 获取作业计划详细
	 * @param p 不用
	 * @return 返回详细信息
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/jobDetail.action")
	public @ResponseBody Map<String, Object> getJobDetail(String p) throws Exception{
        return jobPlanService.findJobPlanDetail(p);
	}
	
	/**
	 * 获取作业计划详细（默认取t_jobplan_info中的ID最大的记录）
	 * @param p 不用
	 * @return 返回详细信息
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/findJobPlanDetailDefault.action")
	public @ResponseBody Map<String, Object> findJobPlanDetailDefault(String p) throws Exception{
        return jobPlanService.findJobPlanDetailDefault();
	}
	
	/**
	 * 添加作业计划
	 */
	@RequestMapping(value="/jobplan/save.action")
	public @ResponseBody Map<String, Object> saveJobplan(String startTime,String startDate,String durationTime,String id,String resourceId,String name,String no,String planId,String planNum,String priority,String planNo,HttpServletRequest request){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			String nodeid=(String)request.getSession().getAttribute("nodeid");
		   
			//获取时间
			Date str = formatDate.parse(startTime);
			
			//获取日期	
			Date sDate = formatDate.parse(startDate);
			
			//拼装日期跟时间
			String temp = StringUtils.formatDate(sDate, 2)+" "+StringUtils.formatDate(str, 5);
			
			//开始时间
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tempDate = format1.parse(temp);			
			Long end = tempDate.getTime()+(Long.parseLong(durationTime)*3600000);			
			Date endDate = new Date(end);
			
			TPartTypeInfo tPartTypeInfo=commonService.get(TPartTypeInfo.class,Long.parseLong(resourceId));
			TJobplanInfo jobplanInfo=new TJobplanInfo();
			jobplanInfo.setTPartTypeInfo(tPartTypeInfo);
			jobplanInfo.setNo(no);
			jobplanInfo.setName(name);
			jobplanInfo.setStatus(10);//创建状态
			jobplanInfo.setPlanEndtime(endDate);
			jobplanInfo.setPlanStarttime(tempDate);
			if(StringUtils.isEmpty(planNum)) planNum="-1";
			jobplanInfo.setPlanNum(Integer.parseInt(planNum));
			jobplanInfo.setPriority(Integer.parseInt(priority));
			jobplanInfo.setNodeid(nodeid);
			jobplanInfo.setFinishNum(0);
			jobplanInfo.setQualifiedNum(0);
			jobplanInfo.setPlanNo(planNo); //生产计划编号

			jobPlanService.addJobPlanInfo(jobplanInfo);
			
		  } catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	/**
	 * 修改作业计划
	 */
	@RequestMapping(value="/jobplan/updateBasicJobPlanInfo.action")
	public @ResponseBody Map<String, Object> updateBasicJobPlanInfo(String startTime,String startDate,String durationTime,String id,String resourceId,String name,String no,String planNum,String planNo,String priority,HttpServletRequest request){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			String nodeid=(String)request.getSession().getAttribute("nodeid");
		    //获取时间
			Date str = formatDate.parse(startTime);
			//获取日期
			Date sDate = formatDate.parse(startDate);
			//拼装日期跟时间
			String temp = StringUtils.formatDate(sDate, 2)+" "+StringUtils.formatDate(str, 5);
			//开始时间
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tempDate = format1.parse(temp);			
			Long end = tempDate.getTime()+(Long.parseLong(durationTime)*3600000);			
			Date endDate = new Date(end);			
			Long planId= (long) 0;
			if(!StringUtils.isEmpty(id)) planId=Long.parseLong(id);			
			TJobplanInfo jobplanInfo=commonService.get(TJobplanInfo.class,planId);//更新和添加的最大的区别
			
			jobplanInfo.setNo(no);
			jobplanInfo.setName(name);
			jobplanInfo.setPlanEndtime(endDate);
			jobplanInfo.setPlanStarttime(tempDate);
			if(StringUtils.isEmpty(planNum)) planNum="-1";
			jobplanInfo.setPlanNum(Integer.parseInt(planNum));
			jobplanInfo.setPriority(Integer.parseInt(priority));
			jobplanInfo.setPlanNo(planNo); //生产计划编号
			jobplanInfo.setNodeid(nodeid);
			jobPlanService.updateJobPlanInfo(jobplanInfo);
			
		  } catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	
	}
	
	
	/**
	 * 修改作业计划
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param id 作业（批次）计划ID
	 * @param resourceId resourceID
	 */
	@RequestMapping(value="/jobplan/update.action")
	public @ResponseBody Map<String, Object> updateJobplan(String startTime ,String endTime , String id,String resourceId,String statusFlag){
		try {
			if(startTime!=null && endTime!=null && id!=null){
				TJobplanInfo tJobplanInfo = jobPlanService.geTJobplanInfoById(Long.parseLong(id));
				if(statusFlag.equals("40") || statusFlag.equals("50"))
				{
					tJobplanInfo.setPlanEndtime(format.parse(endTime));
				}
				else {
					tJobplanInfo.setPlanStarttime(format.parse(startTime));
					tJobplanInfo.setPlanEndtime(format.parse(endTime));
				}
				if(resourceId!=null){
					TPartTypeInfo tPartTypeInfo = commonService.get(TPartTypeInfo.class,Long.valueOf(resourceId));
					tJobplanInfo.setTPartTypeInfo(tPartTypeInfo);
				}
				jobPlanService.updateJobPlanInfo(tJobplanInfo);
				return getMap(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getModelMapError("数据异常!");
		} 
		return getModelMapError("参数有误!");
	}
	
	/**
	 * 拆分预留方法
	 * @param id 作业计划ID
	 * @param time 结束时间
	 */
	@RequestMapping(value="/jobplan/split.action")
	public void SplitJobPlan(String id,String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			TJobplanInfo tJobplanInfo = jobPlanService.geTJobplanInfoById(Long.parseLong(id));
			Date tempEndDate = tJobplanInfo.getPlanEndtime();
			
			//进行修改操作
			tJobplanInfo.setPlanEndtime(format.parse(time));
			jobPlanService.updateJobPlanInfo(tJobplanInfo);
			
			//进行创建操作
			tJobplanInfo.setId(null);
			
			String tempNo = tJobplanInfo.getNo();
			
			//作业计划No字段需处理
			tJobplanInfo.setNo(tempNo);
			tJobplanInfo.setPlanStarttime(format.parse(time));
			tJobplanInfo.setPlanEndtime(tempEndDate);
			jobPlanService.addJobPlanInfo(tJobplanInfo);
			System.out.println("success！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除作业计划
	 * @param id 作业(批次)计划ID
	 * @return 返回操作信息
	 */
	@RequestMapping(value="/jobplan/delete.action")
	public @ResponseBody Map<String, Object> deleteJobplan(String id){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		try {
			TJobplanInfo tJobplanInfo = jobPlanService.geTJobplanInfoById1(Long.parseLong(id));
			if(tJobplanInfo==null) {
				modelMap.put("data", "");
				modelMap.put("success", false);
			}else{
				jobPlanService.deleteJobPlanInfoById(tJobplanInfo);
				modelMap.put("data", "");
				modelMap.put("success", true);
			}
		} catch (NumberFormatException e) {
			return getModelMapError("操作失败!");
		}
		return modelMap;
	}
	
	
	/**
	 * 获取作业计划表中当前最大的ID
	 */
	@RequestMapping(value="/jobplan/getMaxJobPlanInfoId.action")
	public @ResponseBody String getMaxJobPlanInfoId(){
		try{
            return jobPlanService.getMaxJobPlanInfoId();
		} catch (Exception ignored) {
			
		}
		
		return null;
	}
	
	private Map<String,Object> getListMap1(List<Map<String, Object>> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("total", tempMap.size());
		modelMap.put("root", tempMap);
		return modelMap;
	}
	
	private Map<String,Object> getListMap(List<Map<String, Object>> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//modelMap.put("total", tempMap.size());
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
	
	/**
	 * 获取从属生产计划名称
	 */
	@RequestMapping(value="/jobplan/getSubordinatePlanInfoMap.action")
	public @ResponseBody Map<String, Object> getSubordinatePlanInfo(HttpServletRequest request){
		try {
			String nodeid=(String)request.getSession().getAttribute("nodeid");
			List<Map<String, Object>> tJobplanInfo = jobPlanService.getSubordinatePlanInfoMap(nodeid);
			return getListMap1(tJobplanInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * 获取优先级
	 */
	@RequestMapping(value="/jobplan/getPriority.action")
	public @ResponseBody Map<String, Object> getPriorityInfo(){
		try {
			List<Map<String,Object>> lst = jobPlanService.getPriority();		
			return getListMap1(lst);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * 获取设备资源信息
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/updateJobPlanInfoStatus.action")
	public @ResponseBody Boolean updateJobdispatchStatus(String jobPlanId,String status) throws Exception{
		try
		{
			jobPlanService.updateJobPlanInfoStatus(jobPlanId,status);
	        return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 得到作业计划编号
	 */
	@RequestMapping(value ="/jobplan/getJobplanNo.action")
	public @ResponseBody String getJobplanNo(String partTypeId){
		String no="";
		List<Map<String,Object>> partlst =  jobPlanService.getPartTypeByIdFor(partTypeId);
		String maxId  = jobPlanService.getMaxJobPlanId();
		if(partlst.size()>0){
			Map<String,Object> m = partlst.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dd  = sdf.format(new Date());
			no ="WP_"+m.get("bno")+"_"+dd+"_"+maxId;
		}
		return no;
	}
	
/*-----------------------------------------------------------------------------------------------------*/
//	public IJobPlanService getJobPlanService() {
//		return jobPlanService;
//	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
//
//	public void setJobPlanService(IJobPlanService jobPlanService) {
//		this.jobPlanService = jobPlanService;
//	}
	
	
}
