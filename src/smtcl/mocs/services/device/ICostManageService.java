package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.pojos.device.TNodes;

/**
 * �ɱ�����+�����㷨�ӿ� 
 * @����ʱ�� 2013-6-20
 * @���� yutao
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 * @version V1.0
 */
public interface ICostManageService extends IGenericService<TNodes, String> {
	
	/**
	 * ��ȡ�ɱ��嵥�б�
	 * @param pageNo
	 * @param pageSize
	 * @param partTypeNo
	 * @return
	 */
	public List<Map<String, Object>> queryProcessCostList(int pageNo, int pageSize,String partTypeNo);	
	
	/**
	 * ͨ��ʱ�� ��̨�ɱ�
	 * @param pageNo
	 * @param pageSize
	 * @param partTypeNo
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> queryEquipmentCostListByVar(
			int pageNo, int pageSize, String partTypeNo,String startTime,String endTime);
     
	/**
      * ͨ����Ʒ��Ų�ѯ	 ��̨�ɱ�
      * @param partTypeNo
      * @param equNo
      * @param startTime
      * @param endTime
      * @return
      */
	public List<Map<String, Object>> queryEquipmentCostListByEquno(String partTypeNo,String equNo,String startTime,String endTime);

	
	/**
	 * ͨ�����ͻ������Ƶõ���������ɱ�����
	 * @param partTypeNo
	 * @return
	 */
	public List<Map<String, Object>> queryProductRealCostAnalysis(String  partTypeNo,String partNo);
	
	/**
	 * ����ֵ
	 * @param partTypeNo
	 * @return
	 */
	public Map<String,Object> getProductTheoryCostAnalysis(String partTypeNo);
	
	/**
	 * ��Ʒ�����б�
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getPartTypeList(String nodeid);
	
	/**
	 * ��Ʒ�б�
	 */	
	public List<Map<String, Object>> getPartNoList(String partTypeNo);
	
	/**
	 * �ӹ��¼�����--A3 
	 */
	//public Boolean processEquWorkEvent(Long eventId,String partNo);
	
	/**
	 * �ӹ��¼�����--�Ϻ�չ,����B21
	 */
	public Boolean processEquWorkEvent(Long eventId);
	
	/**
	 * ��ȡ���²�Ʒ�ĳɱ�
	 */
	public List<Map<String, Object>> queryLastPartCost(String equSerialNo);
	
	/**
	 * �����ſ���ȡRCR��Ϣ
	 * @param nodeId
	 * @return
	 */
	public String getRCR(String nodeId);
	
	/**
	 * ��ȡ�豸�ܺ�,�豸���кţ����ŷָ�
	 * @param equSerialNos
	 * @return
	 */
	public String getEquRCR(String equSerialNos);
}
