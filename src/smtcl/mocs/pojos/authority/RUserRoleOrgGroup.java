package smtcl.mocs.pojos.authority;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * 映射数据库中"角色-用户-数据关系表的实体
 *
 * User: gjy
 * Date: 2012-10-17
 */
@javax.persistence.Table (name = "R_USER_ROLE_ORGGROUP")
@Entity
public class RUserRoleOrgGroup implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "USERID")
    @Fetch (FetchMode.JOIN)
	private User user;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "ROLEID")
    @Fetch (FetchMode.JOIN)
	private Role role;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "ORG_GROUP_ID")
    @Fetch (FetchMode.JOIN)
	private OrgGroup orgGroup;

    @Column(name="FLAG")
    @Basic
    private String flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the orgGroup
	 */
	public OrgGroup getOrgGroup() {
		return orgGroup;
	}

	/**
	 * @param orgGroup the orgGroup to set
	 */
	public void setOrgGroup(OrgGroup orgGroup) {
		this.orgGroup = orgGroup;
	}

	@Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;

        RUserRoleOrgGroup that = (RUserRoleOrgGroup) o;
        return id != null && id.longValue() == that.id;
    }

    @Override
    public int hashCode () {
        return id != null ? id.hashCode () : 0;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}