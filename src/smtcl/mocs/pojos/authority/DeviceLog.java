package smtcl.mocs.pojos.authority;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

/**
 * 设备机床关联日志
 * @author gaokun
 * @create Jan 28, 2013 11:34:37 AM
 */
@javax.persistence.Table (name = "r_NodeSymgMachine")
@Entity
public class DeviceLog implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column (name = "id")
    @Expose
	private String id;
	
	@Expose
	@Column(name="SYMGMACHINEID")
	private Integer symgMachineId;
	
	@Expose
	@Column(name="EQU_NODEID")
	private Integer equNodeid;
	
	@Expose
	@Column(name="INTERACTIONDATE")
	private Date interactionDate;
	
	@Expose
	@Column(name="INTERACTIONOPERATOR")
	private String interactionOperator;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the symgMachineId
	 */
	public Integer getSymgMachineId() {
		return symgMachineId;
	}

	/**
	 * @param symgMachineId the symgMachineId to set
	 */
	public void setSymgMachineId(Integer symgMachineId) {
		this.symgMachineId = symgMachineId;
	}

	/**
	 * @return the equNodeid
	 */
	public Integer getEquNodeid() {
		return equNodeid;
	}

	/**
	 * @param equNodeid the equNodeid to set
	 */
	public void setEquNodeid(Integer equNodeid) {
		this.equNodeid = equNodeid;
	}

	/**
	 * @return the interactionDate
	 */
	public Date getInteractionDate() {
		return interactionDate;
	}

	/**
	 * @param interactionDate the interactionDate to set
	 */
	public void setInteractionDate(Date interactionDate) {
		this.interactionDate = interactionDate;
	}

	/**
	 * @return the interactionOperator
	 */
	public String getInteractionOperator() {
		return interactionOperator;
	}

	/**
	 * @param interactionOperator the interactionOperator to set
	 */
	public void setInteractionOperator(String interactionOperator) {
		this.interactionOperator = interactionOperator;
	}
	
}