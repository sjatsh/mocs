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
 * ����ͳ��Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-7-22 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="JobDispathStatistics")
@ViewScoped
public class JobDispathStatisticsWeb implements Serializable {
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * �����
	 */
	private String taskNum;
	/**
	 * ��ҵ�ƻ�����
	 */
	private Map<String,Object> jobPlanMap = new HashMap<String,Object>();
	/**
	 * ��ҵ�ƻ���Ҫ - ��ʼʱ��
	 */
	private Date startTime;
	/**
	 * ��ҵ�ƻ���Ҫ - ����ʱ��
	 */
	private Date endTime;
	/**
	 * ����ID,��nood
	 */
	private String foctoryId;
	/**
	 * ���伯��
	 */
	private Map<String,Object> foctoryMap = new HashMap<String,Object>();
	/**
	 * �����嵥
	 */
	private List<Map<String, Object>> dispatchResults = new ArrayList<Map<String, Object>>();	
	
	private String nodeid=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nodeid");
	/**
	 * ����
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
		    	map.put("dispatchno", StringUtils.getSubString((String)map.get("dispatchno"),"1"));   //��ȡ�ֶ�
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
			if( Math.abs(abab) > 7 && status.equals("10")){ //ʱ�����7�죬��״̬Ϊ����
				map.put("yujing","Ч��Ԥ��");
			}
		}
	}
	
	/**
	 * ͨ����ҵ�ƻ�ID�õ�����,ajaxһ��Ҫ��head��body��������
	 */
	public void dispathByPlanId(){
		dispatchResults = jobPlanService.getJobdispatchlistByIdAndTime(nodeid,taskNum,startTime,endTime);	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String,Object> map : dispatchResults){
		    if(map.get("dispatchno")!=null && !"".equals(map.get("dispatchno"))){
		    	map.put("dispatchno", StringUtils.getSubString((String)map.get("dispatchno"),"1"));   //��ȡ�ֶ�
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
			if( Math.abs(abab) >7 && status.equals("10")){ //ʱ�����7�죬��״̬Ϊ����
				map.put("yujing","Ч��Ԥ��");
			}
		}
	}
	
	
	/**==================================set,get����====================================================*/

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
