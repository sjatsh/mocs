package smtcl.mocs.pojos.erp;

 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisWipResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_WIP_RESOURCE" )
public class WisWipResource implements java.io.Serializable {

	// Fields

	private WisWipResourceId id;
	private Long resourceNum;
	private Long frozenCost;
	private String uomDesc;
	private Long usageRateOrAmount;
	private Long scheduledUnits;
	private Long organizationId;
	private Long flag;

	// Constructors

	/** default constructor */
	public WisWipResource() {
	}

	/** minimal constructor */
	public WisWipResource(WisWipResourceId id) {
		this.id = id;
	}

	/** full constructor */
	public WisWipResource(WisWipResourceId id, Long resourceNum,
			Long frozenCost, String uomDesc,
			Long usageRateOrAmount, Long scheduledUnits,
			Long organizationId, Long flag) {
		this.id = id;
		this.resourceNum = resourceNum;
		this.frozenCost = frozenCost;
		this.uomDesc = uomDesc;
		this.usageRateOrAmount = usageRateOrAmount;
		this.scheduledUnits = scheduledUnits;
		this.organizationId = organizationId;
		this.flag = flag;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "entityId", column = @Column(name = "ENTITY_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "operationNum", column = @Column(name = "OPERATION_NUM", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "resourceCode", column = @Column(name = "RESOURCE_CODE", nullable = false, length = 10)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisWipResourceId getId() {
		return this.id;
	}

	public void setId(WisWipResourceId id) {
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
	public Long getUsageRateOrAmount() {
		return this.usageRateOrAmount;
	}

	public void setUsageRateOrAmount(Long usageRateOrAmount) {
		this.usageRateOrAmount = usageRateOrAmount;
	}

	@Column(name = "SCHEDULED_UNITS", precision = 22, scale = 0)
	public Long getScheduledUnits() {
		return this.scheduledUnits;
	}

	public void setScheduledUnits(Long scheduledUnits) {
		this.scheduledUnits = scheduledUnits;
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

}