package smtcl.mocs.pojos.job;
// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TWorkstationInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_workstation_info")
public class TWorkstationInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long workstationId;
	private Set<TProcessInfo> TProcessInfos = new HashSet<TProcessInfo>(0);

	// Constructors

	/** default constructor */
	public TWorkstationInfo() {
	}

	/** minimal constructor */
	public TWorkstationInfo(Long workstationId) {
		this.workstationId = workstationId;
	}

	/** full constructor */
	public TWorkstationInfo(Long workstationId,
			Set<TProcessInfo> TProcessInfos) {
		this.workstationId = workstationId;
		this.TProcessInfos = TProcessInfos;
	}

	// Property accessors
	@Id
	@Column(name = "workstationID", unique = true, nullable = false)
	public Long getWorkstationId() {
		return this.workstationId;
	}

	public void setWorkstationId(Long workstationId) {
		this.workstationId = workstationId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TWorkstationInfo")
	public Set<TProcessInfo> getTProcessInfos() {
		return this.TProcessInfos;
	}

	public void setTProcessInfos(Set<TProcessInfo> TProcessInfos) {
		this.TProcessInfos = TProcessInfos;
	}

}