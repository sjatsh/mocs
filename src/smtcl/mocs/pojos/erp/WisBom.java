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
 * WisBom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_BOM" )
public class WisBom implements java.io.Serializable {

	// Fields

	private WisBomId id;
	private Long itemNum;
	private String componentDesc;
	private Long componentCost;
	private Long componentQuantity;
	private Long expCost;
	private Date dateFrom;
	private Date dateTo;
	private String uomCode;
	private Long organizationId;
	private Long flag;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public WisBom() {
	}

	/** minimal constructor */
	public WisBom(WisBomId id) {
		this.id = id;
	}

	/** full constructor */
	public WisBom(WisBomId id, Long itemNum, String componentDesc,
			Long componentCost, Long componentQuantity,
			Long expCost, Date dateFrom, Date dateTo, String uomCode,
			Long organizationId, Long flag, Date uploadDate) {
		this.id = id;
		this.itemNum = itemNum;
		this.componentDesc = componentDesc;
		this.componentCost = componentCost;
		this.componentQuantity = componentQuantity;
		this.expCost = expCost;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.uomCode = uomCode;
		this.organizationId = organizationId;
		this.flag = flag;
		this.uploadDate = uploadDate;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "seqId", column = @Column(name = "SEQ_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "itemId", column = @Column(name = "ITEM_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "operationNum", column = @Column(name = "OPERATION_NUM", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "componentCode", column = @Column(name = "COMPONENT_CODE", nullable = false, length = 240)) })
	public WisBomId getId() {
		return this.id;
	}

	public void setId(WisBomId id) {
		this.id = id;
	}

	@Column(name = "ITEM_NUM", precision = 22, scale = 0)
	public Long getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

	@Column(name = "COMPONENT_DESC", length = 240)
	public String getComponentDesc() {
		return this.componentDesc;
	}

	public void setComponentDesc(String componentDesc) {
		this.componentDesc = componentDesc;
	}

	@Column(name = "COMPONENT_COST", precision = 22, scale = 0)
	public Long getComponentCost() {
		return this.componentCost;
	}

	public void setComponentCost(Long componentCost) {
		this.componentCost = componentCost;
	}

	@Column(name = "COMPONENT_QUANTITY", precision = 22, scale = 0)
	public Long getComponentQuantity() {
		return this.componentQuantity;
	}

	public void setComponentQuantity(Long componentQuantity) {
		this.componentQuantity = componentQuantity;
	}

	@Column(name = "EXP_COST", precision = 22, scale = 0)
	public Long getExpCost() {
		return this.expCost;
	}

	public void setExpCost(Long expCost) {
		this.expCost = expCost;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_FROM", length = 7)
	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_TO", length = 7)
	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Column(name = "UOM_CODE", length = 3)
	public String getUomCode() {
		return this.uomCode;
	}

	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	@Column(name = "ORGANIZATION_ID", precision = 22, scale = 0)
	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "FLAG", precision = 22, scale = 0)
	public Long getFlag() {
		return this.flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOAD_DATE", length = 7)
	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}