package smtcl.mocs.model;

/**
 * ��λ��Ϣ������
 * ���ߣ�FW
 * ʱ�䣺2014-09-02
 */
public class TMaterialPositionModel implements java.io.Serializable {

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
	private Integer sid; //�ⷿId
	private String storageNo; //�ⷿ���
	private String storageName;//�ⷿ˵��
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