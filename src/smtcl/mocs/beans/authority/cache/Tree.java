package smtcl.mocs.beans.authority.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author gaokun
 * @create Jul 11, 2011 11:35:20 AM
 * @param <T>
 * 树结构类;
 */
public abstract class Tree {
	
	private ReentrantLock lock = new ReentrantLock();

	public abstract TreeNode transform( Object obj );
	
	protected List<TreeNode> roots = new Vector<TreeNode>();
	
	private Map<String,TreeNode> treeNodesMap = new Hashtable<String,TreeNode>();
	
	protected abstract boolean isRootNode(TreeNode treeNode);
	
	protected abstract void sort(Map<String,TreeNode> treeNodesMap);
	
	protected abstract void sort(List<TreeNode> treeNodes);
	
	/**
	 * 当前节点主键;
	 * @param treeNode
	 * @return
	 */
	protected abstract String cpk(TreeNode treeNode);
	
	/**
	 * 父节点主键;
	 * @param treeNode
	 * @return
	 */
	protected abstract String ppk(TreeNode treeNode);
	
	public<T> void reloadTree(List<T> list){
		lock.lock();
		try{
			treeNodesMap.clear();
			roots.clear();
			for (T nd : list) {
				TreeNode t = this.transform(nd);
				
				String key = cpk(t);
				
				treeNodesMap.put(key, t);
			}
			
			Collection<TreeNode> coll = treeNodesMap.values();
			for (TreeNode tn : coll) {
				if( isRootNode( tn )){
					roots.add(tn);
					continue;
				}

				String key = ppk(tn);
				TreeNode par = treeNodesMap.get(key);
				if( par != null ){
					par.getChildNodes().add(tn);
				}
			}
			
			sort(treeNodesMap);
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * 查找树节点;
	 * @param nodeId
	 * @param nodeType
	 * @return
	 */
	public TreeNode getTreeNode(String nodeId){
		return treeNodesMap.get(nodeId);
	}
	
	/**
	 * 查找根节点;
	 * @return
	 */
	public List<TreeNode> getRoots(){
		return roots;
	}
	
	public List<TreeNode> getNodes(){
		List<TreeNode> result = new ArrayList<TreeNode>();
		for (TreeNode treeNode : treeNodesMap.values()) {
			result.add(treeNode);
		}
		sort(result);
		return result;
	}
}
