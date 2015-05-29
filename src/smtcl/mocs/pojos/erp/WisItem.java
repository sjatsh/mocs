package smtcl.mocs.pojos.erp;

 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_ITEM" )
public class WisItem implements java.io.Serializable {

	// Fields

	private WisItemId id;
	private String itemCode;
	private String itemDesc;
	private Long planningMakeBuyCode;
	private String uomCode;
	private String uomDesc;
	private Long categoryId;
	private String categoryDesc;
	private Long organizationId;
	private Long flag;
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	private String a5;

	// Constructors

	/** default constructor */
	public WisItem() {
	}

	/** minimal constructor */
	public WisItem(WisItemId id) {
		this.id = id;
	}

	/** full constructor */
	public WisItem(WisItemId id, String itemCode, String itemDesc,
			Long planningMakeBuyCode, String uomCode, String uomDesc,
			Long categoryId, String categoryDesc,
			Long organizationId, Long flag, String a1, String a2,
			String a3, String a4, String a5) {
		this.id = id;
		this.itemCode = itemCode;
		this.itemDesc = itemDesc;
		this.planningMakeBuyCode = planningMakeBuyCode;
		this.uomCode = uomCode;
		this.uomDesc = uomDesc;
		this.categoryId = categoryId;
		this.categoryDesc = categoryDesc;
		this.organizationId = organizationId;
		this.flag = flag;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a5 = a5;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "itemId", column = @Column(name = "ITEM_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisItemId getId() {
		return this.id;
	}

	public void setId(WisItemId id) {
		this.id = id;
	}

	@Column(name = "ITEM_CODE", length = 240)
	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "ITEM_DESC", length = 240)
	public String getItemDesc() {
		return this.itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Column(name = "PLANNING_MAKE_BUY_CODE", precision = 22, scale = 0)
	public Long getPlanningMakeBuyCode() {
		return this.planningMakeBuyCode;
	}

	public void setPlanningMakeBuyCode(Long planningMakeBuyCode) {
		this.planningMakeBuyCode = planningMakeBuyCode;
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

	@Column(name = "CATEGORY_ID", precision = 22, scale = 0)
	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "CATEGORY_DESC", length = 240)
	public String getCategoryDesc() {
		return this.categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
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