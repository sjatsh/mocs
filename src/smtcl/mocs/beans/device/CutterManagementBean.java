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
 * ������Ϣ
 * @���ߣ�JiangFeng
 * @����ʱ�䣺2013-04-22 
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "CutterManagementBean")
@ViewScoped
public class CutterManagementBean implements Serializable {
	/**
	 * ��Դ�ӿ�ʵ��
	 */
	private IResourceService resourceService = (IResourceService)ServiceFactory.getBean("resourceService");
	
	/**
	 * Ȩ�����ӿ�ʵ��
	 */
	private IOrganizationService organizationService = (IOrganizationService)ServiceFactory.getBean("organizationService");

	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr;
	
	/**
	 * ��ǰ�ӽڵ�
	 */
	private String nodeIdList;
	
	/**
	 * ���νڵ�
	 */
	private TreeNode root;
	
	/**
	 * ��ǰѡ�нڵ���Ϣ
	 */
	private TreeNode selectedNode; 
	
	/**
	 * ��ѯ�ַ���
	 */
	private String search;
	
	/**
	 * datable���� 
	 */
	private List<CuttertypeModel> tculist;
	
	/**
	 * datableѡ����
	 */
	private CuttertypeModel[] selectCutt;
	
	/**
	 * �ж��Ƿ�ѡ�е���
	 */
	private String selected;
	
	/**
	 * datable����ʵ�ֶ���ѡ��
	 */
	private TCuttertypeInfoDataModel mediCuttertypeInfoDataModel;
	
	/**
	 * Ҫ����������
	 */
	private CuttertypeModel addcutt = new CuttertypeModel();
	
	/**
	 * ������������
	 */
	private Integer pid;
	private String nodeid;
	
	
	public void onSelected(){
	    for(CuttertypeModel tt:selectCutt){
	    	selected=tt.getId().toString();
	    }
	}

	/**
	 * ���췽�������ڵ㣩
	 */
	public CutterManagementBean(){
		
	}
	
	/**
	 * ��ѯ����(���ڵ�)
	 */
	public void searchCutterClass(){
		root = new DefaultTreeNode("root",null);
		root.setExpanded(true);
		TreeNode td = new DefaultTreeNode("�������",root);
		td.setExpanded(true);
		if("���뵶������".equals(search))
			search=null;
		List<Map<String, Object>> list = resourceService.getCutterClassTree(null,nodeid);
		for(Map<String, Object> mm : list){
			TreeNode treenode = new DefaultTreeNode(mm.get("id") + "|" + mm.get("name"),td);
			treenode.setExpanded(true);
		}
		getData(pid);
	}

	/**
	 * ���ڵ㵥���¼�
	 * 
	 */
	public void getCutterClassOnclick(NodeSelectEvent event){
		String name = event.getTreeNode().getData().toString();
		String[] str=name.split("\\|");
		if("�������".equals(name)){
			
		}else{
			pid =Integer.parseInt(str[0]);	
			addcutt.setCutterclass(str[1]);
		}
		if("���뵶������".equals(search))
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
	 * ��ӵ�����Ϣ
	 * 
	 */
	public void addCutter(){
		
		addcutt.setNodeid(nodeid);
		String cu = resourceService.addCutterManage(addcutt);
		if(cu.equals("��ӳɹ�")){
			FacesMessage msg = new FacesMessage("������Ϣ���","��ӳɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(cu.equals("�Ѵ���")){
			FacesMessage msg = new FacesMessage("������Ϣ���","�Ѵ��ڣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("������Ϣ���","���ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if("���뵶������".equals(search))
			search=null;
		
		getData(pid);
		
		addcutt=new CuttertypeModel();//ˢ��
	}
	
	/**
	 * ɾ��������Ϣ
	 * @return
	 */
	public void deleteCutter(){
		String delete="";
		for(CuttertypeModel mm:selectCutt){
			delete= resourceService.deleteCutterManage(mm);
		}		
		if(delete.equals("ɾ���ɹ�")){
			FacesMessage msg = new FacesMessage("������Ϣɾ��","ɾ���ɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else {
			FacesMessage msg = new FacesMessage("������Ϣɾ��","ɾ��ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if("���뵶������".equals(search))
			search=null;
		getData(pid);
	
	}
	
	/**
	 * �޸ĵ�����Ϣ
	 * @return
	 */
	public void updateCutter(RowEditEvent event){	
		String update="";
		for(CuttertypeModel mm:selectCutt){
			update= resourceService.updateCutterManage(mm);
		}				
		if(update.equals("�Ѵ���")){
			FacesMessage msg = new FacesMessage("������Ϣ����","�Ѵ��ڣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(update.equals("���³ɹ�")){
			FacesMessage msg = new FacesMessage("������Ϣ����","���³ɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("������Ϣ����","����ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if("���뵶������".equals(search))
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
			TreeNode td = new DefaultTreeNode("�������",root);
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
