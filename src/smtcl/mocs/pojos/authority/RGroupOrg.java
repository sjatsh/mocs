package smtcl.mocs.pojos.authority;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: wangli
 * Date: 12-10-23
 * Time: 下午6:13
 */
@Entity
@Table(name = "R_GROUP_ORG")
public class RGroupOrg implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "NODEID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization org;

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization orgId) {
        this.org = orgId;
    }

    @JoinColumn(name = "ORG_GROUP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrgGroup orgGroup;

    public OrgGroup getOrgGroup() {
        return orgGroup;
    }

    public void setOrgGroup(OrgGroup orgGroupId) {
        this.orgGroup = orgGroupId;
    }

    @Column(name="FLAG")
    @Basic
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
