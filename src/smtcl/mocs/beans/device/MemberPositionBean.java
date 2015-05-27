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
 * 部门管理Bean
 * @作者：qcb
 * @创建时间：2013-7-22 下午13:05:16
 * @修改者：qcb
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="MemberPositionBean")
@ViewScoped
public class MemberPositionBean implements Serializable{
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
	private String nodeNameIs; //插入用
	private String nodeNameUp; //更新用
	
	
	private Dictionary<String,String> dict; //<id,名称>
	private Dictionary<String,String> dict2;//<名称,id>
    private Map<String,Object> nodeMap = new TreeMap<String,Object>();
	
	private TPositionInfo newData= new TPositionInfo();
	
	private TPositionInfo insertData= new TPositionInfo();
	
	/**
	 * 查询结果集
	 */
	private List<TPositionInfo> results;
	private PositionDataModel data;
	
	
	/**
	 * 选择的单条数据
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
		
		//初始化
		results = memberService.queryPositionList("","","");
		for(TPositionInfo rec:results){
			if(rec.getNodeid()==null || rec.getNodeid()=="")
				continue;
			rec.setNodeid(dict.get(rec.getNodeid()));	
		}
		data =new PositionDataModel(results);
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
	 * 插入数据
	 */
	public void insertRecord(){	
		if(nodeNameIs!=null&&nodeNameIs!=""){
			if(dict2.get(nodeNameIs)!=null)
				insertData.setNodeid(dict2.get(nodeNameIs));
		}
		
		if(insertData.getPositionLevel().equals("基础级")){
			insertData.setAuthorityLevel(1);
		}
		else if(insertData.getPositionLevel().equals("中级")){
			insertData.setAuthorityLevel(2);
		}
		else if(insertData.getPositionLevel().equals("中高级")){
			insertData.setAuthorityLevel(3);
		}
		else if(insertData.getPositionLevel().equals("高级")){
			insertData.setAuthorityLevel(4);
		}
		insertData.setStatus(0);
		
		memberService.insertPositionRecord(insertData);
		
		//初始化
		results = memberService.queryPositionList("","","");
		for(TPositionInfo rec:results){
			if(rec.getNodeid()==null || rec.getNodeid()=="")
				continue;
			rec.setNodeid(dict.get(rec.getNodeid()));	
		}
		data =new PositionDataModel(results);
	}
	
	/**
	 * 删除记录
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
				//职位下已挂着人，不能删除
				msg="1";
				return;
			}			
		}
				
		if(null!=results&&results.size()>0) results.clear();  //没有从列表查询到值的话，清空全部数据
		
		//初始化
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
//		if(null!=results&&results.size()>0) results.clear();  //没有从列表查询到值的话，清空全部数据
//		
//		//初始化
//		results = memberService.queryPositionList("","","");
//		for(TPositionInfo rec:results){
//			if(rec.getNodeid()==null || rec.getNodeid()=="")
//				continue;
//			rec.setNodeid(dict.get(rec.getNodeid()));	
//		}
//		data =new PositionDataModel(results);
	}
	
	
	/**
	 * 更新记录
	 */
	public void updateRecord(){
		//TPositionInfo tp = new TPositionInfo();
		
		if(nodeNameUp!=null&&nodeNameUp!=""){
			if(dict2.get(nodeNameUp)!=null)
				newData.setNodeid(dict2.get(nodeNameUp));
		}
		
		if(newData.getPositionLevel().equals("基础级")){
			newData.setAuthorityLevel(1);
		}
		else if(newData.getPositionLevel().equals("中级")){
			newData.setAuthorityLevel(2);
		}
		else if(newData.getPositionLevel().equals("中高级")){
			newData.setAuthorityLevel(3);
		}
		else if(newData.getPositionLevel().equals("高级")){
			newData.setAuthorityLevel(4);
		}
				
		memberService.updatePositionRecord(newData);
		
		//初始化
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
