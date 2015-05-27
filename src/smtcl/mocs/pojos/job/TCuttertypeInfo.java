package smtcl.mocs.pojos.job;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ������������
 * TCuttertypeInfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cuttertype_info")
public class TCuttertypeInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;//������������
	private String no;//�������ͱ��
	private String norm;//���߹��
	private String remark;//��ע
	private Long pid;//�������id
	private String nodeId;//�ڵ�ID

	// Constructors

	/** default constructor */
	public TCuttertypeInfo() {
	}

	/** minimal constructor */
	public TCuttertypeInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TCuttertypeInfo(Long id, String name, String no, String norm,
			String remark, Long pid) {
		super();
		this.id = id;
		this.name = name;
		this.no = no;
		this.norm = norm;
		this.remark = remark;
		this.pid = pid;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "no", length = 30)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Long getPid() {
		return pid;
	}
	@Column(name="pid")
	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "norm", length = 50)
	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="nodeID")
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}