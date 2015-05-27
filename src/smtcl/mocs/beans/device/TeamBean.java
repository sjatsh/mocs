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
 * @创建时间：2013-8-2 下午13:05:16
 * @修改者：qcb
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="TeamBean")
@ViewScoped
public class TeamBean implements Serializable{
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
    
    private List<Map<String,Object>> teams;
    
    private TableDataModel teamData;
	
    private Map<String,Object>[] selectedTeams;
	
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
    
	private String teamType;
	private Map<String, Object> teamTypes = new HashMap<String,Object>();
	
	
	/**
	 * 查询框内容 
	 */
	private String searchType="输入名称/工号";
	
	private Dictionary<String,String> dict;

	
	public TeamBean(){
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
		teams = memberService.queryTeamList("","");		
		teamData =new TableDataModel(teams);
		
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
			
			
			//刷新职位信息
			List<Map<String,Object>> teamTypeTemp=memberService.getTeamTypesByNodeId(nodeLeftStr);
			teamTypes.clear();
			for(Map<String,Object> rec:teamTypeTemp)
	        	teamTypes.put(rec.get("team_name").toString(),rec.get("team_name").toString());
			
			//清空		
			teamType="";
				
			teams = memberService.queryTeamList(nodeLeftStr,"");		
			teamData =new TableDataModel(teams);
		}
	}
	

	/**
	 * 点击右侧树形下拉层调用的后台方法
	 * @param event
	 */
//	public void onRightNode1Select(NodeSelectEvent event) {
//		smtcl.mocs.beans.authority.cache.TreeNode currentNode = (smtcl.mocs.beans.authority.cache.TreeNode) event.getTreeNode().getData();
//		if(!currentNode.isNocheck()){
//			this.nodeRightStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
//			thisNodeName = StringUtils.getSubString(currentNode.getNodeName(),"5");// 获取当前点击的具体树型值
//
//			//刷新职位信息
//			List<Map<String,Object>> teamTypeTemp=memberService.getTeamTypesByNodeId(nodeRightStr);
//			teamTypes.clear();
//			for(Map<String,Object> rec:teamTypeTemp)
//	        	teamTypes.put(rec.get("team_name").toString(),rec.get("team_name").toString());
//			
//			//清空		
//			teamType="";
//				
//			teams = memberService.queryTeamList(nodeRightStr,"");		
//			teamData =new TableDataModel(teams);
//		}
//	}

	public void onRightNode2Select() {					
		teams = memberService.queryTeamList(nodeLeftStr,teamType);		
		teamData =new TableDataModel(teams);		
	}
	

	
	/**
	 * 更新右边列表
	 */
	public void updateParts(){		
		if(!StringUtils.isEmpty(nodeLeftStr)&&!StringUtils.isEmpty(teamType)){
			long teamid=0;
			List<Map<String,Object>> teamNameTemp=memberService.getTeamTypesByNodeId(nodeLeftStr);
	        
			for(Map<String,Object> rec:teamNameTemp){
				if(rec.get("team_name").equals(teamType)){
					teamid=Integer.valueOf(rec.get("teamid").toString());
					break;
				}
			}
			
			for(TMemberInfo rec:selectedMember){
				rec.setNodeid(nodeLeftStr);
				rec.setTeamid(teamid);
				memberService.updateRecord(rec,null);				
			}
			
			if(teams!=null) teams.clear();
				teams = memberService.queryTeamList(nodeLeftStr,teamType);			
			teamData =new TableDataModel(teams);	
			
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
	
			
		if(this.selectedTeams!=null)	
		{
		    for(Map<String,Object> rec:this.selectedTeams){
		    	memberService.updateTeamRecord(nodeLeftStr,Integer.valueOf(rec.get("id").toString()));
		    }
			
			if(teams!=null) teams.clear();		
				teams = memberService.queryTeamList(nodeLeftStr,teamType);			
			teamData =new TableDataModel(teams);	
			
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
	
	
	public List<TMemberInfo> getMembers() {
		return members;
	}

	public void setMembers(List<TMemberInfo> members) {
		this.members = members;
	}

	public TMemberInfo[] getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(TMemberInfo[] selectedMember) {
		this.selectedMember = selectedMember;
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

	public List<Map<String, Object>> getTeams() {
		return teams;
	}

	public void setTeams(List<Map<String, Object>> teams) {
		this.teams = teams;
	}

	public TableDataModel getTeamData() {
		return teamData;
	}

	public void setTeamData(TableDataModel teamData) {
		this.teamData = teamData;
	}

	public Map<String, Object>[] getSelectedTeams() {
		return selectedTeams;
	}

	public void setSelectedTeams(Map<String, Object>[] selectedTeams) {
		this.selectedTeams = selectedTeams;
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

	public String getThisNodeName() {
		return thisNodeName;
	}

	public void setThisNodeName(String thisNodeName) {
		this.thisNodeName = thisNodeName;
	}

	public String getTeamType() {
		return teamType;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

	public Map<String, Object> getTeamTypes() {
		return teamTypes;
	}

	public void setTeamTypes(Map<String, Object> teamTypes) {
		this.teamTypes = teamTypes;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	
	
	
	
	
	
		
}
