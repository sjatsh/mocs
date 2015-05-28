package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.services.jobplan.IJobPlanService;

/**
 * 
 * ��ҵ�ƻ��༭Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-7-2 ����13:05:16
 * @�޸��ߣ�songkaiang
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="JobPlanUpdata")
@ViewScoped
public class JobPlanUpdataBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
    /**
     * ����ҳ�洫ֵ
     */
	private Map<String,Object> jobPlanMap = new HashMap<String,Object>();
	/**
	 * �����ƻ���
	 */
	private Map<String,Object> producePlanMap = new HashMap<String,Object>(); 
	/**
	 * ������Ƽ�
	 */
	private Map<String,Object> partMap = new HashMap<String,Object>();
	/**
	 * ���ȼ�
	 */
	private Map<String,Object> priorityMap = new HashMap<String,Object>();
	/**
	 * ��ҵ�ƻ�ID
	 */
	private String id;
	/**
	 * ״̬
	 */
	private String status;
	/**
	 * ״̬Hidden
	 */
	private String statusHidden;
	/**
	 * ���������ƻ�ID
	 */
	private String uplanId;
	/**
	 * ��ҵ�ƻ�����
	 */
	private String name;
	/**
	 * ��ҵ�ƻ����
	 */
	private String no;
	/**
	 * �ƻ���ʼʱ��
	 */
	private Date planStarttime;
	/**
	 * �ƻ�����ʱ��
	 */
	private Date planEndtime;
	/**
	 * �ƻ�����
	 */
	private String planNum;
		
	/**
	 * ������ID
	 */
	private String partTypeId;
	/**
	 * ������
	 */
	private String partTypeNo;
	/**
	 * �������
	 */
	private String partTypeName;
	/**
	 * ���ͼֽ��
	 */
	private String partTypeDrawingno;
	/**
	 * ����汾��
	 */
	private String partTypeVersion;
	/**
	 * �����ƻ����
	 */
	private String productPlanNo;
	/**
	 * �����ƻ�����
	 */
	private String productPlanType;
	/**
	 * �����ƻ�����
	 */
	private String productPlanNum;
	/**
	 * �����ƻ�״̬
	 */
	private String productPlanStatus;
	/**
	 * �����ƻ�����
	 */
	private String productPlanName;
	/**
	 * ���ȼ�
	 */
	private String priority;
	/**
	 * A3--�����ƻ����
	 */
	private String proPlanNo;
	/**
	 * ��������
	 */
	private Date submitTime;
	/**
	 * �ƻ�����
	 */
	private String planType;
	/**
	 * ����ҵ�ƻ�
	 */
	private List<TJobplanInfo> pJobPlan; 
	/**
	 * ����ҵ�ƻ�id
	 */
	private String pJobPlanId;
	private String isSuccess;
	
	private String allocatedNum;//�ѷ�����������
	private List<Map<String,Object>> addTask=new ArrayList<Map<String,Object>>();//����������ʱ�洢����
	private List<Map<String,Object>> realaddTask=new ArrayList<Map<String,Object>>();//����������ʱ�洢����
	private String addTaskNo;//���������
	private String addTaskNum;//�����������
	private boolean addbool;//�Ƿ���ʾ��Ӱ�ť
	private boolean addboolqian=false;//û���������� ������Ϊ��������get���� ���κ����ݼ���ǰ ��������
	/**
	 * ����
	 */
	public JobPlanUpdataBean(){
		addbool=false;
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		List<Map<String,Object>> lst1 = jobPlanService.getPartAndForList(nodeid);	
		for(Map<String,Object> map : lst1){
			if(map.get("bid")!=null && !"".equals(map.get("bid"))){
				partMap.put((String)map.get("bname"),map.get("bid").toString());
			}
		}
	}
	
	/**
	 * �޸���ҵ�ƻ�
	 */
	public String  updataJobPlan(){

		List<Map<String,Object>> maplst = jobPlanService.getPlanAndPartList(id);
		if(maplst.size()>0){
			Map<String,Object> map = maplst.get(0);
			if(null!=map.get("planType")){
				planType=map.get("planType").toString();
				if(planType.equals("2")){
					getPTJobPlanInfo();
				}
				if(map.get("bid")!=null){
					partTypeId = map.get("bid").toString();
				}
			}
		}
		
		isSuccess=jobPlanService.updataJobPlan(this);
		
		return "jobplanupdata"; //ͨ������ҳ����ת��ҳ����Ҫ��commandButton����commandLinked�ύ����������ajax
	}
	
	/**
	 * 2���������������ϸ
	 */
	public void getPartInfo(){
		partTypeNo = null;
		partTypeName = null;
		partTypeDrawingno = null;
		partTypeVersion = null;
		
		List<Map<String,Object>> lst =jobPlanService.getPartTypeByIdFor(partTypeId);
    	if(lst.size()>0){
    		Map<String,Object>  map = lst.get(0); 		
		if(map.get("bno")!=null){
		partTypeNo = map.get("bno").toString();
		}
		if(map.get("bname")!=null){
		partTypeName = map.get("bname").toString();
		}
		if(map.get("drawingno")!=null){
		partTypeDrawingno = map.get("drawingno").toString();
		}
		if(map.get("version")!=null){
		partTypeVersion = map.get("version").toString();
		}
    	}
		
	}
	public void getPTJobPlanInfo(){
		System.out.println("planType:"+planType);
		if(null==planType||"".equals(planType)||planType.equals("1")){
			pJobPlan=new ArrayList<TJobplanInfo>();
			addbool=false;
		}else if(planType.equals("2")){
			pJobPlan=jobPlanService.getTJobplanInfoByBatchNo(planType,partTypeId);
			addbool=true;
		}else{
			pJobPlan=jobPlanService.getTJobplanInfoByBatchNo(planType,partTypeId);
			addbool=false;
		}
		
	}
	
	public void getProPlanNoValue(){
		int sum=0;
		for(Map<String,Object> map:addTask){
			sum=Integer.parseInt(null==map.get("addTaskNum")?"0":map.get("addTaskNum").toString())+sum;
		}
		allocatedNum=(Integer.parseInt(planNum)-sum)+"";//���������������
	}
	/**
	 * Ϊ����������ʱ���� �������
	 */
	public void addTaskData(){
		int i=0;//Ψһid
		int sum=0;//�ѷ�������
		boolean addbool=true; //�Ƿ��ظ�   �� ���ظ�   �� �ظ�
		for(Map<String,Object> map:addTask){
			sum=Integer.parseInt(null==map.get("addTaskNum")?"0":map.get("addTaskNum").toString())+sum;
			i=Integer.parseInt(map.get("id")+"");
			if(addTaskNo.equals(map.get("addTaskNo")+"")){
				addbool=false;
			}
		}//�ж���һ��idֵ �� ���ѷ��������
		
		if(addbool){//������ظ�
			Map<String,Object> at=new HashMap<String, Object>();
			at.put("id", (i+1)+"");//����idΨһ
			at.put("addTaskNo", addTaskNo);
			at.put("addTaskNum", addTaskNum);
			at.put("reportNum", 0);
			at.put("xs","test");
			at.put("edit","test");
			
			
			Map<String,Object> realat=new HashMap<String, Object>();
			realat.put("id", (i+1)+"");//����idΨһ
			realat.put("addTaskNo", addTaskNo);
			realat.put("addTaskNum", addTaskNum);
			realat.put("reportNum", 0);
			realat.put("xs","test");
			realat.put("edit","test");
			
			if((Integer.parseInt(planNum)-(sum+Integer.parseInt(addTaskNum)))<0){//������÷�����������  �ƻ�����
				//��ʾ�ɷ�����������
				 FacesMessage msg = new FacesMessage("��ҵ�ƻ�����","����ʧ��,�ɷ����������㣡");  
	    	     FacesContext.getCurrentInstance().addMessage(null, msg);  
			}else{
				allocatedNum=(Integer.parseInt(planNum)-(sum+Integer.parseInt(addTaskNum)))+""; //���� �������� �ɷ�����
				addTask.add(at);
				realaddTask.add(realat);
				addTaskNo="";
				addTaskNum="";
			}
		}else{
			 FacesMessage msg = new FacesMessage("��ҵ�ƻ�����","����ʧ��,ERP������ظ���");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
		
	}
	public void onRowCancel(RowEditEvent event){
		
	}
	/**
	 * Ϊ����������ʱ���� �޸�����
	 */
	@SuppressWarnings("unchecked")
	public void updateTaskData(RowEditEvent event){
		Map<String,Object> map=(Map<String,Object>)event.getObject();
		int sum=0;//�ѷ�������
		for(Map<String,Object> mp:addTask){
			sum=Integer.parseInt(null==mp.get("addTaskNum")?"0":mp.get("addTaskNum").toString())+sum;
		}//�ж� ���ѷ��������
		
		for(Map<String,Object> mm:realaddTask){
			if(mm.get("id").toString().equals(map.get("id").toString())){
				//System.out.println("addTask:"+mm.get("addTaskNo")+"--"+mm.get("addTaskNum"));
				//System.out.println("event:"+map.get("addTaskNo")+"--"+map.get("addTaskNum"));
				if((Integer.parseInt(planNum)-sum)<0){//������÷�����������  �ƻ�����
					//��ʾ�ɷ�����������
					 FacesMessage msg = new FacesMessage("��ҵ�ƻ�����","����ʧ��,�ɷ����������㣡");  
		    	     FacesContext.getCurrentInstance().addMessage(null, msg); 
		    	     map.put("addTaskNum", mm.get("addTaskNum"));
				}else if(Integer.parseInt(map.get("reportNum").toString())>Integer.parseInt(map.get("addTaskNum").toString())){//�ж� ����������Ƿ�����ϱ���
					FacesMessage msg = new FacesMessage("��ҵ�ƻ�����","����ʧ��,�ɷ�����������С���ϱ�����");  
		    	    FacesContext.getCurrentInstance().addMessage(null, msg);
		    	    map.put("addTaskNum", mm.get("addTaskNum"));
				}else{
					mm.put("addTaskNum", map.get("addTaskNum"));
				}
			}
		}
		
	}
	/**
	 * Ϊ����������ʱ���� ɾ��
	 */
	public void delTaskData(Map<String,Object> selectedTask){
		allocatedNum=(Integer.parseInt(allocatedNum)+Integer.parseInt(selectedTask.get("addTaskNum").toString()))+""; //��ɾ�����������·���� �ɷ�������
		addTask.remove(selectedTask);//ɾ������
		//realaddTask.remove(selectedTask);
	}
	/**=====================================set,get����=================================================**/
	public String getPartTypeId() {  //ˢ�µķ���λ�ú�ҳ������ķ���λ���йأ�������Ĳ����ķ�����
		
		return partTypeId;
	}
	public boolean isAddboolqian() {
		if(uplanId==null && partTypeId==null){
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if(request.getParameter("editPId")!=null && !"".equals(request.getParameter("editPId"))  && !request.getParameter("editPId").equals("undefined")){
			id = request.getParameter("editPId").trim();
			List<Map<String,Object>> maplst = jobPlanService.getPlanAndPartList(id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(maplst.size()>0){
					Map<String,Object> map = maplst.get(0);
					if(map.get("status")!=null){
						statusHidden = map.get("status").toString();
					}
					if(map.get("statusName")!=null){
						status = map.get("statusName").toString();
					}
					if(map.get("name")!=null){
						name = (String)map.get("name");
					}
					try {
						if(map.get("planStarttime")!=null){
						planStarttime = sdf.parse(map.get("planStarttime").toString());
						}
						if(map.get("planEndtime")!=null){
						planEndtime = sdf.parse(map.get("planEndtime").toString());
						}
						if(map.get("finishDate")!=null){
							submitTime = sdf.parse(map.get("finishDate").toString());
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(map.get("planNum")!=null){
						planNum = map.get("planNum").toString();
					}
					if(map.get("bid")!=null){
						partTypeId = map.get("bid").toString();
					}
					if(map.get("bno")!=null){
						partTypeNo = map.get("bno").toString();
					}
					if(map.get("bname")!=null){
						partTypeName = map.get("bname").toString();
					}
					if(map.get("drawingno")!=null){
						partTypeDrawingno = map.get("drawingno").toString();
					}
					if(map.get("version")!=null){
						partTypeVersion = map.get("version").toString();
					}
					if(map.get("planNo")!=null){
						proPlanNo = map.get("planNo").toString();
					}
					
					if(map.get("priority")!=null){
						priority = map.get("priority").toString();
					}
					if(map.get("no")!=null){
						no = map.get("no").toString();
					}
					if(null!=map.get("planType")){
						planType=map.get("planType").toString();
						if(planType.equals("2")){
							getPTJobPlanInfo();
						}
					}
					if(null!=map.get("pid")){
						pJobPlanId=map.get("pid").toString();
					}
					
					addTask=jobPlanService.getTJobplanTaskInfo(map.get("id")+""); //���ط�������
					realaddTask=jobPlanService.getTJobplanTaskInfo(map.get("id")+"");
					int sum=0;
					for(Map<String,Object> mm:addTask){
						sum=Integer.parseInt(null==mm.get("addTaskNum")?"0":mm.get("addTaskNum").toString())+sum;
						/*if(mm.get("addTaskNo").toString().equals("test003")){//����������
							mm.put("edit", null);
						}*/
					}
					allocatedNum=(Integer.parseInt(planNum)-sum)+"";//���������������
					if(planType.equals("2")){
						addbool=true;
					}
				}
			}
	    }
		return addboolqian;
	}

	public String getIsSuccess() {
		
		return isSuccess;
	}
	
	public IJobPlanService getJobPlanService() {
		return jobPlanService;
	}

	public void setJobPlanService(IJobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
	}

	public Map<String, Object> getJobPlanMap() {
		return jobPlanMap;
	}

	public void setJobPlanMap(Map<String, Object> jobPlanMap) {
		this.jobPlanMap = jobPlanMap;
	}

	public Map<String, Object> getProducePlanMap() {
		return producePlanMap;
	}

	public void setProducePlanMap(Map<String, Object> producePlanMap) {
		this.producePlanMap = producePlanMap;
	}

	public Map<String, Object> getPartMap() {
		return partMap;
	}

	public void setPartMap(Map<String, Object> partMap) {
		this.partMap = partMap;
	}

	public Map<String, Object> getPriorityMap() {
		return priorityMap;
	}

	public void setPriorityMap(Map<String, Object> priorityMap) {
		this.priorityMap = priorityMap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getUplanId() {
		return uplanId;
	}

	public void setUplanId(String uplanId) {
		this.uplanId = uplanId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPlanStarttime() {
		return planStarttime;
	}

	public void setPlanStarttime(Date planStarttime) {
		this.planStarttime = planStarttime;
	}

	public Date getPlanEndtime() {
		return planEndtime;
	}

	public void setPlanEndtime(Date planEndtime) {
		this.planEndtime = planEndtime;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}
	
	

	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPartTypeNo() {
		return partTypeNo;
	}

	public void setPartTypeNo(String partTypeNo) {
		this.partTypeNo = partTypeNo;
	}

	public String getPartTypeName() {
		return partTypeName;
	}

	public void setPartTypeName(String partTypeName) {
		this.partTypeName = partTypeName;
	}

	public String getPartTypeDrawingno() {
		return partTypeDrawingno;
	}

	public void setPartTypeDrawingno(String partTypeDrawingno) {
		this.partTypeDrawingno = partTypeDrawingno;
	}

	public String getPartTypeVersion() {
		return partTypeVersion;
	}

	public void setPartTypeVersion(String partTypeVersion) {
		this.partTypeVersion = partTypeVersion;
	}

	public String getProductPlanNo() {
		return productPlanNo;
	}

	public void setProductPlanNo(String productPlanNo) {
		this.productPlanNo = productPlanNo;
	}

	public String getProductPlanType() {
		return productPlanType;
	}

	public void setProductPlanType(String productPlanType) {
		this.productPlanType = productPlanType;
	}

	public String getProductPlanNum() {
		return productPlanNum;
	}

	public void setProductPlanNum(String productPlanNum) {
		this.productPlanNum = productPlanNum;
	}

	public String getProductPlanStatus() {
		return productPlanStatus;
	}

	public void setProductPlanStatus(String productPlanStatus) {
		this.productPlanStatus = productPlanStatus;
	}

	public String getProductPlanName() {
		return productPlanName;
	}

	public void setProductPlanName(String productPlanName) {
		this.productPlanName = productPlanName;
	}

	public String getProPlanNo() {
		return proPlanNo;
	}

	public void setProPlanNo(String proPlanNo) {
		this.proPlanNo = proPlanNo;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public List<TJobplanInfo> getpJobPlan() {
		return pJobPlan;
	}

	public void setpJobPlan(List<TJobplanInfo> pJobPlan) {
		this.pJobPlan = pJobPlan;
	}

	public String getpJobPlanId() {
		return pJobPlanId;
	}

	public void setpJobPlanId(String pJobPlanId) {
		this.pJobPlanId = pJobPlanId;
	}

	

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getAllocatedNum() {
		return allocatedNum;
	}

	public void setAllocatedNum(String allocatedNum) {
		this.allocatedNum = allocatedNum;
	}

	public List<Map<String, Object>> getAddTask() {
		return addTask;
	}

	public void setAddTask(List<Map<String, Object>> addTask) {
		this.addTask = addTask;
	}

	public String getAddTaskNo() {
		return addTaskNo;
	}

	public void setAddTaskNo(String addTaskNo) {
		this.addTaskNo = addTaskNo;
	}

	public String getAddTaskNum() {
		return addTaskNum;
	}

	public void setAddTaskNum(String addTaskNum) {
		this.addTaskNum = addTaskNum;
	}

	public boolean isAddbool() {
		return addbool;
	}

	public void setAddbool(boolean addbool) {
		this.addbool = addbool;
	}

	
	public void setAddboolqian(boolean addboolqian) {
		this.addboolqian = addboolqian;
	}

	public List<Map<String, Object>> getRealaddTask() {
		return realaddTask;
	}

	public void setRealaddTask(List<Map<String, Object>> realaddTask) {
		this.realaddTask = realaddTask;
	}

	public String getStatusHidden() {
		return statusHidden;
	}

	public void setStatusHidden(String statusHidden) {
		this.statusHidden = statusHidden;
	}
	
}
