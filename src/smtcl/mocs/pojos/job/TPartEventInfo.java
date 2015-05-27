package smtcl.mocs.pojos.job;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPartEventInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_PART_EVENT_INFO")
public class TPartEventInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private Long partId;
	private Long eventId;
	private Long processID;
	private String  dispatchNo;
	private Integer status;
	private String operator;

	// Constructors

	/** default constructor */
	public TPartEventInfo() {
	}

	/** full constructor */
	public TPartEventInfo(Long partId, Long eventId) {
		this.partId = partId;
		this.eventId = eventId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
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

	@Column(name = "eventID")
	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Column(name = "processID")
	public Long getProcessID() {
		return processID;
	}

	public void setProcessID(Long processID) {
		this.processID = processID;
	}

	@Column(name = "dispatchNo")
	public String getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}