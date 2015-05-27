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

/**
 * TProcessplanInfo entity. @author MyEclipse Persistence Tools
 * 工艺方案表
 */
@Entity
@Table(name = "t_processplan_info")
public class TProcessplanInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TPartTypeInfo TPartTypeInfo;
	private String name;
	private String description;
	private Integer materialId;
	private Integer processNum;
	private String version;
	private String operator;
	private Date createDate;
	private Integer status;
	private Integer defaultSelected;
	private String nodeid;


	private Set<TProcessInfo> TProcessInfos = new HashSet<TProcessInfo>(0);

	// Constructors

	/** default constructor */
	public TProcessplanInfo() {
	}

	/** minimal constructor */
	public TProcessplanInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TProcessplanInfo(Long id, TPartTypeInfo TPartTypeInfo,
			String name, String description, Integer materialId,
			Integer processNum, String version, Set<TProcessInfo> TProcessInfos) {
		this.id = id;
		this.TPartTypeInfo = TPartTypeInfo;
		this.name = name;
		this.description = description;
		this.materialId = materialId;
		this.processNum = processNum;
		this.version = version;
		this.TProcessInfos = TProcessInfos;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parttypeID")
	public TPartTypeInfo getTPartTypeInfo() {
		return this.TPartTypeInfo;
	}

	public void setTPartTypeInfo(TPartTypeInfo TPartTypeInfo) {
		this.TPartTypeInfo = TPartTypeInfo;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "materialID")
	public Integer getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@Column(name = "processNum")
	public Integer getProcessNum() {
		return this.processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}

	@Column(name = "version", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TProcessplanInfo")
	public Set<TProcessInfo> getTProcessInfos() {
		return this.TProcessInfos;
	}

	public void setTProcessInfos(Set<TProcessInfo> TProcessInfos) {
		this.TProcessInfos = TProcessInfos;
	}
	
	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name = "createDate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "defaultSelected")
	public Integer getDefaultSelected() {
		return defaultSelected;
	}

	public void setDefaultSelected(Integer defaultSelected) {
		this.defaultSelected = defaultSelected;
	}
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
		
	
}