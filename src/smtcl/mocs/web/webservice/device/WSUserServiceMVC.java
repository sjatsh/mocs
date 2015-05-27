package smtcl.mocs.web.webservice.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IWSUserService;
import smtcl.mocs.utils.device.Constants;

/**
 * WebService用户登录调用接口
 * 
 * @创建者:JiangFeng
 * @修改者:
 * @备注:
 * @创建时间：2013-04-26
 * @修改日期：
 * @修改说明：
 * @版本：V1.0
 */
@Controller
@RequestMapping("/WSUserService")
public class WSUserServiceMVC {
	IWSUserService userService = (IWSUserService) ServiceFactory.getBean("wsUserService");
	IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory.getBean("authorizeService");
	IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	
	/**
	 * 验证用户登录
	 * 
	 * @param username 用户Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/userLoginModule", method = RequestMethod.POST)
	public ModelAndView userLoginModule(String username, String userpwd) {
		Map<String, Object> all = new HashMap<String, Object>();

		Map<String, Object> result = userService.userLogin(username,
				userpwd);
		try{
			if (result.get("msg").equals(Constants.USER_LOGIN_MSG[2])) {
				String userId =	result.get("userId").toString();
				String name = result.get("name").toString();
				List<Module> userModule = authorizeService.getMenu(userId, "mocs",null);
				
               // 先过滤对set集合的拆解
                JsonConfig config = new JsonConfig();
                 config.setJsonPropertyFilter(new PropertyFilter() {
                     @Override
                     public boolean apply(Object arg0, String arg1, Object arg2) {//过盧不需要的字段
                         if (arg1.equals("module") || arg1.equals("app") || arg1.equals("JSONObject") || arg1.equals("buttons")  ) {
                             return true;
                        } else {
                            return false;
                        }
                    }
               });
				//通过JSONArray转为字符串
				JSONArray gg = new JSONArray().fromObject(userModule,config); 
				
				all.put("userId", userId);
				all.put("username", name);
				all.put("authority", gg);
				
				all.put("success", true);
				all.put("msg", Constants.USER_LOGIN_MSG[2]);
			}else{
				all.put("success", false);
				all.put("msg", result.get("msg"));
			}
		}catch(Exception e){
			e.printStackTrace();
				all.put("success", false);
				all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}
		
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * 获取当前节点的直接子节点
	 * @param username 用户Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getAllNodesByParentNodeId", method = RequestMethod.GET)
	public ModelAndView getAllNodesByParentNodeId(String nodeId,String userId) {
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> result=organizationService.getAllNodesByParentNodeId(nodeId, userId);
			if(null!=result&&result.size()>0){
				all.put("data", result);
				all.put("success", true);
				all.put("msg", Constants.USER_LOGIN_MSG[2]);
			}else{
				all.put("data", result);
				all.put("success", false);
				all.put("msg","没有子节点");
			}
		}catch(Exception e){
				e.printStackTrace();
				all.put("data", null);
				all.put("success", false);
				all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * 生产任务管理
	 * @param equSerialNo
	 * @return
	 */
	@RequestMapping(value = "/getProduceTask", method = RequestMethod.POST)
	public ModelAndView getProduceTask(String equSerialNo,String processNo,String partName,String materialName,String checkRework) {
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> result=userService.getProduceTask(equSerialNo);
			if(null!=result&&result.size()>0){
				all.put("data", result);
				all.put("success", true);
				
			}else{
				all.put("data", result);
				all.put("success", false);
				
			}
		}catch(Exception e){
				e.printStackTrace();
				all.put("data", null);
				all.put("success", false);
				all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * 更新生产任务
	 * @param equSerialNo
	 * @return
	 */
	@RequestMapping(value = "/updateProduceTask", method = RequestMethod.POST)
	public ModelAndView updateProduceTask(String equSerialNo,String jobDispatchNo,String jobRepeat) {
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			Boolean result=userService.updateProduceTask(equSerialNo,jobDispatchNo);
			if(result){
				all.put("msg", Constants.USER_UPDATE_MSG[0]);
				all.put("success", true);
				
			}else{
				all.put("msg", Constants.USER_UPDATE_MSG[1]);
				all.put("success", false);
				
			}
		}catch(Exception e){
			all.put("msg", Constants.USER_UPDATE_MSG[1]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * 切换工单
	 * @param equSerialNo
	 * @return
	 */
	@RequestMapping(value = "/jobSwitch", method = RequestMethod.POST)
	public ModelAndView changeProduceTask(String equSerialNo,String ID) {
		LogHelper.log("jobSwitch........", equSerialNo+ID);
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			Map<String,Object> result=userService.changeProduceTask(equSerialNo,ID);
			if(result!=null){
				all.put("msg", Constants.USER_UPDATE_MSG[0]);
				all.put("success", true);
				all.put("data", result);
				
			}else{
				all.put("msg", Constants.USER_UPDATE_MSG[1]);
				all.put("success", false);
				all.put("data", result);
				
			}
		}catch(Exception e){
			all.put("msg", Constants.USER_UPDATE_MSG[1]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * 切换模式
	 * @param equSerialNo
	 * @return
	 */
	@RequestMapping(value = "/switchMode", method = RequestMethod.POST)
	public ModelAndView switchMode(String equSerialNo,String type) {
		
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			Boolean result=userService.switchMode(equSerialNo,type);
			if(result){
				all.put("msg", Constants.USER_UPDATE_MSG[0]);
				all.put("success", true);
				
			}else{
				all.put("msg", Constants.USER_UPDATE_MSG[1]);
				all.put("success", false);
				
			}
		}catch(Exception e){
			all.put("msg", Constants.USER_UPDATE_MSG[1]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * 更新生产任务
	 * @param equSerialNo
	 * @return
	 */
	@RequestMapping(value = "/jobFinished", method = RequestMethod.POST)
	public ModelAndView jobFinished(String equSerialNo,String jobDispatchNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			Boolean result=userService.jobFinished(equSerialNo,jobDispatchNo);
			if(result){
				all.put("success", true);
				
			}else{
				all.put("success", false);
				
			}
		}catch(Exception e){
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	/**
	 * 获取菜单
	 * @param userId
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/getMenu", method = RequestMethod.GET)
	public ModelAndView getMenu(String userId, String appId){
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			List<Map<String,Object>> rs=authorizeService.getMenu(userId,appId);
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
	 * 用户登录
	 * @param equSerialNo
	 * @return
	 */
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public ModelAndView memberLogin(String memID, String memPass,String equSerialNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			Map<String, Object> result=userService.memberLogin(memID,memPass,equSerialNo);
			
			if (result.get("msg").equals(Constants.USER_LOGIN_MSG[2])) {
				all.put("msg", result.get("msg").toString());
				all.put("success", (Boolean) result.get("success"));
				result.remove("msg");
				result.remove("success");
				all.put("data", result);
			} else {
				all.put("msg", result.get("msg").toString());
				all.put("success", (Boolean) result.get("success"));
				result.remove("msg");
				result.remove("success");
				all.put("data", result);
			}
		}catch(Exception e){
			all.put("success", false);
			all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}
		return new ModelAndView("/userinfo/show", all);
	}
}
