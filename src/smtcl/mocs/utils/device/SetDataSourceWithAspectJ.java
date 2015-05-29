package smtcl.mocs.utils.device;

import java.io.Serializable;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import smtcl.mocs.dao.device.impl.CustomerContextHolder;

@Aspect
public class SetDataSourceWithAspectJ  implements Serializable {
	//private	IMailMail MailService=(IMailMail)ServiceFactory.getBean("mailMail");
	/**
	 * 
	 */
	private static final long serialVersionUID = 3350592102750250280L;
	@Before("execution(* smtcl.mocs.utils.authority.AuthorityUtil.*(..))")
	public void setDataBefore6(JoinPoint joinPoint) {
		String tempInterfacesName=joinPoint.getSignature().toString();
		System.out.println(">>>>¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­>>>>>>>>"+tempInterfacesName);
		CustomerContextHolder.setCustomerType(CustomerContextHolder.dataSource0);
	}
	@Before("execution(* smtcl.mocs.services.authority.I*.*(..))")
	public void setDataBefore3(JoinPoint joinPoint) {
		String tempInterfacesName=joinPoint.getSignature().toString();
		System.out.println(">>>>¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­>>>>>>>>"+tempInterfacesName);
		CustomerContextHolder.setCustomerType(CustomerContextHolder.dataSource0);
	}
	@Before("execution(* smtcl.mocs.services.erp.I*.*(..))")
	public void setDataBefore4(JoinPoint joinPoint) {
		String tempInterfacesName=joinPoint.getSignature().toString();
		System.out.println(">>>>¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­>>>>>>>>"+tempInterfacesName);
		CustomerContextHolder.setCustomerType(CustomerContextHolder.dataSource0);
	}
	@Before("execution(* smtcl.mocs.services.storage.I*.*(..))")
	public void setDataBefore5(JoinPoint joinPoint) {
		String tempInterfacesName=joinPoint.getSignature().toString();
		System.out.println(">>>>¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­>>>>>>>>"+tempInterfacesName);
		CustomerContextHolder.setCustomerType(CustomerContextHolder.dataSource0);
	}
	@Before("execution(* smtcl.mocs.services.jobplan.I*.*(..))")
	public void setDataBefore0(JoinPoint joinPoint) {
		changeDataSourceWithAspectJ();
	}
	@Before("execution(* smtcl.mocs.services.report.I*.*(..))")
	public void setDataBefore1(JoinPoint joinPoint) {
		String tempInterfacesName=joinPoint.getSignature().toString();
		System.out.println(">>>>¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­¡­>>>>>>>>"+tempInterfacesName);
		changeDataSourceWithAspectJ();
	}
	@Before("execution(* smtcl.mocs.services.device.I*.*(..))")
	public void setDataBefore2(JoinPoint joinPoint) {
		
		String tempInterfacesName=joinPoint.getSignature().toString();
		
		if(StringUtils.contains(tempInterfacesName, "IWSBZService")||StringUtils.contains(tempInterfacesName, "INodeMatchService")){
			CustomerContextHolder.setCustomerType(CustomerContextHolder.dataSource0);
			return;
		}else if(StringUtils.contains(tempInterfacesName, "ICommonService")){
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "ICostManageService")){
			
			if(StringUtils.contains(tempInterfacesName, "processEquWorkEvent")){
				return;
			}
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IDeviceService")){
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IMemberService")){
			if(!StringUtils.contains(tempInterfacesName, "getUserbyAll")){
				changeDataSourceWithAspectJ();
				return;
			}
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IMtService")){
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IOrganizationService")){
			if(StringUtils.contains(tempInterfacesName, "get_All_Node_By_nodeClass") 
					|| StringUtils.contains(tempInterfacesName, "getNodesAllId")
					|| StringUtils.contains(tempInterfacesName, "getAllTNodesId")
					|| StringUtils.contains(tempInterfacesName, "getTNodesById")){
				changeDataSourceWithAspectJ();
				return;
			}
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IPartService")){
			if(StringUtils.contains(tempInterfacesName, "newNodeChildren")){
				return;
			}
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IProcessService")){
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IProductionService")){
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IProductService")){
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IResourceService")){
			changeDataSourceWithAspectJ();
			return;
		}else if(StringUtils.contains(tempInterfacesName, "IWSUserService")){
			changeDataSourceWithAspectJ();
			return;
		}
		
		
	}
//	@AfterThrowing( pointcut = "execution(* smtcl.mocs.services.*.I*.*(..))",throwing= "error")
//	public void logAfterThrowing(JoinPoint joinPoint, Exception error) {
//			System.out.println("logAfterThrowing() is running!");
//			System.out.println("error-at         --->  :" + joinPoint.getSignature().getName());
//			System.out.println("Exception : " + error);
//			String[] name={"540761895@qq.com"};
//			MailService.sendMail("540761895@qq.com", name, joinPoint.getSignature().toString(), error.getLocalizedMessage());
//			System.out.println("******");
//	}
	
	public static void changeDataSourceWithAspectJ() {
		
		String current_dataSource = "";
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		if (null != (String) session.getAttribute("current_dataSource")) {
			current_dataSource = (String) session.getAttribute("current_dataSource");
			CustomerContextHolder.setCustomerType(current_dataSource);
			System.out.println("--------->>>>   my first aop:::"+ current_dataSource);
		} 
	}
}
