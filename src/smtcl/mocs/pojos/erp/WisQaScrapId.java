package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisQaScrapId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisQaScrapId implements java.io.Serializable {

	// Fields

	private String entityName;
	private String itemCode;
	private String itemDesc;
	private String tzdCode;
	private String invoiceDate;
	private String toOperationNum;
	private String zrOperationNum;
	private String qaResult;
	private String quantity;
	private String hour;
	private String outsourcingFee;
	private String reson;
	private String fpType;
	private String responser;
	private String vendor;
	private String gd;
	private String checker;
	private Date uploadDate;
	private Long organizationId;
	private Long flag;
	private String reqQaId;
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	private String a5;

	// Constructors

	/** default constructor */
	public WisQaScrapId() {
	}

	/** full constructor */
	public WisQaScrapId(String entityName, String itemCode, String itemDesc,
			String tzdCode, String invoiceDate, String toOperationNum,
			String zrOperationNum, String qaResult, String quantity,
			String hour, String outsourcingFee, String reson, String fpType,
			String responser, String vendor, String gd, String checker,
			Date uploadDate, Long organizationId, Long flag,
			String a1, String a2, String a3, String a4, String a5) {
		this.entityName = entityName;
		this.itemCode = itemCode;
		this.itemDesc = itemDesc;
		this.tzdCode = tzdCode;
		this.invoiceDate = invoiceDate;
		this.toOperationNum = toOperationNum;
		this.zrOperationNum = zrOperationNum;
		this.qaResult = qaResult;
		this.quantity = quantity;
		this.hour = hour;
		this.outsourcingFee = outsourcingFee;
		this.reson = reson;
		this.fpType = fpType;
		this.responser = responser;
		this.vendor = vendor;
		this.gd = gd;
		this.checker = checker;
		this.uploadDate = uploadDate;
		this.organizationId = organizationId;
		this.flag = flag;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a5 = a5;
	}

	// Property accessors

	@Column(name = "ENTITY_NAME", length = 240)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "ITEM_CODE", length = 240)
	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "ITEM_DESC", length = 240)
	public String getItemDesc() {
		return this.itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Column(name = "TZD_CODE", length = 240)
	public String getTzdCode() {
		return this.tzdCode;
	}

	public void setTzdCode(String tzdCode) {
		this.tzdCode = tzdCode;
	}

	@Column(name = "INVOICE_DATE", length = 240)
	public String getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Column(name = "TO_OPERATION_NUM", length = 240)
	public String getToOperationNum() {
		return this.toOperationNum;
	}

	public void setToOperationNum(String toOperationNum) {
		this.toOperationNum = toOperationNum;
	}

	@Column(name = "ZR_OPERATION_NUM", length = 240)
	public String getZrOperationNum() {
		return this.zrOperationNum;
	}

	public void setZrOperationNum(String zrOperationNum) {
		this.zrOperationNum = zrOperationNum;
	}

	@Column(name = "QA_RESULT", length = 240)
	public String getQaResult() {
		return this.qaResult;
	}

	public void setQaResult(String qaResult) {
		this.qaResult = qaResult;
	}

	@Column(name = "QUANTITY", length = 240)
	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@Column(name = "HOUR", length = 240)
	public String getHour() {
		return this.hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	@Column(name = "OUTSOURCING_FEE", length = 240)
	public String getOutsourcingFee() {
		return this.outsourcingFee;
	}

	public void setOutsourcingFee(String outsourcingFee) {
		this.outsourcingFee = outsourcingFee;
	}

	@Column(name = "RESON", length = 240)
	public String getReson() {
		return this.reson;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}

	@Column(name = "FP_TYPE", length = 240)
	public String getFpType() {
		return this.fpType;
	}

	public void setFpType(String fpType) {
		this.fpType = fpType;
	}

	@Column(name = "RESPONSER", length = 240)
	public String getResponser() {
		return this.responser;
	}

	public void setResponser(String responser) {
		this.responser = responser;
	}

	@Column(name = "VENDOR", length = 240)
	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Column(name = "GD", length = 240)
	public String getGd() {
		return this.gd;
	}

	public void setGd(String gd) {
		this.gd = gd;
	}

	@Column(name = "CHECKER", length = 240)
	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOAD_DATE", length = 7)
	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "ORGANIZATION_ID", precision = 22, scale = 0)
	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "FLAG", precision = 22, scale = 0)
	public Long getFlag() {
		return this.flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	@Column(name = "A1", length = 240)
	public String getA1() {
		return this.a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	@Column(name = "A2", length = 240)
	public String getA2() {
		return this.a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	@Column(name = "A3", length = 240)
	public String getA3() {
		return this.a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	@Column(name = "A4", length = 240)
	public String getA4() {
		return this.a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	@Column(name = "A5", length = 240)
	public String getA5() {
		return this.a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}


	@Column(name = "req_qa_id", length = 240)
	public String getReqQaId() {
		return reqQaId;
	}

	public void setReqQaId(String reqQaId) {
		this.reqQaId = reqQaId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WisQaScrapId))
			return false;
		WisQaScrapId castOther = (WisQaScrapId) other;

		return ((this.getEntityName() == castOther.getEntityName()) || (this
				.getEntityName() != null && castOther.getEntityName() != null && this
				.getEntityName().equals(castOther.getEntityName())))
				&& ((this.getItemCode() == castOther.getItemCode()) || (this
						.getItemCode() != null
						&& castOther.getItemCode() != null && this
						.getItemCode().equals(castOther.getItemCode())))
				&& ((this.getItemDesc() == castOther.getItemDesc()) || (this
						.getItemDesc() != null
						&& castOther.getItemDesc() != null && this
						.getItemDesc().equals(castOther.getItemDesc())))
				&& ((this.getTzdCode() == castOther.getTzdCode()) || (this
						.getTzdCode() != null && castOther.getTzdCode() != null && this
						.getTzdCode().equals(castOther.getTzdCode())))
				&& ((this.getInvoiceDate() == castOther.getInvoiceDate()) || (this
						.getInvoiceDate() != null
						&& castOther.getInvoiceDate() != null && this
						.getInvoiceDate().equals(castOther.getInvoiceDate())))
				&& ((this.getToOperationNum() == castOther.getToOperationNum()) || (this
						.getToOperationNum() != null
						&& castOther.getToOperationNum() != null && this
						.getToOperationNum().equals(
								castOther.getToOperationNum())))
				&& ((this.getZrOperationNum() == castOther.getZrOperationNum()) || (this
						.getZrOperationNum() != null
						&& castOther.getZrOperationNum() != null && this
						.getZrOperationNum().equals(
								castOther.getZrOperationNum())))
				&& ((this.getQaResult() == castOther.getQaResult()) || (this
						.getQaResult() != null
						&& castOther.getQaResult() != null && this
						.getQaResult().equals(castOther.getQaResult())))
				&& ((this.getQuantity() == castOther.getQuantity()) || (this
						.getQuantity() != null
						&& castOther.getQuantity() != null && this
						.getQuantity().equals(castOther.getQuantity())))
				&& ((this.getHour() == castOther.getHour()) || (this.getHour() != null
						&& castOther.getHour() != null && this.getHour()
						.equals(castOther.getHour())))
				&& ((this.getOutsourcingFee() == castOther.getOutsourcingFee()) || (this
						.getOutsourcingFee() != null
						&& castOther.getOutsourcingFee() != null && this
						.getOutsourcingFee().equals(
								castOther.getOutsourcingFee())))
				&& ((this.getReson() == castOther.getReson()) || (this
						.getReson() != null && castOther.getReson() != null && this
						.getReson().equals(castOther.getReson())))
				&& ((this.getFpType() == castOther.getFpType()) || (this
						.getFpType() != null && castOther.getFpType() != null && this
						.getFpType().equals(castOther.getFpType())))
				&& ((this.getResponser() == castOther.getResponser()) || (this
						.getResponser() != null
						&& castOther.getResponser() != null && this
						.getResponser().equals(castOther.getResponser())))
				&& ((this.getVendor() == castOther.getVendor()) || (this
						.getVendor() != null && castOther.getVendor() != null && this
						.getVendor().equals(castOther.getVendor())))
				&& ((this.getGd() == castOther.getGd()) || (this.getGd() != null
						&& castOther.getGd() != null && this.getGd().equals(
						castOther.getGd())))
				&& ((this.getChecker() == castOther.getChecker()) || (this
						.getChecker() != null && castOther.getChecker() != null && this
						.getChecker().equals(castOther.getChecker())))
				&& ((this.getUploadDate() == castOther.getUploadDate()) || (this
						.getUploadDate() != null
						&& castOther.getUploadDate() != null && this
						.getUploadDate().equals(castOther.getUploadDate())))
				&& ((this.getOrganizationId() == castOther.getOrganizationId()) || (this
						.getOrganizationId() != null
						&& castOther.getOrganizationId() != null && this
						.getOrganizationId().equals(
								castOther.getOrganizationId())))
				&& ((this.getFlag() == castOther.getFlag()) || (this.getFlag() != null
						&& castOther.getFlag() != null && this.getFlag()
						.equals(castOther.getFlag())))
				&& ((this.getA1() == castOther.getA1()) || (this.getA1() != null
						&& castOther.getA1() != null && this.getA1().equals(
						castOther.getA1())))
				&& ((this.getA2() == castOther.getA2()) || (this.getA2() != null
						&& castOther.getA2() != null && this.getA2().equals(
						castOther.getA2())))
				&& ((this.getA3() == castOther.getA3()) || (this.getA3() != null
						&& castOther.getA3() != null && this.getA3().equals(
						castOther.getA3())))
				&& ((this.getA4() == castOther.getA4()) || (this.getA4() != null
						&& castOther.getA4() != null && this.getA4().equals(
						castOther.getA4())))
				&& ((this.getA5() == castOther.getA5()) || (this.getA5() != null
						&& castOther.getA5() != null && this.getA5().equals(
						castOther.getA5())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getEntityName() == null ? 0 : this.getEntityName()
						.hashCode());
		result = 37 * result
				+ (getItemCode() == null ? 0 : this.getItemCode().hashCode());
		result = 37 * result
				+ (getItemDesc() == null ? 0 : this.getItemDesc().hashCode());
		result = 37 * result
				+ (getTzdCode() == null ? 0 : this.getTzdCode().hashCode());
		result = 37
				* result
				+ (getInvoiceDate() == null ? 0 : this.getInvoiceDate()
						.hashCode());
		result = 37
				* result
				+ (getToOperationNum() == null ? 0 : this.getToOperationNum()
						.hashCode());
		result = 37
				* result
				+ (getZrOperationNum() == null ? 0 : this.getZrOperationNum()
						.hashCode());
		result = 37 * result
				+ (getQaResult() == null ? 0 : this.getQaResult().hashCode());
		result = 37 * result
				+ (getQuantity() == null ? 0 : this.getQuantity().hashCode());
		result = 37 * result
				+ (getHour() == null ? 0 : this.getHour().hashCode());
		result = 37
				* result
				+ (getOutsourcingFee() == null ? 0 : this.getOutsourcingFee()
						.hashCode());
		result = 37 * result
				+ (getReson() == null ? 0 : this.getReson().hashCode());
		result = 37 * result
				+ (getFpType() == null ? 0 : this.getFpType().hashCode());
		result = 37 * result
				+ (getResponser() == null ? 0 : this.getResponser().hashCode());
		result = 37 * result
				+ (getVendor() == null ? 0 : this.getVendor().hashCode());
		result = 37 * result + (getGd() == null ? 0 : this.getGd().hashCode());
		result = 37 * result
				+ (getChecker() == null ? 0 : this.getChecker().hashCode());
		result = 37
				* result
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		result = 37
				* result
				+ (getOrganizationId() == null ? 0 : this.getOrganizationId()
						.hashCode());
		result = 37 * result
				+ (getFlag() == null ? 0 : this.getFlag().hashCode());
		result = 37 * result + (getA1() == null ? 0 : this.getA1().hashCode());
		result = 37 * result + (getA2() == null ? 0 : this.getA2().hashCode());
		result = 37 * result + (getA3() == null ? 0 : this.getA3().hashCode());
		result = 37 * result + (getA4() == null ? 0 : this.getA4().hashCode());
		result = 37 * result + (getA5() == null ? 0 : this.getA5().hashCode());
		return result;
	}

}