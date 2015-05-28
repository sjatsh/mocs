package smtcl.mocs.beans.erp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.ErpMaterialDataModel;
import smtcl.mocs.pojos.job.TProductionScrapInfo;
import smtcl.mocs.services.erp.IERPSerice;
import smtcl.mocs.services.jobplan.IJobPlanService;



/**
 * 2017-07-01
 * @author liguoqiang
 *
 */
@ManagedBean(name="productionScrapAddBean")
@ViewScoped
public class ProductionScrapAddBean{
	private List<Map<String,Object>> scrapUserList;//报废账户列表
	private String scrapUserName;
	private List<Map<String,Object>> jobdispatchNoList;//工单编号列表
	private List<Map<String,Object>> erpTaskNoList;//erp任务号列表
	private List<Map<String,Object>> processNoList;//工序列表
	private List<Map<String,Object>>  userList;//用户列表
	private List<Map<String,Object>> sectionList;//工段列表
	private String jobdispatchNo;//工单编号
	private String processScrapNum;//工废数量
	private String materialScrapNum;//料废数量
	private String scrapSum;//报废总数
	private List<String> isCurrentProcess;//是否至工序已加工
	private TProductionScrapInfo  tps=new TProductionScrapInfo();//生产报废属性
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	private IERPSerice erpCommonService = (IERPSerice)ServiceFactory.getBean("erpCommonService");//注入另外一个数据库的service
	private TreeNode jobroot;//工单显示
	private TreeNode jobselectroot;//工单选中
	private Date checkTime;//检票日期
	private String message;//添加是否成功消息
	private String tackTike;//开票人员
	private String onlineProcessId;//前道工序id
	private String processPlanID;//当前工单的工艺方案id
	private List<Map<String,Object>> partList;//零件list
	private String selectedPartId;//选中的零件id
	private List<Map<String,Object>> jobPlanList;//批次列表
	private String jobPlanId;//选中的批次id
	private String nodeid;
	private List<Map<String,Object>> checkList;
	private List isConcession;//是否让步接受
	private List<Map<String,Object>> porcessTimeList;//工单 工序工时清单
	private ErpMaterialDataModel processList;//工时算法 所有数据显示
	private Map<String,Object>[] selectProcess;//选中的工时
	private String djgs;//单件工时
	private String hjgs;//合计工时
	private String ljgs;//累计工时
	private String dxgs;//当序工时
	private List<Object> selectedpl=new ArrayList<Object>();//选中工序集合
	private List<Map<String,Object>> zrcjlist=new ArrayList<Map<String,Object>>();//责任产家列表
	
	@SuppressWarnings("unchecked")
	public ProductionScrapAddBean(){
//		String wy=StringUtils.getUniqueString();//唯一标识
//		String tzdcode="BF_";
//		tps.setTzdCode(tzdcode);
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		IERPSerice erpCommonService = (IERPSerice)ServiceFactory.getBean("erpCommonService");//注入另外一个数据库的service
		//预加载用户列表
//		IUserService us = (IUserService) ServiceFactory.getBean ("userService");
//		List<User> userlsit=us.getUserbyAll(); 
		userList=new ArrayList<Map<String,Object>>();
		//初始化报废账户列表
		scrapUserList=new ArrayList<Map<String,Object>>();
		scrapUserList=erpCommonService.getScrapUser();
		//责任产家数据列表
		zrcjlist=erpCommonService.getWisVendorListMapForAll();
		
		
		//预加载工单编号
//		IJobPlanService jobPlanService2 = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
		jobdispatchNoList=new ArrayList<Map<String,Object>>();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid")+"";
		jobdispatchNoList=jobPlanService.getJobdispatchList(nodeid,null,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchNoList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
		
		//预加载工段列表
		sectionList=new ArrayList<Map<String,Object>>();
		String[] gd={"ETC及共用加工单元ETC工段","ETC及共用加工单元三杠工段","ETC及共用加工单元大件工段","中K加工单元中K床身工段","中K加工单元主轴工段",
				"中K加工单元奇型工段","中K加工单元床鞍工段","小K加工单元36床身工段","小K加工单元柔性加工工段","小K加工单元溜板送刀工段 ","小K加工单元箱体工段"};
		for(int i=0;i<gd.length;i++){
			Map<String,Object> gdmap=new HashMap<String, Object>();
			 gdmap.put("gd", gd[i]);
			 sectionList.add(gdmap);
		}
		//预加载 检查人员列表
		checkList=new ArrayList<Map<String,Object>>();
		String[] check={"加检01","加检02","加检03","加检09","加检10","加检12","加检15","加检16","加检17","加检18","加检20","加检24","加检25",
				"加检29"};
		for(int i=0;i<check.length;i++){
			Map<String,Object> gdmap=new HashMap<String, Object>();
			 gdmap.put("check", check[i]);
			 checkList.add(gdmap);
		}
		 
		 tps.setInvoiceDat(new Date());
		 checkTime=new Date();
		 //从生产调度页面 带参跳过来
		if ( null!=request.getParameter("jobdispatchNO")){
			String jobdisplatNoselect=request.getParameter("jobdispatchNO");
			Map<String,Object> mapJob=jobPlanService.getDataByjobdispatchNo(jobdisplatNoselect);
			erpTaskNoList=new ArrayList<Map<String,Object>>();
			tps.setEntityName(null==mapJob.get("taskNO")?"":mapJob.get("taskNO").toString());//erp任务号
			tps.setItemCode(null==mapJob.get("itemCode")?"":mapJob.get("itemCode").toString());//物料编码信息填充
			tps.setItemDesc(null==mapJob.get("itemDesc")?"":mapJob.get("itemDesc").toString());//物料描述信息填充
			tps.setToOperationNum(null==mapJob.get("toOperationNum")?"":mapJob.get("toOperationNum").toString());//添加 至工序
			onlineProcessId=null==mapJob.get("onlineProcessId")?"":mapJob.get("onlineProcessId").toString();//设置前道工序
			jobdispatchNo=jobdisplatNoselect;//当前选择工单号 显示
			tps.setResult("报废");
			processNoList=new ArrayList<Map<String,Object>>();
			processNoList=(List<Map<String,Object>>)mapJob.get("zrOperationNum");//责任工序列表
			processPlanID=mapJob.get("processPlanID")+"";
			selectedPartId=mapJob.get("partId")+"";//物料id
			request.setAttribute("jobdispatchNO", null);
			processList=new ErpMaterialDataModel(processNoList);
		}
		//预加载零件列表信息
		partList=erpCommonService.getTPartTypeInfoByNodeid(nodeid,null);
	} 
	

	/**
	 * 零件下拉框改变事件
	 */
	public void selectPartChange(){
		jobPlanList=erpCommonService.getJopPlanByPartId(selectedPartId);
		List<Map<String,Object>> partlist=erpCommonService.getTPartTypeInfoByNodeid(nodeid,selectedPartId);
		if(null!=partlist&&partlist.size()>0){
			Map<String,Object> partMap=partlist.get(0);
			tps.setItemCode(partMap.get("no").toString() );
		}
		jobdispatchNo="";
		tps.setEntityName("");
		tps.setToOperationNum("");
		processNoList=new ArrayList<Map<String,Object>>();
		processList=new ErpMaterialDataModel(processNoList);
		djgs="0";//单件工时
		hjgs="0";//合计工时
		ljgs="0";//累计工时
		dxgs="0";//当序工时
		isCurrentProcess = new ArrayList<String>();
		isConcession=new ArrayList();
	}
	
	
	/**
	 * 批次下拉框改变事件
	 */
	public void selectJobPlanChange(){
		jobdispatchNoList=jobPlanService.getJobdispatchList(nodeid,null,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchNoList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
	}
	
	public void onkeyupJobList() {//工单编号按下查询事件
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid=session.getAttribute("nodeid")+"";
		jobdispatchNoList=jobPlanService.getJobdispatchList(nodeid,jobdispatchNo,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchNoList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
    }
	
	@SuppressWarnings("unchecked")
	public void OnTreeNodeSelect(NodeSelectEvent event){//工单编号选择事件
		Map<String,Object> map=(Map<String,Object>)event.getTreeNode().getData();
		String jobdisplatNoselect=map.get("no").toString();//获取工单编号
		Map<String,Object> mapJob=jobPlanService.getDataByjobdispatchNo(jobdisplatNoselect);
		
		erpTaskNoList=new ArrayList<Map<String,Object>>();
		tps.setEntityName(null==mapJob.get("taskNO")?"":mapJob.get("taskNO").toString());//erp任务号
		
		tps.setItemCode(null==mapJob.get("itemCode")?"":mapJob.get("itemCode").toString());//物料编码信息填充
		tps.setItemDesc(null==mapJob.get("itemDesc")?"":mapJob.get("itemDesc").toString());//物料描述信息填充
		tps.setToOperationNum(null==mapJob.get("toOperationNum")?"":mapJob.get("toOperationNum").toString());//添加 至工序
		onlineProcessId=null==mapJob.get("onlineProcessId")?"":mapJob.get("onlineProcessId").toString();//设置前道工序
		
		jobdispatchNo=jobdisplatNoselect;//当前选择工单号 显示
		tps.setResult("报废");
		
		selectedPartId=mapJob.get("partId")+"";//物料id
		
		processNoList=new ArrayList<Map<String,Object>>();
		processNoList=(List<Map<String,Object>>)mapJob.get("zrOperationNum");//责任工序列表
		processPlanID=mapJob.get("processPlanID")+"";
		
		//添加工时列表数据
		processList=new ErpMaterialDataModel(processNoList);
	}
	/**
	 * 工废料废 计算总废
	 */
	public void GFLFSUM(){
		scrapSum="0";
		if(null!=processScrapNum&&!"".equals(processScrapNum)){
			scrapSum=Integer.parseInt(processScrapNum)+"";
		}
		if(null!=materialScrapNum&&!"".equals(materialScrapNum)){
			scrapSum=(Integer.parseInt(scrapSum)+Integer.parseInt(materialScrapNum))+"";
		}
		
	}
	/**
	 * 计算工时信息
	 */
	public void jsgsInfo(){
		int onePorcessTime=0;//单件工时
		int totalProcessTime=0;//合计工时
		int totalTime=0;//累计工时
		int processTime=0;//当序工时
		
		for(Map<String,Object> proMap:processNoList){
			if(proMap.get("processOrder").toString().equals(tps.getToOperationNum())){//判断至工序
				//赋值 当序工时
				processTime=Integer.parseInt(null==proMap.get("processingTime")?"0":proMap.get("processingTime").toString());
				break;
			}
		}
		for(Map<String,Object> processMap:selectProcess){
			//判断当前是否选中至工序 如果选中则勾选 是否至工序加工
			if(processMap.get("processOrder").toString().equals(tps.getToOperationNum())){
				isCurrentProcess.add("是");
			}
			//当件工时
			onePorcessTime=onePorcessTime+Integer.parseInt(null==processMap.get("processingTime")?"0":processMap.get("processingTime").toString());
			
			selectedpl.add(processMap.get("processOrder"));
		}
		//合计工时
		totalProcessTime=processTime*Integer.parseInt(null==scrapSum?"0":scrapSum);
		//累计工时
		totalTime=onePorcessTime*Integer.parseInt(null==scrapSum?"0":scrapSum);
		
		dxgs=processTime+"";//当序工时
		djgs=onePorcessTime+"";//单件工时
		hjgs=totalProcessTime+"";//合计工时
		if(null!=isConcession&&isConcession.size()>0){
			ljgs=(totalTime*0.5)+"";//累计工时
		}else{
			ljgs=totalTime+"";//累计工时
		}
		
		message=erpCommonService.getJobdispatchTSXX(jobdispatchNo, onlineProcessId, processScrapNum, materialScrapNum, isCurrentProcess);
		
	}
	
	/**
	 * 是否至工序选中方法 
	 */
	@SuppressWarnings("unchecked")
	public void SelectCurrentProcess(){
		if(isCurrentProcess.size()>0){//如果当前已经勾选了 是否至工序加工  那么 工时算法里面至工序必须勾选
			Map<String,Object>[] ls=selectProcess;
			boolean bool=true;//判断当前选中是否已经有了至工序 默认没有选中
			for(Map<String,Object> pMap:selectProcess){
				if(pMap.get("processOrder").toString().equals(tps.getToOperationNum())){
					bool=false;//已选中
					break;
				}
			}
			if(bool){
				for(Map<String,Object> proMap:processNoList){
					if(proMap.get("processOrder").toString().equals(tps.getToOperationNum())){//判断至工序
						selectProcess=new Map[null==selectProcess?0:selectProcess.length+1];//给数组添加1；
						if(null!=ls&&ls.length>0){
							for(int i=0;i<ls.length;i++){
								selectProcess[i]=ls[i];
							}
							selectProcess[ls.length]=proMap;	
						}else{
							selectProcess[0]=proMap;	
						}
						break;
					}
				}
				processList=new ErpMaterialDataModel(processNoList);
			}
		}else{
			Map<String,Object>[] ls=selectProcess;
			selectProcess=new Map[null==selectProcess?0:ls.length-1];
			if(selectProcess.length>0){
				int j=0;
				for(int i=0;i<ls.length;i++){
					 Map<String,Object> pMap=ls[i];
					 System.out.println(pMap.get("processOrder").toString()+"---"+tps.getToOperationNum().toString());
					if(!pMap.get("processOrder").toString().trim().equals(tps.getToOperationNum().toString().trim())){
						selectProcess[j]=ls[i];
						j++;
					}
				}
			}
			
		}
	}
	/**
	 * 保存数据
	 */
	public void saveData(String parm){
		if("1".equals(parm)){
			
		}else{
			if(null!=isConcession&&isConcession.size()>0){
				djgs=(Integer.parseInt(djgs)*0.5)+"";//单件工时
			}
			message=erpCommonService.saveTProductionScrapInfo(tps, jobdispatchNo, isCurrentProcess, processScrapNum,materialScrapNum, 
					onlineProcessId,processPlanID,selectedPartId,djgs,selectedpl,scrapUserName);
			if(message.equals("保存成功")){
				tps=new TProductionScrapInfo();
				jobdispatchNo="";
				selectedPartId="";
				jobPlanId="";
				processScrapNum="";
				materialScrapNum="";
				scrapSum="";
				tackTike="";
				isCurrentProcess=new ArrayList();
			}
		}
	}
	
	public List<Map<String, Object>> getScrapUserList() {
		return scrapUserList;
	}
	public void setScrapUserList(List<Map<String, Object>> scrapUserList) {
		this.scrapUserList = scrapUserList;
	}
	public List<Map<String, Object>> getJobdispatchNoList() {
		return jobdispatchNoList;
	}
	public void setJobdispatchNoList(List<Map<String, Object>> jobdispatchNoList) {
		this.jobdispatchNoList = jobdispatchNoList;
	}
	
	public List<Map<String, Object>> getErpTaskNoList() {
		return erpTaskNoList;
	}
	public void setErpTaskNoList(List<Map<String, Object>> erpTaskNoList) {
		this.erpTaskNoList = erpTaskNoList;
	}
	public List<Map<String, Object>> getProcessNoList() {
		return processNoList;
	}
	public void setProcessNoList(List<Map<String, Object>> processNoList) {
		this.processNoList = processNoList;
	}
	public List<Map<String, Object>> getUserList() {
		return userList;
	}
	public void setUserList(List<Map<String, Object>> userList) {
		this.userList = userList;
	}
	public List<Map<String, Object>> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<Map<String, Object>> sectionList) {
		this.sectionList = sectionList;
	}
	public TProductionScrapInfo getTps() {
		return tps;
	}
	public void setTps(TProductionScrapInfo tps) {
		this.tps = tps;
	}
	public String getJobdispatchNo() {
		return jobdispatchNo;
	}
	public void setJobdispatchNo(String jobdispatchNo) {
		this.jobdispatchNo = jobdispatchNo;
	}
	public String getProcessScrapNum() {
		return processScrapNum;
	}
	public void setProcessScrapNum(String processScrapNum) {
		this.processScrapNum = processScrapNum;
	}
	public String getMaterialScrapNum() {
		return materialScrapNum;
	}
	public void setMaterialScrapNum(String materialScrapNum) {
		this.materialScrapNum = materialScrapNum;
	}
	public String getScrapSum() {
		return scrapSum;
	}
	public void setScrapSum(String scrapSum) {
		this.scrapSum = scrapSum;
	}
	public List getIsCurrentProcess() {
		return isCurrentProcess;
	}
	public void setIsCurrentProcess(List isCurrentProcess) {
		this.isCurrentProcess = isCurrentProcess;
	}
	public TreeNode getJobroot() {
		return jobroot;
	}
	public void setJobroot(TreeNode jobroot) {
		this.jobroot = jobroot;
	}
	public TreeNode getJobselectroot() {
		return jobselectroot;
	}
	public void setJobselectroot(TreeNode jobselectroot) {
		this.jobselectroot = jobselectroot;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTackTike() {
		return tackTike;
	}
	public void setTackTike(String tackTike) {
		this.tackTike = tackTike;
	}
	public List<Map<String,Object>> getPartList() {
		return partList;
	}
	public void setPartList(List<Map<String,Object>> partList) {
		this.partList = partList;
	}
	public String getSelectedPartId() {
		return selectedPartId;
	}
	public void setSelectedPartId(String selectedPartId) {
		this.selectedPartId = selectedPartId;
	}
	public List<Map<String,Object>> getJobPlanList() {
		return jobPlanList;
	}
	public void setJobPlanList(List<Map<String,Object>> jobPlanList) {
		this.jobPlanList = jobPlanList;
	}
	public String getJobPlanId() {
		return jobPlanId;
	}
	public void setJobPlanId(String jobPlanId) {
		this.jobPlanId = jobPlanId;
	}
	public List<Map<String,Object>> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<Map<String,Object>> checkList) {
		this.checkList = checkList;
	}
	public List getIsConcession() {
		return isConcession;
	}
	public void setIsConcession(List isConcession) {
		this.isConcession = isConcession;
	}
	public List<Map<String, Object>> getPorcessTimeList() {
		return porcessTimeList;
	}
	public void setPorcessTimeList(List<Map<String, Object>> porcessTimeList) {
		this.porcessTimeList = porcessTimeList;
	}
	public ErpMaterialDataModel getProcessList() {
		return processList;
	}
	public void setProcessList(ErpMaterialDataModel processList) {
		this.processList = processList;
	}
	public Map<String, Object>[] getSelectProcess() {
		return selectProcess;
	}
	public void setSelectProcess(Map<String, Object>[] selectProcess) {
		this.selectProcess = selectProcess;
	}
	public String getDjgs() {
		return djgs;
	}
	public void setDjgs(String djgs) {
		this.djgs = djgs;
	}
	public String getHjgs() {
		return hjgs;
	}
	public void setHjgs(String hjgs) {
		this.hjgs = hjgs;
	}
	public String getLjgs() {
		return ljgs;
	}
	public void setLjgs(String ljgs) {
		this.ljgs = ljgs;
	}
	public String getDxgs() {
		return dxgs;
	}
	public void setDxgs(String dxgs) {
		this.dxgs = dxgs;
	}
	public List<Map<String, Object>> getZrcjlist() {
		return zrcjlist;
	}
	public void setZrcjlist(List<Map<String, Object>> zrcjlist) {
		this.zrcjlist = zrcjlist;
	}
	public String getScrapUserName() {
		return scrapUserName;
	}
	public void setScrapUserName(String scrapUserName) {
		this.scrapUserName = scrapUserName;
	}
	
	
}
