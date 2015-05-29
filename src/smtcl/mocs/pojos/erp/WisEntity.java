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
 * WisEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_ENTITY" )
public class WisEntity implements java.io.Serializable {

	// Fields

	private WisEntityId id;
	private String entityName;
	private Long entityType;
	private String entityClass;
	private Long quantity;
	private Long assemblyItemId;
	private String itemDesc;
	private Date startDate;
	private Date completionDate;
	private Date releaseDate;
	private String inventoryCode;
	private Long flag;
	private Long organizationId;
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	private String a5;

	// Constructors

	/** default constructor */
	public WisEntity() {
	}

	/** minimal constructor */
	public WisEntity(WisEntityId id) {
		this.id = id;
	}

	/** full constructor */
	public WisEntity(WisEntityId id, String entityName, Long entityType,
			String entityClass, Long quantity, Long assemblyItemId,
			String itemDesc, Date startDate, Date completionDate,
			Date releaseDate, String inventoryCode, Long flag,
			Long organizationId, String a1, String a2, String a3,
			String a4, String a5) {
		this.id = id;
		this.entityName = entityName;
		this.entityType = entityType;
		this.entityClass = entityClass;
		this.quantity = quantity;
		this.assemblyItemId = assemblyItemId;
		this.itemDesc = itemDesc;
		this.startDate = startDate;
		this.completionDate = completionDate;
		this.releaseDate = releaseDate;
		this.inventoryCode = inventoryCode;
		this.flag = flag;
		this.organizationId = organizationId;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a5 = a5;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "entityId", column = @Column(name = "ENTITY_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisEntityId getId() {
		return this.id;
	}

	public void setId(WisEntityId id) {
		this.id = id;
	}

	@Column(name = "ENTITY_NAME", length = 240)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "ENTITY_TYPE", precision = 22, scale = 0)
	public Long getEntityType() {
		return this.entityType;
	}

	public void setEntityType(Long entityType) {
		this.entityType = entityType;
	}

	@Column(name = "ENTITY_CLASS", length = 40)
	public String getEntityClass() {
		return this.entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	@Column(name = "QUANTITY", precision = 22, scale = 0)
	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Column(name = "ASSEMBLY_ITEM_ID", precision = 22, scale = 0)
	public Long getAssemblyItemId() {
		return this.assemblyItemId;
	}

	public void setAssemblyItemId(Long assemblyItemId) {
		this.assemblyItemId = assemblyItemId;
	}

	@Column(name = "ITEM_DESC", length = 240)
	public String getItemDesc() {
		return this.itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COMPLETION_DATE", length = 7)
	public Date getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RELEASE_DATE", length = 7)
	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Column(name = "INVENTORY_CODE", length = 40)
	public String getInventoryCode() {
		return this.inventoryCode;
	}

	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}

	@Column(name = "FLAG", precision = 22, scale = 0)
	public Long getFlag() {
		return this.flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	@Column(name = "ORGANIZATION_ID", precision = 22, scale = 0)
	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "A1", length = 240)
	public String getA1() {
		return this.a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	@Column(name = "A2", length = 240)
	public String getA2() {
		return this.a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	@Column(name = "A3", length = 240)
	public String getA3() {
		return this.a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	@Column(name = "A4", length = 240)
	public String getA4() {
		return this.a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	@Column(name = "A5", length = 240)
	public String getA5() {
		return this.a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

}