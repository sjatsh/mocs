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
 * 角色相关的service
 *
 * User: gjy
 * Date: 2012-10-15
 */
public interface IRoleService extends IGenericService<Role, String> {
	public<T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters);
    public<T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters);
    /**
     * 根据当前用户获取权限树节
     * @param locale 
     * @param userId 
     */
    public List<TreeNode> getTreeNodes(String userId, Locale locale);
    /**
     * 根据角色id获取?已分配的权限;
     * @param pageNo
     * @param pageSize
     */
	public IDataCollection<RRolePage> queryPermissionByRoleId(int pageNo, int pageSize, String roleId);

    public List<RRolePage> queryPermissionByRoleId(String roleId);
	/**
     * 根据角色id获取已设置用户列
     */
	public IDataCollection<RUserRoleOrgGroup> queryUsersByRoleId(int pageNo,int pageSize,String roleId, String userId);
	/**
     * 设置当前角色下的用户;
	 * @return 
     */
//	public boolean saveUsersByRoleId(Long roleId, String userId);
	/**
     * 根据角色id设置按钮权限;
     */
//	public boolean saveButtonsByRoleId(Long roleId, String buttonIds);
	/**
     * 根据角色id设置页面权限和按钮权
     */
//	public boolean savePermissionByRoleId(Long roleId, String nodeId);
	/**
	 * 删除当前角色下的按钮;
	 */
	public void deleteButtonsByRoleId(String roleId,String pageId);
	/**
	 * 删除当前角色下的用户;
	 */
//	public void deleteUsersByRoleId(Long roleId);
	/**
     * 获取角色列表
     */
    public IDataCollection<Role> queryRolesByUser(String userId, int pageNo, int pageSize, String pageId, String buttonId, Collection<Parameter> parameters);


    public void saveOrUpdate(Role role, List<RUserRoleOrgGroup> relList, List<RRoleButton> rbtnList, List<RRolePage> rpageList);

    /**
    * 根据角色id删除角色及关联数
    * @param roleId
    * @return
    */
    public List<Role> delete(String... roleId);

    public List<Page> getPageInfo(String ...pageId);
}