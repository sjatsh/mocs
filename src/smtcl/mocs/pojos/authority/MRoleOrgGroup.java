package smtcl.mocs.pojos.authority;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author gaokun
 * @create Nov 20, 2012 2:17:23 PM
 */
@Entity
@Table(name = "M_ROLE_ORGGROUP")
public class MRoleOrgGroup implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@GeneratedValue(generator = "system-identity")
    @GenericGenerator(name = "system-identity", strategy = "identity")
    @Id
    @Column(name="ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "ROLEID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;


    @JoinColumn(name = "C_ORG_GROUP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrgGroup createOrgGroup;

    @Column(name="FLAG")
    @Basic
    private String flag;

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the createOrgGroup
	 */
	public OrgGroup getCreateOrgGroup() {
		return createOrgGroup;
	}

	/**
	 * @param createOrgGroup the createOrgGroup to set
	 */
	public void setCreateOrgGroup(OrgGroup createOrgGroup) {
		this.createOrgGroup = createOrgGroup;
	}

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
