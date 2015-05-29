package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.pojos.authority.Module;

/**
 * 
 * Ȩ�޽ӿ�
 * @���ߣ�YuTao  
 * @����ʱ�䣺2012-11-13 ����4:09:40
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵���� 
 * @version V1.0
 */
public interface IAuthorizeService {
	
	/**
	 * 
	 * �ж�һ���û�����ض�ҳ���Ƿ��н��루�鿴��Ȩ��
	 * @param userID
	 * @param pageID
	 * @return  Boolean     
	 */
	public Boolean isAuthorize(String userID,String pageID);	  //yyh Long ��ΪString
	
	/**
	 * ���ָ���û���ָ��ҳ���Ƿ�ӵ���ض��İ�ťȨ��
	 * @param userID
	 * @param pageID
	 * @param buttonID     
	 * @return  Boolean     
	 */
	public Boolean isAuthorize(String userID,String pageID,String buttonID);
	
	
	/**
	 * �ж��ض��û����ض�ҳ���ϣ��ض��ڵ����ĳ����ť�Ƿ���Ȩ��
	 * @param userID
	 * @param pageID
	 * @param buttonID
	 * @param nodeID
	 * @return  Boolean     
	 */	
	public Boolean isAuthorize(String userID,String pageID,String buttonID,String nodeID);
	
	
	/**
	 * ��������˵�� :����ָ�����û���ҳ�棬��ȡ������Ȩ�޵İ�ť����
	 * @param userID
	 * @param pageID
	 * @return  List<String>     
	 */
	public List<String> getButtonsFunctionList(String userID,String pageID);
	
	/**
	 * �ж��û��Ƿ��ǳ�������Ա
	 * @param userID    
	 * @return  Boolean     
	 */
	public Boolean isSuperUser(String userID);
	
	/**
	 * ��ȡ�û������ض�ҳ��������Ȩ�޵���֯�ܹ��������ķ�ʽ���� 
	 * @param userID
	 * @param pageID
	 * @return  List<TreeNode>     
	 */	
	public List<TreeNode> getAuthorizedNodeTree(String userID,String pageID);
	
	/**
	 * ��������ض�ҳ�����ض����ܣ���ǰ�û�ӵ��Ȩ�޵���֯�ṹ�ڵ㼯��
	 * @param userID
	 * @param pageID
	 * @param buttonID
	 * @return  List<String>     
	 */		
	public List<String> getAuthorizedNodeList(String userID,String pageID,String buttonID);
	
	/**
	 * ��ȡĳ���ڵ��µ�����ֱ���ӽڵ�  
	 * @param userID
	 * @param pageID
	 * @param nodeID
	 * @return  List<TreeNode>     
	 */
	public List<TreeNode> getAuthorizedChildNodes(String userID,String pageID,String nodeID);
	

	/**
	 * ��ȡ�����˵���Ϣ	
	 * @param userID �û�ID
	 * @param appID  ��ϵͳID
	 * @return
	 */
	public List<Module> getMenu(String userID,String appId,String language);
	
	/**
	 * ��ȡĳ���ڵ��ȫ·��	
	 * @param nodeId �ڵ�ID
	 * @return String
	 */	
	public String getNodeLabel(String nodeId);
	
	
	/**
	 * �жϵ�ǰ�ڵ��ȫ·�� 
	 * @param nodeId �ڵ�ID
	 * @return
	 */
	public String getNodeLablePath(String nodeId);
	
	/**
	 * �û���¼
	 * @param username
	 * @param userpwd
	 * @return Map<String, Object>
	 */
	public Map<String,Object> userLogin(String username,String userpwd);
	
	/**
	 * ��ȡ�˵�
	 * @param userId
	 * @param appId
	 * @return
	 */
	public List<Map<String,Object>> getMenu(HttpSession session,String userId, String appId);
	
}
