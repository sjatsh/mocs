package smtcl.mocs.pojos.device;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * TMachineDiagnoseInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_machine_diagnose_info")
public class TMachineDiagnoseInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String component;
	private String m9Variant;
	private String alertInfo;
	private String cause;
	private String solution;
	private String com_zh;
	// Constructors

	/** default constructor */
	public TMachineDiagnoseInfo() {
	}

	/** minimal constructor */
	public TMachineDiagnoseInfo(String m9Variant) {
		this.m9Variant = m9Variant;
	}

	/** full constructor */
	public TMachineDiagnoseInfo(String component, String m9Variant,
			String alertInfo, String cause, String solution) {
		this.component = component;
		this.m9Variant = m9Variant;
		this.alertInfo = alertInfo;
		this.cause = cause;
		this.solution = solution;
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

	@Column(name = "M9_variant", nullable = false, length = 20)
	public String getM9Variant() {
		return this.m9Variant;
	}

	public void setM9Variant(String m9Variant) {
		this.m9Variant = m9Variant;
	}

	@Column(name = "alert_info", length = 100)
	public String getAlertInfo() {
		return this.alertInfo;
	}

	public void setAlertInfo(String alertInfo) {
		this.alertInfo = alertInfo;
	}

	@Column(name = "cause", length = 200)
	public String getCause() {
		return this.cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	@Column(name = "solution", length = 200)
	public String getSolution() {
		return this.solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	@Column(name = "com_zh", length = 20)
	public String getCom_zh() {
		return com_zh;
	}

	public void setCom_zh(String com_zh) {
		this.com_zh = com_zh;
	}

}