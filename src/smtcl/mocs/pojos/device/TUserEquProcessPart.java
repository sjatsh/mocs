package smtcl.mocs.pojos.device;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TUserEquProcessPart entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserEquProcessPart")
public class TUserEquProcessPart implements java.io.Serializable {

	// Fields
	private Long id;//
	private String equSerialNo;//�豸���к�
	private Date updateTime;//����ʱ��
	private String partId;//���ID
	private String drawingNo;//��Ӧͼֽ���
	private String processName;//��Ӧ����
	private Long workTime;//�ӹ�ʱ��
	private String tools;//����
	private String material;//����

	// Constructors

	/** default constructor */
	public TUserEquProcessPart() {
	}

	/** minimal constructor */
	public TUserEquProcessPart(Long id, String equSerialNo) {
		this.id = id;
		this.equSerialNo = equSerialNo;
	}

	/** full constructor */
	public TUserEquProcessPart(Long id, String equSerialNo, Date updateTime,
			String partId, String drawingNo, String processName,
			Long workTime, String tools, String material){
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.updateTime = updateTime;
		this.partId = partId;
		this.drawingNo = drawingNo;
		this.processName = processName;
		this.workTime = workTime;
		this.tools = tools;
		this.material = material;
	}

	@Id
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
	@Column(name = "updateTime", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "partID", length = 30)
	public String getPartId() {
		return this.partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	@Column(name = "drawingNo", length = 50)
	public String getDrawingNo() {
		return this.drawingNo;
	}

	public void setDrawingNo(String drawingNo) {
		this.drawingNo = drawingNo;
	}

	@Column(name = "processName", length = 50)
	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	@Column(name = "workTime", precision = 22, scale = 0)
	public Long getWorkTime() {
		return this.workTime;
	}

	public void setWorkTime(Long workTime) {
		this.workTime = workTime;
	}

	@Column(name = "tools", length = 30)
	public String getTools() {
		return this.tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	@Column(name = "material", length = 30)
	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
}