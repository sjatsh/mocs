package smtcl.mocs.services.storage;



import java.util.Date;
import java.util.List;
import java.util.Map;

import smtcl.mocs.model.TMaterialPositionModel;
import smtcl.mocs.model.TStorageInfoModel;

import smtcl.mocs.pojos.job.TJobplanInfo;
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
	
	/**
	 * 获取批次集合List
	 */
	public List<Map<String,Object>> jobPlanList(String nodeId);
	
	/**
	 * 获取物料集合List
	 */
	public List<Map<String,Object>> materialList(String jobPlanId);
	
	/**
	 * 获取组织物料控制信息
	 */
	public List<Map<String,Object>> materialCtrList(String materialId);
	
	/**
	 * 根据NO获取组织物料控制信息
	 */
	public List<Map<String,Object>> materialCtrByNoList(String materialNo,String nodeId);
	
	/**
	 * 获取物料库房信息
	 */
	public List<Map<String,Object>> materialStorageList(String materialId,String isBatchCtrl,String isSeqCtrl);
	
	/**
	 * 获取物料货位信息
	 */
	public List<Map<String,Object>> materialPositionList(String materialId,String storageId);
	/**
	 * 获取事务类型集合
	 */
	public List<Map<String,Object>> transactionTypeList();
	/**
	 * 获取事务最大事务号
	 */
	public String getMaxID();
	/**
	 * 保存出入库信息
	 */
	public String saveInfo(List<Map<String,Object>> materialInfoList,List<Map<String,Object>> outInStorageList,List<Map<String,Object>> transactionList,String type,String userId,String transTypeId,Date transDate,String jobPlanId);
    
	/**
	 * 根据Id获取批次信息
	 */
	public List<Map<String,Object>> getJobPlanInfoList(String jobPlanId);
	
	/**
	 * 根据库房获取货位集合
	 */
	public List<Map<String,Object>> getPositionInfoList(String storageId);
	
	/**
	 * 获取生产批次集合
	 */
	public List<Map<String,Object>> getProductBatchList(String materialId);
	
	/**
	 * 获取生产序列
	 * @param flag false表示未被使用；true表示被使用
	 */
	public List<Map<String,Object>> getProductSeqList(String materialId,String no,Boolean flag);
	
	/**
	 * 完工入库
	 */
	public String saveProductInStorage(List<Map<String,Object>> dataInfo,List<Map<String,Object>> seqInfo,String materialId,
			Boolean batchCtrl,Boolean seqCtrl,String jobPlanId,int totalNum,String userId,String transTypeId,Date transDate,String nodeId);
	/**
	 * 物料杂收入库
	 */
	public String saveMaterialInStorage(List<Map<String,Object>> dataInfo,List<Map<String,Object>> seqInfo,
			String source,String userId,String transTypeId,Date transDate);
	/**
	 * 物料杂发出库
	 */
	public String saveMaterialOutStorage(List<Map<String,Object>> dataInfo,String transNo,String materialId,
			Boolean batchCtrl,Boolean seqCtrl,String source,String userId,String transTypeId,Date transDate);
	/**
	 * 转移子库存保存
	 */
	public String saveTransferMaterial(List<Map<String,Object>> dataInfo,String source,String userId,String transTypeId,Date transDate);
	/**
	 * 工单退料信息保存
	 */
	public String saveJobDispatchMaterialInStorage(List<Map<String,Object>> materialInfoList,List<Map<String,Object>> outInStorageList,List<Map<String,Object>> productSeqList,
			String userId,String transTypeId,Date transDate,String jobPlanId);
	
	/**
	 * 获取来源类型集合
	 */
	public List<Map<String,Object>> getTransFromTypeList();
	/**
	 * 获取事务类型集合
	 */
	public List<Map<String,Object>> getTransTypeList(String transFrom);
	/**
	 * 物料信息List
	 */
	public List<Map<String,Object>> getMaterialInfoList(String nodeId);
	/**
	 * 库存中物料信息集合
	 */
	public List<Map<String,Object>> getMaterialInfoList2(String nodeId);
	/**
	 * 物料库存库房集合List
	 */
	public List<Map<String,Object>> getMaterialStorageList(String materialId,String nodeId);
	
	/**
	 * 物料库存货位集合List
	 */
	public List<Map<String,Object>> getMaterialStoragePositionList(String materialId,String storageId);
	
	/**
	 * 物料库存批次集合List
	 */
	public List<Map<String,Object>> getMaterialStorageBatchList(String materialId,String storageId,String positionId);
	
	/**
	 * 物料库存序列集合List
	 */
	public List<Map<String,Object>> getMaterialStorageSeqList(String materialId,String storageId,
			String positionId,String batchId,String seqId);
	
	/**
	 * 物料库存版本集合List
	 */
	public List<Map<String,Object>> getMaterialStorageVersionList(String materialId,String storageId,
			String positionId,String batchId,String seqId,String versionNo);

	/**
	 * 单位换算率集合List
	 */
	public List<Map<String,Object>> getUnitRateList(String unitName,String nodeId);
	/**
	 * 获取物料版本信息
	 */
	public List<Map<String,Object>> materialVersionList(String materialId);
	
	/**
	 * 获取库存物料版本信息
	 */
	public List<Map<String,Object>> storageMaterialVersionList(String materialId,String storageId,
			String positionId,String batchId,String seqId);
    
	/**
	 * 获取限制库房信息
	 */
	public List<Map<String,Object>> limitStorgeList(String materialId);
	/**
	 * 获取限制货位信息
	 */
	public List<Map<String,Object>> limitPositionList(String materialId,String storageId);
}
