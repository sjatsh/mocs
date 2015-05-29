package smtcl.mocs.services.device.impl;

import java.util.*;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.StringUtils;

import javax.servlet.http.HttpSession;


/**
 * 查询树节点
 * @version V1.0
 * @创建时间 2012-12-03
 * @作者 liguoqiang
 * @修改者
 * @修改日期
 * @修改说明
 */
public class OrganizationServiceImpl extends
		GenericServiceSpringImpl<TNodes, String> implements
		IOrganizationService {
	
	private IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory.getBean("authorizeService");

	/**
	 * 根据用户id 页面id 返回树
	 * @param userid  用户id       
	 * @param pageid  页面id      
	 * @return TreeNode
	 */
	public TreeNode returnTree(String userid, String pageid) {		
		TreeNode root = null;		
		List list = authorizeService.getAuthorizedNodeTree(userid, pageid);
		if (null != list && list.size() > 0) {
			smtcl.mocs.beans.authority.cache.TreeNode tnodes = (smtcl.mocs.beans.authority.cache.TreeNode) list.get(0);
			root = newNodeChildren(tnodes, null);
		}
		return root;
	}

	/**
	 * 递归算法
	 * 
	 * @param ttParent 节点树
	 * @param parent 返回页面的树
	 * @return 返回treenode
	 */
	public TreeNode newNodeChildren(smtcl.mocs.beans.authority.cache.TreeNode ttParent,
			TreeNode parent) {
		TreeNode newNode = new DefaultTreeNode(ttParent, parent);
		newNode.setExpanded(true);
		for (smtcl.mocs.beans.authority.cache.TreeNode tt : ttParent.getChildNodes()) {
			// 组织树不显示设备
			if (!tt.getNodeType().equals("13")) {
				newNodeChildren(tt, newNode);
			}
		}
		return newNode;
	}


	/**
	 * 根据节点名字返回该节点下的所有节点id，包括子节点的id
	 * 并封装成 '1','2','3' 这种形式
	 * @param currentNode 节点
	 * @return 节点信息
	 */
	public String getAllTNodesId(smtcl.mocs.beans.authority.cache.TreeNode currentNode) {
		List<String> nodeIdList = new ArrayList<String>();// 当前节点包括子节点的id
		getNodesAllId(currentNode, nodeIdList);// 递归获取前节点id和子节点的id
        return StringUtils.returnHqlIN(nodeIdList);
	}
	/**
	 * 根据节点名字返回该节点下的所有节点id，包括子节点的id
	 * 并封装成 '1','2','3' 这种形式
	 * @param tnodes 节点
	 * @return 节点信息
	 */
	public String getAllTNodesId(TNodes tnodes) {
		String sql="select tf.node_id as nodeId from t_nodesflat tf where tf.ancestor_id='"+tnodes.getNodeId()+"'";
		List<Map<String,Object>> list=dao.executeNativeQuery(sql);
		String ns="";
		for(Map<String,Object> map:list){
			ns=ns+"'"+map.get("nodeId")+"',";
		}
		ns=ns.length()>0?ns.substring(0,ns.length()-1):ns;
		return ns;
	}


	/**
	 * 递归获取前节点id和子节点的id
	 * @param tnodes 节点
	 * @param nodeIdList 节点id集合
	 * @return List
	 */
	public List getNodesAllId(smtcl.mocs.beans.authority.cache.TreeNode tnodes,
			List<String> nodeIdList) {
		nodeIdList.add(tnodes.getNodeId());
		for (smtcl.mocs.beans.authority.cache.TreeNode tt : tnodes.getChildNodes()) {
			getNodesAllId(tt, nodeIdList);
		}
		return nodeIdList;
	}
	public List getNodesAllId(TNodes tnodes,List<String> nodeIdList) {
		nodeIdList.add(tnodes.getNodeId());
		for (TNodes tt : tnodes.getTNodeses()) {
			getNodesAllId(tt, nodeIdList);
		}
		return nodeIdList;
	}

	/**
	 * 根据当前节点id 返回所有父节点的名字
	 * @param nodeId 节点id
	 * @return List
	 */
	public String getAllParentName(String nodeId){		
		return authorizeService.getNodeLabel(nodeId);
	}
	
	/**
	 * 检查用户当前选择的节点对应的其他页面权限
	 * @param userId 用户id
	 * @param pageId 页面id
	 * @param buttonId 按钮id
	 * @param nodeId 节点id
	 * @return boolean
	 */
	@Override
	public boolean checkedClickNodeAuthority(String userId, String pageId, String buttonId, String nodeId) {
		return authorizeService.isAuthorize(userId, pageId, buttonId, nodeId);
	}
	
	/*--------------------------WebService start-----------------------------------*/
	/**
	 * 获取节点下的所有子节点
     * @param session httpsession
	 * @param nodeID 节点id
	 * @param userId 用户id
	 * @return 返回节点信息列表
	 */
	@Override
	public List<Map<String, Object>> getAllNodesByParentNodeId(HttpSession session, String nodeID,
			String userId) {
        Locale locale = SessionHelper.getCurrentLocale(session);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		try {
			// 权限集成后调用的方法
			List<smtcl.mocs.beans.authority.cache.TreeNode> list = authorizeService.getAuthorizedChildNodes(userId, "mocs.sbgl.page.sbgl",nodeID);
			if (list != null && list.size() > 0) {
				for (smtcl.mocs.beans.authority.cache.TreeNode t : list) {
					if(!t.getNodeType().equals("13")){
						Map<String, Object> nodesMap = new HashMap<String, Object>();
						nodesMap.put("nodeId", t.getNodeId());
                        if(locale.toString().equals("en") || locale.toString().equals("en_US")){
                            nodesMap.put("name", t.getEnNodeName());
                        }else {
                            nodesMap.put("name", t.getNodeName());
                        }
						nodesMap.put("nodeType", t.getNodeType());
						nodesMap.put("parentId", t.getParentId());
						nodesMap.put("checked", !t.isNocheck());
						nodesMap.put("isParent", t.getParent());
						String sql="select count(*) AS num from t_nodes where p_nodeid='"+t.getNodeId()+"' and nodeClass<>13";
						List<Map<String, Object>> countlist=dao.executeNativeQuery(sql, parameters);
						if(countlist.size()>0){
							Map<String, Object> map=countlist.get(0);
							System.out.println(map.get("num"));
							nodesMap.put("childNodesNum",map.get("num"));
						}else{
							nodesMap.put("childNodesNum",0);
						}
						
						listMap.add(nodesMap);
					}
					
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return listMap;
	}

	/**
	 * 根据nodeclass获取节点
	 * @param parameters 节点nodeClass
	 * @return 返回节点名称和节点ID
	 */
	@Override
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> get_All_Node_By_nodeClass(Collection<Parameter> parameters){
		String hql ="SELECT new Map(" +
				"t.nodeName as nodeName ," +
				//" t.TNodeType.nodeclass as TNodeType ," +
				" t.nodeId as nodeId" +
				") FROM TNodes as t " +
				"where 1=1";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		return dao.executeQuery( hql, parameters);
	}

	
	/**
	 * 获取前节点id
	 * @param nodeId 节点id
	 * @return List<String>
	 */
    @SuppressWarnings("unchecked")
	public List<TNodes> getNodesAllId(String nodeId) {
		String hql="from TNodes" +
				  " where nodeId in("+nodeId+")" +
				  " and nodeClass=12 order by seq" ;
		return dao.executeQuery(hql);
	}
	
	/**
	 * 根据用户id 页面id 返回节点集合
	 * @param userid  用户id       
	 * @param pageid  页面id      
	 * @return TreeNode
	 */
	public List<TNodes> returnWorkshop(String userid, String pageid) {		
		List<TNodes> rslist =new ArrayList<TNodes>();
		List list = authorizeService.getAuthorizedNodeTree(userid, pageid);
		
		if (null != list && list.size() > 0) {
			smtcl.mocs.beans.authority.cache.TreeNode tnodes = (smtcl.mocs.beans.authority.cache.TreeNode) list.get(0);
			rslist=getNodesAllId(getAllTNodesId(tnodes)); 
		}
		return rslist;
	}
	
	
	
	/**
	 * 根据节点id 获取节点对象
	 * @param Id 节点ID
	 * @return 返回节点信息
	 */
    @SuppressWarnings("unchecked")
	public List<TNodes> getTNodesById(String Id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TNodes where id='"+Id+"'";
		return dao.executeQuery(hql, parameters);
	}
}