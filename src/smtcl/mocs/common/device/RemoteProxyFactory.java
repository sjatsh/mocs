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
 * CAPԶ�̽ӿڵ���
 * @���ߣ�YuTao
 * @����ʱ�䣺2012-11-14 ����11:03:48
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public class RemoteProxyFactory {

	private static RemoteProxyFactory capProxyFactory = null;

	/**
	 * ���캯��	  
	 * @return RemoteProxyFactory
	 */
	private static RemoteProxyFactory getRemoteProxyFactory() {
		if (capProxyFactory == null) {
			capProxyFactory = new RemoteProxyFactory();
		}
		return capProxyFactory;
	}

	/**
	 * ���� 
	 * @return RemoteProxyFactory
	 */

	public static RemoteProxyFactory getInstance() {
		return getRemoteProxyFactory();
	}

	/**
	 * �ж�һ���û�����ض�ҳ���Ƿ��н��루�鿴��Ȩ��
	 * @param userID �û�ID
	 * @param pageID ҳ��ID
	 * @return Boolean
	 */
	public Boolean isAuthorize(Long userID, String pageID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID.toString());
		map.put("pageId", pageID);
		// ���ò�����Զ�� Cap ������
		try {

			return proxy.execute(Boolean.class, "passport",
					"authority/hasPagePermission", map);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * �ж��û�����ض�ҳ����ض���ť�Ƿ�ӵ��Ȩ�� 	
	 * @param userID �û�ID
	 * @param pageID ҳ��ID
	 * @param buttonID ��ťID
	 * @return Boolean
	 */
	public Boolean isAuthorize(Long userID, String pageID, String buttonID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("pageId", pageID);
		map.put("buttonId", buttonID);
		// ���ò�����Զ�� Cap ������
		try {
			return proxy.execute(Boolean.class, "passport",
					"authority/hasButtonPermission", map);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * �ж��û�����ض�ҳ����ض���ť�Ƿ�ӵ��Ȩ�� 	
	 * @param userID �û�ID
	 * @param pageID ҳ��ID
	 * @param buttonID ��ťID
	 * @param nodeID �ڵ�ID
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
		// ���ò�����Զ�� Cap ������
		try {
			return proxy.execute(Boolean.class, "passport",
					"authority/hasButtonPermissionWithNode", map);
		} catch (Exception ex) {
			return false;
		}

	}

	/**
	 * ��ȡ�û������ض�ҳ��������Ȩ�޵İ�ť�б� 
	 * @param userID �û�ID
	 * @param pageID ҳ��ID
	 * @return List<String>
	 */
	public List<String> getButtonsFunctionList(Long userID, String pageID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.RMI);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		map.put("pageID", pageID);
		// ���ò�����Զ�� Cap ������
		try {
			// return (Object)proxy.execute( "passport",
			// "authority/getAuthorizeButtons", map);
			return null;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * �ж��û��Ƿ��ǳ�������Ա 	
	 * @param userID  �û�ID
	 * @return Boolean
	 */
	public Boolean isSuperUser(Long userID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		// ���ò�����Զ�� Cap ������
		try {
			// return (Object)proxy.execute( "passport",
			// "authority/isSuperUser", map);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * ��ȡ�û������ض�ҳ��������Ȩ�޵���֯�ܹ��������ķ�ʽ���� 
	 * @param userID  �û�ID
	 * @param pageID  ҳ��ID
	 * @return List<TNodes>
	 */
	public List<TreeNode> getAuthorizedNodeTree(Long userID, String pageID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("pageId", pageID);
		// ���ò�����Զ�� Cap ������
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
	 * ��������ض�ҳ�����ض����ܣ���ǰ�û�ӵ��Ȩ�޵���֯�ṹ�ڵ㼯��
	 * @param userID  �û�ID
	 * @param pageID  ҳ��ID
	 * @param buttonID  ��ťID
	 * @return List<Long>
	 */

	public List<Long> getAuthorizedNodeList(Long userID, String pageID,
			String buttonID) {
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.RMI);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		map.put("pageID", pageID);
		map.put("buttonID", buttonID);
		// ���ò�����Զ�� Cap ������
		try {
			// return (Object)proxy.execute( "passport",
			// "authority/getAuthorizedNodeList", map);
			return null;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * ��ȡĳ���ڵ��µ�����ֱ���ӽڵ� 	
	 * @param userID  �û�ID
	 * @param pageID  ҳ��ID
	 * @param nodeID  �ڵ�ID
	 * @return List<TreeNode>
	 */
	public List<TreeNode> getAuthorizedChildNodes(Long userID, String pageID,
			String nodeID) {
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("pageId", pageID);
		map.put("nodeId", nodeID);
		// ���ò�����Զ�� Cap ������
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
	 * �жϵ�ǰ�ڵ��ȫ·�� 
	 * @param nodeId �ڵ�ID
	 * @return
	 */
	public String getNodeLablePath(String nodeId) {
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeId", nodeId);
		// ���ò�����Զ�� Cap ������
		try {
			return proxy.execute(new TypeToken<String>() {
			}, "passport", "menu/getNodeLabel", map);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * ��ȡ�����˵���Ϣ	
	 * @param userID �û�ID
	 * @param appID  ��ϵͳID
	 * @return
	 */
	public List<Module> getMenu(String userID, String appID) {

		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userID);
		map.put("appId", appID);
		map.put("language", null);
		// ���ò�����Զ�� Cap ������
		try {
			return proxy.execute(
					new TypeToken<List<Module>>() {
					}, "passport", "menu/getMenu", map);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * ��ȡĳ���ڵ��ȫ·��	
	 * @param nodeId �ڵ�ID
	 * @return String
	 */	
	public String getNodeLabel(String nodeId)
	{
		ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeId", nodeId);
		// ���ò�����Զ�� Cap ������
		try {

			String result = proxy.execute(new TypeToken<String>() {}, "passport", "menu/getNodeLabel", map);
			return result;
		} catch (Exception ex) {
			return null;
		}
		
	}
}
