package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.TprogramInfoModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TProgramInfo;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;


/**
 * ��������
 * @����ʱ�� 2013-08-08
 * @���� wangkaili
 * @�޸��ߣ� liguoqiang
 * @�޸����ڣ� 2014-05-09
 * @�޸�˵�� �����ˮ�ţ�ȷ�ϳ������Ʋ��ظ���
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name="ProgramInfoBean")
@ViewScoped
public class ProgramInfoBean implements Serializable{

	/**
	 * ��Դ�ӿ�ʵ��
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");

	/**
	 * dataTable����
	 */
	private List<TProgramInfo> tprolist;
	
	/**
	 * dataTableѡ����
	 */
	private TProgramInfo[] selectedProgram;
	
	/**
	 * dataTable����ʵ���˶���ѡ��
	 */
	private TprogramInfoModel mediumTprogramModel;
	
	/**
	 * ��ѯ�ַ���
	 */
	private String searchStr; 
	
	/**
	 * �ж��Ƿ�ѡ�е���
	 */
	private String selected;
	
	/**
	 * Ҫ����������
	 */
	private TProgramInfo addTpro = new TProgramInfo();
	private String nodeid;
	
	/**
	 * ���췽��
	 */
	public ProgramInfoBean(){
		
	}
	
	/**
	 * ��ѯ����
	 */
	public void query()
	{
		try {
			if("������/����/������".equals(searchStr))
				searchStr=null;
			tprolist = resourceService.getTprogramInfo(searchStr,nodeid);
			mediumTprogramModel = new TprogramInfoModel(tprolist);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������
	 * 
	 */
	public void addTprogramInfo(){
		if(null != addTpro){
			TUser userinfo=(TUser)FaceContextUtil.getSessionMap().get(Constants.USER_SESSION_KEY);
			
			userinfo.getLoginName();
			
			Date date = new Date();
			addTpro.setCreateTime(date);
			addTpro.setCreator(userinfo.getLoginName());
			addTpro.setNodeid(nodeid);
			String t = resourceService.saveTprogramInfo(addTpro);
			if(t.equals("��ӳɹ�")){
				FacesMessage msg = new FacesMessage("������Ϣ���","��ӳɹ���");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else if(t.equals("�Ѵ���")){
				FacesMessage msg = new FacesMessage("������Ϣ���","�����Ѵ��ڣ�");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				FacesMessage msg = new FacesMessage("������Ϣ���","���ʧ�ܣ�");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			try {
				if("������/����/������".equals(searchStr))
					searchStr=null;
				tprolist = resourceService.getTprogramInfo(searchStr,nodeid);
				mediumTprogramModel = new TprogramInfoModel(tprolist);
				addTpro = new TProgramInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���³���
	 * @param event 
	 * 
	 */
	public void updateTprogramInfo(RowEditEvent event){
		TProgramInfo tpro = (TProgramInfo)event.getObject();
		TUser userinfo=(TUser)FaceContextUtil.getSessionMap().get(Constants.USER_SESSION_KEY);
		userinfo.getLoginName();
		
		Date date = new Date();
		tpro.setUpdateTime(date);
		new SimpleDateFormat("yyyy-MM-dd").format(date);
		
	
		
		tpro.setUpdator(userinfo.getLoginName());
		String t = resourceService.updateTprogramInfo(tpro);
		if(t.equals("���³ɹ�")){
			FacesMessage msg = new FacesMessage("������Ϣ����","���³ɹ���");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(t.equals("�Ѵ���")){
			FacesMessage msg = new FacesMessage("������Ϣ����","�Ѵ��ڣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("������Ϣ����","����ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * ȡ��
	 */
	public void onCancel(RowEditEvent event){
		
	}
	
	public void onSelected(){
	    for(TProgramInfo tt:selectedProgram){
	    	selected=tt.getId().toString();
	    }
	}
	
	/**
	 * ɾ������
	 */
	public void deleteTprogramInfo(){
		for(TProgramInfo tpr : selectedProgram){
			resourceService.deleteTprogramInfo(tpr);
		}
		try {
			if("������/����/������".equals(searchStr))
				searchStr=null;
			tprolist = resourceService.getTprogramInfo(searchStr,nodeid);
			mediumTprogramModel = new TprogramInfoModel(tprolist);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}



	public List<TProgramInfo> getTprolist() {
		return tprolist;
	}

	public void setTprolist(List<TProgramInfo> tprolist) {
		this.tprolist = tprolist;
	}

	public TProgramInfo[] getSelectedProgram() {
		return selectedProgram;
	}

	public void setSelectedProgram(TProgramInfo[] selectedProgram) {
		this.selectedProgram = selectedProgram;
	}

	public TprogramInfoModel getMediumTprogramModel() {
		return mediumTprogramModel;
	}

	public void setMediumTprogramModel(TprogramInfoModel mediumTprogramModel) {
		this.mediumTprogramModel = mediumTprogramModel;
	}

	public String getSearchStr() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=(String)session.getAttribute("nodeid2");
		resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
		tprolist = resourceService.getTprogramInfo(null,nodeid);
		mediumTprogramModel = new TprogramInfoModel(tprolist);
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public TProgramInfo getAddTpro() {
		return addTpro;
	}

	public void setAddTpro(TProgramInfo addTpro) {
		this.addTpro = addTpro;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
