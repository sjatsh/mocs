package smtcl.mocs.model;


import java.util.Date;

import javax.persistence.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 生产报废表
 * @author liguoqiang
 * @日期 2014-06-13
 */

public class TProductionScrapInfoModel implements java.io.Serializable {

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