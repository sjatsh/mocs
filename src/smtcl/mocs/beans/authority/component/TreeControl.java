/**
 * com.swg.authority.component.web TreeControl.java
 */
package smtcl.mocs.beans.authority.component;




import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Literal;
import org.dreamwork.jasmine2.web.controls.WebContainer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.authority.VelocityHelper;


public class TreeControl extends WebContainer {

    public TreeControl(){
        this.htmlTagName="";
    }
	
	protected String		type;
	
	protected boolean		pop			= true;
	
//	protected boolean		multi		= false;
	
//	protected String 		raidoCM		= null;
	
	protected int iwidth 	= 120;
	
	protected int twidth 	= 120;
	protected int theight 	= 400;
	
	protected String yps = "ps";
	protected String nps = "ps";
	
	protected Integer level = 0;
	
	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 点击节点回调函数;
	 */
	protected String callback;
	
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
	 * @return the pop
	 */
	public boolean isPop() {
		return pop;
	}

	/**
	 * @param pop the pop to set
	 */
	public void setPop(boolean pop) {
		this.pop = pop;
	}

	/**
	 * @return the iwidth
	 */
	public int getIwidth() {
		return iwidth;
	}

	/**
	 * @param iwidth the iwidth to set
	 */
	public void setIwidth(int iwidth) {
		this.iwidth = iwidth;
	}

	/**
	 * @return the twidth
	 */
	public int getTwidth() {
		return twidth;
	}

	/**
	 * @param twidth the twidth to set
	 */
	public void setTwidth(int twidth) {
		this.twidth = twidth;
	}

	/**
	 * @return the theight
	 */
	public int getTheight() {
		return theight;
	}

	/**
	 * @param theight the theight to set
	 */
	public void setTheight(int theight) {
		this.theight = theight;
	}

	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * @param callback the callback to set
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	
	
	public void createChildControls () {
//		id = "test1";
//		twidth = 140;
//		pop = true;
//		type = "checkbox";
		
		String context = this.context.getRequest().getContextPath();
		String jquery_path		= context + "/javascript/jquery-1.6.2.min.js";
		String jquery_tree_path	= context + "/javascript/tree/js/jquery.ztree.all-3.4.min.js";
		String tree_css			= context + "/javascript/tree/css/zTreeStyle/zTreeStyle.css";
		
		Map<String, Object> map_param = new HashMap<String, Object>();
		map_param.put("select", SessionHelper.getText("app.select"));
		map_param.put("clear", SessionHelper.getText("app.clear"));
		map_param.put("id", id);
		map_param.put("iwidth", iwidth);
		map_param.put("twidth", twidth);
		map_param.put("theight", theight);
		
		map_param.put("jquery_path", jquery_path);
		map_param.put("tree_css", tree_css);
		map_param.put("jquery_tree_path", jquery_tree_path);
		
		map_param.put("type", type);
		
		if(StringUtils.isEmpty(getId()))
				throw new RuntimeException("The tree.jscx control's id attribute is empty!");
		
//		if(StringUtils.isEmpty(raidoCM))
//			raidoCM = "all";
		
		if(StringUtils.isNotBlank(callback))
			map_param.put("callback", callback);
		
		String checkbox = "";
		if("checkbox".equalsIgnoreCase(type)){//
			checkbox = "check: { enable: true, chkboxType:{ \"Y\" :\"" + yps + "\", \"N\" :\"" + nps + "\" }},";
			pop = false;
		}
//		else if("radio".equalsIgnoreCase(type)){//
//			checkbox = "check: { enable: true, chkStyle: \"radio\", radioType: \"" + raidoCM + "\"},";
//		}
		
		map_param.put("pop", pop);
		
		String out = null; 
		if(pop){
			out = VelocityHelper.process("Pop.vm", map_param);
		}else{
			out = VelocityHelper.process("Flat.vm", map_param);
		}
		this.addControl(new Literal (out));
		
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\">\r\n");
		
		
		List<TreeNode>  zNodes = (List<TreeNode>)getDataSource();
		zNodes = zNodes == null ? Collections.EMPTY_LIST : zNodes; 
		
		map_param.put("zNodes", gson.toJson(zNodes));
		map_param.put("multi", false);
		map_param.put("checkbox", checkbox);
		
		if(pop){
			sb.append(VelocityHelper.process("Pop_Fun.vm", map_param));
		}
		
		sb.append(VelocityHelper.process("Init.vm", map_param));
		sb.append("</script>\n");
		
		this.addControl(new Literal(sb.toString()));
		
		super.createChildControls();
	}
	
	public void onDataBind (Object sender) throws EventException {
		super.onDataBind(sender);
		this.createChildControls();
	}

	/**
	 * @return the yps
	 */
	public String getYps() {
		return yps;
	}

	/**
	 * @param yps the yps to set
	 */
	public void setYps(String yps) {
		this.yps = yps;
	}

	/**
	 * @return the nps
	 */
	public String getNps() {
		return nps;
	}

	/**
	 * @param nps the nps to set
	 */
	public void setNps(String nps) {
		this.nps = nps;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
