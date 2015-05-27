package smtcl.mocs.services.authority;

import java.util.List;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.pojos.authority.Application;
import smtcl.mocs.pojos.authority.Module;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-5
 * Time: 下午3:18
 */
public interface IMenuService extends IGenericService<Object, String> {
    /**
     * 获取当前用户拥有权限的应用系统.
     * 返回的字典中，
     *     键值为 id 的是应用系统在数据库中的主键
     *     键值为 label 的是应用系统当前语系中的文本
     *     键值为 url 的是应用系统的默认页面URL
     * @param userId 当前用户ID
     * @param language 当前语言版本.  可为<code>null</code>，此时，使用系统的默认语言版本
     * @return 所有有权限的应用系统，在当前的区域设置.
     */
    List<Application> getApplications (String userId,String language);
    
    /**
     * 获取当前用户拥有权限的指定系统的应用系统.
     * 返回的字典中，
     *     键值为 id 的是应用系统在数据库中的主键
     *     键值为 label 的是应用系统当前语系中的文本
     *     键值为 url 的是应用系统的默认页面URL
     * @param userId 当前用户ID
     * @param appId 系统ID;
     * @param language 当前语言版本.  可为<code>null</code>，此时，使用系统的默认语言版本
     * @return 所有有权限的应用系统，在当前的区域设置.
     */
    public List<Application> getSpecApplications (String userId,String appId,String language);
    
    /**
     * 获取当前用户在指定应用系统内有权限的模块.
     * 返回的字典中，
     *      键值为 id 的是模块在数据库中的主键
     *      键值为 label 的是模块当前语系中的文本
     *      键值为 url 的是模块默认页面URL
     *      键值为 items 的是模块中包含的页面对象集合，每个元素包含：
     *          键值为 id 的是页面在数据库中的主键
     *          键值为 label 的是页面当前语系的文本
     *          键值为 url 的是页面URL
     * @param userId 当前用户
     * @param applicationId 指定的应用系统
     * @param language 指定的语言版本. 可为<code>null</code>，此时，使用系统的默认语言版本
     * @return 模块列表
     */
    List<Module> getMenu (String userId, String applicationId, String language);

    /**
     * 获取某个节点的全路径，如：中国/华东/上海/徐汇
     * @param nodeId 节点ID
     * @return 标签
     */
    String getNodeLabel (String nodeId);
}