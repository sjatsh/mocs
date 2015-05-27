package smtcl.mocs.beans.authority.org.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.i18n.IResourceAdapter;
import org.dreamwork.i18n.IResourceManager;
import org.dreamwork.jasmine2.configure.JasmineConfig;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.events.IClickListener;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.jasmine2.web.controls.Button;
import org.dreamwork.jasmine2.web.controls.Hidden;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.jasmine2.web.controls.TextBox;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.pojos.authority.OrgGroup;
import smtcl.mocs.pojos.authority.Organization;
import smtcl.mocs.pojos.authority.RGroupOrg;
import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.pojos.authority.Role;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.services.authority.IOrgGroupService;
import smtcl.mocs.services.authority.ITreeService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.IConsant;

/**
 * Created by IntelliJ IDEA.
 * User: wangli
 * Date: 12-10-22
 * Time: 下午9:15
 */
public class OrgGroupDetailPage extends Page {
    protected TextBox code, groupName, description;
//    protected CheckBox state;
    protected List<TreeNode> treeData;
    protected Button btnSave/*, btnQuery*/;
    protected LoginUser luser;
    protected Hidden groupId;
    protected OrgGroup group;
    protected IOrgGroupService service;
    protected List nodeList;
    protected boolean isReturn, view, isAdd;
//    protected String selNodes;
    //protected UserCon users;
//    protected GenericHtmlControl tree_div, user_div, base_div;

    @Override
    public void onPagePreload(Page page) throws EventException {
        btnSave.addClickListener(new IClickListener() {
            public void onClick(IWebControl iWebControl) throws EventException {
                save();
            }
        });
        luser = (LoginUser)session.getAttribute (IConsant.SESSION_LOGINUSER_KEY);
        service = (IOrgGroupService)ServiceFactory.getBean("orgGroupService");
        /*btnQuery.addClickListener(new IClickListener() {
            @Override
            public void onClick(IWebControl sender) throws EventException {
                if (StringUtils.isNotEmpty(users.getSaveUserIds())) {
                    List<Parameter> params = new ArrayList<Parameter>();
                    String [] userId = users.getSaveUserIds().split(",");
                    long [] userIdLs = new long[userId.length];
                    for (int i = 0; i < userId.length; i++) {
                        userIdLs[i] = Long.parseLong(userId[i]);
                    }
                    params.add(new Parameter("userId", userIdLs, Operator.IN));
                    users.loadData(params);
                } *//*else if (hasModify){
                    List<Parameter> params = new ArrayList<Parameter>();
                    params.add(new Parameter("groupId", group.getOrgGroupId(), Operator.EQ));
                    users.loadData(params);
                    users.setSaveUserIds();
                }*//*
                user_div.setAttribute("style", "display:block");
                tree_div.setAttribute("style", "display:none");
                selNodes = request.getParameter("tree");
            }
        });*/
    }

    @Override
    public void onPageLoad(Page page) throws EventException {
        if (StringUtils.isNotEmpty(groupId.getText())) {
            group = service.get(groupId.getText());
            code.setEditable(false);
            if (group == null) {
                if (this.defaultLocale!=null) {
                    IResourceManager manager = (IResourceManager) application.getAttribute(JasmineConfig.JASMINE_I18N_HOLDER);
                    IResourceAdapter adapter = manager.getResourceAdapter(resourceName);
                    addStartupStatement("alert('"+adapter.getString(locale, "app.non-date.info")+"');history.go(-1);");
                } else addStartupStatement("alert('该数据组不存在！');history.go(-1);");
                isReturn = true;
                return;
            } /*else if (StringUtils.isEmpty(users.getSaveUserIds())) {
                List<Parameter> params = new ArrayList<Parameter>();
                params.add(new Parameter("groupId", group.getOrgGroupId(), Operator.EQ));
                users.loadData(params);
                users.setSaveUserIds();
            }*/
            String viewString = request.getParameter("view");
            if (viewString != null)view = Boolean.valueOf(viewString);
        } else {
            isAdd = true;
//            group = new OrgGroup();
        }
//        if (!isAdd&&!hasModify)state.setEditable(false);
//        else state.setEditable(true);
    }

    @Override
    public void onPageLoadCompleted(Page page) throws EventException {
        if (isReturn) return;
        loadTreeData();
        try {
            dataBind();
        } catch (Throwable throwable) {
            //
        }
    }

    private void save() {
        try{
            setGroup();  //设置OrgGroup属性
            //设置数据组与数据权限的关联
    //        String treeNodes = request.getParameter("tree");
            String [] nodes = request.getParameterValues("orgId");
            List<RGroupOrg> gorgR = new ArrayList<RGroupOrg>(0);
    //        if (StringUtils.isNotEmpty(treeNodes)&& !"null".equals(treeNodes)) {
    //            String [] nodes = treeNodes.split(",");
            if (nodes != null)for (String node : nodes) {
                Organization org = new Organization();
                org.setOrgId(node);
                RGroupOrg orgR = new RGroupOrg();
                orgR.setOrg(org);
                orgR.setOrgGroup(group);
                gorgR.add(orgR);
            }
    //        }
            //设置数据组与用户的关联
            /*String userIds = users.getSaveUserIds();
            List<RUserOrgGroup> ruog = new ArrayList<RUserOrgGroup>(0);
            if (StringUtils.isNotEmpty(userIds)) {
                String [] nodes = userIds.split(",");
                for (String node : nodes) {
                    User user = new User();
                    user.setUserId(Long.parseLong(node));
                    RUserOrgGroup uOrgR = new RUserOrgGroup();
                    uOrgR.setUser(user);
                    uOrgR.setOrgGroup(group);
                    ruog.add(uOrgR);
                }
            }
            service.saveOrUpdate(group, ruog, gorgR);*/
            String[] userIds = request.getParameterValues("u_hidden");
            String[] roleIds = request.getParameterValues("r_hidden");
            List<RUserRoleOrgGroup> relList = new ArrayList<RUserRoleOrgGroup>();
            if (userIds != null)for (int i = 0; i < userIds.length; i++) {
                RUserRoleOrgGroup rel = new RUserRoleOrgGroup();
                Role r = new Role();
                r.setId(roleIds[i]);
                User u = new User();
                u.setUserId(userIds[i]);
                rel.setOrgGroup(group);
                rel.setRole(r);
                rel.setUser(u);
                relList.add(rel);
            }
            service.saveOrUpdate(group, relList, gorgR);
            if(isAdd)LogHelper.log(luser.getUser().getLoginName(), "passport.page.org.group.list", "add.success", ILogFinal.LOG_SUCCESS);
            else LogHelper.log(luser.getUser().getLoginName(), "passport.page.org.group.list", "update.success", ILogFinal.LOG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            if (isAdd)LogHelper.log(luser.getUser().getLoginName(), "passport.page.org.group.list", "add.fail", ILogFinal.LOG_FAIL);
            else LogHelper.log(luser.getUser().getLoginName(), "passport.page.org.group.list", "update.fail", ILogFinal.LOG_FAIL);
        }
        /*if (isAdd && !AuthorityUtil.isAdmin(luser.getUser().getUserId())) {
            ICommonService comm = (ICommonService) ServiceFactory.getBean("commonService");
            List<RUserRoleOrgGroup> uOrgGroup = comm.find(RUserRoleOrgGroup.class, null,
                    new Parameter("user.userId", luser.getUser().getUserId(), Operator.EQ),
                    new Parameter("orgGroup.orgGroupId", null, Operator.IS_NOT_NULL));
            List<MOrgGroupOrgGroup> list = new ArrayList<MOrgGroupOrgGroup>();
            for (RUserRoleOrgGroup ruro : uOrgGroup) {
                MOrgGroupOrgGroup data = new MOrgGroupOrgGroup();
                data.setOrgGroup(group);
                data.setCreateOrgGroup(ruro.getOrgGroup());
            }
            comm.save(list);
        }*/
        redirect("org_group_list.jasmine");
    }

    private void setGroup ()  {
        if (isAdd) {
            group = new OrgGroup();
            group.setCreateTime(new Date());
            group.setCreateUser(luser.getUser());
        } else {
            group.setUpdateTime(new Date());
            group.setUpdateUser(luser.getUser());
        }
        group.setCode(code.getText());
        group.setDescription(description.getText());
//        group.setState(state.isChecked()?0:1);
        group.setState(IConsant.VALID);
        group.setGroupName(groupName.getText());
    }

    private void loadTreeData () {
        ITreeService service = (ITreeService)ServiceFactory.getBean("treeService");
//        List result = null;
        treeData = AuthorityUtil.queryAuthOrgTree(luser.getUser().getUserId(), "passport.page.org.group.list", "button.view", false);
        /*else {
            result = service.find(new Parameter("orgGroupId", group.getOrgGroupId(), Operator.EQ));
            OrgTree o = new OrgTree();
            o.reloadTree(result);
            treeData = o.getRoots();
        }*/
        if (!isAdd) {
            nodeList = service.find(new Parameter("state", IConsant.VALID, Operator.EQ),
                    new Parameter("orgGroupId", group.getOrgGroupId(), Operator.EQ));
            String hql = "from RUserRoleOrgGroup where orgGroup.orgGroupId=:orgGroupId";
//            List list = service.executeQuery(hql, new Parameter("orgGroupId", group.getOrgGroupId(), Operator.EQ));
//            modifyOrg = list.size() > 0;
            /*StringBuffer buf = new StringBuffer();
            for (Object obj : list) {
                Organization org = (Organization) obj;
                buf.append(org.getOrgId()).append(",");
            }
            if (buf.length() > 0)selNodes = buf.substring(0, buf.length()-1);*/
        }
        /*OrgTree o = new OrgTree();
        o.reloadTree(result);
        treeData = o.getRoots();*/

    }
}
