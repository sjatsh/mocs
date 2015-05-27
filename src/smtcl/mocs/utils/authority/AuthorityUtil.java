/**
 * com.swg.authority.util AuthorityUtil.java
 */
package smtcl.mocs.utils.authority;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dreamwork.i18n.IResourceAdapter;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.services.authority.ITreeService;
import smtcl.mocs.beans.authority.cache.FunTree;
import smtcl.mocs.beans.authority.cache.OrgTree;

import smtcl.mocs.beans.authority.cache.NodeData;
import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.pojos.authority.OrgGroup;
import smtcl.mocs.pojos.authority.Organization;
import smtcl.mocs.pojos.authority.Role;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.common.authority.i18n.DatabaseWebResourceManagerFactory;
import smtcl.mocs.services.authority.ICommonService;

/**
 * 权限获取工具类;
 * @author gaokun
 * @create Oct 12, 2012 10:42:39 AM
 */
public class AuthorityUtil {
	/**
	 * AuthorityUtil 日志类;
	 */
	private final static Logger logger = Logger.getLogger(AuthorityUtil.class);
	
	/**
	 * 判断用户是否拥有权限;
	 * @param userId
	 * @param pageId
	 * @param buttonId
	 * @return
	 */
	public static boolean judge(String userId, String pageId, String buttonId){
		return judge(userId, null, pageId, buttonId);
	}
	
	/**
	 * 判断用户是否拥有权限;
	 * @param userId
	 * @param orgId
	 * @param pageId
	 * @param buttonId
	 * @return
	 */
	public static boolean judge(String userId, String orgId, String pageId, String buttonId){
		if(isAdmin(userId))
			return true;
		
		ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		
		User u1 = se.get(User.class, userId);
		if(u1 == null)
			return false;
		
		if(IConsant.INVALID == u1.getStatus())
			return false;
		
		List<Parameter> params = new ArrayList<Parameter>();
		
		String hql =		" 	from		RUserRoleOrgGroup ruro, " +
							"				RRoleButton rrb %s " +
							" 	where	%s  ruro.role.id = rrb.role.id " +
							" 			and ruro.user.userId = :userId " +
							" 			and rrb.page.pageId = :pageId " +
							" 			and rrb.button.buttonId = :buttonId %s";
		
		String s1 = "";
		String s2 = "";
		String s3 = "";
		if(StringUtils.isNotBlank(orgId)){
			String nodeId  = idenForward(new String[]{orgId})[0];
			s1 = " , RGroupOrg rgo ";
			s2 = " rgo.orgGroup.orgGroupId = ruro.orgGroup.orgGroupId and ";
			s3 = " and rgo.org.orgId in (select fo.ancestorId from FlatOrg fo where fo.nodeId = :nodeId) ";
			
			Parameter n = new Parameter("nodeId", nodeId, Operator.EQ);
			params.add(n);
		}
		
		hql  = String.format(hql, s1, s2, s3);
		
		authAddParams(params, userId, pageId, buttonId);
		
		List<Object> result = se.executeQuery(hql, params);
		return !result.isEmpty();
	}
	
	/**
	 * 查找指定用户下某个页面的权限;
	 * @param userId
	 * @param pageId
	 * @return
	 */
	public static List<String> findUserFunctionByPage(String userId, String pageId){
		return findUserFunctionByPage(userId, null, pageId);
	}
	
	/**
	 * 查找指定用户及区域下某个页面的权限;
	 * @param userId
	 * @param orgId
	 * @param pageId
	 * @return
	 */
	public static List<String> findUserFunctionByPage(String userId, String orgId, String pageId){
		List<String> result = Collections.emptyList();
		
		ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		
		List<Parameter> params = new ArrayList<Parameter>();
		
		Parameter p = new Parameter("pageId", pageId, Operator.EQ);
		params.add(p);
		
		String hql = " select rpb.button.buttonId from RPageButton rpb where rpb.page.pageId = :pageId ";
		
		if(isAdmin(userId)){
			result = se.executeQuery(hql, params);
			return result;
		}
		
		String s1 = "";
		String s2 = "";
		String s3 = "";
		hql =	" select	rrb.button.buttonId " +
				" from		RUserRoleOrgGroup ruro, RRoleButton rrb %s " +
				" where		%s ruro.role.id = rrb.role.id " +
				"		and ruro.user.userId = :userId " +
				"		and rrb.page.pageId = :pageId  %s ";
		
		if(StringUtils.isNotBlank(orgId)){
			String nodeId  = idenForward(new String[]{orgId})[0];
			s1 = " ,RGroupOrg rgo ";
			s2 = " ruro.orgGroup.orgGroupId = rgo.orgGroup.orgGroupId and ";
			s3 = " and rgo.org.orgId in (select fo.ancestorId from FlatOrg fo where fo.nodeId =:nodeId) ";
			
			Parameter n = new Parameter("nodeId", nodeId, Operator.EQ);
			params.add(n);
		}
		
		hql  = String.format(hql, s1, s2, s3);
		
		Parameter u = new Parameter("userId", userId, Operator.EQ);
		params.add(u);
		
		result = se.executeQuery(hql, params);
		return result;
	}
	
	/**
	 * 获得页面和按钮;
	 * @param userId
	 * @param pageId
	 * @param buttonId
	 * @param locale
	 * @return
	 */
	public static List<TreeNode> queryAuthFunctionTree(String userId, String pageId, String buttonId, Locale locale){
		IResourceAdapter adapter = DatabaseWebResourceManagerFactory.getResourceManager().getResourceAdapter(null);
		
		boolean admin = false;
		if(isAdmin(userId)){
			admin = true;
		}
		
		List<Parameter> params = new ArrayList<Parameter>();
		
		String s1 = "";
		String s2 = "";
		String s3 = "";
		//页面;
		String hql_fun =	" select new map(	app.appId as appid, " +
							"					app.appName as appname, " +
							"					m.moduleId as mid, " +
							"					m.moduleName as mname, " +
							"					p.pageId as pid, " +
							"					p.pageName as pname, " +
							"					b.buttonId as bid, " +
							"					b.buttonName as bname	) " +
							" from	Application app, " +
							"		Module m, " +
							"		Page p, " +
							"		Button b, " +
							"		RPageButton rpb %s " +
							" where rpb.page.pageId = p.pageId " +
							"	and b.buttonId = rpb.button.buttonId " +
							"	and p.module.moduleId = m.moduleId " +
							"	and m.app.appId = app.appId %s %s ";
		if(!admin){
			s1 = " , RRoleButton rrb, RUserRoleOrgGroup ruro ";
			s2 = " and rrb.role.id = ruro.role.id and ruro.user.userId = :userId ";
			s3 = " and rrb.page.pageId = p.pageId and rrb.button.buttonId = b.buttonId ";
			
			Parameter u = new Parameter("userId", userId, Operator.EQ);
			params.add(u);
//			authAddParams(params, userId, pageId, buttonId);
		}
		
		hql_fun = String.format(hql_fun, s1, s2, s3);
		
		ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		List<Map> list = se.executeQuery(hql_fun, params);
		
		Map<String, NodeData> map_nd = new HashMap<String, NodeData>();
		for (Map map : list) {
			String appid		= (String)map.get("appid");
			String appname	 	= (String)map.get("appname");
			
			String mid			= (String)map.get("mid");
			String mname		= (String)map.get("mname");
			
			String pid			= IConsant.PAGE_PREFIX + (String)map.get("pid");
			String pname		= (String)map.get("pname");
			
			String bid			= pid + IConsant.BTN_PREFIX + (String)map.get("bid");
			String bname		= (String)map.get("bname");
			
			//应用系统;
			NodeData app_nd = map_nd.get(appid);
			if(app_nd == null){
				app_nd = new NodeData();
				app_nd.setNodeId(appid);
				app_nd.setNodeName(adapter.getString(locale, appname));
				app_nd.setParentId(null);
				app_nd.setType(IConsant.APP_PREFIX);
				
				map_nd.put(appid, app_nd);
			}
			
			NodeData mod_nd = map_nd.get(mid);
			if(mod_nd == null){
				mod_nd = new NodeData();
				mod_nd.setNodeId(mid);
				mod_nd.setNodeName(adapter.getString(locale, mname));
				mod_nd.setParentId(appid);
				mod_nd.setType(IConsant.MOD_PREFIX);
				
				map_nd.put(mid, mod_nd);
			}
			
			NodeData page_nd = map_nd.get(pid);
			if(page_nd == null){
				page_nd = new NodeData();
				page_nd.setNodeId(pid);
				page_nd.setNodeName(adapter.getString(locale, pname));
				page_nd.setParentId(mid);
				page_nd.setType(IConsant.PAGE_PREFIX);
				
				map_nd.put(pid, page_nd);
			}
			
			NodeData btn_nd = map_nd.get(bid);
			if(btn_nd == null){
				btn_nd = new NodeData();
				btn_nd.setNodeId(bid);
				btn_nd.setNodeName(adapter.getString(locale, bname));
				btn_nd.setParentId(pid);
				btn_nd.setType(IConsant.BTN_PREFIX);
				
				map_nd.put(bid, btn_nd);
			}
		}
		
		List<NodeData> result = new ArrayList<NodeData>();
		for (NodeData nd : map_nd.values()) {
			result.add(nd);
		}
		
		FunTree tree = new FunTree(false, false, null);
		tree.reloadTree(result);
		
		return tree.getRoots();
	}
	
	/**
	 * 获得指定用户页面和权限的组织架构集合;
	 * @param userId
	 * @param pageId
	 * @param buttonId
	 * @return
	 */
	public static List<String> queryAuthNodes(String userId, String pageId, String buttonId){
		ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		List<Parameter> params = new ArrayList<Parameter>();
		String hql = "select orgId from Organization where state = 1";
		if(!isAdmin(userId)){
			hql = authHQL("@auth@", null, params, userId, pageId, buttonId);
		}
		return se.executeQuery( hql, params);
	}
	
	/**
	 * 获取可供选择的用户;
	 * @param userId 登录用户id
	 * @return
	 */
	public static IDataCollection<User> queryAuthUserList(String userId, String pageId, String buttonId, int pageNo, int pageSize, String name){
        return queryAuthUserList(userId, true, pageId, buttonId, pageNo, pageSize, name, Collections.EMPTY_LIST);
    }
	
    public static IDataCollection<User> queryAuthUserList(String userId, boolean userBindNode, String pageId, String buttonId, int pageNo, int pageSize, String name){
    	List<Parameter> params = new ArrayList<Parameter>();
    	Parameter p = new Parameter("status", new Long(IConsant.INVALID), Operator.NE);
    	params.add(p);
        return queryAuthUserList(userId, userBindNode, pageId, buttonId, pageNo, pageSize, name, params);
    }

	public static IDataCollection<User> queryAuthUserList(String userId, String pageId, String buttonId, int pageNo, int pageSize, List<Parameter> params1){
        return queryAuthUserList(userId, true, pageId, buttonId, pageNo, pageSize, null, params1);
    }
	
	/**
	 * 
	 * @param userId
	 * @param userBindNode false:不对用户进行过滤;
	 * @param pageId
	 * @param buttonId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static IDataCollection<User> queryAuthUserList(String userId, boolean userBindNode, String pageId, String buttonId, int pageNo, int pageSize, String name, List<Parameter> params1){
        ICommonService service = (ICommonService) ServiceFactory.getBean("commonService");
        String mark = " and @auth@ ";
        if(!userBindNode){
        	mark = "";
        }
        String hql = " from User u where @exadmin@ " + mark;
        hql = excludeAdminHQL(hql, "u.loginName", new String[]{User.ADMIN});
        List<Parameter> params = new ArrayList<Parameter>();
        
        if(StringUtils.isNotBlank( name )){
        	name = "%" + name.trim().toLowerCase() + "%";
        	hql += " and ( lower(u.loginName) like :condition  or lower(u.nickName) like :condition or lower(u.userCode) like :condition ) ";
        	params.add(new Parameter("condition", name, Operator.EQ));
        }
        
        if (params1.size()!=0){
        	for(Parameter p : params1){
        		hql += " and " + p;        		
                if (p.operator == Operator.IS_NULL || p.operator == Operator.IS_NOT_NULL){}
                else params.add(p);
        	}
        }
        hql = authHQL(hql, "u.orgId", params, userId, pageId, buttonId);
		return service.executeQuery(pageNo, pageSize, hql, params);
    }
	
	/**
	 * 获取可供选择的组织架构组;
	 */
	public static IDataCollection<OrgGroup> queryAuthOrgGroupList(String userId, String pageId, String buttonId, int pageNo, int pageSize, String name, List<Parameter> parameters, String leftLimit){
		ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		
		String hql = " from OrgGroup og where og.orgGroupId !='" + OrgGroup.ROOT_GROUP +"' and og.flag is null ";
		List<Parameter> params = new ArrayList<Parameter>();
		
		if(StringUtils.isNotBlank( name )){
        	name = "%" + name.trim().toLowerCase() + "%";
        	hql += " and ( lower(og.groupName) like :condition or lower(og.code) like :condition ) ";
        	params.add(new Parameter("condition", name, Operator.EQ));
        }
		
		if(!isAdmin(userId)){
			String t0 = " ";
			String t1 = " ";
        	if(StringUtils.isNotBlank(leftLimit)){
        		String[] ls = leftLimit.split(",");
        		int n = -1;
        		String tmp_1 = " select distinct rpb1.id from RRoleButton rrb1, RPageButton rpb1 where rpb1.page.pageId = rrb1.page.pageId and rpb1.button.buttonId = rrb1.button.buttonId and rrb1.role.id in (:roleIds)";
        		List list_tmp_1 = se.executeQuery(tmp_1, new Parameter[]{new Parameter("roleIds", ls, Operator.EQ)});
        		if(list_tmp_1.size() > 0){
        			n = list_tmp_1.size();
        			t1 = " ,RRoleButton rrb2, RPageButton rpb2 ";
        			t0 = " and ruro.role.id = rrb2.role.id and rpb2.page.pageId = rrb2.page.pageId and rpb2.button.buttonId = rrb2.button.buttonId and rpb2.id in (" + tmp_1 + ") group by rgo.org.orgId having count( distinct rpb2.id ) >= " + n +" ) ";
        		}else{
        			t0 = " and 1>2 ";
        		}
        		params.add(new Parameter("roleIds", ls, Operator.EQ));
        	}
			hql += " and og.orgGroupId not in ( select rgo1.orgGroup.orgGroupId from RGroupOrg rgo1, FlatOrg fo1 where fo1.ancestorId = rgo1.org.orgId and fo1.nodeId in ( select o.orgId from Organization o where o.orgId not in ( select fo.nodeId from FlatOrg fo where fo.ancestorId in ( select rgo.org.orgId from RUserRoleOrgGroup ruro, RGroupOrg rgo " + t1 + " where ruro.orgGroup.orgGroupId = rgo.orgGroup.orgGroupId and ruro.user.userId = :userId " + t0 + ") ) ) ) ";
			params.add(new Parameter("userId", userId, Operator.EQ));
		}

        if (parameters!=null)for (Parameter p : parameters) {
            if ("userId".equals(p.name)) {
                hql += " and orgGroupId in (select r.orgGroup.orgGroupId from RUserRoleOrgGroup r where r.user.userId = :ruserId)";
                params.add(new Parameter("ruserId", p.value, Operator.EQ));
            } else {
                hql += " and " + p;
                if (p.operator == Operator.IS_NULL || p.operator == Operator.IS_NOT_NULL){}
                else params.add(p);
            }
        }
        hql += " order by og.groupName";
		return se.executeQuery(true, pageNo, pageSize, hql, params);
	}
	
	/**
	 * 获取可供选择的角色列表;
	 */
	public static IDataCollection<Role> queryAuthRoleList(String userId, String pageId, String buttonId, int pageNo, int pageSize, String name, String leftLimit){
		/*ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		String hql = " from Role r where r.flag is null ";
		if(!isAdmin(userId)){
			hql =	" and r.id in ( select mro.role.id " +
					" from MRoleOrgGroup mro, " +
					"		RGroupOrg rgo " +
					" where mro.createOrgGroup.orgGroupId = rgo.orgGroup.orgGroupId " +
					" 		and @auth@ )";
		}
		hql += " order by r.name  ";
		List<Parameter> params = new ArrayList<Parameter>();
		hql = authHQL(hql, "rgo.org.orgId", params, userId, pageId, buttonId);
		return se.executeQuery(pageNo, pageSize, hql, params);*/
        return queryAuthRoleList(userId, pageId, buttonId, pageNo, pageSize, name, Collections.EMPTY_LIST, leftLimit);
	}

    /**
     * 获取可供选择的角色列表;
     */
    public static IDataCollection<Role> queryAuthRoleList(String userId, String pageId, String buttonId, int pageNo, int pageSize, String name, Collection<Parameter> parameters, String leftLimit){
        ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
        String hql = " from Role r where r.flag is null ";
        List<Parameter> params = new ArrayList<Parameter>();
        
        if(StringUtils.isNotBlank( name )){
        	name = "%" + name.trim().toLowerCase() + "%";
        	hql += " and ( lower(r.name) like :condition or lower(r.code) like :condition ) ";
        	params.add(new Parameter("condition", name, Operator.EQ));
        }
        
        if(!isAdmin(userId)){
        	String t0 = " ";
        	String tab = " ";
        	if(StringUtils.isNotBlank(leftLimit)){
        		String[] ls = leftLimit.split(",");
        		int n = -1;
        		String tmp_1 = " select distinct fo1.nodeId from FlatOrg fo1 where fo1.ancestorId in (select rgo1.org.orgId from RGroupOrg rgo1 where rgo1.orgGroup.orgGroupId in(:orgGroupIds))";
        		List list_tmp_1 = se.executeQuery(tmp_1, new Parameter[]{new Parameter("orgGroupIds", ls, Operator.EQ)});
        		if(list_tmp_1.size() > 0){
        			n = list_tmp_1.size();
        			tab = " ,RGroupOrg rgo2, FlatOrg fo2 ";
        			//t0 = " and ruro.orgGroup.orgGroupId in ( select rgo2.orgGroup.orgGroupId from RGroupOrg rgo2, FlatOrg fo2 where rgo2.org.orgId = fo2.ancestorId and fo2.nodeId in (" + tmp_1 + ") group by rgo2.orgGroup.orgGroupId having count(distinct fo2.nodeId) >= " + n + ")";
        			t0 = " and rgo2.org.orgId = fo2.ancestorId and fo2.nodeId in (" + tmp_1 + ") and ruro.orgGroup.orgGroupId = rgo2.orgGroup.orgGroupId group by rpb.id having count(distinct fo2.nodeId) >= " + n ;
        		}else{
        			t0 = " and 1>2 ";
        		}
        		params.add(new Parameter("orgGroupIds", ls, Operator.EQ));
        	}
        	hql += " and r.id not in( select rrb1.role.id from RRoleButton rrb1, RPageButton rpb1 where rpb1.page.pageId = rrb1.page.pageId and rpb1.button.buttonId = rrb1.button.buttonId and rpb1.id in ( select rpb2.id from RPageButton rpb2 where rpb2.id not in ( select rpb.id from RUserRoleOrgGroup ruro, RRoleButton rrb, RPageButton rpb " + tab + " where rpb.page.pageId = rrb.page.pageId and rpb.button.buttonId = rrb.button.buttonId and ruro.role.id = rrb.role.id and ruro.user.userId = :userId " + t0 + " ) ) ) ";
        	params.add(new Parameter("userId", userId, Operator.EQ));
        }
        if (parameters!=null)for (Parameter p : parameters) {
            hql += " and " + p;
            if (p.operator == Operator.IS_NULL || p.operator == Operator.IS_NOT_NULL){}
            else params.add(p);
        }
        hql += " order by r.name  ";
        return se.executeQuery(true, pageNo, pageSize, hql, params);
    }
	
	
	
	
	/**
	 * 一次获得全部数据;
	 * 获得用户可以选择的树;
	 * 用户获得组织架构之和组织架构组关联;
	 * @param userId
	 * @param use
	 * @return
	 */
	public static List<TreeNode> queryAuthOrgTree(String userId, String pageId, String buttonId, boolean use){
		return queryAuthOrgTree(userId, pageId, buttonId, null, false, null, null, use);
	}
	
	/**
	 * 异步树;根据父节点获取儿子节点;
	 * @param userId
	 * @param orgId
	 * @param use
	 * @return
	 */
	public static List<TreeNode> queryAuthOrgTree(String userId, String pageId, String buttonId, String orgId, boolean use, String filter){
		return queryAuthOrgTree(userId, pageId, buttonId, orgId, true, null, filter, use);
	}
	
	/**
	 * 异步树;根据父节点获取儿子节点;
	 * @param userId
	 * @param orgId
	 * @param exclude 排除的组织架构结点;
	 * @param use
	 * @return
	 */
	public static List<TreeNode> queryAuthOrgTree(String userId, String pageId, String buttonId, String orgId,String exclude, boolean use){
		return queryAuthOrgTree(userId, pageId, buttonId, orgId, true, exclude, null, use);
	}
	
	/**
	 * @param userId
	 * @param orgId
	 * @param asyn
	 * @param use
	 * @return
	 */
	public static List<TreeNode> queryAuthOrgTree(String userId, String pageId, String buttonId, String orgId, boolean asyn, String exclude, String filter, boolean use){
		List<TreeNode> result = Collections.emptyList();
		ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		
		List<Object> list_user_bind_org 	= Collections.emptyList();
		
		String hql_org = "select new map( (case when (o.parentId is null ) then true when exists(select 1 from Organization o1 where o1.parentId = o.orgId) then true else false end) as PARENT  , o as NODE)  from Organization o where show = " + IConsant.VALID;
		List<Parameter> params = new ArrayList<Parameter>();
		
		if(StringUtils.isNotBlank(filter)){
			hql_org +=" and o.orgType.typeId in (:filter) ";
			String[] filters = filter.split(",");
			params.add(new Parameter("filter", filters, Operator.EQ));
		}
		
		if(asyn){
			hql_org += " and o.parentId " +  (orgId == null ? " is null " : (" = '" + orgId + "'"));
		}
		
		if(StringUtils.isNotBlank(exclude)){
			hql_org += " and o.orgId <> '" + exclude + "'";
		}
		
		boolean nocheck = true;
		if(isAdmin(userId)){
			nocheck = false;
		}else{
			//用户绑定的组织架构节点集合;
			String hql_user_bind_org = authBindOrgHql(params, userId, pageId, buttonId);
			list_user_bind_org = se.executeQuery(hql_user_bind_org, params);
			if(list_user_bind_org.isEmpty())
				return result;
			
			hql_org += " and ( @authup@ or @auth@ ) " ;
			
			//用户绑定的组织架构祖先节点集合;
			hql_org = authHQL(hql_org, false, "o.orgId", params, userId, pageId, buttonId);
			
			//用户绑定的组织架构后代节点集合;
			hql_org = authHQL(hql_org, "o.orgId", params, userId, pageId, buttonId);
		}
		
		OrgTree tree = new OrgTree(nocheck, false, asyn ? true : null);
		List<Map> list_map = se.executeQuery(hql_org, params);
		
		List<Organization> list_org = new ArrayList<Organization>();
		for (Map map : list_map) {
			Organization o = (Organization)map.get("NODE");
			Boolean par = (Boolean)map.get("PARENT");
			o.setParent(par);
			list_org.add(o);
		}
		tree.reloadTree(list_org);
		
		if(asyn){
			if(StringUtils.isNotBlank(orgId)){
				List<Parameter> ps = new ArrayList<Parameter>();
				ps.add(new Parameter("orgId", orgId, Operator.EQ));
				String h_u_b = authBindOrgHql(ps, userId, pageId, buttonId);
				String h = " select 1 from FlatOrg t where t.nodeId = :orgId and t.ancestorId in (" + h_u_b + ")";
				List l = se.executeQuery(h, ps);
				if( l.size() > 0 ){
					for (TreeNode t : tree.getNodes()) {
						t.setNocheck(false);
					}
				}
			}
		}
		
		for(Object obj : list_user_bind_org){
			String nodeId = (String)obj;
			TreeNode tn = tree.getTreeNode(nodeId);
			if(tn != null){
				//选中;
				if(use){
					tn.setChecked(true);	
				}
				//赋权;
				if(asyn){
					tn.setNocheck(false);
				}else{
					tree.down(tn, false);
				}
			}
		}
		return asyn ? tree.getNodes() : tree.getRoots();
	}
	
	public static String getNodeTxt(String orgId){
		String result = "";
		String nodeId = idenForward(new String[]{orgId})[0];
		
		if(isRoot(nodeId)) return result;
			
		ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		
		List<Parameter> params = new ArrayList<Parameter>();
		String hql = " select o from Organization o, FlatOrg fo where o.orgId = fo.ancestorId and fo.nodeId = :nodeId ";
		params.add(new Parameter("nodeId", nodeId, Operator.EQ));
		
		List<Organization> os = se.executeQuery(hql, params);
		Map<String,Organization> map = new HashMap<String, Organization>();
		for (Organization o : os) {
			String key = o.getOrgId();
			map.put(key, o);
		}
		
		String key = nodeId;
		List<String> list = new ArrayList<String>();
		int i = 20;
		while(i > 0){
			Organization o = map.get(key);
			
			if(o == null)break;
			
			list.add(o.getName());
			
			key = o.getParentId();
			
			if(isRoot(key))break;
			
			key = o.getParentId();
			i--;
		}
		Collections.reverse(list);
		if(!list.isEmpty())
			result = "/" + StringUtils.join(list,"/");
		return result;
	}
	
	private static boolean isRoot(String key){
		return StringUtils.isBlank(key);
	}
	
	/**
	 * 组织架构唯一标识(组合标识按照","分隔)返回扁平化表中的nodeId;
	 * @param orgIds
	 * @return
	 */
	private static String[] idenForward(String[] orgIds){
		return orgIds;
	}
	
	/**
	 * 根据扁平化表中的nodeId返回组织架构唯一标识(组合标识按照","分隔);
	 * @param orgId
	 * @return
	 */
	private static String[] idenReverse(String[] nodeIds){
		return nodeIds;
	}
	
	public static boolean isAdmin(String userId){
		boolean result = false;
		ITreeService se = (ITreeService)ServiceFactory.getBean("treeService");
		User user = se.get(User.class, userId);
		if(user != null){
			result = User.ADMIN.equals(user.getLoginName());
		}
		return result;
	}
	
	/**
	 * 限定为绑定区域及子孙区域;
	 * @param hql
	 * @param field
	 * @param params
	 * @param userId
	 * @param pageId
	 * @param buttonId
	 * @return
	 */
	public static String authHQL(String hql, String field, List<Parameter> params, String userId, String pageId, String buttonId){
		return authHQL(hql, true, field, params, userId, pageId, buttonId);
	}
	
	/**
	 * 限定绑定区域;
	 * @param hql
	 * @param down true:绑定及子孙区域; false:绑定及祖先区域;
	 * @param field
	 * @param params
	 * @param userId
	 * @param pageId
	 * @param buttonId
	 * @return
	 */
	public static String authHQL(String hql, boolean down, String field, List<Parameter> params, String userId, String pageId, String buttonId){
		String result = null;
		if(StringUtils.isNotBlank(hql)){
			String mark = down ? IConsant.MARK_HQL_AUTH : IConsant.MARK_HQL_AUTH_UP;
			if(hql.indexOf(mark) > -1){
				String replace = " ( 1 = 1 ) ";
				if(!isAdmin(userId)){
					String hql_down =	"	select fo.nodeId " +
										"	from FlatOrg fo " +
										"	where fo.ancestorId in (" + authBindOrgHql(params, userId, pageId, buttonId) + ")";
					
					String hql_up	=	"	select fo.ancestorId " +
										"	from FlatOrg fo " +
										"	where fo.nodeId in (" + authBindOrgHql(params, userId, pageId, buttonId) + ")";
					
					replace =	down ? hql_down : hql_up ;
					if(StringUtils.isBlank(field)){
						replace  = String.format(" %s ", replace);
					}else{
						replace  = String.format(" ( %s in ( %s ) ) ", field, replace);
					}
					
				}
				result = hql.replace(mark, replace);
			}else{
				result = hql;
			}
		}
		return result;
	}
	
	public static String excludeAdminHQL(String hql, String fieldName, String[] admins){
		String result = null;
		if(StringUtils.isNotBlank(hql)){
			String mark = IConsant.MARK_HQL_EX_ADMIN;
			if(hql.indexOf(mark) > -1){
				String replace = " ( ";
				for(int i = 0; i < admins.length; i ++){
					if( i != 0){
						hql += " and ";
					}
					replace +=  fieldName + "!= '" + admins[i] +"'";
				}
				replace += " ) ";
				result = hql.replace(mark, replace);
			}else{
				result = hql;
			}
		}
		return result;
	}
	
	/**
	 * 返回用户绑定指定权限的组织架构集合查询hql;
	 * @param params
	 * @param userId
	 * @param pageId
	 * @param buttonId
	 * @return
	 */
	public static String authBindOrgHql(List<Parameter> params, String userId, String pageId, String buttonId){
		String hql =		" 	select	rgo.org.orgId " +
							" 	from		RUserRoleOrgGroup ruro, " +
							" 				RGroupOrg rgo, " +
							"				RRoleButton rrb " +
							" 	where		rgo.orgGroup.orgGroupId = ruro.orgGroup.orgGroupId " +
							"			and	ruro.role.id = rrb.role.id " +
							" 			and ruro.user.userId = :userId " +
							" 			and rrb.page.pageId = :pageId " +
							" 			and rrb.button.buttonId = :buttonId ";
		authAddParams(params, userId, pageId, buttonId);
		return hql;
	}
	
	private static void authAddParams(List<Parameter> params, String userId, String pageId, String buttonId){
		
		Parameter u = new Parameter("userId", userId, Operator.EQ);
		Parameter p = new Parameter("pageId", pageId, Operator.EQ);
		Parameter b = new Parameter("buttonId", buttonId, Operator.EQ);

		Set<String> set = new HashSet<String>(); 
		for (Parameter param : params) {
			set.add(param.name);
		}
		if(!set.contains("userId")) 	params.add(u);
		if(!set.contains("pageId")) 	params.add(p);
		if(!set.contains("buttonId")) 	params.add(b);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(isAdmin(6l));
		String aa = "$$".replace("$", "\\$");
		System.out.println(aa);
		
		String s = "ddfd$$ddd";
		String[] s1 = s.split(aa);
		System.out.println();
		System.out.println(String.format("%s      %s", s1[0], s1[1]));

	}

}
