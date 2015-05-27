package smtcl.mocs.beans.authority.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Literal;
import org.dreamwork.jasmine2.web.controls.WebContainer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.authority.VelocityHelper;

/**
 * @author gaokun
 * @create Oct 15, 2012 1:32:11 PM
	useage:
	<%@taglib prefix="t" uri="tree.jscx" name="tree"%>
	<link href="../javascript/tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" charset="utf-8" />
	<script type="text/javascript" charset="utf-8" src="../javascript/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../javascript/tree/js/jquery.ztree.all-3.4.min.js"></script>
	<t:tree id="" type="" pop="" iwidth="" twidth="" theight="" callback=""/>
	
	control param:
			id:		 	required 		String					unique identity!  					  <input type="hidden" id="id" name="id"/>
			iwidth:  	not 			int						text width							   default:120
			twidth:  	not				int						tree width								default:120
			theight: 	not				int						tree height								default:400
			callback:	not				javascript:function		tree nodeOnClick or onCheck event!
				callback(event, treeId, treeNode)
		
	control function:
			javascript:jQuery.fn.zTree.reinit(id, val, zNodes, setting, expandAll);
			id 			: the id param of control;
			val			: like "" or "1000" or "1000,1001,1002"
			zNodes		: new zNodes like [{"name":"newRoot", "children":[{"name":"node1"},...]},...]
			setting		: {}
			expandAll	: default:false;    expand all nodes;
	control return:
			<input type="hidden" id="id" name="id"/> value is selected nodes join with ",";
 */
public class PopAsynTreeControl extends WebContainer {

    public PopAsynTreeControl(){
        this.htmlTagName="";
    }
	
	protected int iwidth 	= 120;
	
	protected int twidth 	= 120;
	protected int theight 	= 400;
	
	protected boolean asyn = true;
	
	protected boolean pop	= true;
	
	protected String filter ="";
	
	protected String type;
	
	protected String pageId = "passport.page.org";
	
	protected String yps = "";
	protected String nps = "";
	
	public boolean isPop() {
		return pop;
	}

	public void setPop(boolean pop) {
		this.pop = pop;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYps() {
		return yps;
	}

	public void setYps(String yps) {
		this.yps = yps;
	}

	public String getNps() {
		return nps;
	}

	public void setNps(String nps) {
		this.nps = nps;
	}

	/**
	 * 存放当前编辑节点值的html元素的id;
	 */
	protected String exclude;
	
	/**
	 * @return the asyn
	 */
	public boolean isAsyn() {
		return asyn;
	}

	/**
	 * @param asyn the asyn to set
	 */
	public void setAsyn(boolean asyn) {
		this.asyn = asyn;
	}

	/**
	 * @return the exclude
	 */
	public String getExclude() {
		return exclude;
	}

	/**
	 * @param exclude the exclude to set
	 */
	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	/**
	 * 点击节点回调函数;
	 */
	protected String callback;
	
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
	
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the pageId
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	
	
	public void createChildControls () {
		String checkbox = "";
		if("checkbox".equalsIgnoreCase(type)){//多选框;
			checkbox = "check: { enable: true, chkboxType:{ \"Y\" :\"" + yps + "\", \"N\" :\"" + nps + "\" }},";
			pop = false;
		}
		
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
		map_param.put("exclude", exclude);
		
		String value = dynamicAttributes.get("value");
		if(StringUtils.isNotEmpty(value)){
			try {
				Object o = page.eval(value);
				if(o != null){
					String v = page.eval(value).toString();
					String sid = "";
					String txt = "";
					if(StringUtils.isNotBlank(v)){
						sid = v;
						txt = AuthorityUtil.getNodeTxt(sid);
					}
					map_param.put("value", sid);
					map_param.put("txt", txt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isEmpty(getId()))
				throw new RuntimeException("The tree.jscx control's id attribute is empty!");
		
		String user_id = SessionHelper.loginUser(HttpContext.current().getSession()).getUser().getUserId();
		String async_setting= String.format("async	:	{autoParam : [\"nodeId=nodeId\"],\r\n" + 
				"							 dataFilter : ___ds_filter_%s,\r\n" + 
				"							 dataType : \"text\",\r\n" + 
				"							 enable : true,\r\n" + 
				"							 otherParam : {\"userId\":\"%s\", \"exclude\":\"\", \"filter\":\"%s\", \"pageId\":\"%s\"},\r\n" + 
				"							 type : \"get\",\r\n" + 
				"							 url : \"org/queryOrgChild.ajax\"},", id, user_id, filter, pageId);	
		
		if(StringUtils.isNotBlank(callback))
			map_param.put("callback", callback);
		
		map_param.put("pop", pop);
		
		map_param.put("async_setting", async_setting);
		
		map_param.put("asyn", asyn);
		
		String out = null; 
		if(pop){
			out = VelocityHelper.process("Pop.vm", map_param);
		}else{
			out = VelocityHelper.process("Flat.vm", map_param);
		}
		this.addControl(new Literal (out));
		
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\">\r\n");
		
		map_param.put("zNodes", "[]");
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
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
