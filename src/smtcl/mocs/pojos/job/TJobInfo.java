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
import javax.persistence.Transient;

/**
 * TJobInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_job_info")
public class TJobInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TJobplanInfo TJobplanInfo;
	private TProcessInfo TProcessInfo;
	private String no;
	private String name;
	private Date planStarttime;
	private Date planEndtime;
	private Integer status;
	private Integer planNum;
	private Integer finishNum;
	private Integer decomposition;
	private Date realStarttime;
	private Date realEndtime;
	private Integer theoryWorktime;//理论工时
	private Set<TJobdispatchlistInfo> TJobdispatchlistInfos = new HashSet<TJobdispatchlistInfo>(
			0);
	private String nodeid;//节点id

	// Constructors

	/** default constructor */
	public TJobInfo() {
	}

	/** minimal constructor */
	public TJobInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TJobInfo(Long id, TJobplanInfo TJobplanInfo,
			TProcessInfo TProcessInfo, String no, String name,
			Date planStarttime, Date planEndtime, Integer status,
			Integer planNum, Integer finishNum, Integer decomposition,
			Date realStarttime, Date realEndtime, Integer theoryWorktime,
			Set<TJobdispatchlistInfo> TJobdispatchlistInfos) {
		this.id = id;
		this.TJobplanInfo = TJobplanInfo;
		this.TProcessInfo = TProcessInfo;
		this.no = no;
		this.name = name;
		this.planStarttime = planStarttime;
		this.planEndtime = planEndtime;
		this.status = status;
		this.planNum = planNum;
		this.finishNum = finishNum;
		this.decomposition = decomposition;
		this.realStarttime = realStarttime;
		this.realEndtime = realEndtime;
		this.theoryWorktime = theoryWorktime;
		this.TJobdispatchlistInfos = TJobdispatchlistInfos;
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
	@JoinColumn(name = "jobplanID")
	public TJobplanInfo getTJobplanInfo() {
		return this.TJobplanInfo;
	}

	public void setTJobplanInfo(TJobplanInfo TJobplanInfo) {
		this.TJobplanInfo = TJobplanInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processID")
	public TProcessInfo getTProcessInfo() {
		return this.TProcessInfo;
	}

	public void setTProcessInfo(TProcessInfo TProcessInfo) {
		this.TProcessInfo = TProcessInfo;
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

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "plan_num")
	public Integer getPlanNum() {
		return this.planNum;
	}

	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}

	@Column(name = "finish_num")
	public Integer getFinishNum() {
		return this.finishNum;
	}

	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}

	@Column(name = "decomposition")
	public Integer getDecomposition() {
		return this.decomposition;
	}

	public void setDecomposition(Integer decomposition) {
		this.decomposition = decomposition;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TJobInfo")
	public Set<TJobdispatchlistInfo> getTJobdispatchlistInfos() {
		return this.TJobdispatchlistInfos;
	}

	public void setTJobdispatchlistInfos(
			Set<TJobdispatchlistInfo> TJobdispatchlistInfos) {
		this.TJobdispatchlistInfos = TJobdispatchlistInfos;
	}
	
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
}