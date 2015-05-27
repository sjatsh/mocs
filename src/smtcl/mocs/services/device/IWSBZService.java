package smtcl.mocs.services.device;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.Parameter;

/**
 * ��չ�߼���ӿ� * 
 * @����ʱ�� 2013-03-05
 * @���� liguoqiang
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 * @version V1.0
 */
public interface IWSBZService {
	/**
	 * �����豸��ǰ״̬
	 * @param equSerialNo  �豸���к�
	 * @param updateTime ״̬ʱ��
	 * @param status ����״̬
	 * @param programm ��ǰִ���ļ�
	 * @param toolNo ��ǰ���ߺ�
	 * @param part �ڼӹ������Ϣ
	 * @param partTimeCount ��ǰ������ʱ
	 * @param feedSpeed ��ǰ�����ٶ�
	 * @param feedRate ��������
	 * @param axisSpeed ��ǰ����ת��
	 * @param axisSpeedRate ���ᱶ��
	 * @param xFeed ����X
	 * @param yFeed ����Y
	 * @param zFeed ����Z
	 * @param cuttingPower ��������
	 * @param operator ������Ա
	 * @param spindleLaod ���Ḻ�� 
	 * @param partCount ��������
	 * @return boolean
	 */
	public boolean updateCurStatus(String equSerialNo,String updateTime,String status,String programm,
			String toolNo,String part,String partTimeCount,String feedSpeed,String feedrate,
			String axisspeed,String axisspeedRate,String xfeed,String yfeed,String zfeed,String cuttingpower,
			String operator,String spindleLoad,String partCount,String afeed,String bfeed,String cfeed,
			String ufeed,String vfeed,String wfeed);
	
	public boolean updateCurStatus(String equSerialNo,String updateTime,String status,String programm,
			String toolNo,String part,String partTimeCount,String feedSpeed,String feedrate,
			String axisspeed,String axisspeedRate,String xfeed,String yfeed,String zfeed,String cuttingpower,
			String operator,String spindleLoad,String partCount);
	
	/**
	 * �����豸��ǰ����
	 * @param equSerialNo �豸���к�
	 * @param updateTime ����ʱ��
	 * @param runningTime ����ʱ��
	 * @param stopTime �ػ�ʱ��
	 * @param processTime �ӹ�ʱ��
	 * @param prepareTime ׼��ʱ��
	 * @param idleTime ���д���ʱ��
	 * @param breakdownTime ����ʱ��
	 * @param cuttingTime ����ʱ��
	 * @param dryRunningTime ������ʱ��
	 * @param toolChangeTime ����ʱ��
	 * @param manualRunningTime �ֶ�����ʱ��
	 * @param materialTime ������ʱ��
	 * @param otherTime ����ʱ��
	 * @param totalProcessPartsCount �ܹ�������
	 * @param anualProcessPartsCount �깤������
	 * @param monthProcessPartsCount �¹�������
	 * @param dayProcessPartsCount �չ�������
	 * @param workTimePlan �ƻ�����ʱ��
	 * @return boolean
	 */
	public boolean updateStatusStore(String equSerialNo,String updateTime,String runningTime,String stopTime,
			String processTime,String prepareTime,String idleTime,String breakdownTime,String cuttingTime,
			String dryRunningTime,String toolChangeTime,String manualRunningTime,String materialTime,String otherTime,
			String totalProcessPartsCount,String anualProcessPartsCount,String monthProcessPartsCount,
			String dayProcessPartsCount,String workTimePlan);
	
	/**
	 * �����豸������־
	 * @param equSerialNo �豸���к�
	 * @param updatedate ����ʱ��
	 * @param runningTime ����ʱ��
	 * @param stopTime �ػ�ʱ��
	 * @param processTime �ӹ�ʱ��
	 * @param prepareTime ׼��ʱ��
	 * @param idleTime ���д���ʱ��
	 * @param breakdownTime ����ʱ��
	 * @param cuttingTime ����ʱ��
	 * @param dryRunningTime ������ʱ��
	 * @param toolChangeTime ����ʱ��
	 * @param manualRunningTime �ֶ�����ʱ��
	 * @param materialTime ������ʱ��
	 * @param otherTime ����ʱ��
	 * @param totalProcessPartsCount �ܹ�������
	 * @param anualProcessPartsCount �깤������
	 * @param monthProcessPartscount �¹�������
	 * @param dayProcessPartsCount �չ�������
	 * @param workTimePlan �ƻ�����ʱ��
	 * @return boolean
	 */
	public boolean InsertStatusDaily(String equSerialNo,String updatedate,String runningTime,String stopTime,
			String processTime,String prepareTime,String idleTime,String breakdownTime,String cuttingTime,
			String dryRunningTime,String toolChangeTime,String manualRunningTime,String materialTime,String otherTime,
			String totalProcessPartsCount,String anualProcessPartsCount,String monthProcessPartscount,
			String dayProcessPartsCount,String workTimePlan);
	
	/**
	 * ���¼ӹ��¼�
	 * @param equSerialNo �豸���к�
	 * @param cuttingeventId �ӹ��¼�ID
	 * @param starttime ��ʼʱ��
	 * @param finishtime ����ʱ��
	 * @param cuttingTask �ӹ�����
	 * @param ncprogramm ���س�����
	 * @param partName �������
	 * @param theoryWorktime ���ۼӹ�ʱ��
	 * @param cuttingTime ����ʱ��
	 * @param toolchangeTime ����ʱ��
	 * @param workTime �ӹ���ʱ
	 * @param workResult �ӹ����
	 * @param theoryCycletime ��
	 * @return boolean
	 */
	public boolean InsertWorkEvents(String equSerialNo, String cuttingeventId,String starttime, String finishtime,
			String cuttingTask,String ncprogramm,String partName,String theoryWorktime,String cuttingTime,
			String toolchangeTime,String workTime,String workResult,String theoryCycletime);
	
	/**
	 * ���»����¼�
	 * @param equSerialNo �豸���к�
	 * @param eventId �¼�ID
	 * @param eventTime �¼�ʱ��
	 * @param eventName �¼�����
	 * @param programmname ������
	 * @param eventMemo ��ע
	 * @return boolean
	 */
	public boolean InsertEvents(String equSerialNo,String eventId,String eventTime,String eventName,
			String programmname,String eventMemo);
	/**
	 * ����OEE��־
	 * @param equSerialNo �豸���к�
	 * @param caclDate ����
	 * @param worktimeFact ʵ�ʿ���ʱ��
	 * @param worktimePlan �ƻ�����ʱ��
	 * @param acturalOutputTheorytime ʵ�ʲ������ۺ�ʱ
	 * @param processPartsCount �ӹ�������
	 * @param qualifiedPartCount �ϸ񹤼���
	 * @param dayOeevalue ����OEE
	 * @param dayTeepvalue ����TEEP
	 * @return boolean
	 */
	public boolean InsertOEEdaily(String equSerialNo,String caclDate,String worktimeFact,String worktimePlan,
			String acturalOutputTheorytime,String processPartsCount,String qualifiedPartCount,
			String dayOeevalue,String dayTeepvalue);
	
	/**
	 * ����OEE��־�ۼ�
	 * @param equSerialNo �豸���к�
	 * @param year ���
	 * @param month �·�
	 * @param weekofYear ����
	 * @param totalworkTimeFact ��ʵ�ʿ���ʱ��
	 * @param totalWorkTimePlan �ܼƻ�����ʱ��
	 * @param totalActOutputTheoryTime ��ʵ�ʲ������ۺ�ʱ
	 * @param totalProcesspartscount �ܼӹ�������
	 * @param totalQualifiedPartCount �ܺϸ񹤼���
	 * @param totalOeevalue �ý׶�OEE
	 * @param totalTeepvalue �ý׶�TEEP
	 * @return boolean
	 */
	public boolean InsertOEEstore(String equSerialNo,String year,String month,String weekofYear,String totalworkTimeFact,
			String totalWorkTimePlan,String totalActOutputTheoryTime,String totalProcesspartscount,
			String totalQualifiedPartCount, String totalOeevalue,String totalTeepvalue);
	
	/**
	 * ��ȡ����ָ��
	 * @param equSerialNo �豸���к�
	 * @param programm  ��ǰ������
	 * @return String
	 */
	public String getProductionOrder(String equSerialNo,String programm);
	
	/**
	 * �����������IO
	 * @param equSerialNo �豸���к�
	 * @param equIo IO�ַ���
	 * @param updateDate ����ʱ��
	 * @return boolean
	 */
	public boolean insertIntelligentDiagnoseIO(String equSerialNo,String equIo,String updateDate);
	
	/**
	 * ��ȡ�����Ϣ
	 * @param equIo IO�ַ���
	 * @return map
	 */
	public List<Map<String, Object>> get_Diagnostic_Message(String equ_SerialNo,String componentType);
	
	/**
	 * ��ȡ��������
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMachineComponentInfo(String equSerialNo);
	
	/**
	 * ��ȡ����б���Ϣ
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getDiagnoseList(String equSerialNo);
	
	/**
	 * ��ȡ�����Ϣ
	 * @param equIo IO�ַ���(xml)
	 * @return map
	 */
	public String get_Diagnostic_Message_for_xml(String equ_SerialNo,String componentType); 
	
	/**
	 * ��ȡ�����Ϣ
	 * @param equIo IO�ַ���(xml)
	 * @return map
	 */
	public String get_Diagnostic_Message_for_xml_by_nodename(String equ_SerialNo,String componentType,String name_node);  
	
}
