package smtcl.mocs.model;

/**
 * 库位信息辅助类
 * 作者：FW
 * 时间：2014-09-02
 */
public class TMaterialPositionModel implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private String positionNo;//货位编号
	private String positionName;//货位说明
	private String positionStatus;//货位状态
	private Double quantitySize;//能力-数量大小
	private String quantityUnit;//数量基本单位（默认）
	private Double capacitySize;//能力-体积大小
	private String capacityUnit;//体积基本单位（默认）
	private Double weightSize;//能力-重量大小
	private String weightUnit;//重量基本单位（默认）
	private String dimensionSize;//维度大小
	private String dimensionUnit;//维度单位（默认）
	private String axis;//坐标
	private Integer sid; //库房Id
	private String storageNo; //库房编号
	private String storageName;//库房说明
	// Constructors

	/** default constructor */
	public TMaterialPositionModel() {
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPositionNo() {
		return this.positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}
	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionStatus() {
		return this.positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}
	public Double getQuantitySize() {
		return this.quantitySize;
	}

	public void setQuantitySize(Double quantitySize) {
		this.quantitySize = quantitySize;
	}
	public String getQuantityUnit() {
		return this.quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	public Double getCapacitySize() {
		return this.capacitySize;
	}

	public void setCapacitySize(Double capacitySize) {
		this.capacitySize = capacitySize;
	}
	public String getCapacityUnit() {
		return this.capacityUnit;
	}

	public void setCapacityUnit(String capacityUnit) {
		this.capacityUnit = capacityUnit;
	}
	public Double getWeightSize() {
		return this.weightSize;
	}

	public void setWeightSize(Double weightSize) {
		this.weightSize = weightSize;
	}
	public String getWeightUnit() {
		return this.weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public String getDimensionSize() {
		return this.dimensionSize;
	}

	public void setDimensionSize(String dimensionSize) {
		this.dimensionSize = dimensionSize;
	}
	public String getDimensionUnit() {
		return this.dimensionUnit;
	}

	public void setDimensionUnit(String dimensionUnit) {
		this.dimensionUnit = dimensionUnit;
	}
	public String getAxis() {
		return this.axis;
	}

	public void setAxis(String axis) {
		this.axis = axis;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getStorageNo() {
		return storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	
}