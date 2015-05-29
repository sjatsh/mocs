package smtcl.mocs.pojos.device;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_NODEID_MATCH")
public class TNodeidMatch implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nodeidwis;
	private String nodename;
	private String nodeida3;
	private String nodedatasource;
	@Id
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "NODEIDWIS",length=50)
	public String getNodeidwis() {
		return nodeidwis;
	}
	public void setNodeidwis(String nodeidwis) {
		this.nodeidwis = nodeidwis;
	}
	
	
	@Column(name = "NODENAME",length=255)
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	
	
	@Column(name = "NODEIDA3",length=50)
	public String getNodeida3() {
		return nodeida3;
	}
	public void setNodeida3(String nodeida3) {
		this.nodeida3 = nodeida3;
	}
	
	@Column(name = "NODEDATASOURCE",length=20)
	public String getNodedatasource() {
		return nodedatasource;
	}
	public void setNodedatasource(String nodedatasource) {
		this.nodedatasource = nodedatasource;
	}
	
	
}
