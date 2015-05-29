package smtcl.mocs.common.authority.sso.taglib;

import smtcl.mocs.utils.authority.IConsant;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-8
 * Time: 上午11:31
 */
public class TargetTag extends SimpleTagSupport {
    @Override
    @SuppressWarnings ("unchecked")
    public void doTag () throws JspException, IOException {
        List<String> targets = (List<String>) getJspContext ().getAttribute (IConsant.SSO_TARGET_KEY, PageContext.REQUEST_SCOPE);
        StringBuilder builder = new StringBuilder ();
        if (targets != null) for (String target : targets) {
            builder.append (String.format ("<script type='text/javascript' charset='utf-8' src='%s'></script>%n", target));
        }
        getJspContext ().getOut ().print (builder.toString ());
        getJspContext ().getOut ().flush ();
    }
}