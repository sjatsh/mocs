package smtcl.mocs.pojos.storage;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * KC-201 子库房表
 */
@Entity
@Table(name = "t_storage_info")
public class TStorageInfo implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private String storageNo;//库房编号
	private String storageName;//库房说明
	private String storageStatus;//库房状态
	private Date invalidDate;//失效日期
	private String isAvailable;//可净得
	private String planType;//计划方式
	private String positionType;//货位控制
	private String address;//库房位置
	private String storageMan;//主要保管员
	private Date createDate;//库房建立时间
	private Double preProcessTime;//预加工提前期
	private Double inProcessTime;//加工中提前期
	private Double sufProcessTime;//后加工提前期
	private Set<TTransaction> TTransactions = new HashSet<TTransaction>(0);
	private Set<TMaterialStorage> TMaterialStorages = new HashSet<TMaterialStorage>(
			0);
	private Set<RStoragePosition> RStoragePositions = new HashSet<RStoragePosition>(
			0);
    private String nodeId;
	// Constructors

	/** default constructor */
	public TStorageInfo() {
	}

	/** full constructor */
	public TStorageInfo(String storageNo, String storageName,
			String storageStatus, Date invalidDate, String isAvailable,
			String planType, String positionType, String address,
			String storageMan, Date createDate, Double preProcessTime,
			Double inProcessTime, Double sufProcessTime,
			Set<TTransaction> TTransactions,
			Set<TMaterialStorage> TMaterialStorages,
			Set<RStoragePosition> RStoragePositions,String nodeId) {
		this.storageNo = storageNo;
		this.storageName = storageName;
		this.storageStatus = storageStatus;
		this.invalidDate = invalidDate;
		this.isAvailable = isAvailable;
		this.planType = planType;
		this.positionType = positionType;
		this.address = address;
		this.storageMan = storageMan;
		this.createDate = createDate;
		this.preProcessTime = preProcessTime;
		this.inProcessTime = inProcessTime;
		this.sufProcessTime = sufProcessTime;
		this.TTransactions = TTransactions;
		this.TMaterialStorages = TMaterialStorages;
		this.RStoragePositions = RStoragePositions;
		this.nodeId = nodeId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "storage_no", length = 30)
	public String getStorageNo() {
		return this.storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}

	@Column(name = "storage_name", length = 50)
	public String getStorageName() {
		return this.storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	@Column(name = "storage_status", length = 10)
	public String getStorageStatus() {
		return this.storageStatus;
	}

	public void setStorageStatus(String storageStatus) {
		this.storageStatus = storageStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invalid_date", length = 0)
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "isAvailable", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "planType", length = 1)
	public String getPlanType() {
		return this.planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	@Column(name = "positionType", length = 1)
	public String getPositionType() {
		return this.positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	@Column(name = "address", length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "storage_man", length = 10)
	public String getStorageMan() {
		return this.storageMan;
	}

	public void setStorageMan(String storageMan) {
		this.storageMan = storageMan;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "pre_process_time", precision = 5)
	public Double getPreProcessTime() {
		return this.preProcessTime;
	}

	public void setPreProcessTime(Double preProcessTime) {
		this.preProcessTime = preProcessTime;
	}

	@Column(name = "in_process_time", precision = 5)
	public Double getInProcessTime() {
		return this.inProcessTime;
	}

	public void setInProcessTime(Double inProcessTime) {
		this.inProcessTime = inProcessTime;
	}

	@Column(name = "suf_process_time", precision = 5)
	public Double getSufProcessTime() {
		return this.sufProcessTime;
	}

	public void setSufProcessTime(Double sufProcessTime) {
		this.sufProcessTime = sufProcessTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TStorageInfo")
	public Set<TTransaction> getTTransactions() {
		return this.TTransactions;
	}

	public void setTTransactions(Set<TTransaction> TTransactions) {
		this.TTransactions = TTransactions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TStorageInfo")
	public Set<TMaterialStorage> getTMaterialStorages() {
		return this.TMaterialStorages;
	}

	public void setTMaterialStorages(Set<TMaterialStorage> TMaterialStorages) {
		this.TMaterialStorages = TMaterialStorages;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TStorageInfo")
	public Set<RStoragePosition> getRStoragePositions() {
		return this.RStoragePositions;
	}

	public void setRStoragePositions(Set<RStoragePosition> RStoragePositions) {
		this.RStoragePositions = RStoragePositions;
	}
    
	@Column(name = "nodeId", length = 50)
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	

}