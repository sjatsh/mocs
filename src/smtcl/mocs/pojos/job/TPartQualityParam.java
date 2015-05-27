package smtcl.mocs.pojos.job;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TPartQualityParam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_Part_Quality_param")
public class TPartQualityParam implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long partId;
	private Long processId;
	private Long qualityId;
	private String realValue;
	private String operator;
	private Date opDate;
	private String result;

	// Constructors

	/** default constructor */
	public TPartQualityParam() {
	}

	/** full constructor */
	public TPartQualityParam(Long partId, Long processId,
			Long qualityId, String realValue, String operator,
			Date opDate, String result) {
		this.partId = partId;
		this.processId = processId;
		this.qualityId = qualityId;
		this.realValue = realValue;
		this.operator = operator;
		this.opDate = opDate;
		this.result = result;
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

	@Column(name = "partID")
	public Long getPartId() {
		return this.partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	@Column(name = "processID")
	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Column(name = "qualityID")
	public Long getQualityId() {
		return this.qualityId;
	}

	public void setQualityId(Long qualityId) {
		this.qualityId = qualityId;
	}

	@Column(name = "realValue", length = 20)
	public String getRealValue() {
		return this.realValue;
	}

	public void setRealValue(String realValue) {
		this.realValue = realValue;
	}

	@Column(name = "operator", length = 20)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "opDate", length = 0)
	public Date getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	@Column(name = "result", length = 20)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}