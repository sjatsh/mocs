/**
 * com.swg.authority.secure.service IUserService.java
 */
package smtcl.mocs.services.authority;

import java.util.List;

import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.pojos.authority.User;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

/**
 * @author gaokun
 * @create Sep 28, 2012 4:34:40 PM
 */
public interface IUserService extends IGenericService<User, String>{
		
	/**
	 * 1. 保存用户信息
	 * 2. 保存用户、角色和组织架构信息
	 * @param user
	 * @param userRoleOrgGroup
	 * @return
	 */
	void saveOrUpdateUserRoleGroup(User user, List<RUserRoleOrgGroup> userRoleOrgGroup);
	
	/***
	 * 返回USER 数据源
	 * @param userId 		登录用户
	 * @param pageId		页面信息
	 * @param buttonId		按钮信息
	 * @param pageNo		当前页码	
	 * @param pageSize		页码总数
	 * @param params		参数
	 * @return
	 */
	IDataCollection<User> queryAuthUserList(String userId, String pageId, String buttonId, int pageNo, int pageSize, List<Parameter> params);
    
    /***
     * 删除用户
     * @param userIds
     */
    void deleteUserList(String[] userIds);
    
    /***
     * 注销用户
     * @param userIds
     */
    void cancelUserList(String[] userIds);
    /**
	 * 获取所有用户列表
	 * @return
	 */
	public List<User> getUserbyAll();
    
	
}