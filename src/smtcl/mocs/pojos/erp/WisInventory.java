package smtcl.mocs.pojos.erp;

 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisInventory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_INVENTORY" )
public class WisInventory implements java.io.Serializable {

	// Fields

	private WisInventoryId id;
	private String invName;
	private Long organizationId;
	private Long flag;
	private String a1;
	private String a2;

	// Constructors

	/** default constructor */
	public WisInventory() {
	}

	/** minimal constructor */
	public WisInventory(WisInventoryId id) {
		this.id = id;
	}

	/** full constructor */
	public WisInventory(WisInventoryId id, String invName,
			Long organizationId, Long flag, String a1, String a2) {
		this.id = id;
		this.invName = invName;
		this.organizationId = organizationId;
		this.flag = flag;
		this.a1 = a1;
		this.a2 = a2;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "invCode", column = @Column(name = "INV_CODE", nullable = false, length = 10)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisInventoryId getId() {
		return this.id;
	}

	public void setId(WisInventoryId id) {
		this.id = id;
	}

	@Column(name = "INV_NAME", length = 50)
	public String getInvName() {
		return this.invName;
	}

	public void setInvName(String invName) {
		this.invName = invName;
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

}