package smtcl.mocs.pojos.storage;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TStockMaterialbath entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_stock_materialbath")
public class TStockMaterialbath implements java.io.Serializable {

	// Fields

	private Integer id;
	private String no;
	private Integer materialId;
	private Timestamp invalidDate;
	private String vendorBatchNo;

	// Constructors

	/** default constructor */
	public TStockMaterialbath() {
	}

	/** full constructor */
	public TStockMaterialbath(String no, Integer materialId,
			Timestamp invalidDate, String vendorBatchNo) {
		this.no = no;
		this.materialId = materialId;
		this.invalidDate = invalidDate;
		this.vendorBatchNo = vendorBatchNo;
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

	@Column(name = "NO", length = 50)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "material_id")
	public Integer getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@Column(name = "invalidDate", length = 19)
	public Timestamp getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "vendorBatchNo", length = 50)
	public String getVendorBatchNo() {
		return this.vendorBatchNo;
	}

	public void setVendorBatchNo(String vendorBatchNo) {
		this.vendorBatchNo = vendorBatchNo;
	}

}