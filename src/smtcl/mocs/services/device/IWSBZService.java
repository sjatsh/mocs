package smtcl.mocs.services.device;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.Parameter;

/**
 * 北展逻辑层接口 * 
 * @创建时间 2013-03-05
 * @作者 liguoqiang
 * @修改者
 * @修改日期
 * @修改说明
 * @version V1.0
 */
public interface IWSBZService {
	/**
	 * 更新设备当前状态
	 * @param equSerialNo  设备序列号
	 * @param updateTime 状态时间
	 * @param status 运行状态
	 * @param programm 当前执行文件
	 * @param toolNo 当前刀具号
	 * @param part 在加工零件信息
	 * @param partTimeCount 当前工件计时
	 * @param feedSpeed 当前进给速度
	 * @param feedRate 进给倍率
	 * @param axisSpeed 当前主轴转速
	 * @param axisSpeedRate 主轴倍率
	 * @param xFeed 进给X
	 * @param yFeed 进给Y
	 * @param zFeed 进给Z
	 * @param cuttingPower 切削功率
	 * @param operator 操作人员
	 * @param spindleLaod 主轴负载 
	 * @param partCount 工件计数
	 * @return boolean
	 */
	public boolean updateCurStatus(String equSerialNo,String updateTime,String status,String programm,
			String toolNo,String part,String partTimeCount,String feedSpeed,String feedrate,
			String axisspeed,String axisspeedRate,String xfeed,String yfeed,String zfeed,String cuttingpower,
			String operator,String spindleLoad,String partCount,String afeed,String bfeed,String cfeed,
			String ufeed,String vfeed,String wfeed);
	
	public boolean updateCurStatus(String equSerialNo,String updateTime,String status,String programm,
			String toolNo,String part,String partTimeCount,String feedSpeed,String feedrate,
			String axisspeed,String axisspeedRate,String xfeed,String yfeed,String zfeed,String cuttingpower,
			String operator,String spindleLoad,String partCount);
	
	/**
	 * 更新设备当前计数
	 * @param equSerialNo 设备序列号
	 * @param updateTime 更新时间
	 * @param runningTime 开机时间
	 * @param stopTime 关机时间
	 * @param processTime 加工时间
	 * @param prepareTime 准备时间
	 * @param idleTime 空闲待机时间
	 * @param breakdownTime 故障时间
	 * @param cuttingTime 切削时间
	 * @param dryRunningTime 空运行时间
	 * @param toolChangeTime 换刀时间
	 * @param manualRunningTime 手动运行时间
	 * @param materialTime 上下料时间
	 * @param otherTime 其它时间
	 * @param totalProcessPartsCount 总工件计数
	 * @param anualProcessPartsCount 年工件计数
	 * @param monthProcessPartsCount 月工件计数
	 * @param dayProcessPartsCount 日工件计数
	 * @param workTimePlan 计划开机时间
	 * @return boolean
	 */
	public boolean updateStatusStore(String equSerialNo,String updateTime,String runningTime,String stopTime,
			String processTime,String prepareTime,String idleTime,String breakdownTime,String cuttingTime,
			String dryRunningTime,String toolChangeTime,String manualRunningTime,String materialTime,String otherTime,
			String totalProcessPartsCount,String anualProcessPartsCount,String monthProcessPartsCount,
			String dayProcessPartsCount,String workTimePlan);
	
	/**
	 * 更新设备计数日志
	 * @param equSerialNo 设备序列号
	 * @param updatedate 更新时间
	 * @param runningTime 开机时间
	 * @param stopTime 关机时间
	 * @param processTime 加工时间
	 * @param prepareTime 准备时间
	 * @param idleTime 空闲待机时间
	 * @param breakdownTime 故障时间
	 * @param cuttingTime 切削时间
	 * @param dryRunningTime 空运行时间
	 * @param toolChangeTime 换刀时间
	 * @param manualRunningTime 手动运行时间
	 * @param materialTime 上下料时间
	 * @param otherTime 其它时间
	 * @param totalProcessPartsCount 总工件计数
	 * @param anualProcessPartsCount 年工件计数
	 * @param monthProcessPartscount 月工件计数
	 * @param dayProcessPartsCount 日工件计数
	 * @param workTimePlan 计划开机时间
	 * @return boolean
	 */
	public boolean InsertStatusDaily(String equSerialNo,String updatedate,String runningTime,String stopTime,
			String processTime,String prepareTime,String idleTime,String breakdownTime,String cuttingTime,
			String dryRunningTime,String toolChangeTime,String manualRunningTime,String materialTime,String otherTime,
			String totalProcessPartsCount,String anualProcessPartsCount,String monthProcessPartscount,
			String dayProcessPartsCount,String workTimePlan);
	
	/**
	 * 更新加工事件
	 * @param equSerialNo 设备序列号
	 * @param cuttingeventId 加工事件ID
	 * @param starttime 开始时间
	 * @param finishtime 结束时间
	 * @param cuttingTask 加工任务
	 * @param ncprogramm 数控程序名
	 * @param partName 零件名称
	 * @param theoryWorktime 理论加工时间
	 * @param cuttingTime 切削时间
	 * @param toolchangeTime 换刀时间
	 * @param workTime 加工用时
	 * @param workResult 加工结果
	 * @param theoryCycletime 秒
	 * @return boolean
	 */
	public boolean InsertWorkEvents(String equSerialNo, String cuttingeventId,String starttime, String finishtime,
			String cuttingTask,String ncprogramm,String partName,String theoryWorktime,String cuttingTime,
			String toolchangeTime,String workTime,String workResult,String theoryCycletime);
	
	/**
	 * 更新机床事件
	 * @param equSerialNo 设备序列号
	 * @param eventId 事件ID
	 * @param eventTime 事件时间
	 * @param eventName 事件名称
	 * @param programmname 程序名
	 * @param eventMemo 备注
	 * @return boolean
	 */
	public boolean InsertEvents(String equSerialNo,String eventId,String eventTime,String eventName,
			String programmname,String eventMemo);
	/**
	 * 更新OEE日志
	 * @param equSerialNo 设备序列号
	 * @param caclDate 日期
	 * @param worktimeFact 实际开机时间
	 * @param worktimePlan 计划开机时间
	 * @param acturalOutputTheorytime 实际产量理论耗时
	 * @param processPartsCount 加工工件数
	 * @param qualifiedPartCount 合格工件数
	 * @param dayOeevalue 当天OEE
	 * @param dayTeepvalue 当天TEEP
	 * @return boolean
	 */
	public boolean InsertOEEdaily(String equSerialNo,String caclDate,String worktimeFact,String worktimePlan,
			String acturalOutputTheorytime,String processPartsCount,String qualifiedPartCount,
			String dayOeevalue,String dayTeepvalue);
	
	/**
	 * 更新OEE日志累计
	 * @param equSerialNo 设备序列号
	 * @param year 年份
	 * @param month 月份
	 * @param weekofYear 年周
	 * @param totalworkTimeFact 总实际开机时间
	 * @param totalWorkTimePlan 总计划开机时间
	 * @param totalActOutputTheoryTime 总实际产量理论耗时
	 * @param totalProcesspartscount 总加工工件数
	 * @param totalQualifiedPartCount 总合格工件数
	 * @param totalOeevalue 该阶段OEE
	 * @param totalTeepvalue 该阶段TEEP
	 * @return boolean
	 */
	public boolean InsertOEEstore(String equSerialNo,String year,String month,String weekofYear,String totalworkTimeFact,
			String totalWorkTimePlan,String totalActOutputTheoryTime,String totalProcesspartscount,
			String totalQualifiedPartCount, String totalOeevalue,String totalTeepvalue);
	
	/**
	 * 获取生产指令
	 * @param equSerialNo 设备序列号
	 * @param programm  当前程序名
	 * @return String
	 */
	public String getProductionOrder(String equSerialNo,String programm);
	
	/**
	 * 插入智能诊断IO
	 * @param equSerialNo 设备序列号
	 * @param equIo IO字符串
	 * @param updateDate 更新时间
	 * @return boolean
	 */
	public boolean insertIntelligentDiagnoseIO(String equSerialNo,String equIo,String updateDate);
	
	/**
	 * 获取诊断信息
	 * @param equIo IO字符串
	 * @return map
	 */
	public List<Map<String, Object>> get_Diagnostic_Message(String equ_SerialNo,String componentType);
	
	/**
	 * 获取机床部件
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMachineComponentInfo(String equSerialNo);
	
	/**
	 * 获取诊断列表信息
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getDiagnoseList(String equSerialNo);
	
	/**
	 * 获取诊断信息
	 * @param equIo IO字符串(xml)
	 * @return map
	 */
	public String get_Diagnostic_Message_for_xml(String equ_SerialNo,String componentType); 
	
	/**
	 * 获取诊断信息
	 * @param equIo IO字符串(xml)
	 * @return map
	 */
	public String get_Diagnostic_Message_for_xml_by_nodename(String equ_SerialNo,String componentType,String name_node);  
	
}
