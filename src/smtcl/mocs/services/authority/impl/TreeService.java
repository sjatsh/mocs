package smtcl.mocs.services.authority.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.authority.ITreeService;
import smtcl.mocs.beans.authority.cache.NodeData;
import smtcl.mocs.pojos.authority.FlatOrgTemp;
import smtcl.mocs.services.authority.IOnLineService;
import smtcl.mocs.pojos.authority.Device;
import smtcl.mocs.pojos.authority.DeviceLog;
import smtcl.mocs.pojos.authority.Organization;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.common.authority.exception.OnLineDevException;
import smtcl.mocs.beans.authority.secure.LoginUser;
import smtcl.mocs.utils.authority.IBusConsant;
import smtcl.mocs.utils.authority.IConsant;
import smtcl.mocs.utils.authority.SessionHelper;

/**
 * 
 * @author gaokun
 * @create Jul 11, 2011 5:36:52 PM
 * 扁平化组织结构树;
 */
public class TreeService extends GenericServiceSpringImpl<Object, String> implements ITreeService {
	
	/**
	 * TreeService 日志类;
	 */
	private final static Logger logger = Logger.getLogger(TreeService.class);

	/* (non-Javadoc)
	 * @see com.swg.authority.cache.ITreeService#queryOrgList()
	 */
	public List<NodeData> queryOrgList(Parameter... parameters) {
//		List<NodeData> result = new ArrayList<NodeData>();
        List<Parameter> params = new ArrayList<Parameter>();
		String hql = " from Organization where show = " + IConsant.VALID;
        for (Parameter param : parameters) {
            if ("orgGroupId".equals(param.name)) {
                hql += " and orgId in (select gor.org.orgId from RGroupOrg gor where gor.orgGroup.orgGroupId = :orgGroupId)";
                params.add(param);
            } else if (param.operator == Operator.IS_NULL || param.operator == Operator.IS_NOT_NULL) {
                hql += param;
            } else {
                hql += " and " + param;
                params.add(param);
            }
        }
		List<Organization> list= this.getDao().executeQuery(hql, params);
		return org2NodeData(list);
	}

    public List<NodeData> org2NodeData(List<Organization> list) {
        List<NodeData> result = new ArrayList<NodeData>();
        for (Organization org : list) {
            NodeData nd = new NodeData();
            nd.setNodeId(org.getOrgId());
            nd.setNodeName(org.getName());
            nd.setParentId(org.getParentId() == null ? null : org.getParentId() + "");
            nd.setSeq(org.getSeq());
            result.add(nd);
        }
        return result;
    }
	
	public List<Organization> queryOrgChildren(String orgId) {
		String hql = " from Organization where show = " + IConsant.VALID;
		
		List<Parameter> params = new ArrayList<Parameter>();
		if(orgId == null){
			hql += " and parentId is null";
		}else{
			hql += " and parentId = :parentId";
			params.add(new Parameter("parentId", orgId, Operator.EQ));
		}
		
		hql += " order by seq ";
		List<Organization> result= this.getDao().executeQuery(hql, params);
		return result;
	}

    @Override
    public List<Object> find(Parameter... parameters) {
        List<Parameter> params = new ArrayList<Parameter>();
        String hql = "select new map(orgId as orgId, name as name) from Organization where show = " + IConsant.VALID;
        for (Parameter param : parameters) {
            if ("orgGroupId".equals(param.name)) {
                hql += " and orgId in (select gor.org.orgId from RGroupOrg gor where gor.flag is null and gor.orgGroup.orgGroupId = :orgGroupId)";
                params.add(param);
            } else if (param.operator == Operator.IS_NULL || param.operator == Operator.IS_NOT_NULL) {
                hql += param;
            } else {
                hql += " and " + param;
                params.add(param);
            }
        }
        return this.getDao().executeQuery(hql, params);
    }

    /* (non-Javadoc)
      * @see com.swg.authority.cache.ITreeService#flatOrg(java.util.List)
      */
	public void flatOrgTemp(List<NodeData> list) {
		Map<String, NodeData> map = new HashMap<String, NodeData>();
		for (NodeData nd : list) {
			map.put(nd.getNodeId(), nd);
		}
		
		List<FlatOrgTemp> result = new ArrayList<FlatOrgTemp>();
		
		for (NodeData nd : list) {
			reuse(nd.getNodeId(), nd.getNodeId(), map, result);
		}
		
		//1.delete flat_org_temp;
		this.getDao().executeNativeUpdate("truncate table T_NODESFLAT_TEMP", new Parameter[0]);
		
		//2.insert
		for (FlatOrgTemp fo : result) {
			this.getDao().save(fo);
		}
	}
	
	public void flatOrgTemp(String nodeId, String oldPId, String newPId){
		List<String> sqls = new ArrayList<String>(); 
		if(StringUtils.isNotBlank(newPId)){//移动;
			//删除和以前的祖先的关系;
			String del1 = "delete from T_NODESFLAT_TEMP where node_id in(select t1.node_id from T_NODESFLAT_TEMP t1 where t1.ancestor_id = '" + nodeId + "') and ancestor_id in( select t.ancestor_id from T_NODESFLAT_TEMP t where t.node_id = '" + oldPId + "')";
			
			//增加和现在祖先的关系;
			String in1  = "insert into T_NODESFLAT_TEMP select (SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name='T_NODESFLAT_TEMP') as ID, a.node_id as NODE_ID, b.ancestor_id as ANCESTOR_ID from (  select distinct t1.node_id as node_id from T_NODESFLAT_TEMP t1 where t1.ancestor_id = '" + nodeId + "' ) a , (select distinct t.ancestor_id as ancestor_id from T_NODESFLAT_TEMP t where t.node_id = '" + newPId + "') b";
		
			sqls.add(del1);
			sqls.add(in1);
		}else if(StringUtils.isNotBlank(oldPId)){//新增;
			//自己和祖先的关系;
			String in1 = "insert into T_NODESFLAT_TEMP select (SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name='T_NODESFLAT_TEMP') as ID, '" + nodeId + "' as NODE_ID, t.ancestor_id as ANCESTOR_ID  from T_NODESFLAT_TEMP t where t.node_id = '" + oldPId + "'"; 
			
			//自己和自己的关系;
			String in2 = "insert into T_NODESFLAT_TEMP(node_id,ancestor_id) values('"+nodeId+"','"+nodeId+"')";		
		
			sqls.add(in1);
			sqls.add(in2);
		}else if(StringUtils.isNotBlank(nodeId)){//删除;
			String del = " delete from T_NODESFLAT_TEMP where node_id in ( select t.node_id from  T_NODESFLAT_TEMP t where t.ancestor_id ='" +nodeId+ "') ";
			
			sqls.add(del);
		}else{//重新执行;
			
		}
		
		for (String sql : sqls) {
			this.getDao().executeNativeUpdate(sql, new Parameter[0]);
		}
	}
	
	public void updateFlatOrg(){
		String sql1 = " truncate table T_NODESFLAT";
		String sql2 = "insert into T_NODESFLAT select ID, NODE_ID, ANCESTOR_ID from  T_NODESFLAT_TEMP";
		this.getDao().executeNativeUpdate(sql1, new Parameter[0]);
		this.getDao().executeNativeUpdate(sql2, new Parameter[0]);
	}
	
	private void reuse(String nodeId, String parent, Map<String, NodeData> map, List<FlatOrgTemp> result){
		if(parent != null){
			NodeData nd = map.get(parent);
			if(nd == null){
				logger.warn("swg.authority:orgId[" +parent+ "] not exist!");
			}else{
				FlatOrgTemp fo = new FlatOrgTemp();
				fo.setNodeId(nodeId);
				fo.setAncestorId(parent);
				result.add(fo);
				
				reuse(nodeId, nd.getParentId(), map, result);
			}
		}
	}
	
	public void saveNode(User user, Organization org, boolean judge)throws Exception{
		IOnLineService s = (IOnLineService)ServiceFactory.getBean("onlineService");
		if(judge){//调整
			this.getDao().update(org);
		}else{
			String nodeId = org.getOrgId();
			String mesId   = StringUtils.trimToEmpty(org.getMesNodeid());
			
			boolean need = false; 
			
			Device dev = org.getDevice();
			
			//保存;
			if(StringUtils.isBlank(nodeId)){
				this.getDao().save(org);
			}else{
				this.getDao().update(org);
			}
			
			if(dev != null){
				if(StringUtils.isBlank(mesId)){
					throw new OnLineDevException("SYMG机床序列号不能为空!");
				}
				
				nodeId = (nodeId == null) ? "" : nodeId;
				Device de = findDevById(nodeId);
				if(de == null){
					Device n = new Device();
					n.setEquDesc(dev.getEquDesc());
					n.setEquName(org.getName());
					n.setEquSerialno(org.getMesNodeid());
					n.setEquType(dev.getEquType());
					n.setManuFacturer(dev.getManuFacturer());
					n.setManuFactureType(dev.getManuFactureType());
					
					Integer symgId = null;
					String cid = dev.getComposeId();
					if(StringUtils.isNotBlank(cid)){
						String[] cids = cid.split(IBusConsant.SYMG_SWG_SEP, -1);
						if(cids[0].equals(org.getMesNodeid())
								&& StringUtils.isNotBlank(cids[1])){
							symgId = Integer.parseInt(cids[1]);
							n.setSymgEquId(symgId);
							
							need = true;
						}else{
							throw new OnLineDevException(String.format("输入的SYMG机床序列号%s和请求关联的SYMG机床序列号%s不一致!", org.getMesNodeid(), cids[0]));
						}
					}
					n.setWorkStationName(dev.getWorkStationName());
					
//					n.setId(org.getOrgId());
					n.setNodeId(org.getOrgId());
					
					this.getDao().save(n);
					
					if(symgId != null)
						dev(user.getLoginName(), n.getId(), symgId);
				}else{
					Device n = de;
					
					n.setEquDesc(dev.getEquDesc());
					n.setEquName(org.getName());
					n.setEquType(dev.getEquType());
					n.setManuFacturer(dev.getManuFacturer());
					n.setManuFactureType(dev.getManuFactureType());
					
					String cid = dev.getComposeId();
					if(StringUtils.isNotBlank(cid)){
						String[] cids = cid.split(IBusConsant.SYMG_SWG_SEP, -1);
						if(cids[0].equals(org.getMesNodeid())
								&& StringUtils.isNotBlank(cids[1])){
							if(n.getSymgEquId() == null 
									|| !n.getEquSerialno().equals(org.getMesNodeid())){
								need = true;
							}
							n.setSymgEquId(Integer.parseInt(cids[1]));
						}else{
							throw new OnLineDevException(String.format("输入的SYMG机床序列号%s和请求关联的SYMG机床序列号%s不一致!", org.getMesNodeid(), cids[0]));
						}
					}
					
					n.setEquSerialno(org.getMesNodeid());
					n.setWorkStationName(dev.getWorkStationName());
					
					this.getDao().update(n);
				}
			}
			
			if(need && IConsant.NODE_TYPE_DEV.equals(org.getOrgType().getTypeId())){//发送设备;
				boolean f = s.sendDev(user.getUserId(), org.getMesNodeid(), org.getOrgId());
				if(!f){
					throw new OnLineDevException(String.format("该SYMG机床序列号%s已被关联!", org.getMesNodeid()));
				}
			}
		}
	}
	
	public Organization getNode(String id){
		Organization org = this.getDao().get(Organization.class, id);
		if(org != null){
			if( IConsant.NODE_TYPE_DEV.equals(org.getOrgType().getTypeId()) ){
				id = (id == null) ? "" : id;
				Device dev = this.findDevById(id);
				if(dev != null){
					org.setDevice(dev);
				}
			}
		}
		return org;
	}
	
	public void devLog(String devId, Integer symgId){
		String loginId = "unknown";
		Integer dev = null;
		if(StringUtils.isNotBlank(devId)){
			Device d = findDevById(devId);
			if(d != null)
				dev = d.getId();
		}
		if(dev == null || symgId == null){
			return;
		}
		
		HttpSession s = HttpContext.current().getSession();
		if(s != null){
			LoginUser lu = SessionHelper.loginUser(s);
			if(lu != null){
				loginId = lu.getUser().getLoginName();
			}
		}
		
		dev(loginId, dev, symgId);
	}
	
	private void dev(String loginId, Integer dev, Integer symgId){
//		try{
//			DeviceLog dl = new DeviceLog();
//			dl.setEquNodeid(dev);
//			dl.setSymgMachineId(symgId);
//			dl.setInteractionOperator(loginId);
//			dl.setInteractionDate(new Date());
//			this.getDao().save(DeviceLog.class, dl);
//		}catch(Exception e){
//			logger.error("swg.authority.symg:save dev log error!" + e.getMessage());
//		}
	}
	
	private Device findDevById(String nodeId){
		Device result = null;
		List<Device> list = this.getDao().executeQuery(" from Device d where d.nodeId=:nodeId", new Parameter("nodeId", nodeId,Operator.EQ));
		if(!list.isEmpty()){
			result = list.get(0);
		}
		return result;
	}
	
	public Device getDeviceByNodeId(String nodeId){
		return findDevById(nodeId);
	}
}
