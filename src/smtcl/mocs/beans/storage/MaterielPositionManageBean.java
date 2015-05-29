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
 * ��λ����bean
 * ����ʱ�� 2014-08-26
 * ���� FW
 * �޸���
 * �޸�ʱ��
 */
@ManagedBean(name="MaterielPositionManage")
@ViewScoped
public class MaterielPositionManageBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ��λ����List
	 */
	List<TMaterialPositionModel> materialPositionList = new ArrayList<TMaterialPositionModel>();
	private String success;
	/**
	 * �ⷿ����List
	 */
	List<Map<String,Object>> storageList = new ArrayList<Map<String,Object>>();
	/**
	 * ����ѡ��model
	 */
	private MaterialPositionDataModel data;
	/**
	 * ѡ�е���
	 */
	private TMaterialPositionModel[] selectedMaterial;
	/**
	 * �ж��Ƿ�ѡ��
	 */
	private String selected;
	
	/**
	 * ������λList
	 */
	private List<Map<String,Object>> quantityUnitList = new ArrayList<Map<String,Object>>();
	/**
	 * �����λ
	 */
	private List<Map<String,Object>> capacityUnitList = new ArrayList<Map<String,Object>>();
	/**
	 * ������λ
	 */
	private List<Map<String,Object>> weightUnitList = new ArrayList<Map<String,Object>>();
	/**
	 * ά�ȵ�λ
	 */
	private List<Map<String,Object>> dimensionUnitList = new ArrayList<Map<String,Object>>();
	/**
	 * �ڵ�
	 */
	String nodeid;
	/**
	 * �ⷿId
	 */
	private String storageId;
	/**
	 * ��λID
	 */
	private String id;
	/**
	 * ��λNo
	 */
	private String no;
	/**
	 * ��λ˵��
	 */
	private String name;
	/**
	 * ��λ״̬
	 */
	private String status;
	/**
	 * ����������С
	 */
	private String quantitySize;
	/**
	 * ����������λ
	 */
	private String quantityUnit;
	/**
	 * ���������С
	 */
	private String capacitySize;
	/**
	 * ���������λ
	 */
	private String capacityUnit;
	/**
	 * ����������С
	 */
	private String weightSize;
	/**
	 * ����������λ
	 */
	private String weightUnit;
	/**
	 * ά�ȴ�С
	 */
	private String dimensionSize;
	/**
	 * ά�ȵ�λ
	 */
	private String dimensionUnit;
	/**
	 * ����
	 */
	private String axis;
	/**
	 * ����
	 */
	private String query;
	
	private IStorageManageService iStorageManageService=(IStorageManageService)ServiceFactory.getBean("storageManage");
	
	public MaterielPositionManageBean() {
		//��ȡ�ڵ�ID
		//HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    //nodeid = (String)session.getAttribute("nodeid");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	nodeid=session.getAttribute("nodeid2")+"";
	    queryData();
		
	    //��ȡ�ⷿ��ϢList
	    storageList = iStorageManageService.storgeList(nodeid);
	    
	    
	    //��ȡ���ֵ�λ
	    quantityUnitList = iStorageManageService.quantityUnitList(nodeid);//������λ
	    capacityUnitList = iStorageManageService.capacityUnitList(nodeid);//�����λ
	    weightUnitList = iStorageManageService.weightUnitList(nodeid);//������λ
	    dimensionUnitList = iStorageManageService.dimensionUnitList(nodeid);//ά�ȵ�λ
	}
	
	/**
	 *  ��ѯ����
	 */
	public void queryData(){
		try {
			if("������/����".equals(query))
				query=null;
			//��ȡ��λ��Ϣ
		    materialPositionList = iStorageManageService.getMaterialPositionList(query,nodeid);
		    data =new MaterialPositionDataModel(materialPositionList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��λ��Ϣ����
	 */
	public void saveMaterialPositionInfo(){
		//�ж���Ϣ�Ƿ����
		List<Map<String,Object>> list = iStorageManageService.getList("select new Map( "
				+ "m.positionNo as no) "
				+ "from TMaterielPositionInfo m where m.positionNo ='"+no+"' ");
		if(list.size()>0){
			success ="��λ����Ѵ��ڣ���ȷ��";
			return;
		}
		//����ֵ
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
		if(success.equals("�ɹ�")){
			queryData();
		}
		
	}
	/**
	 * ���·���
	 * @param event
	 */
    public void onEdit(RowEditEvent event) {  
    	TMaterialPositionModel info=(TMaterialPositionModel) event.getObject();
          if("2222".equals("��ѡ��")){
        	  FacesMessage msg = new FacesMessage("��λ����","����ʧ��,��ѡ��һ�����ԣ�");  
     	      FacesContext.getCurrentInstance().addMessage(null, msg);  
          }else{
        	  
		    //����ֵ
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
		     if(tt.equals("�ɹ�")){
	 		   FacesMessage msg = new FacesMessage("��λ����","���³ɹ�");  
	 	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	     	 }else{
	     		FacesMessage msg = new FacesMessage("��λ����","����ʧ��"); 
	     		FacesContext.getCurrentInstance().addMessage(null, msg);  
	     	 }
          }
          queryData();
    }  
	
	 /**
     * ȡ��
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
     * ɾ���·���
     */
    public void onDelete(){
    	for(TMaterialPositionModel tt:selectedMaterial){
    		//����ֵ
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
		 quantityUnitList = iStorageManageService.quantityUnitList(nodeid);//������λ
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
		 capacityUnitList = iStorageManageService.capacityUnitList(nodeid);//�����λ
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
		 weightUnitList = iStorageManageService.weightUnitList(nodeid);//������λ
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
		dimensionUnitList = iStorageManageService.dimensionUnitList(nodeid);//ά�ȵ�λ
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
		 //��ȡ�ⷿ��ϢList
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
