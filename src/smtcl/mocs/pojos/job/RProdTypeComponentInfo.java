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

import smtcl.mocs.pojos.device.TUserProducts;

/**
 * RProdTypeComponentInfo entity. @author MyEclipse Persistence Tools
 * 产品类型部件构成表
 */
@Entity
@Table(name = "r_prod_type_component_info")
public class RProdTypeComponentInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TPartTypeInfo TPartTypeInfo;
	private TUserProducts TUserProducts;

	// Constructors

	/** default constructor */
	public RProdTypeComponentInfo() {
	}

	/** minimal constructor */
	public RProdTypeComponentInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public RProdTypeComponentInfo(Long id, TPartTypeInfo TPartTypeInfo,
			TUserProducts TUserProducts) {
		this.id = id;
		this.TPartTypeInfo = TPartTypeInfo;
		this.TUserProducts = TUserProducts;
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
	@JoinColumn(name = "componentID")
	public TPartTypeInfo getTPartTypeInfo() {
		return this.TPartTypeInfo;
	}

	public void setTPartTypeInfo(TPartTypeInfo TPartTypeInfo) {
		this.TPartTypeInfo = TPartTypeInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productID")
	public TUserProducts getTUserproducts() {
		return this.TUserProducts;
	}

	public void setTUserproducts(TUserProducts TUserProducts) {
		this.TUserProducts = TUserProducts;
	}

}