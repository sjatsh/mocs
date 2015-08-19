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
 * Description:作业计划服务接口
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
	 * @param nodeId 节点ID
	 * @param partid 零件类型ID
	 * @param planStatus 计划状态
	 * @param startTime 开始时间
	 * @param endTime 结束时间
     * @param locale 语言标记
	 * @return 返回作业计划list
	 */
	public Map<String, Object> getAllJobPlanAndPartInfo(String nodeId,String partid,String planStatus,String startTime,String endTime,String isexpand,String locale);
	
	/**
	 * 查找所有的工作计划
	 * @return
	 */
	public List<Map<String, Object>> findAllJobPlan(String nodeId,String partid,String planStatus,String startTime,String endTime);

	/**
	 * 根据产品ID，查询作业计划信息
	 * @param jobNo 作业计划编号
	 * @return 返回作业计划信息
	 */
	public Map<String,Object> findJobPlanDetail(String jobNo);
	
	/**
	 * 添加作业计划
	 * @param jobPlan 作业计划对象
	 */
	public void addJobPlanInfo(TJobplanInfo jobPlan);
	
	/**
	 * 修改作业计划
	 * @param jobPlan 作业计划对象
	 */
	public void updateJobPlanInfo(TJobplanInfo jobPlan);
	
	/**
	 * 删除作业计划
	 * @param jobPlan 作业计划对象
	 */
	public void deleteJobPlanInfoById(TJobplanInfo jobPlan);
	
	/**
	 * 根据ID 查询作业计划信息
	 * @param jobPlanId 作业计划ID
	 * @return 返回作业计划对象
	 */
	public TJobplanInfo geTJobplanInfoById(Long jobPlanId);
	
	public TJobplanInfo geTJobplanInfoById1(Long jobPlanId);
	
	/**
	 * 获取作业计划表中当前最大ID
	 */
	public String getMaxJobPlanInfoId();
	
	/**
	 * 获取跳转到编辑页面的计划表和零件表的信息
     * @param jobplabid 作业计划ID
	 */
	public List<Map<String,Object>> getPlanAndPartList(String jobplabid);
	
	/**
	 * 作业计划页面的生产计划下拉列表
     * @param nodeid 节点ID
	 */
	public List<Map<String,Object>> getPlanAndForList(String nodeid);
	
	/**
	 * 作业计划页面的零件下拉列表
     * @param nodeid 节点ID
	 */
	public List<Map<String,Object>> getPartAndForList(String nodeid);
	
	/**
	 * 作业计划编号的模糊查询
	 * @param no 作业计划编号
	 * @return 返回作业计划编号
	 */
	public List<String> getTJobplanInfoNo(String no);
	
	/**
	 * 作业计划保存
	 */
	public String addJobPlan(JobPlanAddBean jobPlanAddBean);
	/**
	 * 通过作业计划查找 分配任务
	 * @param jopPlanId 作业计划ID
	 */
	public List<Map<String,Object>> getTJobplanTaskInfo(String jopPlanId);

	/**
	 * 通过名称查询，判断是否多次提交作业计划
	 */
	public List<Map<String,Object>> getPlanByName(String name);
	
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
	 * 作业计划控制-- 通过作业计划ID得到零件ID
	 */
	public List<Map<String,Object>> getPartIdByJobPlanId(String jobplanId);

	/**
	 * 通过Id和开始时间得到作业计划
	 */
	public List<Map<String,Object>> getPlanListByIdAndTime(String jobplabid,Date startTime,Date endTime);

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
	 * 通过名称查询，判断是否多次提交工单
	 */
	public List<Map<String,Object>> getJobdispatchlistByName(String name);
	
	/**
	 * 通过ID跳转到工单修改页面
	 */
	public List<Map<String,Object>> getJobdispatchlistById(String id);
	
    /**
     * 工单统计，通过任务号和时间
     * @param nodeid 接单ID
     * @param taskNum 任务数
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
	public List<Map<String,Object>> getJobdispatchlistByIdAndTime(String nodeid,String taskNum,Date startTime,Date endTime);

    /**
     * 工单统计 根据任务号得到
     * @return 返回任务Num
     */
	public List<Map<String, Object>> getjobdispatchByTaskNum();

	/**
	 * 工单队列管理--已分配工单清单
     * @param nodeid 节点ID
	 */
	public List<Map<String,Object>> getAlreadyDispatchList(String nodeid);
	
	/**
	 * 工单队列管理--作业计划详情
     * @param jobplanId 作业计划ID
	 */
	public List<Map<String,Object>> getJobPlanDetail(String jobplanId);
	
	/**
	 * 获取从属生产计划名称
     * @param nodeid 节点ID
	 */
	public List<Map<String, Object>> getSubordinatePlanInfoMap(String nodeid);
	
	/**
	 * 获取优先级
	 */
	public List<Map<String,Object>> getPriority();

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
	 * 作业计划控制 --通过工艺方案ID得到工序清单
     * @param nodeid 节点ID
     * @param processPlanId 工序作业计划ID
	 */
	public List<Map<String,Object>> getProcessByProcessPlanId(String nodeid,String processPlanId);
	
	/**
	 * 作业计划控制 --通过工序ID得到设备序列号
	 * @param processId 工序编号
	 */
	public List<Map<String,Object>> getSerNoByProcessId(String processId);
	
	/**
	 * 作业计划控制 --通过设备ID得到设备名称
     * @param equId 设备ID
	 */
	public String getEquNameByEquId(String equId);

    /**
     * 作业计划控制 --通过作业计划ID和零件类型ID得到作业计划
     * @param nodeid 节点ID
     * @param jobplanid 作业计划ID
     * @param partId 零件类型ID
     * @return 返回作业计划信息Map对象
     */
	public List<Map<String,Object>> getJobPlanByJobIdAndPartId(String nodeid,String jobplanid,String partId);

    /**
     * 获取作业计划父作业计划
     * @param batchNo 批次编号
     * @param partTypeId 零件类型ID
     * @return 获取作业计划对象
     */
	public List<TJobplanInfo> getTJobplanInfoByBatchNo(String batchNo,String partTypeId);

    /**
     * 查询plan_type为2的工作计划
     * @param nodeId 节点ID
     * @param partTypeId 零件类型ID
     * @return 返回作业计划对象
     */
	public List<TJobplanInfo> getJobPlan(String nodeId,String partTypeId);

    /**
     * 获取节点id下的所有工单
     * @param nodeid 节点ID
     * @param query 工单编号模糊查询
     * @param jobplanId 作业计划ID
     * @return 返回工单ID和NO
     */
	public List<Map<String,Object>> getJobdispatchList(String nodeid,String query,String jobplanId);

    /**
	 * 根据选择的工单号 来算选所需要的数据
	 * @param jobdispatchNo 工单编号
	 * @return 返回物料信息
	 */
	public Map<String,Object> getDataByjobdispatchNo(String jobdispatchNo);

}
