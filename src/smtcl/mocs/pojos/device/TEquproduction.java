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
 * TEquproduction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_equproduction")
public class TEquproduction implements java.io.Serializable {

	// Fields

	private Long id;
	private String equSerialNo;
	private Long partTypeId;
	private Long processId;
	private Integer processNum;
	private Date updateDate;

	// Constructors

	/** default constructor */
	public TEquproduction() {
	}

	/** full constructor */
	public TEquproduction(String equSerialNo, Long partTypeId,
			Long processId, Integer processNum, Date updateDate) {
		this.equSerialNo = equSerialNo;
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

	@Column(name = "equ_SerialNo", length = 30)
	public String getEquSerialNo() {
		return this.equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
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