package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.MemberDataModel;
import smtcl.mocs.model.TableDataModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TMemberInfo;
import smtcl.mocs.services.device.IMemberService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;
/**
 * 
 * 部门管理Bean
 * @作者：qcb
 * @创建时间：2013-7-26 下午13:05:16
 * @修改者：qcb
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="MemberPartBean")
@ViewScoped
public class MemberPartBean implements Serializable{
	private String pageId="mocs.cjgl.page.cjgl";
	/**
	 * 人员管理接口实例
	 */
	private IMemberService memberService=(IMemberService)ServiceFactory.getBean("memberService");//获取注入;
	
	/**
	 * 人员信息列表
	 */
	private List<TMemberInfo> members;
	
	private TMemberInfo[] selectedMember;

	private MemberDataModel memberData;
	
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	
    private TreeNode root;  
    
    private List<Map<String,Object>> parts;
    
    private TableDataModel partData;
	
    private Map<String,Object>[] selectedPart;
	
	/**
	 * 左边当前节点
	 */
	private String nodeLeftStr;
	
	/**
	 * 右边当前节点
	 */
	private String nodeRightStr;

	/**
	 * 当前节点名称
	 */
	private String thisNodeName = "请选择节点";
    
	private String positionType;
	private Map<String, Object> positionTypes = new HashMap<String,Object>();
	
	private String positionName;
	private Map<String, Object> positionNames =  new HashMap<String,Object>();
	
	/**
	 * 查询框内容 
	 */
	private String searchType="输入名称/工号";
	
	private Dictionary<String,String> dict;

	public MemberPartBean() {
		//人员初始化
		dict=new Hashtable<String, String>();
		List<Map<String, Object>> areas=memberService.getAreaList();
		for(Map<String, Object> rec:areas){
			dict.put((String)rec.get("nodeid"), (String)rec.get("nodeName"));	
		}
		
		members = memberService.queryMemberList(1, 100, "");
		for(TMemberInfo rec:members){
			if(rec.getNodeid()==null || rec.getNodeid()=="")
				continue;
			rec.setNodeid(dict.get(rec.getNodeid()));	
		}
		memberData =new MemberDataModel(members);
		
        TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		root = organizationService.returnTree(user.getUserId(), pageId);     

		//部门初始化
		parts = memberService.queryPositionList1("","","");		
		partData =new TableDataModel(parts);
	}

	
	/**
	 * 搜索框查询
	 */
	public void searchList(){
		if(null!=members&&members.size()>0) members.clear();  //没有从列表查询到值的话，清空全部数据
		
		members = memberService.queryList(searchType);
		for(TMemberInfo rec:members){
			if(rec.getNodeid()==null || rec.getNodeid()=="")
				continue;
			rec.setNodeid(dict.get(rec.getNodeid()));	
		}
		memberData =new MemberDataModel(members);
	}
	
	
	/**
	 * 点击左侧树形下拉层调用的后台方法
	 * @param event
	 */
	public void onLeftNodeSelect(NodeSelectEvent event) {
		smtcl.mocs.beans.authority.cache.TreeNode currentNode = (smtcl.mocs.beans.authority.cache.TreeNode) event.getTreeNode().getData();
		if(!currentNode.isNocheck()){
			this.nodeLeftStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			thisNodeName = StringUtils.getSubString(currentNode.getNodeName(),"5");// 获取当前点击的具体树型值
				
			String nodes=organizationService.getAllTNodesId(currentNode);
			
			members = memberService.queryMemberList(1, 100, nodes);	
			for(TMemberInfo rec:members){
				if(rec.getNodeid()==null || rec.getNodeid()=="")
					continue;
				rec.setNodeid(dict.get(rec.getNodeid()));	
			}
			memberData =new MemberDataModel(members);
		}
	}
	

	/**
	 * 点击右侧树形下拉层调用的后台方法
	 * @param event
	 */
	public void onRightNode1Select(NodeSelectEvent event) {
		smtcl.mocs.beans.authority.cache.TreeNode currentNode = (smtcl.mocs.beans.authority.cache.TreeNode) event.getTreeNode().getData();
		if(!currentNode.isNocheck()){
			this.nodeRightStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			thisNodeName = StringUtils.getSubString(currentNode.getNodeName(),"5");// 获取当前点击的具体树型值
			//FaceContextUtil.getContext().getSessionMap().put("CURRENTNODE", currentNode);
			//刷新职位信息
			List<Map<String,Object>> positionTypeTemp=memberService.getPositionTypesByNodeId(nodeRightStr);
	        
			positionTypes.clear();
			for(Map<String,Object> rec:positionTypeTemp)
	        	positionTypes.put(rec.get("positionType").toString(),rec.get("positionType").toString());
			
			//清空		
			positionType="";
			positionName="";
			positionNames.clear();
					
			parts = memberService.queryPositionList1(nodeRightStr,"","");		
			partData =new TableDataModel(parts);		
		}
	}

	public void onRightNode2Select() {		
		List<Map<String,Object>> postionNameTemp=memberService.getPositionNamesByNodeId(nodeRightStr, positionType);
        
		positionNames.clear();
		for(Map<String,Object> rec:postionNameTemp)
        	positionNames.put(rec.get("positionName").toString(),rec.get("positionName").toString());						
			
				
		parts = memberService.queryPositionList1(nodeRightStr,positionType,"");		
		partData =new TableDataModel(parts);		
	}
	
	public void onRightNode3Select() {
					
		
		parts = memberService.queryPositionList1(nodeRightStr,positionType,positionName);		
		partData =new TableDataModel(parts);	
		
	}
	
	
	
	/**
	 * 更新右边列表
	 */
	public void updateParts(){		
		if(!StringUtils.isEmpty(nodeRightStr)&&!StringUtils.isEmpty(positionName)&&!StringUtils.isEmpty(positionType)){
			long positionid=0;
			List<Map<String,Object>> postionNameTemp=memberService.getPositionNamesByNodeId(nodeRightStr, positionType);
	        
			for(Map<String,Object> rec:postionNameTemp){
				if(rec.get("positionName").equals(positionName)){
					positionid=Integer.valueOf(rec.get("positionId").toString());
					break;
				}
			}
			
			for(TMemberInfo rec:selectedMember){
				rec.setNodeid(nodeRightStr);
				rec.setPositionid(positionid);
				memberService.updateRecord(rec,null);				
			}
			
			if(parts!=null) parts.clear();
			parts = memberService.queryPositionList1(nodeRightStr,positionType,positionName);		
			partData =new TableDataModel(parts);	
			
			if(members!=null) members.clear();
			members = memberService.queryMemberList(1, 100, nodeLeftStr);	
			for(TMemberInfo rec:members){
				if(rec.getNodeid()==null || rec.getNodeid()=="")
					continue;
				rec.setNodeid(dict.get(rec.getNodeid()));	
			}
			memberData =new MemberDataModel(members);
		}else{
			
		}
	}

	/**
	 * 更新左边列表
	 */
	public void updateMembers(){		
	
			
		if(this.selectedPart!=null)	
		{
		    for(Map<String,Object> rec:this.selectedPart){
		    	memberService.updateRecord(nodeLeftStr,Integer.valueOf(rec.get("id").toString()));
		    }
			
			if(parts!=null) parts.clear();
			parts = memberService.queryPositionList1(nodeRightStr,positionType,positionName);		
			partData =new TableDataModel(parts);	
			
			if(members!=null) members.clear();
			members = memberService.queryMemberList(1, 100, nodeLeftStr);		
			for(TMemberInfo rec:members){
				if(rec.getNodeid()==null || rec.getNodeid()=="")
					continue;
				rec.setNodeid(dict.get(rec.getNodeid()));	
			}
			memberData =new MemberDataModel(members);
		}
	}
	
	public TMemberInfo[] getSelectedMember() {
		return selectedMember;
	}


	public void setSelectedMember(TMemberInfo[] selectedMember) {
		this.selectedMember = selectedMember;
	}


	public String getThisNodeName() {
		return thisNodeName;
	}



	public void setThisNodeName(String thisNodeName) {
		this.thisNodeName = thisNodeName;
	}



	public String getNodeLeftStr() {
		return nodeLeftStr;
	}


	public void setNodeLeftStr(String nodeLeftStr) {
		this.nodeLeftStr = nodeLeftStr;
	}


	public String getNodeRightStr() {
		return nodeRightStr;
	}


	public void setNodeRightStr(String nodeRightStr) {
		this.nodeRightStr = nodeRightStr;
	}


	public List<TMemberInfo> getMembers() {
		return members;
	}

	public void setMembers(List<TMemberInfo> members) {
		this.members = members;
	}


	public MemberDataModel getMemberData() {
		return memberData;
	}

	public void setMemberData(MemberDataModel memberData) {
		this.memberData = memberData;
	}
	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}


	public List<Map<String, Object>> getParts() {
		return parts;
	}


	public void setParts(List<Map<String, Object>> parts) {
		this.parts = parts;
	}
	
	public TableDataModel getPartData() {
		return partData;
	}


	public void setPartData(TableDataModel partData) {
		this.partData = partData;
	}




	public Map<String, Object>[] getSelectedPart() {
		return selectedPart;
	}


	public void setSelectedPart(Map<String, Object>[] selectedPart) {
		this.selectedPart = selectedPart;
	}


	public String getPositionType() {
		return positionType;
	}


	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Map<String, Object> getPositionTypes() {
		return positionTypes;
	}

	public void setPositionTypes(Map<String, Object> positionTypes) {
		this.positionTypes = positionTypes;
	}


	public Map<String, Object> getPositionNames() {
		return positionNames;
	}

	public void setPositionNames(Map<String, Object> positionNames) {
		this.positionNames = positionNames;
	}


	public String getSearchType() {
		return searchType;
	}


	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
}
