package smtcl.mocs.beans.device;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import smtcl.mocs.common.device.Md5;
import smtcl.mocs.model.TprogramInfoModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TProgramInfo;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;


/**
 * ��������
 * @����ʱ�� 2013-08-08
 * @���� wangkaili
 * @�޸��ߣ� liguoqiang
 * @�޸����ڣ� 2014-05-09
 * @�޸�˵�� �����ˮ�ţ�ȷ�ϳ������Ʋ��ظ���
 * @version V1.0
 * 
 * @�޸��ߣ� FW
 * @�޸����ڣ� 2015-02-12
 * @�޸�˵�� �����
 * @version�� V2.0
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
	 * ���򼯺�
	 */
	private List<Map<String,Object>> programList = new ArrayList<Map<String,Object>>();
	/**
	 * �󶨳���
	 */
	private List<Map<String,Object>> bingProgramList = new ArrayList<Map<String,Object>>();
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
	 * ��ѡ��ĳ���ID
	 */
	private String selectProgramId;
	/**
	 * ��������
	 */
	private String progName;
	/**
	 * �汾
	 */
	private String versionNo;
	/**
	 * ˵��
	 */
	private String des;
	/**
	 * �������
	 */
	private String materialNo;
	/**
	 * ��������
	 */
	private String processName;
	/**
	 * ����FLAG
	 */
	private Boolean activityFlag;
	
	/**
	 * Ҫ����������
	 */
	private TProgramInfo addTpro = new TProgramInfo();
	private String nodeid;
	private UploadedFile file =null;
	private String fileUrl;
	private String success;
	
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
			if("��������/�汾".equals(searchStr))
				searchStr=null;
			//tprolist = resourceService.getTprogramInfo(searchStr,nodeid);
			//mediumTprogramModel = new TprogramInfoModel(tprolist);
			
			programList =resourceService.getProgramData(searchStr,nodeid);
			for(Map<String,Object> map:programList ){
				if(null!= map.get("status")){
					if(map.get("status").toString().equals("N")){
						map.put("activityFlag", "");
						map.put("notActivityFlag", "none");
					}else{
						map.put("activityFlag", "none");
						map.put("notActivityFlag", "");
					}
				}else{
					map.put("activityFlag", "none");
					map.put("notActivityFlag", "none");
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������
	 * 
	 */
	public void addTprogramInfo(){
		String dd= selectProgramId;
		if(null != addTpro){
			TUser userinfo=(TUser)FaceContextUtil.getSessionMap().get(Constants.USER_SESSION_KEY);
			
			userinfo.getLoginName();
			
			Date date = new Date();
			addTpro.setCreateTime(date);
			addTpro.setCreator(userinfo.getLoginName());
			addTpro.setNodeid(nodeid);
			addTpro.setStatus("N");//״̬
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
				if("��������/�汾".equals(searchStr))
					searchStr=null;
				//tprolist = resourceService.getTprogramInfo(searchStr,nodeid);
				//mediumTprogramModel = new TprogramInfoModel(tprolist);
				programList =resourceService.getProgramData(searchStr,nodeid);
				for(Map<String,Object> map:programList ){
					if(null!= map.get("status")){
						if(map.get("status").toString().equals("N")){
							map.put("activityFlag", "");
							map.put("notActivityFlag", "none");
						}else{
							map.put("activityFlag", "none");
							map.put("notActivityFlag", "");
						}
					}else{
						map.put("activityFlag", "none");
						map.put("notActivityFlag", "none");
					}
				}
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
	 * �޸ĳ��򱣴�
	 */
	public void editProgramInfo(){
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			
			TProgramInfo tpro =new TProgramInfo();
			tpro.setId(Long.valueOf(selectProgramId));
			tpro.setDescribe2(des);
			
			String t = resourceService.updateTprogramInfo(tpro);
			if(t.equals("���³ɹ�")){
				FacesMessage msg = new FacesMessage("������Ϣ����","���³ɹ���");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				query();
			}else{
				FacesMessage msg = new FacesMessage("������Ϣ����","����ʧ�ܣ�");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} 
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
			if("��������/�汾".equals(searchStr))
				searchStr=null;
			tprolist = resourceService.getTprogramInfo(searchStr,nodeid);
			mediumTprogramModel = new TprogramInfoModel(tprolist);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ɾ��ǰ����check
	 */
	public void checkDeleteProgram(){
		success ="";
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			List<Map<String, Object>> list = resourceService.getBingProgramData(selectProgramId);
			if(list.size()>0){
				success ="�ó����Ѱ�,������ɾ��!";
			}
		}
	}
	/**
	 * ɾ������
	 */
	public void deleteProgramInfo(){
		success ="";
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			success = resourceService.deleteProgram(selectProgramId);
			query();
		}
	}
	
	/**
	 * ���򼤻�ǰ����check
	 */
	public void checkActivityProgram(){
		success ="";
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			List<Map<String, Object>> list = resourceService.getBingProgramData(selectProgramId);
			if(list.size()>0){
				
			}else{
				success ="���Ȱ󶨳���";
				return;
			}
			
			//ͬһ���Ƴ���ֻ���Լ���һ���汾
			List<Map<String, Object>> list2 = resourceService.getBingProgramVersion(selectProgramId);
			if(list2.size()>0){
				success ="ͬһ������ֻ���Լ���һ���汾��";
				return;
			}
			
		}
		
		
	}
	/**
	 * �������
	 */
	public void activityProgramInfo(){
		success ="";
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			success = resourceService.activityProgram(selectProgramId,"1");
			query();
		}
	}
	
	/**
	 * ȡ���������
	 */
	public void notActivityProgramInfo(){
		success ="";
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			success = resourceService.activityProgram(selectProgramId,"2");
			query();
		}
	}
	
	/**
	 * �ϴ��ļ�
	 * @param event
	 * @throws Exception
	 */
	public void listener(FileUploadEvent event) throws Exception{
		
	  file=null;
	  file = event.getUploadedFile();
	  String fileName = file.getName();
	  String extension = fileName.substring(fileName.lastIndexOf(".")+1);
	  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssss");
	  fileUrl = df.format(new Date())+"."+extension;
	  
	  if(null!=file){
		if(fileUrl.equals("")){
			FacesMessage msg = new FacesMessage("�����ļ����","���ʧ�ܣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		 String realPath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/static/program/");
		 HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
      	 Map<String, String> mmm = StringUtils.getUrl(request.getServletContext());
      	 String realHttpUrl = mmm.get("mocsURL").toString()+"/mocs/program/";
		 File file1= new File(realHttpUrl);
		 if(!file1.exists()){
			file1.mkdir();
		 }
		 File imageFile = new File(realPath, fileUrl);  
		 try {
			FileOutputStream fops = new FileOutputStream(imageFile);
			   // ���ϴ����ļ���Ϣ���浽��Ӧ���ļ�Ŀ¼��
			   fops.write(file.getData());
			   fops.close();
		  } catch (Exception ex){
			    FacesMessage msg = new FacesMessage("�����ļ����","���ʧ�ܣ�");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
		  }
		//��ȡnodeId
		
			
		 Md5 getMD5 = new Md5();
         String Md5String = getMD5.GetMD5Code(file.getData());

		 addTpro.setProgName(fileName);
		 addTpro.setProgNo(fileName);
		 addTpro.setProgramPath(fileUrl);
		 //addTpro.setContent(file.getData());
		 addTpro.setMd5(Md5String.toUpperCase());
		 //���ð汾 
		
		 List<Map<String,Object>> list = 
				 resourceService.getProgramVersion(fileName,nodeid);
         if(list.size()>0){
        	 if(null!=list.get(0).get("versionNo")){
        		 String str =list.get(0).get("versionNo").toString();
        		 addTpro.setVersionNo("v"+(Integer.valueOf(str.substring(1))+1));
        	 }else{
        		 addTpro.setVersionNo("v1");
        	 }
         }else{
        	 addTpro.setVersionNo("v1");
         }
	  }
	  
	}
	
	
	/**
	 * ��ȡ������Ϣ
	 */
	public void getProgramInfo(){
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			List<Map<String,Object>> list = resourceService.getProgramInfo(selectProgramId);
			for(Map<String,Object> map:list){
				progName =map.get("name").toString();
				versionNo = map.get("versionNo").toString();
				if(null !=map.get("des")){
					des = map.get("des").toString();
				}else{
					des ="";
				}
				
				if(null !=map.get("partNo")){
					materialNo = map.get("partNo").toString();
				}else{
					materialNo ="";
				}
				
				if(null !=map.get("processNo")){
					processName = map.get("processNo").toString();
				}else{
					processName ="";
				}
				
			}
			bingProgramList.clear();
			bingProgramList = resourceService.getProgramMappingList(selectProgramId);
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
		//tprolist = resourceService.getTprogramInfo(null,nodeid);
		//mediumTprogramModel = new TprogramInfoModel(tprolist);
		programList =resourceService.getProgramData(null,nodeid);
		for(Map<String,Object> map:programList ){
			if(null!= map.get("status")){
				if(map.get("status").toString().equals("N")){
					map.put("activityFlag", "");
					map.put("notActivityFlag", "none");
				}else{
					map.put("activityFlag", "none");
					map.put("notActivityFlag", "");
				}
			}else{
				map.put("activityFlag", "none");
				map.put("notActivityFlag", "none");
			}
		}
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

	public String getSelectProgramId() {
		return selectProgramId;
	}

	public void setSelectProgramId(String selectProgramId) {
		this.selectProgramId = selectProgramId;
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public List<Map<String, Object>> getProgramList() {
		return programList;
	}

	public void setProgramList(List<Map<String, Object>> programList) {
		this.programList = programList;
	}

	public String getProgName() {
		return progName;
	}

	public void setProgName(String progName) {
		this.progName = progName;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public Boolean getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(Boolean activityFlag) {
		this.activityFlag = activityFlag;
	}

	public List<Map<String, Object>> getBingProgramList() {
		return bingProgramList;
	}

	public void setBingProgramList(List<Map<String, Object>> bingProgramList) {
		this.bingProgramList = bingProgramList;
	}
	
	
	

}
