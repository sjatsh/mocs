package smtcl.mocs.beans.device;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.services.device.ICostManageService;

/**
 * 加工事件调度
 * @创建时间 2013-6-25
 * @作者 yutao
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public class EquWorkEventProcess implements Runnable {
	
	public Long eventId = null;
	public String partNo=null;

	public EquWorkEventProcess(Long eventId,String partNo) {
		this.eventId = eventId;
		this.partNo=partNo;
		
	}

	public void run() {
		try {
			Thread.sleep(1000); //等待1秒主线程结束
			System.err.println("子线程启动...");
			LogHelper.log("子线程",+eventId+"启动");
			try {				
				ICostManageService costManageService=(ICostManageService) ServiceFactory
						.getBean("costManageService");
//				costManageService.processEquWorkEvent(eventId,partNo);
				costManageService.processEquWorkEvent(eventId);
			LogHelper.log("子线程", +eventId+"结束");	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
