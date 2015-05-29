package smtcl.mocs.model;

import java.util.Date;

/**
 * 库房信息辅助类
 * 作者：FW
 * 时间：2014-09-02
 */
public class TStorageInfoModel implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private String storageNo;//库房编号
	private String storageName;//库房说明
	private String storageStatus;//库房状态
	private String invalidDate;//失效日期
	private String isAvailable;//可净得
	private String planType;//计划方式
	private String positionType;//货位控制
	private String address;//库房位置
	private String storageMan;//主要保管员
	private Date createDate;//库房建立时间
	private Double preProcessTime;//预加工提前期
	private Double inProcessTime;//加工中提前期
	private Double sufProcessTime;//后加工提前期
    private String nodeId;//节点
	public TStorageInfoModel() {
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStorageNo() {
		return this.storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}
	
	public String getStorageName() {
		return this.storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	
	public String getStorageStatus() {
		return this.storageStatus;
	}

	public void setStorageStatus(String storageStatus) {
		this.storageStatus = storageStatus;
	}
	
	public String getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public String getPlanType() {
		return this.planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	public String getPositionType() {
		return this.positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStorageMan() {
		return this.storageMan;
	}

	public void setStorageMan(String storageMan) {
		this.storageMan = storageMan;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Double getPreProcessTime() {
		return this.preProcessTime;
	}

	public void setPreProcessTime(Double preProcessTime) {
		this.preProcessTime = preProcessTime;
	}
	
	public Double getInProcessTime() {
		return this.inProcessTime;
	}

	public void setInProcessTime(Double inProcessTime) {
		this.inProcessTime = inProcessTime;
	}
	
	public Double getSufProcessTime() {
		return this.sufProcessTime;
	}

	public void setSufProcessTime(Double sufProcessTime) {
		this.sufProcessTime = sufProcessTime;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
}