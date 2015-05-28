package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.utils.device.Constants;

/**
 * 
 * ��ҵ�ƻ����й���Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-7-2 ����13:05:16
 * @�޸��ߣ�songkaiang
 * @�޸����ڣ�2015-1-14
 * @�޸�˵������ӹ���������ťȨ�޿���
 * @version V1.0
 */
@ManagedBean(name="JobPlanDetail")
@SessionScoped
public class JobPlanDetailBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * �����ӿ�ʵ��
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	/**
	 * ���Service�ӿ�
	 */
	private IPartService partService = (IPartService)ServiceFactory.getBean("partService");
    /**
     * Ȩ�޽ӿ�
     */
    private IAuthorizeService authorityService = (IAuthorizeService)ServiceFactory.getBean("authorizeService");
	/**
	 * ״̬���ݼ�
	 */
	private List<Map<String,Object>> statusList = new ArrayList<Map<String,Object>>();
	
	/**
	 * ����������ݼ�
	 */
	private List<Map<String,Object>> partTypeList = new ArrayList<Map<String,Object>>();
	//�������
	private String partTypeId;

    /**
     * �Ƿ�԰�ť�в���Ȩ��
     */
    private boolean viewDisabled;

	/**
	 * �ڹ��캯������ʼ������
	 */
	public JobPlanDetailBean(){
		partTypeList.clear();
		partTypeList = jobDispatchService.getPartTypeMap(this.getNodeid());//map����id��name

        this.authorize();
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

    /**
     * ��ťȨ���ж�
     */
    private void authorize() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        TUser checkUser = (TUser) session.getAttribute(Constants.USER_SESSION_KEY);
        Long userID = new Long(checkUser.getUserId());

        String pageID = Constants.ZYJH_PAGE_ID;
        viewDisabled = false;
        List<String> buttonList = authorityService.getButtonsFunctionList(userID.toString(), pageID);
        for (String b : buttonList) {
            if (b != null && Constants.BUTTONS_ID[1].equals(b)) {
                viewDisabled = true;
            }
        }

    }
	
//-----------------------------set-----get-----------------------------------------------------------------------------
	public List<Map<String, Object>> getStatusList() {
		if(statusList.size()<1){
			Map<String,Object> statusMap = new HashMap<String,Object>();
			statusMap.put("id", 10);
			statusMap.put("value", "����");
			statusList.add(statusMap);
			Map<String,Object> statusMap1 = new HashMap<String,Object>();
			statusMap1.put("id", 40);
			statusMap1.put("value", "����");
			statusList.add(statusMap1);
			Map<String,Object> statusMap2 = new HashMap<String,Object>();
			statusMap2.put("id", 70);
			statusMap2.put("value", "���");
			statusList.add(statusMap2);
			Map<String,Object> statusMap3 = new HashMap<String,Object>();
			statusMap3.put("id", 60);
			statusMap3.put("value", "����");
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
}
