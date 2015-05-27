package smtcl.mocs.beans.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.common.device.JsonResponseResult;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;
/**
 * 菜单信息获取
 * @author Jf
 *
 */
@ManagedBean(name="menuBean")
@SessionScoped
public class MenuBean implements Serializable{
	
	private String nowSelectMenuItem;
	private String navigationURL;
	
	/**
	 * 当前菜单集合
	 */
	private List<Module> menuList=new ArrayList<Module>();
	
	/**
	 * 菜单集合JSON字符串--新
	 */
	private String menuJsonStr;
	
	/**
	 * 权限接口实例
	 */
	private IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory.getBean("authorizeService");

	public void getSelectItem(String moduleId){
		if(moduleId!=null){
			this.nowSelectMenuItem=moduleId;
		}
	}
	
	public MenuBean(){
		//yt 添加
		TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		/********旧菜单模式*************
		menuList = (List<Module>) authorizeService.getMenu(user.getUserId(),"mocs",null);
		Collections.sort(menuList, new Comparator<Module>()  
			      {public int compare(Module o1, Module o2) { return o1.getSeq().compareTo(o2.getSeq());}});
		*/      
		//yt 新的功能菜单调整
		List<Map<String,Object>> menuList=authorizeService.getMenu(user.getUserId(), "mocs");
		JsonResponseResult json=new JsonResponseResult();
		json.setContent(menuList);
		this.menuJsonStr=json.toJsonString();
		System.err.println(this.menuJsonStr);
		
		//清除cookie
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		StringUtils.delCookie(request, response, "menuActive");
		
		//yt 控制首页面
		if(menuList!=null&&menuList.size()>0)
		 {
			 System.err.println(menuList.get(0).get("url")); 
			 
			 if(menuList.get(0)!=null)
			 {
			   if(menuList.get(0).get("moduleId")!=null)
			   {
//				  nowSelectMenuItem=menuList.get(0).get("moduleId").toString();
				  nowSelectMenuItem="0";//默认第一个模块
			   }
			   if(menuList.get(0).get("url")!=null&&!"/".equals(menuList.get(0).get("url").toString()))
			      navigationURL=menuList.get(0).get("url").toString();
			   else{
				   //取该模块下子菜单
				   List<Map<String,Object>> pages=(List<Map<String,Object>>)menuList.get(0).get("pages");
				   if(pages!=null&&pages.size()>0)
				   {
					   if(pages.get(0).get("url")!=null) 
					   {
						   navigationURL=pages.get(0).get("url").toString();
						 //  nowSelectMenuItem=pages.get(0).get("pageId").toString();
						   nowSelectMenuItem="0-0";//默认第一个模块下第一个页面
					   }
				   }
			   }
		}
	 }
	}

	public String getNowSelectMenuItem() {
		
		return nowSelectMenuItem;
	}

	public void setNowSelectMenuItem(String nowSelectMenuItem) {
		this.nowSelectMenuItem = nowSelectMenuItem;
	}

	public List<Module> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Module> menuList) {
		this.menuList = menuList;
	}

	public String getMenuJsonStr() {
		return menuJsonStr;
	}

	public void setMenuJsonStr(String menuJsonStr) {
		this.menuJsonStr = menuJsonStr;
	}
	
	public String getNavigationURL() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = request.getParameter("page");
		if(url!=null){
			navigationURL=url;
		}
		navigationURL=navigationURL.replace("/mocs/", "/");
		return navigationURL;
	}

	public void setNavigationURL(String navigationURL) {
		this.navigationURL = navigationURL;
	}
	
}
