package smtcl.mocs.beans.equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.mina.util.CopyOnWriteMap;
import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.pojos.job.TEquipmentCostInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.jobplan.IEquipmentService;

/**
 * 
 * 设备成本维护Bean
 * @作者：yyh
 * @创建时间：2013-08-06 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="EquipmentCost")
@ViewScoped
public class EquipmentCostBean implements Serializable {
	
	/**
	 * 作业计划接口实例
	 */
	private IEquipmentService equipmentService = (IEquipmentService)ServiceFactory.getBean("equipmentService");
	/**
	 * 设备dataTable数据 
	 */
	private List<Map<String, Object>> equlist;
	/**
	 * 设备dataTable数据  外层封装
	 */
	private TEquipmentCostInfoDataModel mediumEquipmentCostInfoModel = new TEquipmentCostInfoDataModel();	
	/**
	 * 设备选中的行
	 */
	private Map<String, Object>[] selectedEquipment; 
	/**
	 * 车间ID
	 */
	private String roomId;
	/**
	 * 车间集合
	 */
	private Map<String, Object> roomMap = new CopyOnWriteMap<String, Object>();
	/**
	 * 设备类型ID
	 */
	private String equTypeId;
	/**
	 * 设备类型集合
	 */
	private Map<String, Object> equTypeMap = new CopyOnWriteMap<String, Object>();
	/**
	 * 设备ID
	 */
	private String equId;
	/**
	 * 设备集合
	 */
	private Map<String, Object> equMap = new CopyOnWriteMap<String, Object>();
	/**
	 * 设备编码
	 */
	private String code;
	/**
	 * 设备Id集合为添加
	 */
	private Map<String, Object> equIdMap = new CopyOnWriteMap<String, Object>();
	/**
	 * 设备编码集合
	 */
	private Map<String, Object> codeMap = new CopyOnWriteMap<String, Object>();
	/**
	 * 设备成本设定的显示MAP
	 */
	private TEquipmentCostInfo equCostObj = new TEquipmentCostInfo();
	/**
	 * 构造函数
	 */
	public EquipmentCostBean(){
		List<Map<String, Object>> lst1 = equipmentService.getEquTypeMap();
		for(Map<String, Object> map : lst1){
			if(map.get("id")!=null && !"".equals(map.get("id"))){
				equTypeMap.put((String)map.get("equipmentType"),map.get("id").toString());
			}
		}
		List<Map<String, Object>> lst2 = equipmentService.getEquMap();
		for(Map<String, Object> map : lst2){
			if(map.get("equId")!=null && !"".equals(map.get("equId"))){
				equMap.put((String)map.get("equName"),map.get("equId").toString());
				equIdMap.put((String)map.get("equName"),map.get("equSerialNo").toString());
			}
		}
		List<Map<String, Object>> lst3 = equipmentService.getEquCodeMap();
		for(Map<String, Object> map : lst3){
			if(map.get("equSerialNo")!=null && !"".equals(map.get("equSerialNo"))){
				codeMap.put((String)map.get("equSerialNo"),map.get("equSerialNo").toString());
			}
		}
		
	}
	
	
	/**
	 * 查询
	 */
	public void searchList(){		
		System.out.println("equTypeId---1---->"+equTypeId);
		System.out.println("equId-------2---->"+equId);
		System.out.println("code--------3---->"+code);
		equlist  = equipmentService.getEquipmentCostList(roomId,equTypeId,equId,code);
		mediumEquipmentCostInfoModel = new TEquipmentCostInfoDataModel(equlist);
	}
	/**
	 * 更新方法
	 * @param event
	 */
    public void onEdit(RowEditEvent event) {  
    	System.out.println("更新----------->");
//    	Map<String, Object> map=(Map<String, Object>) event.getObject();        
    	equipmentService.updateEquCost(this);
    }
    
    /**
     * 取消
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }
    /**
     * 添加弹出提示框
     */
    private String dialog;
    /**
     * 添加
     */
    public void addEquCost(){
    	dialog = null;
    	List<Map<String, Object>> lst  = equipmentService.getEquCostRepeat(equCostObj.getEquSerialNo());
    	if(lst.size()==0){
    	    equipmentService.addEquCost(this);
    		equlist  = equipmentService.getEquipmentCostList(roomId,equTypeId,equId,code);
    		mediumEquipmentCostInfoModel = new TEquipmentCostInfoDataModel(equlist);
    	}else{
    		dialog = "show";
    		System.out.println("已经存在，不要重复添加----------->");  
    	}
    }
    
    /**
     * 删除
     */
    public void onDelete(){
    	System.out.println("selectedEquipment.length----------->"+selectedEquipment.length);   		
    		equipmentService.delEquCost(this);
    	try {
    		equlist  = equipmentService.getEquipmentCostList(roomId,equTypeId,equId,code);
    		mediumEquipmentCostInfoModel = new TEquipmentCostInfoDataModel(equlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    
    private String selected;
    /**
     * 判断是否选中
     */   
    public void onSelected(){
    	for(Map<String, Object> tt:selectedEquipment){
    		selected=tt.get("equSerialNo").toString();
    	}
    }
    
	/**================================set,get方法=============================================**/

	public IEquipmentService getEquipmentService() {
		return equipmentService;
	}


	public void setEquipmentService(IEquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}


	public List<Map<String, Object>> getEqulist() {			
		return equlist;
	}


	public void setEqulist(List<Map<String, Object>> equlist) {
		this.equlist = equlist;
	}

	public TEquipmentCostInfoDataModel getMediumEquipmentCostInfoModel() {
		if(equlist==null){
		System.out.println("equTypeId------->"+equTypeId);
		System.out.println("equId----------->"+equId);
		System.out.println("code------------>"+code);
		equlist  = equipmentService.getEquipmentCostList(roomId,equTypeId,equId,code);
		mediumEquipmentCostInfoModel = new TEquipmentCostInfoDataModel(equlist);
		}
		return mediumEquipmentCostInfoModel;
	}


	public void setMediumEquipmentCostInfoModel(
			TEquipmentCostInfoDataModel mediumEquipmentCostInfoModel) {
		this.mediumEquipmentCostInfoModel = mediumEquipmentCostInfoModel;
	}


	public Map<String, Object>[] getSelectedEquipment() {
		return selectedEquipment;
	}


	public void setSelectedEquipment(Map<String, Object>[] selectedEquipment) {
		this.selectedEquipment = selectedEquipment;
	}


	public String getRoomId() {
		return roomId;
	}


	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}


	public Map<String, Object> getRoomMap() {
		return roomMap;
	}


	public void setRoomMap(Map<String, Object> roomMap) {
		this.roomMap = roomMap;
	}


	public String getEquTypeId() {
		return equTypeId;
	}


	public void setEquTypeId(String equTypeId) {
		this.equTypeId = equTypeId;
	}


	public Map<String, Object> getEquTypeMap() {
		return equTypeMap;
	}


	public void setEquTypeMap(Map<String, Object> equTypeMap) {
		this.equTypeMap = equTypeMap;
	}


	public String getEquId() {
		return equId;
	}


	public void setEquId(String equId) {
		this.equId = equId;
	}


	public Map<String, Object> getEquMap() {
		return equMap;
	}


	public void setEquMap(Map<String, Object> equMap) {
		this.equMap = equMap;
	}


	public Map<String, Object> getCodeMap() {
		return codeMap;
	}


	public void setCodeMap(Map<String, Object> codeMap) {
		this.codeMap = codeMap;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TEquipmentCostInfo getEquCostObj() {
		return equCostObj;
	}

	public void setEquCostObj(TEquipmentCostInfo equCostObj) {
		this.equCostObj = equCostObj;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public Map<String, Object> getEquIdMap() {
		return equIdMap;
	}

	public void setEquIdMap(Map<String, Object> equIdMap) {
		this.equIdMap = equIdMap;
	}

	public String getDialog() {
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}
	
    

}
