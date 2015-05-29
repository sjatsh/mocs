package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.utils.BundleUtils;
import smtcl.mocs.utils.authority.DateUtil;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;

/**
 *
 * 生产调度页面
 * 作者 songkaiang
 * 创建时间 2014-4-23
 * 修改者 songkaiang
 * 修改日期 2015-3-13
 * 修改说明 使用jsf的国际化
 * @version V1.0
 */
@ManagedBean(name="SubJobdispatchAdd")
@ViewScoped
public class JobdispatchBean implements Serializable{
	
	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
    /**
     *  权限接口
     */
    private IAuthorizeService authorityService = (IAuthorizeService)ServiceFactory.getBean("authorizeService");
	/**
	 * 查询的工单状态
	 */
	private String jobState;
	/**
	 * 工单状态集合
	 */
	private List<Map<String,Object>> jobStatusMap = new ArrayList<Map<String,Object>>();
	
	/**
	 * 设备名称
	 */
	private String eduTypeId;
	/**
	 * 设备类型名称集合
	 */
	private List<Map<String,Object>> eduTypeMap = new ArrayList<Map<String,Object>>();
	/**
	 * 零件
	 */
	private String partTypeId;
	/**
	 * 零件类型名称集合
	 */
	private List<Map<String,Object>> partTypeMap = new ArrayList<Map<String,Object>>();
	/**
	 * 任务编号
	 */
	private String taskNum;
	/**
	 * 任务编号集合
	 */
	private List<Map<String,Object>> taskNumMap = new ArrayList<Map<String,Object>>();
	
	private Map<String, Object> groups = new HashMap<String,Object>();
	
	/**
	 * 分组依据
	 */
	public String group;
	
	private String load="tab1";

	private Date startTime;//开始时间
	private Date endTime;//结束时间
	/**
	 * 是否对按钮有操作权限
	 */
	private boolean viewDisabled;
		
	public JobdispatchBean(){
		groups.put("任务号", 1);
		groups.put("设备", 2);
		//零件
		partTypeMap.clear();
		partTypeMap = jobDispatchService.getPartTypeMap(this.getNodeId());


		//任务号
		taskNumMap.clear();
		taskNumMap = jobDispatchService.getBatchNoList(this.getNodeId(),null);
		//设备
		eduTypeMap.clear();
		eduTypeMap = jobDispatchService.getDevicesInfo(this.getNodeId());
		//开始时间
		startTime = DateUtil.getData(-1,1);
		//结束时间
		Date date = DateUtil.getData(2,1);
		endTime = new Date(date.getTime()-1000*60*60*24);//获取当月的最后一天
		this.authorize();
	}

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }

	/**
	 * 按钮权限判断
	 */
	private void authorize() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        TUser checkUser = (TUser) session.getAttribute(Constants.USER_SESSION_KEY);
        String userID = checkUser.getUserId();
        
        String pageID = Constants.SCDD_PAGE_ID;
        viewDisabled = false;
        List<String> buttonList = authorityService.getButtonsFunctionList(userID,pageID);
        for (String b : buttonList) {
            if (b != null && Constants.BUTTONS_ID[1].equals(b)) {
                viewDisabled = true;
            }
        }

    }

    /**
     * 获取选中的节点
     * @return 返回节点uuid
     */
	private String getNodeId(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	public List<Map<String, Object>> getJobStatusMap() {
        jobStatusMap.clear();
        //从后台代码获取国际化信息
        for(int i=1;i<9;i++){
            Map<String,Object> statusMap = new HashMap<String,Object>();
            String keyName = "status.name."+i+"0";
            statusMap.put("Id", i+"0");
            statusMap.put("Name", BundleUtils.getStringFromBundle(keyName, this.getLocale(), "jobdispatch"));
            jobStatusMap.add(statusMap);
        }
        return jobStatusMap;
	}

	public void setJobStatusMap(List<Map<String, Object>> jobStatusMap) {
		this.jobStatusMap = jobStatusMap;
	}
	public List<Map<String, Object>> getTaskNumMap() {
		return taskNumMap;
	}
	public void setTaskNumMap(List<Map<String, Object>> taskNumMap) {
		this.taskNumMap = taskNumMap;
	}
	
	public String getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}

	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getEduTypeId() {
		return eduTypeId;
	}

	public void setEduTypeId(String eduTypeId) {
		this.eduTypeId = eduTypeId;
	}

	public Map<String, Object> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, Object> groups) {
		this.groups = groups;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public IJobDispatchService getJobDispatchService() {
		return jobDispatchService;
	}

	public void setJobDispatchService(IJobDispatchService jobDispatchService) {
		this.jobDispatchService = jobDispatchService;
	}

	public List<Map<String, Object>> getEduTypeMap() {
		return eduTypeMap;
	}

	public void setEduTypeMap(List<Map<String, Object>> eduTypeMap) {
		this.eduTypeMap = eduTypeMap;
	}

	public String getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public List<Map<String, Object>> getPartTypeMap() {
		return partTypeMap;
	}

	public void setPartTypeMap(List<Map<String, Object>> partTypeMap) {
		this.partTypeMap = partTypeMap;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public boolean isViewDisabled() {
		return viewDisabled;
	}

	public void setViewDisabled(boolean viewDisabled) {
		this.viewDisabled = viewDisabled;
	}
	
}
