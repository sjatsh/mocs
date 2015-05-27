/**
 * com.swg.authority.component.web.page AuthFrame.java
 */
package smtcl.mocs.beans.authority.component.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.IWebContainer;
import org.dreamwork.jasmine2.web.controls.Literal;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.jasmine2.web.controls.UserControl;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.services.authority.IAuthService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.SessionHelper;

import com.google.gson.Gson;

/**
 * @author gaokun
 * @create Nov 14, 2012 4:10:26 PM
 */
public class AuthFrame extends UserControl {
	
	public AuthFrame(){
		htmlTagName = "";
	}
	
	private String[] choose = new String[]{"u", "g", "r"};
	private Map<String, Map<String, String>> map_suffix = new HashMap<String, Map<String, String>>();
	
	protected String type = "u";
	
	protected String pageId;
	
	protected String buttonId;
	
	protected String limit = "";
	
	protected boolean admin;
	
	private LoginUser loginUser;
	
	protected String uId;
	
	protected boolean edit = true;

	private IAuthService as = null;
	
	private Gson gson = new Gson();
	
	protected String seqsu = "{}";
	protected String bind = "{}";
	protected String u_select = "{}";
	protected String r_select = "{}";
	protected String g_select = "{}";
	
	@Override
	public void init(){
		super.init();
		
		for (String e : choose) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("suffix", e);
			
			String t = "用户";
			if(e.equals("r")){
				t = "角色";
			}else if(e.equals("g")){
				t = "数据角色";
			}
			
			m.put("name", t);
			map_suffix.put(e, m);
		}
		
		loginUser = SessionHelper.loginUser(HttpContext.current().getSession());
		uId = loginUser.getUser().getUserId();
		as = (IAuthService)ServiceFactory.getBean("authService");
	}
	
	public void onPagePreload (Page page) throws EventException {
		//剥离CDATA字符串;
		IWebContainer ic = (IWebContainer)this.findControl("__template_select_list");
		
		String s = ((Literal)ic.getControl(0)).getInnerHTML().toString();
		s = s.replace("<![CDATA[", "");
		s = s.replace("]]>", "");
		
		ic.getChildControls().clear();
		ic.addControl(new Literal(s));
		
		admin = AuthorityUtil.isAdmin(uId);
	}
	
	
	private List<String[]> opBind(String type, Map map){
		
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 0; i < choose.length; i++) {
			String t = choose[i];
			
			if(!t.equals(type)){
				String id_key	= t + "_id";
				String name_key	= t + "_name";
				
//				String id		=	null;
//				if("u".equals(t)){
//					id		=	((Long)map.get(id_key)) + "";
//				}else{
				String id		=	(String)map.get(id_key);
//				}
				String name		=	(String)map.get(name_key);
				String hidden	=	t + "_hidden";	
				
				result.add(new String[]{id, name, hidden});
			}
		}
		return result;
	}
	
	private void compose(Page page)throws EventException{
		if(this == page){
            page = page.getParent().getPage();
        }

        List<Map> list_bind = new ArrayList<Map>();

        String value = dynamicAttributes.get("value");
        if(StringUtils.isNotEmpty(value)){
            try {
                Object o = page.eval(value);
                if(o != null){
                    String v = page.eval(value).toString();
                    limit = v;
                    list_bind = as.queryBindAuth(v, type);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        String view = dynamicAttributes.get("view");
        if (StringUtils.isNotEmpty(view)) {
            try {
                Object o = page.eval(view);
                if(o != null){
                    String v = page.eval(view).toString();
//                    list_bind = as.queryBindAuth(v, type);
                    edit = Boolean.valueOf(v);
                }
            } catch (Exception e) {
                //throw new RuntimeException(e);
            }
        }

        //注入 __AUTH_SEQ_SUFFIX;
        List<Map<String,String>> seq_suffix = new ArrayList<Map<String,String>>();
        for (String e : choose) {
            if(!e.equals(type)){
                seq_suffix.add(map_suffix.get(e));
            }
        }

        seqsu = gson.toJson(seq_suffix);

        //注入__AUTH_BIND_LIST;
        Map<String, Map<String,String>> bind_map = new LinkedHashMap<String, Map<String,String>>();
        //u,r,g
        for (Map o : list_bind) {
            List<String[]> op = opBind(type, o);
            
            String l_id		=	op.get(0)[0];
            String r_id		=	op.get(1)[0];
            String l_name	=	op.get(0)[1];
            String r_name	=	op.get(1)[1];
            String l_hidden	=	op.get(0)[2];
            String r_hidden	=	op.get(1)[2];
            
            Map<String, String> el = new HashMap<String, String>();
            String key = l_id + "," + r_id;
            el.put("id", key);
            el.put("l_id", l_id);
            el.put("r_id", r_id);
            el.put("lname", l_name);
            el.put("rname", r_name);
            el.put("l_hidden", l_hidden);
            el.put("r_hidden", r_hidden);

            bind_map.put(key, el);
        }

        bind = gson.toJson(bind_map);

//        int pageNo		= 	1;
//        int pageSize 	=	10000;
////        Long userId = loginUser.getUser().getUserId();
//        Boolean b = SessionHelper.userBindNode(request.getSession());
//        IDataCollection<User>		c_user = AuthorityUtil.queryAuthUserList(uId, b, pageId, buttonId, pageNo, pageSize);
//        IDataCollection<Role>		c_role = AuthorityUtil.queryAuthRoleList(uId, pageId, buttonId, pageNo, pageSize);
//        IDataCollection<OrgGroup>	c_orgGroup = AuthorityUtil.queryAuthOrgGroupList(uId, pageId, buttonId, pageNo, pageSize);
//
//        Map<String, Map<String,Object>> select_map_u = new LinkedHashMap<String, Map<String,Object>>();
//        Map<String, Map<String,Object>> select_map_r = new LinkedHashMap<String, Map<String,Object>>();
//        Map<String, Map<String,Object>> select_map_g = new LinkedHashMap<String, Map<String,Object>>();

//        for (User u : c_user.getData()) {
//            String id	= u.getUserId() + "";
//            String temp	= StringUtils.isBlank(u.getNickName()) ? "" : "(" + u.getNickName() + ")";
//            String name	= u.getLoginName() + temp;
//
//            Map<String, Object> m = new HashMap<String, Object>();
//            m.put("id", id);
//            m.put("name", name);
//            m.put("checked", false);
//            select_map_u.put(id, m);
//        }
//
//        for (Role r : c_role.getData()) {
//            String id	= r.getId() + "";
//            String name	= r.getName();
//
//            Map<String, Object> m = new HashMap<String, Object>();
//            m.put("id", id);
//            m.put("name", name);
//            m.put("checked", false);
//            select_map_r.put(id, m);
//        }
//
//        for (OrgGroup g: c_orgGroup.getData()) {
//            String id	= g.getOrgGroupId() + "";
//            String name	= g.getGroupName();
//
//            Map<String, Object> m = new HashMap<String, Object>();
//            m.put("id", id);
//            m.put("name", name);
//            m.put("checked", false);
//            select_map_g.put(id, m);
//        }
        if("r".equals(type)){
//            u_select = gson.toJson(select_map_u);
//            g_select = gson.toJson(select_map_g);
            this.findControl("__div_auth_r").setVisible(false);
        }else if("g".equals(type)){
//            u_select = gson.toJson(select_map_u);
//            r_select = gson.toJson(select_map_r);
            this.findControl("__div_auth_g").setVisible(false);
        }else{
//            r_select = gson.toJson(select_map_r);
//            g_select = gson.toJson(select_map_g);
            this.findControl("__div_auth_u").setVisible(false);
        }
	}
	
	@Override
    public void onPageLoadCompleted(Page page) throws EventException {
		//组装页面;
		compose(page);
		
    	try{
    		dataBind();
    	}catch(Throwable e){
    		e.printStackTrace();
    	}
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

	/**
	 * @return the buttonId
	 */
	public String getButtonId() {
		return buttonId;
	}

	/**
	 * @param buttonId the buttonId to set
	 */
	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

}
