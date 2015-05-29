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
 * KC_002 单位换算表
 */
@Entity
@Table(name = "t_unit_conversion")
public class TUnitConversion implements java.io.Serializable {

	// Fields

	private Integer id;//流水号ID
	private TUnitInfo TUnitInfo;//换算单位ID
	private Integer refUnitId;//参照单位ID
	private Double ratio;//倍率
	private String conversionType;//换算类型（标准、分类内、分类间）
	private Integer materailId;//物料ID
	private String nodeId;
	// Constructors

	/** default constructor */
	public TUnitConversion() {
	}

	/** full constructor */
	public TUnitConversion(TUnitInfo TUnitInfo, Integer refUnitId,
			Double ratio, String conversionType, Integer materailId,String nodeId) {
		this.TUnitInfo = TUnitInfo;
		this.refUnitId = refUnitId;
		this.ratio = ratio;
		this.conversionType = conversionType;
		this.materailId = materailId;
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
	@JoinColumn(name = "con_unitID")
	public TUnitInfo getTUnitInfo() {
		return this.TUnitInfo;
	}

	public void setTUnitInfo(TUnitInfo TUnitInfo) {
		this.TUnitInfo = TUnitInfo;
	}

	@Column(name = "ref_unitID")
	public Integer getRefUnitId() {
		return this.refUnitId;
	}

	public void setRefUnitId(Integer refUnitId) {
		this.refUnitId = refUnitId;
	}

	@Column(name = "ratio", precision = 5)
	public Double getRatio() {
		return this.ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	@Column(name = "conversion_type", length = 1)
	public String getConversionType() {
		return this.conversionType;
	}

	public void setConversionType(String conversionType) {
		this.conversionType = conversionType;
	}

	@Column(name = "materail_id")
	public Integer getMaterailId() {
		return this.materailId;
	}

	public void setMaterailId(Integer materailId) {
		this.materailId = materailId;
	}
    
	@Column(name = "nodeId", length = 50)
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}