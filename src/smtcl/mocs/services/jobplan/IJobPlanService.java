package smtcl.mocs.services.jobplan;

import java.util.Date;
import java.util.List;
import java.util.Map;

import smtcl.mocs.beans.jobplan.JobPlanAddBean;
import smtcl.mocs.beans.jobplan.JobPlanUpdataBean;
import smtcl.mocs.pojos.job.TJobplanInfo;

/**
 * Created by Jf
 * CreateTime:2013/05/06
 * Description:��ҵ�ƻ�����ӿ�
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
	 * @param nodeId �ڵ�ID
	 * @param partid �������ID
	 * @param planStatus �ƻ�״̬
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
     * @param locale ���Ա��
	 * @return ������ҵ�ƻ�list
	 */
	public Map<String, Object> getAllJobPlanAndPartInfo(String nodeId,String partid,String planStatus,String startTime,String endTime,String isexpand,String locale);
	
	/**
	 * �������еĹ����ƻ�
	 * @return
	 */
	public List<Map<String, Object>> findAllJobPlan(String nodeId,String partid,String planStatus,String startTime,String endTime);

	/**
	 * ���ݲ�ƷID����ѯ��ҵ�ƻ���Ϣ
	 * @param jobNo ��ҵ�ƻ����
	 * @return ������ҵ�ƻ���Ϣ
	 */
	public Map<String,Object> findJobPlanDetail(String jobNo);
	
	/**
	 * �����ҵ�ƻ�
	 * @param jobPlan ��ҵ�ƻ�����
	 */
	public void addJobPlanInfo(TJobplanInfo jobPlan);
	
	/**
	 * �޸���ҵ�ƻ�
	 * @param jobPlan ��ҵ�ƻ�����
	 */
	public void updateJobPlanInfo(TJobplanInfo jobPlan);
	
	/**
	 * ɾ����ҵ�ƻ�
	 * @param jobPlan ��ҵ�ƻ�����
	 */
	public void deleteJobPlanInfoById(TJobplanInfo jobPlan);
	
	/**
	 * ����ID ��ѯ��ҵ�ƻ���Ϣ
	 * @param jobPlanId ��ҵ�ƻ�ID
	 * @return ������ҵ�ƻ�����
	 */
	public TJobplanInfo geTJobplanInfoById(Long jobPlanId);
	
	public TJobplanInfo geTJobplanInfoById1(Long jobPlanId);
	
	/**
	 * ��ȡ��ҵ�ƻ����е�ǰ���ID
	 */
	public String getMaxJobPlanInfoId();
	
	/**
	 * ��ȡ��ת���༭ҳ��ļƻ������������Ϣ
     * @param jobplabid ��ҵ�ƻ�ID
	 */
	public List<Map<String,Object>> getPlanAndPartList(String jobplabid);
	
	/**
	 * ��ҵ�ƻ�ҳ��������ƻ������б�
     * @param nodeid �ڵ�ID
	 */
	public List<Map<String,Object>> getPlanAndForList(String nodeid);
	
	/**
	 * ��ҵ�ƻ�ҳ�����������б�
     * @param nodeid �ڵ�ID
	 */
	public List<Map<String,Object>> getPartAndForList(String nodeid);
	
	/**
	 * ��ҵ�ƻ���ŵ�ģ����ѯ
	 * @param no ��ҵ�ƻ����
	 * @return ������ҵ�ƻ����
	 */
	public List<String> getTJobplanInfoNo(String no);
	
	/**
	 * ��ҵ�ƻ�����
	 */
	public String addJobPlan(JobPlanAddBean jobPlanAddBean);
	/**
	 * ͨ����ҵ�ƻ����� ��������
	 * @param jopPlanId ��ҵ�ƻ�ID
	 */
	public List<Map<String,Object>> getTJobplanTaskInfo(String jopPlanId);

	/**
	 * ͨ�����Ʋ�ѯ���ж��Ƿ����ύ��ҵ�ƻ�
	 */
	public List<Map<String,Object>> getPlanByName(String name);
	
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
	 * ��ҵ�ƻ�����-- ͨ����ҵ�ƻ�ID�õ����ID
	 */
	public List<Map<String,Object>> getPartIdByJobPlanId(String jobplanId);

	/**
	 * ͨ��Id�Ϳ�ʼʱ��õ���ҵ�ƻ�
	 */
	public List<Map<String,Object>> getPlanListByIdAndTime(String jobplabid,Date startTime,Date endTime);

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
	 * ͨ�����Ʋ�ѯ���ж��Ƿ����ύ����
	 */
	public List<Map<String,Object>> getJobdispatchlistByName(String name);
	
	/**
	 * ͨ��ID��ת�������޸�ҳ��
	 */
	public List<Map<String,Object>> getJobdispatchlistById(String id);
	
    /**
     * ����ͳ�ƣ�ͨ������ź�ʱ��
     * @param nodeid �ӵ�ID
     * @param taskNum ������
     * @param startTime ��ʼʱ��
     * @param endTime ����ʱ��
     */
	public List<Map<String,Object>> getJobdispatchlistByIdAndTime(String nodeid,String taskNum,Date startTime,Date endTime);

    /**
     * ����ͳ�� ��������ŵõ�
     * @return ��������Num
     */
	public List<Map<String, Object>> getjobdispatchByTaskNum();

	/**
	 * �������й���--�ѷ��乤���嵥
     * @param nodeid �ڵ�ID
	 */
	public List<Map<String,Object>> getAlreadyDispatchList(String nodeid);
	
	/**
	 * �������й���--��ҵ�ƻ�����
     * @param jobplanId ��ҵ�ƻ�ID
	 */
	public List<Map<String,Object>> getJobPlanDetail(String jobplanId);
	
	/**
	 * ��ȡ���������ƻ�����
     * @param nodeid �ڵ�ID
	 */
	public List<Map<String, Object>> getSubordinatePlanInfoMap(String nodeid);
	
	/**
	 * ��ȡ���ȼ�
	 */
	public List<Map<String,Object>> getPriority();

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
	 * ��ҵ�ƻ����� --ͨ�����շ���ID�õ������嵥
     * @param nodeid �ڵ�ID
     * @param processPlanId ������ҵ�ƻ�ID
	 */
	public List<Map<String,Object>> getProcessByProcessPlanId(String nodeid,String processPlanId);
	
	/**
	 * ��ҵ�ƻ����� --ͨ������ID�õ��豸���к�
	 * @param processId ������
	 */
	public List<Map<String,Object>> getSerNoByProcessId(String processId);
	
	/**
	 * ��ҵ�ƻ����� --ͨ���豸ID�õ��豸����
     * @param equId �豸ID
	 */
	public String getEquNameByEquId(String equId);

    /**
     * ��ҵ�ƻ����� --ͨ����ҵ�ƻ�ID���������ID�õ���ҵ�ƻ�
     * @param nodeid �ڵ�ID
     * @param jobplanid ��ҵ�ƻ�ID
     * @param partId �������ID
     * @return ������ҵ�ƻ���ϢMap����
     */
	public List<Map<String,Object>> getJobPlanByJobIdAndPartId(String nodeid,String jobplanid,String partId);

    /**
     * ��ȡ��ҵ�ƻ�����ҵ�ƻ�
     * @param batchNo ���α��
     * @param partTypeId �������ID
     * @return ��ȡ��ҵ�ƻ�����
     */
	public List<TJobplanInfo> getTJobplanInfoByBatchNo(String batchNo,String partTypeId);

    /**
     * ��ѯplan_typeΪ2�Ĺ����ƻ�
     * @param nodeId �ڵ�ID
     * @param partTypeId �������ID
     * @return ������ҵ�ƻ�����
     */
	public List<TJobplanInfo> getJobPlan(String nodeId,String partTypeId);

    /**
     * ��ȡ�ڵ�id�µ����й���
     * @param nodeid �ڵ�ID
     * @param query �������ģ����ѯ
     * @param jobplanId ��ҵ�ƻ�ID
     * @return ���ع���ID��NO
     */
	public List<Map<String,Object>> getJobdispatchList(String nodeid,String query,String jobplanId);

    /**
	 * ����ѡ��Ĺ����� ����ѡ����Ҫ������
	 * @param jobdispatchNo �������
	 * @return ����������Ϣ
	 */
	public Map<String,Object> getDataByjobdispatchNo(String jobdispatchNo);

}
