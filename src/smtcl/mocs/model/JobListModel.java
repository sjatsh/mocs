package smtcl.mocs.model;

import java.io.Serializable;

/**
 * �����б������
 * @author songkaiang
 *
 */
public class JobListModel implements Serializable{
	private String id;//����Id��
	private String status;
	private String taskNo;//�����
	private String process;//����
	private String dispatchNo;//������
	private String planNumber;//�ƻ�����
	private String onlineNumber;//��������
	private String dispatchStatus;//����״̬
	private String equipment;//�豸
	private String startTime;//�ƻ���ʼʱ��
	private String endTime;//�ƻ�����ʱ��
	private String finishNum;//�������
	private String partName;//�������
	private String no;
	private String goodQuantity;//�ϸ�����
	private String wisScrapNum;//��������
	
	public JobListModel(String id,String status,String taskNo, String process, String dispatchNo,
			String planNumber, String onlineNumber, String dispatchStatus,
			String equipment, String startTime, String endTime,
			String finishNum,String partName,String no,String goodQuantity,String wisScrapNum) {
		
		this.id = id;
		this.status = status;
		this.taskNo = taskNo;// �����
		this.process = process;// ����
		this.dispatchNo = dispatchNo;// ������
		this.planNumber = planNumber;// �ƻ�����
		this.onlineNumber = onlineNumber;// ��������
		this.dispatchStatus = dispatchStatus;// ����״̬
		this.equipment = equipment;// �豸
		this.startTime = startTime;// �ƻ���ʼʱ��
		this.endTime = endTime;// �ƻ�����ʱ��
//		this.dispatchEndTime = dispatchEndTime;// ����ֹͣʱ��
		this.finishNum = finishNum;
		this.partName = partName;
		this.no = no;
		this.goodQuantity = goodQuantity;
		this.wisScrapNum = wisScrapNum;
	}
	
	public String getGoodQuantity() {
		return goodQuantity;
	}

	public void setGoodQuantity(String goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getDispatchNo() {
		return dispatchNo;
	}
	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}
	public String getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}
	public String getOnlineNumber() {
		return onlineNumber;
	}
	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
	}
	public String getDispatchStatus() {
		return dispatchStatus;
	}
	public void setDispatchStatus(String dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(String finishNum) {
		this.finishNum = finishNum;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getWisScrapNum() {
		return wisScrapNum;
	}

	public void setWisScrapNum(String wisScrapNum) {
		this.wisScrapNum = wisScrapNum;
	}
	
}
