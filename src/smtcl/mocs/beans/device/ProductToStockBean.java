package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.utils.device.StringUtils;
/**
 * 
 */
@ManagedBean(name="ProductToStock")
@ViewScoped
public class ProductToStockBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ������Ƽ���
	 */
	private List<Map<String,Object>> partTypeList = new ArrayList<Map<String,Object>>();
	/**
	 * ����No����
	 */
	private List<Map<String,Object>> jobPlanNoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * �ۺ���Ϣ���豸��������������
	 */
	private List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
	
	private String isSuccess;
	/**
	 * ��������ID
	 */
	private String partTpyeId;
	/**
	 * ������������
	 */
	private String partTpyeName;
	/**
	 * ����ID
	 */
	private String jobplanId;
	/**
	 * ����No
	 */
	private String jobplanNo;
	/**
	 * �޸�����
	 */
	private String changeNum;
	/**
	 * ����No
	 */
	private String materailNo;
	/**
	 * �����������
	 */
	private String jobplanFinishNum;
	/**
	 * �����������
	 */
	private String jobplanInStockNum;
	/**
	 * �������
	 */
	private String instockNum;
	/**
	 * ����������
	 */
	private String maxInstockNum;
	/**
	 * ��ⵥ��
	 */
	private String instockNo;
	/**
	 * �ⷿ���
	 */
	private String inventoryCode;
	private IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	
	
	public ProductToStockBean() {
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		partTypeList = deviceService.getPartTypeMap("",nodeid);
		SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		instockNo = "RK"+minuteFormat.format(new Date());
	}
	 
	public void getJobPlanInfo(){
		instockNum =null;
		dataList = deviceService.getListInfo("SELECT t_materail_type_info.no as materailNo,t_part_type_info.ID as id,"
				+ " t_materail_type_info.name as materailName,sum(r_processmaterial_info.requirementNum) as num,"
				+ " r_processmaterial_info.requirementType as type"
				+ " from t_part_type_info "
				+ " INNER JOIN t_processplan_info on t_processplan_info.parttypeID = t_part_type_info.ID"
				+ " INNER JOIN t_process_info on t_process_info.processPlanID = t_processplan_info.ID"
				+ " INNER JOIN r_processmaterial_info on r_processmaterial_info.processID = t_process_info.ID"
				+ " INNER JOIN t_materail_type_info on t_materail_type_info.ID = r_processmaterial_info.materialTypeID"
				+ " where t_part_type_info.ID ="+partTpyeId+" and t_processplan_info.defaultSelected =1 GROUP BY materailNo");
		for(Map<String,Object> dd:dataList){
			if(null ==dd.get("materailNo")){
				//dataList.remove(i);
			}else{
				List<Map<String,Object>> list = new  ArrayList<Map<String,Object>>();
				if(null != dd.get("type") && dd.get("type").toString().equals("����")){
					
					//��ȡ�������Σ��������ξ��ǲ�Ʒ�����Σ�
					list = deviceService.getListInfo("select id,no,finishNum,instockNum"
							+ " from t_jobplan_info where partID = "+partTpyeId+" and plan_type =2");
					
				}else{
					String str = dd.get("materailNo").toString();
					list = deviceService.getListInfo("select t_jobplan_info.id as id,t_jobplan_info.no as no,"
							+ " t_jobplan_info.finishNum as finishNum,t_jobplan_info.instockNum as instockNum"
							+ " from t_jobplan_info "
							+ " inner join t_part_type_info on t_part_type_info.id = t_jobplan_info.partID "
							+ " where t_part_type_info.no ='"+str+"' and t_jobplan_info.plan_type =2");
				}
				//String str = dd.get("id").toString();
				//List<Map<String,Object>> list = deviceService.getListInfo("select id,no,finishNum from t_jobplan_info where partID ="+str+"");
				if(list.size()>0){
					dd.put("jobplanId", list.get(0).get("id").toString());
					dd.put("jobplanNo", list.get(0).get("no").toString());
					dd.put("finishNum", list.get(0).get("finishNum").toString());
					dd.put("instockNum", list.get(0).get("instockNum").toString());
					dd.put("leftNum", String.valueOf(Integer.parseInt(list.get(0).get("finishNum").toString())-Integer.parseInt(list.get(0).get("instockNum").toString())));
					//jobplanId =list.get(0).get("id").toString();
					//jobplanNo = list.get(0).get("no").toString();
					//dd.put("jobplanList", list);
				}else{
					dd.put("jobplanId", "0");
					dd.put("jobplanNo", "��Э");
					dd.put("finishNum", "������");
					dd.put("instockNum", "������");
					dd.put("leftNum", "������");
					//dd.put("jobplanList", "");
				}
				
			}
		}
		int tempNum = Integer.MAX_VALUE;
		for(Map<String,Object> dd:dataList){
			if(null !=dd.get("finishNum") &&null !=dd.get("instockNum") && !dd.get("finishNum").toString().equals("������"))
			{
				int finish = Integer.parseInt(dd.get("finishNum").toString());
				int instock = Integer.parseInt(dd.get("instockNum").toString());
				int bomNum = Integer.parseInt(dd.get("num").toString());
				if(Math.floor((finish-instock)/bomNum) < tempNum){
					tempNum =(int)Math.floor((finish-instock)/bomNum);
				}
			}
		}
		if(tempNum == Integer.MAX_VALUE){
			tempNum =0;
		}
		maxInstockNum = Integer.toString(tempNum);
	}
	/**
	 * ��Ʒ��Ᵽ��
	 */
	public void saveDataInfo(){
		isSuccess ="���ʧ��";
		//����
		int tempNum = Integer.MAX_VALUE;
		for(Map<String,Object> dd:dataList){
			if(null !=dd.get("finishNum") &&null !=dd.get("instockNum") && !dd.get("finishNum").toString().equals("������"))
			{
				int finish = Integer.parseInt(dd.get("finishNum").toString());
				int instock = Integer.parseInt(dd.get("instockNum").toString());
				int bomNum = Integer.parseInt(dd.get("num").toString());
				if(Math.floor((finish-instock)/bomNum) < tempNum){
					tempNum =(int)Math.floor((finish-instock)/bomNum);
				}
			}
		}
		if(tempNum == Integer.MAX_VALUE){
			tempNum =0;
		}
		maxInstockNum = Integer.toString(tempNum);
		
		if(null ==instockNum ||instockNum.equals("0")){
			isSuccess ="�����������ȷ����������д";
			return;
		}
        if(Integer.parseInt(instockNum)>Integer.parseInt(maxInstockNum)){
        	isSuccess ="��������������ֵ����������д";
        	return;
		}
		String str = deviceService.saveInStockDataInfo(instockNum, inventoryCode,dataList);
		if(str.equals("��ӳɹ�")){
			isSuccess ="���ɹ�";
			refreshData();
		}
	}
	
	/**
	 * �������»�ȡ����
	 */
	public void refreshData(){
		for(Map<String,Object> dd:dataList){
			if(null ==dd.get("materailNo")){
			}else{
				String str = dd.get("jobplanNo").toString();
				List<Map<String,Object>> list = deviceService.getListInfo("select id,no,finishNum,instockNum"
						+ " from t_jobplan_info where no ='"+str+"'");
				if(list.size()>0){
					dd.remove("jobplanId");
					dd.remove("jobplanNo");
					dd.remove("finishNum");
					dd.remove("instockNum");
					dd.remove("leftNum");
					dd.put("jobplanId", list.get(0).get("id").toString());
					dd.put("jobplanNo", list.get(0).get("no").toString());
					dd.put("finishNum", list.get(0).get("finishNum").toString());
					dd.put("instockNum", list.get(0).get("instockNum").toString());
					dd.put("leftNum", String.valueOf(Integer.parseInt(list.get(0).get("finishNum").toString())-Integer.parseInt(list.get(0).get("instockNum").toString())));
					//dd.put("jobplanList", list);
				}else{
					//dd.put("jobplanId", "0");
					//dd.put("jobplanNo", "��Э");
					//dd.put("finishNum", "������");
					//dd.put("instockNum", "������");
				}
				
			}
		}
	}
	/**
	 * �б��а�ť�¼�
	 * @param strmaterailNo
	 */
	public void getInfo(String strmaterailNo,String bomType){
		if(bomType.equals("����")){
			jobPlanNoList = deviceService.getListInfo("select id,no,finishNum,instockNum"
							+ " from t_jobplan_info where partID = "+partTpyeId+" and plan_type =2");
			
		}else{
			jobPlanNoList = deviceService.getListInfo("select t_jobplan_info.id as id,t_jobplan_info.no as no,"
					+ "t_jobplan_info.finishNum as finishNum,t_jobplan_info.instockNum as instockNum"
					+ " from t_jobplan_info "
					+ " inner join t_part_type_info on t_part_type_info.id = t_jobplan_info.partID "
					+ " where t_part_type_info.no ='"+strmaterailNo+"' and t_jobplan_info.plan_type =2");
			
		}
		if(jobPlanNoList.size()>0){
			materailNo = strmaterailNo;
			jobplanId = jobPlanNoList.get(0).get("id").toString();
			jobplanNo = jobPlanNoList.get(0).get("no").toString();
			jobplanFinishNum = jobPlanNoList.get(0).get("finishNum").toString();
			jobplanInStockNum = jobPlanNoList.get(0).get("instockNum").toString();
			isSuccess ="����";
		}else{
			isSuccess ="��ֹ";
		}
	}
	/**
	 * ��������������
	 */
	public void maxInStockNum(){
		int tempNum = Integer.MAX_VALUE;
		for(Map<String,Object> dd:dataList){
			if(null !=dd.get("finishNum") &&null !=dd.get("instockNum") && !dd.get("finishNum").toString().equals("������"))
			{
				int finish = Integer.parseInt(dd.get("finishNum").toString());
				int instock = Integer.parseInt(dd.get("instockNum").toString());
				int bomNum = Integer.parseInt(dd.get("num").toString());
				if(Math.floor((finish-instock)/bomNum) < tempNum){
					tempNum =(int)Math.floor((finish-instock)/bomNum);
				}
			}
		}
		if(tempNum == Integer.MAX_VALUE){
			tempNum =0;
		}
		instockNum = Integer.toString(tempNum);
	}
	
	/**
	 * ���������γ��������������
	 */
	public void showInfo(){
		List<Map<String,Object>> list = deviceService.getListInfo("select id,no,finishNum,instockNum from "
				+ " t_jobplan_info where id="+jobplanId+"");
		if(list.size()>0){
			jobplanId = list.get(0).get("id").toString();
			jobplanNo = list.get(0).get("no").toString();
			jobplanFinishNum = list.get(0).get("finishNum").toString();
			jobplanInStockNum = list.get(0).get("instockNum").toString();
		}
	}
	
	/**
	 * �����޸���Ϣ
	 */
	public void editDataListInfo(){
		for(Map<String,Object> dd:dataList){
			if(materailNo.contains(dd.get("materailNo").toString())){
				dd.remove("jobplanId");
				dd.remove("jobplanNo");
				dd.remove("finishNum");
				dd.remove("instockNum");
				dd.remove("leftNum");
				dd.put("jobplanId", jobplanId);
				dd.put("jobplanNo", jobplanNo);
				dd.put("finishNum", jobplanFinishNum);
				dd.put("instockNum", jobplanInStockNum);
				dd.put("leftNum", (Integer.parseInt(jobplanFinishNum) -Integer.parseInt(jobplanInStockNum)));
			}
		}
	}
	
	public List<Map<String, Object>> getPartTypeList() {
		return partTypeList;
	}
	
	public void setPartTypeList(List<Map<String, Object>> partTypeList) {
		this.partTypeList = partTypeList;
	}



	public List<Map<String, Object>> getJobPlanNoList() {
		return jobPlanNoList;
	}



	public void setJobPlanNoList(List<Map<String, Object>> jobPlanNoList) {
		this.jobPlanNoList = jobPlanNoList;
	}



	public String getPartTpyeId() {
		return partTpyeId;
	}



	public void setPartTpyeId(String partTpyeId) {
		this.partTpyeId = partTpyeId;
	}



	public String getJobplanId() {
		return jobplanId;
	}



	public void setJobplanId(String jobplanId) {
		this.jobplanId = jobplanId;
	}

	

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public String getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(String changeNum) {
		this.changeNum = changeNum;
	}
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getPartTpyeName() {
		return partTpyeName;
	}

	public void setPartTpyeName(String partTpyeName) {
		this.partTpyeName = partTpyeName;
	}

	public String getJobplanNo() {
		return jobplanNo;
	}

	public void setJobplanNo(String jobplanNo) {
		this.jobplanNo = jobplanNo;
	}
	public String getMaterailNo() {
		return materailNo;
	}

	public void setMaterailNo(String materailNo) {
		this.materailNo = materailNo;
	}

	public String getJobplanFinishNum() {
		return jobplanFinishNum;
	}

	public void setJobplanFinishNum(String jobplanFinishNum) {
		this.jobplanFinishNum = jobplanFinishNum;
	}

	public String getJobplanInStockNum() {
		return jobplanInStockNum;
	}

	public void setJobplanInStockNum(String jobplanInStockNum) {
		this.jobplanInStockNum = jobplanInStockNum;
	}

	public String getInstockNum() {
		return instockNum;
	}

	public void setInstockNum(String instockNum) {
		this.instockNum = instockNum;
	}

	public String getMaxInstockNum() {
		return maxInstockNum;
	}

	public void setMaxInstockNum(String maxInstockNum) {
		this.maxInstockNum = maxInstockNum;
	}

	public String getInstockNo() {
		return instockNo;
	}

	public void setInstockNo(String instockNo) {
		this.instockNo = instockNo;
	}

	public String getInventoryCode() {
		return inventoryCode;
	}

	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}

	
	
}
