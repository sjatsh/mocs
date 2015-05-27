package smtcl.mocs.model;

/**
 * 设备状态总览数据实体类
 * @创建时间 2012-12-03
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public class DeviceInfoModel {
	/**
	 * 序号id
	 */
	private int id=0;
	
	/**
	 *节点id
	 */
	private String nodeId="";	
	
	/**
	 *机床名称
	 */
	private String equName="";   
	
	/**
	 *类型
	 */
	private String equType="";  
	
	/**
	 *机床型号
	 */
	private String manufactureType=""; 
	
	/**
	 *当前程序
	 */
	private String programm=""; 
	
	/**
	 *数控系统
	 */
	private String ncprogramm="";  
	
	/**
	 *操作人员
	 */
	private String operator="";
	
	/**
	 *开机时间
	 */
	private String runningTime="";
	
	/**
	 *关机时间
	 */
	private String stopTime="";
	
	/**
	 *加工时间
	 */
	private String processTime="";
	
	/**
	 *准备时间 
	 */
	private String prepareTime="";
	
	/**
	 *空闲待机时间
	 */
	private String idleTime="";
	
	/**
	 *故障时间
	 */
	private String breakDownTime="";
	
	/**
	 *空运行时间
	 */
	private String dryRunningTime="";
	
	/**
	 *换刀时间
	 */
	private String toolChangeTime="";
	
	/**
	 *切削时间
	 */
	private String cuttingTime;
	
	/**
	 *手动运行时间
	 */
	private String manualRunningTime;
	
	/**
	 *上下料时间
	 */
	private String materialTime;
	
	/**
	 *其他时间
	 */
	private String otherTime;
	
	/**
	 *总工件计数
	 */
	private String totalProcessPartsCount;
	
	/**
	 *年工件计术
	 */
	private String anualProcessPartsCount;
	
	/**
	 *月工件计数
	 */
	private String monthProcessPartsCount;
	
	/**
	 *日工件计数
	 */
	private String dayProcessPartsCount;
	
	/**
	 *设备序列号
	 */
	private String equSerialNo; 
	
	/**
	 *设备状态
	 */
	private String status;
	
	/**
	 *机床id
	 */
	private String symgEquId;
	
	/**
	 *状态时间
	 */
	private String updateTime;
	
	/**
	 *当前刀具号
	 */
	private String toolNo;   
	
	/**
	 *正在加工的零件信息
	 */
	private String part;  
	
	/**
	 *当前工件计时
	 */
	private String partTimeCount;
	
	/**
	 *当前工件计数
	 */
	private String partCount;
	
	/**
	 *当前进给速度
	 */
	private String feedSpeed;  
	
	/**
	 *当前进给倍率
	 */
	private String feedrate;
	
	/**
	 *当前主轴转速
	 */
	private String axisspeed;  
	
	/**
	 *当前主轴倍率
	 */
	private String axisspeedRate;
	
	/**
	 *进给轴坐标X
	 */
	private String xfeed;  
	
	/**
	 *进给轴坐标Y
	 */
	private String yfeed; 
	
	/**
	 *进给轴坐标Z
	 */
	private String zfeed; 
	
	/**
	 *切削功率
	 */
	private String cuttingpower;
	
	/**
	 *可用率
	 */
	private String availability;
	
	/**
	 *表现性
	 */
	private String expressivenessOf;
	
	/**
	 *质量
	 */
	private String quality;
	
	/**
	 *当天OEE
	 */
	private String dayOeevalue;
	
	/**
	 *当天TEEP
	 */
	private String dayTeepvalue; 
	
	/**
	 *OEE分析事件时间
	 */
	private String oeeTime; 
	
	/**
	 *图片URL
	 */
	private String path;
	/**
	 * 实际开机时间
	 */
	private String worktimeFact;
	/**
	 * 合格工件数
	 */
	private String qualifiedPartCount;
	/**
	 * 加工工件数
	 */
	private String processPartsCount;
	
	public String getWorktimeFact() {
		return worktimeFact;
	}

	public void setWorktimeFact(String worktimeFact) {
		this.worktimeFact = worktimeFact;
	}

	public String getQualifiedPartCount() {
		return qualifiedPartCount;
	}

	public void setQualifiedPartCount(String qualifiedPartCount) {
		this.qualifiedPartCount = qualifiedPartCount;
	}

	public String getProcessPartsCount() {
		return processPartsCount;
	}

	public void setProcessPartsCount(String processPartsCount) {
		this.processPartsCount = processPartsCount;
	}

	public String getToolChangeTime() {
		return toolChangeTime;
	}
	
	public void setToolChangeTime(String toolChangeTime) {
		this.toolChangeTime = toolChangeTime;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getEquName() {
		return equName;
	}
	
	public void setEquName(String equName) {
		this.equName = equName;
	}
	
	public String getEquType() {
		return equType;
	}
	
	public void setEquType(String equType) {
		this.equType = equType;
	}
	
	public String getManufactureType() {
		return manufactureType;
	}
	
	public void setManufactureType(String manufactureType) {
		this.manufactureType = manufactureType;
	}
	
	public String getProgramm() {
		return programm;
	}
	
	public void setProgramm(String programm) {
		this.programm = programm;
	}
	
	public String getNcprogramm() {
		return ncprogramm;
	}
	
	public void setNcprogramm(String ncprogramm) {
		this.ncprogramm = ncprogramm;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getRunningTime() {
		return runningTime;
	}
	
	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}
	
	public String getStopTime() {
		return stopTime;
	}
	
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	
	public String getProcessTime() {
		return processTime;
	}
	
	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}
	
	public String getPrepareTime() {
		return prepareTime;
	}
	
	public void setPrepareTime(String prepareTime) {
		this.prepareTime = prepareTime;
	}
	
	public String getIdleTime() {
		return idleTime;
	}
	
	public void setIdleTime(String idleTime) {
		this.idleTime = idleTime;
	}
	
	public String getBreakDownTime() {
		return breakDownTime;
	}
	
	public void setBreakDownTime(String breakDownTime) {
		this.breakDownTime = breakDownTime;
	}
	
	public String getDryRunningTime() {
		return dryRunningTime;
	}
	
	public void setDryRunningTime(String dryRunningTime) {
		this.dryRunningTime = dryRunningTime;
	}
	
	public String getCuttingTime() {
		return cuttingTime;
	}
	
	public void setCuttingTime(String cuttingTime) {
		this.cuttingTime = cuttingTime;
	}
	
	public String getManualRunningTime() {
		return manualRunningTime;
	}
	
	public void setManualRunningTime(String manualRunningTime) {
		this.manualRunningTime = manualRunningTime;
	}
	
	public String getMaterialTime() {
		return materialTime;
	}
	
	public void setMaterialTime(String materialTime) {
		this.materialTime = materialTime;
	}
	
	public String getOtherTime() {
		return otherTime;
	}
	
	public void setOtherTime(String otherTime) {
		this.otherTime = otherTime;
	}
	
	public String getTotalProcessPartsCount() {
		return totalProcessPartsCount;
	}
	
	public void setTotalProcessPartsCount(String totalProcessPartsCount) {
		this.totalProcessPartsCount = totalProcessPartsCount;
	}
	
	public String getAnualProcessPartsCount() {
		return anualProcessPartsCount;
	}
	
	public void setAnualProcessPartsCount(String anualProcessPartsCount) {
		this.anualProcessPartsCount = anualProcessPartsCount;
	}
	
	public String getMonthProcessPartsCount() {
		return monthProcessPartsCount;
	}
	
	public void setMonthProcessPartsCount(String monthProcessPartsCount) {
		this.monthProcessPartsCount = monthProcessPartsCount;
	}
	
	public String getDayProcessPartsCount() {
		return dayProcessPartsCount;
	}
	
	public void setDayProcessPartsCount(String dayProcessPartsCount) {
		this.dayProcessPartsCount = dayProcessPartsCount;
	}
	
	public String getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getEquSerialNo() {
		return equSerialNo;
	}
	
	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSymgEquId() {
		return symgEquId;
	}
	
	public void setSymgEquId(String symgEquId) {
		this.symgEquId = symgEquId;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getToolNo() {
		if(null!=toolNo&&toolNo.length()>13)
			toolNo=toolNo.substring(0,13)+"...";
		return toolNo;
	}
	
	public void setToolNo(String toolNo) {
		this.toolNo = toolNo;
	}
	
	public String getPart() {
		if(null!=part&&part.length()>13)
			part=part.substring(0,13)+"...";
		return part;
	}
	
	public void setPart(String part) {
		this.part = part;
	}
	
	public String getPartTimeCount() {
		return partTimeCount;
	}
	
	public void setPartTimeCount(String partTimeCount) {
		this.partTimeCount = partTimeCount;
	}
	
	public String getPartCount() {
		return partCount;
	}
	
	public void setPartCount(String partCount) {
		this.partCount = partCount;
	}
	
	public String getFeedSpeed() {
		return feedSpeed;
	}
	
	public void setFeedSpeed(String feedSpeed) {
		this.feedSpeed = feedSpeed;
	}
	
	public String getFeedrate() {
		return feedrate;
	}
	
	public void setFeedrate(String feedrate) {
		this.feedrate = feedrate;
	}
	
	public String getAxisspeed() {
		return axisspeed;
	}
	
	public void setAxisspeed(String axisspeed) {
		this.axisspeed = axisspeed;
	}
	
	public String getAxisspeedRate() {
		return axisspeedRate;
	}
	
	public void setAxisspeedRate(String axisspeedRate) {
		this.axisspeedRate = axisspeedRate;
	}
	
	public String getXfeed() {
		return xfeed;
	}
	
	public void setXfeed(String xfeed) {
		this.xfeed = xfeed;
	}
	
	public String getYfeed() {
		return yfeed;
	}
	
	public void setYfeed(String yfeed) {
		this.yfeed = yfeed;
	}
	
	public String getZfeed() {
		return zfeed;
	}
	
	public void setZfeed(String zfeed) {
		this.zfeed = zfeed;
	}
	
	public String getCuttingpower() {
		return cuttingpower;
	}
	
	public void setCuttingpower(String cuttingpower) {
		this.cuttingpower = cuttingpower;
	}
	
	public String getDayOeevalue() {
		return dayOeevalue;
	}
	
	public void setDayOeevalue(String dayOeevalue) {
		this.dayOeevalue = dayOeevalue;
	}
	
	public String getDayTeepvalue() {
		return dayTeepvalue;
	}
	
	public void setDayTeepvalue(String dayTeepvalue) {
		this.dayTeepvalue = dayTeepvalue;
	}
	
	public String getAvailability() {
		return availability;
	}
	
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
	public String getExpressivenessOf() {
		return expressivenessOf;
	}
	
	public void setExpressivenessOf(String expressivenessOf) {
		this.expressivenessOf = expressivenessOf;
	}
	
	public String getQuality() {
		return quality;
	}
	
	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getOeeTime() {
		return oeeTime;
	}
	
	public void setOeeTime(String oeeTime) {
		this.oeeTime = oeeTime;
	}
	
}
