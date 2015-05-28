package smtcl.mocs.services.storage.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import smtcl.mocs.model.TMaterialPositionModel;
import smtcl.mocs.model.TStorageInfoModel;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.RStoragePosition;
import smtcl.mocs.pojos.storage.TJobdispatchMaterial;
import smtcl.mocs.pojos.storage.TMaterialStorage;
import smtcl.mocs.pojos.storage.TMaterielPositionInfo;
import smtcl.mocs.pojos.storage.TStockMaterialbath;
import smtcl.mocs.pojos.storage.TStockMaterialseq;
import smtcl.mocs.pojos.storage.TStorageInfo;
import smtcl.mocs.pojos.storage.TTransaction;
import smtcl.mocs.pojos.storage.TTransactionRelation;
import smtcl.mocs.services.storage.IStorageManageService;
import smtcl.mocs.utils.device.StringUtils;



/**
 * 
 * @version V1.0
 * @创建时间 2014-08-27
 * @作者 FW
 */
public class StorageManageImpl extends GenericServiceSpringImpl<Object, String>  implements IStorageManageService {
	
	@Override
	/**
	 * 获取库房信息集合
	 */
	public List<TStorageInfoModel> getStorageList(String str,String nodeId) {
		
//		Collection<Parameter> parameters = new HashSet<Parameter>();
//		String hql = "select new Map( "
//				+ "s.id as id, "
//				+ "s.storageNo as no, "
//				+ "s.storageName as name, "
//				+ "s.storageStatus as status, "
//				+ "s.invalidDate as invalidDate, "
//				+ "case s.isAvailable when 1 then '是' when 2 then '否' else '其他' end as isAvailable, "
//				+ "case s.planType when 1 then '最小-最大' when 2 then '在订购点' when 3 then '看板补充' when 4 then '盘点补充' else '其他' end as planType, "
//				+ "case s.positionType when 1 then '无' when 2 then '预指定' when 3 then '动态输入' when 4 then '物料层' else '其他' end as positionType, "
//				+ "s.address as address, "
//				+ "s.storageMan as storageMan, "
//				+ "s.createDate as createDate, "
//				+ "s.preProcessTime as preProcessTime, "
//				+ "s.inProcessTime as inProcessTime, "
//				+ "s.sufProcessTime as sufProcessTime ) "
//				+ "from TStorageInfo s ";
				Collection<Parameter> parameters = new HashSet<Parameter>();
				List<TStorageInfo> rslist=new ArrayList<TStorageInfo>();
				List<TStorageInfoModel> list=new ArrayList<TStorageInfoModel>();
				String hql="from TStorageInfo where nodeId ='"+nodeId+"'";
		
				if(!StringUtils.isEmpty(str)){
					hql = hql +" and (storageNo like '%"+str+"%'"
							+ " or storageStatus like '%"+str+"%'"
							+ " or storageName like '%"+str+"%')";
				}
				rslist=dao.executeQuery(hql, parameters);
				
				
				for(TStorageInfo tt:rslist){
					TStorageInfoModel temp =new TStorageInfoModel();
					//可净得
					if(tt.getIsAvailable().equals("1")){
						temp.setIsAvailable("是");
					}else{
						temp.setIsAvailable("否");
					}
					//计划方式
					if(tt.getPlanType().equals("1")){
						temp.setPlanType("最小-最大");
					}else if(tt.getPlanType().equals("2")){
						temp.setPlanType("在订购点");
					}else if(tt.getPlanType().equals("3")){
						temp.setPlanType("看板补充");
					}else{
						temp.setPlanType("盘点补充");
					}
					//控制方式
					if(tt.getPositionType().equals("1")){
						temp.setPositionType("无");
					}else if(tt.getPositionType().equals("2")){
						temp.setPositionType("预指定");
					}else if(tt.getPositionType().equals("3")){
						temp.setPositionType("动态输入");
					}else{
						temp.setPositionType("物料层");
					}
					
					temp.setId(tt.getId());
					temp.setStorageNo(tt.getStorageNo());
					temp.setStorageName(tt.getStorageName());
					temp.setStorageStatus(tt.getStorageStatus());
					temp.setInvalidDate(tt.getInvalidDate().toString());
					temp.setAddress(tt.getAddress());
					temp.setStorageMan(tt.getStorageMan());
					temp.setCreateDate(tt.getCreateDate());
					temp.setPreProcessTime(tt.getPreProcessTime());
					temp.setInProcessTime(tt.getInProcessTime());
					temp.setSufProcessTime(tt.getSufProcessTime());
					temp.setNodeId(tt.getNodeId());
					list.add(temp);
				}
			
		        return list;

	}

	@Override
	/**
	 * 更新库房信息
	 */
	public String updateStorageInfo(TStorageInfo tStorageInfo, int flag) {
		try{
			if(flag ==1){
				dao.save(tStorageInfo);
				return "成功";
			}else{
				dao.update(tStorageInfo);
				return "成功";
			}
		}catch(Exception exp){
			exp.printStackTrace();
			
			return "保存失败";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取货位信息集合
	 */
	public List<TMaterialPositionModel> getMaterialPositionList(String str,String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map( "
				+ "m.id as id, "
				+ "m.positionNo as positionNo, "
				+ "m.positionName as positionName, "
				+ "m.positionStatus as positionStatus,"
				+ "m.quantitySize as quantitySize, "
				+ "m.quantityUnit as quantityUnit, "
				+ "m.capacitySize as capacitySize, "
				+ "m.capacityUnit as capacityUnit, "
				+ "m.weightSize as weightSize, "
				+ "m.weightUnit as weightUnit, "
				+ "m.dimensionSize as dimensionSize, "
				+ "m.dimensionUnit as dimensionUnit, "
				+ "m.axis as axis, "
				+ "s.id as sid, "
				+ "s.storageNo as storageNo, "
				+ "s.storageName as storageName) "
				+ "from TMaterielPositionInfo m,RStoragePosition r, TStorageInfo s "
				+ "where r.TMaterielPositionInfo.id = m.id "
				+ "and r.TStorageInfo.id = s.id "
				+ "and s.nodeId ='"+nodeId+"'";
				if(!StringUtils.isEmpty(str)){
					hql = hql +" and (m.positionNo like '%"+str+"%'"
							+ " or m.positionName like '%"+str+"%'"
							+ " or s.storageName like '%"+str+"%')";
				} 
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		List<TMaterialPositionModel> list = new ArrayList<TMaterialPositionModel>();
		for(Map<String,Object> ss :dataList){
			TMaterialPositionModel temp = new TMaterialPositionModel();
			temp.setId(Integer.parseInt(ss.get("id").toString()));
			temp.setPositionNo(ss.get("positionNo").toString());
			temp.setPositionName(ss.get("positionName").toString());
			if(ss.get("positionStatus").toString().equals("1")){
				temp.setPositionStatus("活动");
			}else{
				temp.setPositionStatus("禁止");
			}
			
			if(null !=ss.get("quantitySize") && !StringUtils.isEmpty(ss.get("quantitySize").toString())){
				temp.setQuantitySize(Double.valueOf(ss.get("quantitySize").toString()));
			}
			
			if(null !=ss.get("quantityUnit") && !StringUtils.isEmpty(ss.get("quantityUnit").toString())){
				temp.setQuantityUnit(ss.get("quantityUnit").toString());
			}
			
			if(null !=ss.get("capacitySize") && !StringUtils.isEmpty(ss.get("capacitySize").toString())){
				temp.setCapacitySize(Double.valueOf(ss.get("capacitySize").toString()));
			}
			
			if(null !=ss.get("capacityUnit") && !StringUtils.isEmpty(ss.get("capacityUnit").toString())){
				temp.setCapacityUnit(ss.get("capacityUnit").toString());
			}
			
			if(null !=ss.get("weightSize") && !StringUtils.isEmpty(ss.get("weightSize").toString())){
				temp.setWeightSize(Double.valueOf(ss.get("weightSize").toString()));
			}
			
			if(null !=ss.get("weightUnit") && !StringUtils.isEmpty(ss.get("weightUnit").toString())){
				temp.setWeightUnit(ss.get("weightUnit").toString());
			}
			if(null !=ss.get("dimensionSize") && !StringUtils.isEmpty(ss.get("dimensionSize").toString())){
				temp.setDimensionSize(ss.get("dimensionSize").toString());
			}
			if(null !=ss.get("dimensionUnit") && !StringUtils.isEmpty(ss.get("dimensionUnit").toString())){
				temp.setDimensionUnit(ss.get("dimensionUnit").toString());
			}
			if(null !=ss.get("axis") && !StringUtils.isEmpty(ss.get("axis").toString())){
				temp.setAxis(ss.get("axis").toString());
			}
			
			temp.setSid(Integer.parseInt(ss.get("sid").toString()));
			temp.setStorageNo(ss.get("storageNo").toString());
			temp.setStorageName(ss.get("storageName").toString());
			list.add(temp);
		}
		return list;
	}

	@Override
	/**
	 * 更新货位信息
	 */
	public String updateMaterialPositionInfo(
			TMaterielPositionInfo tMaterielPositionInfo, int flag,String storageId) {
		try{
			if(flag ==1){
				dao.save(tMaterielPositionInfo);
				
				//中间表数据插入
				RStoragePosition rs = new RStoragePosition();
				
				//库房信息
				TStorageInfo storageInfo = new TStorageInfo();
				storageInfo = dao.get(TStorageInfo.class,Integer.parseInt(storageId));
				
				rs.setTMaterielPositionInfo(tMaterielPositionInfo);
				rs.setTStorageInfo(storageInfo);
				dao.save(rs);
				return "成功";
			}else{
				dao.update(tMaterielPositionInfo);
				
				//中间表数据更新
				Collection<Parameter> parameters = new HashSet<Parameter>();
				
				//库房信息
				TStorageInfo storageInfo = new TStorageInfo();
				storageInfo = dao.get(TStorageInfo.class,Integer.parseInt(storageId));
				
				List<RStoragePosition> rslist=new ArrayList<RStoragePosition>();
				String hql="from RStoragePosition where "
						+ "TMaterielPositionInfo.id ="+tMaterielPositionInfo.getId()+"";
				rslist =dao.executeQuery(hql, parameters);
				for(RStoragePosition t:rslist){
					t.setTStorageInfo(storageInfo);
					dao.update(t);
				}
				return "成功";
			}
		}catch(Exception exp){
			exp.printStackTrace();
			return "保存失败";
		}
	}

	@Override
	/**
	 * 库房删除（把状态改为禁止）
	 */
	public String delStorageInfo(TStorageInfo tStorageInfo) {
		try{
			TStorageInfo ts = new TStorageInfo();
			ts = dao.get(TStorageInfo.class,tStorageInfo.getId());
			ts.setStorageStatus("禁止");
			dao.update(ts);
			return "成功";
		}catch(Exception exp){
			return "保存失败";
		}
	}

	@Override
	public String delMaterielPositionInfo(
			TMaterielPositionInfo tMaterielPositionInfo) {
		try{
			int id = tMaterielPositionInfo.getId();
		    
		    
		    //中间表数据删除
		    Collection<Parameter> parameters = new HashSet<Parameter>();
			RStoragePosition rs = new RStoragePosition();
			List<RStoragePosition> rsList = new ArrayList<RStoragePosition>();
			String hql="from RStoragePosition where TMaterielPositionInfo.id ='"+id+"'";
			rsList =dao.executeQuery(hql,parameters);
			for(RStoragePosition tt:rsList){
				rs = dao.get(RStoragePosition.class,tt.getId());
				dao.delete(rs);
			}
			
			dao.delete(tMaterielPositionInfo);
			
		    return "成功"; 
		}catch(Exception exp){
			exp.printStackTrace();
			return "保存失败";
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getList(String hql) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
     /**
      * 获取人员信息
      */
	@Override
	public List<Map<String, Object>> userList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "m.id as id, "
				+ "m.name as name) "
				+ "from TMemberInfo m where m.nodeid ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
   
	/**
	 * 获取体积单位
	 */
	@Override
	public List<Map<String, Object>> capacityUnitList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "u.id as id, "
				+ "u.unitName as name) "
				+ "from TUnitInfo u,TUnitType t "
				+ "where u.TUnitType.id =t.id "
				+ "and t.unitTypeName ='体积单位' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
    
	/**
	 * 获取库房信息
	 */
	@Override
	public List<Map<String, Object>> storgeList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "s.id as id, "
				+ "s.storageNo as no,"
				+ "s.positionType as positionType) "
				+ "from TStorageInfo s where s.nodeId ='"+nodeId+"' and s.storageStatus ='活动'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
    
	/**
	 * 获取数量单位
	 */
	@Override
	public List<Map<String, Object>> quantityUnitList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "u.id as id, "
				+ "u.unitName as name) "
				+ "from TUnitInfo u,TUnitType t "
				+ "where u.TUnitType.id =t.id "
				+ "and t.unitTypeName ='数量单位' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
	/**
	 * 获取重量单位
	 */
	@Override
	public List<Map<String, Object>> weightUnitList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "u.id as id, "
				+ "u.unitName as name) "
				+ "from TUnitInfo u,TUnitType t "
				+ "where u.TUnitType.id =t.id "
				+ "and t.unitTypeName ='重量单位' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
	/**
	 * 获取维度单位
	 */
	@Override
	public List<Map<String, Object>> dimensionUnitList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "u.id as id, "
				+ "u.unitName as name) "
				+ "from TUnitInfo u,TUnitType t "
				+ "where u.TUnitType.id =t.id "
				+ "and t.unitTypeName ='维度单位' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}

	@Override
	/**
	 * 批次集合
	 */
	public List<Map<String, Object>> jobPlanList(String nodeId) {
		String hql ="select new map("
				+ "a.id as id,"
				+ "a.no as no)"
				+ " from TJobplanInfo as a where nodeid ='"+nodeId+"' and planType =2";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@Override
	/**
	 * 物料集合
	 */
	public List<Map<String, Object>> materialList(String jobPlanId) {
		String sql ="select T_MATERAIL_TYPE_INFO.id as id,"
				+ " T_MATERAIL_TYPE_INFO.no as no,T_MATERAIL_TYPE_INFO.name as name,"
				+ " t_jobdispatch_material.reqNum as requirementNum,"
				+ " t_jobdispatch_material.recNum as receivedNum,"
				+ " T_PROCESS_INFO.process_order as processOrder,"
				+ " T_JOBDISPATCHLIST_INFO.no as jobdispatchNo,"
				+ " t_jobdispatch_material.id as jobdispatchMaterialId,"
				+ " t_jobdispatch_material.price as price,"
				+ " '' as materialTotalValue"
				+ " from T_JOBPLAN_INFO"
				+ " inner join T_JOBDISPATCHLIST_INFO on T_JOBDISPATCHLIST_INFO.jobplanID = T_JOBPLAN_INFO.ID"
				+ " inner join T_Process_Info on T_Process_Info.ID = T_JOBDISPATCHLIST_INFO.processID"
				+ " inner join t_jobdispatch_material on t_jobdispatch_material.jobdispatch_id = T_JOBDISPATCHLIST_INFO.ID"
				+ " inner join T_MATERAIL_TYPE_INFO on T_MATERAIL_TYPE_INFO.ID = t_jobdispatch_material.material_id"
				+ " where T_JOBPLAN_INFO.ID = "+jobPlanId+"";
//		 String hql ="select new map(b.id as id,"
//					+ " b.no as no,b.name as name,"
//					+ " e.reqNum as requirementNum,"
//					+ " e.recNum as receivedNum,"
//					+ " d.processOrder as processOrder)"
//					+ " from TJobplanInfo as a,TMaterailTypeInfo as b,TJobdispatchlistInfo as c,TProcessInfo as d"
//					+ " TJobdispatchMaterial as e"
//					+ " where a.id =c.jobplanId and c.id = e.jobdispatchId and e.materialId = b.id"
//					+ " and TJobplanInfo.ID = '"+jobPlanId+"'";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取组织物料控制信息
	 */
	public List<Map<String, Object>> materialCtrList(String materialId) {
		String hql ="select new map(a.isBatchCtrl as isBatchCtrl,a.isSeqCtrl as isSeqCtrl,a.isPositionCtrl as isPositionCtrl,"
				+ "a.isVersionCtrl as isVersionCtrl,b.unit as unitName,a.isStockCtrl as isStockCtrl,a.isAxisCtrl as isAxisCtrl)"
				+ " from TMaterialExtendStorage as a,TMaterailTypeInfo as b "
				+ " where a.materialId =b.id and a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 根据NO获取组织物料控制信息
	 */
	public List<Map<String, Object>> materialCtrByNoList(String materialNo,String nodeId) {
		String hql ="select new map(a.materialId as id,a.isBatchCtrl as isBatchCtrl,a.isSeqCtrl as isSeqCtrl,"
				+ " a.isPositionCtrl as isPositionCtrl,b.unit as unitName,a.isVersionCtrl as isVersionCtrl,"
				+ " a.isStockCtrl as isStockCtrl,a.isAxisCtrl as isAxisCtrl)"
				+ " from TMaterialExtendStorage as a,TMaterailTypeInfo as b "
				+ " where a.materialId =b.id"
				+ " and b.nodeId ='"+nodeId+"'"
				+ " and b.no ='"+materialNo+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> materialStorageList(String materialId,
			String isBatchCtrl, String isSeqCtrl) {
		String hql ="select a.id as id,b.storage_no as storageNo,"
				+ " if(isnull(c.positionNo),'',c.positionNo) as positionNo,a.available_num as availableNum,"
				+ " if(isnull(d.no),'',d.no) as batchNo,if(isnull(e.no),'',e.no) as seqNo,"
				+ " if(isnull(a.batch_id),'',a.batch_id) as batchId,"
				+ " if(isnull(a.seq_id),'',a.seq_id) as seqId,"
				+ " a.money_num as price,"
				+ " if(isnull(b.id),'',b.id) as storageId,"
				+ " if(isnull(c.id),'',c.id) as positonId,"
				+ " a.version_no as versionNo"
				+ " from t_material_storage as a"
				+ " left join t_storage_info as b on a.storage_id = b.id"
				+ " left join t_materiel_position_info as c on a.positon_id = c.id"
				+ " left join t_stock_materialbath as d on a.batch_id = d.id"
				+ " left join t_stock_materialseq as e on a.seq_id = e.id"
				+ " where a.available_num>0"
				+ " and a.material_id = "+materialId+"";
//		String hql ="";
//		if(isBatchCtrl.equals("0") && isSeqCtrl.equals("0")){//同时受序列和批次控制
//			hql+="select new map (a.id as id,b.storageNo as storageNo,"
//				+ " c.positionNo as positionNo,a.availableNum as availableNum,"
//				+ " d.no as batchNo,e.no as seqNo,a.batchId as batchId,a.seqId as seqId,"
//				+ " a.moneyNum as price,"
//				+ " b.id as storageId,c.id as positonId,a.versionNo as versionNo)"
//				+ " from TMaterialStorage as a, TStorageInfo as b,TMaterielPositionInfo as c,"
//				+ " TStockMaterialbath as d,TStockMaterialseq as e"
//				+ " where a.TStorageInfo.id =b.id and a.positonId = c.id and"
//				+ " a.batchId = d.id and a.seqId = e.id and a.availableNum>0"
//				+ " and a.TMaterailTypeInfo.id = "+materialId+"";
//		}
//		if(isBatchCtrl.equals("0") && isSeqCtrl.equals("1")){//仅受批次控制
//			hql+="select new map (a.id as id,b.storageNo as storageNo,"
//				+ " c.positionNo as positionNo,a.availableNum as availableNum,"
//				+ " d.no as batchNo,'' as seqNo,a.batchId as batchId,'' as seqId,"
//				+ " a.moneyNum as price,"
//				+ " b.id as storageId,c.id as positonId,a.versionNo as versionNo)"
//				+ " from TMaterialStorage as a, TStorageInfo as b,TMaterielPositionInfo as c,"
//				+ " TStockMaterialbath as d"
//				+ " where a.TStorageInfo.id =b.id and a.positonId = c.id and"
//				+ " a.batchId = d.id and a.availableNum>0"
//				+ " and a.TMaterailTypeInfo.id = "+materialId+""
//				+ " and (a.seqId is null or a.seqId='')";
//		}
//		if(isBatchCtrl.equals("1") && isSeqCtrl.equals("0")){//仅受序列控制
//			hql+="select new map (a.id as id,b.storageNo as storageNo,"
//				+ " c.positionNo as positionNo,a.availableNum as availableNum,"
//				+ " '' as batchNo,e.no as seqNo,'' as batchId,a.seqId as seqId,"
//				+ " a.moneyNum as price,"
//				+ " b.id as storageId,c.id as positonId,a.versionNo as versionNo)"
//				+ " from TMaterialStorage as a, TStorageInfo as b,TMaterielPositionInfo as c,"
//				+ " TStockMaterialseq as e"
//				+ " where a.TStorageInfo.id =b.id and a.positonId = c.id and"
//				+ " a.batchId = e.id and a.availableNum>0"
//				+ " and a.TMaterailTypeInfo.id = "+materialId+""
//				+ " and (a.batchId is null or a.batchId='')";
//		}
//		if(isBatchCtrl.equals("1") && isSeqCtrl.equals("1")){//不受控制
//			hql+="select new map (a.id as id,b.storageNo as storageNo,"
//				+ " c.positionNo as positionNo,a.availableNum as availableNum,"
//				+ " '' as batchNo,'' as seqNo,'' as batchId,'' as seqId,"
//				+ " a.moneyNum as price,"
//				+ " b.id as storageId,c.id as positonId,a.versionNo as versionNo)"
//				+ " from TMaterialStorage as a, TStorageInfo as b,TMaterielPositionInfo as c"
//				+ " where a.TStorageInfo.id =b.id and a.positonId = c.id"
//				+ " and a.TMaterailTypeInfo.id = "+materialId+" and a.availableNum>0"
//				+ " and (a.batchId is null or a.batchId='')"
//				+ " and (a.seqId is null or a.seqId='')";
//		}
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(hql);
		return dataList;	}

	@Override
	public List<Map<String, Object>> materialPositionList(String materialId,
			String storageId) {
		String sql ="select isBatchCtrl,isSeqCtrl from t_material_extend_storage where material_id ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql);
		return dataList;
	}

	@Override
	/**
	 * 获取事务最大流水号
	 */
	public String getMaxID() {
		String sql ="select transNo from t_transaction order by ID DESC LIMIT 1";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql);
		if(dataList.size()>0){
		    return dataList.get(0).get("transNo").toString();
		}else{
			return "0";
		}
	}

	@Override
	/**
	 * 保存出入库信息
	 */
	public String saveInfo(List<Map<String,Object>> materialInfoList,
			List<Map<String,Object>> outInStorageList,
			List<Map<String,Object>> transactionList,String type,
			String userId,String transTypeId,Date transDate,String jobPlanId) {
		try{
			//生产批次
			TJobplanInfo tJobplanInfo = new TJobplanInfo();
			tJobplanInfo = dao.get(TJobplanInfo.class,Long.parseLong(jobPlanId));
			//处理工单接收物料数量
			for(Map<String,Object> tt:materialInfoList){
				if(!tt.get("materialTotalValue").toString().equals("")){
					String id =tt.get("jobdispatchMaterialId").toString();
					Double num = Double.parseDouble(tt.get("receivedNum").toString());
					Double totalPrice = Double.parseDouble(tt.get("materialTotalValue").toString());
					
					TJobdispatchMaterial mm= new TJobdispatchMaterial();
					mm = dao.get(TJobdispatchMaterial.class,Integer.parseInt(id));
					Double oldNum = mm.getRecNum();
					Double oldPrice =mm.getPrice();
					mm.setPrice((oldNum*oldPrice+totalPrice)/num);
					mm.setRecNum(num);
					dao.update(mm);
				}
			}
			//处理库存信息（修改库存数量）
			for(Map<String,Object> tt:outInStorageList){
				int id =Integer.parseInt(tt.get("id").toString());
				int num = Integer.parseInt(tt.get("num").toString());
				
				TMaterialStorage tMaterialStorage =new TMaterialStorage();
				tMaterialStorage = dao.get(TMaterialStorage.class,id);
				Double availableNum = tMaterialStorage.getAvailableNum();
				tMaterialStorage.setAvailableNum(availableNum-num);
				dao.update(tMaterialStorage);
			}
			
			//处理事务信息
			for(Map<String,Object> tt:transactionList){
				TTransaction tTransaction = new TTransaction();
				
				
				String transNo = tt.get("transNo").toString();
				String materialId = tt.get("materialId").toString();
				String storageId = tt.get("storageId").toString();
				
				String positonId="0";
				if(null !=tt.get("positonId") && tt.get("positonId")!=""){
					positonId= tt.get("positonId").toString();
				}
				String processNum = tt.get("processNum").toString();
				Double price = Double.parseDouble(tt.get("price").toString());
				//设置参数值
				tTransaction.setTransNo(transNo);
				
				TMaterailTypeInfo mm= new TMaterailTypeInfo();
				mm =dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
				tTransaction.setTMaterailTypeInfo(mm);
				
				TStorageInfo ss= new TStorageInfo();
				ss =dao.get(TStorageInfo.class,Integer.parseInt(storageId));
				tTransaction.setTStorageInfo(ss);
				if(!positonId.equals("0")){
					tTransaction.setPositionId(Integer.parseInt(positonId));
				}
				if(Boolean.valueOf(tt.get("batchCtrl").toString())){
					if(null!=tt.get("batchId") && !tt.get("batchId").toString().equals("")){
						tTransaction.setBatchId(Integer.parseInt(tt.get("batchId").toString()));
					}
					
				}
				if(Boolean.valueOf(tt.get("seqCtrl").toString())){
					if(null!=tt.get("seqId") && !tt.get("seqId").toString().equals("")){
						tTransaction.setPositionId(Integer.parseInt(tt.get("seqId").toString()));
					}
				}
				
				tTransaction.setProcessNum(-Double.parseDouble(processNum));
				tTransaction.setOrderNo(tJobplanInfo.getNo());
				tTransaction.setTransDate(transDate);
				
				TTransactionRelation dd=new TTransactionRelation();
				dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
				tTransaction.setTTransactionRelation(dd);
				tTransaction.setPrice(price);
				tTransaction.setUserId(Integer.parseInt(userId));
				dao.save(tTransaction);
			}
			
			return "保存成功";
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "保存失败";
		}
		
		
	}

	@Override
	/**
	 * 获取事务类型集合
	 */
	public List<Map<String, Object>> transactionTypeList() {
		String sql ="select id,tans_type,tans_from from t_transaction_relation";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 根据ID获取批次信息
	 */
	public List<Map<String,Object>> getJobPlanInfoList(String jobPlanId) {
		String hql ="select new map(a.planNum as planNum,a.finishNum as finishNum,"
				+ " a.instockNum as instockNum,b.name as partName,b.no as partNo)"
				+ " from TJobplanInfo as a,TPartTypeInfo as b "
				+ " where a.TPartTypeInfo.id =b.id"
				+ " and a.id ="+jobPlanId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@Override
	/**
	 * 根据库房获取货位集合
	 */
	public List<Map<String, Object>> getPositionInfoList(String storageId) {
		String hql ="select new map(a.id as id,a.positionNo as no)"
				+ " from TMaterielPositionInfo as a,RStoragePosition as b,TStorageInfo as c "
				+ " where a.id =b.TMaterielPositionInfo.id"
				+ " and c.id=b.TStorageInfo.id"
				+ " and c.id ="+storageId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取生产批次集合
	 */
	public List<Map<String, Object>> getProductBatchList(String materialId) {
		String hql ="select new map(a.id as id,a.no as no)"
				+ " from TStockMaterialbath as a"
				+ " where a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取生产序列
	 */
	public List<Map<String, Object>> getProductSeqList(String materialId,String no,Boolean flag) {
		String hql ="select new map(a.id as id,a.no as no)"
				+ " from TStockMaterialseq as a,TMaterialStorage as b"
				+ " where a.id = b.seqId"
				+ " and a.materialId ="+materialId+"";
				if(no!=""){
					hql+= " and a.no ='"+no+"'";
				}
				if(flag){
					hql+= " and b.availableNum >0";
				}else{
					hql+= " and b.availableNum =0";
				}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
    
	/**
	 * 完工入库
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveProductInStorage(List<Map<String, Object>> dataInfo,
			List<Map<String,Object>> seqInfo,String materialId,Boolean batchCtrl,Boolean seqCtrl,String jobPlanId,int totalNum,
			String userId,String transTypeId,Date transDate,String nodeId) {
		try{ 
			//生产批次
			TJobplanInfo tJobplanInfo = new TJobplanInfo();
			tJobplanInfo = dao.get(TJobplanInfo.class,Long.parseLong(jobPlanId));
			//保存新增批次
			if(batchCtrl){
				TStockMaterialbath tStockMaterialbath =new TStockMaterialbath();
				for(Map<String,Object> tt:dataInfo){
					String hql ="select new map(a.id as id)"
					        + " from TStockMaterialbath as a"
					        + " where a.materialId ="+materialId+""
							+ " and a.no ='"+tt.get("batch").toString()+"'";
					List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()<1){
						tStockMaterialbath.setMaterialId(Integer.valueOf(materialId));
						tStockMaterialbath.setNo(tt.get("batch").toString());
						dao.save(tStockMaterialbath);
					}
				}
			}
			
			//保存新增序列
			if(seqCtrl){
				
				for(Map<String,Object> tt:seqInfo){
					TStockMaterialseq tStockMaterialseq =new TStockMaterialseq();
					String hql ="select new map(a.id as id)"
					        + " from TStockMaterialseq as a"
					        + " where a.materialId ="+materialId+""
							+ " and a.no ='"+tt.get("seqNo").toString()+"'";
					List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()<1){
						tStockMaterialseq.setMaterialId(Integer.valueOf(materialId));
						tStockMaterialseq.setNo(tt.get("seqNo").toString());
						dao.save(tStockMaterialseq);
					}
				}
			}
			
			//获取物料价格
			TMaterailTypeInfo tMaterailTypeInfo = new TMaterailTypeInfo();
			tMaterailTypeInfo =dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
			Double price =0.0;
			if(null !=tMaterailTypeInfo.getPrice() && tMaterailTypeInfo.getPrice().toString() !=""){
				price =Double.parseDouble(tMaterailTypeInfo.getPrice().toString());
			}
			
			//库存处理
			for(Map<String,Object> tt:dataInfo){
				String id = tt.get("id").toString();
				String storageId = tt.get("storageId").toString();//库房ID
				Double num = Double.parseDouble(tt.get("realNum").toString());//入库数量
				String versionNo = tt.get("versionNo").toString();//版本
				int positionId = 0;//货位
				int batchId =0;//批次ID
				int seqId =0;//序列ID
				//库房
				TStorageInfo tStorageInfo = new TStorageInfo();
	        	tStorageInfo =dao.get(TStorageInfo.class,Integer.parseInt(storageId));
	        	//货位
	        	Boolean positionCtrl = Boolean.valueOf(tt.get("positionCtrl").toString());
	        	if(positionCtrl){
	        		String no = tt.get("positionNo").toString();
	        		String hql ="select new map(a.id as id)"
	        				  + " from TMaterielPositionInfo as a "
	        				  + " where a.positionNo ='"+no+"'";
	        		List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()>0){
						positionId = Integer.parseInt(list.get(0).get("id").toString());
					}else{
						TMaterielPositionInfo tMaterielPositionInfo = new TMaterielPositionInfo();
						tMaterielPositionInfo.setPositionNo(no);
						tMaterielPositionInfo.setPositionName(no);
						tMaterielPositionInfo.setPositionStatus("1");
						dao.save(tMaterielPositionInfo);
						
						//库存货位关系表
						RStoragePosition rStoragePosition =new RStoragePosition();
						rStoragePosition.setTMaterielPositionInfo(tMaterielPositionInfo);
						rStoragePosition.setTStorageInfo(tStorageInfo);
						dao.save(rStoragePosition);
						positionId = tMaterielPositionInfo.getId();
					}
	        	}
				
				//批次设值
				if(batchCtrl){
					
					if(null !=tt.get("batch") && tt.get("batch").toString() !=""){
						String batchNo = tt.get("batch").toString();
						String hql = "select new map(a.id as id)"
						        + " from TStockMaterialbath as a"
						        + " where a.materialId ="+materialId+""
								+ " and a.no ='"+batchNo+"'";
						List<Map<String,Object>> list = dao.executeQuery(hql);
						if(list.size()>0){
							batchId = Integer.parseInt(list.get(0).get("id").toString());
						}
					}
				}
				//序列
				if(seqCtrl){//受序列控制
					for(Map<String,Object> map:seqInfo){
						if(map.get("id").toString().equals(id)){
							String hql ="select new map(a.id as id)"
	    					        + " from TStockMaterialseq as a"
	    					        + " where a.materialId ="+materialId+""
	    							+ " and a.no ='"+map.get("seqNo").toString()+"'";
	    					List<Map<String,Object>> list = dao.executeQuery(hql);
	    					if(list.size()>0){
	    						seqId = Integer.parseInt(list.get(0).get("id").toString());
	    					}
	    					
							//获取库房该物料是否存在
							String hql2 ="select new map(a.id as id,a.availableNum as availableNum,"
									+ " a.moneyNum as moneyNum)"
							        + " from TMaterialStorage as a"
							        + " where a.TMaterailTypeInfo.id ="+materialId+""
									+ " and a.TStorageInfo.id ="+storageId+""
									+ " and a.versionNo ='"+versionNo+"'"
								    + " and a.seqId ="+seqId+"";
							if(positionId == 0){//货位
								hql2 +=" and (a.positonId is null or a.positonId ='')";
							}else{
								hql2 +=" and a.positonId ="+positionId+"";
							}
		                    if(batchId == 0){//批次
		                    	hql2 +=" and (a.batchId is null or a.batchId ='')";
							}else{
								hql2 +=" and a.batchId ="+batchId+"";
							}
		                    List<Map<String,Object>> list2 = dao.executeQuery(hql2);
		                    if(list2.size()>0){
		                    	//物料杂收入库
		                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	TMaterialStorage temp =new TMaterialStorage();
		                    	temp = dao.get(TMaterialStorage.class,tempId);
		                    	temp.setAvailableNum(1.0);//现有量
		                    	temp.setMoneyNum(price);//价格
		                    	temp.setProcessDate(transDate);//处理时间
		                    	dao.update(temp);
		                    }else{
								//成品入库
								TMaterialStorage temp =new TMaterialStorage();
								temp.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
		                    	temp.setTStorageInfo(tStorageInfo);//库房
		                    	if(positionId !=0){
		                    		temp.setPositonId(positionId);//货位
		                    	}
		                    	if(batchId !=0){
		                    		temp.setBatchId(batchId);//货位
		                    	}
		                    	
		                    	temp.setSeqId(seqId);//序列ID
		                    	temp.setAvailableNum(1.0);//现有量
		                    	temp.setVersionNo(versionNo);//版本
		                    	temp.setMoneyNum(price);//价格
		                    	temp.setProcessDate(transDate);//处理时间
		                    	temp.setUnitName(tt.get("baseUnitName").toString());//单位
		                    	dao.save(temp);
		                    }
	                    	
	                    	//事务处理
	                        TTransaction tTransaction = new TTransaction();
	                        tTransaction.setTransNo(tt.get("transNo").toString());//事务编号
	                        tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
	                        tTransaction.setTStorageInfo(tStorageInfo);//库房
	                        if(positionId !=0){
	                        	tTransaction.setPositionId(positionId);//货位
	                    	}
	                        tTransaction.setProcessNum(1.0);//处理数量
	                        tTransaction.setPrice(price);//价格
	                        tTransaction.setOrderNo(tJobplanInfo.getNo());//订单号（即来源）
	                        tTransaction.setTransDate(transDate);//处理时间
	                        if(batchId !=0){
	                        	tTransaction.setBatchId(batchId);//批次
	                    	}
	                        tTransaction.setSeqId(seqId);//序列
	                        TTransactionRelation dd= new TTransactionRelation();
	                        dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	        				tTransaction.setTTransactionRelation(dd);
	        				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
	        				dao.save(tTransaction);
						}
					}
					
				}else{
					//获取库房该物料是否存在
					String hql ="select new map(a.id as id,a.availableNum as availableNum,"
							+ " a.moneyNum as moneyNum)"
					        + " from TMaterialStorage as a"
					        + " where a.TMaterailTypeInfo.id ="+materialId+""
							+ " and a.TStorageInfo.id ="+storageId+""
							+ " and a.versionNo ='"+versionNo+"'"
						    + " and (a.seqId is null or a.seqId ='')";
					if(positionId == 0){//货位
						hql +=" and (a.positonId is null or a.positonId ='')";
					}else{
						hql +=" and a.positonId ="+positionId+"";
					}
                    if(batchId == 0){//批次
                    	hql +=" and (a.batchId is null or a.batchId ='')";
					}else{
						hql +=" and a.batchId ="+batchId+"";
					}
                    List<Map<String,Object>> list = dao.executeQuery(hql);
                    if(list.size()>0){
                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
                    	Double tempNum = Double.parseDouble(list.get(0).get("availableNum").toString());
                    	Double tempPrice =Double.parseDouble(list.get(0).get("moneyNum").toString());
                    	Double inSoragePrice = (tempNum*tempPrice+num*price)/(tempNum+num);//入库价格
                    	TMaterialStorage temp =new TMaterialStorage();
                    	
                    	temp = dao.get(TMaterialStorage.class,tempId);
                    	temp.setAvailableNum(num+tempNum);
                    	temp.setMoneyNum(inSoragePrice);
                    	temp.setProcessDate(transDate);//处理时间
                    	dao.update(temp);
                    }else{
                    	TMaterialStorage temp =new TMaterialStorage();
                    	temp.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
                    	temp.setTStorageInfo(tStorageInfo);//库房
                    	if(positionId !=0){
                    		temp.setPositonId(positionId);//货位
                    	}
                    	if(batchId !=0){
                    		temp.setBatchId(batchId);//货位
                    	}
                    	temp.setAvailableNum(num);
                    	temp.setMoneyNum(price);
                    	temp.setVersionNo(versionNo);//版本
                    	temp.setProcessDate(transDate);//处理时间
                    	temp.setUnitName(tt.get("baseUnitName").toString());//单位
                    	dao.save(temp);
                    }
                    
                    //事务处理
                    TTransaction tTransaction = new TTransaction();
                    tTransaction.setTransNo(tt.get("transNo").toString());//事务编号
                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
                    tTransaction.setTStorageInfo(tStorageInfo);//库房
                    if(positionId !=0){
                    	tTransaction.setPositionId(positionId);//货位
                	}
                    tTransaction.setProcessNum(num);//处理数量
                    tTransaction.setPrice(price);//价格
                    tTransaction.setOrderNo(tJobplanInfo.getNo());//订单号（即来源）
                    tTransaction.setTransDate(transDate);//处理时间
                    if(batchId !=0){
                    	tTransaction.setBatchId(batchId);//批次
                	}
                    TTransactionRelation dd= new TTransactionRelation();
                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
    				tTransaction.setTTransactionRelation(dd);
    				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
    				dao.save(tTransaction);
				}
			}
			//更新生产批次信息
			
			int tempNum =0;
			if(null != tJobplanInfo.getInstockNum()){
				tempNum = tJobplanInfo.getInstockNum();
			}
			tJobplanInfo.setInstockNum(totalNum+tempNum);
			dao.update(tJobplanInfo);
			
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "保存失败";
		}
		return "保存成功";
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取来源类型集合
	 */
	public List<Map<String, Object>> getTransFromTypeList() {
		String hql ="select distinct new map(a.tansFrom as tansFrom)"
				+ " from TTransactionRelation as a";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取事务类型集合
	 */
	public List<Map<String, Object>> getTransTypeList(String transFrom) {
		String hql ="select new map(a.id as id,a.tansType as tansType,a.tansActivity as tansActivity)"
				+ " from TTransactionRelation as a"
				+ " where a.tansFrom ='"+transFrom+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 物料信息集合
	 */
	public List<Map<String, Object>> getMaterialInfoList(String nodeId) {
		String hql ="select new map(a.id as id,a.no as no,a.name as name,b.isBatchCtrl as isBatchCtrl,b.isSeqCtrl as isSeqCtrl,"
				+ " b.isPositionCtrl as isPositionCtrl,a.unit as unitName,b.isVersionCtrl as isVersionCtrl,"
				+ " b.isStockCtrl as isStockCtrl,b.isAxisCtrl as isAxisCtrl,b.isPositionCtrl as isPositionCtrl)"
				+ " from TMaterailTypeInfo as a,TMaterialExtendStorage as b"
				+ " where a.id =b.materialId"
				+ " and a.nodeId ='"+nodeId+"'";
				
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;

	}
	
	/**
	 * 库存中物料信息集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMaterialInfoList2(String nodeId) {
		String hql ="select distinct new map(a.id as id,a.no as no,a.name as name)"
				+ " from TMaterailTypeInfo as a,TMaterialExtendStorage as b,TMaterialStorage as c"
				+ " where a.id =b.materialId"
				+ " and a.id = c.TMaterailTypeInfo.id"
				+ " and a.nodeId ='"+nodeId+"'";
				
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 物料杂收入库
	 */
	public String saveMaterialInStorage(List<Map<String, Object>> dataInfo,
			List<Map<String, Object>> seqInfo, String source,
			String userId, String transTypeId, Date transDate) {
		try{
			//库存处理
			for(Map<String,Object> tt:dataInfo){
				Boolean batchCtrl =Boolean.valueOf(tt.get("isBatchCtrl").toString());//批次控制
				Boolean seqCtrl =Boolean.valueOf(tt.get("isSeqCtrl").toString());//序列控制
				String materialId =  tt.get("materialId").toString();//物料ID
				
				String id = tt.get("id").toString();
				String storageId = tt.get("storageId").toString();//库房ID
				Double num = Double.parseDouble(tt.get("realNum").toString());//入库数量
				String versionNo =tt.get("versionNo").toString();//版本
				int positionId = 0;//货位
				int batchId =0;//批次ID
				int seqId =0;//序列ID
				
				//获取物料价格
				TMaterailTypeInfo tMaterailTypeInfo = new TMaterailTypeInfo();
				tMaterailTypeInfo =dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
				Double price =0.000;
				if(null !=tMaterailTypeInfo.getPrice() && tMaterailTypeInfo.getPrice().toString() !=""){
					price =Double.parseDouble(tMaterailTypeInfo.getPrice().toString());
				}
				
				//库房
				TStorageInfo tStorageInfo = new TStorageInfo();
	        	tStorageInfo =dao.get(TStorageInfo.class,Integer.parseInt(storageId));
	        	//货位
	        	Boolean positionCtrl = Boolean.valueOf(tt.get("positionCtrl").toString());
	        	if(positionCtrl){
	        		String no = tt.get("positionNo").toString();
	        		String hql ="select new map(a.id as id)"
	        				  + " from TMaterielPositionInfo as a "
	        				  + " where a.positionNo ='"+no+"'";
	        		List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()>0){
						positionId = Integer.parseInt(list.get(0).get("id").toString());
					}else{
						TMaterielPositionInfo tMaterielPositionInfo = new TMaterielPositionInfo();
						tMaterielPositionInfo.setPositionNo(no);
						tMaterielPositionInfo.setPositionName(no);
						tMaterielPositionInfo.setPositionStatus("1");
						dao.save(tMaterielPositionInfo);
						
						//库存货位关系表
						RStoragePosition rStoragePosition =new RStoragePosition();
						rStoragePosition.setTMaterielPositionInfo(tMaterielPositionInfo);
						rStoragePosition.setTStorageInfo(tStorageInfo);
						dao.save(rStoragePosition);
						positionId = tMaterielPositionInfo.getId();
					}
	        	}
	        	
	        	//批次
				if(batchCtrl){
					TStockMaterialbath tStockMaterialbath =new TStockMaterialbath();
					String hql ="select new map(a.id as id)"
					        + " from TStockMaterialbath as a"
					        + " where a.materialId ="+materialId+""
							+ " and a.no ='"+tt.get("batch").toString()+"'";
					List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()<1){
						tStockMaterialbath.setMaterialId(Integer.valueOf(materialId));
						tStockMaterialbath.setNo(tt.get("batch").toString());
						dao.save(tStockMaterialbath);
						batchId = tStockMaterialbath.getId();
					}else{
						batchId = Integer.parseInt(list.get(0).get("id").toString());
					}
				}
				
				//序列
				if(seqCtrl){//受序列控制
					for(Map<String,Object> map:seqInfo){
						if(map.get("id").toString().equals(id)){
							String hql ="select new map(a.id as id)"
	    					        + " from TStockMaterialseq as a"
	    					        + " where a.materialId ="+materialId+""
	    							+ " and a.no ='"+map.get("seqNo").toString()+"'";
	    					List<Map<String,Object>> list = dao.executeQuery(hql);
	    					if(list.size()>0){
	    						seqId = Integer.parseInt(list.get(0).get("id").toString());
	    					}else{
	    						TStockMaterialseq tStockMaterialseq = new TStockMaterialseq();
	    						tStockMaterialseq.setMaterialId(Integer.valueOf(materialId));
	    						tStockMaterialseq.setNo(map.get("seqNo").toString());
	    						dao.save(tStockMaterialseq);
	    						seqId = tStockMaterialseq.getId();
	    					}
	    					
							//获取库房该物料是否存在
							String hql2 ="select new map(a.id as id,a.availableNum as availableNum,"
									+ " a.moneyNum as moneyNum)"
							        + " from TMaterialStorage as a"
							        + " where a.TMaterailTypeInfo.id ="+materialId+""
									+ " and a.TStorageInfo.id ="+storageId+""
									+ " and a.versionNo ='"+versionNo+"'"
								    + " and a.seqId ="+seqId+"";
							if(positionId == 0){//货位
								hql2 +=" and (a.positonId is null or a.positonId ='')";
							}else{
								hql2 +=" and a.positonId ="+positionId+"";
							}
		                    if(batchId == 0){//批次
		                    	hql2 +=" and (a.batchId is null or a.batchId ='')";
							}else{
								hql2 +=" and a.batchId ="+batchId+"";
							}
		                    List<Map<String,Object>> list2 = dao.executeQuery(hql2);
		                    if(list2.size()>0){
		                    	//物料杂收入库
		                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	TMaterialStorage temp =new TMaterialStorage();
		                    	temp = dao.get(TMaterialStorage.class,tempId);
		                    	temp.setAvailableNum(1.0);//现有量
		                    	temp.setMoneyNum(price);//价格
		                    	temp.setProcessDate(transDate);//处理时间
		                    	dao.update(temp);
		                    }else{
		                    	//物料杂收入库
								TMaterialStorage temp =new TMaterialStorage();
								temp.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
		                    	temp.setTStorageInfo(tStorageInfo);//库房
		                    	if(positionId !=0){
		                    		temp.setPositonId(positionId);//货位
		                    	}
		                    	if(batchId !=0){
		                    		temp.setBatchId(batchId);//批次
		                    	}
		                    	
		                    	
		                    	temp.setSeqId(seqId);//序列ID
		                    	temp.setAvailableNum(1.0);//现有量
		                    	temp.setMoneyNum(price);//价格
		                    	temp.setProcessDate(transDate);//处理时间
		                    	temp.setVersionNo(versionNo);//版本
		                    	temp.setUnitName(tt.get("baseUnitName").toString());
		                    	dao.save(temp);
		                    }
	                    	//事务处理
	                        TTransaction tTransaction = new TTransaction();
	                        tTransaction.setTransNo(tt.get("transNo").toString());//事务编号
	                        tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
	                        tTransaction.setTStorageInfo(tStorageInfo);//库房
	                        if(positionId !=0){
	                        	tTransaction.setPositionId(positionId);//货位
	                    	}
	                        tTransaction.setProcessNum(1.0);//处理数量
	                        tTransaction.setPrice(price);//价格
	                        tTransaction.setOrderNo(source);//订单号（即来源）
	                        tTransaction.setTransDate(transDate);//处理时间
	                        if(batchId !=0){
	                        	tTransaction.setBatchId(batchId);//批次
	                    	}
	                        tTransaction.setSeqId(seqId);//序列
	                        TTransactionRelation dd= new TTransactionRelation();
	                        dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	        				tTransaction.setTTransactionRelation(dd);
	        				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
	        				dao.save(tTransaction);
						}
					}
					
				}else{
					//获取库房该物料是否存在
					String hql ="select new map(a.id as id,a.availableNum as availableNum,"
							+ " a.moneyNum as moneyNum)"
					        + " from TMaterialStorage as a"
					        + " where a.TMaterailTypeInfo.id ="+materialId+""
							+ " and a.TStorageInfo.id ="+storageId+""
							+ " and a.versionNo ='"+versionNo+"'"
						    + " and (a.seqId is null or a.seqId ='')";
					if(positionId == 0){//货位
						hql +=" and (a.positonId is null or a.positonId ='')";
					}else{
						hql +=" and a.positonId ="+positionId+"";
					}
                    if(batchId == 0){//批次
                    	hql +=" and (a.batchId is null or a.batchId ='')";
					}else{
						hql +=" and a.batchId ="+batchId+"";
					}
                    List<Map<String,Object>> list = dao.executeQuery(hql);
                    if(list.size()>0){
                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
                    	Double tempNum = Double.parseDouble(list.get(0).get("availableNum").toString());
                    	Double tempPrice =Double.parseDouble(list.get(0).get("moneyNum").toString());
                    	Double inSoragePrice = (tempNum*tempPrice+num*price)/(tempNum+num);//入库价格
                    	TMaterialStorage temp =new TMaterialStorage();
                    	
                    	temp = dao.get(TMaterialStorage.class,tempId);
                    	temp.setAvailableNum(num+tempNum);
                    	temp.setMoneyNum(inSoragePrice);
                    	dao.update(temp);
                    }else{
                    	TMaterialStorage temp =new TMaterialStorage();
                    	temp.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
                    	temp.setTStorageInfo(tStorageInfo);//库房
                    	if(positionId !=0){
                    		temp.setPositonId(positionId);//货位
                    	}
                    	if(batchId !=0){
                    		temp.setBatchId(batchId);//货位
                    	}
                    	temp.setAvailableNum(num);
                    	temp.setUnitName(tt.get("unitName").toString());
                    	temp.setMoneyNum(price);
                    	temp.setProcessDate(transDate);
                    	temp.setVersionNo(versionNo);//版本
                    	temp.setUnitName(tt.get("baseUnitName").toString());
                    	dao.save(temp);
                    }
                    
                    //事务处理
                    TTransaction tTransaction = new TTransaction();
                    tTransaction.setTransNo(tt.get("transNo").toString());//事务编号
                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
                    tTransaction.setTStorageInfo(tStorageInfo);//库房
                    if(positionId !=0){
                    	tTransaction.setPositionId(positionId);//货位
                	}
                    tTransaction.setProcessNum(num);//处理数量
                    tTransaction.setPrice(price);//价格
                    tTransaction.setOrderNo(source);//订单号（即来源）
                    tTransaction.setTransDate(transDate);//处理时间
                    if(batchId !=0){
                    	tTransaction.setBatchId(batchId);//批次
                	}
                    TTransactionRelation dd= new TTransactionRelation();
                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
    				tTransaction.setTTransactionRelation(dd);
    				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
    				dao.save(tTransaction);
				}
			}
			
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "保存失败";
		}
		return "保存成功";
	}

	@Override
	/**
	 * 物料杂发出库
	 */
	public String saveMaterialOutStorage(List<Map<String, Object>> dataInfo,
			String transNo, String materialId,
			Boolean batchCtrl, Boolean seqCtrl, String source,
			String userId, String transTypeId, Date transDate) {
		try{
			for(Map<String,Object> map:dataInfo){
				Double num =Double.valueOf(map.get("num").toString());
				if(num>0){
					int id= Integer.parseInt(map.get("id").toString());//库存ID
					TMaterialStorage temp =new TMaterialStorage();
					temp = dao.get(TMaterialStorage.class,id);
					Double availableNum =temp.getAvailableNum(); //库存现有量
					if(num>availableNum){
						return "保存失败,数量有问题";
					}else{
						temp.setAvailableNum(availableNum-num);
						temp.setProcessDate(transDate);//处理时间
						dao.update(temp);
						
						//事务处理
	                    TTransaction tTransaction = new TTransaction();
	                    tTransaction.setTransNo(transNo);//事务编号
	                    
	                    TMaterailTypeInfo tMaterailTypeInfo =new TMaterailTypeInfo();
	                    tMaterailTypeInfo = dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
	                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
	                    
	                    TStorageInfo tStorageInfo = new TStorageInfo();
	                    tStorageInfo = dao.get(TStorageInfo.class,Integer.parseInt(map.get("storageId").toString()));
	                    tTransaction.setTStorageInfo(tStorageInfo);//库房
	                    if(null!=map.get("positonId") && !map.get("positonId").toString().equals("")){
	                    	tTransaction.setPositionId(Integer.parseInt(map.get("positonId").toString()));//货位
	                	}
	                    tTransaction.setProcessNum(-num);//处理数量
	                    tTransaction.setPrice(temp.getMoneyNum());//价格
	                    tTransaction.setOrderNo(source);//订单号（即来源）
	                    tTransaction.setTransDate(transDate);//处理时间
	                    if(batchCtrl){
	                    	if(null!=map.get("batchId") && !map.get("batchId").toString().equals("")){
	                    		tTransaction.setBatchId(Integer.parseInt(map.get("batchId").toString()));//批次
	                    	}
	                	}
	                    if(seqCtrl){
	                    	if(null!=map.get("seqId") && !map.get("seqId").toString().equals("")){
	                    		tTransaction.setBatchId(Integer.parseInt(map.get("seqId").toString()));//序列
	                    	}
	                	}
	                    TTransactionRelation dd= new TTransactionRelation();
	                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	    				tTransaction.setTTransactionRelation(dd);
	    				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
	    				dao.save(tTransaction);
						
					}
				}
			}
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "保存失败";
		}
		
		return "保存成功";
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 物料库存库房集合List
	 */
	public List<Map<String, Object>> getMaterialStorageList(String materialId,String nodeId) {
		String hql ="select new map(a.id as id,b.id as storageId,b.storageNo as no,a.availableNum as availableNum,"
				+ " b.positionType as positionType)"
				+ " from TMaterialStorage as a,TStorageInfo as b"
				+ " where a.TStorageInfo.id =b.id and a.TMaterailTypeInfo.id ="+materialId+""
				+ " and b.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 物料库存货位集合List
	 */
	public List<Map<String, Object>> getMaterialStoragePositionList(
			String materialId, String storageId) {
		String hql ="select new map(a.id as id,b.id as positionId,b.positionNo as no,a.availableNum as availableNum)"
				+ " from TMaterialStorage as a,TMaterielPositionInfo as b"
				+ " where a.positonId =b.id and a.TMaterailTypeInfo.id ="+materialId+""
				+ " and a.TStorageInfo.id ="+storageId+" and b.positionStatus ='1'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 物料库存批次集合List
	 */
	public List<Map<String, Object>> getMaterialStorageBatchList(
			String materialId, String storageId, String positionId) {
		String hql ="select new map(a.id as id,b.id as batchId,b.no as no,a.availableNum as availableNum)"
				+ " from TMaterialStorage as a,TStockMaterialbath as b"
				+ " where a.batchId =b.id and a.TMaterailTypeInfo.id ="+materialId+""
				+ " and a.TStorageInfo.id ="+storageId+"";
		if(null == positionId || positionId ==""){
			//hql +=" and (a.positonId = null or a.positonId ='')";
		}else{
			hql +=" and a.positonId ="+positionId+"";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 物料库存序列集合List
	 */
	public List<Map<String, Object>> getMaterialStorageSeqList(
			String materialId, String storageId, String positionId,
			String batchId,String seqId) {
		String hql ="select new map(a.id as id,b.id as seqId,b.no as no,a.availableNum as availableNum)"
				+ " from TMaterialStorage as a,TStockMaterialseq as b"
				+ " where a.seqId =b.id and a.TMaterailTypeInfo.id ="+materialId+""
				+ " and a.TStorageInfo.id ="+storageId+" and a.availableNum>0";
		if(null == positionId || positionId ==""){
			//hql +=" and (a.positonId = null or a.positonId ='')";
		}else{
			hql +=" and a.positonId ="+positionId+"";
		}
		
		if(null == batchId || batchId.equals("")){
			//hql +=" and (a.batchId =null or a.batchId ='')";
		}else{
			hql +=" and a.batchId ="+batchId+"";
		}
		if(null ==seqId || seqId.equals("")){
			//hql +=" and (a.seqId =null or a.seqId ='')";
		}else{
			hql +=" and a.seqId ="+seqId+"";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMaterialStorageVersionList(
			String materialId, String storageId, String positionId,
			String batchId, String seqId, String versionNo) {
		String hql ="select new map(a.id as id,a.availableNum as availableNum)"
				+ " from TMaterialStorage as a"
				+ " where a.TMaterailTypeInfo.id ="+materialId+""
				+ " and a.TStorageInfo.id ="+storageId+" and a.availableNum>0";
		if(null == positionId || positionId ==""){
			
		}else{
			hql +=" and a.positonId ="+positionId+"";
		}
		
		if(null == batchId || batchId.equals("")){
			
		}else{
			hql +=" and a.batchId ="+batchId+"";
		}
		if(null ==seqId || seqId.equals("")){
			
		}else{
			hql +=" and a.seqId ="+seqId+"";
		}
		
        if(null ==versionNo || versionNo.equals("")){
			
		}else{
			hql +=" and a.versionNo ="+versionNo+"";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 单位换算率集合List
	 */
	public List<Map<String, Object>> getUnitRateList(String unitName,
			String nodeId) {
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		//String hql ="select new map(a.id as id)"
		//		+ " from TUnitInfo as a"
		//		+ " where a.unitName ='"+unitName+"'"
		//		+ " and a.nodeId ='"+nodeId+"'";
		//List<Map<String,Object>> tempList = dao.executeQuery(hql);
		//for(Map<String,Object> map:tempList){
		//	String id = map.get("id").toString();
		String hql2 = "select new map(a.unitName as name,b.ratio as rate)"
				+ " from TUnitInfo as a,TUnitConversion as b,TUnitType as c"
				+ " where a.id = b.TUnitInfo.id and b.refUnitId = c.id"
				+ " and a.nodeId ='"+nodeId+"' and c.unitName ='"+unitName+"'";
		dataList = dao.executeQuery(hql2);
		//}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", unitName);
		map.put("rate", "1");
		dataList.add(map);
		return dataList;
	}
    
	/**
	 * 转移子库存保存
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveTransferMaterial(List<Map<String, Object>> dataInfo,
			String source, String userId, String transTypeId, Date transDate) {
		try{
			for(Map<String,Object> map:dataInfo){
				Double num =Double.valueOf(map.get("realNum").toString());//实际数量
				if(num>0){
					String transNo = map.get("transNo").toString();//事务编号
					String versionNo = map.get("versionNo").toString();//物料版本
					
					int id= Integer.parseInt(map.get("storageMaterialId").toString());//库存ID
					
					Long materialId = Long.parseLong(map.get("materialId").toString());//物料ID
					TMaterailTypeInfo tMaterailTypeInfo =new TMaterailTypeInfo();
                    tMaterailTypeInfo = dao.get(TMaterailTypeInfo.class,materialId);
					
					TMaterialStorage temp =new TMaterialStorage();
					temp = dao.get(TMaterialStorage.class,id);
					Double availableNum =temp.getAvailableNum(); //库存现有量
					
					TStorageInfo tStorageInfo = new TStorageInfo();//来源库房
	                tStorageInfo = dao.get(TStorageInfo.class,temp.getTStorageInfo().getId());
	                
	                TStorageInfo tStorageInfo2 = new TStorageInfo();//目标库房
	                tStorageInfo2 = dao.get(TStorageInfo.class,Integer.parseInt(map.get("transferStorageId").toString()));
					if(num>availableNum){
						return "保存失败,数量有问题";
					}else{
						Boolean positionCtrl =Boolean.parseBoolean(map.get("positionCtrl").toString());
						Boolean batchCtrl =Boolean.parseBoolean(map.get("isBatchCtr").toString());
	                    Boolean seqCtrl =Boolean.parseBoolean(map.get("isSeqCtr").toString());
						//出库---------------------------------出库-------->>>>>>>>>>>>>>>>>>>>>>>>>
						temp.setAvailableNum(availableNum-num);
						temp.setProcessDate(transDate);//处理时间
						dao.update(temp);
						//事务处理(出库)
	                    TTransaction tTransactionOut = new TTransaction();
	                    tTransactionOut.setTransNo(transNo);//事务编号
	                    tTransactionOut.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
	                    
	                   
	                    tTransactionOut.setTStorageInfo(tStorageInfo);//库房
	                    if(positionCtrl){
	                    	tTransactionOut.setPositionId(temp.getPositonId());//货位
	                	}
	                    tTransactionOut.setProcessNum(-num);//处理数量（出库负数）
	                    tTransactionOut.setPrice(temp.getMoneyNum());//价格
	                    tTransactionOut.setOrderNo(source);//订单号（即来源）
	                    tTransactionOut.setTransDate(transDate);//处理时间
	                    
	                    
	                    if(batchCtrl){
	                    	tTransactionOut.setBatchId(Integer.parseInt(map.get("batchId").toString()));//批次
	                	}
	                    if(seqCtrl){
	                    	tTransactionOut.setSeqId(Integer.parseInt(map.get("seqId").toString()));//序列
	                	}
	                    TTransactionRelation dd= new TTransactionRelation();
	                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	                    tTransactionOut.setTTransactionRelation(dd);
	                    tTransactionOut.setUserId(Integer.parseInt(userId));//人员Id
	    				dao.save(tTransactionOut);
	    				//-----------------------------------出库-----------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	    				
						//入库---------------------------入库-------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	    				int positionId = 0;//货位
	    				//货位
	    	        	Boolean positionCtrl2 = Boolean.valueOf(map.get("positionCtrl2").toString());
	    	        	if(positionCtrl2){
	    	        		String no = map.get("transferPositionNo").toString();
	    	        		String hql ="select new map(a.id as id)"
	    	        				  + " from TMaterielPositionInfo as a "
	    	        				  + " where a.positionNo ='"+no+"'";
	    	        		List<Map<String,Object>> list = dao.executeQuery(hql);
	    					if(list.size()>0){
	    						positionId = Integer.parseInt(list.get(0).get("id").toString());
	    					}else{
	    						TMaterielPositionInfo tMaterielPositionInfo = new TMaterielPositionInfo();
	    						tMaterielPositionInfo.setPositionNo(no);
	    						tMaterielPositionInfo.setPositionName(no);
	    						tMaterielPositionInfo.setPositionStatus("1");
	    						dao.save(tMaterielPositionInfo);
	    						
	    						//库存货位关系表
	    						RStoragePosition rStoragePosition =new RStoragePosition();
	    						rStoragePosition.setTMaterielPositionInfo(tMaterielPositionInfo);
	    						rStoragePosition.setTStorageInfo(tStorageInfo);
	    						dao.save(rStoragePosition);
	    						positionId = tMaterielPositionInfo.getId();
	    					}
	    	        	}
	    				//获取库房该物料是否存在
						String hql ="select new map(a.id as id,a.availableNum as availableNum,"
								+ " a.moneyNum as moneyNum)"
						        + " from TMaterialStorage as a"
						        + " where a.TMaterailTypeInfo.id ="+materialId+""
						        + " and a.versionNo ='"+versionNo+"'"
								+ " and a.TStorageInfo.id ="+map.get("transferStorageId").toString()+"";
						if(!positionCtrl2){//货位
							hql +=" and (a.positonId is null or a.positonId ='')";
						}else{
							hql +=" and a.positonId ="+positionId+"";
						}
	                    if(!batchCtrl){//批次
	                    	hql +=" and (a.batchId is null or a.batchId ='')";
						}else{
							hql +=" and a.batchId ="+map.get("batchId").toString()+"";
						}
	                    if(!seqCtrl){//批次
	                    	hql +=" and (a.seqId is null or a.seqId ='')";
						}else{
							hql +=" and a.seqId ="+map.get("seqId").toString()+"";
						}
	                    List<Map<String,Object>> list = dao.executeQuery(hql);
						if(seqCtrl){//受序列控制
							//物料入库
							if(list.size()>0){
								int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	TMaterialStorage tt =new TMaterialStorage();
		                    	
		                    	tt = dao.get(TMaterialStorage.class,tempId);
		                    	tt.setAvailableNum(1.0);
		                    	tt.setMoneyNum(temp.getMoneyNum());
		                    	temp.setProcessDate(transDate);//处理时间
		                    	dao.update(tt);
							}else{
								TMaterialStorage tMaterialStorage =new TMaterialStorage();
								tMaterialStorage.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
								tMaterialStorage.setTStorageInfo(tStorageInfo2);//目标库房
		                    	if(positionCtrl2){
		                    		tMaterialStorage.setPositonId(positionId);//目标货位
		                    	}
		                    	if(batchCtrl){
		                    		tMaterialStorage.setBatchId(Integer.parseInt(map.get("batchId").toString()));//货位
		                    	}
		                    	
		                    	
		                    	tMaterialStorage.setSeqId(Integer.parseInt(map.get("seqId").toString()));//序列ID
		                    	tMaterialStorage.setAvailableNum(num);//现有量
		                    	tMaterialStorage.setUnitName(map.get("baseUnitName").toString());
		                    	tMaterialStorage.setMoneyNum(temp.getMoneyNum());//价格
		                    	tMaterialStorage.setProcessDate(transDate);//处理时间
		                    	tMaterialStorage.setVersionNo(versionNo);//物料版本
		                    	dao.save(tMaterialStorage);
							}
	                    	//事务处理
	                        TTransaction tTransaction = new TTransaction();
	                        tTransaction.setTransNo(transNo);//事务编号
	                        tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
	                        tTransaction.setTStorageInfo(tStorageInfo2);//库房
	                        if(positionCtrl2){
	                        	tTransaction.setPositionId(positionId);//货位
	                    	}
	                        tTransaction.setProcessNum(num);//处理数量
	                        tTransaction.setPrice(temp.getMoneyNum());//价格
	                        tTransaction.setOrderNo(source);//订单号（即来源）
	                        tTransaction.setTransDate(transDate);//处理时间
	                        if(batchCtrl){
	                        	tTransaction.setBatchId(Integer.parseInt(map.get("batchId").toString()));//批次
	                    	}
	                        tTransaction.setSeqId(Integer.parseInt(map.get("seqId").toString()));//序列
	                        TTransactionRelation tTransactionRelation= new TTransactionRelation();//事务类型
	                        tTransactionRelation = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	        				tTransaction.setTTransactionRelation(tTransactionRelation);
	        				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
	        				dao.save(tTransaction);
	        				
						  }else{
		                    if(list.size()>0){
		                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	Double tempNum = Double.parseDouble(list.get(0).get("availableNum").toString());
		                    	Double tempPrice =Double.parseDouble(list.get(0).get("moneyNum").toString());
		                    	Double inSoragePrice = (tempNum*tempPrice+num*temp.getMoneyNum())/(tempNum+num);//入库价格
		                    	TMaterialStorage tt =new TMaterialStorage();
		                    	
		                    	tt = dao.get(TMaterialStorage.class,tempId);
		                    	tt.setAvailableNum(num+tempNum);
		                    	tt.setMoneyNum(inSoragePrice);
		                    	dao.update(tt);
		                    }else{
		                    	TMaterialStorage tt =new TMaterialStorage();
		                    	tt.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
		                    	tt.setTStorageInfo(tStorageInfo);//库房
		                    	if(positionCtrl2){
		                    		tt.setPositonId(positionId);//货位
		                    	}
		                    	if(batchCtrl){
		                    		temp.setBatchId(Integer.parseInt(map.get("batchId").toString()));//批次
		                    	}
		                    	tt.setAvailableNum(num);
		                    	tt.setUnitName(map.get("baseUnitName").toString());
		                    	tt.setMoneyNum(temp.getMoneyNum());
		                    	tt.setProcessDate(transDate);
		                    	tt.setVersionNo(versionNo);//物料版本
		                    	dao.save(tt);
		                    }
		                    
		                    //事务处理
		                    TTransaction tTransaction = new TTransaction();
		                    tTransaction.setTransNo(transNo);//事务编号
		                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
		                    tTransaction.setTStorageInfo(tStorageInfo);//库房
		                    if(positionCtrl2){
		                    	tTransaction.setPositionId(positionId);//货位
		                	}
		                    tTransaction.setProcessNum(num);//处理数量
		                    tTransaction.setPrice(temp.getMoneyNum());//价格
		                    tTransaction.setOrderNo(source);//订单号（即来源）
		                    tTransaction.setTransDate(transDate);//处理时间
		                    if(batchCtrl){
		                    	tTransaction.setBatchId(Integer.parseInt(map.get("batchId").toString()));//批次
		                	}
		                    TTransactionRelation tTransactionRelation= new TTransactionRelation();
		                    tTransactionRelation = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
		    				tTransaction.setTTransactionRelation(tTransactionRelation);
		    				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
		    				dao.save(tTransaction);
						}
					  }
				}
			}
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "保存失败";
		}
		
		return "保存成功";
	}
    
	/**
	 * 获取物料版本信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> materialVersionList(String materialId) {
		String hql ="select new map(a.id as id,a.materialId as materialId,"
				+ " a.versionNo as no,a.isDefault as isDefault)"
				+ " from TMaterialVersion as a"
				+ " where a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	
	/**
	 * 获取库存物料版本信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> storageMaterialVersionList(String materialId,String storageId,
			String positionId,String batchId,String seqId) {
		String hql ="select distinct new map(a.versionNo as no)"
				+ " from TMaterialStorage as a"
				+ " where a.TMaterailTypeInfo.id ="+materialId+"";
		if(null !=storageId && !storageId.equals("")){
			hql +=" and a.TStorageInfo.id ="+storageId+"";
		}
		if(null !=positionId && !positionId.equals("")){
			hql +=" and a.positonId ="+positionId+"";
		}
		if(null !=batchId && !batchId.equals("")){
			hql +=" and a.batchId ="+batchId+"";
		}
		if(null !=seqId && !seqId.equals("")){
			hql +=" and a.seqId ="+seqId+"";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	/**
	 * 工单退料信息保存
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveJobDispatchMaterialInStorage(
			List<Map<String, Object>> materialInfoList,
			List<Map<String, Object>> outInStorageList,
			List<Map<String, Object>> productSeqList, String userId,
			String transTypeId, Date transDate, String jobPlanId) {
		try{
			//生产批次
			TJobplanInfo tJobplanInfo = new TJobplanInfo();
			tJobplanInfo = dao.get(TJobplanInfo.class,Long.parseLong(jobPlanId));
			//处理工单接收物料数量
			for(Map<String,Object> tt:materialInfoList){
				if(Double.parseDouble(tt.get("inStorageNum").toString())>0){
					String id =tt.get("jobdispatchMaterialId").toString();
					
					Double num = Double.parseDouble(tt.get("receivedNum").toString());
					Double instorageNum =Double.parseDouble(tt.get("inStorageNum").toString());
					
					TJobdispatchMaterial mm= new TJobdispatchMaterial();
					mm = dao.get(TJobdispatchMaterial.class,Integer.parseInt(id));
					mm.setRecNum(num-instorageNum);
					dao.save(mm);
				}
			}
			//入库处理
			for(Map<String,Object> tt:outInStorageList){
				//控制信息
				Boolean positionCtrl =Boolean.parseBoolean(tt.get("positionCtrl").toString());
				Boolean batchCtrl =Boolean.parseBoolean(tt.get("isBatchCtrl").toString());
                Boolean seqCtrl =Boolean.parseBoolean(tt.get("isSeqCtrl").toString());
                
				String id = tt.get("id").toString(); 
				
				String materialId = tt.get("materialId").toString(); //物料ID
				TMaterailTypeInfo tMaterailTypeInfo = new TMaterailTypeInfo();
				tMaterailTypeInfo = dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
				
				String versionNo = tt.get("versionNo").toString(); //物料版本
				String storageId = tt.get("storageId").toString();//库房ID
				Double num = Double.parseDouble(tt.get("realNum").toString());//入库数量
				Double price = Double.parseDouble(tt.get("selectedPrice").toString());//价格
				String unitName = tt.get("baseUnitName").toString();//单位
				
				//库房
				TStorageInfo tStorageInfo = new TStorageInfo();
	        	tStorageInfo =dao.get(TStorageInfo.class,Integer.parseInt(storageId));
	        	
	        	int positionId = 0;//货位
	        	int batchId =0;//批次ID
	        	int seqId=0;//序列ID
	        	//货位
	        	if(positionCtrl){
	        		String no = tt.get("positionNo").toString();
	        		String hql ="select new map(a.id as id)"
	        				  + " from TMaterielPositionInfo as a "
	        				  + " where a.positionNo ='"+no+"'";
	        		List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()>0){
						positionId = Integer.parseInt(list.get(0).get("id").toString());
					}else{
						TMaterielPositionInfo tMaterielPositionInfo = new TMaterielPositionInfo();
						tMaterielPositionInfo.setPositionNo(no);
						tMaterielPositionInfo.setPositionName(no);
						tMaterielPositionInfo.setPositionStatus("1");
						dao.save(tMaterielPositionInfo);
						
						//库存货位关系表
						RStoragePosition rStoragePosition =new RStoragePosition();
						rStoragePosition.setTMaterielPositionInfo(tMaterielPositionInfo);
						rStoragePosition.setTStorageInfo(tStorageInfo);
						dao.save(rStoragePosition);
						positionId = tMaterielPositionInfo.getId();
					}
	        	}
				//批次设值
				if(batchCtrl){
					if(null !=tt.get("batch") && tt.get("batch").toString() !=""){
						TStockMaterialbath tStockMaterialbath =new TStockMaterialbath();
						String batchNo = tt.get("batch").toString();
						String hql = "select new map(a.id as id)"
						        + " from TStockMaterialbath as a"
						        + " where a.materialId ="+materialId+""
								+ " and a.no ='"+batchNo+"'";
						List<Map<String,Object>> list = dao.executeQuery(hql);
						if(list.size()<1){
							tStockMaterialbath.setMaterialId(Integer.valueOf(materialId));
							tStockMaterialbath.setNo(tt.get("batch").toString());
							dao.save(tStockMaterialbath);
							batchId = tStockMaterialbath.getId();
						}else{
							batchId = Integer.parseInt(list.get(0).get("id").toString());
						}
					}
				}
				//序列
				if(seqCtrl){//受序列控制
					for(Map<String,Object> map:productSeqList){
						if(map.get("id").toString().equals(id)){
							TStockMaterialseq tStockMaterialseq =new TStockMaterialseq();
							String hql ="select new map(a.id as id)"
	    					        + " from TStockMaterialseq as a"
	    					        + " where a.materialId ="+materialId+""
	    							+ " and a.no ='"+map.get("seqNo").toString()+"'";
	    					List<Map<String,Object>> list = dao.executeQuery(hql);
	    					if(list.size()<1){
	    						tStockMaterialseq.setMaterialId(Integer.valueOf(materialId));
	    						tStockMaterialseq.setNo(map.get("seqNo").toString());
	    						dao.save(tStockMaterialseq);
	    						seqId = tStockMaterialseq.getId();
	    					}else{
	    						seqId = Integer.parseInt(list.get(0).get("id").toString());
	    					}
	    					
							//获取库房该物料是否存在
							String hql2 ="select new map(a.id as id,a.availableNum as availableNum,"
									+ " a.moneyNum as moneyNum)"
							        + " from TMaterialStorage as a"
							        + " where a.TMaterailTypeInfo.id ="+materialId+""
									+ " and a.TStorageInfo.id ="+storageId+""
									+ " and a.versionNo ='"+versionNo+"'"
								    + " and a.seqId ="+seqId+"";
							if(positionCtrl){//货位
								hql2 +=" and (a.positonId is null or a.positonId ='')";
							}else{
								hql2 +=" and a.positonId ="+positionId+"";
							}
		                    if(batchCtrl){//批次
		                    	hql2 +=" and (a.batchId is null or a.batchId ='')";
							}else{
								hql2 +=" and a.batchId ="+batchId+"";
							}
		                    List<Map<String,Object>> list2 = dao.executeQuery(hql2);
		                    if(list2.size()>0){
		                    	//物料杂收入库
		                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	TMaterialStorage temp =new TMaterialStorage();
		                    	temp = dao.get(TMaterialStorage.class,tempId);
		                    	temp.setAvailableNum(1.0);//现有量
		                    	temp.setMoneyNum(price);//价格
		                    	temp.setProcessDate(transDate);//处理时间
		                    	dao.update(temp);
		                    }else{
								//入库
								TMaterialStorage temp =new TMaterialStorage();
								
								temp.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
		                    	temp.setTStorageInfo(tStorageInfo);//库房
		                    	if(positionCtrl){
		                    		temp.setPositonId(positionId);//货位
		                    	}
		                    	if(batchCtrl){
		                    		temp.setBatchId(batchId);//批次
		                    	}
		                    	
		                    	temp.setSeqId(seqId);//序列ID
		                    	temp.setAvailableNum(1.0);//现有量
		                    	temp.setUnitName(unitName);//单位
		                    	temp.setMoneyNum(price);//价格
		                    	temp.setVersionNo(versionNo);//版本
		                    	temp.setProcessDate(transDate);//处理时间
		                    	dao.save(temp);
		                    }
	                    	
	                    	//事务处理
	                        TTransaction tTransaction = new TTransaction();
	                        tTransaction.setTransNo(tt.get("transNo").toString());//事务编号
	                        tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
	                        tTransaction.setTStorageInfo(tStorageInfo);//库房
	                        if(positionCtrl){
	                        	tTransaction.setPositionId(positionId);//货位
	                    	}
	                        tTransaction.setProcessNum(1.0);//处理数量
	                        
	                        tTransaction.setPrice(price);//价格
	                        tTransaction.setOrderNo(tJobplanInfo.getNo());//订单号（即来源）
	                        tTransaction.setTransDate(transDate);//处理时间
	                        if(batchCtrl){
	                        	tTransaction.setBatchId(batchId);//批次
	                    	}
	                        tTransaction.setSeqId(seqId);//序列
	                        TTransactionRelation dd= new TTransactionRelation();
	                        dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	        				tTransaction.setTTransactionRelation(dd);
	        				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
	        				dao.save(tTransaction);
						}
					}
					
				}else{
					//获取库房该物料是否存在
					String hql ="select new map(a.id as id,a.availableNum as availableNum,"
							+ " a.moneyNum as moneyNum)"
					        + " from TMaterialStorage as a"
					        + " where a.TMaterailTypeInfo.id ="+materialId+""
							+ " and a.TStorageInfo.id ="+storageId+""
							+ " and a.versionNo ='"+versionNo+"'"
						    + " and (a.seqId is null or a.seqId ='')";
					if(positionCtrl){//货位
						hql +=" and (a.positonId is null or a.positonId ='')";
					}else{
						hql +=" and a.positonId ="+positionId+"";
					}
                    if(batchCtrl){//批次
                    	hql +=" and (a.batchId is null or a.batchId ='')";
					}else{
						hql +=" and a.batchId ="+batchId+"";
					}
                    List<Map<String,Object>> list = dao.executeQuery(hql);
                    if(list.size()>0){
                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
                    	Double tempNum = Double.parseDouble(list.get(0).get("availableNum").toString());
                    	Double tempPrice =Double.parseDouble(list.get(0).get("moneyNum").toString());
                    	Double inSoragePrice = (tempNum*tempPrice+num*price)/(tempNum+num);//入库价格
                    	TMaterialStorage temp =new TMaterialStorage();
                    	
                    	temp = dao.get(TMaterialStorage.class,tempId);
                    	temp.setAvailableNum(num+tempNum);
                    	temp.setMoneyNum(inSoragePrice);
                    	temp.setProcessDate(transDate);//处理时间
                    	dao.update(temp);
                    }else{
                    	TMaterialStorage temp =new TMaterialStorage();
                    	temp.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
                    	temp.setTStorageInfo(tStorageInfo);//库房
                    	if(positionCtrl){
                    		temp.setPositonId(positionId);//货位
                    	}
                    	if(batchCtrl){
                    		temp.setBatchId(batchId);//货位
                    	}
                    	temp.setAvailableNum(num);
                    	temp.setUnitName(unitName);//单位
                    	temp.setMoneyNum(price);
                    	temp.setVersionNo(versionNo);//版本
                    	temp.setProcessDate(transDate);//处理时间
                    	dao.save(temp);
                    }
                    
                    //事务处理
                    TTransaction tTransaction = new TTransaction();
                    tTransaction.setTransNo(tt.get("transNo").toString());//事务编号
                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//物料Id
                    tTransaction.setTStorageInfo(tStorageInfo);//库房
                    if(positionCtrl){
                    	tTransaction.setPositionId(positionId);//货位
                	}
                    tTransaction.setProcessNum(num);//处理数量
                    tTransaction.setPrice(price);//价格
                    tTransaction.setOrderNo(tJobplanInfo.getNo());//订单号（即来源）
                    tTransaction.setTransDate(transDate);//处理时间
                    if(batchId !=0){
                    	tTransaction.setBatchId(batchId);//批次
                	}
                    TTransactionRelation dd= new TTransactionRelation();
                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
    				tTransaction.setTTransactionRelation(dd);
    				tTransaction.setUserId(Integer.parseInt(userId));//人员Id
    				dao.save(tTransaction);
				}
			}
			return "保存成功";
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "保存失败";
		}
	}
    
	
	/**
	 * 获取限制库房信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> limitStorgeList(String materialId) {
		
		//TODO 取得表数据是在组织物料中填写的库房
		String hql ="select new map(a.id as id,a.materialId as materialId,"
				+ " a.versionNo as no,a.isDefault as isDefault)"
				+ " from TMaterialVersion as a"
				+ " where a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
    
	
	/**
	 * 获取限制货位信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> limitPositionList(String materialId,
			String storageId) {
		//TODO 取得表数据是在组织物料中填写的货位
		String hql ="select new map(a.id as id,a.materialId as materialId,"
				+ " a.versionNo as no,a.isDefault as isDefault)"
				+ " from TMaterialVersion as a"
				+ " where a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	
	
	
	
	
	
	
	
	
}
