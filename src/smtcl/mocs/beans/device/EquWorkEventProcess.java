package smtcl.mocs.beans.device;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.services.device.ICostManageService;

/**
 * �ӹ��¼�����
 * @����ʱ�� 2013-6-25
 * @���� yutao
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
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
			Thread.sleep(1000); //�ȴ�1�����߳̽���
			System.err.println("���߳�����...");
			LogHelper.log("���߳�",+eventId+"����");
			try {				
				ICostManageService costManageService=(ICostManageService) ServiceFactory
						.getBean("costManageService");
//				costManageService.processEquWorkEvent(eventId,partNo);
				costManageService.processEquWorkEvent(eventId);
			LogHelper.log("���߳�", +eventId+"����");	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
