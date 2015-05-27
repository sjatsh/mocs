package smtcl.mocs.pojos.job;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCuttertypeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_production_events")
public class TProductionEvents implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
    private String eventNo;
    private String jobdispatchNo;
    private Integer processNum;
    private String operatorNo;
    private Date operateDate;
    private String operateReason;
    private Integer partTypeID;
    private String equSerialNo;
    private String responseNo;
    private String responseProcessNo;
    private Date responseDate;
    private Date starttime;
    private Date endtime;
    private Integer eventType;
    private Integer workTime;
    private Integer flag;
   

	// Constructors

	/** default constructor */
	public TProductionEvents() {
	}

	/** minimal constructor */
	public TProductionEvents(Integer id) {
		this.id = id;
	}

	

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    
	@Column(name = "eventNo")
	public String getEventNo() {
		return eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}
    
	@Column(name = "jobdispatchNo")
	public String getJobdispatchNo() {
		return jobdispatchNo;
	}

	public void setJobdispatchNo(String jobdispatchNo) {
		this.jobdispatchNo = jobdispatchNo;
	}
    
	@Column(name = "processNum")
	public Integer getProcessNum() {
		return processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}
    
	@Column(name = "operator_no")
	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
    
	@Column(name = "operate_date")
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
    
	@Column(name = "operate_reason")
	public String getOperateReason() {
		return operateReason;
	}

	public void setOperateReason(String operateReason) {
		this.operateReason = operateReason;
	}
    
	@Column(name = "partTypeID")
	public Integer getPartTypeID() {
		return partTypeID;
	}

	public void setPartTypeID(Integer partTypeID) {
		this.partTypeID = partTypeID;
	}
    
	@Column(name = "equ_SerialNo")
	public String getEquSerialNo() {
		return equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}
    
	@Column(name = "response_no")
	public String getResponseNo() {
		return responseNo;
	}

	public void setResponseNo(String responseNo) {
		this.responseNo = responseNo;
	}
    
	@Column(name = "response_processNo")
	public String getResponseProcessNo() {
		return responseProcessNo;
	}

	public void setResponseProcessNo(String responseProcessNo) {
		this.responseProcessNo = responseProcessNo;
	}
    
	@Column(name = "responseDate")
	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
    
	@Column(name = "starttime")
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
    
	@Column(name = "endtime")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
    
	@Column(name = "eventType")
	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	@Column(name = "workTime")
	public Integer getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Integer workTime) {
		this.workTime = workTime;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
