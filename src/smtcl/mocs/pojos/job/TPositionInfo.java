package smtcl.mocs.pojos.job;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPositionInfo entity. @author MyEclipse Persistence Tools
 */
/**
 * @author jack
 *
 */
@Entity
@Table(name = "t_position_info")
public class TPositionInfo implements java.io.Serializable {

	// Fields

	private Long positionId;
	private String positionName;
	private String nodeid;
	private String category;
	private String positionLevel;
	private Integer authorityLevel;
	private String attributes;
	private String functions;
	private Integer status;
	
	// Constructors

	/** default constructor */
	public TPositionInfo() {
	}

	/** minimal constructor */
	public TPositionInfo(Long positionId) {
		this.positionId = positionId;
	}

	/** full constructor */
	public TPositionInfo(Long positionId, String positionName,
			String nodeid, String category, String positionLevel,
			Integer authorityLevel, String attributes, String functions,Integer status) {
		this.positionId = positionId;
		this.positionName = positionName;
		this.nodeid = nodeid;
		this.category = category;
		this.positionLevel = positionLevel;
		this.authorityLevel = authorityLevel;
		this.attributes = attributes;
		this.functions = functions;
		this.status=status;
	}

	// Property accessors
	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_id", unique = true, nullable = false)
	public Long getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	@Column(name = "position_name", length = 20)
	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Column(name = "nodeid", length = 50)
	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	@Column(name = "category", length = 20)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "position_level", length = 20)
	public String getPositionLevel() {
		return this.positionLevel;
	}

	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}

	@Column(name = "authority_level")
	public Integer getAuthorityLevel() {
		return this.authorityLevel;
	}

	public void setAuthorityLevel(Integer authorityLevel) {
		this.authorityLevel = authorityLevel;
	}

	@Column(name = "attributes", length = 100)
	public String getAttributes() {
		return this.attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	@Column(name = "functions", length = 100)
	public String getFunctions() {
		return this.functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	@Column(name = "status", length = 100)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}