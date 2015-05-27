/**
 * com.swg.authority.component.web TreeTestPage.java
 */
package smtcl.mocs.beans.authority.component;


import java.util.ArrayList;
import java.util.List;

import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Page;

import smtcl.mocs.beans.authority.cache.NodeData;
import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.beans.authority.cache.OrgTree;

/**
 * @author gaokun
 * @create Oct 22, 2012 2:59:27 PM
 */
public class TreeTestPage extends Page {
	
	protected List<TreeNode> list;
	
	@Override
	public void onPagePreload (Page page) throws EventException {
		List<NodeData> result = new ArrayList<NodeData>();
		
		/*NodeData root = new NodeData();
		root.setNodeId("1000");

		root.setParentId(null);
		
		NodeData n1 = new NodeData();
		n1.setNodeId("1001");

		n1.setParentId("1000");
		
		NodeData n2 = new NodeData();
		n2.setNodeId("1002");
		
		n2.setParentId("1000");
		
		NodeData n3 = new NodeData();
		n3.setNodeId("10011");
		
		n3.setParentId("1001");*/
		
		/*result.add(root);
		result.add(n1);
		result.add(n2);
		result.add(n3);*/
		
		OrgTree o = new OrgTree();
		o.reloadTree(result);
		
		list = o.getRoots();
		
	}
	
	public void onPageLoadCompleted(Page page) throws EventException {
        try {
            dataBind();
        } catch (Throwable throwable) {
            throw new EventException(throwable);
        }
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
