package smtcl.mocs.services.device;

import java.util.*;

import net.sf.cglib.core.Local;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.beans.device.JobdispatchChangeBean;
import smtcl.mocs.model.DeviceInfoModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquCurStatus;


/**
 * 用户查看设备总览接口 
 * @创建时间 2012-12-03
 * @作者 liguoqiang
 * @修改者
 * @修改日期
 * @修改说明
 * @version V1.0
 */
public interface IDeviceService extends IGenericService<TNodes, String> {
	/**
	 * 获取设备总览的历史数据
	 * @param serialon 设备序列号
	 * @return  DeviceInfoModel
	 */
	public DeviceInfoModel getDeviceInfoModelHistory(String serialon);

	/**
	 * 获取设备总览的实时数据
	 * @param serialon 设备序列号
	 * @return DeviceInfoModel
	 */
	public DeviceInfoModel getDeviceInfoModelRealtime(String serialon);

	/**
	 * 获取节点下设备的信息
	 * @param nodeid 设备序列号
	 * @return List<DeviceInfoModel>
	 */
	public List<DeviceInfoModel> getDevicesOverview(Long nodeid);

	/**
	 * 时间比较-图表的查询
	 * @param parameters 查询条件集合
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> compareDevicesTime (Collection<Parameter> parameters,String startTime,String endTime);
	
	/**
	 * 时间统计查询
	 * @param parameters 查询条件集合
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findIHisroryStatistics (String equSerialNo,String startTime,String endTime);
	
	/**
	 * 单台设备时间段OEE查询 
	 * @param parameters 查询条件集合
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> selectServiceOEE(Collection<Parameter> parameters);		
	public List<Map<String, Object>> selectServiceOEE(String startTime,String endTime,String equSerialNo);
	/**
	 * 历史分析分页查询结果集
	 * @param pageNo 页码
	 * @param pageSize 条数
	 * @param parameters 查询条件集合
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getDeviceProFrequenceStat( int pageNo, int pageSize, Collection<Parameter> parameters);
	
	/**
	 * 历史分析查询全部结果集
	 * @param parameters 查询条件集合
	 * @return List
	 */
	public List getAllDeviceProFrequenceStat(Collection<Parameter> parameters);
	
	/**
	 * 根据设备名称查询工件名
	 * @param nameSelected 设备序列号
	 * @return List<String>
	 */
	public List<String> getPartNameByEquName(String nameSelected);

	/**
	 * 根据节点id获取设备信息
	 * @param nodeid 节点id
	 * @return List
	 */
	public List getNodesAllEquName(String nodeid);
	
	/**
	 * 根据设备序列号查询所有设备信息
	 * @param equSerialNos 设备序列号
	 * @return List
	 */
	public List<TEquipmentInfo> getAllEquName(String equSerialNos);
	
	/**
	 * 获取当前节点下所有设备和运行状态（包括子节点）
	 * @param nodeid 节点id 
	 * @param pageNo 页码
	 * @param size 条数
	 * @param status 状态
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getNodesAllEquNameStruts(String nodeid, int pageNo, int size, String status);
	
	/**
	 * 获取某个时间段内某部机器的oee，teep
	 * @param pageNo 页码
	 * @param pageSize 条数
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param name 名字
	 * @param dateType 查询时间类型
	 * @return IDataCollection<DeviceInfoModel> 
	 */
	public IDataCollection<DeviceInfoModel> getOEETEEP(int pageNo,int pageSize,Date start, Date end, String name, String dateType);
	
	/**
	 * 加工事件分析对应的查询 
	 * @param pageNo 页码
	 * @param pageSize 条数
	 * @param parameters 查询条件集合
	 * @param nodeIds 多个节点id
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getDeviceProcessEventStat(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);
	
	/**
	 * 获取表中所有零件名称
	 * @return List<String>
	 */
	public List<String> getAllPartName();

	/**
	 * 获取表中所有事件名称
	 * @return List<String>
	 */
	public List<String> getAllEventName();

	/**
	 * 获取查询条件中,存在数据的日期,并去除重复
	 * @param parameters 查询条件集合
	 * @return List<String>
	 */
	public List<String> getDateSum(Collection<Parameter> parameters);

	/**
	 * 机床事件分析对应的查询
	 * @param pageNo 页码
	 * @param pageSize 条数
	 * @param parameters 查询条件集合
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getDeviceMachineEventStat(Locale locale, int pageNo, int pageSize, Collection<Parameter> parameters,Map<String,String> eventMap);

	/**
	 * 获取数据库的机床事件信息
	 * @param parameters 查询条件集合
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getDeviceMachineEventStatChart(Locale locale,Collection<Parameter> parameters,Map<String,String> eventMap);
	public List<Map<String, Object>> getDeviceMachineEventStatChartDateCount(Collection<Parameter> parameters);
	/**
	 * 用户查询多台机床之间的 OEE走势比较结果 
	 * @param startTime 开始时间	
	 * @param endTime 结束时间
	 * @param dateTimeType 时间类型
	 * @param serNames 多个设备序列号
	 * @return List<DeviceInfoModel>
	 */
	public List<DeviceInfoModel> getDevicesOEECompare(Date startTime, Date endTime, String dateTimeType, String[] serNames);
	
	/**
	 * 单台设备时间段分析属性换算
	 * @param map 需要换算的数据
	 */
	 public void convertData(Map<String, Object> map,Locale locale);
	 
	/**
	 * 根据节点ID获取节点名称
	 * @param nodeid 节点id
	 * @return List
	 */
	public List<Map<String,Object>> getNodeName(String nodeid);

	/**
	 * 根据节点id 查询子节点下所有设备信息
	 * @param nodeid 节点id
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getNodesAllEquNameInfo(String nodeid);

	/**
	 * flag为2获取兴趣列表设备下的设备
	 * @param userId 用户id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>>  getEquipmentAllEquName(String userId);
	
	/**
	 * 获取当前节点下子节点
	 * @param nodeId 节点id
	 * @return String
	 */
	public String getNodeAllId(String nodeId);
	
	/**
	 * 根据节点查询出设备及其状态
	 * @param nodeid 节点id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getNodesAllEquNameStruts(String nodeid);
	
	/**
	 * 根据节点id 查询该节点下所有设备的名称 包括子节点
	 * @param nodeid 节点id
	 * @return List<TEquipmentInfo>
	 */
	public List<TEquipmentInfo> getNodesAllTEquipmentInfo(String nodeid);
	
	/**
	 * 获取实时状态图标数据
	 * @param parameters 查询条件集合
	 * @return List<Map<String, Object>>
	 */ 
	public List<Map<String, Object>> getRealStatistics(Collection<Parameter> parameters);
	
	/**
	 * 定时检查UserEquCurStatus的数据超过半小时没有操作的设为停机。
	 */ 
	public void UpdateUserEquCurStatus();
	
	/**
	 * 获取生产计划完成率
	 * @param nodeid 节点id
	 * @param date 时间 格式yyyy-MM
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getppcr(String nodeid,String date);
	
	/**
	 * 获取当日加工数
	 * @param nodeid
	 * @param date
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getNumberOfDayProcessing(String nodeid,String date);
	
	/**
	 * 获取设备效率
	 * @param nodeid
	 * @param date
	 * @return
	 */
	public List<Map<String,Object>> getEquEfficiency(String nodeid,String date);
	
	/**
	 *机床状态
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMachineToolStatus(String equSerialNo);
	
	/**
	 * 加工任务
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMachiningTask(String equSerialNo);
	
	/**
	 * 设备效率
	 * @param equSerialNo
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getEquipmentEfficiency(String equSerialNo);
	
	/**
	 * 获取制定设备的当日加工数
	 * @param nodeid
	 * @param date 格式yyyy-MM-dd
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getappointdayEquNumber(String[] equSerialNo,String date);
	/**
	 * 根据设备序列号获取设备三维仿真地址
	 * @param equSerialNo
	 * @return
	 */
	public List getipAddress(String equSerialNo);
	/**
	 *根据设备序列号获取设备实时信息
	 * @param equSerialNo
	 * @return
	 */
	public List<TUserEquCurStatus> getTUserEquCurStatusByEqusrialno(String equSerialNo);
	/**
	 * 通过当前节点查询父节点
	 */
	public String getParentIdByNodeid(String nodeid);
	/**
	 * 查询节点下的设备
	 * @param pId
	 * @return
	 */
	public List getTEquipmentInfoByPId(String pId);
	/**
	 * 根据id获取设备清单信息
	 * @param nodeId
	 * @return
	 */
	public List<Map<String,Object>> getEquInventoryInfo(String nodeId);
	
	/**
	 * 获取设备信息
	 */
	public List<Map<String,Object>> queryEquipmentList(String equId,String equId2,String nodeId); 
	
	/**
	 * 获取工单List信息
	 * @param equipmentId
	 * @return
	 */
	public List<Map<String,Object>> queryJobDispatchList(String equipmentId,String dispatchId); 
	
	/**
	 * 获取人员信息
	 */
	public List<Map<String,Object>> getUserList(String userId,String nodeId);
	
	/**
	 * 保存报工信息
	 */
	public String saveInfo(int num,String userId,String equId,String dispatchId,Date startTime,Date finishTime,String partNo,String loginUserNo,String isGood,
			 String depName,String jgCheckUser,String zpCheckUser,String sjCheckUser);
	
	/**
	 * 验证报工数量是否合法
	 */
	public String checkNum(String dispatchId, int num);
	
	/**
	 * 获取零件名集合
	 */
	public List<Map<String,Object>> getPartTypeMap(String partId,String nodeId);
	
	/**
	 * 获取工单号集合
	 */
	public List<Map<String,Object>> getDispatchNoMap();
	
	/**
	 * 获取设备序列号集合
	 */
	public List<Map<String,Object>> getEquTypeMap(String nodeId);

    /**
     * 获取设备序列号集合
     */
    public List<Map<String,Object>> getEquTypeMap();
	
	/**
	 * 获取设备序列号集合(下拉框)
	 */
	public List<Map<String,Object>> getEquSerialNoMap(String query);
	
	/**
	 * 获取设备名称集合
	 */
	public List<Map<String,Object>> getEquTypeNameMapByNodeId(String nodeId);
	
	/**
	 * 获取设备名称集合(下拉框)
	 */
	public List<Map<String,Object>> getEquTypeNameMap(String query);
	
	/**
	 * 获取产量 
	 * @param equSerialNo
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> getRCLData(String equSerialNo,int type);
	/**
	 * 获取member信息
	 */
	public List<Map<String, Object>> getMemberInfo(String userId,String nodeId);
	/**
	 * 获取单个工单信息
	 */
	public List<Map<String,Object>> getJobDispatch(String dispatchId); 
	/**
	 * 获取批次No集合
	 */
	public List<Map<String,Object>> getJobPlanNoList(String partId,String jobplanId); 
	/**
	 * 获取工单No集合
	 */
	public List<Map<String,Object>> getJobdispatchNoList(String jobplanNo); 
	/**
	 * 获取批次集合
	 */
	public List<Map<String,Object>> getJobplanList(String jobplanId); 
	/**
	 * 获取综合信息(报工数据修改)
	 */
	public List<Map<String,Object>> getDataList(String equ_SerialNo,String partTypeID,String processID,Date dateTime); 
  
	/**
	 * 获取工序信息
	 */
	public List<Map<String,Object>> getProcessInfo(String processId); 
	
	/**
	 * 获取设备产量信息
	 */
	public List<Map<String,Object>> getEquProductionInfo(String equ_SerialNo,String partTypeID,String processID,String updateDate); 
	/**
	 * 修改报工数据处理
	 */
	public String saveChangeProcessNum(String num,String userId,String jobdispatchId,String equ_SerialNo,String partTypeID,String processID,Date updateDate,String loginUserNo,String eventNo); 
    
	/**
	 * 获取List信息
	 */
	public List<Map<String,Object>> getListInfo(String sql); 
	/**
	 * 成品入库信息保存
	 * @param num 入库数量
	 * @param inventoryId库房id
	 * @param materialPositionId 库位Id
	 * @param partId 成品ID
	 * @param dataList列表信息
	 */
	public String saveInStockDataInfo(String num,String inventoryId,String materialPositionId,String partId,String instockNo,List<Map<String,Object>> dataList);
}
