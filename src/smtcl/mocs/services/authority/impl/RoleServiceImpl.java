package smtcl.mocs.services.authority.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.IDataCollection;
import org.dreamwork.util.StringUtil;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.dao.authority.ICommonDao;
import smtcl.mocs.pojos.authority.Button;
import smtcl.mocs.pojos.authority.Page;
import smtcl.mocs.pojos.authority.RRoleButton;
import smtcl.mocs.pojos.authority.RRolePage;
import smtcl.mocs.pojos.authority.RUserRoleOrgGroup;
import smtcl.mocs.pojos.authority.Role;
import smtcl.mocs.services.authority.IRoleService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.IConsant;

/**
 * ��ɫ��ص�service
 *
 * User: gjy
 * Date: 2012-10-15
 */
public class RoleServiceImpl extends GenericServiceSpringImpl<Role, String> implements IRoleService {

    private static final String DEL_ROLE = "delete from Role";
    private static final String DEL_R_USER_ROLE_ORGGROUP = "delete from RUserRoleOrgGroup";
    private static final String DEL_R_ROLE_BUTTON = "delete from RRoleButton";
    private static final String DEL_R_ROLE_PAGE = "delete from RRolePage";

	protected ICommonDao commonDao;
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public <T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters) {
        return commonDao.find (type, sort, parameters);
    }

    public <T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters) {
        return commonDao.find (type, sort, parameters);
    }
    /**
     * ���ݵ�ǰ�û���ȡȨ������?
     * @param locale 
     * @param userId 
     */
	public List<TreeNode> getTreeNodes(String userId, Locale locale) {
//		List<TreeNode> result = new ArrayList<TreeNode>();
//		List<Sort> sort = new ArrayList<Sort>();
//		sort.add(new Sort("appName", Direction.ASC));
//		@SuppressWarnings("unchecked")
//		List<Application> app_list = commonDao.find(Application.class,sort,Collections.EMPTY_LIST);
//		List<NodeData> res = new ArrayList<NodeData>();
//		NodeData root = new NodeData();
//		root.setNodeId("root");
//		root.setNodeName("Ȩ��");
//		root.setParentId(null);
//		res.add(root);
//		for (Application app : app_list) {
//			NodeData appNode = new NodeData();
//			appNode.setNodeId("app_"+app.getAppId());
//			appNode.setNodeName(app.getAppName());
//			appNode.setParentId("root");
//			//��ȡӦ��ϵͳ������ģ?
//			for (Module m : app.getModules()) {
//				NodeData moduleNode = new NodeData();
//				moduleNode.setNodeId("mod_"+m.getModuleId());
//				moduleNode.setNodeName(m.getModuleName());
//				moduleNode.setParentId("app_"+app.getAppId());
//				//��ȡģ��������ҳ?
//				for (Page p : m.getPages()) {
//					NodeData pageNode = new NodeData();
//					pageNode.setNodeId("pag_"+p.getPageId());
//					pageNode.setNodeName(p.getPageName());
//					pageNode.setParentId("mod_"+m.getModuleId());
//					res.add(pageNode);
//					//��ȡҳ�������а�?
//					for (Button b : p.getButtons()) {
//						NodeData buttonNode = new NodeData();
//						buttonNode.setNodeId("bnt_"+b.getButtonId());
//						buttonNode.setNodeName(b.getButtonName());
//						buttonNode.setParentId("pag_"+p.getPageId());
//						res.add(buttonNode);
//					}
//				}
//				res.add(moduleNode);
//			}
//			res.add(appNode);
//		}
//		try {
//			OrgTree o = new OrgTree();
//			o.reloadTree(res);
//			result = o.getRoots();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
        return null;//AuthorityUtil.queryAuthFunctionTree(userId, "", "", true, locale);
	}

	/**
	 * ���ݽ�ɫid��ȡ�ѷ����Ȩ��(������ť);
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<RRolePage> queryPermissionByRoleId(int pageNo, int pageSize,String roleId) {
		String hql = " from RRolePage a where 1=1 ";
        List<Parameter> params = new ArrayList<Parameter>(2);
        hql += " and a.role.id=:roleId ";
    	params.add(new Parameter("roleId",roleId , Operator.EQ));
    	hql += " order by a.id ";
    	IDataCollection<RRolePage> userList = this.getDao().executeQuery(pageNo,pageSize,hql, params);
    	queryButtonByRoleId(userList.getData(),roleId);
    	return userList;
	}

    public List<RRolePage> queryPermissionByRoleId(String roleId) {
        String hql = " from RRolePage a where 1=1 ";
        List<Parameter> params = new ArrayList<Parameter>(2);
        hql += " and a.role.id=:roleId ";
        params.add(new Parameter("roleId",roleId , Operator.EQ));
        hql += " order by a.id ";
        List<RRolePage> userList = this.getDao().executeQuery(hql, params);
        queryButtonByRoleId(userList,roleId);
        return userList;
    }

	/**
	 * ���ݽ�ɫid��ȡ��ť;
	 * @param list 
	 */
	@SuppressWarnings("unchecked")
	private List<RRolePage> queryButtonByRoleId(List<RRolePage> list, String roleId) {
//		String hql = " select a.button from RRoleButton a,RPageButton b where a.button = b.button and a.page = b.page  ";
		String hql = " select a.page.pageId||'"+IConsant.BTN_PREFIX+"'||a.button.buttonId from RRoleButton a where 1=1";
        List<Parameter> params = new ArrayList<Parameter>(2);
        hql += " and a.role.id=:roleId ";
    	params.add(new Parameter("roleId",roleId , Operator.EQ));
		List<String> list_bt = this.getDao().executeQuery(hql, params); //
		for(RRolePage rp:list){
			List<Button> b_set = this.getButtonByPage(rp.getPage());
            List<Button> b_set1 = new ArrayList<Button>();
			for(Button b1:b_set){
                Button btn = b1.clone();
                String b = rp.getPage().getPageId() + IConsant.BTN_PREFIX+b1.getButtonId();
				for(String b2:list_bt){
					if(b.equals(b2)){
						btn.setChecked(true);
						break;
					}
				}
                b_set1.add(btn);
			}
			rp.getPage().setButtons(b_set1);
		}
		return list;
	}

    public List<Page> getPageInfo(String ...pageId) {
        String hql = "from Page where 1=1";
        List<Page> pageList = null;
        if (pageId != null) {
            Parameter p = new Parameter("pageId", pageId, Operator.IN);
            hql += " and " + p;
            pageList = dao.executeQuery(hql, p);
        } else pageList = dao.executeQuery(hql);
        for(Page page : pageList){
            List<Button> b_set = this.getButtonByPage(page);
            page.setButtons(b_set);
        }
        return pageList;
    }

	/**
	 * ��ȡ����ǰҳ����صİ�ť
	 */
	@SuppressWarnings("unchecked")
	private List<Button> getButtonByPage(Page page) {
		String hql = " select a.button from RPageButton a where 1=1 "; 
        List<Parameter> params = new ArrayList<Parameter>(2);
        hql += " and a.page=:page ";
    	params.add(new Parameter("page",page , Operator.EQ));
		List<Button> list_bt = this.getDao().executeQuery(hql, params);
		return list_bt;
	}

	/**
	 * ���ݽ�ɫid��ȡ�������û���?
	 */
	public IDataCollection<RUserRoleOrgGroup> queryUsersByRoleId(int pageNo,int pageSize,String roleId, String userId) {
		String hql = " from RUserRole a  where 1=1 ";
        List<Parameter> params = new ArrayList<Parameter>(2);
        if(roleId != null){
        	hql += " and a.role.id=:roleId ";
        	params.add(new Parameter("roleId",roleId , Operator.EQ));
        }
        if(userId != null){
        	hql += " and a.user.userId=:userId ";
        	params.add(new Parameter("userId",userId , Operator.EQ));
        }
    	hql += " order by a.id ";
    	@SuppressWarnings("unchecked")
    	IDataCollection<RUserRoleOrgGroup> userList = this.getDao().executeQuery(pageNo, pageSize, hql, params);	
    	return userList;
	}
	
	/**
	 * ���ݽ�ɫid��ȡҳ��;
	 */
	public List<RRolePage> queryPagesByRoleId(String roleId,String pageId) {
		String hql = " from RRolePage a where 1=1 "; 
        List<Parameter> params = new ArrayList<Parameter>(2);
        if(roleId != null){
        	hql += " and a.role.id=:roleId ";
        	params.add(new Parameter("roleId",roleId , Operator.EQ));
        }
        if(!StringUtil.isEmpty(pageId)){
        	hql += " and a.page.pageId=:pageId ";
        	params.add(new Parameter("pageId",pageId , Operator.EQ));
        }
    	hql += " order by a.id ";
    	@SuppressWarnings("unchecked")
		List<RRolePage> pageList = this.getDao().executeQuery(hql, params);	
    	return pageList;
	}
	
	/**
	 * ���ݽ�ɫid��ȡ��ť;
	 * @param roleId 
	 * @param buttonId 
	 * @param pageId 
	 */
	public List<RRoleButton> queryButtonsByRoleId(String roleId,String buttonId, String pageId) {
		String hql = " from RRoleButton a where 1=1 "; 
        List<Parameter> params = new ArrayList<Parameter>(2);
        if(roleId != null){
        	hql += " and a.role.id=:roleId ";
        	params.add(new Parameter("roleId",roleId , Operator.EQ));
        }
        if(!StringUtil.isEmpty(buttonId)){
        	hql += " and a.button.buttonId=:buttonId ";
        	params.add(new Parameter("buttonId",buttonId , Operator.EQ));
        }
        if(!StringUtil.isEmpty(pageId)){
        	hql += " and a.page.pageId=:pageId ";
        	params.add(new Parameter("pageId",pageId , Operator.EQ));
        }
    	hql += " order by a.id ";
    	@SuppressWarnings("unchecked")
		List<RRoleButton> buttonList = this.getDao().executeQuery(hql, params);	
    	return buttonList;
	}

	/**
	 * ���õ�ǰ��ɫ�µ��û�;
	 */
//	@SuppressWarnings("unchecked")
	/*public boolean saveUsersByRoleId(Long roleId, String userId) {
		try{
		//�������?
		//deleteUsersByRoleId(roleId);
			
		//����û���ɫ��ϵ	
		if(!StringUtil.isEmpty(userId)){
			List<String> userIds = Arrays.asList(userId.split(","));
			for(String uId:userIds){
				//�Ѵ��ڵ��û�����?
				if(queryUsersByRoleId(1,15,roleId,new Long(uId)).getData().size()<1){
					RUserRoleOrgGroup ur = new RUserRoleOrgGroup();
					ur.setRole((Role) this.getDao().get(Role.class, roleId));
					ur.setUser((User) this.getDao().get(User.class, new Long(uId)));
					commonDao.save(ur);
				}
			}
		}
		}catch(Exception e){
			return false;
		}
		return true;
	}*/
	/**
	 * ɾ����ǰ��ɫ�µ��û�;
	 */
	/*public void deleteUsersByRoleId(Long roleId) {
		try{
			String hql = " delete from RUserRole a where 1=1 "; 
	        List<Parameter> params = new ArrayList<Parameter>(2);
	        hql += " and a.role.id=:roleId ";
	    	params.add(new Parameter("roleId",roleId , Operator.EQ));
	    	this.getDao().executeUpdate(hql, params);	
		}catch(Exception ex ){
			
		}
	}*/

	/**
	 * ���ݽ�ɫid���ð�ťȨ��;
	 */
	/*@SuppressWarnings("unchecked")
	public boolean saveButtonsByRoleId(Long roleId, String buttonIds) {
		try{
			//�������?
            deleteButtons(roleId, null, false);
			//������Ӱ�ť
			List<String> buttonList = Arrays.asList(buttonIds.split(","));
			for(String bId:buttonList){
				String[] src = bId.split("__");
                if (IConsant.BTN_VIEW.equals(src[2])) continue;
				RRoleButton rb = new RRoleButton();
				rb.setRole((Role) this.getDao().get(Role.class, roleId));
				rb.setButton((Button) this.getDao().get(Button.class, src[2]));
				rb.setPage((Page) this.getDao().get(Page.class, src[0]));
				commonDao.save(rb);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}*/

	
	/**
	 * ���ݽ�ɫid���ð�ťȨ�޺�ҳ��Ȩ?
	 */
	/*@SuppressWarnings("unchecked")
	@Override
	public boolean savePermissionByRoleId(Long roleId, String nodeId) {
		try{
			if(!StringUtil.isEmpty(nodeId)){
				List<String> nodeList = Arrays.asList(nodeId.split(","));
                String s = IConsant.BTN_PREFIX.replace("$", "\\$");
				for(String id:nodeList){
					//ֻ��Ҫ����ҳ��Ͱ�ť
					if(id.indexOf(IConsant.BTN_PREFIX)!=-1){	//�����ҳ��Ͱ�ťID
						String[] src = id.split(s);
                        String pageId = src[0].substring(IConsant.PAGE_PREFIX.length());
						//���ҳ���Ѵ����򲻱�?
						if(queryPagesByRoleId(roleId,pageId).size()<1){
							RRolePage rp = new RRolePage();
							rp.setRole(this.getDao().get(Role.class, roleId));
							rp.setPage(this.getDao().get(Page.class, pageId));
							commonDao.save(rp);
                            //����ҳ���ʱ��Ĭ�ϱ���鿴��?
                            RRoleButton rb = new RRoleButton();
                            rb.setRole(this.getDao().get(Role.class, roleId));
                            rb.setButton(this.getDao().get(Button.class, IConsant.BTN_VIEW));
                            rb.setPage(this.getDao().get(Page.class, pageId));
                            commonDao.save(rb);
						}
						//�����ť�Ѵ����򲻱�?
						if(!IConsant.BTN_VIEW.equals(src[1])&&queryButtonsByRoleId(roleId,pageId,src[1]).size()<1){
							RRoleButton rb = new RRoleButton();
							rb.setRole(this.getDao().get(Role.class, roleId));
							rb.setButton(this.getDao().get(Button.class, src[1]));
							rb.setPage(this.getDao().get(Page.class, pageId));
							commonDao.save(rb);
						}
					}
				}
			}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
	}*/

	/**
	 * ɾ����ǰ��ɫ�µİ�ť;
	 */
	@Override
	public void deleteButtonsByRoleId(String roleId, String pageId) {
		try{
			String hql = " delete from RRoleButton a where 1=1 "; 
	        List<Parameter> params = new ArrayList<Parameter>(2);
	        if(roleId!=null){
	        	hql += " and a.role.id=:roleId ";
		    	params.add(new Parameter("roleId",roleId , Operator.EQ));
	        }
	    	if(!StringUtil.isEmpty(pageId)){
	    		hql += " and a.button.page.pageId=:pageId ";
		    	params.add(new Parameter("pageId",pageId , Operator.EQ));
	    	}
	    	this.getDao().executeUpdate(hql, params);	
		}catch(Exception ex ){
			
		}
		
	}

    /**
     * ɾ����ť
     * @param roleId ��ɫid
     * @param pageId ҳ��id
     * @param delBtnView trueɾ���鿴��ť false��ɾ?
     */
    private void deleteButtons(Long roleId, String pageId, boolean delBtnView) {
        try{
            String hql = " delete from RRoleButton a where 1=1 ";
            List<Parameter> params = new ArrayList<Parameter>(2);
            if(roleId!=null){
                hql += " and a.role.id=:roleId ";
                params.add(new Parameter("roleId",roleId , Operator.EQ));
            }
            if(!StringUtil.isEmpty(pageId)){
                hql += " and a.button.page.pageId=:pageId ";
                params.add(new Parameter("pageId",pageId , Operator.EQ));
            }
            if (!delBtnView) {
                hql += " and a.button.buttonId <>:btnId";
                params.add(new Parameter("btnId", IConsant.BTN_VIEW, Operator.EQ));
            }
            this.getDao().executeUpdate(hql, params);
        }catch(Exception ex ){

        }
    }

	/**
     * ��ȡ��ɫ�б�
     */
	@Override
	public IDataCollection<Role> queryRolesByUser(String userId, int pageNo, int pageSize, String pageId, String buttonId, Collection<Parameter> parameters) {
	      /*String hql = " from Role r where 1=1 ";
	      for(Parameter p:params){
	    	  hql += " and " + p;
	      }
	      //���ݵ�ǰ��¼�˵���֯�ܹ����˽�ɫ
	      if(user!=null && !AuthorityUtil.isAdmin(user.getUserId())){
	    	  hql += " and r.creater.orgId in (select c.nodeId from RUserOrgGroup a,RGroupOrg b,FlatOrg c where " +
	    	  		 " c.ancestorId = b.org.orgId and b.orgGroup.orgGroupId = a.user.orgId and a.user=:user)";
	    	  params.add(new Parameter("user", user, Operator.EQ));
	      }
	      hql += " order by r.code ";*/
        /*String hql = "from Role where @auth@ ";
        List<Parameter> params = new ArrayList<Parameter>();
        for (Parameter p : parameters) {
            hql += " and " + p;
            if (p.operator == Operator.IS_NULL || p.operator == Operator.IS_NOT_NULL){}
            else params.add(p);
        }
        hql = AuthorityUtil.authHQL(hql, "creater.orgId", params, userId, pageId, buttonId);*/
        return AuthorityUtil.queryAuthRoleList(userId, pageId, buttonId, pageNo, pageSize, null, parameters, null);
	}

    @Override
    public void saveOrUpdate(Role role, List<RUserRoleOrgGroup> relList, List<RRoleButton> rbtnList, List<RRolePage> rpageList) {
        if (role.getId() == null) {
            dao.save(role);
        } else dao.update(role);
        Parameter param = new Parameter("roleId", role.getId(), Operator.EQ);
        dao.executeUpdate(DEL_R_USER_ROLE_ORGGROUP + " where flag is null and role.id = :roleId", param);
        dao.executeUpdate(DEL_R_ROLE_BUTTON + " where flag is null and role.id = :roleId", param);
        dao.executeUpdate(DEL_R_ROLE_PAGE + " where flag is null and role.id = :roleId", param);
        if (relList != null && relList.size() != 0) dao.save(RUserRoleOrgGroup.class, relList);
        if (rbtnList != null && rbtnList.size() != 0) dao.save(RRoleButton.class, rbtnList);
        if (rpageList != null && rpageList.size() != 0) dao.save(RRolePage.class, rpageList);
    }

    @Override
    public List<Role> delete(String... roleId) {
    	//��Ԫ��ϵ�����Ѱ󶨵Ľ�ɫ����ɾ��
    	Parameter param = new Parameter("roleId", roleId, Operator.IN);
        String hql1 = "(select role.id from RUserRoleOrgGroup where role.id in(:roleId))";
        String queryHql = " from Role r where r.id in "+hql1;
        List<Role> list = dao.executeQuery(queryHql, param);
      //  String hql1 = DEL_R_USER_ROLE_ORGGROUP + " where role.id in (:roleId)";
        String hql2 = DEL_R_ROLE_BUTTON + " where role.id in (:roleId) and role.id not in" +hql1;
        String hql3 = DEL_R_ROLE_PAGE + " where role.id in (:roleId) and role.id not in" +hql1;
        String hql4 = DEL_ROLE + " where id in(:roleId) and id not in" +hql1;

     //   dao.executeUpdate(hql1, param);
        dao.executeUpdate(hql2, param);
        dao.executeUpdate(hql3, param);
        dao.executeUpdate(hql4, param);
        return list;
    }
}