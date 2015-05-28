package smtcl.mocs.beans.jobplan;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.job.TJobInfo;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.services.jobplan.IJobPlanService;

/**
 * 
 * ��ҵ�ƻ�����Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-7-2 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="JobPlanControl")
@ViewScoped
public class JobPlanControlBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * @��������ҵ�ƻ���Ϣ���ݼ�
	 */	
	private Map<String, Object> jobPlanResults = new HashMap<String,Object>();
	/**
	 * ��ҵ�ƻ����Ƽ���
	 */
	private Map<String,Object> jobPlanNameMap = new HashMap<String,Object>();
	/**
	 * ��ҵ�ƻ�ID
	 */
	private String jobplabid;
	/**
	 * ����������Ƽ���
	 */
	private Map<String,Object> partTypeMap = new HashMap<String,Object>();
	/**
	 * �������ID
	 */
	private String partTypeId;
	/**
	 * ���շ������Ƽ���
	 */
	private Map<String,Object> skillMap = new HashMap<String,Object>();
	/**
	 * ���շ���ID
	 */
	private String skillId;
	/**
	 * �ƻ�����
	 */
	private String planNum;
	/**
	 * ����Ͷ������
	 */
	private String thisPlanNum;
	/**
	 * ��ʼʱ��
	 */
	private Date startTime;
	/**
	 * ����ʱ��
	 */
	private Date endTime;
	/**
	 * ����Ͷ�������б�
	 */
	private List<Map<String,Object>> batchlist = new ArrayList<Map<String,Object>>();


	/**
	 * ����dataTable����    ====>����
	 */
	private List<Map<String,Object>> processlist = new ArrayList<Map<String,Object>>();
	/**
	 * ����dataTable����  ����װ
	 */
	private TJobDispatchDataModel mediumProcessModel = new TJobDispatchDataModel();	
	/**
	 * ����ѡ�е���
	 */
	private Map<String,Object>[] selectedProcess; 	
	/**
	 * ����ѡ�е��е�ID
	 */
	private String selected;
	/**
	 * ��ҵdataTable����   ====������
	 */
	private List<Map<String,Object>> jobdispatchlist = new ArrayList<Map<String,Object>>();
	/**
	 * ��ҵdataTable����  ����װ
	 */
	private TJobDispatchDataModel jobdispatchModel = new TJobDispatchDataModel();
	/**
	 * ��ҵѡ�е���
	 */
	private Map<String,Object>[] selectedJobdispatch;

	/**
	 * �ڵ�ID
	 */
	private String nodeid;
	/**
	 * �ж������Ƿ���Զ�
	 */
	private String selectDisble;
	/**
	 * �Ƿ�ͬʱ��������
	 */
	private boolean booleanValue = true;

	/**
	 * ���캯��
	 */
	@SuppressWarnings("unchecked")
	public  JobPlanControlBean(){
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid = (String)session.getAttribute("nodeid");
		
		List<Map<String,Object>> lst = jobPlanService.getPartTypeMap();
		for(Map<String,Object> map : lst){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//�����������
				partTypeMap.put((String)map.get("name"),map.get("id").toString());
			}
		}
		List<Map<String,Object>> lst1 = jobPlanService.getJobPlanMap();
		for(Map<String,Object> map : lst1){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//��ҵ�ƻ�
				jobPlanNameMap.put((String)map.get("name"),map.get("id").toString());
			}
		}
		
		/**==============================**/
		
		//�������ID,������
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();	
		String id = "";
		if(request.getParameter("editPId")!=null){  //�ж��Ƿ�Ϊ��
		id = request.getParameter("editPId").trim();
		}
		if(id!=null && !id.equals("") && !id.equals("undefined")){  //ҳ��urlû�д�ֵ��������ԭ����
		jobplabid = id;
		List<Map<String, Object>> partlst =jobPlanService.getPartIdByJobPlanId(jobplabid);
		if(partlst.size()>0){
			Map<String, Object>  map = partlst.get(0);
			partTypeId = map.get("partId").toString();
		}
		//û��ID˵��û�е����ҵ�ƻ��������ǿ��Զ���
		if(jobplabid!=null&& !"".equals(jobplabid)&&!"-1".equals(jobplabid)){
			selectDisble = "yes";
		 }
		}
		//ͨ��������R���շ���
		List<Map<String,Object>> lst2 = jobPlanService.getProcessplanMap(partTypeId);
		for(Map<String,Object> map : lst2){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//���շ�������
				skillMap.put((String)map.get("name"),map.get("id").toString());
			}
		}	
		//Ĭ��ѡ��1�����շ���
		if(lst2.size()>0){
			Map<String,Object> map = lst2.get(0);
			skillId = map.get("id").toString();
		}
		
		//Ͷ�������б�
		batchlist = jobPlanService.getBatchList(skillId,jobplabid);
		
		//��ҵ�ƻ���Ϣ
		jobPlanResults.clear();
		List<Map<String, Object>> lstlst = jobPlanService.getJobPlanByJobIdAndPartId(nodeid,jobplabid, partTypeId);
		if(lstlst.size()>0){
		jobPlanResults = lstlst.get(0);   //��ҪĬ��ѡ�е�һ��
		planNum = jobPlanResults.get("planNum").toString();
		}
		
		//�����嵥
		processlist = jobPlanService.getProcessByProcessPlanId(nodeid,skillId);
		mediumProcessModel = new TJobDispatchDataModel(processlist); //��װ�����õ�1��
		selectedProcess = new Map[processlist.size()];
		for(int i=0; i<processlist.size();i++){  //��ΪĬ��ȫѡ
			Map<String, Object> tj = processlist.get(i);
		   selectedProcess[i] = tj;
		}
		
	}
	/**
	 * ��ҵ�ƻ�������ѯ
	 */
	public void searchListByjobplan(){
		//ͨ����ҵ�ƻ��õ����Map
		partTypeMap.clear();
		List<Map<String,Object>> lst = jobPlanService.getPartTypeMapByJobPlanid(jobplabid);
		for(Map<String,Object> map : lst){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//�����������
				partTypeMap.put((String)map.get("name"),map.get("id").toString());
			}
		}

	}
	
	/**
	 * �������������ѯ
	 */
	public void searchListBypart(){
		//ͨ��������R���շ���
		skillMap.clear();
		List<Map<String,Object>> lst2 = jobPlanService.getProcessplanMap(partTypeId);
		for(Map<String,Object> map : lst2){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//���շ�������
				skillMap.put((String)map.get("name"),map.get("id").toString());
			}
		}
	}
	
	
	/**
	 * ���շ���������ѯ
	 */
	@SuppressWarnings("unchecked")
	public void searchList(){   //�л����Ļ���Ҫ�ŵ������ķ������棬û�л����ľ��Ƿ��� get�������棬����ajax�г�ͻ,
		planNum = null;
		
		//Ͷ�������б�
		batchlist = jobPlanService.getBatchList(skillId,jobplabid);
		
		//��ҵ�ƻ���Ϣ
		jobPlanResults.clear();
		List<Map<String, Object>> lstlst = jobPlanService.getJobPlanByJobIdAndPartId(nodeid,jobplabid, partTypeId);
		if(lstlst.size()>0){
		jobPlanResults = lstlst.get(0);   //��ҪĬ��ѡ�е�һ��
		planNum = jobPlanResults.get("planNum").toString();
		}
		
		//�����嵥
		processlist = jobPlanService.getProcessByProcessPlanId(nodeid,skillId);
		mediumProcessModel = new TJobDispatchDataModel(processlist); //��װ�����õ�1��
		selectedProcess = new Map[processlist.size()];
		for(int i=0; i<processlist.size();i++){  //��ΪĬ��ȫѡ
			Map<String, Object> tj = processlist.get(i);
		   selectedProcess[i] = tj;
		}
	
	}	

    /**
 	 * ���·���
	 * @param event
     */
    public void onEdit(RowEditEvent event) {  
    	System.out.println("����----------->"+jobdispatchlist.size());
    	for(Map<String, Object> disp:jobdispatchlist){		
    		String proid = disp.get("processId").toString();
    		String equid = disp.get("equId").toString();
    		String processNum = disp.get("processNum").toString();
    		String planStarttime = disp.get("planStarttime").toString();
    		String planEndtime = disp.get("planEndtime").toString();
    		
			String equName =  jobPlanService.getEquNameByEquId(equid);
			disp.put("equName", equName); //�豸���Ʒ���
    		for(Map<String, Object> job:jobMaplst){
    			String id = job.get("id").toString();
    			if(proid.equals(id)){//��ͬ�Ĺ���ID,-->�ӹ����б��ظ���ҵ�б�
    				job.put("equId", equid);  //��selectedJobdispatch�е��豸id������jobMaplst��
    				job.put("processNum", processNum);
    				job.put("planStarttime", planStarttime);
    				job.put("planEndtime", planEndtime);
    			}
    		}
    	}
    	
    	
    	
    }
    
    /**
     * ȡ��
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }

   private List<TJobInfo>  joblst = new  ArrayList<TJobInfo>(); //�����Ŷ��������
   List<Map<String, Object>>  jobMaplst = new  ArrayList<Map<String, Object>>(); //������MAP������
   private List<TJobdispatchlistInfo>  jobdispatchlst = new  ArrayList<TJobdispatchlistInfo>();
    /**
     * ���ɹ���
     */
   public void produceJobDispathList(){	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	//����������ҵ
    joblst.clear();
//	List<Map<String, Object>> lst = jobPlanService.getJobByJobPlanId(jobplabid); //��ҵ�嵥= ���ݿ���+���ɵ�
	jobMaplst.clear();
   	for(Map<String, Object> gg:selectedProcess){
   		//���ɶ�������б�--��
   		TJobInfo p = new TJobInfo();
   		p.setNo("JOB_"+gg.get("no")+"_"+jobplabid);//��ҵ���
   		p.setName(gg.get("name")+"_��ҵ_"+jobplabid);//��ҵ����
   		p.setTheoryWorktime(Integer.parseInt(gg.get("theoryWorktime").toString()));//���۹�ʱ
   		TJobplanInfo tji = new TJobplanInfo();
   		tji.setId(new Long(gg.get("id").toString()));
   		p.setTJobplanInfo(tji);
   		//����MAP�����б�
   		String num = String.valueOf((int)(Math.random()*100));
   		Map<String, Object> pMap = new HashMap<String, Object>();
   		pMap.put("no","JOB_"+gg.get("no")+"_"+jobplabid+"_"+num);//��ҵ���
   		pMap.put("name",gg.get("cname")+"_��ҵ_"+jobplabid+"_"+num);//��ҵ����
   		pMap.put("theoryWorktime",Integer.parseInt(gg.get("theoryWorktime").toString()));//���۹�ʱ
   		pMap.put("equName", gg.get("equName")); //�豸����
   		pMap.put("equId", gg.get("equId")); //�豸ID
   		pMap.put("id",gg.get("id").toString()); //����ID
//   		pMap.put("cid",gg.get("cid").toString());//��ҵ�ƻ�ID
   		pMap.put("bianma",gg.get("no").toString());            //������룬Ϊƴ������
   		pMap.put("xuliehao",gg.get("equSerialNo").toString()); //�������кţ�Ϊƴ������
   		pMap.put("processNum",thisPlanNum);              //�ƻ�����
   		pMap.put("planStarttime",sdf.format(startTime));//��ʼʱ��
   		pMap.put("planEndtime",sdf.format(endTime));     //����ʱ��   

   		/*
		boolean b = false;
		for (Map<String, Object> g : lst) {
			if (g.get("jobNo") != null) {
				String no = (String) g.get("jobNo");
				if(no.equals(pMap.get("no"))){  //�ж����ɵ�NO�����ݿ�������NO�ظ�
					b = true;
					break;
				}
			}
		}
		if(b== false){ //�ų�Ϊ2�����͵�2��
		joblst.add(p); 
		jobMaplst.add(pMap);
		}	
		*/
		joblst.add(p); 
		jobMaplst.add(pMap);   //���Զ������
   		
	}
   	//��ҵ���ɹ���
    String time = sdf1.format(new Date());
    jobdispatchlst.clear();
    jobdispatchlist.clear();
   	for(Map<String, Object> pMap : jobMaplst){
   	//���ɶ�������б�--��
   		TJobdispatchlistInfo tp = new TJobdispatchlistInfo();
   		tp.setNo("WO_"+pMap.get("no")+"_"+time); //�������
   		tp.setName("WO_"+pMap.get("name")+"_"+time);//��������
   		tp.setTheoryWorktime(Integer.parseInt(pMap.get("theoryWorktime").toString()));//���۹�ʱ
   		tp.setProcessNum(Integer.parseInt(thisPlanNum)); //�ƻ�����
   		tp.setPlanStarttime(startTime);                  //��ʼʱ��
   		tp.setPlanEndtime(endTime);                      //����ʱ��
   		TEquipmentInfo te = new TEquipmentInfo();
   		te.setEquId(new Long(pMap.get("equId").toString()));
   		tp.setTEquipmentInfo(te);
//   		TJobInfo tj = new TJobInfo();
//   		tj.setId(id);
//   		tp.setTJobInfo(tj);//��ҵID��û�г�
   	//����MAP�����б�
   		Map<String, Object> tpMap = new HashMap<String, Object>();
   		tpMap.put("no","WO_"+pMap.get("bianma")+"_"+time+"_"+pMap.get("id")); //������� 
   		tpMap.put("processId",pMap.get("id")); //����ID
   		tpMap.put("processNo",pMap.get("bianma"));//�������,��ҵ����ļ���,����ֱ��pMap.get("bianma")�õ�
   		tpMap.put("name",pMap.get("name").toString());//��ҵ����
   		tpMap.put("equId",pMap.get("equId").toString());//�豸ID
   		tpMap.put("equName",pMap.get("equName").toString());//�豸����
   		tpMap.put("processNum",thisPlanNum);              //�ƻ�����
		tpMap.put("planStarttime",sdf.format(startTime));//��ʼʱ��
		tpMap.put("planEndtime",sdf.format(endTime));     //����ʱ��   
		
		String no = pMap.get("id").toString(); //����ID
		List<Map<String,Object>> nolst =  jobPlanService.getSerNoByProcessId(no);
		Map<String,Object> noMap = new HashMap<String,Object>();
		for(Map<String,Object> n : nolst){
			if(n.get("equId")!=null && !"".equals(n.get("equId"))){//�豸ID
				noMap.put((String)n.get("equName"),n.get("equId").toString());
			}
		}
		tpMap.put("euqmap", noMap);  //�豸����Map,�޸�����
   		
   	//�ų�Ϊ2�����͵�2��	
   		jobdispatchlst.add(tp);  		
   		jobdispatchlist.add(tpMap);   //���ɵ�+
    	}
        	/*    //���ݿ�ԭ�еĲ���ʾ
	   	    List<Map<String, Object>> skillst = jobPlanService.getDispatchList(skillId);
			for(Map<String, Object> m : skillst){
			jobdispatchlist.add(m);      //�������ݿ�ԭ�е�
			    }
			*/
			jobdispatchModel = new TJobDispatchDataModel(jobdispatchlist);
   }
   
   /**
    * ���湤��
    */
   public void saveJobDispatch(){
	   jobPlanService.saveJobDispatch(this);
	   
   }
    
	/**==================================set,get����====================================================*/

	public IJobPlanService getJobPlanService() {
		return jobPlanService;
	}

	public void setJobPlanService(IJobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
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

	public Map<String, Object> getPartTypeMap() {
		return partTypeMap;
	}

	public void setPartTypeMap(Map<String, Object> partTypeMap) {
		this.partTypeMap = partTypeMap;
	}


	public String getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public Map<String, Object> getSkillMap() {
		return skillMap;
	}

	public void setSkillMap(Map<String, Object> skillMap) {
		this.skillMap = skillMap;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}


	public String getThisPlanNum() {
		return thisPlanNum;
	}

	public void setThisPlanNum(String thisPlanNum) {
		this.thisPlanNum = thisPlanNum;
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

	public List<Map<String, Object>> getBatchlist() {
		return batchlist;
	}

	public void setBatchlist(List<Map<String, Object>> batchlist) {
		this.batchlist = batchlist;
	}

	public TJobDispatchDataModel getMediumProcessModel() {
		return mediumProcessModel;
	}

	public void setMediumProcessModel(TJobDispatchDataModel mediumProcessModel) {
		this.mediumProcessModel = mediumProcessModel;
	}

	public Map<String, Object>[] getSelectedProcess() {
		return selectedProcess;
	}

	public void setSelectedProcess(Map<String, Object>[] selectedProcess) {
		this.selectedProcess = selectedProcess;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public TJobDispatchDataModel getJobdispatchModel() {
		return jobdispatchModel;
	}

	public void setJobdispatchModel(TJobDispatchDataModel jobdispatchModel) {
		this.jobdispatchModel = jobdispatchModel;
	}

	public Map<String, Object>[] getSelectedJobdispatch() {
		return selectedJobdispatch;
	}

	public void setSelectedJobdispatch(Map<String, Object>[] selectedJobdispatch) {
		this.selectedJobdispatch = selectedJobdispatch;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public List<Map<String, Object>> getJobMaplst() {
		return jobMaplst;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}
	
	public String getSelectDisble() {
		return selectDisble;
	}
	
	public void setSelectDisble(String selectDisble) {
		this.selectDisble = selectDisble;
	}
	
	public boolean isBooleanValue() {
		return booleanValue;
	}
	
	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}		
    
}
