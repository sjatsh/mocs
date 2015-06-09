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
 * @����ʱ�� 2014-08-27
 * @���� FW
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 * @version V1.0
 */
public interface IStorageManageService {
	/**
	 * ��ȡ�ⷿList
	 * @param str ��ѯ����
	 * @param nodeId �ڵ�
	 */
	public List<TStorageInfoModel> getStorageList(String str,String nodeId);
	
	/**
	 * ���¿ⷿ��Ϣ
	 * @param tStorageInfo �ⷿ��
	 * @param flag ��־ ��1:����;2:�޸ģ�
	 */
	public String updateStorageInfo(TStorageInfo tStorageInfo,int flag);
	
	/**
	 * ɾ���ⷿ��Ϣ
	 */
	public String delStorageInfo(TStorageInfo tStorageInfo);
	
	
	/**
	 * ��ȡ��λList
	 * @param str ����
	 */
	public List<TMaterialPositionModel> getMaterialPositionList(String str,String nodeId);
	
	/**
	 * ���»�λ��Ϣ
	 * @param tMaterialPositionInfo �ⷿ��
	 * @param flag ��־ ��1:����;2:�޸ģ�
	 * @param storageId �ⷿId
	 */
	public String updateMaterialPositionInfo(TMaterielPositionInfo tMaterielPositionInfo,int flag,String storageId);
    
	/**
	 * ɾ����λ��Ϣ
	 */
	public String delMaterielPositionInfo(TMaterielPositionInfo tMaterielPositionInfo);
	
	/**
	 * ��ȡ��Ϣ
	 * @param hql 
	 */
	public List<Map<String,Object>> getList(String hql);
	
	/**
	 * ��ȡ��Ա��Ϣ
	 */
	public List<Map<String,Object>> userList(String nodeId);
	/**
	 * ��ȡ�����λ��Ϣ
	 */
	public List<Map<String,Object>> capacityUnitList(String nodeId);
	/**
	 * ��ȡ�ⷿ��Ϣ
	 */
	public List<Map<String,Object>> storgeList(String nodeId);
	/**
	 * ��ȡ������λ��Ϣ
	 */
	public List<Map<String,Object>> quantityUnitList(String nodeId);
	/**
	 * ��ȡ������λ��Ϣ
	 */
	public List<Map<String,Object>> weightUnitList(String nodeId);
	/**
	 * ��ȡά�ȵ�λ��Ϣ
	 */
	public List<Map<String,Object>> dimensionUnitList(String nodeId);
	
	/**
	 * ��ȡ���μ���List
	 */
	public List<Map<String,Object>> jobPlanList(String nodeId);
	
	/**
	 * ��ȡ���ϼ���List
	 */
	public List<Map<String,Object>> materialList(String jobPlanId);
	
	/**
	 * ��ȡ��֯���Ͽ�����Ϣ
	 */
	public List<Map<String,Object>> materialCtrList(String materialId);
	
	/**
	 * ����NO��ȡ��֯���Ͽ�����Ϣ
	 */
	public List<Map<String,Object>> materialCtrByNoList(String materialNo,String nodeId);
	
	/**
	 * ��ȡ���Ͽⷿ��Ϣ
	 */
	public List<Map<String,Object>> materialStorageList(String materialId,String isBatchCtrl,String isSeqCtrl);
	
	/**
	 * ��ȡ���ϻ�λ��Ϣ
	 */
	public List<Map<String,Object>> materialPositionList(String materialId,String storageId);
	/**
	 * ��ȡ�������ͼ���
	 */
	public List<Map<String,Object>> transactionTypeList();
	/**
	 * ��ȡ������������
	 */
	public String getMaxID();
	/**
	 * ����������Ϣ
	 */
	public String saveInfo(List<Map<String,Object>> materialInfoList,List<Map<String,Object>> outInStorageList,List<Map<String,Object>> transactionList,String type,String userId,String transTypeId,Date transDate,String jobPlanId);
    
	/**
	 * ����Id��ȡ������Ϣ
	 */
	public List<Map<String,Object>> getJobPlanInfoList(String jobPlanId);
	
	/**
	 * ���ݿⷿ��ȡ��λ����
	 */
	public List<Map<String,Object>> getPositionInfoList(String storageId);
	
	/**
	 * ��ȡ�������μ���
	 */
	public List<Map<String,Object>> getProductBatchList(String materialId);
	
	/**
	 * ��ȡ��������
	 * @param flag false��ʾδ��ʹ�ã�true��ʾ��ʹ��
	 */
	public List<Map<String,Object>> getProductSeqList(String materialId,String no,Boolean flag);
	
	/**
	 * �깤���
	 */
	public String saveProductInStorage(List<Map<String,Object>> dataInfo,List<Map<String,Object>> seqInfo,String materialId,
			Boolean batchCtrl,Boolean seqCtrl,String jobPlanId,int totalNum,String userId,String transTypeId,Date transDate,String nodeId);
	/**
	 * �����������
	 */
	public String saveMaterialInStorage(List<Map<String,Object>> dataInfo,List<Map<String,Object>> seqInfo,
			String source,String userId,String transTypeId,Date transDate);
	/**
	 * �����ӷ�����
	 */
	public String saveMaterialOutStorage(List<Map<String,Object>> dataInfo,String transNo,String materialId,
			Boolean batchCtrl,Boolean seqCtrl,String source,String userId,String transTypeId,Date transDate);
	/**
	 * ת���ӿ�汣��
	 */
	public String saveTransferMaterial(List<Map<String,Object>> dataInfo,String source,String userId,String transTypeId,Date transDate);
	/**
	 * ����������Ϣ����
	 */
	public String saveJobDispatchMaterialInStorage(List<Map<String,Object>> materialInfoList,List<Map<String,Object>> outInStorageList,List<Map<String,Object>> productSeqList,
			String userId,String transTypeId,Date transDate,String jobPlanId);
	
	/**
	 * ��ȡ��Դ���ͼ���
	 */
	public List<Map<String,Object>> getTransFromTypeList();
	/**
	 * ��ȡ�������ͼ���
	 */
	public List<Map<String,Object>> getTransTypeList(String transFrom);
	/**
	 * ������ϢList
	 */
	public List<Map<String,Object>> getMaterialInfoList(String nodeId);
	/**
	 * �����������Ϣ����
	 */
	public List<Map<String,Object>> getMaterialInfoList2(String nodeId);
	/**
	 * ���Ͽ��ⷿ����List
	 */
	public List<Map<String,Object>> getMaterialStorageList(String materialId,String nodeId);
	
	/**
	 * ���Ͽ���λ����List
	 */
	public List<Map<String,Object>> getMaterialStoragePositionList(String materialId,String storageId);
	
	/**
	 * ���Ͽ�����μ���List
	 */
	public List<Map<String,Object>> getMaterialStorageBatchList(String materialId,String storageId,String positionId);
	
	/**
	 * ���Ͽ�����м���List
	 */
	public List<Map<String,Object>> getMaterialStorageSeqList(String materialId,String storageId,
			String positionId,String batchId,String seqId);
	
	/**
	 * ���Ͽ��汾����List
	 */
	public List<Map<String,Object>> getMaterialStorageVersionList(String materialId,String storageId,
			String positionId,String batchId,String seqId,String versionNo);

	/**
	 * ��λ�����ʼ���List
	 */
	public List<Map<String,Object>> getUnitRateList(String unitName,String nodeId);
	/**
	 * ��ȡ���ϰ汾��Ϣ
	 */
	public List<Map<String,Object>> materialVersionList(String materialId);
	
	/**
	 * ��ȡ������ϰ汾��Ϣ
	 */
	public List<Map<String,Object>> storageMaterialVersionList(String materialId,String storageId,
			String positionId,String batchId,String seqId);
    
	/**
	 * ��ȡ���ƿⷿ��Ϣ
	 */
	public List<Map<String,Object>> limitStorgeList(String materialId);
	/**
	 * ��ȡ���ƻ�λ��Ϣ
	 */
	public List<Map<String,Object>> limitPositionList(String materialId,String storageId);
}
