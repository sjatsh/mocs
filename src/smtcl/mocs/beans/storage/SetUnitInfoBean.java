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
	 * ������λ�Ļ�����Ϣ�ӿ�
	 */
	private  ISetUnitServcie setunitService=(ISetUnitServcie) ServiceFactory.getBean("setunitService");
	
	/**
	 * ��ѯ�������
	 */
	private List<Map<String,Object>> unitInfolist=new ArrayList<Map<String,Object>>();
	
	/**
	 * dataTable����ʵ���˶���ѡ��
	 */
	
	private TUnitInfoModel mediumTUnitInfoModel;
	
	/**
	 * dataTableѡ����
	 */
	private Map[] selectedTunInfo;
	
	
	/**
	 * �ж��Ƿ�ѡ�е���
	 */
	private String selected;
	
	/**
	 * ��λ������������б�
	 */
	private List unitTypeList;
	
	
	/**
	 * ��λ״̬����
	 */
	private  String [] unitstatuss ;
    /**
     * ��λ�������
     */
	private String unittypename;
	/**
	 * ��λ����
	 */
	private String unitName;
	
	/**
	 * ��λ��д
	 */
	private String unitShort;
	
	/**
	 * ����
	 */
	private String unitDesc;
	
	/**
	 * �ڵ�Id
	 */
	private String nodeid;
	
	/**
	 * ���췽��
	 */
	public SetUnitInfoBean(){
		
		
	}
	
    /**
     * ��λ������Ƶ������б�
     * 
     */
	public List getunitTypeList(){
		if (null == unitTypeList) {
			unitTypeList = new ArrayList();
		} else
			unitTypeList.clear();
		unitTypeList.add(new SelectItem(null, "��ѡ��"));
		List mtModellist = setunitService.getunitTypeName(nodeid);
		for (int i = 0; i < mtModellist.size(); i++) {
			Map<String, Object> record = (Map<String, Object>) mtModellist
					.get(i);
			unitTypeList.add(new SelectItem(record.get("unittypename")));
		}
		return unitTypeList;
		
	}
	/**
	 * ����������λ
	 */
	public void addTUnitInfo(){
		
			String t=setunitService.addTUnitInfo(this);
			
			if(t.equals("��ӳɹ�")){
				FacesMessage msg = new FacesMessage("��λ�������","��ӳɹ���");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				searchList();
			}else if(t.equals("�Ѵ���")){
				FacesMessage msg = new FacesMessage("��λ�������","�����Ѵ��ڣ�");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				FacesMessage msg = new FacesMessage("��λ�������","���ʧ�ܣ�");
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
	    for(Map tt:selectedTunInfo){
	    	selected=tt.get("Id").toString();
	    }
	}
	
	public void updateTUnitInfo(RowEditEvent event){
		Map<String, Object> tunInfo= (Map<String, Object>) event.getObject();
		String tt=setunitService.updateTUnitInfo(tunInfo);
		
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
	 * datatable���ݵĲ�ѯ����
	 */
	public void searchList(){
		
		unitInfolist=setunitService.getUnitInfo(nodeid);
		mediumTUnitInfoModel=new TUnitInfoModel(unitInfolist);
	}
	
	/**
	 * ɾ��������λ��Ϣ
	 */
	public void deleteTUnitInfo(){
		 for(Map map:selectedTunInfo){
			 setunitService.deleteTUnitInfo(map);
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
		unitstatuss[0]="����";
		unitstatuss[1]="δ����";
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
