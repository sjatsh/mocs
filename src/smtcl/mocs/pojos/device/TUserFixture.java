package smtcl.mocs.pojos.device;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TUserFixture entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserFixture")
public class TUserFixture implements java.io.Serializable {

	// Fields

	private Long userFixtureId;//夹具ID
	private TNodes TNodes;
	private String ufixNo;//夹具编号
	private Long ufixTypeId;//夹具类型ID
	private String ufixNorms;//夹具规格
	private String ufixName;//夹具名称
	private String ufixProvider;//生产厂家
	private String ufixMaterialNo;//厂家物料号
	private String ufixMaking;//材料
	private String ufixStorePlace;//目前存放地点
	private String ufixStatus;//夹具状态
	private String ufixUsingMachine;//使用设备
	private Integer ufixClass;//夹具类别
	private String ufixMemo;

	// Constructors

	/** default constructor */
	public TUserFixture() {
	}

	/** minimal constructor */
	public TUserFixture(Long userFixtureId, TNodes TNodes, String ufixNo) {
		this.userFixtureId = userFixtureId;
		this.TNodes = TNodes;
		this.ufixNo = ufixNo;
	}

	/** full constructor */
	public TUserFixture(Long userFixtureId, TNodes TNodes, String ufixNo,
			Long ufixTypeId, String ufixNorms, String ufixName,
			String ufixProvider, String ufixMaterialNo, String ufixMaking,
			String ufixStorePlace, String ufixStatus, String ufixUsingMachine) {
		this.userFixtureId = userFixtureId;
		this.TNodes = TNodes;
		this.ufixNo = ufixNo;
		this.ufixTypeId = ufixTypeId;
		this.ufixNorms = ufixNorms;
		this.ufixName = ufixName;
		this.ufixProvider = ufixProvider;
		this.ufixMaterialNo = ufixMaterialNo;
		this.ufixMaking = ufixMaking;
		this.ufixStorePlace = ufixStorePlace;
		this.ufixStatus = ufixStatus;
		this.ufixUsingMachine = ufixUsingMachine;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userFixtureID", nullable = false, precision = 22, scale = 0)
	public Long getUserFixtureId() {
		return this.userFixtureId;
	}

	public void setUserFixtureId(Long userFixtureId) {
		this.userFixtureId = userFixtureId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeID", nullable = false)
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}

	@Column(name = "uFix_No", nullable = false, length = 20)
	public String getUfixNo() {
		return this.ufixNo;
	}

	public void setUfixNo(String ufixNo) {
		this.ufixNo = ufixNo;
	}

	@Column(name = "uFix_TypeId", length = 20)
	public Long getUfixTypeId() {
		return this.ufixTypeId;
	}

	public void setUfixTypeId(Long ufixTypeId) {
		this.ufixTypeId = ufixTypeId;
	}

	@Column(name = "uFix_Norms", length = 20)
	public String getUfixNorms() {
		return this.ufixNorms;
	}

	public void setUfixNorms(String ufixNorms) {
		this.ufixNorms = ufixNorms;
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

	@Column(name = "uFix_Status", length = 10)
	public String getUfixStatus() {
		return this.ufixStatus;
	}

	public void setUfixStatus(String ufixStatus) {
		this.ufixStatus = ufixStatus;
	}

	@Column(name = "uFix_UsingMachine", length = 50)
	public String getUfixUsingMachine() {
		return this.ufixUsingMachine;
	}

	public void setUfixUsingMachine(String ufixUsingMachine) {
		this.ufixUsingMachine = ufixUsingMachine;
	}

	@Column(name = "uFix_Class")
	public Integer getUfixClass() {
		return ufixClass;
	}
	
	public void setUfixClass(Integer ufixClass) {
		this.ufixClass = ufixClass;
	}
	
	@Column(name = "uFix_memo")
	public String getUfixMemo() {
		return ufixMemo;
	}

	public void setUfixMemo(String ufixMemo) {
		this.ufixMemo = ufixMemo;
	}
	
	
}