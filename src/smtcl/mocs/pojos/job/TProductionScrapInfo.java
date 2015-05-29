package smtcl.mocs.pojos.job;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * �������ϱ�
 * @author liguoqiang
 * @���� 2014-06-13
 */
@Entity
@Table(name = "t_production_scrap_info")
public class TProductionScrapInfo implements java.io.Serializable {

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
	public TProductionScrapInfo() {
		
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

	@Column(name = "tzd_code")
	public String getTzdCode() {
		return tzdCode;
	}
	public void setTzdCode(String tzdCode) {
		this.tzdCode = tzdCode;
	}

	@Column(name = "entity_name")
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "item_code")
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "item_desc")
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Column(name = "to_operation_num")
	public String getToOperationNum() {
		return toOperationNum;
	}
	public void setToOperationNum(String toOperationNum) {
		this.toOperationNum = toOperationNum;
	}

	@Column(name = "zr_operation_num")
	public String getZrOperationNum() {
		return zrOperationNum;
	}
	public void setZrOperationNum(String zrOperationNum) {
		this.zrOperationNum = zrOperationNum;
	}

	@Column(name = "responser")
	public String getResponser() {
		return responser;
	}
	public void setResponser(String responser) {
		this.responser = responser;
	}

	@Column(name = "quantity")
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "scrap_Type")
	public String getScrapType() {
		return scrapType;
	}
	public void setScrapType(String scrapType) {
		this.scrapType = scrapType;
	}

	@Column(name = "gd")
	public String getGd() {
		return gd;
	}
	public void setGd(String gd) {
		this.gd = gd;
	}

	@Column(name = "check_operation")
	public String getCheckOperation() {
		return checkOperation;
	}
	public void setCheckOperation(String checkOperation) {
		this.checkOperation = checkOperation;
	}

	@Column(name = "invoice_dat")
	public Date getInvoiceDat() {
		return invoiceDat;
	}
	public void setInvoiceDat(Date invoiceDat) {
		this.invoiceDat = invoiceDat;
	}

	@Column(name = "reson")
	public String getReson() {
		return reson;
	}
	public void setReson(String reson) {
		this.reson = reson;
	}

	@Column(name = "vendor")
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Column(name = "result")
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}

	@Column(name = "upload_date")
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "hour")
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}

	@Column(name = "Outsourcing_fee")
	public String getOutSourcingFee() {
		return outSourcingFee;
	}
	public void setOutSourcingFee(String outSourcingFee) {
		this.outSourcingFee = outSourcingFee;
	}

	
	
}