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
 * 作业计划统计Bean
 * @作者：yyh
 * @创建时间：2013-7-2 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="JobPlanStatistics")
@ViewScoped
public class JobPlanStatisticsBean implements Serializable {
	
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
    /**
     * 数据结构集
     */
	private List<Map<String, Object>> jobPlanResults = new ArrayList<Map<String, Object>>();
	/**
	 * 作业计划名称
	 */
	private String jobPlanName;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime=new Date();
	/**
	 * 作业计划名称结果集
	 */
	private Map<String,Object> jobPlanNameMap = new HashMap<String,Object>();
	/**
	 * 作业计划ID
	 */
	private String jobplabid;

	/**
	 * 构造
	 */
	public JobPlanStatisticsBean(){
		List<Map<String,Object>> lst = jobPlanService.getPlanAndPartList("");
		for(Map<String,Object> map : lst){
			if(map.get("id")!=null && !"".equals(map.get("id"))){
				jobPlanNameMap.put((String)map.get("name"),map.get("id").toString());
			}
		}	
		//默认最近一个月
		this.startTime=StringUtils.convertDatea(1)[0];
		this.endTime=StringUtils.convertDatea(1)[1];
		jobPlanResults = jobPlanService.getPlanListByIdAndTime(jobplabid, startTime, endTime);	
	}	
	
	/**
	 * 查找
	 */
	public void searchList(){

	}
	
	/**
	 * 页面返回方法
	 */
	public String toPage(){
		return "jobplanstatistics";
	} 
	
     /**==================================柱状图====================================================*/
	
	/**
	 * 柱状图数据
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
	 *  柱状图数据数据封装
	 * @return
	 */
	public String toColumnJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String id = "";
		if(request.getParameter("editPId")!=null){  //判断是否为空
		id = request.getParameter("editPId").trim();
		}
		if(id!=null && !"".equals(id) && !id.equals("undefined")){  //页面url没有传值过来就是原来的
		jobplabid = id;
		}

		List<Map<String, Object>> lst = jobPlanService.getPlanListByIdAndTime(jobplabid, startTime, endTime);
		
		String[] columns = new String[lst.size()];      //横坐标名称
		double[] planValue = new double[lst.size()];     //计划产量
		double[] actualValue = new double[lst.size()];   //合格数量
		double[] noactualValue = new double[lst.size()];   //不合格数量
		for(int i=0;i<lst.size();i++){
			Map<String, Object> map = lst.get(i);
			columns[i] = map.get("name").toString();
			if(map.get("planNum")!=null && !"".equals(map.get("planNum"))){
			planValue[i] = Double.parseDouble(map.get("planNum").toString());
			}
			if(map.get("finishNum")!=null && !"".equals(map.get("finishNum"))){ //总数-合格数=不合格数
				if(map.get("qualifiedNum")==null || "".equals(map.get("qualifiedNum"))){ //合格数为空 就全部为不合格
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
			data.put("columns", columns);         //横坐标名称
			data.put("planValue", planValue); //计划产量	
			data.put("actualValue", actualValue); //合格数量
			data.put("noactualValue", noactualValue); //不合格数量

			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}		
		return null;
	}

	
	/**==================================set,get方法===================================================*/
	
	
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
		if(request.getParameter("editPId")!=null){  //判断是否为空
		id = request.getParameter("editPId").trim();
		}
		if(id!=null && !id.equals("") && !id.equals("undefined")){  //页面url没有传值过来就是原来的
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
