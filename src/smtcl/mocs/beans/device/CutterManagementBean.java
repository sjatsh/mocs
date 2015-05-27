package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.TCuttertypeInfoDataModel;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IResourceService;

/**
 * 刀具信息
 * @作者：JiangFeng
 * @创建时间：2013-04-22 
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "CutterManagementBean")
@ViewScoped
public class CutterManagementBean implements Serializable {
	/**
	 * 资源接口实例
	 */
	private IResourceService resourceService = (IResourceService)ServiceFactory.getBean("resourceService");
	
	/**
	 * 权限树接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService)ServiceFactory.getBean("organizationService");

	/**
	 * 当前节点
	 */
	private String nodeStr;
	
	/**
	 * 当前子节点
	 */
	private String nodeIdList;
	
	/**
	 * 树形节点
	 */
	private TreeNode root;
	
	/**
	 * 当前选中节点信息
	 */
	private TreeNode selectedNode; 
	
	/**
	 * 查询字符串
	 */
	private String search;
	
	/**
	 * datable数据 
	 */
	private List<CuttertypeModel> tculist;
	
	/**
	 * datable选中行
	 */
	private CuttertypeModel[] selectCutt;
	
	/**
	 * 判断是否选中的行
	 */
	private String selected;
	
	/**
	 * datable数据实现多行选中
	 */
	private TCuttertypeInfoDataModel mediCuttertypeInfoDataModel;
	
	/**
	 * 要新增的数据
	 */
	private CuttertypeModel addcutt = new CuttertypeModel();
	
	/**
	 * 刀具类别的名称
	 */
	private Integer pid;
	private String nodeid;
	
	
	public void onSelected(){
	    for(CuttertypeModel tt:selectCutt){
	    	selected=tt.getId().toString();
	    }
	}

	/**
	 * 构造方法（树节点）
	 */
	public CutterManagementBean(){
		
	}
	
	/**
	 * 查询方法(树节点)
	 */
	public void searchCutterClass(){
		root = new DefaultTreeNode("root",null);
		root.setExpanded(true);
		TreeNode td = new DefaultTreeNode("刀具类别",root);
		td.setExpanded(true);
		if("输入刀具名称".equals(search))
			search=null;
		List<Map<String, Object>> list = resourceService.getCutterClassTree(null,nodeid);
		for(Map<String, Object> mm : list){
			TreeNode treenode = new DefaultTreeNode(mm.get("id") + "|" + mm.get("name"),td);
			treenode.setExpanded(true);
		}
		getData(pid);
	}

	/**
	 * 树节点单击事件
	 * 
	 */
	public void getCutterClassOnclick(NodeSelectEvent event){
		String name = event.getTreeNode().getData().toString();
		String[] str=name.split("\\|");
		if("刀具类别".equals(name)){
			
		}else{
			pid =Integer.parseInt(str[0]);	
			addcutt.setCutterclass(str[1]);
		}
		if("输入刀具名称".equals(search))
			search=null;
		getData(pid);
	}
	
	public void getData(Integer pid){
		List<Map<String, Object>> cutlist = resourceService.getCutterById(pid,nodeid,search);
		List<CuttertypeModel> ttList=new ArrayList<CuttertypeModel>();
		for(Map<String, Object> mm:cutlist){
			CuttertypeModel tt=new CuttertypeModel();
			tt.setId(mm.get("id").toString());
			tt.setName(mm.get("name").toString());
			tt.setNo(mm.get("no").toString());	
			tt.setNorm(mm.get("norm")+"");
			tt.setRemark(mm.get("remark")+"");
			if(mm.get("norm") == null){
				tt.setNorm("");
			}if(mm.get("remark") == null){
				tt.setRemark("");
			}
			tt.setCutterclass(mm.get("cutter").toString());
			ttList.add(tt);
		}
		mediCuttertypeInfoDataModel = new TCuttertypeInfoDataModel(ttList);
	}
	
	/**
	 * 添加刀具信息
	 * 
	 */
	public void addCutter(){
		
		addcutt.setNodeid(nodeid);
		String cu = resourceService.addCutterManage(addcutt);
		if(cu.equals("添加成功")){
			FacesMessage msg = new FacesMessage("刀具信息添加","添加成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(cu.equals("已存在")){
			FacesMessage msg = new FacesMessage("刀具信息添加","已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("刀具信息添加","添加失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if("输入刀具名称".equals(search))
			search=null;
		
		getData(pid);
		
		addcutt=new CuttertypeModel();//刷新
	}
	
	/**
	 * 删除刀具信息
	 * @return
	 */
	public void deleteCutter(){
		String delete="";
		for(CuttertypeModel mm:selectCutt){
			delete= resourceService.deleteCutterManage(mm);
		}		
		if(delete.equals("删除成功")){
			FacesMessage msg = new FacesMessage("刀具信息删除","删除成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else {
			FacesMessage msg = new FacesMessage("刀具信息删除","删除失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if("输入刀具名称".equals(search))
			search=null;
		getData(pid);
	
	}
	
	/**
	 * 修改刀具信息
	 * @return
	 */
	public void updateCutter(RowEditEvent event){	
		String update="";
		for(CuttertypeModel mm:selectCutt){
			update= resourceService.updateCutterManage(mm);
		}				
		if(update.equals("已存在")){
			FacesMessage msg = new FacesMessage("刀具信息更新","已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(update.equals("更新成功")){
			FacesMessage msg = new FacesMessage("刀具信息更新","更新成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("刀具信息更新","更新失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if("输入刀具名称".equals(search))
			search=null;
		getData(pid);
	}



	public String getNodeStr() {
		return nodeStr;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public String getNodeIdList() {
		return nodeIdList;
	}

	public void setNodeIdList(String nodeIdList) {
		this.nodeIdList = nodeIdList;
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

	public String getSearch() {
		if(null==nodeid){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			nodeid=(String)session.getAttribute("nodeid2");
			resourceService = (IResourceService)ServiceFactory.getBean("resourceService");
			root = new DefaultTreeNode("root",null);
			root.setExpanded(true);
			TreeNode td = new DefaultTreeNode("刀具类别",root);
			td.setExpanded(true);
			List<Map<String, Object>> list = resourceService.getCutterClassTree(null,nodeid);
			for(Map<String, Object> mm : list){
				TreeNode treenode = new DefaultTreeNode(mm.get("id") + "|" + mm.get("name"),td);
				treenode.setExpanded(true);
			}   	
			getData(pid);
		}
		
		
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<CuttertypeModel> getTculist() {
		return tculist;
	}

	public void setTculist(List<CuttertypeModel> tculist) {
		this.tculist = tculist;
	}

	public TCuttertypeInfoDataModel getMediCuttertypeInfoDataModel() {
		return mediCuttertypeInfoDataModel;
	}

	public void setMediCuttertypeInfoDataModel(
			TCuttertypeInfoDataModel mediCuttertypeInfoDataModel) {
		this.mediCuttertypeInfoDataModel = mediCuttertypeInfoDataModel;
	}

	public CuttertypeModel[] getSelectCutt() {
		return selectCutt;
	}

	public void setSelectCutt(CuttertypeModel[] selectCutt) {
		this.selectCutt = selectCutt;
	}

	public CuttertypeModel getAddcutt() {
		return addcutt;
	}

	public void setAddcutt(CuttertypeModel addcutt) {
		this.addcutt = addcutt;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
