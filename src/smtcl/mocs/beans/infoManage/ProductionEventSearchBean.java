package smtcl.mocs.beans.infoManage;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.services.report.IReportService;



/**
 * 产品生产事件查询
 * @author liguoqiang
 * @date 2014-08-06
 *
 */
@ManagedBean(name="productionEventSearchBean")
@ViewScoped
public class ProductionEventSearchBean  extends PageListBaseBean implements Serializable{
	//报表服务接口实例
	private IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	//报表时间
	private Date startTime;
	private Date endTime;
	private String eventType;
	private String partId;
	private String jobdispatchNo;
	private List<Map<String,Object>> partList;//零件list
	private String jobPlanId;//选中的批次id
	private List<Map<String,Object>> jobPlanList;//批次列表
	private List<Map<String,Object>> jobdispatchList;//工单列表
	private IDataCollection<Map<String, Object>> scrapReportData;
	private String nodeid;
	private TreeNode jobroot;//工单显示
	private TreeNode jobselectroot;//工单选中
	
	public ProductionEventSearchBean(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid")+"";
			//预加载零件列表信息
		partList=partService.getTPartTypeInfo(nodeid, null, null, null);
		defaultDataModel=null;
		
		IJobPlanService jobPlanService2 = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
		jobdispatchList=new ArrayList<Map<String,Object>>();
		jobdispatchList=jobPlanService2.getJobdispatchList(nodeid,null,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
		
	}
	/**
	 * 零件下拉框改变事件
	 */
	public void selectPartChange(){
		jobPlanList=partService.getJopPlanByPartId(partId);
		jobdispatchNo="";
		jobdispatchList=new ArrayList<Map<String,Object>>();
		jobroot = new DefaultTreeNode("Root", null);
	}
	/**
	 * 批次下拉框改变事件
	 */
	public void selectJobPlanChange(){
		jobdispatchList=jobPlanService.getJobdispatchList(nodeid,null,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
	}
	/**
	 * 工单键盘按下事件
	 */
	public void onkeyupJobList() {//工单编号按下查询事件
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid=session.getAttribute("nodeid")+"";
		//工单编号列表
		List<Map<String,Object>> jobdispatchNoList=jobPlanService.getJobdispatchList(nodeid,jobdispatchNo,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchNoList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
    }
	/**
	 * 工单下拉选择事件
	 * @param event
	 */
	public void OnTreeNodeSelect(NodeSelectEvent event){//工单编号选择事件
		Map<String,Object> map=(Map<String,Object>)event.getTreeNode().getData();
		String jobdisplatNoselect=map.get("no").toString();//获取工单编号
		jobdispatchNo=jobdisplatNoselect;//当前选择工单号 显示
	}
	
	public void Search(){
		defaultDataModel=null;
	}
	/**
	 * 加工事件分析重写的分页方法
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
			if (defaultDataModel == null) {
				defaultDataModel = new PageListDataModel(pageSize2) {
					@Override
					public DataPage fetchPage(int startRow, int pageSize) {
						scrapReportData = reportService.getProductionEventScrap(startRow / pageSize + 1, pageSize2, 
								startTime, endTime, eventType, partId, jobdispatchNo);
						return new DataPage(scrapReportData.getTotalRows(), startRow,scrapReportData.getData());
					}
				};
			}
			return defaultDataModel;
	}

	@Override
	public PageListDataModel getExtendDataModel() {
		// TODO Auto-generated method stub
		return null;
	}
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public IDataCollection<Map<String, Object>> getScrapReportData() {
		return scrapReportData;
	}

	public void setScrapReportData(IDataCollection<Map<String, Object>> scrapReportData) {
		this.scrapReportData = scrapReportData;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getJobdispatchNo() {
		return jobdispatchNo;
	}
	public void setJobdispatchNo(String jobdispatchNo) {
		this.jobdispatchNo = jobdispatchNo;
	}
	public List<Map<String, Object>> getPartList() {
		return partList;
	}
	public void setPartList(List<Map<String, Object>> partList) {
		this.partList = partList;
	}
	public String getJobPlanId() {
		return jobPlanId;
	}
	public void setJobPlanId(String jobPlanId) {
		this.jobPlanId = jobPlanId;
	}
	public List<Map<String, Object>> getJobPlanList() {
		return jobPlanList;
	}
	public void setJobPlanList(List<Map<String, Object>> jobPlanList) {
		this.jobPlanList = jobPlanList;
	}
	public List<Map<String, Object>> getJobdispatchList() {
		return jobdispatchList;
	}
	public void setJobdispatchList(List<Map<String, Object>> jobdispatchList) {
		this.jobdispatchList = jobdispatchList;
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
}
