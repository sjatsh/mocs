package smtcl.mocs.model;

public class ProductProcessModel {
	private String id;
	private String processId;//����id
	private String processNo;//�����
	private String processName;//��������
	private String dispatchNo;//�������
	private String operator;//������Ա
	private String starttime;//����ʱ��
	private String finishtime;//����ʱ��
	private String status;//�ӹ�״̬
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
