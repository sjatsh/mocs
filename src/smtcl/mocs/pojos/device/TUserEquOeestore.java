package smtcl.mocs.pojos.device;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TUserEquOeestore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserEquOEEstore")
public class TUserEquOeestore implements java.io.Serializable {

	// Fields

	private Long id;//ID
	private String equSerialNo;//
	private Long year;//
	private Long month;//
	private Long weekofYear;//
	private Long totalworkTimeFact;//
	private Long totalWorkTimePlan;//
	private Long totalActOutputTheoryTime;//
	private Double totalProcesspartscount;//
	private Double totalQualifiedPartCount;//
	private Double totalOeevalue;//
	private Double totalTeepvalue;//

	// Constructors

	/** default constructor */
	public TUserEquOeestore() {
	}

	/** minimal constructor */
	public TUserEquOeestore(Long id, String equSerialNo) {
		this.id = id;
		this.equSerialNo = equSerialNo;
	}

	/** full constructor */
	public TUserEquOeestore(Long id,String equSerialNo, Long year,
			Long month, Long weekofYear,
			Long totalworkTimeFact, Long totalWorkTimePlan,
			Long totalActOutputTheoryTime, Double totalProcesspartscount,
			Double totalQualifiedPartCount, Double totalOeevalue,
			Double totalTeepvalue) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.year = year;
		this.month = month;
		this.weekofYear = weekofYear;
		this.totalworkTimeFact = totalworkTimeFact;
		this.totalWorkTimePlan = totalWorkTimePlan;
		this.totalActOutputTheoryTime = totalActOutputTheoryTime;
		this.totalProcesspartscount = totalProcesspartscount;
		this.totalQualifiedPartCount = totalQualifiedPartCount;
		this.totalOeevalue = totalOeevalue;
		this.totalTeepvalue = totalTeepvalue;
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

	@Column(name = "Year", precision = 22, scale = 0)
	public Long getYear() {
		return this.year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	@Column(name = "Month", precision = 22, scale = 0)
	public Long getMonth() {
		return this.month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	@Column(name = "WeekofYear", precision = 22, scale = 0)
	public Long getWeekofYear() {
		return this.weekofYear;
	}

	public void setWeekofYear(Long weekofYear) {
		this.weekofYear = weekofYear;
	}

	@Column(name = "TotalworkTimeFact", precision = 22, scale = 0)
	public Long getTotalworkTimeFact() {
		return this.totalworkTimeFact;
	}

	public void setTotalworkTimeFact(Long totalworkTimeFact) {
		this.totalworkTimeFact = totalworkTimeFact;
	}

	@Column(name = "TotalWorkTimePlan", precision = 22, scale = 0)
	public Long getTotalWorkTimePlan() {
		return this.totalWorkTimePlan;
	}

	public void setTotalWorkTimePlan(Long totalWorkTimePlan) {
		this.totalWorkTimePlan = totalWorkTimePlan;
	}

	@Column(name = "TotalAct_outputTheoryTime", precision = 22, scale = 0)
	public Long getTotalActOutputTheoryTime() {
		return this.totalActOutputTheoryTime;
	}

	public void setTotalActOutputTheoryTime(Long totalActOutputTheoryTime) {
		this.totalActOutputTheoryTime = totalActOutputTheoryTime;
	}

	@Column(name = "TotalProcesspartscount", precision = 10, scale = 1)
	public Double getTotalProcesspartscount() {
		return this.totalProcesspartscount;
	}

	public void setTotalProcesspartscount(Double totalProcesspartscount) {
		this.totalProcesspartscount = totalProcesspartscount;
	}

	@Column(name = "TotalQualifiedPartCount", precision = 10, scale = 1)
	public Double getTotalQualifiedPartCount() {
		return this.totalQualifiedPartCount;
	}

	public void setTotalQualifiedPartCount(Double totalQualifiedPartCount) {
		this.totalQualifiedPartCount = totalQualifiedPartCount;
	}

	@Column(name = "totalOEEvalue", precision = 10, scale = 3)
	public Double getTotalOeevalue() {
		return this.totalOeevalue;
	}

	public void setTotalOeevalue(Double totalOeevalue) {
		this.totalOeevalue = totalOeevalue;
	}

	@Column(name = "totalTEEPvalue", precision = 10, scale = 3)
	public Double getTotalTeepvalue() {
		return this.totalTeepvalue;
	}

	public void setTotalTeepvalue(Double totalTeepvalue) {
		this.totalTeepvalue = totalTeepvalue;
	}

}