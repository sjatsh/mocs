package smtcl.mocs.model;

/**
 * �豸״̬��������ʵ����
 * @����ʱ�� 2012-12-03
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public class DeviceInfoModel {
	/**
	 * ���id
	 */
	private int id=0;
	
	/**
	 *�ڵ�id
	 */
	private String nodeId="";	
	
	/**
	 *��������
	 */
	private String equName="";   
	
	/**
	 *����
	 */
	private String equType="";  
	
	/**
	 *�����ͺ�
	 */
	private String manufactureType=""; 
	
	/**
	 *��ǰ����
	 */
	private String programm=""; 
	
	/**
	 *����ϵͳ
	 */
	private String ncprogramm="";  
	
	/**
	 *������Ա
	 */
	private String operator="";
	
	/**
	 *����ʱ��
	 */
	private String runningTime="";
	
	/**
	 *�ػ�ʱ��
	 */
	private String stopTime="";
	
	/**
	 *�ӹ�ʱ��
	 */
	private String processTime="";
	
	/**
	 *׼��ʱ�� 
	 */
	private String prepareTime="";
	
	/**
	 *���д���ʱ��
	 */
	private String idleTime="";
	
	/**
	 *����ʱ��
	 */
	private String breakDownTime="";
	
	/**
	 *������ʱ��
	 */
	private String dryRunningTime="";
	
	/**
	 *����ʱ��
	 */
	private String toolChangeTime="";
	
	/**
	 *����ʱ��
	 */
	private String cuttingTime;
	
	/**
	 *�ֶ�����ʱ��
	 */
	private String manualRunningTime;
	
	/**
	 *������ʱ��
	 */
	private String materialTime;
	
	/**
	 *����ʱ��
	 */
	private String otherTime;
	
	/**
	 *�ܹ�������
	 */
	private String totalProcessPartsCount;
	
	/**
	 *�깤������
	 */
	private String anualProcessPartsCount;
	
	/**
	 *�¹�������
	 */
	private String monthProcessPartsCount;
	
	/**
	 *�չ�������
	 */
	private String dayProcessPartsCount;
	
	/**
	 *�豸���к�
	 */
	private String equSerialNo; 
	
	/**
	 *�豸״̬
	 */
	private String status;
	
	/**
	 *����id
	 */
	private String symgEquId;
	
	/**
	 *״̬ʱ��
	 */
	private String updateTime;
	
	/**
	 *��ǰ���ߺ�
	 */
	private String toolNo;   
	
	/**
	 *���ڼӹ��������Ϣ
	 */
	private String part;  
	
	/**
	 *��ǰ������ʱ
	 */
	private String partTimeCount;
	
	/**
	 *��ǰ��������
	 */
	private String partCount;
	
	/**
	 *��ǰ�����ٶ�
	 */
	private String feedSpeed;  
	
	/**
	 *��ǰ��������
	 */
	private String feedrate;
	
	/**
	 *��ǰ����ת��
	 */
	private String axisspeed;  
	
	/**
	 *��ǰ���ᱶ��
	 */
	private String axisspeedRate;
	
	/**
	 *����������X
	 */
	private String xfeed;  
	
	/**
	 *����������Y
	 */
	private String yfeed; 
	
	/**
	 *����������Z
	 */
	private String zfeed; 
	
	/**
	 *��������
	 */
	private String cuttingpower;
	
	/**
	 *������
	 */
	private String availability;
	
	/**
	 *������
	 */
	private String expressivenessOf;
	
	/**
	 *����
	 */
	private String quality;
	
	/**
	 *����OEE
	 */
	private String dayOeevalue;
	
	/**
	 *����TEEP
	 */
	private String dayTeepvalue; 
	
	/**
	 *OEE�����¼�ʱ��
	 */
	private String oeeTime; 
	
	/**
	 *ͼƬURL
	 */
	private String path;
	/**
	 * ʵ�ʿ���ʱ��
	 */
	private String worktimeFact;
	/**
	 * �ϸ񹤼���
	 */
	private String qualifiedPartCount;
	/**
	 * �ӹ�������
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
