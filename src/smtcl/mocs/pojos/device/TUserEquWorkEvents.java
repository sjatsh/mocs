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
 * TUserEquWorkEvents entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserEquWorkEvents")
public class TUserEquWorkEvents implements java.io.Serializable {

	// Fields

	private Long id;//ID
	private String equSerialNo;//�豸���к�
	private String cuttingeventId;//�ӹ��¼�ID
	private Date starttime;//��ʼʱ��
	private Date finishtime;//����ʱ��
	private String cuttingTask;//�ӹ�����
	private String ncprogramm;//���س�����
	private String partNo;//������
	private Long theoryWorktime;//���ۼӹ�ʱ��
	private Long cuttingTime;//����ʱ��
	private Long toolchangeTime;//����ʱ��
	private Long workTime;//�ӹ���ʱ
	private String workResult;//�ӹ����
	private Long theoryCycletime;//��
	private String operatorNo; //��ǰ�����߹���
	private Long flag;//�Ƿ���erp���λ��1���ѵ��룬0��δ����

	// Constructors

	/** default constructor */
	public TUserEquWorkEvents() {
	}

	/** minimal constructor */
	public TUserEquWorkEvents(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TUserEquWorkEvents(Long id, String equSerialNo,
			String cuttingeventId, Date starttime, Date finishtime,
			String cuttingTask, String ncprogramm, String partNo,
			Long theoryWorktime, Long cuttingTime,
			Long toolchangeTime, Long workTime, String workResult,
			Long theoryCycletime) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.cuttingeventId = cuttingeventId;
		this.starttime = starttime;
		this.finishtime = finishtime;
		this.cuttingTask = cuttingTask;
		this.ncprogramm = ncprogramm;
		this.partNo = partNo;
		this.theoryWorktime = theoryWorktime;
		this.cuttingTime = cuttingTime;
		this.toolchangeTime = toolchangeTime;
		this.workTime = workTime;
		this.workResult = workResult;
		this.theoryCycletime = theoryCycletime;
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

	@Column(name = "EQU_SERIALNO", length = 30)
	public String getEquSerialNo() {
		return this.equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	@Column(name = "cuttingeventID")
	public String getCuttingeventId() {
		return this.cuttingeventId;
	}

	public void setCuttingeventId(String cuttingeventId) {
		this.cuttingeventId = cuttingeventId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "starttime", length = 7)
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finishtime", length = 7)
	public Date getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	@Column(name = "cuttingTask", length = 50)
	public String getCuttingTask() {
		return this.cuttingTask;
	}

	public void setCuttingTask(String cuttingTask) {
		this.cuttingTask = cuttingTask;
	}

	@Column(name = "NCprogramm", length = 200)
	public String getNcprogramm() {
		return this.ncprogramm;
	}

	public void setNcprogramm(String ncprogramm) {
		this.ncprogramm = ncprogramm;
	}

	@Column(name = "partNo", length = 30)
	public String getPartNo() {
		return this.partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	@Column(name = "theoryWorktime", precision = 22, scale = 0)
	public Long getTheoryWorktime() {
		return this.theoryWorktime;
	}

	public void setTheoryWorktime(Long theoryWorktime) {
		this.theoryWorktime = theoryWorktime;
	}

	@Column(name = "cuttingTime", precision = 22, scale = 0)
	public Long getCuttingTime() {
		return this.cuttingTime;
	}

	public void setCuttingTime(Long cuttingTime) {
		this.cuttingTime = cuttingTime;
	}

	@Column(name = "toolchangeTime", precision = 22, scale = 0)
	public Long getToolchangeTime() {
		return this.toolchangeTime;
	}

	public void setToolchangeTime(Long toolchangeTime) {
		this.toolchangeTime = toolchangeTime;
	}

	@Column(name = "workTime", precision = 22, scale = 0)
	public Long getWorkTime() {
		return this.workTime;
	}

	public void setWorkTime(Long workTime) {
		this.workTime = workTime;
	}

	@Column(name = "workResult", length = 20)
	public String getWorkResult() {
		return this.workResult;
	}

	public void setWorkResult(String workResult) {
		this.workResult = workResult;
	}

	@Column(name = "theoryCycletime", precision = 22, scale = 0)
	public Long getTheoryCycletime() {
		return this.theoryCycletime;
	}

	public void setTheoryCycletime(Long theoryCycletime) {
		this.theoryCycletime = theoryCycletime;
	}
	
	@Column(name = "operator_no")
	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	@Column(name = "flag", precision = 22, scale = 0)
	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}
}