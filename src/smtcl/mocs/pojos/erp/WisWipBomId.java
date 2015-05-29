package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisWipBomId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisWipBomId implements java.io.Serializable {

	// Fields

	private Long entityId;
	private Long operationNum;
	private String componentCode;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisWipBomId() {
	}

	/** full constructor */
	public WisWipBomId(Long entityId, Long operationNum,
			String componentCode, Date uploadDate) {
		this.entityId = entityId;
		this.operationNum = operationNum;
		this.componentCode = componentCode;
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

	@Column(name = "COMPONENT_CODE", nullable = false, length = 240)
	public String getComponentCode() {
		return this.componentCode;
	}

	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
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
		if (!(other instanceof WisWipBomId))
			return false;
		WisWipBomId castOther = (WisWipBomId) other;

		return ((this.getEntityId() == castOther.getEntityId()) || (this
				.getEntityId() != null && castOther.getEntityId() != null && this
				.getEntityId().equals(castOther.getEntityId())))
				&& ((this.getOperationNum() == castOther.getOperationNum()) || (this
						.getOperationNum() != null
						&& castOther.getOperationNum() != null && this
						.getOperationNum().equals(castOther.getOperationNum())))
				&& ((this.getComponentCode() == castOther.getComponentCode()) || (this
						.getComponentCode() != null
						&& castOther.getComponentCode() != null && this
						.getComponentCode()
						.equals(castOther.getComponentCode())))
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
				+ (getComponentCode() == null ? 0 : this.getComponentCode()
						.hashCode());
		result = 37
				* result
				+ (getUploadDate() == null ? 0 : this.getUploadDate()
						.hashCode());
		return result;
	}

}