package smtcl.mocs.services.device;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.primefaces.model.TreeNode;

import smtcl.mocs.pojos.device.TNodes;

import javax.servlet.http.HttpSession;


/**
 * ��֯�ṹ���ӿ���
 * @version V1.0
 * @����ʱ�� 2012-12-03
 * @���� liguoqiang
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 */
public interface IOrganizationService extends IGenericService<TNodes, String> {
	/**
	 * ���ص�ǰ�û���ָ��ҳ�����֯�ṹ��
	 * @param userid �û�id
	 * @param pageid ҳ��id
	 * @return TreeNode
	 */
	public TreeNode returnTree(String userid,String pageid);
	
	/**
	 * ���ݽڵ����ַ��ظýڵ��µ����нڵ�id�������ӽڵ��id
	 * ����װ�� '1','2','3' ������ʽ
	 * @param currentNode ��ǰ�ڵ�
	 * @return String
	 */
	public String getAllTNodesId(smtcl.mocs.beans.authority.cache.TreeNode currentNode);
	/**
	 * ���ݽڵ����ַ��ظýڵ��µ����нڵ�id�������ӽڵ��id
	 * ����װ�� '1','2','3' ������ʽ
	 * @param nodeName �ڵ�
	 * @return
	 */
	public String getAllTNodesId(TNodes tnodes);
	/**
	 * ���ݵ�ǰ�ڵ�id���������и��ڵ������
	 * @param nodeId �ڵ�id
	 * @return String
	 */
	public String getAllParentName(String nodeId);
	
	/**
	 * ����û���ǰѡ��Ľڵ��Ӧ������ҳ��Ȩ��
	 * @param userId �û�id
	 * @param pageId ҳ��id
	 * @param buttonId ��ťid
	 * @param nodeId �ڵ�id
	 * @return boolean
	 */
	public boolean checkedClickNodeAuthority(String userId ,String pageId,String buttonId,String nodeId);

	/**
	 * ��ȡ�ڵ��µ������ӽڵ�
	 * @param nodeId �ڵ�id
	 * @param userId �û�id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getAllNodesByParentNodeId(HttpSession session,String nodeId,String userId);

	/**
	 * ����nodeclass��ȡ�ڵ�
	 * @param nodeClass �ڵ�nodeClass
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> get_All_Node_By_nodeClass(Collection<Parameter> parameters);

	
	
	/**
	 * �����û�id ҳ��id ���ؽڵ㼯��
	 * @param userid  �û�id       
	 * @param pageid  ҳ��id      
	 * @return TreeNode
	 */
	public List<TNodes> returnWorkshop(String userid, String pageid);
	
	/**
	 * ���ݽڵ�id ��ȡ�ڵ����
	 * @param Id
	 * @return
	 */
	public List<TNodes> getTNodesById(String Id);


}
