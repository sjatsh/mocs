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
 * 程序配置
 * @创建时间 2013-08-08
 * @作者 wangkaili
 * @修改者： liguoqiang
 * @修改日期： 2014-05-09
 * @修改说明 添加流水号，确认程序名称不重复。
 * @version V1.0
 * 
 * @修改者： FW
 * @修改日期： 2015-02-12
 * @修改说明 ：大改
 * @version： V2.0
 */
@SuppressWarnings("serial")
@ManagedBean(name="ProgramInfoBean")
@ViewScoped
public class ProgramInfoBean implements Serializable{

	/**
	 * 资源接口实例
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");

	/**
	 * dataTable数据
	 */
	private List<TProgramInfo> tprolist;
	/**
	 * 程序集合
	 */
	private List<Map<String,Object>> programList = new ArrayList<Map<String,Object>>();
	/**
	 * 绑定程序
	 */
	private List<Map<String,Object>> bingProgramList = new ArrayList<Map<String,Object>>();
	/**
	 * dataTable选中行
	 */
	private TProgramInfo[] selectedProgram;
	
	/**
	 * dataTable数据实现了多行选中
	 */
	private TprogramInfoModel mediumTprogramModel;
	
	/**
	 * 查询字符串
	 */
	private String searchStr; 
	
	/**
	 * 判断是否选中的行
	 */
	private String selected;
	/**
	 * 被选择的程序ID
	 */
	private String selectProgramId;
	/**
	 * 程序名称
	 */
	private String progName;
	/**
	 * 版本
	 */
	private String versionNo;
	/**
	 * 说明
	 */
	private String des;
	/**
	 * 零件编码
	 */
	private String materialNo;
	/**
	 * 工序名称
	 */
	private String processName;
	/**
	 * 激活FLAG
	 */
	private Boolean activityFlag;
	
	/**
	 * 要新增的数据
	 */
	private TProgramInfo addTpro = new TProgramInfo();
	private String nodeid;
	private UploadedFile file =null;
	private String fileUrl;
	private String success;
	
	/**
	 * 构造方法
	 */
	public ProgramInfoBean(){
		
	}
	
	/**
	 * 查询方法
	 */
	public void query()
	{
		try {
			if("输入名称/版本".equals(searchStr))
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
	 * 新增方法
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
			addTpro.setStatus("N");//状态
			String t = resourceService.saveTprogramInfo(addTpro);
			if(t.equals("添加成功")){
				FacesMessage msg = new FacesMessage("程序信息添加","添加成功！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else if(t.equals("已存在")){
				FacesMessage msg = new FacesMessage("程序信息添加","数据已存在！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				FacesMessage msg = new FacesMessage("程序信息添加","添加失败！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			try {
				if("输入名称/版本".equals(searchStr))
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
	 * 更新程序
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
		if(t.equals("更新成功")){
			FacesMessage msg = new FacesMessage("程序信息更新","更新成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(t.equals("已存在")){
			FacesMessage msg = new FacesMessage("程序信息更新","已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("程序信息更新","更新失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * 修改程序保存
	 */
	public void editProgramInfo(){
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			
			TProgramInfo tpro =new TProgramInfo();
			tpro.setId(Long.valueOf(selectProgramId));
			tpro.setDescribe2(des);
			
			String t = resourceService.updateTprogramInfo(tpro);
			if(t.equals("更新成功")){
				FacesMessage msg = new FacesMessage("程序信息更新","更新成功！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				query();
			}else{
				FacesMessage msg = new FacesMessage("程序信息更新","更新失败！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} 
		}
	}
	
	/**
	 * 取消
	 */
	public void onCancel(RowEditEvent event){
		
	}
	
	public void onSelected(){
	    for(TProgramInfo tt:selectedProgram){
	    	selected=tt.getId().toString();
	    }
	}
	
	/**
	 * 删除方法
	 */
	public void deleteTprogramInfo(){
		for(TProgramInfo tpr : selectedProgram){
			resourceService.deleteTprogramInfo(tpr);
		}
		try {
			if("输入名称/版本".equals(searchStr))
				searchStr=null;
			tprolist = resourceService.getTprogramInfo(searchStr,nodeid);
			mediumTprogramModel = new TprogramInfoModel(tprolist);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 程序删除前进行check
	 */
	public void checkDeleteProgram(){
		success ="";
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			List<Map<String, Object>> list = resourceService.getBingProgramData(selectProgramId);
			if(list.size()>0){
				success ="该程序已绑定,不可以删除!";
			}
		}
	}
	/**
	 * 删除程序
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
	 * 程序激活前进行check
	 */
	public void checkActivityProgram(){
		success ="";
		if(null==selectProgramId ||selectProgramId.equals("")){
			return;
		}else{
			List<Map<String, Object>> list = resourceService.getBingProgramData(selectProgramId);
			if(list.size()>0){
				
			}else{
				success ="请先绑定程序！";
				return;
			}
			
			//同一名称程序只可以激活一个版本
			List<Map<String, Object>> list2 = resourceService.getBingProgramVersion(selectProgramId);
			if(list2.size()>0){
				success ="同一个程序只可以激活一个版本！";
				return;
			}
			
		}
		
		
	}
	/**
	 * 激活程序
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
	 * 取消激活程序
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
	 * 上传文件
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
			FacesMessage msg = new FacesMessage("程序文件添加","添加失败！");
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
			   // 将上传的文件信息保存到相应的文件目录里
			   fops.write(file.getData());
			   fops.close();
		  } catch (Exception ex){
			    FacesMessage msg = new FacesMessage("程序文件添加","添加失败！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
		  }
		//获取nodeId
		
			
		 Md5 getMD5 = new Md5();
         String Md5String = getMD5.GetMD5Code(file.getData());

		 addTpro.setProgName(fileName);
		 addTpro.setProgNo(fileName);
		 addTpro.setProgramPath(fileUrl);
		 //addTpro.setContent(file.getData());
		 addTpro.setMd5(Md5String.toUpperCase());
		 //设置版本 
		
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
	 * 获取程序信息
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
