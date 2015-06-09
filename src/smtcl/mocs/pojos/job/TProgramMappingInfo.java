package smtcl.mocs.pojos.job;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProgramMappingInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_program_mapping_info")
public class TProgramMappingInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer programId;
	private Integer materailId;
	private Integer processId;
	private String isMainProgram;
	private String isActivity;

	// Constructors

	/** default constructor */
	public TProgramMappingInfo() {
	}

	/** full constructor */
	public TProgramMappingInfo(Integer programId, Integer materailId,
			Integer processId, String isMainProgram, String isActivity) {
		this.programId = programId;
		this.materailId = materailId;
		this.processId = processId;
		this.isMainProgram = isMainProgram;
		this.isActivity = isActivity;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "programId")
	public Integer getProgramId() {
		return this.programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	@Column(name = "materailId")
	public Integer getMaterailId() {
		return this.materailId;
	}

	public void setMaterailId(Integer materailId) {
		this.materailId = materailId;
	}

	@Column(name = "processId")
	public Integer getProcessId() {
		return this.processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	@Column(name = "isMainProgram", length = 11)
	public String getIsMainProgram() {
		return this.isMainProgram;
	}

	public void setIsMainProgram(String isMainProgram) {
		this.isMainProgram = isMainProgram;
	}

	@Column(name = "isActivity", length = 11)
	public String getIsActivity() {
		return this.isActivity;
	}

	public void setIsActivity(String isActivity) {
		this.isActivity = isActivity;
	}

}