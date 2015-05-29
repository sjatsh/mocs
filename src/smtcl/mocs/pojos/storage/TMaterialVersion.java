package smtcl.mocs.pojos.storage;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * KC_105 物料版本
 */
@Entity
@Table(name = "t_material_version")
public class TMaterialVersion implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private Integer materialId;//物料ID
	private String versionNo;//物料版本号
	private String versionDesc;//版本说明
	private Date startDate;//启动日期
	private Integer isDefault;//是否默认版本

	// Constructors

	/** default constructor */
	public TMaterialVersion() {
	}

	/** full constructor */
	public TMaterialVersion(Integer materialId, String versionNo,
			String versionDesc, Date startDate, Integer isDefault) {
		this.materialId = materialId;
		this.versionNo = versionNo;
		this.versionDesc = versionDesc;
		this.startDate = startDate;
		this.isDefault = isDefault;
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

	@Column(name = "material_id")
	public Integer getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@Column(name = "version_no", length = 10)
	public String getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	@Column(name = "version_desc", length = 50)
	public String getVersionDesc() {
		return this.versionDesc;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startDate", length = 0)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "isDefault")
	public Integer getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

}