package smtcl.mocs.pojos.job;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPartClassInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_part_class_info")
public class TPartClassInfo implements java.io.Serializable {

	// Fields
	private Long id;
	private String no;
	private String name;
	private String property;
	private String description;
	private Integer status;
	private String nodeid;
	// Constructors

	/** default constructor */
	public TPartClassInfo() {
	}

	/** full constructor */
	public TPartClassInfo(String no, String name, String property,
			String description) {
		this.no = no;
		this.name = name;
		this.property = property;
		this.description = description;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "no", length = 100)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "property", length = 100)
	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	
}