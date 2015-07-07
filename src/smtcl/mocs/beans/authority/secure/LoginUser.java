package smtcl.mocs.beans.authority.secure;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.authority.Application;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.pojos.authority.Page;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.services.authority.IMenuService;
import smtcl.mocs.utils.authority.HelperUtil;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * @author gaokun
 * @create Sep 27, 2012 5:35:49 PM
 */
public class LoginUser {
	
	private User user;
	
	List<Application> applications;
	
	public LoginUser(User user, HttpServletRequest request){
		this.user		= user;
		
		if(request != null)
			init(request);
	}
	
	public void init(HttpServletRequest request){
		HttpSession		session		= request.getSession();
		ServletContext	application	= session.getServletContext();
		String			userId		= user.getUserId();
		IMenuService	service		= (IMenuService) ServiceFactory.getBean ("menuService");
		
		Locale locale = SessionHelper.getCurrentLocale (session);
        String language = locale == null ? null : locale.toString ();
         //临时数据处理
        if(null!=language&&language.equals("en")){
        	language=language+"_US";
        }
        if(null!=language&&language.equals("zh")){
        	language=language+"_CN";
        }
        applications = service.getSpecApplications (userId, IConsant.AUTH_APP, language);
        if (application != null && applications.size () > 0) for (Application app : applications) {
            app.setModules (HelperUtil.orderModule(service.getMenu (userId, app.getAppId (), language)));
            if (app.getModules () != null && app.getModules ().size () > 0)
                for (Module module : app.getModules ()) {
                    Collection<Page> pages = module.getPages ();
                    if (pages != null && pages.size () > 0)
                        for (Page p : pages) {
                            String url = p.getUrl ();
                            if (url.charAt (0) == '~') {
                                url = request.getContextPath () + url.substring (1);
                                p.setUrl (url);
                            }
                        }
                }
        }
	}
	
	public List<Application> getApplications() {
		return applications;
	}



//	public void setApplications(List<Application> applications) {
//		this.applications = applications;
//	}



	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
