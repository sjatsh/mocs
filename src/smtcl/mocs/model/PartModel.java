package smtcl.mocs.model;

import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;

/**
 * 零件工序向导实体类
 * @创建时间 2013-07-17
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public class PartModel {

	/**
	 * 零件类型实体类
	 */
	private TPartTypeInfo tPartTypeInfo;
	/**
	 * 工艺方案实体类
	 */
	private TProcessplanInfo tProcessplanInfo;
	
	private String nodeid;
	/****************************基本信息***********************************/
	/**
	 * 工序id
	 */
	private String id="";
	/**
	 * 工序编号
	 */
	private String no="";
	/**
	 * 工序名称
	 */
	private String name="";
	/**
	 * 工序类型
	 */
	private String processType;
	/**
	 * 前序编号
	 */
	private String onlineProcessID="";
	/**
	 * 工序序号
	 */
	private String processOrder;
	/**
	 * 是否尾序
	 */
	private String offlineProcess="";
	/**
	 * 工序连续
	 */
	private String processDuration="";
	/**
	 * 是否必检
	 */
	private String ischeck;
	/**
	 * 检验类型
	 */
	private String checktype;
	/**
	 * 指导文件
	 */
	private String file="";
	/**
	 * 工序描述
	 */
	private String description="";
	
	
	/***************************机台************************************/
	/**
	 *加工设备类型
	 */
	private String equipmentTypeID="";
	/**
	 * 具体设备
	 */
	private String equinfo;
	/**
	 * 加工程序
	 */
	private String programID="";
	/**
	 * 加工时间
	 */
	private String processingTime="0";
	/**
	 * 节拍时间
	 */
	private String theoryWorktime="0";
	/**
	 * 预计产能（个/小时）
	 */
	private String capacity="0";
	/***************************资源************************************/
	/**
	 * 夹具id
	 */
	private String jjId;
	/**
	 * 夹具编号
	 */
	private String jjNo;
	/**
	 * 夹具名称
	 */
	private String jjName;
	/**
	 * 装夹时间
	 */
	private String installTime;
	/**
	 * 卸载时间
	 */
	private String uninstallTime;
	
	/***************************成本************************************/
	/**
	 * 主料成本
	 */
	private String mainMaterialCost;
	/**
	 * 人工成本
	 */
	private String peopleCost;
	/**
	 * 辅料成本
	 */
	private String subsidiaryMaterialCost;
	/**
	 * 能源成本
	 */
	private String energyCost;
	/**
	 * 设备折旧
	 */
	private String deviceCost;
	/**
	 * 资源消耗
	 */
	private String resourceCost;
	public TPartTypeInfo gettPartTypeInfo() {
		return tPartTypeInfo;
	}
	public void settPartTypeInfo(TPartTypeInfo tPartTypeInfo) {
		this.tPartTypeInfo = tPartTypeInfo;
	}
	public TProcessplanInfo gettProcessplanInfo() {
		return tProcessplanInfo;
	}
	public void settProcessplanInfo(TProcessplanInfo tProcessplanInfo) {
		this.tProcessplanInfo = tProcessplanInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOnlineProcessID() {
		return onlineProcessID;
	}
	public void setOnlineProcessID(String onlineProcessID) {
		this.onlineProcessID = onlineProcessID;
	}
	public String getOfflineProcess() {
		return offlineProcess;
	}
	public void setOfflineProcess(String offlineProcess) {
		this.offlineProcess = offlineProcess;
	}
	public String getProcessDuration() {
		return processDuration;
	}
	public void setProcessDuration(String processDuration) {
		this.processDuration = processDuration;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	public String getChecktype() {
		return checktype;
	}
	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEquipmentTypeID() {
		return equipmentTypeID;
	}
	public void setEquipmentTypeID(String equipmentTypeID) {
		this.equipmentTypeID = equipmentTypeID;
	}
	public String getProgramID() {
		return programID;
	}
	public void setProgramID(String programID) {
		this.programID = programID;
	}
	public String getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(String processingTime) {
		this.processingTime = processingTime;
	}
	public String getTheoryWorktime() {
		return theoryWorktime;
	}
	public void setTheoryWorktime(String theoryWorktime) {
		this.theoryWorktime = theoryWorktime;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getJjId() {
		return jjId;
	}
	public void setJjId(String jjId) {
		this.jjId = jjId;
	}
	public String getJjNo() {
		return jjNo;
	}
	public void setJjNo(String jjNo) {
		this.jjNo = jjNo;
	}
	public String getJjName() {
		return jjName;
	}
	public void setJjName(String jjName) {
		this.jjName = jjName;
	}
	public String getInstallTime() {
		return installTime;
	}
	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}
	public String getUninstallTime() {
		return uninstallTime;
	}
	public void setUninstallTime(String uninstallTime) {
		this.uninstallTime = uninstallTime;
	}
	public String getMainMaterialCost() {
		return mainMaterialCost;
	}
	public void setMainMaterialCost(String mainMaterialCost) {
		this.mainMaterialCost = mainMaterialCost;
	}
	public String getPeopleCost() {
		return peopleCost;
	}
	public void setPeopleCost(String peopleCost) {
		this.peopleCost = peopleCost;
	}
	public String getSubsidiaryMaterialCost() {
		return subsidiaryMaterialCost;
	}
	public void setSubsidiaryMaterialCost(String subsidiaryMaterialCost) {
		this.subsidiaryMaterialCost = subsidiaryMaterialCost;
	}
	public String getEnergyCost() {
		return energyCost;
	}
	public void setEnergyCost(String energyCost) {
		this.energyCost = energyCost;
	}
	public String getDeviceCost() {
		return deviceCost;
	}
	public void setDeviceCost(String deviceCost) {
		this.deviceCost = deviceCost;
	}
	public String getResourceCost() {
		return resourceCost;
	}
	public void setResourceCost(String resourceCost) {
		this.resourceCost = resourceCost;
	}
	public String getEquinfo() {
		return equinfo;
	}
	public void setEquinfo(String equinfo) {
		this.equinfo = equinfo;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getProcessOrder() {
		return processOrder;
	}
	public void setProcessOrder(String processOrder) {
		this.processOrder = processOrder;
	}
	
}
