package smtcl.mocs.beans.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.TMaterialClassDataModel;
import smtcl.mocs.model.TMaterialClassModel;
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
@ManagedBean(name="materailClassConfigBean")
@ViewScoped
public class MaterailClassConfigBean {
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
	private TMaterialClassDataModel mediumMaterialModel;
	/**
	 * dataTable选中行
	 */
	private TMaterialClassModel[] selectedMaterial;
	/**
	 * 当前点击树节点
	 */
	private String currentTreeNode;
	/**
	 * 新增数据
	 */
	private TMaterialClass addPojo=new TMaterialClass();
	/**
	 * 物料下拉框选择项
	 */
	private List<Map<String,String>> materialSelect;
	
	private String nodeid;
	/**
	 * 构造方法
	 */
	public MaterailClassConfigBean(){

		
	}
	/**
	 * 节点查询方法
	 */
	public void MaterailSearch(){
		if("输入物料名称".equals(search))
			search=null;
		root=resourceService.getMaterailTreeNodeOnAll(search,nodeid);
	}
	/**
	 * tree选中事件
	 * @param event
	 */
	public void onNodeSelect(NodeSelectEvent event){
		TMaterialClass mc=(TMaterialClass)event.getTreeNode().getData();
		System.out.println(mc.getMClassname());
		List<TMaterialClass> rs=resourceService.getTMaterialClassByName(mc.getMClassname());
		if(null!=rs&&rs.size()>0){
			TMaterialClass tmc=rs.get(0);
			List<TMaterialClassModel> tmcmlist=new ArrayList<TMaterialClassModel>();
			tmcmlist=ondigui(tmc, tmcmlist);
			currentTreeNode=mc.getMClassname();
			mediumMaterialModel =new TMaterialClassDataModel(tmcmlist);
		}else{
			FacesMessage msg = new FacesMessage("物料类别维护","当前选中无效");  
   	        FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
		addPojo.setNodeId(nodeid);
		
	}
	/**
	 * 递归方法
	 * @param tmc
	 * @param tmcmlist
	 */
	public List<TMaterialClassModel> ondigui(TMaterialClass tmc,List<TMaterialClassModel> tmcmlist){
		TMaterialClassModel tmcm=new TMaterialClassModel();
		if(null!=tmc.getMClassid())
			tmcm.setId(tmc.getMClassid()+"");
		else
			tmcm.setId("");
		if(null!=tmc.getMClassname())
			tmcm.setName(tmc.getMClassname());
		else
			tmcm.setName("");
		if(null!=tmc.getMClassno())
			tmcm.setNo(tmc.getMClassno());
		else
			tmcm.setNo("");
		if(null!=tmc.getMMemo())
			tmcm.setMemo(tmc.getMMemo());
		else
			tmcm.setMemo("");
		if(null!=tmc.getTMaterialClass()&&null!=tmc.getTMaterialClass().getMClassname())
			tmcm.setParentName(tmc.getTMaterialClass().getMClassname());
		else
			tmcm.setParentName("");
		
		tmcmlist.add(tmcm);
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
		String str="";
		for(TMaterialClassModel tt:selectedMaterial){
			TMaterialClass tmc=(TMaterialClass)resourceService.getTMaterialClassById(tt.getId()).get(0);
			tmc.setMClassname(tt.getName());
			tmc.setMClassno(tt.getNo());
			//tmc.setMClasstype(tt.getType());
			tmc.setMMemo(tt.getMemo());
			TMaterialClass tmcc=(TMaterialClass)resourceService.getTMaterialClassByName(tt.getParentName()).get(0);
			tmc.setTMaterialClass(tmcc);
			str=tt.getName()+resourceService.updateTMaterialClass(tmc)+"!";
		}
		 FacesMessage msg = new FacesMessage("物料类别更新",str);  
	     FacesContext.getCurrentInstance().addMessage(null, msg); 
	     
	    root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
		TMaterialClass tmcc=(TMaterialClass)resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		List<TMaterialClassModel> tmcmlist=new ArrayList<TMaterialClassModel>();
		tmcmlist=ondigui(tmcc, tmcmlist);
		mediumMaterialModel =new TMaterialClassDataModel(tmcmlist);
	}
	public void onCancel(){
		
	}
	/**
	 * 新增方法
	 */
	public void addMaterailClass(){
		TMaterialClass tmc=(TMaterialClass)resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		addPojo.setTMaterialClass(tmc);
		addPojo.setNodeId(nodeid);
		String tt=resourceService.saveTMaterialClass(addPojo);
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
		root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
		TMaterialClass tmcc=(TMaterialClass)resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		List<TMaterialClassModel> tmcmlist=new ArrayList<TMaterialClassModel>();
		tmcmlist=ondigui(tmcc, tmcmlist);
		mediumMaterialModel =new TMaterialClassDataModel(tmcmlist);
		addPojo=new TMaterialClass();
	}
	/**
	 * 删除方法
	 */
	public void deleteMaterial(){
		for(TMaterialClassModel tt:selectedMaterial){
			TMaterialClass tmc=(TMaterialClass)resourceService.getTMaterialClassById(tt.getId()).get(0);
			List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid().toString(),nodeid,null);
			if(null!=tmti&&tmti.size()>0){
				 FacesMessage msg = new FacesMessage("物料类别删除","删除失败,该物料类别存在详细物料！");  
	    	     FacesContext.getCurrentInstance().addMessage(null, msg);  
				break;
			}else{
				tmc.setMStatus(1);
				resourceService.updateTMaterialClass(tmc);
			}
			
		}
		root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
		TMaterialClass tmcc=(TMaterialClass)resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		List<TMaterialClassModel> tmcmlist=new ArrayList<TMaterialClassModel>();
		tmcmlist=ondigui(tmcc, tmcmlist);
		mediumMaterialModel =new TMaterialClassDataModel(tmcmlist);
	}
	
	public String getSearch() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid2")+"";
		
		resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
		root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
		materialSelect=resourceService.getSelectTMaterialClassForAll(nodeid);
		addPojo.setNodeId(nodeid);
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
	public TMaterialClassDataModel getMediumMaterialModel() {
		return mediumMaterialModel;
	}
	public void setMediumMaterialModel(TMaterialClassDataModel mediumMaterialModel) {
		this.mediumMaterialModel = mediumMaterialModel;
	}
	public TMaterialClassModel[] getSelectedMaterial() {
		return selectedMaterial;
	}
	public void setSelectedMaterial(TMaterialClassModel[] selectedMaterial) {
		this.selectedMaterial = selectedMaterial;
	}
	public String getCurrentTreeNode() {
		return currentTreeNode;
	}
	public void setCurrentTreeNode(String currentTreeNode) {
		this.currentTreeNode = currentTreeNode;
	}
	public TMaterialClass getAddPojo() {
		return addPojo;
	}
	public void setAddPojo(TMaterialClass addPojo) {
		this.addPojo = addPojo;
	}
	public List<Map<String, String>> getMaterialSelect() {
		return materialSelect;
	}
	public void setMaterialSelect(List<Map<String, String>> materialSelect) {
		this.materialSelect = materialSelect;
	}
	
}
