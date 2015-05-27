package smtcl.mocs.beans.authority.user.page;

import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.services.authority.IUserService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.EnumStatus;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.pojos.authority.User;

import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.events.IClickListener;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.jasmine2.web.controls.*;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.StringUtil;


/****
 * 
 * @author jun
 * 用户维护列表
 * 功能：新增用户、删除用户、查询用户信息
 */
public class UserInfoListPage extends Page {
    protected Pagging2 pagging;
    protected org.dreamwork.util.IDataCollection<User> data;
    protected Button btnQuery;
    protected TextBox loginName,nickName;
    protected DropdownList status;
    protected EnumStatus[] statusList;
    protected UserList userList;
    protected boolean btnVisible;
    protected Button btnDelete;
    protected Hidden items;
    
    /***
	 * 加载页面前事件：
	 * 1.添加 查询按钮监听、删除按钮监听
	 */
    public void onPagePreload(Page page) throws EventException {
        btnQuery.addClickListener(new IClickListener() {
            public void onClick(IWebControl sender) throws EventException {
            	pagging.setPageNo(1);
                loadData();
            }
        });       
        btnDelete.addClickListener(new IClickListener() {
            public void onClick(IWebControl sender) throws EventException {
                onDelete();
            }
        });
        LoginUser luser = SessionHelper.loginUser(session);
        btnVisible = AuthorityUtil.judge(luser.getUser().getUserId(), "passport.page.user.list", "button.manage");
        userList.setManage(btnVisible);
    }

    /***
	 * 页面加载事件：
	 * 1. 按钮信息初始化
	 */
    public void onPageLoad(Page page) throws EventException {
    	statusList = new EnumStatus[]{EnumStatus.Valid,EnumStatus.Invalid};
    }

    /**
	 * 页面加载完成事件：
	 * 1. 完成数据加载
	 * 2. 页面参数的绑定
	 */
    public void onPageLoadCompleted(Page page) throws EventException {
        loadData();
        try {
            dataBind();
        } catch (Throwable throwable) {
            throw new EventException(throwable);
        }
    }

    /**
     * 查询用户信息
     */    
    @SuppressWarnings("unchecked")
	private void loadData() {
    	userList.loadPage(pagging.getPageNo(), pagging.getPageSize());
    }

    /**
     * 1. 删除用户
     * 2. 删除与用户绑定的功能角色、数据角色的关系
     */
    private void onDelete() {
    	LoginUser loginUser = (LoginUser) session.getAttribute(IConsant.SESSION_LOGINUSER_KEY);
		String pageId = "passport.page.user.list";
        try {
            String text = items.getText();
            if (!StringUtil.isEmpty(text)) {
                String[] a = text.split(",");
                IUserService service = (IUserService) ServiceFactory.getBean("userService");
                service.cancelUserList(a);
            	redirect("userInfoList.jasmine");
            }
            LogHelper.log(loginUser.getUser().getLoginName(), pageId, "删除用户", ILogFinal.LOG_SUCCESS);
        } catch (Exception ex) {
            this.addStartupStatement ("error ('错误', '发生未知错误,删除失败!');");
            ex.printStackTrace();
            LogHelper.log(loginUser.getUser().getLoginName(), pageId, "删除用户", ILogFinal.LOG_FAIL);
        }
    }    

}