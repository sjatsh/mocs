package smtcl.mocs.beans.common;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

/**
 *
 * 菜单信息获取
 * @作者 jf
 * @创建时间 2013-6-4 下午13:05:16
 * @修改者 yyh,songkaiang
 * @修改日期 2015-3-5
 * @修改说明 删除无用代码
 * @version V1.0
 */
@ManagedBean(name="menuBean")
@ViewScoped
public class MenuBean implements Serializable{
	
	private String nowSelectMenuItem;

	private String userName;

	public void getSelectItem(String moduleId){
		if(moduleId!=null){
			this.nowSelectMenuItem=moduleId;
		}
	}
	

	public MenuBean(){
		//yt 添加
		TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		userName=user.getLoginName();
	}
	
	/**
	 * 退出登录
	 */
	public void LogOut(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(Constants.USER_SESSION_KEY, null);
		session.setAttribute(Constants.APPLICATION_ID, null);
		session.setAttribute("nodeid", null);
		session.setAttribute("swg.authority.session.user", null);
		session.setAttribute("factoryProfileBean", null);
		session.setAttribute("tsControlBean", null);
		session.setAttribute("menuBean", null);
		session.setAttribute("nodeid2", null);
		//	清除cookie
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		StringUtils.saveCookie(request, response, "menuActive", "");
		StringUtils.saveCookie(request, response, "defNode", "");
	}

	public String getNowSelectMenuItem() {
		return nowSelectMenuItem;
	}

	public void setNowSelectMenuItem(String nowSelectMenuItem) {
		this.nowSelectMenuItem = nowSelectMenuItem;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
