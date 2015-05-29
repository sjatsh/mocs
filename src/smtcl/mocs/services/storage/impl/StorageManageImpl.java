package smtcl.mocs.services.storage.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.model.TMaterialPositionModel;
import smtcl.mocs.model.TStorageInfoModel;
import smtcl.mocs.pojos.storage.RStoragePosition;
import smtcl.mocs.pojos.storage.TMaterielPositionInfo;
import smtcl.mocs.pojos.storage.TStorageInfo;
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
				+ " u.id as id, "
				+ " u.unitName as name) "
				+ " from TUnitInfo u,TUnitType t "
				+ " where u.TUnitType.id = t.id "
				+ " and t.unitTypeName ='体积单位' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
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
				+ "s.storageName as name) "
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
	
	
	
	
	
	
	
}
