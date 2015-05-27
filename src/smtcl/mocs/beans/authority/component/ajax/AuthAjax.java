/**
 * com.swg.authority.component.web.ajax AuthAjax.java
 */
package smtcl.mocs.beans.authority.component.ajax;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.ajax.RequestMethod;
import org.dreamwork.jasmine2.annotation.AjaxMethod;
import org.dreamwork.jasmine2.annotation.AjaxParameter;
import org.dreamwork.jasmine2.annotation.AjaxService;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.authority.OrgGroup;
import smtcl.mocs.pojos.authority.Role;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.JsonResponseResult;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * @author gaokun
 * @create Nov 28, 2012 11:36:38 PM
 */
@AjaxService ("auth")
public class AuthAjax {
	
	@AjaxMethod (method = RequestMethod.POST)
    public String u_query (@AjaxParameter (name = "userId") String userId,
    		@AjaxParameter (name = "name") String condition,
    		@AjaxParameter (name = "leftLimit") String leftLimit,
    		@AjaxParameter (name = "pageId") String pageId,
    		@AjaxParameter (name = "buttonId") String buttonId,
    		@AjaxParameter (name = "pageNo") int pageNo,
    		@AjaxParameter (name = "pageSize") int pageSize) {
		JsonResponseResult jrr = new JsonResponseResult();
		try{
			Boolean b = SessionHelper.userBindNode(HttpContext.current().getSession());
			IDataCollection<User>		c_user = AuthorityUtil.queryAuthUserList(userId, b, pageId, buttonId, pageNo, pageSize, condition);
			
			Map<String, Map<String,Object>> select_map_u = new LinkedHashMap<String, Map<String,Object>>();
	        for (User u : c_user.getData()) {
	            String id	= u.getUserId() + "";
	            String temp	= StringUtils.isBlank(u.getNickName()) ? "" : "(" + u.getNickName() + ")";
	            String name	= u.getLoginName() + temp;
	            //add by gjy 2012-12-21
	            //bug #130 绑定用户和数据角色时，查询数据角色，请显示数据角色的代码和名称2列
	            String code	= u.getUserCode();
	            //add end

	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("id", id);
	            m.put("name", name);
	            //add by gjy 2012-12-21
	            //bug #130 绑定用户和数据角色时，查询数据角色，请显示数据角色的代码和名称2列
	            m.put("code", code);
	            //add end
	            m.put("checked", false);
	            select_map_u.put(id, m);
	        }
	        
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("data", select_map_u);
			m.put("total", c_user.getTotalRows());
			
			jrr.setContent(m);
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg(SessionHelper.getText("app.non-date.info"));
		}
		return jrr.toJsonString();
    }
	
	@AjaxMethod (method = RequestMethod.POST)
    public String r_query(@AjaxParameter (name = "userId") String userId,
    		@AjaxParameter (name = "name") String condition,
    		@AjaxParameter (name = "leftLimit") String leftLimit,
    		@AjaxParameter (name = "pageId") String pageId,
    		@AjaxParameter (name = "buttonId") String buttonId,
    		@AjaxParameter (name = "pageNo") int pageNo,
    		@AjaxParameter (name = "pageSize") int pageSize) {
		JsonResponseResult jrr = new JsonResponseResult();
		try{
			IDataCollection<Role>		c_role = AuthorityUtil.queryAuthRoleList(userId, pageId, buttonId, pageNo, pageSize, condition, leftLimit);
			Map<String, Map<String,Object>> select_map_r = new LinkedHashMap<String, Map<String,Object>>();
			for (Role r : c_role.getData()) {
	            String id	= r.getId() + "";
	            String name	= r.getName();
	            //add by gjy 2012-12-21
	            //bug #130 绑定用户和数据角色时，查询数据角色，请显示数据角色的代码和名称2列
	            String code	= r.getCode();
	            //add end

	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("id", id);
	            m.put("name", name);
	            //add by gjy 2012-12-21
	            //bug #130 绑定用户和数据角色时，查询数据角色，请显示数据角色的代码和名称2列
	            m.put("code", code);
	            //add end
	            m.put("checked", false);
	            select_map_r.put(id, m);
	        }
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("data", select_map_r);
			m.put("total", c_role.getTotalRows());
			
			jrr.setContent(m);
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg(SessionHelper.getText("app.non-date.info"));
		}
		return jrr.toJsonString();
    }
	
	@AjaxMethod (method = RequestMethod.POST)
    public String g_query(@AjaxParameter (name = "userId") String userId,
    		@AjaxParameter (name = "name") String condition,
    		@AjaxParameter (name = "leftLimit") String leftLimit,
    		@AjaxParameter (name = "pageId") String pageId,
    		@AjaxParameter (name = "buttonId") String buttonId,
    		@AjaxParameter (name = "pageNo") int pageNo,
    		@AjaxParameter (name = "pageSize") int pageSize) {
		JsonResponseResult jrr = new JsonResponseResult();
		try{
			IDataCollection<OrgGroup>	c_orgGroup = AuthorityUtil.queryAuthOrgGroupList(userId, pageId, buttonId, pageNo, pageSize, condition, Collections.EMPTY_LIST, leftLimit);
			Map<String, Map<String,Object>> select_map_g = new LinkedHashMap<String, Map<String,Object>>();
			for (OrgGroup g: c_orgGroup.getData()) {
	            String id	= g.getOrgGroupId() + "";
	            String name	= g.getGroupName();
	            //add by gjy 2012-12-21
	            //bug #130 绑定用户和数据角色时，查询数据角色，请显示数据角色的代码和名称2列
	            String code	= g.getCode();
	            //add end

	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("id", id);
	            m.put("name", name);
	            //add by gjy 2012-12-21
	            //bug #130 绑定用户和数据角色时，查询数据角色，请显示数据角色的代码和名称2列
	            m.put("code", code);
	            //add end
	            m.put("checked", false);
	            select_map_g.put(id, m);
	        }
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("data", select_map_g);
			m.put("total", c_orgGroup.getTotalRows());
			
			jrr.setContent(m);
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg(SessionHelper.getText("app.non-date.info"));
		}
		return jrr.toJsonString();
    }
	
	@AjaxMethod
    public String auth_view (@AjaxParameter (name = "userId") String userId) {
		JsonResponseResult jrr = new JsonResponseResult();
		try{
			ICommonService service = (ICommonService)ServiceFactory.getBean("commonService");
			
			String hql_view = 	" select new map(rgo.org.orgId as dataid, rgo.org.name as data, rrb.page.module.app.appId as aid, rrb.page.module.app.appName as aname, rrb.page.pageId as pid, rrb.page.pageName as pname, rrb.button.buttonId as bid, rrb.button.buttonName as bname ) " +
								" from RGroupOrg rgo, RUserRoleOrgGroup ruro, RRoleButton rrb " +
								" where ruro.user.userId = :userId " +
								"		and ruro.orgGroup.orgGroupId = rgo.orgGroup.orgGroupId " +
								"		and ruro.role.id = rrb.role.id " +
								" order by rrb.page.module.app.appName , rrb.page.pageId, rrb.button.buttonId ";
			
			List<Map> map_view = service.executeQuery(hql_view, new Parameter[]{new Parameter("userId", userId, Operator.EQ)});
			
			Map<String, Map<String, Object>> map_result = new LinkedHashMap<String, Map<String, Object>>();
			for (Map map : map_view) {
				String dataid 	= (String)map.get("dataid");
				String data		= (String)map.get("data");
				String aid		= (String)map.get("aid");
				String aname	= (String)map.get("aname");
				String pid		= (String)map.get("pid");
				String pname	= (String)map.get("pname");
				String bid		= (String)map.get("bid");
				String bname	= (String)map.get("bname");
				
				
				Map<String, Object> m = map_result.get(dataid);
				if(m == null){
					m	=	new HashMap<String, Object>();
					map_result.put(dataid, m);
					
					m.put("data", data);
					m.put("role", new LinkedHashMap<String, Object>());
				}
				
				Map<String, Object> app = (Map<String, Object>)((Map)m.get("role")).get(aid);
				if(app == null){
					app = new LinkedHashMap<String, Object>();
					((Map)m.get("role")).put(aid, app);
					app.put("app", SessionHelper.getText(aname));
					app.put("detail", new LinkedHashMap<String, String>());
				}
				((Map)app.get("detail")).put(pid + "," + bid, SessionHelper.getText(pname) + "(" +SessionHelper.getText(bname) + ")");
			}
			
			jrr.setContent(map_result.values());
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg(SessionHelper.getText("app.non-date.info"));
		}
		return jrr.toJsonString();
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}