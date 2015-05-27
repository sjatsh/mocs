package smtcl.mocs.common.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.pojos.authority.Module;

import com.google.gson.reflect.TypeToken;
import com.swg.cap.runtime.configure.entry.ServiceType;
import com.swg.cap.runtime.stub.CapProxyFactory;
import com.swg.cap.runtime.stub.ICapProxy;

/**
 * 
 * CAP远程接口调用
 * @作者：YuTao
 * @创建时间：2012-11-14 上午11:03:48
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public class RemoteProxyFactory {

	private static RemoteProxyFactory capProxyFactory = null;

	/**
	 * 构造函数	  
	 * @return RemoteProxyFactory
	 */
	private static RemoteProxyFactory getRemoteProxyFactory() {
		if (capProxyFactory == null) {
			capProxyFactory = new RemoteProxyFactory();
		}
		return capProxyFactory;
	}

	/**
	 * 单例 
	 * @return RemoteProxyFactory
	 */

	public static RemoteProxyFactory getInstance() {
		return getRemoteProxyFactory();
	}

	/**
	 * 判断一个用户针对特定页面是否有进入（查看）权限
	 * @param userID 用户ID
	 * @param pageID 页面ID
	 * @return Boolean
	 */
	public Boolean isAuthorize(Long userID, String pageID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID.toString());
		map.put("pageId", pageID);
		// 调用并返回远程 Cap 服务结果
		try {

			return proxy.execute(Boolean.class, "passport",
					"authority/hasPagePermission", map);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 判断用户针对特定页面的特定按钮是否拥有权限 	
	 * @param userID 用户ID
	 * @param pageID 页面ID
	 * @param buttonID 按钮ID
	 * @return Boolean
	 */
	public Boolean isAuthorize(Long userID, String pageID, String buttonID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("pageId", pageID);
		map.put("buttonId", buttonID);
		// 调用并返回远程 Cap 服务结果
		try {
			return proxy.execute(Boolean.class, "passport",
					"authority/hasButtonPermission", map);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 判断用户针对特定页面的特定按钮是否拥有权限 	
	 * @param userID 用户ID
	 * @param pageID 页面ID
	 * @param buttonID 按钮ID
	 * @param nodeID 节点ID
	 * @return Boolean
	 */
	public Boolean isAuthorize(Long userID, String pageID, String buttonID,
			String nodeID) {
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("pageId", pageID);
		map.put("buttonId", buttonID);
		map.put("nodeId", nodeID);
		// 调用并返回远程 Cap 服务结果
		try {
			return proxy.execute(Boolean.class, "passport",
					"authority/hasButtonPermissionWithNode", map);
		} catch (Exception ex) {
			return false;
		}

	}

	/**
	 * 获取用户对于特定页面所有有权限的按钮列表 
	 * @param userID 用户ID
	 * @param pageID 页面ID
	 * @return List<String>
	 */
	public List<String> getButtonsFunctionList(Long userID, String pageID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.RMI);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		map.put("pageID", pageID);
		// 调用并返回远程 Cap 服务结果
		try {
			// return (Object)proxy.execute( "passport",
			// "authority/getAuthorizeButtons", map);
			return null;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 判断用户是否是超级管理员 	
	 * @param userID  用户ID
	 * @return Boolean
	 */
	public Boolean isSuperUser(Long userID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		// 调用并返回远程 Cap 服务结果
		try {
			// return (Object)proxy.execute( "passport",
			// "authority/isSuperUser", map);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 获取用户对于特定页面所有有权限的组织架构，以树的方式返回 
	 * @param userID  用户ID
	 * @param pageID  页面ID
	 * @return List<TNodes>
	 */
	public List<TreeNode> getAuthorizedNodeTree(Long userID, String pageID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("pageId", pageID);
		// 调用并返回远程 Cap 服务结果
		try {

			List<TreeNode> result = proxy.execute(
					new TypeToken<List<TreeNode>>() {
					}, "passport", "authority/getAuthorizedNodeTree", map);

			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * 返回针对特定页面上特定功能，当前用户拥有权限的组织结构节点集合
	 * @param userID  用户ID
	 * @param pageID  页面ID
	 * @param buttonID  按钮ID
	 * @return List<Long>
	 */

	public List<Long> getAuthorizedNodeList(Long userID, String pageID,
			String buttonID) {
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.RMI);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		map.put("pageID", pageID);
		map.put("buttonID", buttonID);
		// 调用并返回远程 Cap 服务结果
		try {
			// return (Object)proxy.execute( "passport",
			// "authority/getAuthorizedNodeList", map);
			return null;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * 获取某个节点下的所有直接子节点 	
	 * @param userID  用户ID
	 * @param pageID  页面ID
	 * @param nodeID  节点ID
	 * @return List<TreeNode>
	 */
	public List<TreeNode> getAuthorizedChildNodes(Long userID, String pageID,
			String nodeID) {
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("pageId", pageID);
		map.put("nodeId", nodeID);
		// 调用并返回远程 Cap 服务结果
		try {
			List<TreeNode> result = proxy.execute(
					new TypeToken<List<TreeNode>>() {
					}, "passport", "authority/getAuthorizedChildNodes", map);
			return result;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 判断当前节点的全路径 
	 * @param nodeId 节点ID
	 * @return
	 */
	public String getNodeLablePath(String nodeId) {
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeId", nodeId);
		// 调用并返回远程 Cap 服务结果
		try {
			return proxy.execute(new TypeToken<String>() {
			}, "passport", "menu/getNodeLabel", map);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 获取导航菜单信息	
	 * @param userID 用户ID
	 * @param appID  子系统ID
	 * @return
	 */
	public List<Module> getMenu(String userID, String appID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("appId", appID);
		map.put("language", null);
		// 调用并返回远程 Cap 服务结果
		try {
			return proxy.execute(
					new TypeToken<List<Module>>() {
					}, "passport", "menu/getMenu", map);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 获取某个节点的全路径	
	 * @param nodeId 节点ID
	 * @return String
	 */	
	public String getNodeLabel(String nodeId)
	{
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeId", nodeId);
		// 调用并返回远程 Cap 服务结果
		try {

			String result = proxy.execute(new TypeToken<String>() {}, "passport", "menu/getNodeLabel", map);
			return result;
		} catch (Exception ex) {
			return null;
		}
		
	}
}
