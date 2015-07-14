package smtcl.mocs.services.device.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.pojos.authority.Page;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.authority.IAuthorityService;
import smtcl.mocs.services.authority.IMenuService;
import smtcl.mocs.services.authority.impl.AuthorityServiceImpl;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;

/**
 * Ȩ�޽ӿ�ʵ��
 * @���� YuTao
 * @����ʱ�� 2012-11-14 ����11:02:14
 * @�޸��� songkaiang
 * @�޸����� 2015-1-29
 * @�޸�˵�� ���ʻ��˵��ͽڵ�
 * @�汾 V1.0
 */
public class AuthorizeServiceImpl extends GenericServiceSpringImpl<TUser, String> implements IAuthorizeService {	
    
	@Override
	public Boolean isAuthorize(String userID, String pageID) { //yyh Long ��ΪString
		 
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.hasPagePermission(userID, pageID); 	//yyh ȥ����toString()    	
	//	return RemoteProxyFactory.getInstance().isAuthorize(userID, pageID);		
	}
	
	@Override
	public Boolean isAuthorize(String userID, String pageID, String buttonID) {
		
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.hasButtonPermission(userID, pageID, buttonID); 	
		//return RemoteProxyFactory.getInstance().isAuthorize(userID, pageID, buttonID);		
	}

	@Override
	public Boolean isAuthorize(String userID, String pageID, String buttonID,
			String nodeID) {		
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.hasButtonPermissionWithNode(userID, pageID, buttonID, nodeID);
		//return RemoteProxyFactory.getInstance().isAuthorize(userID, pageID, buttonID, nodeID);	
	}

	@Override
	public List<String> getButtonsFunctionList(String userID, String pageID) {
		
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.getAuthorizedButtons(userID, pageID);
		//return RemoteProxyFactory.getInstance().getButtonsFunctionList(userID, pageID);
	}

	@Override
	public Boolean isSuperUser(String userID) {
		
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.isSuperUser(userID);
		//return RemoteProxyFactory.getInstance().isSuperUser(userID);
	}

	@Override
	public List<TreeNode> getAuthorizedNodeTree(String userID, String pageID) {
		
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.getAuthorizedNodeTree(userID, pageID);
		//return RemoteProxyFactory.getInstance().getAuthorizedNodeTree(userID, pageID);
	}

	@Override
	public List<String> getAuthorizedNodeList(String userID, String pageID,String buttonID) {
		
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.getAuthorizedNodeList(userID, pageID, buttonID);
		//return RemoteProxyFactory.getInstance().getAuthorizedNodeList(userID, pageID, buttonID);
	}

	@Override
	public List<TreeNode> getAuthorizedChildNodes(String userID, String pageID,String nodeID) {
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.getAuthorizedChildNodes(userID, pageID, nodeID);
	}
	
	@Override
	public List<Module> getMenu(String userID, String appId,String language) {
		IMenuService authority = (IMenuService) ServiceFactory.getBean("menuService");
		return authority.getMenu(userID, appId, language);
	}

	@Override
	public String getNodeLablePath(String nodeId) {
		IMenuService authority = (IMenuService) ServiceFactory.getBean("menuService");
		return authority.getNodeLabel(nodeId);
	}
	
	@Override
	public String getNodeLabel(String nodeId) {
		IMenuService authority = (IMenuService) ServiceFactory.getBean("menuService");
		return authority.getNodeLabel(nodeId);
	}
	
	/**
	 * �û���¼
	 */
	@Override
	public Map<String, Object> userLogin(String username, String userpwd) {
		Map<String, Object> loginRes = new HashMap<String, Object>();
		Parameter p_user_name = new Parameter("loginName", username,Operator.EQ);
		TUser user = this.get(p_user_name);
		if (null != user) 
			if (user.getPassword().equals(userpwd)) 
			    {  //���봫�����ľ���MD5
					loginRes.put("name", user.getLoginName());
					loginRes.put("userId", user.getUserId());
					loginRes.put("success", true);
					return loginRes;
				} 
		loginRes.put("success", false);
		
		return loginRes;
	}
	/**
	 * ��ȡ�˵�
     * @param session httpsession
	 * @param userId �û�ID
	 * @param appId Ӧ��ID
	 * @return �˵�������Ϣ
	 */
    @SuppressWarnings("unchecked")
	public List<Map<String,Object>> getMenu(HttpSession session,String userId, String appId){
		Locale locale = SessionHelper.getCurrentLocale (session);
		List<Module> userModule=this.getMenu(userId, appId, locale.toString());
        Collections.sort(userModule, new Comparator<Module>() {
            public int compare(Module o1, Module o2) {
                return o1.getSeq().compareTo(o2.getSeq());
            }
        });
        List<Map<String,Object>> rs=new ArrayList<Map<String,Object>>();
		for(Module mo:userModule){
			Map<String,Object> module=new HashMap<String,Object>();//����һ���˵�
			List<Map<String,Object>> childModules=new ArrayList<Map<String,Object>>();//��������ģ�鼯��  (�൱�ڶ����˵�)
			
			if("mocs.gcjm".equals(mo.getModuleId())) continue;  //���� ������ģ
			if("mocs.gwgl".equals(mo.getModuleId())) continue;  //���� ��λ����
			if("mocs.ckgl".equals(mo.getModuleId())) continue;  //�����ֿ����
			if("mocs.dtgl".equals(mo.getModuleId())) continue;  //������ͼ����
			module.put("label", mo.getLabel());
			module.put("url", mo.getUrl());
			module.put("remark",mo.getRemark());
			module.put("moduleId",mo.getModuleId());
			module.put("moduleName",mo.getModuleName() );
			boolean addpage=false;
			for(Page page:mo.getPages()){
				if(!page.getLabel().equals(mo.getLabel())){
					Map<String,Object> pagem=new HashMap<String,Object>();
					pagem.put("label",page.getLabel());
					pagem.put("pageId",page.getPageId());
					pagem.put("url",page.getUrl());
					pagem.put("pageName",page.getPageName());
					//pagem.put("module",page.getModule());
					pagem.put("buttons",page.getButtons());
					pagem.put("seq",page.getSeq());

					//�����豸���������ҳ��
                    if (Constants.P_V_PAGES.containsKey(page.getLabel())) {//ƥ���Ƿ�������ģ��
                        boolean status = true;
                        for (Map<String, Object> rec : childModules)//�������е�ģ��
                        {
                            if (rec.get("label").equals(Constants.P_V_PAGES.get(page.getLabel())))//���ģ���Ѵ��ڣ����ģ�����������
                            {
                                List<Map<String, Object>> childlist = (List<Map<String, Object>>) rec.get("pages");//��ȡ����ģ��
                                childlist.add(pagem);
                                rec.put("pages", childlist);//Ϊ����ģ�����ҳ��
                                status = false;
                                break;
                            }
                        }
                        if (status) {
                            Map<String, Object> childModule = new HashMap<String, Object>();//������� ��������ģ��
                            childModule.put("label", Constants.P_V_PAGES.get(page.getLabel())); //������ģ�鸴��lable
                            childModule.put("url", Constants.VIRTUALPAGES.get(Constants.P_V_PAGES.get(page.getLabel()).toString())); //������ģ�鸳ֵurl��ַ
                            List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
                            childlist.add(pagem);
                            childModule.put("pages", childlist);//Ϊ����ģ�����ҳ��
                            childModules.add(childModule);//�Ѵ�����ģ�����ģ�������
                        }
                    } else if(Constants.MOCS_KCGL_TMK.containsKey(page.getLabel())){
	                		//1�������еĶ���ģ��childModules �Ƿ��Ѿ��������������߳���
	               		 boolean ist=true;
	               		 for (Map<String, Object> rec : childModules)//�������е�ģ��
	                        {
	               			//����Ѿ������������������ϳ���
	                           if(Constants.MOCS_KCGL_TMK.get(page.getLabel()).equals(rec.get("label"))){
	                           	 List<Map<String, Object>> childlist = (List<Map<String, Object>>) rec.get("pages");//��ȡ����ģ��
	                           	 if(null==childlist)
	                           		childlist = new ArrayList<Map<String,Object>>();
	                                childlist.add(pagem);
	                                rec.put("pages", childlist);//Ϊ����ģ�����ҳ��
	                                ist=false;
	                           }
	                        }
	               		//��������������������ϳ���
	               		 if(ist){
	               			for(Page page2:mo.getPages()){
	               				//�ҵ�������� �������ϳ���
	               				if(page2.getLabel().equals(Constants.MOCS_KCGL_TMK.get(page2.getLabel()))){
	               					Map<String, Object> childModule = new HashMap<String, Object>();//��������ģ��
	                                childModule.put("label",page2.getLabel()); //������ģ�鸳ֵ
	                                childModule.put("url",page2.getUrl()); 
	                                childModule.put("pageId",page2.getPageId());
	                                childModule.put("pageName",page2.getPageName());
	                                childModule.put("buttons",page2.getButtons());
	                                childModule.put("seq",page2.getSeq());
	                                List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
	                                childlist.add(pagem);
	                                childModule.put("pages", childlist);//Ϊ����ģ�����ҳ��
	                                childModules.add(childModule);//�Ѵ�����ģ�����ģ�������
	               				}
	               			}
	               			ist=true;
	               		 }
	               	}else{
	               		childModules.add(pagem);//��Ӳ���������ģ�������ҳ��
	               	}
                }else{
					addpage=true;
				}
			}
			System.out.println(addpage);
			System.out.println(mo.getPages().size());
            if (!addpage || mo.getPages().size() != 1) {
                module.put("pages", childModules);//
            }

            rs.add(module);//���һ���ڵ�
		}
		return rs; 
	}
}
