package smtcl.mocs.web.webservice.model;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class MachineRequestInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date RequestTime;
	private String machineNo;
	public MachineRequestInfo(Date RequestTime,String machineNo){
		this.RequestTime=RequestTime;
		this.machineNo=machineNo;
	}
	public Date getRequestTime() {
		return RequestTime;
	}
	public void setRequestTime(Date requestTime) {
		RequestTime = requestTime;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
}
