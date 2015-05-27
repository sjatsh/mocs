package smtcl.mocs.beans.device;

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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.TFixtureModel;
import smtcl.mocs.model.TMfixtureDataModel;
import smtcl.mocs.pojos.device.TFixtureClassInfo;
import smtcl.mocs.pojos.job.TFixtureTypeInfo;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IResourceService;


/**
 * �о���������
 * @����ʱ�� 2013-07-09
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="tFixtureConfigBean")
@ViewScoped
public class TFixtureConfigBean {
	/**
	 * �ڵ�����ѯ����
	 */
	private String query;
	/**
	 * ��Դ�ӿ�ʵ��
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService"); 
	/**
	 * ��ѯ�ڵ����Ϣ
	 */
	private TreeNode root;  
	/**
	 * ��ǰѡ��ڵ�
	 */
	private TreeNode selectedPart;  
	/**
	 * dataTable������ʾ
	 */
	private TMfixtureDataModel mediumMaterialModel;
	/**
	 * dataTableѡ����
	 */
	private TFixtureModel[] selectedMaterial;
	/**
	 * ��������
	 */
	private TFixtureModel addTfm=new TFixtureModel();
	
	private TFixtureClassInfo ttf=new TFixtureClassInfo();
	/**
	 * ��ǰ����ڵ�
	 */
	private String nodeName;
	private  String nodeid;

	/**
	 * ���췽��
	 */
	public TFixtureConfigBean(){
	
	}
	/**
	 * ��ѯ����
	 */
	public void queryData(){
		root = new DefaultTreeNode("Root", null);
		root.setExpanded(true);
		if("����о�����".equals(query))
			query=null;
		setRoot(null,root);
		shuxin(null,query);
	}
	
	public void setRoot(String tt,TreeNode root){
         ttf=new TFixtureClassInfo();
        ttf.setName("�о����");
        TreeNode partInfo = new DefaultTreeNode(ttf, root); 
        partInfo.setExpanded(true);
        List<TFixtureClassInfo> result=resourceService.getTFixtureClassInfoByQuery(tt,nodeid);
        for(TFixtureClassInfo mm:result){
        	 TreeNode part = new DefaultTreeNode(mm, partInfo); 
        	 part.setExpanded(true);
        }
       
	}
	/**
	 * �ڵ����¼�����
	 * @param event
	 */
	public void getPartProcessList(NodeSelectEvent event){
		ttf=(TFixtureClassInfo)event.getTreeNode().getData();
		nodeName=ttf.getName();
		addTfm.setType(nodeName);
		query=null;
		shuxin(ttf,query);
		System.out.println(ttf.getName());
	}
	
	public void shuxin(TFixtureClassInfo ttf,String query){
		List<TFixtureModel> ttmList=new ArrayList<TFixtureModel>();
		List<Map<String,Object>> rs=new ArrayList<Map<String,Object>>();
		if(null==ttf||null==ttf.getId())
			rs=resourceService.getTfixtureConfigByClassId(null,nodeid,query);
		else
			rs=resourceService.getTfixtureConfigByClassId(ttf.getId()+"",nodeid,query);
		for(Map<String,Object> mm:rs){
			TFixtureModel tfm=new TFixtureModel();
			tfm.setId(mm.get("id").toString());
			tfm.setUfixName(mm.get("ufixName").toString());
			tfm.setUfixNo(mm.get("ufixNo").toString());
			tfm.setType(mm.get("type").toString());
			tfm.setUfixMemo(mm.get("ufixMemo").toString());
			ttmList.add(tfm);
		}
		 mediumMaterialModel=new TMfixtureDataModel(ttmList);
	}
	/**
	 *���·���
	 */
	public void onEdit(){
		String str="";
		for(TFixtureModel tfm:selectedMaterial){
			str=resourceService.updateTUserFixture(tfm);
		}
		if(str.equals("���³ɹ�")){
			FacesMessage msg = new FacesMessage("������Ϣ����","���³ɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(str.equals("�Ѵ���")){
			FacesMessage msg = new FacesMessage("������Ϣ����","�Ѵ��ڣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("������Ϣ����","����ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		System.out.println("updateTUserFixture"+str);
	}
	/**
	 * ��������
	 */
	public void addTfmData(){
		
		TFixtureTypeInfo tuf=new TFixtureTypeInfo();
		tuf.setFixturesName(addTfm.getUfixName());
		tuf.setFixturesNo(addTfm.getUfixNo());
		TFixtureClassInfo tfci=resourceService.getTFixtureClassInfoByName(addTfm.getType()).get(0);
		tuf.setFixtureclassId(tfci.getId());
		tuf.setNodeId(nodeid);
		tuf.setFixturesDescription(addTfm.getUfixMemo());
		query=null;
		String aa=resourceService.addTUserFixture(tuf);
		if(aa.equals("��ӳɹ�")){
			FacesMessage msg = new FacesMessage("������Ϣ���","��ӳɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(aa.equals("�Ѵ���")){
			FacesMessage msg = new FacesMessage("������Ϣ���","�Ѵ��ڣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("������Ϣ���","���ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		shuxin(ttf,query);
		addTfm=new TFixtureModel();
	}
	/**
	 * ɾ��
	 */
	public void deleteDataTable(){
		String tt="";
		for(TFixtureModel tfm:selectedMaterial){
			tt=resourceService.deleteTUserFixture(tfm.getId());
		}
		if(tt.equals("ɾ���ɹ�")){
			FacesMessage msg = new FacesMessage("������Ϣɾ��","ɾ���ɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("������Ϣ���","ɾ��ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		query=null;
		System.out.println("deleteTUserFixture"+tt);
		shuxin(ttf,query);
	}
	
	public String getQuery() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=(String)session.getAttribute("nodeid2");
		resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
		root = new DefaultTreeNode("Root", null);
        root.setExpanded(true);
        TFixtureClassInfo ttf=new TFixtureClassInfo();
        ttf.setName("�о����");
        TreeNode partInfo = new DefaultTreeNode(ttf, root); 
        partInfo.setExpanded(true);
        List<TFixtureClassInfo> result=resourceService.getTFixtureClassInfoByQuery(null,nodeid);
        for(TFixtureClassInfo mm:result){
        	 TreeNode part = new DefaultTreeNode(mm, partInfo); 
        	 part.setExpanded(true);
        }
       
		
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	public TreeNode getSelectedPart() {
		return selectedPart;
	}
	public void setSelectedPart(TreeNode selectedPart) {
		this.selectedPart = selectedPart;
	}
	public TMfixtureDataModel getMediumMaterialModel() {
		return mediumMaterialModel;
	}
	public void setMediumMaterialModel(TMfixtureDataModel mediumMaterialModel) {
		this.mediumMaterialModel = mediumMaterialModel;
	}
	public TFixtureModel[] getSelectedMaterial() {
		return selectedMaterial;
	}
	public void setSelectedMaterial(TFixtureModel[] selectedMaterial) {
		this.selectedMaterial = selectedMaterial;
	}
	public TFixtureModel getAddTfm() {
		return addTfm;
	}
	public void setAddTfm(TFixtureModel addTfm) {
		this.addTfm = addTfm;
	}
	
}
