package smtcl.mocs.beans.authority.org.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dreamwork.jasmine2.events.EventException;
import org.dreamwork.jasmine2.web.controls.Page;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.beans.authority.secure.LoginUser;

import com.google.gson.Gson;
import smtcl.mocs.pojos.authority.TypeNode;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.services.authority.IOrgGroupService;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * 组织结构显示;
 * @author gaokun
 * @create Oct 26, 2012 10:55:36 AM
 */
public class OrgListPage extends Page {
	
	protected String userId;
	
	protected String typeNodeArray;
	
	protected String pageId = "passport.page.org";	
	
    @Override
    public void onPagePreload(Page page) throws EventException {
    	LoginUser loginUser = SessionHelper.loginUser(session);
    	userId = loginUser.getUser().getUserId();
    	
    	IOrgGroupService server = (IOrgGroupService)ServiceFactory.getBean("orgGroupService");
    	List<TypeNode> tns = server.queryTypeNodes();  

    	List<TEquipmenttypeInfo> tti = server.queryTEquipmenttypeInfo();
    	List<TEquipmenttypeInfo> teti = new ArrayList<TEquipmenttypeInfo>();
        for(TEquipmenttypeInfo t :tti){//gson转化为字符的时候只能转化对象，不能转化对象的与之相关联的数据
        	TEquipmenttypeInfo tt = new TEquipmenttypeInfo();
        	tt.setId(t.getId());
        	tt.setEquipmentType(t.getEquipmentType());
        	tt.setDescription(t.getDescription());
        	teti.add(tt);
        }   	
        
    	Map m = new HashMap();
    	m.put("tns", tns);
    	m.put("tti", teti);   
    	typeNodeArray = new Gson().toJson(m);    	
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
