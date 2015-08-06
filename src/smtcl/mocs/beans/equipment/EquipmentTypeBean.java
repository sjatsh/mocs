package smtcl.mocs.beans.equipment;

import java.io.Serializable;
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
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.services.jobplan.IEquipmentService;
import smtcl.mocs.utils.device.ExcelUtils;

/**
 * 
 * �豸����ά��Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-08-06 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="EquipmentType")
@ViewScoped
public class EquipmentTypeBean implements Serializable {
	
	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IEquipmentService equipmentService = (IEquipmentService)ServiceFactory.getBean("equipmentService");
	private String pid;
	/**
	 * �޸����
	 */
	private String eqmType;
	/**
	 * �б���
	 */
	private String num;
	/**
	 * �豸dataTable���� 
	 */
	private List<Map<String, Object>> equlist = new ArrayList<Map<String, Object>>();
	/**
	 * �豸dataTable����  ����װ
	 */
	private TEquipmentTypeInfoDataModel mediumEquipmentTypeInfoModel = new TEquipmentTypeInfoDataModel();	
	/**
	 * �豸ѡ�е���
	 */
	private Map<String, Object>[] selectedEquipment; 	
	/**
	 * �豸����ID
	 */
	private String equipmentTypeId;
	
	/**
	 * ���������
	 */
	private TreeNode root;  
	/**
	 * ��ǰѡ�����ڵ�
	 */
	private TreeNode treeSelectedEqu; 
	private String pnodeid;
	/**
	 *��ӵĶ��� 
	 */
	private TEquipmenttypeInfo equTypeObj = new TEquipmenttypeInfo();
	/**
	 * ����ǵĸ��豸�������
	 */
	private Map<String, Object> equTypeMap = new HashMap<String, Object>();
	
	/**
	 *�ݹ�
	 */
	public void getTreeNode(TEquipmenttypeInfo te,TreeNode node,String nodeid){
		TreeNode root = new DefaultTreeNode(te, node);
		root.setExpanded(true);
		for(TEquipmenttypeInfo tei:te.getTEquipmenttypeInfos()){
			if(null!=tei.getNodeid()&&tei.getNodeid().toString().equals(nodeid)&&!tei.getIsdel().equals("1")){
				getTreeNode(tei, root, nodeid);
			}
		}
	}
	/**
	 * ����
	 */
	public EquipmentTypeBean(){
		//�����
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
	
        root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);   
        TEquipmenttypeInfo lstx = equipmentService.getgetEquTypeIdById("-999");
        getTreeNode(lstx, root, nodeid);
        
        //���ʱ������
		List<Map<String, Object>>  lst2 = equipmentService.getParentIdMap();
		for(Map<String, Object> map : lst2){
			 if(map.get("id")!=null && !"".equals(map.get("id"))){
			   equTypeMap.put((String)map.get("equipmentType"), map.get("id").toString());
			 }
		}
        TEquipmenttypeInfo ti = equipmentService.getgetEquTypeIdById("-999"); //������1�����ϼ�Ĭ����0
        pid="-999";
        equTypeObj.setTEquipmenttypeInfo(ti);  ///��ǰ�ĸ��ڵ㣬�������
        
	}
	
	/**
	 * �������ѯ
	 */
    public void onNodeSelect(NodeSelectEvent event) {   
    	
    	TEquipmenttypeInfo te=(TEquipmenttypeInfo)event.getTreeNode().getData();
        String typeName = te.getEquipmentType();
        String typeId = "";
        if(typeName!=null && typeName.equals("�豸����")){
        	typeId = "-999";
        }else{
        	typeId = equipmentService.getEquTypeIdByName(typeName);
        }
        pnodeid=typeId;
        equlist = equipmentService.getEquTypeByClick(typeId);
		int num1 = equlist.size();
		num = String.valueOf(num1);
		List<Map<String, Object>>  lst = equipmentService.getParentNameById(typeId);
		if(null!=lst&&lst.size()>0){

		}else{
			equlist=new ArrayList<Map<String,Object>>();
		}
		
        mediumEquipmentTypeInfoModel = new TEquipmentTypeInfoDataModel(equlist);
        
        TEquipmenttypeInfo ti = equipmentService.getgetEquTypeIdById(typeId);
        equTypeObj.setTEquipmenttypeInfo(ti);  ///��ǰ�ĸ��ڵ㣬�������
       
    } 
	
    /**
     * ��ӵ�����ʾ��
     */
    private String dialog;
    /**
     * �豸�������
     */
    public void addEquType(){
    	List<Map<String, Object>> lst  = equipmentService.getEquTypeRepeat(equTypeObj.getTypecode());
    	if(lst.size()==0){
    	    equipmentService.addEquType(this);
    	    
    	}else{
    		dialog = "show";
    		System.out.println("�Ѿ����ڣ���Ҫ�ظ����----------->");  
    	}
    	 equlist = equipmentService.getEquTypeByClick(pnodeid);
    	 mediumEquipmentTypeInfoModel = new TEquipmentTypeInfoDataModel(equlist);
    	 HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
 		 String nodeid = (String)session.getAttribute("nodeid2");
 	
         root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);   
         TEquipmenttypeInfo lstx = equipmentService.getgetEquTypeIdById("-999");
         getTreeNode(lstx, root, nodeid);
    }
    
    /**
 	 * ���·���
	 * @param event
     */
    public void onEdit(RowEditEvent event) {  
    	System.out.println("����----------->");
//    	Map<String, Object> map=(Map<String, Object>) event.getObject();        
    	equipmentService.updateEquType(this);
    }
    
    /**
     * ȡ��
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }

    /**
     * ɾ��
     */
    public void onDelete(){
    	System.out.println("selectedEquipment.length----------->"+selectedEquipment.length);   		
    		equipmentService.delEquType(this);
    	try {
    		equlist.clear();
    		equlist = equipmentService.getEquipmentInfoAList(equipmentTypeId);
    		int num1 = equlist.size();
    		num = String.valueOf(num1);
    		mediumEquipmentTypeInfoModel = new TEquipmentTypeInfoDataModel(equlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    
    private String selected;
    /**
     * �ж��Ƿ�ѡ��
     */   
    public void onSelected(){
    	System.out.println("ѡ��----------->"); 
    	for(Map<String, Object> tt:selectedEquipment){
    		String typeId=tt.get("id").toString();
    		List<Map<String, Object>> lst  = equipmentService.getEquTypeInEquSize(typeId);
    		if(lst.size()>0){
    			selected = "use";
    		}else{
    			selected = "nouse";
    		}
    	}
    }
    
    /**
     * excel����
     */
    public void downloadExcel(){
		equlist.clear();
		equlist = equipmentService.getEquipmentInfoAList(equipmentTypeId);
    	
    	String[] firstRowValue = new String[]{
    	  "�豸����ID",
    	  "�豸��������",
    	  "�������",
    	  "�������ͺ�",
    	  "����ϵͳ",
    	  "�豸��������"
    	};
    	SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd"); //������/�ܣ��Ҳ���·��
    	Date d  = new Date();
    	String d1 = sdf.format(d);
    	new ExcelUtils().outputExcel(d1+".xls", equlist,firstRowValue);//��֧����������
    	
    }
    
	
	/**================================set,get����=============================================**/
    public TreeNode getRoot() {
		return root;
	}
	public IEquipmentService getEquipmentService() {
		return equipmentService;
	}

	public void setEquipmentService(IEquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	public String getEqmType() {
		return eqmType;
	}

	public void setEqmType(String eqmType) {
		this.eqmType = eqmType;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public List<Map<String, Object>> getEqulist() {
		
		return equlist;
	}

	public void setEqulist(List<Map<String, Object>> equlist) {
		this.equlist = equlist;
	}

	public Map<String, Object>[] getSelectedEquipment() {
		return selectedEquipment;
	}

	public void setSelectedEquipment(Map<String, Object>[] selectedEquipment) {
		this.selectedEquipment = selectedEquipment;
	}

	public String getEquipmentTypeId() {
		return equipmentTypeId;
	}

	public void setEquipmentTypeId(String equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TEquipmentTypeInfoDataModel getMediumEquipmentTypeInfoModel() {
		return mediumEquipmentTypeInfoModel;
	}

	public void setMediumEquipmentTypeInfoModel(
			TEquipmentTypeInfoDataModel mediumEquipmentTypeInfoModel) {
		this.mediumEquipmentTypeInfoModel = mediumEquipmentTypeInfoModel;
	}

	public TreeNode getTreeSelectedEqu() {
		return treeSelectedEqu;
	}

	public void setTreeSelectedEqu(TreeNode treeSelectedEqu) {
		this.treeSelectedEqu = treeSelectedEqu;
	}

	public TEquipmenttypeInfo getEquTypeObj() {
		return equTypeObj;
	}

	public void setEquTypeObj(TEquipmenttypeInfo equTypeObj) {
		this.equTypeObj = equTypeObj;
	}

	public Map<String, Object> getEquTypeMap() {
		return equTypeMap;
	}

	public void setEquTypeMap(Map<String, Object> equTypeMap) {
		this.equTypeMap = equTypeMap;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getDialog() {
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}
		
	private String dialogtonul;

	public String getDialogtonul() {
		dialog = null;
		return dialogtonul;
	}

	public void setDialogtonul(String dialogtonul) {
		this.dialogtonul = dialogtonul;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
}
