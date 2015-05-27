package smtcl.mocs.pojos.device;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TMachineDiagnoseio entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_machine_diagnoseio")
public class TMachineDiagnoseio implements java.io.Serializable {

	// Fields

	private Long id;
	private String equSerialNo;
	private String equIo;
	private Date updateDate;

	// Constructors

	/** default constructor */
	public TMachineDiagnoseio() {
		
	}

	/** minimal constructor */
	public TMachineDiagnoseio(Long id){
		this.id = id;
	}

	/** full constructor */
	public TMachineDiagnoseio(Long id, String equSerialNo, String equIo,
			Date updateDate) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.equIo = equIo;
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

	@Column(name = "equ_io", length = 2000)
	public String getEquIo() {
		return this.equIo;
	}

	public void setEquIo(String equIo) {
		this.equIo = equIo;
	}

	@Column(name = "updateDate", length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}