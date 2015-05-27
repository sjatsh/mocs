package smtcl.mocs.pojos.job;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TProcessmaterialInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_ProcessMaterial_info")
public class TProcessmaterialInfo implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private Long id;
	private TProcessInfo TProcessInfo;
	private TMaterailTypeInfo TMaterailTypeInfo;
	private Long materialId;
	private Integer requirementNum;
	private String requirementType;
	private Integer status;

	// Constructors

	/** default constructor */
	public TProcessmaterialInfo() {
	}

	/** minimal constructor */
	public TProcessmaterialInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TProcessmaterialInfo(Long id, TProcessInfo TProcessInfo,
			TMaterailTypeInfo TMaterailTypeInfo, Long materialId,
			Integer requirementNum, String requirementType) {
		this.id = id;
		this.TProcessInfo = TProcessInfo;
		this.TMaterailTypeInfo = TMaterailTypeInfo;
		this.materialId = materialId;
		this.requirementNum = requirementNum;
		this.requirementType = requirementType;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processID")
	public TProcessInfo getTProcessInfo() {
		return this.TProcessInfo;
	}

	public void setTProcessInfo(TProcessInfo TProcessInfo) {
		this.TProcessInfo = TProcessInfo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "materialTypeID")
	public TMaterailTypeInfo getTMaterailTypeInfo() {
		return this.TMaterailTypeInfo;
	}

	public void setTMaterailTypeInfo(TMaterailTypeInfo TMaterailTypeInfo) {
		this.TMaterailTypeInfo = TMaterailTypeInfo;
	}

	@Column(name = "materialID")
	public Long getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	@Column(name = "requirementNum")
	public Integer getRequirementNum() {
		return this.requirementNum;
	}

	public void setRequirementNum(Integer requirementNum) {
		this.requirementNum = requirementNum;
	}

	@Column(name = "requirementType", length = 10)
	public String getRequirementType() {
		return this.requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}