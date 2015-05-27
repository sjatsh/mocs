package smtcl.mocs.common.device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dreamwork.gson.GsonHelper;

import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

/**
 * SSO登录处理
 * @创建时间 2012-12-21
 * @作者 余涛
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@SuppressWarnings("serial")
public class SsoChanelLoginServlet extends HttpServlet {
    @Override
    protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String token = request.getParameter ("token");
        String target = request.getParameter ("callback");
        String action=request.getParameter("action");
        response.setContentType ("text/javascript;charset=utf-8");
        response.setHeader("P3P" , "CP=CAO PSA OUR" );

        //用户注销 SSO
        if(!StringUtils.isEmpty(action)&&action.equals("logout")){
        	HttpSession session = request.getSession ();
        	session.removeAttribute(Constants.USER_SESSION_KEY);
        }
        
        if (StringUtils.isEmpty (target) || StringUtils.isEmpty (token)) {
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

        String json = EntityUtils.toString (resp.getEntity ());
        User user =  GsonHelper.getGson().fromJson(json, User.class);
        HttpSession session = request.getSession ();
        
        if(session.getAttribute("MenuHeadBean")!=null){
        	session.removeAttribute("MenuHeadBean");
        }
        
        TUser userinfo=new TUser();
        userinfo.setLoginName(user.getLoginName());
        userinfo.setOrgId(user.getUserId());
        userinfo.setUserId(user.getUserId());
       
        /**计算自动登录，登录信息保存一天**/
//        int seconds=1*24*60*60;  
//        Cookie cookie = new Cookie("user", user.getLoginName()+"=="+user.getPassword());  
//        cookie.setMaxAge(seconds);   
//        cookie.setPath("/a3-mocs");
//        response.addCookie(cookie);
      
        session.setAttribute (Constants.USER_SESSION_KEY, userinfo);   
    }
}