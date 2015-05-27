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
	 *  returnType(返回值类型)
	 *  message(消息)
	 *  machineId(机床主键)
	 *  machineSeriaNo（序列号）
		machineType（产品型号）
		machineName（机床类型名称）
		orderNo（订单号）
		customerName（用户名称）
		outDate（出厂时间）
		assemble_startDate（装配开始时间）
		assemble_endDate（装配结束时间）
		install_startDate（安装开始时间）
		install_endDate（安装结束时间）
		order_createDate（订单生产时间）
		assemble_shiftNo（装配班次）
	 */
	
	/**
	 * 返回值类型:
	 * “0”表示 机床序列号不存在
		“1”表示 密码不正确 
		“2”表示 已经关联
		“3”表示 正确情况
	 */
	@Expose
	private String returnType;
	
	/**
	 * 消息;
	 */
	@Expose
	private String message;
	
	@Expose
	private Integer machineId;
	
	/**
	 * 序列号;
	 */
	@Expose
	private String machineSeriaNo;
	
	/**
	 * 产品型号;
	 */
	@Expose
	private String machineType;
	
	/**
	 * 机床类型名称;
	 */
	@Expose
	private String machineName;
	
	/**
	 * 订单号;
	 */
	@Expose
	private String orderNo;
	
	/**
	 * 用户名称;
	 */
	@Expose
	private String customerName;
	
	/**
	 * 出厂时间;
	 */
	@Expose
	private String outDate;
	
	/**
	 * 装配开始时间;
	 */
	@Expose
	@SerializedName(value="assemble_startDate")
	private String assembleStartDate;
	
	/**
	 * 装配结束时间;
	 */
	@Expose
	@SerializedName(value="assemble_endDate")
	private String assembleEndDate;
	
	/**
	 * 安装开始时间;
	 */
	@Expose
	@SerializedName(value="install_startDate")
	private String installStartDate;
	
	/**
	 * 安装结束时间;
	 */
	@Expose
	@SerializedName(value="install_endDate")
	private String installEndDate;
	
	/**
	 * 订单生产时间;
	 */
	@Expose
	@SerializedName(value="order_createDate")
	private String orderCreateDate;
	
	/**
	 * 装配班次;
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
