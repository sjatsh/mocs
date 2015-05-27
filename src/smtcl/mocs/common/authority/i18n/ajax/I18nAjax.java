package smtcl.mocs.common.authority.i18n.ajax;

import org.dreamwork.i18n.LocaleUtil;
import org.dreamwork.jasmine2.ajax.RequestMethod;
import org.dreamwork.jasmine2.annotation.AjaxMethod;
import org.dreamwork.jasmine2.annotation.AjaxParameter;
import org.dreamwork.jasmine2.annotation.AjaxService;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.jasmine2.web.IWebControl;

import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-1
 * Time: ÏÂÎç10:41
 */
@AjaxService ("i18n")
public class I18nAjax {
    @AjaxMethod (method = RequestMethod.POST)
    public String changeLanguage (@AjaxParameter (name = "language") String language) {
        HttpContext context = HttpContext.current ();
        Locale locale = LocaleUtil.parseLocale (language);
        HttpSession session = context.getSession ();
        Locale current = (Locale) session.getAttribute (IWebControl.LOCALE_KEY);
        if (!locale.equals (current)) {
            session.setAttribute (IWebControl.LOCALE_KEY, locale);
            return "{action:'reload'}";
        }
        return "{action:'none'}";
    }
}
