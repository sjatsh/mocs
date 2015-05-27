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
 * 生产调度页面
 * @author songkaiang
 *
 */
@ManagedBean(name="SubJobdispatchAdd")
@SessionScoped
public class JobdispatchBean implements Serializable{
	
	private IProductService productService = (IProductService)ServiceFactory.getBean("productService");
	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	
	/**
	 * 查询的工单状态
	 */
	private String jobState;
	/**
	 * 工单状态集合
	 */
	private List<Map<String,Object>> jobStatusMap = new ArrayList<Map<String,Object>>();
	
	/**
	 * 设备名称
	 */
	private String eduTypeId;
	/**
	 * 设备类型名称集合
	 */
	private List<Map<String,Object>> eduTypeMap = new ArrayList<Map<String,Object>>();
	/**
	 * 零件
	 */
	private String partTypeId;
	/**
	 * 零件类型名称集合
	 */
	private List<Map<String,Object>> partTypeMap = new ArrayList<Map<String,Object>>();
	/**
	 * 任务编号
	 */
	private String taskNum;
	/**
	 * 任务编号集合
	 */
	private List<Map<String,Object>> taskNumMap = new ArrayList<Map<String,Object>>();
	
	/**
	 * 在treetable中选中的行
	 */
	private TreeNode selectionNode;
	
	private Map<String, Object> groups = new HashMap<String,Object>();
	
	/**
	 * 分组依据
	 */
	public String group;
	
	private String load="tab1";
	
	/**
	 * 工单列表-任务号
	 */
	private TreeNode treeNodeBybatchNo;
	
	/**
	 * 工单列表-设备
	 */
	private TreeNode treeNodeByequName;
	
	private Date startTime;//开始时间
	private Date endTime;//结束时间
		
	public JobdispatchBean(){
		groups.put("任务号", 1);
		groups.put("设备", 2);
		//零件
		partTypeMap.clear();
		partTypeMap = jobDispatchService.getPartTypeMap(this.getNodeId());
		//状态
		jobStatusMap.clear();
		jobStatusMap = (List<Map<String,Object>>) jobDispatchService.getJobStatus();
		//任务号
		taskNumMap.clear();
		taskNumMap = (List<Map<String,Object>>) jobDispatchService.getBatchNoList(this.getNodeId(),null);
		//设备
		eduTypeMap.clear();
		eduTypeMap = (List<Map<String,Object>>) jobDispatchService.getDevicesInfo(this.getNodeId());
		//开始时间
		startTime = DateUtil.getData(-1,1);
		//结束时间
		Date date = DateUtil.getData(2,1);
		endTime = new Date(date.getTime()-1000*60*60*24);//获取当月的最后一天
	}
	
	/**
	 * 1，当工单列表状态改变是调用
	 * 2，状态更改后重新加载数据
	 */
	public void refreshJobList(String pattern,String jobid,String status){
		jobDispatchService.updateDispatch(jobid,Integer.parseInt(status));//更改工单状态
	}
	
	private String getNodeId(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	/**
	 * tree树过滤方法 
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
