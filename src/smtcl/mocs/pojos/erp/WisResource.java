package smtcl.mocs.pojos.erp;

 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_RESOURCE" )
public class WisResource implements java.io.Serializable {

	// Fields

	private WisResourceId id;
	private String resourceDesc;
	private Long resourceType;
	private Long rate;
	private String uomDesc;
	private Long chargeType;
	private String baseType;
	private Long organizationId;
	private Long flag;

	// Constructors

	/** default constructor */
	public WisResource() {
	}

	/** minimal constructor */
	public WisResource(WisResourceId id) {
		this.id = id;
	}

	/** full constructor */
	public WisResource(WisResourceId id, String resourceDesc,
			Long resourceType, Long rate, String uomDesc,
			Long chargeType, String baseType, Long organizationId,
			Long flag) {
		this.id = id;
		this.resourceDesc = resourceDesc;
		this.resourceType = resourceType;
		this.rate = rate;
		this.uomDesc = uomDesc;
		this.chargeType = chargeType;
		this.baseType = baseType;
		this.organizationId = organizationId;
		this.flag = flag;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "resourceCode", column = @Column(name = "RESOURCE_CODE", nullable = false, length = 10)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisResourceId getId() {
		return this.id;
	}

	public void setId(WisResourceId id) {
		this.id = id;
	}

	@Column(name = "RESOURCE_DESC", length = 240)
	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	@Column(name = "RESOURCE_TYPE", precision = 22, scale = 0)
	public Long getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "RATE", precision = 22, scale = 0)
	public Long getRate() {
		return this.rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}

	@Column(name = "UOM_DESC", length = 25)
	public String getUomDesc() {
		return this.uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	@Column(name = "CHARGE_TYPE", precision = 22, scale = 0)
	public Long getChargeType() {
		return this.chargeType;
	}

	public void setChargeType(Long chargeType) {
		this.chargeType = chargeType;
	}

	@Column(name = "BASE_TYPE", length = 1)
	public String getBaseType() {
		return this.baseType;
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType;
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