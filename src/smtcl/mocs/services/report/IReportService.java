package smtcl.mocs.services.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.job.TJobplanInfo;

/**
 * 信息管理-报表数据获取服务
 * @author songkaiang
 *
 */
public interface IReportService {
	
	/**
	 * 获取作业计划表中的信息
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 计划类型  1.作业计划 2.批次计划
	 */
	public List<Map<String,Object>> outputData(String nodeid,String startTime,String endTime,int plan_type);
	
	/**
	 * 获取给定时间之前的IJobInfo数据
	 * @param startTime 开始时间->(时间过滤条件不在用)
	 * @param pid
	 * @return 计划类型  1.作业计划 2.批次计划
	 */
	public List<Map<String,Object>> getJobInfo(int partid, String startTime,String endTime,int plan_type);
	
	/**
	 * 月产量报表数据获取
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<Map<String,Object>> monthOutputData(String nodeid, String startTime,String endTime);
	
	/**
	 * 获取历史投料批次报表数据
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<Map<String,Object>> historyTouliaoData(String nodeid,String startTime,String endTime);
	
	/**
	 * 通过查询条件查询-》加工工单明细表
	 * @param jobplanStatus 批次状态
	 * @param partNo 零件名称
	 * @param jobCreateDate 工单建立时间
	 * @param jobStartTime 工单开始时间
	 * @param equSerialNo //设备
	 * @return
	 */
	public List<Map<String,Object>> dispatchDetailData(String nodeid,String jobplanStatus,String partNo,Date jobCreateDate,Date jobCreateDateEnd,Date jobStartTime,Date jobStartTimeEnd,String equSerialNo,String person);
	
	/**
	 * 获取人员明细表信息
	 */
	public List<Map<String,Object>> getPersonDetailInfo(String nodeid,String startTime,String endTime);
	
	/**
	 * 获取设备明细表信息
	 */
	public List<Map<String,Object>> getEquSerialNoDetailInfo(String nodeid,String startTime,String endTime);
	
	/**
	 * 通过零件Id获取信息
	 * @param partid
	 * @return
	 */
	public List<Map<String,Object>> getTJobplanInfoByPartId(int partid,String startTime,String endTime,int plan_type);
	
	/**
	 * 获取人员编号信息
	 * @return
	 */
	public List<Map<String,Object>> getPersonList(String nodeid);
	/**
	 * 根据时间查询生产报废报表数据
	 * @param start
	 * @return
	 */
	public List<Map<String,Object>> getProductionScrapReport(String start,String end,String scrapType);
	/**
	 * 获取生产事件查询数据
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

