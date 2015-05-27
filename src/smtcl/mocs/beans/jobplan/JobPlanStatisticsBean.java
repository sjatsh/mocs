package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * ��ҵ�ƻ�ͳ��Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-7-2 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="JobPlanStatistics")
@ViewScoped
public class JobPlanStatisticsBean implements Serializable {
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
    /**
     * ���ݽṹ��
     */
	private List<Map<String, Object>> jobPlanResults = new ArrayList<Map<String, Object>>();
	/**
	 * ��ҵ�ƻ�����
	 */
	private String jobPlanName;
	/**
	 * ��ʼʱ��
	 */
	private Date startTime;
	/**
	 * ����ʱ��
	 */
	private Date endTime=new Date();
	/**
	 * ��ҵ�ƻ����ƽ����
	 */
	private Map<String,Object> jobPlanNameMap = new HashMap<String,Object>();
	/**
	 * ��ҵ�ƻ�ID
	 */
	private String jobplabid;

	/**
	 * ����
	 */
	public JobPlanStatisticsBean(){
		List<Map<String,Object>> lst = jobPlanService.getPlanAndPartList("");
		for(Map<String,Object> map : lst){
			if(map.get("id")!=null && !"".equals(map.get("id"))){
				jobPlanNameMap.put((String)map.get("name"),map.get("id").toString());
			}
		}	
		//Ĭ�����һ����
		this.startTime=StringUtils.convertDatea(1)[0];
		this.endTime=StringUtils.convertDatea(1)[1];
		jobPlanResults = jobPlanService.getPlanListByIdAndTime(jobplabid, startTime, endTime);	
	}	
	
	/**
	 * ����
	 */
	public void searchList(){

	}
	
	/**
	 * ҳ�淵�ط���
	 */
	public String toPage(){
		return "jobplanstatistics";
	} 
	
     /**==================================��״ͼ====================================================*/
	
	/**
	 * ��״ͼ����
	 */
	public String columJsonData;		

	public String getColumJsonData() {
		columJsonData = this.toColumnJSON();
		return columJsonData;
	}

	public void setColumJsonData(String columJsonData) {
		this.columJsonData = columJsonData;
	}

	/**
	 *  ��״ͼ�������ݷ�װ
	 * @return
	 */
	public String toColumnJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String id = "";
		if(request.getParameter("editPId")!=null){  //�ж��Ƿ�Ϊ��
		id = request.getParameter("editPId").trim();
		}
		if(id!=null && !"".equals(id) && !id.equals("undefined")){  //ҳ��urlû�д�ֵ��������ԭ����
		jobplabid = id;
		}

		List<Map<String, Object>> lst = jobPlanService.getPlanListByIdAndTime(jobplabid, startTime, endTime);
		
		String[] columns = new String[lst.size()];      //����������
		double[] planValue = new double[lst.size()];     //�ƻ�����
		double[] actualValue = new double[lst.size()];   //�ϸ�����
		double[] noactualValue = new double[lst.size()];   //���ϸ�����
		for(int i=0;i<lst.size();i++){
			Map<String, Object> map = lst.get(i);
			columns[i] = map.get("name").toString();
			if(map.get("planNum")!=null && !"".equals(map.get("planNum"))){
			planValue[i] = Double.parseDouble(map.get("planNum").toString());
			}
			if(map.get("finishNum")!=null && !"".equals(map.get("finishNum"))){ //����-�ϸ���=���ϸ���
				if(map.get("qualifiedNum")==null || "".equals(map.get("qualifiedNum"))){ //�ϸ���Ϊ�� ��ȫ��Ϊ���ϸ�
					noactualValue[i] = Double.parseDouble(map.get("finishNum").toString());
				}else{
			noactualValue[i] =
					Double.parseDouble(map.get("finishNum").toString()) -
					Double.parseDouble(map.get("qualifiedNum").toString());
				}
			}
			if(map.get("qualifiedNum")!=null && !"".equals(map.get("qualifiedNum"))){
			actualValue[i] = Double.parseDouble(map.get("qualifiedNum").toString());
			}
		}
		try {
			data.put("columns", columns);         //����������
			data.put("planValue", planValue); //�ƻ�����	
			data.put("actualValue", actualValue); //�ϸ�����
			data.put("noactualValue", noactualValue); //���ϸ�����

			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}		
		return null;
	}

	
	/**==================================set,get����===================================================*/
	
	
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

	public List<Map<String, Object>> getJobPlanResults() {
		jobPlanResults.clear();
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String id = "";
		if(request.getParameter("editPId")!=null){  //�ж��Ƿ�Ϊ��
		id = request.getParameter("editPId").trim();
		}
		if(id!=null && !id.equals("") && !id.equals("undefined")){  //ҳ��urlû�д�ֵ��������ԭ����
		jobplabid = id;
		}
		
		jobPlanResults = jobPlanService.getPlanListByIdAndTime(jobplabid, startTime, endTime);	
		return jobPlanResults;
	}

	public void setJobPlanResults(List<Map<String, Object>> jobPlanResults) {
		this.jobPlanResults = jobPlanResults;
	}

	public String getJobPlanName() {
		return jobPlanName;
	}

	public void setJobPlanName(String jobPlanName) {
		this.jobPlanName = jobPlanName;
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

	public Map<String, Object> getJobPlanNameMap() {
		return jobPlanNameMap;
	}

	public void setJobPlanNameMap(Map<String, Object> jobPlanNameMap) {
		this.jobPlanNameMap = jobPlanNameMap;
	}	
	
	public String getJobplabid() {
		return jobplabid;
	}

	public void setJobplabid(String jobplabid) {
		this.jobplabid = jobplabid;
	}
	

}
