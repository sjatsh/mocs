package smtcl.mocs.web.ext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 
 * 工单统计Bean
 * @作者：yyh
 * @创建时间：2013-7-22 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="JobDispathStatistics")
@ViewScoped
public class JobDispathStatisticsWeb implements Serializable {
	
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * 任务号
	 */
	private String taskNum;
	/**
	 * 作业计划集合
	 */
	private Map<String,Object> jobPlanMap = new HashMap<String,Object>();
	/**
	 * 作业计划概要 - 开始时间
	 */
	private Date startTime;
	/**
	 * 作业计划概要 - 结束时间
	 */
	private Date endTime;
	/**
	 * 车间ID,即nood
	 */
	private String foctoryId;
	/**
	 * 车间集合
	 */
	private Map<String,Object> foctoryMap = new HashMap<String,Object>();
	/**
	 * 工单清单
	 */
	private List<Map<String, Object>> dispatchResults = new ArrayList<Map<String, Object>>();	
	
	private String nodeid=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nodeid");
	/**
	 * 构造
	 */
	public JobDispathStatisticsWeb(){		
		List<Map<String,Object>> lst = jobPlanService.getjobdispatchByTaskNum();
		for(Map<String,Object> map : lst){
			if(map.get("taskNum")!=null && !"".equals(map.get("taskNum"))){
				jobPlanMap.put((String)map.get("taskNum"),map.get("taskNum").toString());
			}
		}
		dispatchResults = jobPlanService.getJobdispatchlistByIdAndTime(nodeid,null,null,null);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String,Object> map : dispatchResults){
		    if(map.get("dispatchno")!=null && !"".equals(map.get("dispatchno"))){
		    	map.put("dispatchno", StringUtils.getSubString((String)map.get("dispatchno"),"1"));   //截取字段
		    }
			String a = (String)map.get("planStarttime");
			String b = (String)map.get("realStarttime"); 
			String status = "";
			if(map.get("status")!=null){
				status = map.get("status").toString(); 
			}
			int  abab = 0;
			if(a!=null && b!=null){
				String ab = StringUtils.getBetweenDayNumber(a,b);
				abab = Integer.parseInt(ab);
			}			
			if( Math.abs(abab) > 7 && status.equals("10")){ //时间相差7天，且状态为创建
				map.put("yujing","效期预警");
			}
		}
	}
	
	/**
	 * 通过作业计划ID得到工单,ajax一定要有head和body才起作用
	 */
	public void dispathByPlanId(){
		dispatchResults = jobPlanService.getJobdispatchlistByIdAndTime(nodeid,taskNum,startTime,endTime);	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String,Object> map : dispatchResults){
		    if(map.get("dispatchno")!=null && !"".equals(map.get("dispatchno"))){
		    	map.put("dispatchno", StringUtils.getSubString((String)map.get("dispatchno"),"1"));   //截取字段
		    }
			String a = (String)map.get("planStarttime");
			String b = (String)map.get("realStarttime"); 
			String status = "";
			if(map.get("status")!=null){
			status = map.get("status").toString(); 
			}
			int  abab = 0;
			if(a!=null && b!=null){
			String ab = StringUtils.getBetweenDayNumber(a,b);
			abab = Integer.parseInt(ab);
			}
			if( Math.abs(abab) >7 && status.equals("10")){ //时间相差7天，且状态为创建
				map.put("yujing","效期预警");
			}
		}
	}
	
	
	/**==================================set,get方法====================================================*/

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

	public String getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}

	public Map<String, Object> getJobPlanMap() {
		return jobPlanMap;
	}

	public void setJobPlanMap(Map<String, Object> jobPlanMap) {
		this.jobPlanMap = jobPlanMap;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFoctoryId() {
		return foctoryId;
	}

	public void setFoctoryId(String foctoryId) {
		this.foctoryId = foctoryId;
	}

	public Map<String, Object> getFoctoryMap() {
		return foctoryMap;
	}

	public void setFoctoryMap(Map<String, Object> foctoryMap) {
		this.foctoryMap = foctoryMap;
	}

	public List<Map<String, Object>> getDispatchResults() {
		return dispatchResults;
	}

	public void setDispatchResults(List<Map<String, Object>> dispatchResults) {
		this.dispatchResults = dispatchResults;
	}

}
