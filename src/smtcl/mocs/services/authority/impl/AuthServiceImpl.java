package smtcl.mocs.services.authority.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.services.authority.IAuthService;


/**
 * 
 * @author gaokun
 * @create Nov 14, 2012 5:06:03 PM
 */
public class AuthServiceImpl extends GenericServiceSpringImpl<Object, String> implements IAuthService {

	public List<Map> queryBindAuth(String condition, String type){
		
		String hql = 	" select new Map( 	ruro.user.userId as u_id, " +
						"					ruro.user.loginName as u_l, " +
						"					ruro.user.nickName as u_n, " +
						"					ruro.orgGroup.orgGroupId as g_id, " +
						"					ruro.orgGroup.groupName as g_name, " +
						"					ruro.role.id as r_id, " +
						"					ruro.role.name as r_name ) " +
						" from RUserRoleOrgGroup ruro where ruro.flag is null ";
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("condition", condition, Operator.EQ));
		if("r".equals(type)){//角色;
			hql += " and ruro.role.id = :condition";
			hql += " order by ruro.user.loginName, ruro.orgGroup.groupName ";
		}else if("g".equals(type)){//数据?
			hql += " and ruro.orgGroup.orgGroupId = :condition";
			hql += " order by ruro.user.loginName, ruro.role.name ";
		}else{//用户
			hql += " and ruro.user.userId = :condition";
			hql += " order by ruro.role.name, ruro.orgGroup.groupName ";
		}
		
		List<Map> list_bind = this.getDao().executeQuery(hql, params);
		for (Map map : list_bind) {
			String u_l	=	(String)map.get("u_l");
			String u_n	=	(String)map.get("u_n");
			String tmp	=	StringUtils.isBlank(u_n) ? "" : "(" + u_n + ")";
			String name	=	u_l + tmp;
			map.put("u_name", name);
		}
		
		return list_bind;
	}
}
