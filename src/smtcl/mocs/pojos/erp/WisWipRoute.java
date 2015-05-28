package smtcl.mocs.pojos.erp;

 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisWipRoute entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_WIP_ROUTE" )
public class WisWipRoute implements java.io.Serializable {

	// Fields

	private WisWipRouteId id;
	private String operationDesc;
	private String deptCode;
	private Long organizationId;
	private Long flag;

	// Constructors

	/** default constructor */
	public WisWipRoute() {
	}

	/** minimal constructor */
	public WisWipRoute(WisWipRouteId id) {
		this.id = id;
	}

	/** full constructor */
	public WisWipRoute(WisWipRouteId id, String operationDesc, String deptCode,
			Long organizationId, Long flag) {
		this.id = id;
		this.operationDesc = operationDesc;
		this.deptCode = deptCode;
		this.organizationId = organizationId;
		this.flag = flag;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "entityId", column = @Column(name = "ENTITY_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "operationNum", column = @Column(name = "OPERATION_NUM", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", nullable = false, length = 7)) })
	public WisWipRouteId getId() {
		return this.id;
	}

	public void setId(WisWipRouteId id) {
		this.id = id;
	}

	@Column(name = "OPERATION_DESC", length = 240)
	public String getOperationDesc() {
		return this.operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	@Column(name = "DEPT_CODE", length = 10)
	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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