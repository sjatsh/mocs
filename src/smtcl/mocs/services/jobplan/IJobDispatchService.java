package smtcl.mocs.services.jobplan;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import smtcl.mocs.beans.jobplan.JobdispatchAddBean;
import smtcl.mocs.beans.jobplan.JobdispatchUpdataBean;
import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;

public interface IJobDispatchService {

	/**
	 * ���ݽڵ�ID��ѯ�Ľڵ��µ��豸
	 * @param nodeId
	 * @return
	 */
	public List<Map<String, Object>> getDevicesInfo(String nodeId,String taskNum,String jobstatus, String partid,String equid,String planStime,String planEtime);
	
	/**
	 * ���ݲ�ѯ������ѯ�ýڵ��µ��豸
	 */
	public List<Map<String, Object>> getDevicesInfo(String nodeId, String jobState, String partName, String batchId);
	
	/**
	 * �����豸��Ӧ�Ĺ���
	 * @param nodeId
	 * @param jobplanId
	 * @return
	 */
	public List<Map<String, Object>> getJobDispatchsInfo(String nodeId,String taskNum,String jobstatus,String partid,String equid,String planStime,String planEtime);
	
	/**
	 * ������ҵ�ƻ�ID����ѯ��ҵ�ƻ���Ϣ
	 * @param jobplanId
	 * @return
	 */
	public Map<String,Object> getJobPlanDetail(String jobplanId);
	
	/**
	 * ��ӹ�����Ϣ
	 * @param jobdispatchInfo
	 */
	public void addJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo);
	
	/**
	 * �޸Ĺ�����Ϣ
	 * @param jobdispatchInfo
	 */
	public void updateJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo);
	
	/**
	 * ɾ��������Ϣ
	 * @param jobdispatchInfo
	 */
	public void deleteJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo);
	
	/**
	 * ��ȡ�������е�ǰ���ID
	 * @return
	 */
	public String getMaxJobDispatchId();
	
	/**
	 * ��ȡ��ҵ�ƻ������б�
	 * @return
	 */
	public List<Map<String,Object>> getJobPlanList(String nodeId);
	/**
	 * ��ȡ������ҵ�����б�Map
	 * @return
	 */
	public List<Map<String, Object>> getJobInfoMap(String nodeId);
	
	/**
	 * ���¹���״̬
	 * @param jobdispatchInfo
	 */
	public Boolean updateJobdispatchStatus(String dispatchId,String status,String nowDate,String flag);
	
	/**
	 * ������ҵ״̬������һ��������¼ʱ
	 * 
	 */
	public Boolean updateJobInfoStatusWhenAddJobPlan(String jobId,String status);
	/**
	 * ͨ����ҵ���jobplanid�ֶβ����Ӧ��status�ֶ�
	 * 
	 */
	public List<Map<String,Object>> getStatusByJobPlanId(long jobPlanId,int status);
	/**
	 * ������ҵ�ƻ����status�ֶ�״̬������ҵ���status�ֶε����
	 * 
	 */
	public Boolean updateJobPlanInfoStatusByjobStatus(String jobPlanId,String nowDate,String status,String flag);
	/**
	 * ��������ʱ���Ҷ�Ӧ�豸�����������������Ĺ���
	 * 
	 */
	public boolean getDispatchStatusByEquId(String equId);
	/**
	 * ���¹���״̬��Ϊ����״̬ʱ
	 * @param jobdispatchInfo
	 */
	public Boolean updateJobDispatchWhenStop(String jobdispatchId,String nowDate,String status,String flag);
	/**
	 * ��������ʱ���Ҷ�Ӧ�豸�����������������߼ӹ��Ĺ���
	 * 
	 */
	public List<Map<String,Object>> getStatusByJobPlanIdWhenStop(long jobPlanId);
	/**
	 * ���¹����ƻ�״̬��Ϊ����Ϊ����״̬ʱ
	 * 
	 */
    public Boolean updateJobPlanInfoStatusByjobStatusWhenStop(String jobPlanId,String nowDate,String status);
    /**
	 * ѡ��һ��������¼
	 * 
	 */
    public List<Map<String, Object>> getJobDispatchsInfoOne(String jobplanId);
    /**
   	 * ������󹤵���Ų�����Ӧ����ҵ�ƻ�
   	 * 
   	 */
    public Map<String, Object> findJobPlanDetailDefaultBYDispatchIdDefault();
    
    
	/**
	 * ������ͣ
	 */
	public Boolean setStatusToOldstatus(String dispatId,String status,String flag);
	

	/**
	 * �����ָ�
	 */
	public Boolean updateJobdispatchWhenRecover(String dispatId,String status,String flag);
	
	
	/**
	 * �����½� --ͨ��������Ƶõ����շ���ID�õ������嵥
	 */
	public List<Map<String,Object>> getProcessByProcessPlanId(String nodeid,String processPlanId);
	
	
	/**
	 * �����½� -- ͨ���������ID�õ������������
	 */
	public String getPartTypeNameById(String partTypeId);
	
	/**
	 * �����½� -- �������� --����ֱ�����ɵĹ���
	 */
	public void saveDispatch(JobdispatchAddBean jobdispatchAddBean);
	
	/**
	 * �����½�-- ��ȡ������ͼ���
	 */
	public List<Map<String,Object>> getPartTypeMap(String nodeid);
	
	/**
	 * �豸���� -- �豸���ͼ���
	 */
	public List<Map<String,Object>> getEquTypeMap(String processId);
	
	/**
	 * �����޸� --ͨ������ID��ȡ������Ϣ
	 */
	public Map<String,Object> getJobDispatchById(String nodeid,String disPatchId);
	
	/**
	 * �����޸�  --ͨ���豸����ID�õ�����
	 */
//	public List<Map<String,Object>> getMaterialInfo(String eduTypeId);
	
	/**
	 * �����޸�  --��ѯ�����Ƿ��ظ�
	 */
	public boolean getDispatchNameRepeat(String dispatchName);
	
	/**
	 * �����޸�  --�޸�
	 */
	public void updataJobdispatchlist(JobdispatchUpdataBean jobdispatchUpdataBean);
	
	/**
	 * �����޸� -- �豸���� -- �豸���ͼ���
	 */
	public List<Map<String,Object>> getEquTypeMapByDisPatchId(String dispacthId);
	
	/**
	 * ��ѯ����״̬
	 */
	public List<Map<String,Object>> getJobStatus();
	
	/**
	 * ��ѯ�������е���׼��Ϣorͨ��ģ����ѯ(������Ϣ)
	 */
	public List<Map<String,Object>> getBatchNoList(String nodeId,String query);
	
	/**
	 * ͨ��������Ʋ����豸����
	 * @return
	 */
	public List<Map<String, Object>> findEquipmentByPartName(String nodeid, String partName);
	
	/**
	 * ��ѯTEquJobDispatchʵ�������������Ϣ
	 */
	public List<Map<String, Object>> getEquJobDispatchList(String jobdispatchName,HttpSession session);
	/**
	 * ͨ����id�Ʋ�ѯpatchNO
	 * @return
	 */
	public List<Map<String, Object>> getJobPatchNoById(String id);
	
	
	public List<Map<String, Object>> getJobPatchNoByName(String name);
	/**
	 * ͨ������id��ѯ�豸
	 * @param dispatchId
	 * @return
	 */
	public List<TEquJobDispatch> getJobDispatchById(String dispatchNo);
	
	/**
	 * ͨ���������Ʋ�ѯ������Ϣ
	 */
	public List<TJobdispatchlistInfo> getJobDispatchInfo(String id);
	
	/**
	 * ���Ĺ���״̬
	 * @param jobName
	 * @param status
	 */
	public boolean updateDispatch(String jobid,int status);
	
	/**
	 * ��ȡ��������
	 */
	public List<Map<String, Object>> getJobDispatchReportData(String nodeid,String descParam,String startTime,String endTime,String partName,String batchNo);
	
	/**
	 * ͨ��queryģ����ѯ�豸����
	 * @param nodeid
	 * @param query Ϊ�豸����
	 * @return
	 */
	public List<Map<String,Object>> getequNameList(String nodeid,String query);
	/**
	 * ����node�ڵ㣬��ѯ���е��豸
	 * @param nodeId
	 * @return
	 */
	public List<Map<String, Object>> getDevicesInfo(String nodeId);
}
