package smtcl.mocs.services.authority;

import java.util.List;

import smtcl.mocs.beans.authority.cache.TreeNode;

public interface IAuthorityService {
    /**
     * 判断一个用户针对特定页面是否有进入（查看）权限
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return 是否有权限
     */
    public boolean hasPagePermission (String userId, String pageId);

    /**
     * 判断用户针对特定页面的特定按钮是否拥有权限
     *
     * @param userId 用户ID
     * @param pageId 页面ID
     * @param buttonId 按钮ID
     * @return 是否有权限
     */
    public boolean hasButtonPermission (String userId, String pageId,String buttonId);

    /**
     * 判断特定用户在特定页面上，特定节点针对某个按钮是否有权限
     * @param userId 用户ID
     * @param pageId 页面ID
     * @param buttonId 按钮ID
     * @param nodeId 节点ID
     * @return 是否有权限
     */
    public boolean hasButtonPermissionWithNode (String userId, String pageId,String buttonId,String nodeId
    );

    /**
     * 获取用户对于特定页面所有有权限的按钮列表
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return 页面上所有有权限的按钮主键
     */
    public List<String> getAuthorizedButtons (String userId, String pageId);

    /**
     * 判断用户是否是超级管理员
     * @param userId 用户ID
     * @return 是否超级管理员
     */
    public boolean isSuperUser (String userId);

    /**
     * 获取用户对于特定页面所有有权限的组织架构。
     * 以树的方式返回
     * @param userId 用户ID
     * @param pageId 页面ID
     * @return 树
     */
    public List<TreeNode> getAuthorizedNodeTree (String userId,String pageId);

    /**
     * 返回针对特定页面上特定功能，当前用户拥有权限的组织结构节点集合
     * @param userId 用户ID
     * @param pageId 页面ID
     * @param buttonId 按钮ID
     * @return 节点
     */
    public List<String> getAuthorizedNodeList (String userId,String pageId,String buttonId);

    /**
     * 获取某个节点下的所有直接子节点
     * @param userId 用户ID
     * @param pageId 页面ID
     * @param nodeId 节点ID
     * @return
     */
    public List<TreeNode> getAuthorizedChildNodes (String userId,String pageId,String nodeId);
}
