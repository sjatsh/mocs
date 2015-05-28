package smtcl.mocs.beans.erp;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.services.erp.IImportService;

/**
 * 向ERP数据库中的Wis_Transfer表写入数据
 * @author songkaiang
 *
 */
public class WisTransferThread implements Runnable{
	
	
	public Long eventId = null;
	
	public WisTransferThread(long eventId){
		this.eventId = eventId;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000); //等待2秒主线程结束
			System.err.println("ERP回传表子线程启动...");
			LogHelper.log("ERP回传表子线程",+eventId+"启动");
			try {
				IImportService importService = (IImportService)ServiceFactory.getBean("importService");
				if(importService!=null){
					importService.insertWisTransfer(eventId);
					importService.chaseLeaks();
					
				}
				LogHelper.log("ERP回传表子线程", +eventId+"结束");	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

}
