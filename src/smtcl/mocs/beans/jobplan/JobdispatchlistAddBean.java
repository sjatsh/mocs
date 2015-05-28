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
 * ��������Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-7-15 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="JobdispatchlistAdd")
@ViewScoped
public class JobdispatchlistAddBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * ��ѡ���ֵ
	 */
	private List<String> checkBoxValue;
	/**
	 * �����Ķ�ѡ���ֵ
	 */
	private boolean booleanValue;
	/**
	 * ��������
	 */
	private String jobdispatchlistName;
	/**
	 * ��ҵID
	 */
	private String jobplanId;
	/**
	 * ��ҵID����ҵ���Ƽ���
	 */
	private Map<String,Object> jobplanMap = new HashMap<String,Object>();
	/**
	 * �������
	 */
	private String jobdispatchlistNo;
	/**
	 * ��������
	 */
	private String jobdispatchlistNum;
	/**
	 * ��ҵ�ƻ�����
	 */
	private String jobNum;
	/**
	 * �豸ID
	 */
	private String equipmentId;
	/**
	 * �豸ID���豸���Ƽ���
	 */
	private Map<String,Object> equipmentMap = new HashMap<String,Object>();
	/**
	 * �豸����ID
	 */
	private String equipmentTypeId;
	/**
	 * �豸����ID���豸�������Ƽ���
	 */
	private Map<String,Object> equipmentTypeMap = new HashMap<String,Object>();
	/**
	 * ������Ϣ����
	 */
	private List<Map<String,Object>> materailTypelist = new ArrayList<Map<String,Object>>();
	/**
	 * ������ʼʱ��
	 */
	private Date jobdispatchlistStartDate;
	/**
	 * ��������ʱ��
	 */
	private Date jobdispatchlistEndDate;
    /**
     * ����˵��
     */
	private String jobdispatchlistDec;
	/**
	 * ��������ҵ����-��������
	 */
	private String  subjobplanNum;
	/**
	 * ��������ҵ����-������ʼʱ��
	 */
	private Date  subjobplanStartDate;
	/**
	 * ��������ҵ����-��������ʱ��
	 */
	private Date  subjobplanEndDate;
	/**
	 * �������� - ��������
	 */
	private String processplanName;
	/**
	 * �������� - Ĭ�Ϸ���
	 */
	private String processplanDefault;
	/**
	 * �������� - ����ʱ��
	 */
	private String processplanTheoryWorktime;
	/**
	 * �������� - ָ���ļ�
	 */
	private String processplanFile;
	/**
	 * ���������ϸ -ID
	 */
	private String partId;
	/**
	 * ���������ϸ -����
	 */
	private String partName;
	/**
	 * ��ҵ�ƻ�ID
	 */
	private String planId;
	/**
	 * ���۹�ʱ 
	 */
	private String theoryWorktime; //����ҵ�õ�
	
	/**
	 * ����
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JobdispatchlistAddBean(){
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		Map<String,Object> lst1 =jobPlanService.getJobdispatchlistInfoForAdd("");
		List<Map<String,Object>> lst0 = (ArrayList)lst1.get("lst0");
		for(Map<String,Object> map : lst0){
			if(map.get("jobid")!=null && !map.get("jobid").equals("")){
				jobplanMap.put(map.get("jobname").toString(), map.get("jobid").toString());
			}
		}		
		List<Map<String,Object>> lst = jobPlanService.getEquimentByType(nodeid,""); //�豸
		for(Map<String,Object> map : lst){
			if(map.get("equId")!=null && !map.get("equId").equals("")){
				equipmentMap.put(map.get("equName").toString(), map.get("equId").toString());
			}
		}
		List<Map<String,Object>> lstlst = jobPlanService.getEquimentAndType(""); //�豸����
		for(Map<String,Object> map : lstlst){
			if(map.get("id")!=null && !map.get("id").equals("")){
				equipmentTypeMap.put(map.get("equipmentType").toString(), map.get("id").toString());
			}
		}
	}
	
	/**
	 * �������Id��������Ƶ�2������
	 */
	public void getEquipmentTypeSub(){
		equipmentMap.clear();
		//��ȡ�ڵ�ID
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
	 * ��ҵ����ID�����������������
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
		
		jobdispatchlistStartDate = jobPlanService.getMaxEndTimeFormDispatchList(jobplanId); //��ȡ��ʼʱ��
		
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
				jobNum = map.get("jobplanNum").toString(); //�õ���ҵ�ƻ�����			
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
//		processplanDefault;      //���շ��������ֶ�
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
     * ����
     */
	public void addJobdispatchlist(){		
		List<Map<String,Object>> lst = jobPlanService.getJobdispatchlistByName(jobdispatchlistName);
		if(lst.size()==0){   //�����ƵĹ��������ڣ��ύ
		jobPlanService.addJobdispatchlistInfo(this);
	    }else{
	    	dialog = "show";
	    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    	request.setAttribute("dialog", dialog);
	    }
	}
	
	
	/**=====================================set,get����==============================================**/

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
