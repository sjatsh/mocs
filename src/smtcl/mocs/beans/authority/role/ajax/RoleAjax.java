package smtcl.mocs.beans.authority.role.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dreamwork.jasmine2.ajax.RequestMethod;
import org.dreamwork.jasmine2.annotation.AjaxMethod;
import org.dreamwork.jasmine2.annotation.AjaxParameter;
import org.dreamwork.jasmine2.annotation.AjaxService;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.persistence.Sort;
import org.json.JSONObject;

import smtcl.mocs.pojos.authority.Button;
import smtcl.mocs.pojos.authority.Page;
import smtcl.mocs.pojos.authority.Role;
import smtcl.mocs.services.authority.IRoleService;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * 角色相关的Ajax处理器.
 *
 * User: gjy
 * Date: 2012-10-15
 */
@AjaxService ("role")
public class RoleAjax {
	/**
	 * 验证角色名是否唯一
	 */
	@AjaxMethod(method = RequestMethod.POST)
	public String validateRoleName( @AjaxParameter(name = "roleName") String roleName) {
		IRoleService service = (IRoleService) ServiceFactory.getBean ("roleService");
		JSONObject result = new JSONObject();
		
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("name", roleName, Operator.EQ));
		List<Role> list = service.find(Role.class, (List<Sort>) null, params);
		if(list.size()>0){
			result.put("succ", false);
		}else{
			result.put("succ", true);
		}
		return "(" + result.toString() + ")";
	}

    @AjaxMethod(method = RequestMethod.POST)
    public String getPageInfo (@AjaxParameter(name = "pageId") String pageIds) {
        String [] pageId = pageIds.split("_-_");
        IRoleService service = (IRoleService) ServiceFactory.getBean("roleService");
        JSONObject result = new JSONObject();
        List <Page> pageList = service.getPageInfo(pageId);
        List<Map> list = new ArrayList<Map>();
        HttpContext context = HttpContext.current();
        for (Page p : pageList) {
            Map map = new HashMap();
            map.put("pageId", p.getPageId());
            map.put("pageName", SessionHelper.getTextByKey(null, context.getSession(), p.getPageName()));
            map.put("moduleName", SessionHelper.getTextByKey(null, context.getSession(), p.getModule().getModuleName()));
            map.put("appName", SessionHelper.getTextByKey(null, context.getSession(), p.getModule().getApp().getAppName()));
            StringBuffer buf = new StringBuffer();
            
            buf.append("			<table  class=\"fun_menu_table_four_info_table\" style=\"padding-top:7px;\" border=\"0\" cellpadding=\"0px\" cellspacing=\"0px\" width=\"100%\">")
     	   	   .append("				<tbody><tr class=\"fun_menu_table_four_info_table_tr1\">")
               .append("				<td class=\"fun_menu_table_two_info_table_three_td1\">&nbsp;&nbsp;&nbsp;&nbsp;</td>");
            for(Button btn : p.getButtons()) {
            	buf.append("				<td class=\"fun_menu_table_two_info_table_three_td1\">") 
	         	   .append("					<input name=\"button\" type=\"checkbox\" ")
	         	   .append("id = \"").append(p.getPageId())
	         	   .append(IConsant.BTN_PREFIX).append(btn.getButtonId()).append("\"")
	         	   .append("value=\"").append(p.getPageId())
	         	   .append(IConsant.BTN_PREFIX).append(btn.getButtonId()).append("\"/>") 
	         	   .append("					&nbsp;&nbsp;&nbsp;&nbsp;")
	         	   .append(SessionHelper.getTextByKey(null, context.getSession(),
	                            btn.getButtonName()))
	               .append("						</td> ");
	                
            	
            	/*
            	buf.append("	<li class=\"fun_menu_li2\">")
            	   .append("		<div class=\"fun_menu_li2_info\">")
            	   .append("			<div class=\"fun_menu_li2_info_left\">")
            	   .append("				<div class=\"function_role_ck1s\"  style=\"vertical-align:top;\">")
            	   .append("					<input name=\"button\" type=\"checkbox\" ")
            	   .append("id = \"").append(p.getPageId())
            	   .append(IConsant.BTN_PREFIX).append(btn.getButtonId()).append("\"")
            	   .append("value=\"").append(p.getPageId())
            	   .append(IConsant.BTN_PREFIX).append(btn.getButtonId()).append("\"/>")  
            	   .append("				</div> ")
            	   .append("			</div> ")
            	   .append("			<div class=\"fun_menu_li2_info_right\">")
            	   .append(SessionHelper.getTextByKey(null, context.getSession(),
                               btn.getButtonName()))
                   .append("			</div> ")
                   .append("			<div class=\"clear\"></div> ")
                   .append("		</div> ")
                   .append("	</li> ")
                   .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                */
            }
            buf.append("	<td class=\"fun_menu_table_two_info_table_three_td1\">&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></tbody></table> ");
            
            map.put("btn", buf.toString());
            map.put("remark", "");
            list.add(map);
        }
        result.put("pages", list);
        return "(" + result.toString() +")";
    }
	
    /**
     * 获取所有页面列表
     * @return 数据库中定义的所有页面列表，以JSON对象表达式方式返回
     */
//    @AjaxMethod
//    public String getPageList () {
//        IRoleService service = (IRoleService) ServiceFactory.getBean ("roleService");
//        List<TreeNode> list = service.getTreeNodes();
//        JSONArray array = new JSONArray(list);
//        return "("+array.toString()+")";
//    }
    
    /**
     * 设置当前角色下的用户
     */
    @AjaxMethod
    public String saveUser (@AjaxParameter (name = "roleId") String roleId,@AjaxParameter (name = "userId") String userId) {
        IRoleService service = (IRoleService) ServiceFactory.getBean ("roleService");
        boolean r = false;//service.saveUsersByRoleId(roleId,userId);
        JSONObject result = new JSONObject();
        if(r){
			result.put("succ", true);
		}else{
			result.put("succ", false);
			result.put("msg", "error");
		}
        return "("+result.toString()+")";
    }
    
    /**
     * 保存勾选的权限
     */
    @AjaxMethod
    public String savePermission (@AjaxParameter (name = "roleId") long roleId,@AjaxParameter (name = "nodeIds") String nodeId) {
        IRoleService service = (IRoleService) ServiceFactory.getBean ("roleService");
        boolean r = false;//service.savePermissionByRoleId(roleId,nodeId);
        JSONObject result = new JSONObject();
        if(r){
			result.put("succ", true);
		}else{
			result.put("succ", false);
			result.put("msg", "error");
		}
        return "("+result.toString()+")";
    }
    
    /**
	 * 验证角色代码是否唯一
	 */
	@AjaxMethod(method = RequestMethod.POST)
	public String validateRoleCode( @AjaxParameter(name = "roleCode") String roleCode) {
		IRoleService service = (IRoleService) ServiceFactory.getBean ("roleService");
		JSONObject result = new JSONObject();
		
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("code", roleCode, Operator.EQ));
		List<Role> list = service.find(Role.class, (List<Sort>) null, params);
		if(list.size()>0){
			result.put("succ", false);
		}else{
			result.put("succ", true);
		}
		return "(" + result.toString() + ")";
	}
}