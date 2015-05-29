package smtcl.mocs.beans.device;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import smtcl.mocs.model.PartProcessDataModel;
import smtcl.mocs.model.TProcessplanInfoModel;
import smtcl.mocs.model.TQualityParamDataModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.pojos.job.TQualityParam;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;

/**
 * 零件工序配置
 * @创建时间 2013-07-12
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明  
 * @version V1.0
 */
@ManagedBean(name="partProcessConfigBean")
@ViewScoped
public class PartProcessConfigBean {
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	/**
	 * 查询条件
	 */
	private String query;
	/**
	 * 树形节点
	 */
	private TreeNode root;
	/**
	 * 当前选择节点
	 */
	private TreeNode selectedPart;  
	/**
	 * 工艺方案下拉框数据
	 */
	private List<Map<String,Object>> processPlan=new ArrayList<Map<String,Object>>();
	
	/**
	 * 当前选中的工艺方案
	 */
	private String selectProcessPlan;
	/**
	 * dataTable数据  实现了多行选中
	 */
	private PartProcessDataModel mediumPartModel;
	/**
	 * 零件工艺方案dataTable
	 */
	private TProcessplanInfoModel processPlanModel;
	/**
	 * dataTable选中的行
	 */
	private TProcessInfo[] selectedRowPart;
	/**
	 * 零件工艺方案dataTable选中行
	 */
	private TProcessplanInfo[] selectedRowPlan;
	/**
	 * 要新增的数据
	 */
	private TProcessInfo addPart=new TProcessInfo();
	/**
	 * 工艺线路数据
	 */
	private List<TProcessInfo> gyxllist;
	/**
	 * 成本数据
	 */
	private List<Map<String,Object>> cblist;
	/**
	 * 物料数据
	 */
	private List<Map<String,Object>> wllist;
	/**
	 * 夹具数据
	 */
	private List<Map<String,Object>> jjlist;
	/**
	 * 刀具数据
	 */
	private List<Map<String,Object>> djlist;
	/**
	 * 机台数据
	 */
	private List<Map<String,Object>> jtlist;
	/**
	 * 质检数据
	 */
	private List<Map<String,Object>> zjlist;
	/**
	 * 当前选中零件
	 */
	private String selectProcess;
	/**
	 * 零件工艺方案查询条件
	 */
	private String processPlanSearch;
	/**
	 * 新增的零件工艺方案
	 */
	private TProcessplanInfo addPlal=new TProcessplanInfo();
	/**
	 * 跳转对象
	 */
	private Map<String,String> path;
	
	private String loadprocess="1";
	private String nodeid;
	
	public void setLoadprocess(String loadprocess) {
		this.loadprocess = loadprocess;
	}
	public String getLoadprocess() {
		Constants.LOADCOUNT=1;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid2")+"";
		root = new DefaultTreeNode("Root", null);
        root.setExpanded(true);
        TreeNode partInfo = new DefaultTreeNode("零件信息列表(名称|编号)", root); 
        partInfo.setExpanded(true);
        List<Map<String,Object>> result=partService.getPartTree(null,nodeid);
        for(Map<String,Object> mm:result){
        	 TreeNode part = new DefaultTreeNode(mm.get("name")+"|"+mm.get("no"), partInfo); 
        	 part.setExpanded(true);
        }
        //processPlan=new ArrayList<Map<String,Object>>();
       // gyxllist=new ArrayList<TProcessInfo>();
       //List<TProcessInfo> pprs=new ArrayList<TProcessInfo>();
		//mediumPartModel =new PartProcessDataModel(pprs);
		
		return loadprocess;
	}

	

	/**
	 * 构造方法
	 */
    public PartProcessConfigBean() {  
        selectProcessPlan="";
        Constants.LOADCOUNT=1;
    }  
    
    /**
	 *  查询方法
	 */
	public void queryData(){
		root = new DefaultTreeNode("Root", null);
        root.setExpanded(true);
        TreeNode partInfo = new DefaultTreeNode("零件信息列表(名称|编号|图号)", root); 
        partInfo.setExpanded(true);
        if("输入ID/编号/名称".equals(query))
			query=null;
        List<Map<String,Object>> result=partService.getPartTree(query,nodeid);
        for(Map<String,Object> mm:result){
        	 TreeNode part = new DefaultTreeNode(mm.get("name")+"|"+mm.get("no")+"|"+mm.get("drawingno"), partInfo); 
        	 part.setExpanded(true);
        }
	}
	
	/**
	 * 树节点单击事件，获取零件工序列表
	 */
	public void getPartProcessList(NodeSelectEvent event){
		String name=(event.getTreeNode().toString());
		String[] str=name.split("\\|");
		selectProcess=str[1];
		query=str[0];
		List<TProcessplanInfo> result=partService.getTProcessplanInfo(str[1],null);
		processPlan=new ArrayList<Map<String,Object>>();
		if(null!=result&&result.size()>0){
			for(TProcessplanInfo tpp:result){
				Map map=new HashMap();
				map.put("id",tpp.getId());
				map.put("name", tpp.getName());
				processPlan.add(map);
				if(tpp.getDefaultSelected()==1){
					selectProcessPlan=tpp.getId().toString();
				}
			}
		}else{
			processPlan=new ArrayList<Map<String,Object>>();
			selectProcessPlan=null;
		}
		
		if(null==selectProcessPlan||"".equals(selectProcessPlan)){
			if(result!=null&&result.size()>0)
			selectProcessPlan=((TProcessplanInfo)result.get(0)).getId().toString();
		}
		
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		mediumPartModel =new PartProcessDataModel(pprs);
		gyxllist=pprs;
		path=new HashMap<String, String>();
		path.put("ljgxxd", selectProcess+","+selectProcessPlan+",ljgxxd");
		path.put("ljgxxdup", selectProcess+","+selectProcessPlan+",ljgxxdup");
	}
	
	/**
	 *工艺选择下拉框改变方法
	 */
	public void selectChange( ){
		System.out.println(selectProcessPlan);
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		mediumPartModel =new PartProcessDataModel(pprs);
		gyxllist=partService.getTProcessInfo(selectProcessPlan);
		path=new HashMap<String, String>();
		path.put("ljgxxd", selectProcess+","+selectProcessPlan+",ljgxxd");
		path.put("ljgxxdup", selectProcess+","+selectProcessPlan+",ljgxxdup");
	}
	
	/**
	 * dataTable更新方法
	 */
	public void onEdit(RowEditEvent event){
		TProcessInfo tpart=(TProcessInfo) event.getObject();
        partService.saveTProcessInfo(tpart);
	}
	
	/**
	 * dataTable取消方法
	 */
	public void onCancel(){
		
	}
	
	/**
	 * dataTable删除方法
	 */
	public void onDelete(){
		for(TProcessInfo part:selectedRowPart){
    		partService.deleteTProcessInfo(part);
    		
    	}
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		mediumPartModel =new PartProcessDataModel(pprs);	
	}
	/**
	 * 获取工艺线路数据
	 */
	public void getGYXLData(){
		gyxllist=partService.getTProcessInfo(selectProcessPlan);
	}
	/**
	 * 获取成本数据
	 */
	public void getCBData(){
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		cblist=partService.getCBData(pprs);
	}
	
	/**
	 * 获取物料数据
	 */
	public void getWLData(){
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		wllist=partService.getWLData(pprs);
	}
	
	/**
	 * 获取夹具数据
	 */
	public void getJJData(){
		 jjlist=partService.getJJData(selectProcessPlan);
	}
	/**
	 * 获取刀具数据
	 */
	public void getDJData(){
		djlist=partService.getDJData(selectProcessPlan);
	}
	/**
	 * 获取机台数据
	 */
	public void getJTData(){
		jtlist=partService.getJTData(selectProcessPlan);
	}
	/**
	 * 获取质检数据
	 */
	public void getZJData(){
		 zjlist=partService.getTQualityParamByProcessPlanId(selectProcessPlan);
		   
	}
	/**
	 *新增导行
	 */
	public String toAddGuide(){
		
		return "PartProcessGuide";
	}
	/**
	 * 新增工艺方案
	 * @return
	 */
	public String toAddProcessPlan(){
		return "ProcessPlanInfoPath";
	}
	/**
	 * 编辑向导
	 * @return
	 */
	public String toUpdateGuide(){
		return "PartProcessGuideUpdatePath";
	}
	/**
	 * 工艺方案维护按钮点击事件
	 */
	public void processPlanData(){
		List<TProcessplanInfo> rs=partService.getTProcessplanInfo(selectProcess,null);
		processPlanModel=new TProcessplanInfoModel(rs);
	}
	/**
	 * 工艺方案名字生成策略
	 */
	public void onShowAddPanl(){
		List<TProcessplanInfo> rs=partService.getTProcessplanInfo(selectProcess,null);
		if(null==rs||rs.size()<1){
			addPlal.setName((selectProcess+"_01"));
		}else{
			TProcessplanInfo tpi=rs.get(rs.size()-1);
			String name=tpi.getName();
			int tt=name.lastIndexOf("_");
			String num=name.substring(tt+1,name.length());
			System.out.println(num);
			 DecimalFormat d=new DecimalFormat("00");
			addPlal.setName((selectProcess+"_"+d.format((Integer.parseInt(num)+1))));
		}
	}
	/**
	 * 新增工艺方案
	 */
	public void addPlanData(){
		TUser userinfo=(TUser)FaceContextUtil.getSessionMap().get(Constants.USER_SESSION_KEY);
		Date date=new Date();
		TProcessplanInfo s=new TProcessplanInfo();
		s.setName(addPlal.getName());
		s.setOperator(userinfo.getLoginName());
		s.setCreateDate(date);
		s.setDescription(addPlal.getDescription());
		s.setStatus(0);
		s.setDefaultSelected(0);
		s.setNodeid(nodeid);
		List<TPartTypeInfo> tPartTypeInfo=partService.getTPartTypeInfoByNo(selectProcess);
		s.setTPartTypeInfo(tPartTypeInfo.get(0));
		String rs=partService.saveTProcessplanInfo(s); //新增工艺方案
		partService.updateDeDefaultTProcessplanInfo(s);//设为默认
		if(rs.equals("1")){
			FacesMessage msg = new FacesMessage("Succesful","文件保存成功");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(rs.equals("0")){
			FacesMessage msg = new FacesMessage("Fall","文件保存失败,已存在");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("Fall","文件保存失败!");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		this.processPlanData();
	}
	/**
	 * 工艺方案更新按钮
	 * @param event
	 */
	public void onPlanEdit(RowEditEvent event){
		for(TProcessplanInfo plan:selectedRowPlan){
			String str=partService.updateTProcessplanInfo(plan);
			if(plan.getDefaultSelected().equals("1")){
				str=partService.updateDeDefaultTProcessplanInfo(plan);	
			}
			 
    	}
		this.searchTProcessplanInfo();
	}
	/**
	 * 工艺方案删除按钮
	 */
	public void onDeletePlan(){
		for(TProcessplanInfo plan:selectedRowPlan){
			plan.setStatus(1);
			String str=partService.updateTProcessplanInfo(plan);
    	}
		this.processPlanData();
	}
	/**
	 * 设为默认
	 */
	public void onDefault(){
		String str="";
		if(selectedRowPlan.length>1){
			FacesMessage msg = new FacesMessage("工艺方案默认设置","默认工艺方案一个零件只能设置一个默认工艺方案!");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			for(TProcessplanInfo plan:selectedRowPlan){
				 str=partService.updateDeDefaultTProcessplanInfo(plan);
	    	}
			if(str.equals("设置成功")){
				FacesMessage msg = new FacesMessage("工艺方案默认设置","设置成功!");  
		        FacesContext.getCurrentInstance().addMessage(null, msg);
		        this.processPlanData();
			}else{
				FacesMessage msg = new FacesMessage("工艺方案默认设置","设置失败!");  
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}
	/**
	 * 工艺方案模糊查询
	 */
	public void searchTProcessplanInfo(){
        if("输入ID/编号/名称".equals(processPlanSearch))
        	processPlanSearch=null;
		List<TProcessplanInfo> rs=partService.getTProcessplanInfo(selectProcess,processPlanSearch);
		processPlanModel=new TProcessplanInfoModel(rs);
	}
	
    public TreeNode getSelectedPart() {
		return selectedPart;
	}

	public void setSelectedPart(TreeNode selectedPart) {
		this.selectedPart = selectedPart;
	}

	public TreeNode getRoot() {  
        return root;  
    }

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Map<String, Object>> getProcessPlan() {
		return processPlan;
	}

	public void setProcessPlan(List<Map<String, Object>> processPlan) {
		this.processPlan = processPlan;
	}

	public String getSelectProcessPlan() {
		return selectProcessPlan;
	}

	public void setSelectProcessPlan(String selectProcessPlan) {
		this.selectProcessPlan = selectProcessPlan;
	}

	public PartProcessDataModel getMediumPartModel() {
		return mediumPartModel;
	}

	public void setMediumPartModel(PartProcessDataModel mediumPartModel) {
		this.mediumPartModel = mediumPartModel;
	}

	public TProcessInfo[] getSelectedRowPart() {
		return selectedRowPart;
	}

	public void setSelectedRowPart(TProcessInfo[] selectedRowPart) {
		this.selectedRowPart = selectedRowPart;
	}

	public TProcessInfo getAddPart() {
		return addPart;
	}

	public void setAddPart(TProcessInfo addPart) {
		this.addPart = addPart;
	}

	public List<Map<String, Object>> getCblist() {
		return cblist;
	}

	public void setCblist(List<Map<String, Object>> cblist) {
		this.cblist = cblist;
	}

	public List<Map<String, Object>> getWllist() {
		return wllist;
	}

	public void setWllist(List<Map<String, Object>> wllist) {
		this.wllist = wllist;
	}

	public List<Map<String, Object>> getJjlist() {
		return jjlist;
	}

	public void setJjlist(List<Map<String, Object>> jjlist) {
		this.jjlist = jjlist;
	}

	public List<Map<String, Object>> getDjlist() {
		return djlist;
	}

	public void setDjlist(List<Map<String, Object>> djlist) {
		this.djlist = djlist;
	}

	public List<Map<String, Object>> getJtlist() {
		return jtlist;
	}

	public void setJtlist(List<Map<String, Object>> jtlist) {
		this.jtlist = jtlist;
	}

	public String getSelectProcess() {
		return selectProcess;
	}

	public void setSelectProcess(String selectProcess) {
		this.selectProcess = selectProcess;
	}

	public List<TProcessInfo> getGyxllist() {
		return gyxllist;
	}

	public void setGyxllist(List<TProcessInfo> gyxllist) {
		this.gyxllist = gyxllist;
	}

	public TProcessplanInfoModel getProcessPlanModel() {
		return processPlanModel;
	}

	public void setProcessPlanModel(TProcessplanInfoModel processPlanModel) {
		this.processPlanModel = processPlanModel;
	}

	public TProcessplanInfo[] getSelectedRowPlan() {
		return selectedRowPlan;
	}

	public void setSelectedRowPlan(TProcessplanInfo[] selectedRowPlan) {
		this.selectedRowPlan = selectedRowPlan;
	}

	public String getProcessPlanSearch() {
		return processPlanSearch;
	}

	public void setProcessPlanSearch(String processPlanSearch) {
		this.processPlanSearch = processPlanSearch;
	}

	public TProcessplanInfo getAddPlal() {
		return addPlal;
	}

	public void setAddPlal(TProcessplanInfo addPlal) {
		this.addPlal = addPlal;
	}

	public Map<String, String> getPath() {
		return path;
	}

	public void setPath(Map<String, String> path) {
		this.path = path;
	}
	public List<Map<String,Object>> getZjlist() {
		return zjlist;
	}
	public void setZjlist(List<Map<String,Object>> zjlist) {
		this.zjlist = zjlist;
	}
	
}
