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

import org.hibernate.annotations.OrderBy;

import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserProdctionPlan;

/**
 * TJobplanInfo entity. @author MyEclipse Persistence Tools
 * ��ҵ�ƻ���
 */
@Entity
@Table(name = "t_jobplan_info")
public class TJobplanInfo implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private Long id;//��ҵ�ƻ�id
	private String  planNo;//�����ƻ����
	private TPartTypeInfo TPartTypeInfo;//������ͱ�
	private String no;//��ҵ�ƻ����
	private String name;//��ҵ�ƻ�����
	private Integer planNum;//�ƻ�����
	private Integer finishNum;//�������
	private Date planStarttime;//�ƻ���ʼʱ��
	private Date planEndtime;//�ƻ�����ʱ��
	private Integer priority;//���ȼ�
	private Integer status;//״̬
	private Double process;//����
	private Date realStarttime;//ʵ�ʿ�ʼʱ��
	private Date realEndtime;//ʵ�ʽ���ʱ��
	private Date finishDate;//����ʱ��
	private Integer childrenTotalNum;//�Ӽƻ��Ѹ��ǲ���
	private Integer qualifiedNum;//�ϸ���
	private String nodeid;//�ڵ�id
	private Integer planType;//�ƻ�����
	private TJobplanInfo TJobplanInfo;//����ҵ�ƻ�
	private Date createDate;//����ʱ��
	private Integer allocatedNum;//��������������
	private Integer scrapNum;//���μƻ���������
	private Set<TJobInfo> TJobInfos = new HashSet<TJobInfo>(0);
	private Set<TJobplanInfo> TJobplanInfos = new HashSet<TJobplanInfo>(0);
	// Constructors

	/** default constructor */
	public TJobplanInfo() {
	}

	/** minimal constructor */
	public TJobplanInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TJobplanInfo(Long id, String planNo,
			TPartTypeInfo TPartTypeInfo, String no, String name,
			Integer planNum, Integer finishNum, Date planStarttime,
			Date planEndtime, Integer priority, Integer status,
			Double process, Date realStarttime,
			Date finishDate, Integer childrenTotalNum,
			Set<TJobInfo> TJobInfos) {
		this.id = id;
		this.planNo = planNo;
		this.TPartTypeInfo = TPartTypeInfo;
		this.no = no;
		this.name = name;
		this.planNum = planNum;
		this.finishNum = finishNum;
		this.planStarttime = planStarttime;
		this.planEndtime = planEndtime;
		this.priority = priority;
		this.status = status;
		this.process = process;
		this.realStarttime = realStarttime;
		this.finishDate = finishDate;
		this.childrenTotalNum = childrenTotalNum;
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

	@Column(name ="planNo")
	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partID")
	public TPartTypeInfo getTPartTypeInfo() {
		return this.TPartTypeInfo;
	}

	public void setTPartTypeInfo(TPartTypeInfo TPartTypeInfo) {
		this.TPartTypeInfo = TPartTypeInfo;
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

	@Column(name = "planNum")
	public Integer getPlanNum() {
		return this.planNum;
	}

	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}

	@Column(name = "finishNum")
	public Integer getFinishNum() {
		return this.finishNum;
	}

	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
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

	@Column(name = "priority")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "process")
	public Double getProcess() {
		return this.process;
	}

	public void setProcess(Double process) {
		this.process = process;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finish_date", length = 0)
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name = "children_totalNum")
	public Integer getChildrenTotalNum() {
		return this.childrenTotalNum;
	}

	public void setChildrenTotalNum(Integer childrenTotalNum) {
		this.childrenTotalNum = childrenTotalNum;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TJobplanInfo")
	public Set<TJobInfo> getTJobInfos() {
		return this.TJobInfos;
	}

	public void setTJobInfos(Set<TJobInfo> TJobInfos) {
		this.TJobInfos = TJobInfos;
	}

	@Column(name = "qualifiedNum")
	public Integer getQualifiedNum() {
		return qualifiedNum;
	}

	public void setQualifiedNum(Integer qualifiedNum) {
		this.qualifiedNum = qualifiedNum;
	}
	
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
	@Column(name = "plan_type")
	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "p_id")
	public TJobplanInfo getTJobplanInfo() {
		return TJobplanInfo;
	}

	public void setTJobplanInfo(TJobplanInfo tJobplanInfo) {
		TJobplanInfo = tJobplanInfo;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TJobplanInfo") 
	public Set<TJobplanInfo> getTJobplanInfos() {
		return TJobplanInfos;
	}

	public void setTJobplanInfos(Set<TJobplanInfo> tJobplanInfos) {
		TJobplanInfos = tJobplanInfos;
	}
	@Column(name = "allocated_num")
	public Integer getAllocatedNum() {
		return allocatedNum;
	}

	public void setAllocatedNum(Integer allocatedNum) {
		this.allocatedNum = allocatedNum;
	}

	@Column(name = "createDate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "scrapNum")
	public Integer getScrapNum() {
		return scrapNum;
	}

	public void setScrapNum(Integer scrapNum) {
		this.scrapNum = scrapNum;
	}
	
	
}