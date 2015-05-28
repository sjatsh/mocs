/**
 * com.swg.authority.log.data Log.java
 */
package smtcl.mocs.pojos.authority;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 日志;
 * @author gaokun
 * @create Dec 25, 2012 2:32:18 PM
 */
@Entity
@Table(name = "T_LOG")
public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
	
	@Column(name="OP_TIME")
	private Date opTime;
	
	@Column(name="LOGIN_NAME")
	private String userName;
	
	@Column(name="PAGE_ID")
	private String pageId;
	
	@Column(name="IP")
	private String ip;
	
	@Column(name="MESSAGE")
	private String message;
	
	@Column(name="STATUS")
	private int status;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the opTime
	 */
	public Date getOpTime() {
		return opTime;
	}


	/**
	 * @param opTime the opTime to set
	 */
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @return the pageId
	 */
	public String getPageId() {
		return pageId;
	}


	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}


	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}


	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
