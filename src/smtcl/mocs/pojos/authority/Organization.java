package smtcl.mocs.pojos.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Organization generated by MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_NODES")
public class Organization implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Expose
	@SerializedName(value="nodeId")
	@Column(name="NODEID")
	private String orgId;
	
	@Expose
	@Column(name="NODENAME")
	private String name;
	
	@Column(name="P_NODEID")
	@Expose
	private String parentId;
	
	@Expose
	@Column(name="SEQ")
	private Integer seq = 1000;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
	@Fetch (FetchMode.JOIN)
	@JoinColumn(name="NODECLASS")
	@Expose
	@SerializedName(value="nodeType")
	private TypeNode orgType;
	
	@Expose
	@Column(name="NODESTATUS")
	private Integer state = 1;
	
	@Transient
	@Expose
	@SerializedName(value="isParent")
	private boolean parent = true;
	
	@Expose
	@Column(name="CONTACTPERSON")
	private String contactPerson;
	
	@Expose
	@Column(name="CONTACTTELEPHONE")
	private String contactTelephone;
	
	@Expose
	@Column(name="ADDRESS")
	private String address;
	
	@Expose
	@Column(name="DESCRIPTION")
	private String description;
	
	@Expose
	@Column(name="MES_NODEID")
	private String mesNodeid;
	
	@Transient
	private String mesPwd;
	
	@Expose
	@Column(name="CREATE_USER")
	private String createUser;
	
	@Expose
	@Column(name="FLAG")
	private Integer show = 1;
	
	@Expose
	@Transient
	private Device device;
	
	/**
	 * @return the show
	 */
	public Integer getShow() {
		return show;
	}

	/**
	 * @param show the show to set
	 */
	public void setShow(Integer show) {
		this.show = show;
	}

	@Transient
	@Expose
	private String longName;
	
	
	@Expose
	@Transient
	private boolean manage = true;
	
	// Constructors


	/** default constructor */
	public Organization() {
	}

	// Property accessors

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @return the manage
	 */
	public boolean isManage() {
		return manage;
	}

	/**
	 * @param manage the manage to set
	 */
	public void setManage(boolean manage) {
		this.manage = manage;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the orgType
	 */
	public TypeNode getOrgType() {
		return orgType;
	}

	/**
	 * @param orgType the orgType to set
	 */
	public void setOrgType(TypeNode orgType) {
		this.orgType = orgType;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * @return the parent
	 */
	public boolean isParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(boolean parent) {
		this.parent = parent;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the contactTelephone
	 */
	public String getContactTelephone() {
		return contactTelephone;
	}

	/**
	 * @param contactTelephone the contactTelephone to set
	 */
	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the mesNodeid
	 */
	public String getMesNodeid() {
		return mesNodeid;
	}

	/**
	 * @param mesNodeid the mesNodeid to set
	 */
	public void setMesNodeid(String mesNodeid) {
		this.mesNodeid = mesNodeid;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the mesPwd
	 */
	public String getMesPwd() {
		return mesPwd;
	}

	/**
	 * @param mesPwd the mesPwd to set
	 */
	public void setMesPwd(String mesPwd) {
		this.mesPwd = mesPwd;
	}

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}
}