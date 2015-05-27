package smtcl.mocs.beans.device;

import java.util.ArrayList;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.TMaterialDetailDataModel;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TMaterialClass;
import smtcl.mocs.services.device.IResourceService;

/**
 * 物料类别维护
 * @创建时间 2013-08-04
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="materailDetailConfigBean")
@ViewScoped
public class MaterailDetailConfigBean {
	/**
	 * 节点树查询条件
	 */
	private String search;
	/**
	 * 资源接口实例
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
	/**
	 * 查询节点的信息
	 */
	private TreeNode root;  
	/**
	 * 当前选中节点信息
	 */
	private TreeNode selectedNode; 
	/**
	 * dataTable数据显示
	 */
	private TMaterialDetailDataModel mediumMaterialModel;
	/**
	 * dataTable选中行
	 */
	private TMaterailTypeInfo[] selectedMaterial;
	/**
	 * 当前点击树节点
	 */
	private String currentTreeNode;
	/**
	 * 新增对象
	 */
	private TMaterailTypeInfo addMaterail=new TMaterailTypeInfo();
	/**
	 * 新增父类节点
	 */
	private String addclassName;
	/**
	 * 物料名称下拉框列表
	 */
	private List<TMaterialClass> selectMaterail=new ArrayList<TMaterialClass>();
	
	private int selectRow=0;
	
	private String nodeid;
	private String pid;
	/**
	 * 构造方法
	 */
	public MaterailDetailConfigBean(){
		
	}
	/**
	 * 节点查询方法
	 */
	public void MaterailSearch(){
		if("输入物料名称".equals(search))
			search=null;
		root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
		
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(null,nodeid,search);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
	}
	/**
	 * tree选中事件
	 * @param event
	 */
	public void onNodeSelect(NodeSelectEvent event){
		if("输入物料名称".equals(search))
			search=null;
		TMaterialClass mc=(TMaterialClass)event.getTreeNode().getData();
		TMaterialClass tmc=(TMaterialClass)resourceService.getTMaterialClassByName(mc.getMClassname()).get(0);
		pid=tmc.getMClassid().toString();
		
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid()+"",nodeid,null);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
		List<TMaterialClass> tmcmlist=new ArrayList<TMaterialClass>();
		tmcmlist=ondigui(tmc,tmcmlist);
		selectMaterail=tmcmlist;
		currentTreeNode=mc.getMClassname();
		addMaterail.setTypeno(tmc.getMClasstype());
		addclassName=tmc.getMClassname();
		addMaterail.setPId(Integer.parseInt(tmc.getMClassid().toString()));
		addMaterail.setNodeId(nodeid);
	}
	/**
	 * 递归方法
	 * @param tmc
	 * @param tmcmlist
	 */
	public List<TMaterialClass> ondigui(TMaterialClass tmc,List<TMaterialClass> tmcmlist){
		tmcmlist.add(tmc);
		for(TMaterialClass tt:tmc.getTMaterialClasses()){
			if(tt.getMStatus()==0&&tt.getNodeId().equals(nodeid)){
				ondigui(tt,tmcmlist);
			}
		}
		return tmcmlist;
	}
	
	/**
	 * 更新方法
	 */
	public void onEdit(){
		System.out.println("****更新方法***");
		for(TMaterailTypeInfo tmddm:selectedMaterial){
			resourceService.updateTMaterailTypeInfo(tmddm);
		}
		if("输入物料名称".equals(search))
			search=null;
		TMaterialClass tmc=resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid()+"",nodeid,search);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
	}
	public void onCancel(){
		
	}
	/**
	 * 新增方法
	 */
	public void addMaterailClass(){
		System.out.println("新增 ");
		TMaterialClass tmc=resourceService.getTMaterialClassByName(addclassName).get(0);
		addMaterail.setStatus(0);
		addMaterail.setPId(Integer.parseInt(tmc.getMClassid().toString()));
		if(null==addMaterail.getPrice()||"".equals(addMaterail.getPrice())){
			addMaterail.setPrice(Double.parseDouble("0"));
		}
		addMaterail.setNodeId(nodeid);
		String tt=resourceService.saveTMaterailTypeInfo(addMaterail);
		if(tt.equals("已存在")){
			 FacesMessage msg = new FacesMessage("物料类别新增","新增失败,已存在该物料！");  
			 FacesContext.getCurrentInstance().addMessage(null, msg);  
		}else if(tt.equals("保存成功")){
			 FacesMessage msg = new FacesMessage("物料类别新增","新增成功！");  
			 FacesContext.getCurrentInstance().addMessage(null, msg);  
		}else{
			 FacesMessage msg = new FacesMessage("物料类别新增","新增失败！");  
			 FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
		if("输入物料名称".equals(search))
			search=null;
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid()+"",nodeid,search);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
		addMaterail=new TMaterailTypeInfo();
		addMaterail.setPId(Integer.parseInt(pid));
		
	}
	/**
	 * 选中检查
	 */
	public void onSelect(){
		for(TMaterailTypeInfo tmddm:selectedMaterial){
			selectRow=1;
		}
	}
	/**
	 * 删除方法
	 */
	public void deleteMaterial(){
		for(TMaterailTypeInfo tmddm:selectedMaterial){
			tmddm.setStatus(1);
			resourceService.deleteTMaterailTypeInfo(tmddm);
		}
		TMaterialClass tmc=resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		if("输入物料名称".equals(search))
			search=null;
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid()+"",nodeid,search);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
	}
	/**
	 * 下拉框改变事件
	 */
	public void selectChange(){
		System.out.println(addclassName+"--"+addMaterail.getTypeno());
		System.out.println(selectMaterail.size());
		if(addclassName.equals("请选择")){
			addMaterail.setTypeno("");
			addclassName="请选择";
		}else{
			TMaterialClass tmc=(TMaterialClass)resourceService.getTMaterialClassByName(addclassName).get(0);
			addMaterail.setTypeno(tmc.getMClasstype());
		}
		
	}
	public String getSearch() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid2")+"";
		resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
		root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
		addMaterail.setNodeId(nodeid);
		return search;
		
	}
	public void setSearch(String search) {
		this.search = search;
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
	public String getCurrentTreeNode() {
		return currentTreeNode;
	}
	public void setCurrentTreeNode(String currentTreeNode) {
		this.currentTreeNode = currentTreeNode;
	}
	public TMaterialDetailDataModel getMediumMaterialModel() {
		return mediumMaterialModel;
	}
	public void setMediumMaterialModel(TMaterialDetailDataModel mediumMaterialModel) {
		this.mediumMaterialModel = mediumMaterialModel;
	}
	public TMaterailTypeInfo getAddMaterail() {
		return addMaterail;
	}
	public void setAddMaterail(TMaterailTypeInfo addMaterail) {
		this.addMaterail = addMaterail;
	}
	public List<TMaterialClass> getSelectMaterail() {
		return selectMaterail;
	}
	public void setSelectMaterail(List<TMaterialClass> selectMaterail) {
		this.selectMaterail = selectMaterail;
	}
	public TMaterailTypeInfo[] getSelectedMaterial() {
		return selectedMaterial;
	}
	public void setSelectedMaterial(TMaterailTypeInfo[] selectedMaterial) {
		this.selectedMaterial = selectedMaterial;
	}
	public int getSelectRow() {
		return selectRow;
	}
	public void setSelectRow(int selectRow) {
		this.selectRow = selectRow;
	}
	public String getAddclassName() {
		return addclassName;
	}
	public void setAddclassName(String addclassName) {
		this.addclassName = addclassName;
	}
	
}
