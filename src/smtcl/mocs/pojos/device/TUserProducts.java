package smtcl.mocs.pojos.device;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TUserProducts entity. @author MyEclipse Persistence Tools
 * 用户产品表
 * 在线工厂查询计划的时候，需要涉及的产品型号信息。
 */
@Entity
@Table(name = "T_UserProducts")
public class TUserProducts implements java.io.Serializable {

	// Fields

	private Long userProdId;//产品ID
	private String nodeId;//节点ID
	private String uprodName;//产品名称
	private String uprodType;//产品型号
	private String uprodNorms;//产品规格
	private String uprodVersion;//版本号
	private String uprodDesc;//产品描述
	private Date uprodOnlineTime;//投产时间
	private String uprodStatus;//产品状态
	private String uprodShortCode;//短码
	private Set<TUserProdctionPlan> TUserProdctionPlans = new HashSet<TUserProdctionPlan>(0);

	// Constructors

	/** default constructor */
	public TUserProducts() {
	}

	/** minimal constructor */
	public TUserProducts(Long userProdId, String nodeId) {
		this.userProdId = userProdId;
		this.nodeId = nodeId;
	}

	/** full constructor */
	public TUserProducts(Long userProdId, String nodeId,
			String uprodName, String uprodType, String uprodNorms,
			String uprodVersion, String uprodDesc, Date uprodOnlineTime,
			String uprodStatus, String uprodShortCode,
			Set<TUserProdctionPlan> TUserProdctionPlans) {
		this.userProdId = userProdId;
		this.nodeId = nodeId;
		this.uprodName = uprodName;
		this.uprodType = uprodType;
		this.uprodNorms = uprodNorms;
		this.uprodVersion = uprodVersion;
		this.uprodDesc = uprodDesc;
		this.uprodOnlineTime = uprodOnlineTime;
		this.uprodStatus = uprodStatus;
		this.uprodShortCode = uprodShortCode;
		this.TUserProdctionPlans = TUserProdctionPlans;
	}

	@Id
	@Column(name = "userProdID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getUserProdId() {
		return this.userProdId;
	}

	public void setUserProdId(Long userProdId) {
		this.userProdId = userProdId;
	}

	@Column(name = "nodeID", length =50)
	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "uprod_Name", length = 20)
	public String getUprodName() {
		return this.uprodName;
	}

	public void setUprodName(String uprodName) {
		this.uprodName = uprodName;
	}

	@Column(name = "uprod_Type", length = 20)
	public String getUprodType() {
		return this.uprodType;
	}

	public void setUprodType(String uprodType) {
		this.uprodType = uprodType;
	}

	@Column(name = "uprod_Norms", length = 20)
	public String getUprodNorms() {
		return this.uprodNorms;
	}

	public void setUprodNorms(String uprodNorms) {
		this.uprodNorms = uprodNorms;
	}

	@Column(name = "uprod_Version", length = 10)
	public String getUprodVersion() {
		return this.uprodVersion;
	}

	public void setUprodVersion(String uprodVersion) {
		this.uprodVersion = uprodVersion;
	}

	@Column(name = "uprod_Desc", length = 100)
	public String getUprodDesc() {
		return this.uprodDesc;
	}

	public void setUprodDesc(String uprodDesc) {
		this.uprodDesc = uprodDesc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "uprod_OnlineTime", length = 7)
	public Date getUprodOnlineTime() {
		return this.uprodOnlineTime;
	}

	public void setUprodOnlineTime(Date uprodOnlineTime) {
		this.uprodOnlineTime = uprodOnlineTime;
	}

	@Column(name = "uprod_Status", length = 20)
	public String getUprodStatus() {
		return this.uprodStatus;
	}

	public void setUprodStatus(String uprodStatus) {
		this.uprodStatus = uprodStatus;
	}

	@Column(name = "uprod_ShortCode", length = 10)
	public String getUprodShortCode() {
		return this.uprodShortCode;
	}

	public void setUprodShortCode(String uprodShortCode) {
		this.uprodShortCode = uprodShortCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUserProducts")
	public Set<TUserProdctionPlan> getTUserProdctionPlans() {
		return this.TUserProdctionPlans;
	}

	public void setTUserProdctionPlans(
			Set<TUserProdctionPlan> TUserProdctionPlans) {
		this.TUserProdctionPlans = TUserProdctionPlans;
	}

}