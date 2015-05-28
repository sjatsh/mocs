package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.model.TUnitTypeModel;
import smtcl.mocs.model.TprogramInfoModel;
import smtcl.mocs.pojos.job.TProgramInfo;
import smtcl.mocs.pojos.storage.TUnitType;
import smtcl.mocs.services.storage.ISetUnitServcie;
@ManagedBean(name = "SetUnitBean")
@ViewScoped
public class SetUnitBean implements Serializable {
      
	/**
	 * 计量单位的基本信息接口
	 */
	private  ISetUnitServcie setunitService=(ISetUnitServcie) ServiceFactory.getBean("setunitService");
	
	/**
	 * 查询结果集合
	 */
	private List<Map<String, Object>> unitlist;
	
	
	/**
	 * dataTable数据实现了多行选中
	 */
	
	private TUnitTypeModel   mediumTUnitTypeModel;
	/**
	 * 分类名称
	 */
	
	private String unittypename;
	
	/**
	 * 分类说明
	 */
	
	private String unittypedesc;
	
	/**
	 * 基准单位
	 */
	
	private String unit;
	
	/**
	 * 单位缩写
	 */
	
	private String unitshort;
	
	/**
	 * 失效日期
	 */
	
	private Date invadate;
	
	/**
	 * dataTable选中行
	 */
	private Map[] selectedType;
	
	
	/**
	 * 判断是否选中的行
	 */
	private String selected;
	
	/**
	 * 节点Id
	 */
	private String nodeid;
	
	/**
	 * 构造方法
	 */
	public SetUnitBean(){
		
		
	}
    
	/**
	 * 新增计量单位的分类信息
	 */
	public void addTUnitType(){
		
		String tt=setunitService.addTUnitType(this);
		
		if(tt.equals("添加成功")){
			FacesMessage msg = new FacesMessage("单位分类添加","添加成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList();
		}else if(tt.equals("已存在")){
			FacesMessage msg = new FacesMessage("单位分类添加","数据已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("单位分类添加","添加失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	/**
	 * 修改计量分类信息
	 */
	
	public void updateTUnitType(RowEditEvent event){
		Map<String, Object> tuntype= (Map<String, Object>) event.getObject();
		String tt=setunitService.updateTUnitType(tuntype);
		if(tt.equals("修改成功")){
			FacesMessage msg = new FacesMessage("单位分类修改","修改成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList();
		}else if(tt.equals("已存在")){
			FacesMessage msg = new FacesMessage("单位分类修改","数据已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("单位分类修改","修改失败！");
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
	    for(Map tt:selectedType){
	    	selected=tt.get("Id").toString();
	    }
	}
	
	
	/**
	 * datatable数据的查询方法
	 */
	public void searchList(){
		
		unitlist=setunitService.getUnitTypeInfo(nodeid);
		mediumTUnitTypeModel=new TUnitTypeModel(unitlist);
	}
	
	/**
	 * 删除单位分类的信息
	 */
	public void deleteTUnitType(){
		 for(Map map:selectedType){
			 setunitService.deleteTUnitType(map);
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

	
   public TUnitTypeModel getMediumTUnitTypeModel() {
		
		return mediumTUnitTypeModel;
	}

	public void setMediumTUnitTypeModel(TUnitTypeModel mediumTUnitTypeModel) {
		this.mediumTUnitTypeModel = mediumTUnitTypeModel;
	}
	

	public List<Map<String, Object>> getUnitlist() {
		    
			return unitlist;
	}

	public void setUnitlist(List<Map<String, Object>> unitlist) {
		this.unitlist = unitlist;
	}

	
	public String getUnittypedesc() {
		return unittypedesc;
	}

	public void setUnittypedesc(String unittypedesc) {
		this.unittypedesc = unittypedesc;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitshort() {
		return unitshort;
	}

	public void setUnitshort(String unitshort) {
		this.unitshort = unitshort;
	}

	public Date getInvadate() {
		return invadate;
	}

	public void setInvadate(Date invadate) {
		this.invadate = invadate;
	}

	

	public Map[] getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(Map[] selectedType) {
		this.selectedType = selectedType;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

    public String getNodeid() {
		
		return nodeid;
	}

	
     
	

    
	
	
}
