package smtcl.mocs.services.storage;

import java.util.List;
import java.util.Map;

import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.TMaterialVersion;
import smtcl.mocs.pojos.storage.TMaterielPositionInfo;
import smtcl.mocs.pojos.storage.TStorageInfo;

public interface IMaterielManageService {

	/**
	 * 物料存量查询
	 * @param node	节点
	 * @param storage_id 子库房ID
	 * @param position_id	库位ID
	 * @param materiel_id	物料编码ID
	 * @param materielVersion 物料版本
	 * @param materielDesc_id 物料描述ID
	 * @param status 状态
	 * @return
	 */
	List<Map<String, Object>> materielQuery(String nodeid, String storage_id,
			String position_id, String materiel_id, String materielVersion,
			String materielDesc_id, String status);
	
	/**
	 * 物料存量明细查询
	* @param node	节点
	 * @param storage_id 子库房ID
	 * @param position_id	库位ID
	 * @param materiel_id	物料编码ID
	 * @param materielVersion 物料版本
	 * @param materielDesc_id 物料描述ID
	 * @param status 状态
	 * @return
	 */
	List<Map<String, Object>> materielDetailQuery(String nodeid, String storage_id,
			String position_id, String materiel_id, String materielVersion,
			String materielDesc_id, String status);
	
	/**
	 * 物料批次查询
	 * @param storage_id 库存Id
	 * @param position_id 库位ID
	 * @param batchStart 开始批次
	 * @param batchEnd 结束批次
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	List<Map<String, Object>> materielBatchQuery(String nodeid,
			String materiel_id, String storage_id, String position_id,
			String batchStart, String batchEnd, String materielDesc_id,
			String waixieid);
	
	/**
	 * 物料序列查询
	 * @param storage_id 库存Id
	 * @param position_id 库位ID
	 * @param seqStart 开始序列
	 * @param seqEnd 结束序列
	 * @return
	 */
	List<Map<String, Object>> materielSeqQuery(String nodeid,
			String materiel_id, String storage_id, String position_id,
			String seqStart, String seqEnd, String materielDesc_id);
	
	/**
	 * 通过nodeid节点获取子库存信息
	 * @param nodeid
	 * @return
	 */
	List<TStorageInfo> getStorageInfo(String nodeid);
	
	/**
	 * 获取货位信息
	 * @param storage_id 字库房id
	 * @return
	 */
	List<Map<String,Object>> getMaterielPositionInfo(String storage_id);
	
	/**
	 * 通过nodeid节点获取物料信息
	 * @param nodeid
	 * @return
	 */
	List<TMaterailTypeInfo> getMaterailTypeInfo(String nodeid);
	
	/**
	 * 通过物料Id获取物料版本
	 * @param materiel_id
	 * @return
	 */
	List<TMaterialVersion> getMaterialVersion(String materiel_id);

	/**
	 * 获取批次号集合
	 * @return
	 */
	List<Map<String,Object>> findbatchNo();
	
	/**
	 * 获取序列号集合
	 * @return
	 */
	List<Map<String,Object>> findseqNo();
	
	/**
	 * 模糊查询获取库房信息
	 * @param storageNo
	 * @return
	 */
	List<Map<String,Object>> findStrogeNo(String nodeid,String storageNo);

	/**
	 * 模糊查询获取物料信息
	 * @param nodeid
	 * @param materialValue
	 * @param flag no or name
	 * @return
	 */
	List<Map<String, Object>> findMaterialInfo(String nodeid,
			String materialValue, String flag);

	/**
	 * 模糊查询获取库位信息
	 * @param nodeid
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> findPositionInfo(String nodeid, String query);
	
}
