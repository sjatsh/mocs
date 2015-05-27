package smtcl.mocs.beans.authority.org.page;

import smtcl.mocs.pojos.authority.OrgGroup;
import smtcl.mocs.services.authority.IOrgGroupService;
import smtcl.mocs.utils.authority.IConsant;
import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Hidden;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.jasmine2.web.controls.UserControl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
import org.dreamwork.util.ListDataCollection;

import smtcl.mocs.beans.authority.secure.LoginUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wangli
 * Date: 12-10-22
 * Time: 下午4:22
 */

/**
 * ajax请求用法
 * param method 必填
 * param pageNo 可以为空
 * param pageSize 可以为空
 * param groupName 可以为空 权限组名称模糊查询
 * param userId    用户编码
 */
public class OrgGroupControl extends UserControl {
    protected IDataCollection<OrgGroup> data;
    protected boolean isList = false, manage;
    protected Hidden saveIds;
    protected LoginUser luser;

    private IOrgGroupService service;

    @Override
    public void onPageLoad(Page page) throws EventException {
        service = (IOrgGroupService) ServiceFactory.getBean("orgGroupService");
        luser = (LoginUser)session.getAttribute (IConsant.SESSION_LOGINUSER_KEY);
    }

    @Override
    public void onPageLoadCompleted(Page page) throws EventException {
        String method = request.getParameter("method");
        if (StringUtils.isNotEmpty(method)) { //ajax 请求
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            String groupName = request.getParameter("groupName");//数据组名称
            String userId = request.getParameter("userId");//数据组关联的用户
            String pageId = request.getParameter("pageId");
            String buttonId = request.getParameter("buttonId");
            List<Parameter> params = new ArrayList<Parameter>();
            if (StringUtils.isNotEmpty(groupName)) {
                params.add(new Parameter("groupName", "%"+groupName+"%", Operator.LIKE));
            }
            if (StringUtils.isNotEmpty(userId)) {
                params.add(new Parameter("userId", userId, Operator.EQ));
            }
            if (StringUtils.isNotEmpty(pageNo)&&StringUtils.isNotEmpty(pageSize)) {
                loadData(Integer.parseInt(pageNo), Integer.parseInt(pageSize), pageId, buttonId, params);
            } else loadData(pageId, buttonId, params);
        }
        try {
            dataBind();
        } catch (Throwable throwable) {
            //
        }
    }

    /**
     * 获取分页数据
     * 根据用户编码查询数据组参数设置new Parameter("userId", userid, Operater.EQ);
     * @param pageNo 第几条
     * @param pageSize 每页几条
     * @param params 参数列表
     */
    public void loadData(int pageNo, int pageSize, String pageId, String buttonId, Parameter... params) {
         data = service.queryOrgGroupList(luser.getUser().getUserId(), pageId, buttonId, pageNo, pageSize, params);
    }

    /**
     * 获取分页数据
     * 根据用户编码查询数据组参数设置new Parameter("userId", userid, Operater.EQ);
     * @param params 参数列表
     */
    public void loadData(String pageId, String buttonId, Parameter... params) {
    	if (data == null) data = new ListDataCollection<OrgGroup>();
         data.setData(service.queryOrgGroupList(luser.getUser().getUserId(), pageId, buttonId, params));
    }

    /**
     * 获取分页数据
     * 根据用户编码查询数据组参数设置new Parameter("userId", userid, Operater.EQ);
     * @param params 参数列表
     */
    public void loadData(int pageNo, int pageSize, String pageId, String buttonId, Collection<Parameter> params) {
        loadData(pageNo, pageSize, pageId, buttonId, params.toArray(new Parameter[params.size()]));
    }

    public void loadData(String pageId, String buttonId, Collection<Parameter> params) {
        loadData(pageId, buttonId, params.toArray(new Parameter[params.size()]));
    }

    public int getTotal () {
        return data.getTotalRows();
    }

    public boolean isList() {
        return isList;
    }

    public void setIsList(boolean list) {
        isList = list;
    }

    public String getSaveIds() {
        return saveIds.getText();
    }

    public void setSaveIds(String saveIds) {
        this.saveIds.setText(saveIds);
    }

    public boolean isManage() {
        return manage;
    }

    public void setManage(boolean manage) {
        this.manage = manage;
    }
}