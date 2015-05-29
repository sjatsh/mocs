package smtcl.mocs.pojos.erp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisQaCheck entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_QA_CHECK" )
public class WisQaCheck implements java.io.Serializable {

	// Fields

	private WisQaCheckId id;

	// Constructors

	/** default constructor */
	public WisQaCheck() {
	}

	/** full constructor */
	public WisQaCheck(WisQaCheckId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "itemCode", column = @Column(name = "ITEM_CODE", length = 240)),
			@AttributeOverride(name = "itemDesc", column = @Column(name = "ITEM_DESC", length = 240)),
			@AttributeOverride(name = "entityName", column = @Column(name = "ENTITY_NAME", length = 240)),
			@AttributeOverride(name = "quantity", column = @Column(name = "QUANTITY", precision = 22, scale = 0)),
			@AttributeOverride(name = "okFlag", column = @Column(name = "OK_FLAG", precision = 22, scale = 0)),
			@AttributeOverride(name = "department", column = @Column(name = "DEPARTMENT", length = 240)),
			@AttributeOverride(name = "jgCheck", column = @Column(name = "JG_CHECK", length = 240)),
			@AttributeOverride(name = "zpCheck", column = @Column(name = "ZP_CHECK", length = 240)),
			@AttributeOverride(name = "sjCheck", column = @Column(name = "SJ_CHECK", length = 240)),
			@AttributeOverride(name = "creator", column = @Column(name = "CREATOR", length = 240)),
			@AttributeOverride(name = "writeDate", column = @Column(name = "WRITE_DATE", length = 20)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", length = 7)),
			@AttributeOverride(name = "organizationId", column = @Column(name = "ORGANIZATION_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "flag", column = @Column(name = "FLAG", precision = 22, scale = 0)),
			@AttributeOverride(name = "a1", column = @Column(name = "A1", length = 240)),
			@AttributeOverride(name = "a2", column = @Column(name = "A2", length = 240)),
			@AttributeOverride(name = "a3", column = @Column(name = "A3", length = 240)),
			@AttributeOverride(name = "a4", column = @Column(name = "A4", length = 240)),
			@AttributeOverride(name = "a5", column = @Column(name = "A5", length = 240)) })
	public WisQaCheckId getId() {
		return this.id;
	}

	public void setId(WisQaCheckId id) {
		this.id = id;
	}

}