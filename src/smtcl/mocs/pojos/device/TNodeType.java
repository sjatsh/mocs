package smtcl.mocs.pojos.device;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TNodeType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_NODE_TYPE")
public class TNodeType implements java.io.Serializable {

	// Fields

	private String nodeclass;
	private String txt;
	private Long status;
	private Set<TNodes> TNodeses = new HashSet<TNodes>(0);

	// Constructors

	/** default constructor */
	public TNodeType() {
	}

	/** minimal constructor */
	public TNodeType(String nodeclass) {
		this.nodeclass = nodeclass;
	}

	/** full constructor */
	public TNodeType(String nodeclass, String txt, Long status,
			Set<TNodes> TNodeses) {
		this.nodeclass = nodeclass;
		this.txt = txt;
		this.status = status;
		this.TNodeses = TNodeses;
	}

	// Property accessors
	@Id
	@Column(name = "NODECLASS", unique = true, nullable = false, length = 32)
	public String getNodeclass() {
		return this.nodeclass;
	}

	public void setNodeclass(String nodeclass) {
		this.nodeclass = nodeclass;
	}

	@Column(name = "TXT", length = 50)
	public String getTxt() {
		return this.txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodeType")
	public Set<TNodes> getTNodeses() {
		return this.TNodeses;
	}

	public void setTNodeses(Set<TNodes> TNodeses) {
		this.TNodeses = TNodeses;
	}

}