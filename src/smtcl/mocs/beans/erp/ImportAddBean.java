package smtcl.mocs.beans.erp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.model.erp.JobPlanDataModel;
import smtcl.mocs.model.erp.JobPlanImportInfo;
import smtcl.mocs.services.erp.IImportService;
import smtcl.mocs.utils.device.StringUtils;

/**
 * �������ε��빦�ܿ���bean
 * @author songkaiang
 *
 */
@ManagedBean(name="importAddBean")
@ViewScoped
public class ImportAddBean implements Serializable{
	
	private static final long serialVersionUID = -3109061148374638774L;

	private IImportService importService = (IImportService)ServiceFactory.getBean("importService");
	
	//table��ʾ������
	private List<JobPlanImportInfo> outData = new ArrayList<JobPlanImportInfo>();
	/**
	 * dataTable����  ʵ���˶���ѡ��
	 */
	private JobPlanDataModel mediumModel;
	/**
	 * ѡ�е�����
	 */
	private JobPlanImportInfo[] selectedInfo;
	/**
	 * ��ѯ��ʼʱ��ͽ���ʱ��
	 */
	private Date startTime;
	private Date endTime;
	
	/**
	 * �����־
	 */
	private int flag;
	/**
	 * �����־���ݼ�
	 */
	private Map<String,Object> flagMap = new TreeMap<String,Object>();
	/**
	 * ����֮����ҳ������ʾ����Ϣ
	 */
	private String isSuccess;
	
	public ImportAddBean(){
		//���쿪ʼʱ��ͽ���ʱ��
		Date[] dates=StringUtils.convertDatea(1);
		startTime=dates[0];
		endTime=dates[1];
		//���촦���־��Ϣ��
		flagMap.clear();
		flagMap.put("δ����", 0);
		flagMap.put("�ѹ���", 1);
		flagMap.put("�ѵ���", 2);
		
		this.SubmitSearch();
	}
	//���ݲ�ѯ
	public void SubmitSearch(){
		//ÿ�β�ѯ�������
		outData.clear();
		//��ȡǰ��Ҫչ�ֵ�����
		outData=importService.getJobPlanImportInfo(startTime, endTime, flag);
		//�����ݷ�װ��JobPlanDataModel
		mediumModel = new JobPlanDataModel(outData);
	}
	//���ݵ���
	public void importData(){
		boolean success = false;
		for(JobPlanImportInfo jp : selectedInfo){
			if(!jp.getFlag().equals("δ����")){
				isSuccess = "��ѡ�����־Ϊδ���롣";
				return;
			}
			System.out.println(jp.getJobno());
			//�����ݱ��浽TJobplanInfo��t_jobplan_info������
			//�������ʧ�ܲ���erp�м����и���
			success=importService.saveInfoToPartType(jp, this.getnodeid());
			if(success){
				//���ݵ����erp�м�����Ӧ����flag��Ϊ2
				importService.updateWISEntity(jp.getJobno(),jp.getUploadDate(), 2);
			}
		}
		//δ��ѡ�������ݵ���ʾ��Ϣ
		if(selectedInfo.length<=0){
			isSuccess = "��ѡ��Ҫ��������ݡ�";
		}else{
			//�ɹ�������ʾ��Ϣ
			if(success){
				isSuccess = "����ɹ�";
				//������ɺ�ˢ������
				this.SubmitSearch();
			}else{
				isSuccess = "����ʧ�ܣ�δ�����ݿ��з��ֶ�Ӧ����š�";
			}
		}
		
	}

	//--------------------------------private--------------------------------------
	private String getnodeid(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	//----------------------------set-------get------------------------------------

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

	public List<JobPlanImportInfo> getOutData() {
		return outData;
	}

	public void setOutData(List<JobPlanImportInfo> outData) {
		this.outData = outData;
	}
	
	public JobPlanImportInfo[] getSelectedInfo() {
		return selectedInfo;
	}
	public void setSelectedInfo(JobPlanImportInfo[] selectedInfo) {
		this.selectedInfo = selectedInfo;
	}
	public JobPlanDataModel getMediumModel() {
		return mediumModel;
	}
	public void setMediumModel(JobPlanDataModel mediumModel) {
		this.mediumModel = mediumModel;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public Map<String, Object> getFlagMap() {
		return flagMap;
	}
	public void setFlagMap(Map<String, Object> flagMap) {
		this.flagMap = flagMap;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
}
