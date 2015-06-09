package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.BundleUtils;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 
 * �������Bean
 * ���� yyh
 * ����ʱ�� 2014-2-23
 * �޸��� yyh,songkaiang
 * �޸����� 2015-3-13
 * �޸�˵�� ʹ��jsf�Ĺ��ʻ�
 * @version V1.0
 */
@ManagedBean(name="JobdispatchAdd")
@ViewScoped
public class JobdispatchAddBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * �����ӿ�ʵ��
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	/**
	 * ����������Ƽ���
	 */
	private Map<String,Object> partTypeMap = new HashMap<String,Object>();
	/**
	 * �豸�������Ƽ���
	 */
	private Map<String,Object> eduTypeMapforupdata = new HashMap<String,Object>();
	/**
	 * �豸�������Ƴ�ʼ��
	 */
	private String eduTypeMapforupdataname;
	/**
	 * �豸����ID��ʼ��
	 */
	private String  eduTypeMapforupdataId;
	/**
	 * �������ID
	 */
	private String partTypeId;
	/**
	 * Ͷ������
	 */
	private String num;
	/**
	 * �����
	 */
	private String taskNum;
	/**
	 * �ƻ���ʼʱ��
	 */
	private Date startDate;
	/**
	 * �ƻ�����ʱ��
	 */
	private Date endDate;
	
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
	 * ����dataTable����   ====������
	 */
	private List<Map<String,Object>> jobdispatchlist = new ArrayList<Map<String,Object>>();
	/**
	 * ����dataTable����  ����װ
	 */
	private TJobDispatchDataModel jobdispatchModel = new TJobDispatchDataModel();
	/**
	 * ����ѡ�е���
	 */
	private Map<String,Object>[] selectedJobdispatch;
	/**
	 * �ڵ�ID
	 */
	private String nodeid;
	/**
	 * ��ҵ�ƻ���
	 */
	private String planjobId;
	/**
	 * ��ҵ�ƻ��ż���
	 */
	private Map<String,Object> planjobMap = new TreeMap<String,Object>();
	/**
	 * ��ʾ��Ϣ
	 */
	private String isSuccess;
	/****************************************************************/
	/**
	 * ��ҵ�嵥��ѡ�п�
	 */
	private boolean oneBool = true; //��ҵ�嵥�Ĳ˵���ѡ�п�Ĭ��Ϊѡ��
	
	public List<String> complete(String query){
		List<String> results = new ArrayList<String>();
		List<TPartTypeInfo> list = partService.getAllPartType(this.getNodeId(), query);
		for(TPartTypeInfo t:list){
			results.add(t.getName());
		}
		return results;
	}
	
	/**
	 * ���ƶ�ѡ���ȫѡ���
	 */
	public void editCheckBox(){
		if(oneBool){
			for (Map<String, Object> gg : jobdispatchlist) {
				gg.put("bool", true);
			}
		}else{
			for (Map<String, Object> gg : jobdispatchlist) {
				gg.put("bool", false);
			}
		}
	}
	List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
	/**
	 * ���ѡ�����ɵĹ����嵥�б�
	 */
	public  void editList(String jobNo){
		System.out.println("----------->"+jobNo);
		for(Map<String,Object> map : jobdispatchlist){
			String jn = map.get("dno").toString();
			boolean b = Boolean.parseBoolean(map.get("bool").toString());	
			if(jn.equals(jobNo) && !b){  // ȷ�����ĸ��� b�Ƿ�ѡ��
				lst.add(map); //���ܱ�����ʱ��ɾ����ֻ�ܰ�Ҫɾ���ķ���list,Ȼ����ɾ�����list
			}
		}

	}
	/**
	 * ɾ������Ĺ����嵥�б�
	 */
//	public void delList(){
//		jobdispatchlist.removeAll(lst);
//		lst.clear();
//		jobdispatchModel = new TJobDispatchDataModel(jobdispatchlist);
//	}
	
	/****************************************************************/	
	
	/**
	 * ��ȡ�ڵ�id
	 * @return ����nodeid
	 */
	private String getNodeId(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	/**
	 * ���캯��
	 */
	public JobdispatchAddBean() {
		nodeid = this.getNodeId();
		List<Map<String, Object>> lst = jobDispatchService.getPartTypeMap(this
				.getNodeId());
		for (Map<String, Object> map : lst) {
			if (map.get("id") != null && !"".equals(map.get("id"))) {// �����������
				partTypeMap.put((String) map.get("name"), map.get("id").toString() + "@#" + (String) map.get("name"));
			}
		}
	}
	
	private String partTypeId00;   //�������ID--����
	/**
	 * ��ѯ<span>����ʹ��</span>
	 */
	public void searchList() {
		
	}
	
    /**
 	 * ���·���
	 * @param event
     */
    public void onEdit(RowEditEvent event) {  
    	System.out.println("����----------->"+jobdispatchlist.size());
    	for(Map<String, Object> disp:jobdispatchlist){	
    		String equtypeid = disp.get("equTypeId").toString();
    		String parttypename = jobDispatchService.getPartTypeNameById(equtypeid);
    		disp.put("eduTypeName", parttypename);
    	}
    }
    
    /**
     * ȡ��
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }
    
    /**
     * ��ѡ�е�ʱ��
     */
    public void onRowSelect(SelectEvent event) {  
    	System.out.println("��ѡ��----------->"+jobdispatchlist.size());
    	for(Map<String, Object> disp:jobdispatchlist){	
    		String equtypeid = disp.get("equTypeId").toString();
    		System.out.println("�豸����ID----------->"+equtypeid);
    	}
    }

    /**
     * ���ɹ���
     */
   public void produceJobDispathList(){	
	   jobdispatchlist.clear();
	   SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	   String time = sdf1.format(new Date());
	    String fristDispath ="";  //�õ��׹�λ������
		List<TPartTypeInfo> list = partService.getPartTypeInfo(partTypeId);
		for (TPartTypeInfo t : list) {
			partTypeId00 = t.getId().toString();
			break;
		}
	   for(Map<String, Object> gg:selectedProcess){
		   Map<String, Object> pMap = new HashMap<String, Object>();
		   String num11 = String.valueOf((int)(Math.random()*100));
		   pMap.put("dno","WO_"+gg.get("no")+"_"+partTypeId00+"_"+time+"_"+num11);     //�������
		   pMap.put("dname","WO_"+gg.get("cname")+"_"+partTypeId00+"_"+time+"_"+num11);//��������
		    if("".equals(fristDispath)){  //ֻ��1��
			    fristDispath = "WO_"+gg.get("no")+"_"+partTypeId00+"_"+time+"_"+num11;
			   }
		   pMap.put("batchNo",fristDispath);               //�׹�λ������
		   pMap.put("taskNum",taskNum);               //�����
		   pMap.put("id",gg.get("id"));               //����ID
		   pMap.put("no",gg.get("no"));               //�������
		   pMap.put("cname",gg.get("cname"));         //��������
		   pMap.put("num",num);                       //����

		   pMap.put("equTypeId",gg.get("eduTypeMapforupdataId"));                           //�豸����ID
		   pMap.put("eduTypeName",gg.get("eduTypeMapforupdataname"));          //�豸��������
		   pMap.put("eduTypeMapforupdata",gg.get("eduTypeMapforupdata"));      //�豸���ͼ���
		   
		   jobdispatchlist.add(pMap);
	   }
		for (Map<String, Object> gg : jobdispatchlist) {   //����ȫ����Ϊ�Ѿ�ѡ��
			gg.put("bool", true);
		}
	   jobdispatchModel = new TJobDispatchDataModel(jobdispatchlist);
   }
   
	/**
	 * ѡ�еõ�ID����
	 */
   public void onSelected(){
	for(Map<String,Object> tt:selectedJobdispatch){
		selected=(String)tt.get("id");
	}
   }
   
   /**
    * ȡ������
    */
	public void delJobDispathList() {
		System.out.println("ȡ��----------->" + jobdispatchlist.size());
		jobdispatchlist.clear();
		/*for (Map<String, Object> disp : jobdispatchlist) {
			String equtypeid = disp.get("equTypeId").toString();
		}*/
	}
   
   /**
    * ���湤��
    */
   public String saveJobDispatch(){
       isSuccess = BundleUtils.getStringFromBundle("faild.add", this.getLocale(), "jobdispatch");
       if(this.getJobdispatchlist().size()<=0){
           return isSuccess;
       }else{
           jobDispatchService.saveDispatch(this);
           isSuccess = BundleUtils.getStringFromBundle("add.success", this.getLocale(), "jobdispatch");
           return isSuccess;
       }

   }
   /**
    * ѡ����ҵ�ź󣬻�ȡͶ������������ţ����Զ����
    */
   public void autoComplete(){
	   num = this.getNum();
	   if(!planjobId.isEmpty()){
           TJobplanInfo t = jobPlanService.geTJobplanInfoById(Long.parseLong(planjobId));
           this.setTaskNum(t.getName());
       }
	   
   }
   	/*************************private**********************************/
   private String getPartId(String partName){
	   List<TPartTypeInfo> list = partService.getPartTypeInfo(partName);
		for(TPartTypeInfo t : list ){
			return t.getId().toString();
		}
		return null;
   }

   private String getSkillid(){
       HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
       List<Map<String, Object>> lst2;
       if(request.getParameter("partId")!=null){
           lst2 = jobPlanService.getProcessplanMap(request.getParameter("partId").trim());
       }else{
           lst2 = jobPlanService.getProcessplanMap(this.getPartId(partTypeId));
       }


		// Ĭ��ѡ��1�����շ���
		if (lst2.size() > 0) {
			Map<String, Object> map = lst2.get(0);
			return map.get("id").toString();
		}
		return null;
   }
   private Locale getLocale(){
       HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
       return SessionHelper.getCurrentLocale(request.getSession());
   }
    /********************set,get����************************************/    

	public IJobPlanService getJobPlanService() {
		return jobPlanService;
	}
	
	public void setJobPlanService(IJobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
	}
	
	public Map<String, Object> getPartTypeMap() {
		return partTypeMap;
	}
	
	public String getPartTypeId() {
		if(partTypeId==null){
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if (request.getParameter("partId") != null
					&& !"".equals(request.getParameter("partId"))
					&& !request.getParameter("partId").equals("undefined")) {
				
				List<Map<String, Object>> lst = jobDispatchService.getPartTypeMap(this.getNodeId());
				String partId = request.getParameter("partId").trim();
				startDate = StringUtils.convertDate(request.getParameter("StartDate").trim(),"yyyy-MM-dd");
				endDate = StringUtils.convertDate(request.getParameter("EndDate").trim(), "yyyy-MM-dd");
				for(Map<String,Object> map:lst){
					if(map.get("id") != null && map.get("id").toString().equals(partId)){
						partTypeId = map.get("name").toString();
						break;
					}
				}
				String planid = request.getParameter("planId").trim();
				List<TJobplanInfo> jobPlanList = jobPlanService.getJobPlan(this.getNodeId(),partId);
				for(TJobplanInfo t:jobPlanList){
					if(planid.equals(t.getId().toString())){
						planjobId = t.getId().toString();
						
						taskNum = t.getName();
						break;
					}
				}
			}
			
		}
		return partTypeId;
	}
	
	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public String getNum() {
		if(planjobId != null && !planjobId.isEmpty()){
			TJobplanInfo t = jobPlanService.geTJobplanInfoById(Long.parseLong(planjobId));
			return t == null ? null : t.getPlanNum().toString();
		}
		return num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
	
	public String getTaskNum() {
		return taskNum;
	}
	
	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}

	@SuppressWarnings("unchecked")
	public TJobDispatchDataModel getMediumProcessModel() {
		if(partTypeId!=null && !partTypeId.isEmpty()){
			// �����嵥
			processlist = jobDispatchService.getProcessByProcessPlanId(this.getNodeId(),
					this.getSkillid());
			mediumProcessModel = new TJobDispatchDataModel(processlist); // ��װ�����õ�1��
			selectedProcess = new Map[processlist.size()];
			for (int i = 0; i < processlist.size(); i++) { // ��ΪĬ��ȫѡ
				Map<String, Object> tj = processlist.get(i);
				selectedProcess[i] = tj;
			}
			for (Map<String, Object> mapp : selectedProcess) {
				String processId = mapp.get("id").toString(); // ����ID
				// �豸���ͼ���
				List<Map<String, Object>> lst1 = jobDispatchService
						.getEquTypeMap(processId);
				eduTypeMapforupdata.clear(); // ����ϴε�����
				/* �޸�����start */
				List<Map<String, Object>> sblist = new ArrayList<Map<String, Object>>();
				/* �޸�����end */
				for (Map<String, Object> map : lst1) {
					Map<String, Object> sb = new HashMap<String, Object>();
					if (map.get("id") != null && !"".equals(map.get("id"))) {// �豸��������,Ϊ���޸�
						eduTypeMapforupdata.put((String) map.get("name"),
								map.get("id").toString());
						sb.put("sbname", (String) map.get("name"));
						sb.put("sbvalue", map.get("id").toString());
						sblist.add(sb);
					}
				}
				mapp.put("eduTypeMapforupdata", sblist);
				// ��ȥʱ��ĳ�ʼ�豸��������
				if (lst1.size() > 0) {
					Map<String, Object> map = lst1.get(0);
					eduTypeMapforupdataname = (String) map.get("name");
					eduTypeMapforupdataId = map.get("id").toString();
				}
				mapp.put("eduTypeMapforupdataname", eduTypeMapforupdataname);
				mapp.put("eduTypeMapforupdataId", eduTypeMapforupdataId);
			}
		}
		return mediumProcessModel;
	}
	
	public void setMediumProcessModel(TJobDispatchDataModel mediumProcessModel) {
		this.mediumProcessModel = mediumProcessModel;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>[] getSelectedProcess() {
		if(partTypeId!=null && !partTypeId.isEmpty()){
			// �����嵥
			processlist = jobDispatchService.getProcessByProcessPlanId(this.getNodeId(),
					this.getSkillid());
			selectedProcess = new Map[processlist.size()];
			for (int i = 0; i < processlist.size(); i++) { // ��ΪĬ��ȫѡ
				Map<String, Object> tj = processlist.get(i);
				selectedProcess[i] = tj;
			}
			jobdispatchlist.clear(); // ˢ�����ͬʱ����Ҳ�
		}
		
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
	
	public List<Map<String, Object>> getJobdispatchlist() {
		return jobdispatchlist;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isOneBool() {
		return oneBool;
	}
	public void setOneBool(boolean oneBool) {
		this.oneBool = oneBool;
	}
	public List<Map<String, Object>> getLst() {
		return lst;
	}
	public void setLst(List<Map<String, Object>> lst) {
		this.lst = lst;
	}
	public String getPlanjobId() {
		return planjobId;
	}
	public void setPlanjobId(String planjobId) {
		this.getPlanjobMap();
		this.planjobId = planjobId;
	}
	public Map<String, Object> getPlanjobMap() {
		planjobMap.clear();
		if(partTypeId!=null && !partTypeId.isEmpty()){
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            List<TJobplanInfo> jobPlanList;
            if(request.getParameter("partId")!=null){
                jobPlanList = jobPlanService.getJobPlan(this.getNodeId(),request.getParameter("partId"));
            }else{
                jobPlanList = jobPlanService.getJobPlan(this.getNodeId(),this.getPartId(partTypeId));
            }

			for (TJobplanInfo job : jobPlanList) {
				
				planjobMap.put(job.getName(), job.getId());
			}
		}
		
		return planjobMap;
	}
	public void setPlanjobMap(Map<String, Object> planjobMap) {
		this.planjobMap = planjobMap;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
   
}
