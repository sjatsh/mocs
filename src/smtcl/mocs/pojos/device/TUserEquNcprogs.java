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
 * TUserEquNcprogs entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UserEquNCProgs")
public class TUserEquNcprogs implements java.io.Serializable {

	// Fields

	private Long id;//ID
	private String equSerialNo;//设备序列号
	private Date updateTime;//更新时间
	private String programmfile;//文件名
	private String contents;//程序内容
	private String thumbnail;//程序缩略图名称
	private String partId;//对应零件ID
	private Long processTimeTheory;//理论加工时间
	private Long cuttingTimeFact;//实际切削时间
	private Long processTimeFact;//实际加工时间
	private Long toolChangeTimeFact;//实际换刀时间
	private String tool;//刀具

	// Constructors

	/** default constructor */
	public TUserEquNcprogs() {
	}

	/** minimal constructor */
	public TUserEquNcprogs(Long id, String equSerialNo) {
		this.id = id;
		this.equSerialNo = equSerialNo;
	}

	/** full constructor */
	public TUserEquNcprogs(Long id, String equSerialNo, Date updateTime,
			String programmfile, String contents, String thumbnail,
			String partId, Long processTimeTheory,
			Long cuttingTimeFact, Long processTimeFact,
			Long toolChangeTimeFact, String tool) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.updateTime = updateTime;
		this.programmfile = programmfile;
		this.contents = contents;
		this.thumbnail = thumbnail;
		this.partId = partId;
		this.processTimeTheory = processTimeTheory;
		this.cuttingTimeFact = cuttingTimeFact;
		this.processTimeFact = processTimeFact;
		this.toolChangeTimeFact = toolChangeTimeFact;
		this.tool = tool;
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
	@Column(name = "updateTime", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "programmfile", length = 200)
	public String getProgrammfile() {
		return this.programmfile;
	}

	public void setProgrammfile(String programmfile) {
		this.programmfile = programmfile;
	}

	@Column(name = "contents")
	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Column(name = "thumbnail", length = 50)
	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Column(name = "partID", length = 20)
	public String getPartId() {
		return this.partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	@Column(name = "processTimeTheory", precision = 22, scale = 0)
	public Long getProcessTimeTheory() {
		return this.processTimeTheory;
	}

	public void setProcessTimeTheory(Long processTimeTheory) {
		this.processTimeTheory = processTimeTheory;
	}

	@Column(name = "cuttingTimeFact", precision = 22, scale = 0)
	public Long getCuttingTimeFact() {
		return this.cuttingTimeFact;
	}

	public void setCuttingTimeFact(Long cuttingTimeFact) {
		this.cuttingTimeFact = cuttingTimeFact;
	}

	@Column(name = "processTimeFact", precision = 22, scale = 0)
	public Long getProcessTimeFact() {
		return this.processTimeFact;
	}

	public void setProcessTimeFact(Long processTimeFact) {
		this.processTimeFact = processTimeFact;
	}

	@Column(name = "toolChangeTimeFact", precision = 22, scale = 0)
	public Long getToolChangeTimeFact() {
		return this.toolChangeTimeFact;
	}

	public void setToolChangeTimeFact(Long toolChangeTimeFact) {
		this.toolChangeTimeFact = toolChangeTimeFact;
	}

	@Column(name = "tool", length = 30)
	public String getTool() {
		return this.tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

}