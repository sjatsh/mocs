package smtcl.mocs.model;


import java.util.Date;

import javax.persistence.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * �������ϱ�
 * @author liguoqiang
 * @���� 2014-06-13
 */

public class TProductionScrapInfoModel implements java.io.Serializable {

	private Long id;//��ˮ��
	private String tzdCode;//���ϵ���
	private String entityName;//erp�����
	private String itemCode;//���ϱ���
	private String itemDesc;//��������
	private String toOperationNum;//�������
	private String zrOperationNum;//���ι����
	private String responser;//������Ա
	private Integer quantity;//���ϸ�����
	private String scrapType;//��Ʒ���� 1.���� 2.�Ϸ�
	private String gd;//����
	private String checkOperation;//������Ա
	private Date invoiceDat;//��Ʊ����
	private String reson;//����ԭ��
	private String vendor;//���γ���
	private String Result;//�Ƿ��������Ѽӹ� Ĭ�� ����
	private Date uploadDate;//�ϴ�ʱ��
	private String hour;//��ʱ
	private String outSourcingFee;//��Э����
	/** default constructor */
	public TProductionScrapInfoModel() {
		
	}
	// Property accessors
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTzdCode() {
		return tzdCode;
	}
	public void setTzdCode(String tzdCode) {
		this.tzdCode = tzdCode;
	}

	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getToOperationNum() {
		return toOperationNum;
	}
	public void setToOperationNum(String toOperationNum) {
		this.toOperationNum = toOperationNum;
	}

	public String getZrOperationNum() {
		return zrOperationNum;
	}
	public void setZrOperationNum(String zrOperationNum) {
		this.zrOperationNum = zrOperationNum;
	}

	public String getResponser() {
		return responser;
	}
	public void setResponser(String responser) {
		this.responser = responser;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getScrapType() {
		return scrapType;
	}
	public void setScrapType(String scrapType) {
		this.scrapType = scrapType;
	}

	public String getGd() {
		return gd;
	}
	public void setGd(String gd) {
		this.gd = gd;
	}

	public String getCheckOperation() {
		return checkOperation;
	}
	public void setCheckOperation(String checkOperation) {
		this.checkOperation = checkOperation;
	}

	public Date getInvoiceDat() {
		return invoiceDat;
	}
	public void setInvoiceDat(Date invoiceDat) {
		this.invoiceDat = invoiceDat;
	}

	public String getReson() {
		return reson;
	}
	public void setReson(String reson) {
		this.reson = reson;
	}

	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}

	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getOutSourcingFee() {
		return outSourcingFee;
	}
	public void setOutSourcingFee(String outSourcingFee) {
		this.outSourcingFee = outSourcingFee;
	}

	
	
}