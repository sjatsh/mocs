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
 * �����������
 * @����ʱ�� 2013-07-09
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="partClassConfigBean")
@ViewScoped
public class PartClassConfigBean implements Serializable{ 
	
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	
	/**
	 * dataTable���� 
	 */
	private List<TPartClassInfo> partlist;

	/**
	 * dataTable����  ʵ���˶���ѡ��
	 */
	private PartClassDataModel mediumPartModel;
	
	/**
	 * ��ѯ����
	 */
	private String query;
	
	/**
	 * ѡ�е���
	 */
	private TPartClassInfo[] selectedPart;
	
	/**
	 * Ҫ����������
	 */
	private TPartClassInfo addPart=new TPartClassInfo();
	/**
	 * �ж��Ƿ�ѡ��
	 */
	private String selected;
	private String nodeid;


	public PartClassConfigBean() { 
      
        
       
    }
	
	/**
	 *  ��ѯ����
	 */
	public void queryData(){
		try {
			if("����ID/���/����".equals(query))
				query=null;
			partlist=partService.getTPartClassInfo(query,nodeid);
			mediumPartModel =new PartClassDataModel(partlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���·���
	 * @param event
	 */
    public void onEdit(RowEditEvent event) {  
          TPartClassInfo tpart=(TPartClassInfo) event.getObject();
          if(tpart.getProperty().equals("��ѡ��")){
        	  FacesMessage msg = new FacesMessage("����������","����ʧ��,��ѡ��һ�����ԣ�");  
     	      FacesContext.getCurrentInstance().addMessage(null, msg);  
          }else{
        	   String tt=partService.saveTPartClassInfo(tpart);
        	    if(tt.equals("���³ɹ�")){
         		  FacesMessage msg = new FacesMessage("����������","���³ɹ�");  
         	      FacesContext.getCurrentInstance().addMessage(null, msg);  
	         	}else if(tt.equals("�Ѵ���")){
	         		FacesMessage msg = new FacesMessage("����������","����ʧ�ܣ��Ѵ��ڸ�����");  
	         	    FacesContext.getCurrentInstance().addMessage(null, msg); 
	         	}else{
	         		 FacesMessage msg = new FacesMessage("����������","����ʧ��");  
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
    	for(TPartClassInfo tt:selectedPart){
    		selected=tt.getId().toString();
    	}
    }
    /**
     * ɾ���·���
     */
    public void onDelete(){
    	for(TPartClassInfo part:selectedPart){
    		partService.deleteTPartClassInfo(part);
    	}
    	try {
    		if("����ID/���/����".equals(query))
				query=null;
         	partlist=partService.getTPartClassInfo(query,nodeid);
         	mediumPartModel =new PartClassDataModel(partlist);
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}
    }
    /**
     * ��շ���
     */
    public void onClear(){
    	
    }
   
    /**
     * ��������
     */
    public void addPartClassInfo(){
    	if(null!=addPart){
    	
    	addPart.setNodeid(nodeid);
    	String tt=partService.addTPartClassInfo(addPart);
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