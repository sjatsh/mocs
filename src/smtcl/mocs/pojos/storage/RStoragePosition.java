package smtcl.mocs.pojos.storage;

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
 * KC_204 库房货位关联表
 */
@Entity
@Table(name = "r_storage_position")
public class RStoragePosition implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private TMaterielPositionInfo TMaterielPositionInfo;//库房ID
	private TStorageInfo TStorageInfo;//货位ID

	// Constructors

	/** default constructor */
	public RStoragePosition() {
	}

	/** full constructor */
	public RStoragePosition(TMaterielPositionInfo TMaterielPositionInfo,
			TStorageInfo TStorageInfo) {
		this.TMaterielPositionInfo = TMaterielPositionInfo;
		this.TStorageInfo = TStorageInfo;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id")
	public TMaterielPositionInfo getTMaterielPositionInfo() {
		return this.TMaterielPositionInfo;
	}

	public void setTMaterielPositionInfo(
			TMaterielPositionInfo TMaterielPositionInfo) {
		this.TMaterielPositionInfo = TMaterielPositionInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_id")
	public TStorageInfo getTStorageInfo() {
		return this.TStorageInfo;
	}

	public void setTStorageInfo(TStorageInfo TStorageInfo) {
		this.TStorageInfo = TStorageInfo;
	}

}