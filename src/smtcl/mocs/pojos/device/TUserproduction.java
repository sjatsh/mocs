package smtcl.mocs.pojos.device;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TUserproduction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_userproduction")
public class TUserproduction implements java.io.Serializable {

	// Fields

	private Long id;
	private String operatorNo;
	private Long partTypeId;
	private Long processId;
	private Integer processNum;
	private Date updateDate;

	// Constructors

	/** default constructor */
	public TUserproduction() {
	}

	/** full constructor */
	public TUserproduction(String operatorNo, Long partTypeId,
			Long processId, Integer processNum, Date updateDate) {
		this.operatorNo = operatorNo;
		this.partTypeId = partTypeId;
		this.processId = processId;
		this.processNum = processNum;
		this.updateDate = updateDate;
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

	@Column(name = "operator_no", length = 30)
	public String getOperatorNo() {
		return this.operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	@Column(name = "partTypeID")
	public Long getPartTypeId() {
		return this.partTypeId;
	}

	public void setPartTypeId(Long partTypeId) {
		this.partTypeId = partTypeId;
	}

	@Column(name = "processID")
	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Column(name = "processNum")
	public Integer getProcessNum() {
		return this.processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updateDate", length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}