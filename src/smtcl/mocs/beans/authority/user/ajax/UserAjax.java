package smtcl.mocs.beans.authority.user.ajax;

import java.util.ArrayList;
import java.util.List;

import org.dreamwork.jasmine2.ajax.RequestMethod;
import org.dreamwork.jasmine2.annotation.AjaxMethod;
import org.dreamwork.jasmine2.annotation.AjaxParameter;
import org.dreamwork.jasmine2.annotation.AjaxService;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.persistence.Sort;
import org.json.JSONObject;

import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.services.authority.ICommonService;


/***
 * 
 * @author jun
 * 功能：
 * 验证用户登录名是否唯一
 */
@AjaxService("userAjax")
public class UserAjax {
	/**
	 * 验证用户登录名是否唯一
	 */
	@AjaxMethod(method = RequestMethod.POST)
	public String validateLoginName( @AjaxParameter(name = "loginName") String loginName) {
		ICommonService service = (ICommonService) ServiceFactory.getBean("commonService");
		JSONObject result = new JSONObject();
		
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("loginName", loginName.trim(), Operator.EQ));
		params.add(new Parameter("status",new Long(1), Operator.EQ));
		List<User> list = service.find(User.class, (List<Sort>) null, params);
		if(list.size()>0){
			result.put("succ", false);
		}else{
			result.put("succ", true);
		}
		return "(" + result.toString() + ")";
	}
	
	
	/**
	 * 验证昵称是否唯一
	 */
	@AjaxMethod(method = RequestMethod.POST)
	public String validateNickName( @AjaxParameter(name = "nickName") String loginName) {
		ICommonService service = (ICommonService) ServiceFactory.getBean("commonService");
		JSONObject result = new JSONObject();
		
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("nickName", loginName.trim(), Operator.EQ));
		params.add(new Parameter("status",new Long(1), Operator.EQ));
		List<User> list = service.find(User.class, (List<Sort>) null, params);
		if(list.size()>0){
			result.put("succ", false);
		}else{
			result.put("succ", true);
		}
		return "(" + result.toString() + ")";
	}
		
}