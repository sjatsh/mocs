package smtcl.mocs.pojos.device;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TUserCutter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserCutter")
public class TUserCutter implements java.io.Serializable {

	// Fields

	private Long userCutterId;//刀具ID
	private TNodes TNodes;
	private String ucutNo;//刀具编号
	private Long ucutTypeId;//刀具类型ID
	private String ucutNorms;//刀具型号
	private String ucutStatus;//刀具状态
	private String ucutProvider;//生产厂家
	private String ucutMaterialNo;//厂家物料号
	private String ucutMakings;//刀具材料
	private String ucutStorePlace;//目前地点
	private String ucutUsingMachine;//使用设备
	private Double ucutRemainLifetime;//小时为单位
	private Double ucutDesignLifetime;//设计寿命

	// Constructors

	/** default constructor */
	public TUserCutter() {
	}

	/** minimal constructor */
	public TUserCutter(Long userCutterId, TNodes TNodes, String ucutNo) {
		this.userCutterId = userCutterId;
		this.TNodes = TNodes;
		this.ucutNo = ucutNo;
	}

	/** full constructor */
	public TUserCutter(Long userCutterId, TNodes TNodes, String ucutNo,
			Long ucutTypeId, String ucutNorms, String ucutStatus,
			String ucutProvider, String ucutMaterialNo, String ucutMakings,
			String ucutStorePlace, String ucutUsingMachine,
			Double ucutRemainLifetime, Double ucutDesignLifetime) {
		this.userCutterId = userCutterId;
		this.TNodes = TNodes;
		this.ucutNo = ucutNo;
		this.ucutTypeId = ucutTypeId;
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
	@Column(name = "userCutterID", nullable = false, precision = 22, scale = 0)
	public Long getUserCutterId() {
		return this.userCutterId;
	}

	public void setUserCutterId(Long userCutterId) {
		this.userCutterId = userCutterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeID", nullable = false)
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}

	@Column(name = "uCut_No", nullable = false, length = 20)
	public String getUcutNo() {
		return this.ucutNo;
	}

	public void setUcutNo(String ucutNo) {
		this.ucutNo = ucutNo;
	}

	@Column(name = "uCut_Norms", length = 20)
	public String getUcutNorms() {
		return this.ucutNorms;
	}
	
	@Column(name = "uCut_TypeID", length = 10)
	public Long getUcutTypeId() {
		return ucutTypeId;
	}

	public void setUcutTypeId(Long ucutTypeId) {
		this.ucutTypeId = ucutTypeId;
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