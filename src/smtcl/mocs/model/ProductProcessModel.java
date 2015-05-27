package smtcl.mocs.model;

public class ProductProcessModel {
	private String id;
	private String processId;//工序id
	private String processNo;//工序号
	private String processName;//工序名称
	private String dispatchNo;//工单编号
	private String operator;//生产人员
	private String starttime;//上线时间
	private String finishtime;//下线时间
	private String status;//加工状态
	private String basicNo;
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getBasicNo() {
		return basicNo;
	}
	public void setBasicNo(String basicNo) {
		this.basicNo = basicNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessNo() {
		return processNo;
	}
	public void setProcessNo(String processNo) {
		this.processNo = processNo;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
