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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * KC_002 单位基本信息
 */
@Entity
@Table(name = "t_unit_info")
public class TUnitInfo implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private TUnitType TUnitType;//单位类别
	private String unitName;////单位名称
	private String unitShort;//单位缩写
	private String unitDesc;//单位描述
	private String unitClass;//单位类别
	private String unitStatus;//状态（是否启用）
	private Date createDate;//创建日期
	private Set<TUnitConversion> TUnitConversions = new HashSet<TUnitConversion>(
			0);
	private String nodeId;
	// Constructors

	/** default constructor */
	public TUnitInfo() {
	}

	/** full constructor */
	public TUnitInfo(TUnitType TUnitType, String unitName, String unitShort,
			String unitDesc, String unitClass, String unitStatus,
			Date createDate, Set<TUnitConversion> TUnitConversions,String nodeId) {
		this.TUnitType = TUnitType;
		this.unitName = unitName;
		this.unitShort = unitShort;
		this.unitDesc = unitDesc;
		this.unitClass = unitClass;
		this.unitStatus = unitStatus;
		this.createDate = createDate;
		this.TUnitConversions = TUnitConversions;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_typeID")
	public TUnitType getTUnitType() {
		return this.TUnitType;
	}

	public void setTUnitType(TUnitType TUnitType) {
		this.TUnitType = TUnitType;
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

	@Column(name = "unit_desc", length = 50)
	public String getUnitDesc() {
		return this.unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	@Column(name = "unit_class", length = 1)
	public String getUnitClass() {
		return this.unitClass;
	}

	public void setUnitClass(String unitClass) {
		this.unitClass = unitClass;
	}

	@Column(name = "unit_status", length = 1)
	public String getUnitStatus() {
		return this.unitStatus;
	}

	public void setUnitStatus(String unitStatus) {
		this.unitStatus = unitStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUnitInfo")
	public Set<TUnitConversion> getTUnitConversions() {
		return this.TUnitConversions;
	}

	public void setTUnitConversions(Set<TUnitConversion> TUnitConversions) {
		this.TUnitConversions = TUnitConversions;
	}
    
	@Column(name = "nodeId", length = 50)
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}