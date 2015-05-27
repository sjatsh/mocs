package smtcl.mocs.pojos.device;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;

/**
 * TNodes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Nodes")
public class TNodes implements java.io.Serializable {

	// Fields

	private String nodeId;
	private TNodes TNodes;
//	private String parentId;
	private String nodeName;
	private String contactPerson;
	private String contactTelephone;
	private String address;
	private String description;
	private String mesNodeId;
	private Long nodeStatus;
	private TNodeType TNodeType;
	private String path;
    private Long seq;

	private Set<TUserCutterUsage> TUserCutterUsages = new HashSet<TUserCutterUsage>(
			0);
	private Set<TUserResource> TUserResources = new HashSet<TUserResource>(0);
	private Set<TEquipmentInfo> TEquipmentInfos = new HashSet<TEquipmentInfo>(0);
	private Set<TUserProdctionPlan> TUserProdctionPlans = new HashSet<TUserProdctionPlan>(
			0);
	private Set<TUserFixtureUsage> TUserFixtureUsages = new HashSet<TUserFixtureUsage>(
			0);
	private Set<TUserCutter> TUserCutters = new HashSet<TUserCutter>(0);
	private Set<TNodeCostResource> TNodeCostResources = new HashSet<TNodeCostResource>(
			0);
	private Set<TUserFixture> TUserFixtures = new HashSet<TUserFixture>(0);
	private Set<TNodeProductionProfiles> TNodeProductionProfileses = new HashSet<TNodeProductionProfiles>(
			0);
	
	
	private Set<TNodes> TNodeses = new HashSet<TNodes>(0);

	// Constructors

	/** default constructor */
	public TNodes() {
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "P_NODEID")
	public TNodes getTNodes() {
		return this.TNodes;
	}

	public void setTNodes(TNodes TNodes) {
		this.TNodes = TNodes;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="nodeID")
	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TNodes")
	@OrderBy(clause = "nodeId desc")  
	public Set<TNodes> getTNodeses() {
		return this.TNodeses;
	}

	public void setTNodeses(Set<TNodes> TNodeses) {
		this.TNodeses = TNodeses;
	}

	@Column(name = "nodeName", length = 50)
	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "contactPerson", length = 10)
	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name = "contactTelephone", length = 20)
	public String getContactTelephone() {
		return this.contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	@Column(name = "address", length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "mes_NodeID", length = 100)
	public String getMesNodeId() {
		return this.mesNodeId;
	}

	public void setMesNodeId(String mesNodeId) {
		this.mesNodeId = mesNodeId;
	}

	@Column(name = "nodeStatus", precision = 22, scale = 0)
	public Long getNodeStatus() {
		return this.nodeStatus;
	}

	@Column(name = "seq", precision = 22, scale = 0)
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}
	@Column(name = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setNodeStatus(Long nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NODECLASS")
	public TNodeType getTNodeType() {
		return this.TNodeType;
	}

	public void setTNodeType(TNodeType TNodeType) {
		this.TNodeType = TNodeType;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TUserCutterUsage> getTUserCutterUsages() {
		return this.TUserCutterUsages;
	}

	public void setTUserCutterUsages(Set<TUserCutterUsage> TUserCutterUsages) {
		this.TUserCutterUsages = TUserCutterUsages;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TUserResource> getTUserResources() {
		return this.TUserResources;
	}

	public void setTUserResources(Set<TUserResource> TUserResources) {
		this.TUserResources = TUserResources;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TEquipmentInfo> getTEquipmentInfos() {
		return this.TEquipmentInfos;
	}

	public void setTEquipmentInfos(Set<TEquipmentInfo> TEquipmentInfos) {
		this.TEquipmentInfos = TEquipmentInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TUserProdctionPlan> getTUserProdctionPlans() {
		return this.TUserProdctionPlans;
	}

	public void setTUserProdctionPlans(
			Set<TUserProdctionPlan> TUserProdctionPlans) {
		this.TUserProdctionPlans = TUserProdctionPlans;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TUserFixtureUsage> getTUserFixtureUsages() {
		return this.TUserFixtureUsages;
	}

	public void setTUserFixtureUsages(Set<TUserFixtureUsage> TUserFixtureUsages) {
		this.TUserFixtureUsages = TUserFixtureUsages;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TUserCutter> getTUserCutters() {
		return this.TUserCutters;
	}

	public void setTUserCutters(Set<TUserCutter> TUserCutters) {
		this.TUserCutters = TUserCutters;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TNodeCostResource> getTNodeCostResources() {
		return this.TNodeCostResources;
	}

	public void setTNodeCostResources(Set<TNodeCostResource> TNodeCostResources) {
		this.TNodeCostResources = TNodeCostResources;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TUserFixture> getTUserFixtures() {
		return this.TUserFixtures;
	}

	public void setTUserFixtures(Set<TUserFixture> TUserFixtures) {
		this.TUserFixtures = TUserFixtures;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNodes")
	public Set<TNodeProductionProfiles> getTNodeProductionProfileses() {
		return this.TNodeProductionProfileses;
	}

	public void setTNodeProductionProfileses(
			Set<TNodeProductionProfiles> TNodeProductionProfileses) {
		this.TNodeProductionProfileses = TNodeProductionProfileses;
	}

}