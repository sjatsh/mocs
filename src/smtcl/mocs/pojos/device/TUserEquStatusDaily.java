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
 * TUserEquStatusDaily entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserEquStatusDaily")
public class TUserEquStatusDaily implements java.io.Serializable {

	// Fields

	private Long id;//ID
	private String equSerialNo;//�豸���к�
	private Date updatedate;//����
	private Long runningTime;//����ʱ��
	private Long stopTime;//�ػ�ʱ��
	private Long processTime;//�ӹ�ʱ��
	private Long prepareTime;//׼��ʱ��
	private Long idleTime;//���д���ʱ��
	private Long breakdownTime;//����ʱ��
	private Long cuttingTime;//����ʱ��
	private Long dryRunningTime;//������ʱ��
	private Long toolChangeTime;//����ʱ��
	private Long manualRunningTime;//�ֶ�����ʱ��
	private Long materialTime;//������ʱ��
	private Long otherTime;//����ʱ��
	private Double totalProcessPartsCount;//�ܹ�������
	private Double anualProcessPartsCount;//�깤������
	private Double monthProcessPartscount;//�¹�������
	private Double dayProcessPartsCount;//�չ�������
	private Long workTimePlan;//�ƻ�����ʱ��

	// Constructors

	/** default constructor */
	public TUserEquStatusDaily() {
	}

	/** minimal constructor */
	public TUserEquStatusDaily(Long id, String equSerialNo) {
		this.id = id;
		this.equSerialNo = equSerialNo;
	}

	/** full constructor */
	public TUserEquStatusDaily(Long id, String equSerialNo, Date updatedate,
			Long runningTime, Long stopTime,
			Long processTime, Long prepareTime,
			Long idleTime, Long breakdownTime,
			Long cuttingTime, Long dryRunningTime,
			Long toolChangeTime, Long manualRunningTime,
			Long materialTime, Long otherTime,
			Double totalProcessPartsCount, Double anualProcessPartsCount,
			Double monthProcessPartscount, Double dayProcessPartsCount,
			Long workTimePlan) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.updatedate = updatedate;
		this.runningTime = runningTime;
		this.stopTime = stopTime;
		this.processTime = processTime;
		this.prepareTime = prepareTime;
		this.idleTime = idleTime;
		this.breakdownTime = breakdownTime;
		this.cuttingTime = cuttingTime;
		this.dryRunningTime = dryRunningTime;
		this.toolChangeTime = toolChangeTime;
		this.manualRunningTime = manualRunningTime;
		this.materialTime = materialTime;
		this.otherTime = otherTime;
		this.totalProcessPartsCount = totalProcessPartsCount;
		this.anualProcessPartsCount = anualProcessPartsCount;
		this.monthProcessPartscount = monthProcessPartscount;
		this.dayProcessPartsCount = dayProcessPartsCount;
		this.workTimePlan = workTimePlan;
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
	@Column(name = "EQU_SERIALNO", nullable = false, length = 30)
	public String getEquSerialNo() {
		return equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

//	@Column(name = "EQU_SERIALNO", nullable = false, length = 30)
//	public String getEquSerialno() {
//		return this.equSerialNo;
//	}
//
//	public void setEquSerialno(String equSerialNo) {
//		this.equSerialNo = equSerialNo;
//	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE", length = 7)
	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@Column(name = "runningTime", precision = 22, scale = 0)
	public Long getRunningTime() {
		return this.runningTime;
	}

	public void setRunningTime(Long runningTime) {
		this.runningTime = runningTime;
	}

	@Column(name = "stopTime", precision = 22, scale = 0)
	public Long getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Long stopTime) {
		this.stopTime = stopTime;
	}

	@Column(name = "processTime", precision = 22, scale = 0)
	public Long getProcessTime() {
		return this.processTime;
	}

	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}

	@Column(name = "prepareTime", precision = 22, scale = 0)
	public Long getPrepareTime() {
		return this.prepareTime;
	}

	public void setPrepareTime(Long prepareTime) {
		this.prepareTime = prepareTime;
	}

	@Column(name = "idleTime", precision = 22, scale = 0)
	public Long getIdleTime() {
		return this.idleTime;
	}

	public void setIdleTime(Long idleTime) {
		this.idleTime = idleTime;
	}

	@Column(name = "breakdownTime", precision = 22, scale = 0)
	public Long getBreakdownTime() {
		return this.breakdownTime;
	}

	public void setBreakdownTime(Long breakdownTime) {
		this.breakdownTime = breakdownTime;
	}

	@Column(name = "cuttingTime", precision = 22, scale = 0)
	public Long getCuttingTime() {
		return this.cuttingTime;
	}

	public void setCuttingTime(Long cuttingTime) {
		this.cuttingTime = cuttingTime;
	}

	@Column(name = "dryRunningTime", precision = 22, scale = 0)
	public Long getDryRunningTime() {
		return this.dryRunningTime;
	}

	public void setDryRunningTime(Long dryRunningTime) {
		this.dryRunningTime = dryRunningTime;
	}

	@Column(name = "toolChangeTime", precision = 22, scale = 0)
	public Long getToolChangeTime() {
		return this.toolChangeTime;
	}

	public void setToolChangeTime(Long toolChangeTime) {
		this.toolChangeTime = toolChangeTime;
	}

	@Column(name = "manualRunningTime", precision = 22, scale = 0)
	public Long getManualRunningTime() {
		return this.manualRunningTime;
	}

	public void setManualRunningTime(Long manualRunningTime) {
		this.manualRunningTime = manualRunningTime;
	}

	@Column(name = "materialTime", precision = 22, scale = 0)
	public Long getMaterialTime() {
		return this.materialTime;
	}

	public void setMaterialTime(Long materialTime) {
		this.materialTime = materialTime;
	}

	@Column(name = "otherTime", precision = 22, scale = 0)
	public Long getOtherTime() {
		return this.otherTime;
	}

	public void setOtherTime(Long otherTime) {
		this.otherTime = otherTime;
	}

	@Column(name = "totalProcessPartsCount", precision = 15, scale = 1)
	public Double getTotalProcessPartsCount() {
		return this.totalProcessPartsCount;
	}

	public void setTotalProcessPartsCount(Double totalProcessPartsCount) {
		this.totalProcessPartsCount = totalProcessPartsCount;
	}

	@Column(name = "anualProcessPartsCount", precision = 10, scale = 1)
	public Double getAnualProcessPartsCount() {
		return this.anualProcessPartsCount;
	}

	public void setAnualProcessPartsCount(Double anualProcessPartsCount) {
		this.anualProcessPartsCount = anualProcessPartsCount;
	}

	@Column(name = "monthProcessPartscount", precision = 10, scale = 1)
	public Double getMonthProcessPartscount() {
		return this.monthProcessPartscount;
	}

	public void setMonthProcessPartscount(Double monthProcessPartscount) {
		this.monthProcessPartscount = monthProcessPartscount;
	}

	@Column(name = "dayProcessPartsCount", precision = 8, scale = 1)
	public Double getDayProcessPartsCount() {
		return this.dayProcessPartsCount;
	}

	public void setDayProcessPartsCount(Double dayProcessPartsCount) {
		this.dayProcessPartsCount = dayProcessPartsCount;
	}

	@Column(name = "workTimePlan", precision = 22, scale = 0)
	public Long getWorkTimePlan() {
		return this.workTimePlan;
	}

	public void setWorkTimePlan(Long workTimePlan) {
		this.workTimePlan = workTimePlan;
	}

}