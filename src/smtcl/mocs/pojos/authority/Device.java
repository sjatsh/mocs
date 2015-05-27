package smtcl.mocs.pojos.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

/**
 * 设备信息;
 * @author gaokun
 * @create Jan 28, 2013 11:33:08 AM
 */
@javax.persistence.Table (name = "T_EQUIPMENTADDINFO")
@Entity
public class Device implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "EQU_ID")
    @Expose
	private Integer id;
	
	@Column(name="NODEID")
	@Expose
	private String nodeId;
	
	@Column(name="EQU_NAME")
	@Expose
	private String equName;
	
	@Column(name="EQU_TYPE")
	@Expose
	private String equType;
	
	@Column(name="WORKSTATIONNAME")
	@Expose
	private String workStationName;
	
	@Column(name="MANUFACTURETYPE")
	@Expose
	private String manuFactureType;
	
	@Column(name="MANUFACTURER")
	@Expose
	private String manuFacturer;
	
	@Column(name="EQU_DESC")
	@Expose
	private String equDesc;
	
	@Column(name="SYMG_EQU_ID")
	@Expose
	private Integer symgEquId;
	
	/**
	 * 组成 equSerialno + "@symg_swg@" + symgEquId
	 */
	@Transient
	@Expose
	private String composeId;
	
	
	@Column(name="EQU_SERIALNO")
	@Expose
	private String equSerialno;


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}


	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}


	/**
	 * @return the equName
	 */
	public String getEquName() {
		return equName;
	}


	/**
	 * @param equName the equName to set
	 */
	public void setEquName(String equName) {
		this.equName = equName;
	}


	/**
	 * @return the equType
	 */
	public String getEquType() {
		return equType;
	}


	/**
	 * @param equType the equType to set
	 */
	public void setEquType(String equType) {
		this.equType = equType;
	}


	/**
	 * @return the workStationName
	 */
	public String getWorkStationName() {
		return workStationName;
	}


	/**
	 * @param workStationName the workStationName to set
	 */
	public void setWorkStationName(String workStationName) {
		this.workStationName = workStationName;
	}


	/**
	 * @return the manuFactureType
	 */
	public String getManuFactureType() {
		return manuFactureType;
	}


	/**
	 * @param manuFactureType the manuFactureType to set
	 */
	public void setManuFactureType(String manuFactureType) {
		this.manuFactureType = manuFactureType;
	}


	/**
	 * @return the manuFacturer
	 */
	public String getManuFacturer() {
		return manuFacturer;
	}


	/**
	 * @param manuFacturer the manuFacturer to set
	 */
	public void setManuFacturer(String manuFacturer) {
		this.manuFacturer = manuFacturer;
	}


	/**
	 * @return the equDesc
	 */
	public String getEquDesc() {
		return equDesc;
	}


	/**
	 * @param equDesc the equDesc to set
	 */
	public void setEquDesc(String equDesc) {
		this.equDesc = equDesc;
	}


	/**
	 * @return the symgEquId
	 */
	public Integer getSymgEquId() {
		return symgEquId;
	}


	/**
	 * @param symgEquId the symgEquId to set
	 */
	public void setSymgEquId(Integer symgEquId) {
		this.symgEquId = symgEquId;
	}


	/**
	 * @return the equSerialno
	 */
	public String getEquSerialno() {
		return equSerialno;
	}


	/**
	 * @param equSerialno the equSerialno to set
	 */
	public void setEquSerialno(String equSerialno) {
		this.equSerialno = equSerialno;
	}


	/**
	 * @return the composeId
	 */
	public String getComposeId() {
		return composeId;
	}


	/**
	 * @param composeId the composeId to set
	 */
	public void setComposeId(String composeId) {
		this.composeId = composeId;
	}
	
	
}