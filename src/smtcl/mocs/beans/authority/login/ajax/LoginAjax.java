package smtcl.mocs.beans.authority.login.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dreamwork.jasmine2.ajax.RequestMethod;
import org.dreamwork.jasmine2.annotation.AjaxMethod;
import org.dreamwork.jasmine2.annotation.AjaxParameter;
import org.dreamwork.jasmine2.annotation.AjaxService;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.misc.AlgorithmUtil;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.common.authority.sso.SsoScanner;
import smtcl.mocs.common.authority.sso.SsoToken;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.services.authority.IUserService;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-9
 * Time: 下午4:58
 */
@AjaxService ("login")
public class LoginAjax {
    @AjaxMethod (method = RequestMethod.POST)
    public String login (@AjaxParameter (name="userName") String userName, @AjaxParameter (name="password") String password) {
        HttpContext context = HttpContext.current ();
        HttpServletRequest request = context.getRequest ();
        HttpServletResponse response = context.getResponse ();
        HttpSession session = context.getSession ();
        ServletContext application = context.getApplication ();

        Parameter p_user_name = new Parameter ("loginName", userName, Operator.EQ);
        IUserService us = (IUserService) ServiceFactory.getBean ("userService");
        User user = us.get (p_user_name);

        if (user != null) {
            try {
                String encode = AlgorithmUtil.md5 (password).toUpperCase ();
                if (user.getPassword ().equals (encode)) {
                    saveUser (user, request, application);
                    Boolean b = (Boolean) context.getAttribute (IConsant.SSO_SUPPORTED_KEY);
                    boolean ssoSupported = b != null && b;
                    if (ssoSupported) {
                        RequestDispatcher dispatcher = processSSO (user, request, application);
                        dispatcher.forward (request, response);
                    }
                    return "{status:'ok'}";
                }
            } catch (Exception ex) {
                ex.printStackTrace ();
//                throw new RuntimeException (ex);
            } finally {
                session.removeAttribute (IConsant.LOGIN_LOCATION);
                session.removeAttribute (IConsant.LOGIN_METHOD_KEY);
                session.removeAttribute (IConsant.LOGIN_FORM_DATA);
            }
        }


        return "{status:'fail'}";
    }

    @AjaxMethod (method = RequestMethod.POST)
    public String changePassword (
            @AjaxParameter (name="oldPassword") String oldPassword,
            @AjaxParameter (name="newPassword") String newPassword
    ) {
        HttpContext context = HttpContext.current ();
        LoginUser lu = SessionHelper.loginUser (context.getSession ());
        if (lu == null) {
            return "{status:'fail',cause:'not-login-in'}";
        }

        IUserService service = (IUserService) ServiceFactory.getBean ("userService");
        User user = service.get (lu.getUser().getUserId());
        try {
            String encoded = AlgorithmUtil.md5 (oldPassword).toUpperCase ();
            if (!encoded.equals (user.getPassword ())) {
                return "{status:'fail',cause:'old-password'}";
            }

            // change password
            encoded = AlgorithmUtil.md5 (newPassword);
            user.setPassword (encoded.toUpperCase());
            
            service.update (user);

            return "{status:'ok'}";
        } catch (Exception ex) {
            ex.printStackTrace ();
            return "{status:'fail',cause:'internal error'}";
        }
    }

    @SuppressWarnings ("unchecked")
    private void saveUser (User user, HttpServletRequest request, ServletContext context) {
    	HttpSession session = request.getSession ();

        //新增LoginUser对象;
        LoginUser loginUser = new LoginUser(user, request);
        session.setAttribute (IConsant.SESSION_LOGINUSER_KEY, loginUser);  
        
        Map<String, LoginUser> userMap = (Map<String, LoginUser>) context.getAttribute (IConsant.LOGINUSER_MAP_KEY);
        if (userMap == null) {
            userMap = new HashMap<String, LoginUser> ();
            context.setAttribute (IConsant.LOGINUSER_MAP_KEY, userMap);
        }
        userMap.put (user.getUserId(), loginUser);
    }

    @SuppressWarnings ("unchecked")
    private RequestDispatcher processSSO (User user, HttpServletRequest request, ServletContext application) {
        String processor = (String) application.getAttribute (IConsant.SSO_PROCESSOR_KEY);
        String callback = (String) application.getAttribute (IConsant.SSO_CALLBACK_KEY);
        List<String> targets = (List<String>) application.getAttribute (IConsant.SSO_TARGET_KEY);

        RequestDispatcher dispatcher = request.getRequestDispatcher (processor);
        SsoToken token = new SsoToken (user, 600);
        String fingerPrint = token.getToken ();
        synchronized (SsoScanner.locker) {
            Map<String, SsoToken> map = (Map<String, SsoToken>) application.getAttribute (IConsant.SSO_TOKEN_KEY);
            if (map == null) {
                map = new HashMap<String, SsoToken> ();
                application.setAttribute (IConsant.SSO_TOKEN_KEY, map);
            }

            map.put (fingerPrint, token);
        }
        List<String> peers = new ArrayList<String> (targets.size ());
        for (String target : targets) {
            peers.add (String.format ("%s?token=%s&callback=%s", target, fingerPrint, callback));
        }
        request.setAttribute (IConsant.SSO_TARGET_KEY, peers);
        return dispatcher;
    }
}
