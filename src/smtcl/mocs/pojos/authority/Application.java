package smtcl.mocs.pojos.authority;

import java.util.Collection;

import javax.persistence.*;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;
import org.json.JSONString;

/**
 * 映射数据库中"应用系统"的实体类 
 *
 * User: gjy
 * Date: 2012-10-15
 */
@javax.persistence.Table (name = "T_APPLICATION")
@Entity
public class Application implements java.io.Serializable,JSONString{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue (generator = "system-uuid")
    @GenericGenerator (name = "system-uuid", strategy = "uuid")
    @Column (name = "APP_ID")
    @Expose
    @SerializedName ("id")
	private String appId;
	
	@Column (name = "LABEL_KEY")
    @Expose
    @SerializedName ("name")
	private String appName;
	
	@Column (name = "REMARK")
	private String remark;
	
	@Column (name = "SEQ")
	private Integer seq;
	
	@OneToMany (mappedBy = "app")
    @Expose (serialize = false)
    private Collection<Module> modules;

    @Column (name = "URL")
    @Expose
    private String url;

    @Transient
    @Expose
    private String label;
	
	public Collection<Module> getModules() {
		return modules;
	}

	public void setModules(Collection<Module> modules) {
		this.modules = modules;
	}

	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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
		json.put("appId", appId);
		json.put("appName", appName);
		json.put("remark", remark);
		json.put("seq", seq);
        json.put("url", url);
		return json;
	}

	
}