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
 * �豸�ɱ�ά��Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-08-06 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="EquipmentCost")
@ViewScoped
public class EquipmentCostBean implements Serializable {
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IEquipmentService equipmentService = (IEquipmentService)ServiceFactory.getBean("equipmentService");
	/**
	 * �豸dataTable���� 
	 */
	private List<Map<String, Object>> equlist;
	/**
	 * �豸dataTable����  ����װ
	 */
	private TEquipmentCostInfoDataModel mediumEquipmentCostInfoModel = new TEquipmentCostInfoDataModel();	
	/**
	 * �豸ѡ�е���
	 */
	private Map<String, Object>[] selectedEquipment; 
	/**
	 * ����ID
	 */
	private String roomId;
	/**
	 * ���伯��
	 */
	private Map<String, Object> roomMap = new CopyOnWriteMap<String, Object>();
	/**
	 * �豸����ID
	 */
	private String equTypeId;
	/**
	 * �豸���ͼ���
	 */
	private Map<String, Object> equTypeMap = new CopyOnWriteMap<String, Object>();
	/**
	 * �豸ID
	 */
	private String equId;
	/**
	 * �豸����
	 */
	private Map<String, Object> equMap = new CopyOnWriteMap<String, Object>();
	/**
	 * �豸����
	 */
	private String code;
	/**
	 * �豸Id����Ϊ���
	 */
	private Map<String, Object> equIdMap = new CopyOnWriteMap<String, Object>();
	/**
	 * �豸���뼯��
	 */
	private Map<String, Object> codeMap = new CopyOnWriteMap<String, Object>();
	/**
	 * �豸�ɱ��趨����ʾMAP
	 */
	private TEquipmentCostInfo equCostObj = new TEquipmentCostInfo();
	/**
	 * ���캯��
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
	 * ��ѯ
	 */
	public void searchList(){		
		System.out.println("equTypeId---1---->"+equTypeId);
		System.out.println("equId-------2---->"+equId);
		System.out.println("code--------3---->"+code);
		equlist  = equipmentService.getEquipmentCostList(roomId,equTypeId,equId,code);
		mediumEquipmentCostInfoModel = new TEquipmentCostInfoDataModel(equlist);
	}
	/**
	 * ���·���
	 * @param event
	 */
    public void onEdit(RowEditEvent event) {  
    	System.out.println("����----------->");
//    	Map<String, Object> map=(Map<String, Object>) event.getObject();        
    	equipmentService.updateEquCost(this);
    }
    
    /**
     * ȡ��
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }
    /**
     * ��ӵ�����ʾ��
     */
    private String dialog;
    /**
     * ���
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
    		System.out.println("�Ѿ����ڣ���Ҫ�ظ����----------->");  
    	}
    }
    
    /**
     * ɾ��
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
     * �ж��Ƿ�ѡ��
     */   
    public void onSelected(){
    	for(Map<String, Object> tt:selectedEquipment){
    		selected=tt.get("equSerialNo").toString();
    	}
    }
    
	/**================================set,get����=============================================**/

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
