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
 * ����������bean
 * ����ʱ�� 2014-12-09
 * ���� FW
 * �޸���
 * �޸�ʱ��
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
	 * ��Դ���ͼ���
	 */
	List<Map<String,Object>> transFromTypeList = new ArrayList<Map<String,Object>>();
	/**
	 * �������ͼ���
	 */
	List<Map<String,Object>> transTypeList = new ArrayList<Map<String,Object>>();
	/**
	 * ��Ա����
	 */
	List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
	/**
	 * �ڵ�
	 */
	String nodeid;
	/**
	 * ��Դ������
	 */
	String transFromName;
	/**
	 * ����������
	 */
	String transTypeName;
	/**
	 * ��������Id
	 */
	String transTypeId;
	/**
	 * ����
	 */
	String transActivity;
	/**
	 * ������ʱ��
	 */
	Date dateTime;
	/**
	 * ��������Ա
	 */
	String userName;
	
	private IStorageManageService iStorageManageService=(IStorageManageService)ServiceFactory.getBean("storageManage");
	
	public TransactionManageBean() {
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    nodeid = (String)session.getAttribute("nodeid");
	    transFromTypeList = iStorageManageService.getTransFromTypeList();//��Դ���ͼ���
	    userList = iStorageManageService.userList(nodeid);//��Ա����
	    
	    dateTime = new Date();
	}
	
	/**
	 * ������Դ���ͻ�ȡ�������ͼ���
	 */
	public void getTransTypeInfo(){
	    transTypeList = iStorageManageService.getTransTypeList(transFromName);//�������ͼ���
	}
	
	/**
	 * �����������ͻ�ȡ��Ϣ
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
