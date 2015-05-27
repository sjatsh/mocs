package smtcl.mocs.pojos.device;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TNodeCostResource entity. 
 *  @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_NodeCostResource")
public class TNodeCostResource implements java.io.Serializable {

	// Fields

	private Long id;  //ID
	private TNodes TNodes;
	private Date beginTime;//开始时间
	private Date endTime;//结束时间
	private Double usedAmount;//使用数量
	private String uresoType;//资源类型
	private String uresoNo;//物料编号
	private String uresoNorms;//规格
	private String uresoProvider;//供应厂家
	private String uresoSerialNo;//厂家物料编号
	private String uresoUnit;//单位

	// Constructors

	/** default constructor */
	public TNodeCostResource() {
	}

	/** minimal constructor */
	public TNodeCostResource(TNodes TNodes, String uresoNo) {
		this.TNodes = TNodes;
		this.uresoNo = uresoNo;
	}

	/** full constructor */
	public TNodeCostResource(TNodes TNodes, Date beginTime, Date endTime,
			Double usedAmount, String uresoType, String uresoNo,
			String uresoNorms, String uresoProvider, String uresoSerialNo,
			String uresoUnit) {
		this.TNodes = TNodes;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.usedAmount = usedAmount;
		this.uresoType = uresoType;
		this.uresoNo = uresoNo;
		this.uresoNorms = uresoNorms;
		this.uresoProvider = uresoProvider;
		this.uresoSerialNo = uresoSerialNo;
		this.uresoUnit = uresoUnit;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeID", nullable = false)
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "beginTime", length = 7)
	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endTime", length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "usedAmount", precision = 10)
	public Double getUsedAmount() {
		return this.usedAmount;
	}

	public void setUsedAmount(Double usedAmount) {
		this.usedAmount = usedAmount;
	}

	@Column(name = "uReso_Type", length = 20)
	public String getUresoType() {
		return this.uresoType;
	}

	public void setUresoType(String uresoType) {
		this.uresoType = uresoType;
	}

	@Column(name = "uReso_No", nullable = false, length = 50)
	public String getUresoNo() {
		return this.uresoNo;
	}

	public void setUresoNo(String uresoNo) {
		this.uresoNo = uresoNo;
	}

	@Column(name = "uReso_Norms", length = 20)
	public String getUresoNorms() {
		return this.uresoNorms;
	}

	public void setUresoNorms(String uresoNorms) {
		this.uresoNorms = uresoNorms;
	}

	@Column(name = "uReso_Provider", length = 50)
	public String getUresoProvider() {
		return this.uresoProvider;
	}

	public void setUresoProvider(String uresoProvider) {
		this.uresoProvider = uresoProvider;
	}

	@Column(name = "uReso_SerialNo", length = 50)
	public String getUresoSerialNo() {
		return this.uresoSerialNo;
	}

	public void setUresoSerialNo(String uresoSerialNo) {
		this.uresoSerialNo = uresoSerialNo;
	}

	@Column(name = "uReso_Unit", length = 10)
	public String getUresoUnit() {
		return this.uresoUnit;
	}

	public void setUresoUnit(String uresoUnit) {
		this.uresoUnit = uresoUnit;
	}

}