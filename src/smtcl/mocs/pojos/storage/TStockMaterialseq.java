package smtcl.mocs.pojos.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TStockMaterialseq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_stock_materialseq")
public class TStockMaterialseq implements java.io.Serializable {

	// Fields

	private Integer id;
	private String no;
	private Integer materialId;
	private String vendorSeqNo;
	private String status;

	// Constructors

	/** default constructor */
	public TStockMaterialseq() {
	}

	/** full constructor */
	public TStockMaterialseq(String no, Integer materialId, String vendorSeqNo,
			String status) {
		this.no = no;
		this.materialId = materialId;
		this.vendorSeqNo = vendorSeqNo;
		this.status = status;
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

	@Column(name = "vendorSeqNo", length = 50)
	public String getVendorSeqNo() {
		return this.vendorSeqNo;
	}

	public void setVendorSeqNo(String vendorSeqNo) {
		this.vendorSeqNo = vendorSeqNo;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}