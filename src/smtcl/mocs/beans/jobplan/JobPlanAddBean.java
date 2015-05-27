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

import smtcl.mocs.pojos.device.TUserProdctionPlan;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.jobplan.IJobPlanService;

/**
 * 
 * ��ҵ�ƻ����Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-7-2 ����13:05:16
 * @�޸��ߣ�songkaiang
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="JobPlanAdd")
@ViewScoped
public class JobPlanAddBean implements Serializable {
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
    /**
     * ����ҳ�洫ֵ
     */
	private Map<String,Object> jobPlanMap = new HashMap<String,Object>();
	/**
	 * �����ƻ���
	 */
	private Map<String,Object> producePlanMap = new HashMap<String,Object>(); //��������ĸСд
	/**
	 * ������Ƽ�
	 */
	private List<Map<String,Object>> partMap = new ArrayList<Map<String,Object>>();
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
	private String status="�½�";
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
	 * ҳ��ֵ���
	 */
	private String valueToNul;	
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
	private boolean addbool=false;//�Ƿ���ʾ��Ӱ�ť
	/**
	 * ����
	 */
	public JobPlanAddBean(){
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		List<Map<String,Object>> lst = jobPlanService.getPlanAndForList(nodeid);//�����ƻ������б�
		for(Map<String,Object> map : lst){
			if(map.get("cid")!=null && !"".equals(map.get("cid"))){
			    producePlanMap.put((String)map.get("uplanName"),map.get("cid").toString());
			}
		}	
		partMap = jobPlanService.getPartAndForList(nodeid);	//�������������
		//�������ID
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("addpNo")!=null && !"".equals(request.getParameter("addpNo"))  && !request.getParameter("addpNo").equals("undefined")){
			partTypeId = request.getParameter("addpNo").trim();
			
			List<Map<String,Object>> lstpart =jobPlanService.getPartTypeByIdFor(partTypeId);
	    	if(lstpart.size()>0){
	    		Map<String,Object>  map = lstpart.get(0); 		
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
	    	status="�½�";
        }
		//��ҵ�ƻ����	
		List<Map<String,Object>> partlst =  jobPlanService.getPartTypeByIdFor(partTypeId);
		if(partlst.size()>0){
			Map<String,Object> m = partlst.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd  = sdf.format(new Date());
//			no ="WP_"+(String)m.get("bno")+"_"+dd+"_"+m.get("bid").toString();
			no ="WP_"+dd;
		}
		addTask=new ArrayList<Map<String,Object>>();
		realaddTask=new ArrayList<Map<String,Object>>();
	}
	
	private String dialog;
	/**
	 * �����ҵ�ƻ�
	 */
	public String addJobAction(){	
		isSuccess="���ʧ��";
		if(no==null || "".equals(no)){
			int  radnum = (int)(Math.random()*1000000);  //����6λ�����
			if(radnum<99999){
				radnum = radnum+100000;
			}
			String autoNo  = String.valueOf(radnum);	
			no = "WP"+autoNo;   //��ҵ�ƻ����NO�����룬�Զ����ɸ���
		}
		List<Map<String,Object>> lst = jobPlanService.getPlanByName(no);	
	    if(lst.size()==0){   //�����Ƶ���ҵ�ƻ������ڣ��ύ
	    	isSuccess=jobPlanService.addJobPlan(this);	
	    }else{
	    	dialog = "show";
	    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    	request.setAttribute("dialog", dialog);
	    }
	    return "jobplanadd";
	}
	
	/**
	 * �õ���ҵ�ƻ����
	 */
	public void getJobplanNo(){
		List<Map<String,Object>> partlst =  jobPlanService.getPartTypeByIdFor(partTypeId);
		String maxId  = jobPlanService.getMaxJobPlanId();
		if(partlst.size()>0){
			Map<String,Object> m = partlst.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dd  = sdf.format(new Date());
			no ="WP_"+(String)m.get("bno")+"_"+dd+"_"+maxId;
		}
	}
	
	private String show; //����ŷ�����ʾ��Ϣ
	/**
	 * ����ŷ���
	 */
	public void getShowInfo(){
		List<Map<String,Object>> lst = jobPlanService.getPlanByName(no);
		if(lst.size()==0){ //������
			show = "��Ų�����,����";
		}else{             //�Ѵ���
			show ="����Ѵ���,������";
		}
	}
	
	
	/**
	 * ��ҵ�ƻ���ŵ�ģ����ѯ
	 * @param query
	 * @return
     * @throws and @exception
	 */
    public List<String> complete(String query) {   
        List<String> results = jobPlanService.getTJobplanInfoNo(query);   
        return results;   
    } 
	
	/**
	 * 2�����������ƻ���ϸ
	 */
    public void getJobplanInfo(){
    	productPlanNo = null;
    	productPlanType = null;
    	productPlanNum = null;
    	productPlanStatus = null;
    	productPlanName = null;
    	
    	List<Map<String,Object>> lst =jobPlanService.getJobplanByIdFor(uplanId);
    	if(lst.size()>0){
    		Map<String,Object>  map = lst.get(0);    	
		if(map.get("uplanNo")!=null){
		productPlanNo = map.get("uplanNo").toString();
		}
		if(map.get("uplanType")!=null){
		productPlanType = map.get("uplanType").toString();
		}
		if(map.get("uplanNum")!=null){
		productPlanNum = map.get("uplanNum").toString();
		}
		if(map.get("uplanStatus")!=null){
		productPlanStatus = map.get("uplanStatus").toString();
		}
		if(map.get("uplanName")!=null){
		productPlanName = map.get("uplanName").toString();
		}
     }
    	
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
			
			Map<String,Object> realat=new HashMap<String, Object>();
			realat.put("id", (i+1)+"");//����idΨһ
			realat.put("addTaskNo", addTaskNo);
			realat.put("addTaskNum", addTaskNum);
			realat.put("reportNum", 0);
			
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
	public void updateTaskData(RowEditEvent event){
		Map<String,Object> map=(Map<String,Object>)event.getObject();
		int sum=0;//�ѷ�������
		for(Map<String,Object> mp:addTask){
			sum=Integer.parseInt(null==mp.get("addTaskNum")?"0":mp.get("addTaskNum").toString())+sum;
		}//�ж� ���ѷ��������
		
		for(Map<String,Object> mm:realaddTask){
			if(mm.get("id").toString().equals(map.get("id").toString())){
				System.out.println("addTask:"+mm.get("addTaskNo")+"--"+mm.get("addTaskNum"));
				System.out.println("event:"+map.get("addTaskNo")+"--"+map.get("addTaskNum"));
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
		realaddTask.remove(selectedTask);
	}
	
	/**=====================================set,get����=================================================**/
	
	

	public String getDialog() {
		
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	dialog = (String)request.getAttribute("dialog");
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
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

	public List<Map<String, Object>> getPartMap() {
		return partMap;
	}

	public void setPartMap(List<Map<String, Object>> partMap) {
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
		status="�½�";
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
	
	public String getPartTypeId() {
		if(uplanId==null && partTypeId==null){
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if (request.getParameter("planId") != null
					&& !"".equals(request.getParameter("planId"))
					&& !request.getParameter("planId").equals("undefined")) {
				id = request.getParameter("planId").trim();
				initDataByPlanId();
			}
			
			if(request.getParameter("partId") != null
					&& !"".equals(request.getParameter("partId"))
					&& !request.getParameter("partId").equals("undefined")){
				
				partTypeId = request.getParameter("partId").trim();
				planType = "1";//��ҵ�ƻ�
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
				String dd  = format.format(new Date());
				no ="WP_"+dd;
				name = "WP_"+dd;
				priority = "1";
			}
			
		}
		
		return partTypeId;
	}

	private void initDataByPlanId() {
		if(id!=null && !id.isEmpty()){
			List<Map<String,Object>> maplst = jobPlanService.getPlanAndPartList(id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(maplst.size()>0){
				Map<String,Object> map = maplst.get(0);
				if(map.get("status")!=null){
					status = map.get("status").toString();
				}
				try {
					if(map.get("planStarttime")!=null){
					planStarttime = sdf.parse(map.get("planStarttime").toString());
					}
					if(map.get("planEndtime")!=null){
					planEndtime = sdf.parse(map.get("planEndtime").toString());
					}
				} catch (ParseException e) {
					e.printStackTrace();
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
				//��ҵ�ƻ����	
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
				String dd  = format.format(new Date());
				no ="WP_"+dd;
				name = "WP_"+dd;
				if(null!=map.get("planType")){
					planType="2";
					if(planType.equals("2")){
						getPTJobPlanInfo();
					}
				}
				if(map.get("id") != null){
					pJobPlanId = map.get("id").toString();
				}
			}
		}
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
    /**
     * ����ҳ������ҳ���ֵ
     * @return
     */
	public String getValueToNul() {
		id = null;
		status = null;
		uplanId = null;
		name = null;
		planStarttime = null;
		planEndtime = null;
		planNum = null;
//		partTypeId = null;
		priority = null;
		return valueToNul;
	}

	public void setValueToNul(String valueToNul) {
		this.valueToNul = valueToNul;
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

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
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

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
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

	public String getAllocatedNum() {
		return allocatedNum;
	}

	public void setAllocatedNum(String allocatedNum) {
		this.allocatedNum = allocatedNum;
	}

	public boolean isAddbool() {
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request.getParameter("planId") != null
				&& !"".equals(request.getParameter("planId"))
				&& !request.getParameter("planId").equals("undefined")) {
			addbool=true;
		}
		return addbool;
	}

	public void setAddbool(boolean addbool) {
		this.addbool = addbool;
	}

	public List<Map<String, Object>> getRealaddTask() {
		return realaddTask;
	}

	public void setRealaddTask(List<Map<String, Object>> realaddTask) {
		this.realaddTask = realaddTask;
	}

}
