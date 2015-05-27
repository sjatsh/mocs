package smtcl.mocs.services.authority.impl;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.services.authority.IAuthorityService;
import smtcl.mocs.utils.authority.AuthorityUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: seth
 * Date: 12-11-28
 * Time: 下午6:29
 */
public class AuthorityServiceImpl implements IAuthorityService {
    private static final String BUTTON_VIEW = "button.view";
    /**
     * 判断一个用户针对特定页面是否有进入（查看）权限
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return 是否有权限
     */
    @Override
    public boolean hasPagePermission (String userId, String pageId) {
        return AuthorityUtil.judge (userId, pageId, BUTTON_VIEW);
    }

    /**
     * 判断用户针对特定页面的特定按钮是否拥有权限
     *
     * @param userId   用户ID
     * @param pageId   页面ID
     * @param buttonId 按钮ID
     * @return 是否有权限
     */
    @Override
    public boolean hasButtonPermission (String userId, String pageId, String buttonId) {
        return AuthorityUtil.judge(userId, pageId, buttonId);
    }

    /**
     * 判断特定用户在特定页面上，特定节点针对某个按钮是否有权限
     *
     * @param userId   用户ID
     * @param pageId   页面ID
     * @param buttonId 按钮ID
     * @param nodeId   节点ID
     * @return 是否有权限
     */
    @Override
    public boolean hasButtonPermissionWithNode (String userId, String pageId, String buttonId, String nodeId) {
        return AuthorityUtil.judge(userId, nodeId, pageId, buttonId);
    }

    /**
     * 获取用户对于特定页面所有有权限的按钮列表
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return 页面上所有有权限的按钮主键
     */
    @Override
    public List<String> getAuthorizedButtons (String userId, String pageId) {
        return AuthorityUtil.findUserFunctionByPage (userId, pageId);
    }

    /**
     * 判断用户是否是超级管理员
     *
     * @param userId 用户ID
     * @return 是否超级管理员
     */
    @Override
    public boolean isSuperUser (String userId) {
        return AuthorityUtil.isAdmin(userId);
    }

    /**
     * 获取用户对于特定页面所有有权限的组织架构。
     * 以树的方式返回
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return 树
     */
    @Override
    public List<TreeNode> getAuthorizedNodeTree (String userId, String pageId) {
        return AuthorityUtil.queryAuthOrgTree(userId, pageId, BUTTON_VIEW, false);
    }

    /**
     * 返回针对特定页面上特定功能，当前用户拥有权限的组织结构节点集合
     *
     * @param userId   用户ID
     * @param pageId   页面ID
     * @param buttonId 按钮ID
     * @return 节点
     */
    @Override
    public List<String> getAuthorizedNodeList (String userId, String pageId, String buttonId) {
        return AuthorityUtil.queryAuthNodes(userId, pageId, buttonId);
    }

    /**
     * 获取某个节点下的所有直接子节点
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @param nodeId 节点ID
     * @return 字节点列表
     */
    @Override
    public List<TreeNode> getAuthorizedChildNodes (String userId, String pageId, String nodeId) {
        return AuthorityUtil.queryAuthOrgTree(userId, pageId, BUTTON_VIEW, nodeId, false, null);
    }
}
