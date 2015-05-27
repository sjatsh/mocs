package smtcl.mocs.beans.authority.secure.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import smtcl.mocs.utils.authority.RequestWrapper;
import org.apache.commons.lang.StringUtils;


import smtcl.mocs.utils.authority.IConsant;

/**
 * HttpServletFilter 的实现类.
 *
 * 用于过滤用户是否登录
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 11-1-11
 * Time: 下午2:51
 */
public class LoginFilter implements Filter {
    private Set<String> excludes = new HashSet<String> ();
    private String notLoginPage;
    private String sessionLoginKey;

    /**
     * 过滤器初始化.
     *
     * 当过滤器初始化时，从配置中读取不过滤的参数列表，以备后用
     *
     * @param config ServletConfig 实例
     * @throws ServletException 异常
     */
    public void init (FilterConfig config) throws ServletException {
        String excludeList = config.getInitParameter ("exclude-list");
        if (StringUtils.isEmpty (excludeList)) excludeList = "/login.jsp";
        String[] a = excludeList.split (";");
        excludes.addAll (Arrays.asList (a));

        notLoginPage = config.getInitParameter ("not-login-handler");
        if (notLoginPage != null && notLoginPage.charAt (0) != '/')
            notLoginPage = '/' + notLoginPage;

        sessionLoginKey = config.getInitParameter ("session.login.key");
        if (sessionLoginKey == null || sessionLoginKey.trim ().length () == 0)
            sessionLoginKey = IConsant.SESSION_LOGINUSER_KEY;
    }

    /**
     * 过滤.
     *
     * 判断用户是否已经等率，若为登录，跳转到登录页?
     *
     * @param req HttpServletRequest实例
     * @param resp HttpServletResponse 实例
     * @param chain FilterChain实例
     * @throws IOException IO异常
     * @throws ServletException 异常
     */
    public void doFilter (ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = getRequestName (request);
        if (!excludes.contains (path)) {
            HttpSession session = request.getSession ();
            if (session.getAttribute (sessionLoginKey) == null) {
                response.reset ();
                response.resetBuffer ();
                String contextPath = request.getContextPath ();
                if(StringUtils.isNotBlank(contextPath))
	                if (contextPath.charAt (contextPath.length () - 1) == '/')
	                    contextPath = contextPath.substring (0, contextPath.length () - 1);

                String method = request.getMethod ();
                if ("post".equalsIgnoreCase (method)) {
                    RequestWrapper wrapper = new RequestWrapper (request);
                    session.setAttribute (IConsant.LOGIN_FORM_DATA, wrapper);
                }

                session.setAttribute (IConsant.LOGIN_LOCATION, request.getRequestURI ());
                session.setAttribute (IConsant.LOGIN_METHOD_KEY, request.getMethod ().toUpperCase ());
                response.sendRedirect (contextPath + notLoginPage);
                return;
            }
        }
        chain.doFilter (request, response);
    }

    public void destroy () {

    }

    private String getRequestName (HttpServletRequest request) {
        String path = request.getServletPath ();
        int pos = path.indexOf ('?');
        if (pos != -1) path = path.substring (0, pos);
        if (path.charAt (0) != '/') path = '/' + path;
        return path;
    }
}