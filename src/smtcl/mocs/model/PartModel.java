package smtcl.mocs.model;

import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;

/**
 * ���������ʵ����
 * @����ʱ�� 2013-07-17
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public class PartModel {

	/**
	 * �������ʵ����
	 */
	private TPartTypeInfo tPartTypeInfo;
	/**
	 * ���շ���ʵ����
	 */
	private TProcessplanInfo tProcessplanInfo;
	
	private String nodeid;
	/****************************������Ϣ***********************************/
	/**
	 * ����id
	 */
	private String id="";
	/**
	 * ������
	 */
	private String no="";
	/**
	 * ��������
	 */
	private String name="";
	/**
	 * ��������
	 */
	private String processType;
	/**
	 * ǰ����
	 */
	private String onlineProcessID="";
	/**
	 * �������
	 */
	private String processOrder;
	/**
	 * �Ƿ�β��
	 */
	private String offlineProcess="";
	/**
	 * ��������
	 */
	private String processDuration="";
	/**
	 * �Ƿ�ؼ�
	 */
	private String ischeck;
	/**
	 * ��������
	 */
	private String checktype;
	/**
	 * ָ���ļ�
	 */
	private String file="";
	/**
	 * ��������
	 */
	private String description="";
	
	
	/***************************��̨************************************/
	/**
	 *�ӹ��豸����
	 */
	private String equipmentTypeID="";
	/**
	 * �����豸
	 */
	private String equinfo;
	/**
	 * �ӹ�����
	 */
	private String programID="";
	/**
	 * �ӹ�ʱ��
	 */
	private String processingTime="0";
	/**
	 * ����ʱ��
	 */
	private String theoryWorktime="0";
	/**
	 * Ԥ�Ʋ��ܣ���/Сʱ��
	 */
	private String capacity="0";
	/***************************��Դ************************************/
	/**
	 * �о�id
	 */
	private String jjId;
	/**
	 * �о߱��
	 */
	private String jjNo;
	/**
	 * �о�����
	 */
	private String jjName;
	/**
	 * װ��ʱ��
	 */
	private String installTime;
	/**
	 * ж��ʱ��
	 */
	private String uninstallTime;
	
	/***************************�ɱ�************************************/
	/**
	 * ���ϳɱ�
	 */
	private String mainMaterialCost;
	/**
	 * �˹��ɱ�
	 */
	private String peopleCost;
	/**
	 * ���ϳɱ�
	 */
	private String subsidiaryMaterialCost;
	/**
	 * ��Դ�ɱ�
	 */
	private String energyCost;
	/**
	 * �豸�۾�
	 */
	private String deviceCost;
	/**
	 * ��Դ����
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
