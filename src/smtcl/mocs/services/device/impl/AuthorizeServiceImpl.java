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
 * 权限接口实现
 * @作者 YuTao
 * @创建时间 2012-11-14 上午11:02:14
 * @修改者 songkaiang
 * @修改日期 2015-1-29
 * @修改说明 国际化菜单和节点
 * @版本 V1.0
 */
public class AuthorizeServiceImpl extends GenericServiceSpringImpl<TUser, String> implements IAuthorizeService {	
    
	@Override
	public Boolean isAuthorize(String userID, String pageID) { //yyh Long 改为String
		 
		IAuthorityService authority=new AuthorityServiceImpl();
		return authority.hasPagePermission(userID, pageID); 	//yyh 去掉了toString()    	
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
	 * 用户登录
	 */
	@Override
	public Map<String, Object> userLogin(String username, String userpwd) {
		Map<String, Object> loginRes = new HashMap<String, Object>();
		Parameter p_user_name = new Parameter("loginName", username,Operator.EQ);
		TUser user = this.get(p_user_name);
		if (null != user) 
			if (user.getPassword().equals(userpwd)) 
			    {  //密码传进来的就是MD5
					loginRes.put("name", user.getLoginName());
					loginRes.put("userId", user.getUserId());
					loginRes.put("success", true);
					return loginRes;
				} 
		loginRes.put("success", false);
		
		return loginRes;
	}
	/**
	 * 获取菜单
     * @param session httpsession
	 * @param userId 用户ID
	 * @param appId 应用ID
	 * @return 菜单集合信息
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
			Map<String,Object> module=new HashMap<String,Object>();//创建一级菜单
			List<Map<String,Object>> childModules=new ArrayList<Map<String,Object>>();//创建虚拟模块集合  (相当于二级菜单)
			
			if("mocs.gcjm".equals(mo.getModuleId())) continue;  //跳过 工厂建模
			if("mocs.gwgl".equals(mo.getModuleId())) continue;  //跳过 工位管理
			if("mocs.ckgl".equals(mo.getModuleId())) continue;  //跳过仓库管理
			if("mocs.dtgl".equals(mo.getModuleId())) continue;  //跳过地图管理
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

					//处理设备管理的三级页面
                    if (Constants.P_V_PAGES.containsKey(page.getLabel())) {//匹配是否有虚拟模块
                        boolean status = true;
                        for (Map<String, Object> rec : childModules)//遍历已有的模块
                        {
                            if (rec.get("label").equals(Constants.P_V_PAGES.get(page.getLabel())))//如果模块已存在，则给模块添加子数据
                            {
                                List<Map<String, Object>> childlist = (List<Map<String, Object>>) rec.get("pages");//获取虚拟模块
                                childlist.add(pagem);
                                rec.put("pages", childlist);//为虚拟模块添加页面
                                status = false;
                                break;
                            }
                        }
                        if (status) {
                            Map<String, Object> childModule = new HashMap<String, Object>();//如果有则 创建虚拟模块
                            childModule.put("label", Constants.P_V_PAGES.get(page.getLabel())); //给虚拟模块复制lable
                            childModule.put("url", Constants.VIRTUALPAGES.get(Constants.P_V_PAGES.get(page.getLabel()).toString())); //给虚拟模块赋值url地址
                            List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
                            childlist.add(pagem);
                            childModule.put("pages", childlist);//为虚拟模块添加页面
                            childModules.add(childModule);//把创建的模块存入模块表里面
                        }
                    } else if(Constants.MOCS_KCGL_TMK.containsKey(page.getLabel())){
	                		//1遍历已有的二级模块childModules 是否已经包含物料入库或者出库
	               		 boolean ist=true;
	               		 for (Map<String, Object> rec : childModules)//遍历已有的模块
	                        {
	               			//如果已经存在物料入库或者物料出库
	                           if(Constants.MOCS_KCGL_TMK.get(page.getLabel()).equals(rec.get("label"))){
	                           	 List<Map<String, Object>> childlist = (List<Map<String, Object>>) rec.get("pages");//获取虚拟模块
	                           	 if(null==childlist)
	                           		childlist = new ArrayList<Map<String,Object>>();
	                                childlist.add(pagem);
	                                rec.put("pages", childlist);//为虚拟模块添加页面
	                                ist=false;
	                           }
	                        }
	               		//如果不存在物料入库和物料出库
	               		 if(ist){
	               			for(Page page2:mo.getPages()){
	               				//找到物料入库 或者物料出库
	               				if(page2.getLabel().equals(Constants.MOCS_KCGL_TMK.get(page2.getLabel()))){
	               					Map<String, Object> childModule = new HashMap<String, Object>();//创建二级模块
	                                childModule.put("label",page2.getLabel()); //给二级模块赋值
	                                childModule.put("url",page2.getUrl()); 
	                                childModule.put("pageId",page2.getPageId());
	                                childModule.put("pageName",page2.getPageName());
	                                childModule.put("buttons",page2.getButtons());
	                                childModule.put("seq",page2.getSeq());
	                                List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
	                                childlist.add(pagem);
	                                childModule.put("pages", childlist);//为虚拟模块添加页面
	                                childModules.add(childModule);//把创建的模块存入模块表里面
	               				}
	               			}
	               			ist=true;
	               		 }
	               	}else{
	               		childModules.add(pagem);//添加不属于虚拟模块的物理页面
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

            rs.add(module);//添加一级节点
		}
		return rs; 
	}
}
