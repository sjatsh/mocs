package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.TableDataModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 
 */
@ManagedBean(name="JobdispatchNumEdit")
@ViewScoped
public class JobdispatchNumEditBean implements Serializable {
	
	/**
	 * 零件名称集合
	 */
	private List<Map<String,Object>> partTypeList = new ArrayList<Map<String,Object>>();
	/**
	 * 批次No集合
	 */
	private List<Map<String,Object>> jobPlanNoList = new ArrayList<Map<String,Object>>();
	/**
	 * 工单No集合
	 */
	private List<Map<String,Object>> jobdispatchNoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 批次信息
	 */
	private List<Map<String,Object>> jobPlanList = new ArrayList<Map<String,Object>>();
	/**
	 * 综合信息（设备，部件，工单）
	 */
	private List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
	/**
	 * 用户名称集
	 */
	private List<Map<String,Object>> userMap = new ArrayList<Map<String,Object>>();
	/**
	 * 标志
	 */
	private Boolean flag =false;
	private String isSuccess;
	private String noteId;
	private Map<String, Object> selectValue; 
	/**
	 * 部件类型ID
	 */
	private String partTpyeId;
	/**
	 * 部件类型名称
	 */
	private String partTpyeName;
	/**
	 * 批次ID
	 */
	private String jobplanId;
	/**
	 * 批次No
	 */
	private String jobplanNo;
	/**
	 * 工单ID
	 */
	private String jobdispatchId;
	/**
	 * 批次投料数
	 */
	private String jobplanNum;
	/**
	 * 工单编号
	 */
	private String jobdispatchNo;
	/**
	 * 工序Id
	 */
	private String processId;
	/**
	 * 工序No
	 */
	private String processNo;
	/**
	 * 工序顺号
	 */
	private String processOrder;
	/**
	 * 报工人员id
	 */
	private String userId;
	
	/**
	 * 登录人员No
	 */
	private String loginUserNo;
	
	/**
	 * 报工人员No
	 */
	private String userNo;
	/**
	 * 报工人员
	 */
	private String userName;
	/**
	 * 设备Id
	 */
	private String equId;
	/**
	 * 设备No
	 */
	private String equNo;
	/**
	 * 设备名称
	 */
	private String equName;
	/**
	 * 修改数量
	 */
	private String changeNum;
	/**
	 * 日期
	 */
	private Date changeDateTime;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 工单号
	 */
	private String jobDispatchNo;
	/**
	 * 报工单号
	 */
	private String eventNo;
	private IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	
	
	
	public JobdispatchNumEditBean() {
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		noteId = (String)session.getAttribute("nodeid");
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("jobdispatchId")!=null && !"".equals(request.getParameter("jobdispatchId"))){
			flag =true;
			jobdispatchId = request.getParameter("jobdispatchId").trim();
			
			jobdispatchNoList = deviceService.getDispatchNoMap();
			//获取单个工单信息
			List<Map<String,Object>> jobdispatchInfo = deviceService.getJobDispatch(jobdispatchId);
			if(jobdispatchInfo.size()>0){
				if(jobdispatchInfo.get(0).get("jobplanID")!=null && jobdispatchInfo.get(0).get("jobplanID").toString()!=""){
					jobplanId = jobdispatchInfo.get(0).get("jobplanID").toString();
				}
				
				jobplanNo = jobdispatchInfo.get(0).get("taskNum").toString();
				processId = jobdispatchInfo.get(0).get("processID").toString();
				jobDispatchNo = jobdispatchInfo.get(0).get("no").toString();
				
				List<Map<String,Object>> processInfo = deviceService.getProcessInfo(processId);//工序信息
				if(processInfo.size()>0){
					processNo = processInfo.get(0).get("no").toString();
					partTpyeId = processInfo.get(0).get("partId").toString();
					processOrder = processInfo.get(0).get("process_order").toString();
				}
			}
			jobPlanNoList = deviceService.getJobPlanNoList(partTpyeId,jobplanId);
			if(jobPlanNoList.size()>0){
//				partTpyeId = jobPlanNoList.get(0).get("partID").toString();
				jobplanId = jobPlanNoList.get(0).get("id").toString();
				jobplanNo = jobPlanNoList.get(0).get("no").toString();
				jobplanNum = jobPlanNoList.get(0).get("planNum").toString();
			}
			//jobPlanNoList = deviceService.getJobPlanNoList(partTpyeId);
			//partTypeList = deviceService.getPartTypeMap(partTpyeId,"");
			partTypeList = deviceService.getListInfo("select t_part_type_info.id as id,t_part_type_info.name as name from t_jobdispatchlist_info"
					+ " inner join t_process_info on t_process_info.id = t_jobdispatchlist_info.processID"
					+ " inner join t_processplan_info on t_processplan_info.id = t_process_info.processPlanID"
					+ " inner join t_part_type_info on t_part_type_info.id = t_processplan_info.parttypeID"
					+ " where t_jobdispatchlist_info.id ="+jobdispatchId+" "
							+ " and t_part_type_info.nodeid ='"+noteId+"' ");
			if(partTypeList.size()>0){
				partTpyeName = partTypeList.get(0).get("name").toString(); 
				partTpyeId = partTypeList.get(0).get("id").toString();
			}
		}else{
			partTypeList = deviceService.getPartTypeMap("",noteId);
		}
		//dataList = deviceService.queryEquipmentList("", "");
		//for(Map<String,Object> dd:dataList){
		//    String equSerialNo = dd.get("no").toString();
		//	dd.put("", "");
		//} 
		//用户名绑定
		TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		 String id= user.getUserId();
		 List<Map<String, Object>> list = deviceService.getMemberInfo(id,noteId);
		 if(list!=null&&list.size()>0){
		   userId = list.get(0).get("id").toString();
		   userName = list.get(0).get("name").toString();
		   loginUserNo = list.get(0).get("no").toString();
		 }
		 userMap = deviceService.getUserList("",noteId);
	}
	
	 public void onRowSelect(SelectEvent event) {
		 Map<String,Object> selectedUser =(Map<String,Object>) event.getObject();
		 
		 equId = selectedUser.get("equId").toString();
		 equNo = selectedUser.get("no").toString();
		 equName = selectedUser.get("name").toString();
	    }
	 /**
	  * 数据信息验证
	  */
	 public void checkDataInfo(){
		 isSuccess ="信息验证失败";
		 if(null == equNo || equNo==""){
			 return;
		 }
		 if(null == partTpyeId || partTpyeId==""){
			 return;
		 }
		 if(null == processId || processId==""){
			 return;
		 }
		 if(null == changeDateTime){
			 return;
		 }
		 //判断工单数量
		 isSuccess =deviceService.checkNum(jobdispatchId, Integer.parseInt(changeNum));
		 if(isSuccess !="符合要求"){
			 return;
		 }
		 
		 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 list = deviceService.getEquProductionInfo(equNo, partTpyeId, processId, StringUtils.formatDate(changeDateTime, 2));
	     if(list.size()>0){
	    	 int i = Integer.parseInt(list.get(0).get("processNum").toString());
	    	 if((i+Integer.parseInt(changeNum))<0){
	    		 isSuccess ="修改产量数据不符合要求，请确认！";
	    	 }else{
	    		 isSuccess ="符合要求";
	    	 }
	     }else{
	    	 if(Integer.parseInt(changeNum)<0){
	    		 isSuccess ="修改产量数据不符合要求，请确认！";
	    	 }else{
	    		 isSuccess ="符合要求";
	    	 }
	     }
	 
	 }
	public void getJobPlanInfo(){
		jobplanId ="";
		jobdispatchId ="";
		List<Map<String,Object>> info = deviceService.getPartTypeMap(partTpyeId,noteId);
		if(info.size()>0){
			partTpyeName = info.get(0).get("name").toString();
			jobPlanNoList = deviceService.getJobPlanNoList(partTpyeId,"");
		}else{
			
		}
		
		 getAllInfo();
	}
	
	public void getJobdispatchInfo(){
		jobdispatchId="";
		jobdispatchNoList = deviceService.getJobdispatchNoList(jobplanNo);
		//jobPlanList = deviceService.getJobplanList(jobplanId);
		jobPlanList = deviceService.getListInfo("select ID,planNum,finishNum,partID,no"
				+ " from t_jobplan_info"
				+ " where no ='"+jobplanNo+"'");
		if(jobPlanList.size()>0){
			    jobplanNum =jobPlanList.get(0).get("planNum").toString();
			    //jobplanNo = jobPlanList.get(0).get("no").toString();
			}
		 getAllInfo();
	}
	
	/**
	 * 保存修改信息
	 */
	public void saveDataInfo(){
		isSuccess ="保存失败";
		 if(null == equNo || equNo==""){
			 return;
		 }
		 if(null == partTpyeId || partTpyeId==""){
			 return;
		 }
		 if(null == processId || processId==""){
			 return;
		 }
		 if(null == changeDateTime){
			 return;
		 }
		 if(null == jobdispatchId || jobdispatchId==""){
			 return;
		 }
		 isSuccess = deviceService.saveChangeProcessNum(changeNum, userId, jobdispatchId, equNo, partTpyeId, processId, changeDateTime, loginUserNo,eventNo);
         if(isSuccess =="添加成功"){
//        	 jobdispatchId="";
//        	 equId ="";
//        	 equNo="";
//        	 partTpyeId="";	 
//        	 changeDateTime=null;
//        	 processId="";
//        	 dataList.clear();
//        	 jobplanNum="";
//        	 eventNo="";
//        	 reason="";
//        	 processNo ="";
//        	 changeNum="";
//        	 jobplanId ="";
        	 getAllInfo();
         }
	}
	
	public void getInfo(){
		List<Map<String,Object>> info = deviceService.getJobDispatch(jobdispatchId);
		if(info.size()>0){
			jobDispatchNo = info.get(0).get("no").toString();
			processId = info.get(0).get("processID").toString();
			if(jobPlanList.size()<=0){
			    jobplanNum =info.get(0).get("processNum").toString();
			    //jobplanNo = info.get(0).get("taskNum").toString();
			}
			List<Map<String,Object>> processInfo = deviceService.getProcessInfo(processId);//工序信息
			if(processInfo.size()>0){
				processNo = processInfo.get(0).get("no").toString();
				processOrder = processInfo.get(0).get("process_order").toString();
			}
		   getAllInfo();
		}
		
	}
	public void getUserInfo(){
		List<Map<String, Object>> list = deviceService.getUserList(userId,noteId);
		if(list!=null&&list.size()>0){
			   userName = list.get(0).get("name").toString();
			 }
	}
    
	/**
	 * 处理综合信息(设备产量统计)
	 */
    public void getAllInfo(){
    	dataList.clear();
    	if(null ==partTpyeId ||partTpyeId ==""){
    		return;
    	}
    	
    	if(null ==processId ||processId ==""){
    		return;
    	}
    	if(null ==jobdispatchId ||jobdispatchId ==""){
    		return;
    	}
    	if(null == changeDateTime){
			 return;
		 }
    	dataList = deviceService.queryEquipmentList("", "",noteId);
    	int i=1;
    	for(Map<String,Object> dd:dataList){
    	   String equSerialNo = dd.get("no").toString();
    	   List<Map<String,Object>> list = deviceService.getDataList(equSerialNo, partTpyeId, processId,changeDateTime);
    	   if(list.size()>0){
    		   if(list.get(0).get("ID") !=null){
	    		   String num= list.get(0).get("processNum").toString();
	    		   dd.put("processNum", num);
    		   }else{
    			   dd.put("processNum", "0");
    		   }
    	   }else{
    	       dd.put("processNum", "0");
    	   }
    	   dd.put("rowIndex", i++);
    	}
    }

	public List<Map<String, Object>> getPartTypeList() {
		return partTypeList;
	}
	
	public void setPartTypeList(List<Map<String, Object>> partTypeList) {
		this.partTypeList = partTypeList;
	}



	public List<Map<String, Object>> getJobPlanNoList() {
		return jobPlanNoList;
	}



	public void setJobPlanNoList(List<Map<String, Object>> jobPlanNoList) {
		this.jobPlanNoList = jobPlanNoList;
	}



	public String getPartTpyeId() {
		return partTpyeId;
	}



	public void setPartTpyeId(String partTpyeId) {
		this.partTpyeId = partTpyeId;
	}



	public String getJobplanId() {
		return jobplanId;
	}



	public void setJobplanId(String jobplanId) {
		this.jobplanId = jobplanId;
	}

	public String getJobdispatchId() {
		return jobdispatchId;
	}

	public void setJobdispatchId(String jobdispatchId) {
		this.jobdispatchId = jobdispatchId;
	}

	public List<Map<String, Object>> getJobdispatchNoList() {
		return jobdispatchNoList;
	}

	public void setJobdispatchNoList(List<Map<String, Object>> jobdispatchNoList) {
		this.jobdispatchNoList = jobdispatchNoList;
	}

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public String getJobplanNum() {
		return jobplanNum;
	}

	public void setJobplanNum(String jobplanNum) {
		this.jobplanNum = jobplanNum;
	}

	public List<Map<String, Object>> getJobPlanList() {
		return jobPlanList;
	}

	public void setJobPlanList(List<Map<String, Object>> jobPlanList) {
		this.jobPlanList = jobPlanList;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public Map<String, Object> getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(Map<String, Object> selectValue) {
		this.selectValue = selectValue;
	}

	public String getJobdispatchNo() {
		return jobdispatchNo;
	}

	public void setJobdispatchNo(String jobdispatchNo) {
		this.jobdispatchNo = jobdispatchNo;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	public List<Map<String, Object>> getUserMap() {
		return userMap;
	}

	public void setUserMap(List<Map<String, Object>> userMap) {
		this.userMap = userMap;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginUserNo() {
		return loginUserNo;
	}

	public void setLoginUserNo(String loginUserNo) {
		this.loginUserNo = loginUserNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEquId() {
		return equId;
	}

	public void setEquId(String equId) {
		this.equId = equId;
	}

	public String getEquNo() {
		return equNo;
	}

	public void setEquNo(String equNo) {
		this.equNo = equNo;
	}

	public String getEquName() {
		return equName;
	}

	public void setEquName(String equName) {
		this.equName = equName;
	}

	public String getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(String changeNum) {
		this.changeNum = changeNum;
	}

	public Date getChangeDateTime() {
		return changeDateTime;
	}

	public void setChangeDateTime(Date changeDateTime) {
		this.changeDateTime = changeDateTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getJobDispatchNo() {
		return jobDispatchNo;
	}

	public void setJobDispatchNo(String jobDispatchNo) {
		this.jobDispatchNo = jobDispatchNo;
	}

	public String getPartTpyeName() {
		return partTpyeName;
	}

	public void setPartTpyeName(String partTpyeName) {
		this.partTpyeName = partTpyeName;
	}

	public String getJobplanNo() {
		return jobplanNo;
	}

	public void setJobplanNo(String jobplanNo) {
		this.jobplanNo = jobplanNo;
	}

	public String getEventNo() {
		return eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public String getProcessOrder() {
		return processOrder;
	}

	public void setProcessOrder(String processOrder) {
		this.processOrder = processOrder;
	}
	
	
}
