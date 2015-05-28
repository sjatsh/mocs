package smtcl.mocs.services.jobplan;

import java.util.List;
import java.util.Map;

import smtcl.mocs.pojos.job.TEquJobDispatch;

public interface UpdataJobDispatch {
	/**
	 * ����TEquJobDispatch���״̬
	 * @param serialNo �������к�
	 * @param status ״̬
	 * @param jobdispatchlistName ��������
	 */
	public void updataJobDispatchStatus(String serialNo,String status, String jobdispatchlistName);

    /**
     * �½�TEquJobDispatch���󲢱���
     * @param serialNo �豸���к�
     * @param jobdispatchId ����ID
     */
    public void save(String serialNo,String jobdispatchId);

    /**
     * ͨ��������Ʋ�ѯ����������Ϣ
     * @param nodeid �ӵ�ID
     * @param startTime �����ƻ���ʼʱ��
     * @param endTime �����ƻ�����ʱ��
     * @param partName �������
     * @param batchNo ��������
     * @return ���ع�����Ϣ
     */
	public List<Map<String,Object>> getBatchNoByPartName2(String nodeid,String startTime, String endTime,
			String partName, String batchNo);
	
	/**
	 * ͨ���豸�͹����Ż�ȡ������Ϣ
	 * @param equSerialNo �豸���к�
	 * @param dispatchNo �������
	 * @return ����TEquJobDispatchʵ�����
	 */
	public List<TEquJobDispatch> getEquJobDispatch(String equSerialNo,String dispatchNo);
}
