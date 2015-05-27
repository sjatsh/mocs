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
 * �������й���Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-7-2 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="JobDispatchQueueManage")
@ViewScoped
public class JobDispatchQueueManageWeb implements Serializable {
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * ������ҵ�ƻ�ID
	 */
	private String jobPlanId;
	/**
	 * ������ҵ�ƻ�����
	 */
	private Map<String,Object> jobPlanMap = new HashMap<String,Object>();
	/**
	 * �豸ID
	 */
	private String partId;
	/**
	 * �豸����
	 */
	private Map<String,Object> partMap = new HashMap<String,Object>();
	/**
	 * ������Χ��ʼʱ��
	 */
	private Date dispatchStartTime;
	/**
	 * ������Χ����ʱ��
	 */
	private Date dispatchEndTime;
	/**
	 * ��ҵ�ƻ���Ҫ - ��ҵ�ƻ�ID
	 */
	private String jobplanid;
	/**
	 * ��ҵ�ƻ���Ҫ - ��ҵ�ƻ�����
	 */
	private String schedule;
	/**
	 * ��ҵ�ƻ���Ҫ - �����ƻ�ID
	 */
	private String producePlanId;
	/**
	 * ��ҵ�ƻ���Ҫ - �������ID
	 */
	private String partTypeId;
	/**
	 * ��ҵ�ƻ���Ҫ - ��ҵ����
	 */
	private String jobName;
	/**
	 * ��ҵ�ƻ���Ҫ - �ƻ�����
	 */
	private String planNum;
	/**
	 * ��ҵ�ƻ���Ҫ - ��ʼʱ��
	 */
	private String startTime;
	/**
	 * ��ҵ�ƻ���Ҫ - ����ʱ��
	 */
	private String endTime;
	/**
	 * ��ҵ�ƻ���Ҫ - ���ȼ�
	 */
	private String priority;
	/**
	 * ��ҵ�ƻ���Ҫ - �������
	 */
	private String finishNum;
	/**
	 * ��������ҵ����
	 */
	private List<Map<String, Object>> waitJobResults = new ArrayList<Map<String, Object>>();
	/**
	 * �ѷ��乤���嵥
	 */
	private List<Map<String, Object>> dispatchFinishResults = new ArrayList<Map<String, Object>>();
	/**
	 * �����嵥
	 */
	private List<Map<String, Object>> dispatchResults = new ArrayList<Map<String, Object>>();
	
	
	/**
	 * ����
	 */
	public JobDispatchQueueManageWeb(){
		
	}
	/**
	 * ��ҵ�ƻ�ID
	 */
	String jobplanId;
	/**
	 * ��ҵ�ƻ�����
	 */
	public void getJonPlanDetail(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//		jobplanId = (String)session.getAttribute("");  //Ҫ������õ�
		jobplanId = "";
		List<Map<String, Object>> lst = jobPlanService.getJobPlanDetail(jobplanId); //��ҵ�ƻ�����
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
	
	
	/**==================================set,get����====================================================*/


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
		//��ȡ�ڵ�ID
				HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				String nodeid = (String)session.getAttribute("nodeid");
				
				/*
				waitJobResults = jobPlanService.getWaitJobList(nodeid);//��������ҵ
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
				    	map.put("name", StringUtils.getSubString((String)map.get("name"),"1"));   //��ȡ�ֶ�
				    }
				}	
				
				if(jobplanId == null){
				if(waitJobResults.size()>0){                    //��ҵ�ƻ�ID,Ĭ�ϵ�һ��
					Map<String, Object> map = waitJobResults.get(0);
					if(map.get("jobplanid")!=null){
					jobplanId = map.get("jobplanid").toString();
					}
				}
				}
				jobplanId = "1";  //���ԣ�Ĭ�ϸ���ID
*/
				dispatchFinishResults = jobPlanService.getAlreadyDispatchList(nodeid); //�ѷ��乤��
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
				    	map.put("no", StringUtils.getSubString((String)map.get("no"),"4"));   //��ȡ�ֶ�
				    }
				    /*if(map.get("name")!=null && !"".equals(map.get("name"))){
				    	map.put("name", StringUtils.getSubString((String)map.get("name"),"1"));   //��ȡ�ֶ�
				    }*/
				    /*if(map.get("jobno")!=null && !"".equals(map.get("jobno"))){
				    	map.put("jobno", StringUtils.getSubString((String)map.get("jobno"),"6"));   //��ȡ�ֶ�
				    }*/
				}
				
			/*	
				List<Map<String, Object>> lst = jobPlanService.getJobPlanDetail(jobplanId); //��ҵ�ƻ�����  ===>û��ʹ�õ���ext��ʵ����
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
