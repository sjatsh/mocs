package smtcl.mocs.beans.erp;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.services.erp.IImportService;

/**
 * ��ERP���ݿ��е�Wis_Transfer��д������
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
			Thread.sleep(2000); //�ȴ�2�����߳̽���
			System.err.println("ERP�ش������߳�����...");
			LogHelper.log("ERP�ش������߳�",+eventId+"����");
			try {
				IImportService importService = (IImportService)ServiceFactory.getBean("importService");
				if(importService!=null){
					importService.insertWisTransfer(eventId);
					importService.chaseLeaks();
					
				}
				LogHelper.log("ERP�ش������߳�", +eventId+"����");	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

}
