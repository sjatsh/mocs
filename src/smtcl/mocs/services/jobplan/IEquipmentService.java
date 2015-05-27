package smtcl.mocs.services.jobplan;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.beans.equipment.EquipmentAccountingBean;
import smtcl.mocs.beans.equipment.EquipmentCostBean;
import smtcl.mocs.beans.equipment.EquipmentTypeBean;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TUserEquCurStatus;
import smtcl.mocs.pojos.job.TEquipmentCostInfo;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;

/**
 * �豸����SERVICE�ӿ�
 * @���ߣ�yyh
 * @����ʱ�䣺2013-08-06
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public interface IEquipmentService extends IGenericService<TEquipmentCostInfo, String> {
	
	/**
	 * �豸����ά��--�豸������Ϣ�б�
	 */
	public List<Map<String, Object>> getEquipmentInfoAList(String equipmentType);
	
	/**
	 * �豸����ά��--��������豸���
	 */
	public List<Map<String, Object>> getEquType();
	
	/**
	 * �豸����ά��--������ĸ����豸�����ҵ��豸
	 */
	public List<Map<String, Object>> getEquByTypeId(String equipmentType);
	
	/**
	 * �豸����ά��--ͨ������ID�õ�����
	 */
	public TEquipmenttypeInfo getgetEquTypeIdById(String typeId);
	
	/**
	 * �豸����ά��--ͨ���������Ƶõ�����ID
	 */
	public String getEquTypeIdByName(String name);
	
	/**
	 * �豸����ά��--���������õ��Ҳ��б�
	 */
	public List<Map<String, Object>> getEquTypeByClick(String pEqutypeId);
	
	/**
	 * �豸����ά��--�Ҳ��б��ֶθ��豸��������
	 */
	public List<Map<String, Object>> getParentNameById(String Id);
	
	/**
	 * �豸����ά��--���ʱ������
	 */
	public List<Map<String, Object>> getParentIdMap();
	
	/**
	 * �豸����ά��--����
	 */
	public void addEquType(EquipmentTypeBean equipmentTypeBean);
	
	/**
	 * �豸����ά��--��ѯ����ͨ���豸ID���鿴�Ƿ��ظ�
	 */
	public List<Map<String, Object>> getEquTypeRepeat(String typecode);
	
	/**
	 * �豸����ά��--ɾ���ɱ�
	 */
	public void delEquType(EquipmentTypeBean equipmentTypeBean);
	
	/**
	 * �豸����ά��--ɾ�����ж��Ƿ��Ѿ�ʹ�ã����ڻ���������
	 */
	public List<Map<String, Object>> getEquTypeInEquSize(String equTypeId);
	
	/**
	 * �豸����ά��--�޸ĳɱ�
	 */
	public void updateEquType(EquipmentTypeBean equipmentTypeBean);		
	
	/**
	 * �豸̨��ά��--�豸��Ϣ�б�
	 */
	public List<Map<String, Object>> getMachineList(String equipmentType);
	
	/**
	 * �豸̨��ά��--��������豸���
	 */
	public List<Map<String, Object>> getEquTypeInAcc();
	
	/**
	 * �豸̨��ά��--������ĸ����豸���������豸���
	 */
	public List<Map<String, Object>> getEquByTypeIdInAcc(String equipmentType);
	
	/**
	 * �豸̨��ά��--������ĸ����豸�������豸�������豸
	 */
	public List<Map<String, Object>> getEquByTypeInAcc(String typeId);
	
	/**
	 * �豸̨��ά��--����
	 */
	public void addEqu(String nodeid,EquipmentAccountingBean equipmentAccountingBean);
	
	/**
	 * �豸̨��ά��--��ѯ����ͨ���豸equSerialNo���鿴�Ƿ��ظ�
	 */
	public List<Map<String, Object>> getEquRepeat(String equSerialNo);
	
	/**
	 * �豸̨��ά��--ɾ��
	 */
	public void delEqu(String nodeid,EquipmentAccountingBean equipmentAccountingBean);
	
	/**
	 * �豸̨��ά��--ͨ���豸ID�õ��豸
	 */
	public  TEquipmentInfo getTEquipmentInfoById(String equId);
	
	/**
	 * �豸̨��ά��--ͨ��������ID�ҵ�����������
	 */
	public String getMenberNameById(String id);
	
	/**
	 * �豸̨��ά��--�޸�
	 */
	public void updateEqu(EquipmentAccountingBean equipmentAccountingBean);	
	
	/**
	 * �豸̨��ά��--�޸�ͼƬ
	 */
	public void updateEquImage(String equId,List<String> listdocStorePath);
	
	/**
	 * �豸�ɱ�ά��--�б�
	 */
	public List<Map<String, Object>> getEquipmentCostList(String roomId,String equTypeId,String equId,String code);
	
	/**
	 * �豸�ɱ�ά��--�õ����伯��
	 */
	public List<Map<String, Object>> getRoomMap();
	
	/**
	 * �豸�ɱ�ά��--�õ��豸���ͼ���
	 */
	public List<Map<String, Object>> getEquTypeMap();
	
	/**
	 * �豸�ɱ�ά��--�õ��豸����
	 */
	public List<Map<String, Object>> getEquMap();
	
	/**
	 * �豸�ɱ�ά��--�õ��豸���뼯��
	 */
	public List<Map<String, Object>> getEquCodeMap();
	
	/**
	 * �豸�ɱ�ά��--��ѯ����
	 */
	public List<Map<String, Object>> getEquCost();
	
	/**
	 * �豸�ɱ�ά��--����
	 */
	public void addEquCost(EquipmentCostBean equipmentCostBean);	
	
	/**
	 * �豸�ɱ�ά��--��ѯ����ͨ����ţ��鿴�Ƿ��ظ�
	 */
	public List<Map<String, Object>> getEquCostRepeat(String equSerialNo);
	
	/**
	 * �豸�ɱ�ά��--ɾ���ɱ�
	 */
	public void delEquCost(EquipmentCostBean equipmentCostBean);
	
	/**
	 * �豸�ɱ�ά��--�޸ĳɱ�
	 */
	public void updateEquCost(EquipmentCostBean equipmentCostBean);
	
	public TUserEquCurStatus getTUserEquCurStatus(String equSerialNo);
	/**
	 * ��ȡ�豸λ������
	 * @return
	 */
	public String getEquData(String nodeIds,int type);
	/**
	 * �������λ������
	 * @param equData
	 * @return
	 */
	public String saveEquData(String equData);

}
