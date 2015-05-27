package smtcl.mocs.beans.authority.role.page;

import java.util.ArrayList;
import java.util.List;

import smtcl.mocs.pojos.authority.*;
import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.services.authority.IRoleService;
import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.beans.authority.cache.TreeNode;

import smtcl.mocs.utils.authority.SessionHelper;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.events.IClickListener;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.jasmine2.web.controls.*;
import org.dreamwork.jasmine2.web.controls.Button;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.ListDataCollection;
import org.dreamwork.util.StringUtil;

import smtcl.mocs.beans.authority.secure.LoginUser;


	/**
    * 角色维护
    */
public class RoleDetailPage extends org.dreamwork.jasmine2.web.controls.Page {
    protected Button btnSave;
    protected Button btnDelete,btnDelete2,btnQuery;
    protected Hidden items,/*buttons,*/pageNo;
    protected smtcl.mocs.pojos.authority.Role roleInfo;
    protected Hidden roleId;
    protected TextBox role_Name,roleCode;
    protected TextBox remark;
    protected DropdownList roleType;
    protected IRoleService service;
    protected User user;
    protected org.dreamwork.util.IDataCollection<RUserRoleOrgGroup> userList;
    protected org.dreamwork.util.IDataCollection<RRolePage> pageList;
    protected List<TreeNode> list;
    private LoginUser loginUser =  null;
    protected boolean hasManageRight,hasPageEditRight;
//    protected Pagging2 pagging,pagging2;

    public void onPagePreload(Page page) throws EventException {
    	loginUser =  (LoginUser) session.getAttribute(IConsant.SESSION_LOGINUSER_KEY);
    	service = (IRoleService) ServiceFactory.getBean ("roleService");
//        list = service.getTreeNodes(loginUser.getUser().getUserId(),(Locale) session.getAttribute(IConsant.LOGIN_LOCATION));
        list = AuthorityUtil.queryAuthFunctionTree(loginUser.getUser().getUserId(), "passport.page.role.list", "button.view", locale);
        btnSave.addClickListener(new IClickListener() {
            public void onClick(IWebControl sender) throws EventException {
                onSave();
            }
        });
        btnDelete.addClickListener(new IClickListener() {
            public void onClick(IWebControl sender) throws EventException {
                onDelete();
            }
        });
        btnDelete2.addClickListener(new IClickListener() {
            public void onClick(IWebControl sender) throws EventException {
                onDelete2();
            }
        });
        //查看
        String mode = request.getParameter("mode");
        if(!StringUtil.isEmpty(mode)&&"view".equals(mode)){
        	hasManageRight = false;
        	hasPageEditRight = false;
        //编辑	
        }else{
        	//管理权限
            hasManageRight = AuthorityUtil.judge(loginUser.getUser().getUserId(), "passport.page.role.list", "button.manage");
            /* 2012-12-16日邮?
             * 功能角色和数据角色一旦被分配了，不允许修改功能角色和功能菜单的关?
             */
            hasPageEditRight = true;
        }
    }

    public void onPageLoad(Page page) throws EventException {
    	this.registerClientScriptBlock(InternalJS.class, InternalJS.UTIL_JS);
        List<Sort> sortList = new ArrayList<Sort>();
        sortList.add(new Sort("typeId", Sort.Direction.ASC));
        List<TypeRole> typeList = service.find(TypeRole.class, sortList);
        for(TypeRole type : typeList) {
            type.setText(SessionHelper.getText(type.getText()));
        }
        roleType.setDataSource(typeList);
        roleType.setValueField("typeId");
        roleType.setTextField("text");
        if (!StringUtil.isEmpty(roleId.getText())) {
        	roleInfo = service.get(roleId.getText());
        }
    }

    public void onPageLoadCompleted(Page page) throws EventException {
    	loadData();
        if(!isPostback()) {
            if (!StringUtil.isEmpty(roleId.getText())) {
                roleType.setSelectedValue(roleInfo.getRoleType().getTypeId());
                roleCode.setEditable(false);
            }
        }
        try {
            dataBind();
        } catch (Throwable throwable) {
            throw new EventException(throwable);
        }
    }
    
    private void loadData() {
    	if(!StringUtil.isEmpty(roleId.getText())){
//    		userList = service.queryUsersByRoleId(pagging.getPageNo(),pagging.getPageSize(),new Long(roleId.getText()),null);
    		pageList = new ListDataCollection<RRolePage>();
            pageList.setData(service.queryPermissionByRoleId(roleId.getText()));
    	}
    }

    private void onSave() {
        boolean create = false;
        //基本信息
        if (StringUtil.isEmpty(roleId.getText())) {
        	roleInfo = new Role();

            roleInfo.setCreater(loginUser.getUser());
            roleInfo.setState(Long.valueOf(IConsant.VALID+""));
            create = true;
        }
        roleInfo.setName(role_Name.getText());
        roleInfo.setCode(roleCode.getText());
        roleInfo.setRemark(remark.getText());
        TypeRole typeRole = new TypeRole();
        typeRole.setTypeId((String)roleType.getSelectedValue());
        roleInfo.setRoleType(typeRole);

        //功能
        String []pageButtons = request.getParameterValues("button");
        List<RRoleButton> btnList = new ArrayList<RRoleButton>();
        String s = IConsant.BTN_PREFIX.replace("$", "\\$");
        if (pageButtons != null)for (String btn : pageButtons) {
            String [] pb = btn.split(s);
            //add by gjy 2012-12-21
            //查看权限已默认添?
            if(IConsant.BTN_VIEW.equals(pb[1])){
            	continue;
            }else{
            	RRoleButton rr = new RRoleButton();
            	smtcl.mocs.pojos.authority.Button b = new smtcl.mocs.pojos.authority.Button();
                b.setButtonId(pb[1]);
                smtcl.mocs.pojos.authority.Page p = new smtcl.mocs.pojos.authority.Page();
                p.setPageId(pb[0]);
                rr.setButton(b);
                rr.setPage(p);
                rr.setRole(roleInfo);
                btnList.add(rr);
            }
        }

        //权限
        String[] userIds = request.getParameterValues("u_hidden");
        String[] groupIds = request.getParameterValues("g_hidden");
        List<RUserRoleOrgGroup> relList = new ArrayList<RUserRoleOrgGroup>();
        if (userIds != null)for (int i = 0; i < userIds.length; i++) {
            RUserRoleOrgGroup rel = new RUserRoleOrgGroup();
            OrgGroup g = new OrgGroup();
            g.setOrgGroupId(groupIds[i]);
            User u = new User();
            u.setUserId(userIds[i]);
            rel.setOrgGroup(g);
            rel.setRole(roleInfo);
            rel.setUser(u);
            relList.add(rel);
        }

        //角色与页面的关系
        String []pid = request.getParameterValues("pid");
        List<RRolePage> rpList = new ArrayList<RRolePage>();
        if (pid != null) for (int i = 0; i < pid.length; i++) {
        	//add by gjy 2012-12-21
        	//默认添加查看权限
        	RRoleButton rr = new RRoleButton();
        	smtcl.mocs.pojos.authority.Button b = new smtcl.mocs.pojos.authority.Button();
            b.setButtonId(IConsant.BTN_VIEW);
            smtcl.mocs.pojos.authority.Page pp = new smtcl.mocs.pojos.authority.Page();
            pp.setPageId(pid[i]);
            rr.setButton(b);
            rr.setPage(pp);
            rr.setRole(roleInfo);
            btnList.add(rr);
            //add end
            RRolePage rp = new RRolePage();
            rp.setRole(roleInfo);
            smtcl.mocs.pojos.authority.Page p = new smtcl.mocs.pojos.authority.Page();
            p.setPageId(pid[i]);
            rp.setPage(p);
            rpList.add(rp);
        }
        try {
        	service.saveOrUpdate(roleInfo, relList, btnList, rpList);
        	LogHelper.log(loginUser.getUser().getLoginName(), "passport.page.role.list", "role add success", ILogFinal.LOG_SUCCESS);
		} catch (Exception e) {
			LogHelper.log(loginUser.getUser().getLoginName(), "passport.page.role.list", "role add failed", ILogFinal.LOG_FAIL);
			e.printStackTrace();
		}
        
        
        /*if (create) {
        	LoginUser loginUser =  (LoginUser) session.getAttribute(IConsant.SESSION_LOGINUSER_KEY);
        	roleInfo.setCreater(loginUser.getUser());
        	roleInfo.setState(Long.valueOf(IConsant.VALID+""));
            service.save(roleInfo);
        } else {
            service.update(roleInfo);
            service.saveButtonsByRoleId(new Long(roleId.getText()),buttons.getText());
        }*/
        //保存按钮信息
        
        //this.addStartupStatement ("保存成功");
        /*if (create && !AuthorityUtil.isAdmin(loginUser.getUser().getUserId())) {
            ICommonService comm = (ICommonService) ServiceFactory.getBean("commonService");
            List<RUserRoleOrgGroup> uOrgGroup = comm.find(RUserRoleOrgGroup.class, null,
                    new Parameter("user.userId", loginUser.getUser().getUserId(), Operator.EQ),
                    new Parameter("orgGroup.orgGroupId", null, Operator.IS_NOT_NULL));
            List<MRoleOrgGroup> list = new ArrayList<MRoleOrgGroup>();
            for (RUserRoleOrgGroup ruro : uOrgGroup) {
                MRoleOrgGroup data = new MRoleOrgGroup();
                data.setRole(roleInfo);
                data.setCreateOrgGroup(ruro.getOrgGroup());
            }
            comm.save(list);
        }*/
//        redirect("roleDetail.jasmine?roleId=" + roleInfo.getId());
        redirect("roleList.jasmine");
    }
    
    private void onDelete() {
        try {
            String text = items.getText();
            if (!StringUtil.isEmpty(text)) {
                String[] a = text.split(",");
                ICommonService service = (ICommonService) ServiceFactory.getBean("commonService");
                for(String aa:a){
                	service.deleteById(RUserRoleOrgGroup.class, "id", new Long(aa));
                }
            }
            LogHelper.log(loginUser.getUser().getLoginName(), "passport.page.role.list", "role delete success", ILogFinal.LOG_SUCCESS);
            redirect("roleDetail.jasmine?roleId="+roleId.getText()+"&pageNo=p1");
        } catch (Exception ex) {
        	LogHelper.log(loginUser.getUser().getLoginName(), "passport.page.role.list", "role delete failed", ILogFinal.LOG_FAIL);
            ex.printStackTrace();
        }
    }
    
    private void onDelete2() {
        try {
            String text = items.getText();
            if (!StringUtil.isEmpty(text)) {
                String[] a = text.split(",");
                ICommonService service = (ICommonService) ServiceFactory.getBean("commonService");
                for(String aa:a){
                	service.deleteById(RRolePage.class, "id", new Long(aa));
                }
            }
            redirect("roleDetail.jasmine?roleId="+roleId.getText()+"&pageNo=p2");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}