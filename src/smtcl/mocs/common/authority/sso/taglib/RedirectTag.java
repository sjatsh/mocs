package smtcl.mocs.common.authority.sso.taglib;

import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.RequestWrapper;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-8
 * Time: 上午11:47
 */
public class RedirectTag extends SimpleTagSupport {
    public static final String AUTO_REDIRECT_FORM = "__auto_form";
    @Override
    public void doTag () throws JspException, IOException {
        JspContext context = getJspContext ();
        RequestWrapper wrapper = (RequestWrapper) context.getAttribute (IConsant.LOGIN_FORM_DATA, PageContext.REQUEST_SCOPE);
        String action = (String) context.getAttribute (IConsant.LOGIN_LOCATION, PageContext.REQUEST_SCOPE);
        String method = (String) context.getAttribute (IConsant.LOGIN_METHOD_KEY, PageContext.REQUEST_SCOPE);

        if ("get".equalsIgnoreCase (action)) return;

        StringBuilder builder = new StringBuilder ();
        builder.append (String.format ("<form action='%s' method='%s' style='display:none' id='%s'>%n", action, method, AUTO_REDIRECT_FORM));
        for (String name : wrapper.keySet ()) {
            String[] values = wrapper.getParameterValues (name);
            for (String value : values) {
                builder.append (String.format ("<input type='hidden' value='%s' name='%s' />%n", escape (value), name));
            }
        }
        builder.append ("</form>\r\n");
        JspWriter writer = context.getOut ();
        writer.print (builder);
        writer.flush ();
    }

    private String escape (String text) {
        return text.replace ("'", "&#39;");
    }
}