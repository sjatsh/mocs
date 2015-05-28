package smtcl.mocs.pojos.authority;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;



/**
 * 映射数据库中"角色"的实体类
 *
 * User: gjy
 * Date: 2012-10-11
 */
@javax.persistence.Table (name = "T_ROLE")
@Entity
public class Role implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@javax.persistence.Column(name = "ROLEID")
    @Id
    private String id;
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@javax.persistence.Column (name = "CODE")
    @Basic
    private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@javax.persistence.Column (name = "NAME")
    @Basic
    private String name;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Column (name="REMARK")
    private String remark;

    public String getRemark () {
        return remark;
    }

    public void setRemark (String remark) {
        this.remark = remark;
    }
    
    @JoinColumn (name="ROLETYPE")
    @ManyToOne (fetch = javax.persistence.FetchType.EAGER)
	@Fetch (FetchMode.JOIN)
    private TypeRole roleType;
    
    public TypeRole getRoleType() {
		return roleType;
	}

	public void setRoleType(TypeRole roleType) {
		this.roleType = roleType;
	}

	@Column (name="STATE")
    private Long state;

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "CREATE_USER")
    @Fetch (FetchMode.JOIN)
    private User creater;

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

    @Column(name="FLAG")
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
