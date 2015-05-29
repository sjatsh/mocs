package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IDeviceService;

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
	 * 零件名称集合
	 */
	private List<Map<String,Object>> partTypeList = new ArrayList<Map<String,Object>>();
	/**
	 * 批次No集合
	 */
	private List<Map<String,Object>> jobPlanNoList = new ArrayList<Map<String,Object>>();
	/**
	 * 库房编号集合
	 */
	private List<Map<String,Object>> inventoryNoList = new ArrayList<Map<String,Object>>();
	/**
	 * 库位编号集合
	 */
	private List<Map<String,Object>> materialPositionNoList = new ArrayList<Map<String,Object>>();
	/**
	 * 综合信息（设备，部件，工单）
	 */
	private List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
	
	public List<Map<String, Object>> getInventoryNoList() {
		return inventoryNoList;
	}

	public void setInventoryNoList(List<Map<String, Object>> inventoryNoList) {
		this.inventoryNoList = inventoryNoList;
	}

	private String isSuccess;
	/**
	 * 部件类型ID
	 */
	private String partTpyeId;
	/**
	 * 部件类型名称
	 */
	private String partTpyeName;
	/**
	 * 批次ID
	 */
	private String jobplanId;
	/**
	 * 批次No
	 */
	private String jobplanNo;
	/**
	 * 修改数量
	 */
	private String changeNum;
	/**
	 * 物料No
	 */
	private String materailNo;
	/**
	 * 批次完成数量
	 */
	private String jobplanFinishNum;
	/**
	 * 批次入库数量
	 */
	private String jobplanInStockNum;
	/**
	 * 入库数量
	 */
	private String instockNum;
	/**
	 * 最大入库数量
	 */
	private String maxInstockNum;
	/**
	 * 入库单号
	 */
	private String instockNo;
	/**
	 * 库房编号
	 */
	private String inventoryCode;
	/**
	 * 库房ID
	 */
	private String inventoryId;
	/**
	 * 库位ID
	 */
	private String materialPositionId;
	/**
	 * 标识
	 */
	private Boolean flag =false;
	/**
	 * 节点
	 */
	private String nodeId;
	/**
	 * 入库批次号
	 */
	private String storageNo;
	private IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	
	
	public ProductToStockBean() {
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    nodeId = (String)session.getAttribute("nodeid");
		
		partTypeList = deviceService.getPartTypeMap("",nodeId);
		SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		instockNo = "RK"+minuteFormat.format(new Date());
	    
		//获取库房号集合
		inventoryNoList =deviceService.getListInfo("SELECT id,storage_no as no from t_storage_info "
				+ "where storage_status ='活动' and nodeId ='"+nodeId+"'");
	}
	 
	public void getJobPlanInfo(){
		instockNum =null;
		dataList = deviceService.getListInfo("SELECT materailNo,id,materailName,num,type,processOrder,materailId from (SELECT t_materail_type_info.no as materailNo,t_part_type_info.ID as id,"
				+ " t_materail_type_info.ID as materailId,"
				+ " t_materail_type_info.name as materailName,sum(r_processmaterial_info.requirementNum) as num,"
				+ " r_processmaterial_info.requirementType as type,t_process_info.process_order as processOrder"
				+ " from t_part_type_info "
				+ " INNER JOIN t_processplan_info on t_processplan_info.parttypeID = t_part_type_info.ID"
				+ " INNER JOIN t_process_info on t_process_info.processPlanID = t_processplan_info.ID"
				+ " INNER JOIN r_processmaterial_info on r_processmaterial_info.processID = t_process_info.ID"
				+ " INNER JOIN t_materail_type_info on t_materail_type_info.ID = r_processmaterial_info.materialTypeID"
				+ " where t_part_type_info.ID ="+partTpyeId+" and t_processplan_info.defaultSelected =1 GROUP BY materailNo) a "
						+ " WHERE NOT ISNULL(id) ");
		
//		dataList = deviceService.getListInfo("SELECT t_materail_type_info.no as materailNo,"
//				+ " t_part_type_info.ID as id,t_materail_type_info.ID as materailId,"
//				+ " t_materail_type_info.name as materailName,r_processmaterial_info.requirementNum as num,"
//				+ " r_processmaterial_info.requirementType as type,t_process_info.process_order as processOrder"
//				+ " from t_part_type_info "
//				+ " INNER JOIN t_processplan_info on t_processplan_info.parttypeID = t_part_type_info.ID"
//				+ " INNER JOIN t_process_info on t_process_info.processPlanID = t_processplan_info.ID"
//				+ " INNER JOIN r_processmaterial_info on r_processmaterial_info.processID = t_process_info.ID"
//				+ " INNER JOIN t_materail_type_info on t_materail_type_info.ID = r_processmaterial_info.materialTypeID"
//				+ " where t_part_type_info.ID ="+partTpyeId+" and t_processplan_info.defaultSelected =1");
		
		for(Map<String,Object> dd:dataList){
			if(null ==dd.get("materailNo")){
				//dataList.remove(i);
			}else{
				List<Map<String,Object>> list = new  ArrayList<Map<String,Object>>();
				if(null != dd.get("type") && dd.get("type").toString().equals("主料")){
					
					//获取主料批次（主料批次就是产品的批次）
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
					dd.put("storageNo", list.get(0).get("no").toString());// 20140923 FW 入库批次
					dd.put("finishNum", list.get(0).get("finishNum").toString());
					dd.put("instockNum", list.get(0).get("instockNum").toString());
					dd.put("leftNum", String.valueOf(Integer.parseInt(list.get(0).get("finishNum").toString())-Integer.parseInt(list.get(0).get("instockNum").toString())));
					//jobplanId =list.get(0).get("id").toString();
					//jobplanNo = list.get(0).get("no").toString();
					//dd.put("jobplanList", list);
				}else{
					dd.put("jobplanId", "0");
					dd.put("jobplanNo", "外协");
					dd.put("storageNo", "外协");// 20140923 FW 入库批次
					dd.put("finishNum", "不可用");
					dd.put("instockNum", "不可用");
					dd.put("leftNum", "不可用");
					//dd.put("jobplanList", "");
				}
				
			}
		}
		int tempNum = Integer.MAX_VALUE;
		for(Map<String,Object> dd:dataList){
			if(null !=dd.get("finishNum") &&null !=dd.get("instockNum") && !dd.get("finishNum").toString().equals("不可用"))
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
			//tempNum =0;
		}
		maxInstockNum = Integer.toString(tempNum);
	}
	/**
	 * 成品入库保存
	 */
	public void saveDataInfo(){
		isSuccess ="入库失败";
		//数据
		int tempNum = Integer.MAX_VALUE;
		for(Map<String,Object> dd:dataList){
			if(null !=dd.get("finishNum") &&null !=dd.get("instockNum") && !dd.get("finishNum").toString().equals("不可用"))
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
			//tempNum =0;
		}
		maxInstockNum = Integer.toString(tempNum);
		
		if(null ==instockNum ||instockNum.equals("0")){
			isSuccess ="入库数量不正确，请重新填写";
			return;
		}
        if(Integer.parseInt(instockNum)>Integer.parseInt(maxInstockNum)){
        	isSuccess ="入库数量超过最大值，请重新填写";
        	return;
		}
		String str = deviceService.saveInStockDataInfo(instockNum, inventoryId,materialPositionId,partTpyeId,instockNo,dataList);
		if(str.equals("添加成功")){
			isSuccess ="入库成功";
			SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			instockNo = "RK"+minuteFormat.format(new Date());
			refreshData();
		}
	}
	/**
	 * 获取库位List
	 */
	 public void getMaterialPositionList(){
		if(null!= inventoryId && ""!= inventoryId){
		   materialPositionNoList = deviceService.getListInfo("select t_materiel_position_info.id as id,t_materiel_position_info.positionNo as no "
		   		+ " from t_materiel_position_info"
		   		+ " inner join r_storage_position on r_storage_position.position_id =t_materiel_position_info.id "
		   		+ " inner join t_storage_info on t_storage_info.id = r_storage_position.storage_id "
		   		+ " where t_storage_info.id ="+inventoryId+""
		   				+ " and t_materiel_position_info.positionStatus ='1'");
		}
	}
	
	/**
	 * 入库后重新获取数据
	 */
	public void refreshData(){
		for(Map<String,Object> dd:dataList){
			if(null ==dd.get("materailNo")){
			}else{
				String str = dd.get("jobplanNo").toString();
				List<Map<String,Object>> list = deviceService.getListInfo("select id,no,finishNum,instockNum"
						+ " from t_jobplan_info where no ='"+str+"'");
				if(list.size()>0){
					dd.put("jobplanId", list.get(0).get("id").toString());
					dd.put("jobplanNo", list.get(0).get("no").toString());
					dd.put("finishNum", list.get(0).get("finishNum").toString());
					dd.put("instockNum", list.get(0).get("instockNum").toString());
					dd.put("leftNum", String.valueOf(Integer.parseInt(list.get(0).get("finishNum").toString())-Integer.parseInt(list.get(0).get("instockNum").toString())));
					//dd.put("jobplanList", list);
				}else{
					//dd.put("jobplanId", "0");
					//dd.put("jobplanNo", "外协");
					//dd.put("finishNum", "不可用");
					//dd.put("instockNum", "不可用");
				}
				
			}
		}
	}
	/**
	 * 列表中按钮事件
	 * @param strmaterailNo
	 */
	public void getInfo(String strmaterailNo,String bomType){
		if(bomType.equals("主料")){
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
			storageNo = jobPlanNoList.get(0).get("no").toString();
			jobplanFinishNum = jobPlanNoList.get(0).get("finishNum").toString();
			jobplanInStockNum = jobPlanNoList.get(0).get("instockNum").toString();
			
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("id", "0");
			map.put("no", "外协");
			map.put("finishNum", "不可用");
			map.put("instockNum", "不可用");
			jobPlanNoList.add(map);
			
			isSuccess ="允许";
		}else{
			//isSuccess ="禁止";
			//jobPlanNoList.add(Map<>)
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("id", "0");
			map.put("no", "外协");
			map.put("finishNum", "不可用");
			map.put("instockNum", "不可用");
			jobplanFinishNum ="";
			jobplanInStockNum ="";
			jobPlanNoList.add(map);
			isSuccess ="允许";
		}
	}
	/**
	 * 计算最大入库数量
	 */
	public void maxInStockNum(){
		int tempNum = Integer.MAX_VALUE;
		for(Map<String,Object> dd:dataList){
			if(null !=dd.get("finishNum") &&null !=dd.get("instockNum") && !dd.get("finishNum").toString().equals("不可用"))
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
			//tempNum =0;
		}
		instockNum = Integer.toString(tempNum);
	}
	
	/**
	 * 设置完批次成数量和入库数量
	 */
	public void showInfo(){
		flag = false;
		if(jobplanId.equals("-1")){
			flag =true;
			return;
		}
		List<Map<String,Object>> list = deviceService.getListInfo("select id,no,finishNum,instockNum from "
				+ " t_jobplan_info where id="+jobplanId+"");
		if(list.size()>0){
			jobplanId = list.get(0).get("id").toString();
			jobplanNo = list.get(0).get("no").toString();
			storageNo = list.get(0).get("no").toString();//add 20140923 FW 生产批号
			jobplanFinishNum = list.get(0).get("finishNum").toString();
			jobplanInStockNum = list.get(0).get("instockNum").toString();
			
			//获取主料库房号（每个产品只有一个主料）
			//List<Map<String,Object>> list2 = importService.getInventoryCode(list.get(0).get("no").toString());
			//if(list2.size()>0)
			//{
			//	inventoryCode = list2.get(0).get("INVENTORYCODE").toString();	
			//}
		
		} else{
			jobplanId ="0";
			jobplanNo ="外协";
			storageNo ="外协";
			jobplanFinishNum ="不可用";
			jobplanInStockNum ="不可用";
		}
	}
	
	/**
	 * 保存修改信息
	 */
	public void editDataListInfo(){
		for(Map<String,Object> dd:dataList){
			if(materailNo.contains(dd.get("materailNo").toString()) && !flag){
				
				dd.put("jobplanId", jobplanId);
				dd.put("jobplanNo", jobplanNo);
				dd.put("storageNo", storageNo);
				dd.put("finishNum", jobplanFinishNum);
				dd.put("instockNum", jobplanInStockNum);
				if(jobplanFinishNum.equals("不可用")){
					dd.put("leftNum","不可用");
				}else{
				  dd.put("leftNum", (Integer.parseInt(jobplanFinishNum) -Integer.parseInt(jobplanInStockNum)));
				}
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

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		//获取库房号集合
				inventoryNoList =deviceService.getListInfo("SELECT id,storage_no as no from t_storage_info "
						+ "where storage_status ='活动' and nodeId ='"+nodeId+"'");
		this.inventoryId = inventoryId;
	}

	public List<Map<String, Object>> getMaterialPositionNoList() {
		return materialPositionNoList;
	}

	public void setMaterialPositionNoList(
			List<Map<String, Object>> materialPositionNoList) {
		this.materialPositionNoList = materialPositionNoList;
	}

	public String getMaterialPositionId() {
		return materialPositionId;
	}

	public void setMaterialPositionId(String materialPositionId) {
		this.materialPositionId = materialPositionId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getStorageNo() {
		return storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}

	
	
}
