package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.jobplan.IJobPlanService;

/**
 * 
 * 工单创建Bean
 * @作者：yyh
 * @创建时间：2013-7-15 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="JobdispatchlistAdd")
@ViewScoped
public class JobdispatchlistAddBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * 多选框的值
	 */
	private List<String> checkBoxValue;
	/**
	 * 单个的多选框的值
	 */
	private boolean booleanValue;
	/**
	 * 工单名称
	 */
	private String jobdispatchlistName;
	/**
	 * 作业ID
	 */
	private String jobplanId;
	/**
	 * 作业ID和作业名称集合
	 */
	private Map<String,Object> jobplanMap = new HashMap<String,Object>();
	/**
	 * 工单编号
	 */
	private String jobdispatchlistNo;
	/**
	 * 工单数量
	 */
	private String jobdispatchlistNum;
	/**
	 * 作业计划数量
	 */
	private String jobNum;
	/**
	 * 设备ID
	 */
	private String equipmentId;
	/**
	 * 设备ID和设备名称集合
	 */
	private Map<String,Object> equipmentMap = new HashMap<String,Object>();
	/**
	 * 设备类型ID
	 */
	private String equipmentTypeId;
	/**
	 * 设备类型ID和设备类型名称集合
	 */
	private Map<String,Object> equipmentTypeMap = new HashMap<String,Object>();
	/**
	 * 物料信息集合
	 */
	private List<Map<String,Object>> materailTypelist = new ArrayList<Map<String,Object>>();
	/**
	 * 工单开始时间
	 */
	private Date jobdispatchlistStartDate;
	/**
	 * 工单结束时间
	 */
	private Date jobdispatchlistEndDate;
    /**
     * 工单说明
     */
	private String jobdispatchlistDec;
	/**
	 * 生产子作业详情-生产数量
	 */
	private String  subjobplanNum;
	/**
	 * 生产子作业详情-生产开始时间
	 */
	private Date  subjobplanStartDate;
	/**
	 * 生产子作业详情-生产结束时间
	 */
	private Date  subjobplanEndDate;
	/**
	 * 工艺详情 - 方案名称
	 */
	private String processplanName;
	/**
	 * 工艺详情 - 默认方案
	 */
	private String processplanDefault;
	/**
	 * 工艺详情 - 节拍时间
	 */
	private String processplanTheoryWorktime;
	/**
	 * 工艺详情 - 指导文件
	 */
	private String processplanFile;
	/**
	 * 零件类型详细 -ID
	 */
	private String partId;
	/**
	 * 零件类型详细 -名称
	 */
	private String partName;
	/**
	 * 作业计划ID
	 */
	private String planId;
	/**
	 * 理论工时 
	 */
	private String theoryWorktime; //从作业得到
	
	/**
	 * 构造
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JobdispatchlistAddBean(){
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		Map<String,Object> lst1 =jobPlanService.getJobdispatchlistInfoForAdd("");
		List<Map<String,Object>> lst0 = (ArrayList)lst1.get("lst0");
		for(Map<String,Object> map : lst0){
			if(map.get("jobid")!=null && !map.get("jobid").equals("")){
				jobplanMap.put(map.get("jobname").toString(), map.get("jobid").toString());
			}
		}		
		List<Map<String,Object>> lst = jobPlanService.getEquimentByType(nodeid,""); //设备
		for(Map<String,Object> map : lst){
			if(map.get("equId")!=null && !map.get("equId").equals("")){
				equipmentMap.put(map.get("equName").toString(), map.get("equId").toString());
			}
		}
		List<Map<String,Object>> lstlst = jobPlanService.getEquimentAndType(""); //设备类型
		for(Map<String,Object> map : lstlst){
			if(map.get("id")!=null && !map.get("id").equals("")){
				equipmentTypeMap.put(map.get("equipmentType").toString(), map.get("id").toString());
			}
		}
	}
	
	/**
	 * 零件类型Id和零件名称的2级联动
	 */
	public void getEquipmentTypeSub(){
		equipmentMap.clear();
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		List<Map<String,Object>> lst = jobPlanService.getEquimentByType(nodeid,equipmentTypeId);
		for(Map<String,Object> map : lst){
			if(map.get("equId")!=null && !map.get("equId").equals("")){
				equipmentMap.put(map.get("equName").toString(), map.get("equId").toString());
			}
		}		
	}
	
	/**
	 * 作业名称ID和其他关联表的联动
	 */
	@SuppressWarnings("unchecked")
	public void getJobInfoSub(){
		materailTypelist.clear();
		jobNum = null;
		subjobplanNum =null;
		subjobplanStartDate =null;
		subjobplanEndDate =null;
		processplanName =null;
		processplanTheoryWorktime =null;
		processplanFile =null;
		partId =null;
		partName =null;
		planId = null;
		theoryWorktime = null;
		jobdispatchlistStartDate = null;
		
		jobdispatchlistStartDate = jobPlanService.getMaxEndTimeFormDispatchList(jobplanId); //获取初始时间
		
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> lst =jobPlanService.getJobdispatchlistInfoForAdd(jobplanId);
		List<Map<String,Object>> lst7 = (ArrayList<Map<String,Object>>)lst.get("lst7");
		List<Map<String,Object>> lst6 = (ArrayList<Map<String,Object>>)lst.get("lst6");
		List<Map<String,Object>> lst1 = (ArrayList<Map<String,Object>>)lst.get("lst1");
		List<Map<String,Object>> lst2 = (ArrayList<Map<String,Object>>)lst.get("lst2");
		List<Map<String,Object>> lst3 = (ArrayList<Map<String,Object>>)lst.get("lst3");
		List<Map<String,Object>> lst4 = (ArrayList<Map<String,Object>>)lst.get("lst4");
		List<Map<String,Object>> lst5 = (ArrayList<Map<String,Object>>)lst.get("lst5");
		
		if (lst6.size()>0) {
			Map<String,Object> map = lst6.get(0);
			if (map.get("jobplanNum") != null) {
				jobNum = map.get("jobplanNum").toString(); //得到作业计划数量			
			}
		}
		
		for(Map<String,Object> map : lst7){
			if(map.get("jobplanid")!=null){
			planId = map.get("jobplanid").toString();
			}
			if(map.get("theoryWorktime")!=null){
			theoryWorktime = map.get("theoryWorktime").toString();
			}
		}
		
		for(Map<String,Object> map : lst1){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("tid", map.get("tid").toString());
		m.put("tno", (String)map.get("tno"));
		m.put("tname", (String)map.get("tname"));
		m.put("requirementNum", (String)map.get("requirementNum"));		
		materailTypelist.add(m);
		}
System.out.println("----------->"+materailTypelist.size());
		
		if(lst2.size()>0){
		Map<String,Object> map2 = lst2.get(0);
		  if(map2.get("jplanNum")!=null){
		subjobplanNum = map2.get("jplanNum").toString();
		  }
		if(map2.get("planStarttime")!=null && !"".equals(map2.get("planStarttime"))){
		try {
			subjobplanStartDate = sdf.parse(map2.get("planStarttime").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		if(map2.get("planEndtime")!=null && !"".equals(map2.get("planEndtime"))){
		try {
			subjobplanEndDate = sdf.parse(map2.get("planEndtime").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		}
		
		if(lst3.size()>0){
		Map<String,Object> map3 = lst3.get(0);
		processplanName = (String)map3.get("pname");
//		processplanDefault;      //工艺方案中无字段
		 if(map3.get("theoryWorktime")!=null){
		processplanTheoryWorktime = map3.get("theoryWorktime").toString();
		 }
		processplanFile = (String)map3.get("file");
		}
		
		if(lst4.size()>0){
		Map<String,Object> map4 = lst4.get(0);
		partId = map4.get("partid").toString();
		partName = (String)map4.get("partname");
		}
		
		if(lst5.size()>0){
		Map<String,Object> map5 = lst5.get(0);
		 if(map5.get("cid")!=null){
		equipmentTypeId = map5.get("cid").toString();
		 }
		}
		
	}
	
	private String dialog;
    /**
     * 保存
     */
	public void addJobdispatchlist(){		
		List<Map<String,Object>> lst = jobPlanService.getJobdispatchlistByName(jobdispatchlistName);
		if(lst.size()==0){   //此名称的工单不存在，提交
		jobPlanService.addJobdispatchlistInfo(this);
	    }else{
	    	dialog = "show";
	    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    	request.setAttribute("dialog", dialog);
	    }
	}
	
	
	/**=====================================set,get方法==============================================**/

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

	public List<String> getCheckBoxValue() {
		return checkBoxValue;
	}

	public void setCheckBoxValue(List<String> checkBoxValue) {
		this.checkBoxValue = checkBoxValue;
	}

	public boolean isBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public String getJobdispatchlistName() {
		return jobdispatchlistName;
	}

	public void setJobdispatchlistName(String jobdispatchlistName) {
		this.jobdispatchlistName = jobdispatchlistName;
	}

	public String getJobplanId() {
		return jobplanId;
	}

	public void setJobplanId(String jobplanId) {
		this.jobplanId = jobplanId;
	}

	public Map<String, Object> getJobplanMap() {
		return jobplanMap;
	}

	public void setJobplanMap(Map<String, Object> jobplanMap) {
		this.jobplanMap = jobplanMap;
	}

	public String getJobdispatchlistNum() {
		return jobdispatchlistNum;
	}

	public void setJobdispatchlistNum(String jobdispatchlistNum) {
		this.jobdispatchlistNum = jobdispatchlistNum;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Map<String, Object> getEquipmentMap() {
		return equipmentMap;
	}

	public void setEquipmentMap(Map<String, Object> equipmentMap) {
		this.equipmentMap = equipmentMap;
	}

	public String getEquipmentTypeId() {
		return equipmentTypeId;
	}

	public void setEquipmentTypeId(String equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}

	public Map<String, Object> getEquipmentTypeMap() {
		return equipmentTypeMap;
	}

	public void setEquipmentTypeMap(Map<String, Object> equipmentTypeMap) {
		this.equipmentTypeMap = equipmentTypeMap;
	}

	public List<Map<String, Object>> getMaterailTypelist() {
		return materailTypelist;
	}

	public void setMaterailTypelist(List<Map<String, Object>> materailTypelist) {
		this.materailTypelist = materailTypelist;
	}

	public Date getJobdispatchlistStartDate() {
		return jobdispatchlistStartDate;
	}

	public void setJobdispatchlistStartDate(Date jobdispatchlistStartDate) {
		this.jobdispatchlistStartDate = jobdispatchlistStartDate;
	}

	public Date getJobdispatchlistEndDate() {
		return jobdispatchlistEndDate;
	}

	public void setJobdispatchlistEndDate(Date jobdispatchlistEndDate) {
		this.jobdispatchlistEndDate = jobdispatchlistEndDate;
	}

	public String getJobdispatchlistDec() {
		return jobdispatchlistDec;
	}

	public void setJobdispatchlistDec(String jobdispatchlistDec) {
		this.jobdispatchlistDec = jobdispatchlistDec;
	}

	public String getSubjobplanNum() {
		return subjobplanNum;
	}

	public void setSubjobplanNum(String subjobplanNum) {
		this.subjobplanNum = subjobplanNum;
	}

	public Date getSubjobplanStartDate() {
		return subjobplanStartDate;
	}

	public void setSubjobplanStartDate(Date subjobplanStartDate) {
		this.subjobplanStartDate = subjobplanStartDate;
	}

	public Date getSubjobplanEndDate() {
		return subjobplanEndDate;
	}

	public void setSubjobplanEndDate(Date subjobplanEndDate) {
		this.subjobplanEndDate = subjobplanEndDate;
	}

	public String getProcessplanName() {
		return processplanName;
	}

	public void setProcessplanName(String processplanName) {
		this.processplanName = processplanName;
	}

	public String getProcessplanDefault() {
		return processplanDefault;
	}

	public void setProcessplanDefault(String processplanDefault) {
		this.processplanDefault = processplanDefault;
	}

	public String getProcessplanTheoryWorktime() {
		return processplanTheoryWorktime;
	}

	public void setProcessplanTheoryWorktime(String processplanTheoryWorktime) {
		this.processplanTheoryWorktime = processplanTheoryWorktime;
	}

	public String getProcessplanFile() {
		return processplanFile;
	}

	public void setProcessplanFile(String processplanFile) {
		this.processplanFile = processplanFile;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getDialog() {
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getJobdispatchlistNo() {
		return jobdispatchlistNo;
	}

	public void setJobdispatchlistNo(String jobdispatchlistNo) {
		this.jobdispatchlistNo = jobdispatchlistNo;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getTheoryWorktime() {
		return theoryWorktime;
	}

	public void setTheoryWorktime(String theoryWorktime) {
		this.theoryWorktime = theoryWorktime;
	}	


	
}
