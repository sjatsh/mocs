package smtcl.mocs.beans.authority.cache;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author gaokun
 * @create Jul 11, 2011 5:39:48 PM
 */
public class NodeData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nodeId;
	private String nodeType;
	private String parentId;
	private String nodeName;
	
	private boolean checked = false;
	private boolean nocheck = true;
	
	private Integer seq;
	
	@Expose
	private String type;
	/**
	 * @return the nodeId
	 */
	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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
	 * @return the check
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param check the check to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
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
}
