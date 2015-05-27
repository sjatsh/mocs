package smtcl.mocs.common.device;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IDeviceService;

/**
 * 定时修改实时状态
 * @创建时间 2013-4-27
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 * 
 */
public class ProcessMachineStopJob {
	
	/**
	 * 设备接口实例
	 */
	private IDeviceService deviceService =(IDeviceService)ServiceFactory.getBean("deviceService"); //获取注入;
	
	/**
	 * 线程启动
	 */
	public void run() {  
		System.out.println("-------------------定时器UpdateTUserEquCurStatus开始运行--------------------");
		deviceService.UpdateUserEquCurStatus();
		System.out.println("-------------------定时器UpdateTUserEquCurStatus运行结束--------------------\n");
	}
}
