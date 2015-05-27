package smtcl.mocs.beans.authority.user.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.jasmine2.web.controls.Pagging2;
import org.dreamwork.jasmine2.web.controls.UserControl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
import org.dreamwork.util.StringUtil;

import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.services.authority.IUserService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.IConsant;

/*******************************************************************************
 * 
 * @author jun 用户列表控件 1. 获取用户显示列的权限 2. 显示当前用户有权限查看的用户列表的信息 3.
 *         依据查询的条件过滤有权限查看的用户列表信息
 */
public class UserList extends UserControl {
	protected Pagging2 pagging;
	protected IDataCollection<User> data;
	protected String userCode, loginName, nickName, status, fieldName, groupId, isExist;
	protected boolean loginNameVisible = false, nickNameVisible = false, emailVisible = false, statusVisible = false, modifyVisible = false,manage;
	protected List<String> fieldList;
	protected IUserService service;
	protected int pageNo, pageSize;
    
	

	public void onPagePreload(Page page) throws EventException {
		
	}

	/***************************************************************************
	 * 页面加载事件： 1. 获取用户列表显示列的权限 2. 查询参数包括：用户登录名、用户昵称、邮箱、状态
	 */
	public void onPageLoad(Page page) throws EventException {
		service = (IUserService) ServiceFactory.getBean("userService");
		// 获取 动态 列表 字段名
		if (StringUtil.isEmpty(fieldName)) {
			fieldName = request.getParameter("fieldName");
		}

		if (!StringUtil.isEmpty(fieldName)) {
			fieldList = Arrays.asList(fieldName.split(","));
			if (fieldName.indexOf("loginName") >= 0) {
				loginNameVisible = true;
			}
			if (fieldName.indexOf("nickName") >= 0) {
				nickNameVisible = true;
			}
			if (fieldName.indexOf("email") >= 0) {
				emailVisible = true;
			}
			if (fieldName.indexOf("status") >= 0) {
				statusVisible = true;
			}
			if (fieldName.indexOf("modify") >= 0) {
				modifyVisible = true;
			}
		}

	}

	/**
	 * 页面加载完成事件： 1. 完成数据加载 2. 页面参数的绑定
	 */
	public void onPageLoadCompleted(Page page) throws EventException {
		loadData();
		try {
			dataBind();
		} catch (Throwable throwable) {
			throw new EventException(throwable);
		}
	}

	/***************************************************************************
	 * 数据加载 1. 依据查询参数的条件，实现过滤查询。查询条件包括：员工号、登录名称、用户昵称、状态 2.
	 * 依据登录用户的查看权限，获取能显示的用户信息。
	 */
	@SuppressWarnings("unchecked")
	private void loadData() {
		if (!StringUtil.isEmpty(isExist))
			return;
		List<Parameter> params = new ArrayList<Parameter>();
		String hql = " from User where @auth@ and loginName != '" + User.ADMIN + "' ";
		// params.add(new Parameter("userId",new Long("100"),Operator.EQ));
		// 用户代码
		if (userCode != null && !StringUtil.isEmpty(request.getParameter(userCode))) {
			userCode = request.getParameter(userCode);
		} else {
			userCode = request.getParameter("userCode");
		}
		if (!StringUtil.isEmpty(userCode)) {
			hql += " and userCode like :userCode ";
			params.add(new Parameter("userCode", "%" + userCode + "%", Operator.LIKE));
		}

		// 登录名
		if (loginName != null && !StringUtil.isEmpty(request.getParameter(loginName))) {
			loginName = request.getParameter(loginName);
		} else {
			loginName = request.getParameter("loginName");
		}
		if (!StringUtil.isEmpty(loginName)) {
			hql += " and loginName like :loginName ";
			params.add(new Parameter("loginName", "%" + loginName + "%", Operator.LIKE));
		}

		// 昵称名
		if (nickName != null && !StringUtil.isEmpty(request.getParameter(nickName))) {
			nickName = request.getParameter(nickName);
		} else {
			nickName = request.getParameter("nickName");
		}
		if (!StringUtil.isEmpty(nickName)) {
			hql += " and nickName like :nickName ";
			params.add(new Parameter("nickName", "%" + nickName + "%", Operator.LIKE));
		}

		// 状态
		if (status != null && !StringUtil.isEmpty(request.getParameter(status))) {
			status = request.getParameter(status);
		} else {
			status = request.getParameter("status");
		}
		hql += " and state=:status ";
		if (!StringUtil.isEmpty(status) && !"-999".equals(status)) {
			params.add(new Parameter("status", Long.valueOf(status), Operator.EQ));
		} else {
			params.add(new Parameter("status", new Long(1), Operator.EQ));
		}

		LoginUser loginUser = (LoginUser) session.getAttribute(IConsant.SESSION_LOGINUSER_KEY);
		String pageId = "passport.page.user.list";
		if (loginUser != null) {
//			IUserService service = (IUserService) ServiceFactory.getBean("userService");
			data = AuthorityUtil.queryAuthUserList(loginUser.getUser().getUserId(), pageId, "button.view", pageNo, pageSize, params);
			for (int i = 0; i < data.getData().size(); i++) {
				User u = data.getData().get(i);
				data.getData().get(i).setManageVisible(AuthorityUtil.judge(loginUser.getUser().getUserId(), u.getOrgId(), pageId, "button.manage"));
			}
			LogHelper.log(loginUser.getUser().getLoginName(), pageId, "用户列表查询", ILogFinal.LOG_SUCCESS);
		}else{
			LogHelper.log(loginUser.getUser().getLoginName(), pageId, "用户列表查询", ILogFinal.LOG_FAIL);
		}
		
	}

    
	
	/**
	 * 获取分页数据 根据用户编码查询数据组参数设置
	 * 
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            每页几条
	 */
	public void loadPage(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		loadData();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public int getTotal() {
		return data.getTotalRows();
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

	public boolean isManage() {
		return manage;
	}

	public void setManage(boolean manage) {
		this.manage = manage;
	}

}