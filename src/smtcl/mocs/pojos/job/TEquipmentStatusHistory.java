package smtcl.mocs.pojos.job;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TEquipmentStatusHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_equipment_status_history")
public class TEquipmentStatusHistory implements java.io.Serializable {

	// Fields

	private Long id;
	private String equSerialNo;
	private Date updateTime;
	private String status;
	private String programm;
	private String toolNo;
	private String part;
	private String partTimeCount;
	private String feedSpeed;
	private String feedrate;
	private String axisspeed;
	private String axisspeedRate;
	private String xfeed;
	private String yfeed;
	private String zfeed;
	private String afeed;
	private String bfeed;
	private String cfeed;
	private String ufeed;
	private String vfeed;
	private String wfeed;
	private String cuttingpower;
	private String operator;
	private String spindleLoad;
	private String partCount;

	// Constructors

	/** default constructor */
	public TEquipmentStatusHistory() {
	}

	/** minimal constructor */
	public TEquipmentStatusHistory(String equSerialNo, Date updateTime,
			String status) {
		this.equSerialNo = equSerialNo;
		this.updateTime = updateTime;
		this.status = status;
	}

	/** full constructor */
	public TEquipmentStatusHistory(String equSerialNo, Date updateTime,
			String status, String programm, String toolNo, String part,
			String partTimeCount, String feedSpeed, String feedrate,
			String axisspeed, String axisspeedRate, String xfeed, String yfeed,
			String zfeed, String afeed, String bfeed, String cfeed,
			String ufeed, String vfeed, String wfeed, String cuttingpower,
			String operator, String spindleLoad, String partCount) {
		this.equSerialNo = equSerialNo;
		this.updateTime = updateTime;
		this.status = status;
		this.programm = programm;
		this.toolNo = toolNo;
		this.part = part;
		this.partTimeCount = partTimeCount;
		this.feedSpeed = feedSpeed;
		this.feedrate = feedrate;
		this.axisspeed = axisspeed;
		this.axisspeedRate = axisspeedRate;
		this.xfeed = xfeed;
		this.yfeed = yfeed;
		this.zfeed = zfeed;
		this.afeed = afeed;
		this.bfeed = bfeed;
		this.cfeed = cfeed;
		this.ufeed = ufeed;
		this.vfeed = vfeed;
		this.wfeed = wfeed;
		this.cuttingpower = cuttingpower;
		this.operator = operator;
		this.spindleLoad = spindleLoad;
		this.partCount = partCount;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "equ_SerialNo", nullable = false, length = 30)
	public String getEquSerialNo() {
		return this.equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "Programm", length = 50)
	public String getProgramm() {
		return this.programm;
	}

	public void setProgramm(String programm) {
		this.programm = programm;
	}

	@Column(name = "ToolNo", length = 20)
	public String getToolNo() {
		return this.toolNo;
	}

	public void setToolNo(String toolNo) {
		this.toolNo = toolNo;
	}

	@Column(name = "Part", length = 30)
	public String getPart() {
		return this.part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	@Column(name = "partTimeCount", length = 20)
	public String getPartTimeCount() {
		return this.partTimeCount;
	}

	public void setPartTimeCount(String partTimeCount) {
		this.partTimeCount = partTimeCount;
	}

	@Column(name = "FeedSpeed", length = 20)
	public String getFeedSpeed() {
		return this.feedSpeed;
	}

	public void setFeedSpeed(String feedSpeed) {
		this.feedSpeed = feedSpeed;
	}

	@Column(name = "feedrate", length = 20)
	public String getFeedrate() {
		return this.feedrate;
	}

	public void setFeedrate(String feedrate) {
		this.feedrate = feedrate;
	}

	@Column(name = "axisspeed", length = 20)
	public String getAxisspeed() {
		return this.axisspeed;
	}

	public void setAxisspeed(String axisspeed) {
		this.axisspeed = axisspeed;
	}

	@Column(name = "axisspeedRate", length = 20)
	public String getAxisspeedRate() {
		return this.axisspeedRate;
	}

	public void setAxisspeedRate(String axisspeedRate) {
		this.axisspeedRate = axisspeedRate;
	}

	@Column(name = "Xfeed", length = 20)
	public String getXfeed() {
		return this.xfeed;
	}

	public void setXfeed(String xfeed) {
		this.xfeed = xfeed;
	}

	@Column(name = "Yfeed", length = 20)
	public String getYfeed() {
		return this.yfeed;
	}

	public void setYfeed(String yfeed) {
		this.yfeed = yfeed;
	}

	@Column(name = "Zfeed", length = 20)
	public String getZfeed() {
		return this.zfeed;
	}

	public void setZfeed(String zfeed) {
		this.zfeed = zfeed;
	}

	@Column(name = "Afeed", length = 20)
	public String getAfeed() {
		return this.afeed;
	}

	public void setAfeed(String afeed) {
		this.afeed = afeed;
	}

	@Column(name = "Bfeed", length = 20)
	public String getBfeed() {
		return this.bfeed;
	}

	public void setBfeed(String bfeed) {
		this.bfeed = bfeed;
	}

	@Column(name = "Cfeed", length = 20)
	public String getCfeed() {
		return this.cfeed;
	}

	public void setCfeed(String cfeed) {
		this.cfeed = cfeed;
	}

	@Column(name = "Ufeed", length = 20)
	public String getUfeed() {
		return this.ufeed;
	}

	public void setUfeed(String ufeed) {
		this.ufeed = ufeed;
	}

	@Column(name = "Vfeed", length = 20)
	public String getVfeed() {
		return this.vfeed;
	}

	public void setVfeed(String vfeed) {
		this.vfeed = vfeed;
	}

	@Column(name = "Wfeed", length = 20)
	public String getWfeed() {
		return this.wfeed;
	}

	public void setWfeed(String wfeed) {
		this.wfeed = wfeed;
	}

	@Column(name = "cuttingpower", length = 20)
	public String getCuttingpower() {
		return this.cuttingpower;
	}

	public void setCuttingpower(String cuttingpower) {
		this.cuttingpower = cuttingpower;
	}

	@Column(name = "operator", length = 20)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "SpindleLoad", length = 20)
	public String getSpindleLoad() {
		return this.spindleLoad;
	}

	public void setSpindleLoad(String spindleLoad) {
		this.spindleLoad = spindleLoad;
	}

	@Column(name = "partCount", length = 20)
	public String getPartCount() {
		return this.partCount;
	}

	public void setPartCount(String partCount) {
		this.partCount = partCount;
	}

}