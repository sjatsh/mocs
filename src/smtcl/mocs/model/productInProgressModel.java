package smtcl.mocs.model;

/**
 * 在制品批次统计
 *
 */
public class productInProgressModel {
	private String productName;//产品名称
	private String batchNo;//批次号
	private String orderNo;//订单号
	private String productPicNO;//产品图号
	private String deliveryPic;//交货图
	private String planNumber;//计划数量
	private String onlineNumber;//上线数量(投入)
	private String pipNumber;//在制数量
	private String offlineNumber;//下线数量
	private String processName;//工序名称
	private String dispatchNo;//工单编号
	private String finishNum;//产出
	private String goodQuantity;//良品率
	private String planStarttime;//工序开始时间(计划开始时间)
	private String planEndtime;//计划结束时间
	
	public productInProgressModel(){
		
	}
	
	public productInProgressModel(String productName, String batchNo,
			String orderNo, String productPicNO, String deliveryPic,
			String planNumber, String onlineNumber, String pipNumber,
			String offlineNumber,String planStarttime,String planEndtime) {
		super();
		this.productName = productName;
		this.batchNo = batchNo;
		this.orderNo = orderNo;
		this.productPicNO = productPicNO;
		this.deliveryPic = deliveryPic;
		this.planNumber = planNumber;
		this.onlineNumber = onlineNumber;
		this.pipNumber = pipNumber;
		this.offlineNumber = offlineNumber;
		this.planStarttime = planStarttime;
		this.planEndtime = planEndtime;
	}

	
	public productInProgressModel(String processName,String dispatchNo,String onlineNumber,
			String finishNum,String pipNumber, String goodQuantity, String planStarttime,
			String productName, String planNumber,String offlineNumber) {
		super();
		this.processName = processName;
		this.dispatchNo = dispatchNo;
		this.onlineNumber = onlineNumber;
		this.finishNum = finishNum;
		this.pipNumber = pipNumber;
		this.goodQuantity = goodQuantity;
		this.planStarttime = planStarttime;
		this.productName = productName;
		this.planNumber = planNumber;
		this.offlineNumber = offlineNumber;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getProductPicNO() {
		return productPicNO;
	}
	public void setProductPicNO(String productPicNO) {
		this.productPicNO = productPicNO;
	}
	public String getDeliveryPic() {
		return deliveryPic;
	}
	public void setDeliveryPic(String deliveryPic) {
		this.deliveryPic = deliveryPic;
	}
	public String getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}
	public String getOnlineNumber() {
		return onlineNumber;
	}
	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
	}
	public String getPipNumber() {
		return pipNumber;
	}
	public void setPipNumber(String pipNumber) {
		this.pipNumber = pipNumber;
	}
	public String getOfflineNumber() {
		return offlineNumber;
	}
	public void setOfflineNumber(String offlineNumber) {
		this.offlineNumber = offlineNumber;
	}

	public String getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(String finishNum) {
		this.finishNum = finishNum;
	}

	public String getGoodQuantity() {
		return goodQuantity;
	}

	public void setGoodQuantity(String goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	public String getPlanStarttime() {
		return planStarttime;
	}

	public void setPlanStarttime(String planStarttime) {
		this.planStarttime = planStarttime;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public String getPlanEndtime() {
		return planEndtime;
	}

	public void setPlanEndtime(String planEndtime) {
		this.planEndtime = planEndtime;
	}
	
}
