package smtcl.mocs.pojos.job;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCuttertypeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cutterclass_info")
public class TCutterClassInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer status;	
	private String nodeId;

	// Constructors

	/** default constructor */
	public TCutterClassInfo() {
	}

	/** minimal constructor */
	public TCutterClassInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TCutterClassInfo(Long id, String name, Integer status) {
		this.id = id;
		this.name = name;
		this.status = status;	
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "status", length = 30)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "nodeID")
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}