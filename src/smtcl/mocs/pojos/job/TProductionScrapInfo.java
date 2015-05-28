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
 * 生产报废表
 * @author liguoqiang
 * @日期 2014-06-13
 */
@Entity
@Table(name = "t_production_scrap_info")
public class TProductionScrapInfo implements java.io.Serializable {

	private Long id;//流水号
	private String tzdCode;//报废单号
	private String entityName;//erp任务号
	private String itemCode;//物料编码
	private String itemDesc;//物料描述
	private String toOperationNum;//至工序号
	private String zrOperationNum;//责任工序号
	private String responser;//责任人员
	private Integer quantity;//不合格数量
	private String scrapType;//废品类型 1.工废 2.料废
	private String gd;//工段
	private String checkOperation;//检验人员
	private Date invoiceDat;//开票日期
	private String reson;//报废原因
	private String vendor;//责任厂家
	private String Result;//是否至工序已加工 默认 报废
	private Date uploadDate;//上传时间
	private String hour;//工时
	private String outSourcingFee;//外协费用
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