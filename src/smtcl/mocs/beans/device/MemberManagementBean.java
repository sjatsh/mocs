package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import com.bea.xml.stream.samples.Parse;

import smtcl.mocs.model.MemberDataModel;
import smtcl.mocs.model.TableDataModel;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TMemberInfo;
import smtcl.mocs.services.authority.IUserService;
import smtcl.mocs.services.device.IMemberService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

import java.util.Date;

/**
 * 
 * 人员管理Bean
 * @作者：qcb
 * @创建时间：2013-7-11 下午13:05:16
 * @修改者：qcb
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="MemberManagementBean")
@ViewScoped
public class MemberManagementBean implements Serializable {
	private String pageId="mocs.cjgl.page.cjgl";
	private Map<String, Object>[] selectedData;
	private String nodeid;
	/**
	 * 人员信息列表
	 */
	private List<Map<String, Object>> results;
	
	private Map<String, Object> newData;
	
	private TMemberInfo insertData=new TMemberInfo();
	
	private TableDataModel data;			
	
	/**
	 * 区域的集合
	 */
	private Map<String, Object> area = new HashMap<String, Object>();	

	/**
	 * 区域类型 
	 */
	private String areaType;
	
	/**
	 * 查询框内容 
	 */
	private String searchType="输入名称/工号";
			
	private String teamName;
	
	private String positionName;
	
	private String nodeName;
	
	private String teamNameUp;
	
	private String positionNameUp;
	
	private String nodeNameUp;
	
	
	/**
	 * 当前节点
	 */
	private String nodeStr;

	/**
	 * 当前节点名称
	 */
	private String thisNodeName = "请选择节点";
	
    private TreeNode root;  
	
    private String msg;
    
    private Dictionary<String,String> dict;
    private Dictionary<String,String> dictTeam;
    private Dictionary<String,String> dictPosition;
    
    private Map<String,Object> nodeMap = new TreeMap<String,Object>();
    private Map<String,Object> teamMap = new TreeMap<String,Object>();
    private Map<String,Object> positionMap = new TreeMap<String,Object>();
    private List<Map<String,Object>> scrapUserList;//账户列表
    private String selectUserId;
    private String updateUserId;
	/**
	 * 人员管理接口实例
	 */
	private IMemberService memberService=(IMemberService)ServiceFactory.getBean("memberService");//获取注入;

	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	/**
	 * 构造函数
	 */
	public MemberManagementBean() {
		//添加当前节点
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid2")+"";
		
		IUserService us = (IUserService) ServiceFactory.getBean ("userService");
		List<User> userlsit=us.getUserbyAll(); 
		List<Map<String,Object>> userList=new ArrayList<Map<String,Object>>();
		scrapUserList=new ArrayList<Map<String,Object>>();
		for(User user:userlsit){
			Map<String,Object> usermap=new HashMap<String,Object>();
			usermap.put("userId", user.getUserId());
			usermap.put("name", user.getNickName());
			userList.add(usermap);
			scrapUserList.add(usermap);
		}
		
		dict=new Hashtable<String, String>();
		dictTeam=new Hashtable<String, String>();
		dictPosition=new Hashtable<String, String>();
		
		//节点id、名称键值对
		List<Map<String, Object>> nodes=memberService.getAreaList();
		for(Map<String, Object> rec:nodes){
			if(rec.get("nodeName")==null || rec.get("nodeName").equals(""))
				continue;
			dict.put((String)rec.get("nodeName"), (String)rec.get("nodeid"));	
			nodeMap.put((String)rec.get("nodeName"), (String)rec.get("nodeName"));
		}
		
		//初始化
		results = memberService.queryMemberList2(nodeid,null);
		data =new TableDataModel(results);
				
        TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		root = organizationService.returnTree(user.getUserId(), pageId);     
		
	}
	
	public void changePositionTeam(String id){
		teamMap.clear();
		positionMap.clear();
		//班组id、名称键值对
		List<Map<String, Object>> teams=memberService.getTeamList(id);
		for(Map<String, Object> rec:teams){
			if(rec.get("teamName")==null || rec.get("teamName").equals(""))
				continue;
			dictTeam.put((String)rec.get("teamName"), rec.get("teamid").toString());					
			teamMap.put((String)rec.get("teamName"), (String)rec.get("teamName"));
		}
			
		//职位id、名称键值对	
		List<Map<String, Object>> positions=memberService.getPositionList(id);
		for(Map<String, Object> rec:positions){
			if(rec.get("positionName")==null || rec.get("positionName").equals(""))
				continue;
			dictPosition.put((String)rec.get("positionName"), rec.get("positionId").toString());	
			positionMap.put((String)rec.get("positionName"), (String)rec.get("positionName"));
		}
	}

	/**
	 * 搜索框查询
	 */
	public void searchList(){
		if(null!=results&&results.size()>0) results.clear();  //没有从列表查询到值的话，清空全部数据
		
		results = memberService.queryMemberList2(nodeid,searchType);
		data =new TableDataModel(results);
	}
	
	
	/**
	 * 点击左侧树形下拉层调用的后台方法
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		smtcl.mocs.beans.authority.cache.TreeNode currentNode = (smtcl.mocs.beans.authority.cache.TreeNode) event.getTreeNode().getData();
		if(!currentNode.isNocheck()){
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			thisNodeName = StringUtils.getSubString(currentNode.getNodeName(),"5");// 获取当前点击的具体树型值
			
			String nodes=organizationService.getAllTNodesId(currentNode);
			
			//results = memberService.queryMemberList(nodeStr,null);
			results = memberService.queryMemberList2(nodeid,null);
			data =new TableDataModel(results);
			
			changePositionTeam(this.nodeStr);
		}
	}
	
	/**
	 * 删除记录
	 */
	public void deleteRecord(){
		//逻辑删除
		for(Map<String,Object> rec:selectedData){			
			memberService.setStatus("t_member_info","ID",Integer.valueOf(rec.get("id").toString()));
		}
				
		//memberService.deleteRecord("t_member_info"); 物理删除
		
		if(null!=results&&results.size()>0) results.clear();  //没有从列表查询到值的话，清空全部数据
		
		results = memberService.queryMemberList(nodeStr,null);
		data =new TableDataModel(results);
	}
	
	
	public void insertRecord(){		
		if(thisNodeName!=null&&!thisNodeName.equals(""))
		{
			if(dict.get(thisNodeName)!=null)
				insertData.setNodeid(dict.get(thisNodeName));
			//thisNodeName=nodeName;
			nodeStr=insertData.getNodeid();
		}
		nodeStr=nodeid;
				
		
		List<Map<String, Object>> memberTemp=memberService.checkMember(insertData.getNo(), nodeStr);	
		if(memberTemp.size()==0||memberTemp==null)
		{
			insertData.setNodeid(nodeid);
			if(insertData.getBirthday()!=null){
				Calendar cal = Calendar.getInstance();
			    int year = cal.get(Calendar.YEAR);
			    int y=Integer.parseInt(StringUtils.formatDate(insertData.getBirthday(), 2).substring(0,4));	     	      
			    insertData.setAge(year-y);
			}
			if(insertData.getBirthday()!=null)
				insertData.setBirthday(StringUtils.convertDate(StringUtils.formatDate(insertData.getBirthday(), 2), "yyyy-MM-dd"));
		
			if(teamName!=null&&!teamName.equals(""))
			{
				if(dictTeam.get(teamName)!=null)
					insertData.setTeamid(Long.valueOf(dictTeam.get(teamName)));
			}
			

			
			if(positionName!=null&&!positionName.equals(""))
			{
				if(dictPosition.get(positionName)!=null)
					insertData.setPositionid(Long.valueOf(dictPosition.get(positionName)));
			}

			insertData.setStatus(0);
			
			memberService.insertRecord(insertData,selectUserId);
			
			msg="";
			results = memberService.queryMemberList(nodeStr,nodeid);
			data =new TableDataModel(results);
			insertData=new TMemberInfo();
		}
		else
		{
			//工号重复，新建失败！
			msg="工号重复，新建失败！";
			//return;
		}
		
	}
	
	public void updateRecord(){
		TMemberInfo tm = new TMemberInfo();
				
		List<TMemberInfo> members =  memberService.getRecordById(Integer.valueOf(newData.get("id").toString()));
		if(members!=null&&members.size()>0){
			tm=members.get(0);
	
			tm.setName(newData.get("name").toString());
			tm.setNo(newData.get("no").toString());
			tm.setPhoneNumber(newData.get("phoneNumber").toString());
			tm.setSex(newData.get("sex").toString());
			tm.setMarriage(null==newData.get("marriage")?"":newData.get("marriage").toString());
			tm.setEducation(newData.get("education").toString());
			tm.setEmail(newData.get("email").toString());
			tm.setAddress(newData.get("address").toString());
			tm.setPartyAffiliation(newData.get("partyAffiliation").toString());
			tm.setMemo(newData.get("memo").toString());
			tm.setSalary(Double.parseDouble(newData.get("salary").toString()));
			if(newData.get("birthday")!=null){
				tm.setBirthday(StringUtils.convertDate(StringUtils.formatDate((Date)newData.get("birthday"), 2), "yyyy-MM-dd"));
				Calendar cal = Calendar.getInstance();
			    int year = cal.get(Calendar.YEAR);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    String dateStr = sdf.format(newData.get("birthday"));
			    int y=Integer.parseInt(dateStr.substring(0,4));	
			    tm.setAge(year-y);
			}
			
			if(teamNameUp!=null&&!teamNameUp.equals(""))
			{
				if(dictTeam.get(teamNameUp)!=null)
					tm.setTeamid(Long.valueOf(dictTeam.get(teamNameUp)));
			}
			
			if(nodeNameUp!=null&&!nodeNameUp.equals(""))
			{
				if(dict.get(nodeNameUp)!=null)
					tm.setNodeid(dict.get(nodeNameUp));
			}
	
			if(positionNameUp!=null&&!positionNameUp.equals(""))
			{
				if(dictPosition.get(positionNameUp)!=null)
					tm.setPositionid(Long.valueOf(dictPosition.get(positionNameUp)));
			}
		    
			memberService.updateRecord(tm,updateUserId);
			
		}
		
		//初始化
		results = memberService.queryMemberList(nodeStr,null);
		data =new TableDataModel(results);
	}
		

	public Map<String,Object>[] getSelectedData() {
		if(selectedData==null||selectedData.length==0){
			newData=new HashMap<String, Object>();
		}else{
			newData=selectedData[0];
			String mid=newData.get("id").toString();
			List<TUser> userlist=memberService.getUserByMenberId(mid);
			if(null!=userlist&&userlist.size()>0){
				updateUserId=userlist.get(0).getUserId()+"";
			}else{
				updateUserId="";
			}
		}
		
		return selectedData;
	}

	public void setSelectedData(Map<String,Object>[] selectedData) {
		this.selectedData = selectedData;
	}
	public List<Map<String, Object>> getResults() {
		return results;
	}
	public void setResults(List<Map<String, Object>> results) {
		this.results = results;
	}
	public Map<String, Object> getNewData() {
		return newData;
	}
	public void setNewData(Map<String, Object> newData) {
		this.newData = newData;
	}
	public TMemberInfo getInsertData() {
		return insertData;
	}
	public void setInsertData(TMemberInfo insertData) {
		this.insertData = insertData;
	}
	public TableDataModel getData() {
		return data;
	}
	public void setData(TableDataModel data) {
		this.data = data;
	}
	public Dictionary<String, String> getDictTeam() {
		return dictTeam;
	}
	public void setDictTeam(Dictionary<String, String> dictTeam) {
		this.dictTeam = dictTeam;
	}
	public Dictionary<String, String> getDictPosition() {
		return dictPosition;
	}
	public void setDictPosition(Dictionary<String, String> dictPosition) {
		this.dictPosition = dictPosition;
	}
	public Dictionary<String, String> getDict() {
		return dict;
	}
	public void setDict(Dictionary<String, String> dict) {
		this.dict = dict;
	}
	public Map<String, Object> getNodeMap() {
		return nodeMap;
	}
	public void setNodeMap(Map<String, Object> nodeMap) {
		this.nodeMap = nodeMap;
	}
	public Map<String, Object> getTeamMap() {
		return teamMap;
	}
	public void setTeamMap(Map<String, Object> teamMap) {
		this.teamMap = teamMap;
	}
	public Map<String, Object> getPositionMap() {
		return positionMap;
	}
	public void setPositionMap(Map<String, Object> positionMap) {
		this.positionMap = positionMap;
	}
	public Map<String, Object> getArea() {
		return area;
	}
	public void setArea(Map<String, Object> area) {
		this.area = area;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getTeamNameUp() {
		return teamNameUp;
	}
	public void setTeamNameUp(String teamNameUp) {
		this.teamNameUp = teamNameUp;
	}
	public String getPositionNameUp() {
		return positionNameUp;
	}
	public void setPositionNameUp(String positionNameUp) {
		this.positionNameUp = positionNameUp;
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
	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Map<String, Object>> getScrapUserList() {
		return scrapUserList;
	}
	public void setScrapUserList(List<Map<String, Object>> scrapUserList) {
		this.scrapUserList = scrapUserList;
	}
	public String getSelectUserId() {
		return selectUserId;
	}
	public void setSelectUserId(String selectUserId) {
		this.selectUserId = selectUserId;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
}
