package smtcl.mocs.services.jobplan;

import java.util.List;
import java.util.Map;

import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;

public interface UpdataJobDispatch {
	/**
	 * ����TEquJobDispatch���״̬
	 * @param serialNo
	 * @param status
	 * @param jobdispatchlistName
	 */
	public void updataJobDispatchStatus(String serialNo,String status, String jobdispatchlistName);
	public void save(String serialNo,String jobdispatchName);
	public List<TEquJobDispatch> getEName(String no, String equid);
	public List<String> getPartNameList(String partTypeid);
	public List<TJobdispatchlistInfo> getTJobdispatchlistInfo(String batchNo,String no);
	/**
	 * ͨ��������Ʋ�ѯ����������Ϣ
	 * @param partName
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getBatchNoByPartName(String nodeid);
	
	public List<Map<String,Object>> getBatchNoByPartName2(String nodeid,String startTime, String endTime,
			String partName, String batchNo);
	
	/**
	 * ͨ���豸�͹����Ż�ȡ������Ϣ
	 * @param equSerialNo
	 * @param dispatchNo
	 * @return
	 */
	public List<TEquJobDispatch> getEquJobDispatch(String equSerialNo,String dispatchNo);
}
