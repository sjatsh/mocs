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
 * TUserProdctionPlan entity. @author MyEclipse Persistence Tools
 * 用户产品计划表
 * 在线工厂涉及的生产计划。注意这个生产计划可以和节点的任何一个层次构成管理。即可以是厂计划，也可以是车间计划或线计划或设备
 */
@Entity
@Table(name = "T_UserProdctionPlan")
public class TUserProdctionPlan implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userProdPlanId;//生产计划ID
	private TUserProducts TUserProducts;//产品id
	private TNodes TNodes;//节点id
	private String uplanNo;//计划编号
	private String uplanType;//计划类型
	private Double uplanNum;//计划数量
	private String uplanStatus;//计划状态
	private String uplanInchargePerson;//计划负责人
	private Date uplanOnlineDate;//计划投产日期
	private Date uplanFinishDate;//计划完成日期
	private Date uplanBeginTime;//实际开始时间
	private Date uplanActualFinishTime;//实际结束时间
	private Double uplanActualQuantity;//实际完成数量
	private String uplanMasterPlanNo;//主生产计划号
	private Long uplanGoodQuantity;//合格产量
	private String uplanName;//计划名称
	private String uplanUnit;//单位
	private String uplanRouting;//工艺路线
	private Date finishDate;//交货期

	// Constructors

	/** default constructor */
	public TUserProdctionPlan() {
	}

	/** minimal constructor */
	public TUserProdctionPlan(Long userProdPlanId,
			TUserProducts TUserProducts, TNodes TNodes) {
		this.userProdPlanId = userProdPlanId;
		this.TUserProducts = TUserProducts;
		this.TNodes = TNodes;
	}

	/** full constructor */
	public TUserProdctionPlan(Long userProdPlanId,
			TUserProducts TUserProducts, TNodes TNodes, String uplanNo,
			String uplanType, Double uplanNum, String uplanStatus,
			String uplanInchargePerson, Date uplanOnlineDate,
			Date uplanFinishDate, Date uplanBeginTime,
			Date uplanActualFinishTime, Double uplanActualQuantity,
			String uplanMasterPlanNo, Long uplanGoodQuantity,
			String uplanName, String uplanUnit, String uplanRouting,Date finishDate) {
		this.userProdPlanId = userProdPlanId;
		this.TUserProducts = TUserProducts;
		this.TNodes = TNodes;
		this.uplanNo = uplanNo;
		this.uplanType = uplanType;
		this.uplanNum = uplanNum;
		this.uplanStatus = uplanStatus;
		this.uplanInchargePerson = uplanInchargePerson;
		this.uplanOnlineDate = uplanOnlineDate;
		this.uplanFinishDate = uplanFinishDate;
		this.uplanBeginTime = uplanBeginTime;
		this.uplanActualFinishTime = uplanActualFinishTime;
		this.uplanActualQuantity = uplanActualQuantity;
		this.uplanMasterPlanNo = uplanMasterPlanNo;
		this.uplanGoodQuantity = uplanGoodQuantity;
		this.uplanName = uplanName;
		this.uplanUnit = uplanUnit;
		this.uplanRouting = uplanRouting;
		this.finishDate=finishDate;
	}

	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "userProd_PlanID", nullable = false, precision = 22, scale = 0)
	public Long getUserProdPlanId() {
		return this.userProdPlanId;
	}

	public void setUserProdPlanId(Long userProdPlanId) {
		this.userProdPlanId = userProdPlanId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userProdID", nullable = false)
	public TUserProducts getTUserProducts() {
		return this.TUserProducts;
	}

	public void setTUserProducts(TUserProducts TUserProducts) {
		this.TUserProducts = TUserProducts;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeID", nullable = false)
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}

	@Column(name = "uplan_No", length = 20)
	public String getUplanNo() {
		return this.uplanNo;
	}

	public void setUplanNo(String uplanNo) {
		this.uplanNo = uplanNo;
	}

	@Column(name = "uplan_Type", length = 20)
	public String getUplanType() {
		return this.uplanType;
	}

	public void setUplanType(String uplanType) {
		this.uplanType = uplanType;
	}

	@Column(name = "uplan_Num", precision = 10, scale = 1)
	public Double getUplanNum() {
		return this.uplanNum;
	}

	public void setUplanNum(Double uplanNum) {
		this.uplanNum = uplanNum;
	}

	@Column(name = "uplan_Status", length = 30)
	public String getUplanStatus() {
		return this.uplanStatus;
	}

	public void setUplanStatus(String uplanStatus) {
		this.uplanStatus = uplanStatus;
	}

	@Column(name = "uplan_inchargePerson", length = 20)
	public String getUplanInchargePerson() {
		return this.uplanInchargePerson;
	}

	public void setUplanInchargePerson(String uplanInchargePerson) {
		this.uplanInchargePerson = uplanInchargePerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uplan_OnlineDate", length = 7)
	public Date getUplanOnlineDate() {
		return this.uplanOnlineDate;
	}

	public void setUplanOnlineDate(Date uplanOnlineDate) {
		this.uplanOnlineDate = uplanOnlineDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uplan_FinishDate", length = 7)
	public Date getUplanFinishDate() {
		return this.uplanFinishDate;
	}

	public void setUplanFinishDate(Date uplanFinishDate) {
		this.uplanFinishDate = uplanFinishDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uplan_BeginTime", length = 7)
	public Date getUplanBeginTime() {
		return this.uplanBeginTime;
	}

	public void setUplanBeginTime(Date uplanBeginTime) {
		this.uplanBeginTime = uplanBeginTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uplan_ActualFinishTime", length = 7)
	public Date getUplanActualFinishTime() {
		return this.uplanActualFinishTime;
	}

	public void setUplanActualFinishTime(Date uplanActualFinishTime) {
		this.uplanActualFinishTime = uplanActualFinishTime;
	}

	@Column(name = "uplan_ActualQuantity", precision = 10, scale = 1)
	public Double getUplanActualQuantity() {
		return this.uplanActualQuantity;
	}

	public void setUplanActualQuantity(Double uplanActualQuantity) {
		this.uplanActualQuantity = uplanActualQuantity;
	}

	@Column(name = "uplan_MasterPlan_No")
	public String getUplanMasterPlanNo() {
		return this.uplanMasterPlanNo;
	}

	public void setUplanMasterPlanNo(String uplanMasterPlanNo) {
		this.uplanMasterPlanNo = uplanMasterPlanNo;
	}

	@Column(name = "uplan_GoodQuantity", precision = 22, scale = 0)
	public Long getUplanGoodQuantity() {
		return this.uplanGoodQuantity;
	}

	public void setUplanGoodQuantity(Long uplanGoodQuantity) {
		this.uplanGoodQuantity = uplanGoodQuantity;
	}

	@Column(name = "uplan_Name")
	public String getUplanName() {
		return this.uplanName;
	}

	public void setUplanName(String uplanName) {
		this.uplanName = uplanName;
	}

	@Column(name = "uplan_Unit", length = 10)
	public String getUplanUnit() {
		return this.uplanUnit;
	}

	public void setUplanUnit(String uplanUnit) {
		this.uplanUnit = uplanUnit;
	}

	@Column(name = "uplan_Routing")
	public String getUplanRouting() {
		return this.uplanRouting;
	}

	public void setUplanRouting(String uplanRouting) {
		this.uplanRouting = uplanRouting;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finishDate", length = 7)
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

}