package smtcl.mocs.model;

import java.io.Serializable;

/**
 * 工单列表基础类
 * @author songkaiang
 *
 */
public class JobListModel implements Serializable{
	private String id;//工单Id号
	private String status;
	private String taskNo;//任务号
	private String process;//工序
	private String dispatchNo;//工单号
	private String planNumber;//计划数量
	private String onlineNumber;//上线数量
	private String dispatchStatus;//工单状态
	private String equipment;//设备
	private String startTime;//计划开始时间
	private String endTime;//计划结束时间
	private String finishNum;//完成数量
	private String partName;//零件名称
	private String no;
	private String goodQuantity;//合格数量
	private String wisScrapNum;//报废数量
	
	public JobListModel(String id,String status,String taskNo, String process, String dispatchNo,
			String planNumber, String onlineNumber, String dispatchStatus,
			String equipment, String startTime, String endTime,
			String finishNum,String partName,String no,String goodQuantity,String wisScrapNum) {
		
		this.id = id;
		this.status = status;
		this.taskNo = taskNo;// 任务号
		this.process = process;// 工序
		this.dispatchNo = dispatchNo;// 工单号
		this.planNumber = planNumber;// 计划数量
		this.onlineNumber = onlineNumber;// 上线数量
		this.dispatchStatus = dispatchStatus;// 工单状态
		this.equipment = equipment;// 设备
		this.startTime = startTime;// 计划开始时间
		this.endTime = endTime;// 计划结束时间
//		this.dispatchEndTime = dispatchEndTime;// 工单停止时间
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
