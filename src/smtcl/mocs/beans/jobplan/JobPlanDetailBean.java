package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;

/**
 * 作业计划队列管理Bean
 * @作者 yyh
 * @创建时间 2013-7-2 下午13:05:16
 * @修改者 songkaiang
 * @修改日期
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="JobPlanDetail")
@SessionScoped
public class JobPlanDetailBean implements Serializable {
	//---------------------------service--------------------------------------------
    /**
	 * 工单接口实例
	 */
    IJobDispatchService dispatchService = (IJobDispatchService) ServiceFactory.getBean("jobDispatchService");
    /**
     * 权限接口
     */
    private IAuthorizeService authorityService = (IAuthorizeService)ServiceFactory.getBean("authorizeService");
    /**
	 * 零件Service接口
	 */
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	
	//---------------------------------------------------------------------------------
	/**
	 * 状态数据集
	 */
	private List<Map<String,Object>> statusList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 零件名称数据集
	 */
	private List<Map<String,Object>> partTypeList = new ArrayList<Map<String,Object>>();
    /**
     * 零件名称
     */
	private String partTypeId;

	/**
	 * 标志Id
	 */
	private String flgId;
    /**
     * 是否对按钮有操作权限
     */
    private boolean viewDisabled;
    
    /**
     * 计划类型
     */
	private String plantype;
	
	
	private String partId1;
	
	private Date subjobplanStartDate;

    private Date subjobplanEndDate;
    
    private String planId;

	/**
	 * 在构造函数数初始化数据
	 */
	public JobPlanDetailBean(){
		partTypeList.clear();

        partTypeList = dispatchService.getPartTypeMap(this.getNodeid());//map中是id，name
        this.authorize();
	}

    /**
     * 按钮权限判断
     */
    private void authorize() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        TUser checkUser = (TUser) session.getAttribute(Constants.USER_SESSION_KEY);
        String userID = checkUser.getUserId();

        String pageID = Constants.ZYJH_PAGE_ID;
        viewDisabled = false;
        List<String> buttonList = authorityService.getButtonsFunctionList(userID, pageID);
        for (String b : buttonList) {
            if (b != null && Constants.BUTTONS_ID[1].equals(b)) {
                viewDisabled = true;
            }
        }

    }
	
	public List<String> complete(String query){
		List<String> results = new ArrayList<String>();
		List<TPartTypeInfo> list = partService.getAllPartType(this.getNodeid(), query);
		for(TPartTypeInfo t:list){
			results.add(t.getName());
		}
		return results;
	}
	
//------------------------------private------------------------------------------------------------------------------	
	private String getNodeid(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }
	
//-----------------------------set-----get-----------------------------------------------------------------------------
	public List<Map<String, Object>> getStatusList() {
            if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
                statusList.clear();
                Map<String,Object> statusMap = new HashMap<String,Object>();
                statusMap.put("id", 10);
                statusMap.put("value", "Created");
                statusList.add(statusMap);
                Map<String,Object> statusMap1 = new HashMap<String,Object>();
                statusMap1.put("id", 40);
                statusMap1.put("value", "Forwarded");
                statusList.add(statusMap1);
                Map<String,Object> statusMap2 = new HashMap<String,Object>();
                statusMap2.put("id", 70);
                statusMap2.put("value", "Finished");
                statusList.add(statusMap2);
                Map<String,Object> statusMap3 = new HashMap<String,Object>();
                statusMap3.put("id", 60);
                statusMap3.put("value", "Closed");
                statusList.add(statusMap3);
            }else{
                statusList.clear();
                Map<String,Object> statusMap = new HashMap<String,Object>();
                statusMap.put("id", 10);
                statusMap.put("value", "创建");
                statusList.add(statusMap);
                Map<String,Object> statusMap1 = new HashMap<String,Object>();
                statusMap1.put("id", 40);
                statusMap1.put("value", "上线");
                statusList.add(statusMap1);
                Map<String,Object> statusMap2 = new HashMap<String,Object>();
                statusMap2.put("id", 70);
                statusMap2.put("value", "完成");
                statusList.add(statusMap2);
                Map<String,Object> statusMap3 = new HashMap<String,Object>();
                statusMap3.put("id", 60);
                statusMap3.put("value", "结束");
                statusList.add(statusMap3);
            }
		return statusList;
	}
	public void setStatusList(List<Map<String, Object>> statusList) {
		this.statusList = statusList;
	}

	public String getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public List<Map<String, Object>> getPartTypeList() {
		return partTypeList;
	}

	public void setPartTypeList(List<Map<String, Object>> partTypeList) {
		this.partTypeList = partTypeList;
	}

    public boolean isViewDisabled() {
        return viewDisabled;
    }

    public void setViewDisabled(boolean viewDisabled) {
        this.viewDisabled = viewDisabled;
    }

	public String getPlantype() {
		return plantype;
	}

	public void setPlantype(String plantype) {
		this.plantype = plantype;
	}

	public String getFlgId() {
		return flgId;
	}

	public void setFlgId(String flgId) {
		this.flgId = flgId;
	}

	public String getPartId1() {
		return partId1;
	}

	public void setPartId1(String partId1) {
		this.partId1 = partId1;
	}

	public Date getSubjobplanStartDate() {
		return subjobplanStartDate;
	}

	public void setSubjobplanStartDate(Date subjobplanStartDate) {
		this.subjobplanStartDate = subjobplanStartDate;
	}

	public Date getSubjobplanEndDate() {
		return subjobplanEndDate;
	}

	public void setSubjobplanEndDate(Date subjobplanEndDate) {
		this.subjobplanEndDate = subjobplanEndDate;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
    
}
