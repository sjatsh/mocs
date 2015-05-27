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
 * �����Ϣ����
 * @����ʱ�� 2013-07-11
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="partTypeConfigBean")
@ViewScoped
public class PartTypeConfigBean {
	
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");

	/**
	 * dataTable���� 
	 */
	private List<TPartTypeInfo> partlist;

	/**
	 * dataTable����  ʵ���˶���ѡ��
	 */
	private PartTypeDataModel mediumPartModel;
	
	/**
	 * ��ѯ����
	 */
	private String query;
	
	/**
	 * ѡ�е���
	 */
	private TPartTypeInfo[] selectedPart;
	
	/**
	 * Ҫ����������
	 */
	private TPartTypeInfo addPart=new TPartTypeInfo();
	
	/**
	 * ������ͼ���
	 */
	private List<String> typeNoList;
	
	/**
	 * ���ã�ͣ���б�
	 */
	private List<String> options=new ArrayList<String>();
	
	/**
	 * ��ǰѡ����
	 */
	private String selectOption;
	
	private String selected;
	
	/**
	 * �ϴ��ļ�
	 */
	private UploadedFile file;
	private String partImgUrl;//Ԥ��ͼƬ·��
	
	private String partNo;
	private String imgUrl1="/images/part/zwtp.jpg";
	private String imgUrl2="/images/part/zwtp.jpg";
	private String nodeid;
	/**
	 * ���췽��
	 */
	public PartTypeConfigBean() { 
        options.add("�½�");
      //options.add("Ͷ��");
      //options.add("ͣ��");
        options.add("ɾ��");
        
       
    }
	
	/**
	 *  ��ѯ����
	 */
	public void queryData(){
		try {
			if("����ID/���/����".equals(query))
				query=null;
			partlist=new ArrayList<TPartTypeInfo>();
			partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
			mediumPartModel =new PartTypeDataModel(partlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���·���
	 * @param event
	 */
    public void onEdit(RowEditEvent event) {  
          TPartTypeInfo tpart=(TPartTypeInfo) event.getObject();
          	String tt="";
          	if(tpart.getStatus().equals("Ͷ��")){
          		tt="Ͷ��";
          	}else{
          		tt= partService.saveTPartTypeInfo(tpart);
          	}
          	
        	if(tt.equals("���³ɹ�")){
         		 FacesMessage msg = new FacesMessage("������͸���","���³ɹ�");  
         	     FacesContext.getCurrentInstance().addMessage(null, msg);  
         	}else if(tt.equals("�Ѵ���")){
         		 FacesMessage msg = new FacesMessage("������͸���","����ʧ��,�Ѵ��ڸ����ݣ�");  
         	     FacesContext.getCurrentInstance().addMessage(null, msg); 
         	}else if(tt.equals("�Ѵ���")){
         		 FacesMessage msg = new FacesMessage("������͸���","����ʧ��,������Ͷ�������ݣ�");  
         	     FacesContext.getCurrentInstance().addMessage(null, msg); 
         	}else{
         		 FacesMessage msg = new FacesMessage("������͸���","����ʧ��");  
         	     FacesContext.getCurrentInstance().addMessage(null, msg); 
         	}
        	 queryData();
    }  
      
    /**
     * ȡ��
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }
    
    /**
     * ɾ���·���
     */
    public void onDelete(){
    	String messg="";
    	for(TPartTypeInfo part:selectedPart){
    		if(part.getStatus().equals("Ͷ��")){
    			messg="Ͷ��";
    			break;
    		}
    		partService.deleteTPartTypeInfo(part);
    	}
    	if(messg.equals("Ͷ��")){
    		FacesMessage msg = new FacesMessage("�������ɾ��","ɾ��ʧ��,������Ͷ�������ݣ�");  
    	    FacesContext.getCurrentInstance().addMessage(null, msg); 
    	}else{
    		FacesMessage msg = new FacesMessage("�������ɾ��","ɾ���ɹ���");  
    	    FacesContext.getCurrentInstance().addMessage(null, msg); 
    	}
    	
    	try {
    		if("����ID/���/����".equals(query))
				query=null;
    		partlist=new ArrayList<TPartTypeInfo>();
         	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
         	mediumPartModel =new PartTypeDataModel(partlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    /**
     * ���
     */
    public void onClear(){
    	try {
			List<TPartTypeInfo> pl=partService.getTPartTypeInfo(query,selectOption,nodeid);
			for(TPartTypeInfo part:pl){
	    		partService.deleteTPartTypeInfo(part);
	    	}
			if("����ID/���/����".equals(query))
				query=null;
			partlist=new ArrayList<TPartTypeInfo>();
         	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
         	mediumPartModel =new PartTypeDataModel(partlist);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    
    /**
     * ͣ�÷���
     */
    public void onDisabled(){
    	String str="";
    	for(TPartTypeInfo part:selectedPart){
    		if(part.getStatus().equals("�½�")){
    			partService.stopOrStartTPartTypeInfo(part,"ͣ��");
    		}else{
    			str=str+"��"+part.getNo();
    		}
    	}
    	if(!"".equals(str)){
    		FacesMessage msg = new FacesMessage("�������ͣ��","���Ϊ��"+str.substring(1,str.length())+"�����״̬��Ϊ�½�,����ͣ�ã�");  
      	    FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	try {
    		if("����ID/���/����".equals(query))
				query=null;
    		partlist=new ArrayList<TPartTypeInfo>();
         	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
         	mediumPartModel =new PartTypeDataModel(partlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    
    /**
     * ���÷���
     */
    public void onResume(){
    	String str="";
    	for(TPartTypeInfo part:selectedPart){
    		if(part.getStatus().equals("ɾ��")){
    			partService.stopOrStartTPartTypeInfo(part,"ɾ��");
    			FacesMessage msg = new FacesMessage("������ͻָ�","�ָ��ɹ���");   
          	    FacesContext.getCurrentInstance().addMessage(null, msg);
    		}else{
    			str=str+"��"+part.getNo();
    		}
    		
    	}
    	/*if(!"".equals(str)){
    		FacesMessage msg = new FacesMessage("������ͻָ�","ֻ�ܶ�ͣ��״̬������������ָ����������Ϊ��"+str.substring(1,str.length())+"�����״̬��Ϊͣ��,���ָܻ���");  
      	    FacesContext.getCurrentInstance().addMessage(null, msg);
    	}*/
    	try {
    		if("����ID/���/����".equals(query))
				query=null;
    		partlist=new ArrayList<TPartTypeInfo>();
         	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
         	mediumPartModel =new PartTypeDataModel(partlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    /**
     * ������ı��ѯ
     */
    public void selectChange(){
    	  try {
    		partlist=new ArrayList<TPartTypeInfo>();
    		if("����ID/���/����".equals(query))
				query=null;
          	partlist=partService.getTPartTypeInfo(query,selectOption,nodeid);
  			mediumPartModel =new PartTypeDataModel(partlist);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
    }
    
    /**
     * ��������
     */
    public void addPartTypeInfo(){
    	if(null!=addPart){
        	addPart.setNodeid(nodeid);
    		String tt=partService.addTPartTypeInfo(addPart);
    		if(tt.equals("��ӳɹ�")){
	       		 FacesMessage msg = new FacesMessage("����������","��ӳɹ�");  
	       	     FacesContext.getCurrentInstance().addMessage(null, msg);  
	       	}else if(tt.equals("�Ѵ���")){
	       		 FacesMessage msg = new FacesMessage("����������","�����Ѵ���");  
	       	     FacesContext.getCurrentInstance().addMessage(null, msg); 
	       	}else{
	       		 FacesMessage msg = new FacesMessage("����������","���ʧ��");  
	       	     FacesContext.getCurrentInstance().addMessage(null, msg); 
	       	}
    		try {
    			if("����ID/���/����".equals(query))
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
     * �ϴ��ļ�����
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
				FacesMessage msg = new FacesMessage("�����ͼƬ��","�ϴ��ɹ���");  
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
    //��ȡͼƬ·��
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
     * ��ȡ��ǰѡ�е����id
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
