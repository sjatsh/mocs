package smtcl.mocs.beans.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TMaterialClass;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.Constants;

/**
 * 
 * @author liguoqiang
 * @date 2014/08/28
 */
@ManagedBean(name="organizeMaterielManageBean")
@ViewScoped
public class OrganizeMaterielManageBean {
	/**
	 * 查询节点的信息
	 */
	private TreeNode root;  
	/**
	 * 当前选中节点信息
	 */
	private TreeNode selectedNode;
	/**
	 * 当前节点
	 */
	private String nodeid;
	/**
	 *资源接口实例
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
	/**
	 * 物料类别list
	 */
	private List<Map<String,Object>> classList=new ArrayList<Map<String,Object>>();
	/**
	 * 当前选中的物料类别
	 */
	private String pid;
	/**
	 * 物料类型list
	 */
	private List<Map<String,Object>> typelist=new ArrayList<Map<String,Object>>();
	/**
	 * 当前选中的物料类型
	 */
	private String type;
	/**
	 * 物料编号
	 */
	private String no;
	/**
	 * 物料描述
	 */
	private String desc;
	/**
	 * 物料状态
	 */
	private String status;
	/**
	 * 物料单位list
	 */
	private List<Map<String,Object>> unitList=new ArrayList<Map<String,Object>>();
	/**
	 * 物料单位
	 */
	private String unit;
	private Date startTime;
	private Date endTime;
	private List<Map<String,Object>> materiellist=new ArrayList<Map<String,Object>>();
	/**
	 * 构造方法
	 */
	public OrganizeMaterielManageBean(){
//		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//		//nodeid="8a8abc8d44d361fd0144d3c76dd60001";
//		nodeid=session.getAttribute("nodeid2")+"";
//		resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
//		//左侧树 
//		root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
//		//table数据
//		materiellist =resourceService.getTMaterailTypeInfo(pid, nodeid, type, no, desc, status, unit, startTime, endTime);
//		typelist=Constants.MATERIELTYPE;//物料类型
//		classList=resourceService.getTMaterialClassByAll();//物料类别
//		unitList=resourceService.getUnitInfo(null);//物料单位
		
	}
	/**
	 * tree选中事件
	 * @param event
	 */
	public void onNodeSelect(NodeSelectEvent event){
		TMaterialClass mc=(TMaterialClass)event.getTreeNode().getData();
		//TMaterialClass tmc=(TMaterialClass)resourceService.getTMaterialClassByName(mc.getMClassname()).get(0);
		//List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid()+"",nodeid,null);
		materiellist =resourceService.getTMaterailTypeInfo(mc.getMClassid().toString(), nodeid, type, no, desc, status, unit, startTime, endTime);

	}
	public void SearchMateriel(){
		materiellist =resourceService.getTMaterailTypeInfo(pid, nodeid, type, no, desc, status, unit, startTime, endTime);
	}
	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	public TreeNode getSelectedNode() {
		return selectedNode;
	}
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public IResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getType() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		//nodeid="8a8abc8d44d361fd0144d3c76dd60001";
		nodeid=session.getAttribute("nodeid2")+"";
		resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
		//左侧树 
		root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
		//table数据
		materiellist =resourceService.getTMaterailTypeInfo(pid, nodeid, type, no, desc, status, unit, startTime, endTime);
		typelist=Constants.MATERIELTYPE;//物料类型
		classList=resourceService.getTMaterialClassByAll(nodeid);//物料类别
		unitList=resourceService.getUnitInfo(null);//物料单位
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public List<Map<String, Object>> getMateriellist() {
		return materiellist;
	}
	public void setMateriellist(List<Map<String, Object>> materiellist) {
		this.materiellist = materiellist;
	}
	public List<Map<String, Object>> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<Map<String, Object>> typelist) {
		this.typelist = typelist;
	}
	public List<Map<String, Object>> getClassList() {
		return classList;
	}
	public void setClassList(List<Map<String, Object>> classList) {
		this.classList = classList;
	}
	public List<Map<String, Object>> getUnitList() {
		return unitList;
	}
	public void setUnitList(List<Map<String, Object>> unitList) {
		this.unitList = unitList;
	}
	
}
