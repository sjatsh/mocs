package smtcl.mocs.beans.authority.secure.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dreamwork.misc.AlgorithmUtil;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.StringUtil;

import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.services.authority.IUserService;
import smtcl.mocs.common.authority.sso.SsoScanner;
import smtcl.mocs.common.authority.sso.SsoToken;
import smtcl.mocs.utils.authority.HelperUtil;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.RequestWrapper;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * 登录处理?
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 11-1-11
 * Time: 下午1:23
 */
public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String homepage, failHandler;
    private boolean ssoSupported = false;

    private static final Logger logger = Logger.getLogger (LoginServlet.class);

    /**
     * 初始化servlet.
     * @throws ServletException
     */
    @Override
    public void init () throws ServletException {
        ServletConfig config = getServletConfig ();
        homepage = config.getInitParameter ("home-page");
        if (StringUtil.isEmpty (homepage)) homepage = "/";
        if(!HelperUtil.isAbsoluteURL(homepage))
        	if (homepage.charAt (0) != '/') homepage = '/' + homepage;

        failHandler = config.getInitParameter ("login-fail-handler");
        if (StringUtil.isEmpty (failHandler)) failHandler = "/";
        if (failHandler.charAt (0) != '/') failHandler = '/' + failHandler;

        ServletContext context = getServletContext ();
        Boolean b = (Boolean) context.getAttribute (IConsant.SSO_SUPPORTED_KEY);
        ssoSupported = b != null && b;
    }

    /**
     * 根据用户提交的用户名和口令，对数据库中的数据进行比对.
     *
     * 若比对成功，则在会话中记录用户信息并跳转到系统首页；
     * 否则提示登录失败的原?
     * @param request Http Request
     * @param response Http Response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter ("userName");
        String password = request.getParameter ("password");
        String action = request.getParameter ("action");

        String contentPath = request.getContextPath ();
        if (!StringUtil.isEmpty (contentPath) && contentPath.charAt (contentPath.length () - 1) == '/')
            contentPath = contentPath.substring (0, contentPath.length () - 1);

        String page = null;
        if(HelperUtil.isAbsoluteURL(homepage)){
        	page = homepage;
        }else{
        	page = contentPath + homepage;
        }
        //start
        //判断是否从界面login.jsp登录，而不是从在线工厂跳转的,也修改了login.jsp页面,homepage从web.xml获取
        String formLoginPage = request.getParameter ("formLoginPage");
        if(formLoginPage!=null&&!"".equals(formLoginPage)){
        	page = contentPath + "/index.faces";
        }
        //end
        HttpSession session = request.getSession ();

        if ("logout".equalsIgnoreCase (action)) {
            logout (request, response);
            return;
        }
        
        Parameter p_user_name = new Parameter ("loginName", userName, Operator.EQ);
        Parameter p_state_name = new Parameter ("status", new Long(IConsant.INVALID), Operator.NE);

        IUserService us = (IUserService)ServiceFactory.getBean("userService");
        User user = us.get (p_user_name, p_state_name);

        if (user != null) {
            try {
                String encode = AlgorithmUtil.md5 (password).toUpperCase ();
                if (user.getPassword ().equals (encode)) {
                    saveUser (user, request, getServletContext ());
                    String next = request.getParameter ("location");
                    if (next == null)
                        next = (String) session.getAttribute (IConsant.LOGIN_LOCATION);
                    
                    logger.warn("location = " + next);

                    String method = (String) session.getAttribute (IConsant.LOGIN_METHOD_KEY);
                    if (method == null)
                        method = "GET";
                    if (StringUtil.isEmpty (next)) next = page;
                    if (StringUtil.isEmpty (method)) method = "GET";

                    request.setAttribute (IConsant.LOGIN_LOCATION, next);
                    request.setAttribute (IConsant.LOGIN_METHOD_KEY, method.toUpperCase ());

                    if ("post".equalsIgnoreCase (method)) {
                        // rebuild post form in target page.
                        RequestWrapper wrapper = (RequestWrapper) session.getAttribute (IConsant.LOGIN_FORM_DATA);
                        if (wrapper != null) {
                            request.setAttribute (IConsant.LOGIN_FORM_DATA, wrapper);
                        }
                    }

                    if (ssoSupported) {
                        logger.warn("sso supported!");
                        RequestDispatcher dispatcher = processSSO (user, request);
                        dispatcher.forward (request, response);
                        LogHelper.log(user.getLoginName(), SessionHelper.getIpAddr(request), null, "ssologin", ILogFinal.LOG_SUCCESS);
                        return;
                    }
                    if ("post".equalsIgnoreCase (method)) {
                        ServletContext context = getServletContext ();
                        String processor = (String) context.getAttribute (IConsant.SSO_PROCESSOR_KEY);
                        RequestDispatcher dispatcher = request.getRequestDispatcher (processor);
                        dispatcher.forward (request, response);
                    } else {
                        response.sendRedirect (next);
                    }
                    
                    LogHelper.log(user.getLoginName(), SessionHelper.getIpAddr(request), null, "login", ILogFinal.LOG_SUCCESS);
                    logger.info(String.format("com.swg.authority: %s login success!", user.getLoginName()));
                    return;
                }else{
                	LogHelper.log(userName, SessionHelper.getIpAddr(request), null, "user name or password error!", ILogFinal.LOG_FAIL);
                }
            } catch (NoSuchAlgorithmException e) {
            	LogHelper.log(user.getLoginName(), SessionHelper.getIpAddr(request), null, "login", ILogFinal.LOG_FAIL);
                throw new ServletException (e);
            } finally {
                session.removeAttribute (IConsant.LOGIN_LOCATION);
                session.removeAttribute (IConsant.LOGIN_METHOD_KEY);
                session.removeAttribute (IConsant.LOGIN_FORM_DATA);
            }
        }else{
        	LogHelper.log(userName, SessionHelper.getIpAddr(request), null, "user name don't exist!", ILogFinal.LOG_FAIL);
        }
        
        if (!StringUtil.isEmpty(userName)){
        	session.setAttribute ("login.status", "fail");
        }else{
        	session.setAttribute ("login.status", null);
        }
        
        String fh = failHandler;
        String next = request.getParameter ("location");
        if(StringUtils.isNotBlank(next)){
        	String last = failHandler.substring(failHandler.lastIndexOf("/") + 1);
        	String mark = "?";
        	if(last.indexOf("?")> -1){
        		mark = "&";
        	}
        	fh = failHandler + mark + "location=" + URLEncoder.encode(next, "utf-8");
        }
        
        response.sendRedirect (contentPath + fh);
    }

    /**
     * 用户的处理程?
     *
     * 将当前会话清空，并跳转到登录页面
     * @param request 当前web请求
     * @param response 当前web响应
     * @throws IOException 可能的异?
     */
    @SuppressWarnings ("unchecked")
    private void logout (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
        HttpSession session = request.getSession ();
        LoginUser loginUser = (LoginUser) session.getAttribute (IConsant.SESSION_LOGINUSER_KEY);
        
        String contentPath = request.getContextPath ();
        if (!StringUtil.isEmpty (contentPath) && contentPath.charAt (contentPath.length () - 1) == '/')
            contentPath = contentPath.substring (0, contentPath.length () - 1);
        
        String next = request.getParameter ("location");
        if (next == null)
            next = (String) session.getAttribute (IConsant.LOGIN_LOCATION);
        
        if(next == null)
        	next = contentPath + "/not-login.jsp";
        try{
	        if (loginUser != null) {
	            session.removeAttribute (IConsant.SESSION_LOGINUSER_KEY);
	
	            ServletContext context = getServletContext ();
	            Map<String, LoginUser> userMap = (Map<String, LoginUser>) context.getAttribute (IConsant.LOGINUSER_MAP_KEY);
	            String userId = loginUser.getUser().getUserId();
	            if (userMap.containsKey (userId)) userMap.remove (userId);
	            
	            LogHelper.log(loginUser.getUser().getLoginName(), SessionHelper.getIpAddr(request), null, "ssologout", ILogFinal.LOG_SUCCESS);
	            loginUser = null;
	            
                String method = (String) session.getAttribute (IConsant.LOGIN_METHOD_KEY);
                if (method == null)
                    method = "GET";
                if (StringUtil.isEmpty (method)) method = "GET";

                request.setAttribute (IConsant.LOGIN_LOCATION, next);
                request.setAttribute (IConsant.LOGIN_METHOD_KEY, method.toUpperCase ());
	            
	            if (ssoSupported) {
	                logger.warn("sso supported!");
	                RequestDispatcher dispatcher = processSSOLogout(request);
	                dispatcher.forward (request, response);
	                
	                session.invalidate ();
	                return;
	            }
	            
	            session.invalidate ();
	        }else{
	        	LogHelper.log("", SessionHelper.getIpAddr(request), null, "ssologout empty loginUser", ILogFinal.LOG_FAIL);
	        }
        }catch(Exception e){
        	String u = loginUser == null ? "" : loginUser.getUser().getLoginName();
        	LogHelper.log(u, SessionHelper.getIpAddr(request), null, "ssologout", ILogFinal.LOG_FAIL);
        }
        
        response.sendRedirect (next);
    }

    /**
     * 保存用户信息到会话中
     * @param user 当前用户
     * @param request 当前请求
     */
    @SuppressWarnings ("unchecked")
    private void saveUser (User user, HttpServletRequest request, ServletContext context) {
    	HttpSession session = request.getSession ();
    	 
        //新增LoginUser对象;
        LoginUser loginUser = new LoginUser(user, request);
        session.setAttribute (IConsant.SESSION_LOGINUSER_KEY, loginUser);

        Map<String, LoginUser> userMap = (Map<String, LoginUser>) context.getAttribute (IConsant.LOGINUSER_MAP_KEY);
        if (userMap == null) {
            userMap = new HashMap<String, LoginUser> ();
            context.setAttribute (IConsant.LOGINUSER_MAP_KEY, userMap);
        }
        userMap.put (user.getUserId(), loginUser);
    }

    @SuppressWarnings ("unchecked")
    private RequestDispatcher processSSO (User user, HttpServletRequest request) {
        ServletContext application = getServletContext ();
        String processor = (String) application.getAttribute (IConsant.SSO_PROCESSOR_KEY);
        String callback = (String) application.getAttribute (IConsant.SSO_CALLBACK_KEY);

        RequestDispatcher dispatcher = request.getRequestDispatcher (processor);
        SsoToken token = new SsoToken (user, 600);
        String fingerPrint = token.getToken ();
        synchronized (SsoScanner.locker) {
            Map<String, SsoToken> map = (Map<String, SsoToken>) application.getAttribute (IConsant.SSO_TOKEN_KEY);
            if (map == null) {
                map = new HashMap<String, SsoToken> ();
                application.setAttribute (IConsant.SSO_TOKEN_KEY, map);
            }

            map.put (fingerPrint, token);
        }
        try{
	        List<String> targets = HelperUtil.querySSOTarget(application);
	        List<String> peers = new ArrayList<String> (targets.size ());
	        for (String target : targets) {
	            peers.add (String.format ("%s?token=%s&callback=%s", target, fingerPrint, callback));
	        }
	        request.setAttribute (IConsant.SSO_TARGET_KEY, peers);
        }catch(Exception e){
        	logger.error("com.swg.authority:parse sso-conifg.xml error!" + e.getMessage());
        }
        return dispatcher;
    }
    
    private RequestDispatcher processSSOLogout (HttpServletRequest request) {
        ServletContext application = getServletContext ();
        String processor = (String) application.getAttribute (IConsant.SSO_PROCESSOR_KEY);
//        String callback = (String) application.getAttribute (IConsant.SSO_CALLBACK_KEY);
//
        RequestDispatcher dispatcher = request.getRequestDispatcher (processor);
//        String fingerPrint = request.getParameter("token");
//        synchronized (SsoScanner.locker) {
//            Map<String, SsoToken> map = (Map<String, SsoToken>) application.getAttribute (IConsant.SSO_TOKEN_KEY);
//            if (map == null) {
//                map = new HashMap<String, SsoToken> ();
//                application.setAttribute (IConsant.SSO_TOKEN_KEY, map);
//            }
//            map.put (fingerPrint, token);
//        }
        try{
        	List<String> targets = HelperUtil.querySSOTarget(application);
	        List<String> peers = new ArrayList<String> (targets.size ());
	        for (String target : targets) {
	            peers.add (String.format ("%s?action=logout", target));
	        }
	        request.setAttribute (IConsant.SSO_TARGET_KEY, peers);
        }catch(Exception e){
        	logger.error("com.swg.authority:parse sso-conifg.xml error!" + e.getMessage());
        }
        return dispatcher;
    }
}