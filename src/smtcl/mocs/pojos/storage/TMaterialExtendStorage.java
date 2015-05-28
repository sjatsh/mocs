package smtcl.mocs.pojos.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * KC_101 物料库存
 */
@Entity
@Table(name = "t_material_extend_storage")
public class TMaterialExtendStorage implements java.io.Serializable {

	// Fields

	private Integer materialId;//物料ID
	private Integer isStock;//是否库存物料
	private Integer isVersionCtrl;//是否版本控制
	private Integer isStorage;//是否可存储
	private Integer isProcess;//是否可处理
	private Integer isRetain;//是否可保留
	private Integer isBatchTimeCtrl;//批次存储期限控制
	private Integer batchTime;//批次存储期限（天）
	private String batchTimeUnit;//存储期限单位
	private Integer isBatchCtrl;//批次控制
	private String batchPrefix;//批次控制起始前缀
	private String batchStartno;//批次控制起始编号
	private Integer isSeqCtrl;//序列控制
	private String seqPrefix;//序列起始前缀
	private String seqStartno;//序列起始编号
	private Integer isPositionCtrl;//库位控制
	private Integer isStockCtrl;//限制子库存
	private Integer isAxisCtrl;//限制库位
	private String stockNo;//子库存号（限制子库存下使用）
	private String postionNo;//库位号（限制库位下使用）
	private String defaultStockNo;//默认子库存
	private String defaultPositionNo;//默认库位号

	// Constructors

	/** default constructor */
	public TMaterialExtendStorage() {
	}

	/** full constructor */
	public TMaterialExtendStorage(Integer isStock, Integer isVersionCtrl,
			Integer isStorage, Integer isProcess, Integer isRetain,
			Integer isBatchTimeCtrl, Integer batchTime, String batchTimeUnit,
			Integer isBatchCtrl, String batchPrefix, String batchStartno,
			Integer isSeqCtrl, String seqPrefix, String seqStartno,
			Integer isPositionCtrl, Integer isStockCtrl, String stockNo,
			String postionNo, String defaultStockNo, String defaultPositionNo) {
		this.isStock = isStock;
		this.isVersionCtrl = isVersionCtrl;
		this.isStorage = isStorage;
		this.isProcess = isProcess;
		this.isRetain = isRetain;
		this.isBatchTimeCtrl = isBatchTimeCtrl;
		this.batchTime = batchTime;
		this.batchTimeUnit = batchTimeUnit;
		this.isBatchCtrl = isBatchCtrl;
		this.batchPrefix = batchPrefix;
		this.batchStartno = batchStartno;
		this.isSeqCtrl = isSeqCtrl;
		this.seqPrefix = seqPrefix;
		this.seqStartno = seqStartno;
		this.isPositionCtrl = isPositionCtrl;
		this.isStockCtrl = isStockCtrl;
		this.stockNo = stockNo;
		this.postionNo = postionNo;
		this.defaultStockNo = defaultStockNo;
		this.defaultPositionNo = defaultPositionNo;
	}

	// Property accessors
	@Id
	@Column(name = "material_id", unique = true, nullable = false)
	public Integer getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@Column(name = "isStock")
	public Integer getIsStock() {
		return this.isStock;
	}

	public void setIsStock(Integer isStock) {
		this.isStock = isStock;
	}

	@Column(name = "isVersionCtrl")
	public Integer getIsVersionCtrl() {
		return this.isVersionCtrl;
	}

	public void setIsVersionCtrl(Integer isVersionCtrl) {
		this.isVersionCtrl = isVersionCtrl;
	}

	@Column(name = "isStorage")
	public Integer getIsStorage() {
		return this.isStorage;
	}

	public void setIsStorage(Integer isStorage) {
		this.isStorage = isStorage;
	}

	@Column(name = "isProcess")
	public Integer getIsProcess() {
		return this.isProcess;
	}

	public void setIsProcess(Integer isProcess) {
		this.isProcess = isProcess;
	}

	@Column(name = "isRetain")
	public Integer getIsRetain() {
		return this.isRetain;
	}

	public void setIsRetain(Integer isRetain) {
		this.isRetain = isRetain;
	}

	@Column(name = "isBatchTimeCtrl")
	public Integer getIsBatchTimeCtrl() {
		return this.isBatchTimeCtrl;
	}

	public void setIsBatchTimeCtrl(Integer isBatchTimeCtrl) {
		this.isBatchTimeCtrl = isBatchTimeCtrl;
	}

	@Column(name = "batchTime")
	public Integer getBatchTime() {
		return this.batchTime;
	}

	public void setBatchTime(Integer batchTime) {
		this.batchTime = batchTime;
	}

	@Column(name = "batchTimeUnit", length = 20)
	public String getBatchTimeUnit() {
		return this.batchTimeUnit;
	}

	public void setBatchTimeUnit(String batchTimeUnit) {
		this.batchTimeUnit = batchTimeUnit;
	}

	@Column(name = "isBatchCtrl")
	public Integer getIsBatchCtrl() {
		return this.isBatchCtrl;
	}

	public void setIsBatchCtrl(Integer isBatchCtrl) {
		this.isBatchCtrl = isBatchCtrl;
	}

	@Column(name = "batch_prefix", length = 10)
	public String getBatchPrefix() {
		return this.batchPrefix;
	}

	public void setBatchPrefix(String batchPrefix) {
		this.batchPrefix = batchPrefix;
	}

	@Column(name = "batch_startno", length = 20)
	public String getBatchStartno() {
		return this.batchStartno;
	}

	public void setBatchStartno(String batchStartno) {
		this.batchStartno = batchStartno;
	}

	@Column(name = "isSeqCtrl")
	public Integer getIsSeqCtrl() {
		return this.isSeqCtrl;
	}

	public void setIsSeqCtrl(Integer isSeqCtrl) {
		this.isSeqCtrl = isSeqCtrl;
	}

	@Column(name = "seq_prefix", length = 10)
	public String getSeqPrefix() {
		return this.seqPrefix;
	}

	public void setSeqPrefix(String seqPrefix) {
		this.seqPrefix = seqPrefix;
	}

	@Column(name = "seq_startno", length = 20)
	public String getSeqStartno() {
		return this.seqStartno;
	}

	public void setSeqStartno(String seqStartno) {
		this.seqStartno = seqStartno;
	}

	@Column(name = "isPositionCtrl")
	public Integer getIsPositionCtrl() {
		return this.isPositionCtrl;
	}

	public void setIsPositionCtrl(Integer isPositionCtrl) {
		this.isPositionCtrl = isPositionCtrl;
	}

	@Column(name = "isStockCtrl")
	public Integer getIsStockCtrl() {
		return this.isStockCtrl;
	}

	public void setIsStockCtrl(Integer isStockCtrl) {
		this.isStockCtrl = isStockCtrl;
	}

	@Column(name = "stockNo", length = 20)
	public String getStockNo() {
		return this.stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	@Column(name = "postionNo", length = 20)
	public String getPostionNo() {
		return this.postionNo;
	}

	public void setPostionNo(String postionNo) {
		this.postionNo = postionNo;
	}

	@Column(name = "default_stockNo", length = 20)
	public String getDefaultStockNo() {
		return this.defaultStockNo;
	}

	public void setDefaultStockNo(String defaultStockNo) {
		this.defaultStockNo = defaultStockNo;
	}

	@Column(name = "default_positionNo", length = 20)
	public String getDefaultPositionNo() {
		return this.defaultPositionNo;
	}

	public void setDefaultPositionNo(String defaultPositionNo) {
		this.defaultPositionNo = defaultPositionNo;
	}

	@Column(name = "isAxisCtrl")
	public Integer getIsAxisCtrl() {
		return isAxisCtrl;
	}

	public void setIsAxisCtrl(Integer isAxisCtrl) {
		this.isAxisCtrl = isAxisCtrl;
	}

}