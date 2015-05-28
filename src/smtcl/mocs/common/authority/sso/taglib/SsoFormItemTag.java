package smtcl.mocs.common.authority.sso.taglib;

import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.RequestWrapper;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-9
 * Time: 下午6:06
 */
public class SsoFormItemTag extends SimpleTagSupport {
    private String var;

    public String getVar () {
        return var;
    }

    public void setVar (String var) {
        this.var = var;
    }

    @Override
    public void doTag () throws JspException, IOException {
        JspFragment body = getJspBody ();
        if (body != null) {
            JspContext context = getJspContext ();
            RequestWrapper wrapper = (RequestWrapper) context.getAttribute (IConsant.LOGIN_FORM_DATA, PageContext.REQUEST_SCOPE);
            if (wrapper != null) for (String name : wrapper.keySet ()) {
                String[] values = wrapper.getParameterValues (name);
                if (values != null) for (String value : values) {
                    NameValuePair pair = new NameValuePair (name, value);
                    context.setAttribute (var, pair);
                    body.invoke (null);
                }
            }
        }
    }
}