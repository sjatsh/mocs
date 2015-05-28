package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisItemId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisItemId implements java.io.Serializable {

	// Fields

	private Long itemId;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisItemId() {
	}

	/** full constructor */
	public WisItemId(Long itemId, Date uploadDate) {
		this.itemId = itemId;
		this.uploadDate = uploadDate;
	}

	// Property accessors

	@Column(name = "ITEM_ID", nullable = false, precision = 22, scale = 0)
	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
		if (!(other instanceof WisItemId))
			return false;
		WisItemId castOther = (WisItemId) other;

		return ((this.getItemId() == castOther.getItemId()) || (this
				.getItemId() != null && castOther.getItemId() != null && this
				.getItemId().equals(castOther.getItemId())))
				&& ((this.getUploadDate() == castOther.getUploadDate()) || (this
						.getUploadDate() != null
						&& castOther.getUploadDate() != null && this
						.getUploadDate().equals(castOther.getUploadDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getItemId() == null ? 0 : this.getItemId().hashCode());
		result = 37
				* result
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		return result;
	}

}