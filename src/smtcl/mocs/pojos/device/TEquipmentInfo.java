package smtcl.mocs.pojos.device;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * TEquipmentInfo entity. 设备附加信息
 *  @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_EquipmentAddInfo")
public class TEquipmentInfo implements java.io.Serializable {

	// Fields

	private Long equId;        //ID               
	private TNodes TNodes;  //节点ID
	private Long equTypeId;  //设备类型ID
	private String equName;  //设备名称
	private String workstationname;//工位名称
	private String manufacturetype;//制造商型号
	private String manufacturer;//制造商
	private String equDesc;//设备描述
	private Long symgEquId;//沈机机床ID
	private String equSerialNo;//设备序列号
	private String path;      //图片路径
	private Double xAxis;//x坐标 x-axis
	private Double yAxis;//y坐标 y-axis
	private String ipAddress; //IP地址
	private String ncType;  //数控系统
	
	private String norm;     //型号/牌号
	private String outfacNo; //出厂编号
	private Date checktime;  //进/验日期
	private Integer status;//机床自动模式、手动模式
	// Constructors

	/** default constructor */
	public TEquipmentInfo() {
	}

	/** minimal constructor */
	public TEquipmentInfo(Long equId, TNodes TNodes, String equSerialNo) {
		this.equId = equId;
		this.TNodes = TNodes;
		this.equSerialNo = equSerialNo;
	}

	/** full constructor */
	public TEquipmentInfo(Long equId, TNodes TNodes, String equName,
			String workstationname, String manufacturetype,
			String manufacturer, String equDesc, Long symgEquId,
			String equSerialNo) {
		this.equId = equId;
		this.TNodes = TNodes;
		this.equName = equName;
		this.workstationname = workstationname;
		this.manufacturetype = manufacturetype;
		this.manufacturer = manufacturer;
		this.equDesc = equDesc;
		this.symgEquId = symgEquId;
		this.equSerialNo = equSerialNo;
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EQU_ID", unique = true, nullable = false)
	public Long getEquId() {
		return this.equId;
	}

	public void setEquId(Long equId) {
		this.equId = equId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeID", nullable = false)
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}
	
	@Column(name = "equ_type", precision = 22, scale = 0)
	public Long getEquTypeId() {
		return this.equTypeId;
	}

	public void setEquTypeId(Long equTypeId) {
		this.equTypeId = equTypeId;
	}

	@Column(name = "equ_name", length = 30)
	public String getEquName() {
		return this.equName;
	}

	public void setEquName(String equName) {
		this.equName = equName;
	}

	@Column(name = "workstationname", length = 30)
	public String getWorkstationname() {
		return this.workstationname;
	}

	public void setWorkstationname(String workstationname) {
		this.workstationname = workstationname;
	}

	@Column(name = "manufacturetype", length = 30)
	public String getManufacturetype() {
		return this.manufacturetype;
	}

	public void setManufacturetype(String manufacturetype) {
		this.manufacturetype = manufacturetype;
	}

	@Column(name = "manufacturer", length = 30)
	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "equ_desc", length = 100)
	public String getEquDesc() {
		return this.equDesc;
	}

	public void setEquDesc(String equDesc) {
		this.equDesc = equDesc;
	}

	@Column(name = "symg_equ_ID", precision = 22, scale = 0)
	public Long getSymgEquId() {
		return this.symgEquId;
	}

	public void setSymgEquId(Long symgEquId) {
		this.symgEquId = symgEquId;
	}

	@Column(name = "equ_SerialNo", nullable = false, length = 30)
	public String getEquSerialNo() {
		return this.equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}
	
	@Column(name = "PATH", length = 200)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name = "X_AXIS")
	public Double getxAxis() {
		return xAxis;
	}

	public void setxAxis(Double xAxis) {
		this.xAxis = xAxis;
	}
	@Column(name = "Y_AXIS")
	public Double getyAxis() {
		return yAxis;
	}

	public void setyAxis(Double yAxis) {
		this.yAxis = yAxis;
	}
	
	@Column(name = "IPAddress")
	public  String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Column(name = "NC_Type")
	public String getNcType() {
		return ncType;
	}

	public void setNcType(String ncType) {
		this.ncType = ncType;
	}	
	
	@Column(name = "norm", length = 200)
	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	@Column(name = "outfac_no", length = 200)
	public String getOutfacNo() {
		return outfacNo;
	}

	public void setOutfacNo(String outfacNo) {
		this.outfacNo = outfacNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checktime", length = 0)
	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}