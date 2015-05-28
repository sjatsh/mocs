package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisQaCheckId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisQaCheckId implements java.io.Serializable {

	// Fields

	private String itemCode;
	private String itemDesc;
	private String entityName;
	private Long quantity;
	private Long okFlag;
	private String department;
	private String jgCheck;
	private String zpCheck;
	private String sjCheck;
	private String creator;
	private String writeDate;
	private Date uploadDate;
	private Long organizationId;
	private Long flag;
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	private String a5;

	// Constructors

	/** default constructor */
	public WisQaCheckId() {
	}

	/** full constructor */
	public WisQaCheckId(String itemCode, String itemDesc, String entityName,
			Long quantity, Long okFlag, String department,
			String jgCheck, String zpCheck, String sjCheck, String creator,
			String writeDate, Date uploadDate, Long organizationId,
			Long flag, String a1, String a2, String a3, String a4,
			String a5) {
		this.itemCode = itemCode;
		this.itemDesc = itemDesc;
		this.entityName = entityName;
		this.quantity = quantity;
		this.okFlag = okFlag;
		this.department = department;
		this.jgCheck = jgCheck;
		this.zpCheck = zpCheck;
		this.sjCheck = sjCheck;
		this.creator = creator;
		this.writeDate = writeDate;
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

	@Column(name = "ENTITY_NAME", length = 240)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "QUANTITY", precision = 22, scale = 0)
	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Column(name = "OK_FLAG", precision = 22, scale = 0)
	public Long getOkFlag() {
		return this.okFlag;
	}

	public void setOkFlag(Long okFlag) {
		this.okFlag = okFlag;
	}

	@Column(name = "DEPARTMENT", length = 240)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "JG_CHECK", length = 240)
	public String getJgCheck() {
		return this.jgCheck;
	}

	public void setJgCheck(String jgCheck) {
		this.jgCheck = jgCheck;
	}

	@Column(name = "ZP_CHECK", length = 240)
	public String getZpCheck() {
		return this.zpCheck;
	}

	public void setZpCheck(String zpCheck) {
		this.zpCheck = zpCheck;
	}

	@Column(name = "SJ_CHECK", length = 240)
	public String getSjCheck() {
		return this.sjCheck;
	}

	public void setSjCheck(String sjCheck) {
		this.sjCheck = sjCheck;
	}

	@Column(name = "CREATOR", length = 240)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "WRITE_DATE", length = 20)
	public String getWriteDate() {
		return this.writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WisQaCheckId))
			return false;
		WisQaCheckId castOther = (WisQaCheckId) other;

		return ((this.getItemCode() == castOther.getItemCode()) || (this
				.getItemCode() != null && castOther.getItemCode() != null && this
				.getItemCode().equals(castOther.getItemCode())))
				&& ((this.getItemDesc() == castOther.getItemDesc()) || (this
						.getItemDesc() != null
						&& castOther.getItemDesc() != null && this
						.getItemDesc().equals(castOther.getItemDesc())))
				&& ((this.getEntityName() == castOther.getEntityName()) || (this
						.getEntityName() != null
						&& castOther.getEntityName() != null && this
						.getEntityName().equals(castOther.getEntityName())))
				&& ((this.getQuantity() == castOther.getQuantity()) || (this
						.getQuantity() != null
						&& castOther.getQuantity() != null && this
						.getQuantity().equals(castOther.getQuantity())))
				&& ((this.getOkFlag() == castOther.getOkFlag()) || (this
						.getOkFlag() != null && castOther.getOkFlag() != null && this
						.getOkFlag().equals(castOther.getOkFlag())))
				&& ((this.getDepartment() == castOther.getDepartment()) || (this
						.getDepartment() != null
						&& castOther.getDepartment() != null && this
						.getDepartment().equals(castOther.getDepartment())))
				&& ((this.getJgCheck() == castOther.getJgCheck()) || (this
						.getJgCheck() != null && castOther.getJgCheck() != null && this
						.getJgCheck().equals(castOther.getJgCheck())))
				&& ((this.getZpCheck() == castOther.getZpCheck()) || (this
						.getZpCheck() != null && castOther.getZpCheck() != null && this
						.getZpCheck().equals(castOther.getZpCheck())))
				&& ((this.getSjCheck() == castOther.getSjCheck()) || (this
						.getSjCheck() != null && castOther.getSjCheck() != null && this
						.getSjCheck().equals(castOther.getSjCheck())))
				&& ((this.getCreator() == castOther.getCreator()) || (this
						.getCreator() != null && castOther.getCreator() != null && this
						.getCreator().equals(castOther.getCreator())))
				&& ((this.getWriteDate() == castOther.getWriteDate()) || (this
						.getWriteDate() != null
						&& castOther.getWriteDate() != null && this
						.getWriteDate().equals(castOther.getWriteDate())))
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

		result = 37 * result
				+ (getItemCode() == null ? 0 : this.getItemCode().hashCode());
		result = 37 * result
				+ (getItemDesc() == null ? 0 : this.getItemDesc().hashCode());
		result = 37
				* result
				+ (getEntityName() == null ? 0 : this.getEntityName()
						.hashCode());
		result = 37 * result
				+ (getQuantity() == null ? 0 : this.getQuantity().hashCode());
		result = 37 * result
				+ (getOkFlag() == null ? 0 : this.getOkFlag().hashCode());
		result = 37
				* result
				+ (getDepartment() == null ? 0 : this.getDepartment()
						.hashCode());
		result = 37 * result
				+ (getJgCheck() == null ? 0 : this.getJgCheck().hashCode());
		result = 37 * result
				+ (getZpCheck() == null ? 0 : this.getZpCheck().hashCode());
		result = 37 * result
				+ (getSjCheck() == null ? 0 : this.getSjCheck().hashCode());
		result = 37 * result
				+ (getCreator() == null ? 0 : this.getCreator().hashCode());
		result = 37 * result
				+ (getWriteDate() == null ? 0 : this.getWriteDate().hashCode());
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