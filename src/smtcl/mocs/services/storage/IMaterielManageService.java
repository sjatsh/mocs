package smtcl.mocs.services.storage;

import java.util.List;
import java.util.Map;

import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.TMaterialVersion;
import smtcl.mocs.pojos.storage.TMaterielPositionInfo;
import smtcl.mocs.pojos.storage.TStorageInfo;

public interface IMaterielManageService {

	/**
	 * ���ϴ�����ѯ
	 * @param node	�ڵ�
	 * @param storage_id �ӿⷿID
	 * @param position_id	��λID
	 * @param materiel_id	���ϱ���ID
	 * @param materielVersion ���ϰ汾
	 * @param materielDesc_id ��������ID
	 * @param status ״̬
	 * @return
	 */
	List<Map<String, Object>> materielQuery(String nodeid, String storage_id,
			String position_id, String materiel_id, String materielVersion,
			String materielDesc_id, String status);
	
	/**
	 * ���ϴ�����ϸ��ѯ
	* @param node	�ڵ�
	 * @param storage_id �ӿⷿID
	 * @param position_id	��λID
	 * @param materiel_id	���ϱ���ID
	 * @param materielVersion ���ϰ汾
	 * @param materielDesc_id ��������ID
	 * @param status ״̬
	 * @return
	 */
	List<Map<String, Object>> materielDetailQuery(String nodeid, String storage_id,
			String position_id, String materiel_id, String materielVersion,
			String materielDesc_id, String status);
	
	/**
	 * �������β�ѯ
	 * @param storage_id ���Id
	 * @param position_id ��λID
	 * @param batchStart ��ʼ����
	 * @param batchEnd ��������
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return
	 */
	List<Map<String, Object>> materielBatchQuery(String nodeid,
			String materiel_id, String storage_id, String position_id,
			String batchStart, String batchEnd, String materielDesc_id,
			String waixieid);
	
	/**
	 * �������в�ѯ
	 * @param storage_id ���Id
	 * @param position_id ��λID
	 * @param seqStart ��ʼ����
	 * @param seqEnd ��������
	 * @return
	 */
	List<Map<String, Object>> materielSeqQuery(String nodeid,
			String materiel_id, String storage_id, String position_id,
			String seqStart, String seqEnd, String materielDesc_id);
	
	/**
	 * ͨ��nodeid�ڵ��ȡ�ӿ����Ϣ
	 * @param nodeid
	 * @return
	 */
	List<TStorageInfo> getStorageInfo(String nodeid);
	
	/**
	 * ��ȡ��λ��Ϣ
	 * @param storage_id �ֿⷿid
	 * @return
	 */
	List<Map<String,Object>> getMaterielPositionInfo(String storage_id);
	
	/**
	 * ͨ��nodeid�ڵ��ȡ������Ϣ
	 * @param nodeid
	 * @return
	 */
	List<TMaterailTypeInfo> getMaterailTypeInfo(String nodeid);
	
	/**
	 * ͨ������Id��ȡ���ϰ汾
	 * @param materiel_id
	 * @return
	 */
	List<TMaterialVersion> getMaterialVersion(String materiel_id);

	/**
	 * ��ȡ���κż���
	 * @return
	 */
	List<Map<String,Object>> findbatchNo();
	
	/**
	 * ��ȡ���кż���
	 * @return
	 */
	List<Map<String,Object>> findseqNo();
	
	/**
	 * ģ����ѯ��ȡ�ⷿ��Ϣ
	 * @param storageNo
	 * @return
	 */
	List<Map<String,Object>> findStrogeNo(String nodeid,String storageNo);

	/**
	 * ģ����ѯ��ȡ������Ϣ
	 * @param nodeid
	 * @param materialValue
	 * @param flag no or name
	 * @return
	 */
	List<Map<String, Object>> findMaterialInfo(String nodeid,
			String materialValue, String flag);

	/**
	 * ģ����ѯ��ȡ��λ��Ϣ
	 * @param nodeid
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> findPositionInfo(String nodeid, String query);
	
}
