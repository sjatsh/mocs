package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.utils.device.StringUtils;

public class TestServiceMain {
	
	public static void main(String[] args){
		
//		ApplicationContext context= new FileSystemXmlApplicationContext("D:\\Workspaces\\MOCSWorspace\\mocs\\WebRoot\\WEB-INF\\device-context.xml");
		//ApplicationContext context= new FileSystemXmlApplicationContext("D:\\Item\\mocs\\WebRoot\\WEB-INF\\device-context.xml");
		ApplicationContext context= new FileSystemXmlApplicationContext("D:\\Workspaces\\Myeclipse10workspace\\a3-mocs-sy-pl-01\\WebRoot\\WEB-INF\\device-context.xml");   
//		/**
//		 * 产品型号 下拉框
//		 */
		ICostManageService costManageService=(ICostManageService)context.getBean("costManageService");
//		
		costManageService.processEquWorkEvent(new Long(163));
		
//		System.err.println(costManageService.getPartTypeList().size());
		
//		IDeviceService deviceService=(IDeviceService)context.getBean("deviceService");
//		deviceService.getMachineToolStatus("TC500R-SYB21-03");
		
		
//		ICostManageService costManageService=(ICostManageService)context.getBean("costManageService");
		//IJobDispatchService jobDispatchService=(IJobDispatchService)context.getBean("jobDispatchService");
		//String nodeId="8a8abc973f1dc2dc013f1dc3d7dc0001";
		//String jobplanId="";
//		List<Map<String, Object>> rs=jobDispatchService.getJobDispatchsInfo(nodeId, jobplanId);
//		System.out.println(rs);
//		costManageService.queryProcessCostList(1,20,"100001");
//		costManageService.queryEquipmentCostListByVar(1,100,"100001","2013-03-13 09:20:00", "2012-03-15 09:20:00");
//		costManageService.queryEquipmentCostListByEquno("100001", "ETC3650-SYB21-02","2013-03-13 09:20:00", "2013-07-14 09:20:00");
//		costManageService.queryProductRealCostAnalysis("100001","");
//		costManageService.getProductTheoryCostAnalysis("100001");
//		costManageService.processEquWorkEvent(new Long(8));
//		costManageService.queryLastPartCost();
		
		System.err.println(StringUtils.isValid("http://10.10.10.13:8080/mocs/images/part/1394536782695adoyrpqmvju1.png"));
	}

}
