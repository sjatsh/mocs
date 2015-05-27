package smtcl.mocs.pojos.device;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TUserEquEvents entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserEquEvents")
public class TUserEquEvents implements java.io.Serializable {

	// Fields

	private Long id;//ID
	private String equSerialNo;//设备序列号
	private String eventId;//事件ID
	private Date eventTime;//事件时间
	private String eventName;//事件名称
	private String programmname;//程序名
	private String eventMemo;//备注

	// Constructors

	/** default constructor */
	public TUserEquEvents() {
	}

	/** minimal constructor */
	public TUserEquEvents(Long id, String equSerialNo) {
		this.id = id;
		this.equSerialNo = equSerialNo;
	}

	/** full constructor */
	public TUserEquEvents(Long id, String equSerialNo, String eventId, Date eventTime,
			String eventName, String programmname, String eventMemo) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.eventId = eventId;
		this.eventTime = eventTime;
		this.eventName = eventName;
		this.programmname = programmname;
		this.eventMemo = eventMemo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "equ_serialNo", nullable = false, length = 30)
	public String getEquSerialNo() {
		return this.equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	@Column(name = "eventID", length = 20)
	public String getEventId() {
		return this.eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eventTime", length = 7)
	public Date getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	@Column(name = "eventName", length = 20)
	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Column(name = "Programmname", length = 200)
	public String getProgrammname() {
		return this.programmname;
	}

	public void setProgrammname(String programmname) {
		this.programmname = programmname;
	}

	@Column(name = "eventMemo", length = 100)
	public String getEventMemo() {
		return this.eventMemo;
	}

	public void setEventMemo(String eventMemo) {
		this.eventMemo = eventMemo;
	}

}