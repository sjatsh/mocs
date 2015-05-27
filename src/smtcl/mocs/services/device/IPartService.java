package smtcl.mocs.services.device;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.MaterialsModel;
import smtcl.mocs.model.PartModel;
import smtcl.mocs.model.TProcessEquipmentModel;
import smtcl.mocs.model.TProcessFixturetypeModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquNcprogs;
import smtcl.mocs.pojos.device.TUserFixture;
import smtcl.mocs.pojos.job.TCuttertypeInfo;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.pojos.job.TFixtureTypeInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TPartClassInfo;
import smtcl.mocs.pojos.job.TPartProcessCost;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessEquipment;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessmaterialInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.pojos.job.TQualityParam;

/**
 * ���Service�ӿ�
 * @����ʱ�� 2013-07-09
 * @���� liguoqiang  
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public interface IPartService extends IGenericService<TNodes, String>{
	/**
	 * ����������ѯTPartTypeInfo�����������Ϊ�գ����ѯȫ������
	 * @param query
	 * @return List<TPartTypeInfo>
	 * @throws Exception 
	 */
	public List<TPartTypeInfo> getTPartTypeInfo(String query,String status,String nodeid);
	public List<Map<String,Object>> getTPartTypeInfo(String nodeid,String partNo,Date startTime,Date endTime);
	/**
	 * ����TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public String saveTPartTypeInfo(TPartTypeInfo tPartTypeInfo);
	
	/**
	 * ɾ��TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public void deleteTPartTypeInfo(TPartTypeInfo tPartTypeInfo);
	
	/**
	 * ��� TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public String addTPartTypeInfo(TPartTypeInfo tPartTypeInfo);
	
	/**
	 * ͣ�ã����÷���
	 * @param tPartTypeInfo
	 * @param status
	 */
	public void stopOrStartTPartTypeInfo(TPartTypeInfo tPartTypeInfo,String status);
	
	/**
	 * ��TPartClassInfo���л�ȡ���е�typeNo
	 * @return
	 */
	public List<String> getTypeNo(String nodeid);
	
	/**
	 * ����������ѯTPartClassInfo�����������Ϊ�գ����ѯȫ������
	 * @param query
	 * @return List<TPartTypeInfo>
	 * @throws Exception 
	 */
	public List<TPartClassInfo> getTPartClassInfo(String query,String nodeid);
	
	/**
	 * ����TPartClassInfo
	 * @param tPartTypeInfo
	 */
	public String saveTPartClassInfo(TPartClassInfo tPartClassInfo);
	
	/**
	 * ɾ��TPartClassInfo
	 * @param tPartTypeInfo
	 */
	public void deleteTPartClassInfo(TPartClassInfo tPartClassInfo);
	
	/**
	 * ��� TPartClassInfo
	 * @param tPartTypeInfo
	 */
	public String addTPartClassInfo(TPartClassInfo tPartClassInfo);
	
	/**
	 * ���������Ų�ѯ�����Ϣ
	 * @param tPartTypeInfo
	 */
	public List<TPartTypeInfo> getTPartTypeInfoByNo(String no);
	/**
	 * �����������Ų�ѯ������
	 * @param typeNo
	 * @return
	 */
	public List<TPartTypeInfo> getTPartTypeInfoByTypeNo(String typeNo);
	/**
	 * ��ȡ��������б���
	 * @return
	 */
	public List<Map<String,Object>> getPartTree(String query,String nodeid);
	
	/**
	 * ��ȡ���շ���
	 * @param no
	 * @return
	 */
	public List<TProcessplanInfo> getTProcessplanInfo(String no,String parm);
	public List<Map<String,Object>> getTProcessplanInfo(String no);
	/**
	 * ��ȡ����
	 * @return
	 */
	public List<TProcessInfo> getTProcessInfo(String processPlanID);
	public List<Map<String,Object>> getTProcessInfoByGYFA(String processPlanID);
	/**
	 * ����ǰ���Ų��ҹ���
	 * @param onlineProcessID
	 * @return
	 */
	public List<TProcessInfo> getTProcessInfoByOnlineProcessID(String onlineProcessID);
	/**
	 * ���¹���
	 * @param tProcessInfo
	 */
	public void saveTProcessInfo(TProcessInfo tProcessInfo);
	
	/**
	 * ��������
	 * @param tProcessInfo
	 */
	public void addTProcessInfo(TProcessInfo tProcessInfo);
	
	/**
	 * ɾ������
	 * @param tPartClassInfo
	 */
	public void deleteTProcessInfo(TProcessInfo tProcessInfo);
	
	/**
	 * ��ȡ�������еĳ����б�
	 * @return
	 */
	public List<Map<String,Object>> getSelectTUserEquNcprogs(String nodeid);
	
	/**
	 * ��ȡ�������й��շ����б�
	 * @return
	 */
	public List<Map<String,Object>> getSelectTProcessplanInfo();
	
	/**
	 * ��ȡ�������мо���Ϣ�б�
	 * @return
	 */
	public List<TFixtureTypeInfo> getSelectTFixturesInfo(String fixtureclassId,String nodeid);
	
	/**
	 *  ��ȡ�������й���վ��Ϣ
	 * @return
	 */
	public List<Map<String,Object>> getSelectTWorkstationInfo();
	
	/**
	 * ��ȡ�������л��������б�
	 * @return
	 */
	public List<Map<String,Object>> getSelectTEquipmenttypeInfo();
	
	/**
	 * ���سɱ�����
	 * @param pprs
	 * @return
	 */
	public List<Map<String,Object>> getCBData(List<TProcessInfo> pprs);
	public List<Map<String,Object>> getCBData(String processId);
	/**
	 * ������������
	 * @param pprs
	 * @return
	 */
	public List<Map<String,Object>> getWLData(List<TProcessInfo> pprs);
	
	/**
	 * ���ؼо�����
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>> getJJData(String processPlanID);
	
	/**
	 * ���ص�����������
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>>  getDJData(String processPlanID);
	
	/**
	 * ���ػ�̨����
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>> getJTData(String processPlanID);
	/**
	 * ���ݹ���id��ȡ�������
	 * @param id
	 * @return
	 */
	public List<TProcessplanInfo> getTProcessplanInfoById(String id);
	
	/**
	 * ��ȡ�豸����
	 * @return
	 */
	public List<TEquipmenttypeInfo> getTEquipmenttypeInfo();
	
	/**
	 * ���ݹ���id��ѯ ������Ϣ
	 * @param no
	 * @return
	 */
	public List<TProcessmaterialInfo> getTProcessmaterialInfo(String id);
	
	/**
	 * ��ȡ������������������
	 * @return
	 */
	public List<Map<String,Object>> getSelectTMaterailTypeInfo(String nodeid);
	
	/**
	 * �������ϱ�Ż�ȡ����
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfo(String no);
	
	/**
	 * ��ȡ��������������
	 * @return
	 */
	public List<Map<String,Object>> getSelectTCuttertypeInfo(String nodeid);
	
	/**
	 * ���ݵ��߱�Ż�ȡ������Ϣ
	 * @param no
	 * @return
	 */
	public List<TCuttertypeInfo> getTCuttertypeInfo(String no);
	
	/**
	 * ��ȡ�豸����
	 * @return
	 */
	public List<TEquipmenttypeInfo> getTEquipmenttypeInfoById(String id);
	
	/**
	 * ��ȡ�������еĳ����б�
	 * @return
	 */
	public List<TUserEquNcprogs> getSelectTUserEquNcprogsById(String id);
	
	/**
	 * �����������ͱ�Ż�ȡ��������
	 * @param no
	 * @return
	 */
	public List<TMaterailTypeInfo> getSelectTMaterailTypeInfoByNo(String no);
	

	/**
	 * ���������򵼱���
	 * @param part �򵼸���ģ��
	 * @param materialList ��������
	 * @param cuttertypeList ��������
	 * @param partTypeno ������
	 * @param tqplist �ʼ�����
	 * @param teti �豸����
	 * @param tftmlist �о�����
	 */
	public String savePartProcessGuide(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,String partTypeNo,
			List<TQualityParam> tqplist,List<TProcessEquipmentModel> teti,List<TProcessFixturetypeModel> tftmlist);
	
	
	public String savePartProcessGuideTest(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,String partTypeNo,
			List<TQualityParam> tqplist,List<TProcessEquipmentModel> teti,List<TProcessFixturetypeModel> tftmlist);
	/**
	 * ���������򵼸���
	 * @param part
	 * @param materialList
	 * @param cuttertypeList
	 */
	public String savePartProcessGuide(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,
			String partTypeNo,TProcessInfo tProcessInfo,TPartProcessCost tPartProcessCost,List<MaterialsModel> deleteWL
			,List<CuttertypeModel> deleteDJ);
	/**
	 * ���湤�շ���
	 * @param plan
	 * @return
	 */
	public String saveTProcessplanInfo(TProcessplanInfo plan);
	
	/**
	 * ���ݹ���id���ع������
	 * @param Id
	 * @return
	 */
	public TProcessInfo getTProcessInfoById(String Id);
	
	/**
	 * ���ݹ���id��ѯ������Ϣ
	 * @param no
	 * @return
	 */
	public List<Map<String,Object>> getTProcessCuttertypeInfo(String processId,String nodeid);
	
	/**
	 * ���ݹ���id��ѯ�о���Ϣ
	 * @param id
	 * @return
	 */
	public List<TPartProcessCost> getTPartProcessCost(String id);
	
	/**
	 * ���ݼо߱�Ų�ѯ�о���Ϣ
	 * @param no
	 * @return
	 */
	public List<TFixtureTypeInfo> getTFixtureTypeInfoById(String Id);
	/**
	 * ���¹��շ���
	 * @param plan
	 * @return
	 */
	public String updateTProcessplanInfo(TProcessplanInfo plan);
	/**
	 * ���շ�����ΪĬ��
	 * @param plan
	 * @return
	 */
	public String updateDeDefaultTProcessplanInfo(TProcessplanInfo plan);
	/**
	 * ��ѯ�豸�ڵ�
	 * @param search
	 * @return
	 */
	public TreeNode getTreeNodeEquInfo(String nodeid);
	/**
	 * �����豸����id �����豸
	 * @param list
	 * @return
	 */
	public List<TEquipmentInfo> getTEquipmentInfo(List list);
	/**
	 * �����豸����id���ҵ����豸
	 * @param id
	 * @return 
	 */
	public List<TEquipmentInfo> getTEquipmentInfoByEquSerialNo(String id);
	/**
	 * ���ݹ���id���ҹ����豸��
	 * @return
	 */
	public List<TProcessEquipmentModel> getTProcessEquipmentByProcessId(String processId);
	 /**
	   * ���ݹ���id��ȡ�о�����
	   * @return
	   */
	  public List<TProcessFixturetypeModel> getTProcessFixturetypeModelByProcessId(String processid);
	  /**
	   * ���ݹ����ȡ�ʼ�����
	   * @param processid
	   * @return
	   */
	  public List<TQualityParam> getTQualityParamByProcessId(String processid);
	  /**
	   * ���ݹ��շ�����ȡ�ʼ�����
	   * @param ProcessPlanId
	   * @return
	   */
	  public List<Map<String,Object>> getTQualityParamByProcessPlanId(String ProcessPlanId);
	  
	  /**
	   * ���ݲ�ѯ������ģ����ѯ�������
	   */
	  public List<TPartTypeInfo> getAllPartType(String nodeid, String partName);
	  
	  /**
	   * ����������Ʋ�ѯ�����Ϣ
	   * @return
	   */
	  public List<TPartTypeInfo> getPartTypeInfo(String partName);
	  /**
	   * ���ݹ���id ����������Ϣ
	   * @param processId
	   * @return
	   */
	  public List<Map<String,Object>> getProcessWL(String processId);
	  /**
	   * ��ȡ�����̨
	   * @param processId
	   * @return
	   */
	  public List<Map<String,Object>> getProcessJTData(String processId);
	  /**
		 * ���������Ż�ȡ ���μƻ�
		 * @param partId
		 * @return
		 */
		public List<Map<String,Object>> getJopPlanByPartId(String partId);
}
