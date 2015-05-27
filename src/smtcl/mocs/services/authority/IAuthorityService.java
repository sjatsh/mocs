package smtcl.mocs.services.authority;

import java.util.List;

import smtcl.mocs.beans.authority.cache.TreeNode;

public interface IAuthorityService {
    /**
     * �ж�һ���û�����ض�ҳ���Ƿ��н��루�鿴��Ȩ��
     *
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @return �Ƿ���Ȩ��
     */
    public boolean hasPagePermission (String userId, String pageId);

    /**
     * �ж��û�����ض�ҳ����ض���ť�Ƿ�ӵ��Ȩ��
     *
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @param buttonId ��ťID
     * @return �Ƿ���Ȩ��
     */
    public boolean hasButtonPermission (String userId, String pageId,String buttonId);

    /**
     * �ж��ض��û����ض�ҳ���ϣ��ض��ڵ����ĳ����ť�Ƿ���Ȩ��
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @param buttonId ��ťID
     * @param nodeId �ڵ�ID
     * @return �Ƿ���Ȩ��
     */
    public boolean hasButtonPermissionWithNode (String userId, String pageId,String buttonId,String nodeId
    );

    /**
     * ��ȡ�û������ض�ҳ��������Ȩ�޵İ�ť�б�
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @return ҳ����������Ȩ�޵İ�ť����
     */
    public List<String> getAuthorizedButtons (String userId, String pageId);

    /**
     * �ж��û��Ƿ��ǳ�������Ա
     * @param userId �û�ID
     * @return �Ƿ񳬼�����Ա
     */
    public boolean isSuperUser (String userId);

    /**
     * ��ȡ�û������ض�ҳ��������Ȩ�޵���֯�ܹ���
     * �����ķ�ʽ����
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @return ��
     */
    public List<TreeNode> getAuthorizedNodeTree (String userId,String pageId);

    /**
     * ��������ض�ҳ�����ض����ܣ���ǰ�û�ӵ��Ȩ�޵���֯�ṹ�ڵ㼯��
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @param buttonId ��ťID
     * @return �ڵ�
     */
    public List<String> getAuthorizedNodeList (String userId,String pageId,String buttonId);

    /**
     * ��ȡĳ���ڵ��µ�����ֱ���ӽڵ�
     * @param userId �û�ID
     * @param pageId ҳ��ID
     * @param nodeId �ڵ�ID
     * @return
     */
    public List<TreeNode> getAuthorizedChildNodes (String userId,String pageId,String nodeId);
}
