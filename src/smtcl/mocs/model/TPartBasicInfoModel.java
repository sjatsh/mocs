package smtcl.mocs.model;


public class TPartBasicInfoModel {
	private Long id;
	private String no;
	private String name;
	private String onlineDate;
	private String offlineDate;
	private String partTypeId;
	private String partName;
	private String batchNo;
	private String currentProcessID;
	private String processDate;
	private String reworkCount;
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getOfflineDate() {
		return offlineDate;
	}
	public void setOfflineDate(String offlineDate) {
		this.offlineDate = offlineDate;
	}
	public String getPartTypeId() {
		return partTypeId;
	}
	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCurrentProcessID() {
		return currentProcessID;
	}
	public void setCurrentProcessID(String currentProcessID) {
		this.currentProcessID = currentProcessID;
	}
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	public String getReworkCount() {
		return reworkCount;
	}
	public void setReworkCount(String reworkCount) {
		this.reworkCount = reworkCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
