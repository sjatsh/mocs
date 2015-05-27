package smtcl.mocs.pojos.job;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TQualityParam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_quality_param")
public class TQualityParam implements java.io.Serializable {

	// Fields

	private Long id;
	private String qualityNo; //质量参数编号
	private String qualityName;//质量参数名称
	private String standardValue;//参数标准值
	private String unit;//单位
	private String maxValue;//上限值
	private String minValue;//下限值
	private String minTolerance;//允差下限
	private String maxTolerance;//允差上限
	private Integer isKey;//是否是关键检验项
	private String description;//参数描述
	private Integer checkTime;//检验工时
	private Integer checkType;//检验参数类型

	// Constructors

	/** default constructor */
	public TQualityParam() {
	}

	/** full constructor */
	public TQualityParam(String qualityNo, String qualityName,
			String standardValue, String unit, String maxValue,
			String minValue, String tolerance, Integer isKey,
			String description, Integer checkTime, Integer checkType) {
		this.qualityNo = qualityNo;
		this.qualityName = qualityName;
		this.standardValue = standardValue;
		this.unit = unit;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.isKey = isKey;
		this.description = description;
		this.checkTime = checkTime;
		this.checkType = checkType;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "qualityNo", length = 20)
	public String getQualityNo() {
		return this.qualityNo;
	}

	public void setQualityNo(String qualityNo) {
		this.qualityNo = qualityNo;
	}

	@Column(name = "qualityName", length = 50)
	public String getQualityName() {
		return this.qualityName;
	}

	public void setQualityName(String qualityName) {
		this.qualityName = qualityName;
	}

	@Column(name = "standardValue", length = 20)
	public String getStandardValue() {
		return this.standardValue;
	}

	public void setStandardValue(String standardValue) {
		this.standardValue = standardValue;
	}

	@Column(name = "unit", length = 10)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "max_Value", length = 20)
	public String getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	@Column(name = "minValue", length = 20)
	public String getMinValue() {
		return this.minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	@Column(name = "min_tolerance")
	public String getMinTolerance() {
		return minTolerance;
	}

	public void setMinTolerance(String minTolerance) {
		this.minTolerance = minTolerance;
	}
	@Column(name = "max_tolerance")
	public String getMaxTolerance() {
		return maxTolerance;
	}

	public void setMaxTolerance(String maxTolerance) {
		this.maxTolerance = maxTolerance;
	}

	@Column(name = "isKey")
	public Integer getIsKey() {
		return this.isKey;
	}

	public void setIsKey(Integer isKey) {
		this.isKey = isKey;
	}

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "checkTime")
	public Integer getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Integer checkTime) {
		this.checkTime = checkTime;
	}

	@Column(name = "checkType")
	public Integer getCheckType() {
		return this.checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

}