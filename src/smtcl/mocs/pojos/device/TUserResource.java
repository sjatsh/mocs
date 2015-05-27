package smtcl.mocs.pojos.device;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TUserResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserResource")
public class TUserResource implements java.io.Serializable {

	// Fields

	private Long userResourceId;//��ԴID
	private TNodes TNodes;
	private String uresoType;//��Դ����
	private String uresoNo;//���ϱ��
	private String uresoNorms;//���
	private String uresoProvider;//��Ӧ����
	private String uresoSerialNo;//�������ϱ��
	private String uresoUnit;//��λ
	private Double uresoStock;//�����
	private Date uresoUpdatetime;//����ʱ��

	// Constructors

	/** default constructor */
	public TUserResource() {
	}

	/** minimal constructor */
	public TUserResource(Long userResourceId, TNodes TNodes,
			String uresoNo) {
		this.userResourceId = userResourceId;
		this.TNodes = TNodes;
		this.uresoNo = uresoNo;
	}

	/** full constructor */
	public TUserResource(Long userResourceId, TNodes TNodes,
			String uresoType, String uresoNo, String uresoNorms,
			String uresoProvider, String uresoSerialNo, String uresoUnit,
			Double uresoStock, Date uresoUpdatetime) {
		this.userResourceId = userResourceId;
		this.TNodes = TNodes;
		this.uresoType = uresoType;
		this.uresoNo = uresoNo;
		this.uresoNorms = uresoNorms;
		this.uresoProvider = uresoProvider;
		this.uresoSerialNo = uresoSerialNo;
		this.uresoUnit = uresoUnit;
		this.uresoStock = uresoStock;
		this.uresoUpdatetime = uresoUpdatetime;
	}

	@Id
	@Column(name = "userResourceID", nullable = false, precision = 22, scale = 0)
	public Long getUserResourceId() {
		return this.userResourceId;
	}

	public void setUserResourceId(Long userResourceId) {
		this.userResourceId = userResourceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeID", nullable = false)
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}

	@Column(name = "uReso_Type", length = 20)
	public String getUresoType() {
		return this.uresoType;
	}

	public void setUresoType(String uresoType) {
		this.uresoType = uresoType;
	}

	@Column(name = "uReso_No", nullable = false, length = 50)
	public String getUresoNo() {
		return this.uresoNo;
	}

	public void setUresoNo(String uresoNo) {
		this.uresoNo = uresoNo;
	}

	@Column(name = "uReso_Norms", length = 20)
	public String getUresoNorms() {
		return this.uresoNorms;
	}

	public void setUresoNorms(String uresoNorms) {
		this.uresoNorms = uresoNorms;
	}

	@Column(name = "uReso_Provider", length = 50)
	public String getUresoProvider() {
		return this.uresoProvider;
	}

	public void setUresoProvider(String uresoProvider) {
		this.uresoProvider = uresoProvider;
	}

	@Column(name = "uReso_SerialNo", length = 50)
	public String getUresoSerialNo() {
		return this.uresoSerialNo;
	}

	public void setUresoSerialNo(String uresoSerialNo) {
		this.uresoSerialNo = uresoSerialNo;
	}

	@Column(name = "uReso_Unit", length = 10)
	public String getUresoUnit() {
		return this.uresoUnit;
	}

	public void setUresoUnit(String uresoUnit) {
		this.uresoUnit = uresoUnit;
	}

	@Column(name = "uReso_Stock", precision = 10)
	public Double getUresoStock() {
		return this.uresoStock;
	}

	public void setUresoStock(Double uresoStock) {
		this.uresoStock = uresoStock;
	}

	@Column(name = "uReso_Updatetime", length = 11)
	public Date getUresoUpdatetime() {
		return this.uresoUpdatetime;
	}

	public void setUresoUpdatetime(Date uresoUpdatetime) {
		this.uresoUpdatetime = uresoUpdatetime;
	}

}