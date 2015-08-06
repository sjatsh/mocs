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
 * 设备类型维护Bean
 * @作者：yyh
 * @创建时间：2013-08-06 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="EquipmentType")
@ViewScoped
public class EquipmentTypeBean implements Serializable {
	
	/**
	 * 作业计划接口实例
	 */
	private IEquipmentService equipmentService = (IEquipmentService)ServiceFactory.getBean("equipmentService");
	private String pid;
	/**
	 * 修改类别
	 */
	private String eqmType;
	/**
	 * 列表数
	 */
	private String num;
	/**
	 * 设备dataTable数据 
	 */
	private List<Map<String, Object>> equlist = new ArrayList<Map<String, Object>>();
	/**
	 * 设备dataTable数据  外层封装
	 */
	private TEquipmentTypeInfoDataModel mediumEquipmentTypeInfoModel = new TEquipmentTypeInfoDataModel();	
	/**
	 * 设备选中的行
	 */
	private Map<String, Object>[] selectedEquipment; 	
	/**
	 * 设备类型ID
	 */
	private String equipmentTypeId;
	
	/**
	 * 左侧树对象
	 */
	private TreeNode root;  
	/**
	 * 当前选择树节点
	 */
	private TreeNode treeSelectedEqu; 
	private String pnodeid;
	/**
	 *添加的对象 
	 */
	private TEquipmenttypeInfo equTypeObj = new TEquipmenttypeInfo();
	/**
	 * 添加是的父设备类别下拉
	 */
	private Map<String, Object> equTypeMap = new HashMap<String, Object>();
	
	/**
	 *递归
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
	 * 构造
	 */
	public EquipmentTypeBean(){
		//左侧树
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
	
        root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);   
        TEquipmenttypeInfo lstx = equipmentService.getgetEquTypeIdById("-999");
        getTreeNode(lstx, root, nodeid);
        
        //添加时的下拉
		List<Map<String, Object>>  lst2 = equipmentService.getParentIdMap();
		for(Map<String, Object> map : lst2){
			 if(map.get("id")!=null && !"".equals(map.get("id"))){
			   equTypeMap.put((String)map.get("equipmentType"), map.get("id").toString());
			 }
		}
        TEquipmenttypeInfo ti = equipmentService.getgetEquTypeIdById("-999"); //最上面1级的上级默认是0
        pid="-999";
        equTypeObj.setTEquipmenttypeInfo(ti);  ///当前的父节点，添加是用
        
	}
	
	/**
	 * 点击树查询
	 */
    public void onNodeSelect(NodeSelectEvent event) {   
    	
    	TEquipmenttypeInfo te=(TEquipmenttypeInfo)event.getTreeNode().getData();
        String typeName = te.getEquipmentType();
        String typeId = "";
        if(typeName!=null && typeName.equals("设备类型")){
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
        equTypeObj.setTEquipmenttypeInfo(ti);  ///当前的父节点，添加是用
       
    } 
	
    /**
     * 添加弹出提示框
     */
    private String dialog;
    /**
     * 设备类型添加
     */
    public void addEquType(){
    	List<Map<String, Object>> lst  = equipmentService.getEquTypeRepeat(equTypeObj.getTypecode());
    	if(lst.size()==0){
    	    equipmentService.addEquType(this);
    	    
    	}else{
    		dialog = "show";
    		System.out.println("已经存在，不要重复添加----------->");  
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
 	 * 更新方法
	 * @param event
     */
    public void onEdit(RowEditEvent event) {  
    	System.out.println("更新----------->");
//    	Map<String, Object> map=(Map<String, Object>) event.getObject();        
    	equipmentService.updateEquType(this);
    }
    
    /**
     * 取消
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }

    /**
     * 删除
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
     * 判断是否选中
     */   
    public void onSelected(){
    	System.out.println("选中----------->"); 
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
     * excel下载
     */
    public void downloadExcel(){
		equlist.clear();
		equlist = equipmentService.getEquipmentInfoAList(equipmentTypeId);
    	
    	String[] firstRowValue = new String[]{
    	  "设备类型ID",
    	  "设备类型名称",
    	  "分类编码",
    	  "制造商型号",
    	  "数控系统",
    	  "设备类型描述"
    	};
    	SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd"); //不能用/杠，找不到路径
    	Date d  = new Date();
    	String d1 = sdf.format(d);
    	new ExcelUtils().outputExcel(d1+".xls", equlist,firstRowValue);//不支持中文名称
    	
    }
    
	
	/**================================set,get方法=============================================**/
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
