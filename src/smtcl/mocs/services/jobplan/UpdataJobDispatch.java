package smtcl.mocs.services.jobplan;

import java.util.List;
import java.util.Map;

import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;

public interface UpdataJobDispatch {
	/**
	 * 更新TEquJobDispatch表的状态
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
	 * 通过零件名称查询工单批次信息
	 * @param partName
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getBatchNoByPartName(String nodeid);
	
	public List<Map<String,Object>> getBatchNoByPartName2(String nodeid,String startTime, String endTime,
			String partName, String batchNo);
	
	/**
	 * 通过设备和工单号获取关联信息
	 * @param equSerialNo
	 * @param dispatchNo
	 * @return
	 */
	public List<TEquJobDispatch> getEquJobDispatch(String equSerialNo,String dispatchNo);
}
