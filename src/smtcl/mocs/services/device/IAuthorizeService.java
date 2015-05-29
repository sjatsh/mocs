package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.pojos.authority.Module;

/**
 * 
 * 权限接口
 * @作者：YuTao  
 * @创建时间：2012-11-13 下午4:09:40
 * @修改者： 
 * @修改日期： 
 * @修改说明： 
 * @version V1.0
 */
public interface IAuthorizeService {
	
	/**
	 * 
	 * 判断一个用户针对特定页面是否有进入（查看）权限
	 * @param userID
	 * @param pageID
	 * @return  Boolean     
	 */
	public Boolean isAuthorize(String userID,String pageID);	  //yyh Long 改为String
	
	/**
	 * 检查指定用户在指定页面是否拥有特定的按钮权限
	 * @param userID
	 * @param pageID
	 * @param buttonID     
	 * @return  Boolean     
	 */
	public Boolean isAuthorize(String userID,String pageID,String buttonID);
	
	
	/**
	 * 判断特定用户在特定页面上，特定节点针对某个按钮是否有权限
	 * @param userID
	 * @param pageID
	 * @param buttonID
	 * @param nodeID
	 * @return  Boolean     
	 */	
	public Boolean isAuthorize(String userID,String pageID,String buttonID,String nodeID);
	
	
	/**
	 * 函数功能说明 :根据指定的用户，页面，获取所有有权限的按钮集合
	 * @param userID
	 * @param pageID
	 * @return  List<String>     
	 */
	public List<String> getButtonsFunctionList(String userID,String pageID);
	
	/**
	 * 判断用户是否是超级管理员
	 * @param userID    
	 * @return  Boolean     
	 */
	public Boolean isSuperUser(String userID);
	
	/**
	 * 获取用户对于特定页面所有有权限的组织架构。以树的方式返回 
	 * @param userID
	 * @param pageID
	 * @return  List<TreeNode>     
	 */	
	public List<TreeNode> getAuthorizedNodeTree(String userID,String pageID);
	
	/**
	 * 返回针对特定页面上特定功能，当前用户拥有权限的组织结构节点集合
	 * @param userID
	 * @param pageID
	 * @param buttonID
	 * @return  List<String>     
	 */		
	public List<String> getAuthorizedNodeList(String userID,String pageID,String buttonID);
	
	/**
	 * 获取某个节点下的所有直接子节点  
	 * @param userID
	 * @param pageID
	 * @param nodeID
	 * @return  List<TreeNode>     
	 */
	public List<TreeNode> getAuthorizedChildNodes(String userID,String pageID,String nodeID);
	

	/**
	 * 获取导航菜单信息	
	 * @param userID 用户ID
	 * @param appID  子系统ID
	 * @return
	 */
	public List<Module> getMenu(String userID,String appId,String language);
	
	/**
	 * 获取某个节点的全路径	
	 * @param nodeId 节点ID
	 * @return String
	 */	
	public String getNodeLabel(String nodeId);
	
	
	/**
	 * 判断当前节点的全路径 
	 * @param nodeId 节点ID
	 * @return
	 */
	public String getNodeLablePath(String nodeId);
	
	/**
	 * 用户登录
	 * @param username
	 * @param userpwd
	 * @return Map<String, Object>
	 */
	public Map<String,Object> userLogin(String username,String userpwd);
	
	/**
	 * 获取菜单
	 * @param userId
	 * @param appId
	 * @return
	 */
	public List<Map<String,Object>> getMenu(HttpSession session,String userId, String appId);
	
}
