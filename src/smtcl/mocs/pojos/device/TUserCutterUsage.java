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
 * TUserCutterUsage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserCutterUsage")
public class TUserCutterUsage implements java.io.Serializable {

	// Fields

	private Long id;//ID
	private TNodes TNodes;
	private Date startUsageTime;//开始使用时间
	private Date endUsageTime;//结束使用时间
	private Double cuttingTime;//小时为单位
	private String ucutNo;//刀具编号
	private String ucutType;//刀具类型
	private String ucutNorms;//刀具型号
	private String ucutStatus;//刀具状态
	private String ucutProvider;//生产厂家
	private String ucutMaterialNo;//厂家物料号
	private String ucutMakings;//刀具材料
	private String ucutStorePlace;//使用地点
	private String ucutUsingMachine;//使用设备
	private Double ucutRemainLifetime;//小时为单位
	private Double ucutDesignLifetime;//设计寿命

	// Constructors

	/** default constructor */
	public TUserCutterUsage() {
	}

	/** minimal constructor */
	public TUserCutterUsage(String ucutNo) {
		this.ucutNo = ucutNo;
	}

	/** full constructor */
	public TUserCutterUsage(TNodes TNodes, Date startUsageTime,
			Date endUsageTime, Double cuttingTime, String ucutNo,
			String ucutType, String ucutNorms, String ucutStatus,
			String ucutProvider, String ucutMaterialNo, String ucutMakings,
			String ucutStorePlace, String ucutUsingMachine,
			Double ucutRemainLifetime, Double ucutDesignLifetime) {
		this.TNodes = TNodes;
		this.startUsageTime = startUsageTime;
		this.endUsageTime = endUsageTime;
		this.cuttingTime = cuttingTime;
		this.ucutNo = ucutNo;
		this.ucutType = ucutType;
		this.ucutNorms = ucutNorms;
		this.ucutStatus = ucutStatus;
		this.ucutProvider = ucutProvider;
		this.ucutMaterialNo = ucutMaterialNo;
		this.ucutMakings = ucutMakings;
		this.ucutStorePlace = ucutStorePlace;
		this.ucutUsingMachine = ucutUsingMachine;
		this.ucutRemainLifetime = ucutRemainLifetime;
		this.ucutDesignLifetime = ucutDesignLifetime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeID")
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startUsageTime", length = 7)
	public Date getStartUsageTime() {
		return this.startUsageTime;
	}

	public void setStartUsageTime(Date startUsageTime) {
		this.startUsageTime = startUsageTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endUsageTime", length = 7)
	public Date getEndUsageTime() {
		return this.endUsageTime;
	}

	public void setEndUsageTime(Date endUsageTime) {
		this.endUsageTime = endUsageTime;
	}

	@Column(name = "cutting_Time", precision = 10, scale = 1)
	public Double getCuttingTime() {
		return this.cuttingTime;
	}

	public void setCuttingTime(Double cuttingTime) {
		this.cuttingTime = cuttingTime;
	}

	@Column(name = "uCut_No", nullable = false, length = 20)
	public String getUcutNo() {
		return this.ucutNo;
	}

	public void setUcutNo(String ucutNo) {
		this.ucutNo = ucutNo;
	}

	@Column(name = "uCut_Type", length = 10)
	public String getUcutType() {
		return this.ucutType;
	}

	public void setUcutType(String ucutType) {
		this.ucutType = ucutType;
	}

	@Column(name = "uCut_Norms", length = 20)
	public String getUcutNorms() {
		return this.ucutNorms;
	}

	public void setUcutNorms(String ucutNorms) {
		this.ucutNorms = ucutNorms;
	}

	@Column(name = "uCut_Status", length = 10)
	public String getUcutStatus() {
		return this.ucutStatus;
	}

	public void setUcutStatus(String ucutStatus) {
		this.ucutStatus = ucutStatus;
	}

	@Column(name = "uCut_Provider", length = 50)
	public String getUcutProvider() {
		return this.ucutProvider;
	}

	public void setUcutProvider(String ucutProvider) {
		this.ucutProvider = ucutProvider;
	}

	@Column(name = "uCut_MaterialNo", length = 20)
	public String getUcutMaterialNo() {
		return this.ucutMaterialNo;
	}

	public void setUcutMaterialNo(String ucutMaterialNo) {
		this.ucutMaterialNo = ucutMaterialNo;
	}

	@Column(name = "uCut_Makings", length = 30)
	public String getUcutMakings() {
		return this.ucutMakings;
	}

	public void setUcutMakings(String ucutMakings) {
		this.ucutMakings = ucutMakings;
	}

	@Column(name = "uCut_StorePlace", length = 30)
	public String getUcutStorePlace() {
		return this.ucutStorePlace;
	}

	public void setUcutStorePlace(String ucutStorePlace) {
		this.ucutStorePlace = ucutStorePlace;
	}

	@Column(name = "uCut_UsingMachine", length = 30)
	public String getUcutUsingMachine() {
		return this.ucutUsingMachine;
	}

	public void setUcutUsingMachine(String ucutUsingMachine) {
		this.ucutUsingMachine = ucutUsingMachine;
	}

	@Column(name = "uCut_Remain_lifetime", precision = 12, scale = 1)
	public Double getUcutRemainLifetime() {
		return this.ucutRemainLifetime;
	}

	public void setUcutRemainLifetime(Double ucutRemainLifetime) {
		this.ucutRemainLifetime = ucutRemainLifetime;
	}

	@Column(name = "uCut_Design_lifetime", precision = 12, scale = 1)
	public Double getUcutDesignLifetime() {
		return this.ucutDesignLifetime;
	}

	public void setUcutDesignLifetime(Double ucutDesignLifetime) {
		this.ucutDesignLifetime = ucutDesignLifetime;
	}

}