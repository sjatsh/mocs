package smtcl.mocs.services.device.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import smtcl.mocs.beans.device.EquWorkEventProcess;
import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TMachineDiagnoseio;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquCurStatus;
import smtcl.mocs.pojos.device.TUserEquEvents;
import smtcl.mocs.pojos.device.TUserEquOeedaily;
import smtcl.mocs.pojos.device.TUserEquOeestore;
import smtcl.mocs.pojos.device.TUserEquStatusDaily;
import smtcl.mocs.pojos.device.TUserEquStatusStore;
import smtcl.mocs.pojos.device.TUserEquWorkEvents;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.IWSBZService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.ParseXML;
import smtcl.mocs.utils.device.StringUtils;



/**
 * 北展逻辑层
 * @version V1.0
 * @创建时间 2013-03-05
 * @作者 liguoqiang
 * @修改者
 * @修改日期
 * @修改说明
 */
public class WSBZServiceImpl extends GenericServiceSpringImpl<TNodes, String> implements IWSBZService {
	
	/**
	 * 数据调用层
	 */
	private ICommonService commonService;
	
	/**
	 * 构造一个线程池
	 */
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 30, 10, TimeUnit.SECONDS,  
			new ArrayBlockingQueue<Runnable>(20), new ThreadPoolExecutor.DiscardOldestPolicy());
	
	 
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

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
	@Override
	public boolean updateCurStatus(String equSerialNo, String updateTime,
			String status, String programm, String toolNo, String part,
			String partTimeCount, String feedSpeed, String feedrate,
			String axisspeed, String axisspeedRate, String xfeed, String yfeed,
			String zfeed, String cuttingpower, String operator,
			String spindleLoad, String partCount,String afeed,String bfeed,String cfeed,String ufeed,String vfeed,String wfeed) {
		
//		String params="equSerialNo="+equSerialNo+"&updateTime="+updateTime+"&status="+status;
//		
//		LogHelper.log("updateCurStatus", params);
		
		boolean bool=true;
		try {
			equSerialNo=equSerialNo.trim();
			TUserEquCurStatus tu=commonService.get(TUserEquCurStatus.class,equSerialNo);
			if(null!=tu){
				tu.setEquSerialNo(equSerialNo);
				tu.setUpdateTime(StringUtils.convertDate(updateTime, "yyyy-MM-dd HH:mm:ss"));
				tu.setStatus(StringUtils.rstatus(status));
				tu.setProgramm(programm);
				tu.setToolNo(toolNo);
				tu.setPart(part);
				tu.setPartTimeCount(partTimeCount);
				tu.setFeedSpeed(feedSpeed);
				tu.setFeedrate(feedrate);
				tu.setAxisspeed(axisspeed);
				tu.setAxisspeedRate(axisspeedRate);
				tu.setXfeed(StringUtils.isEmpty3(xfeed));
				tu.setYfeed(StringUtils.isEmpty3(yfeed));
				tu.setZfeed(StringUtils.isEmpty3(zfeed));
				tu.setCuttingpower(cuttingpower);
				tu.setOperator(operator);
				tu.setSpindleLoad(spindleLoad);
				tu.setPartCount(partCount);
				tu.setAfeed(StringUtils.isEmpty3(afeed));
				tu.setBfeed(StringUtils.isEmpty3(bfeed));
				tu.setCfeed(StringUtils.isEmpty3(cfeed));
				tu.setUfeed(StringUtils.isEmpty3(ufeed));
				tu.setVfeed(StringUtils.isEmpty3(vfeed));
				tu.setWfeed(StringUtils.isEmpty3(wfeed));
				commonService.update(tu);
			}else{
				 tu=new TUserEquCurStatus();
				 	tu.setEquSerialNo(equSerialNo);
					tu.setUpdateTime(StringUtils.convertDate(updateTime, "yyyy-MM-dd"));
					tu.setStatus(status);
					tu.setProgramm(programm);
					tu.setToolNo(toolNo);
					tu.setPart(part);
					tu.setPartTimeCount(partTimeCount);
					tu.setFeedSpeed(feedSpeed);
					tu.setFeedrate(feedrate);
					tu.setAxisspeed(axisspeed);
					tu.setAxisspeedRate(axisspeedRate);
					tu.setXfeed(StringUtils.isEmpty3(xfeed));
					tu.setYfeed(StringUtils.isEmpty3(yfeed));
					tu.setZfeed(StringUtils.isEmpty3(zfeed));
					tu.setCuttingpower(cuttingpower);
					tu.setOperator(operator);
					tu.setSpindleLoad(spindleLoad);
					tu.setPartCount(partCount);
					tu.setAfeed(StringUtils.isEmpty3(afeed));
					tu.setBfeed(StringUtils.isEmpty3(bfeed));
					tu.setCfeed(StringUtils.isEmpty3(cfeed));
					tu.setUfeed(StringUtils.isEmpty3(ufeed));
					tu.setVfeed(StringUtils.isEmpty3(vfeed));
					tu.setWfeed(StringUtils.isEmpty3(wfeed));
					commonService.save(tu);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}
	
	@Override
	public boolean updateCurStatus(String equSerialNo, String updateTime,
			String status, String programm, String toolNo, String part,
			String partTimeCount, String feedSpeed, String feedrate,
			String axisspeed, String axisspeedRate, String xfeed, String yfeed,
			String zfeed, String cuttingpower, String operator,
			String spindleLoad, String partCount) {
		
		boolean bool=true;
		try {
			equSerialNo=equSerialNo.trim();
			TUserEquCurStatus tu=commonService.get(TUserEquCurStatus.class,equSerialNo);
			if(null!=tu){
				tu.setEquSerialNo(equSerialNo);
				tu.setUpdateTime(StringUtils.convertDate(updateTime, "yyyy-MM-dd HH:mm:ss"));
				tu.setStatus(StringUtils.rstatus(status));
				tu.setProgramm(programm);
				tu.setToolNo(toolNo);
				tu.setPart(part);
				tu.setPartTimeCount(partTimeCount);
				tu.setFeedSpeed(feedSpeed);
				tu.setFeedrate(feedrate);
				tu.setAxisspeed(axisspeed);
				tu.setAxisspeedRate(axisspeedRate);
				tu.setXfeed(StringUtils.isEmpty3(xfeed));
				tu.setYfeed(StringUtils.isEmpty3(yfeed));
				tu.setZfeed(StringUtils.isEmpty3(zfeed));
				tu.setCuttingpower(cuttingpower);
				tu.setOperator(operator);
				tu.setSpindleLoad(spindleLoad);
				tu.setPartCount(partCount);
				commonService.update(tu);
			}else{
				 tu=new TUserEquCurStatus();
				 	tu.setEquSerialNo(equSerialNo);
					tu.setUpdateTime(StringUtils.convertDate(updateTime, "yyyy-MM-dd"));
					tu.setStatus(status);
					tu.setProgramm(programm);
					tu.setToolNo(toolNo);
					tu.setPart(part);
					tu.setPartTimeCount(partTimeCount);
					tu.setFeedSpeed(feedSpeed);
					tu.setFeedrate(feedrate);
					tu.setAxisspeed(axisspeed);
					tu.setAxisspeedRate(axisspeedRate);
					tu.setXfeed(StringUtils.isEmpty3(xfeed));
					tu.setYfeed(StringUtils.isEmpty3(yfeed));
					tu.setZfeed(StringUtils.isEmpty3(zfeed));
					tu.setCuttingpower(cuttingpower);
					tu.setOperator(operator);
					tu.setSpindleLoad(spindleLoad);
					tu.setPartCount(partCount);
					commonService.save(tu);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}

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
	@Override
	public boolean updateStatusStore(String equSerialNo, String updateTime,
			String runningTime, String stopTime, String processTime,
			String prepareTime, String idleTime, String breakdownTime,
			String cuttingTime, String dryRunningTime, String toolChangeTime,
			String manualRunningTime, String materialTime, String otherTime,
			String totalProcessPartsCount, String anualProcessPartsCount,
			String monthProcessPartsCount, String dayProcessPartsCount,
			String workTimePlan) {
		boolean bool=true;
		try {
			TUserEquStatusStore tu=commonService.get(TUserEquStatusStore.class,equSerialNo);
			if(null!=tu){
				tu.setEquSerialNo(equSerialNo);
				tu.setUpdateTime(StringUtils.convertDate(updateTime, "yyyy-MM-dd HH:mm:ss"));
				tu.setRunningTime(Long.parseLong(runningTime));
				tu.setStopTime(Long.parseLong(stopTime));
				tu.setProcessTime(Long.parseLong(processTime));
				tu.setPrepareTime(Long.parseLong(prepareTime));
				tu.setIdleTime(Long.parseLong(idleTime));
				tu.setBreakdownTime(Long.parseLong(breakdownTime));
				tu.setCuttingTime(Long.parseLong(cuttingTime));
				tu.setDryRunningTime(Long.parseLong(dryRunningTime));
				tu.setToolChangeTime(Long.parseLong(toolChangeTime));
				tu.setManualRunningTime(Long.parseLong(manualRunningTime));
				tu.setMaterialTime(Long.parseLong(materialTime));
				tu.setOtherTime(Long.parseLong(otherTime));
				tu.setTotalProcessPartsCount(Double.parseDouble(totalProcessPartsCount));
				tu.setAnualProcessPartsCount(Double.parseDouble(anualProcessPartsCount));
				tu.setMonthProcessPartsCount(Double.parseDouble(monthProcessPartsCount));
				tu.setDayProcessPartsCount(Double.parseDouble(dayProcessPartsCount));
				tu.setWorkTimePlan(Long.parseLong(workTimePlan));
				commonService.update(tu);
			}else{
				tu=new TUserEquStatusStore();
				tu.setEquSerialNo(equSerialNo);
				tu.setUpdateTime(StringUtils.convertDate(updateTime, "yyyy-MM-dd HH:mm:ss") );
				tu.setRunningTime(Long.parseLong(runningTime));
				tu.setStopTime(Long.parseLong(stopTime));
				tu.setProcessTime(Long.parseLong(processTime));
				tu.setPrepareTime(Long.parseLong(prepareTime));
				tu.setIdleTime(Long.parseLong(idleTime));
				tu.setBreakdownTime(Long.parseLong(breakdownTime));
				tu.setCuttingTime(Long.parseLong(cuttingTime));
				tu.setDryRunningTime(Long.parseLong(dryRunningTime));
				tu.setToolChangeTime(Long.parseLong(toolChangeTime));
				tu.setManualRunningTime(Long.parseLong(manualRunningTime));
				tu.setMaterialTime(Long.parseLong(materialTime));
				tu.setOtherTime(Long.parseLong(otherTime));
				tu.setTotalProcessPartsCount(Double.parseDouble(totalProcessPartsCount));
				tu.setAnualProcessPartsCount(Double.parseDouble(anualProcessPartsCount));
				tu.setMonthProcessPartsCount(Double.parseDouble(monthProcessPartsCount));
				tu.setDayProcessPartsCount(Double.parseDouble(dayProcessPartsCount));
				tu.setWorkTimePlan(Long.parseLong(workTimePlan));
				commonService.save(tu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}
	
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
			 String dayProcessPartsCount,String workTimePlan){
		boolean bool=true;
		Collection<Parameter> parameters = new HashSet<Parameter>();
		updatedate=updatedate.substring(0,10);
		String hql="from TUserEquStatusDaily where equSerialNo='"+equSerialNo+"' " +
				//	" and updatedate=to_date('"+updatedate+"','yyyy-MM-dd')";
                                        " and updatedate=DATE_FORMAT('"+updatedate+"','%Y-%m-%d %T')";
		List<TUserEquStatusDaily> list=dao.executeQuery(hql, parameters);
		TUserEquStatusDaily tu;
		if(null!=list&&list.size()>0){
			tu=list.get(0);
			tu.setEquSerialNo(equSerialNo);
			tu.setUpdatedate(StringUtils.convertDate(updatedate, "yyyy-MM-dd"));
			tu.setRunningTime(Long.parseLong(runningTime));
			tu.setStopTime(Long.parseLong(stopTime));
			tu.setProcessTime(Long.parseLong(processTime));
			tu.setPrepareTime(Long.parseLong(prepareTime));
			tu.setIdleTime(Long.parseLong(idleTime));
			tu.setBreakdownTime(Long.parseLong(breakdownTime));
			tu.setCuttingTime(Long.parseLong(cuttingTime));
			tu.setDryRunningTime(Long.parseLong(dryRunningTime));
			tu.setToolChangeTime(Long.parseLong(toolChangeTime));
			tu.setManualRunningTime(Long.parseLong(manualRunningTime));
			tu.setMaterialTime(Long.parseLong(materialTime));
			tu.setOtherTime(Long.parseLong(otherTime));
			tu.setTotalProcessPartsCount(Double.parseDouble(totalProcessPartsCount));
			tu.setAnualProcessPartsCount(Double.parseDouble(anualProcessPartsCount));
			tu.setMonthProcessPartscount(Double.parseDouble(monthProcessPartscount));
			tu.setDayProcessPartsCount(Double.parseDouble(dayProcessPartsCount));
			tu.setWorkTimePlan(Long.parseLong(workTimePlan));
			commonService.update(tu);
		}else{
			tu=new TUserEquStatusDaily();
			tu.setEquSerialNo(equSerialNo);
			tu.setUpdatedate(StringUtils.convertDate(updatedate, "yyyy-MM-dd"));
			tu.setRunningTime(Long.parseLong(runningTime));
			tu.setStopTime(Long.parseLong(stopTime));
			tu.setProcessTime(Long.parseLong(processTime));
			tu.setPrepareTime(Long.parseLong(prepareTime));
			tu.setIdleTime(Long.parseLong(idleTime));
			tu.setBreakdownTime(Long.parseLong(breakdownTime));
			tu.setCuttingTime(Long.parseLong(cuttingTime));
			tu.setDryRunningTime(Long.parseLong(dryRunningTime));
			tu.setToolChangeTime(Long.parseLong(toolChangeTime));
			tu.setManualRunningTime(Long.parseLong(manualRunningTime));
			tu.setMaterialTime(Long.parseLong(materialTime));
			tu.setOtherTime(Long.parseLong(otherTime));
			tu.setTotalProcessPartsCount(Double.parseDouble(totalProcessPartsCount));
			tu.setAnualProcessPartsCount(Double.parseDouble(anualProcessPartsCount));
			tu.setMonthProcessPartscount(Double.parseDouble(monthProcessPartscount));
			tu.setDayProcessPartsCount(Double.parseDouble(dayProcessPartsCount));
			tu.setWorkTimePlan(Long.parseLong(workTimePlan));
			commonService.save(tu);
		}
		   
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}
	
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
    @Override
	public boolean InsertWorkEvents(String equSerialNo, String cuttingeventId,String starttime, String finishtime,
			String cuttingTask,String ncprogramm,String partName,String theoryWorktime,String cuttingTime,
			String toolchangeTime,String workTime,String workResult,String theoryCycletime){

			String params="equSerialNo="+equSerialNo+"-----&cuttingeventId="+cuttingeventId+"-----&starttime="+starttime+"-----&finishtime="+cuttingTask+"-----&cuttingTask=cuttingTask";
			
			LogHelper.log("InsertWorkEvents:", params);
			
			boolean bool=true;
			TUserEquWorkEvents tu=new TUserEquWorkEvents();
			tu.setEquSerialNo(equSerialNo);
			tu.setCuttingeventId(cuttingeventId);
			tu.setStarttime(StringUtils.convertDate(starttime, "yyyy-MM-dd HH:mm:ss"));
			tu.setFinishtime(StringUtils.convertDate(finishtime, "yyyy-MM-dd HH:mm:ss"));
			tu.setCuttingTask(cuttingTask);
			tu.setNcprogramm(ncprogramm);
			tu.setPartNo(partName); //yutao修改
			tu.setTheoryWorktime(Long.parseLong(theoryWorktime));
			tu.setCuttingTime(Long.parseLong(cuttingTime));
			tu.setToolchangeTime(Long.parseLong(toolchangeTime));
			tu.setWorkTime(Long.parseLong(workTime));
			tu.setWorkResult(workResult);
			tu.setTheoryCycletime(Long.parseLong(theoryCycletime));
			tu.setFlag(0L);//是否导入erp标记位，1：已导入，0：未导入
			//查询机床
			String mode="";
			String hql=" from TEquipmentInfo t1 where t1.equSerialNo='"+equSerialNo+"'";
			List<TEquipmentInfo> equList=dao.executeQuery(hql);
			if(equList!=null&&equList.size()>0){
				mode=equList.get(0).getStatus()+"";
				if("0".equals(mode))  
					{ 
					    tu.setCuttingTask(""); //手动模式不记录工单
					    tu.setPartNo("");
					}
			}
			
			//添加人员关联
			hql=" select new Map(t1.equSerialNo as equSerialNo,t.operatorNo as operatorNo) " +
					"from TEquipmentMemberInfo t,TEquipmentInfo t1 where t1.equId=t.equId and t1.equSerialNo='"+equSerialNo+"'";
			List<Map<String,Object>> list1=dao.executeQuery(hql);
			if(list1!=null&&list1.size()>0)
			{
				Map<String,Object> rec=list1.get(0);
				if(rec!=null&&!StringUtils.isEmpty(rec.get("operatorNo")+"")) tu.setOperatorNo(rec.get("operatorNo")+"");
			}
			
			try {
				commonService.save(tu);
				bool=true;
				/**
				 * 开启子线程处理  yutao  start
				 */				
				// 构造一个线程池  

				if("1".equals(mode)){  //自动模式统计工单计数
				threadPool.execute(new EquWorkEventProcess(tu.getId(),"")); 
				 String temppath = this.getClass().getClassLoader().getResource("").getPath(); 
				 temppath= new File(new File(temppath).getParent()).getParent();
				 File file1= new File(temppath+Constants.configPath);
				 if(file1.exists()){
					// threadPool.execute(new WisTransferThread(tu.getId()));
				 }


				}
//				EquWorkEventProcess equWorkEventProcess=new EquWorkEventProcess(tu.getId());
//				Thread childThread = new Thread(equWorkEventProcess);
//				childThread.start();
				/**********end*******************/	
			} catch (Exception e) {
				e.printStackTrace();
				bool=false;
			}
		return bool;
	}
	
	
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
			String programmname,String eventMemo){
		boolean bool=true;
		
		TUserEquEvents tu=new TUserEquEvents();
			tu.setEquSerialNo(equSerialNo);
			tu.setEventId(eventId);
			tu.setEventTime(StringUtils.convertDate(eventTime, "yyyy-MM-dd HH:mm:ss"));
			tu.setEventName(StringUtils.rstatus(eventName));
			tu.setProgrammname(programmname);
			tu.setEventMemo(eventMemo);
			
			try {
				commonService.save(tu);
			} catch (Exception e) {
				e.printStackTrace();
				bool=false;
			}
		return bool;
	}
	

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
			String dayOeevalue,String dayTeepvalue){
		boolean bool=true;
		Collection<Parameter> parameters = new HashSet<Parameter>();
			try {
							
				if(caclDate!=null&&caclDate.contains(" ")) caclDate=caclDate.substring(0,caclDate.indexOf(" "));
				
				String hql="from TUserEquOeedaily where equSerialNo='"+equSerialNo+"' " +
					//	" and caclDate=to_date('"+caclDate+"','yyyy-MM-dd')";
                                                " and caclDate=DATE_FORMAT('"+caclDate+"','%Y-%m-%d %T')";
				System.err.println(caclDate);
				List<TUserEquOeedaily> list=dao.executeQuery(hql, parameters);
				TUserEquOeedaily tu;
				if(null!=list&&list.size()>0){
					System.err.println("-----");
					tu=list.get(0);
					tu.setEquSerialNo(equSerialNo);
					tu.setCaclDate(StringUtils.convertDate(caclDate, "yyyy-MM-dd"));
					tu.setWorktimeFact(Long.parseLong(worktimeFact));
					tu.setWorktimePlan(Long.parseLong(worktimePlan));
					tu.setActuralOutputTheorytime(Long.parseLong(acturalOutputTheorytime));
					tu.setProcessPartsCount(Double.parseDouble(processPartsCount));
					tu.setQualifiedPartCount(Double.parseDouble(qualifiedPartCount));
					tu.setDayOeevalue(Double.parseDouble(dayOeevalue));
					tu.setDayTeepvalue(Double.parseDouble(dayTeepvalue));
					commonService.update(tu);
				}else{
					tu =new TUserEquOeedaily();
					tu.setEquSerialNo(equSerialNo);
					tu.setCaclDate(StringUtils.convertDate(caclDate, "yyyy-MM-dd"));
					tu.setWorktimeFact(Long.parseLong(worktimeFact));
					tu.setWorktimePlan(Long.parseLong(worktimePlan));
					tu.setActuralOutputTheorytime(Long.parseLong(acturalOutputTheorytime));
					tu.setProcessPartsCount(Double.parseDouble(processPartsCount));
					tu.setQualifiedPartCount(Double.parseDouble(qualifiedPartCount));
					tu.setDayOeevalue(Double.parseDouble(dayOeevalue));
					tu.setDayTeepvalue(Double.parseDouble(dayTeepvalue));
					commonService.save(tu);
				}
			} catch (Exception e) {
				e.printStackTrace();
				bool=false;
			}
		return bool;
	}
	
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
			String totalQualifiedPartCount, String totalOeevalue,String totalTeepvalue){
		boolean bool=true;
		Collection<Parameter> parameters = new HashSet<Parameter>();
		TUserEquOeestore tu;
		try {
			String hql="from TUserEquOeestore where equSerialNo='"+equSerialNo+"' ";
			boolean bb=true;
			if(month.equals("0")&&weekofYear.equals("0")){
				hql=hql+" and year>='"+year+"' "
					   +" and month=0 " + " and weekofYear=0 order by year";
			}else if(weekofYear.equals("0")){
				hql=hql+" and concat(year,case when length(month)<2 then concat('0',month) "+
                        "   when  length(month)>1 then concat('',month) end) "+
						"<='"+(year+(month.length()>1?month:("0"+month)))+"' "+
						" and weekofYear=0 "+
						" order by year,month";
			}else if(month.equals("0")){
				hql=hql	+" and concat(year,case when length(weekofYear)<2 then concat('0',weekofYear) "+
                        "   when  length(weekofYear)>1 then concat('',weekofYear) end) "+
						" ='"+(year+(weekofYear.length()>1?weekofYear:("0"+weekofYear)))+"' "+
						" and month=0"+
						" order by year,weekofYear";
			}else{
				bb=false;
			}
			if(bb){
				List<TUserEquOeestore> list=dao.executeQuery(hql, parameters);
				if(null!=list&&list.size()>0){
					tu=list.get(0);
					tu.setEquSerialNo(equSerialNo);
					tu.setYear(Long.parseLong(year));
					tu.setMonth(Long.parseLong(month));
					tu.setWeekofYear(Long.parseLong(weekofYear));
					tu.setTotalworkTimeFact(Long.parseLong(totalworkTimeFact));
					tu.setTotalWorkTimePlan(Long.parseLong(totalWorkTimePlan));
					tu.setTotalActOutputTheoryTime(Long.parseLong(totalActOutputTheoryTime));
					tu.setTotalProcesspartscount(Double.parseDouble(totalProcesspartscount));
					tu.setTotalQualifiedPartCount(Double.parseDouble(totalQualifiedPartCount));
					tu.setTotalOeevalue(Double.parseDouble(totalOeevalue));
					tu.setTotalTeepvalue(Double.parseDouble(totalTeepvalue));
					commonService.update(tu);
				}else{
					tu=new TUserEquOeestore();
					tu.setEquSerialNo(equSerialNo);
					tu.setYear(Long.parseLong(year));
					tu.setMonth(Long.parseLong(month));
					tu.setWeekofYear(Long.parseLong(weekofYear));
					tu.setTotalworkTimeFact(Long.parseLong(totalworkTimeFact));
					tu.setTotalWorkTimePlan(Long.parseLong(totalWorkTimePlan));
					tu.setTotalActOutputTheoryTime(Long.parseLong(totalActOutputTheoryTime));
					tu.setTotalProcesspartscount(Double.parseDouble(totalProcesspartscount));
					tu.setTotalQualifiedPartCount(Double.parseDouble(totalQualifiedPartCount));
					tu.setTotalOeevalue(Double.parseDouble(totalOeevalue));
					tu.setTotalTeepvalue(Double.parseDouble(totalTeepvalue));
					commonService.save(tu);
				}
			}else{
				bool=false;
			}	
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}
	
	
	//程序名|工单编号|零件编号|操作人员id|理论加工时间|理论节拍时间
	public String getProductionOrder(String equSerialNo,String programm){
		String rsStr=programm;
		Map<String, Object> rs=new HashMap<String, Object>();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map("
				   + " b.no as no,"
				   + " b.theoryWorktime as jgtheoryWorktime,"
				   + " b.TProcessInfo.theoryWorktime as jptheoryWorktime,"
				   + " b.TProcessInfo.TProcessplanInfo.TPartTypeInfo.no as partNo)"
				   + " from TEquJobDispatch a,TJobdispatchlistInfo b"
				   + " where a.jobdispatchNo=b.no"
				   + " and a.equSerialNo='"+equSerialNo+"'"
				   + " and a.status=2";
		List<Map<String,Object>> list=dao.executeQuery(hql, parameters);
		if(StringUtils.listIsNull(list)){
			Map<String,Object> jd=list.get(0);
			rsStr=rsStr+"|"+jd.get("no");//工单编号
			if(!StringUtils.isEmpty(jd.get("jgtheoryWorktime")+""))
			    rs.put("jgtheoryWorktime", jd.get("jgtheoryWorktime").toString());//理论加工时间
			else 
				rs.put("jgtheoryWorktime", "0");//理论加工时间
			if(!StringUtils.isEmpty(jd.get("jptheoryWorktime")+""))
			    rs.put("jptheoryWorktime", jd.get("jptheoryWorktime").toString());//理论节拍时间
			else
				rs.put("jptheoryWorktime", "0");//理论节拍时间
			if(null!=jd.get("partNo")&&!"".equals(jd.get("partNo")+"")){
				rsStr=rsStr+"|"+jd.get("partNo")+"";//零件编号
			}else{
				rsStr=rsStr+"|0";//零件编号
			}
			
		}else{
			rsStr=rsStr+"|0|0";
			rs.put("jgtheoryWorktime","");//理论加工时间
			rs.put("jptheoryWorktime", "");//理论节拍时间
		}
		String hql4="select m.id" +
			    " from TEquipmentMemberInfo em,TMemberInfo m,TEquipmentInfo e" +
			    " where em.memberId=m.id" +
			    " and em.equId=e.equId" +
			    " and e.equSerialNo='"+equSerialNo+"'";
		List list4=dao.executeQuery(hql4, parameters);
		if(StringUtils.listIsNull(list4)){
			rsStr=rsStr+"|"+list4.get(0);//操作人员id
		}else{
			rsStr=rsStr+"|0";//操作人员id
		}
			rsStr=rsStr+"|"+rs.get("jgtheoryWorktime");
			rsStr=rsStr+"|"+rs.get("jptheoryWorktime");
			System.err.println(rsStr);
		return rsStr;
	}
	
	/**
	 * 插入智能诊断IO
	 * @param equSerialNo 设备序列号
	 * @param equIo IO字符串
	 * @param updateDate 更新时间
	 * @return boolean
	 */
	public boolean insertIntelligentDiagnoseIO(String equSerialNo,String equIo,String updateDate){
		boolean bool=false;
		Collection<Parameter> parameters = new HashSet<Parameter>();
		System.err.println("-------------IOSTRING:"+equIo);
		equIo=ParseXML.praxml(equIo);		
		String hql="from TMachineDiagnoseio where equSerialNo='"+equSerialNo+"'";
		Date ud=StringUtils.convertDate(updateDate,"yyyy-MM-dd HH:mm:SS");
		try {
			List list=dao.executeQuery(hql, parameters);
			if(null!=list&&list.size()>0){
				TMachineDiagnoseio td=(TMachineDiagnoseio)list.get(0);
				td.setEquSerialNo(equSerialNo);
				td.setEquIo(equIo);
				td.setUpdateDate(ud);
				commonService.update(td);
				bool=true;
			}else{
				TMachineDiagnoseio td=new TMachineDiagnoseio();
				td.setEquSerialNo(equSerialNo);
				td.setEquIo(equIo);
				td.setUpdateDate(ud);
				commonService.save(td);
				bool=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}

	@Override
	public List<Map<String, Object>> get_Diagnostic_Message(String equ_SerialNo,String componentType) {
		String hql ="SELECT new Map(" +
				"t.equIo as equIo ," +
				" t.equSerialNo as equSerialNo"+
				") FROM TMachineDiagnoseio as t " +
				"where 1=1 "+ " and t.equSerialNo = " + "'" + equ_SerialNo + "'";
		List<Map<String, Object>>  temp = dao.executeQuery(hql);
		if(!temp.isEmpty()){
		String xmlStr=ParseXML.getNodeAttributeName(temp.get(0).get("equIo").toString(),componentType);
		temp.clear();
		String[] xmlstrarray=xmlStr.split("&");
		StringBuilder tempsuilder=new StringBuilder();
		for(String single:xmlstrarray){
			String componentvalue=single.split("@")[0];
			String variantvalue=single.split("@")[1];
			variantvalue=variantvalue.substring(0, variantvalue.lastIndexOf(","));
			String hqls="SELECT new Map(t.component as component , t.m9Variant as m9Variant , t.alertInfo as alertInfo , t.cause as cause , t.solution as solution) From TMachineDiagnoseInfo as t " +
					"where t.component ="+"'"+componentvalue+"'"+" AND t.m9Variant in("+variantvalue+")";
			List<Map<String, Object>> tempnum=dao.executeQuery(hqls);
			temp.addAll(tempnum);
			if(!tempnum.isEmpty()){
			Map<String, Object> tempcountnum=new HashMap<String, Object>();
			
			tempcountnum.put("component-num", tempsuilder.append(temp.size()+"&"));
			temp.add(tempcountnum);
			}
		}
		  if(temp.isEmpty()){return temp;}
		}
		return temp;
	}
	
	@Override
	public String get_Diagnostic_Message_for_xml_by_nodename(String equ_SerialNo,String componentType,String name_node) {
		List<Map<String, Object>>  temp=get_Diagnostic_Message(equ_SerialNo,componentType);
		String xmlstr= ParseXML.list_map_to_String_for_flash(temp,equ_SerialNo);
		return ParseXML.remove_irrelevant_factor(xmlstr,name_node);
	}

	@Override
	public List<Map<String, Object>> getMachineComponentInfo(String equSerialNo) {
		//机床序列号预留
		String hql="select distinct new Map(t.component as component,t.com_zh as name) from TMachineDiagnoseInfo t";
		List<Map<String,Object>> result= commonService.executeQuery(hql);
		return result;
	}

	@Override
	public List<Map<String, Object>> getDiagnoseList(String equSerialNo) {
		List<Map<String, Object>> templist=get_Diagnostic_Message(equSerialNo,"");
		List<Map<String, Object>> rs=new ArrayList<Map<String,Object>>();
		String hql="select new Map(t.updateDate as updateDate) from TMachineDiagnoseio t where t.equSerialNo='"+equSerialNo+"'";
		List<Map<String,Object>> result=commonService.executeQuery(hql);
		if(result==null&&result.size()<1) return null;
		String updatetime=((Map<String,Object>)result.get(0)).get("updateDate")+"";
		Set<Map<String,Object>> set = new HashSet<Map<String,Object>>();
		if(templist!=null&&templist.size()>0)
		{
	    	for(Map<String,Object> map:templist)
	    	{  
	    		String component=map.get("component")+"";
	    		String alertInfo=map.get("alertInfo")+"";	    		
	    		if(!StringUtils.isEmpty(component)&&!"null".equals(component))
	    		{
	    			Map<String,Object> addmap=new HashMap<String,Object>();
	    			addmap.put("component", component);
	    			addmap.put("alertInfo", alertInfo);	 
	    			addmap.put("alertTime", updatetime);
	    			addmap.put("equSerialNo", equSerialNo);
	    			set.add(addmap);
	    		}    		
	    	}
	    	rs.addAll(set);
 		}	
		return rs;
	}

	@Override
	public String get_Diagnostic_Message_for_xml(String equ_SerialNo,String componentType) {
		List<Map<String, Object>>  temp=get_Diagnostic_Message(equ_SerialNo,componentType);
		
		return ParseXML.list_map_to_String(temp,equ_SerialNo);
	}
}
