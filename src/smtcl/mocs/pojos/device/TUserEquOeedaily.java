package smtcl.mocs.pojos.device;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TUserEquOeedaily entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserEquOEEdaily")
public class TUserEquOeedaily implements java.io.Serializable {

	// Fields

	private Long id;//ID
	private String equSerialNo;//�豸���к�
	private Date caclDate;//����
	private Long worktimeFact;//ʵ�ʿ���ʱ��
	private Long worktimePlan;//�ƻ�����ʱ��
	private Long acturalOutputTheorytime;//ʵ�ʲ������ۺ�ʱ
	private Double processPartsCount;//�ӹ�������
	private Double qualifiedPartCount;//�ϸ񹤼���
	private Double dayOeevalue;//����OEE
	private Double dayTeepvalue;//����TEEP

	// Constructors

	/** default constructor */
	public TUserEquOeedaily() {
	}

	/** minimal constructor */
	public TUserEquOeedaily(Long id, String equSerialNo, Date caclDate) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.caclDate = caclDate;
	}

	/** full constructor */
	public TUserEquOeedaily(Long id, String equSerialNo, Date caclDate,
			Long worktimeFact, Long worktimePlan,
			Long acturalOutputTheorytime, Double processPartsCount,
			Double qualifiedPartCount, Double dayOeevalue, Double dayTeepvalue) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.caclDate = caclDate;
		this.worktimeFact = worktimeFact;
		this.worktimePlan = worktimePlan;
		this.acturalOutputTheorytime = acturalOutputTheorytime;
		this.processPartsCount = processPartsCount;
		this.qualifiedPartCount = qualifiedPartCount;
		this.dayOeevalue = dayOeevalue;
		this.dayTeepvalue = dayTeepvalue;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "equ_serialNo", nullable = false, length = 30)
	public String getEquSerialNo() {
		return this.equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cacl_date", nullable = false, length = 7)
	public Date getCaclDate() {
		return this.caclDate;
	}

	public void setCaclDate(Date caclDate) {
		this.caclDate = caclDate;
	}

	@Column(name = "worktimeFact", precision = 22, scale = 0)
	public Long getWorktimeFact() {
		return this.worktimeFact;
	}

	public void setWorktimeFact(Long worktimeFact) {
		this.worktimeFact = worktimeFact;
	}

	@Column(name = "worktimePlan", precision = 22, scale = 0)
	public Long getWorktimePlan() {
		return this.worktimePlan;
	}

	public void setWorktimePlan(Long worktimePlan) {
		this.worktimePlan = worktimePlan;
	}

	@Column(name = "acturalOutputTheorytime", precision = 22, scale = 0)
	public Long getActuralOutputTheorytime() {
		return this.acturalOutputTheorytime;
	}

	public void setActuralOutputTheorytime(Long acturalOutputTheorytime) {
		this.acturalOutputTheorytime = acturalOutputTheorytime;
	}

	@Column(name = "processPartsCount", precision = 10, scale = 1)
	public Double getProcessPartsCount() {
		return this.processPartsCount;
	}

	public void setProcessPartsCount(Double processPartsCount) {
		this.processPartsCount = processPartsCount;
	}

	@Column(name = "qualifiedPartCount", precision = 10, scale = 1)
	public Double getQualifiedPartCount() {
		return this.qualifiedPartCount;
	}

	public void setQualifiedPartCount(Double qualifiedPartCount) {
		this.qualifiedPartCount = qualifiedPartCount;
	}

	@Column(name = "dayOEEvalue", precision = 10, scale = 3)
	public Double getDayOeevalue() {
		return this.dayOeevalue;
	}

	public void setDayOeevalue(Double dayOeevalue) {
		this.dayOeevalue = dayOeevalue;
	}

	@Column(name = "dayTEEPvalue", precision = 10, scale = 3)
	public Double getDayTeepvalue() {
		return this.dayTeepvalue;
	}

	public void setDayTeepvalue(Double dayTeepvalue) {
		this.dayTeepvalue = dayTeepvalue;
	}

}