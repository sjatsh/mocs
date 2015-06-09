package smtcl.mocs.web.webservice.model;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RunMeJob extends QuartzJobBean{
	private MachineToolUpdateFreq machineToolUpdateFreq;
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
		machineToolUpdateFreq.removeAllElement();
	}
	public void setMachineToolUpdateFreq(MachineToolUpdateFreq machineToolUpdateFreq) {
		this.machineToolUpdateFreq = machineToolUpdateFreq;
	}

}
