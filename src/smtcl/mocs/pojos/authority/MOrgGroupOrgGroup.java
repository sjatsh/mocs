package smtcl.mocs.pojos.authority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author gaokun
 * @create Nov 20, 2012 2:17:23 PM
 */
@Entity
@Table(name = "M_ORGGROUP_ORGGROUP")
public class MOrgGroupOrgGroup implements Serializable {
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

    @JoinColumn(name = "ORG_GROUP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrgGroup orgGroup; //当前创建的数据组


    @JoinColumn(name = "C_ORG_GROUP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrgGroup createOrgGroup;//用户有权限创建的数据�?

    @Column(name="FLAG")
    private String flag;


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
