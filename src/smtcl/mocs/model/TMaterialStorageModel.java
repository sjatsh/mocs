package smtcl.mocs.model;

import java.util.Date;

/**
 * 物料库房信息辅助类
 * 作者：FW
 * 时间：2014-11-14
 */
public class TMaterialStorageModel implements java.io.Serializable {

	// Fields

	private String storageId;//库房ID
	private String positonId;//货位ID
	private String storageNo;//库房编号
	private String positionNo;//货位编号
	private String availableNum;//现有数量
	private String batchNo;//批次号
	private String seqNo;//序列号
	private String num;//出入库数量
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