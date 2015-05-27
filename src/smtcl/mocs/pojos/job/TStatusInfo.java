package smtcl.mocs.pojos.job;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TStatusInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_status_info")
public class TStatusInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;

	// Constructors

	/** default constructor */
	public TStatusInfo() {
	}

	/** minimal constructor */
	public TStatusInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TStatusInfo(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}