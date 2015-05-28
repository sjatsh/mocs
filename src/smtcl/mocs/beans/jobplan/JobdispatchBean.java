package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.cookie.DateUtils;
import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.model.TreeNode;

import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.IProductService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.utils.authority.DateUtil;
import smtcl.mocs.utils.device.Constants;

/**
 * ��������ҳ��
 *
 * @author songkaiang
 */
@ManagedBean(name = "SubJobdispatchAdd")
@SessionScoped
public class JobdispatchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private IProductService productService = (IProductService) ServiceFactory.getBean("productService");
    /**
     * �����ӿ�ʵ��
     */
    private IJobDispatchService jobDispatchService = (IJobDispatchService) ServiceFactory.getBean("jobDispatchService");
    /**
     * Ȩ�޽ӿ�
     */
    private IAuthorizeService authorityService = (IAuthorizeService)ServiceFactory.getBean("authorizeService");
    /**
     * ��ѯ�Ĺ���״̬
     */
    private String jobState;
    /**
     * ����״̬����
     */
    private List<Map<String, Object>> jobStatusMap = new ArrayList<Map<String, Object>>();
    /**
     * �豸����
     */
    private String eduTypeId;
    /**
     * �豸�������Ƽ���
     */
    private List<Map<String, Object>> eduTypeMap = new ArrayList<Map<String, Object>>();
    /**
     * ���
     */
    private String partTypeId;
    /**
     * ����������Ƽ���
     */
    private List<Map<String, Object>> partTypeMap = new ArrayList<Map<String, Object>>();
    /**
     * ������
     */
    private String taskNum;
    /**
     * �����ż���
     */
    private List<Map<String, Object>> taskNumMap = new ArrayList<Map<String, Object>>();
    private Map<String, Object> groups = new HashMap<String, Object>();
    /**
     * ��������
     */
    public String group;
    private String load = "tab1";
    /**
     * �����б�-�����
     */
    private TreeNode treeNodeBybatchNo;

    /**
     * �����б�-�豸
     */
    private TreeNode treeNodeByequName;

    private Date startTime;//��ʼʱ��
    private Date endTime;//����ʱ��
    /**
     * �Ƿ�԰�ť�в���Ȩ��
     */
    private boolean viewDisabled;

    public JobdispatchBean() {
        groups.put("�����", 1);
        groups.put("�豸", 2);
        //���
        partTypeMap.clear();
        partTypeMap = jobDispatchService.getPartTypeMap(this.getNodeId());
        //״̬
        jobStatusMap.clear();
        jobStatusMap = jobDispatchService.getJobStatus();
        //�����
        taskNumMap.clear();
        taskNumMap = jobDispatchService.getBatchNoList(this.getNodeId(), null);
        //�豸
        eduTypeMap.clear();
        eduTypeMap = jobDispatchService.getDevicesInfo(this.getNodeId());
        //��ʼʱ��
        startTime = DateUtil.getData(-1, 1);
        //����ʱ��
        Date date = DateUtil.getData(2, 1);
        endTime = new Date(date.getTime() - 1000 * 60 * 60 * 24);//��ȡ���µ����һ��

        this.authorize();
    }

    /**
     * 1���������б�״̬�ı��ǵ���
     * 2��״̬���ĺ����¼�������
     */
    public void refreshJobList(String pattern, String jobid, String status) {
        jobDispatchService.updateDispatch(jobid, Integer.parseInt(status));//���Ĺ���״̬
    }

    private String getNodeId() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        return (String) session.getAttribute("nodeid");
    }

    /**
     * ��ťȨ���ж�
     */
    private void authorize() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        TUser checkUser = (TUser) session.getAttribute(Constants.USER_SESSION_KEY);
        Long userID = new Long(checkUser.getUserId());

        String pageID = Constants.SCDD_PAGE_ID;
        viewDisabled = false;
        List<String> buttonList = authorityService.getButtonsFunctionList(userID.toString(),pageID);
        for (String b : buttonList) {
            if (b != null && Constants.BUTTONS_ID[1].equals(b)) {
                viewDisabled = true;
            }
        }

    }

    /**
     * tree�����˷���
     *
     * @param pattern
     */
    public void searchList(String pattern) {
        if (pattern.equalsIgnoreCase("treeNodeBybatchNo")) {
            treeNodeBybatchNo = productService.getBaseJobListTree(
                    this.getNodeId(),
                    DateUtils.formatDate(startTime, "yyyy-MM-dd"),
                    DateUtils.formatDate(endTime, "yyyy-MM-dd"), taskNum,
                    jobState, partTypeId, eduTypeId);
        } else {
            treeNodeByequName = productService.getJobListTree(this.getNodeId(),
                    DateUtils.formatDate(startTime, "yyyy-MM-dd"),
                    DateUtils.formatDate(endTime, "yyyy-MM-dd"), taskNum,
                    jobState, partTypeId, eduTypeId);
        }
    }

    public List<Map<String, Object>> getJobStatusMap() {
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

    public TreeNode getTreeNodeBybatchNo() {
        return treeNodeBybatchNo;
    }

    public void setTreeNodeBybatchNo(TreeNode treeNodeBybatchNo) {
        this.treeNodeBybatchNo = treeNodeBybatchNo;
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

    public TreeNode getTreeNodeByequName() {
        return treeNodeByequName;
    }

    public void setTreeNodeByequName(TreeNode treeNodeByequName) {
        this.treeNodeByequName = treeNodeByequName;
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
