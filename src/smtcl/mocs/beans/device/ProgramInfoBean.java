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
 * 程序配置
 * @创建时间 2013-08-08
 * @作者 wangkaili
 * @修改者： liguoqiang
 * @修改日期： 2014-05-09
 * @修改说明 添加流水号，确认程序名称不重复。
 * @version V1.0
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
	 * 要新增的数据
	 */
	private TProgramInfo addTpro = new TProgramInfo();
	private String nodeid;
	
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
			if("输入编号/名称/创建人".equals(searchStr))
				searchStr=null;
			tprolist = resourceService.getTprogramInfo(searchStr,nodeid);
			mediumTprogramModel = new TprogramInfoModel(tprolist);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增方法
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
				if("输入编号/名称/创建人".equals(searchStr))
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
			if("输入编号/名称/创建人".equals(searchStr))
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
