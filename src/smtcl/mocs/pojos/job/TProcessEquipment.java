package smtcl.mocs.pojos.job;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProcessEquipment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_process_equipment")
public class TProcessEquipment implements java.io.Serializable {

	// Fields

	private Long id;
	private Long processId;
	private Long equipmentId;
	private Long equipmentTypeId;
	// Constructors

	/** default constructor */
	public TProcessEquipment() {
	}

	/** full constructor */
	public TProcessEquipment(Long processId, Long equipmentId,
			Long equipmentTypeId) {
		this.processId = processId;
		this.equipmentId = equipmentId;
		this.equipmentTypeId = equipmentTypeId;
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

	@Column(name = "equipmentID")
	public Long getEquipmentId() {
		return this.equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	@Column(name = "equipmentTypeID")
	public Long getEquipmentTypeId() {
		return this.equipmentTypeId;
	}

	public void setEquipmentTypeId(Long equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}

}