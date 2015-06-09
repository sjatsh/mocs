package smtcl.mocs.services.device;

import java.util.*;

import net.sf.cglib.core.Local;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.beans.device.JobdispatchChangeBean;
import smtcl.mocs.model.DeviceInfoModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquCurStatus;


/**
 * �û��鿴�豸�����ӿ� 
 * @����ʱ�� 2012-12-03
 * @���� liguoqiang
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 * @version V1.0
 */
public interface IDeviceService extends IGenericService<TNodes, String> {
	/**
	 * ��ȡ�豸��������ʷ����
	 * @param serialon �豸���к�
	 * @return  DeviceInfoModel
	 */
	public DeviceInfoModel getDeviceInfoModelHistory(String serialon);

	/**
	 * ��ȡ�豸������ʵʱ����
	 * @param serialon �豸���к�
	 * @return DeviceInfoModel
	 */
	public DeviceInfoModel getDeviceInfoModelRealtime(String serialon);

	/**
	 * ��ȡ�ڵ����豸����Ϣ
	 * @param nodeid �豸���к�
	 * @return List<DeviceInfoModel>
	 */
	public List<DeviceInfoModel> getDevicesOverview(Long nodeid);

	/**
	 * ʱ��Ƚ�-ͼ��Ĳ�ѯ
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> compareDevicesTime (Collection<Parameter> parameters,String startTime,String endTime);
	
	/**
	 * ʱ��ͳ�Ʋ�ѯ
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findIHisroryStatistics (String equSerialNo,String startTime,String endTime);
	
	/**
	 * ��̨�豸ʱ���OEE��ѯ 
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> selectServiceOEE(Collection<Parameter> parameters);		
	public List<Map<String, Object>> selectServiceOEE(String startTime,String endTime,String equSerialNo);
	/**
	 * ��ʷ������ҳ��ѯ�����
	 * @param pageNo ҳ��
	 * @param pageSize ����
	 * @param parameters ��ѯ��������
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getDeviceProFrequenceStat( int pageNo, int pageSize, Collection<Parameter> parameters);
	
	/**
	 * ��ʷ������ѯȫ�������
	 * @param parameters ��ѯ��������
	 * @return List
	 */
	public List getAllDeviceProFrequenceStat(Collection<Parameter> parameters);
	
	/**
	 * �����豸���Ʋ�ѯ������
	 * @param nameSelected �豸���к�
	 * @return List<String>
	 */
	public List<String> getPartNameByEquName(String nameSelected);

	/**
	 * ���ݽڵ�id��ȡ�豸��Ϣ
	 * @param nodeid �ڵ�id
	 * @return List
	 */
	public List getNodesAllEquName(String nodeid);
	
	/**
	 * �����豸���кŲ�ѯ�����豸��Ϣ
	 * @param equSerialNos �豸���к�
	 * @return List
	 */
	public List<TEquipmentInfo> getAllEquName(String equSerialNos);
	
	/**
	 * ��ȡ��ǰ�ڵ��������豸������״̬�������ӽڵ㣩
	 * @param nodeid �ڵ�id 
	 * @param pageNo ҳ��
	 * @param size ����
	 * @param status ״̬
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getNodesAllEquNameStruts(String nodeid, int pageNo, int size, String status);
	
	/**
	 * ��ȡĳ��ʱ�����ĳ��������oee��teep
	 * @param pageNo ҳ��
	 * @param pageSize ����
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param name ����
	 * @param dateType ��ѯʱ������
	 * @return IDataCollection<DeviceInfoModel> 
	 */
	public IDataCollection<DeviceInfoModel> getOEETEEP(int pageNo,int pageSize,Date start, Date end, String name, String dateType);
	
	/**
	 * �ӹ��¼�������Ӧ�Ĳ�ѯ 
	 * @param pageNo ҳ��
	 * @param pageSize ����
	 * @param parameters ��ѯ��������
	 * @param nodeIds ����ڵ�id
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getDeviceProcessEventStat(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);
	
	/**
	 * ��ȡ���������������
	 * @return List<String>
	 */
	public List<String> getAllPartName();

	/**
	 * ��ȡ���������¼�����
	 * @return List<String>
	 */
	public List<String> getAllEventName();

	/**
	 * ��ȡ��ѯ������,�������ݵ�����,��ȥ���ظ�
	 * @param parameters ��ѯ��������
	 * @return List<String>
	 */
	public List<String> getDateSum(Collection<Parameter> parameters);

	/**
	 * �����¼�������Ӧ�Ĳ�ѯ
	 * @param pageNo ҳ��
	 * @param pageSize ����
	 * @param parameters ��ѯ��������
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getDeviceMachineEventStat(Locale locale, int pageNo, int pageSize, Collection<Parameter> parameters,Map<String,String> eventMap);

	/**
	 * ��ȡ���ݿ�Ļ����¼���Ϣ
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getDeviceMachineEventStatChart(Locale locale,Collection<Parameter> parameters,Map<String,String> eventMap);
	public List<Map<String, Object>> getDeviceMachineEventStatChartDateCount(Collection<Parameter> parameters);
	/**
	 * �û���ѯ��̨����֮��� OEE���ƱȽϽ�� 
	 * @param startTime ��ʼʱ��	
	 * @param endTime ����ʱ��
	 * @param dateTimeType ʱ������
	 * @param serNames ����豸���к�
	 * @return List<DeviceInfoModel>
	 */
	public List<DeviceInfoModel> getDevicesOEECompare(Date startTime, Date endTime, String dateTimeType, String[] serNames);
	
	/**
	 * ��̨�豸ʱ��η������Ի���
	 * @param map ��Ҫ���������
	 */
	 public void convertData(Map<String, Object> map,Locale locale);
	 
	/**
	 * ���ݽڵ�ID��ȡ�ڵ�����
	 * @param nodeid �ڵ�id
	 * @return List
	 */
	public List<Map<String,Object>> getNodeName(String nodeid);

	/**
	 * ���ݽڵ�id ��ѯ�ӽڵ��������豸��Ϣ
	 * @param nodeid �ڵ�id
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getNodesAllEquNameInfo(String nodeid);

	/**
	 * flagΪ2��ȡ��Ȥ�б��豸�µ��豸
	 * @param userId �û�id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>>  getEquipmentAllEquName(String userId);
	
	/**
	 * ��ȡ��ǰ�ڵ����ӽڵ�
	 * @param nodeId �ڵ�id
	 * @return String
	 */
	public String getNodeAllId(String nodeId);
	
	/**
	 * ���ݽڵ��ѯ���豸����״̬
	 * @param nodeid �ڵ�id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getNodesAllEquNameStruts(String nodeid);
	
	/**
	 * ���ݽڵ�id ��ѯ�ýڵ��������豸������ �����ӽڵ�
	 * @param nodeid �ڵ�id
	 * @return List<TEquipmentInfo>
	 */
	public List<TEquipmentInfo> getNodesAllTEquipmentInfo(String nodeid);
	
	/**
	 * ��ȡʵʱ״̬ͼ������
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */ 
	public List<Map<String, Object>> getRealStatistics(Collection<Parameter> parameters);
	
	/**
	 * ��ʱ���UserEquCurStatus�����ݳ�����Сʱû�в�������Ϊͣ����
	 */ 
	public void UpdateUserEquCurStatus();
	
	/**
	 * ��ȡ�����ƻ������
	 * @param nodeid �ڵ�id
	 * @param date ʱ�� ��ʽyyyy-MM
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getppcr(String nodeid,String date);
	
	/**
	 * ��ȡ���ռӹ���
	 * @param nodeid
	 * @param date
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getNumberOfDayProcessing(String nodeid,String date);
	
	/**
	 * ��ȡ�豸Ч��
	 * @param nodeid
	 * @param date
	 * @return
	 */
	public List<Map<String,Object>> getEquEfficiency(String nodeid,String date);
	
	/**
	 *����״̬
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMachineToolStatus(String equSerialNo);
	
	/**
	 * �ӹ�����
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMachiningTask(String equSerialNo);
	
	/**
	 * �豸Ч��
	 * @param equSerialNo
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getEquipmentEfficiency(String equSerialNo);
	
	/**
	 * ��ȡ�ƶ��豸�ĵ��ռӹ���
	 * @param nodeid
	 * @param date ��ʽyyyy-MM-dd
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getappointdayEquNumber(String[] equSerialNo,String date);
	/**
	 * �����豸���кŻ�ȡ�豸��ά�����ַ
	 * @param equSerialNo
	 * @return
	 */
	public List getipAddress(String equSerialNo);
	/**
	 *�����豸���кŻ�ȡ�豸ʵʱ��Ϣ
	 * @param equSerialNo
	 * @return
	 */
	public List<TUserEquCurStatus> getTUserEquCurStatusByEqusrialno(String equSerialNo);
	/**
	 * ͨ����ǰ�ڵ��ѯ���ڵ�
	 */
	public String getParentIdByNodeid(String nodeid);
	/**
	 * ��ѯ�ڵ��µ��豸
	 * @param pId
	 * @return
	 */
	public List getTEquipmentInfoByPId(String pId);
	/**
	 * ����id��ȡ�豸�嵥��Ϣ
	 * @param nodeId
	 * @return
	 */
	public List<Map<String,Object>> getEquInventoryInfo(String nodeId);
	
	/**
	 * ��ȡ�豸��Ϣ
	 */
	public List<Map<String,Object>> queryEquipmentList(String equId,String equId2,String nodeId); 
	
	/**
	 * ��ȡ����List��Ϣ
	 * @param equipmentId
	 * @return
	 */
	public List<Map<String,Object>> queryJobDispatchList(String equipmentId,String dispatchId); 
	
	/**
	 * ��ȡ��Ա��Ϣ
	 */
	public List<Map<String,Object>> getUserList(String userId,String nodeId);
	
	/**
	 * ���汨����Ϣ
	 */
	public String saveInfo(int num,String userId,String equId,String dispatchId,Date startTime,Date finishTime,String partNo,String loginUserNo,String isGood,
			 String depName,String jgCheckUser,String zpCheckUser,String sjCheckUser);
	
	/**
	 * ��֤���������Ƿ�Ϸ�
	 */
	public String checkNum(String dispatchId, int num);
	
	/**
	 * ��ȡ���������
	 */
	public List<Map<String,Object>> getPartTypeMap(String partId,String nodeId);
	
	/**
	 * ��ȡ�����ż���
	 */
	public List<Map<String,Object>> getDispatchNoMap();
	
	/**
	 * ��ȡ�豸���кż���
	 */
	public List<Map<String,Object>> getEquTypeMap(String nodeId);

    /**
     * ��ȡ�豸���кż���
     */
    public List<Map<String,Object>> getEquTypeMap();
	
	/**
	 * ��ȡ�豸���кż���(������)
	 */
	public List<Map<String,Object>> getEquSerialNoMap(String query);
	
	/**
	 * ��ȡ�豸���Ƽ���
	 */
	public List<Map<String,Object>> getEquTypeNameMapByNodeId(String nodeId);
	
	/**
	 * ��ȡ�豸���Ƽ���(������)
	 */
	public List<Map<String,Object>> getEquTypeNameMap(String query);
	
	/**
	 * ��ȡ���� 
	 * @param equSerialNo
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> getRCLData(String equSerialNo,int type);
	/**
	 * ��ȡmember��Ϣ
	 */
	public List<Map<String, Object>> getMemberInfo(String userId,String nodeId);
	/**
	 * ��ȡ����������Ϣ
	 */
	public List<Map<String,Object>> getJobDispatch(String dispatchId); 
	/**
	 * ��ȡ����No����
	 */
	public List<Map<String,Object>> getJobPlanNoList(String partId,String jobplanId); 
	/**
	 * ��ȡ����No����
	 */
	public List<Map<String,Object>> getJobdispatchNoList(String jobplanNo); 
	/**
	 * ��ȡ���μ���
	 */
	public List<Map<String,Object>> getJobplanList(String jobplanId); 
	/**
	 * ��ȡ�ۺ���Ϣ(���������޸�)
	 */
	public List<Map<String,Object>> getDataList(String equ_SerialNo,String partTypeID,String processID,Date dateTime); 
  
	/**
	 * ��ȡ������Ϣ
	 */
	public List<Map<String,Object>> getProcessInfo(String processId); 
	
	/**
	 * ��ȡ�豸������Ϣ
	 */
	public List<Map<String,Object>> getEquProductionInfo(String equ_SerialNo,String partTypeID,String processID,String updateDate); 
	/**
	 * �޸ı������ݴ���
	 */
	public String saveChangeProcessNum(String num,String userId,String jobdispatchId,String equ_SerialNo,String partTypeID,String processID,Date updateDate,String loginUserNo,String eventNo); 
    
	/**
	 * ��ȡList��Ϣ
	 */
	public List<Map<String,Object>> getListInfo(String sql); 
	/**
	 * ��Ʒ�����Ϣ����
	 * @param num �������
	 * @param inventoryId�ⷿid
	 * @param materialPositionId ��λId
	 * @param partId ��ƷID
	 * @param dataList�б���Ϣ
	 */
	public String saveInStockDataInfo(String num,String inventoryId,String materialPositionId,String partId,String instockNo,List<Map<String,Object>> dataList);
}
