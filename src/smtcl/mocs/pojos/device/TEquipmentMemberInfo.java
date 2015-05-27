package smtcl.mocs.pojos.device;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TEquipmentMemberInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_Equipment_Member_info")
public class TEquipmentMemberInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private Long equId;
	private Long memberId;
	private Long status;
	private String operatorNo;

	// Constructors

	/** default constructor */
	public TEquipmentMemberInfo() {
	}

	/** full constructor */
	public TEquipmentMemberInfo(Long equId, Long memberId, Long status) {
		this.equId = equId;
		this.memberId = memberId;
		this.status = status;
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

	@Column(name = "equ_ID")
	public Long getEquId() {
		return this.equId;
	}

	public void setEquId(Long equId) {
		this.equId = equId;
	}

	@Column(name = "member_ID")
	public Long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "status")
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	@Column(name = "operator_no")
	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
}