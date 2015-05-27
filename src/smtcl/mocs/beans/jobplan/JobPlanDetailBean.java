package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 
 * 作业计划队列管理Bean
 * @作者：yyh
 * @创建时间：2013-7-2 下午13:05:16
 * @修改者：songkaiang
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="JobPlanDetail")
@SessionScoped
public class JobPlanDetailBean implements Serializable {
	//---------------------------service--------------------------------------------
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	/**
	 * 零件Service接口
	 */
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	
	//---------------------------------------------------------------------------------
	private static String PART_NAME = "partName"; //(cookie名称)零件名称
	/**
	 * 状态数据集
	 */
	private List<Map<String,Object>> statusList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 零件名称数据集
	 */
	private List<Map<String,Object>> partTypeList = new ArrayList<Map<String,Object>>();
	//零件名称
	private String partTypeId;
	
	/**
	 * 在构造函数数初始化数据
	 */
	public JobPlanDetailBean(){
		partTypeList.clear();
		partTypeList = jobDispatchService.getPartTypeMap(this.getNodeid());//map中是id，name
	}
	
	public List<String> complete(String query){
		List<String> results = new ArrayList<String>();
		List<TPartTypeInfo> list = partService.getAllPartType(this.getNodeid(), query);
		for(TPartTypeInfo t:list){
			results.add(t.getName());
		}
		return results;
	}
	
//------------------------------private------------------------------------------------------------------------------	
	private String getNodeid(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
//-----------------------------set-----get-----------------------------------------------------------------------------
	public List<Map<String, Object>> getStatusList() {
		if(statusList.size()<1){
			Map<String,Object> statusMap = new HashMap<String,Object>();
			statusMap.put("id", 10);
			statusMap.put("value", "创建");
			statusList.add(statusMap);
			Map<String,Object> statusMap1 = new HashMap<String,Object>();
			statusMap1.put("id", 40);
			statusMap1.put("value", "上线");
			statusList.add(statusMap1);
			Map<String,Object> statusMap2 = new HashMap<String,Object>();
			statusMap2.put("id", 70);
			statusMap2.put("value", "完成");
			statusList.add(statusMap2);
			Map<String,Object> statusMap3 = new HashMap<String,Object>();
			statusMap3.put("id", 60);
			statusMap3.put("value", "结束");
			statusList.add(statusMap3);
		}
		return statusList;
	}
	public void setStatusList(List<Map<String, Object>> statusList) {
		this.statusList = statusList;
	}
	public String getPartTypeId() {
		return partTypeId;
	}
	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}
	public List<Map<String, Object>> getPartTypeList() {
		return partTypeList;
	}
	public void setPartTypeList(List<Map<String, Object>> partTypeList) {
		this.partTypeList = partTypeList;
	}


}
