package smtcl.mocs.services.authority;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.pojos.authority.*;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.IDataCollection;

/**
 * ��ɫ��ص�service
 *
 * User: gjy
 * Date: 2012-10-15
 */
public interface IRoleService extends IGenericService<Role, String> {
	public<T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters);
    public<T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters);
    /**
     * ���ݵ�ǰ�û���ȡȨ������
     * @param locale 
     * @param userId 
     */
    public List<TreeNode> getTreeNodes(String userId, Locale locale);
    /**
     * ���ݽ�ɫid��ȡ?�ѷ����Ȩ��;
     * @param pageNo
     * @param pageSize
     */
	public IDataCollection<RRolePage> queryPermissionByRoleId(int pageNo, int pageSize, String roleId);

    public List<RRolePage> queryPermissionByRoleId(String roleId);
	/**
     * ���ݽ�ɫid��ȡ�������û���
     */
	public IDataCollection<RUserRoleOrgGroup> queryUsersByRoleId(int pageNo,int pageSize,String roleId, String userId);
	/**
     * ���õ�ǰ��ɫ�µ��û�;
	 * @return 
     */
//	public boolean saveUsersByRoleId(Long roleId, String userId);
	/**
     * ���ݽ�ɫid���ð�ťȨ��;
     */
//	public boolean saveButtonsByRoleId(Long roleId, String buttonIds);
	/**
     * ���ݽ�ɫid����ҳ��Ȩ�޺Ͱ�ťȨ
     */
//	public boolean savePermissionByRoleId(Long roleId, String nodeId);
	/**
	 * ɾ����ǰ��ɫ�µİ�ť;
	 */
	public void deleteButtonsByRoleId(String roleId,String pageId);
	/**
	 * ɾ����ǰ��ɫ�µ��û�;
	 */
//	public void deleteUsersByRoleId(Long roleId);
	/**
     * ��ȡ��ɫ�б�
     */
    public IDataCollection<Role> queryRolesByUser(String userId, int pageNo, int pageSize, String pageId, String buttonId, Collection<Parameter> parameters);


    public void saveOrUpdate(Role role, List<RUserRoleOrgGroup> relList, List<RRoleButton> rbtnList, List<RRolePage> rpageList);

    /**
    * ���ݽ�ɫidɾ����ɫ��������
    * @param roleId
    * @return
    */
    public List<Role> delete(String... roleId);

    public List<Page> getPageInfo(String ...pageId);
}