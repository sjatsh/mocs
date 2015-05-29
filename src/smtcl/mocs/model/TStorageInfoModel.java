package smtcl.mocs.model;

import java.util.Date;

/**
 * �ⷿ��Ϣ������
 * ���ߣ�FW
 * ʱ�䣺2014-09-02
 */
public class TStorageInfoModel implements java.io.Serializable {

	// Fields

	private Integer id;//��ˮ��ID
	private String storageNo;//�ⷿ���
	private String storageName;//�ⷿ˵��
	private String storageStatus;//�ⷿ״̬
	private String invalidDate;//ʧЧ����
	private String isAvailable;//�ɾ���
	private String planType;//�ƻ���ʽ
	private String positionType;//��λ����
	private String address;//�ⷿλ��
	private String storageMan;//��Ҫ����Ա
	private Date createDate;//�ⷿ����ʱ��
	private Double preProcessTime;//Ԥ�ӹ���ǰ��
	private Double inProcessTime;//�ӹ�����ǰ��
	private Double sufProcessTime;//��ӹ���ǰ��
    private String nodeId;//�ڵ�
	public TStorageInfoModel() {
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStorageNo() {
		return this.storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}
	
	public String getStorageName() {
		return this.storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	
	public String getStorageStatus() {
		return this.storageStatus;
	}

	public void setStorageStatus(String storageStatus) {
		this.storageStatus = storageStatus;
	}
	
	public String getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public String getPlanType() {
		return this.planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	public String getPositionType() {
		return this.positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStorageMan() {
		return this.storageMan;
	}

	public void setStorageMan(String storageMan) {
		this.storageMan = storageMan;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Double getPreProcessTime() {
		return this.preProcessTime;
	}

	public void setPreProcessTime(Double preProcessTime) {
		this.preProcessTime = preProcessTime;
	}
	
	public Double getInProcessTime() {
		return this.inProcessTime;
	}

	public void setInProcessTime(Double inProcessTime) {
		this.inProcessTime = inProcessTime;
	}
	
	public Double getSufProcessTime() {
		return this.sufProcessTime;
	}

	public void setSufProcessTime(Double sufProcessTime) {
		this.sufProcessTime = sufProcessTime;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
}