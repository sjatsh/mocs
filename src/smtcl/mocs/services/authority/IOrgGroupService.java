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
 * Time: ����6:55
 */
public interface IOrgGroupService extends IGenericService<OrgGroup, String> {

    /**
     * ������ɾ?
     * @param groupId �������?
     */
    public List<OrgGroup> delete(String... groupId);

    /**
     * ��ȫɾ��
     * @param groupId �������?
     */
    public void deleteAll(String... groupId);

    /**
     * �����鼰Ȩ�ޱ���
     * @param group ����?
     * @param relList Ȩ��
     * @param gRelList ��֯�ܹ�
     */
    public void saveOrUpdate(OrgGroup group, List<RUserRoleOrgGroup> relList, List<RGroupOrg> gRelList);

    public List<OrgGroup> queryOrgGroupList(String userId, String pageId, String buttonId, Parameter... parameters);

    public IDataCollection<OrgGroup> queryOrgGroupList(String userId, String pageId, String buttonId, int pageNo, int pageSize, Parameter... parameters);
    
    public List<TypeNode> queryTypeNodes();
    
    /**
     * ��ȡ�����豸�������б�
     */
    public List<TEquipmenttypeInfo> queryTEquipmenttypeInfo();
}
