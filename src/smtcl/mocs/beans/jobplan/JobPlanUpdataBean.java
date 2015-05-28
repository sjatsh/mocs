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
 * 作业计划编辑Bean
 * @作者：yyh
 * @创建时间：2013-7-2 下午13:05:16
 * @修改者：songkaiang
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="JobPlanUpdata")
@ViewScoped
public class JobPlanUpdataBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
    /**
     * 用于页面传值
     */
	private Map<String,Object> jobPlanMap = new HashMap<String,Object>();
	/**
	 * 生产计划集
	 */
	private Map<String,Object> producePlanMap = new HashMap<String,Object>(); 
	/**
	 * 零件名称集
	 */
	private Map<String,Object> partMap = new HashMap<String,Object>();
	/**
	 * 优先级
	 */
	private Map<String,Object> priorityMap = new HashMap<String,Object>();
	/**
	 * 作业计划ID
	 */
	private String id;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 状态Hidden
	 */
	private String statusHidden;
	/**
	 * 从属生产计划ID
	 */
	private String uplanId;
	/**
	 * 作业计划名称
	 */
	private String name;
	/**
	 * 作业计划编号
	 */
	private String no;
	/**
	 * 计划开始时间
	 */
	private Date planStarttime;
	/**
	 * 计划结束时间
	 */
	private Date planEndtime;
	/**
	 * 计划数量
	 */
	private String planNum;
		
	/**
	 * 零件编号ID
	 */
	private String partTypeId;
	/**
	 * 零件编号
	 */
	private String partTypeNo;
	/**
	 * 零件名称
	 */
	private String partTypeName;
	/**
	 * 零件图纸号
	 */
	private String partTypeDrawingno;
	/**
	 * 零件版本号
	 */
	private String partTypeVersion;
	/**
	 * 生产计划编号
	 */
	private String productPlanNo;
	/**
	 * 生产计划类型
	 */
	private String productPlanType;
	/**
	 * 生产计划数量
	 */
	private String productPlanNum;
	/**
	 * 生产计划状态
	 */
	private String productPlanStatus;
	/**
	 * 生产计划名称
	 */
	private String productPlanName;
	/**
	 * 优先级
	 */
	private String priority;
	/**
	 * A3--生产计划编号
	 */
	private String proPlanNo;
	/**
	 * 交货日期
	 */
	private Date submitTime;
	/**
	 * 计划类型
	 */
	private String planType;
	/**
	 * 父作业计划
	 */
	private List<TJobplanInfo> pJobPlan; 
	/**
	 * 父作业计划id
	 */
	private String pJobPlanId;
	private String isSuccess;
	
	private String allocatedNum;//已分配任务数量
	private List<Map<String,Object>> addTask=new ArrayList<Map<String,Object>>();//分配任务临时存储数据
	private List<Map<String,Object>> realaddTask=new ArrayList<Map<String,Object>>();//分配任务临时存储数据
	private String addTaskNo;//添加任务编号
	private String addTaskNum;//添加任务数量
	private boolean addbool;//是否显示添加按钮
	private boolean addboolqian=false;//没有其他作用 纯粹是为了利用其get方法 在任何数据加载前 加载数据
	/**
	 * 构造
	 */
	public JobPlanUpdataBean(){
		addbool=false;
		//获取节点ID
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
	 * 修改作业计划
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
		
		return "jobplanupdata"; //通过配置页面跳转，页面需要用commandButton或者commandLinked提交，不能是用ajax
	}
	
	/**
	 * 2级联动零件类型详细
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
		allocatedNum=(Integer.parseInt(planNum)-sum)+"";//计算待分配任务数
	}
	/**
	 * 为分配任务临时数据 添加数据
	 */
	public void addTaskData(){
		int i=0;//唯一id
		int sum=0;//已分配总数
		boolean addbool=true; //是否重复   是 不重复   否 重复
		for(Map<String,Object> map:addTask){
			sum=Integer.parseInt(null==map.get("addTaskNum")?"0":map.get("addTaskNum").toString())+sum;
			i=Integer.parseInt(map.get("id")+"");
			if(addTaskNo.equals(map.get("addTaskNo")+"")){
				addbool=false;
			}
		}//判断下一个id值 和 总已分配的数量
		
		if(addbool){//如果不重复
			Map<String,Object> at=new HashMap<String, Object>();
			at.put("id", (i+1)+"");//设置id唯一
			at.put("addTaskNo", addTaskNo);
			at.put("addTaskNum", addTaskNum);
			at.put("reportNum", 0);
			at.put("xs","test");
			at.put("edit","test");
			
			
			Map<String,Object> realat=new HashMap<String, Object>();
			realat.put("id", (i+1)+"");//设置id唯一
			realat.put("addTaskNo", addTaskNo);
			realat.put("addTaskNum", addTaskNum);
			realat.put("reportNum", 0);
			realat.put("xs","test");
			realat.put("edit","test");
			
			if((Integer.parseInt(planNum)-(sum+Integer.parseInt(addTaskNum)))<0){//如果设置分配数量大于  计划数量
				//提示可分配数量不足
				 FacesMessage msg = new FacesMessage("作业计划新增","新增失败,可分配数量不足！");  
	    	     FacesContext.getCurrentInstance().addMessage(null, msg);  
			}else{
				allocatedNum=(Integer.parseInt(planNum)-(sum+Integer.parseInt(addTaskNum)))+""; //否则 正常计算 可分配数
				addTask.add(at);
				realaddTask.add(realat);
				addTaskNo="";
				addTaskNum="";
			}
		}else{
			 FacesMessage msg = new FacesMessage("作业计划新增","新增失败,ERP任务号重复！");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
		
	}
	public void onRowCancel(RowEditEvent event){
		
	}
	/**
	 * 为分配任务临时数据 修改数据
	 */
	@SuppressWarnings("unchecked")
	public void updateTaskData(RowEditEvent event){
		Map<String,Object> map=(Map<String,Object>)event.getObject();
		int sum=0;//已分配总数
		for(Map<String,Object> mp:addTask){
			sum=Integer.parseInt(null==mp.get("addTaskNum")?"0":mp.get("addTaskNum").toString())+sum;
		}//判断 总已分配的数量
		
		for(Map<String,Object> mm:realaddTask){
			if(mm.get("id").toString().equals(map.get("id").toString())){
				//System.out.println("addTask:"+mm.get("addTaskNo")+"--"+mm.get("addTaskNum"));
				//System.out.println("event:"+map.get("addTaskNo")+"--"+map.get("addTaskNum"));
				if((Integer.parseInt(planNum)-sum)<0){//如果设置分配数量大于  计划数量
					//提示可分配数量不足
					 FacesMessage msg = new FacesMessage("作业计划新增","新增失败,可分配数量不足！");  
		    	     FacesContext.getCurrentInstance().addMessage(null, msg); 
		    	     map.put("addTaskNum", mm.get("addTaskNum"));
				}else if(Integer.parseInt(map.get("reportNum").toString())>Integer.parseInt(map.get("addTaskNum").toString())){//判断 任务分配数是否大于上报数
					FacesMessage msg = new FacesMessage("作业计划新增","新增失败,可分配数量不能小于上报数！");  
		    	    FacesContext.getCurrentInstance().addMessage(null, msg);
		    	    map.put("addTaskNum", mm.get("addTaskNum"));
				}else{
					mm.put("addTaskNum", map.get("addTaskNum"));
				}
			}
		}
		
	}
	/**
	 * 为分配任务临时数据 删除
	 */
	public void delTaskData(Map<String,Object> selectedTask){
		allocatedNum=(Integer.parseInt(allocatedNum)+Integer.parseInt(selectedTask.get("addTaskNum").toString()))+""; //将删除的数量重新分配给 可分配数量
		addTask.remove(selectedTask);//删除数据
		//realaddTask.remove(selectedTask);
	}
	/**=====================================set,get方法=================================================**/
	public String getPartTypeId() {  //刷新的放置位置和页面参数的放置位置有关，最上面的参数的方法中
		
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
					
					addTask=jobPlanService.getTJobplanTaskInfo(map.get("id")+""); //加载分配任务
					realaddTask=jobPlanService.getTJobplanTaskInfo(map.get("id")+"");
					int sum=0;
					for(Map<String,Object> mm:addTask){
						sum=Integer.parseInt(null==mm.get("addTaskNum")?"0":mm.get("addTaskNum").toString())+sum;
						/*if(mm.get("addTaskNo").toString().equals("test003")){//纯属测试用
							mm.put("edit", null);
						}*/
					}
					allocatedNum=(Integer.parseInt(planNum)-sum)+"";//计算待分配任务数
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
