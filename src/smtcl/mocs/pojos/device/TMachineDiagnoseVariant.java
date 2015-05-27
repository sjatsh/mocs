package smtcl.mocs.pojos.device;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * TMachineDiagnoseVariant entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_machine_diagnose_variant")
public class TMachineDiagnoseVariant implements java.io.Serializable {

	// Fields

	private Long id;
	private String component;
	private String m8Variant;
	private Integer m8Value;
	private String memo;
	private String m9Variant;
	private String plcAlertNo;

	// Constructors

	/** default constructor */
	public TMachineDiagnoseVariant() {
	}

	/** full constructor */
	public TMachineDiagnoseVariant(String component, String m8Variant,
			Integer m8Value, String memo, String m9Variant, String plcAlertNo) {
		this.component = component;
		this.m8Variant = m8Variant;
		this.m8Value = m8Value;
		this.memo = memo;
		this.m9Variant = m9Variant;
		this.plcAlertNo = plcAlertNo;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "component", length = 20)
	public String getComponent() {
		return this.component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	@Column(name = "M8_variant", length = 20)
	public String getM8Variant() {
		return this.m8Variant;
	}

	public void setM8Variant(String m8Variant) {
		this.m8Variant = m8Variant;
	}

	@Column(name = "M8_value")
	public Integer getM8Value() {
		return this.m8Value;
	}

	public void setM8Value(Integer m8Value) {
		this.m8Value = m8Value;
	}

	@Column(name = "memo", length = 30)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "M9_variant", length = 20)
	public String getM9Variant() {
		return this.m9Variant;
	}

	public void setM9Variant(String m9Variant) {
		this.m9Variant = m9Variant;
	}

	@Column(name = "PLC_alert_no", length = 20)
	public String getPlcAlertNo() {
		return this.plcAlertNo;
	}

	public void setPlcAlertNo(String plcAlertNo) {
		this.plcAlertNo = plcAlertNo;
	}

}