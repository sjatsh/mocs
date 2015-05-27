package smtcl.mocs.pojos.job;
// default package

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

/**
 * TProcessInfo entity. @author MyEclipse Persistence Tools
 * 零件工序表
 */
@Entity
@Table(name = "t_process_info")
public class TProcessInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long programId; //程序
	private TProcessplanInfo TProcessplanInfo; //工艺方案
	private TWorkstationInfo TWorkstationInfo;//工作站
	private String no;
	private String name;
	private String engName;
	private String description; //描述
	private Double processingTime; //加工时间
	private Integer theoryWorktime;//节拍时间
	private Integer installTime;
	private Integer uninstallTime;
	private String file;
	private Integer processDuration;
	private Long onlineProcessId;
	private Integer offlineProcess;
	private Integer processOrder;	
	private Double capacity;//预计产能（个/小时）
	private Integer status; //是否删除
	private Integer processType;//工序类型
	private Integer needcheck;//是否必检
	private Integer checkType;//质量检验类型
	private String memo;
	private String nodeid;
	private Set<TProcessmaterialInfo> TProcessmaterialInfos = new HashSet<TProcessmaterialInfo>(
			0);
	private Set<TPartProcessCost> TPartProcessCosts = new HashSet<TPartProcessCost>(
			0);
	private Set<TJobInfo> TJobInfos = new HashSet<TJobInfo>(0);

	// Constructors

	/** default constructor */
	public TProcessInfo() {
	}

	/** minimal constructor */
	public TProcessInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TProcessInfo(Long id, Long programId,
			TProcessplanInfo TProcessplanInfo,
			TWorkstationInfo TWorkstationInfo,
		    String no, String name,
			String description, Double processingTime, Integer theoryWorktime,
			Integer installTime, Integer uninstallTime, String file,
			Integer processDuration,Integer offlineProcess,
			Set<TProcessmaterialInfo> TProcessmaterialInfos,
			Set<TPartProcessCost> TPartProcessCosts,
			Set<RProcessEquipmenttype> RProcessEquipmenttypes,
			 Set<TJobInfo> TJobInfos,String engName) {
		this.id = id;
		this.programId = programId;
		this.TProcessplanInfo = TProcessplanInfo;
		this.TWorkstationInfo = TWorkstationInfo;
		this.no = no;
		this.name = name;
		this.engName=engName;
		this.description = description;
		this.processingTime = processingTime;
		this.theoryWorktime = theoryWorktime;
		this.installTime = installTime;
		this.uninstallTime = uninstallTime;
		this.file = file;
		this.processDuration = processDuration;
		this.offlineProcess = offlineProcess;
		this.TProcessmaterialInfos = TProcessmaterialInfos;
		this.TPartProcessCosts = TPartProcessCosts;
		this.TJobInfos = TJobInfos;
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

	@Column(name = "programID")
	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processPlanID")
	public TProcessplanInfo getTProcessplanInfo() {
		return this.TProcessplanInfo;
	}

	public void setTProcessplanInfo(TProcessplanInfo TProcessplanInfo) {
		this.TProcessplanInfo = TProcessplanInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workstationID")
	public TWorkstationInfo getTWorkstationInfo() {
		return this.TWorkstationInfo;
	}

	public void setTWorkstationInfo(TWorkstationInfo TWorkstationInfo) {
		this.TWorkstationInfo = TWorkstationInfo;
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

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "processing_time")
	public Double getProcessingTime() {
		return this.processingTime;
	}

	public void setProcessingTime(Double processingTime) {
		this.processingTime = processingTime;
	}

	@Column(name = "theorywork_time")
	public Integer getTheoryWorktime() {
		return this.theoryWorktime;
	}

	public void setTheoryWorktime(Integer theoryWorktime) {
		this.theoryWorktime = theoryWorktime;
	}

	@Column(name = "install_time")
	public Integer getInstallTime() {
		return this.installTime;
	}

	public void setInstallTime(Integer installTime) {
		this.installTime = installTime;
	}

	@Column(name = "uninstall_time")
	public Integer getUninstallTime() {
		return this.uninstallTime;
	}

	public void setUninstallTime(Integer uninstallTime) {
		this.uninstallTime = uninstallTime;
	}

	@Column(name = "advice_file", length = 200)
	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Column(name = "processDuration")
	public Integer getProcessDuration() {
		return this.processDuration;
	}

	public void setProcessDuration(Integer processDuration) {
		this.processDuration = processDuration;
	}

	@Column(name = "onlineProcessID")
	public Long getOnlineProcessId() {
		return this.onlineProcessId;
	}

	public void setOnlineProcessId(Long onlineProcessId) {
		this.onlineProcessId = onlineProcessId;
	}

	@Column(name = "offlineProcess")
	public Integer getOfflineProcess() {
		return this.offlineProcess;
	}

	public void setOfflineProcess(Integer offlineProcess) {
		this.offlineProcess = offlineProcess;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TProcessInfo")
	public Set<TProcessmaterialInfo> getTProcessmaterialInfos() {
		return this.TProcessmaterialInfos;
	}

	public void setTProcessmaterialInfos(
			Set<TProcessmaterialInfo> TProcessmaterialInfos) {
		this.TProcessmaterialInfos = TProcessmaterialInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TProcessInfo")
	public Set<TPartProcessCost> getTPartProcessCosts() {
		return this.TPartProcessCosts;
	}

	public void setTPartProcessCosts(Set<TPartProcessCost> TPartProcessCosts) {
		this.TPartProcessCosts = TPartProcessCosts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TProcessInfo")
	public Set<TJobInfo> getTJobInfos() {
		return this.TJobInfos;
	}

	public void setTJobInfos(Set<TJobInfo> TJobInfos) {
		this.TJobInfos = TJobInfos;
	}
	
	@Column(name = "Engname")
	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}
	
	@Column(name = "capacity")
	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "processType")
	public Integer getProcessType() {
		return processType;
	}

	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	@Column(name = "needcheck")
	public Integer getNeedcheck() {
		return needcheck;
	}

	public void setNeedcheck(Integer needcheck) {
		this.needcheck = needcheck;
	}

	@Column(name = "checkType")
	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	@Column(name = "process_order")
	public Integer getProcessOrder() {
		return processOrder;
	}

	public void setProcessOrder(Integer processOrder) {
		this.processOrder = processOrder;
	}

	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
	
}