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
	 * 1. �����û���Ϣ
	 * 2. �����û�����ɫ����֯�ܹ���Ϣ
	 * @param user
	 * @param userRoleOrgGroup
	 * @return
	 */
	void saveOrUpdateUserRoleGroup(User user, List<RUserRoleOrgGroup> userRoleOrgGroup);
	
	/***
	 * ����USER ����Դ
	 * @param userId 		��¼�û�
	 * @param pageId		ҳ����Ϣ
	 * @param buttonId		��ť��Ϣ
	 * @param pageNo		��ǰҳ��	
	 * @param pageSize		ҳ������
	 * @param params		����
	 * @return
	 */
	IDataCollection<User> queryAuthUserList(String userId, String pageId, String buttonId, int pageNo, int pageSize, List<Parameter> params);
    
    /***
     * ɾ���û�
     * @param userIds
     */
    void deleteUserList(String[] userIds);
    
    /***
     * ע���û�
     * @param userIds
     */
    void cancelUserList(String[] userIds);
    /**
	 * ��ȡ�����û��б�
	 * @return
	 */
	public List<User> getUserbyAll();
    
	
}