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
 * ��������ҳ��
 * ���� songkaiang
 * ����ʱ�� 2014-4-23
 * �޸��� songkaiang
 * �޸����� 2015-3-13
 * �޸�˵�� ʹ��jsf�Ĺ��ʻ�
 * @version V1.0
 */
@ManagedBean(name="SubJobdispatchAdd")
@ViewScoped
public class JobdispatchBean implements Serializable{
	
	/**
	 * �����ӿ�ʵ��
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
    /**
     *  Ȩ�޽ӿ�
     */
    private IAuthorizeService authorityService = (IAuthorizeService)ServiceFactory.getBean("authorizeService");
	/**
	 * ��ѯ�Ĺ���״̬
	 */
	private String jobState;
	/**
	 * ����״̬����
	 */
	private List<Map<String,Object>> jobStatusMap = new ArrayList<Map<String,Object>>();
	
	/**
	 * �豸����
	 */
	private String eduTypeId;
	/**
	 * �豸�������Ƽ���
	 */
	private List<Map<String,Object>> eduTypeMap = new ArrayList<Map<String,Object>>();
	/**
	 * ���
	 */
	private String partTypeId;
	/**
	 * ����������Ƽ���
	 */
	private List<Map<String,Object>> partTypeMap = new ArrayList<Map<String,Object>>();
	/**
	 * ������
	 */
	private String taskNum;
	/**
	 * �����ż���
	 */
	private List<Map<String,Object>> taskNumMap = new ArrayList<Map<String,Object>>();
	
	private Map<String, Object> groups = new HashMap<String,Object>();
	
	/**
	 * ��������
	 */
	public String group;
	
	private String load="tab1";

	private Date startTime;//��ʼʱ��
	private Date endTime;//����ʱ��
	/**
	 * �Ƿ�԰�ť�в���Ȩ��
	 */
	private boolean viewDisabled;
		
	public JobdispatchBean(){
		groups.put("�����", 1);
		groups.put("�豸", 2);
		//���
		partTypeMap.clear();
		partTypeMap = jobDispatchService.getPartTypeMap(this.getNodeId());


		//�����
		taskNumMap.clear();
		taskNumMap = jobDispatchService.getBatchNoList(this.getNodeId(),null);
		//�豸
		eduTypeMap.clear();
		eduTypeMap = jobDispatchService.getDevicesInfo(this.getNodeId());
		//��ʼʱ��
		startTime = DateUtil.getData(-1,1);
		//����ʱ��
		Date date = DateUtil.getData(2,1);
		endTime = new Date(date.getTime()-1000*60*60*24);//��ȡ���µ����һ��
		this.authorize();
	}

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }

	/**
	 * ��ťȨ���ж�
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
     * ��ȡѡ�еĽڵ�
     * @return ���ؽڵ�uuid
     */
	private String getNodeId(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	public List<Map<String, Object>> getJobStatusMap() {
        jobStatusMap.clear();
        //�Ӻ�̨�����ȡ���ʻ���Ϣ
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
