package smtcl.mocs.pojos.job;
// default package

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import smtcl.mocs.pojos.device.TUserProducts;

/**
 * TOrderInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_order_info")
public class TOrderInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long orderId;
	private TUserProducts TUserProducts;
	private String orderNo;
	private String customerName;
	private Date orderDate;
	private Integer num;
	private Date finishDate;
	private Set<TOrderPlanInfo> TOrderPlanInfos = new HashSet<TOrderPlanInfo>(0);

	// Constructors

	/** default constructor */
	public TOrderInfo() {
	}

	/** minimal constructor */
	public TOrderInfo(Long orderId) {
		this.orderId = orderId;
	}

	/** full constructor */
	public TOrderInfo(Long orderId, TUserProducts TUserProducts,
			String orderNo, String customerName, Date orderDate,
			Integer num, Date finishDate,
			Set<TOrderPlanInfo> TOrderPlanInfos) {
		this.orderId = orderId;
		this.TUserProducts = TUserProducts;
		this.orderNo = orderNo;
		this.customerName = customerName;
		this.orderDate = orderDate;
		this.num = num;
		this.finishDate = finishDate;
		this.TOrderPlanInfos = TOrderPlanInfos;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId", unique = true, nullable = false)
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userProdID")
	public TUserProducts getTUserProducts() {
		return this.TUserProducts;
	}

	public void setTUserProducts(TUserProducts TUserProducts) {
		this.TUserProducts = TUserProducts;
	}

	@Column(name = "orderNo", length = 50)
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "customerName", length = 200)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "orderDate", length = 0)
	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finishDate", length = 0)
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TOrderInfo")
	public Set<TOrderPlanInfo> getTOrderPlanInfos() {
		return this.TOrderPlanInfos;
	}

	public void setTOrderPlanInfos(Set<TOrderPlanInfo> TOrderPlanInfos) {
		this.TOrderPlanInfos = TOrderPlanInfos;
	}

}