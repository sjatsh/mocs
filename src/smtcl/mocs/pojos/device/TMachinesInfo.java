package smtcl.mocs.pojos.device;

// default package


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TMachinesInfo entity. 机床基本表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SymgMachines")
public class TMachinesInfo implements java.io.Serializable {

	// Fields

	private Long symgMachineId;                   //机床ID
	private String symgMName;					  //机床名称
	private Long symgMTypeId;                     //机床型号ID
	private String symgMSerialNo;                 //机床序列号
	private String symgMActivationCode;           //激活码
	private Date symgMOnlineTime;                 //进线时间
	private Date symgMOfflineTime;				  //完成时间
	private Date symgMLeaveDate;				  //出厂日期
	private String symgMManufactureChief;         //负责工段长
	private String symgMOrgName;                  //机床生产部门
	private String symgMNodeId;
	private String path;
	private String shiftNo;	

	private Set<TNodeMachine> TNodeMachines = new HashSet<TNodeMachine>(0);

	// Constructors

	/** default constructor */
	public TMachinesInfo() {
	}

	/** minimal constructor */
	public TMachinesInfo(Long symgMachineId) {
		this.symgMachineId = symgMachineId;
	}

	/** full constructor */
	public TMachinesInfo(Long symgMachineId, String symgMName,
			Long symgMTypeId, String symgMSerialNo,
			String symgMActivationCode, Date symgMOnlineTime,
			Date symgMOfflineTime, Date symgMLeaveDate,
			String symgMManufactureChief, String symgMOrgName,
			String path,String shiftNo,
			Set<TNodeMachine> TNodeMachines) {
		this.symgMachineId = symgMachineId;
		this.symgMName = symgMName;
		this.symgMTypeId = symgMTypeId;
		this.symgMSerialNo = symgMSerialNo;
		this.symgMActivationCode = symgMActivationCode;
		this.symgMOnlineTime = symgMOnlineTime;
		this.symgMOfflineTime = symgMOfflineTime;
		this.symgMLeaveDate = symgMLeaveDate;
		this.symgMManufactureChief = symgMManufactureChief;
		this.symgMOrgName = symgMOrgName;
		this.path = path;
		this.shiftNo = shiftNo;
		this.TNodeMachines = TNodeMachines;	
	}

	@Id
	@Column(name = "symgMachineID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getSymgMachineId() {
		return this.symgMachineId;
	}

	public void setSymgMachineId(Long symgMachineId) {
		this.symgMachineId = symgMachineId;
	}

	@Column(name = "symgM_name", length = 30)
	public String getSymgMName() {
		return this.symgMName;
	}

	public void setSymgMName(String symgMName) {
		this.symgMName = symgMName;
	}

	@Column(name = "symgM_TypeID", precision = 22, scale = 0)
	public Long getSymgMTypeId() {
		return this.symgMTypeId;
	}

	public void setSymgMTypeId(Long symgMTypeId) {
		this.symgMTypeId = symgMTypeId;
	}

	@Column(name = "symgM_serialNo", length = 20)
	public String getSymgMSerialNo() {
		return this.symgMSerialNo;
	}

	public void setSymgMSerialNo(String symgMSerialNo) {
		this.symgMSerialNo = symgMSerialNo;
	}

	@Column(name = "symgM_ActivationCode", length = 20)
	public String getSymgMActivationCode() {
		return this.symgMActivationCode;
	}

	public void setSymgMActivationCode(String symgMActivationCode) {
		this.symgMActivationCode = symgMActivationCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "symgM_OnlineTime", length = 7)
	public Date getSymgMOnlineTime() {
		return this.symgMOnlineTime;
	}

	public void setSymgMOnlineTime(Date symgMOnlineTime) {
		this.symgMOnlineTime = symgMOnlineTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "symgM_offlineTime", length = 7)
	public Date getSymgMOfflineTime() {
		return this.symgMOfflineTime;
	}

	public void setSymgMOfflineTime(Date symgMOfflineTime) {
		this.symgMOfflineTime = symgMOfflineTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "symgM_LeaveDate", length = 7)
	public Date getSymgMLeaveDate() {
		return this.symgMLeaveDate;
	}

	public void setSymgMLeaveDate(Date symgMLeaveDate) {
		this.symgMLeaveDate = symgMLeaveDate;
	}

	@Column(name = "symgM_ManufactureChief", length = 10)
	public String getSymgMManufactureChief() {
		return this.symgMManufactureChief;
	}

	public void setSymgMManufactureChief(String symgMManufactureChief) {
		this.symgMManufactureChief = symgMManufactureChief;
	}

	@Column(name = "symgM_OrgName", length = 30)
	public String getSymgMOrgName() {
		return this.symgMOrgName;
	}

	public void setSymgMOrgName(String symgMOrgName) {
		this.symgMOrgName = symgMOrgName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TMachinesInfo")
	public Set<TNodeMachine> getTNodeMachines() {
		return this.TNodeMachines;
	}

	public void setTNodeMachines(Set<TNodeMachine> TNodeMachines) {
		this.TNodeMachines = TNodeMachines;
	}
	
	@Column(name = "symgM_NodeId", length = 50)
	public String getSymgMNodeId() {
		return symgMNodeId;
	}

	public void setSymgMNodeId(String symgMNodeId) {
		this.symgMNodeId = symgMNodeId;
	}
	
	@Column(name = "PATH", length = 200)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name = "shiftno", length = 50)
	public String getShiftNo() {
		return shiftNo;
	}

	public void setShiftNo(String shiftNo) {
		this.shiftNo = shiftNo;
	}
}