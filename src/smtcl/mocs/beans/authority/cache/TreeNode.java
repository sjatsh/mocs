package smtcl.mocs.beans.authority.cache;

import java.util.List;
import java.util.Vector;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author gaokun
 * @create Jul 11, 2011 11:05:15 AM
 * @param <T>
 * Ê÷½Úµã;
 */
public class TreeNode{
	
	@Expose
	private String nodeId;
	
	@Expose
	@SerializedName(value="name")
	private String nodeName;
	
	@Expose
	private String nodeType;
	
	@Expose
	private String parentId;
	
	@Expose
	private Integer seq;
	
	@Expose
	private Object bindData;
	
	@Expose
	@SerializedName(value="children")
	private List<TreeNode> childNodes = new Vector<TreeNode>();
	
	/**
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Expose
	private boolean checked = false;
	
	@Expose
	private boolean nocheck = false;
	
	@Expose
	@SerializedName(value="isParent")
	private Boolean parent = null;
	
	@Expose
	private TreeFont font;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<TreeNode> getChildNodes() {
		return childNodes;
	}
	
	public Object getBindData(){
		return this.bindData;
	}
	
	public void setBindData( Object bindData ){
		this.bindData = bindData;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
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
		font = this.nocheck ? TreeFont.DISABLED_FONT : null;
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
}
