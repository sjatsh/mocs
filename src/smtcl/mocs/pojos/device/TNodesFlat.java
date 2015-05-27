package smtcl.mocs.pojos.device;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TNodesFlat entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_NodesFlat")
public class TNodesFlat implements java.io.Serializable {

	// Fields

	private Long id;
	private String nodeId;
	private String ancestorId;

	// Constructors

	/** default constructor */
	public TNodesFlat() {
	}

	/** minimal constructor */
	public TNodesFlat(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TNodesFlat(Long id, String nodeId, String ancestorId) {
		this.id = id;
		this.nodeId = nodeId;
		this.ancestorId = ancestorId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "node_id", length = 50)
	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "ANCESTOR_ID",length=50)
	public String getAncestorId() {
		return this.ancestorId;
	}

	public void setAncestorId(String ancestorId) {
		this.ancestorId = ancestorId;
	}

}