package smtcl.mocs.pojos.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * KC_102 �����嵥
 */
@Entity
@Table(name = "t_material_extend_detail")
public class TMaterialExtendDetail implements java.io.Serializable {

	// Fields

	private Integer materailId;//����ID

	// Constructors

	/** default constructor */
	public TMaterialExtendDetail() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "materail_id", unique = true, nullable = false)
	public Integer getMaterailId() {
		return this.materailId;
	}

	public void setMaterailId(Integer materailId) {
		this.materailId = materailId;
	}

}