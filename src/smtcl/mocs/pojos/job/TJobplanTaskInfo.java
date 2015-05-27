package smtcl.mocs.pojos.job;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




/**
 * TJobplanInfo entity. @author MyEclipse Persistence Tools
 * ��ҵ�ƻ���������
 */
@Entity
@Table(name = "t_jobplan_task_info")
public class TJobplanTaskInfo implements java.io.Serializable {

	private Long id;//��ˮ��
	private Long jobPlanId;//��ҵ�ƻ�id
	private String jobPlanNo;//��ҵ�ƻ����
	private String taskNO;//������
	private Integer taskNum;//�����������
	private String operator;//������
	private Date operatorTime;//����ʱ��
	private Integer reportNum;//�ϱ�����

	
	/** default constructor */
	public TJobplanTaskInfo() {
	}

	/** minimal constructor */
	public TJobplanTaskInfo(Long id) {
		this.id = id;
	}

	public TJobplanTaskInfo(Long id, Long jobPlanId, String jobPlanNo,
			String taskNO, Integer taskNum, String operator, Date operatorTime) {
		super();
		this.id = id;
		this.jobPlanId = jobPlanId;
		this.jobPlanNo = jobPlanNo;
		this.taskNO = taskNO;
		this.taskNum = taskNum;
		this.operator = operator;
		this.operatorTime = operatorTime;
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
	
	@Column(name ="jobPlanId")
	public Long getJobPlanId() {
		return jobPlanId;
	}

	public void setJobPlanId(Long jobPlanId) {
		this.jobPlanId = jobPlanId;
	}
	
	@Column(name ="jobPlanNo")
	public String getJobPlanNo() {
		return jobPlanNo;
	}

	public void setJobPlanNo(String jobPlanNo) {
		this.jobPlanNo = jobPlanNo;
	}
	
	@Column(name ="taskNO")
	public String getTaskNO() {
		return taskNO;
	}

	public void setTaskNO(String taskNO) {
		this.taskNO = taskNO;
	}
				   
	@Column(name ="taskNum")
	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}
	
	@Column(name ="operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(name ="operatorTime")
	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	@Column(name ="reportNum")
	public Integer getReportNum() {
		return reportNum;
	}

	public void setReportNum(Integer reportNum) {
		this.reportNum = reportNum;
	}
	

}