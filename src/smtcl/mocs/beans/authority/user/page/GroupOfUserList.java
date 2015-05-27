package smtcl.mocs.beans.authority.user.page;

import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.services.authority.ICommonService;
import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.*;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
import org.dreamwork.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/****
 * 
 * @author jun
 * 用户对应的数据组列表
 * 功能：新增、删除用户隶属的数据组
 */
public class GroupOfUserList extends UserControl {
    protected Pagging2 pagging;
    protected IDataCollection<RUserRoleOrgGroup> data;
    protected String groupCode,groupName,fieldName,userId;
    protected boolean groupCodeVisible=false, groupNameVisible=false;
    protected List<String> fieldList; 
    public void onPagePreload(Page page) throws EventException {

    }

    public void onPageLoad(Page page) throws EventException {
    	// 获取 动态 列表 字段名    	
    	if (!StringUtil.isEmpty(getFieldName())){
    		fieldList = Arrays.asList(getFieldName().split(","));
    		if(getFieldName().indexOf("groupCode")>=0){
    			groupCodeVisible=true; 
    		}
    		if(getFieldName().indexOf("groupName")>=0){
    			groupNameVisible=true;
    		}    		
    	}

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
     * 查询用户所属的数据组信息
     */    
    @SuppressWarnings("unchecked")
	private void loadData() {
    	ICommonService service = (ICommonService) ServiceFactory.getBean("commonService");
        int pageSize = pagging.getPageSize();
        int pageNo = 1 ;
		pagging.setPageNo(pageNo);
        String hql = "select new Map(a.id as orgUserId, a.orgGroup.code as groupCode, a.orgGroup.groupName as groupName) " +
        			 " from RUserOrgGroup a left join a.user left join a.orgGroup " +
        			 " where 1 = 1 "; 
        		
        List<Parameter> params = new ArrayList<Parameter>(2);
        if (!StringUtil.isEmpty(request.getParameter("userId"))){
        	hql+=" and a.user.userId=:userId ";// 当前用户
        	params.add(new Parameter("userId",Long.valueOf(request.getParameter("userId")),Operator.EQ));
        } else {
        	hql+=" and a.user.userId='none'"; // 新增用户界面，不存在
        }
        
        hql+=" order by a.orgGroup.id asc ";
//        data = service.executeQuery(pageNo, pageSize,hql, params);
    }

	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}





   
}