package smtcl.mocs.pojos.device;

// default package
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TNodeMachine entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "r_NodeSymgMachine")
public class TNodeMachine implements java.io.Serializable {

	// Fields

	private Long id;
	private TMachinesInfo TMachinesInfo;
	private String equNodeID;
	private Date interactionDate; //关联时间
	private String interactionOperator;//关联操作人

	// Constructors

	/** default constructor */
	public TNodeMachine() {
	}

	/** minimal constructor */
	public TNodeMachine(TMachinesInfo TMachinesInfo,
			String  equNodeID) {
		this.TMachinesInfo = TMachinesInfo;
		this.equNodeID = equNodeID;
	}

	/** full constructor */
	public TNodeMachine(TMachinesInfo TMachinesInfo,
			String  equNodeID, Date interactionDate,
			String interactionOperator) {
		this.TMachinesInfo = TMachinesInfo;
		this.equNodeID = equNodeID;
		this.interactionDate = interactionDate;
		this.interactionOperator = interactionOperator;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symgMachineID", nullable = false)
	public TMachinesInfo getTMachinesInfo() {
		return this.TMachinesInfo;
	}

	public void setTMachinesInfo(TMachinesInfo TMachinesInfo) {
		this.TMachinesInfo = TMachinesInfo;
	}

	@Column(name = "equ_nodeID", length = 50)
	public String getEquNodeID() {
		return this.equNodeID;
	}

	public void setEquNodeID(String equNodeID) {
		this.equNodeID = equNodeID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "interactionDate", length = 7)
	public Date getInteractionDate() {
		return this.interactionDate;
	}

	public void setInteractionDate(Date interactionDate) {
		this.interactionDate = interactionDate;
	}

	@Column(name = "interactionOperator", length = 10)
	public String getInteractionOperator() {
		return this.interactionOperator;
	}

	public void setInteractionOperator(String interactionOperator) {
		this.interactionOperator = interactionOperator;
	}

}