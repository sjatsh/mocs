package smtcl.mocs.pojos.job;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProcessFixturetype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_process_fixturetype")
public class TProcessFixturetype implements java.io.Serializable {

	// Fields

	private Long id;
	private Long processId;
	private Long fixturetypeId;

	// Constructors

	/** default constructor */
	public TProcessFixturetype() {
	}

	/** full constructor */
	public TProcessFixturetype(Long processId, Long fixturetypeId) {
		this.processId = processId;
		this.fixturetypeId = fixturetypeId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "processID")
	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Column(name = "fixturetypeID")
	public Long getFixturetypeId() {
		return this.fixturetypeId;
	}

	public void setFixturetypeId(Long fixturetypeId) {
		this.fixturetypeId = fixturetypeId;
	}

}