package smtcl.mocs.services.jobplan;

import java.util.List;
import java.util.Map;

import smtcl.mocs.pojos.job.TEquJobDispatch;

public interface UpdataJobDispatch {
	/**
	 * 更新TEquJobDispatch表的状态
	 * @param serialNo 机床序列号
	 * @param status 状态
	 * @param jobdispatchlistName 工单名称
	 */
	public void updataJobDispatchStatus(String serialNo,String status, String jobdispatchlistName);

    /**
     * 新建TEquJobDispatch对象并保存
     * @param serialNo 设备序列号
     * @param jobdispatchId 工单ID
     */
    public void save(String serialNo,String jobdispatchId);

    /**
     * 通过零件名称查询工单批次信息
     * @param nodeid 接单ID
     * @param startTime 工单计划开始时间
     * @param endTime 工单计划结束时间
     * @param partName 零件名称
     * @param batchNo 工单批次
     * @return 返回工单信息
     */
	public List<Map<String,Object>> getBatchNoByPartName2(String nodeid,String startTime, String endTime,
			String partName, String batchNo);
	
	/**
	 * 通过设备和工单号获取关联信息
	 * @param equSerialNo 设备序列号
	 * @param dispatchNo 工单编号
	 * @return 返回TEquJobDispatch实体对象
	 */
	public List<TEquJobDispatch> getEquJobDispatch(String equSerialNo,String dispatchNo);
}
