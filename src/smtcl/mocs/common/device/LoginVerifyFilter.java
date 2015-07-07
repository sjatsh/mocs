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
 * ��¼��֤
 * @����: JiangFeng
 * @����ʱ�䣺2013-04-26
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
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
			//��ʼ��URL��Ϣ
			ServletContext context=request.getServletContext();
			urlMap=StringUtils.getUrl(context);
			session.setAttribute("URLMAPPING", urlMap);
		}
		
		TUser checkUser = (TUser) session.getAttribute(Constants.USER_SESSION_KEY);
		/*************����Ȩ�� yutao**************************/	
		/**����SSOʵ��**/
		/**�Զ���¼**/
		/*
		Boolean urlFlag=false;//������Զ���¼����ת���豸������,��ֹ�ڵ�Ϊ��
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
		System.out.println("ҳ��URL" + nowURL);
		System.out.println("��ǰ��SessionId:filter-" + session.getId());
		if (checkUser == null || "".equals(checkUser)) {
			resp.sendRedirect(urlMap.get("authURL")+"/login.jsp?location="+urlMap.get("mocsURL")+"/mocs/map/map.faces");
			//resp.sendRedirect(urlMap.get("authURL")+"/login.jsp?location="+urlMap.get("mocsURL")+nowURL);
			return;
//			String requestFlag = req.getHeader("X-Requested-With");//�ж��Ƿ����첽�ύ X-Requested-With
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
				// ��ȡ��ǰ��¼�û����ʵ��߼���ַ
				Map<String, String> map = Constants.MOCS_PATH_MAP;
				// ����Զ��Ȩ�޽ӿ�
				
				//boolean result = (boolean) authorizeService.isAuthorize("100", map.get(nowURL)); 
				if("/mocs/admin/index.faces".equals(nowURL)){
					chain.doFilter(req, resp);
					return;
				}else{
					boolean result = (boolean) authorizeService.isAuthorize(checkUser.getUserId(), map.get(nowURL));
					// ��֤�û��Ƿ�ӵ��Ȩ�޷��ʵ�ǰҳ��
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
