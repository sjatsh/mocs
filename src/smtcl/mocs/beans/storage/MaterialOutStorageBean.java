package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import smtcl.mocs.services.storage.IStorageManageService;
/**
 * 物料出入库bean
 * 创建时间 2014-12-17
 * 作者 FW
 * 修改者
 * 修改时间
 */
@ManagedBean(name="MaterialOutStorage")
@ViewScoped
public class MaterialOutStorageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String success;
	/**
	 * 物料集合List
	 */
	List<Map<String,Object>> materialList = new ArrayList<Map<String,Object>>();
	/**
	 * 物料库存信息
	 */
	List<Map<String,Object>> materialInStorageList = new ArrayList<Map<String,Object>>();
	
	
	/**
	 * 节点
	 */
	String nodeid;
	/**
	 * 来源
	 */
	String source;
	/**
	 * 物料ID
	 */
	String materialId;
	/**
	 * 物料编码
	 */
	String materialNo;
	/**
	 * 物料名称
	 */
	String materialName;
	/**
	 * 库存数量
	 */
	Double inStorageNum;
	/**
	 * 批次控制
	 */
	Boolean batchCtr;
	/**
	 * 序列控制
	 */
	Boolean seqCtr;
	/**
     * 货位控制
     */
	Boolean positionCtrl;
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
	
	private IStorageManageService iStorageManageService=(IStorageManageService)ServiceFactory.getBean("storageManage");
	
	public MaterialOutStorageBean() {
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
	    //事务No
	    String maxID = iStorageManageService.getMaxID();
	    transNo = Integer.parseInt(maxID)+1;
	    
	    //物料集合
	    materialList = iStorageManageService.getMaterialInfoList(nodeid);
	    
	}
	/**
	 * 物料
	 */
	public void getMaterialInfo() {
	    batchCtr =false;
	    seqCtr =false;
	    materialInStorageList.clear();
	    inStorageNum= 0.0;
	    
		 for(Map<String,Object> tt:materialList){
			 if(materialId.equals(tt.get("id").toString())){
				 materialNo = tt.get("no").toString();
				 materialName = tt.get("name").toString();
				 if(tt.get("isBatchCtrl").toString().equals("0")){
					 batchCtr =true;
				 }else{
					 batchCtr =false;
				 }
				 
				 if(tt.get("isSeqCtrl").toString().equals("0")){
					 seqCtr =true;
				 }else{
					 seqCtr =false;
				 }
				
				 materialInStorageList = iStorageManageService.materialStorageList(
						 materialId, tt.get("isBatchCtrl").toString(), tt.get("isSeqCtrl").toString());
				 
				 for(Map<String,Object> map:materialInStorageList){
					 if(null!= map.get("availableNum")){
						 inStorageNum += Double.valueOf(map.get("availableNum").toString());
					 }
					 map.put("num", "0");
				 }
			 }
		 }
		 
	}
	
	/**
	 * 杂发出库
	 * @return
	 */
	public void saveDataInfo() {
		success ="";
		if(materialInStorageList.size()>0){
			success = iStorageManageService.
					saveMaterialOutStorage(materialInStorageList, String.valueOf(transNo), materialId, batchCtr, seqCtr, source, transUser, transTypeId, transDate);
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
    		 FacesMessage msg = new FacesMessage("出库","更新失败,请确认！");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg); 
    	     
    	     return;
    	}
    	
        if(Double.parseDouble(map.get("num").toString())>Double.parseDouble(map.get("availableNum").toString())){
    			FacesMessage msg = new FacesMessage("出库","出库数量不正确,请确认！");  
       	        FacesContext.getCurrentInstance().addMessage(null, msg); 
       	        map.put("num", "0");
       	        return;
    	}
    }  

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	
	public List<Map<String, Object>> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Map<String, Object>> materialList) {
		this.materialList = materialList;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
    
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Double getInStorageNum() {
		return inStorageNum;
	}
	public void setInStorageNum(Double inStorageNum) {
		this.inStorageNum = inStorageNum;
	}
	public void setBatchCtr(Boolean batchCtr) {
		this.batchCtr = batchCtr;
	}

	public Boolean getSeqCtr() {
		return seqCtr;
	}

	public void setSeqCtr(Boolean seqCtr) {
		this.seqCtr = seqCtr;
	}
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	public Boolean getPositionCtrl() {
		return positionCtrl;
	}

	public void setPositionCtrl(Boolean positionCtrl) {
		this.positionCtrl = positionCtrl;
	}

	public int getTransNo() {
		return transNo;
	}

	public void setTransNo(int transNo) {
		this.transNo = transNo;
	}
	public List<Map<String, Object>> getMaterialInStorageList() {
		return materialInStorageList;
	}
	public void setMaterialInStorageList(
			List<Map<String, Object>> materialInStorageList) {
		this.materialInStorageList = materialInStorageList;
	}
	public Boolean getBatchCtr() {
		return batchCtr;
	}
	
}
