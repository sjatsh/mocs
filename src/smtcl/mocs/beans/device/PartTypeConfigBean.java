package smtcl.mocs.beans.device;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.util.HSSFColor.BLACK;
import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.exception.FileUploadException;
import org.richfaces.model.UploadedFile;

import smtcl.mocs.model.PartTypeDataModel;
import smtcl.mocs.pojos.job.TPartClassInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 零件信息配置
 * @创建时间 2013-07-11
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="partTypeConfigBean")
@ViewScoped
public class PartTypeConfigBean {
	
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");

	/**
	 * dataTable数据 
	 */
	private List<TPartTypeInfo> partlist;

	/**
	 * dataTable数据  实现了多行选中
	 */
	private PartTypeDataModel mediumPartModel;
	
	/**
	 * 查询条件
	 */
	private String query;
	
	/**
	 * 选中的行
	 */
	private TPartTypeInfo[] selectedPart;
	
	/**
	 * 要新增的数据
	 */
	private TPartTypeInfo addPart=new TPartTypeInfo();
	
	/**
	 * 零件类型集合
	 */
	private List<String> typeNoList;
	
	/**
	 * 启用，停用列表
	 */
	private List<String> options=new ArrayList<String>();
	
	/**
	 * 当前选中项
	 */
	private String selectOption;
	
	private String selected;
	
	/**
	 * 上传文件
	 */
	private UploadedFile file;
	private String partImgUrl;//预览图片路径
	
	private String partNo;
	private String imgUrl1="/images/part/zwtp.jpg";
	private String imgUrl2="/images/part/zwtp.jpg";
	private String nodeid;
	/**
	 * 构造方法
	 */
	public PartTypeConfigBean() { 
        options.add("新建");
      //options.add("投产");
      //options.add("停用");
        options.add("删除");
        
       
    }
	
	/**
	 *  查询方法
	 */
	public void queryData(){
		try {
			if("输入ID/编号/名称".equals(query))
				query=null;
			partlist=new ArrayList<TPartTypeInfo>();
			partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
			mediumPartModel =new PartTypeDataModel(partlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新方法
	 * @param event
	 */
    public void onEdit(RowEditEvent event) {  
          TPartTypeInfo tpart=(TPartTypeInfo) event.getObject();
          	String tt="";
          	if(tpart.getStatus().equals("投产")){
          		tt="投产";
          	}else{
          		tt= partService.saveTPartTypeInfo(tpart);
          	}
          	
        	if(tt.equals("更新成功")){
         		 FacesMessage msg = new FacesMessage("零件类型更新","更新成功");  
         	     FacesContext.getCurrentInstance().addMessage(null, msg);  
         	}else if(tt.equals("已存在")){
         		 FacesMessage msg = new FacesMessage("零件类型更新","更新失败,已存在该数据！");  
         	     FacesContext.getCurrentInstance().addMessage(null, msg); 
         	}else if(tt.equals("已存在")){
         		 FacesMessage msg = new FacesMessage("零件类型更新","更新失败,存在已投产的数据！");  
         	     FacesContext.getCurrentInstance().addMessage(null, msg); 
         	}else{
         		 FacesMessage msg = new FacesMessage("零件类型更新","更新失败");  
         	     FacesContext.getCurrentInstance().addMessage(null, msg); 
         	}
        	 queryData();
    }  
      
    /**
     * 取消
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }
    
    /**
     * 删除新方法
     */
    public void onDelete(){
    	String messg="";
    	for(TPartTypeInfo part:selectedPart){
    		if(part.getStatus().equals("投产")){
    			messg="投产";
    			break;
    		}
    		partService.deleteTPartTypeInfo(part);
    	}
    	if(messg.equals("投产")){
    		FacesMessage msg = new FacesMessage("零件类型删除","删除失败,存在已投产的数据！");  
    	    FacesContext.getCurrentInstance().addMessage(null, msg); 
    	}else{
    		FacesMessage msg = new FacesMessage("零件类型删除","删除成功！");  
    	    FacesContext.getCurrentInstance().addMessage(null, msg); 
    	}
    	
    	try {
    		if("输入ID/编号/名称".equals(query))
				query=null;
    		partlist=new ArrayList<TPartTypeInfo>();
         	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
         	mediumPartModel =new PartTypeDataModel(partlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    /**
     * 清空
     */
    public void onClear(){
    	try {
			List<TPartTypeInfo> pl=partService.getTPartTypeInfo(query,selectOption,nodeid);
			for(TPartTypeInfo part:pl){
	    		partService.deleteTPartTypeInfo(part);
	    	}
			if("输入ID/编号/名称".equals(query))
				query=null;
			partlist=new ArrayList<TPartTypeInfo>();
         	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
         	mediumPartModel =new PartTypeDataModel(partlist);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    
    /**
     * 停用方法
     */
    public void onDisabled(){
    	String str="";
    	for(TPartTypeInfo part:selectedPart){
    		if(part.getStatus().equals("新建")){
    			partService.stopOrStartTPartTypeInfo(part,"停用");
    		}else{
    			str=str+"、"+part.getNo();
    		}
    	}
    	if(!"".equals(str)){
    		FacesMessage msg = new FacesMessage("零件类型停用","编号为："+str.substring(1,str.length())+"的零件状态不为新建,不能停用！");  
      	    FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	try {
    		if("输入ID/编号/名称".equals(query))
				query=null;
    		partlist=new ArrayList<TPartTypeInfo>();
         	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
         	mediumPartModel =new PartTypeDataModel(partlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    
    /**
     * 启用方法
     */
    public void onResume(){
    	String str="";
    	for(TPartTypeInfo part:selectedPart){
    		if(part.getStatus().equals("删除")){
    			partService.stopOrStartTPartTypeInfo(part,"删除");
    			FacesMessage msg = new FacesMessage("零件类型恢复","恢复成功！");   
          	    FacesContext.getCurrentInstance().addMessage(null, msg);
    		}else{
    			str=str+"、"+part.getNo();
    		}
    		
    	}
    	/*if(!"".equals(str)){
    		FacesMessage msg = new FacesMessage("零件类型恢复","只能对停用状态的零件类型做恢复操作！编号为："+str.substring(1,str.length())+"的零件状态不为停用,不能恢复！");  
      	    FacesContext.getCurrentInstance().addMessage(null, msg);
    	}*/
    	try {
    		if("输入ID/编号/名称".equals(query))
				query=null;
    		partlist=new ArrayList<TPartTypeInfo>();
         	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
         	mediumPartModel =new PartTypeDataModel(partlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    /**
     * 下拉框改变查询
     */
    public void selectChange(){
    	  try {
    		partlist=new ArrayList<TPartTypeInfo>();
    		if("输入ID/编号/名称".equals(query))
				query=null;
          	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
  			mediumPartModel =new PartTypeDataModel(partlist);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
    }
    
    /**
     * 新增方法
     */
    public void addPartTypeInfo(){
    	if(null!=addPart){
        	addPart.setNodeid(nodeid);
    		String tt=partService.addTPartTypeInfo(addPart);
    		if(tt.equals("添加成功")){
	       		 FacesMessage msg = new FacesMessage("零件类型添加","添加成功");  
	       	     FacesContext.getCurrentInstance().addMessage(null, msg);  
	       	}else if(tt.equals("已存在")){
	       		 FacesMessage msg = new FacesMessage("零件类型添加","数据已存在");  
	       	     FacesContext.getCurrentInstance().addMessage(null, msg); 
	       	}else{
	       		 FacesMessage msg = new FacesMessage("零件类型添加","添加失败");  
	       	     FacesContext.getCurrentInstance().addMessage(null, msg); 
	       	}
    		try {
    			if("输入ID/编号/名称".equals(query))
    				query=null;
    			partlist=new ArrayList<TPartTypeInfo>();
             	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
             	mediumPartModel =new PartTypeDataModel(partlist);
             	addPart=new TPartTypeInfo();
    	   	} catch (Exception e) {
    	   		e.printStackTrace();
    	   	}
    	}
    }
    /**
     * 上传文件方法
     * @param id
     */
    public void Upload(FileUploadEvent event){
    	file=null;
        file = event.getUploadedFile();
		String uri = StringUtils.getFileName(file);
		if(null!=partNo&&!"".equals(partNo)){
			if(null != file){
				savaFile(file,uri);
				TPartTypeInfo tpti=partService.getTPartTypeInfoByNo(partNo).get(0);
				tpti.setPath(uri);
				String tt=partService.saveTPartTypeInfo(tpti);
				System.out.println(tt);
				FacesMessage msg = new FacesMessage("上零件图片传","上传成功！");  
	     	    FacesContext.getCurrentInstance().addMessage(null, msg); 
			}
			partNo="";
		}
        
    }
    public void savaFile(UploadedFile file,String uri){
		 
		   String realPath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/static/images/part/");
		   System.out.println("realPath====="+realPath+""+uri);
		   File imageFile = new File(realPath, uri);   
		   try {
			   FileOutputStream fops = new FileOutputStream(imageFile);
			   fops.write(file.getData());
			   fops.flush();  
			   fops.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			 
	}
    //获取图片路径
    public void getImgUrlAddress(ActionEvent event){
    	String url = (String) event.getComponent().getAttributes().get("url"); 
    	partImgUrl=url;
    	if(null!=url&&!"".equals(url)){
    			imgUrl1="../images/part/"+url;
    	}else{
    		imgUrl1="/images/part/zwtp.jpg";
    		
    	}
    	
    }
    /**
     * 获取当前选中的零件id
     * @param id
     */
    public void setPart(ActionEvent event) {
    	String no = (String) event.getComponent().getAttributes().get("no"); 
    	
    	System.out.println("----------------------------->"+partNo);
    	partNo=no;
    	System.out.println("----------------------------->"+partNo);
    }
   
    public void onSelected(){
    	for(TPartTypeInfo tt:selectedPart){
    		selected=tt.getId().toString();
    	}
    }

	public PartTypeDataModel getMediumPartModel() {
		return mediumPartModel;
	}

	public void setMediumPartModel(PartTypeDataModel mediumPartModel) {
		this.mediumPartModel = mediumPartModel;
	}

	public String getQuery() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	nodeid=session.getAttribute("nodeid2")+"";
	    partlist=new ArrayList<TPartTypeInfo>();
    	partlist=partService.getTPartTypeInfo(null,null,nodeid);
		mediumPartModel =new PartTypeDataModel(partlist);
		typeNoList=partService.getTypeNo(nodeid);
			
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public TPartTypeInfo[] getSelectedPart() {
		return selectedPart;
	}

	public void setSelectedPart(TPartTypeInfo[] selectedPart) {
		this.selectedPart = selectedPart;
	}

	public TPartTypeInfo getAddPart() {
		return addPart;
	}

	public void setAddPart(TPartTypeInfo addPart) {
		this.addPart = addPart;
	}

	public List<String> getTypeNoList() {
		return typeNoList;
	}

	public void setTypeNoList(List<String> typeNoList) {
		this.typeNoList = typeNoList;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getSelectOption() {
		System.out.println(selectOption);
		return selectOption;
	}

	public void setSelectOption(String selectOption) {
		this.selectOption = selectOption;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getImgUrl1() {
		return imgUrl1;
	}

	public void setImgUrl1(String imgUrl1) {
		this.imgUrl1 = imgUrl1;
	}

	public String getImgUrl2() {
		return imgUrl2;
	}

	public void setImgUrl2(String imgUrl2) {
		this.imgUrl2 = imgUrl2;
	}

	public String getPartImgUrl() {
		return partImgUrl;
	}

	public void setPartImgUrl(String partImgUrl) {
		this.partImgUrl = partImgUrl;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}	
	
}
