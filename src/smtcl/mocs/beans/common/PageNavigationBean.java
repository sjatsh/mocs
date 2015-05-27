package smtcl.mocs.beans.common;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * ����ǰ̨ҳ����תBEAN
 * 
 * @author Jf
 * 
 */
@ManagedBean(name = "pageNavigationBean")
@SessionScoped
public class PageNavigationBean implements Serializable{

	private String navigationURL = "/mocs/device/factory_profile.xhtml";
	public PageNavigationBean() {
		
	}

	/*----------------------------------------------------------------------*/
	
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