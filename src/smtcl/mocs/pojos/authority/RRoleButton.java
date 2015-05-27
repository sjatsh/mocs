package smtcl.mocs.pojos.authority;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * 映射数据库中"角色-按钮"关系表的实体
 *
 * User: gjy
 * Date: 2012-10-16
 */
@javax.persistence.Table (name = "R_ROLE_BUTTON")
@Entity
public class RRoleButton implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "ROLEID")
    @Fetch (FetchMode.JOIN)
    private Role role;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "BUTTON_ID")
    @Fetch (FetchMode.JOIN)
    private Button button;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "PAGE_ID")
    @Fetch (FetchMode.JOIN)
    private Page page;

    @Column(name="FLAG")
    private String flag;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public Page getPage() {
		return page;
	}

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setPage(Page page) {
		this.page = page;
	}

	@Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;

        RRoleButton that = (RRoleButton) o;
        return id != null && id.longValue() == that.id;
    }

    @Override
    public int hashCode () {
        return id != null ? id.hashCode () : 0;
    }
    
}