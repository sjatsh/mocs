package smtcl.mocs.pojos.job;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCuttertypeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_equ_jobdispatch")
public class TEquJobDispatch implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String jobdispatchNo;
	private String equSerialNo;
	private Integer status;

	// Constructors

	/** default constructor */
	public TEquJobDispatch() {
	}

	/** minimal constructor */
	public TEquJobDispatch(Integer id) {
		this.id = id;
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

	@Column(name = "jobdispatchNo")
	public String getJobdispatchNo() {
		return jobdispatchNo;
	}

	public void setJobdispatchNo(String jobdispatchNo) {
		this.jobdispatchNo = jobdispatchNo;
	}

	@Column(name = "equ_SerialNo")
	public String getEquSerialNo() {
		return equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}