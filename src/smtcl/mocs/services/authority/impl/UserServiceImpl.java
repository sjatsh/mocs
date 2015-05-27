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
 * �û��ӿڵ�ʵ����
 * 1. �����û�����
 * 2. �����û������ܽ�ɫ ���ݽ�ɫ���Ĺ�
 * @author jun
 * 
 */
public class UserServiceImpl extends GenericServiceSpringImpl<User, String> implements IUserService {

	
	/***
	 * queryAuthUserList ��ѯ ��Ȩ�޲鿴���û���Ϣ
	 * 1. ���� userId     �����û���Ϣ
	 * 2. ���� parameters �����ѯ������Ϣ 
	 * 3. ���� �û���Ϣ
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
     * ���ݵ�����ѯ����������ѯ��
     * ������Ȩ�޲鿴���û���Ϣ
     */
    @Override
    public List<User> find(Parameter... parameters) {
        List<Parameter> params = new ArrayList<Parameter>();
        return dao.executeQuery(getHql(params, parameters), params);
    }

    /***
     * ������Ȩ�޲鿴�û���SQL
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
     * 1. pageNo ��ǰҳ��
     * 2. pageSize ҳ
     * 3. parameters ������ѯ����������ѯ����
     * 
     * ���� ��ҳ��User��Ϣ��� 
     */
    @Override
    public IDataCollection<User> find(int pageNo, int pageSize, Parameter... parameters) {
        List<Parameter> params = new ArrayList<Parameter>();
        return dao.executeQuery(pageNo, pageSize, getHql(params, parameters), params);
    }


    /**
     * 1. �����û���Ϣ
     * 2. �����û������ܽ�ɫ�����ݽ�ɫ֮��Ĺ�
     * ����
     * 1. user ������û���
     * 2. userRoleOrgGroup ������û����ܽ�ɫ�����ݽ�ɫ�Ĺ�ϵ
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
	 * ɾ���û�
	 * 1. ɾ���û������ܽ�ɫ���ݽ�ɫ����ϵ
	 * 2. ɾ���û�?
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
	 * ע���û�
	 */
	@Override
	public void cancelUserList(String[] userIds) {
		try{
			String hql = "update User set status = 0 where userId in (:userIds)";
			Parameter p = new Parameter("userIds",userIds, Operator.EQ);
			dao.executeUpdate(hql, p);
			
			//�����Ԫ��ϵ
			String hql1 = " delete from RUserRoleOrgGroup ruro where ruro.flag is null and ruro.user.userId in (:userIds) ";
			dao.executeUpdate(hql1, p);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * ��ȡ�����û��б�
	 * @return
	 */
	public List<User> getUserbyAll(){
		String hql="from User";
		return dao.executeQuery(hql);
	}

}