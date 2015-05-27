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
 * �������ά��
 * @����ʱ�� 2013-08-04
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="materailDetailConfigBean")
@ViewScoped
public class MaterailDetailConfigBean {
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
	private TMaterialDetailDataModel mediumMaterialModel;
	/**
	 * dataTableѡ����
	 */
	private TMaterailTypeInfo[] selectedMaterial;
	/**
	 * ��ǰ������ڵ�
	 */
	private String currentTreeNode;
	/**
	 * ��������
	 */
	private TMaterailTypeInfo addMaterail=new TMaterailTypeInfo();
	/**
	 * ��������ڵ�
	 */
	private String addclassName;
	/**
	 * ���������������б�
	 */
	private List<TMaterialClass> selectMaterail=new ArrayList<TMaterialClass>();
	
	private int selectRow=0;
	
	private String nodeid;
	private String pid;
	/**
	 * ���췽��
	 */
	public MaterailDetailConfigBean(){
		
	}
	/**
	 * �ڵ��ѯ����
	 */
	public void MaterailSearch(){
		if("������������".equals(search))
			search=null;
		root=resourceService.getMaterailTreeNodeOnAll(null,nodeid);
		
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(null,nodeid,search);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
	}
	/**
	 * treeѡ���¼�
	 * @param event
	 */
	public void onNodeSelect(NodeSelectEvent event){
		if("������������".equals(search))
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
	 * �ݹ鷽��
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
	 * ���·���
	 */
	public void onEdit(){
		System.out.println("****���·���***");
		for(TMaterailTypeInfo tmddm:selectedMaterial){
			resourceService.updateTMaterailTypeInfo(tmddm);
		}
		if("������������".equals(search))
			search=null;
		TMaterialClass tmc=resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid()+"",nodeid,search);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
	}
	public void onCancel(){
		
	}
	/**
	 * ��������
	 */
	public void addMaterailClass(){
		System.out.println("���� ");
		TMaterialClass tmc=resourceService.getTMaterialClassByName(addclassName).get(0);
		addMaterail.setStatus(0);
		addMaterail.setPId(Integer.parseInt(tmc.getMClassid().toString()));
		if(null==addMaterail.getPrice()||"".equals(addMaterail.getPrice())){
			addMaterail.setPrice(Double.parseDouble("0"));
		}
		addMaterail.setNodeId(nodeid);
		String tt=resourceService.saveTMaterailTypeInfo(addMaterail);
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
		if("������������".equals(search))
			search=null;
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid()+"",nodeid,search);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
		addMaterail=new TMaterailTypeInfo();
		addMaterail.setPId(Integer.parseInt(pid));
		
	}
	/**
	 * ѡ�м��
	 */
	public void onSelect(){
		for(TMaterailTypeInfo tmddm:selectedMaterial){
			selectRow=1;
		}
	}
	/**
	 * ɾ������
	 */
	public void deleteMaterial(){
		for(TMaterailTypeInfo tmddm:selectedMaterial){
			tmddm.setStatus(1);
			resourceService.deleteTMaterailTypeInfo(tmddm);
		}
		TMaterialClass tmc=resourceService.getTMaterialClassByName(currentTreeNode).get(0);
		if("������������".equals(search))
			search=null;
		List<TMaterailTypeInfo> tmti=resourceService.getTMaterailTypeInfoByPid(tmc.getMClassid()+"",nodeid,search);
		mediumMaterialModel=new TMaterialDetailDataModel(tmti);
	}
	/**
	 * ������ı��¼�
	 */
	public void selectChange(){
		System.out.println(addclassName+"--"+addMaterail.getTypeno());
		System.out.println(selectMaterail.size());
		if(addclassName.equals("��ѡ��")){
			addMaterail.setTypeno("");
			addclassName="��ѡ��";
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
