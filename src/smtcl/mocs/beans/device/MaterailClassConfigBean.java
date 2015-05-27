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
 * �������ά��
 * @����ʱ�� 2013-08-04
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="materailClassConfigBean")
@ViewScoped
public class MaterailClassConfigBean {
	/**
	 * �ڵ�����ѯ����
	 */
	private String search;
	/**
	 * ��Դ�ӿ�ʵ��
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
	/**
	 * ��ѯ�ڵ����Ϣ
	 */
	private TreeNode root;  
	/**
	 * ��ǰѡ�нڵ���Ϣ
	 */
	private TreeNode selectedNode; 
	/**
	 * dataTable������ʾ
	 */
	private TMaterialClassDataModel mediumMaterialModel;
	/**
	 * dataTableѡ����
	 */
	private TMaterialClassModel[] selectedMaterial;
	/**
	 * ��ǰ������ڵ�
	 */
	private String currentTreeNode;
	/**
	 * ��������
	 */
	private TMaterialClass addPojo=new TMaterialClass();
	/**
	 * ����������ѡ����
	 */
	private List<Map<String,String>> materialSelect;
	
	private String nodeid;
	/**
	 * ���췽��
	 */
	public MaterailClassConfigBean(){

		
	}
	/**
	 * �ڵ��ѯ����
	 */
	public void MaterailSearch(){
		if("������������".equals(search))
			search=null;
		root=resourceService.getMaterailTreeNodeOnAll(search,nodeid);
	}
	/**
	 * treeѡ���¼�
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
			FacesMessage msg = new FacesMessage("�������ά��","��ǰѡ����Ч");  
   	        FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
		addPojo.setNodeId(nodeid);
		
	}
	/**
	 * �ݹ鷽��
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
	 * ���·���
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
		 FacesMessage msg = new FacesMessage("����������",str);  
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
	 * ��������
	 */
	public void addMaterailClass(){
		TMaterialClass tmc=(TMaterialClass)resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		addPojo.setTMaterialClass(tmc);
		addPojo.setNodeId(nodeid);
		String tt=resourceService.saveTMaterialClass(addPojo);
		if(tt.equals("�Ѵ���")){
			 FacesMessage msg = new FacesMessage("�����������","����ʧ��,�Ѵ��ڸ����ϣ�");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg);  
		}else if(tt.equals("����ɹ�")){
			 FacesMessage msg = new FacesMessage("�����������","�����ɹ���");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg);  
		}else{
			 FacesMessage msg = new FacesMessage("�����������","����ʧ�ܣ�");  
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
	 * ɾ������
	 */
	public void deleteMaterial(){
		for(TMaterialClassModel tt:selectedMaterial){
			TMaterialClass tmc=(TMaterialClass)resourceService.getTMaterialClassById(tt.getId()).get(0);
			List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid().toString(),nodeid,null);
			if(null!=tmti&&tmti.size()>0){
				 FacesMessage msg = new FacesMessage("�������ɾ��","ɾ��ʧ��,��������������ϸ���ϣ�");  
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
