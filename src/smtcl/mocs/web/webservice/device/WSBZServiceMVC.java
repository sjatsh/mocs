package smtcl.mocs.web.webservice.device;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import smtcl.mocs.services.device.IWSBZService;
import smtcl.mocs.utils.device.Constants;


/**
 * 北展WebService
 * @version V1.0
 * @创建时间 2013-03-05
 * @作者 liguoqiang
 * @修改者
 * @修改日期
 * @修改说明
 */
@Controller
@RequestMapping("/WSBZService")
public class WSBZServiceMVC {
	/**
	 * 注入北展service
	 */
	IWSBZService wsBZService=(IWSBZService)ServiceFactory.getBean("wsBZService");
	private Lock mylock=new ReentrantLock(false);
	/**
	 * 更新设备当前状态
	 * @param method 调用哪个方法
	 * @param parms  参数
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Portal", method = RequestMethod.GET)
	public ModelAndView Portal(String method,String parms) throws Exception{
		Map<String, Object> all = new HashMap<String, Object>();
		boolean rs=false;
		String rStr="";
		byte[] bs;
		try {
			bs = parms.getBytes("ISO-8859-1");
			parms = new String(bs,"UTF-8");
			String param[]=parms.split("\\|",-1);
			//LogHelper.log("---------WSBZServiceMVC---------->>>>>>"+parms, method);
			try {
				mylock.lock();
				if(method.equals("UpdateStatusStore")){
					 rs=wsBZService.updateStatusStore(param[0], param[1], param[2], param[3], param[4], param[5], param[6],
							param[7], param[8], param[9], param[10], param[11], param[12], param[13], param[14], param[15], param[16],
							param[17], param[18]);
				}else if(method.equals("InsertStatusDaily")){
					rs=wsBZService.InsertStatusDaily(param[0], param[1], param[2], param[3], param[4], param[5], param[6],
							param[7], param[8], param[9], param[10], param[11], param[12], param[13], param[14], param[15], param[16],
							param[17], param[18]);
				}else if(method.equals("InsertEvents")){
					rs=wsBZService.InsertEvents(param[0], param[1], param[2], param[3], param[4], param[5]);
				}else if(method.equals("InsertOEEdaily")){
					rs=wsBZService.InsertOEEdaily(param[0], param[1], param[2], param[3], param[4], param[5], param[6], 
							param[7], param[8]);
				}else if(method.equals("InsertOEEstore")){
					rs=wsBZService.InsertOEEstore(param[0], param[1], param[2], param[3], param[4], param[5], param[6],
							param[7], param[8], param[9], param[10]);
				}else if(method.equals("GetProductionOrder")){
					rStr=wsBZService.getProductionOrder(param[0], param[1]);
					if(null!=rStr&&!"".equals(rStr)&&!rStr.equals(param[1])){
						rs=true;
					}
				}else if(method.equals("InsertIntelligentDiagnoseIO")){
					rs=wsBZService.insertIntelligentDiagnoseIO(param[0], param[1], param[2]);
				}
			} finally {
				mylock.unlock();
			}
			if(rs){
				all.put("msg", "IPORTSUCCESS");
				all.put("success", true);
				if(!"".equals(rStr)){
				all.put("data", rStr);
				}
			}else
			{
				all.put("msg", Constants.USER_UPDATE_MSG[1]);
				all.put("success", false);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			all.put("msg", Constants.USER_UPDATE_MSG[1]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
}