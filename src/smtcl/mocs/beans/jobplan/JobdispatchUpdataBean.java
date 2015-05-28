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
 * �����޸�Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2014-2-23
 * @�޸��ߣ�songkaiang
 * @�޸����ڣ�2014-6-17
 * @�޸�˵���������޸�ҳ��������ƺͿ���
 * @version V1.0
 */
@ManagedBean(name="JobdispatchUpdata")
@ViewScoped
public class JobdispatchUpdataBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * �����ӿ�ʵ��
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	private UpdataJobDispatch updataJobDispat = (UpdataJobDispatch)ServiceFactory.getBean("updataJobDispatch");
	
	/**
	 * ����ID
	 */
	private String jobdispatchlistId;
	/**
	 * ��������
	 */
	private String jobdispatchlistName;
	/**
	 * ��������
	 */
	private String jobdispatchlistNum;
	/**
	 * �豸����ID
	 */
	private String equipmentTypeId;
	/**
	 * �豸����ID���豸�������Ƽ���
	 */
	private Map<String,Object> equipmentTypeMap = new HashMap<String,Object>();
	/**
	 * TEquJobDispatch������Ϣ����
	 */
	private List<Map<String,Object>> tequJobDispatchList = new ArrayList<Map<String,Object>>();
	/**
	 * ������ʼʱ��
	 */
	private Date jobdispatchlistStartDate;
	/**
	 * ��������ʱ��
	 */
	private Date jobdispatchlistEndDate;
	/**
	 * ��ѡ���ֵ
	 */
	private List<String> checkBoxValue;
	/**
	 * //��ʾ���������Ƿ��ظ�
	 */
	private String showMsg; 
	
	/**
	 * ��������
	 */
	private String startJob;
	
	/**
	 * �������
	 */
	private String jobdispatchpartName;
	/**
	 * ����״̬
	 */
	private String jobStatus;
	
	/**
	 * ���캯�� 
	 */
	@SuppressWarnings("unchecked")
	public JobdispatchUpdataBean(){
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();	
		String id = "";
		if(request.getParameter("editPId")!=null){  //�ж��Ƿ�Ϊ��
			id = request.getParameter("editPId").trim();
		}
		jobdispatchlistId = id; //������̨
		List<Map<String,Object>> lstlst = jobDispatchService.getEquTypeMapByDisPatchId(jobdispatchlistId); //�豸����
		for(Map<String,Object> map : lstlst){
			if(map.get("id")!=null && !map.get("id").equals("")){
				equipmentTypeMap.put((String)map.get("name"), map.get("id").toString());
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map = jobDispatchService.getJobDispatchById(nodeid,id); //ͨ������ID�õ�������Ϣ
		List<Map<String,Object>> lst =  (List<Map<String, Object>>) map.get("lst");  //������Ϣ
		
		if(lst.size()>0){ //������Ϣ
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
	 * �����༭-->��������
	 */
	public void startJob(){
		this.setStartJob("true");
		this.updataJobdispatchlist();
	}
	
	/**
	 * ��ѯ���������Ƿ��ظ�
	 */
	public void getDispatchNameRepeat(){	
		if(jobdispatchlistName==null ||"".equals(jobdispatchlistName)){
			    showMsg = "����Ϊ��!";
		}else{		
				boolean b = jobDispatchService.getDispatchNameRepeat(jobdispatchlistName);
				if(b == true){
					showMsg = "�����ظ�!";
				}else{
					showMsg = "����ʹ��!";
				}
		     }
	}	
	
    /**
     * �޸�
     */
	public void updataJobdispatchlist(){	
		jobDispatchService.updataJobdispatchlist(this);	
	}
	/**
	 * �޸�TEquJobDispatch���е�����״̬
	 */
	public void updataJobDispatch(String serialNo,String status){
		//����TEquJobDispatch���״̬
		updataJobDispat.updataJobDispatchStatus(serialNo,status,this.getJobdispatchlistId());
		//���¼�������
		tequJobDispatchList = jobDispatchService.getEquJobDispatchList(this.getJobdispatchlistId());
	}
	
	private String serailNo;
	
	/**
	 * ��TEquJobDispatch������ӹ������豸����
	 */
	public void save(){
		if(!serailNo.isEmpty())
			updataJobDispat.save(serailNo,this.getJobdispatchlistId());
		
		tequJobDispatchList.clear();
		tequJobDispatchList = jobDispatchService.getEquJobDispatchList(this.getJobdispatchlistId());
	}
	
	/******************************set,get����**********************************************/

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
