package smtcl.mocs.beans.storage;

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

import smtcl.mocs.services.storage.IStorageManageService;
/**
 * 物料事务处理bean
 * 创建时间 2014-12-09
 * 作者 FW
 * 修改者
 * 修改时间
 */
@ManagedBean(name="TransactionManage")
@ViewScoped
public class TransactionManageBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String success;
	/**
	 * 来源类型集合
	 */
	List<Map<String,Object>> transFromTypeList = new ArrayList<Map<String,Object>>();
	/**
	 * 事务类型集合
	 */
	List<Map<String,Object>> transTypeList = new ArrayList<Map<String,Object>>();
	/**
	 * 人员集合
	 */
	List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
	/**
	 * 节点
	 */
	String nodeid;
	/**
	 * 来源类型名
	 */
	String transFromName;
	/**
	 * 事务类型名
	 */
	String transTypeName;
	/**
	 * 事务类型Id
	 */
	String transTypeId;
	/**
	 * 事务活动
	 */
	String transActivity;
	/**
	 * 事务处理时间
	 */
	Date dateTime;
	/**
	 * 事务处理人员
	 */
	String userName;
	
	private IStorageManageService iStorageManageService=(IStorageManageService)ServiceFactory.getBean("storageManage");
	
	public TransactionManageBean() {
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    nodeid = (String)session.getAttribute("nodeid");
	    transFromTypeList = iStorageManageService.getTransFromTypeList();//来源类型集合
	    userList = iStorageManageService.userList(nodeid);//人员集合
	    
	    dateTime = new Date();
	}
	
	/**
	 * 根据来源类型获取事务类型集合
	 */
	public void getTransTypeInfo(){
	    transTypeList = iStorageManageService.getTransTypeList(transFromName);//事务类型集合
	}
	
	/**
	 * 根据事务类型获取信息
	 */
	public void getTransInfo(){
	    for(Map<String,Object> map:transTypeList){
	    	if(transTypeId.equals(map.get("id").toString())){
	    		transTypeName = map.get("tansType").toString();
	    		transActivity = map.get("tansActivity").toString();
	    	}
	    }
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<Map<String, Object>> getTransFromTypeList() {
		return transFromTypeList;
	}

	public void setTransFromTypeList(List<Map<String, Object>> transFromTypeList) {
		this.transFromTypeList = transFromTypeList;
	}

	public List<Map<String, Object>> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List<Map<String, Object>> transTypeList) {
		this.transTypeList = transTypeList;
	}

	public List<Map<String, Object>> getUserList() {
		return userList;
	}

	public void setUserList(List<Map<String, Object>> userList) {
		this.userList = userList;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getTransFromName() {
		return transFromName;
	}

	public void setTransFromName(String transFromName) {
		this.transFromName = transFromName;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTransTypeId() {
		return transTypeId;
	}

	public void setTransTypeId(String transTypeId) {
		this.transTypeId = transTypeId;
	}

	public String getTransActivity() {
		return transActivity;
	}

	public void setTransActivity(String transActivity) {
		this.transActivity = transActivity;
	}
	
}
