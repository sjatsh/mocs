package smtcl.mocs.beans.authority.menu;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.jasmine2.web.controls.UserControl;

import smtcl.mocs.beans.authority.secure.LoginUser;

import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-8
 * Time: ÏÂÎç6:36
 */
public class LoginStatusUC extends UserControl {
    protected boolean loggedIn;
    protected User user;
    protected String path;

    public LoginStatusUC () {
        resourceName = "application";
    }

    @Override
    public void onPagePreload (Page page) throws EventException {
        super.onPagePreload (page);
        String p = SessionHelper.modName(request);
        path = "";
        if(StringUtils.isNotBlank(p)){
        	path = "<li class=\"new_header_li1\"><a href=\"#\" class=\"new_header_link2\">" + p + "</a></li>";
        }
    }

    @Override
    public void onPageLoad (Page page) throws EventException {
        LoginUser user = SessionHelper.loginUser (session);
        if (user != null) {
            loggedIn = true;
            this.user = user.getUser ();
        }
    }

    @Override
    public void onPageLoadCompleted (Page page) throws EventException {
        try {
            dataBind ();
        } catch (Throwable throwable) {
            throw new EventException (throwable);
        }
    }
}