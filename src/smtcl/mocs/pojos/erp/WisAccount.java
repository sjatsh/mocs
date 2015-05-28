package smtcl.mocs.pojos.erp;

 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * WisTransfer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_ACCOUNT" )
public class WisAccount implements java.io.Serializable {
	private Integer dispositionId;
	private Integer distributionAccount; 
	private String segment1;
	private Integer organizationId;
	private Integer flag;
	
	public WisAccount(){
		super();
	}
	
	@Id
	@Column(name = "DISPOSITION_ID")
	public Integer getDispositionId() {
		return dispositionId;
	}
	public void setDispositionId(Integer dispositionId) {
		this.dispositionId = dispositionId;
	}
	
	@Column(name = "DISTRIBUTION_ACCOUNT")
	public Integer getDistributionAccount() {
		return distributionAccount;
	}
	public void setDistributionAccount(Integer distributionAccount) {
		this.distributionAccount = distributionAccount;
	}
	
	@Column(name = "SEGMENT1")
	public String getSegment1() {
		return segment1;
	}
	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}
	
	@Column(name = "ORGANIZATION_ID")
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	
	@Column(name = "FLAG")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	
}