package smtcl.mocs.model;

/**
 * ����Ʒ����ͳ��
 *
 */
public class productInProgressModel {
	private String productName;//��Ʒ����
	private String batchNo;//���κ�
	private String orderNo;//������
	private String productPicNO;//��Ʒͼ��
	private String deliveryPic;//����ͼ
	private String planNumber;//�ƻ�����
	private String onlineNumber;//��������(Ͷ��)
	private String pipNumber;//��������
	private String offlineNumber;//��������
	private String processName;//��������
	private String dispatchNo;//�������
	private String finishNum;//����
	private String goodQuantity;//��Ʒ��
	private String planStarttime;//����ʼʱ��(�ƻ���ʼʱ��)
	private String planEndtime;//�ƻ�����ʱ��
	
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
