package smtcl.mocs.pojos.job;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TMaterailTypeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_materail_type_info")
public class TMaterailTypeInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String no;
	private String name;
	private String norm;
	private String unit;
	private Double price;
	private String typeno;
	private String memo;
	private Integer PId;
	private Integer status;
	private Double density;
	private String nodeId;
	private Set<TProcessmaterialInfo> TProcessmaterialInfos = new HashSet<TProcessmaterialInfo>(
			0);

	// Constructors
	
	/** default constructor */
	public TMaterailTypeInfo() {
	}

	/** minimal constructor */
	public TMaterailTypeInfo(Long id, Integer PId) {
		this.id = id;
		this.PId = PId;
	}

	/** full constructor */
	public TMaterailTypeInfo(Long id, String no, String name, String norm,
			String unit, Double price, String typeno, String memo, Integer PId,
			Integer status, Double density,
			Set<TProcessmaterialInfo> TProcessmaterialInfos) {
		this.id = id;
		this.no = no;
		this.name = name;
		this.norm = norm;
		this.unit = unit;
		this.price = price;
		this.typeno = typeno;
		this.memo = memo;
		this.PId = PId;
		this.status = status;
		this.density = density;
		this.TProcessmaterialInfos = TProcessmaterialInfos;
	}

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

	@Column(name = "norm", length = 50)
	public String getNorm() {
		return this.norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	@Column(name = "unit", length = 10)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "price", precision = 10, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "typeno", length = 50)
	public String getTypeno() {
		return this.typeno;
	}

	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "p_id")
	public Integer getPId() {
		return this.PId;
	}

	public void setPId(Integer PId) {
		this.PId = PId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "density", precision = 15, scale = 5)
	public Double getDensity() {
		return this.density;
	}

	public void setDensity(Double density) {
		this.density = density;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TMaterailTypeInfo")
	public Set<TProcessmaterialInfo> getTProcessmaterialInfos() {
		return this.TProcessmaterialInfos;
	}

	public void setTProcessmaterialInfos(
			Set<TProcessmaterialInfo> TProcessmaterialInfos) {
		this.TProcessmaterialInfos = TProcessmaterialInfos;
	}
	
	@Column(name = "nodeID")
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

}