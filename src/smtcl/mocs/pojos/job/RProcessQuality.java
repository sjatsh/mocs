package smtcl.mocs.pojos.job;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * RProcessQuality entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_process_quality")
public class RProcessQuality implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long processId;
	private Long qualityId;

	// Constructors

	/** default constructor */
	public RProcessQuality() {
	}

	/** minimal constructor */
	public RProcessQuality(Long id) {
		this.id = id;
	}

	/** full constructor */
	public RProcessQuality(Long id, Long processId, Long qualityId) {
		this.id = id;
		this.processId = processId;
		this.qualityId = qualityId;
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

	@Column(name = "qualityID")
	public Long getQualityId() {
		return this.qualityId;
	}

	public void setQualityId(Long qualityId) {
		this.qualityId = qualityId;
	}

}