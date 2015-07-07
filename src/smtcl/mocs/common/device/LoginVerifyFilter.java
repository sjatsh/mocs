package smtcl.mocs.common.device;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 登录验证
 * @作者: JiangFeng
 * @创建时间：2013-04-26
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("serial")
public class LoginVerifyFilter extends HttpServlet implements Filter {
	IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory.getBean("authorizeService");
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
			
		Map<String, String> urlMap = (Map<String, String>) session.getAttribute("URLMAPPING");
		if(urlMap==null){
			//初始化URL信息
			ServletContext context=request.getServletContext();
			urlMap=StringUtils.getUrl(context);
			session.setAttribute("URLMAPPING", urlMap);
		}
		
		TUser checkUser = (TUser) session.getAttribute(Constants.USER_SESSION_KEY);
		/*************集成权限 yutao**************************/	
		/**改用SSO实现**/
		/**自动登录**/
		/*
		Boolean urlFlag=false;//如果是自动登录，跳转到设备概览上,防止节点为空
		if (checkUser == null || "".equals(checkUser)){
		String usercookie=StringUtils.getCookie((HttpServletRequest)request, "user");
	    String cooks[] = usercookie.split("==");  
	    String username="";
	    String password="";
	    if (cooks.length == 2) {  
              username=cooks[0];
              password=cooks[1];
              IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory.getBean("authorizeService");
              Map<String,Object> map=authorizeService.userLogin(username, password);
              if(map!=null&&(Boolean)map.get("success")){
            	  TUser userinfo=new TUser();
	              userinfo.setLoginName(map.get("name")+"");    
	              userinfo.setOrgId(map.get("userId")+"");
	              userinfo.setUserId(map.get("userId")+"");      
	              session.setAttribute (Constants.USER_SESSION_KEY, userinfo);
	              checkUser = (TUser) session.getAttribute(Constants.USER_SESSION_KEY);
	              urlFlag=true;
              }
	    }  
		}
		*/
        /***************************************/   
		String contextURL=req.getContextPath();
		String nowURL = req.getRequestURI();
//		if(urlFlag) nowURL=contextURL+"/index.faces";
		System.out.println("页面URL" + nowURL);
		System.out.println("当前的SessionId:filter-" + session.getId());
		if (checkUser == null || "".equals(checkUser)) {
			resp.sendRedirect(urlMap.get("authURL")+"/login.jsp?location="+urlMap.get("mocsURL")+"/mocs/map/map.faces");
			//resp.sendRedirect(urlMap.get("authURL")+"/login.jsp?location="+urlMap.get("mocsURL")+nowURL);
			return;
//			String requestFlag = req.getHeader("X-Requested-With");//判断是否是异步提交 X-Requested-With
//			if(requestFlag!=null&&requestFlag.equalsIgnoreCase("XMLHttpRequest")){
//				PrintWriter out = resp.getWriter();
//				out.print(-1);
//				out.flush();
//				out.close();
//			}
		} else {
			if (nowURL.equals(contextURL+"/index.faces")) { 
				chain.doFilter(req, resp);
				return;
			} else {
				// 获取当前登录用户访问的逻辑地址
				Map<String, String> map = Constants.MOCS_PATH_MAP;
				// 调用远程权限接口
				
				//boolean result = (boolean) authorizeService.isAuthorize("100", map.get(nowURL)); 
				if("/mocs/admin/index.faces".equals(nowURL)){
					chain.doFilter(req, resp);
					return;
				}else{
					boolean result = (boolean) authorizeService.isAuthorize(checkUser.getUserId(), map.get(nowURL));
					// 验证用户是否拥有权限访问当前页面
					if (result) {
						chain.doFilter(req, resp);
						return;
					} else {
						if(nowURL.equals("/mocs/map/map.faces")){
							resp.sendRedirect(contextURL+"/index.faces");
						}else{
							resp.sendRedirect(contextURL+"/InsufficientPermissions.faces");
						}
						
						return;
					}
				}
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
