package smtcl.mocs.beans.authority.org.ajax;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.jasmine2.ajax.RequestMethod;
import org.dreamwork.jasmine2.annotation.AjaxMethod;
import org.dreamwork.jasmine2.annotation.AjaxParameter;
import org.dreamwork.jasmine2.annotation.AjaxService;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.beans.authority.secure.LoginUser;

import smtcl.mocs.beans.authority.cache.FlatManager;
import smtcl.mocs.services.authority.ITreeService;
import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.services.authority.IOnLineService;
import smtcl.mocs.beans.authority.cache.DeviceRelationMtInfo;
import smtcl.mocs.pojos.authority.Device;
import smtcl.mocs.pojos.authority.Organization;
import smtcl.mocs.pojos.authority.TypeNode;
import smtcl.mocs.common.authority.exception.AuthorityException;
import smtcl.mocs.common.authority.exception.OnLineDevException;
import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.common.authority.log.LogHelper;
import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.HelperUtil;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.JsonResponseResult;
import smtcl.mocs.utils.authority.SessionHelper;


/**
 * 
 * @author gaokun
 * @create Oct 12, 2012 10:38:54 AM
 */
@AjaxService("org")
public class OrgAjax {
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@AjaxMethod
	public String queryOrgTree( @AjaxParameter(name = "async") boolean async, 
								@AjaxParameter(name = "userId") String userId,
								@AjaxParameter(name = "pageId") String pageId) {
		JsonResponseResult jrr = new JsonResponseResult();
		try{
			List<TreeNode> list = null;
			if(async){
				list = AuthorityUtil.queryAuthOrgTree(userId, pageId, "button.view", null, false, null);
				
			}else{
				list = AuthorityUtil.queryAuthOrgTree(userId, pageId, "button.view", false);
			}
			jrr.setContent(list);
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg(SessionHelper.getText("app.non-date.info"));
		}
		return jrr.toJsonString();
	}
	
	@AjaxMethod
	public String queryOrgChild(
			@AjaxParameter(name = "nodeId") String nodeId,
			@AjaxParameter(name = "userId") String userId,
			@AjaxParameter(name = "exclude") String exclude,
			@AjaxParameter(name = "filter") String filter,
			@AjaxParameter(name = "pageId") String pageId) {
		JsonResponseResult jrr = new JsonResponseResult();
		try{
			List<TreeNode> list = AuthorityUtil.queryAuthOrgTree(userId, pageId, "button.view", nodeId, true, exclude, filter, false);
			for (TreeNode t : list) {
				Organization o = (Organization)t.getBindData();
				if(o != null){
					o.setManage(AuthorityUtil.judge(userId, o.getOrgId(), pageId, "button.manage"));
				}
			}
			jrr.setContent(list);
			jrr.attach("manage", AuthorityUtil.judge(userId, nodeId, pageId, "button.manage"));
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg(SessionHelper.getText("app.non-date.info"));
		}
		return jrr.toJsonString();
	}
	
	@AjaxMethod(method=RequestMethod.POST)
	public String saveOrg(  @AjaxParameter(name = "node", serialize = true) Organization node,
							@AjaxParameter(name = "userId") String userId,
							@AjaxParameter(name = "pageId") String pageId){
		JsonResponseResult jrr = new JsonResponseResult();
		LoginUser loginUser = SessionHelper.loginUser(HttpContext.current().getSession());
		String s = "";
		String nodeId = null;
		String oldPId = null;
		String newPId = null;
		try{
			ITreeService service = (ITreeService)ServiceFactory.getBean("treeService");
			String orgId = node.getOrgId(); 
			Organization o = null;
			boolean flat = false;
			if(StringUtils.isNotBlank(orgId)){//modify
				o = service.get(Organization.class, orgId);
				if(o == null) throw new AuthorityException(SessionHelper.getText("org.node.not.exist"));
				
				if(!o.getParentId().equals(node.getParentId())){
					s = String.format(" (judge node parent from[%s] to[%s])", o.getParentId(), node.getParentId() );
					flat = true;
					
					nodeId = o.getOrgId();
					oldPId = o.getParentId();
					newPId = node.getParentId();
				}
				o.setParentId(node.getParentId());
				o.setName(node.getName());
				o.setOrgType(service.get(TypeNode.class, node.getOrgType().getTypeId()));
				o.setSeq(node.getSeq());
				o.setCreateUser(userId);
				o.setMesNodeid(node.getMesNodeid());
				
				o.setContactPerson(node.getContactPerson());
				o.setContactTelephone(node.getContactTelephone());
				o.setAddress(node.getAddress());
				o.setDescription(node.getDescription());
				o.setState(node.getState());
				
				o.setDevice(node.getDevice());
				
				service.saveNode(loginUser.getUser(), o, flat);
			}else{//new
				o = new Organization();
				o.setParentId(node.getParentId());
				o.setName(node.getName());
				o.setOrgType(service.get(TypeNode.class, node.getOrgType().getTypeId()));
				o.setSeq(node.getSeq());
				o.setCreateUser(userId);
				o.setMesNodeid(node.getMesNodeid());
				
				o.setContactPerson(node.getContactPerson());
				o.setContactTelephone(node.getContactTelephone());
				o.setAddress(node.getAddress());
				o.setDescription(node.getDescription());
				o.setState(node.getState());
				
				o.setDevice(node.getDevice());
				
				service.saveNode(loginUser.getUser(), o, flat);
				
				flat = true;
				nodeId = o.getOrgId();
				oldPId = o.getParentId();
			}
			
			if(flat){//刷新;
				FlatManager.getInstance().add(nodeId, oldPId, newPId);
			}
				
			jrr.setContent(o);
			LogHelper.log(loginUser.getUser().getLoginName(), pageId, String.format("save org! name:[%s],type:[%s] %s", o.getName(), o.getOrgType(), s), ILogFinal.LOG_SUCCESS);
		}catch(Exception e){
			jrr.setSucc(false);
			if(e.getClass() == AuthorityException.class)
				jrr.setMsg(e.getMessage());
			else if(e.getClass() == OnLineDevException.class)
				jrr.setMsg(e.getMessage());
			else
				jrr.setMsg(SessionHelper.getText("org.save.error"));
			e.printStackTrace();
			LogHelper.log(loginUser.getUser().getLoginName(), pageId, String.format("save org error! name:[%s],type:[%s] %s", node.getName(), node.getOrgType(), s), ILogFinal.LOG_FAIL);
		}
		return jrr.toJsonString();
	}
	
	@AjaxMethod
	public String getOrg(  @AjaxParameter(name = "nodeId") String nodeId,
							@AjaxParameter(name = "userId") String userId){
		JsonResponseResult jrr = new JsonResponseResult();
		try{
			ITreeService service = (ITreeService)ServiceFactory.getBean("treeService");
			Organization o = new Organization();
			if(nodeId != null){
				o = service.getNode(nodeId);
				String ptxt = AuthorityUtil.getNodeTxt(o.getParentId());
				o.setLongName(ptxt);
			}
			jrr.setContent(o);
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg(SessionHelper.getText("app.non-date.info"));
		}
		return jrr.toJsonString();
	}
	
	@AjaxMethod
	public String delOrg(   @AjaxParameter(name = "nodeId") String nodeId,
							@AjaxParameter(name = "userId") String userId,
							@AjaxParameter(name = "pageId") String pageId){
		JsonResponseResult jrr = new JsonResponseResult();
		LoginUser loginUser = SessionHelper.loginUser(HttpContext.current().getSession());
		try{
			ITreeService service = (ITreeService)ServiceFactory.getBean("treeService");
			Organization o = service.get(Organization.class, nodeId);
			if(o == null) throw new AuthorityException(SessionHelper.getText("org.node.not.exist"));
			
			String h1 = " from User u where u.status <> :status and u.orgId in ( select fo.nodeId from FlatOrg fo where fo.ancestorId = :orgId ) ";
			List<Parameter> ps = new ArrayList<Parameter>();
	    	Parameter s = new Parameter("status", new Long(IConsant.INVALID), Operator.EQ);
	    	Parameter oid = new Parameter("orgId", o.getOrgId(), Operator.EQ);
	    	ps.add(s);
	    	ps.add(oid);
	    	List list_h1 = service.executeQuery(h1, ps);
	    	if(!list_h1.isEmpty()){
	    		throw new AuthorityException("该组织架构及子孙节点已与用户绑定!");
	    	}
	    	
	    	String h2 = " from RGroupOrg rgo where rgo.flag is null and rgo.org.orgId in ( select fo.nodeId from FlatOrg fo where fo.ancestorId = :orgId ) ";
			List<Parameter> ps2 = new ArrayList<Parameter>();
	    	ps2.add(oid);
	    	List list_h2 = service.executeQuery(h2, ps2);
	    	if(!list_h2.isEmpty()){
	    		throw new AuthorityException("该组织架构及子孙节点已与数据角色绑定!");
	    	}
	    	
	    	IOnLineService os = (IOnLineService)ServiceFactory.getBean("onlineService");
	    	
	    	//判断是否解除设备关联;
	    	if(IConsant.NODE_TYPE_DEV.equals(o.getOrgType().getTypeId())){
	    		Device dev = service.getDeviceByNodeId(o.getOrgId());
	    		if(dev != null){
		    		if(dev.getSymgEquId() != null){
		    			boolean f = false;
			    		try{
			    			f = os.unbindDev(loginUser.getUser().getUserId(), o.getMesNodeid(), o.getOrgId());
			    		}catch(OnLineDevException ode){
			    			throw new AuthorityException(ode.getMessage());
			    		}
			    		if(!f){
			    			throw new AuthorityException("不允许解除设备联机关联?");
			    		}
		    		}
	    		}
	    	}else{
	    		//判读节点的子孙中是否包含设备节点,如果存在则调用设备关系解?
	    		String h3 = "select 1 from Organization o, Device d where o.orgId = d.nodeId and o.orgType.typeId= '" + IConsant.NODE_TYPE_DEV + "' and d.symgEquId is not null and o.orgId in (select fo.nodeId from FlatOrg fo where fo.ancestorId= :nodeId) ";
	    		List list_h3 = service.executeQuery(h3, new Parameter[]{new Parameter("nodeId", nodeId, Operator.EQ)});
	    		if(!list_h3.isEmpty()){
	    			boolean f = false;
		    		try{
		    			f = os.unbindNode(loginUser.getUser().getUserId(), o.getOrgId());
		    		}catch(OnLineDevException ode){
		    			throw new AuthorityException(ode.getMessage());
		    		}
		    		if(!f){
		    			throw new AuthorityException("不允许解除节点下设备联机关系!");
		    		}
	    		}
	    	}
	    	
	    	service.executeUpdate("delete from Device o where o.nodeId in ( select fo.nodeId from FlatOrg fo where fo.ancestorId= :nodeId)", new Parameter[]{new Parameter("nodeId", nodeId, Operator.EQ)});
			
			service.executeUpdate("delete from Organization o where o.orgId in ( select fo.nodeId from FlatOrg fo where fo.ancestorId= :nodeId)", new Parameter[]{new Parameter("nodeId", nodeId, Operator.EQ)});
			
			//刷新;
			FlatManager.getInstance().add(nodeId, null, null);
			
			jrr.setContent(o);
			LogHelper.log(loginUser.getUser().getLoginName(), pageId, String.format("del org! id:[%s], name:[%s],type:[%s]", nodeId, o.getName(), o.getOrgType()), ILogFinal.LOG_SUCCESS);
		}catch(Exception e){
			jrr.setSucc(false);
			if(e.getClass() == AuthorityException.class)
				jrr.setMsg(e.getMessage());
			else
				jrr.setMsg(SessionHelper.getText("org.del.error"));
			e.printStackTrace();
			LogHelper.log(loginUser.getUser().getLoginName(), pageId, String.format("del org error! id[%s]", nodeId), ILogFinal.LOG_FAIL);
		}
		return jrr.toJsonString();
	}
	
//	@AjaxMethod
//	public String delOrgs(   @AjaxParameter(name = "nodeIds") String nodeIds,
//							@AjaxParameter(name = "userId") String userId){
//		JsonResponseResult jrr = new JsonResponseResult();
//		try{
//			String[] ns		= nodeIds.split(",");
//			ITreeService service = (ITreeService)ServiceFactory.getBean("treeService");
//			
//			service.executeUpdate("delete from Organization o where o.orgId in ( select fo.nodeId from FlatOrg fo where fo.ancestorId in (:nodeId))", new Parameter[]{new Parameter("nodeId", ns, Operator.EQ)});
//			
//			//刷新;
//			FlatManager.getInstance().add();
//		}catch(Exception e){
//			jrr.setSucc(false);
//			if(e.getClass() == AuthorityException.class)
//				jrr.setMsg(e.getMessage());
//			else
//				jrr.setMsg(SessionHelper.getText("org.del.error"));
//			e.printStackTrace();
//		}
//		return jrr.toJsonString();
//	}
	
	@AjaxMethod
	public String flatOrg(){
		JsonResponseResult jrr = new JsonResponseResult();
		try{
			HelperUtil.flatOrg(new String[]{});
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg(SessionHelper.getText("org.del.error"));
		}
		return jrr.toJsonString();
	}
	
	@AjaxMethod(method=RequestMethod.POST)
	public String queryMachineInfo(@AjaxParameter(name = "nodeId") String nodeId,
			@AjaxParameter(name = "mid") String mid,
			@AjaxParameter(name = "pwd") String pwd){
		JsonResponseResult jrr = new JsonResponseResult();
		IOnLineService s = (IOnLineService)ServiceFactory.getBean("onlineService");
		try{
			DeviceRelationMtInfo m = s.verifyDev(nodeId, mid, pwd);
			jrr.setContent(m);
		}catch(OnLineDevException o){
			jrr.setSucc(false);
			jrr.setMsg(o.getMessage());
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg("查询错误!");
		}
		return jrr.toJsonString();
	} 
	
	@AjaxMethod(method=RequestMethod.POST)
	public String symg_show(@AjaxParameter(name = "nodeId") String nodeId,
							@AjaxParameter(name = "mid") String mid){
		JsonResponseResult jrr = new JsonResponseResult();
		ICommonService s = (ICommonService)ServiceFactory.getBean("commonService");
		try{
			String h = " from Device where equSerialno =:equSerialno";
			List<Parameter> params = new ArrayList<Parameter>();
			params.add(new Parameter("equSerialno", mid, Operator.EQ));
			if(StringUtils.isNotBlank(nodeId)){
				h += " and nodeId != :nodeId ";
				params.add(new Parameter("nodeId", nodeId, Operator.EQ));
			}
			List l = s.executeQuery(h, params);
			String msg = "";
			if(!l.isEmpty()){
				msg = "SYMG机床序列号重复?";
			}
			jrr.setContent(msg);
		}catch(Exception e){
			jrr.setSucc(false);
			jrr.setMsg("查询错误!");
		}
		return jrr.toJsonString();
	} 
	
	
}