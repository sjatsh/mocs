package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.services.jobplan.UpdataJobDispatch;

/**
 * 
 * 工单修改Bean
 * @作者：yyh
 * @创建时间：2014-2-23
 * @修改者：songkaiang
 * @修改日期：2014-6-17
 * @修改说明：工单修改页面重新设计和开发
 * @version V1.0
 */
@ManagedBean(name="JobdispatchUpdata")
@ViewScoped
public class JobdispatchUpdataBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	private UpdataJobDispatch updataJobDispat = (UpdataJobDispatch)ServiceFactory.getBean("updataJobDispatch");
	
	/**
	 * 工单ID
	 */
	private String jobdispatchlistId;
	/**
	 * 工单名称
	 */
	private String jobdispatchlistName;
	/**
	 * 工单数量
	 */
	private String jobdispatchlistNum;
	/**
	 * 设备类型ID
	 */
	private String equipmentTypeId;
	/**
	 * 设备类型ID和设备类型名称集合
	 */
	private Map<String,Object> equipmentTypeMap = new HashMap<String,Object>();
	/**
	 * TEquJobDispatch对象信息集合
	 */
	private List<Map<String,Object>> tequJobDispatchList = new ArrayList<Map<String,Object>>();
	/**
	 * 工单开始时间
	 */
	private Date jobdispatchlistStartDate;
	/**
	 * 工单结束时间
	 */
	private Date jobdispatchlistEndDate;
	/**
	 * 多选框的值
	 */
	private List<String> checkBoxValue;
	/**
	 * //显示工单名称是否重复
	 */
	private String showMsg; 
	
	/**
	 * 启动工单
	 */
	private String startJob;
	
	/**
	 * 零件名称
	 */
	private String jobdispatchpartName;
	/**
	 * 工单状态
	 */
	private String jobStatus;
	
	/**
	 * 构造函数 
	 */
	@SuppressWarnings("unchecked")
	public JobdispatchUpdataBean(){
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();	
		String id = "";
		if(request.getParameter("editPId")!=null){  //判断是否为空
			id = request.getParameter("editPId").trim();
		}
		jobdispatchlistId = id; //传给后台
		List<Map<String,Object>> lstlst = jobDispatchService.getEquTypeMapByDisPatchId(jobdispatchlistId); //设备类型
		for(Map<String,Object> map : lstlst){
			if(map.get("id")!=null && !map.get("id").equals("")){
				equipmentTypeMap.put((String)map.get("name"), map.get("id").toString());
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map = jobDispatchService.getJobDispatchById(nodeid,id); //通过工单ID得到所有信息
		List<Map<String,Object>> lst =  (List<Map<String, Object>>) map.get("lst");  //工单信息
		
		if(lst.size()>0){ //工单信息
			Map<String,Object> dismap = lst.get(0);
			jobdispatchlistName =  (String)dismap.get("name");
			if(dismap.get("processNum")!=null){
				jobdispatchlistNum = dismap.get("processNum").toString();
			}
			if(dismap.get("id")!=null){
				equipmentTypeId =  dismap.get("id").toString();
			}
			if(dismap.get("partName")!=null){
				jobdispatchpartName = dismap.get("partName").toString();
			}
			if(dismap.get("status")!=null){
				jobStatus = dismap.get("status").toString();
			}
			
			try {
				if(dismap.get("planStarttime")!=null){
					jobdispatchlistStartDate = sdf.parse((String)dismap.get("planStarttime"));
				}
				if(dismap.get("planEndtime")!=null){
					jobdispatchlistEndDate = sdf.parse((String)dismap.get("planEndtime"));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		tequJobDispatchList.clear();
		tequJobDispatchList = jobDispatchService.getEquJobDispatchList(this.getJobdispatchlistId());
	}
	

	/**
	 * 工单编辑-->启动工单
	 */
	public void startJob(){
		this.setStartJob("true");
		this.updataJobdispatchlist();
	}
	
	/**
	 * 查询工单名称是否重复
	 */
	public void getDispatchNameRepeat(){	
		if(jobdispatchlistName==null ||"".equals(jobdispatchlistName)){
			    showMsg = "不能为空!";
		}else{		
				boolean b = jobDispatchService.getDispatchNameRepeat(jobdispatchlistName);
				if(b == true){
					showMsg = "名称重复!";
				}else{
					showMsg = "可以使用!";
				}
		     }
	}	
	
    /**
     * 修改
     */
	public void updataJobdispatchlist(){	
		jobDispatchService.updataJobdispatchlist(this);	
	}
	/**
	 * 修改TEquJobDispatch表中的数据状态
	 */
	public void updataJobDispatch(String serialNo,String status){
		//更新TEquJobDispatch表的状态
		updataJobDispat.updataJobDispatchStatus(serialNo,status,this.getJobdispatchlistId());
		//重新加载数据
		tequJobDispatchList = jobDispatchService.getEquJobDispatchList(this.getJobdispatchlistId());
	}
	
	private String serailNo;
	
	/**
	 * 向TEquJobDispatch表中添加工单和设备关联
	 */
	public void save(){
		if(!serailNo.isEmpty())
			updataJobDispat.save(serailNo,this.getJobdispatchlistId());
		
		tequJobDispatchList.clear();
		tequJobDispatchList = jobDispatchService.getEquJobDispatchList(this.getJobdispatchlistId());
	}
	
	/******************************set,get方法**********************************************/

	public String getSerailNo() {
		return serailNo;
	}

	public void setSerailNo(String serailNo) {
		this.serailNo = serailNo;
	}

	public IJobPlanService getJobPlanService() {
		return jobPlanService;
	}

	public void setJobPlanService(IJobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
	}

	public String getJobdispatchlistName() {
		return jobdispatchlistName;
	}

	public void setJobdispatchlistName(String jobdispatchlistName) {
		this.jobdispatchlistName = jobdispatchlistName;
	}

	public String getJobdispatchlistNum() {
		return jobdispatchlistNum;
	}

	public void setJobdispatchlistNum(String jobdispatchlistNum) {
		this.jobdispatchlistNum = jobdispatchlistNum;
	}

	public String getEquipmentTypeId() {
		return equipmentTypeId;
	}

	public void setEquipmentTypeId(String equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}

	public Map<String, Object> getEquipmentTypeMap() {
		return equipmentTypeMap;
	}

	public void setEquipmentTypeMap(Map<String, Object> equipmentTypeMap) {
		this.equipmentTypeMap = equipmentTypeMap;
	}

	public Date getJobdispatchlistStartDate() {
		return jobdispatchlistStartDate;
	}

	public void setJobdispatchlistStartDate(Date jobdispatchlistStartDate) {
		this.jobdispatchlistStartDate = jobdispatchlistStartDate;
	}

	public Date getJobdispatchlistEndDate() {
		return jobdispatchlistEndDate;
	}

	public void setJobdispatchlistEndDate(Date jobdispatchlistEndDate) {
		this.jobdispatchlistEndDate = jobdispatchlistEndDate;
	}

	public List<String> getCheckBoxValue() {
		return checkBoxValue;
	}

	public void setCheckBoxValue(List<String> checkBoxValue) {
		this.checkBoxValue = checkBoxValue;
	}

	public String getShowMsg() {
		return showMsg;
	}

	public void setShowMsg(String showMsg) {
		this.showMsg = showMsg;
	}

	public String getJobdispatchlistId() {
		return jobdispatchlistId;
	}

	public void setJobdispatchlistId(String jobdispatchlistId) {
		this.jobdispatchlistId = jobdispatchlistId;
	}
	
	public List<Map<String, Object>> getTequJobDispatchList() {
		return tequJobDispatchList;
	}

	public void setTequJobDispatchList(List<Map<String, Object>> tequJobDispatchList) {
		this.tequJobDispatchList = tequJobDispatchList;
	}

	public String getStartJob() {
		return startJob;
	}

	public void setStartJob(String startJob) {
		this.startJob = startJob;
	}

	public String getJobdispatchpartName() {
		return jobdispatchpartName;
	}
	public void setJobdispatchpartName(String jobdispatchpartName) {
		this.jobdispatchpartName = jobdispatchpartName;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

}
