package smtcl.mocs.beans.authority.user.page;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.events.IClickListener;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.jasmine2.web.controls.*;

import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.beans.authority.role.page.RoleListControl;


/****
 * 
 * @author jun
 * �û���ӽ�ɫ�б�
 * ���ܣ��û���������Ľ�ɫ��Ϣ
 */
public class RoleOfUserDetailPage extends Page {   
    protected Button btnSave;
    protected RoleListControl list1;
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
    }

    public void onPageLoad(Page page) throws EventException {
    	
    }

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
    	
    }
    
    /**
     * �����û����ɫ�Ķ�Ӧ��ϵ
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private void onSave() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        RUserRoleOrgGroup roleUser = new RUserRoleOrgGroup();
        
        request.getParameterValues("roleId");
        redirect("userInfoDetail.jasmine");
    }
   
}