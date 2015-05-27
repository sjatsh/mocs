package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.model.PositionDataModel;
import smtcl.mocs.model.TableDataModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TMemberInfo;
import smtcl.mocs.pojos.job.TPositionInfo;
import smtcl.mocs.services.device.IMemberService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;






import javax.faces.context.FacesContext;  






import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;  
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
/**
 * 
 * ���Ź���Bean
 * @���ߣ�qcb
 * @����ʱ�䣺2013-7-22 ����13:05:16
 * @�޸��ߣ�qcb
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="MemberPositionBean")
@ViewScoped
public class MemberPositionBean implements Serializable{
	private String pageId="mocs.cjgl.page.cjgl";
	
	/**
	 * ��Ա����ӿ�ʵ��
	 */
	private IMemberService memberService=(IMemberService)ServiceFactory.getBean("memberService");//��ȡע��;
	
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	
    private TreeNode root;  
    
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr;

	
	
	/**
	 * ��ǰ�ڵ�����
	 */
	private String thisNodeName = "��ѡ��ڵ�";
	private String nodeNameIs; //������
	private String nodeNameUp; //������
	
	
	private Dictionary<String,String> dict; //<id,����>
	private Dictionary<String,String> dict2;//<����,id>
    private Map<String,Object> nodeMap = new TreeMap<String,Object>();
	
	private TPositionInfo newData= new TPositionInfo();
	
	private TPositionInfo insertData= new TPositionInfo();
	
	/**
	 * ��ѯ�����
	 */
	private List<TPositionInfo> results;
	private PositionDataModel data;
	
	
	/**
	 * ѡ��ĵ�������
	 */
	private TPositionInfo[] selectedData;
	
	private String msg;
	
	
	//private String nodeName;
	
	public MemberPositionBean() {  
        TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		root = organizationService.returnTree(user.getUserId(), pageId);     

		dict=new Hashtable<String, String>();
		dict2=new Hashtable<String, String>();
		
		List<Map<String, Object>> areas=memberService.getAreaList();
		for(Map<String, Object> rec:areas){
			dict.put((String)rec.get("nodeid"), (String)rec.get("nodeName"));	
			dict2.put((String)rec.get("nodeName"), (String)rec.get("nodeid"));
			nodeMap.put((String)rec.get("nodeName"), (String)rec.get("nodeName"));
		}
		
		//��ʼ��
		results = memberService.queryPositionList("","","");
		for(TPositionInfo rec:results){
			if(rec.getNodeid()==null || rec.getNodeid()=="")
				continue;
			rec.setNodeid(dict.get(rec.getNodeid()));	
		}
		data =new PositionDataModel(results);
    }  
	
	/**
	 * ������������������õĺ�̨����
	 * @param event
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		smtcl.mocs.beans.authority.cache.TreeNode currentNode = (smtcl.mocs.beans.authority.cache.TreeNode) event.getTreeNode().getData();
		if(!currentNode.isNocheck()){
			this.nodeStr = currentNode.getNodeId(); // �������ڵ��ֵ������ǰ�ڵ�nodeStr
			thisNodeName = StringUtils.getSubString(currentNode.getNodeName(),"5");// ��ȡ��ǰ����ľ�������ֵ
															
			results = memberService.queryPositionList(nodeStr,"","");
			for(TPositionInfo rec:results){
				if(rec.getNodeid()==null || rec.getNodeid()=="")
					continue;
				rec.setNodeid(dict.get(rec.getNodeid()));	
			}
			data =new PositionDataModel(results);
		}
	}
	
	
	/**
	 * ��������
	 */
	public void insertRecord(){	
		if(nodeNameIs!=null&&nodeNameIs!=""){
			if(dict2.get(nodeNameIs)!=null)
				insertData.setNodeid(dict2.get(nodeNameIs));
		}
		
		if(insertData.getPositionLevel().equals("������")){
			insertData.setAuthorityLevel(1);
		}
		else if(insertData.getPositionLevel().equals("�м�")){
			insertData.setAuthorityLevel(2);
		}
		else if(insertData.getPositionLevel().equals("�и߼�")){
			insertData.setAuthorityLevel(3);
		}
		else if(insertData.getPositionLevel().equals("�߼�")){
			insertData.setAuthorityLevel(4);
		}
		insertData.setStatus(0);
		
		memberService.insertPositionRecord(insertData);
		
		//��ʼ��
		results = memberService.queryPositionList("","","");
		for(TPositionInfo rec:results){
			if(rec.getNodeid()==null || rec.getNodeid()=="")
				continue;
			rec.setNodeid(dict.get(rec.getNodeid()));	
		}
		data =new PositionDataModel(results);
	}
	
	/**
	 * ɾ����¼
	 */
	public void deleteRecord(){
		List<Map<String, Object>> p;			
		String id="";
		
		for(TPositionInfo rec:selectedData){
			if(rec.getNodeid()!=null)
				id=dict2.get((String)rec.getNodeid());
			
			p = memberService.queryPositionList1(id,null,(String)rec.getPositionName());	
			if(p.size()==0||p==null){
				memberService.setStatus("t_position_info","position_id",Long.valueOf(rec.getPositionId()));
				msg="";
			}
			else{
				//ְλ���ѹ����ˣ�����ɾ��
				msg="1";
				return;
			}			
		}
				
		if(null!=results&&results.size()>0) results.clear();  //û�д��б��ѯ��ֵ�Ļ������ȫ������
		
		//��ʼ��
		results = memberService.queryPositionList("","","");
		for(TPositionInfo rec:results){
			if(rec.getNodeid()==null || rec.getNodeid()=="")
				continue;
			rec.setNodeid(dict.get(rec.getNodeid()));	
		}
		data =new PositionDataModel(results);
	}
	
	public void saveDelete(){
//		memberService.deleteRecord("t_position_info");
//		
//		if(null!=results&&results.size()>0) results.clear();  //û�д��б��ѯ��ֵ�Ļ������ȫ������
//		
//		//��ʼ��
//		results = memberService.queryPositionList("","","");
//		for(TPositionInfo rec:results){
//			if(rec.getNodeid()==null || rec.getNodeid()=="")
//				continue;
//			rec.setNodeid(dict.get(rec.getNodeid()));	
//		}
//		data =new PositionDataModel(results);
	}
	
	
	/**
	 * ���¼�¼
	 */
	public void updateRecord(){
		//TPositionInfo tp = new TPositionInfo();
		
		if(nodeNameUp!=null&&nodeNameUp!=""){
			if(dict2.get(nodeNameUp)!=null)
				newData.setNodeid(dict2.get(nodeNameUp));
		}
		
		if(newData.getPositionLevel().equals("������")){
			newData.setAuthorityLevel(1);
		}
		else if(newData.getPositionLevel().equals("�м�")){
			newData.setAuthorityLevel(2);
		}
		else if(newData.getPositionLevel().equals("�и߼�")){
			newData.setAuthorityLevel(3);
		}
		else if(newData.getPositionLevel().equals("�߼�")){
			newData.setAuthorityLevel(4);
		}
				
		memberService.updatePositionRecord(newData);
		
		//��ʼ��
		results = memberService.queryPositionList("","","");
		for(TPositionInfo rec:results){
			if(rec.getNodeid()==null || rec.getNodeid()=="")
				continue;
			rec.setNodeid(dict.get(rec.getNodeid()));	
		}
		data =new PositionDataModel(results);
	}
	

	public TPositionInfo[] getSelectedData() {
		//this.newData=selectedData[0];
		if(selectedData==null||selectedData.length==0){
			newData=new TPositionInfo();
			
		}else{
			newData=selectedData[0];
//			String id=newData.getNodeid();
//			if(id==null || id=="")
//				;
//			else
//				newData.setNodeid(dict.get(id));	
		}
		
		return selectedData;
	}


	public Map<String, Object> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<String, Object> nodeMap) {
		this.nodeMap = nodeMap;
	}

	public void setSelectedData(TPositionInfo[] selectedData) {
		this.selectedData = selectedData;
	}
	
	public TPositionInfo getNewData() {
		return newData;
	}

	public void setNewData(TPositionInfo newData) {
		this.newData = newData;
	}
	
	
	public PositionDataModel getData() {
		return data;
	}

	public void setData(PositionDataModel data) {
		this.data = data;
	}
	

	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public List<TPositionInfo> getResults() {
		return results;
	}

	public void setResults(List<TPositionInfo> results) {
		this.results = results;
	}



	public TPositionInfo getInsertData() {
		return insertData;
	}

	public void setInsertData(TPositionInfo insertData) {
		this.insertData = insertData;
	}

	public String getNodeNameIs() {
		return nodeNameIs;
	}

	public void setNodeNameIs(String nodeNameIs) {
		this.nodeNameIs = nodeNameIs;
	}

	public String getNodeNameUp() {
		return nodeNameUp;
	}

	public void setNodeNameUp(String nodeNameUp) {
		this.nodeNameUp = nodeNameUp;
	}

	public String getNodeStr() {
		return nodeStr;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public String getThisNodeName() {
		return thisNodeName;
	}

	public void setThisNodeName(String thisNodeName) {
		this.thisNodeName = thisNodeName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Dictionary<String, String> getDict() {
		return dict;
	}

	public void setDict(Dictionary<String, String> dict) {
		this.dict = dict;
	}

	
}
