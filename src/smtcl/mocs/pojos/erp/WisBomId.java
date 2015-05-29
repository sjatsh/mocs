package smtcl.mocs.pojos.erp;

 
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * WisBomId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class WisBomId implements java.io.Serializable {

	// Fields

	private Long seqId;
	private Long itemId;
	private Long operationNum;
	private String componentCode;

	// Constructors

	/** default constructor */
	public WisBomId() {
	}

	/** full constructor */
	public WisBomId(Long seqId, Long itemId,
			Long operationNum, String componentCode) {
		this.seqId = seqId;
		this.itemId = itemId;
		this.operationNum = operationNum;
		this.componentCode = componentCode;
	}

	// Property accessors

	@Column(name = "SEQ_ID", nullable = false, precision = 22, scale = 0)
	public Long getSeqId() {
		return this.seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	@Column(name = "ITEM_ID", nullable = false, precision = 22, scale = 0)
	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WisBomId))
			return false;
		WisBomId castOther = (WisBomId) other;

		return ((this.getSeqId() == castOther.getSeqId()) || (this.getSeqId() != null
				&& castOther.getSeqId() != null && this.getSeqId().equals(
				castOther.getSeqId())))
				&& ((this.getItemId() == castOther.getItemId()) || (this
						.getItemId() != null && castOther.getItemId() != null && this
						.getItemId().equals(castOther.getItemId())))
				&& ((this.getOperationNum() == castOther.getOperationNum()) || (this
						.getOperationNum() != null
						&& castOther.getOperationNum() != null && this
						.getOperationNum().equals(castOther.getOperationNum())))
				&& ((this.getComponentCode() == castOther.getComponentCode()) || (this
						.getComponentCode() != null
						&& castOther.getComponentCode() != null && this
						.getComponentCode()
						.equals(castOther.getComponentCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSeqId() == null ? 0 : this.getSeqId().hashCode());
		result = 37 * result
				+ (getItemId() == null ? 0 : this.getItemId().hashCode());
		result = 37
				* result
				+ (getOperationNum() == null ? 0 : this.getOperationNum()
						.hashCode());
		result = 37
				* result
				+ (getComponentCode() == null ? 0 : this.getComponentCode()
						.hashCode());
		return result;
	}

}