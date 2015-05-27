package smtcl.mocs.pojos.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author gaokun
 * @create Nov 27, 2012 3:31:09 PM
 */
@javax.persistence.Table (name = "T_USER_TYPE")
@Entity
public class TypeUser implements java.io.Serializable{
	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue (generator = "system-uuid")
    @GenericGenerator (name = "system-uuid", strategy = "uuid")
    @Column (name = "USER_TYPE")
	@Expose
	private String typeId;
	
	@Column (name = "TXT")
	@Expose
	private String text;
	
	@Column (name = "STATUS")
	@Expose
	private Integer show = 1;
	
	/**
	 * @return the show
	 */
	public Integer getShow() {
		return show;
	}

	/**
	 * @param show the show to set
	 */
	public void setShow(Integer show) {
		this.show = show;
	}
}