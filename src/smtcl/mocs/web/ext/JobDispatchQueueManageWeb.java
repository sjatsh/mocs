package smtcl.mocs.web.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 工单队列管理Bean
 * @作者：yyh
 * @创建时间：2013-7-2 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="JobDispatchQueueManage")
@ViewScoped
public class JobDispatchQueueManageWeb implements Serializable {
	
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * 从属作业计划ID
	 */
	private String jobPlanId;
	/**
	 * 从属作业计划集合
	 */
	private Map<String,Object> jobPlanMap = new HashMap<String,Object>();
	/**
	 * 设备ID
	 */
	private String partId;
	/**
	 * 设备集合
	 */
	private Map<String,Object> partMap = new HashMap<String,Object>();
	/**
	 * 工单范围开始时间
	 */
	private Date dispatchStartTime;
	/**
	 * 工单范围结束时间
	 */
	private Date dispatchEndTime;
	/**
	 * 作业计划概要 - 作业计划ID
	 */
	private String jobplanid;
	/**
	 * 作业计划概要 - 作业计划进度
	 */
	private String schedule;
	/**
	 * 作业计划概要 - 生产计划ID
	 */
	private String producePlanId;
	/**
	 * 作业计划概要 - 零件类型ID
	 */
	private String partTypeId;
	/**
	 * 作业计划概要 - 作业名称
	 */
	private String jobName;
	/**
	 * 作业计划概要 - 计划产量
	 */
	private String planNum;
	/**
	 * 作业计划概要 - 开始时间
	 */
	private String startTime;
	/**
	 * 作业计划概要 - 结束时间
	 */
	private String endTime;
	/**
	 * 作业计划概要 - 优先级
	 */
	private String priority;
	/**
	 * 作业计划概要 - 完成数量
	 */
	private String finishNum;
	/**
	 * 待分配作业集合
	 */
	private List<Map<String, Object>> waitJobResults = new ArrayList<Map<String, Object>>();
	/**
	 * 已分配工单清单
	 */
	private List<Map<String, Object>> dispatchFinishResults = new ArrayList<Map<String, Object>>();
	/**
	 * 工单清单
	 */
	private List<Map<String, Object>> dispatchResults = new ArrayList<Map<String, Object>>();
	
	
	/**
	 * 构造
	 */
	public JobDispatchQueueManageWeb(){
		
	}
	/**
	 * 作业计划ID
	 */
	String jobplanId;
	/**
	 * 作业计划详情
	 */
	public void getJonPlanDetail(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//		jobplanId = (String)session.getAttribute("");  //要从这里得到
		jobplanId = "";
		List<Map<String, Object>> lst = jobPlanService.getJobPlanDetail(jobplanId); //作业计划详情
		if(lst.size()>0){
			Map<String, Object> map = lst.get(0);
			if(map.get("jobplanid")!=null){
				jobplanid	 = map.get("jobplanid").toString();
			}
			if(map.get("process")!=null){
				schedule	 = map.get("process").toString();
			}
			if(map.get("userProdPlanId")!=null){
				producePlanId	 = map.get("userProdPlanId").toString();
			}
			if(map.get("parttypeid")!=null){
				partTypeId	 = map.get("parttypeid").toString();
			}
			
			if(map.get("name")!=null){
			jobName	 = (String)map.get("name");
			}
			if(map.get("planNum")!=null){
			planNum = map.get("planNum").toString();
			}
			if(map.get("planStarttime")!=null){
			startTime = (String)map.get("planStarttime");
			}
			if(map.get("planEndtime")!=null){
			endTime = (String)map.get("planEndtime");
			}
			if(map.get("priority")!=null){
			priority = map.get("priority").toString();
			}
			if(map.get("finishNum")!=null){
			finishNum = map.get("finishNum").toString();
			}
		}	
	}
	
	
	/**==================================set,get方法====================================================*/


	public String getJobPlanId() {
		return jobPlanId;
	}

	public void setJobPlanId(String jobPlanId) {
		this.jobPlanId = jobPlanId;
	}

	public Map<String, Object> getJobPlanMap() {
		return jobPlanMap;
	}

	public void setJobPlanMap(Map<String, Object> jobPlanMap) {
		this.jobPlanMap = jobPlanMap;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public Map<String, Object> getPartMap() {
		return partMap;
	}

	public void setPartMap(Map<String, Object> partMap) {
		this.partMap = partMap;
	}

	public Date getDispatchStartTime() {
		return dispatchStartTime;
	}

	public void setDispatchStartTime(Date dispatchStartTime) {
		this.dispatchStartTime = dispatchStartTime;
	}

	public Date getDispatchEndTime() {
		return dispatchEndTime;
	}

	public void setDispatchEndTime(Date dispatchEndTime) {
		this.dispatchEndTime = dispatchEndTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(String finishNum) {
		this.finishNum = finishNum;
	}

	public List<Map<String, Object>> getWaitJobResults() {		
		return waitJobResults;
	}

	public void setWaitJobResults(List<Map<String, Object>> waitJobResults) {
		this.waitJobResults = waitJobResults;
	}

	public List<Map<String, Object>> getDispatchFinishResults() {
		//获取节点ID
				HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				String nodeid = (String)session.getAttribute("nodeid");
				
				/*
				waitJobResults = jobPlanService.getWaitJobList(nodeid);//待分配作业
				for(Map<String, Object> map : waitJobResults){
					if(map.get("name")==null){
						map.put("name", "");
					}
					if(map.get("planNum")==null){
						map.put("planNum", 0);
					}
					if(StringUtils.isEmpty((String)map.get("planStarttime"))){
						map.put("planStarttime", "");
					}
				    if(StringUtils.isEmpty((String)map.get("planEndtime"))){
						map.put("planEndtime", "");
					}
				    
				    if(map.get("name")!=null && !"".equals(map.get("name"))){
				    	map.put("name", StringUtils.getSubString((String)map.get("name"),"1"));   //截取字段
				    }
				}	
				
				if(jobplanId == null){
				if(waitJobResults.size()>0){                    //作业计划ID,默认第一个
					Map<String, Object> map = waitJobResults.get(0);
					if(map.get("jobplanid")!=null){
					jobplanId = map.get("jobplanid").toString();
					}
				}
				}
				jobplanId = "1";  //测试，默认给个ID
*/
				dispatchFinishResults = jobPlanService.getAlreadyDispatchList(nodeid); //已分配工单
				for(Map<String, Object> map : dispatchFinishResults){
					if(map.get("no")==null){
						map.put("no", "");
					}
					if(map.get("name")==null){
						map.put("name", "");
					}
					if(map.get("processNum")==null){
						map.put("processNum", 0);
					}
					
					if(StringUtils.isEmpty((String)map.get("planStarttime"))){
						map.put("planStarttime", "");
					}
					if(StringUtils.isEmpty((String)map.get("planEndtime"))){
						map.put("planEndtime", "");
					}
					if(map.get("finishNum")==null){
						map.put("finishNum", 0);
					}
					
				    if(map.get("no")!=null && !"".equals(map.get("no"))){
				    	map.put("no", StringUtils.getSubString((String)map.get("no"),"4"));   //截取字段
				    }
				    /*if(map.get("name")!=null && !"".equals(map.get("name"))){
				    	map.put("name", StringUtils.getSubString((String)map.get("name"),"1"));   //截取字段
				    }*/
				    /*if(map.get("jobno")!=null && !"".equals(map.get("jobno"))){
				    	map.put("jobno", StringUtils.getSubString((String)map.get("jobno"),"6"));   //截取字段
				    }*/
				}
				
			/*	
				List<Map<String, Object>> lst = jobPlanService.getJobPlanDetail(jobplanId); //作业计划详情  ===>没有使用到，ext中实现了
				if(lst.size()>0){
					Map<String, Object> map = lst.get(0);
					if(map.get("jobplanid")!=null){
						jobplanid	 = map.get("jobplanid").toString();
					}
					if(map.get("process")!=null){
						schedule	 = map.get("process").toString();
					}
					if(map.get("userProdPlanId")!=null){
						producePlanId	 = map.get("userProdPlanId").toString();
					}
					if(map.get("parttypeid")!=null){
						partTypeId	 = map.get("parttypeid").toString();
					}
					
					if(map.get("name")!=null){
					jobName	 = (String)map.get("name");
					}
					if(map.get("planNum")!=null){
					planNum = map.get("planNum").toString();
					}
					if(map.get("planStarttime")!=null){
					startTime = (String)map.get("planStarttime");
					}
					if(map.get("planEndtime")!=null){
					endTime = (String)map.get("planEndtime");
					}
					if(map.get("priority")!=null){
					priority = map.get("priority").toString();
					}
					if(map.get("finishNum")!=null){
					finishNum = map.get("finishNum").toString();
					}
				}
				*/
		return dispatchFinishResults;
	}

	public void setDispatchFinishResults(
			List<Map<String, Object>> dispatchFinishResults) {
		this.dispatchFinishResults = dispatchFinishResults;
	}

	public List<Map<String, Object>> getDispatchResults() {
		return dispatchResults;
	}

	public void setDispatchResults(List<Map<String, Object>> dispatchResults) {
		this.dispatchResults = dispatchResults;
	}

	public IJobPlanService getJobPlanService() {
		return jobPlanService;
	}

	public void setJobPlanService(IJobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
	}

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public String getJobplanid() {
		return jobplanid;
	}

	public void setJobplanid(String jobplanid) {
		this.jobplanid = jobplanid;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getProducePlanId() {
		return producePlanId;
	}

	public void setProducePlanId(String producePlanId) {
		this.producePlanId = producePlanId;
	}

	public String getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public String getJobplanId() {
		return jobplanId;
	}

	public void setJobplanId(String jobplanId) {
		this.jobplanId = jobplanId;
	}		
	
}
