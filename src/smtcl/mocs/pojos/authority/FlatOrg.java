package smtcl.mocs.pojos.authority;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author gaokun
 * @create Jul 12, 2011 11:02:23 AM
 */
@Table (name = "T_NODESFLAT")
@Entity
public class FlatOrg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "ID")
	private Long id;
	
	@Column (name = "NODE_ID")
	private String nodeId;
	
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the ancestorId
	 */
	public String getAncestorId() {
		return ancestorId;
	}

	/**
	 * @param ancestorId the ancestorId to set
	 */
	public void setAncestorId(String ancestorId) {
		this.ancestorId = ancestorId;
	}

	@Column (name = "ANCESTOR_ID")
	private String ancestorId;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
