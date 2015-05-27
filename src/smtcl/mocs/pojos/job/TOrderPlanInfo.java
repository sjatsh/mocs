package smtcl.mocs.pojos.job;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import smtcl.mocs.pojos.device.TUserProdctionPlan;

/**
 * TOrderPlanInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_ORDER_PLAN_INFO")
public class TOrderPlanInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TUserProdctionPlan TUserProdctionPlan;
	private TOrderInfo TOrderInfo;
	private Integer num;

	// Constructors

	/** default constructor */
	public TOrderPlanInfo() {
	}

	/** minimal constructor */
	public TOrderPlanInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TOrderPlanInfo(Long id, TUserProdctionPlan TUserProdctionPlan,
			TOrderInfo TOrderInfo, Integer num) {
		this.id = id;
		this.TUserProdctionPlan = TUserProdctionPlan;
		this.TOrderInfo = TOrderInfo;
		this.num = num;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "planID")
	public TUserProdctionPlan getTUserProdctionPlan() {
		return this.TUserProdctionPlan;
	}

	public void setTUserProdctionPlan(TUserProdctionPlan TUserProdctionPlan) {
		this.TUserProdctionPlan = TUserProdctionPlan;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	public TOrderInfo getTOrderInfo() {
		return this.TOrderInfo;
	}

	public void setTOrderInfo(TOrderInfo TOrderInfo) {
		this.TOrderInfo = TOrderInfo;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}