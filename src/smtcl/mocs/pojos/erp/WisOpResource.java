package smtcl.mocs.pojos.erp;

 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisOpResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_OP_RESOURCE" )
public class WisOpResource implements java.io.Serializable {

	// Fields

	private WisOpResourceId id;
	private Long resourceNum;
	private Long frozenCost;
	private String uomDesc;
	private Double usageRateOrAmount;
	private Long organizationId;
	private Long flag;
	private Long seqId;

	// Constructors

	/** default constructor */
	public WisOpResource() {
	}

	/** minimal constructor */
	public WisOpResource(WisOpResourceId id) {
		this.id = id;
	}

	/** full constructor */
	public WisOpResource(WisOpResourceId id, Long resourceNum,
			Long frozenCost, String uomDesc,
			Double usageRateOrAmount, Long organizationId,
			Long flag, Long seqId) {
		this.id = id;
		this.resourceNum = resourceNum;
		this.frozenCost = frozenCost;
		this.uomDesc = uomDesc;
		this.usageRateOrAmount = usageRateOrAmount;
		this.organizationId = organizationId;
		this.flag = flag;
		this.seqId = seqId;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "assemblyItemId", column = @Column(name = "ASSEMBLY_ITEM_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "operationNum", column = @Column(name = "OPERATION_NUM", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "resourceCode", column = @Column(name = "RESOURCE_CODE", nullable = false, length = 10)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisOpResourceId getId() {
		return this.id;
	}

	public void setId(WisOpResourceId id) {
		this.id = id;
	}

	@Column(name = "RESOURCE_NUM", precision = 22, scale = 0)
	public Long getResourceNum() {
		return this.resourceNum;
	}

	public void setResourceNum(Long resourceNum) {
		this.resourceNum = resourceNum;
	}

	@Column(name = "FROZEN_COST", precision = 22, scale = 0)
	public Long getFrozenCost() {
		return this.frozenCost;
	}

	public void setFrozenCost(Long frozenCost) {
		this.frozenCost = frozenCost;
	}

	@Column(name = "UOM_DESC", length = 25)
	public String getUomDesc() {
		return this.uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	@Column(name = "USAGE_RATE_OR_AMOUNT", precision = 22, scale = 0)
	public Double getUsageRateOrAmount() {
		return this.usageRateOrAmount;
	}

	public void setUsageRateOrAmount(Double usageRateOrAmount) {
		this.usageRateOrAmount = usageRateOrAmount;
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

	@Column(name = "SEQ_ID", precision = 22, scale = 0)
	public Long getSeqId() {
		return this.seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

}