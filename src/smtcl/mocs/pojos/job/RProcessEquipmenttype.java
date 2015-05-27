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
 * RProcessEquipmenttype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_process_equipmenttype")
public class RProcessEquipmenttype implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TEquipmenttypeInfo TEquipmenttypeInfo;
	private TProcessInfo TProcessInfo;

	// Constructors

	/** default constructor */
	public RProcessEquipmenttype() {
	}

	/** minimal constructor */
	public RProcessEquipmenttype(Long id) {
		this.id = id;
	}

	/** full constructor */
	public RProcessEquipmenttype(Long id,
			TEquipmenttypeInfo TEquipmenttypeInfo, TProcessInfo TProcessInfo) {
		this.id = id;
		this.TEquipmenttypeInfo = TEquipmenttypeInfo;
		this.TProcessInfo = TProcessInfo;
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
	@JoinColumn(name = "equipmentID")
	public TEquipmenttypeInfo getTEquipmenttypeInfo() {
		return this.TEquipmenttypeInfo;
	}

	public void setTEquipmenttypeInfo(TEquipmenttypeInfo TEquipmenttypeInfo) {
		this.TEquipmenttypeInfo = TEquipmenttypeInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processID")
	public TProcessInfo getTProcessInfo() {
		return this.TProcessInfo;
	}

	public void setTProcessInfo(TProcessInfo TProcessInfo) {
		this.TProcessInfo = TProcessInfo;
	}

}