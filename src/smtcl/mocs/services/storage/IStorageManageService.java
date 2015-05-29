package smtcl.mocs.services.storage;


import java.util.List;
import java.util.Map;

import smtcl.mocs.model.TMaterialPositionModel;
import smtcl.mocs.model.TStorageInfoModel;
import smtcl.mocs.pojos.storage.TMaterielPositionInfo;
import smtcl.mocs.pojos.storage.TStorageInfo;

/**
 * @创建时间 2014-08-27
 * @作者 FW
 * @修改者
 * @修改日期
 * @修改说明
 * @version V1.0
 */
public interface IStorageManageService {
	/**
	 * 获取库房List
	 * @param str 查询条件
	 * @param nodeId 节点
	 */
	public List<TStorageInfoModel> getStorageList(String str,String nodeId);
	
	/**
	 * 更新库房信息
	 * @param tStorageInfo 库房类
	 * @param flag 标志 （1:新增;2:修改）
	 */
	public String updateStorageInfo(TStorageInfo tStorageInfo,int flag);
	
	/**
	 * 删除库房信息
	 */
	public String delStorageInfo(TStorageInfo tStorageInfo);
	
	
	/**
	 * 获取货位List
	 * @param str 条件
	 */
	public List<TMaterialPositionModel> getMaterialPositionList(String str,String nodeId);
	
	/**
	 * 更新货位信息
	 * @param tMaterialPositionInfo 库房类
	 * @param flag 标志 （1:新增;2:修改）
	 * @param storageId 库房Id
	 */
	public String updateMaterialPositionInfo(TMaterielPositionInfo tMaterielPositionInfo,int flag,String storageId);
    
	/**
	 * 删除货位信息
	 */
	public String delMaterielPositionInfo(TMaterielPositionInfo tMaterielPositionInfo);
	
	/**
	 * 获取信息
	 * @param hql 
	 */
	public List<Map<String,Object>> getList(String hql);
	
	/**
	 * 获取人员信息
	 */
	public List<Map<String,Object>> userList(String nodeId);
	/**
	 * 获取体积单位信息
	 */
	public List<Map<String,Object>> capacityUnitList(String nodeId);
	/**
	 * 获取库房信息
	 */
	public List<Map<String,Object>> storgeList(String nodeId);
	/**
	 * 获取数量单位信息
	 */
	public List<Map<String,Object>> quantityUnitList(String nodeId);
	/**
	 * 获取重量单位信息
	 */
	public List<Map<String,Object>> weightUnitList(String nodeId);
	/**
	 * 获取维度单位信息
	 */
	public List<Map<String,Object>> dimensionUnitList(String nodeId);
}
