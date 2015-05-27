package smtcl.mocs.beans.authority.user.page;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.services.authority.IUserService;
import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.EnumStatus;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.pojos.authority.OrgGroup;
import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.pojos.authority.Role;
import smtcl.mocs.pojos.authority.User;

import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.events.IClickListener;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.jasmine2.web.controls.*;
import org.dreamwork.misc.AlgorithmUtil;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.StringUtil;

/*******************************************************************************
 * @author jun �û�ά�� ���ܣ������û����޸��û���Ϣ
 */
public class UserInfoDetailPage extends Page {
	protected Button btnSave;
	protected User userInfo;
	protected Hidden userId;
	// loginName ��¼���� password ���룻 email �ʼ���ַ�� nickName �ǳ�, �û�����
	protected TextBox loginName, password, email, nickName, userCode,roleName,groupName;
//	protected boolean loginNameVisible = false;
	protected ICommonService service;
	protected List<User> userList;
	protected boolean view ; //�ж��Ƿ�ͨ���鿴����
	protected List<TreeNode> treeData;

	/***
	 * ����ҳ��ǰ�¼���
	 * 1.��� ���水ť�¼�
	 * 2.��ѯ �û�Ȩ����Ϣ
	 */
	public void onPagePreload(Page page) throws EventException {
		btnSave.addClickListener(new IClickListener() {
			public void onClick(IWebControl sender) throws EventException {
				try { 
					onSave();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		LoginUser loginUser = (LoginUser) session.getAttribute(IConsant.SESSION_LOGINUSER_KEY);
		if(loginUser!=null){
			treeData = AuthorityUtil.queryAuthOrgTree(loginUser.getUser().getUserId(),"passport.page.user.list","button.view", false);
		}else{
			Parameter p_user_name = new Parameter ("loginName", User.ADMIN, Operator.EQ);

	        IUserService us = (IUserService)ServiceFactory.getBean("userService");
	        User user = us.get (p_user_name);
			treeData = AuthorityUtil.queryAuthOrgTree(user.getUserId(),"passport.page.user.list","button.view", false);
		}		
	}

	/***
	 * ҳ������¼���
	 * 1. ��ȡ�û���Ϣ
	 */
	public void onPageLoad(Page page) throws EventException {
		this.registerClientScriptBlock(InternalJS.class, InternalJS.UTIL_JS);
		service = (ICommonService) ServiceFactory.getBean("commonService");
		if (!StringUtil.isEmpty(userId.getText())) {
			userInfo = service.get(User.class, userId.getText());
			loginName.setEditable(false);
			String viewString = request.getParameter("view");
            if (viewString != null)view = Boolean.valueOf(viewString);
		} else {
			userInfo = new User();
		}
	}

	/**
	 * ҳ���������¼���
	 * 1. ������ݼ���
	 * 2. ҳ������İ�
	 */
	public void onPageLoadCompleted(Page page) throws EventException {
		loadData();
		try {
			dataBind();
		} catch (Throwable throwable) {
			throw new EventException(throwable);
		}
	}

	private void loadData() {
	}

	/**
	 * �����û���Ϣ
	 * 1. �����û��޸ĺ����Ϣ
	 * 2. �����û������ݽ�ɫ�͹��ܽ�ɫ���ߵĹ�ϵ
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	private void onSave() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		IUserService userService = (IUserService) ServiceFactory.getBean("userService"); 
		LoginUser loginUser = (LoginUser) session.getAttribute(IConsant.SESSION_LOGINUSER_KEY);
		String pageId = "passport.page.user.list";
		try{
			if (StringUtil.isEmpty(userId.getText())) {
				userInfo = new User();
				userInfo.setRegisterDate(new Date(System.currentTimeMillis()));
				userInfo.setStatus(EnumStatus.Valid.getValue());
				
				if (loginUser != null)
					userInfo.setCreateUser(loginUser.getUser().getCreateUser());
			}

			if (!StringUtil.isEmpty(loginName.getText())) {
				userInfo.setLoginName(loginName.getText());
			}
			if (!StringUtil.isEmpty(nickName.getText())) {
				userInfo.setNickname(nickName.getText());
			}
			if (!StringUtil.isEmpty(password.getText())) {
				userInfo.setPassword(AlgorithmUtil.md5(password.getText().trim()).toUpperCase());
			}
			if (!StringUtil.isEmpty(email.getText())) {
				userInfo.setEmail(email.getText());
			}else{
				userInfo.setEmail("");
			}
			
			if (!StringUtil.isEmpty(userCode.getText())) {
				userInfo.setUserCode(userCode.getText());
			}
			if (!StringUtil.isEmpty(request.getParameter("orgId"))){
				userInfo.setOrgId(request.getParameter("orgId"));
			}
			
			
	        String[] roleIds = request.getParameterValues("r_hidden");
	        String[] groupIds = request.getParameterValues("g_hidden");
	        List<RUserRoleOrgGroup> relationList = new ArrayList<RUserRoleOrgGroup>();
	        if (roleIds != null)for (int i = 0; i < roleIds.length; i++) {
	            RUserRoleOrgGroup rel = new RUserRoleOrgGroup();
	            Role r = new Role();
	            r.setId(roleIds[i]);
	            OrgGroup g = new OrgGroup();
	            g.setOrgGroupId(groupIds[i]);
	            rel.setUser(userInfo);
	            rel.setOrgGroup(g);
	            rel.setRole(r);            
	            relationList.add(rel);
	        }
			
	        userService.saveOrUpdateUserRoleGroup(userInfo, relationList);
	        LogHelper.log(loginUser.getUser().getLoginName(), pageId, "���������û��ɹ�", ILogFinal.LOG_SUCCESS);
			redirect("userInfoList.jasmine");
		}catch(Exception e){
			e.printStackTrace();
			LogHelper.log(loginUser.getUser().getLoginName(), pageId, "���������û�ʧ��", ILogFinal.LOG_FAIL);
		}
		
	}

}