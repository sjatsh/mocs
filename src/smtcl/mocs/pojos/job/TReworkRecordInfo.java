package smtcl.mocs.pojos.job;


import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TReworkRecordInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_Rework_record_info")
public class TReworkRecordInfo implements java.io.Serializable {

	// Fields
	private Long id;
	private Long partId;
	private Long paramId;
	private String paramValue;
	private Date requesttime;

	// Constructors

	/** default constructor */
	public TReworkRecordInfo() {
	}

	/** full constructor */
	public TReworkRecordInfo(Long partId, Long paramId,
			String paramValue, Date requesttime) {
		this.partId = partId;
		this.paramId = paramId;
		this.paramValue = paramValue;
		this.requesttime = requesttime;
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

	@Column(name = "paramID")
	public Long getParamId() {
		return this.paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	@Column(name = "paramValue", length = 20)
	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "requesttime", length = 0)
	public Date getRequesttime() {
		return this.requesttime;
	}

	public void setRequesttime(Timestamp requesttime) {
		this.requesttime = requesttime;
	}

}