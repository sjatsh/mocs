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
 * TUserFixtureUsage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserFixtureUsage")
public class TUserFixtureUsage implements java.io.Serializable {

	// Fields

	private Long id;//ID
	private TNodes TNodes;
	private Date startUsageTime;//开始使用时间
	private Date endUsageTime;//结束使用时间
	private String usageMemo;//备注
	private String ufixNo;//夹具编号
	private String ufixType;//夹具类型
	private String ufixName;//夹具名称
	private String ufixProvider;//生产厂家
	private String ufixMaterialNo;//厂家物料号
	private String ufixMaking;//材料
	private String ufixStorePlace;//地点
	private String ufixUsingMachine;//使用设备

	// Constructors

	/** default constructor */
	public TUserFixtureUsage() {
	}

	/** full constructor */
	public TUserFixtureUsage(TNodes TNodes, Date startUsageTime,
			Date endUsageTime, String usageMemo, String ufixNo,
			String ufixType, String ufixName, String ufixProvider,
			String ufixMaterialNo, String ufixMaking, String ufixStorePlace,
			String ufixUsingMachine) {
		this.TNodes = TNodes;
		this.startUsageTime = startUsageTime;
		this.endUsageTime = endUsageTime;
		this.usageMemo = usageMemo;
		this.ufixNo = ufixNo;
		this.ufixType = ufixType;
		this.ufixName = ufixName;
		this.ufixProvider = ufixProvider;
		this.ufixMaterialNo = ufixMaterialNo;
		this.ufixMaking = ufixMaking;
		this.ufixStorePlace = ufixStorePlace;
		this.ufixUsingMachine = ufixUsingMachine;
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

	@Column(name = "usageMemo")
	public String getUsageMemo() {
		return this.usageMemo;
	}

	public void setUsageMemo(String usageMemo) {
		this.usageMemo = usageMemo;
	}

	@Column(name = "uFix_No")
	public String getUfixNo() {
		return this.ufixNo;
	}

	public void setUfixNo(String ufixNo) {
		this.ufixNo = ufixNo;
	}

	@Column(name = "uFix_Type", length = 20)
	public String getUfixType() {
		return this.ufixType;
	}

	public void setUfixType(String ufixType) {
		this.ufixType = ufixType;
	}

	@Column(name = "uFix_Name", length = 30)
	public String getUfixName() {
		return this.ufixName;
	}

	public void setUfixName(String ufixName) {
		this.ufixName = ufixName;
	}

	@Column(name = "uFix_Provider", length = 30)
	public String getUfixProvider() {
		return this.ufixProvider;
	}

	public void setUfixProvider(String ufixProvider) {
		this.ufixProvider = ufixProvider;
	}

	@Column(name = "uFix_MaterialNo", length = 20)
	public String getUfixMaterialNo() {
		return this.ufixMaterialNo;
	}

	public void setUfixMaterialNo(String ufixMaterialNo) {
		this.ufixMaterialNo = ufixMaterialNo;
	}

	@Column(name = "uFix_Making", length = 20)
	public String getUfixMaking() {
		return this.ufixMaking;
	}

	public void setUfixMaking(String ufixMaking) {
		this.ufixMaking = ufixMaking;
	}

	@Column(name = "uFix_StorePlace", length = 50)
	public String getUfixStorePlace() {
		return this.ufixStorePlace;
	}

	public void setUfixStorePlace(String ufixStorePlace) {
		this.ufixStorePlace = ufixStorePlace;
	}

	@Column(name = "uFix_UsingMachine", length = 50)
	public String getUfixUsingMachine() {
		return this.ufixUsingMachine;
	}

	public void setUfixUsingMachine(String ufixUsingMachine) {
		this.ufixUsingMachine = ufixUsingMachine;
	}

}