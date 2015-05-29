package smtcl.mocs.web.webservice.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.filter.reqres.Request;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.common.device.JsonResponseResult;
import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.common.device.RemoteProxyFactory;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IWSUserService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.MenuColourUtils;

/**
 * WebService�û���¼���ýӿ�
 * 
 * @������ JiangFeng
 * @�޸��� songkaiang
 * @��ע
 * @����ʱ�� 2013-04-26
 * @�޸�����
 * @�޸�˵��
 * @�汾 V1.0
 */
@Controller
@RequestMapping("/WSUserService")
public class WSUserServiceMVC {
	IWSUserService userService = (IWSUserService) ServiceFactory.getBean("wsUserService");
	IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory.getBean("authorizeService");
	IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	
	/**
	 * ��֤�û���¼
	 * 
	 * @param username �û�Id
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
				
               // �ȹ��˶�set���ϵĲ��
                JsonConfig config = new JsonConfig();
                 config.setJsonPropertyFilter(new PropertyFilter() {
                     @Override
                     public boolean apply(Object arg0, String arg1, Object arg2) {//���R����Ҫ���ֶ�
                         return arg1.equals("module") || arg1.equals("app") || arg1.equals("JSONObject") || arg1.equals("buttons");
                    }
               });
				//ͨ��JSONArrayתΪ�ַ���
				JSONArray gg = JSONArray.fromObject(userModule, config);
				
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
	 * ��ȡ��ǰ�ڵ��ֱ���ӽڵ�
	 * @param userId �û�Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getAllNodesByParentNodeId", method = RequestMethod.GET)
	public ModelAndView getAllNodesByParentNodeId(HttpServletRequest request,String nodeId,String userId) {
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> result=organizationService.getAllNodesByParentNodeId(request.getSession(),nodeId, userId);
			if(null!=result&&result.size()>0){
				all.put("data", result);
				all.put("success", true);
				all.put("msg", Constants.USER_LOGIN_MSG[2]);
			}else{
				all.put("data", result);
				all.put("success", false);
				all.put("msg","û���ӽڵ�");
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
	 * �����������
	 * @param equSerialNo �������к�
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
	 * ������������
	 * @param equSerialNo �������к�
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
	 * �л�����
	 * @param equSerialNo �������к�
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
				all.put("data", null);
				
			}
		}catch(Exception e){
			all.put("msg", Constants.USER_UPDATE_MSG[1]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * �л�ģʽ
	 * @param equSerialNo �������к�
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
	 * ������������
	 * @param equSerialNo �������к�
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
	 * ��ȡ�˵�
	 * @param userId �û�ID
	 * @param appId appID
	 */
	@RequestMapping(value = "/getMenu", method = RequestMethod.GET)
	public ModelAndView getMenu(HttpServletRequest request,String userId, String appId){
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			List<Map<String,Object>> rs=authorizeService.getMenu(request.getSession(), userId,appId);
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
	 * �û���¼
	 * @param equSerialNo �������к�
	 */
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public ModelAndView memberLogin(String memID, String memPass,String equSerialNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			Map<String, Object> result=userService.memberLogin(memID,memPass,equSerialNo);
			
			if (result.get("msg").equals(Constants.USER_LOGIN_MSG[2])) {
				all.put("msg", result.get("msg").toString());
				all.put("success", result.get("success"));
				result.remove("msg");
				result.remove("success");
				all.put("data", result);
			} else {
				all.put("msg", result.get("msg").toString());
				all.put("success", result.get("success"));
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
	
	/**
	 * �������߼��
	 * @param equSerialNo �������к�
	 * @param jobDispatchNo	������
	 */
	@RequestMapping(value = "/JobOnlineCheck", method = RequestMethod.POST)
	public ModelAndView JobOnlineCheck(String equSerialNo, String jobDispatchNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try{
			int flag = userService.JobOnlineCheck(equSerialNo, jobDispatchNo);
			all.put("jobOnlineCheckOk", flag);
			if(flag == 2){
				all.put("msg", "�ù����ӹ������ѳ��ƻ��������н�����֪ͨ����Աȷ�ϣ�������ȡ���ӹ�����������");
				all.put("success","true");
			}else{
				all.put("msg", "���Լӹ�");
				all.put("success","true");
			}
		}catch(Exception e){
			e.printStackTrace();
			all.put("success", false);
			all.put("msg", Constants.IS_OK_NOTOK[0]);
		}
		return new ModelAndView("/userinfo/show", all);
	}
	
	/**
	 * ���ù��ʻ����԰汾
	 * @param request request
	 * @param local ���Ա��
	 */
	@RequestMapping(value = "/setLocal", method = RequestMethod.GET)
	public void setLocal(HttpServletRequest request, String local) {
		HttpSession session = request.getSession();
		Locale language = (Locale)session.getAttribute(IWebControl.LOCALE_KEY);
		if (!StringUtils.isEmpty(local)
				&& !StringUtils.isEmpty(language.toString())
				&& !language.toString().equals(local)) {
			Locale locale = new Locale(local);
			session.setAttribute(IWebControl.LOCALE_KEY, locale);
		}
	}

    /**
     * ��ȡ��̨���ʻ����Ա��
     * @param request request
     */
    @RequestMapping(value = "/getLocale", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> JobOnlineCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = SessionHelper.getCurrentLocale(session);
        Map<String,String> map = new HashMap<String,String>();
        map.put("locale",locale.toString());
        return map;
    }

    /**
     * ��ȡ�˵���Ϣ
     * @param request request
     */
    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    @ResponseBody
    public String getMenuList(HttpServletRequest request){
        TUser user = (TUser) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
        List<Map<String,Object>> menuList = authorizeService.getMenu(request.getSession(), user.getUserId(), "mocs");
        for(Map<String,Object> map : menuList){
            String colourMenu = map.get("moduleName").toString();
            map.put("colour", MenuColourUtils.getMenuColourUtils().getColourMenu().get(colourMenu));
        }
        JsonResponseResult json=new JsonResponseResult();
		json.setContent(menuList);
		return json.toJsonString();
    }
    
    
    @RequestMapping(value = "/getMapdata", method = RequestMethod.GET)
	public ModelAndView getMapdata(HttpServletRequest request) {
    	Map<String, Object> all = new HashMap<String, Object>();
		List<Map<String, Object>> rs=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		try{
			
			TUser user = (TUser) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
			//��ǰ�û��Ľڵ�Ȩ��
			result=organizationService.getAllNodesByParentNodeId(request.getSession(),"-999", user.getUserId());
			//���еĽڵ�
			rs=RemoteProxyFactory.getInstance().getMapNode();
			for(Map<String, Object> rsm:rs){
				boolean bool=true;
				for(Map<String, Object> rmap:result){
					if(null!=rmap.get("nodeId")&&null!=rsm.get("nodeId")&&rmap.get("nodeId").toString().equals(rsm.get("nodeId").toString())){
						bool=false;
					}
				}
				if(bool){
					rsm.put("nodeId", "");
				}
			}
			all.put("content", rs);	
			all.put("success", true);
			all.put("msg", Constants.USER_QUERY_MSG[0]);
		}catch(Exception e){
			e.printStackTrace();
				all.put("success", false);
				all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}
		
		return new ModelAndView("/userinfo/show", all);
	}
	
   
}
