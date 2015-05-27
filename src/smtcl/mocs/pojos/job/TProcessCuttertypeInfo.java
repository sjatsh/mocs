package smtcl.mocs.pojos.job;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProcessCuttertypeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_process_cuttertype_info")
public class TProcessCuttertypeInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long processId;
	private Long cuttertypeId;
	private Integer requirementNum;
	private Integer status;

	// Constructors

	/** default constructor */
	public TProcessCuttertypeInfo() {
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

	@Column(name = "processID", length = 50)
	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Column(name = "cuttertypeID", length = 30)
	public Long getCuttertypeId() {
		return this.cuttertypeId;
	}

	public void setCuttertypeId(Long cuttertypeId) {
		this.cuttertypeId = cuttertypeId;
	}

	@Column(name = "requirementNum")
	public Integer getRequirementNum() {
		return this.requirementNum;
	}

	public void setRequirementNum(Integer requirementNum) {
		this.requirementNum = requirementNum;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

}