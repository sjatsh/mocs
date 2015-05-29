package smtcl.mocs.web.webservice.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import smtcl.mocs.services.device.IWSBZService;
import smtcl.mocs.utils.device.Constants;


/**
 * 智能诊断WebService
 * @version V1.0
 * @创建时间 2013-12-25
 * @作者 yutao
 * @修改者
 * @修改日期
 * @修改说明
 */
@Controller
@RequestMapping("/WSDiagnoseService")
public class WSDiagnoseServiceMVC {
	/**
	 * 注入北展service
	 */
	IWSBZService wsBZService=(IWSBZService)ServiceFactory.getBean("wsBZService");
	
	/**
	 * 获取设备诊断信息
	 * @param equSerialNo 机床设备序列号
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getDiagnoseInfo", method = RequestMethod.GET)
	public ModelAndView getDiagnoseInfo(String equSerialNo,String componentType){
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			
			String rStr=wsBZService.get_Diagnostic_Message_for_xml(equSerialNo,componentType);
			if(!StringUtils.isEmpty(rStr))
			{
				all.put("msg", Constants.USER_QUERY_MSG[0]);
				all.put("success", true);
				all.put("content", rStr);
			}else 
			{
				all.put("msg", Constants.USER_QUERY_MSG[2]);
				all.put("success", false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			all.put("msg", Constants.USER_QUERY_MSG[2]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * 获取机床部件信息
	 * @param equSerialNo 机床设备序列号
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getMachineComponentInfo", method = RequestMethod.POST)
	public ModelAndView getMachineComponentInfo(String equSerialNo){
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> rs=wsBZService.getMachineComponentInfo(equSerialNo);
			all.put("msg", Constants.USER_QUERY_MSG[0]);
			all.put("success", true);
			all.put("content", rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			all.put("msg", Constants.USER_QUERY_MSG[2]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * 获取机床报修列表
	 * @param equSerialNo 机床设备序列号
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getDiagnoseList", method = RequestMethod.POST)
	public ModelAndView getDiagnoseList(String equSerialNo){
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(equSerialNo))
			{
				equSerialNo="TC500R-SYB21-03";
			}
			List<Map<String,Object>> rs=wsBZService.getDiagnoseList(equSerialNo);
			all.put("msg", Constants.USER_QUERY_MSG[0]);
			all.put("success", true);
			all.put("content", rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			all.put("msg", Constants.USER_QUERY_MSG[2]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
}