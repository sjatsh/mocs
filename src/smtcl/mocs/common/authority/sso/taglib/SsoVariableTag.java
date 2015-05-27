package smtcl.mocs.common.authority.sso.taglib;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-8
 * Time: ÏÂÎç3:59
 */
public class SsoVariableTag extends SimpleTagSupport {
    private String var, target, type = "java.lang.Object";

    public String getVar () {
        return var;
    }

    public void setVar (String var) {
        this.var = var;
    }

    public String getTarget () {
        return target;
    }

    public void setTarget (String target) {
        this.target = target;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    @Override
    public void doTag () throws JspException, IOException {
        JspContext pageContext = getJspContext ();
        Object obj = pageContext.findAttribute (target);
        if (obj != null) {
            pageContext.setAttribute (var, obj);
            JspFragment body = getJspBody ();
            if (body != null)
                body.invoke (null);
        }
    }
}