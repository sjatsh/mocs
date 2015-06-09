package smtcl.mocs.pojos.job;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProgramInfo entity. @author MyEclipse Persistence Tools
 * 程序信息表
 */
@Entity
@Table(name = "t_program_info")
public class TProgramInfo implements Serializable{
	
	//fields
	private Long id;//程序信息id
	private String progNo;//程序信息编号
	private String progName;//程序信息名称
	private String creator;//创建人
	private Date createTime;//创建时间
	private String updator;//更新人
	private Date updateTime;//更新时间
	private byte[] content;//内容
	private String nodeid;
	private String versionNo;
	private String programPath;
	private String status;
	private String md5;
	private String describe2;

	// Constructors

	/** default constructor */
	public TProgramInfo() {
		super();
	}

	/** full constructor */
	public TProgramInfo(Long id, String progNo, String progName,
			String creator, Date createTime, String updator, Date updateTime,
			byte[] content) {
		super();
		this.id = id;
		this.progNo = progNo;
		this.progName = progName;
		this.creator = creator;
		this.createTime = createTime;
		this.updator = updator;
		this.updateTime = updateTime;
		this.content = content;
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
	
	@Column(name = "prog_no")
	public String getProgNo() {
		return this.progNo;
	}

	public void setProgNo(String progNo) {
		this.progNo = progNo;
	}

	@Column(name = "prog_name")
	public String getProgName() {
		return this.progName;
	}

	public void setProgName(String progName) {
		this.progName = progName;
	}

	@Column(name = "creator")
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "updator")
	public String getUpdator() {
		return this.updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	@Column(name = "content")
	public byte[] getContent() {
		return this.content;
	}
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
    
	@Column(name = "versionNo")
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
    
	@Column(name = "programPath")
	public String getProgramPath() {
		return programPath;
	}

	public void setProgramPath(String programPath) {
		this.programPath = programPath;
	}
    
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
	@Column(name = "md5")
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
    
	@Column(name = "describe2")
	public String getDescribe2() {
		return describe2;
	}

	public void setDescribe2(String describe2) {
		this.describe2 = describe2;
	}
	
	
	
	
	
}
