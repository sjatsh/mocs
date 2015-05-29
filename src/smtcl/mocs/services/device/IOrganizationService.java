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
 * 组织结构树接口类
 * @version V1.0
 * @创建时间 2012-12-03
 * @作者 liguoqiang
 * @修改者
 * @修改日期
 * @修改说明
 */
public interface IOrganizationService extends IGenericService<TNodes, String> {
	/**
	 * 返回当前用户，指定页面的组织结构树
	 * @param userid 用户id
	 * @param pageid 页面id
	 * @return TreeNode
	 */
	public TreeNode returnTree(String userid,String pageid);
	
	/**
	 * 根据节点名字返回该节点下的所有节点id，包括子节点的id
	 * 并封装成 '1','2','3' 这种形式
	 * @param currentNode 当前节点
	 * @return String
	 */
	public String getAllTNodesId(smtcl.mocs.beans.authority.cache.TreeNode currentNode);
	/**
	 * 根据节点名字返回该节点下的所有节点id，包括子节点的id
	 * 并封装成 '1','2','3' 这种形式
	 * @param nodeName 节点
	 * @return
	 */
	public String getAllTNodesId(TNodes tnodes);
	/**
	 * 根据当前节点id，返回所有父节点的名字
	 * @param nodeId 节点id
	 * @return String
	 */
	public String getAllParentName(String nodeId);
	
	/**
	 * 检查用户当前选择的节点对应的其他页面权限
	 * @param userId 用户id
	 * @param pageId 页面id
	 * @param buttonId 按钮id
	 * @param nodeId 节点id
	 * @return boolean
	 */
	public boolean checkedClickNodeAuthority(String userId ,String pageId,String buttonId,String nodeId);

	/**
	 * 获取节点下的所有子节点
	 * @param nodeId 节点id
	 * @param userId 用户id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getAllNodesByParentNodeId(HttpSession session,String nodeId,String userId);

	/**
	 * 根据nodeclass获取节点
	 * @param nodeClass 节点nodeClass
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> get_All_Node_By_nodeClass(Collection<Parameter> parameters);

	
	
	/**
	 * 根据用户id 页面id 返回节点集合
	 * @param userid  用户id       
	 * @param pageid  页面id      
	 * @return TreeNode
	 */
	public List<TNodes> returnWorkshop(String userid, String pageid);
	
	/**
	 * 根据节点id 获取节点对象
	 * @param Id
	 * @return
	 */
	public List<TNodes> getTNodesById(String Id);


}
