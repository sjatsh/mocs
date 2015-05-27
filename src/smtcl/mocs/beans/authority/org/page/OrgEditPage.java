package smtcl.mocs.beans.authority.org.page;

import java.util.List;

import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.beans.authority.secure.LoginUser;

import com.google.gson.Gson;
import smtcl.mocs.pojos.authority.TypeNode;
import smtcl.mocs.services.authority.IOrgGroupService;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * 组织结构编辑;
 * @author gaokun
 * @create Oct 26, 2012 10:55:36 AM
 */
public class OrgEditPage extends Page {
	

	protected String userId;
	
	protected String typeNodeArray;
	
	protected String pageId = "passport.page.org.edit";
	
    @Override
    public void onPagePreload(Page page) throws EventException {
    	LoginUser loginUser = SessionHelper.loginUser(session);
    	userId = loginUser.getUser().getUserId();
    	
    	IOrgGroupService server = (IOrgGroupService)ServiceFactory.getBean("orgGroupService");
    	List<TypeNode> tns = server.queryTypeNodes();
    	typeNodeArray = new Gson().toJson(tns);
    }
    
    @Override
    public void onPageLoadCompleted(Page page) throws EventException {
    	try{
    		dataBind();
    	}catch(Throwable e){
    		e.printStackTrace();
    	}
    }

}
