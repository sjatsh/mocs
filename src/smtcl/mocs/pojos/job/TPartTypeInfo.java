package smtcl.mocs.pojos.job;
// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TPartTypeInfo entity. @author MyEclipse Persistence Tools
 * ���������Ϣ
 */
@Entity
@Table(name = "t_part_type_info")
public class TPartTypeInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;//�������id
	private String no;//������ͱ��
	private String name;//�����������
	private String typeNo;//�����������
	private String drawingno;//ͼֽ���
	private String filename;//ͼ�ļ���
	private String version;//������Ͱ汾
	private String source;//���������Դ
	private String status;//������ͱ��
	private String description;//�����������
	private Date createDate;//����ʱ��
	private Date updateDate;//������ʱ��
	private String originalStatus;//ԭʼ״̬
	private String path;//�������·��
	private String nodeid;

	private Set<RProdTypeComponentInfo> RProdTypeComponentInfos = new HashSet<RProdTypeComponentInfo>(
			0);
	private Set<TPartProcessCost> TPartProcessCosts = new HashSet<TPartProcessCost>(
			0);
	private Set<TProcessplanInfo> TProcessplanInfos = new HashSet<TProcessplanInfo>(
			0);
	private Set<TJobplanInfo> TJobplanInfos = new HashSet<TJobplanInfo>(0);

	// Constructors

	/** default constructor */
	public TPartTypeInfo() {
	}

	/** minimal constructor */
	public TPartTypeInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TPartTypeInfo(Long id, String no, String name, String typeNo,
			String drawingno, String filename, String version, String source,
			String status, String description, Date createDate,
			Date updateDate,
			Set<RProdTypeComponentInfo> RProdTypeComponentInfos,
			Set<TPartProcessCost> TPartProcessCosts,
			Set<TProcessplanInfo> TProcessplanInfos,
			Set<TJobplanInfo> TJobplanInfos,String path) {
		this.id = id;
		this.no = no;
		this.name = name;
		this.typeNo = typeNo;
		this.drawingno = drawingno;
		this.filename = filename;
		this.version = version;
		this.source = source;
		this.status = status;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.RProdTypeComponentInfos = RProdTypeComponentInfos;
		this.TPartProcessCosts = TPartProcessCosts;
		this.TProcessplanInfos = TProcessplanInfos;
		this.TJobplanInfos = TJobplanInfos;
		this.path=path;
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

	@Column(name = "typeNo")
	public String getTypeNo() {
		return this.typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	@Column(name = "drawingno", length = 50)
	public String getDrawingno() {
		return this.drawingno;
	}

	public void setDrawingno(String drawingno) {
		this.drawingno = drawingno;
	}

	@Column(name = "filename", length = 200)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "version", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "source")
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "status", length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate", length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPartTypeInfo")
	public Set<RProdTypeComponentInfo> getRProdTypeComponentInfos() {
		return this.RProdTypeComponentInfos;
	}

	public void setRProdTypeComponentInfos(
			Set<RProdTypeComponentInfo> RProdTypeComponentInfos) {
		this.RProdTypeComponentInfos = RProdTypeComponentInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPartTypeInfo")
	public Set<TPartProcessCost> getTPartProcessCosts() {
		return this.TPartProcessCosts;
	}

	public void setTPartProcessCosts(Set<TPartProcessCost> TPartProcessCosts) {
		this.TPartProcessCosts = TPartProcessCosts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPartTypeInfo")
	public Set<TProcessplanInfo> getTProcessplanInfos() {
		return this.TProcessplanInfos;
	}

	public void setTProcessplanInfos(Set<TProcessplanInfo> TProcessplanInfos) {
		this.TProcessplanInfos = TProcessplanInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPartTypeInfo")
	public Set<TJobplanInfo> getTJobplanInfos() {
		return this.TJobplanInfos;
	}
	
	public void setTJobplanInfos(Set<TJobplanInfo> TJobplanInfos) {
		this.TJobplanInfos = TJobplanInfos;
	}
	
	@Column(name = "original_status", length = 20)
	public String getOriginalStatus() {
		return originalStatus;
	}
	

	public void setOriginalStatus(String originalStatus) {
		this.originalStatus = originalStatus;
	}
	
	@Column(name = "path", length = 20)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
	
	
	
}