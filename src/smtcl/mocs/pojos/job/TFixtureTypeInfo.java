package smtcl.mocs.pojos.job;
// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TFixturesInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_fixturetype_info")
public class TFixtureTypeInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String fixturesNo;
	private Long fixtureclassId;
	private String fixturesName;
	private String fixturesDescription;
	private String fixturesDrawing;
	private Integer status;
	private String nodeId;

	// Constructors

	/** default constructor */
	public TFixtureTypeInfo() {
	}

	/** minimal constructor */
	public TFixtureTypeInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TFixtureTypeInfo(Long id, String fixturesNo, String fixturesName,
			String fixturesDescription, String fixturesDrawing) {
		this.id = id;
		this.fixturesNo = fixturesNo;
		this.fixturesName = fixturesName;
		this.fixturesDescription = fixturesDescription;
		this.fixturesDrawing = fixturesDrawing;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "fixturesNo", length = 30)
	public String getFixturesNo() {
		return this.fixturesNo;
	}

	public void setFixturesNo(String fixturesNo) {
		this.fixturesNo = fixturesNo;
	}

	@Column(name = "fixturesName", length = 50)
	public String getFixturesName() {
		return this.fixturesName;
	}

	public void setFixturesName(String fixturesName) {
		this.fixturesName = fixturesName;
	}

	@Column(name = "fixturesDescription", length = 200)
	public String getFixturesDescription() {
		return this.fixturesDescription;
	}

	public void setFixturesDescription(String fixturesDescription) {
		this.fixturesDescription = fixturesDescription;
	}

	@Column(name = "fixturesDrawing", length = 200)
	public String getFixturesDrawing() {
		return this.fixturesDrawing;
	}

	public void setFixturesDrawing(String fixturesDrawing) {
		this.fixturesDrawing = fixturesDrawing;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "fixtureclassID")
	public Long getFixtureclassId() {
		return fixtureclassId;
	}

	public void setFixtureclassId(Long fixtureclassId) {
		this.fixtureclassId = fixtureclassId;
	}

	@Column(name = "nodeID")
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

}