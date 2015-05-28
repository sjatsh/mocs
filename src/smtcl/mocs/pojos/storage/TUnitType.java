package smtcl.mocs.pojos.storage;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * KC_001 单位类别
 */
@Entity
@Table(name = "t_unit_type")
public class TUnitType implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private String unitTypeName;//单位类别名称
	private String unitTypeDesc;//单位类别说明
	private String unitName;//标准单位名称
	private String unitShort;//标准单位缩写
	private Date invalidDate;//失效日期
	private Date createDate;//创建日期
	private Set<TUnitInfo> TUnitInfos = new HashSet<TUnitInfo>(0);
	private String nodeId;
	// Constructors

	/** default constructor */
	public TUnitType() {
	}

	/** full constructor */
	public TUnitType(String unitTypeName, String unitTypeDesc, String unitName,
			String unitShort, Date invalidDate, Date createDate,
			Set<TUnitInfo> TUnitInfos,String nodeId) {
		this.unitTypeName = unitTypeName;
		this.unitTypeDesc = unitTypeDesc;
		this.unitName = unitName;
		this.unitShort = unitShort;
		this.invalidDate = invalidDate;
		this.createDate = createDate;
		this.TUnitInfos = TUnitInfos;
		this.nodeId = nodeId;
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

	@Column(name = "unit_typeName", length = 20)
	public String getUnitTypeName() {
		return this.unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	@Column(name = "unit_typeDesc", length = 50)
	public String getUnitTypeDesc() {
		return this.unitTypeDesc;
	}

	public void setUnitTypeDesc(String unitTypeDesc) {
		this.unitTypeDesc = unitTypeDesc;
	}

	@Column(name = "unit_name", length = 20)
	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name = "unit_short", length = 10)
	public String getUnitShort() {
		return this.unitShort;
	}

	public void setUnitShort(String unitShort) {
		this.unitShort = unitShort;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invalid_date", length = 0)
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUnitType")
	public Set<TUnitInfo> getTUnitInfos() {
		return this.TUnitInfos;
	}

	public void setTUnitInfos(Set<TUnitInfo> TUnitInfos) {
		this.TUnitInfos = TUnitInfos;
	}
	@Column(name = "nodeId", length = 50)
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}