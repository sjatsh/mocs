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

/**
 * TPartProcessCost entity. @author MyEclipse Persistence Tools
 * ������͹���ɱ���
 */
@Entity
@Table(name = "r_Part_Process_Cost")
public class TPartProcessCost implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TPartTypeInfo TPartTypeInfo;
	private TProcessInfo TProcessInfo;
	private Double mainMaterialCost;//���ϳɱ�
	private Double subsidiaryMaterialCost;//���ϳɱ�
	private Double peopleCost;//��Ա�ɱ�
	private Double energyCost;//��Դ�ɱ�
	private Double deviceCost;//�豸�۾�
	private Double resourceCost;//��Դ����

	// Constructors

	/** default constructor */
	public TPartProcessCost() {
	}

	/** minimal constructor */
	public TPartProcessCost(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TPartProcessCost(Long id, TPartTypeInfo TPartTypeInfo,
			TProcessInfo TProcessInfo, Double mainMaterialCost,
			Double subsidiaryMaterialCost, Double peopleCost, Double energyCost,
			Double deviceCost, Double resourceCost) {
		this.id = id;
		this.TPartTypeInfo = TPartTypeInfo;
		this.TProcessInfo = TProcessInfo;
		this.mainMaterialCost = mainMaterialCost;
		this.subsidiaryMaterialCost = subsidiaryMaterialCost;
		this.peopleCost = peopleCost;
		this.energyCost = energyCost;
		this.deviceCost = deviceCost;
		this.resourceCost = resourceCost;
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
	@JoinColumn(name = "partTypeID")
	public TPartTypeInfo getTPartTypeInfo() {
		return this.TPartTypeInfo;
	}

	public void setTPartTypeInfo(TPartTypeInfo TPartTypeInfo) {
		this.TPartTypeInfo = TPartTypeInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processID")
	public TProcessInfo getTProcessInfo() {
		return this.TProcessInfo;
	}

	public void setTProcessInfo(TProcessInfo TProcessInfo) {
		this.TProcessInfo = TProcessInfo;
	}

	@Column(name = "main_material_cost", precision = 10, scale = 0)
	public Double getMainMaterialCost() {
		return this.mainMaterialCost;
	}

	public void setMainMaterialCost(Double mainMaterialCost) {
		this.mainMaterialCost = mainMaterialCost;
	}

	@Column(name = "subsidiary_material_cost", precision = 10, scale = 0)
	public Double getSubsidiaryMaterialCost() {
		return this.subsidiaryMaterialCost;
	}

	public void setSubsidiaryMaterialCost(Double subsidiaryMaterialCost) {
		this.subsidiaryMaterialCost = subsidiaryMaterialCost;
	}

	@Column(name = "people_cost", precision = 10, scale = 0)
	public Double getPeopleCost() {
		return this.peopleCost;
	}

	public void setPeopleCost(Double peopleCost) {
		this.peopleCost = peopleCost;
	}

	@Column(name = "energy_cost", precision = 10, scale = 0)
	public Double getEnergyCost() {
		return this.energyCost;
	}

	public void setEnergyCost(Double energyCost) {
		this.energyCost = energyCost;
	}

	@Column(name = "device_cost", precision = 10, scale = 0)
	public Double getDeviceCost() {
		return this.deviceCost;
	}

	public void setDeviceCost(Double deviceCost) {
		this.deviceCost = deviceCost;
	}

	@Column(name = "resource_cost", precision = 10, scale = 0)
	public Double getResourceCost() {
		return this.resourceCost;
	}

	public void setResourceCost(Double resourceCost) {
		this.resourceCost = resourceCost;
	}

}