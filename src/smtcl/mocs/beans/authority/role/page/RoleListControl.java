package smtcl.mocs.beans.authority.role.page;

import smtcl.mocs.services.authority.IRoleService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.pojos.authority.Role;

import smtcl.mocs.utils.authority.SessionHelper;
import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.events.IClickListener;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.jasmine2.web.controls.*;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.StringUtil;

import smtcl.mocs.beans.authority.secure.LoginUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 角色列表
 */
public class RoleListControl extends UserControl {
    protected org.dreamwork.util.IDataCollection<Role> data;
    protected Hidden items;
    protected Button btnDelete;
    protected boolean editable;
    protected String colums,roleName;
    protected List<String> columList;
    protected boolean editerVisable = false, codeVisable=false, nameVisable=false, remarkVisable=false, createrVisable=false;
    protected boolean hasManageRight,hasViewRight;
    protected LoginUser loginUser;

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public String getColums() {
		return colums;
	}

	public void setColums(String colums) {
		this.colums = colums;
	}

	public void onPagePreload(Page page) throws EventException {
        btnDelete.addClickListener(new IClickListener() {
            public void onClick(IWebControl sender) throws EventException {
                onDelete();
            }
        });
        loginUser = SessionHelper.loginUser(session);
        //hasAddRight = true/*AuthorityUtil.judge(loginUser.getUser().getUserId(), "passport.page.role.list", "button.new")*/;
        hasManageRight = AuthorityUtil.judge(loginUser.getUser().getUserId(), "passport.page.role.list", "button.manage");
        hasViewRight = AuthorityUtil.judge(loginUser.getUser().getUserId(), "passport.page.role.list", "button.view");
	}

    public void onPageLoad(Page page) throws EventException {
    	if(StringUtil.isEmpty(colums)){
    		colums = request.getParameter("colums");
    	}
    	if(StringUtil.isEmpty(roleName)){
    		roleName = request.getParameter("roleName");
    	}
    	
    	//是否可编?
    	if(editable){
    		editerVisable = true;
    	}
    	
    	//列名显示
    	if (!StringUtil.isEmpty(colums)){
    		if(colums.indexOf("code")>=0){
    			codeVisable=true; 
    		}
    		if(colums.indexOf("name")>=0){
    			nameVisable=true;
    		}
    		if(colums.indexOf("remark")>=0){
    			remarkVisable=true;
    		}
    		if(colums.indexOf("creater")>=0){
    			createrVisable=true;
    		}
    		
    	}
    }

    public void onPageLoadCompleted(Page page) throws EventException {
    	String method = request.getParameter("fromDiv");
        if (StringUtils.isNotEmpty(method)) {
        	List<Parameter> params = new ArrayList<Parameter>();
        	String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            String pageId = request.getParameter("pageId");
            String buttonId = request.getParameter("buttonId");
            if (StringUtils.isNotEmpty(pageNo)&&StringUtils.isNotEmpty(pageSize)) {
                loadData(Integer.parseInt(pageNo), Integer.parseInt(pageSize), pageId, buttonId, params);
            } else loadData(-1, -1, pageId, buttonId, params);
        }
        try {
            dataBind();
        } catch (Throwable throwable) {
            throw new EventException(throwable);
        }
    }

	public void loadData(int pageNo, int pageSize, String pageId, String buttonId, Collection<Parameter> params) {
    	IRoleService service = (IRoleService) ServiceFactory.getBean("roleService");
//	    if (!StringUtil.isEmpty(roleName)) {
//	    	params.add(new Parameter("name", "%"+roleName.trim()+"%", Operator.EQ));
//		}
        data = service.queryRolesByUser(loginUser.getUser().getUserId(), pageNo, pageSize, pageId, buttonId, params);
    }
    
    private void onDelete() {
        try {
            String text = items.getText();
            if (!StringUtil.isEmpty(text)) {
                String[] a = text.split(",");
                IRoleService service = (IRoleService) ServiceFactory.getBean("roleService");
//                Long [] roleId = new Long[a.length];
                /*for(int i = 0; i < a.length; i ++){
                    roleId[i] = Long.valueOf(a[i]);
//                	service.deleteRole(new Long(aa));
                }*/
                List<Role> list = service.delete(a);
                StringBuilder buf = new StringBuilder();
                for(int i=0; i<list.size(); i++) {
                    if (i !=0 && i % 3 == 0) buf.append("\\n");
                    buf.append(list.get(i).getName()).append("\t");
                }
                if(list.size()>0){
                	this.addStartupStatement("alert('${role.rel_del_msg}'+'"+buf+"')");
                }
            }
            redirect("roleList.jasmine");
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
    
    public int getTotal () {
        return data.getTotalRows();
    }
}