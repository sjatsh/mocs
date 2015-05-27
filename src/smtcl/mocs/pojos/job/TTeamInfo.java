package smtcl.mocs.pojos.job;

//default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* TTeamInfo entity. @author MyEclipse Persistence Tools
*/
@Entity
@Table(name = "t_team_info")
public class TTeamInfo implements java.io.Serializable {

	// Fields

	private Long teamid;
	private String teamName;
	private String workstation;
	private String category;
	private String teamcode;
	private String nodeid;

	// Constructors

	/** default constructor */
	public TTeamInfo() {
	}

	/** minimal constructor */
	public TTeamInfo(Long teamid) {
		this.teamid = teamid;
	}

	/** full constructor */
	public TTeamInfo(Long teamid, String teamName, String workstation,
			String category, String teamcode, String nodeid) {
		this.teamid = teamid;
		this.teamName = teamName;
		this.workstation = workstation;
		this.category = category;
		this.teamcode = teamcode;
		this.nodeid = nodeid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teamid", unique = true, nullable = false)
	public Long getTeamid() {
		return this.teamid;
	}

	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}

	@Column(name = "team_name", length = 20)
	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Column(name = "workstation", length = 20)
	public String getWorkstation() {
		return this.workstation;
	}

	public void setWorkstation(String workstation) {
		this.workstation = workstation;
	}

	@Column(name = "category", length = 20)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "teamcode", length = 50)
	public String getTeamcode() {
		return this.teamcode;
	}

	public void setTeamcode(String teamcode) {
		this.teamcode = teamcode;
	}

	@Column(name = "nodeid", length = 50)
	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

}