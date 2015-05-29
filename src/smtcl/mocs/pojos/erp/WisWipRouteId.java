package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisWipRouteId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisWipRouteId implements java.io.Serializable {

	// Fields

	private Long entityId;
	private Long operationNum;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisWipRouteId() {
	}

	/** full constructor */
	public WisWipRouteId(Long entityId, Long operationNum,
			Date uploadDate) {
		this.entityId = entityId;
		this.operationNum = operationNum;
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
		if (!(other instanceof WisWipRouteId))
			return false;
		WisWipRouteId castOther = (WisWipRouteId) other;

		return ((this.getEntityId() == castOther.getEntityId()) || (this
				.getEntityId() != null && castOther.getEntityId() != null && this
				.getEntityId().equals(castOther.getEntityId())))
				&& ((this.getOperationNum() == castOther.getOperationNum()) || (this
						.getOperationNum() != null
						&& castOther.getOperationNum() != null && this
						.getOperationNum().equals(castOther.getOperationNum())))
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
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		return result;
	}

}