package smtcl.mocs.beans.authority.menu;

import java.util.List;

import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.jasmine2.web.controls.UserControl;

import smtcl.mocs.beans.authority.secure.LoginUser;

import smtcl.mocs.pojos.authority.Application;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-8
 * Time: обнГ5:36
 */
public class MenuUC extends UserControl {
    protected List<Application> applications;
    
    protected String target;

    public MenuUC () {
        resourceName = "menu";
    }

    public String getTarget () {
        return target;
    }

    public void setTarget (String target) {
        this.target = target;
    }

    @Override
    public void onPagePreload (Page page) throws EventException {
    }

    @Override
    public void onPageLoad (Page page) throws EventException {
    }

    @Override
    public void onPageLoadCompleted (Page page) throws EventException {
        try {
            loadData ();
            dataBind ();
        } catch (Throwable throwable) {
            throw new EventException (throwable);
        }
    }

    private void loadData () {
        LoginUser user = SessionHelper.loginUser (session);
        String key  = SessionHelper.modKey(request);
        if (user != null) {
        	if(user.getApplications() == null)
        		user.init(request);
            applications = user.getApplications();
            
            for (Application app : applications) {
				for (Module m : app.getModules()) {
					for (smtcl.mocs.pojos.authority.Page p : m.getPages()) {
						p.setCurrent(false);
						if(p.getUrl().indexOf(key) > -1)
							p.setCurrent(true);
					}
				}
			}
            
        }
    }
}