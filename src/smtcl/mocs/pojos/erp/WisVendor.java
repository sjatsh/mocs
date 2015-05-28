package smtcl.mocs.pojos.erp;

 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisVendor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_VENDOR" )
public class WisVendor implements java.io.Serializable {

	// Fields

	private WisVendorId id;
	private String vendorName;
	private Long orgId;
	private Long flag;
	private String a1;
	private String a2;

	// Constructors

	/** default constructor */
	public WisVendor() {
	}

	/** minimal constructor */
	public WisVendor(WisVendorId id) {
		this.id = id;
	}

	/** full constructor */
	public WisVendor(WisVendorId id, String vendorName, Long orgId,
			Long flag, String a1, String a2) {
		this.id = id;
		this.vendorName = vendorName;
		this.orgId = orgId;
		this.flag = flag;
		this.a1 = a1;
		this.a2 = a2;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "vendorNum", column = @Column(name = "VENDOR_NUM", nullable = false, length = 30)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisVendorId getId() {
		return this.id;
	}

	public void setId(WisVendorId id) {
		this.id = id;
	}

	@Column(name = "VENDOR_NAME", length = 240)
	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name = "ORG_ID", precision = 22, scale = 0)
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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