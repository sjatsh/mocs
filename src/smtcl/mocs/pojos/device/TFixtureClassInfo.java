package smtcl.mocs.pojos.device;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TUserFixture entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_fixtureclass_info")
public class TFixtureClassInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private Integer status;
	private String nodeId;
	
	public TFixtureClassInfo(){
		
	}

	public TFixtureClassInfo(Long id, String name, Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "status")
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