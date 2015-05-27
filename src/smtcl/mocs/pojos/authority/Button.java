package smtcl.mocs.pojos.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;
import org.json.JSONString;

/**
 * 映射数据库中"按钮"的实体类
 *
 * User: gjy
 * Date: 2012-10-15
 */
@javax.persistence.Table (name = "T_BUTTON")
@Entity
public class Button implements java.io.Serializable,JSONString {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue (generator = "system-uuid")
    @GenericGenerator (name = "system-uuid", strategy = "uuid")
    @Column (name = "BUTTON_ID")
	private String buttonId;
	
	@Column (name = "LABEL_KEY")
	private String buttonName;
	
	@Column (name = "SEQ")
	private Integer seq;
	
	@Transient
	private boolean isChecked;

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String toJSONString() {
		return this.getJSONObject().toString();
	}

	public JSONObject getJSONObject() {
		JSONObject json = new JSONObject();
		json.put("buttonId", buttonId);
		json.put("buttonName", buttonName);
		json.put("seq", seq);
		return json;
	}

    public Button clone() {
        Button btn = new Button();
        btn.buttonId = this.buttonId;
        btn.buttonName = this.buttonName;
        btn.seq = this.seq;
        return btn;
    }
}