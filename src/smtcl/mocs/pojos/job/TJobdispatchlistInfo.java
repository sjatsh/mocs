package smtcl.mocs.pojos.job;
// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import smtcl.mocs.pojos.device.TEquipmentInfo;

/**
 * TJobdispatchlistInfo entity. @author MyEclipse Persistence Tools
 * 工单表
 */
@Entity
@Table(name = "t_jobdispatchlist_info")
public class TJobdispatchlistInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TEquipmentInfo TEquipmentInfo;
	private TJobInfo TJobInfo;
	private String no;
	private String name;
	private Integer processNum;//加工数量/数量
	private Date planStarttime;
	private Date planEndtime;
	private Date realStarttime;
	private Date realEndtime;
	private Integer theoryWorktime;//理论工时
	private Integer finishNum;//完成数量
	private Integer goodQuantity;//合格率
	private Integer badQuantity;
	private Integer isRelation;
	private Integer isBackRepository;
	private Integer relationshipId;
	private Integer status;
	private String creator;
	private Date createDate;
	private String remark;
	private Integer isAutoStart;
	private Long jobplanId;
	private String batchNo;
	private String nodeid;//节点id
	private Integer onlineNumber;//上线数量
	private String taskNum;  //任务号
	private TProcessInfo TProcessInfo; //工序
	private TEquipmenttypeInfo TEquipmenttypeInfo; //设备类型
	private Integer erpScrapNum;//erp报废数量
	private Integer wisScrapNum;//wis报废数量
	

	private Integer oldStatus;//工单的上一个状态，用于恢复
	// Constructors

	/** default constructor */
	public TJobdispatchlistInfo() {
	}

	/** minimal constructor */
	public TJobdispatchlistInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TJobdispatchlistInfo(Long id,
			TEquipmentInfo TEquipmentInfo, TJobInfo TJobInfo, String no,
			String name, Integer processNum, Date planStarttime,
			Date planEndtime, Date realStarttime,
			Date realEndtime, Integer theoryWorktime, Integer finishNum,
			Integer goodQuantity, Integer badQuantity, Integer isRelation,
			Integer isBackRepository, Integer relationshipId, Integer status,
			String creator, Date createDate, String remark,
			Integer isAutoStart) {
		this.id = id;
		this.TEquipmentInfo = TEquipmentInfo;
		this.TJobInfo = TJobInfo;
		this.no = no;
		this.name = name;
		this.processNum = processNum;
		this.planStarttime = planStarttime;
		this.planEndtime = planEndtime;
		this.realStarttime = realStarttime;
		this.realEndtime = realEndtime;
		this.theoryWorktime = theoryWorktime;
		this.finishNum = finishNum;
		this.goodQuantity = goodQuantity;
		this.badQuantity = badQuantity;
		this.isRelation = isRelation;
		this.isBackRepository = isBackRepository;
		this.relationshipId = relationshipId;
		this.status = status;
		this.creator = creator;
		this.createDate = createDate;
		this.remark = remark;
		this.isAutoStart = isAutoStart;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipmentID")
	public TEquipmentInfo getTEquipmentInfo() {
		return this.TEquipmentInfo;
	}

	public void setTEquipmentInfo(TEquipmentInfo TEquipmentInfo) {
		this.TEquipmentInfo = TEquipmentInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jobID")
	public TJobInfo getTJobInfo() {
		return this.TJobInfo;
	}

	public void setTJobInfo(TJobInfo TJobInfo) {
		this.TJobInfo = TJobInfo;
	}

	@Column(name = "no", length = 50)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "processNum")
	public Integer getProcessNum() {
		return this.processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plan_starttime", length = 0)
	public Date getPlanStarttime() {
		return this.planStarttime;
	}

	public void setPlanStarttime(Date planStarttime) {
		this.planStarttime = planStarttime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plan_endtime", length = 0)
	public Date getPlanEndtime() {
		return this.planEndtime;
	}

	public void setPlanEndtime(Date planEndtime) {
		this.planEndtime = planEndtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "real_starttime", length = 0)
	public Date getRealStarttime() {
		return this.realStarttime;
	}

	public void setRealStarttime(Date realStarttime) {
		this.realStarttime = realStarttime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "real_endtime", length = 0)
	public Date getRealEndtime() {
		return this.realEndtime;
	}

	public void setRealEndtime(Date realEndtime) {
		this.realEndtime = realEndtime;
	}

	@Column(name = "theorywork_time")
	public Integer getTheoryWorktime() {
		return this.theoryWorktime;
	}

	public void setTheoryWorktime(Integer theoryWorktime) {
		this.theoryWorktime = theoryWorktime;
	}

	@Column(name = "finishNum", length = 0)
	public Integer getFinishNum() {
		return this.finishNum;
	}

	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}

	@Column(name = "goodQuantity")
	public Integer getGoodQuantity() {
		return this.goodQuantity;
	}

	public void setGoodQuantity(Integer goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	@Column(name = "badQuantity")
	public Integer getBadQuantity() {
		return this.badQuantity;
	}

	public void setBadQuantity(Integer badQuantity) {
		this.badQuantity = badQuantity;
	}

	@Column(name = "isRelation")
	public Integer getIsRelation() {
		return this.isRelation;
	}

	public void setIsRelation(Integer isRelation) {
		this.isRelation = isRelation;
	}

	@Column(name = "isBackRepository")
	public Integer getIsBackRepository() {
		return this.isBackRepository;
	}

	public void setIsBackRepository(Integer isBackRepository) {
		this.isBackRepository = isBackRepository;
	}

	@Column(name = "relationshipID")
	public Integer getRelationshipId() {
		return this.relationshipId;
	}

	public void setRelationshipId(Integer relationshipId) {
		this.relationshipId = relationshipId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "creator", length = 20)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "isAutoStart")
	public Integer getIsAutoStart() {
		return this.isAutoStart;
	}

	public void setIsAutoStart(Integer isAutoStart) {
		this.isAutoStart = isAutoStart;
	}
	@Column(name = "jobplanID")
	public Long getJobplanId() {
		return jobplanId;
	}
	
	public void setJobplanId(Long jobplanId) {
		this.jobplanId = jobplanId;
	}

	@Column(name = "batchNo")
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	@Column(name = "oldStatus")
	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}
	
	@Column(name = "onlineNumber")
	public Integer getOnlineNumber() {
		return onlineNumber;
	}

	public void setOnlineNumber(Integer onlineNumber) {
		this.onlineNumber = onlineNumber;
	}

	@Column(name = "taskNum")
	public String getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processID")
	public TProcessInfo getTProcessInfo() {
		return TProcessInfo;
	}

	public void setTProcessInfo(TProcessInfo tProcessInfo) {
		TProcessInfo = tProcessInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipmentTypeID")
	public TEquipmenttypeInfo getTEquipmenttypeInfo() {
		return TEquipmenttypeInfo;
	}

	public void setTEquipmenttypeInfo(TEquipmenttypeInfo tEquipmenttypeInfo) {
		TEquipmenttypeInfo = tEquipmenttypeInfo;
	}
	
	@Column(name = "erpScrapNum")
	public Integer getErpScrapNum() {
		return erpScrapNum;
	}

	public void setErpScrapNum(Integer erpScrapNum) {
		this.erpScrapNum = erpScrapNum;
	}
	@Column(name = "wisScrapNum")
	public Integer getWisScrapNum() {
		return wisScrapNum;
	}

	public void setWisScrapNum(Integer wisScrapNum) {
		this.wisScrapNum = wisScrapNum;
	}

	
}