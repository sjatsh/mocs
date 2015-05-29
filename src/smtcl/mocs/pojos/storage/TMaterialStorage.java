package smtcl.mocs.pojos.storage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import smtcl.mocs.pojos.job.TMaterailTypeInfo;

/**
 * KC_202  库存表
 */
@Entity
@Table(name = "t_material_storage")
public class TMaterialStorage implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private TStorageInfo TStorageInfo;//子库存ID
	private TMaterailTypeInfo TMaterailTypeInfo;//物料ID
	private String versionNo;//物料版本号
	private Integer positonId;//货位ID
	private String batchNo;//批次编号
	private String seqNo;//序列号
	private Double availableNum;//现有量
	private Double retainNum;//保留量
	private String unitName;//单位
	private Date batchDate;//批次到期日
	private Double moneyNum;//金额
	private String storageFrom;//来源
	private Date processDate;//日期

	// Constructors

	/** default constructor */
	public TMaterialStorage() {
	}

	/** full constructor */
	public TMaterialStorage(TStorageInfo TStorageInfo,
			TMaterailTypeInfo TMaterailTypeInfo, String versionNo,
			Integer positonId, String batchNo, String seqNo,
			Double availableNum, Double retainNum, String unitName,
			Date batchDate, Double moneyNum, String storageFrom,
			Date processDate) {
		this.TStorageInfo = TStorageInfo;
		this.TMaterailTypeInfo = TMaterailTypeInfo;
		this.versionNo = versionNo;
		this.positonId = positonId;
		this.batchNo = batchNo;
		this.seqNo = seqNo;
		this.availableNum = availableNum;
		this.retainNum = retainNum;
		this.unitName = unitName;
		this.batchDate = batchDate;
		this.moneyNum = moneyNum;
		this.storageFrom = storageFrom;
		this.processDate = processDate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_id")
	public TStorageInfo getTStorageInfo() {
		return this.TStorageInfo;
	}

	public void setTStorageInfo(TStorageInfo TStorageInfo) {
		this.TStorageInfo = TStorageInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_id")
	public TMaterailTypeInfo getTMaterailTypeInfo() {
		return this.TMaterailTypeInfo;
	}

	public void setTMaterailTypeInfo(TMaterailTypeInfo TMaterailTypeInfo) {
		this.TMaterailTypeInfo = TMaterailTypeInfo;
	}

	@Column(name = "version_no", length = 10)
	public String getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	@Column(name = "positon_id")
	public Integer getPositonId() {
		return this.positonId;
	}

	public void setPositonId(Integer positonId) {
		this.positonId = positonId;
	}

	@Column(name = "batchNo", length = 50)
	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "seqNo", length = 30)
	public String getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	@Column(name = "available_num", precision = 5)
	public Double getAvailableNum() {
		return this.availableNum;
	}

	public void setAvailableNum(Double availableNum) {
		this.availableNum = availableNum;
	}

	@Column(name = "retain_num", precision = 5)
	public Double getRetainNum() {
		return this.retainNum;
	}

	public void setRetainNum(Double retainNum) {
		this.retainNum = retainNum;
	}

	@Column(name = "unitName", length = 20)
	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "batchDate", length = 0)
	public Date getBatchDate() {
		return this.batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	@Column(name = "money_num", precision = 5)
	public Double getMoneyNum() {
		return this.moneyNum;
	}

	public void setMoneyNum(Double moneyNum) {
		this.moneyNum = moneyNum;
	}

	@Column(name = "storage_from", length = 30)
	public String getStorageFrom() {
		return this.storageFrom;
	}

	public void setStorageFrom(String storageFrom) {
		this.storageFrom = storageFrom;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "processDate", length = 0)
	public Date getProcessDate() {
		return this.processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

}