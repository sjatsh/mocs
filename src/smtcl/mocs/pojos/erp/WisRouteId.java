package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisRouteId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisRouteId implements java.io.Serializable {

	// Fields

	private Long assemblyItemId;
	private Long operationNum;
	private String operationDesc;
	private String departCode;
	private Date activeDate;
	private Date inactiveDate;
	private Long organizationId;
	private Long flag;
	private Date uploadDate;
	private Long seqId;

	// Constructors

	/** default constructor */
	public WisRouteId() {
	}

	/** full constructor */
	public WisRouteId(Long assemblyItemId, Long operationNum,
			String operationDesc, String departCode, Date activeDate,
			Date inactiveDate, Long organizationId, Long flag,
			Date uploadDate, Long seqId) {
		this.assemblyItemId = assemblyItemId;
		this.operationNum = operationNum;
		this.operationDesc = operationDesc;
		this.departCode = departCode;
		this.activeDate = activeDate;
		this.inactiveDate = inactiveDate;
		this.organizationId = organizationId;
		this.flag = flag;
		this.uploadDate = uploadDate;
		this.seqId = seqId;
	}

	// Property accessors

	@Column(name = "ASSEMBLY_ITEM_ID", precision = 22, scale = 0)
	public Long getAssemblyItemId() {
		return this.assemblyItemId;
	}

	public void setAssemblyItemId(Long assemblyItemId) {
		this.assemblyItemId = assemblyItemId;
	}

	@Column(name = "OPERATION_NUM", precision = 22, scale = 0)
	public Long getOperationNum() {
		return this.operationNum;
	}

	public void setOperationNum(Long operationNum) {
		this.operationNum = operationNum;
	}

	@Column(name = "OPERATION_DESC", length = 240)
	public String getOperationDesc() {
		return this.operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	@Column(name = "DEPART_CODE", length = 10)
	public String getDepartCode() {
		return this.departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ACTIVE_DATE", length = 7)
	public Date getActiveDate() {
		return this.activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INACTIVE_DATE", length = 7)
	public Date getInactiveDate() {
		return this.inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOAD_DATE", length = 7)
	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "SEQ_ID", precision = 22, scale = 0)
	public Long getSeqId() {
		return this.seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WisRouteId))
			return false;
		WisRouteId castOther = (WisRouteId) other;

		return ((this.getAssemblyItemId() == castOther.getAssemblyItemId()) || (this
				.getAssemblyItemId() != null
				&& castOther.getAssemblyItemId() != null && this
				.getAssemblyItemId().equals(castOther.getAssemblyItemId())))
				&& ((this.getOperationNum() == castOther.getOperationNum()) || (this
						.getOperationNum() != null
						&& castOther.getOperationNum() != null && this
						.getOperationNum().equals(castOther.getOperationNum())))
				&& ((this.getOperationDesc() == castOther.getOperationDesc()) || (this
						.getOperationDesc() != null
						&& castOther.getOperationDesc() != null && this
						.getOperationDesc()
						.equals(castOther.getOperationDesc())))
				&& ((this.getDepartCode() == castOther.getDepartCode()) || (this
						.getDepartCode() != null
						&& castOther.getDepartCode() != null && this
						.getDepartCode().equals(castOther.getDepartCode())))
				&& ((this.getActiveDate() == castOther.getActiveDate()) || (this
						.getActiveDate() != null
						&& castOther.getActiveDate() != null && this
						.getActiveDate().equals(castOther.getActiveDate())))
				&& ((this.getInactiveDate() == castOther.getInactiveDate()) || (this
						.getInactiveDate() != null
						&& castOther.getInactiveDate() != null && this
						.getInactiveDate().equals(castOther.getInactiveDate())))
				&& ((this.getOrganizationId() == castOther.getOrganizationId()) || (this
						.getOrganizationId() != null
						&& castOther.getOrganizationId() != null && this
						.getOrganizationId().equals(
								castOther.getOrganizationId())))
				&& ((this.getFlag() == castOther.getFlag()) || (this.getFlag() != null
						&& castOther.getFlag() != null && this.getFlag()
						.equals(castOther.getFlag())))
				&& ((this.getUploadDate() == castOther.getUploadDate()) || (this
						.getUploadDate() != null
						&& castOther.getUploadDate() != null && this
						.getUploadDate().equals(castOther.getUploadDate())))
				&& ((this.getSeqId() == castOther.getSeqId()) || (this
						.getSeqId() != null && castOther.getSeqId() != null && this
						.getSeqId().equals(castOther.getSeqId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getAssemblyItemId() == null ? 0 : this.getAssemblyItemId()
						.hashCode());
		result = 37
				* result
				+ (getOperationNum() == null ? 0 : this.getOperationNum()
						.hashCode());
		result = 37
				* result
				+ (getOperationDesc() == null ? 0 : this.getOperationDesc()
						.hashCode());
		result = 37
				* result
				+ (getDepartCode() == null ? 0 : this.getDepartCode()
						.hashCode());
		result = 37
				* result
				+ (getActiveDate() == null ? 0 : this.getActiveDate()
						.hashCode());
		result = 37
				* result
				+ (getInactiveDate() == null ? 0 : this.getInactiveDate()
						.hashCode());
		result = 37
				* result
				+ (getOrganizationId() == null ? 0 : this.getOrganizationId()
						.hashCode());
		result = 37 * result
				+ (getFlag() == null ? 0 : this.getFlag().hashCode());
		result = 37
				* result
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		result = 37 * result
				+ (getSeqId() == null ? 0 : this.getSeqId().hashCode());
		return result;
	}

}