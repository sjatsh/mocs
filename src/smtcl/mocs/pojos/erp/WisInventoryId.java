package smtcl.mocs.pojos.erp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisInventoryId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisInventoryId implements java.io.Serializable {

	// Fields

	private String invCode;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisInventoryId() {
	}

	/** full constructor */
	public WisInventoryId(String invCode, Date uploadDate) {
		this.invCode = invCode;
		this.uploadDate = uploadDate;
	}

	// Property accessors

	@Column(name = "INV_CODE", nullable = false, length = 10)
	public String getInvCode() {
		return this.invCode;
	}

	public void setInvCode(String invCode) {
		this.invCode = invCode;
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
		if (!(other instanceof WisInventoryId))
			return false;
		WisInventoryId castOther = (WisInventoryId) other;

		return ((this.getInvCode() == castOther.getInvCode()) || (this
				.getInvCode() != null && castOther.getInvCode() != null && this
				.getInvCode().equals(castOther.getInvCode())))
				&& ((this.getUploadDate() == castOther.getUploadDate()) || (this
						.getUploadDate() != null
						&& castOther.getUploadDate() != null && this
						.getUploadDate().equals(castOther.getUploadDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getInvCode() == null ? 0 : this.getInvCode().hashCode());
		result = 37
				* result
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		return result;
	}

}