package smtcl.mocs.pojos.device;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TNodeProductionProfiles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_NodeProductionProfiles")
public class TNodeProductionProfiles implements java.io.Serializable {

	// Fields

	private Long id;
	private TNodes TNodes;
	private Date updateTime;//更新时间
	private Long uprodId;//产品ID(零件类别ID)
	private Double dailyOutput;//日产量
	private Double weeklyOutput;//周产量
	private Double monthlyOutput;//月产量
	private Double annualOutput;//年产量
	private Double totalOutput;//总产量

	// Constructors

	/** default constructor */
	public TNodeProductionProfiles() {
	}

	/** minimal constructor */
	public TNodeProductionProfiles(TNodes TNodes, Long uprodId) {
		this.TNodes = TNodes;
		this.uprodId = uprodId;
	}

	/** full constructor */
	public TNodeProductionProfiles(TNodes TNodes, Date updateTime,
			Long uprodId, Double dailyOutput, Double weeklyOutput,
			Double monthlyOutput, Double annualOutput, Double totalOutput) {
		this.TNodes = TNodes;
		this.updateTime = updateTime;
		this.uprodId = uprodId;
		this.dailyOutput = dailyOutput;
		this.weeklyOutput = weeklyOutput;
		this.monthlyOutput = monthlyOutput;
		this.annualOutput = annualOutput;
		this.totalOutput = totalOutput;
	}

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
	@JoinColumn(name = "nodeID", nullable = false)
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}

	@Column(name = "updateTime", length = 11)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "uProd_ID", nullable = false, precision = 22, scale = 0)
	public Long getUprodId() {
		return this.uprodId;
	}

	public void setUprodId(Long uprodId) {
		this.uprodId = uprodId;
	}

	@Column(name = "dailyOutput", precision = 10, scale = 1)
	public Double getDailyOutput() {
		return this.dailyOutput;
	}

	public void setDailyOutput(Double dailyOutput) {
		this.dailyOutput = dailyOutput;
	}

	@Column(name = "weeklyOutput", precision = 10, scale = 1)
	public Double getWeeklyOutput() {
		return this.weeklyOutput;
	}

	public void setWeeklyOutput(Double weeklyOutput) {
		this.weeklyOutput = weeklyOutput;
	}

	@Column(name = "monthlyOutput", precision = 10, scale = 1)
	public Double getMonthlyOutput() {
		return this.monthlyOutput;
	}

	public void setMonthlyOutput(Double monthlyOutput) {
		this.monthlyOutput = monthlyOutput;
	}

	@Column(name = "annualOutput", precision = 15, scale = 1)
	public Double getAnnualOutput() {
		return this.annualOutput;
	}

	public void setAnnualOutput(Double annualOutput) {
		this.annualOutput = annualOutput;
	}

	@Column(name = "totalOutput", precision = 15, scale = 1)
	public Double getTotalOutput() {
		return this.totalOutput;
	}

	public void setTotalOutput(Double totalOutput) {
		this.totalOutput = totalOutput;
	}

}