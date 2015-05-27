package smtcl.mocs.services.jobplan;

import java.util.Date;
import java.util.List;
import java.util.Map;

import smtcl.mocs.beans.jobplan.JobPlanAddBean;
import smtcl.mocs.beans.jobplan.JobPlanControlBean;
import smtcl.mocs.beans.jobplan.JobPlanUpdataBean;
import smtcl.mocs.beans.jobplan.JobdispatchlistAddBean;
import smtcl.mocs.beans.jobplan.JobdispatchlistUpdataBean;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TJobplanTaskInfo;

/**
 * ��ҵ�ƻ�����SERVICE�ӿ���
 * @���ߣ�Jf
 * @����ʱ�䣺2013-05-06
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public interface IJobPlanService {

	/**
	 * ���ݽڵ�ID��ѯ���г��估��Ӧ����ҵ�ƻ�
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAllJobPlanAndPartInfo(String nodeId);
	/**
	 * ��ѯ��ҵ�ƻ�-�������
	 * @param nodeId
	 * @param partName
	 * @param planStatus
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Object> getAllJobPlanAndPartInfo(String nodeId,String partid,String planStatus,String startTime,String endTime,String isexpand);
	
	/**
	 * �������еĹ����ƻ�
	 * @return
	 */
	public List<Map<String, Object>> findAllJobPlan(String nodeId,String partid,String planStatus,String startTime,String endTime);
	
	/**
	 * ���ݳ����ѯ���в�Ʒ���ƻ�������Ϣ
	 * @param partId
	 * @return
	 */
//	public List<Map<String, Object>> findJobPlanByPartInfoId(int partId);
	
	
	
	/**
	 * ���ݲ�ƷID����ѯ��ҵ�ƻ���Ϣ
	 * @param jobNo
	 * @return
	 */
	public Map<String,Object> findJobPlanDetail(String jobNo);
	
	/**
	 * �����ҵ�ƻ�
	 * @param jobPlan
	 */
	public void addJobPlanInfo(TJobplanInfo jobPlan);
	
	/**
	 * �޸���ҵ�ƻ�
	 * @param jobPlan
	 */
	public void updateJobPlanInfo(TJobplanInfo jobPlan);
	
	/**
	 * ɾ����ҵ�ƻ�
	 * @param jobPlanId
	 */
	public void deleteJobPlanInfoById(TJobplanInfo jobPlan);
	
	/**
	 * ����ID ��ѯ��ҵ�ƻ���Ϣ
	 * @param jobPlanId
	 * @return
	 */
	public TJobplanInfo geTJobplanInfoById(Long jobPlanId);
	
	public TJobplanInfo geTJobplanInfoById1(Long jobPlanId);
	
	/**
	 * ��ȡ��ҵ�ƻ����е�ǰ���ID
	 * @return
	 */
	public String getMaxJobPlanInfoId();
	
	
	/**
	 * ��ȡ��ת���༭ҳ��ļƻ������������Ϣ
	 */
	public List<Map<String,Object>> getPlanAndPartList(String jobplabid);
	
	/**
	 * ��ҵ�ƻ�ҳ��������ƻ������б�
	 */
	public List<Map<String,Object>> getPlanAndForList(String nodeid);
	
	/**
	 * ��ҵ�ƻ�ҳ�����������б�
	 */
	public List<Map<String,Object>> getPartAndForList(String nodeid);
	
	/**
	 * ��ҵ�ƻ���ŵ�ģ����ѯ
	 * @param 
	 * @return
	 */
	public List<String> getTJobplanInfoNo(String no);
	
	/**
	 * ��ҵ�ƻ�����
	 */
	public String addJobPlan(JobPlanAddBean jobPlanAddBean);
	/**
	 * ͨ����ҵ�ƻ����� ��������
	 * @param jopPlanId
	 * @return
	 */
	public List<Map<String,Object>> getTJobplanTaskInfo(String jopPlanId);
	/**
	 * ͨ����ҵ�ƻ����� �����������
	 * @param jopPlanId
	 * @return
	 */
	public List<TJobplanTaskInfo> getTJobplanTaskInfoObject(String jopPlanId);
	/**
	 * ͨ�����Ʋ�ѯ���ж��Ƿ����ύ��ҵ�ƻ�
	 */
	public List<Map<String,Object>> getPlanByName(String name);
	
	/**
	 * ��ҵ�ƻ�������������ƻ���ϸ-ͨ��ID
	 */
	public List<Map<String,Object>> getJobplanByIdFor(String jobplanId);
	
	/**
	 * ��ҵ�ƻ�����������������ϸ-ͨ��ID
	 */
	public List<Map<String,Object>> getPartTypeByIdFor(String partTypeId);
	
	/**
	 * ��ҵ�ƻ������ID��Ϊ��ҵ�ƻ�ƴװ
	 */
	public String getMaxJobPlanId();
	
	/**
	 * ��ҵ�ƻ��޸�
	 */
	public String updataJobPlan(JobPlanUpdataBean jobPlanAddBean);
	
	/**
	 * ͨ����ҵ�ƻ��õ������嵥
	 */
	public List<Map<String,Object>> getProcessByJobPlanId(String jobplabid);
	
	/**
	 * ����ҳ��--ͨ�����ID�õ���ҵ�ƻ�ID����
	 */
	public List<Map<String,Object>> getJobPlanMapByPart(String nodeid,String partId);
	
	/**
	 * ����ҳ��--ͨ�����ID�õ����շ���ID����
	 */
	public List<Map<String,Object>> getProcessPlanMapByPart(String nodeid,String partId);
	
	/**
	 * ͨ����ҵ�ƻ�ID�����շ���ID�����ID�õ������嵥
	 */
	public List<Map<String,Object>> getProcessPlanById(String nodeid,String jobplanid,String processPlanId,String partId);
	
	/**
	 * ͨ����ҵ�ƻ�ID�����շ���ID�����ID�õ������嵥,--��Ҫ������ҵ�ƻ�������1�Զ�
	 */
	public List<Map<String,Object>> getProcessPlanById1(String jobplanid,String processPlanId,String partId);
	
	/**
	 * ͨ����ҵ�ƻ��õ���ҵ�б� --��ҵ�ƻ�����ҳ��
	 */
	public List<Map<String,Object>> getJobByJobPlanId(String jobplanId);
	
	/**
	 * ��ҵ�ƻ�����-- ͨ����ҵ�ƻ�ID�õ����ID
	 */
	public List<Map<String,Object>> getPartIdByJobPlanId(String jobplanId);
	
	/**
	 * ��ҵ�嵥����
	 */
//	public void addJobInfo(JobPlanControlBean jobPlanControlBean);
	
	/**
	 * ��ҵ�嵥���� �Ƿ��ظ����--ͨ������ж�
	 */
	public List<Map<String,Object>> getBooleanAddJob(String no);
	
	/**
	 * ��ҵ�嵥���ɺ�----��ҵ�ƻ�״̬�޸�//���ɹ�20
	 */
	public void updateJobplanStatus(String jobplanId);
	
	/**
	 * �������1����ҵ�ƻ�
	 */
//	public void addOneJob(JobPlanControlBean jobPlanControlBean);
	
	/**
	 * ͨ��Id�Ϳ�ʼʱ��õ���ҵ�ƻ�
	 */
	public List<Map<String,Object>> getPlanListByIdAndTime(String jobplabid,Date startTime,Date endTime);

	/**
	 * ɾ����ҵͨ��ID
	 */
	public void delJob(String jobId);
	
	/**
	 * �޸Ĺ���ʱ�õ���ҵ����
	 */
	public List<Map<String,Object>> getJobIdMap();
	
	/**
	 * ��ȡ�������ʱ����Ϣ
	 */
	public Map<String,Object> getJobdispatchlistInfoForAdd(String jobplanId);
	
	/**
	 * ��ȡ�������ʱ����Ϣ--�豸����
	 */
	public List<Map<String,Object>> getEquimentAndType(String typeId);
	
	/**
	 * ��ȡ�������ʱ����Ϣ--�豸����
	 */
	public List<Map<String,Object>> getEquimentByType(String nodeid,String typeId);
	
	/**
	 * �������
	 */
	public void addJobdispatchlistInfo(JobdispatchlistAddBean jobdispatchlistAddBean);
	
	/**
	 * ͨ�����Ʋ�ѯ���ж��Ƿ����ύ����
	 */
	public List<Map<String,Object>> getJobdispatchlistByName(String name);
	
	/**
	 * ͨ��ID��ת�������޸�ҳ��
	 */
	public List<Map<String,Object>> getJobdispatchlistById(String id);
	
	/**
	 * �����޸�
	 */
	public void updataJobdispatchlistInfo(JobdispatchlistUpdataBean jobdispatchlistAddBean);
	
	/**
	 * ����ͳ�ƣ�ͨ������ź�ʱ��
	 */
	public List<Map<String,Object>> getJobdispatchlistByIdAndTime(String nodeid,String taskNum,Date startTime,Date endTime);
	
	//����ͳ�� ��������ŵõ�
	public List<Map<String, Object>> getjobdispatchByTaskNum();
	
	/**
	 * �������й���--��������ҵ
	 */
	public List<Map<String,Object>> getWaitJobList(String nodeid);
	
	/**
	 * �������й���--�ѷ��乤���嵥
	 */
	public List<Map<String,Object>> getAlreadyDispatchList(String nodeid);
	
	/**
	 * �������й���--��ҵ�ƻ�����
	 */
	public List<Map<String,Object>> getJobPlanDetail(String jobplanId);
	
	/**
	 * ��ȡ���������ƻ�����
	 * @return
	 */
	public List<Map<String, Object>> getSubordinatePlanInfoMap(String nodeid);
	
	/**
	 * ��ȡ���ȼ�
	 */
	public List<Map<String,Object>> getPriority();
	
	/**
	 * ��ҵ�ƻ����й���--��ҵ�ƻ��嵥
	 */
	public List<Map<String, Object>> getJobplanList(String nodeid);
	
	/**
	 * ��������--ʼĩʱ��Ĺ�����ʼʱ��Ϊ��Ӧ�ƻ��Ŀ�ʼʱ��
	 */
	public Date getMaxEndTimeFormDispatchList(String jobid);
	/**
	 * ��ҵ�ƻ�����Ĭ��ȡһ����¼
	 */
	public Map<String, Object> findJobPlanDetailDefault();
	/**
	 * ������ҵ�ƻ�״̬����
	 */
	public Boolean updateJobPlanInfoStatus(String jobPlanId,String status);
	
	
	/********************************A3******************************************/
	
	/**
	 * ��ҵ�ƻ����� -- ��ȡ������ͼ���
	 */
	public List<Map<String,Object>> getPartTypeMap();
	
	/**
	 * ��ҵ�ƻ����� -- ��ȡ��ҵ�ƻ�����
	 */
	public List<Map<String,Object>> getJobPlanMap();
	
	/**
	 * ��ҵ�ƻ����� -- ��ȡ���շ�������
	 */
	public List<Map<String,Object>> getProcessplanMap(String partid);
	
	/**
	 * ��ҵ�ƻ����� -- ͨ����ҵ�ƻ�Id��ȡ������ͼ���
	 */
	public List<Map<String,Object>> getPartTypeMapByJobPlanid(String jobplanid);
	
	/**
	 * Ͷ�������б�
	 */
	public List<Map<String,Object>> getBatchList(String processplanId,String jobplabid);
	
	/**
	 * ��ҵ�ƻ����� --�����б�
	 */
	public List<Map<String,Object>> getDispatchList(String processplanId);
	
	/**
	 * ��ҵ�ƻ����� --�޸Ĺ���
	 * @param jobPlanControlBean
	 */
	public void updateJobDispatch(JobPlanControlBean jobPlanControlBean);
	
	/**
	 * ��ҵ�ƻ����� --������ҵ�ͱ��湤��
	 */
	public void saveJobDispatch(JobPlanControlBean jobPlanControlBean);
	
	/**
	 * ��ҵ�ƻ����� --ͨ�����շ���ID�õ������嵥
	 */
	public List<Map<String,Object>> getProcessByProcessPlanId(String nodeid,String processPlanId);
	
	/**
	 * ��ҵ�ƻ����� --ͨ������ID�õ��豸���к�
	 * 
	 */
	public List<Map<String,Object>> getSerNoByProcessId(String processId);
	
	/**
	 * ��ҵ�ƻ����� --ͨ���豸ID�õ��豸����
	 */
	public String getEquNameByEquId(String equId);
	
	/**
	 * ��ҵ�ƻ����� --ͨ����ҵ�ƻ�ID���������ID�õ���ҵ�ƻ�
	 */
	public List<Map<String,Object>> getJobPlanByJobIdAndPartId(String nodeid,String jobplanid,String partId);
	/**
	 * ��ȡ��ҵ�ƻ�����ҵ�ƻ�
	 * @param batchNo
	 * @return
	 */
	public List<TJobplanInfo> getTJobplanInfoByBatchNo(String batchNo,String partTypeId);
	
	/**
	 * ��ѯplan_typeΪ2�Ĺ����ƻ�
	 * @return
	 */
	public List<TJobplanInfo> getJobPlan(String nodeId,String partTypeId);
	
	/**
	 * ����id��ȡ��ҵ�ƻ�
	 * @param id
	 * @return
	 */
	public List<TJobplanInfo> getTJobplanInfoById(String id);
	/**
	 * ��ȡ�ڵ�id�µ����й���
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getJobdispatchList(String nodeid,String query,String jobplanId);
	/**
	 * ����ѡ��Ĺ����� ����ѡ����Ҫ������
	 * @param jobdispatchNo
	 * @return
	 */
	public Map<String,Object> getDataByjobdispatchNo(String jobdispatchNo);

	/**
	 * ���ݹ������ ��ȡ��������
	 * @param no
	 * @return
	 */
	public TJobdispatchlistInfo getTJobdispatchlistInfoByNo(String no);
	/**
	 * ���¹���
	 * @param tt
	 * @return
	 */
	public String updateTJobdispatchlistInfo(TJobdispatchlistInfo tt);
}
