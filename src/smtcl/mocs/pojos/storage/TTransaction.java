package smtcl.mocs.pojos.storage;

import java.sql.Timestamp;
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
 * KC_302 事务处理表
 */
@Entity
@Table(name = "t_transaction")
public class TTransaction implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private TTransactionRelation TTransactionRelation;
	private TStorageInfo TStorageInfo;
	private TMaterailTypeInfo TMaterailTypeInfo;
	private String transNo;//事务编号
	private Integer positionId;//货位ID
	private String versionNo;//版本
	private Integer transStorageId;//转移子库存
	private Integer transPositionId;//转移货位
	private Double processNum;//处理数量
	private String unitName;//单位
	private String orderNo;//订单号
	private String jobdispatchNo;//工单号
	private String batchNo;//批次号
	private String seqNo;//序列号
	private Date transDate;//事务处理日期
	private String transStatus;//事务处理标识

	// Constructors

	/** default constructor */
	public TTransaction() {
	}

	/** full constructor */
	public TTransaction(TTransactionRelation TTransactionRelation,
			TStorageInfo TStorageInfo, TMaterailTypeInfo TMaterailTypeInfo,
			String transNo, Integer positionId, String versionNo,
			Integer transStorageId, Integer transPositionId, Double processNum,
			String unitName, String orderNo, String jobdispatchNo,
			String batchNo, String seqNo, Date transDate,
			String transStatus) {
		this.TTransactionRelation = TTransactionRelation;
		this.TStorageInfo = TStorageInfo;
		this.TMaterailTypeInfo = TMaterailTypeInfo;
		this.transNo = transNo;
		this.positionId = positionId;
		this.versionNo = versionNo;
		this.transStorageId = transStorageId;
		this.transPositionId = transPositionId;
		this.processNum = processNum;
		this.unitName = unitName;
		this.orderNo = orderNo;
		this.jobdispatchNo = jobdispatchNo;
		this.batchNo = batchNo;
		this.seqNo = seqNo;
		this.transDate = transDate;
		this.transStatus = transStatus;
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
	@JoinColumn(name = "trans_relation_id")
	public TTransactionRelation getTTransactionRelation() {
		return this.TTransactionRelation;
	}

	public void setTTransactionRelation(
			TTransactionRelation TTransactionRelation) {
		this.TTransactionRelation = TTransactionRelation;
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

	@Column(name = "transNo", length = 30)
	public String getTransNo() {
		return this.transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	@Column(name = "position_id")
	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	@Column(name = "version_no", length = 10)
	public String getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	@Column(name = "trans_storage_id")
	public Integer getTransStorageId() {
		return this.transStorageId;
	}

	public void setTransStorageId(Integer transStorageId) {
		this.transStorageId = transStorageId;
	}

	@Column(name = "trans_position_id")
	public Integer getTransPositionId() {
		return this.transPositionId;
	}

	public void setTransPositionId(Integer transPositionId) {
		this.transPositionId = transPositionId;
	}

	@Column(name = "processNum", precision = 5)
	public Double getProcessNum() {
		return this.processNum;
	}

	public void setProcessNum(Double processNum) {
		this.processNum = processNum;
	}

	@Column(name = "unit_name", length = 20)
	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name = "orderNo", length = 30)
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "jobdispatchNo", length = 50)
	public String getJobdispatchNo() {
		return this.jobdispatchNo;
	}

	public void setJobdispatchNo(String jobdispatchNo) {
		this.jobdispatchNo = jobdispatchNo;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transDate", length = 0)
	public Date getTransDate() {
		return this.transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	@Column(name = "transStatus", length = 1)
	public String getTransStatus() {
		return this.transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

}