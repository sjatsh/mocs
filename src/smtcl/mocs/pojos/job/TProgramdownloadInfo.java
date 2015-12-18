package smtcl.mocs.pojos.job;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProgramdownloadInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_programdownload_info")
public class TProgramdownloadInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer programId;
	private Integer processId;
	private String equSerialNo;
	private Date downloadTime;
	private String userName;

	// Constructors

	/** default constructor */
	public TProgramdownloadInfo() {
	}

	/** full constructor */
	public TProgramdownloadInfo(Integer programId, Integer processId,
			String equSerialNo, Date downloadTime, String userName) {
		this.programId = programId;
		this.processId = processId;
		this.equSerialNo = equSerialNo;
		this.downloadTime = downloadTime;
		this.userName = userName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "programId")
	public Integer getProgramId() {
		return this.programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	@Column(name = "processId")
	public Integer getProcessId() {
		return this.processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	@Column(name = "equSerialNo", length = 50)
	public String getEquSerialNo() {
		return this.equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	@Column(name = "downloadTime", length = 19)
	public Date getDownloadTime() {
		return this.downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

	@Column(name = "userName", length = 10)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}