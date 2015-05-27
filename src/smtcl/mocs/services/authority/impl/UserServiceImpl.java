package smtcl.mocs.services.authority.impl;

import java.util.ArrayList;
import java.util.List;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.services.authority.IUserService;
import smtcl.mocs.utils.authority.AuthorityUtil;

/**
 * 用户接口的实现类
 * 1. 更新用户的信
 * 2. 更新用户、功能角色 数据角色三的关
 * @author jun
 * 
 */
public class UserServiceImpl extends GenericServiceSpringImpl<User, String> implements IUserService {

	
	/***
	 * queryAuthUserList 查询 有权限查看的用户信息
	 * 1. 参数 userId     单个用户信息
	 * 2. 参数 parameters 多个查询条件信息 
	 * 3. 返回 用户信息
	 */
    @Override
    public IDataCollection<User> queryAuthUserList(String userId, String pageId, String buttonId, int pageNo, int pageSize, List<Parameter> params1) {
        String mark = " and @auth@ ";

        String hql = " from User u where @exadmin@ " + mark;
        hql = AuthorityUtil.excludeAdminHQL(hql, "u.loginName", new String[]{User.ADMIN});
        List<Parameter> params = new ArrayList<Parameter>();
        
        if (params1.size()!=0){
        	for(Parameter p : params1){
        		hql += " and " + p;        		
                if (p.operator == Operator.IS_NULL || p.operator == Operator.IS_NOT_NULL){}
                else params.add(p);
        	}
        }
        hql = AuthorityUtil.authHQL(hql, "u.orgId", params, userId, pageId, buttonId);
		return dao.executeQuery(pageNo, pageSize, hql, params);
    }

    
    /****
     * 依据单个查询条件或多个查询条
     * 返回有权限查看的用户信息
     */
    @Override
    public List<User> find(Parameter... parameters) {
        List<Parameter> params = new ArrayList<Parameter>();
        return dao.executeQuery(getHql(params, parameters), params);
    }

    /***
     * 返回有权限查看用户的SQL
     * @param params
     * @param parameters
     * @return
     */
    private String getHql(List<Parameter>params, Parameter... parameters) {
        String hql = " from User where 1=1 ";
        for (Parameter p : parameters) {
            if ("groupId".equals(p.name)) {
                hql += " and userId in (select b.user.userId from RUserOrgGroup b where b.orgGroup.id=:orgGroupId)";
                params.add(new Parameter("orgGroupId", p.value, Operator.EQ));
            } else {
                hql += " and " + p;
                if (p.operator == Operator.IS_NULL || p.operator == Operator.IS_NOT_NULL){}
                else params.add(p);
            }
        }
        hql += " order by loginName asc";
        return hql;
    }

    /**
     * IDataCollection
     * 1. pageNo 当前页码
     * 2. pageSize 页
     * 3. parameters 单个查询条件或多个查询条件
     * 
     * 返回 分页的User信息结果 
     */
    @Override
    public IDataCollection<User> find(int pageNo, int pageSize, Parameter... parameters) {
        List<Parameter> params = new ArrayList<Parameter>();
        return dao.executeQuery(pageNo, pageSize, getHql(params, parameters), params);
    }


    /**
     * 1. 保存用户信息
     * 2. 保存用户、功能角色和数据角色之间的关
     * 参数
     * 1. user 保存的用户信
     * 2. userRoleOrgGroup 保存的用户功能角色和数据角色的关系
     */
	@Override
	public void saveOrUpdateUserRoleGroup(User user, List<RUserRoleOrgGroup> userRoleOrgGroup) {		
		if (user.getUserId() == null) dao.save(user);
        else dao.update(user);
        Parameter param = new Parameter("userId", user.getUserId(), Operator.EQ);
        dao.executeUpdate("delete from RUserRoleOrgGroup where flag is null and user.userId = :userId", param);
        if (userRoleOrgGroup != null && userRoleOrgGroup.size() != 0)dao.save(RUserRoleOrgGroup.class, userRoleOrgGroup);
	}


	/***
	 * 删除用户
	 * 1. 删除用户、功能角色数据角色三关系
	 * 2. 删除用户?
	 */
	@Override
	public void deleteUserList(String[] userIds) {
		try{
			String hql = "delete from User where userId in (:userIds)";
			Parameter p = new Parameter("userIds",userIds, Operator.IN);
			dao.executeUpdate("delete from RUserRoleOrgGroup where flag is null and user.userId in (:userId)", p);
			dao.executeUpdate(hql, p);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	/****
	 * 注销用户
	 */
	@Override
	public void cancelUserList(String[] userIds) {
		try{
			String hql = "update User set status = 0 where userId in (:userIds)";
			Parameter p = new Parameter("userIds",userIds, Operator.EQ);
			dao.executeUpdate(hql, p);
			
			//解除三元关系
			String hql1 = " delete from RUserRoleOrgGroup ruro where ruro.flag is null and ruro.user.userId in (:userIds) ";
			dao.executeUpdate(hql1, p);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 获取所有用户列表
	 * @return
	 */
	public List<User> getUserbyAll(){
		String hql="from User";
		return dao.executeQuery(hql);
	}

}