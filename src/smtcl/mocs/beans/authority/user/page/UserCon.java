package smtcl.mocs.beans.authority.user.page;

import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.services.authority.IUserService;
import smtcl.mocs.utils.authority.IConsant;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Hidden;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.jasmine2.web.controls.UserControl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
import org.dreamwork.util.ListDataCollection;
import org.dreamwork.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wangli
 * Date: 12-10-30
 * Time: 下午6:26
 */

public class UserCon extends UserControl {
    protected IDataCollection<User> data;
    protected String fieldName;
    protected boolean loginNameVisible=false, nickNameVisible=false, emailVisible=false, statusVisible=false, modifyVisible=false;
    protected List<String> fieldList;
    protected IUserService service;
    protected int pageNo,pageSize;
    protected Hidden saveUserIds;
    protected LoginUser luser;


    public void onPagePreload(Page page) throws EventException {
        service = (IUserService) ServiceFactory.getBean("userService");
        luser = (LoginUser)session.getAttribute (IConsant.SESSION_LOGINUSER_KEY);
    }

    public void onPageLoad(Page page) throws EventException {
    	// 获取 动态 列表 字段名
    	if (StringUtil.isEmpty(fieldName)){
    		fieldName = request.getParameter("fieldName");
    	}
    	if (!StringUtil.isEmpty(fieldName)){
    		fieldList = Arrays.asList(fieldName.split(","));
    		if(fieldName.indexOf("loginName")>=0){
    			loginNameVisible=true;
    		}
    		if(fieldName.indexOf("nickName")>=0){
    			nickNameVisible=true;
    		}
    		if(fieldName.indexOf("email")>=0){
    			emailVisible=true;
    		}
    		if(fieldName.indexOf("status")>=0){
    			statusVisible=true;
    		}
    		if(fieldName.indexOf("modify")>=0){
    			modifyVisible=true;
    		}
    	}
    }

    public void onPageLoadCompleted(Page page) throws EventException {
        if (!StringUtil.isEmpty(request.getParameter("method"))) {
            List<Parameter> params = new ArrayList<Parameter>();
            String loginName = request.getParameter("loginName");
            params.add(new Parameter("state", IConsant.VALID, Operator.EQ));
            if (!StringUtil.isEmpty(loginName)) {
                params.add(new Parameter("loginName", "%"+loginName+"%", Operator.LIKE));
            }
            String userIds = request.getParameter("userIds");
            if (!StringUtil.isEmpty(userIds)) {
                String op = request.getParameter("userIdOp");
                String [] userId = userIds.split(",");
                long [] userIdLs = new long[userId.length];
                for (int i = 0; i < userId.length; i++) {
                    userIdLs[i] = Long.parseLong(userId[i]);
                }
                params.add(new Parameter("userId", userIdLs, op == null? Operator.NOT_IN : Operator.IN));
            }
            loadData(params);
        }

        try {
            dataBind();
        } catch (Throwable throwable) {
            throw new EventException(throwable);
        }
    }

    public void loadData(List<Parameter> params) {
        data = new ListDataCollection<User>();
//        data.setData(service.queryAuthUserList(luser.getUser().getUserId(), params));
    }

    public void loadData(int pageNo, int pageSize, List<Parameter> params) {
        data = service.find(pageNo, pageSize, params);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSaveUserIds() {
        return saveUserIds.getText();
    }

    public void setSaveUserIds(String saveUserIds) {
        this.saveUserIds.setText(saveUserIds);
    }

    public void setSaveUserIds() {
        if (data != null && data.getData() != null) {
            List<User> list = data.getData();
            StringBuffer buf = new StringBuffer();
            for (User u : list) buf.append(u.getUserId()).append(",");
            if (buf.length() != 0)setSaveUserIds(buf.substring(0, buf.length()-1));
        }
    }
}
