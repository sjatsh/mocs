package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.model.PartClassDataModel;
import smtcl.mocs.pojos.job.TPartClassInfo;
import smtcl.mocs.services.device.IPartService;

/**
 * 零件类型配置
 * @创建时间 2013-07-09
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="partClassConfigBean")
@ViewScoped
public class PartClassConfigBean implements Serializable{ 
	
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	
	/**
	 * dataTable数据 
	 */
	private List<TPartClassInfo> partlist;

	/**
	 * dataTable数据  实现了多行选中
	 */
	private PartClassDataModel mediumPartModel;
	
	/**
	 * 查询条件
	 */
	private String query;
	
	/**
	 * 选中的行
	 */
	private TPartClassInfo[] selectedPart;
	
	/**
	 * 要新增的数据
	 */
	private TPartClassInfo addPart=new TPartClassInfo();
	/**
	 * 判断是否选中
	 */
	private String selected;
	private String nodeid;


	public PartClassConfigBean() { 
      
        
       
    }
	
	/**
	 *  查询方法
	 */
	public void queryData(){
		try {
			if("输入ID/编号/名称".equals(query))
				query=null;
			partlist=partService.getTPartClassInfo(query,nodeid);
			mediumPartModel =new PartClassDataModel(partlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新方法
	 * @param event
	 */
    public void onEdit(RowEditEvent event) {  
          TPartClassInfo tpart=(TPartClassInfo) event.getObject();
          if(tpart.getProperty().equals("请选择")){
        	  FacesMessage msg = new FacesMessage("零件大类更新","更新失败,请选择一个属性！");  
     	      FacesContext.getCurrentInstance().addMessage(null, msg);  
          }else{
        	   String tt=partService.saveTPartClassInfo(tpart);
        	    if(tt.equals("更新成功")){
         		  FacesMessage msg = new FacesMessage("零件大类更新","更新成功");  
         	      FacesContext.getCurrentInstance().addMessage(null, msg);  
	         	}else if(tt.equals("已存在")){
	         		FacesMessage msg = new FacesMessage("零件大类更新","更新失败，已存在该数据");  
	         	    FacesContext.getCurrentInstance().addMessage(null, msg); 
	         	}else{
	         		 FacesMessage msg = new FacesMessage("零件大类更新","更新失败");  
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
    	for(TPartClassInfo tt:selectedPart){
    		selected=tt.getId().toString();
    	}
    }
    /**
     * 删除新方法
     */
    public void onDelete(){
    	for(TPartClassInfo part:selectedPart){
    		partService.deleteTPartClassInfo(part);
    	}
    	try {
    		if("输入ID/编号/名称".equals(query))
				query=null;
         	partlist=partService.getTPartClassInfo(query,nodeid);
         	mediumPartModel =new PartClassDataModel(partlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    /**
     * 清空方法
     */
    public void onClear(){
    	
    }
   
    /**
     * 新增方法
     */
    public void addPartClassInfo(){
    	if(null!=addPart){
    	
    	addPart.setNodeid(nodeid);
    	String tt=partService.addTPartClassInfo(addPart);
    	if(tt.equals("添加成功")){
    		 FacesMessage msg = new FacesMessage("零件大类添加","添加成功");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg);  
    	}else if(tt.equals("已存在")){
    		 FacesMessage msg = new FacesMessage("零件大类添加","数据已存在");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg); 
    	}else{
    		 FacesMessage msg = new FacesMessage("零件大类添加","添加失败");  
    	     FacesContext.getCurrentInstance().addMessage(null, msg); 
    	}
		try {
			if("输入ID/编号/名称".equals(query))
				query=null;
         	partlist=partService.getTPartClassInfo(query,nodeid);
         	mediumPartModel =new PartClassDataModel(partlist);
         	addPart=new TPartClassInfo();
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    	}
    }

	public List<TPartClassInfo> getPartlist() {
		return partlist;
	}

	public void setPartlist(List<TPartClassInfo> partlist) {
		this.partlist = partlist;
	}

	public PartClassDataModel getMediumPartModel() {
		return mediumPartModel;
	}

	public void setMediumPartModel(PartClassDataModel mediumPartModel) {
		this.mediumPartModel = mediumPartModel;
	}

	public String getQuery() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	nodeid=session.getAttribute("nodeid2")+"";
    	
		partlist=partService.getTPartClassInfo(null,nodeid);
		mediumPartModel =new PartClassDataModel(partlist);
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public TPartClassInfo[] getSelectedPart() {
		return selectedPart;
	}

	public void setSelectedPart(TPartClassInfo[] selectedPart) {
		this.selectedPart = selectedPart;
	}

	public TPartClassInfo getAddPart() {
		return addPart;
	}

	public void setAddPart(TPartClassInfo addPart) {
		this.addPart = addPart;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
    
}  