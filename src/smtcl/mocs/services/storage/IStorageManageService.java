package smtcl.mocs.services.storage;


import java.util.List;
import java.util.Map;

import smtcl.mocs.model.TMaterialPositionModel;
import smtcl.mocs.model.TStorageInfoModel;
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
}
