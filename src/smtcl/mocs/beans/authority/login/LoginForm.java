package smtcl.mocs.beans.authority.login;

import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.RequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-9-29
 * Time: ÏÂÎç4:49
 */
public class LoginForm {
    private HttpServletRequest request;

    public HttpServletRequest getRequest () {
        return request;
    }

    public void setRequest (HttpServletRequest request) {
        this.request = request;
    }

    public String getLoginForm () {
        RequestWrapper wrapper = (RequestWrapper) request.getAttribute (IConsant.LOGIN_FORM_DATA);
        String action = (String) request.getAttribute (IConsant.LOGIN_LOCATION);
        String method = (String) request.getAttribute (IConsant.LOGIN_METHOD_KEY);

        StringBuilder builder = new StringBuilder ();
        builder.append (String.format ("<form action='%s' method='%s' style='display:none' id='__auto_form'>%n", action, method));
        for (String name : wrapper.keySet ()) {
            String[] values = wrapper.getParameterValues (name);
            for (String value : values) {
                builder.append (String.format ("<input type='hidden' value='%s' name='%s' />%n", escape (value), name));
            }
        }
        builder.append ("</form>\r\n");
        return builder.toString ();
    }

    @SuppressWarnings ("unchecked")
    public String getSSOTargets () {
        List<String> targets = (List<String>) request.getAttribute (IConsant.SSO_TARGET_KEY);
        StringBuilder builder = new StringBuilder ();
        if (targets != null) for (String target : targets) {
            builder.append (String.format ("<script type='text/javascript' charset='utf-8' src='%s'></script>%n", target));
        }
        return builder.toString ();
    }

    private String escape (String text) {
        return text.replace ("'", "&#39;");
    }
}