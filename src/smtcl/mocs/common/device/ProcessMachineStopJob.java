package smtcl.mocs.common.device;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IDeviceService;

/**
 * ��ʱ�޸�ʵʱ״̬
 * @����ʱ�� 2013-4-27
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 * 
 */
public class ProcessMachineStopJob {
	
	/**
	 * �豸�ӿ�ʵ��
	 */
	private IDeviceService deviceService =(IDeviceService)ServiceFactory.getBean("deviceService"); //��ȡע��;
	
	/**
	 * �߳�����
	 */
	public void run() {  
		System.out.println("-------------------��ʱ��UpdateTUserEquCurStatus��ʼ����--------------------");
		deviceService.UpdateUserEquCurStatus();
		System.out.println("-------------------��ʱ��UpdateTUserEquCurStatus���н���--------------------\n");
	}
}
