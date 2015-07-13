package smtcl.mocs.pojos.device;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TUserEquCurStatus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserEquCurStatus")
public class TUserEquCurStatus implements java.io.Serializable {

	// Fields

	private String equSerialNo;//�豸���к�
	private Date updateTime;//״̬ʱ��
	private String status;//����״̬
	private String programm;//��ǰִ���ļ�
	private String toolNo;//��ǰ���ߺ�
	private String part;//�ڼӹ������
	private String partTimeCount;//��ǰ������ʱ
	private String feedSpeed;//��ǰ�����ٶ�
	private String feedrate;//��������
	private String axisspeed;//��ǰ����ת��
	private String axisspeedRate;//���ᱶ��
	private String xfeed;//����X
	private String yfeed;//����Y
	private String zfeed;//����Z
	private String afeed;//����a
	private String bfeed;//����b
	private String cfeed;//����c
	private String ufeed;//����u
	private String vfeed;//����v
	private String wfeed;//����w
	private String cuttingpower;//��������
	private String operator;//������Ա
	private String spindleLoad;//���Ḻ��
	private String partCount;//��������
	private Double timeZone;

	// Constructors

	/** default constructor */
	public TUserEquCurStatus() {
	}

	/** minimal constructor */
	public TUserEquCurStatus(String equSerialNo, Date updateTime, String status) {
		this.equSerialNo = equSerialNo;
		this.updateTime = updateTime;
		this.status = status;
	}

	/** full constructor */
	public TUserEquCurStatus(String equSerialNo, Date updateTime,
			String status, String programm, String toolNo, String part,
			String partTimeCount, String feedSpeed, String feedrate,
			String axisspeed, String axisspeedRate, String xfeed, String yfeed,
			String zfeed, String cuttingpower, String operator,
			String spindleLoad, String partCount) {
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
		this.cuttingpower = cuttingpower;
		this.operator = operator;
		this.spindleLoad = spindleLoad;
		this.partCount = partCount;
	}

	// Property accessors
	@Id
	@Column(name = "equ_SerialNo", nullable = false, length = 30)
	public String getEquSerialNo() {
		return this.equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime", nullable = false, length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "Programm", length = 200)
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
	
	@Column(name = "Afeed", length = 20)
	public String getAfeed() {
		return afeed;
	}

	public void setAfeed(String afeed) {
		this.afeed = afeed;
	}

	@Column(name = "Bfeed", length = 20)
	public String getBfeed() {
		return bfeed;
	}

	public void setBfeed(String bfeed) {
		this.bfeed = bfeed;
	}

	@Column(name = "Cfeed", length = 20)
	public String getCfeed() {
		return cfeed;
	}

	public void setCfeed(String cfeed) {
		this.cfeed = cfeed;
	}
	
	@Column(name = "Ufeed", length = 20)
	public String getUfeed() {
		return ufeed;
	}

	public void setUfeed(String ufeed) {
		this.ufeed = ufeed;
	}
	
	@Column(name = "Vfeed", length = 20)
	public String getVfeed() {
		return vfeed;
	}

	public void setVfeed(String vfeed) {
		this.vfeed = vfeed;
	}

	@Column(name = "Wfeed", length = 20)
	public String getWfeed() {
		return wfeed;
	}

	public void setWfeed(String wfeed) {
		this.wfeed = wfeed;
	}
	
	@Column(name = "timeZone")
	public Double getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Double timeZone) {
		this.timeZone = timeZone;
	}
	
	
}