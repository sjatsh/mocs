package smtcl.mocs.model;

public class ProcessQualityModel {
	private String id;
	private String processName;//工序名称
	private String dispatchNo;//工单编号
	private String checkType;//检验类型
	private String operator;//检测人员
	private String checkParam;//检测参数
	private String standardValue;//标准值
	private String maxValue;//上线值
	private String realValue;//实际值
	private String maxtolerance;//允差上线
	private String mintolerance;//允差下线
	private String checkTime;//检验工时
	private String result;//检验结果
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getDispatchNo() {
		return dispatchNo;
	}
	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getCheckParam() {
		return checkParam;
	}
	public void setCheckParam(String checkParam) {
		this.checkParam = checkParam;
	}
	public String getStandardValue() {
		return standardValue;
	}
	public void setStandardValue(String standardValue) {
		this.standardValue = standardValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getRealValue() {
		return realValue;
	}
	public void setRealValue(String realValue) {
		this.realValue = realValue;
	}
	public String getMaxtolerance() {
		return maxtolerance;
	}
	public void setMaxtolerance(String maxtolerance) {
		this.maxtolerance = maxtolerance;
	}
	public String getMintolerance() {
		return mintolerance;
	}
	public void setMintolerance(String mintolerance) {
		this.mintolerance = mintolerance;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
