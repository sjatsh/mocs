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
 * ��Ʒ�����¼���ѯ
 * @author liguoqiang
 * @date 2014-08-06
 *
 */
@ManagedBean(name="productionEventSearchBean")
@ViewScoped
public class ProductionEventSearchBean  extends PageListBaseBean implements Serializable{
	//�������ӿ�ʵ��
	private IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	//����ʱ��
	private Date startTime;
	private Date endTime;
	private String eventType;
	private String partId;
	private String jobdispatchNo;
	private List<Map<String,Object>> partList;//���list
	private String jobPlanId;//ѡ�е�����id
	private List<Map<String,Object>> jobPlanList;//�����б�
	private List<Map<String,Object>> jobdispatchList;//�����б�
	private IDataCollection<Map<String, Object>> scrapReportData;
	private String nodeid;
	private TreeNode jobroot;//������ʾ
	private TreeNode jobselectroot;//����ѡ��
	
	public ProductionEventSearchBean(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid")+"";
			//Ԥ��������б���Ϣ
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
	 * ���������ı��¼�
	 */
	public void selectPartChange(){
		jobPlanList=partService.getJopPlanByPartId(partId);
		jobdispatchNo="";
		jobdispatchList=new ArrayList<Map<String,Object>>();
		jobroot = new DefaultTreeNode("Root", null);
	}
	/**
	 * ����������ı��¼�
	 */
	public void selectJobPlanChange(){
		jobdispatchList=jobPlanService.getJobdispatchList(nodeid,null,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
	}
	/**
	 * �������̰����¼�
	 */
	public void onkeyupJobList() {//������Ű��²�ѯ�¼�
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid=session.getAttribute("nodeid")+"";
		//��������б�
		List<Map<String,Object>> jobdispatchNoList=jobPlanService.getJobdispatchList(nodeid,jobdispatchNo,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchNoList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
    }
	/**
	 * ��������ѡ���¼�
	 * @param event
	 */
	public void OnTreeNodeSelect(NodeSelectEvent event){//�������ѡ���¼�
		Map<String,Object> map=(Map<String,Object>)event.getTreeNode().getData();
		String jobdisplatNoselect=map.get("no").toString();//��ȡ�������
		jobdispatchNo=jobdisplatNoselect;//��ǰѡ�񹤵��� ��ʾ
	}
	
	public void Search(){
		defaultDataModel=null;
	}
	/**
	 * �ӹ��¼�������д�ķ�ҳ����
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
