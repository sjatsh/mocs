package smtcl.mocs.common.authority.i18n.filter;

import org.dreamwork.i18n.LocaleUtil;
import org.dreamwork.jasmine2.configure.JasmineConfig;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-2
 * Time: ÏÂÎç9:54
 */
public class I18nFilter implements Filter {
    @Override
    public void init (FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession ();
        Locale current = (Locale) session.getAttribute (IWebControl.LOCALE_KEY);

        String language = request.getParameter ("__lang");
        if (!StringUtil.isEmpty (language)) {
            Locale locale = LocaleUtil.parseLocale (language);
            if (!locale.equals (current))
                session.setAttribute (IWebControl.LOCALE_KEY, locale);
        } else if (current == null) {
            ServletContext application = session.getServletContext ();
            Locale defaultLocale = (Locale) application.getAttribute (JasmineConfig.DEFAULT_LOCALE);
            session.setAttribute (IWebControl.LOCALE_KEY, defaultLocale);
        }

        chain.doFilter (request, response);
    }

    @Override
    public void destroy () {

    }
}
