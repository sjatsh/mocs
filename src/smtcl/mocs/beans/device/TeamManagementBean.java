package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.MemberDataModel;
import smtcl.mocs.model.PositionDataModel;
import smtcl.mocs.model.TableDataModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TMemberInfo;
import smtcl.mocs.pojos.job.TPositionInfo;
import smtcl.mocs.pojos.job.TTeamInfo;
import smtcl.mocs.services.device.IMemberService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;
/**
 * 
 * 班组管理Bean
 * @作者：qcb
 * @创建时间：2013-8-6 下午13:05:16
 * @修改者：qcb
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="TeamManagementBean")
@ViewScoped	


public class TeamManagementBean implements Serializable{
	private String pageId="mocs.cjgl.page.cjgl"; 
	/**
	 * 人员管理接口实例
	 */
	private IMemberService memberService=(IMemberService)ServiceFactory.getBean("memberService");//获取注入;
	
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	
    private TreeNode root;  
    
	/**
	 * 当前节点
	 */
	private String nodeStr;

	/**
	 * 当前节点名称
	 */
	private String thisNodeName = "请选择节点";
	
	private List<Map<String,Object>> teams;
    
    private TableDataModel teamData;
	
    private Map<String,Object>[] selectedData;
	
    private Map<String,Object> newData;
    private TTeamInfo insertData=new TTeamInfo();
    
    private Dictionary<String,String> dict;
    private Map<String,Object> nodeMap = new TreeMap<String,Object>();
    
    private String msg;
    private String msgd;
//	private String teamCode;
//	
//	private String teamName;
//	
//	private int teamid;
//	
//	private String workstation;
    


	public TeamManagementBean(){
		
	    TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		root = organizationService.returnTree(user.getUserId(), pageId);     

		dict=new Hashtable<String, String>();
		//节点id、名称键值对
		List<Map<String, Object>> nodes=memberService.getAreaList();
		for(Map<String, Object> rec:nodes){
			if(rec.get("nodeName")==null || rec.get("nodeName").equals(""))
				continue;
			dict.put((String)rec.get("nodeName"), (String)rec.get("nodeid"));	
			nodeMap.put((String)rec.get("nodeName"), (String)rec.get("nodeName"));
		}
		
		
		//部门初始化
		teams = memberService.queryTeamList("");		
		teamData =new TableDataModel(teams);
	}

	/**
	 * 点击左侧树形下拉层调用的后台方法
	 * @param event
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		smtcl.mocs.beans.authority.cache.TreeNode currentNode = (smtcl.mocs.beans.authority.cache.TreeNode) event.getTreeNode().getData();
		if(!currentNode.isNocheck()){
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			thisNodeName = StringUtils.getSubString(currentNode.getNodeName(),"5");// 获取当前点击的具体树型值
															
			teams = memberService.queryTeamList(nodeStr);
			teamData =new TableDataModel(teams);
		}
	}
	
	/**
	 * 插入数据
	 */
	public void insertRecord(){
		if(insertData.getNodeid()!=null&&!insertData.getNodeid().equals(""))
		{
			if(dict.get(insertData.getNodeid())!=null)
				insertData.setNodeid(dict.get(insertData.getNodeid()));
		}
		
		List<Map<String, Object>> teamTemp=memberService.checkTeam(insertData.getTeamcode());	
		if(teamTemp.size()==0||teamTemp==null)
		{
			memberService.insertTeamRecord(insertData);
			//初始化
			teams = memberService.queryTeamList("");		
			teamData =new TableDataModel(teams);
			msg="";
		}
		else
		{
			//班组编号重复，新建失败！
			msg="1";
			return;
		}
	}
	
	/**
	 * 删除记录
	 */
	public void deleteRecord(){
		List<Map<String, Object>> p;			
		String id="";
		
		
		for(Map<String,Object> rec:selectedData){
			
			
			id=(String)rec.get("nodeid");
			
			p = memberService.queryTeamList(id,(String)rec.get("teamName"));	
			if(p.size()==0||p==null){
				memberService.deleteTeamRecord(Integer.valueOf(rec.get("id").toString()));
				msgd="";
			}
			else{
				//班组下已挂着人，不能删除
				msgd="1";
				return;
			}			
			
		}
		if(null!=teams&&teams.size()>0) teams.clear();  //没有从列表查询到值的话，清空全部数据
		
		//初始化
		teams = memberService.queryTeamList("");		
		teamData =new TableDataModel(teams);
	}
	
	/**
	 * 更新记录
	 */
	public void updateRecord(){
		TTeamInfo tm = new TTeamInfo();		
		
		List<TTeamInfo> members =  memberService.getTeamRecordById(Integer.valueOf(newData.get("id").toString()));
		if(members!=null&&members.size()>0){
			tm=members.get(0);
		
			tm.setTeamName(newData.get("teamName").toString());
			tm.setTeamcode(newData.get("teamCode").toString());
			tm.setWorkstation(newData.get("workstation").toString());
			
			if(newData.get("nodeName")!=null&&!newData.get("nodeName").equals(""))
			{
				if(dict.get(newData.get("nodeName"))!=null)
					tm.setNodeid(dict.get(newData.get("nodeName")));
			}
			
			memberService.updateTeamRecord(tm);
		}
		
		//初始化
		teams = memberService.queryTeamList("");		
		teamData =new TableDataModel(teams);
	}
	
	
	
	public Map<String, Object>[] getSelectedData() {
		
		if(selectedData==null||selectedData.length==0){
			newData=new HashMap<String,Object>();
		}else{
			newData=selectedData[0];
		}
		
		return selectedData;
	}


	public void setSelectedData(Map<String, Object>[] selectedData) {
		this.selectedData = selectedData;
	}
	
	
	
	public TreeNode getRoot() {
		return root;
	}


	public void setRoot(TreeNode root) {
		this.root = root;
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


	public Map<String, Object> getNewData() {
		return newData;
	}

	public void setNewData(Map<String, Object> newData) {
		this.newData = newData;
	}

	

	public TTeamInfo getInsertData() {
		return insertData;
	}

	public void setInsertData(TTeamInfo insertData) {
		this.insertData = insertData;
	}

	public Map<String, Object> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<String, Object> nodeMap) {
		this.nodeMap = nodeMap;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgd() {
		return msgd;
	}

	public void setMsgd(String msgd) {
		this.msgd = msgd;
	}
	
	
}
