package smtcl.mocs.pojos.erp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisRoute entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_ROUTE" )
public class WisRoute implements java.io.Serializable {

	// Fields

	private WisRouteId id;

	// Constructors

	/** default constructor */
	public WisRoute() {
	}

	/** full constructor */
	public WisRoute(WisRouteId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "assemblyItemId", column = @Column(name = "ASSEMBLY_ITEM_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "operationNum", column = @Column(name = "OPERATION_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "operationDesc", column = @Column(name = "OPERATION_DESC", length = 240)),
			@AttributeOverride(name = "departCode", column = @Column(name = "DEPART_CODE", length = 10)),
			@AttributeOverride(name = "activeDate", column = @Column(name = "ACTIVE_DATE", length = 7)),
			@AttributeOverride(name = "inactiveDate", column = @Column(name = "INACTIVE_DATE", length = 7)),
			@AttributeOverride(name = "organizationId", column = @Column(name = "ORGANIZATION_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "flag", column = @Column(name = "FLAG", precision = 22, scale = 0)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", length = 7)),
			@AttributeOverride(name = "seqId", column = @Column(name = "SEQ_ID", precision = 22, scale = 0)) })
	public WisRouteId getId() {
		return this.id;
	}

	public void setId(WisRouteId id) {
		this.id = id;
	}

}