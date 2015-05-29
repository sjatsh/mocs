package smtcl.mocs.pojos.storage;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * KC_202 ��λ��
 */
@Entity
@Table(name = "t_materiel_position_info")
public class TMaterielPositionInfo implements java.io.Serializable {

	// Fields

	private Integer id;//��ˮ��ID
	private String positionNo;//��λ���
	private String positionName;//��λ˵��
	private String positionStatus;//��λ״̬
	private Double quantitySize;//����-������С
	private String quantityUnit;//����������λ��Ĭ�ϣ�
	private Double capacitySize;//����-�����С
	private String capacityUnit;//���������λ��Ĭ�ϣ�
	private Double weightSize;//����-������С
	private String weightUnit;//����������λ��Ĭ�ϣ�
	private String dimensionSize;//ά�ȴ�С
	private String dimensionUnit;//ά�ȵ�λ��Ĭ�ϣ�
	private String axis;//����
	private Set<RStoragePosition> RStoragePositions = new HashSet<RStoragePosition>(
			0);

	// Constructors

	/** default constructor */
	public TMaterielPositionInfo() {
	}

	/** full constructor */
	public TMaterielPositionInfo(String positionNo, String positionName,
			String positionStatus, Double quantitySize, String quantityUnit,
			Double capacitySize, String capacityUnit, Double weightSize,
			String weightUnit, String dimensionSize, String dimensionUnit,
			String axis, Set<RStoragePosition> RStoragePositions) {
		this.positionNo = positionNo;
		this.positionName = positionName;
		this.positionStatus = positionStatus;
		this.quantitySize = quantitySize;
		this.quantityUnit = quantityUnit;
		this.capacitySize = capacitySize;
		this.capacityUnit = capacityUnit;
		this.weightSize = weightSize;
		this.weightUnit = weightUnit;
		this.dimensionSize = dimensionSize;
		this.dimensionUnit = dimensionUnit;
		this.axis = axis;
		this.RStoragePositions = RStoragePositions;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "positionNo", length = 20)
	public String getPositionNo() {
		return this.positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	@Column(name = "positionName", length = 30)
	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Column(name = "positionStatus", length = 1)
	public String getPositionStatus() {
		return this.positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

	@Column(name = "quantity_size", precision = 5)
	public Double getQuantitySize() {
		return this.quantitySize;
	}

	public void setQuantitySize(Double quantitySize) {
		this.quantitySize = quantitySize;
	}

	@Column(name = "quantity_unit", length = 20)
	public String getQuantityUnit() {
		return this.quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	@Column(name = "capacity_size", precision = 5)
	public Double getCapacitySize() {
		return this.capacitySize;
	}

	public void setCapacitySize(Double capacitySize) {
		this.capacitySize = capacitySize;
	}

	@Column(name = "capacity_unit", length = 20)
	public String getCapacityUnit() {
		return this.capacityUnit;
	}

	public void setCapacityUnit(String capacityUnit) {
		this.capacityUnit = capacityUnit;
	}

	@Column(name = "weight_size", precision = 5)
	public Double getWeightSize() {
		return this.weightSize;
	}

	public void setWeightSize(Double weightSize) {
		this.weightSize = weightSize;
	}

	@Column(name = "weight_unit", length = 20)
	public String getWeightUnit() {
		return this.weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	@Column(name = "dimension_size", length = 50)
	public String getDimensionSize() {
		return this.dimensionSize;
	}

	public void setDimensionSize(String dimensionSize) {
		this.dimensionSize = dimensionSize;
	}

	@Column(name = "dimension_unit", length = 20)
	public String getDimensionUnit() {
		return this.dimensionUnit;
	}

	public void setDimensionUnit(String dimensionUnit) {
		this.dimensionUnit = dimensionUnit;
	}

	@Column(name = "axis", length = 20)
	public String getAxis() {
		return this.axis;
	}

	public void setAxis(String axis) {
		this.axis = axis;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TMaterielPositionInfo")
	public Set<RStoragePosition> getRStoragePositions() {
		return this.RStoragePositions;
	}

	public void setRStoragePositions(Set<RStoragePosition> RStoragePositions) {
		this.RStoragePositions = RStoragePositions;
	}

}