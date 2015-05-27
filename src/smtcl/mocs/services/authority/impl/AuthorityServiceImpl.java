package smtcl.mocs.services.authority.impl;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.services.authority.IAuthorityService;
import smtcl.mocs.utils.authority.AuthorityUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: seth
 * Date: 12-11-28
 * Time: ����6:29
 */
public class AuthorityServiceImpl implements IAuthorityService {
    private static final String BUTTON_VIEW = "button.view";
    /**
     * �ж�һ���û�����ض�ҳ���Ƿ��н��루�鿴��Ȩ��
     *
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @return �Ƿ���Ȩ��
     */
    @Override
    public boolean hasPagePermission (String userId, String pageId) {
        return AuthorityUtil.judge (userId, pageId, BUTTON_VIEW);
    }

    /**
     * �ж��û�����ض�ҳ����ض���ť�Ƿ�ӵ��Ȩ��
     *
     * @param userId   �û�ID
     * @param pageId   ҳ��ID
     * @param buttonId ��ťID
     * @return �Ƿ���Ȩ��
     */
    @Override
    public boolean hasButtonPermission (String userId, String pageId, String buttonId) {
        return AuthorityUtil.judge(userId, pageId, buttonId);
    }

    /**
     * �ж��ض��û����ض�ҳ���ϣ��ض��ڵ����ĳ����ť�Ƿ���Ȩ��
     *
     * @param userId   �û�ID
     * @param pageId   ҳ��ID
     * @param buttonId ��ťID
     * @param nodeId   �ڵ�ID
     * @return �Ƿ���Ȩ��
     */
    @Override
    public boolean hasButtonPermissionWithNode (String userId, String pageId, String buttonId, String nodeId) {
        return AuthorityUtil.judge(userId, nodeId, pageId, buttonId);
    }

    /**
     * ��ȡ�û������ض�ҳ��������Ȩ�޵İ�ť�б�
     *
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @return ҳ����������Ȩ�޵İ�ť����
     */
    @Override
    public List<String> getAuthorizedButtons (String userId, String pageId) {
        return AuthorityUtil.findUserFunctionByPage (userId, pageId);
    }

    /**
     * �ж��û��Ƿ��ǳ�������Ա
     *
     * @param userId �û�ID
     * @return �Ƿ񳬼�����Ա
     */
    @Override
    public boolean isSuperUser (String userId) {
        return AuthorityUtil.isAdmin(userId);
    }

    /**
     * ��ȡ�û������ض�ҳ��������Ȩ�޵���֯�ܹ���
     * �����ķ�ʽ����
     *
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @return ��
     */
    @Override
    public List<TreeNode> getAuthorizedNodeTree (String userId, String pageId) {
        return AuthorityUtil.queryAuthOrgTree(userId, pageId, BUTTON_VIEW, false);
    }

    /**
     * ��������ض�ҳ�����ض����ܣ���ǰ�û�ӵ��Ȩ�޵���֯�ṹ�ڵ㼯��
     *
     * @param userId   �û�ID
     * @param pageId   ҳ��ID
     * @param buttonId ��ťID
     * @return �ڵ�
     */
    @Override
    public List<String> getAuthorizedNodeList (String userId, String pageId, String buttonId) {
        return AuthorityUtil.queryAuthNodes(userId, pageId, buttonId);
    }

    /**
     * ��ȡĳ���ڵ��µ�����ֱ���ӽڵ�
     *
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @param nodeId �ڵ�ID
     * @return �ֽڵ��б�
     */
    @Override
    public List<TreeNode> getAuthorizedChildNodes (String userId, String pageId, String nodeId) {
        return AuthorityUtil.queryAuthOrgTree(userId, pageId, BUTTON_VIEW, nodeId, false, null);
    }
}
