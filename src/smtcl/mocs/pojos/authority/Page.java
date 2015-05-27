package smtcl.mocs.pojos.authority;

import java.util.Collection;

import javax.persistence.*;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;
import org.json.JSONString;

/**
 * 映射数据库中"页面"的实体类
 *
 * User: gjy
 * Date: 2012-10-15
 */
@javax.persistence.Table (name = "T_PAGE")
@Entity
@NamedQueries ({
    @NamedQuery (
        name = "getMenuByUserId",
        query =
        "select distinct new Map (a.page.pageId as id, a.page.module as module, a.page.url as url, a.page.pageName as label_key, a.page.seq as seq)" +
        "  from RRolePage a, RUserRoleOrgGroup b" +
        " where b.user.userId = :userId " +
        "   and a.page.module.app.appId = :appId " +
        "   and a.role = b.role"
    )
})
public class Page implements java.io.Serializable,JSONString {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue (generator = "system-uuid")
    @GenericGenerator (name = "system-uuid", strategy = "uuid")
    @Column (name = "PAGE_ID")
    @Expose
    @SerializedName ("id")
	private String pageId;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "MODULE_ID")
    @Fetch (FetchMode.JOIN)
    @Expose (serialize = false)
	private Module module;
	
	@Column (name = "LABEL_KEY")
    @Expose
    @SerializedName ("name")
	private String pageName;
	
	@Column (name = "SEQ")
    @Expose (serialize = false)
	private Integer seq;
	
	@Column (name = "URL")
    @Expose
	private String url;

    @Transient
    @Expose
    private String label;
	
	@Transient
    @Expose (serialize = false)
	private Collection<Button> buttons;
	
	@Transient
    private boolean current = false;
	
	public Collection<Button> getButtons() {
		return buttons;
	}

	public void setButtons(Collection<Button> buttons) {
		this.buttons = buttons;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    /**
	 * @return the current
	 */
	public boolean isCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(boolean current) {
		this.current = current;
	}

	public String toJSONString() {
		return this.getJSONObject().toString();
	}

	public JSONObject getJSONObject() {
		JSONObject json = new JSONObject();
		json.put("pageId", pageId);
		json.put("pageName", pageName);
		json.put("seq", seq);
		return json;
	}
}