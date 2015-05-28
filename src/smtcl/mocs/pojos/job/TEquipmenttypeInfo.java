package smtcl.mocs.pojos.job;
// default package

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

import org.hibernate.annotations.OrderBy;

/**
 * TEquipmenttypeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_equipmenttype_info")
public class TEquipmenttypeInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String equipmentType;
	private String description;
	private String typecode;        //分类编码
	private String norm;            //型号/牌号
	private String cnc;             //数控系统
	private TEquipmenttypeInfo TEquipmenttypeInfo;//父设备类型节点
	private Integer level;          //层级类别
	private String isdel; //是否删除0正常1删除
	private String nodeid;
	private String erpResouceCode;
	private String erpResouceDesc;
	
	private Set<RProcessEquipmenttype> RProcessEquipmenttypes = new HashSet<RProcessEquipmenttype>(
			0);
	private Set<TEquipmenttypeInfo> TEquipmenttypeInfos = new HashSet<TEquipmenttypeInfo>(
			0);
	// Constructors

	/** default constructor */
	public TEquipmenttypeInfo() {
	}

	/** minimal constructor */
	public TEquipmenttypeInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TEquipmenttypeInfo(Long id, String equipmentType,
			String description,
			Set<RProcessEquipmenttype> RProcessEquipmenttypes) {
		this.id = id;
		this.equipmentType = equipmentType;
		this.description = description;
		this.RProcessEquipmenttypes = RProcessEquipmenttypes;
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

	@Column(name = "equipmentType", length = 30)
	public String getEquipmentType() {
		return this.equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TEquipmenttypeInfo")
	public Set<RProcessEquipmenttype> getRProcessEquipmenttypes() {
		return this.RProcessEquipmenttypes;
	}

	public void setRProcessEquipmenttypes(
			Set<RProcessEquipmenttype> RProcessEquipmenttypes) {
		this.RProcessEquipmenttypes = RProcessEquipmenttypes;
	}
	
	@Column(name = "typecode")
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	@Column(name = "norm")
	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	@Column(name = "CNC")
	public String getCnc() {
		return cnc;
	}

	public void setCnc(String cnc) {
		this.cnc = cnc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p_equtype_id")
	public TEquipmenttypeInfo getTEquipmenttypeInfo() {
		return this.TEquipmenttypeInfo;
	}

	public void setTEquipmenttypeInfo(TEquipmenttypeInfo TEquipmenttypeInfo) {
		this.TEquipmenttypeInfo = TEquipmenttypeInfo;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "isdel")
	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TEquipmenttypeInfo")
	@OrderBy(clause = "id asc")
	public Set<TEquipmenttypeInfo> getTEquipmenttypeInfos() {
		return this.TEquipmenttypeInfos;
	}

	public void setTEquipmenttypeInfos(
			Set<TEquipmenttypeInfo> TEquipmenttypeInfos) {
		this.TEquipmenttypeInfos = TEquipmenttypeInfos;
	}
	
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
	@Column(name = "erp_resouce_code")
	public String getErpResouceCode() {
		return erpResouceCode;
	}

	public void setErpResouceCode(String erpResouceCode) {
		this.erpResouceCode = erpResouceCode;
	}
	
	@Column(name = "erp_resouce_desc")
	public String getErpResouceDesc() {
		return erpResouceDesc;
	}

	public void setErpResouceDesc(String erpResouceDesc) {
		this.erpResouceDesc = erpResouceDesc;
	}
	
	
}