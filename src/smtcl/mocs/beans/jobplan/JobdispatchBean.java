package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.cookie.DateUtils;
import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.model.TreeNode;

import smtcl.mocs.services.device.IProductService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.utils.authority.DateUtil;

/**
 * ��������ҳ��
 * @author songkaiang
 *
 */
@ManagedBean(name="SubJobdispatchAdd")
@SessionScoped
public class JobdispatchBean implements Serializable{
	
	private IProductService productService = (IProductService)ServiceFactory.getBean("productService");
	/**
	 * �����ӿ�ʵ��
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	
	/**
	 * ��ѯ�Ĺ���״̬
	 */
	private String jobState;
	/**
	 * ����״̬����
	 */
	private List<Map<String,Object>> jobStatusMap = new ArrayList<Map<String,Object>>();
	
	/**
	 * �豸����
	 */
	private String eduTypeId;
	/**
	 * �豸�������Ƽ���
	 */
	private List<Map<String,Object>> eduTypeMap = new ArrayList<Map<String,Object>>();
	/**
	 * ���
	 */
	private String partTypeId;
	/**
	 * ����������Ƽ���
	 */
	private List<Map<String,Object>> partTypeMap = new ArrayList<Map<String,Object>>();
	/**
	 * ������
	 */
	private String taskNum;
	/**
	 * �����ż���
	 */
	private List<Map<String,Object>> taskNumMap = new ArrayList<Map<String,Object>>();
	
	/**
	 * ��treetable��ѡ�е���
	 */
	private TreeNode selectionNode;
	
	private Map<String, Object> groups = new HashMap<String,Object>();
	
	/**
	 * ��������
	 */
	public String group;
	
	private String load="tab1";
	
	/**
	 * �����б�-�����
	 */
	private TreeNode treeNodeBybatchNo;
	
	/**
	 * �����б�-�豸
	 */
	private TreeNode treeNodeByequName;
	
	private Date startTime;//��ʼʱ��
	private Date endTime;//����ʱ��
		
	public JobdispatchBean(){
		groups.put("�����", 1);
		groups.put("�豸", 2);
		//���
		partTypeMap.clear();
		partTypeMap = jobDispatchService.getPartTypeMap(this.getNodeId());
		//״̬
		jobStatusMap.clear();
		jobStatusMap = (List<Map<String,Object>>) jobDispatchService.getJobStatus();
		//�����
		taskNumMap.clear();
		taskNumMap = (List<Map<String,Object>>) jobDispatchService.getBatchNoList(this.getNodeId(),null);
		//�豸
		eduTypeMap.clear();
		eduTypeMap = (List<Map<String,Object>>) jobDispatchService.getDevicesInfo(this.getNodeId());
		//��ʼʱ��
		startTime = DateUtil.getData(-1,1);
		//����ʱ��
		Date date = DateUtil.getData(2,1);
		endTime = new Date(date.getTime()-1000*60*60*24);//��ȡ���µ����һ��
	}
	
	/**
	 * 1���������б�״̬�ı��ǵ���
	 * 2��״̬���ĺ����¼�������
	 */
	public void refreshJobList(String pattern,String jobid,String status){
		jobDispatchService.updateDispatch(jobid,Integer.parseInt(status));//���Ĺ���״̬
	}
	
	private String getNodeId(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	/**
	 * tree�����˷��� 
	 * @param pattern
	 */
	public void searchList(String pattern){
		if (pattern.equalsIgnoreCase("treeNodeBybatchNo")) {
			treeNodeBybatchNo = productService.getBaseJobListTree(
					this.getNodeId(),
					DateUtils.formatDate(startTime, "yyyy-MM-dd"),
					DateUtils.formatDate(endTime, "yyyy-MM-dd"), taskNum,
					jobState, partTypeId, eduTypeId);
		} else {
			treeNodeByequName = productService.getJobListTree(this.getNodeId(),
					DateUtils.formatDate(startTime, "yyyy-MM-dd"),
					DateUtils.formatDate(endTime, "yyyy-MM-dd"), taskNum,
					jobState, partTypeId, eduTypeId);
		}
	}
	
	public List<Map<String, Object>> getJobStatusMap() {
		return jobStatusMap;
	}

	public void setJobStatusMap(List<Map<String, Object>> jobStatusMap) {
		this.jobStatusMap = jobStatusMap;
	}
	public List<Map<String, Object>> getTaskNumMap() {
		return taskNumMap;
	}
	public void setTaskNumMap(List<Map<String, Object>> taskNumMap) {
		this.taskNumMap = taskNumMap;
	}
	
	public String getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}

	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getEduTypeId() {
		return eduTypeId;
	}

	public void setEduTypeId(String eduTypeId) {
		this.eduTypeId = eduTypeId;
	}

	public TreeNode getTreeNodeBybatchNo() {
		return treeNodeBybatchNo;
	}
	public void setTreeNodeBybatchNo(TreeNode treeNodeBybatchNo) {
		this.treeNodeBybatchNo = treeNodeBybatchNo;
	}

	public Map<String, Object> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, Object> groups) {
		this.groups = groups;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public TreeNode getTreeNodeByequName() {
		return treeNodeByequName;
	}

	public void setTreeNodeByequName(TreeNode treeNodeByequName) {
		this.treeNodeByequName = treeNodeByequName;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public IJobDispatchService getJobDispatchService() {
		return jobDispatchService;
	}

	public void setJobDispatchService(IJobDispatchService jobDispatchService) {
		this.jobDispatchService = jobDispatchService;
	}

	public List<Map<String, Object>> getEduTypeMap() {
		return eduTypeMap;
	}

	public void setEduTypeMap(List<Map<String, Object>> eduTypeMap) {
		this.eduTypeMap = eduTypeMap;
	}

	public String getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public List<Map<String, Object>> getPartTypeMap() {
		return partTypeMap;
	}

	public void setPartTypeMap(List<Map<String, Object>> partTypeMap) {
		this.partTypeMap = partTypeMap;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public TreeNode getSelectionNode() {
		return selectionNode;
	}
	public void setSelectionNode(TreeNode selectionNode) {
		this.selectionNode = selectionNode;
	}
	
}
