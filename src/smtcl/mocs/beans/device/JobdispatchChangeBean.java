package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.TableDataModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;

/**
 * 
 */
@ManagedBean(name="JobdispatchChange")
@ViewScoped
public class JobdispatchChangeBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Map<String, Object>> results;
	private List<Map<String, Object>> dispatcheRsults=new ArrayList<Map<String,Object>>();
	/**
	 * �û����Ƽ�
	 */
	private List<Map<String,Object>> userMap = new ArrayList<Map<String,Object>>();
	private Map<String, Object> selectValue;
	
	/**
	 * �豸�������кż���
	 */
	private List<Map<String,Object>> equTypeList = new ArrayList<Map<String,Object>>();
	
	/**
	 * �����ż���
	 */
	private List<Map<String,Object>> dispatchNoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * ������Ƽ���
	 */
	private List<Map<String,Object>> partTypeList = new ArrayList<Map<String,Object>>();
	/**
	 * �豸�������Ƽ���
	 */
	private List<Map<String,Object>> equTypeNameList = new ArrayList<Map<String,Object>>();
    
	private TableDataModel data;
    private TreeNode root;  
    private String msg;
	private Map<String,Object> selectedUser;
	private String isSuccess;
	private String noteId;
	/**
	 * �豸ID
	 */
	private String equId;
	/**
	 * ����ID
	 */
	private String jobdispatchId;
	/**
	 * ������Աid
	 */
	private String userId;
	
	/**
	 * ��¼��ԱNo
	 */
	private String loginUserNo;
	
	/**
	 * ������ԱNo
	 */
	private String userNo;
	/**
	 * ��������
	 */
	private int num;
	/**
	 * ��ʼʱ������
	 */
	private Date startTime;
	/**
	 * ����ʱ��
	 */
	private Date finishTime;
	/**
	 * ������
	 */
	private String partNo;
	
	/**
	 *��ѯ �豸ID
	 */
	private String serachEquId;
	/**
	 *��ѯ �豸ID2
	 */
	private String serachEquId2;
	/**
	 * ��ѯ����Id
	 */
	private String serachDispatchId;
	
	/**
	 * ��ѡ����豸����
	 */
	private String selectedEquName;
	
	/**
	 * ��ѡ��Ĺ�����
	 */
	private String selectedDispatchNo;
	
	/**
	 * �豸���к�
	 */
	private String equSerialNo;
	/**
	 * ��������
	 */
	private String processName;
	/**
	 * �����
	 */
	private String processOrder;
	/**
	 * Ͷ����
	 */
	private String planNum;
	/**
	 * �ѱ�����
	 */
	private String finishNum;
	/**
	 * ������Ա
	 */
	private String userName;
	/**
	 * ��������
	 */
	private String wisScrapNum;
	/**
	 * ����״̬
	 */
	private String status;
	
	/**
	 * ��󱨹�����
	 */
	private String maxNum;
	/**
	 * �¼����
	 */
	private String eventNo;
	
	/**
	 * 400���Ƿ�ϸ�
	 */
	private String isGood;
	/**
	 * 400����鲿��
	 */
	private String checkDep;
	/**
	 * 400��ӹ����Ա
	 */
	private String jgCheckUser;
	/**
	 * 400��װ����Ա
	 */
	private String zpCheckUser;
	/**
	 * 400���̼���Ա
	 */
	private String sjCheckUser;
	/**
	 * 400�򱨹���Ա
	 */
	private String fhUserName;
	/**
	 * 400�򱨹�����
	 */
	private int fhNum;
	/**
	 * 400��ʼʱ��
	 */
	private Date fhStartTime;
	/**
	 * 400�����ʱ��
	 */
	private Date fhFinishTime;
	/**
	 * 400�򱨹���Աid
	 */
	private String fhUserId;
	
	private IDeviceService baogongService=(IDeviceService)ServiceFactory.getBean("deviceService");
	
	public JobdispatchChangeBean() {
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		noteId = (String)session.getAttribute("nodeid");
				
		results = baogongService.queryEquipmentList("","",noteId);
		data =new TableDataModel(results);
        //TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		//root = organizationService.returnTree(user.getUserId(), pageId);  
		num =1;
		fhNum =1;
		finishTime = new Date();
		fhFinishTime = new Date();
		//startTime =new Date();
		
		//��ȡ���������򼯺�
		equTypeList =baogongService.getEquTypeMap(noteId);
		dispatchNoList =baogongService.getDispatchNoMap();
		partTypeList = baogongService.getPartTypeMap("",noteId);
		equTypeNameList = baogongService.getEquTypeNameMapByNodeId(noteId);
	}
	
	public void getUserInfo(){
        List<Map<String, Object>> list;
        if (processOrder.equals("400") ) {
            list = baogongService.getUserList(fhUserId, noteId);
        } else {
            list = baogongService.getUserList(userId, noteId);
        }

        if (list != null && list.size() > 0) {
            userName = list.get(0).get("name").toString();
            fhUserName = list.get(0).get("name").toString(); //400��ģ���޸� 20141020 FW
        }
    }
	
	public void serachEqu(){
		results = baogongService.queryEquipmentList(serachEquId,serachEquId2,noteId);
		equId ="";
		data =new TableDataModel(results);
	}
	public void serachDispatch(){
		 dispatcheRsults = baogongService.queryJobDispatchList(equId,serachDispatchId);
	}
	
	 @SuppressWarnings("unchecked")
	public void onRowSelect(SelectEvent event) {
		 selectedUser =(Map<String,Object>) event.getObject();
		 
		 equId = selectedUser.get("equId").toString();
		 equSerialNo = selectedUser.get("no").toString();
		 selectedEquName = selectedUser.get("name").toString();
	    }
	 
	 public void dispatchRowSelect(SelectEvent event) {
		 Map<String,Object> rs=(Map<String,Object>) event.getObject();
	 }
	
	 public void getjobdispatch(){	
		 dispatcheRsults = baogongService.queryJobDispatchList(equId,"");
		
		}
	 /*
	  * ����
	  */
	 public String saveData(){	
		 isSuccess ="����ʧ��";
		if(equId.isEmpty()){
			return isSuccess;
		}
		if(jobdispatchId.isEmpty()){
			return isSuccess;
		}

		if(processOrder.equals("400")){
			isSuccess = baogongService.saveInfo(fhNum,fhUserId,equId,jobdispatchId,fhStartTime,fhFinishTime,partNo,loginUserNo,
					isGood,checkDep,jgCheckUser,zpCheckUser,sjCheckUser);
		}else{
			isSuccess = baogongService.saveInfo(num,userId,equId,jobdispatchId,startTime,finishTime,partNo,loginUserNo,
					isGood,checkDep,jgCheckUser,zpCheckUser,sjCheckUser);
		}
		List<Map<String, Object>> info = baogongService.getJobDispatch(jobdispatchId);
		if(null != info && info.size() >0){
            finishNum = info.get(0).get("finishNum").toString();
            wisScrapNum = info.get(0).get("wisScrapNum").toString();
            status = info.get(0).get("statusName").toString();
            planNum = info.get(0).get("processNum").toString();
            selectedDispatchNo = info.get(0).get("no").toString();
		}
		if(isSuccess.contains("��ӳɹ�")){
			eventNo = isSuccess.replace("��ӳɹ�", "");
			isSuccess =isSuccess.replace(eventNo, "");
		}
		
		return isSuccess;
		
	 }
	 
	 /**
	  * ��֤��������
	  */
	 public String checkNum(){
		 isSuccess ="������Ҫ��";
		 isSuccess = baogongService.checkNum(jobdispatchId,num);
		 return isSuccess;
	 }
	 
	 /**
	  * 400����֤��������
	  */
	 public String checkNum2(){
		 isSuccess ="������Ҫ��";
		 isSuccess = baogongService.checkNum(jobdispatchId,fhNum);
		 return isSuccess;
	 }
	 
	 
	 public void getJobdispatchIdValue(){
		 if(null!=selectValue&&null!=selectValue.get("jobdispatchid")){
			 jobdispatchId=selectValue.get("jobdispatchid").toString();
			 partNo = selectValue.get("partNo").toString();
			 processName = selectValue.get("processname").toString();
			 processOrder = selectValue.get("processorder").toString();
			 planNum = selectValue.get("plannum").toString();
			 finishNum = selectValue.get("finishNum").toString();
			 selectedDispatchNo =selectValue.get("no").toString();
			 wisScrapNum = selectValue.get("wisScrapNum").toString();
			 int i = Integer.parseInt(planNum)-Integer.parseInt(finishNum)-Integer.parseInt(wisScrapNum)+1;
			 maxNum ="<"+i;
			
		 }else
		 {
			 jobdispatchId="";
		 }
		 userMap = baogongService.getUserList("",noteId);
		 TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		 String id= user.getUserId();
		 List<Map<String, Object>> list = baogongService.getMemberInfo(id,noteId);
		 if(list!=null&&list.size()>0){
		   userId = list.get(0).get("id").toString();
		   userName = list.get(0).get("name").toString();
		   
		   fhUserId = list.get(0).get("id").toString(); //400��ģ���޸� 20141020 FW
		   fhUserName = list.get(0).get("name").toString(); //400��ģ���޸� 20141020 FW
		   
		   loginUserNo = list.get(0).get("no").toString();
		 }else{
		   if(userMap!=null&&userMap.size()>0){
			   userName = userMap.get(0).get("name").toString();
			   userId = userMap.get(0).get("id").toString();
			   
			   fhUserId = userMap.get(0).get("id").toString(); //400��ģ���޸� 20141020 FW
			   fhUserName = userMap.get(0).get("name").toString(); //400��ģ���޸� 20141020 FW
		   }
		 }
		
		
	 }
	
	public Map<String, Object> getSelectedUserId() {
		
		return selectedUser;
	}
	

	public void setSelectedUserId(Map<String, Object> selectedUser) {
		this.selectedUser = selectedUser;
	}

	public TableDataModel getData() {
		return data;
	}


	public void setData(TableDataModel data) {
		this.data = data;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getEquId() {
		return equId;
	}

	public void setEquId(String equId) {
		this.equId = equId;
	}
	
	public List<Map<String, Object>> getDispatcheRsults() {
		return dispatcheRsults;
	}

	public void setDispatcheRsults(List<Map<String, Object>> dispatcheRsults) {
		this.dispatcheRsults = dispatcheRsults;
	}
	
	

	public Map<String, Object> getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(Map<String, Object> selectValue) {
		this.selectValue = selectValue;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Map<String, Object>> getUserMap() {
		return userMap;
	}

	public void setUserMap(List<Map<String, Object>> userMap) {
		this.userMap = userMap;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getJobdispatchId() {
		return jobdispatchId;
	}

	public void setJobdispatchId(String jobdispatchId) {
		this.jobdispatchId = jobdispatchId;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getSerachEquId() {
		return serachEquId;
	}

	public void setSerachEquId(String serachEquId) {
		this.serachEquId = serachEquId;
	}
	
	public String getSerachDispatchId() {
		return serachDispatchId;
	}

	public void setSerachDispatchId(String serachDispatchId) {
		this.serachDispatchId = serachDispatchId;
	}

	public List<Map<String, Object>> getEquTypeList() {
		return equTypeList;
	}

	public void setEquTypeList(List<Map<String, Object>> equTypeList) {
		this.equTypeList = equTypeList;
	}

	public List<Map<String, Object>> getDispatchNoList() {
		return dispatchNoList;
	}

	public void setDispatchNoList(List<Map<String, Object>> dispatchNoList) {
		this.dispatchNoList = dispatchNoList;
	}

	public List<Map<String, Object>> getPartTypeList() {
		return partTypeList;
	}

	public void setPartTypeList(List<Map<String, Object>> partTypeList) {
		this.partTypeList = partTypeList;
	}

	public String getSerachEquId2() {
		return serachEquId2;
	}

	public void setSerachEquId2(String serachEquId2) {
		this.serachEquId2 = serachEquId2;
	}

	public List<Map<String, Object>> getEquTypeNameList() {
		return equTypeNameList;
	}

	public void setEquTypeNameList(List<Map<String, Object>> equTypeNameList) {
		this.equTypeNameList = equTypeNameList;
	}

	public String getSelectedEquName() {
		return selectedEquName;
	}

	public void setSelectedEquName(String selectedEquName) {
		this.selectedEquName = selectedEquName;
	}

	public String getSelectedDispatchNo() {
		return selectedDispatchNo;
	}

	public void setSelectedDispatchNo(String selectedDispatchNo) {
		this.selectedDispatchNo = selectedDispatchNo;
	}

	public String getEquSerialNo() {
		return equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessOrder() {
		return processOrder;
	}

	public void setProcessOrder(String processOrder) {
		this.processOrder = processOrder;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}

	public String getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(String finishNum) {
		this.finishNum = finishNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWisScrapNum() {
		return wisScrapNum;
	}

	public void setWisScrapNum(String wisScrapNum) {
		this.wisScrapNum = wisScrapNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
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

	public String getEventNo() {
		return eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public String getIsGood() {
		return isGood;
	}

	public void setIsGood(String isGood) {
		this.isGood = isGood;
	}

	public String getCheckDep() {
		return checkDep;
	}

	public void setCheckDep(String checkDep) {
		this.checkDep = checkDep;
	}

	public String getJgCheckUser() {
		return jgCheckUser;
	}

	public void setJgCheckUser(String jgCheckUser) {
		this.jgCheckUser = jgCheckUser;
	}

	public String getZpCheckUser() {
		return zpCheckUser;
	}

	public void setZpCheckUser(String zpCheckUser) {
		this.zpCheckUser = zpCheckUser;
	}

	public String getSjCheckUser() {
		return sjCheckUser;
	}

	public void setSjCheckUser(String sjCheckUser) {
		this.sjCheckUser = sjCheckUser;
	}

	public String getFhUserName() {
		return fhUserName;
	}

	public void setFhUserName(String fhUserName) {
		this.fhUserName = fhUserName;
	}

	public int getFhNum() {
		return fhNum;
	}

	public void setFhNum(int fhNum) {
		this.fhNum = fhNum;
	}

	public Date getFhStartTime() {
		return fhStartTime;
	}

	public void setFhStartTime(Date fhStartTime) {
		this.fhStartTime = fhStartTime;
	}

	public Date getFhFinishTime() {
		return fhFinishTime;
	}

	public void setFhFinishTime(Date fhFinishTime) {
		this.fhFinishTime = fhFinishTime;
	}

	public String getFhUserId() {
		return fhUserId;
	}

	public void setFhUserId(String fhUserId) {
		this.fhUserId = fhUserId;
	}
	
}
