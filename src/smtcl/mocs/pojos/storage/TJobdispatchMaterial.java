package smtcl.mocs.pojos.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "t_jobdispatch_material")
public class TJobdispatchMaterial implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer jobdispatchId;
	private Integer materialId;
	private Double reqNum;
	private Double recNum;
	private Double price;

	// Constructors

	/** default constructor */
	public TJobdispatchMaterial() {
	}

	/** full constructor */
	public TJobdispatchMaterial(Integer jobdispatchId, Integer materialId,
			Double reqNum, Double recNum,Double price) {
		this.jobdispatchId = jobdispatchId;
		this.materialId = materialId;
		this.reqNum = reqNum;
		this.recNum = recNum;
		this.price = price;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "jobdispatch_id")
	public Integer getJobdispatchId() {
		return this.jobdispatchId;
	}

	public void setJobdispatchId(Integer jobdispatchId) {
		this.jobdispatchId = jobdispatchId;
	}

	@Column(name = "material_id")
	public Integer getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@Column(name = "reqNum", precision = 5)
	public Double getReqNum() {
		return this.reqNum;
	}

	public void setReqNum(Double reqNum) {
		this.reqNum = reqNum;
	}

	@Column(name = "recNum", precision = 5)
	public Double getRecNum() {
		return this.recNum;
	}

	public void setRecNum(Double recNum) {
		this.recNum = recNum;
	}
	
	@Column(name = "price", precision = 12)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}