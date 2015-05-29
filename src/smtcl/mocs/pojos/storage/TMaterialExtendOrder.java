package smtcl.mocs.pojos.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * KC_103 物料采购
 */
@Entity
@Table(name = "t_material_extend_order")
public class TMaterialExtendOrder implements java.io.Serializable {

	// Fields

	private Integer materialId;//物料ID
	private String buyer;//采购人员
	private Double price;//价格

	// Constructors

	/** default constructor */
	public TMaterialExtendOrder() {
	}

	/** full constructor */
	public TMaterialExtendOrder(String buyer, Double price) {
		this.buyer = buyer;
		this.price = price;
	}

	// Property accessors
	@Id
	@Column(name = "material_id", unique = true, nullable = false)
	public Integer getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@Column(name = "buyer", length = 20)
	public String getBuyer() {
		return this.buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	@Column(name = "price", precision = 5)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}