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

/**

 * 
 * ��ҵ�ƻ����Bean
 * @���� yyh
 * @����ʱ�� 2013-7-2 ����13:05:16
 * @�޸��� songkaiang
 * @�޸�����
 * @�޸�˵��
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
    private String status;
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
	private List<TJobplanInfo> pJobPlan = new ArrayList<TJobplanInfo>();
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
    private List<Map<String,Object>> jobplanType = new ArrayList<Map<String,Object>>();


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
            if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
                status = "New";
            }else{
                status="�½�";
            }

        }
		//��ҵ�ƻ����	
		List<Map<String,Object>> partlst =  jobPlanService.getPartTypeByIdFor(partTypeId);
		if(partlst.size()>0){
//			Map<String,Object> m = partlst.get(0);
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
		if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
			isSuccess="Failed to add";
		}else{
			isSuccess="���ʧ��";
		}
		
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

	private String show; //����ŷ�����ʾ��Ϣ
	/**
	 * ����ŷ���
	 */
	public void getShowInfo(){
		List<Map<String,Object>> lst = jobPlanService.getPlanByName(no);
        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
            if(lst.size()==0){ //������
                show = "ID available";
            }else{             //�Ѵ���
                show ="ID is not available";
            }
        }else{
            if(lst.size()==0){ //������
                show = "��Ų�����,����";
            }else{             //�Ѵ���
                show ="����Ѵ���,������";
            }
        }

	}
	
	
	/**
	 * ��ҵ�ƻ���ŵ�ģ����ѯ
	 * @param query ����ƻ����
	 * @return ���ؼƻ���ҵ����б�
	 */
    public List<String> complete(String query) {
        return jobPlanService.getTJobplanInfoNo(query);
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

	public void getPTJobPlanInfo(){
		if(null==planType||"".equals(planType)||planType.equals("1")){
            this.putJobPlan();
			pJobPlan=new ArrayList<TJobplanInfo>();
			addbool=false;
		}else if(planType.equals("2")){
            this.putJobPlan();
			pJobPlan.addAll(jobPlanService.getTJobplanInfoByBatchNo(planType, partTypeId));
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

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }


    /**=====================================set,get����=================================================**/

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
        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
            Map<String,Object> newMap = new HashMap<String,Object>();
            newMap.put("bid","");
            newMap.put("bname","Select");
            partMap.add(0,newMap);
        }else{
            Map<String,Object> newMap = new HashMap<String,Object>();
            newMap.put("bid","");
            newMap.put("bname","��ѡ��");
            partMap.add(0,newMap);
        }
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
        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
            status = "Created";
        }else{
            status="�½�";
        }
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


    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
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

    public List<Map<String, Object>> getRealaddTask() {
        return realaddTask;
    }

}
