/**
 * com.swg.authority.cap.invoke.data DeviceRelationMtInfo.java
 */
package smtcl.mocs.beans.authority.cache;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author gaokun
 * @create Jan 10, 2013 12:46:48 PM
 */
public class DeviceRelationMtInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  returnType(����ֵ����)
	 *  message(��Ϣ)
	 *  machineId(��������)
	 *  machineSeriaNo�����кţ�
		machineType����Ʒ�ͺţ�
		machineName�������������ƣ�
		orderNo�������ţ�
		customerName���û����ƣ�
		outDate������ʱ�䣩
		assemble_startDate��װ�俪ʼʱ�䣩
		assemble_endDate��װ�����ʱ�䣩
		install_startDate����װ��ʼʱ�䣩
		install_endDate����װ����ʱ�䣩
		order_createDate����������ʱ�䣩
		assemble_shiftNo��װ���Σ�
	 */
	
	/**
	 * ����ֵ����:
	 * ��0����ʾ �������кŲ�����
		��1����ʾ ���벻��ȷ 
		��2����ʾ �Ѿ�����
		��3����ʾ ��ȷ���
	 */
	@Expose
	private String returnType;
	
	/**
	 * ��Ϣ;
	 */
	@Expose
	private String message;
	
	@Expose
	private Integer machineId;
	
	/**
	 * ���к�;
	 */
	@Expose
	private String machineSeriaNo;
	
	/**
	 * ��Ʒ�ͺ�;
	 */
	@Expose
	private String machineType;
	
	/**
	 * ������������;
	 */
	@Expose
	private String machineName;
	
	/**
	 * ������;
	 */
	@Expose
	private String orderNo;
	
	/**
	 * �û�����;
	 */
	@Expose
	private String customerName;
	
	/**
	 * ����ʱ��;
	 */
	@Expose
	private String outDate;
	
	/**
	 * װ�俪ʼʱ��;
	 */
	@Expose
	@SerializedName(value="assemble_startDate")
	private String assembleStartDate;
	
	/**
	 * װ�����ʱ��;
	 */
	@Expose
	@SerializedName(value="assemble_endDate")
	private String assembleEndDate;
	
	/**
	 * ��װ��ʼʱ��;
	 */
	@Expose
	@SerializedName(value="install_startDate")
	private String installStartDate;
	
	/**
	 * ��װ����ʱ��;
	 */
	@Expose
	@SerializedName(value="install_endDate")
	private String installEndDate;
	
	/**
	 * ��������ʱ��;
	 */
	@Expose
	@SerializedName(value="order_createDate")
	private String orderCreateDate;
	
	/**
	 * װ����;
	 */
	@Expose
	@SerializedName(value="assemble_shiftNo")
	private String assembleShiftNo;
	

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}


	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
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
	 * @return the machineSeriaNo
	 */
	public String getMachineSeriaNo() {
		return machineSeriaNo;
	}


	/**
	 * @param machineSeriaNo the machineSeriaNo to set
	 */
	public void setMachineSeriaNo(String machineSeriaNo) {
		this.machineSeriaNo = machineSeriaNo;
	}


	/**
	 * @return the machineType
	 */
	public String getMachineType() {
		return machineType;
	}


	/**
	 * @param machineType the machineType to set
	 */
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}


	/**
	 * @return the machineName
	 */
	public String getMachineName() {
		return machineName;
	}


	/**
	 * @param machineName the machineName to set
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}


	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}


	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}


	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	/**
	 * @return the outDate
	 */
	public String getOutDate() {
		return outDate;
	}


	/**
	 * @param outDate the outDate to set
	 */
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}


	/**
	 * @return the assembleStartDate
	 */
	public String getAssembleStartDate() {
		return assembleStartDate;
	}


	/**
	 * @param assembleStartDate the assembleStartDate to set
	 */
	public void setAssembleStartDate(String assembleStartDate) {
		this.assembleStartDate = assembleStartDate;
	}


	/**
	 * @return the assembleEndDate
	 */
	public String getAssembleEndDate() {
		return assembleEndDate;
	}


	/**
	 * @param assembleEndDate the assembleEndDate to set
	 */
	public void setAssembleEndDate(String assembleEndDate) {
		this.assembleEndDate = assembleEndDate;
	}


	/**
	 * @return the installStartDate
	 */
	public String getInstallStartDate() {
		return installStartDate;
	}


	/**
	 * @param installStartDate the installStartDate to set
	 */
	public void setInstallStartDate(String installStartDate) {
		this.installStartDate = installStartDate;
	}


	/**
	 * @return the installEndDate
	 */
	public String getInstallEndDate() {
		return installEndDate;
	}


	/**
	 * @param installEndDate the installEndDate to set
	 */
	public void setInstallEndDate(String installEndDate) {
		this.installEndDate = installEndDate;
	}


	/**
	 * @return the orderCreateDate
	 */
	public String getOrderCreateDate() {
		return orderCreateDate;
	}


	/**
	 * @param orderCreateDate the orderCreateDate to set
	 */
	public void setOrderCreateDate(String orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}


	/**
	 * @return the assembleShiftNo
	 */
	public String getAssembleShiftNo() {
		return assembleShiftNo;
	}


	/**
	 * @param assembleShiftNo the assembleShiftNo to set
	 */
	public void setAssembleShiftNo(String assembleShiftNo) {
		this.assembleShiftNo = assembleShiftNo;
	}


	/**
	 * @return the machineId
	 */
	public Integer getMachineId() {
		return machineId;
	}


	/**
	 * @param machineId the machineId to set
	 */
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
