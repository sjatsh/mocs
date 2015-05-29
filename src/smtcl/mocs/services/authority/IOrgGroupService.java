package smtcl.mocs.services.authority;

import smtcl.mocs.pojos.authority.OrgGroup;
import smtcl.mocs.pojos.authority.RGroupOrg;
import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.pojos.authority.TypeNode;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wangli
 * Date: 12-10-22
 * Time: 下午6:55
 */
public interface IOrgGroupService extends IGenericService<OrgGroup, String> {

    /**
     * 数据组删?
     * @param groupId 数据组编?
     */
    public List<OrgGroup> delete(String... groupId);

    /**
     * 完全删除
     * @param groupId 数据组编?
     */
    public void deleteAll(String... groupId);

    /**
     * 数据组及权限保存
     * @param group 数据?
     * @param relList 权限
     * @param gRelList 组织架构
     */
    public void saveOrUpdate(OrgGroup group, List<RUserRoleOrgGroup> relList, List<RGroupOrg> gRelList);

    public List<OrgGroup> queryOrgGroupList(String userId, String pageId, String buttonId, Parameter... parameters);

    public IDataCollection<OrgGroup> queryOrgGroupList(String userId, String pageId, String buttonId, int pageNo, int pageSize, Parameter... parameters);
    
    public List<TypeNode> queryTypeNodes();
    
    /**
     * 获取机床设备的类型列表
     */
    public List<TEquipmenttypeInfo> queryTEquipmenttypeInfo();
}
