package smtcl.mocs.model;

public class ProcessQualityModel {
	private String id;
	private String processName;//��������
	private String dispatchNo;//�������
	private String checkType;//��������
	private String operator;//�����Ա
	private String checkParam;//������
	private String standardValue;//��׼ֵ
	private String maxValue;//����ֵ
	private String realValue;//ʵ��ֵ
	private String maxtolerance;//�ʲ�����
	private String mintolerance;//�ʲ�����
	private String checkTime;//���鹤ʱ
	private String result;//������
	
	
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
