package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisEntityId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisEntityId implements java.io.Serializable {

	// Fields

	private Long entityId;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisEntityId() {
	}

	/** full constructor */
	public WisEntityId(Long entityId, Date uploadDate) {
		this.entityId = entityId;
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
		if (!(other instanceof WisEntityId))
			return false;
		WisEntityId castOther = (WisEntityId) other;

		return ((this.getEntityId() == castOther.getEntityId()) || (this
				.getEntityId() != null && castOther.getEntityId() != null && this
				.getEntityId().equals(castOther.getEntityId())))
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
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		return result;
	}

}