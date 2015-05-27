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
 * �û�ά���б�
 * ���ܣ������û���ɾ���û�����ѯ�û���Ϣ
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
	 * ����ҳ��ǰ�¼���
	 * 1.��� ��ѯ��ť������ɾ����ť����
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
	 * ҳ������¼���
	 * 1. ��ť��Ϣ��ʼ��
	 */
    public void onPageLoad(Page page) throws EventException {
    	statusList = new EnumStatus[]{EnumStatus.Valid,EnumStatus.Invalid};
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

    /**
     * ��ѯ�û���Ϣ
     */    
    @SuppressWarnings("unchecked")
	private void loadData() {
    	userList.loadPage(pagging.getPageNo(), pagging.getPageSize());
    }

    /**
     * 1. ɾ���û�
     * 2. ɾ�����û��󶨵Ĺ��ܽ�ɫ�����ݽ�ɫ�Ĺ�ϵ
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
            LogHelper.log(loginUser.getUser().getLoginName(), pageId, "ɾ���û�", ILogFinal.LOG_SUCCESS);
        } catch (Exception ex) {
            this.addStartupStatement ("error ('����', '����δ֪����,ɾ��ʧ��!');");
            ex.printStackTrace();
            LogHelper.log(loginUser.getUser().getLoginName(), pageId, "ɾ���û�", ILogFinal.LOG_FAIL);
        }
    }    

}