package smtcl.mocs.services.authority.impl;

import java.util.ArrayList;
import java.util.List;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.authority.OrgGroup;
import smtcl.mocs.pojos.authority.RGroupOrg;
import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.pojos.authority.TypeNode;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.services.authority.IOrgGroupService;
import smtcl.mocs.utils.authority.AuthorityUtil;

/**
 * Created by IntelliJ IDEA.
 * User: wangli
 * Date: 12-10-22
 * Time: 下午6:56
 */
public class OrgGroupServiceImpl extends GenericServiceSpringImpl<OrgGroup, String> implements IOrgGroupService {

    private static final String DEL_ORG_GROUP = "delete from OrgGroup";
    private static final String DEL_R_USER_ROLE_ORG_GROUP = "delete from RUserRoleOrgGroup";
    private static final String DEL_R_GROUP_ORG = "delete from RGroupOrg";

    @Override
    public List<OrgGroup> find(Parameter... parameters) {
        List<Parameter> params = new ArrayList<Parameter>();
        return dao.executeQuery(getHql(params, parameters), params);
    }

    private String getHql(List<Parameter>params, Parameter... parameters) {
        String hql = " from OrgGroup where 1=1 ";

        for (Parameter p : parameters) {
            if ("userId".equals(p.name)) {
                hql += " and orgGroupId in (select r.orgGroup.orgGroupId from RUserRoleOrgGroup r where r.user.userId = :userId)";
                params.add(new Parameter("userId", p.value, Operator.EQ));
            } else {
                hql += " and " + p;
                if (p.operator == Operator.IS_NULL || p.operator == Operator.IS_NOT_NULL){}
                else params.add(p);
            }
        }
        return hql;
    }

    @Override
    public IDataCollection<OrgGroup> find(int pageNo, int pageSize, Parameter... parameters) {
        List<Parameter> params = new ArrayList<Parameter>();
        return dao.executeQuery(pageNo, pageSize, getHql(params, parameters), params);
    }

    /**
     * 完全删除
     * @param groupId 数据组编?
     */
    public void deleteAll(String... groupId) {
        Parameter param = new Parameter("groupId", groupId, Operator.IN);
        String hql1 = DEL_R_USER_ROLE_ORG_GROUP + " where orgGroup.orgGroupId in (:groupId)";
        String hql2 = DEL_R_GROUP_ORG + " where orgGroup.orgGroupId in(:groupId)";
        String hql3 = DEL_ORG_GROUP + " where orgGroupId in(:groupId)";

        dao.executeUpdate(hql1, param);
        dao.executeUpdate(hql2, param);
        dao.executeUpdate(hql3, param);
    }

    /**
     * 完全删除
     * @param groupId 数据组编?
     */
    public List<OrgGroup> delete(String... groupId) {
        Parameter param = new Parameter("groupId", groupId, Operator.IN);
        String hql1 = "(select orgGroup.orgGroupId from RUserRoleOrgGroup where orgGroup.orgGroupId in(:groupId))";
        String queryHql = " from OrgGroup og where og.orgGroupId in "+hql1;
        List<OrgGroup> list = dao.executeQuery(queryHql, param);
//        String hql1 = DEL_R_USER_ROLE_ORG_GROUP + " where orgGroup.orgGroupId in (:groupId)";
        String hql2 = DEL_R_GROUP_ORG + " where orgGroup.orgGroupId in(:groupId) and orgGroup.orgGroupId not in"+hql1;
        String hql3 = DEL_ORG_GROUP + " where orgGroupId in(:groupId) and orgGroupId not in"+hql1;

//        dao.executeUpdate(hql1, param);
        dao.executeUpdate(hql2, param);
        dao.executeUpdate(hql3, param);
        return list;
    }

    public void saveOrUpdate(OrgGroup group, List<RUserRoleOrgGroup> relList, List<RGroupOrg> gRelList) {
        if (group.getOrgGroupId() == null) dao.save(group);
        else dao.update(group);
        Parameter param = new Parameter("groupId", group.getOrgGroupId(), Operator.EQ);
        dao.executeUpdate(DEL_R_USER_ROLE_ORG_GROUP + " where flag is null and orgGroup.orgGroupId = :groupId", param);
        dao.executeUpdate(DEL_R_GROUP_ORG + " where flag is null and orgGroup.orgGroupId = :groupId", param);
        if (relList != null && relList.size() != 0)dao.save(RUserRoleOrgGroup.class, relList);
        if (gRelList != null && gRelList.size() != 0) dao.save(RGroupOrg.class, gRelList);
    }

    @Override
    public List<OrgGroup> queryOrgGroupList(String userId, String pageId, String buttonId, Parameter... parameters) {
    	List<Parameter> ps = new ArrayList<Parameter>();
    	for (Parameter p : parameters) {
			ps.add(p);
		}
        return AuthorityUtil.queryAuthOrgGroupList(userId, pageId, buttonId, -1, -1, null, ps, null).getData();
    }

    @Override
    public IDataCollection<OrgGroup> queryOrgGroupList(String userId, String pageId, String buttonId, int pageNo, int pageSize, Parameter... parameters) {
        /*String hql = "from OrgGroup where @auth@ ";//AuthorityUtil.queryAuthOrgGroupHql(userId);
        List<Parameter> params = new ArrayList<Parameter>();
        for (Parameter p : parameters) {
            if ("userId".equals(p.name)) {
                hql += " and orgGroupId in (select r.orgGroup.orgGroupId from RUserRoleOrgGroup r where r.user.userId = :ruserId)";
                params.add(new Parameter("ruserId", p.value, Operator.EQ));
            } else {
                hql += " and " + p;
                if (p.operator == Operator.IS_NULL || p.operator == Operator.IS_NOT_NULL){}
                else params.add(p);
            }
        }
        hql = AuthorityUtil.authHQL(hql, "createUser.orgId", params, userId, pageId, buttonId);
//        hql += " order by loginName asc";
        return dao.executeQuery(pageNo, pageSize, hql, params);*/
    	List<Parameter> ps = new ArrayList<Parameter>();
    	for (Parameter p : parameters) {
			ps.add(p);
		}
        return AuthorityUtil.queryAuthOrgGroupList(userId, pageId, buttonId, pageNo, pageSize, null, ps, null);
    }
    
    public List<TypeNode> queryTypeNodes(){
    	String hql = " from TypeNode where show !=0 order by text";
    	return dao.executeQuery(hql, new Parameter[0]);
    }
    
    /**
     * 获取机床设备的类型列表
     */
    public List<TEquipmenttypeInfo> queryTEquipmenttypeInfo(){
    	String hql = "from TEquipmenttypeInfo";
    	return dao.executeQuery(hql, new Parameter[0]);
    } 
    
}
