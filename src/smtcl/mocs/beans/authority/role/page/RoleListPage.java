package smtcl.mocs.beans.authority.role.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.events.IClickListener;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.jasmine2.web.controls.Button;
import org.dreamwork.jasmine2.web.controls.InternalJS;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.jasmine2.web.controls.Pagging2;
import org.dreamwork.jasmine2.web.controls.TextBox;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.IConsant;

/**
 * ╫ги╚ап╠М
 */
public class RoleListPage extends org.dreamwork.jasmine2.web.controls.Page {
	protected RoleListControl roleList;
	protected Pagging2 pagging;
    protected Button btnQuery;
    protected TextBox roleName;
    protected boolean hasManageRight,hasViewRight;
    private LoginUser loginUser =  null;
    
    public void onPagePreload(Page page) throws EventException {
    	loginUser =  (LoginUser) session.getAttribute(IConsant.SESSION_LOGINUSER_KEY);
    	
    	hasManageRight = AuthorityUtil.judge(loginUser.getUser().getUserId(), "passport.page.role.list", "button.manage");
        btnQuery.addClickListener(new IClickListener() {
            public void onClick(IWebControl iWebControl) throws EventException {
                loadData();
            }
        });
        registerClientScriptBlock(InternalJS.class, InternalJS.UTIL_JS);
    }

    public void onPageLoad(Page page) throws EventException {
    }

    public void onPageLoadCompleted(Page page) throws EventException {
    	loadData();
        try {
            dataBind();
        } catch (Throwable throwable) {
            //
        }
    }
    
    private void loadData() {
        List<Parameter> params = new ArrayList<Parameter>();
        if (roleName!=null&&StringUtils.isNotEmpty(roleName.getText())) {
            params.add(new Parameter("name", "%"+roleName.getText()+"%", Operator.LIKE));
        }
        try {
        	roleList.loadData(pagging.getPageNo(), pagging.getPageSize(), "passport.page.role.list", "button.view", params);
        	LogHelper.log(loginUser.getUser().getLoginName(), "passport.page.role.list", "role list load success", ILogFinal.LOG_SUCCESS);
		} catch (Exception e) {
			LogHelper.log(loginUser.getUser().getLoginName(), "passport.page.role.list", "role list load failed", ILogFinal.LOG_FAIL);
			e.printStackTrace();
		}
        
    }
}