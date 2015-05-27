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
 * 映射数据库中"模块"的实体类
 *
 * User: gjy
 * Date: 2012-10-15
 */
@javax.persistence.Table (name = "T_MODULE")
@Entity
public class Module implements java.io.Serializable,JSONString {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue (generator = "system-uuid")
    @GenericGenerator (name = "system-uuid", strategy = "uuid")
    @Column (name = "MODULE_ID")
    @Expose
    @SerializedName ("id")
	private String moduleId;
	
	@ManyToOne (fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn (name = "APP_ID")
    @Fetch (FetchMode.JOIN)
    @Expose (serialize = false)
	private Application app;
	
	@Column (name = "LABEL_KEY")
    @Expose
    @SerializedName ("name")
	private String moduleName;
	
	@Column (name = "REMARK")
    @Expose (serialize = false)
	private String remark;
	
	@Column (name = "SEQ")
    @Expose (serialize = false)
	private Integer seq;
	
	@OneToMany (mappedBy = "module")
    @Expose
    private Collection<Page> pages;

    @Column (name = "URL")
    @Expose
    private String url;

    @Transient
    @Expose
    private String label;

	public Collection<Page> getPages() {
		return pages;
	}

	public void setPages(Collection<Page> pages) {
		this.pages = pages;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    public String toJSONString() {
		return this.getJSONObject().toString();
	}

	public JSONObject getJSONObject() {
		JSONObject json = new JSONObject();
		json.put("moduleId", moduleId);
		json.put("moduleName", moduleName);
		json.put("pages", pages);
		json.put("seq", seq);
        json.put("url", url);
		return json;
	}
}