package smtcl.mocs.pojos.erp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisResourceId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisResourceId implements java.io.Serializable {

	// Fields

	private String resourceCode;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisResourceId() {
	}

	/** full constructor */
	public WisResourceId(String resourceCode, Date uploadDate) {
		this.resourceCode = resourceCode;
		this.uploadDate = uploadDate;
	}

	// Property accessors

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
		if (!(other instanceof WisResourceId))
			return false;
		WisResourceId castOther = (WisResourceId) other;

		return ((this.getResourceCode() == castOther.getResourceCode()) || (this
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