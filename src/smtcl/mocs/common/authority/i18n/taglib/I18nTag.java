package smtcl.mocs.common.authority.i18n.taglib;

import org.dreamwork.i18n.IResourceAdapter;
import org.dreamwork.i18n.IResourceManager;
import org.dreamwork.i18n.MissingResourceException;
import org.dreamwork.jasmine2.i18n.I18nUtil;
import org.dreamwork.peony.DataBinder;
import org.dreamwork.util.StringUtil;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.dreamwork.jasmine2.configure.JasmineConfig.*;
import static javax.servlet.jsp.PageContext.*;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-1
 * Time: ÏÂÎç9:46
 */
public class I18nTag extends SimpleTagSupport {
    private static final String LOCALE_KEY = "dreamwork.jasmine2.locale";
    private static final Pattern CONTEXT_PATTERN = Pattern.compile ("^\\((.*?)\\)(\\.(.*?))*$");
    private String resource;
    private String expression;

    public String getResource () {
        return resource;
    }

    public void setResource (String resource) {
        this.resource = resource;
    }

    public String getExpression () {
        return expression;
    }

    public void setExpression (String expression) {
        this.expression = expression;
    }

    @Override
    public void doTag () throws JspException, IOException {
        JspContext context = getJspContext ();
        IResourceManager manager = (IResourceManager) context.getAttribute (JASMINE_I18N_HOLDER, APPLICATION_SCOPE);
        if (manager == null)
            throw new MissingResourceException ("I18n is not supported");

        if (StringUtil.isEmpty (expression))
            throw new MissingResourceException ("Missing i18n expression");

        Locale locale = findCurrentLocale ();
        IResourceAdapter adapter= manager.getResourceAdapter (resource);
        if (adapter == null)
            throw new MissingResourceException ("Missing resource: " + resource);

        expression = expression.trim ();
        String key, replacement;
        if (expression.indexOf (',') > 0) {
            String[] a = expression.split (",");
            key = a[0].trim ();
            Object[] parameters = buildParameters (a);
            replacement = adapter.getString (locale, key, parameters);
        } else {
            replacement = adapter.getString (locale, expression, expression);
        }
        getJspContext ().getOut ().write (replacement);
    }

    private Locale findCurrentLocale () {
        JspContext context = this.getJspContext ();
        Locale locale = (Locale) context.getAttribute (LOCALE_KEY, SESSION_SCOPE);
        if (locale == null) {
            locale = (Locale) context.getAttribute (DEFAULT_LOCALE, APPLICATION_SCOPE);
        }
        return locale;
    }

    private Object[] buildParameters (String[] array) {
        Object[] parameters = new Object[array.length - 1];
        for (int i = 1; i < array.length; i ++) {
            String key = array [i].trim ();
            if (I18nUtil.isNumber (key)) {
                parameters [i - 1] = key;
                continue;
            }

            Matcher m = CONTEXT_PATTERN.matcher (key);
            if (m.matches ()) {
                Object value = evalContextAttribute (m, key);
                if (value != null) {
                    parameters [i - 1] = value;
                    continue;
                }
            }

            Object value = getJspContext ().findAttribute (key);
            if (value != null) {
                parameters [i - 1] = value;
                continue;
            }

            parameters [i - 1] = key;
        }

        return parameters;
    }

    private Object evalContextAttribute (Matcher m, String expression) {
        String attrName = m.group (1);
        if (attrName == null) return null;

        Object container = getJspContext ().findAttribute (attrName);
        if (container == null) return null;

        int pos = expression.indexOf (").");
        if (pos == -1) return container;

        expression = expression.substring (pos + 2);

        return DataBinder.eval (container, expression);
    }
}