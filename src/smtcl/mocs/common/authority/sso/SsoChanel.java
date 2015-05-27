package smtcl.mocs.common.authority.sso;

import smtcl.mocs.utils.authority.IConsant;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 对应servlet的url:/sso/sso-chanel
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-9-29
 * Time: 下午2:23
 */
public class SsoChanel extends HttpServlet {
    @Override
    @SuppressWarnings ("unchecked")
    protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext application = getServletContext ();
        SsoToken token;
        synchronized (SsoScanner.locker) {
            Map<String, SsoToken> map = (Map<String, SsoToken>) application.getAttribute (IConsant.SSO_TOKEN_KEY);
            if (map == null || map.size () == 0)
                throw new ServletException ("SSO login not supported");

            String fingerPrint = request.getParameter ("token");
            token = map.get (fingerPrint);
        }
        if (token == null)
            throw new ServletException ("finger print not found!");

        if (token.getTimestamp () < System.currentTimeMillis ())
            throw new ServletException ("finger print has expired!");

        Object user = token.getUserData ();
        ISsoSerializer serializer = new JsonSerializer ();
        String result = serializer.serialize (user);
        response.setContentType ("text/plain;charset=utf-8");
        response.setContentLength (result.getBytes ("utf-8").length);
        response.setCharacterEncoding ("utf-8");
        response.getWriter ().write (result);
        response.flushBuffer ();
    }
}