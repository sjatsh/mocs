package smtcl.mocs.pojos.job;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPartBasicInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_part_basic_info")
public class TPartBasicInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String no;
	private String name;
	private Date onlineDate;
	private Date offlineDate;
	private Long partTypeId;
	private String batchNo;
	private Long currentProcessID;
	private Date processDate;
	private Integer reworkCount;
	private Integer status;

	// Constructors

	/** default constructor */
	public TPartBasicInfo() {
	}

	/** full constructor */
	public TPartBasicInfo(String no, String name, Date onlineDate,
			Date offlineDate) {
		this.no = no;
		this.name = name;
		this.onlineDate = onlineDate;
		this.offlineDate = offlineDate;
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

	@Column(name = "no", length = 30)
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

	@Column(name = "online_date", length = 0)
	public Date getOnlineDate() {
		return this.onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	@Column(name = "offline_date", length = 0)
	public Date getOfflineDate() {
		return this.offlineDate;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	@Column(name = "parttype_id")
	public Long getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(Long partTypeId) {
		this.partTypeId = partTypeId;
	}

	@Column(name = "batchNo")
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "currentProcessID")
	public Long getCurrentProcessID() {
		return currentProcessID;
	}

	public void setCurrentProcessID(Long currentProcessID) {
		this.currentProcessID = currentProcessID;
	}

	@Column(name = "processDate")
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	@Column(name = "rework_count")
	public Integer getReworkCount() {
		return reworkCount;
	}

	public void setReworkCount(Integer reworkCount) {
		this.reworkCount = reworkCount;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}