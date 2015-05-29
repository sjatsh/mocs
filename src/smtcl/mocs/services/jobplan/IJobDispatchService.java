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
	 * 根据节点ID查询改节点下的设备
	 * @param nodeId
	 * @return
	 */
	public List<Map<String, Object>> getDevicesInfo(String nodeId,String taskNum,String jobstatus, String partid,String equid,String planStime,String planEtime);
	
	/**
	 * 根据查询条件查询该节点下的设备
	 */
	public List<Map<String, Object>> getDevicesInfo(String nodeId, String jobState, String partName, String batchId);
	
	/**
	 * 查找设备对应的工单
	 * @param nodeId
	 * @param jobplanId
	 * @return
	 */
	public List<Map<String, Object>> getJobDispatchsInfo(String nodeId,String taskNum,String jobstatus,String partid,String equid,String planStime,String planEtime);
	
	/**
	 * 根据作业计划ID，查询作业计划信息
	 * @param jobplanId
	 * @return
	 */
	public Map<String,Object> getJobPlanDetail(String jobplanId);
	
	/**
	 * 添加工单信息
	 * @param jobdispatchInfo
	 */
	public void addJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo);
	
	/**
	 * 修改工单信息
	 * @param jobdispatchInfo
	 */
	public void updateJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo);
	
	/**
	 * 删除工单信息
	 * @param jobdispatchInfo
	 */
	public void deleteJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo);
	
	/**
	 * 获取工单表中当前最大ID
	 * @return
	 */
	public String getMaxJobDispatchId();
	
	/**
	 * 获取作业计划下拉列表
	 * @return
	 */
	public List<Map<String,Object>> getJobPlanList(String nodeId);
	/**
	 * 获取从属作业下拉列表Map
	 * @return
	 */
	public List<Map<String, Object>> getJobInfoMap(String nodeId);
	
	/**
	 * 更新工单状态
	 * @param jobdispatchInfo
	 */
	public Boolean updateJobdispatchStatus(String dispatchId,String status,String nowDate,String flag);
	
	/**
	 * 更新作业状态当新增一个工单记录时
	 * 
	 */
	public Boolean updateJobInfoStatusWhenAddJobPlan(String jobId,String status);
	/**
	 * 通过作业表的jobplanid字段查出对应的status字段
	 * 
	 */
	public List<Map<String,Object>> getStatusByJobPlanId(long jobPlanId,int status);
	/**
	 * 更新作业计划表的status字段状态根据作业表的status字段的情况
	 * 
	 */
	public Boolean updateJobPlanInfoStatusByjobStatus(String jobPlanId,String nowDate,String status,String flag);
	/**
	 * 工单启动时查找对应设备下有无其它已启动的工单
	 * 
	 */
	public boolean getDispatchStatusByEquId(String equId);
	/**
	 * 更新工单状态当为结束状态时
	 * @param jobdispatchInfo
	 */
	public Boolean updateJobDispatchWhenStop(String jobdispatchId,String nowDate,String status,String flag);
	/**
	 * 工单结束时查找对应设备下有无其它正在上线加工的工单
	 * 
	 */
	public List<Map<String,Object>> getStatusByJobPlanIdWhenStop(long jobPlanId);
	/**
	 * 更新工单计划状态当为工单为结束状态时
	 * 
	 */
    public Boolean updateJobPlanInfoStatusByjobStatusWhenStop(String jobPlanId,String nowDate,String status);
    /**
	 * 选择一条工单记录
	 * 
	 */
    public List<Map<String, Object>> getJobDispatchsInfoOne(String jobplanId);
    /**
   	 * 根据最大工单编号查找响应的作业计划
   	 * 
   	 */
    public Map<String, Object> findJobPlanDetailDefaultBYDispatchIdDefault();
    
    
	/**
	 * 工单暂停
	 */
	public Boolean setStatusToOldstatus(String dispatId,String status,String flag);
	

	/**
	 * 工单恢复
	 */
	public Boolean updateJobdispatchWhenRecover(String dispatId,String status,String flag);
	
	
	/**
	 * 工单新建 --通过零件名称得到工艺方案ID得到工序清单
	 */
	public List<Map<String,Object>> getProcessByProcessPlanId(String nodeid,String processPlanId);
	
	
	/**
	 * 工单新建 -- 通过零件类型ID得到零件类型名称
	 */
	public String getPartTypeNameById(String partTypeId);
	
	/**
	 * 工单新建 -- 工单保存 --工序直接生成的工单
	 */
	public void saveDispatch(JobdispatchAddBean jobdispatchAddBean);
	
	/**
	 * 工单新建-- 获取零件类型集合
	 */
	public List<Map<String,Object>> getPartTypeMap(String nodeid);
	
	/**
	 * 设备类型 -- 设备类型集合
	 */
	public List<Map<String,Object>> getEquTypeMap(String processId);
	
	/**
	 * 工单修改 --通过工单ID获取工单信息
	 */
	public Map<String,Object> getJobDispatchById(String nodeid,String disPatchId);
	
	/**
	 * 工单修改  --通过设备类型ID得到物料
	 */
//	public List<Map<String,Object>> getMaterialInfo(String eduTypeId);
	
	/**
	 * 工单修改  --查询名称是否重复
	 */
	public boolean getDispatchNameRepeat(String dispatchName);
	
	/**
	 * 工单修改  --修改
	 */
	public void updataJobdispatchlist(JobdispatchUpdataBean jobdispatchUpdataBean);
	
	/**
	 * 工单修改 -- 设备类型 -- 设备类型集合
	 */
	public List<Map<String,Object>> getEquTypeMapByDisPatchId(String dispacthId);
	
	/**
	 * 查询工单状态
	 */
	public List<Map<String,Object>> getJobStatus();
	
	/**
	 * 查询工单表中的批准信息or通过模糊查询(批次信息)
	 */
	public List<Map<String,Object>> getBatchNoList(String nodeId,String query);
	
	/**
	 * 通过零件名称查找设备类型
	 * @return
	 */
	public List<Map<String, Object>> findEquipmentByPartName(String nodeid, String partName);
	
	/**
	 * 查询TEquJobDispatch实体类表中所有信息
	 */
	public List<Map<String, Object>> getEquJobDispatchList(String jobdispatchName,HttpSession session);
	/**
	 * 通过工id称查询patchNO
	 * @return
	 */
	public List<Map<String, Object>> getJobPatchNoById(String id);
	
	
	public List<Map<String, Object>> getJobPatchNoByName(String name);
	/**
	 * 通过工单id查询设备
	 * @param dispatchId
	 * @return
	 */
	public List<TEquJobDispatch> getJobDispatchById(String dispatchNo);
	
	/**
	 * 通过工单名称查询工单信息
	 */
	public List<TJobdispatchlistInfo> getJobDispatchInfo(String id);
	
	/**
	 * 更改工单状态
	 * @param jobName
	 * @param status
	 */
	public boolean updateDispatch(String jobid,int status);
	
	/**
	 * 获取报表数据
	 */
	public List<Map<String, Object>> getJobDispatchReportData(String nodeid,String descParam,String startTime,String endTime,String partName,String batchNo);
	
	/**
	 * 通过query模糊查询设备名称
	 * @param nodeid
	 * @param query 为设备名称
	 * @return
	 */
	public List<Map<String,Object>> getequNameList(String nodeid,String query);
	/**
	 * 根据node节点，查询所有的设备
	 * @param nodeId
	 * @return
	 */
	public List<Map<String, Object>> getDevicesInfo(String nodeId);
}
