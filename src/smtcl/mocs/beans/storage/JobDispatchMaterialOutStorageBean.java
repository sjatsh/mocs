package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.model.TMaterialStorageModel;
import smtcl.mocs.services.storage.IStorageManageService;
/**
 * 库存管理bean
 * 创建时间 2014-11-12
 * 作者 FW
 * 修改者
 * 修改时间
 */
@ManagedBean(name="JobDispatchMaterialOutStorage")
@ViewScoped
public class JobDispatchMaterialOutStorageBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String success;
	/**
	 * 批次集合List
	 */
	List<Map<String,Object>> jobPlanList = new ArrayList<Map<String,Object>>();
	/**
	 * 物料集合List
	 */
	List<Map<String,Object>> materialInfoList = new ArrayList<Map<String,Object>>();
	
	List<TMaterialStorageModel> materialStorageModelList = new ArrayList<TMaterialStorageModel>();
	List<Map<String,Object>> materialStorageList = new ArrayList<Map<String,Object>>();
	List<Map<String,Object>> transactionList = new ArrayList<Map<String,Object>>();//事务集合
	List<Map<String,Object>> outInStorageList = new ArrayList<Map<String,Object>>();//出入库信息集合
	
	List<Map<String,Object>> handleNumAndPriceList = new ArrayList<Map<String,Object>>();//处理数量
	
	List<Map<String,Object>> tempTransactionList = new ArrayList<Map<String,Object>>();//临时事务集合
	List<Map<String,Object>> tempOutInStorageList = new ArrayList<Map<String,Object>>();//临时出入库信息集合
	/**
	 * 节点
	 */
	String nodeid;
	/**
	 * 批次ID
	 */
	String jobPlanId;
	/**
	 * 出入库数量
	 */
	Double outInNum;
	/**
	 * 选择物料ID
	 */
	String selectedMaterialId;
	/**
	 * 选择工序序号
	 */
	String selectedProcessOrder;
	/**
	 * 选择工单编号
	 */
	String selectedJobDispatchNo;
	/**
	 * 选择index
	 */
	String selectedIndex;
	/**
	 * 选择价格
	 */
	String selectedPrice;
	/**
	 * 处理类型
	 */
	String trantionType;
	/**
	 * 发料总值
	 */
	Double materialTotalValue;
	/**
     * 事务No
     */
	int transNo;
	/**
	 * 事务处理日期
	 */
	Date transDate;
	/**
	 * 事务处理人员
	 */
	String transUser;
	/**
	 * 事务类型Id
	 */
	String transTypeId;
	/**
	 * 事务类型活动
	 */
	String transActivity;
	
	/**
	 * 批次控制
	 */
	Boolean batchCtrl;
	/**
	 * 序列控制
	 */
	Boolean seqCtrl;
	/**
     * 货位控制
     */
	Boolean positionCtrl;
	/**
	 *货位类型
	 */
	String positionType;
	/**
	 * 组织物料货位类型2
	 */
	String positionType2;
	
	private IStorageManageService iStorageManageService=(IStorageManageService)ServiceFactory.getBean("storageManage");
	
	public JobDispatchMaterialOutStorageBean() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (null !=request.getParameter("transTypeId")
			&& !"".equals(request.getParameter("transTypeId"))
			&& !request.getParameter("transTypeId").equals("undefined")){
			transTypeId = request.getParameter("transTypeId").toString();
		}
		if (null !=request.getParameter("transActivity")
				&& !"".equals(request.getParameter("transActivity"))
				&& !request.getParameter("transActivity").equals("undefined")){
			transActivity = request.getParameter("transActivity").toString();
		}
		if (null !=request.getParameter("userId")
				&& !"".equals(request.getParameter("userId"))
				&& !request.getParameter("userId").equals("undefined")){
			transUser = request.getParameter("userId").toString();
		}
		if (null !=request.getParameter("date")
				&& !"".equals(request.getParameter("date"))
				&& !request.getParameter("date").equals("undefined")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			try {
				transDate = sdf.parse(request.getParameter("date").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    nodeid = (String)session.getAttribute("nodeid");
	    jobPlanList = iStorageManageService.jobPlanList(nodeid);//批次集合
	    
	    //事务No
	    String maxID = iStorageManageService.getMaxID();
	    transNo = Integer.parseInt(maxID)+1;
	    
	}
	
	/**
	 *  获取批次所需物料
	 */
	public void getMaterialInfo(){
		transactionList.clear();//事务清空
		outInStorageList.clear();//出入库信息清空
		materialInfoList = iStorageManageService.materialList(jobPlanId);
		for(int i=0;i<materialInfoList.size();i++){
			materialInfoList.get(i).put("index", i);//序号
		}
	}
	
	/**
	 * 获取库房信息
	 */
	public void getStorageInfo(String materialId,String processOrder,String jobdispatchNo,String price,String index){
		outInNum =0.0;//出入库数量清零
		materialTotalValue = 0.00;//发料总值清空
		
		handleNumAndPriceList.clear();
		tempTransactionList.clear();
		tempOutInStorageList.clear();
		
		selectedMaterialId = materialId;
		selectedProcessOrder = processOrder;
		selectedJobDispatchNo = jobdispatchNo;
		selectedPrice = price;
		selectedIndex =index;
		
		List<Map<String,Object>> list = iStorageManageService.materialCtrList(materialId);
		
		
		if(list.size()>0){
			String isBatchCtrl =list.get(0).get("isBatchCtrl").toString();
			String isSeqCtrl =list.get(0).get("isSeqCtrl").toString();
			
			if(isBatchCtrl.equals("0")){
				 batchCtrl =true;
			 }else{
				 batchCtrl =false;
			 }
			 
			 if(isSeqCtrl.equals("0")){
				 seqCtrl =true;
			 }else{
				 seqCtrl =false;
			 }
			
			materialStorageList = iStorageManageService.materialStorageList(materialId, isBatchCtrl, isSeqCtrl);
		    if(materialStorageList.size()>0){
		    	for(Map<String,Object> ss:materialStorageList){
		    	    ss.put("num", "0");
		    	}
		    }
		}
		
	}
	
	/**
	 * 更新方法
	 * @param event
	 */
    @SuppressWarnings("unchecked")
	public void onEdit(RowEditEvent event) {  
    	Map<String,Object> map=(Map<String, Object>) event.getObject();
    	
    	//判断数量是否正确
    	if(null ==map.get("num") || map.get("num").toString() =="" || map.get("num").toString() =="0"){
    		 FacesMessage msg = new FacesMessage("出入库","更新失败,请确认！");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg); 
    	     return;
    	}
    	
		if(Double.parseDouble(map.get("num").toString())>Double.parseDouble(map.get("availableNum").toString())){
			FacesMessage msg = new FacesMessage("出库","出库数量不正确,请确认！");  
   	        FacesContext.getCurrentInstance().addMessage(null, msg); 
   	        map.put("num", "0");
   	        return;
		}
    	
    	Map<String,Object> tempMap = new HashMap<String, Object>();
    	boolean flag =false;
    	boolean flag2= false;
    	boolean flag3= false;
    	//事务信息控制
    	for(Map<String,Object> tt:tempTransactionList){
    		if(tt.get("id").toString().equals(map.get("id").toString())){
    			tt.put("num", map.get("num").toString());
    			flag =true;
    			break;
    		}
    	}
    	if(!flag){
    		tempMap.put("id", map.get("id"));
    		tempMap.put("selectedIndex", map.get("selectedIndex"));
    		tempMap.put("transNo", transNo);
    		tempMap.put("materialId", selectedMaterialId);
    		tempMap.put("storageId", map.get("storageId"));
    		tempMap.put("positonId", map.get("positonId"));
    		tempMap.put("processNum", map.get("num"));
    		tempMap.put("batchCtrl", batchCtrl);
    		tempMap.put("seqCtrl", seqCtrl);
    		tempMap.put("batchId", map.get("batchId"));
    		tempMap.put("seqId", map.get("seqId"));
    		tempMap.put("positionCtrl", positionCtrl);
    		tempMap.put("price",  map.get("price"));
    		tempTransactionList.add(tempMap);
    	}
    	
    	//出入库信息控制
    	for(Map<String,Object> tt:tempOutInStorageList){
    		if(tt.get("id").toString().equals(map.get("id").toString())){
    			flag2 =true;
    			tt.put("num", map.get("num").toString());
    			break;
    		}
    	}
    	if(!flag2){
    		tempOutInStorageList.add(map);
    	}
    	
    	//物料数量和价格
    	for(Map<String,Object> tt:handleNumAndPriceList){
    		if(tt.get("id").toString().equals(map.get("id").toString())){
    			tt.put("num", map.get("num"));
    			tt.put("price", map.get("price"));
    			flag3 =true;
    			break;
    		}
    	}
    	if(!flag3){
    		Map<String,Object> map3 =new HashMap<String, Object>();
    		map3.put("id", map.get("id"));
    		map3.put("num", map.get("num"));
    	    map3.put("price", map.get("price"));
			
    		handleNumAndPriceList.add(map3);
    	}
    }  
    
    public void editDataListInfo() {
    	if(tempTransactionList.size()>0){
	    	getMaterialInfo();
	    	for(Map<String,Object> tt: materialInfoList){
	    		if(selectedIndex.equals(tt.get("index").toString())){
	    			Double rec =0.0;
	    			if(null ==tt.get("receivedNum") ||tt.get("receivedNum").toString() ==""){
	    				rec=0.0;
	    			}else{
	    				rec = Double.parseDouble(tt.get("receivedNum").toString());
	    			}
	    			//计算价格和数量
	    			for(Map<String,Object> mm:handleNumAndPriceList){
	    				if(null != mm.get("num") &&mm.get("num").toString() !="")
	    				{
	    					outInNum +=Double.parseDouble(mm.get("num").toString());
	    				}
	    				if(null != mm.get("num") 
	    						&&mm.get("num").toString() !=""
	    						&& null != mm.get("price").toString()
	    						&&mm.get("price").toString() !=""){
	    					materialTotalValue
	    					+=Double.parseDouble(mm.get("price").toString())*Double.parseDouble(mm.get("num").toString());
	    				}
	    			}
	    			
	    			tt.put("receivedNum", outInNum+rec);
	    			tt.put("materialTotalValue", materialTotalValue);
	    		}
	    	}
	    	
	    	for(Map<String,Object> tt:tempTransactionList){//事务
	    		Boolean flag =false;
	    		for(Map<String,Object> tt2:transactionList){
	    			if(tt2.get("id").toString().equals(tt.get("id").toString())){
	    				flag =true;
	    				tt.put("num", tt2.get("num").toString());
	    				break;
	    			}
	    		}
	    		if(!flag){
	    			transactionList.add(tt);
	    		}
	    	}
	    	
	    	for(Map<String,Object> tt:tempOutInStorageList){//出入库信息控制
	    		Boolean flag =false;
	    		for(Map<String,Object> tt2:outInStorageList){
	    			if(tt2.get("id").toString().equals(tt.get("id").toString())){
	    				flag =true;
	    				tt.put("num", tt2.get("num").toString());
	    				break;
	    			}
	    		}
	    		if(!flag){
	    			outInStorageList.add(tt);
	    		}
	    	}
    	}
	}
    
    public void saveInfo() {
    	success ="";
    	//判断数据是否符合条件
    	for(Map<String,Object> tt: materialInfoList){
    		Double req =Double.parseDouble(tt.get("requirementNum").toString());
    		Double rec =Double.parseDouble(tt.get("receivedNum").toString());
    		if(rec>req ||rec<0.0){
    			success ="物料数量不正确，请确认";
        		return;
    		}
    	}
    	if(outInStorageList.size()<1){
    		success ="请分配";
    		return;
    	}
		success = iStorageManageService.saveInfo(materialInfoList,outInStorageList, transactionList, transActivity,
				transUser,transTypeId,transDate,jobPlanId);
	}
    
   
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getNodeid() {
		return nodeid;
	}

	public List<Map<String, Object>> getJobPlanList() {
		return jobPlanList;
	}

	public void setJobPlanList(List<Map<String, Object>> jobPlanList) {
		this.jobPlanList = jobPlanList;
	}

	public List<Map<String, Object>> getMaterialInfoList() {
		return materialInfoList;
	}

	public void setMaterialInfoList(List<Map<String, Object>> materialInfoList) {
		this.materialInfoList = materialInfoList;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getJobPlanId() {
		return jobPlanId;
	}

	public void setJobPlanId(String jobPlanId) {
		this.jobPlanId = jobPlanId;
	}

	public List<Map<String, Object>> getMaterialStorageList() {
		return materialStorageList;
	}

	public void setMaterialStorageList(List<Map<String, Object>> materialStorageList) {
		this.materialStorageList = materialStorageList;
	}

	public List<Map<String, Object>> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Map<String, Object>> transactionList) {
		this.transactionList = transactionList;
	}

	public List<Map<String, Object>> getOutInStorageList() {
		return outInStorageList;
	}

	public void setOutInStorageList(List<Map<String, Object>> outInStorageList) {
		this.outInStorageList = outInStorageList;
	}

	public Double getOutInNum() {
		return outInNum;
	}

	public void setOutInNum(Double outInNum) {
		this.outInNum = outInNum;
	}

	public String getSelectedMaterialId() {
		return selectedMaterialId;
	}

	public void setSelectedMaterialId(String selectedMaterialId) {
		this.selectedMaterialId = selectedMaterialId;
	}

	public String getSelectedProcessOrder() {
		return selectedProcessOrder;
	}

	public void setSelectedProcessOrder(String selectedProcessOrder) {
		this.selectedProcessOrder = selectedProcessOrder;
	}
	
	public String getTrantionType() {
		return trantionType;
	}

	public void setTrantionType(String trantionType) {
		this.trantionType = trantionType;
	}
	
	public String getSelectedPrice() {
		return selectedPrice;
	}

	public void setSelectedPrice(String selectedPrice) {
		this.selectedPrice = selectedPrice;
	}

	public int getTransNo() {
		return transNo;
	}

	public void setTransNo(int transNo) {
		this.transNo = transNo;
	}

	public Boolean getBatchCtrl() {
		return batchCtrl;
	}

	public void setBatchCtrl(Boolean batchCtrl) {
		this.batchCtrl = batchCtrl;
	}

	public Boolean getSeqCtrl() {
		return seqCtrl;
	}

	public void setSeqCtrl(Boolean seqCtrl) {
		this.seqCtrl = seqCtrl;
	}

	public Boolean getPositionCtrl() {
		return positionCtrl;
	}

	public void setPositionCtrl(Boolean positionCtrl) {
		this.positionCtrl = positionCtrl;
	}
	
}
