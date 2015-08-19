package smtcl.mocs.web.webservice.device;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import smtcl.mocs.services.device.IWSBZService;
import smtcl.mocs.utils.device.Constants;

@Controller
@RequestMapping("/WSBZServiceUpdateCurStatus")
public class UpdateCurStatusMVC {
	IWSBZService wsBZService=(IWSBZService)ServiceFactory.getBean("wsBZService");
	
	private final Semaphore semp = new Semaphore(300,true);
	@RequestMapping(value = "/Portal", method = RequestMethod.GET)
	public  ModelAndView Portal(String method,String parms) throws Exception{
		Map<String, Object> all = new HashMap<String, Object>();
		boolean rs=false;
		String rStr="";
		byte[] bs;
		try {
			bs = parms.getBytes("ISO-8859-1");
			parms = new String(bs,"UTF-8");
			String param[]=parms.split("\\|",-1);
					try {
						semp.acquire();
						// manipulate protected state
						if (param.length > 22)
							rs = wsBZService.updateCurStatus(param[0],
									param[1], param[2], param[3], param[4],
									param[5], param[6], param[7], param[8],
									param[9], param[10], param[11], param[12],
									param[13], param[14], param[15], param[16],
									param[17], param[18], param[19], param[20],
									param[21], param[22], param[23]);
						else
							rs = wsBZService.updateCurStatus(param[0],
									param[1], param[2], param[3], param[4],
									param[5], param[6], param[7], param[8],
									param[9], param[10], param[11], param[12],
									param[13], param[14], param[15], param[16],
									param[17]);
					} finally {
						semp.release();
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
