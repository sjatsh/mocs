package smtcl.mocs.services.device;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import smtcl.mocs.beans.device.EquWorkEventProcess;

public class TestServiceMain {
	
	public static void main(String[] args){
		
//		ApplicationContext context= new FileSystemXmlApplicationContext("D:\\Workspaces\\MOCSWorspace\\mocs\\WebRoot\\WEB-INF\\device-context.xml");
		//ApplicationContext context= new FileSystemXmlApplicationContext("D:\\Item\\mocs\\WebRoot\\WEB-INF\\device-context.xml");
		ApplicationContext context= new FileSystemXmlApplicationContext("E:\\git\\mocs\\WebRoot\\WEB-INF\\device-context.xml");   
		/**
		 * 产品型号 下拉框
		 */
		ICostManageService costManageService=(ICostManageService)context.getBean("costManageService");
		
		System.err.println("start..."+new Date());
		EquWorkEventProcess thread0=new EquWorkEventProcess(new Long(12325),"");
		new Thread(thread0).start();
		System.err.println("start...01..."+new Date());
		EquWorkEventProcess thread1=new EquWorkEventProcess(new Long(12325),"");
		new Thread(thread1).start();
		/**
		 * 构造一个线程池
		 */
//	    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 30, 10, TimeUnit.SECONDS,  
//				new ArrayBlockingQueue<Runnable>(20), new ThreadPoolExecutor.DiscardOldestPolicy());
//	    
//	    threadPool.execute(new EquWorkEventProcess(new Long(12325),"")); 
//		
//		System.err.println("start..."+new Date());
//		
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.err.println("start...01..."+new Date());
//		threadPool.execute(new EquWorkEventProcess(new Long(12325),"")); 
//	    costManageService.processEquWorkEvent(new Long(12325));
	    
		
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
	}

}
