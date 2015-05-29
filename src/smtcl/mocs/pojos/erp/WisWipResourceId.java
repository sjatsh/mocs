package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisWipResourceId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisWipResourceId implements java.io.Serializable {

	// Fields

	private Long entityId;
	private Long operationNum;
	private String resourceCode;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisWipResourceId() {
	}

	/** full constructor */
	public WisWipResourceId(Long entityId, Long operationNum,
			String resourceCode, Date uploadDate) {
		this.entityId = entityId;
		this.operationNum = operationNum;
		this.resourceCode = resourceCode;
		this.uploadDate = uploadDate;
	}

	// Property accessors

	@Column(name = "ENTITY_ID", nullable = false, precision = 22, scale = 0)
	public Long getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	@Column(name = "OPERATION_NUM", nullable = false, precision = 22, scale = 0)
	public Long getOperationNum() {
		return this.operationNum;
	}

	public void setOperationNum(Long operationNum) {
		this.operationNum = operationNum;
	}

	@Column(name = "RESOURCE_CODE", nullable = false, length = 10)
	public String getResourceCode() {
		return this.resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOAD_DATE", nullable = false, length = 7)
	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WisWipResourceId))
			return false;
		WisWipResourceId castOther = (WisWipResourceId) other;

		return ((this.getEntityId() == castOther.getEntityId()) || (this
				.getEntityId() != null && castOther.getEntityId() != null && this
				.getEntityId().equals(castOther.getEntityId())))
				&& ((this.getOperationNum() == castOther.getOperationNum()) || (this
						.getOperationNum() != null
						&& castOther.getOperationNum() != null && this
						.getOperationNum().equals(castOther.getOperationNum())))
				&& ((this.getResourceCode() == castOther.getResourceCode()) || (this
						.getResourceCode() != null
						&& castOther.getResourceCode() != null && this
						.getResourceCode().equals(castOther.getResourceCode())))
				&& ((this.getUploadDate() == castOther.getUploadDate()) || (this
						.getUploadDate() != null
						&& castOther.getUploadDate() != null && this
						.getUploadDate().equals(castOther.getUploadDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEntityId() == null ? 0 : this.getEntityId().hashCode());
		result = 37
				* result
				+ (getOperationNum() == null ? 0 : this.getOperationNum()
						.hashCode());
		result = 37
				* result
				+ (getResourceCode() == null ? 0 : this.getResourceCode()
						.hashCode());
		result = 37
				* result
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		return result;
	}

}