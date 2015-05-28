package smtcl.mocs.services.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.job.TJobplanInfo;

/**
 * ��Ϣ����-�������ݻ�ȡ����
 * @author songkaiang
 *
 */
public interface IReportService {
	
	/**
	 * ��ȡ��ҵ�ƻ����е���Ϣ
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return �ƻ�����  1.��ҵ�ƻ� 2.���μƻ�
	 */
	public List<Map<String,Object>> outputData(String nodeid,String startTime,String endTime,int plan_type);
	
	/**
	 * ��ȡ����ʱ��֮ǰ��IJobInfo����
	 * @param startTime ��ʼʱ��->(ʱ���������������)
	 * @param pid
	 * @return �ƻ�����  1.��ҵ�ƻ� 2.���μƻ�
	 */
	public List<Map<String,Object>> getJobInfo(int partid, String startTime,String endTime,int plan_type);
	
	/**
	 * �²����������ݻ�ȡ
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return
	 */
	public List<Map<String,Object>> monthOutputData(String nodeid, String startTime,String endTime);
	
	/**
	 * ��ȡ��ʷͶ�����α�������
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return
	 */
	public List<Map<String,Object>> historyTouliaoData(String nodeid,String startTime,String endTime);
	
	/**
	 * ͨ����ѯ������ѯ-���ӹ�������ϸ��
	 * @param jobplanStatus ����״̬
	 * @param partNo �������
	 * @param jobCreateDate ��������ʱ��
	 * @param jobStartTime ������ʼʱ��
	 * @param equSerialNo //�豸
	 * @return
	 */
	public List<Map<String,Object>> dispatchDetailData(String nodeid,String jobplanStatus,String partNo,Date jobCreateDate,Date jobCreateDateEnd,Date jobStartTime,Date jobStartTimeEnd,String equSerialNo,String person);
	
	/**
	 * ��ȡ��Ա��ϸ����Ϣ
	 */
	public List<Map<String,Object>> getPersonDetailInfo(String nodeid,String startTime,String endTime);
	
	/**
	 * ��ȡ�豸��ϸ����Ϣ
	 */
	public List<Map<String,Object>> getEquSerialNoDetailInfo(String nodeid,String startTime,String endTime);
	
	/**
	 * ͨ�����Id��ȡ��Ϣ
	 * @param partid
	 * @return
	 */
	public List<Map<String,Object>> getTJobplanInfoByPartId(int partid,String startTime,String endTime,int plan_type);
	
	/**
	 * ��ȡ��Ա�����Ϣ
	 * @return
	 */
	public List<Map<String,Object>> getPersonList(String nodeid);
	/**
	 * ����ʱ���ѯ�������ϱ�������
	 * @param start
	 * @return
	 */
	public List<Map<String,Object>> getProductionScrapReport(String start,String end,String scrapType);
	/**
	 * ��ȡ�����¼���ѯ����
	 * @param pageNo
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @param eventType
	 * @param partId
	 * @param jobdispatchNo
	 * @return
	 */
	public IDataCollection<Map<String, Object>> getProductionEventScrap(int pageNo, int pageSize, Date startTime,Date 
			endTime,String eventType,String partId,String jobdispatchNo); 


}

