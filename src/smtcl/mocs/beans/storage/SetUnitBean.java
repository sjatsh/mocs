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
	 * ������λ�Ļ�����Ϣ�ӿ�
	 */
	private  ISetUnitServcie setunitService=(ISetUnitServcie) ServiceFactory.getBean("setunitService");
	
	/**
	 * ��ѯ�������
	 */
	private List<Map<String, Object>> unitlist;
	
	
	/**
	 * dataTable����ʵ���˶���ѡ��
	 */
	
	private TUnitTypeModel   mediumTUnitTypeModel;
	/**
	 * ��������
	 */
	
	private String unittypename;
	
	/**
	 * ����˵��
	 */
	
	private String unittypedesc;
	
	/**
	 * ��׼��λ
	 */
	
	private String unit;
	
	/**
	 * ��λ��д
	 */
	
	private String unitshort;
	
	/**
	 * ʧЧ����
	 */
	
	private Date invadate;
	
	/**
	 * dataTableѡ����
	 */
	private Map[] selectedType;
	
	
	/**
	 * �ж��Ƿ�ѡ�е���
	 */
	private String selected;
	
	/**
	 * �ڵ�Id
	 */
	private String nodeid;
	
	/**
	 * ���췽��
	 */
	public SetUnitBean(){
		
		
	}
    
	/**
	 * ����������λ�ķ�����Ϣ
	 */
	public void addTUnitType(){
		
		String tt=setunitService.addTUnitType(this);
		
		if(tt.equals("��ӳɹ�")){
			FacesMessage msg = new FacesMessage("��λ�������","��ӳɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList();
		}else if(tt.equals("�Ѵ���")){
			FacesMessage msg = new FacesMessage("��λ�������","�����Ѵ��ڣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("��λ�������","���ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	/**
	 * �޸ļ���������Ϣ
	 */
	
	public void updateTUnitType(RowEditEvent event){
		Map<String, Object> tuntype= (Map<String, Object>) event.getObject();
		String tt=setunitService.updateTUnitType(tuntype);
		if(tt.equals("�޸ĳɹ�")){
			FacesMessage msg = new FacesMessage("��λ�����޸�","�޸ĳɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList();
		}else if(tt.equals("�Ѵ���")){
			FacesMessage msg = new FacesMessage("��λ�����޸�","�����Ѵ��ڣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("��λ�����޸�","�޸�ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	/**
	 * ȡ��
	 */
	public void onCancel(RowEditEvent event){
		
	}
	
	
	/**
	 * ѡ��
	 */
	public void onSelected(){
	    for(Map tt:selectedType){
	    	selected=tt.get("Id").toString();
	    }
	}
	
	
	/**
	 * datatable���ݵĲ�ѯ����
	 */
	public void searchList(){
		
		unitlist=setunitService.getUnitTypeInfo(nodeid);
		mediumTUnitTypeModel=new TUnitTypeModel(unitlist);
	}
	
	/**
	 * ɾ����λ�������Ϣ
	 */
	public void deleteTUnitType(){
		 for(Map map:selectedType){
			 setunitService.deleteTUnitType(map);
		 }
		 searchList();
	}
	
	
	/************** set,get���� ***************************/
   
	
	
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
