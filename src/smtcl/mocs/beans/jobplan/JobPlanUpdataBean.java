package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;

/**
 * 
 * ��ҵ�ƻ��༭Bean
 * @���� yyh
 * @����ʱ�� 2013-7-2 ����13:05:16
 * @�޸��� songkaiang
 * @�޸����� 2015-212
 * @�޸�˵�� ��̨���ݹ��ʻ�
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
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * ������Ƽ�
	 */
	private Map<String,Object> partMap = new HashMap<String,Object>();
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
	private List<TJobplanInfo> pJobPlan = new ArrayList<TJobplanInfo>();
	/**
	 * ����ҵ�ƻ�id
	 */
	private String pJobPlanId;
	private String isSuccess;
	
	private String allocatedNum;//�ѷ�����������
	private List<Map<String,Object>> addTask=new ArrayList<Map<String,Object>>();//����������ʱ�洢����
	private List<Map<String,Object>> realaddTask=new ArrayList<Map<String,Object>>();//����������ʱ�洢����
//	private boolean addboolqian=false;//û���������� ������Ϊ��������get���� ���κ����ݼ���ǰ ��������
    private List<Map<String,Object>> jobplanType = new ArrayList<Map<String,Object>>();
    /**
	 * ����
	 */
	public JobPlanUpdataBean(){
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
		List<Map<String,Object>> lst =jobPlanService.getPartTypeByIdFor(partTypeId);
    	if(lst.size()>0){
    		Map<String,Object>  map = lst.get(0); 		
		if(map.get("bno")!=null){
		partTypeNo = map.get("bno").toString();
		}
    	}
		
	}
	public void getPTJobPlanInfo(){
        if(null==planType||"".equals(planType)||planType.equals("1")){
            pJobPlan=new ArrayList<TJobplanInfo>();
            this.putJobPlan();
        }else if(planType.equals("2")){
            this.putJobPlan();
            pJobPlan.addAll(jobPlanService.getTJobplanInfoByBatchNo(planType, partTypeId));
        }else{
            pJobPlan=jobPlanService.getTJobplanInfoByBatchNo(planType,partTypeId);
        }
		
	}
	
	public void getProPlanNoValue(){
		int sum=0;
		for(Map<String,Object> map:addTask){
			sum=Integer.parseInt(null==map.get("addTaskNum")?"0":map.get("addTaskNum").toString())+sum;
		}
		allocatedNum=(Integer.parseInt(planNum)-sum)+"";//���������������
	}

	/**=====================================set,get����=================================================**/
	public String getPartTypeId() {  //ˢ�µķ���λ�ú�ҳ������ķ���λ���йأ�������Ĳ����ķ�����
		
		return partTypeId;
	}
	public boolean isAddboolqian() {
		if(partTypeId==null){
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
                        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")) {
                            String key = map.get("statusName").toString();
                            Map<String, String> statusMap = Constants.statusMap;
                            status = statusMap.get(key);
                        }else{
                            status = map.get("statusName").toString();
                        }

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
				}
			}
	    }
		return false;
	}

	public String getIsSuccess() {
		
		return isSuccess;
	}

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }

    private void putJobPlan(){
        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
            pJobPlan.clear();
            TJobplanInfo t = new TJobplanInfo();
            t.setId(0L);
            t.setName("Select");
            pJobPlan.add(0,t);
        }else{
            pJobPlan.clear();
            TJobplanInfo t = new TJobplanInfo();
            t.setId(0L);
            t.setName("��ѡ��");
            pJobPlan.add(0,t);
        }
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

    public List<Map<String, Object>> getJobplanType() {
        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("label","Select");
            map1.put("value","");
            jobplanType.add(map1);
            Map<String,Object> map2 = new HashMap<String,Object>();
            map2.put("label","Production Plan");
            map2.put("value","1");
            jobplanType.add(map2);
            Map<String,Object> map3 = new HashMap<String,Object>();
            map3.put("label","Batch Plan");
            map3.put("value","2");
            jobplanType.add(map3);
        }else{
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("label","��ѡ��");
            map1.put("value","");
            jobplanType.add(map1);
            Map<String,Object> map2 = new HashMap<String,Object>();
            map2.put("label","��ҵ�ƻ�");
            map2.put("value","1");
            jobplanType.add(map2);
            Map<String,Object> map3 = new HashMap<String,Object>();
            map3.put("label","���μƻ�");
            map3.put("value","2");
            jobplanType.add(map3);
        }
        return jobplanType;
    }

    public void setJobplanType(List<Map<String, Object>> jobplanType) {
        this.jobplanType = jobplanType;
    }

	public Map<String, Object> getPartMap() {
        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
            partMap.put("Select","");
        }else{
            partMap.put("��ѡ��","");
        }
		return partMap;
	}

	public void setPartMap(Map<String, Object> partMap) {
		this.partMap = partMap;
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

	public String getProPlanNo() {
		return proPlanNo;
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

	public List<Map<String, Object>> getRealaddTask() {
		return realaddTask;
	}


	public String getStatusHidden() {
		return statusHidden;
	}

	public void setStatusHidden(String statusHidden) {
		this.statusHidden = statusHidden;
	}
	
}
