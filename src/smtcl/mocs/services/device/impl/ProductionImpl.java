package smtcl.mocs.services.device.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.device.TNodeProductionProfiles;
import smtcl.mocs.services.device.IProductionService;


/**
 * 生产状态查询实现
 * @作者：ZhaoHongshi
 * @创建时间：2012-11-13 下午3:15:08
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @版本：V1.0
 */
@SuppressWarnings({"unchecked" ,"rawtypes"})
public class ProductionImpl extends GenericServiceSpringImpl<TNodeProductionProfiles, String> implements
		IProductionService {
	/**
	 * 组织节点生产状态查询实现	
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getProductOutput(int pageNo,
			int pageSize, Collection<Parameter> parameters) {
		String hql = "SELECT new Map(a.TNodes.nodeId as nodeId, "
				+ " b.uprodName as uprodName, " 
				+ " b.uprodType as uprodType, "
				+ " a.dailyOutput as dailyOutput, "
				+ " a.weeklyOutput as weeklyOutput, "
				+ " a.monthlyOutput as monthlyOutput, "
				+ " a.annualOutput as annualOutput, "
				+ " a.totalOutput as totalOutput)"
				+ " FROM TNodeProductionProfiles a ,TUserProducts b "
				+ " where a.uprodId = b.userProdId";

		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 *  组织节点生产计划查询实现
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getProductPlan(int pageNo,
			int pageSize, Collection<Parameter> parameters) {
		String hql = "SELECT new map(a.userProdPlanId as userProdPlanId, "
				+ " a.uplanNo AS uplanNo, "
				+ " b.uprodType AS uprodType,"
				+ " b.uprodShortCode AS uprodShortCode,"
				+ " a.uplanName AS uplanName, "
				+ " a.uplanStatus AS uplanStatus, "
				+ " a.uplanMasterPlanNo AS uplanMasterPlanNo,"
				//+ " IFNULL(a.uplanNum,'0') AS uplanNum, "
				//+ " IFNULL(a.uplanActualQuantity,'0') AS uplanActualQuantity, "
				///+ " IFNULL(a.uplanGoodQuantity,'0') AS uplanGoodQuantity, "
				+ " a.uplanNum AS uplanNum, "
				+ " a.uplanActualQuantity AS uplanActualQuantity, "
				+ " a.uplanGoodQuantity AS uplanGoodQuantity, "
				
				+ " a.uplanInchargePerson AS uplanInchargePerson, "
				+ " DATE_FORMAT(a.uplanOnlineDate,'%Y-%m-%d %T') AS uplanOnlineDate, "
				+ " DATE_FORMAT(a.uplanFinishDate,'%Y-%m-%d %T') AS uplanFinishDate, "
				+ " DATE_FORMAT(a.uplanBeginTime,'%Y-%m-%d %T') AS uplanBeginTime, "
				+ " DATE_FORMAT(a.uplanActualFinishTime,'%Y-%m-%d %T') AS uplanActualFinishTime, "
				+ " a.TNodes.nodeId AS nodeId, "
				+ " b.uprodName AS uprodName, "
				+ " b.uprodNorms AS uprodNorms, "
				+ " b.userProdId AS userProdId, "
				+ " b.uprodDesc AS uprodDesc, "
				+ " a.uplanUnit AS uplanUnit, "
				+ " a.uplanRouting AS uplanRouting, "
				+ " a.uplanType AS uplanType)"
				+ " FROM TUserProdctionPlan a , TUserProducts b "
				+ " where a.TUserProducts.userProdId = b.userProdId ";

		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql +=  " ORDER BY a.uplanNo DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * 组织节点排班计划查询实现
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getShiftPlan(int pageNo,
			int pageSize, Collection<Parameter> parameters) {
		String hql = "SELECT new map(a.TNodes.nodeId as nodeId , "
				   + " a.userShiftid AS userShiftid, "
				   + " a.userShiftName AS userShiftName, "
				   + " b.nodeName AS nodeName, "
				   + " a.userShiftGroup AS userShiftGroup, "
				   + " DATE_FORMAT(a.startworkTime,'%Y-%m-%d %T') AS startworkTime, "
				   + " DATE_FORMAT(a.endworkTime,'%Y-%m-%d %T') AS endworkTime, "
				   + " a.planQuantity AS planQuantity, "
				   + " a.actualQuantity AS actualQuantity)"
				   + " FROM TUserShiftPlan a ,TNodes b "
				   + " where a.TNodes.nodeId = b.nodeId ";
		
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql +=  " ORDER BY a.userShiftid ASC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * 根据节点ID获取节点名称
	 * @param nodeid
	 * @return List
	 */
	@Override
	public List getNodeName(String nodeid) {
		String hql = "SELECT a.nodeName "
				   + "FROM TNodes a "
				   + "where a.nodeId = " + "'" + nodeid + "'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * 获取计划编号列表
	 * @param parameters
	 * @return List
	 */
	@Override
	public List getAllPlanNo(Collection<Parameter> parameters) {
		String hql = "SELECT a.uplanNo AS uplanNo "
				+ " FROM TUserProdctionPlan a , TUserProducts b "
				+ " where a.TUserProducts.userProdId = b.userProdId ";

		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		return dao.executeQuery(hql, parameters);
	}
	/**
	 * 组织节点生产状态查询实现	
	 * @param parameters
	 * @return IDataCollection<Map<String,Object>>
	 */
	@Override
	public List<Map<String, Object>> getAllProductOutput(
			Collection<Parameter> parameters) {
		String hql = "SELECT new Map(a.TNodes.nodeId as nodeId, "
				+ " b.uprodName as uprodName, " 
				+ " b.uprodType as uprodType, "
				+ " a.dailyOutput as dailyOutput, "
				+ " a.weeklyOutput as weeklyOutput, "
				+ " a.monthlyOutput as monthlyOutput, "
				+ " a.annualOutput as annualOutput, "
				+ " a.totalOutput as totalOutput)"
				+ " FROM TNodeProductionProfiles a ,TUserProducts b "
				+ " where a.uprodId = b.userProdId";

		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		
		return dao.executeQuery(hql, parameters);
	}
}
