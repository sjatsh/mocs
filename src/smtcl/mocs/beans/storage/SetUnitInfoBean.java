package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.model.TUnitInfoModel;
import smtcl.mocs.pojos.job.TMaterialClass;
import smtcl.mocs.pojos.storage.TUnitInfo;
import smtcl.mocs.pojos.storage.TUnitType;
import smtcl.mocs.services.storage.ISetUnitServcie;
@ManagedBean(name = "SetUnitInfoBean")
@ViewScoped
public class SetUnitInfoBean implements Serializable {
	/**
	 * 计量单位的基本信息接口
	 */
	private  ISetUnitServcie setunitService=(ISetUnitServcie) ServiceFactory.getBean("setunitService");
	
	/**
	 * 查询结果集合
	 */
	private List<Map<String,Object>> unitInfolist=new ArrayList<Map<String,Object>>();
	
	/**
	 * dataTable数据实现了多行选中
	 */
	
	private TUnitInfoModel mediumTUnitInfoModel;
	
	/**
	 * dataTable选中行
	 */
	private Map[] selectedTunInfo;
	
	
	/**
	 * 判断是否选中的行
	 */
	private String selected;
	
	/**
	 * 单位类别名称下拉列表
	 */
	private List unitTypeList;
	
	
	/**
	 * 单位状态数组
	 */
	private  String [] unitstatuss ;
    /**
     * 单位类别名称
     */
	private String unittypename;
	/**
	 * 单位名称
	 */
	private String unitName;
	
	/**
	 * 单位缩写
	 */
	private String unitShort;
	
	/**
	 * 描述
	 */
	private String unitDesc;
	
	/**
	 * 节点Id
	 */
	private String nodeid;
	
	/**
	 * 构造方法
	 */
	public SetUnitInfoBean(){
		
		
	}
	
    /**
     * 单位类别名称的下拉列表
     * 
     */
	public List getunitTypeList(){
		if (null == unitTypeList) {
			unitTypeList = new ArrayList();
		} else
			unitTypeList.clear();
		unitTypeList.add(new SelectItem(null, "请选择"));
		List mtModellist = setunitService.getunitTypeName(nodeid);
		for (int i = 0; i < mtModellist.size(); i++) {
			Map<String, Object> record = (Map<String, Object>) mtModellist
					.get(i);
			unitTypeList.add(new SelectItem(record.get("unittypename")));
		}
		return unitTypeList;
		
	}
	/**
	 * 新增计量单位
	 */
	public void addTUnitInfo(){
		
			String t=setunitService.addTUnitInfo(this);
			
			if(t.equals("添加成功")){
				FacesMessage msg = new FacesMessage("单位设置添加","添加成功！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				searchList();
			}else if(t.equals("已存在")){
				FacesMessage msg = new FacesMessage("单位设置添加","数据已存在！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				FacesMessage msg = new FacesMessage("单位设置添加","添加失败！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		
		
	}
	
	
	
	/**
	 * 取消
	 */
	public void onCancel(RowEditEvent event){
		
	}
	
	/**
	 * 选中
	 */
	public void onSelected(){
	    for(Map tt:selectedTunInfo){
	    	selected=tt.get("Id").toString();
	    }
	}
	
	public void updateTUnitInfo(RowEditEvent event){
		Map<String, Object> tunInfo= (Map<String, Object>) event.getObject();
		String tt=setunitService.updateTUnitInfo(tunInfo);
		
		if(tt.equals("修改成功")){
			FacesMessage msg = new FacesMessage("单位设置修改","修改成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList();
		}else if(tt.equals("已存在")){
			FacesMessage msg = new FacesMessage("单位设置修改","数据已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("单位设置修改","修改失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * datatable数据的查询方法
	 */
	public void searchList(){
		
		unitInfolist=setunitService.getUnitInfo(nodeid);
		mediumTUnitInfoModel=new TUnitInfoModel(unitInfolist);
	}
	
	/**
	 * 删除计量单位信息
	 */
	public void deleteTUnitInfo(){
		 for(Map map:selectedTunInfo){
			 setunitService.deleteTUnitInfo(map);
		 }
		 searchList();
	}
	
	
	
	
	
	/************** set,get方法 ***************************/
	
	
	
	public String getUnittypename() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=(String)session.getAttribute("nodeid2");
		return unittypename;
	}
	public void setUnittypename(String unittypename) {
		this.unittypename = unittypename;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitShort() {
		return unitShort;
	}
	public void setUnitShort(String unitShort) {
		this.unitShort = unitShort;
	}
	public String getUnitDesc() {
		return unitDesc;
	}
	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
	public TUnitInfoModel getMediumTUnitInfoModel() {
		return mediumTUnitInfoModel;
	}
	public void setMediumTUnitInfoModel(TUnitInfoModel mediumTUnitInfoModel) {
		this.mediumTUnitInfoModel = mediumTUnitInfoModel;
	}
	
	public Map[] getSelectedTunInfo() {
		return selectedTunInfo;
	}

	public void setSelectedTunInfo(Map[] selectedTunInfo) {
		this.selectedTunInfo = selectedTunInfo;
	}

	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}

	public List<String> getUnitstatuss() {
		unitstatuss=new String[2];
		unitstatuss[0]="启用";
		unitstatuss[1]="未启用";
		return Arrays.asList(unitstatuss);
	}

	public void setUnitstatuss(String[] unitstatuss) {
		this.unitstatuss = unitstatuss;
	}


	public String getNodeid() {
		
		return nodeid;
	}


	public List<Map<String, Object>> getUnitInfolist() {
		return unitInfolist;
	}


	public void setUnitInfolist(List<Map<String, Object>> unitInfolist) {
		this.unitInfolist = unitInfolist;
	}

    
}
