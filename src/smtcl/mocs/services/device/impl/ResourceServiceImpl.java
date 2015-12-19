package smtcl.mocs.services.device.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.beans.storage.OrganizeMaterielAddBean;
import smtcl.mocs.beans.storage.OrganizeMaterielUpdateBean;
import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.TFixtureModel;
import smtcl.mocs.pojos.device.TFixtureClassInfo;
import smtcl.mocs.pojos.device.TUserResource;
import smtcl.mocs.pojos.job.TCutterClassInfo;
import smtcl.mocs.pojos.job.TCuttertypeInfo;
import smtcl.mocs.pojos.job.TFixtureTypeInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TMaterialClass;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProgramInfo;
import smtcl.mocs.pojos.job.TProgramMappingInfo;
import smtcl.mocs.pojos.job.TProgramdownloadInfo;
import smtcl.mocs.pojos.storage.TMaterialExtendOrder;
import smtcl.mocs.pojos.storage.TMaterialExtendPlan;
import smtcl.mocs.pojos.storage.TMaterialExtendStorage;
import smtcl.mocs.pojos.storage.TMaterialVersion;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 
 * 查询资源情况实现类
 * 
 * @作者：LiuChuanzhen
 * @创建时间：2012-11-13 下午13:00:25
 * @修改者：JiangFeng sunjun
 * @修改日期：2015年12月18日13:26:43
 * @修改说明：
 * @版本：V1.0
 */
public class ResourceServiceImpl extends
		GenericServiceSpringImpl<TUserResource, String> implements
		IResourceService {
	private ICommonService commonService;

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * 零件接口实例
	 */
	private IPartService partService = (IPartService) ServiceFactory
			.getBean("partService");

	/**
	 * 获取消耗品库存情况
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getConsumeStorage(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT new Map(a.userResourceId as userResourceId,"
				+ " a.TNodes.nodeName as nodeName,"
				+ " a.uresoType as uresoType,  "
				+ " a.uresoNo as uresoNo,"
				+ " a.uresoNorms as uresoNorms,"
				+ " a.uresoProvider as uresoProvider,"
				+ " a.uresoSerialNo as uresoSerialNo,"
				+ " a.uresoUnit as uresoUnit,"
				+ " a.uresoStock as uresoStock,"
				+ " DATE_FORMAT(a.uresoUpdatetime ,'%Y-%m-%d %T') as uresoUpdatetime) "
				+ " FROM TUserResource a  " + " where 1=1 ";
		if (nodeIds != null) {
			hql += " AND a.TNodes.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY  DATE_FORMAT(a.uresoUpdatetime ,'%Y-%m-%d %T')  DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * 获取消耗品使用信息
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getConsumeUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT new Map(a.id as id,"
				+ " a.TNodes.nodeName as nodeName,"
				+ " DATE_FORMAT(a.beginTime ,'%Y-%m-%d') as beginTime,  "
				+ " DATE_FORMAT(a.endTime ,'%Y-%m-%d') as endTime,"
				+ " a.usedAmount as usedAmount," + " a.uresoType as uresoType,"
				+ " a.uresoNo as uresoNo," + " a.uresoNorms as uresoNorms,"
				+ " a.uresoProvider as uresoProvider,"
				+ " a.uresoSerialNo as uresoSerialNo,"
				+ "a.uresoUnit as uresoUnit) " + " FROM TNodeCostResource a "
				+ " where 1=1 ";
		if (nodeIds != null) {
			hql += " AND a.TNodes.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * 获取所有消耗品类型
	 * 
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllConType() {
		String hql = " SELECT DISTINCT a.uresoType as uresoType FROM TNodeCostResource a WHERE a.uresoType IS NOT NULL";
		return dao.executeQuery(hql);
	}

	/**
	 * 查询夹具清单
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getClampList(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT new Map(a.userFixtureId as userFixtureId,"
				+ " a.ufixNo as ufixNo,  " + " a.ufixType as ufixType,"
				+ " a.ufixNorms as ufixNorms," + " a.ufixName as ufixName,"
				+ " a.ufixProvider as ufixProvider,"
				+ " a.ufixMaterialNo as ufixMaterialNo,"
				+ " a.ufixMaking as ufixMaking,"
				+ " a.ufixStorePlace as ufixStorePlace,"
				+ " a.ufixUsingMachine as ufixUsingMachine) "
				+ " FROM TUserFixture a ,TNodes c "
				+ " WHERE a.TNodes.nodeId= c.nodeId ";
		if (nodeIds != null) {
			hql += " AND c.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY a.userFixtureId DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * 夹具使用历史
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getClampUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT NEW MAP(a.id as id,"
				+ "DATE_FORMAT(a.startUsageTime ,'%Y-%m-%d %T') as startUsageTime,"
				+ "DATE_FORMAT(a.endUsageTime ,'%Y-%m-%d %T') as endUsageTime,"
				+ "a.usageMemo as usageMemo," + "a.ufixNo as ufixNo,"
				+ "a.ufixType as ufixType," + "a.ufixName as ufixName,"
				+ "a.ufixProvider as ufixProvider,"
				+ "a.ufixMaterialNo as ufixMaterialNo,"
				+ "a.ufixMaking as ufixMaking,"
				+ "a.ufixStorePlace as ufixStorePlace,"
				+ "a.ufixUsingMachine as ufixUsingMachine) "
				+ " FROM TUserFixtureUsage a, TNodes c "
				+ " WHERE a.TNodes.nodeId= c.nodeId ";
		if (nodeIds != null) {
			hql += " AND c.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY DATE_FORMAT(a.startUsageTime ,'%Y-%m-%d %T')  DESC ";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/*
	 * 获取当前节点下所有设备名--夹具
	 * 
	 * @SuppressWarnings("unchecked") public List<TUserFixture>
	 * clafindByMachineName(String nodeIds) { String hql =
	 * " SELECT DISTINCT a.ufixUsingMachine as ucutUsingMachine FROM TUserFixture a WHERE a.ufixUsingMachine IS NOT NULL AND  a.TNodes.nodeId in("
	 * + nodeIds + ")"; return dao.executeQuery(hql); }
	 * 
	 * // 获取当前节点下所有设备名--刀具
	 * 
	 * @SuppressWarnings("unchecked") public List<TUserCutter>
	 * cutfindByMachineName(String nodeIds) { String hql =
	 * " SELECT DISTINCT a.ucutUsingMachine as ucutUsingMachine FROM TUserCutter a WHERE a.ucutUsingMachine IS NOT NULL AND  a.TNodes.nodeId in("
	 * + nodeIds + ")"; return dao.executeQuery(hql); }
	 */

	/**
	 * 刀具清单
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getCutleryList(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT new Map(a.userCutterId as userCutterId,"
				+ " a.TNodes.nodeName as nodeName," + " a.ucutNo as ucutNo,  "
				+ " a.ucutType as ucutType," + " a.ucutNorms as ucutNorms,"
				+ " a.ucutStatus as ucutStatus,"
				+ " a.ucutProvider as ucutProvider,"
				+ " a.ucutMaterialNo as ucutMaterialNo,"
				+ " a.ucutMakings as ucutMakings, "
				+ " a.ucutStorePlace as ucutStorePlace,"
				+ " a.ucutUsingMachine as ucutUsingMachine,"
				+ " a.ucutRemainLifetime as ucutRemainLifetime, "
				+ "a.ucutDesignLifetime as ucutDesignLifetime) "
				+ " FROM TUserCutter a, TNodes c "
				+ " WHERE a.TNodes.nodeId= c.nodeId  ";
		if (nodeIds != null) {
			hql += " AND c.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY a.userCutterId DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * 刀具使用历史
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getCutleryUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT NEW Map(a.id as id,"
				+ " a.TNodes.nodeName as nodeName,"
				+ " DATE_FORMAT(a.startUsageTime, '%Y-%m-%d %T') as startUsageTime,"
				+ " DATE_FORMAT(a.endUsageTime, '%Y-%m-%d %T') as endUsageTime,"
				+ " a.cuttingTime as cuttingTime," + " a.ucutNo as ucutNo,"
				+ " a.ucutType as ucutType," + " a.ucutNorms as ucutNorms,"
				+ " a.ucutStatus as ucutStatus,"
				+ " a.ucutProvider as ucutProvider,"
				+ " a.ucutMaterialNo as ucutMaterialNo,"
				+ " a.ucutMakings as ucutMakings,"
				+ " a.ucutStorePlace as ucutStorePlace,"
				+ " a.ucutUsingMachine as ucutUsingMachine,"
				+ " a.ucutRemainLifetime as ucutRemainLifetime,"
				+ "a.ucutDesignLifetime as ucutDesignLifetime) "
				+ " FROM TUserCutterUsage a , TNodes c "
				+ "  WHERE a.TNodes.nodeId= c.nodeId  ";
		if (nodeIds != null) {
			hql += " AND c.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY DATE_FORMAT(a.startUsageTime, '%Y-%m-%d %T') DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * 查询所有刀具类型
	 * 
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllUCutType() {
		String hql = " SELECT DISTINCT a.ucutType as ucutType FROM TUserCutter a";
		return dao.executeQuery(hql);
	}

	/**
	 ********************************************** 资源配置*********************************************
	 */
	/**
	 * 根据条件查询相对应的物料节点信息
	 * 
	 * @param search
	 * @return
	 */
	public TreeNode getMaterailTreeNodeOnAll(String search, String nodeid) {
		TreeNode root = new DefaultTreeNode("根", null);
		TreeNode root2 = new DefaultTreeNode("根", null);
		root2.setExpanded(true);
		Collection<Parameter> parameters = new HashSet<Parameter>();
		if (null == search || "".equals(search)) {
			String hql = "from TMaterialClass where MClassid='-999' and MStatus=0 ";
			List<TMaterialClass> tmclist = dao.executeQuery(hql, parameters);
			TMaterialClass tmc;
			if (null != tmclist && tmclist.size() > 0) {
				tmc = (TMaterialClass) tmclist.get(0);
			} else {
				tmc = new TMaterialClass();
			}
			root2 = newNodeChildren(tmc, root, nodeid);

		} else {
			List<TMaterialClass> tmclist = this.getTMaterialClassByName(search);
			if (null != tmclist && tmclist.size() > 0) {
				TMaterialClass tmcOther = tmclist.get(0);
				root2 = newNodeChildren(tmcOther, root, nodeid);
			}
		}
		return root;
	}

	/**
	 * 递归返回物料树节点
	 * 
	 * @param tmc
	 * @param parent
	 * @return
	 */
	public TreeNode newNodeChildren(TMaterialClass tmc, TreeNode parent,
			String nodeid) {
		TreeNode newNode = new DefaultTreeNode(tmc, parent);
		newNode.setExpanded(true);
		for (TMaterialClass tt : tmc.getTMaterialClasses()) {
			if (tt.getMStatus() == 0 && tt.getNodeId().equals(nodeid)) {
				newNodeChildren(tt, newNode, nodeid);
			}
		}
		return newNode;
	}

	/**
	 * 根据名字查询物料类别
	 * 
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassByName(String search) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TMaterialClass where MStatus='0' and MClassname='"
				+ search + "' ";
		List<TMaterialClass> tmclist = dao.executeQuery(hql, parameters);
		return tmclist;
	}

	public List<Map<String,Object>> getTMaterialClassByAll(String nodeid){
		String hql="select new Map("
				+ " a.MClassid as id,"
				+ " a.MClassname as name)"
				+ " from TMaterialClass a"
				+ " where a.MStatus=0 "
				+ "and a.nodeId='"+nodeid+"'";
		return dao.executeQuery(hql);
	}

	/**
	 * 根据id查询物料类别
	 * 
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassById(String id) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TMaterialClass where MStatus=0 and MClassid=" + id;
		List<TMaterialClass> tmclist = dao.executeQuery(hql, parameters);
		return tmclist;
	}

	/**
	 * 保存物料类别
	 * 
	 * @param tmc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveTMaterialClass(TMaterialClass tmc) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TMaterialClass " + " where MStatus=0 "
				+ " and (MClassname='" + tmc.getMClassname() + "'or MClassno='"
				+ tmc.getMClassno() + "' or MClasstype='" + tmc.getMClasstype()
				+ "')  " + " and nodeId='" + tmc.getNodeId() + "'";
		List<TMaterialClass> tmclist = dao.executeQuery(hql, parameters);
		if (null != tmclist && tmclist.size() > 0) {
			return "已存在";
		} else {
			tmc.setMStatus(0);
			try {
				commonService.save(tmc);
				return "保存成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "保存失败";
			}
		}
	}

	/**
	 * 更新物料
	 * 
	 * @param tmc
	 * @return
	 */
	public String updateTMaterialClass(TMaterialClass tmc) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TMaterialClass where MStatus=0 and (MClassname='"
				+ tmc.getMClassname() + "' or MClassno='" + tmc.getMClassno()
				+ "'" + " ) and MClasstype='" + tmc.getMClasstype()
				+ "' and MClassid<>'" + tmc.getMClassid() + "'";
		List<TMaterialClass> tmclist = dao.executeQuery(hql, parameters);
		if (null != tmclist && tmclist.size() > 0) {
			return "已存在";
		} else {
			try {
				commonService.update(tmc);
				return "更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "更新失败";
			}

		}
	}

	/**
	 * 获取所有物料类别名称
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getSelectTMaterialClassForAll(String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map(MClassname as MClassname) from TMaterialClass where MStatus=0 and nodeId='"
				+ nodeid + "'";
		List<Map<String, String>> tmclist = dao.executeQuery(hql, parameters);
		return tmclist;
	}

	/**
	 * 根据父节点查询物料详细信息
	 * 
	 * @param pid
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfoByPid(String pid,
			String nodeid, String search) {
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TMaterailTypeInfo where "
				+ " nodeId='" + nodeid + "' ";
		if(!pid.equals("-999")){
			
			if (null != search && !search.equals("")) {
				hql = hql + " and (name like '%" + search + "%' or no like '%"
						+ search + "%' or typeno like '%" + search + "%') ";
			}
			if (null != pid && !pid.equals("")) {
				hql = hql + " and PId='" + pid+"' ";
				
			}
		}
		hql+=" order by no asc";
		List<TMaterailTypeInfo> tmclist = dao.executeQuery(hql, parameters);
		return tmclist;
	}

	/**
	 * 查询物料信息
	 * @param pid
	 * @param nodeid
	 * @param type
	 * @param no
	 * @param desc
	 * @param status
	 * @param unit
	 * @param startTime
	 * @param endTime
	 * @return
	 */

	public List<Map<String,Object>> getTMaterailTypeInfo(String pid,String nodeid,String type,String no,String desc,
			String status,String unit,Date startTime,Date endTime){
		String hql="select new Map("
				+ " a.id as id,"
				+ " a.no as no,"
				+ " a.name as name,"
				+ " a.norm as norm,"
				+ " a.typeno as typeno,"
				+ " case when a.className = 1 then '原材料' "
				+ "		 when a.className = 2 then '半成品' "
				+ "		 when a.className = 3 then '成品' "
				+ "      when a.className = 4 then '消耗品' "
				+ "      when a.className = 5 then '后台维护' "
				+ " end as className ,"
				+ " a.PId as pid,"
				+ " b.MClassname as MClassname)"
				+ " from TMaterailTypeInfo a ,TMaterialClass b  "
				+ " where a.PId=b.MClassid "
				+ " and a.nodeId='"+nodeid+"' ";
		
		if(null!=pid&&!pid.equals("")){
			hql+=" and a. PId="+pid;
		}	
		if(null!=type&&!type.equals("")){
			hql+=" and a.className='"+type+"'";
		}
		if(null!=no&&!no.equals("")){
			hql+=" and a.no";
			if(no.indexOf("%")>-1){
				hql+=" like '"+no+"'";
			}else{
				hql+=" ='"+no+"'";
			}
		}
		if(null!=desc&&!desc.equals("")){
			hql+=" and a.name ";
			if(desc.indexOf("%")>-1){
				hql+="  like '"+desc+"'";
			}else{
				hql+="  ='"+desc+"'";
			}
		}
		if(null!=status&&!status.equals("")){
			hql+=" and a.status ="+status+"";
		}
		if(null!=unit&&!unit.equals("")){
			hql+=" and a.unit ='"+unit+"'";
		}
		if(null!=startTime&&!startTime.toString().equals("")){
			hql+=" and DATE_FORMAT(a.createTime,'%Y-%m-%d %T')>='"+StringUtils.formatDate(startTime, 2)+"'";
		}
		if(null!=endTime&&!endTime.toString().equals("")){
			hql+=" and DATE_FORMAT(a.createTime,'%Y-%m-%d %T')<='"+StringUtils.formatDate(endTime, 2)+"'";
		}
		
		hql+=" order by a.id asc";
		List<Map<String,Object>> tmclist=dao.executeQuery(hql);
		return tmclist;
	}
	


	/**
	 * 保存零件详细
	 * 
	 * @param tmti
	 * @return
	 */
	public String saveTMaterailTypeInfo(TMaterailTypeInfo tmti) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TMaterailTypeInfo where status=0 and no='"
				+ tmti.getNo() + "' and id<>" + tmti.getId() + " and nodeId='"
				+ tmti.getNodeId() + "'";
		List<TMaterailTypeInfo> tmclist = dao.executeQuery(hql, parameters);
		if (null != tmclist && tmclist.size() > 0) {
			return "已存在";
		} else {
			try {
				TMaterailTypeInfo t = new TMaterailTypeInfo();
				t.setNo(tmti.getNo());
				t.setName(tmti.getName());
				t.setNorm(tmti.getNorm());
				t.setUnit(tmti.getUnit());
				t.setPrice(tmti.getPrice());
				t.setTypeno(tmti.getTypeno());
				t.setMemo(tmti.getMemo());
				t.setPId(tmti.getPId());
				t.setStatus(tmti.getStatus());
				t.setDensity(tmti.getDensity());
				t.setNodeId(tmti.getNodeId());
				commonService.save(t);
				return "保存成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "保存失败";
			}
		}

	}

	/**
	 * 更新零件详细
	 * 
	 * @param tmti
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateTMaterailTypeInfo(TMaterailTypeInfo tmti) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TMaterailTypeInfo where status=0 and no='"
				+ tmti.getNo() + "' and id<>" + tmti.getId();
		List<TMaterailTypeInfo> tmclist = dao.executeQuery(hql, parameters);
		if (null != tmclist && tmclist.size() > 0) {
			return "已存在";
		} else {
			try {
				commonService.update(tmti);
				return "更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "更新失败";
			}
		}
	}

	/**
	 * 删除物料信息
	 */
	public void deleteTMaterailTypeInfo(TMaterailTypeInfo tmti) {
		
		String hql="delete from TMaterailTypeInfo tti where tti.id='"+tmti.getId()+"'";
		dao.executeUpdate(hql);
		List list=dao.executeQuery("from TPartTypeInfo tpti "
				+ "where tpti.no='"+tmti.getNo()+"'");	
		if(list.size()>0){
			
			dao.executeUpdate("delete from TPartTypeInfo tpti where tpti.no='"+tmti.getNo()+"'");
		}
	}

	/**
	 * 根据类别id获取TfixtureConfig所需的datatable数据
	 * 
	 * @param classId
	 * @return
	 */
	public List<Map<String, Object>> getTfixtureConfigByClassId(String classId,
			String nodeid, String query) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "SELECT new Map(a.id as id,"
				+ " a.fixturesNo as ufixNo,  " + " a.fixturesName as ufixName,"
				+ " a.fixturesDescription as ufixMemo," + " b.name as type) "
				+ " FROM TFixtureTypeInfo a,TFixtureClassInfo b"
				+ " WHERE a.fixtureclassId=b.id " + " and a.nodeId='" + nodeid
				+ "'";
		if (null != classId && !"".equals(classId)) {
			hql = hql + " and  a.fixtureclassId='" + classId + "'";
		}
		if (null != query && !query.equals("")) {
			hql = hql + " and  (a.fixturesNo like '%" + query
					+ "%' or a.fixturesName like '%" + query + "%')";
		}
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * 夹具树节点模糊查询
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TFixtureClassInfo> getTFixtureClassInfoByQuery(String query,
			String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TFixtureClassInfo where (nodeid is null or nodeid='"
				+ nodeid + "')";
		if (null != query && !"".equals(query)) {
			hql = hql + " and  name like '%" + query + "%'";
		}
		hql = hql + " order by id";
		List<TFixtureClassInfo> rs = dao.executeQuery(hql, parameters);
		return rs;
	}

	/**
	 * 夹具类型更新
	 * 
	 * @return
	 */
	public String updateTUserFixture(TFixtureModel tfm) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TFixtureTypeInfo where (fixturesName='"
				+ tfm.getUfixName() + "'" + " or fixturesNo='"
				+ tfm.getUfixNo() + "') and id<>" + tfm.getId();
		List<TFixtureTypeInfo> list = dao.executeQuery(hql, parameters);
		if (null == list && list.size() > 0) {
			return "已存在";
		} else {
			try {
				String hql2 = "from TFixtureTypeInfo where id='" + tfm.getId()
						+ "'";
				List<TFixtureTypeInfo> tlist = dao.executeQuery(hql2,
						parameters);
				TFixtureTypeInfo tuf = tlist.get(0);
				tuf.setFixturesName(tfm.getUfixName());
				tuf.setFixturesNo(tfm.getUfixNo());
				TFixtureClassInfo tfci = this.getTFixtureClassInfoByName(
						tfm.getType()).get(0);
				tuf.setFixtureclassId(tfci.getId());
				tuf.setFixturesDescription(tfm.getUfixMemo());
				return "更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "更新失败";
			}
		}
	}

	/**
	 * 根据夹具类别id返回夹具类别
	 * 
	 * @param id
	 * @return
	 */
	public List<TFixtureClassInfo> getTFixtureClassInfoByName(String name) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TFixtureClassInfo where name='" + name + "'";
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * 夹具新增
	 * 
	 * @param tuf
	 * @return
	 */
	public String addTUserFixture(TFixtureTypeInfo tuf) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TFixtureTypeInfo where (fixturesName='"
				+ tuf.getFixturesName() + "'" + " or fixturesNo='"
				+ tuf.getFixturesNo() + "') and nodeId='" + tuf.getNodeId()
				+ "'";
		List<TFixtureTypeInfo> list = dao.executeQuery(hql, parameters);
		if (null != list && list.size() > 0) {
			return "已存在";
		} else {
			try {
				commonService.save(tuf);
				return "添加成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "添加成功";
			}
		}
	}

	/**
	 * 夹具删除
	 * 
	 * @param id
	 * @return
	 */
	public String deleteTUserFixture(String id) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		try {
			String hql = "from TFixtureTypeInfo where id='" + id + "'";
			List<TFixtureTypeInfo> rs = dao.executeQuery(hql, parameters);
			if (null != rs && rs.size() > 0) {
				TFixtureTypeInfo tt = rs.get(0);
				commonService.delete(tt);
				return "删除成功";
			} else {
				return "删除失败";
			}
		} catch (Exception e) {
			return "删除失败";
		}
	}

	/**
	 * 根据程序编号查询程序信息
	 * 
	 * @param progNo
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByProgNo(String progNo) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where progNo = " + progNo;
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		return tprlist;
	}

	/**
	 * 根据程序名称查询程序信息
	 * 
	 * @param progName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TProgramInfo> getTprogramInfoByProgName(String progName) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where progName ='" + progName + "'";
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		return tprlist;
	}

	/**
	 * 根据创建人查询程序信息
	 * 
	 * @param creator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TProgramInfo> getTprogramInfoByCreator(String creator) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where creator ='" + creator + "'";
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		return tprlist;
	}

	/**
	 * 根据条件查询TProgramInfo对象如果条件为空，则查询全部对象
	 * 
	 * @param searchStr
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfo(String searchStr, String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = " from TProgramInfo where nodeid='" + nodeid + "' ";
		if (searchStr != null && !"".equals(searchStr)) {
			hql = hql + " and (progNo like '%" + searchStr + "%'"
					+ "or progName like '%" + searchStr + "%'"
					+ "or creator like '%" + searchStr + "%')";
		}
		List<TProgramInfo> rs = dao.executeQuery(hql, parameters);
		return rs;
	}

	/**
	 * 添加程序信息
	 * 
	 * @param tpr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveTprogramInfo(TProgramInfo tpr) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where versionNo = '"
				+ tpr.getVersionNo() + "' and progName='" + tpr.getProgName()
				+ "' and nodeid='" + tpr.getNodeid() + "'";
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		try {
			if (tprlist != null && tprlist.size() < 1) {
				commonService.save(tpr);
				return "添加成功";
			} else {
				return "已存在";
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		return "添加失败";
	}

	/**
	 * 更新程序信息
	 * 
	 * @param tpr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateTprogramInfo(TProgramInfo tpr) {
		// Collection<Parameter> parameters = new HashSet<Parameter>();
		// String hql = "from TProgramInfo where (progNo='" + tpr.getProgNo() +
		// "' or progName='"+tpr.getProgName()
		// +"') and nodeid='"+tpr.getNodeid()+"' and id<>'"+tpr.getId()+"'";
		// List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		try {
			// if(null == tprlist || tprlist.size() < 1){
			// List<TProgramInfo> rslist =
			// dao.executeQuery("from TProgramInfo where id='"+tpr.getId()+"'");
			// TProgramInfo tt=rslist.get(0);
			// tt.setProgNo(tpr.getProgNo());
			// tt.setProgName(tpr.getProgName());
			// Date date = new Date();
			// tt.setUpdateTime(date);
			// tt.setUpdator(tpr.getUpdator());
			// tt.setContent(tpr.getContent());
			// commonService.update(tt);
			// return "更新成功";
			//
			// }else{
			// return "已存在";
			// }
			TProgramInfo temp = new TProgramInfo();
			temp = dao.get(TProgramInfo.class, tpr.getId());
			temp.setDescribe2(tpr.getDescribe2());
			dao.save(TProgramInfo.class, temp);
			return "更新成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "更新失败！";
		}
	}

	/**
	 * 删除程序
	 * 
	 * @param tpr
	 * @return
	 */
	public void deleteTprogramInfo(TProgramInfo tpr) {
		String hql = "delete from TProgramInfo where id = '" + tpr.getId()
				+ "'";
		try {
			if (null != tpr.getProgNo() && "" != tpr.getProgNo()) {
				commonService.executeUpdate(hql);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 获取刀具类别树
	 * 
	 * @param search
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCutterClassTree(String search,
			String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map( id as id ,name as name,status as status) "
				+ "from TCutterClassInfo where (nodeid is null or nodeid='"
				+ nodeid + "')";
		if (null != search && !"".equals(search)) {
			hql = hql + " where ( name like '%" + search + "%')";
		}
		System.out.println(hql);
		List<Map<String, Object>> list = dao.executeQuery(hql, parameters);
		return list;

	}

	/**
	 * 获取刀具信息
	 * 
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getCutterById(Integer pid, String nodeid,
			String search) {
		String hql = "select new Map( a.id as id ,a.name as name,a.no as no,a.norm as norm,a.remark as remark,b.name as cutter) "
				+ " from TCuttertypeInfo a , TCutterClassInfo b "
				+ " where a.pid = b.id "

				+ " and a.nodeId='" + nodeid + "'";
		if (null != pid && !"".equals(pid)) {
			hql = hql + " and a.pid ='" + pid + "'";
		}
		if (null != search && !"".equals(search)) {
			hql = hql + " and (a.id like '%" + search + "%' or a.name like '%"
					+ search + "%')";
		}
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String, Object>> ts = dao.executeQuery(hql, parameters);
		return ts;
	}

	/**
	 * 添加刀具信息
	 * 
	 * @param cutt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addCutterManage(CuttertypeModel cutt) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TCuttertypeInfo where (name='" + cutt.getName()
				+ "' or no='" + cutt.getNo() + "') and nodeId='"
				+ cutt.getNodeid() + "'";
		List list = dao.executeQuery(hql, parameters);
		if (null != list && list.size() > 0) {
			return "已存在";
		} else {
			String hqlc = "from TCutterClassInfo where name='"
					+ cutt.getCutterclass().trim() + "'";
			List<TCutterClassInfo> clist = dao.executeQuery(hqlc, parameters);
			if (null != clist && clist.size() > 0) {
				TCutterClassInfo tcc = clist.get(0);
				TCuttertypeInfo add = new TCuttertypeInfo();
				add.setName(cutt.getName());
				add.setPid(tcc.getId());
				add.setNo(cutt.getNo());
				add.setNorm(cutt.getNorm());
				add.setRemark(cutt.getRemark());
				add.setNodeId(cutt.getNodeid());
				try {
					commonService.save(add);
					return "添加成功";
				} catch (Exception e) {
					e.printStackTrace();
					return "添加失败";
				}
			} else {
				return "添加失败";
			}
		}

	}

	/**
	 * 删除刀具信息
	 * 
	 * @param cutt
	 * @return
	 */
	public String deleteCutterManage(CuttertypeModel cutt) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TCuttertypeInfo where id = '" + cutt.getId() + "'";
		List lsit = dao.executeQuery(hql, parameters);
		try {
			commonService.delete(lsit.get(0));
			return "删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "删除失败";
		}
	}

	/**
	 * 修改刀具信息
	 * 
	 * @param cutt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateCutterManage(CuttertypeModel cutt) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TCuttertypeInfo where (name='" + cutt.getName()
				+ "' or no='" + cutt.getNo() + "' or norm= '" + cutt.getNorm()
				+ "' or remark='" + cutt.getRemark() + "') and id<>'"
				+ cutt.getId() + "'";
		List list = dao.executeQuery(hql, parameters);
		if (null != list && list.size() > 0) {
			return "已存在";
		} else {
			String hql2 = "from TCutterClassInfo where name='"
					+ cutt.getCutterclass() + "'";
			List<TCutterClassInfo> clist = dao.executeQuery(hql2, parameters);
			String hqlu = "from TCuttertypeInfo where id='" + cutt.getId()
					+ "'";
			TCuttertypeInfo up = (TCuttertypeInfo) dao.executeQuery(hqlu,
					parameters).get(0);
			up.setName(cutt.getName());
			up.setNo(cutt.getNo());
			up.setNorm(cutt.getNorm());
			up.setRemark(cutt.getRemark());
			// up.setPid(Integer.parseInt(clist.get(0).getId().toString()));
			try {
				commonService.update(up);
				return "更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "更新失败";
			}
		}

	}

	/**
	 * 获取所有单位
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getUnitInfo(String unitType) {
		String hql = "select new Map(" + " a.unitName as unitName,"
				+ " a.id as id)" + " from TUnitInfo a";
		if (null != unitType && !unitType.equals("")) {
			hql += " where unitClass=" + unitType;
		}
		return dao.executeQuery(hql);
	}

	/**
	 * 获取辅助单位
	 * 
	 * @param unitId
	 * @return
	 */
	public List<Map<String, Object>> getUnitInfoOnAssis(String unitId) {
		String sql = "SELECT a.id,a.unit_name AS unitName FROM t_unit_info a "
				+ " WHERE a.unit_typeid=(SELECT unit_typeid FROM t_unit_info WHERE id="
				+ unitId + ")" + " AND a.unit_class=2 ";
		return dao.executeNativeQuery(sql);
	}

	/**
	 * 获取子库存
	 */
	public List<Map<String, Object>> getStockOnAll() {
		String hql = "select new Map(" + " storageNo as storageNo,"
				+ " storageName as storageName)" + " from TStorageInfo "
				+ " where storageStatus <> '禁止' " + " ";
		return dao.executeQuery(hql);
	}

	/**
	 * 获取子库存
	 */
	public List<Map<String, Object>> getPostionByStockNo(String stockNo) {
		String hql = "select new Map("
				+ " a.TMaterielPositionInfo.positionNo as positionNo,"
				+ " a.TMaterielPositionInfo.positionName as positionName)"
				+ " from RStoragePosition a "
				+ " where a.TStorageInfo.storageNo='" + stockNo + "' "
				+ " and a.TMaterielPositionInfo.positionStatus=1 ";
		return dao.executeQuery(hql);
	}

	/**
	 * 获取人员
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getBuyerList() {
		String hql = "select new Map(" + " no as no," + " name as name)"
				+ " from TMemberInfo ";
		return dao.executeQuery(hql);
	}


	/**
	 * 根据Param(字段名) materialId(值) 获取 obj (对象)
	 */
	public Object getObject(String materialId, String Param, Object obj) {
		String hql = "from " + obj.getClass().getName() + " where " + Param
				+ "=" + materialId;
		List<Object> list = dao.executeQuery(hql);
		if (null != list && list.size() > 0) {
			obj = list.get(0);
		}
		return obj;
	}

	/**
	 * 修改组织物料
	 * 
	 * @param omm
	 * @return
	 */
	public String UpdateOrganizeMateriel(OrganizeMaterielUpdateBean oma) {
		try {
			TMaterailTypeInfo tti = oma.getTti();// 物料主属性
			// 单位转换属性 暂时不做
			// 是否允许BOM 暂时不做
			String hql = "from TMaterailTypeInfo where no='" + tti.getNo()
					+ "' and id!=" + tti.getId();
			List<TMaterailTypeInfo> tmlist = dao.executeQuery(hql);
			if (null != tmlist && tmlist.size() > 0) {
				return "该物料已经存在";
			}
			dao.update(TMaterailTypeInfo.class, tti);
			if(tti.getClassName().equals("3")){
				TPartTypeInfo tpi=new TPartTypeInfo();
				String hqlpart="from TPartTypeInfo where no='"+tti.getNo()+"'";
				List<TPartTypeInfo> tpilist=dao.executeQuery(hqlpart);
				if(null!=tpilist&&tpilist.size()>0){
					tpi = tpilist.get(0);
					tpi.setNo(tti.getNo());
					tpi.setName(tti.getName());
					tpi.setUpdateDate(new Date());
					tpi.setNodeid(tti.getNodeId());
					dao.update(TPartTypeInfo.class,tpi);
				}
					
			}
			TMaterialExtendStorage tmes = oma.getTmes();// 库存
			// tmes.setMaterialId(Integer.parseInt(tti.getId().toString()));//设置物料id
			// 设置是否库存物料 版本控制 可存储 可处理 可保留
			List<String> kcItem = new ArrayList<String>();
			kcItem.add("库存物料");
			kcItem.add("版本控制");
			kcItem.add("可存储");
			kcItem.add("可处理");
			kcItem.add("可保留");
			for (String kc : kcItem) {
				int i = 0;
				if (oma.getIsKuCun().contains(kc)) {
					i = 1;
				}
				if (kc.equals("库存物料")) {
					tmes.setIsStock(i);
					continue;
				} else if (kc.equals("版本控制")) {
					tmes.setIsVersionCtrl(i);
					continue;
				} else if (kc.equals("可存储")) {
					tmes.setIsStorage(i);
					continue;
				} else if (kc.equals("可处理")) {
					tmes.setIsProcess(i);
					continue;
				} else if (kc.equals("可保留")) {
					tmes.setIsRetain(i);
					continue;
				}
			}
			dao.update(TMaterialExtendStorage.class, tmes);

			TMaterialExtendOrder tmio = oma.getTmio();// 采购
			// tmio.setMaterialId(Integer.parseInt(tti.getId().toString()));//设置物料id
			if (null != oma.getPrice() && !"".equals(oma.getPrice())) {
				tmio.setPrice(Double.parseDouble(oma.getPrice()));
			}
			tmio.setBuyer(oma.getBuyer());
			dao.update(TMaterialExtendOrder.class, tmio);

			TMaterialExtendPlan tmep = oma.getTmep();// 计划
			tmep = oma.getTmep();
			// tmep.setMaterialId(Integer.parseInt(tti.getId().toString()));//设置物料id
			dao.update(TMaterialExtendPlan.class, tmep);

			return "更新成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "更新失败";
		}
	}

	/**
	 * 返回物料版本
	 * 
	 * @param mno
	 * @return
	 */
	public List<Map<String, Object>> getMaterielVersion(String mno) {
		String hql = "select new Map(" + " a.versionNo as versionNo,"
				+ " a.versionDesc as versionDesc,"
				+ " DATE_FORMAT(a.startDate,'%Y-%m-%d %T') as dateTime,"
				+ " b.no as mno)"
				+ " from TMaterialVersion a,TMaterailTypeInfo b"
				+ " where a.materialId=b.id";
		if (null != mno && !mno.equals("")) {
			if (mno.indexOf("%") > 0) {
				hql += " and b.no like '" + mno + "' ";
			} else {
				hql += " and b.no ='" + mno + "' ";
			}
		}
		hql += " order by a.versionNo desc";
		return dao.executeQuery(hql);
	}

	/**
	 * 返回程序信息
	 */
	@Override
	public List<Map<String, Object>> uploadProgram(String progName,
			String progVersion, String progContent, String memName,
			String progDesc) {
		
		return null;
	}
	
	

	/**
	 * 返回程序版本信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProgramVersion(String progName,
			String nodeId) {
		String hql = "select new Map(" + " versionNo as versionNo,"
				+ " id as id)" + " from TProgramInfo " + " where progName ='"
				+ progName + "' and nodeid ='" + nodeId + "'"
				+ " order by LENGTH(versionNo) desc,versionNo desc";
		return dao.executeQuery(hql);
	}

	/**
	 * 获取程序信息
	 */
	@Override
	public List<Map<String, Object>> getProgramInfo(String progId) {
		String sql = "select t_program_info.id as id,prog_name as name,versionNo,describe2 as des,"
				+ " t_program_info.status as status,creator,t_part_type_info.no as partNo,"
				+ " t_process_info.no as processNo,t_program_mapping_info.isMainProgram as isMainProgram"
				+ " from t_program_info"
				+ " left join t_program_mapping_info on t_program_mapping_info.programId = t_program_info.id"
				+ " left join t_part_type_info on t_part_type_info.id =t_program_mapping_info.materailId"
				+ " left join t_process_info on t_process_info.id = t_program_mapping_info.processId"
				+ " where t_program_info.id = " + progId + "";
		return dao.executeNativeQuery(sql);
	}

	/**
	 * 获取物料信息(成品)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMaterialInfo(String nodeId) {
		String hql = "select new Map(" + " no as no," + " id as id,"
				+ " name as name)" + " from TPartTypeInfo "
				+ " where nodeid ='" + nodeId + "'";
		List<Map<String, Object>> list = dao.executeQuery(hql);
		for (Map<String, Object> map : list) {
			map.put("nameAndNo",
					map.get("name").toString() + "|" + map.get("no").toString());
		}
		return list;
	}

	/**
	 * 获取工序信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProcessInfo(String materialId,
			String nodeId) {
		String hql = "";
		if (materialId.equals("")) {
			hql = "select new Map(" + " b.name as name," + " b.id as id)"
					+ " from TProcessplanInfo as a,TProcessInfo as b"
					+ " where a.id =b.TProcessplanInfo.id" + " and b.nodeid ='"
					+ nodeId + "'" + " and a.defaultSelected=1";
		} else {
			hql = "select new Map(" + " b.name as name," + " b.id as id)"
					+ " from TProcessplanInfo as a,TProcessInfo as b"
					+ " where a.id =b.TProcessplanInfo.id"
					+ " and a.TPartTypeInfo.id =" + materialId + ""
					+ " and b.nodeid ='" + nodeId + "'"
					+ " and a.defaultSelected=1";
		}
		return dao.executeQuery(hql);
	}

	/**
	 * 获取已绑定的程序(包括参数中的程序)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getBingProgramInfo(String materialId,
			String processId, String nodeId) {
		String hql = "";
		if ((null == materialId || materialId.equals(""))
				&& (null == processId || processId.equals(""))) {
			hql = "select new Map("
					+ " b.progName as name,"
					+ " b.id as id,"
					+ " b.versionNo as versionNo,b.status as status,b.creator as creator,b.createTime as createTime)"
					+ " from TProgramInfo as b" + " where b.nodeid ='" + nodeId
					+ "'";
		} else {
			hql = "select new Map("
					+ " b.progName as name,"
					+ " b.id as id,"
					+ " a.isMainProgram as isMainProgram,"
					+ " b.versionNo as versionNo,b.status as status,b.creator as creator,b.createTime as createTime)"
					+ " from TProgramMappingInfo as a,TProgramInfo as b"
					+ " where b.id =a.programId" + " and b.nodeid ='" + nodeId
					+ "'";
			if (null != materialId && !materialId.equals("")) {
				hql += " and a.materailId =" + materialId + "";
			}
			if (null != processId && !processId.equals("")) {
				hql += " and a.processId=" + processId + "";
			}
		}

		return dao.executeQuery(hql);
	}

	/**
	 * 获取已绑定的程序(不包括参数中的程序)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getBingProgramInfo2(String materialId,
			String processId) {
		String hql = "select new Map(b.id as id)"
				+ " from TProgramMappingInfo as a,TProgramInfo as b"
				+ " where b.id =a.programId" + " and (a.materailId <>"
				+ materialId + "" + " or a.processId <>" + processId + ")";
		return dao.executeQuery(hql);
	}

	/**
	 * 获取程序信息List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProgramList(String nodeId) {
		String hql = "select new Map("
				+ " a.id as id,"
				+ " a.progName as name,"
				+ " a.versionNo as versionNo,a.describe2 as describe,a.status as status,"
				+ " a.creator as creator,a.createTime as createTime)"
				+ " from TProgramInfo as a" + " where a.nodeid ='" + nodeId
				+ "'";
		// +
		// " where a.id not in (select new Map (programId) from TProgramMappingInfo)";
		return dao.executeQuery(hql);
	}

	@Override
	public String bingProgram(List<Map<String, Object>> dataList,
			String materialId, String processId) {
		try {

			String sql = "delete from t_program_mapping_info where materailId ="
					+ materialId + "" + " and processId=" + processId + "";
			dao.executeNativeUpdate(sql);// 首先删除映射表中的数据

			// zhouxinyi
			// 2015/05/20
			// 程序与工序绑定后，同时赋值到t_process_info.programID字段中
			long mailProgramID = Integer.parseInt(dataList.get(0).get("id")
					.toString());
			String isMainProg = "";
			int progId = 0;

			for (Map<String, Object> map : dataList) {
				TProgramMappingInfo tpro = new TProgramMappingInfo();
				tpro.setMaterailId(Integer.parseInt(materialId));
				tpro.setProcessId(Integer.parseInt(processId));

				isMainProg = map.get("isMainProgram").toString();
				progId = Integer.parseInt(map.get("id").toString());
				tpro.setIsMainProgram(isMainProg);
				tpro.setProgramId(progId);
				// 绑定主程序
				if (isMainProg == "Y") {
					mailProgramID = progId;
				}

				dao.save(TProgramMappingInfo.class, tpro);
			}

			// zhouxinyi
			// 2015/05/20
			// 获取当前工序
			TProcessInfo _processinfo = partService
					.getTProcessInfoById(processId);
			if (_processinfo != null) {
				_processinfo.setProgramId(mailProgramID);
				dao.save(TProcessInfo.class, _processinfo);
			}
			// TProcessInfo _processinfo=new TProcessInfo();
			// _processinfo

			return "绑定成功";
		} catch (RuntimeException r) {
			r.printStackTrace();
			return "绑定失败";

		}
	}

	/**
	 * 获取程序综合信息
	 */
	@Override
	public List<Map<String, Object>> getProgramData(String searchStr,
			String nodeid) {
		String sql = "select t_program_info.id as id,prog_name as name,versionNo,describe2 as des,"
				+ " t_program_info.status as status,creator,t_part_type_info.no as partNo,"
				+ " t_process_info.no as processNo,t_process_info.name as processName,"
				+ " t_program_mapping_info.isMainProgram as isMainProgram"
				+ " from t_program_info"
				+ " left join t_program_mapping_info on t_program_mapping_info.programId = t_program_info.id"
				+ " left join t_part_type_info on t_part_type_info.id =t_program_mapping_info.materailId"
				+ " left join t_process_info on t_process_info.id = t_program_mapping_info.processId "
				+ " where t_program_info.nodeid ='" + nodeid + "'";
		if (searchStr != null && !"".equals(searchStr)) {
			sql = sql + " and (prog_name like '%" + searchStr + "%'"
					+ "or versionNo like '%" + searchStr + "%')";
		}
		sql = sql + " order by t_program_info.create_time desc";
		return dao.executeNativeQuery(sql);
	}

	/**
	 * 获取已绑定程序
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getBingProgramData(String programId) {
		String hql = "select new Map(" + " a.id as id,"
				+ " b.progName as progName," + " b.versionNo as versionNo,"
				+ " a.isMainProgram as isMainProgram)"
				+ " from TProgramMappingInfo as a,TProgramInfo as b"
				+ " where a.programId = b.id" + " and a.programId ="
				+ programId + "";
		return dao.executeQuery(hql);

	}

	/**
	 * 获取已绑定程序版本
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getBingProgramVersion(String programId) {
		String hql = "select new Map(b.progName as progName) from TProgramInfo as b where b.id ="
				+ programId + "";
		List<Map<String, Object>> list = dao.executeQuery(hql);
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		if (list.size() > 0) {
			String hql2 = "select new Map(a.id as id)"
					+ " from TProgramInfo as a" + " where a.progName = '"
					+ list.get(0).get("progName").toString() + "'"
					+ " and a.status ='Y'";
			list2 = dao.executeQuery(hql2);
		}

		return list2;
	}

	/**
	 * 删除程序
	 */
	@Override
	public String deleteProgram(String programId) {
		try {
			String sql = "delete from t_program_info where id =" + programId
					+ "";
			dao.executeNativeUpdate(sql);
			return "删除成功";
		} catch (RuntimeException r) {
			r.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String activityProgram(String programId, String flag) {
		try {
			TProgramInfo info = new TProgramInfo();
			info = dao.get(TProgramInfo.class, Long.valueOf(programId));
			if (flag.equals("1")) {
				info.setStatus("Y");
			} else {
				info.setStatus("N");
			}

			dao.save(TProgramInfo.class, info);
			return "激活成功";
		} catch (RuntimeException r) {
			r.printStackTrace();
			return "激活失败";
		}
	}

	/**
	 * 程序下载
	 */
	@Override
	public String downLoadProgram(String programId) {
		try {
			TProgramInfo info = new TProgramInfo();
			info = dao.get(TProgramInfo.class, Long.valueOf(programId));
			String path = info.getProgramPath();

			String realPath = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/static/program/");
			File file = new File(realPath + "/" + path);
			if (file.exists()) {
				HttpServletResponse response = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
				download(realPath + "/" + path, response);
				return "";
			} else {
				return "程序不存在";
			}

		} catch (RuntimeException r) {
			r.printStackTrace();
			return "下载失败";
		}
	}

	private HttpServletResponse download(String path,
			HttpServletResponse response) {
		OutputStream toClient = null;
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);

			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1)
					.toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new FileInputStream(path);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			toClient = response.getOutputStream();
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());

			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (toClient != null) {
				try {
					toClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}


	
	


	
	/**
	 * 新增组织物料
	 * @param omm
	 * @return
	 */
	public String SaveOrganizeMateriel(OrganizeMaterielAddBean oma){
		try {
			TMaterailTypeInfo tti=oma.getTti();//物料主属性
			//单位转换属性 暂时不做
			//是否允许BOM 暂时不做
			String hql="from TMaterailTypeInfo where no='"+tti.getNo()+"'";
			List<TMaterailTypeInfo> tmlist=dao.executeQuery(hql);
			if(null!=tmlist&&tmlist.size()>0){
				return "该物料已经存在";
			}
				tti.setCreateTime(new Date());
				dao.save(TMaterailTypeInfo.class, tti);
			if(tti.getClassName().equals("3")){
				TPartTypeInfo tpi=new TPartTypeInfo();
					tpi.setNo(tti.getNo());
					tpi.setName(tti.getName());
					tpi.setStatus("新建");
					tpi.setCreateDate(new Date());
					tpi.setUpdateDate(new Date());
					tpi.setNodeid(tti.getNodeId());
					dao.save(TPartTypeInfo.class,tpi);
			}
			TMaterialVersion tmv=oma.getTmv();
				tmv.setMaterialId(Integer.parseInt(tti.getId().toString()));//设置物料id
				tmv.setStartDate(new Date());
				tmv.setIsDefault(1);
				dao.save(TMaterialVersion.class,tmv);
				
			TMaterialExtendStorage tmes=oma.getTmes();//库存
				tmes.setMaterialId(Integer.parseInt(tti.getId().toString()));//设置物料id
				//设置是否库存物料 版本控制 可存储 可处理 可保留
				List<String> kcItem=new ArrayList<String>();
				kcItem.add("库存物料");
				kcItem.add("版本控制");
				kcItem.add("可存储");
				kcItem.add("可处理");
				kcItem.add("可保留");
				for(String kc:kcItem){
					int i=0;
					if(oma.getIsKuCun().contains(kc)){
						i=1;
					}
					if(kc.equals("库存物料")){
						tmes.setIsStock(i);
						continue;
					}else if(kc.equals("版本控制")){
						tmes.setIsVersionCtrl(i);
						continue;
					}else if(kc.equals("可存储")){
						tmes.setIsStorage(i);
						continue;
					}else if(kc.equals("可处理")){
						tmes.setIsProcess(i);
						continue;
					}else if(kc.equals("可保留")){
						tmes.setIsRetain(i);
						continue;
					}
				}
				dao.save(TMaterialExtendStorage.class, tmes);
				
			TMaterialExtendOrder tmio=new TMaterialExtendOrder();//采购
				tmio.setMaterialId(Integer.parseInt(tti.getId().toString()));//设置物料id
				if(null!=oma.getPrice()&&!"".equals(oma.getPrice())){
					tmio.setPrice(Double.parseDouble(oma.getPrice()));
				}
				tmio.setBuyer(oma.getBuyer());
				dao.save(TMaterialExtendOrder.class, tmio);
				
			TMaterialExtendPlan tmep=oma.getTmep();//计划
				tmep=oma.getTmep();
				tmep.setMaterialId(Integer.parseInt(tti.getId().toString()));//设置物料id
				dao.save(TMaterialExtendPlan.class, tmep);
				
				return "添加成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "添加失败";
		}
	}
	
	


	/**
	 * 获取工单获取相应程序
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProgramByJobDispatchNo(
			String jobDispatchNo, String partId) {
		String hql = "";
		//优先查找工单编号的程序
		//查找方式为  工单表(TJobdispatchlistInfo)--》工序表(TProcessInfo)--》程序绑定表（TProgramMappingInfo）--》程序表(TProgramInfo)--》零件表（TPartTypeInfo）
		if (null != jobDispatchNo&&!"".equals(jobDispatchNo)) {
			hql = "select new Map("
					+ " d.id as programId,d.progName as progName,d.versionNo as versionNo,"
					+ " d.programPath as programPath,d.md5 as md5)"
					+ " from TJobdispatchlistInfo as a,TProcessInfo as b,"
					+ " TProgramMappingInfo as c,TProgramInfo as d,TPartTypeInfo as e"
					+ " where a.TProcessInfo.id = b.id and b.id =c.processId and c.programId = d.id"
					+ " and e.id = c.materailId" + " and d.status ='Y'";
			hql += " and a.no ='" + jobDispatchNo + "'";
		} else {
			//如果工单id为空 则根据零件id查找工序  
			//程序绑定表（TProgramMappingInfo）--》工序表（TProcessInfo）--》程序表(TProgramInfo)--》零件表（TPartTypeInfo）
			//以TProgramMappingInfo表为中心
			hql = "select new Map(pg.id AS programId, pg.progName AS progName, "
					+ " pg.versionNo as versionNo, pg.programPath "
					+ " as programPath, pg.md5 as md5, p.name AS stepname,"
					+ " pt.id AS partid, pt.name AS partname) "
					+ " FROM TProgramMappingInfo m, TProcessInfo p, TProgramInfo pg, TPartTypeInfo pt "
					+ "WHERE pt.id = "
					+ partId
					+ " AND m.processId = p.id AND m.programId = pg.id AND m.materailId = pt.id";
		}
		try {
			return dao.executeQuery(hql);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 程序下载记录信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProgramLoadDownInfo(String programId,
			String equSerialNo) {
		String hql = "select new Map(id as id)" + " from TProgramdownloadInfo"
				+ " where programId = " + programId + ""
				+ " and equSerialNo ='" + equSerialNo + "'";
		return dao.executeQuery(hql);
	}

	/**
	 * 保存下载信息
	 */
	@Override
	public String saveProgramLoadDownInfo(String programId, String equSerialNo,
			String userID) {
		try {
			// String
			// sql="insert into t_programdownload_info (programId,equSerialNo,downloadTime,userName)"
			// + " values ("+programId+",'"+equSerialNo+"',"+new
			// Date()+",'"+userID+"')";
			// dao.executeNativeUpdate(sql);
			TProgramdownloadInfo tpInfo = new TProgramdownloadInfo();
			tpInfo.setProgramId(Integer.valueOf(programId));
			tpInfo.setEquSerialNo(equSerialNo);
			tpInfo.setUserName(userID);
			tpInfo.setDownloadTime(new Date());
			dao.save(TProgramdownloadInfo.class, tpInfo);
			return "更新成功";
		} catch (RuntimeException r) {
			r.printStackTrace();
			return "更新失败";
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProgramById(String programId) {
		String hql = "select new Map(b.progName as progName) from TProgramInfo as b where b.id ="
				+ programId + "";
		List<Map<String, Object>> list = dao.executeQuery(hql);
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		if (list.size() > 0) {
			String hql2 = "select new Map(" + " a.id as id,"
					+ " b.progName as progName," + " b.versionNo as versionNo,"
					+ " a.isMainProgram as isMainProgram,"
					+ " b.status as status)"
					+ " from TProgramMappingInfo as a,TProgramInfo as b"
					+ " where a.programId = b.id" + " and b.progName ='"
					+ list.get(0).get("progName").toString() + "'";
			list2 = dao.executeQuery(hql2);
		}

		return list2;
	}

	/**
	 * @author zhouxinyi
	 * @date 20150520
	 * @comment 获取关联程序
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProgramMappingList(String programId) {
		String hql = "select new Map(b.programId as programId,b.materailId as materailId,b.processId as processId) from TProgramMappingInfo as b where b.programId ="
				+ programId + "";
		List<Map<String, Object>> list = dao.executeQuery(hql);
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		if (list.size() > 0) {
			String hql2 = "select new Map(" + " a.id as id,"
					+ " b.progName as progName," + " b.versionNo as versionNo,"
					+ " a.isMainProgram as isMainProgram,"
					+ " b.status as status)"
					+ " from TProgramMappingInfo as a,TProgramInfo as b"
					+ " where a.programId = b.id" + " and a.materailId ="
					+ list.get(0).get("materailId").toString()
					+ " and a.processId ="
					+ list.get(0).get("processId").toString();

			list2 = dao.executeQuery(hql2);
		}

		return list2;
	}

	/**
	 * 获取零件信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getParts(String partName) {
		//根据零件编号和零件名称 模糊匹配零件信息
		String hql = "select new Map(id as id,name as partName,no as partNo)"
				+ " from TPartTypeInfo" + " where no like '%" + partName
				+ "%' or name like '%" + partName + "%'";
		return dao.executeQuery(hql);
	}

	/**
	 * 获取nodeId BY equSerialNo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getEquNodeId(String equSerialNo) {
		String hql = "select new Map(a.TNodes.TNodes.nodeId as nodeId)"
				+ " from TEquipmentInfo as a" + " where a.equSerialNo ='"
				+ equSerialNo + "'";
		return dao.executeQuery(hql);
	}
	public List<Map<String, Object>> getEquNodeIdBySql(String equSerialNo) {
		String hql = " SELECT tn.P_NODEID FROM T_EquipmentAddInfo te,T_Nodes tn "
				+ " WHERE te.nodeID = tn.nodeID AND te.equ_SerialNo = '"+equSerialNo+"'";
		return dao.executeNativeQuery(hql);
	}
	
	/**
	 * add by yangnenghui
	 * 通过jobDispatchNo(工单编号)查询对应的相关数据
	 * maxCutNo		刀具切削次数上限(根据jobDispatchNo从t_jobdispatchlist_info表中查询)
	 * maxCutTime	刀具切削时间上限(暂无，写死50)
	 * toolNo		刀具号(暂无,写死1-9)
	 * value1		预留变量1(空值)
	 * value2		预留变量2(空值)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getToolData(String jobDispatchNo) {
		
			if(!jobDispatchNo.equals("")){
				
				String hql = "select tji.maxiumcut as maxiumcut from t_jobdispatchlist_info tji where tji.no = '"+jobDispatchNo+"'";
				List<Map<String, Object>> maxCutNo = dao.executeNativeQuery(hql);
				List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
				Object objMaxCutNo = maxCutNo.get(0).get("maxiumcut");
				if(objMaxCutNo == null){
					objMaxCutNo = "";
				}
				
				for(int i = 1;i < 10;i++){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("maxCutNo", objMaxCutNo);
					map.put("maxCutTime", "50");
					map.put("toolNo", i);
					map.put("value1", "");
					map.put("value2", "");
					resultlist.add(map);
				}
				return resultlist;
			}else{
				return null;
			}
	}

	@Override
	public TMaterailTypeInfo getTMaterialTypeInfoById(Long id) {
		
		String hql="from TMaterailTypeInfo where id="+id;
		return (TMaterailTypeInfo)dao.executeQuery(hql).get(0);
	}
}
