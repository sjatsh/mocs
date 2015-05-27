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

import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.services.authority.ICommonService;

/****
 * 
 * @author jun
 * �û���Ӧ�Ľ�ɫ�б�
 * ���ܣ�������ɾ���û������Ľ�ɫ
 */
public class RoleOfUserList extends UserControl {
    protected Pagging2 pagging;
    protected IDataCollection<RUserRoleOrgGroup> data;
    protected String roleCode,roleName,fieldName,userId;
    protected boolean roleCodeVisible=false, roleNameVisible=false;
    protected List<String> fieldList; 
    public void onPagePreload(Page page) throws EventException {

    }

    public void onPageLoad(Page page) throws EventException {
    	// ��ȡ ��̬ �б� �ֶ���    	
    	if (!StringUtil.isEmpty(getFieldName())){
    		fieldList = Arrays.asList(getFieldName().split(","));
    		if(getFieldName().indexOf("roleCode")>=0){
    			roleCodeVisible=true; 
    		}
    		if(getFieldName().indexOf("roleName")>=0){
    			roleNameVisible=true;
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
     * ��ѯ�û������Ľ�ɫ��Ϣ
     */    
    @SuppressWarnings("unchecked")
	private void loadData() {
    	ICommonService service = (ICommonService) ServiceFactory.getBean("commonService");
        int pageSize = pagging.getPageSize();
        int pageNo = 1 ;
		pagging.setPageNo(pageNo);
        String hql = "select new Map(a.id as roleUserId, a.role.id as roleCode, a.role.name as roleName) " +
        			 " from RUserRole a left join a.user left join a.role " +
        			 " where 1 = 1 "; 
        		
        List<Parameter> params = new ArrayList<Parameter>(2);
        if (!StringUtil.isEmpty(request.getParameter("userId"))){
        	hql+=" and a.user.userId=:userId ";// ��ǰ�û�
        	params.add(new Parameter("userId",new Long(request.getParameter("userId")),Operator.EQ));
        } else {
        	hql+=" and a.user.userId='none'"; // �����û����棬������
        }
        
        hql+=" order by a.role.id asc ";
//        data = service.executeQuery(pageNo, pageSize,hql, params);
    }

	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}





   
}