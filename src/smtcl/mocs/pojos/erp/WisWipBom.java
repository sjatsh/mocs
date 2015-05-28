package smtcl.mocs.pojos.erp;

 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisWipBom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_WIP_BOM" )
public class WisWipBom implements java.io.Serializable {

	// Fields

	private WisWipBomId id;
	private Long quantity;
	private String uomCode;
	private String uomDesc;
	private Long requiredQuantity;
	private Long wipSupplyType;
	private String supplySubinventory;
	private Long organizationId;
	private Long flag;

	// Constructors

	/** default constructor */
	public WisWipBom() {
	}

	/** minimal constructor */
	public WisWipBom(WisWipBomId id) {
		this.id = id;
	}

	/** full constructor */
	public WisWipBom(WisWipBomId id, Long quantity, String uomCode,
			String uomDesc, Long requiredQuantity,
			Long wipSupplyType, String supplySubinventory,
			Long organizationId, Long flag) {
		this.id = id;
		this.quantity = quantity;
		this.uomCode = uomCode;
		this.uomDesc = uomDesc;
		this.requiredQuantity = requiredQuantity;
		this.wipSupplyType = wipSupplyType;
		this.supplySubinventory = supplySubinventory;
		this.organizationId = organizationId;
		this.flag = flag;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "entityId", column = @Column(name = "ENTITY_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "operationNum", column = @Column(name = "OPERATION_NUM", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "componentCode", column = @Column(name = "COMPONENT_CODE", nullable = false, length = 240)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisWipBomId getId() {
		return this.id;
	}

	public void setId(WisWipBomId id) {
		this.id = id;
	}

	@Column(name = "QUANTITY", precision = 22, scale = 0)
	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Column(name = "UOM_CODE", length = 3)
	public String getUomCode() {
		return this.uomCode;
	}

	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	@Column(name = "UOM_DESC", length = 25)
	public String getUomDesc() {
		return this.uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	@Column(name = "REQUIRED_QUANTITY", precision = 22, scale = 0)
	public Long getRequiredQuantity() {
		return this.requiredQuantity;
	}

	public void setRequiredQuantity(Long requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	@Column(name = "WIP_SUPPLY_TYPE", precision = 22, scale = 0)
	public Long getWipSupplyType() {
		return this.wipSupplyType;
	}

	public void setWipSupplyType(Long wipSupplyType) {
		this.wipSupplyType = wipSupplyType;
	}

	@Column(name = "SUPPLY_SUBINVENTORY", length = 10)
	public String getSupplySubinventory() {
		return this.supplySubinventory;
	}

	public void setSupplySubinventory(String supplySubinventory) {
		this.supplySubinventory = supplySubinventory;
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