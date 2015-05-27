package smtcl.mocs.services.authority;

import java.util.List;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.pojos.authority.Application;
import smtcl.mocs.pojos.authority.Module;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-5
 * Time: ����3:18
 */
public interface IMenuService extends IGenericService<Object, String> {
    /**
     * ��ȡ��ǰ�û�ӵ��Ȩ�޵�Ӧ��ϵͳ.
     * ���ص��ֵ��У�
     *     ��ֵΪ id ����Ӧ��ϵͳ�����ݿ��е�����
     *     ��ֵΪ label ����Ӧ��ϵͳ��ǰ��ϵ�е��ı�
     *     ��ֵΪ url ����Ӧ��ϵͳ��Ĭ��ҳ��URL
     * @param userId ��ǰ�û�ID
     * @param language ��ǰ���԰汾.  ��Ϊ<code>null</code>����ʱ��ʹ��ϵͳ��Ĭ�����԰汾
     * @return ������Ȩ�޵�Ӧ��ϵͳ���ڵ�ǰ����������.
     */
    List<Application> getApplications (String userId,String language);
    
    /**
     * ��ȡ��ǰ�û�ӵ��Ȩ�޵�ָ��ϵͳ��Ӧ��ϵͳ.
     * ���ص��ֵ��У�
     *     ��ֵΪ id ����Ӧ��ϵͳ�����ݿ��е�����
     *     ��ֵΪ label ����Ӧ��ϵͳ��ǰ��ϵ�е��ı�
     *     ��ֵΪ url ����Ӧ��ϵͳ��Ĭ��ҳ��URL
     * @param userId ��ǰ�û�ID
     * @param appId ϵͳID;
     * @param language ��ǰ���԰汾.  ��Ϊ<code>null</code>����ʱ��ʹ��ϵͳ��Ĭ�����԰汾
     * @return ������Ȩ�޵�Ӧ��ϵͳ���ڵ�ǰ����������.
     */
    public List<Application> getSpecApplications (String userId,String appId,String language);
    
    /**
     * ��ȡ��ǰ�û���ָ��Ӧ��ϵͳ����Ȩ�޵�ģ��.
     * ���ص��ֵ��У�
     *      ��ֵΪ id ����ģ�������ݿ��е�����
     *      ��ֵΪ label ����ģ�鵱ǰ��ϵ�е��ı�
     *      ��ֵΪ url ����ģ��Ĭ��ҳ��URL
     *      ��ֵΪ items ����ģ���а�����ҳ����󼯺ϣ�ÿ��Ԫ�ذ�����
     *          ��ֵΪ id ����ҳ�������ݿ��е�����
     *          ��ֵΪ label ����ҳ�浱ǰ��ϵ���ı�
     *          ��ֵΪ url ����ҳ��URL
     * @param userId ��ǰ�û�
     * @param applicationId ָ����Ӧ��ϵͳ
     * @param language ָ�������԰汾. ��Ϊ<code>null</code>����ʱ��ʹ��ϵͳ��Ĭ�����԰汾
     * @return ģ���б�
     */
    List<Module> getMenu (String userId, String applicationId, String language);

    /**
     * ��ȡĳ���ڵ��ȫ·�����磺�й�/����/�Ϻ�/���
     * @param nodeId �ڵ�ID
     * @return ��ǩ
     */
    String getNodeLabel (String nodeId);
}