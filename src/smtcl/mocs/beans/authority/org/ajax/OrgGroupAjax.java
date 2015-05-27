package smtcl.mocs.beans.authority.org.ajax;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.annotation.AjaxMethod;
import org.dreamwork.jasmine2.annotation.AjaxParameter;
import org.dreamwork.jasmine2.annotation.AjaxService;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.json.JSONObject;

import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.pojos.authority.OrgGroup;
import smtcl.mocs.services.authority.IOrgGroupService;
import smtcl.mocs.services.authority.ITreeService;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * Created with IntelliJ IDEA.
 * User: wangli
 * Date: 12-10-24
 * Time: ÏÂÎç6:20
 */
@AjaxService("orgGroup")
public class OrgGroupAjax {

    @AjaxMethod
    public String delGroupByIds(
            @AjaxParameter(name = "groupIds") String groupIds) {
        IOrgGroupService service  = (IOrgGroupService) ServiceFactory.getBean("orgGroupService");
        LoginUser luser = SessionHelper.loginUser(HttpContext.current().getSession());
        String []idsStr = groupIds.split("@_@");
        /*Long []ids = new Long[idsStr.length];
        for (int i = 0; i < idsStr.length; i++) {
            ids[i] = Long.parseLong(idsStr[i]);
        }*/
        JSONObject result = new JSONObject();
        try{
            List<OrgGroup> list = service.delete(idsStr);
            StringBuilder buf = new StringBuilder();
            for(int i=0; i<list.size(); i++) {
                if (i !=0 && i % 3 == 0) buf.append("\\n");
                buf.append(list.get(i).getGroupName()).append("\t");
            }
            result.put("data", buf.toString());
            result.put("msg", true);
            LogHelper.log(luser.getUser().getLoginName(), "passport.page.org.group.list", "delete.success", ILogFinal.LOG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", false);
            LogHelper.log(luser.getUser().getLoginName(), "passport.page.org.group.list", "delete.fail", ILogFinal.LOG_FAIL);
        }
        return "(" + result + ")";
    }

    @AjaxMethod
    public Boolean checkCode(@AjaxParameter(name = "code") String code) {
        IOrgGroupService service  = (IOrgGroupService) ServiceFactory.getBean("orgGroupService");
        try {
            return service.find(new Parameter("code", code, Operator.EQ)).size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @AjaxMethod
    public Boolean checkName(@AjaxParameter(name = "name") String name,
                             @AjaxParameter(name = "groupId") String groupId) {
        IOrgGroupService service  = (IOrgGroupService) ServiceFactory.getBean("orgGroupService");
        try {
            List<Parameter> params = new ArrayList<Parameter>();
            params.add(new Parameter("groupName", name, Operator.EQ));
            if (StringUtils.isNotEmpty(groupId)) params.add(new Parameter("orgGroupId", groupId, Operator.NE));
            return service.find(params).size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @AjaxMethod
    public String getOrg(@AjaxParameter(name = "nodeIds")String []nodeIds) {
        JSONObject result = new JSONObject();
        ITreeService service = (ITreeService)ServiceFactory.getBean("treeService");
        List list = service.find(new Parameter("orgId", nodeIds, Operator.IN));
        result.put("data", list);
        return "("+result.toString()+")";
    }
}
