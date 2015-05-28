package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.model.MaterialPositionDataModel;
import smtcl.mocs.model.TMaterialPositionModel;
import smtcl.mocs.pojos.storage.TMaterielPositionInfo;
import smtcl.mocs.services.storage.IStorageManageService;
import smtcl.mocs.utils.device.StringUtils;
/**
 * 货位管理bean
 * 创建时间 2014-08-26
 * 作者 FW
 * 修改者
 * 修改时间
 */
@ManagedBean(name="MaterielPositionManage")
@ViewScoped
public class MaterielPositionManageBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 货位集合List
	 */
	List<TMaterialPositionModel> materialPositionList = new ArrayList<TMaterialPositionModel>();
	private String success;
	/**
	 * 库房集合List
	 */
	List<Map<String,Object>> storageList = new ArrayList<Map<String,Object>>();
	/**
	 * 多行选择model
	 */
	private MaterialPositionDataModel data;
	/**
	 * 选中的行
	 */
	private TMaterialPositionModel[] selectedMaterial;
	/**
	 * 判断是否选中
	 */
	private String selected;
	
	/**
	 * 数量单位List
	 */
	private List<Map<String,Object>> quantityUnitList = new ArrayList<Map<String,Object>>();
	/**
	 * 体积单位
	 */
	private List<Map<String,Object>> capacityUnitList = new ArrayList<Map<String,Object>>();
	/**
	 * 重量单位
	 */
	private List<Map<String,Object>> weightUnitList = new ArrayList<Map<String,Object>>();
	/**
	 * 维度单位
	 */
	private List<Map<String,Object>> dimensionUnitList = new ArrayList<Map<String,Object>>();
	/**
	 * 节点
	 */
	String nodeid;
	/**
	 * 库房Id
	 */
	private String storageId;
	/**
	 * 货位ID
	 */
	private String id;
	/**
	 * 货位No
	 */
	private String no;
	/**
	 * 货位说明
	 */
	private String name;
	/**
	 * 货位状态
	 */
	private String status;
	/**
	 * 能力数量大小
	 */
	private String quantitySize;
	/**
	 * 数量基本单位
	 */
	private String quantityUnit;
	/**
	 * 能力体积大小
	 */
	private String capacitySize;
	/**
	 * 体积基本单位
	 */
	private String capacityUnit;
	/**
	 * 能力重量大小
	 */
	private String weightSize;
	/**
	 * 重量基本单位
	 */
	private String weightUnit;
	/**
	 * 维度大小
	 */
	private String dimensionSize;
	/**
	 * 维度单位
	 */
	private String dimensionUnit;
	/**
	 * 坐标
	 */
	private String axis;
	/**
	 * 条件
	 */
	private String query;
	
	private IStorageManageService iStorageManageService=(IStorageManageService)ServiceFactory.getBean("storageManage");
	
	public MaterielPositionManageBean() {
		//获取节点ID
		//HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    //nodeid = (String)session.getAttribute("nodeid");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	nodeid=session.getAttribute("nodeid2")+"";
	    queryData();
		
	    //获取库房信息List
	    storageList = iStorageManageService.storgeList(nodeid);
	    
	    
	    //获取各种单位
	    quantityUnitList = iStorageManageService.quantityUnitList(nodeid);//数量单位
	    capacityUnitList = iStorageManageService.capacityUnitList(nodeid);//体积单位
	    weightUnitList = iStorageManageService.weightUnitList(nodeid);//重量单位
	    dimensionUnitList = iStorageManageService.dimensionUnitList(nodeid);//维度单位
	}
	
	/**
	 *  查询方法
	 */
	public void queryData(){
		try {
			if("输入编号/名称".equals(query))
				query=null;
			//获取货位信息
		    materialPositionList = iStorageManageService.getMaterialPositionList(query,nodeid);
		    data =new MaterialPositionDataModel(materialPositionList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 库位信息保存
	 */
	public void saveMaterialPositionInfo(){
		//判断信息是否存在
		List<Map<String,Object>> list = iStorageManageService.getList("select new Map( "
				+ "m.positionNo as no) "
				+ "from TMaterielPositionInfo m where m.positionNo ='"+no+"' ");
		if(list.size()>0){
			success ="库位编号已存在，请确认";
			return;
		}
		//设置值
		TMaterielPositionInfo info = new TMaterielPositionInfo();
		info.setPositionNo(no);
		info.setPositionName(name);
		info.setPositionStatus(status);
		if(!StringUtils.isEmpty(quantitySize)){
		   info.setQuantitySize(Double.parseDouble(quantitySize));
		}
		info.setQuantityUnit(quantityUnit);
		if(!StringUtils.isEmpty(capacitySize)){
		    info.setCapacitySize(Double.parseDouble(capacitySize));
		}
		info.setCapacityUnit(capacityUnit);
		if(!StringUtils.isEmpty(weightSize)){
		   info.setWeightSize(Double.parseDouble(weightSize));
		}
		info.setWeightUnit(weightUnit);
		info.setDimensionSize(dimensionSize);
		info.setDimensionUnit(dimensionUnit);
		info.setAxis(axis);
		success = iStorageManageService.updateMaterialPositionInfo(info, 1,storageId);
		if(success.equals("成功")){
			queryData();
		}
		
	}
	/**
	 * 更新方法
	 * @param event
	 */
    public void onEdit(RowEditEvent event) {  
    	TMaterialPositionModel info=(TMaterialPositionModel) event.getObject();
          if("2222".equals("请选择")){
        	  FacesMessage msg = new FacesMessage("库位更新","更新失败,请选择一个属性！");  
     	      FacesContext.getCurrentInstance().addMessage(null, msg);  
          }else{
        	  
		    //设置值
        	  TMaterielPositionInfo materialPositionInfo = new TMaterielPositionInfo();
        	  materialPositionInfo.setId(info.getId());
        	  materialPositionInfo.setPositionNo(info.getPositionNo());
        	  materialPositionInfo.setPositionName(info.getPositionName());
        	  materialPositionInfo.setPositionStatus(info.getPositionStatus());
        	  materialPositionInfo.setQuantitySize(info.getQuantitySize());
        	  materialPositionInfo.setQuantityUnit(info.getQuantityUnit());
        	  materialPositionInfo.setCapacitySize(info.getCapacitySize());
        	  materialPositionInfo.setCapacityUnit(info.getCapacityUnit());
        	  materialPositionInfo.setWeightSize(info.getWeightSize());
        	  materialPositionInfo.setWeightUnit(info.getWeightUnit());
        	  materialPositionInfo.setDimensionSize(info.getDimensionSize());
        	  materialPositionInfo.setDimensionUnit(info.getDimensionUnit());
        	  materialPositionInfo.setAxis(info.getAxis());
        	  
    	     String tt=iStorageManageService.updateMaterialPositionInfo(materialPositionInfo, 2,info.getSid().toString());
		     if(tt.equals("成功")){
	 		   FacesMessage msg = new FacesMessage("库位更新","更新成功");  
	 	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	     	 }else{
	     		FacesMessage msg = new FacesMessage("库位更新","更新失败"); 
	     		FacesContext.getCurrentInstance().addMessage(null, msg);  
	     	 }
          }
          queryData();
    }  
	
	 /**
     * 取消
     * @param event
     */
    public void onCancel(RowEditEvent event) {  
    	
    }
    
    public void onSelected(){
    	for(TMaterialPositionModel tt:selectedMaterial){
    		selected=tt.getId().toString();
    	}
    }
    
    /**
     * 删除新方法
     */
    public void onDelete(){
    	for(TMaterialPositionModel tt:selectedMaterial){
    		//设置值
    		TMaterielPositionInfo info = new TMaterielPositionInfo();
    		info.setId(tt.getId());
    		iStorageManageService.delMaterielPositionInfo(info);
    	}
    	queryData();
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

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQuantitySize() {
		
		return quantitySize;
	}

	public void setQuantitySize(String quantitySize) {
		this.quantitySize = quantitySize;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		 quantityUnitList = iStorageManageService.quantityUnitList(nodeid);//数量单位
		this.quantityUnit = quantityUnit;
	}

	public String getCapacitySize() {
		return capacitySize;
	}

	public void setCapacitySize(String capacitySize) {
		this.capacitySize = capacitySize;
	}

	public String getCapacityUnit() {
		return capacityUnit;
	}

	public void setCapacityUnit(String capacityUnit) {
		 capacityUnitList = iStorageManageService.capacityUnitList(nodeid);//体积单位
		this.capacityUnit = capacityUnit;
	}

	public String getWeightSize() {
		return weightSize;
	}

	public void setWeightSize(String weightSize) {
		this.weightSize = weightSize;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		 weightUnitList = iStorageManageService.weightUnitList(nodeid);//重量单位
		this.weightUnit = weightUnit;
	}

	public String getDimensionSize() {
		return dimensionSize;
	}

	public void setDimensionSize(String dimensionSize) {
		this.dimensionSize = dimensionSize;
	}

	public String getDimensionUnit() {
		return dimensionUnit;
	}

	public void setDimensionUnit(String dimensionUnit) {
		dimensionUnitList = iStorageManageService.dimensionUnitList(nodeid);//维度单位
		this.dimensionUnit = dimensionUnit;
	}

	public String getAxis() {
		return axis;
	}

	public void setAxis(String axis) {
		this.axis = axis;
	}

	public MaterialPositionDataModel getData() {
		return data;
	}

	public void setData(MaterialPositionDataModel data) {
		this.data = data;
	}

	public TMaterialPositionModel[] getSelectedMaterial() {
		return selectedMaterial;
	}

	public void setSelectedMaterial(TMaterialPositionModel[] selectedMaterial) {
		this.selectedMaterial = selectedMaterial;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public List<TMaterialPositionModel> getMaterialPositionList() {
		return materialPositionList;
	}

	public void setMaterialPositionList(
			List<TMaterialPositionModel> materialPositionList) {
		this.materialPositionList = materialPositionList;
	}

	public List<Map<String, Object>> getStorageList() {
		return storageList;
	}

	public void setStorageList(List<Map<String, Object>> storageList) {
		this.storageList = storageList;
	}

	public String getQuery() {
		
		return query;
	}

	public void setQuery(String query) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	nodeid=session.getAttribute("nodeid2")+"";
    	queryData();
		 //获取库房信息List
	    storageList = iStorageManageService.storgeList(nodeid);
		this.query = query;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public List<Map<String, Object>> getQuantityUnitList() {
		return quantityUnitList;
	}

	public void setQuantityUnitList(List<Map<String, Object>> quantityUnitList) {
		this.quantityUnitList = quantityUnitList;
	}

	public List<Map<String, Object>> getCapacityUnitList() {
		return capacityUnitList;
	}

	public void setCapacityUnitList(List<Map<String, Object>> capacityUnitList) {
		this.capacityUnitList = capacityUnitList;
	}

	public List<Map<String, Object>> getWeightUnitList() {
		return weightUnitList;
	}

	public void setWeightUnitList(List<Map<String, Object>> weightUnitList) {
		this.weightUnitList = weightUnitList;
	}

	public List<Map<String, Object>> getDimensionUnitList() {
		return dimensionUnitList;
	}

	public void setDimensionUnitList(List<Map<String, Object>> dimensionUnitList) {
		this.dimensionUnitList = dimensionUnitList;
	}
	
	
	
}
