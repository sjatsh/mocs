package smtcl.mocs.pojos.erp;

 
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WisTransfer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_TRANSFER" )
public class WisTransfer implements java.io.Serializable {

	// Fields

	private WisTransferId id;
	private Long quantity;
	private Date startDate;
	private Date completionDate;
	private Long intraoperationStep;
	private Long cmpFlag;
	private String inventoryCode;
	private String bfAccount;
	private Long organizationId;
	private Long flag;
	private String errMsg;
	private String reqQaId;

	// Constructors

	/** default constructor */
	public WisTransfer() {
	}

	/** minimal constructor */
	public WisTransfer(WisTransferId id, Long quantity,
			Long intraoperationStep, Long cmpFlag,
			Long organizationId, Long flag) {
		this.id = id;
		this.quantity = quantity;
		this.intraoperationStep = intraoperationStep;
		this.cmpFlag = cmpFlag;
		this.organizationId = organizationId;
		this.flag = flag;
	}

	/** full constructor */
	public WisTransfer(WisTransferId id, Long quantity, Date startDate,
			Date completionDate, Long intraoperationStep,
			Long cmpFlag, String inventoryCode, String bfAccount,
			Long organizationId, Long flag, String errMsg) {
		this.id = id;
		this.quantity = quantity;
		this.startDate = startDate;
		this.completionDate = completionDate;
		this.intraoperationStep = intraoperationStep;
		this.cmpFlag = cmpFlag;
		this.inventoryCode = inventoryCode;
		this.bfAccount = bfAccount;
		this.organizationId = organizationId;
		this.flag = flag;
		this.errMsg = errMsg;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "entityName", column = @Column(name = "ENTITY_NAME", nullable = false, length = 240)),
			@AttributeOverride(name = "operationNum", column = @Column(name = "OPERATION_NUM", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "resourceCode", column = @Column(name = "RESOURCE_CODE", nullable = false, length = 10)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisTransferId getId() {
		return this.id;
	}

	public void setId(WisTransferId id) {
		this.id = id;
	}

	@Column(name = "QUANTITY", nullable = false, precision = 22, scale = 0)
	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMPLETION_DATE", length = 7)
	public Date getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	@Column(name = "INTRAOPERATION_STEP", nullable = false, precision = 22, scale = 0)
	public Long getIntraoperationStep() {
		return this.intraoperationStep;
	}

	public void setIntraoperationStep(Long intraoperationStep) {
		this.intraoperationStep = intraoperationStep;
	}

	@Column(name = "CMP_FLAG", nullable = false, precision = 22, scale = 0)
	public Long getCmpFlag() {
		return this.cmpFlag;
	}

	public void setCmpFlag(Long cmpFlag) {
		this.cmpFlag = cmpFlag;
	}

	@Column(name = "INVENTORY_CODE", length = 10)
	public String getInventoryCode() {
		return this.inventoryCode;
	}

	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}

	@Column(name = "BF_ACCOUNT", length = 240)
	public String getBfAccount() {
		return this.bfAccount;
	}

	public void setBfAccount(String bfAccount) {
		this.bfAccount = bfAccount;
	}

	@Column(name = "ORGANIZATION_ID", nullable = false, precision = 22, scale = 0)
	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "FLAG", nullable = false, precision = 22, scale = 0)
	public Long getFlag() {
		return this.flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	@Column(name = "ERR_MSG", length = 2000)
	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	@Column(name = "req_qa_id", length = 2000)
	public String getReqQaId() {
		return reqQaId;
	}

	public void setReqQaId(String reqQaId) {
		this.reqQaId = reqQaId;
	}

}