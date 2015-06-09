package smtcl.mocs.model;

import java.util.Date;

/**
 * ���Ͽⷿ��Ϣ������
 * ���ߣ�FW
 * ʱ�䣺2014-11-14
 */
public class TMaterialStorageModel implements java.io.Serializable {

	// Fields

	private String storageId;//�ⷿID
	private String positonId;//��λID
	private String storageNo;//�ⷿ���
	private String positionNo;//��λ���
	private String availableNum;//��������
	private String batchNo;//���κ�
	private String seqNo;//���к�
	private String num;//���������
	public TMaterialStorageModel() {
	}
	public String getStorageId() {
		return storageId;
	}
	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	public String getPositonId() {
		return positonId;
	}
	public void setPositonId(String positonId) {
		this.positonId = positonId;
	}
	public String getStorageNo() {
		return storageNo;
	}
	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}
	public String getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}
	public String getAvailableNum() {
		return availableNum;
	}
	public void setAvailableNum(String availableNum) {
		this.availableNum = availableNum;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	
	
}