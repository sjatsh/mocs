package smtcl.mocs.pojos.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * KC_104 ���ϼƻ�
 */
@Entity
@Table(name = "t_material_extend_plan")
public class TMaterialExtendPlan implements java.io.Serializable {

	// Fields

	private Integer materialId;//����ID
	private String planType;//���ƻ�����
	private String planner;//�ƻ�Ա
	private String planClass;//�����ɹ�
	private Double storageMax;//������ֵ
	private Double storageMin;//�����Сֵ
	private Double orderMin;//��������Сֵ
	private Double orderMax;//���������ֵ
	private Double costOrder;//�ɱ�����
	private Double storageRate;//������
	private Double thresholdValue;//��ȫ���
	private String planfrom;//�ƻ���Դ

	// Constructors

	/** default constructor */
	public TMaterialExtendPlan() {
	}

	/** full constructor */
	public TMaterialExtendPlan(String planType, String planner,
			String planClass, Double storageMax, Double storageMin,
			Double orderMin, Double orderMax, Double costOrder,
			Double storageRate, Double thresholdValue, String planfrom) {
		this.planType = planType;
		this.planner = planner;
		this.planClass = planClass;
		this.storageMax = storageMax;
		this.storageMin = storageMin;
		this.orderMin = orderMin;
		this.orderMax = orderMax;
		this.costOrder = costOrder;
		this.storageRate = storageRate;
		this.thresholdValue = thresholdValue;
		this.planfrom = planfrom;
	}

	// Property accessors
	@Id
	@Column(name = "material_id", unique = true, nullable = false)
	public Integer getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@Column(name = "planType", length = 1)
	public String getPlanType() {
		return this.planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	@Column(name = "planner", length = 20)
	public String getPlanner() {
		return this.planner;
	}

	public void setPlanner(String planner) {
		this.planner = planner;
	}

	@Column(name = "planClass", length = 1)
	public String getPlanClass() {
		return this.planClass;
	}

	public void setPlanClass(String planClass) {
		this.planClass = planClass;
	}

	@Column(name = "storage_max", precision = 5)
	public Double getStorageMax() {
		return this.storageMax;
	}

	public void setStorageMax(Double storageMax) {
		this.storageMax = storageMax;
	}

	@Column(name = "storage_min", precision = 5)
	public Double getStorageMin() {
		return this.storageMin;
	}

	public void setStorageMin(Double storageMin) {
		this.storageMin = storageMin;
	}

	@Column(name = "order_min", precision = 5)
	public Double getOrderMin() {
		return this.orderMin;
	}

	public void setOrderMin(Double orderMin) {
		this.orderMin = orderMin;
	}

	@Column(name = "order_max", precision = 5)
	public Double getOrderMax() {
		return this.orderMax;
	}

	public void setOrderMax(Double orderMax) {
		this.orderMax = orderMax;
	}

	@Column(name = "cost_order", precision = 5)
	public Double getCostOrder() {
		return this.costOrder;
	}

	public void setCostOrder(Double costOrder) {
		this.costOrder = costOrder;
	}

	@Column(name = "storage_rate", precision = 5)
	public Double getStorageRate() {
		return this.storageRate;
	}

	public void setStorageRate(Double storageRate) {
		this.storageRate = storageRate;
	}

	@Column(name = "threshold_value", precision = 5)
	public Double getThresholdValue() {
		return this.thresholdValue;
	}

	public void setThresholdValue(Double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	@Column(name = "planfrom", length = 20)
	public String getPlanfrom() {
		return this.planfrom;
	}

	public void setPlanfrom(String planfrom) {
		this.planfrom = planfrom;
	}

}