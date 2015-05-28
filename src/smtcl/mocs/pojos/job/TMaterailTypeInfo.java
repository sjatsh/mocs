package smtcl.mocs.pojos.job;

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

import smtcl.mocs.pojos.storage.TMaterialStorage;
import smtcl.mocs.pojos.storage.TTransaction;

/**
 * 115-����
 */
@Entity
@Table(name = "t_materail_type_info")
public class TMaterailTypeInfo implements java.io.Serializable {

	// Fields

	private Long id;//����ID
	private String no;//���ϱ��
	private String name;//��������
	private String norm;//���Ϲ��
	private String unit;//���ϵ�λ
	private Double price;//���ϵ���
	private String typeno;//�ͺ�/�ƺ�
	private String memo;//��ע
	private Integer PId;//�������id
	private Integer status;//״̬
	private Double density;//�ܶ�
	private String nodeId;//�ڵ�ID----------------(A3)
	private String className;//��KC���������ͣ�ë�������Ʒ...�����ñ�ʶ��
	private Integer assistUnitId;//��KC��������λ�������������ŷָ���
	private String unitConversion;//��KC����λת������
	private Integer versionId;//��KC����ǰ�汾ID
	private Integer isBom;//��KC���Ƿ�����BOM
	private Date createTime;//����ʱ��    
	private Set<TTransaction> TTransactions = new HashSet<TTransaction>(0);
	private Set<TMaterialStorage> TMaterialStorages = new HashSet<TMaterialStorage>(
			0);

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
	
	/** full constructor */
	public TMaterailTypeInfo(String no,
			String name, String norm, String unit, Double price, String typeno,
			String memo, Integer status, Double density, String nodeId,
			String className, Integer assistUnitId, String unitConversion,
			Integer versionId, Integer isBom,
			Set<TProcessmaterialInfo> RProcessmaterialInfos,
			Set<TTransaction> TTransactions,
			Set<TMaterialStorage> TMaterialStorages) {
//		this.TMaterialClass = TMaterialClass;
		this.no = no;
		this.name = name;
		this.norm = norm;
		this.unit = unit;
		this.price = price;
		this.typeno = typeno;
		this.memo = memo;
		this.status = status;
		this.density = density;
		this.nodeId = nodeId;
		this.className = className;
		this.assistUnitId = assistUnitId;
		this.unitConversion = unitConversion;
		this.versionId = versionId;
		this.isBom = isBom;
		this.TProcessmaterialInfos = RProcessmaterialInfos;
		this.TTransactions = TTransactions;
		this.TMaterialStorages = TMaterialStorages;
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

		@Column(name = "p_id")
		public Integer getPId() {
			return this.PId;
		}

		public void setPId(Integer PId) {
			this.PId = PId;
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

		@Column(name = "nodeID", length = 50)
		public String getNodeId() {
			return this.nodeId;
		}

		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}

		@Column(name = "className", length = 1)
		public String getClassName() {
			return this.className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		@Column(name = "assist_unitID")
		public Integer getAssistUnitId() {
			return this.assistUnitId;
		}

		public void setAssistUnitId(Integer assistUnitId) {
			this.assistUnitId = assistUnitId;
		}

		@Column(name = "unit_conversion", length = 1)
		public String getUnitConversion() {
			return this.unitConversion;
		}

		public void setUnitConversion(String unitConversion) {
			this.unitConversion = unitConversion;
		}

		@Column(name = "versionID")
		public Integer getVersionId() {
			return this.versionId;
		}

		public void setVersionId(Integer versionId) {
			this.versionId = versionId;
		}

		@Column(name = "isBOM")
		public Integer getIsBom() {
			return this.isBom;
		}

		public void setIsBom(Integer isBom) {
			this.isBom = isBom;
		}

		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TMaterailTypeInfo")
		public Set<TProcessmaterialInfo> getRProcessmaterialInfos() {
			return this.TProcessmaterialInfos;
		}

		public void setRProcessmaterialInfos(
				Set<TProcessmaterialInfo> RProcessmaterialInfos) {
			this.TProcessmaterialInfos = RProcessmaterialInfos;
		}

		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TMaterailTypeInfo")
		public Set<TTransaction> getTTransactions() {
			return this.TTransactions;
		}

		public void setTTransactions(Set<TTransaction> TTransactions) {
			this.TTransactions = TTransactions;
		}

		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TMaterailTypeInfo")
		public Set<TMaterialStorage> getTMaterialStorages() {
			return this.TMaterialStorages;
		}

		public void setTMaterialStorages(Set<TMaterialStorage> TMaterialStorages) {
			this.TMaterialStorages = TMaterialStorages;
		}
		
		@Column(name = "createTime")
		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		
}