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
 * 作业计划管理SERVICE接口类
 * @作者：Jf
 * @创建时间：2013-05-06
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public interface IJobPlanService {

	/**
	 * 根据节点ID查询所有车间及对应的作业计划
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAllJobPlanAndPartInfo(String nodeId);
	/**
	 * 查询作业计划-零件名称
	 * @param nodeId
	 * @param partName
	 * @param planStatus
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Object> getAllJobPlanAndPartInfo(String nodeId,String partid,String planStatus,String startTime,String endTime,String isexpand);
	
	/**
	 * 查找所有的工作计划
	 * @return
	 */
	public List<Map<String, Object>> findAllJobPlan(String nodeId,String partid,String planStatus,String startTime,String endTime);
	
	/**
	 * 根据车间查询所有产品及计划任务信息
	 * @param partId
	 * @return
	 */
//	public List<Map<String, Object>> findJobPlanByPartInfoId(int partId);
	
	
	
	/**
	 * 根据产品ID，查询作业计划信息
	 * @param jobNo
	 * @return
	 */
	public Map<String,Object> findJobPlanDetail(String jobNo);
	
	/**
	 * 添加作业计划
	 * @param jobPlan
	 */
	public void addJobPlanInfo(TJobplanInfo jobPlan);
	
	/**
	 * 修改作业计划
	 * @param jobPlan
	 */
	public void updateJobPlanInfo(TJobplanInfo jobPlan);
	
	/**
	 * 删除作业计划
	 * @param jobPlanId
	 */
	public void deleteJobPlanInfoById(TJobplanInfo jobPlan);
	
	/**
	 * 根据ID 查询作业计划信息
	 * @param jobPlanId
	 * @return
	 */
	public TJobplanInfo geTJobplanInfoById(Long jobPlanId);
	
	public TJobplanInfo geTJobplanInfoById1(Long jobPlanId);
	
	/**
	 * 获取作业计划表中当前最大ID
	 * @return
	 */
	public String getMaxJobPlanInfoId();
	
	
	/**
	 * 获取跳转到编辑页面的计划表和零件表的信息
	 */
	public List<Map<String,Object>> getPlanAndPartList(String jobplabid);
	
	/**
	 * 作业计划页面的生产计划下拉列表
	 */
	public List<Map<String,Object>> getPlanAndForList(String nodeid);
	
	/**
	 * 作业计划页面的零件下拉列表
	 */
	public List<Map<String,Object>> getPartAndForList(String nodeid);
	
	/**
	 * 作业计划编号的模糊查询
	 * @param 
	 * @return
	 */
	public List<String> getTJobplanInfoNo(String no);
	
	/**
	 * 作业计划保存
	 */
	public String addJobPlan(JobPlanAddBean jobPlanAddBean);
	/**
	 * 通过作业计划查找 分配任务
	 * @param jopPlanId
	 * @return
	 */
	public List<Map<String,Object>> getTJobplanTaskInfo(String jopPlanId);
	/**
	 * 通过作业计划查找 分配任务对象
	 * @param jopPlanId
	 * @return
	 */
	public List<TJobplanTaskInfo> getTJobplanTaskInfoObject(String jopPlanId);
	/**
	 * 通过名称查询，判断是否多次提交作业计划
	 */
	public List<Map<String,Object>> getPlanByName(String name);
	
	/**
	 * 作业计划添加联动生产计划详细-通过ID
	 */
	public List<Map<String,Object>> getJobplanByIdFor(String jobplanId);
	
	/**
	 * 作业计划添加联动零件类型详细-通过ID
	 */
	public List<Map<String,Object>> getPartTypeByIdFor(String partTypeId);
	
	/**
	 * 作业计划的最大ID，为作业计划拼装
	 */
	public String getMaxJobPlanId();
	
	/**
	 * 作业计划修改
	 */
	public String updataJobPlan(JobPlanUpdataBean jobPlanAddBean);
	
	/**
	 * 通过作业计划得到工序清单
	 */
	public List<Map<String,Object>> getProcessByJobPlanId(String jobplabid);
	
	/**
	 * 控制页面--通过零件ID得到作业计划ID下拉
	 */
	public List<Map<String,Object>> getJobPlanMapByPart(String nodeid,String partId);
	
	/**
	 * 控制页面--通过零件ID得到工艺方案ID下拉
	 */
	public List<Map<String,Object>> getProcessPlanMapByPart(String nodeid,String partId);
	
	/**
	 * 通过作业计划ID，工艺方案ID，零件ID得到工序清单
	 */
	public List<Map<String,Object>> getProcessPlanById(String nodeid,String jobplanid,String processPlanId,String partId);
	
	/**
	 * 通过作业计划ID，工艺方案ID，零件ID得到工序清单,--不要关联作业计划表，会是1对多
	 */
	public List<Map<String,Object>> getProcessPlanById1(String jobplanid,String processPlanId,String partId);
	
	/**
	 * 通过作业计划得到作业列表 --作业计划控制页面
	 */
	public List<Map<String,Object>> getJobByJobPlanId(String jobplanId);
	
	/**
	 * 作业计划控制-- 通过作业计划ID得到零件ID
	 */
	public List<Map<String,Object>> getPartIdByJobPlanId(String jobplanId);
	
	/**
	 * 作业清单保存
	 */
//	public void addJobInfo(JobPlanControlBean jobPlanControlBean);
	
	/**
	 * 作业清单保存 是否重复添加--通过编号判断
	 */
	public List<Map<String,Object>> getBooleanAddJob(String no);
	
	/**
	 * 作业清单生成后----作业计划状态修改//待派工20
	 */
	public void updateJobplanStatus(String jobplanId);
	
	/**
	 * 单独添加1个作业计划
	 */
//	public void addOneJob(JobPlanControlBean jobPlanControlBean);
	
	/**
	 * 通过Id和开始时间得到作业计划
	 */
	public List<Map<String,Object>> getPlanListByIdAndTime(String jobplabid,Date startTime,Date endTime);

	/**
	 * 删除作业通过ID
	 */
	public void delJob(String jobId);
	
	/**
	 * 修改工单时得到作业集合
	 */
	public List<Map<String,Object>> getJobIdMap();
	
	/**
	 * 获取工单添加时的信息
	 */
	public Map<String,Object> getJobdispatchlistInfoForAdd(String jobplanId);
	
	/**
	 * 获取工单添加时的信息--设备类型
	 */
	public List<Map<String,Object>> getEquimentAndType(String typeId);
	
	/**
	 * 获取工单添加时的信息--设备名称
	 */
	public List<Map<String,Object>> getEquimentByType(String nodeid,String typeId);
	
	/**
	 * 工单添加
	 */
	public void addJobdispatchlistInfo(JobdispatchlistAddBean jobdispatchlistAddBean);
	
	/**
	 * 通过名称查询，判断是否多次提交工单
	 */
	public List<Map<String,Object>> getJobdispatchlistByName(String name);
	
	/**
	 * 通过ID跳转到工单修改页面
	 */
	public List<Map<String,Object>> getJobdispatchlistById(String id);
	
	/**
	 * 工单修改
	 */
	public void updataJobdispatchlistInfo(JobdispatchlistUpdataBean jobdispatchlistAddBean);
	
	/**
	 * 工单统计，通过任务号和时间
	 */
	public List<Map<String,Object>> getJobdispatchlistByIdAndTime(String nodeid,String taskNum,Date startTime,Date endTime);
	
	//工单统计 根据任务号得到
	public List<Map<String, Object>> getjobdispatchByTaskNum();
	
	/**
	 * 工单队列管理--待分配作业
	 */
	public List<Map<String,Object>> getWaitJobList(String nodeid);
	
	/**
	 * 工单队列管理--已分配工单清单
	 */
	public List<Map<String,Object>> getAlreadyDispatchList(String nodeid);
	
	/**
	 * 工单队列管理--作业计划详情
	 */
	public List<Map<String,Object>> getJobPlanDetail(String jobplanId);
	
	/**
	 * 获取从属生产计划名称
	 * @return
	 */
	public List<Map<String, Object>> getSubordinatePlanInfoMap(String nodeid);
	
	/**
	 * 获取优先级
	 */
	public List<Map<String,Object>> getPriority();
	
	/**
	 * 作业计划队列管理--作业计划清单
	 */
	public List<Map<String, Object>> getJobplanList(String nodeid);
	
	/**
	 * 工单创建--始末时间的工单开始时间为对应计划的开始时间
	 */
	public Date getMaxEndTimeFormDispatchList(String jobid);
	/**
	 * 作业计划详情默认取一条记录
	 */
	public Map<String, Object> findJobPlanDetailDefault();
	/**
	 * 更改作业计划状态方法
	 */
	public Boolean updateJobPlanInfoStatus(String jobPlanId,String status);
	
	
	/********************************A3******************************************/
	
	/**
	 * 作业计划控制 -- 获取零件类型集合
	 */
	public List<Map<String,Object>> getPartTypeMap();
	
	/**
	 * 作业计划控制 -- 获取作业计划集合
	 */
	public List<Map<String,Object>> getJobPlanMap();
	
	/**
	 * 作业计划控制 -- 获取工艺方案集合
	 */
	public List<Map<String,Object>> getProcessplanMap(String partid);
	
	/**
	 * 作业计划控制 -- 通过作业计划Id获取零件类型集合
	 */
	public List<Map<String,Object>> getPartTypeMapByJobPlanid(String jobplanid);
	
	/**
	 * 投料批次列表
	 */
	public List<Map<String,Object>> getBatchList(String processplanId,String jobplabid);
	
	/**
	 * 作业计划控制 --工单列表
	 */
	public List<Map<String,Object>> getDispatchList(String processplanId);
	
	/**
	 * 作业计划控制 --修改工单
	 * @param jobPlanControlBean
	 */
	public void updateJobDispatch(JobPlanControlBean jobPlanControlBean);
	
	/**
	 * 作业计划控制 --保存作业和保存工单
	 */
	public void saveJobDispatch(JobPlanControlBean jobPlanControlBean);
	
	/**
	 * 作业计划控制 --通过工艺方案ID得到工序清单
	 */
	public List<Map<String,Object>> getProcessByProcessPlanId(String nodeid,String processPlanId);
	
	/**
	 * 作业计划控制 --通过工序ID得到设备序列号
	 * 
	 */
	public List<Map<String,Object>> getSerNoByProcessId(String processId);
	
	/**
	 * 作业计划控制 --通过设备ID得到设备名称
	 */
	public String getEquNameByEquId(String equId);
	
	/**
	 * 作业计划控制 --通过作业计划ID和零件类型ID得到作业计划
	 */
	public List<Map<String,Object>> getJobPlanByJobIdAndPartId(String nodeid,String jobplanid,String partId);
	/**
	 * 获取作业计划父作业计划
	 * @param batchNo
	 * @return
	 */
	public List<TJobplanInfo> getTJobplanInfoByBatchNo(String batchNo,String partTypeId);
	
	/**
	 * 查询plan_type为2的工作计划
	 * @return
	 */
	public List<TJobplanInfo> getJobPlan(String nodeId,String partTypeId);
	
	/**
	 * 根据id获取作业计划
	 * @param id
	 * @return
	 */
	public List<TJobplanInfo> getTJobplanInfoById(String id);
	/**
	 * 获取节点id下的所有工单
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getJobdispatchList(String nodeid,String query,String jobplanId);
	/**
	 * 根据选择的工单号 来算选所需要的数据
	 * @param jobdispatchNo
	 * @return
	 */
	public Map<String,Object> getDataByjobdispatchNo(String jobdispatchNo);

	/**
	 * 根据工单编号 获取工单对象
	 * @param no
	 * @return
	 */
	public TJobdispatchlistInfo getTJobdispatchlistInfoByNo(String no);
	/**
	 * 更新工单
	 * @param tt
	 * @return
	 */
	public String updateTJobdispatchlistInfo(TJobdispatchlistInfo tt);
}
