package smtcl.mocs.beans.authority.org.page;

import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.SessionHelper;
import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.events.IClickListener;
import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.jasmine2.web.controls.*;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.beans.authority.secure.LoginUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wangli
 * Date: 12-10-22
 * Time: ÏÂÎç6:18
 */
public class OrgGroupListPage extends Page {
    protected OrgGroupControl list;
    protected Pagging2 pagging;
    protected Button btnQuery;
    protected TextBox groupName;
    protected boolean manage;

    @Override
    public void onPagePreload(Page page) throws EventException {
        btnQuery.addClickListener(new IClickListener() {
            public void onClick(IWebControl iWebControl) throws EventException {
                pagging.setPageNo(1);
                loadData();
            }
        });
        registerClientScriptBlock(InternalJS.class, InternalJS.UTIL_JS);
        LoginUser luser = SessionHelper.loginUser(session);
        manage = AuthorityUtil.judge(luser.getUser().getUserId(), "passport.page.org.group.list", "button.manage");
        list.setManage(manage);
    }

    @Override
    public void onPageLoad(Page page) throws EventException {
//        list.setList(true);
    }

    @Override
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
        if (StringUtils.isNotEmpty(groupName.getText())) {
            params.add(new Parameter("groupName", "%"+groupName.getText()+"%", Operator.LIKE));
        }
        list.loadData(pagging.getPageNo(), pagging.getPageSize(), "passport.page.org.group.list", "button.view", params);
//        list.loadData(1, 10, params);
    }
}
