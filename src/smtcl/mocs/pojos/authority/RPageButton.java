package smtcl.mocs.pojos.authority;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * 映射数据库中"页面-按钮"关系表的实体
 *
 * User: gjy
 * Date: 2012-11-2
 */
@javax.persistence.Table (name = "R_PAGE_BUTTON")
@Entity
public class RPageButton implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")   
    private Long id;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "PAGE_ID")
    @Fetch (FetchMode.JOIN)
    @Basic
    private Page page;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "BUTTON_ID")
    @Fetch (FetchMode.JOIN)
    private Button button;

    @Column(name="FLAG")
    private String flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setPage(Page page) {
		this.page = page;
	}

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;

        RPageButton that = (RPageButton) o;
        return id != null && id.longValue() == that.id;
    }

    @Override
    public int hashCode () {
        return id != null ? id.hashCode () : 0;
    }
	
}