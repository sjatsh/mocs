package smtcl.mocs.pojos.erp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisVendorId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisVendorId implements java.io.Serializable {

	// Fields

	private String vendorNum;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisVendorId() {
	}

	/** full constructor */
	public WisVendorId(String vendorNum, Date uploadDate) {
		this.vendorNum = vendorNum;
		this.uploadDate = uploadDate;
	}

	// Property accessors

	@Column(name = "VENDOR_NUM", nullable = false, length = 30)
	public String getVendorNum() {
		return this.vendorNum;
	}

	public void setVendorNum(String vendorNum) {
		this.vendorNum = vendorNum;
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
		if (!(other instanceof WisVendorId))
			return false;
		WisVendorId castOther = (WisVendorId) other;

		return ((this.getVendorNum() == castOther.getVendorNum()) || (this
				.getVendorNum() != null && castOther.getVendorNum() != null && this
				.getVendorNum().equals(castOther.getVendorNum())))
				&& ((this.getUploadDate() == castOther.getUploadDate()) || (this
						.getUploadDate() != null
						&& castOther.getUploadDate() != null && this
						.getUploadDate().equals(castOther.getUploadDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getVendorNum() == null ? 0 : this.getVendorNum().hashCode());
		result = 37
				* result
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		return result;
	}

}