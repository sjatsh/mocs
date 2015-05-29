package smtcl.mocs.beans.authority.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smtcl.mocs.pojos.authority.Organization;

/**
 * @author gaokun
 * @create Jul 13, 2011 3:13:33 PM
 * 组织结构树;
 */
public class OrgTree extends Tree {
	
	/**
	 * OrgTree 日志类;
	 */
	private final static Logger logger = Logger.getLogger(OrgTree.class);
	
	private boolean nocheck;
	
	private boolean checked;
	
	private Boolean parent;
	
	public OrgTree(){
		this(false, false, null);
	}
	
	public OrgTree(boolean nocheck, boolean checked, Boolean parent){
		this.nocheck = nocheck;
		this.checked = checked;
		this.parent	 = parent;
	}
	
	/* (non-Javadoc)
	 * @see com.swg.alcom.cache.Tree#transform(java.lang.Object)
	 */
	@Override
	public TreeNode transform(Object obj) {
		
		if( obj != null && (obj.getClass().isAssignableFrom(Organization.class))){
			Organization n = (Organization)obj;
			
			TreeNode treeNode = new TreeNode();
			try{
				treeNode.setNodeId(n.getOrgId());
				treeNode.setNodeName(n.getName());
                treeNode.setEnNodeName(n.getEnName());
				treeNode.setParentId(n.getParentId() == null ? null : n.getParentId());
				treeNode.setSeq(n.getSeq());
				treeNode.setNocheck(nocheck);
				treeNode.setChecked(checked);
				treeNode.setParent(parent);
				treeNode.setBindData(n);
				treeNode.setParent(n.isParent());
				treeNode.setNodeType(n.getOrgType().getTypeId());
				
				return treeNode;
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.swg.alcom.cache.Tree#isRootNode(com.swg.alcom.cache.TreeNode)
	 */
	@Override
	protected boolean isRootNode(TreeNode treeNode) {
		boolean result = false;
		if( treeNode != null 
				&& treeNode.getParentId() == null ){
			result = true;
		}
		return result;
	}

	/* 
	 * 排序
	 */
	@Override
	protected void sort(Map<String, TreeNode> treeNodesMap) {
		Sort sort = new Sort();
		for (TreeNode tn : treeNodesMap.values()) {
			Collections.sort(tn.getChildNodes(), sort ); 
		}
	}
	
	protected void sort(List<TreeNode> treeNodes) {
		Sort sort = new Sort();
		Collections.sort(treeNodes, sort ); 
	}
	
	static class Sort implements Comparator<TreeNode>{

		public int compare(TreeNode o1, TreeNode o2) {
			if(o1 == null || o1.getSeq() == null ) return 1;
			if(o2 == null || o2.getSeq() == null ) return -1;
			return (int)Math.signum(o1.getSeq() - o2.getSeq());
		}
	}
	
	public void down(TreeNode t, boolean nocheck){
		if(t != null){
			t.setNocheck(nocheck);
			for (TreeNode c : t.getChildNodes()) {
				down(c, nocheck);
			}
		}
	}

	/**
	 * @return the nocheck
	 */
	public boolean isNocheck() {
		return nocheck;
	}

	/**
	 * @param nocheck the nocheck to set
	 */
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the parent
	 */
	public Boolean getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Boolean parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see com.swg.authority.cache.Tree#cpk(com.swg.authority.cache.TreeNode)
	 */
	@Override
	protected String cpk(TreeNode treeNode) {
		// TODO Auto-generated method stub
		return treeNode.getNodeId();
	}

	/* (non-Javadoc)
	 * @see com.swg.authority.cache.Tree#ppk(com.swg.authority.cache.TreeNode)
	 */
	@Override
	protected String ppk(TreeNode treeNode) {
		// TODO Auto-generated method stub
		return treeNode.getParentId();
	}
}
