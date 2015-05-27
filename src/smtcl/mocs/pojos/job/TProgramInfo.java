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
 * ������Ϣ��
 */
@Entity
@Table(name = "t_program_info")
public class TProgramInfo implements Serializable{
	
	//fields
	private Long id;//������Ϣid
	private String progNo;//������Ϣ���
	private String progName;//������Ϣ����
	private String creator;//������
	private Date createTime;//����ʱ��
	private String updator;//������
	private Date updateTime;//����ʱ��
	private String content;//����
	private String nodeid;

	// Constructors

	/** default constructor */
	public TProgramInfo() {
		super();
	}

	/** full constructor */
	public TProgramInfo(Long id, String progNo, String progName,
			String creator, Date createTime, String updator, Date updateTime,
			String content) {
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

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "content", length = 50)
	public String getContent() {
		return this.content;
	}
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
}
