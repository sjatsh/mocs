package smtcl.mocs.web.webservice.device;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.web.webservice.model.MachineToolUpdateFreq;
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter{
	
	private ThreadLocal<Long> startTimethl = new ThreadLocal<Long>(); 
	
//	private MachineToolUpdateFreq machineToolUpdateFreq=(MachineToolUpdateFreq)ServiceFactory.getBean("machineToolUpdateFreq");
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
 
		long startTime = System.currentTimeMillis();
		startTimethl.set(startTime);
		request.setAttribute("method", request.getParameter("method"));
		byte[]bs = request.getParameter("parms").getBytes("ISO-8859-1");
		String parms = new String(bs,"UTF-8");
		String param[]=parms.split("\\|",-1);
		request.setAttribute("machineNo", param[0]);
		request.setAttribute("updateTime", param[1]);
		String url=request.getServletPath();
		if(null!=request.getParameter("method")){
			if("InsertWorkEvents".equalsIgnoreCase(request.getParameter("method"))){
				String newurlInsertWorkEvents=url.replace("WSBZService", "WSBZServiceInsertWorkEvents");
				request.getRequestDispatcher(newurlInsertWorkEvents).forward(request, response);
				return true;
			}else if("UpdateCurStatus".equalsIgnoreCase(request.getParameter("method"))){
				String newurlUpdateCurStatus=url.replace("WSBZService", "WSBZServiceUpdateCurStatus");
				request.getRequestDispatcher(newurlUpdateCurStatus).forward(request, response);
				return true;
			}
			
		}
		return true;
	}
 
	//after the handler is executed
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)throws Exception {
//			machineToolUpdateFreq.addElementFrom((String) request.getAttribute("machineNo"));
	}
	
	
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object hander, Exception e) throws Exception {
		long endTime = System.currentTimeMillis();
		long beginTime = startTimethl.get();
		
		String machineNo=(String) request.getAttribute("machineNo");
		
		// Logs
		LogHelper.log(machineNo+" : "+"[" + request.getAttribute("method") + "] run : ", (endTime - beginTime) + "ms");
	}
}
