package smtcl.mocs.common.authority.sso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对应servlet的url:/sso/sso-target
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-9
 * Time: 下午4:14
 */
public class SsoTarget extends HttpServlet {
    private ISsoReceiver receiver;

    @Override
    @SuppressWarnings ("unchecked")
    public void init () throws ServletException {
        ServletConfig config = getServletConfig ();
        String className = config.getInitParameter ("sso.receiver");
        if (className == null || className.trim ().length () == 0) {
            throw new ServletException ("Can't find sso receiver");
        }

        try {
            Class<ISsoReceiver> c = (Class<ISsoReceiver>) Class.forName (className);
            receiver = c.newInstance ();
        } catch (Exception ex) {
            throw new ServletException (ex);
        }
    }

    @Override
    protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter ("token");
        String target = request.getParameter ("callback");
        response.setContentType ("text/javascript;charset=utf-8");
        // patch ie
        response.setHeader("P3P" , "CP=CAO PSA OUR" );

        if (!StringUtils.hasText (target) || !StringUtils.hasText (token)) {
            response.sendError (HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        HttpPost post = new HttpPost (target.trim ());
        List<NameValuePair> list = new ArrayList<NameValuePair> ();
        list.add (new BasicNameValuePair ("token", token));
        post.setEntity (new UrlEncodedFormEntity (list, "utf-8"));
        HttpResponse resp = new DefaultHttpClient ().execute (post);
        StatusLine line = resp.getStatusLine ();
        if (line.getStatusCode () != HttpServletResponse.SC_OK) {
            response.sendError (line.getStatusCode (), line.getReasonPhrase ());
            return;
        }

        String expression = EntityUtils.toString (resp.getEntity ());
        HttpSession session = request.getSession ();
        receiver.receive (session, expression);
    }
}