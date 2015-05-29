package smtcl.mocs.web.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.UpdataJobDispatch;

/**
 * 工单更新页面
 * @author songkaiang
 *
 */
@Controller
public class JobDispatchUpdateWeb {
	
	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	
	private UpdataJobDispatch updataJobDispat = (UpdataJobDispatch)ServiceFactory.getBean("updataJobDispatch");
	
	/**
	 * 检测发送的工单是否关联到设备
	 * @param dispatchName
	 * @return
	 */
	@RequestMapping(value ="/jobdispatch/checkEquSerialNum.action")
	public @ResponseBody Map<String,Object> checkEquSerialNum(String jobdispatchlistId){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		List<Map<String,Object>> mapList = jobDispatchService.getEquJobDispatchList(jobdispatchlistId,session);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean flag = false;
		for(Map<String,Object> map1: mapList){
			if(!"0".equals(map1.get("flagStatus").toString()))
				flag = true;
		}
		map.put("flag", flag);
		return map;
	}

	/**
	 * 检测工单的状态是否为已派工
	 * @param dispatchName
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/checkJobDispatchStatus.action")
	public @ResponseBody Map<String,Object> checkJobDispatchStatus(String jobdispatchlistId){
		Map<String,Object> map = new HashMap<String,Object>();
		List<TJobdispatchlistInfo> list = jobDispatchService.getJobDispatchInfo(jobdispatchlistId);
		boolean flag = false;
		String jobStatus = "";
		for(TJobdispatchlistInfo t : list){
			jobStatus = t.getStatus().toString();
			if(t.getStatus() == 30)
				flag = true;
		}
		map.put("flag", flag);
		map.put("jobStatus", jobStatus);
		return map;
	}
	
	/**
	 * 更改工单的状态
	 * @param dispatchName
	 * @return
	 */
	@RequestMapping(value="/jobdispatch/changeJobDispatchStatus.action")
	public @ResponseBody Map<String,Object> changeJobDispatchStatus(String jobdispatchlistId,int status){
		Map<String,Object> map = new HashMap<String,Object>();
		boolean flag = jobDispatchService.updateDispatch(jobdispatchlistId, status);
		map.put("flag", flag);
		return map;
	}
	
	/**
	 * 检测工单是否包括要添加的设备
	 * @param serialNo
	 * @return true已存在
	 */
	@RequestMapping(value="/jobdispatch/checkEquSerailNo.action")
	public  @ResponseBody Map<String,Object> checkEquSerailNo(String jobdispatchlistId, String serialNo){
		Map<String,Object> map = new HashMap<String,Object>();
		List<TJobdispatchlistInfo> list = jobDispatchService.getJobDispatchInfo(jobdispatchlistId);
		TJobdispatchlistInfo t = (TJobdispatchlistInfo)list.get(0);
		List<TEquJobDispatch> list1 = updataJobDispat.getEquJobDispatch(serialNo, t.getNo());
		boolean flag = false;
		if(list1.size()>0){
			flag = true;
		}
		map.put("flag", flag);
		return map;
	}
	
}
