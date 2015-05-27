package smtcl.mocs.pojos.job;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

/**
 * TMaterialClass entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_material_class")
public class TMaterialClass implements java.io.Serializable {

	// Fields

	private Long MClassid;
	private TMaterialClass TMaterialClass;
	private String MClassname;
	private String MClassno;
	private String MClasstype;
	private String MMemo;
	private Integer MClasslevel;
	private Integer MStatus;
	private Double MDensity;
	private String nodeId;
	private Set<TMaterialClass> TMaterialClasses = new HashSet<TMaterialClass>(
			0);

	// Constructors

	/** default constructor */
	public TMaterialClass() {
	}

	/** minimal constructor */
	public TMaterialClass(Integer MStatus) {
		this.MStatus = MStatus;
	}

	/** full constructor */
	public TMaterialClass(TMaterialClass TMaterialClass, String MClassname,
			String MClassno, String MClasstype, String MMemo,
			Integer MClasslevel, Integer MStatus,
			Set<TMaterialClass> TMaterialClasses) {
		this.TMaterialClass = TMaterialClass;
		this.MClassname = MClassname;
		this.MClassno = MClassno;
		this.MClasstype = MClasstype;
		this.MMemo = MMemo;
		this.MClasslevel = MClasslevel;
		this.MStatus = MStatus;
		this.TMaterialClasses = TMaterialClasses;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "m_classid", unique = true, nullable = false)
	public Long getMClassid() {
		return this.MClassid;
	}

	public void setMClassid(Long MClassid) {
		this.MClassid = MClassid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "m_p_classid")
	public TMaterialClass getTMaterialClass() {
		return this.TMaterialClass;
	}

	public void setTMaterialClass(TMaterialClass TMaterialClass) {
		this.TMaterialClass = TMaterialClass;
	}

	@Column(name = "m_classname", length = 20)
	public String getMClassname() {
		return this.MClassname;
	}

	public void setMClassname(String MClassname) {
		this.MClassname = MClassname;
	}

	@Column(name = "m_classno", length = 50)
	public String getMClassno() {
		return this.MClassno;
	}

	public void setMClassno(String MClassno) {
		this.MClassno = MClassno;
	}

	@Column(name = "m_classtype", length = 50)
	public String getMClasstype() {
		return this.MClasstype;
	}

	public void setMClasstype(String MClasstype) {
		this.MClasstype = MClasstype;
	}

	@Column(name = "m_memo", length = 200)
	public String getMMemo() {
		return this.MMemo;
	}

	public void setMMemo(String MMemo) {
		this.MMemo = MMemo;
	}

	@Column(name = "m_classlevel")
	public Integer getMClasslevel() {
		return this.MClasslevel;
	}

	public void setMClasslevel(Integer MClasslevel) {
		this.MClasslevel = MClasslevel;
	}

	@Column(name = "m_status", nullable = false)
	public Integer getMStatus() {
		return this.MStatus;
	}

	public void setMStatus(Integer MStatus) {
		this.MStatus = MStatus;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TMaterialClass")
	@OrderBy(clause="m_classid")
	public Set<TMaterialClass> getTMaterialClasses() {
		return this.TMaterialClasses;
	}

	public void setTMaterialClasses(Set<TMaterialClass> TMaterialClasses) {
		this.TMaterialClasses = TMaterialClasses;
	}
	
	@Column(name = "m_density")
	public Double getMDensity() {
		return MDensity;
	}

	public void setMDensity(Double mDensity) {
		MDensity = mDensity;
	}
	
	@Column(name = "nodeID")
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

}