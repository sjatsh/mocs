package smtcl.mocs.services.erp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.model.erp.JobPlanImportInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;

/**
 * �������ε��빦��service�ӿ�
 * @author songkaiang
 *
 */
public interface IImportService extends IGenericService<Object, String>{

	/**
	 * ��ȡҪ���뵽IJobPlanInfo���е���Ϣ
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return
	 */
	public List<JobPlanImportInfo> getJobPlanImportInfo(Date startTime,Date endTime,int flag);
	
	/**
	 * ��WIS_Entity���е�flag����״̬
	 * @param map
	 * @param flag
	 * @return
	 */
	public boolean updateWISEntity(String assemblyItemId,String uploaddate, int flag);
	
	/**
	 * ��jp��Ϣ���浽TJobplanInfo��t_jobplan_info������
	 * @param jp
	 * @param nodeid
	 * @return ����ɹ�����true�����򷵻�false
	 */
	public boolean saveInfoToPartType(JobPlanImportInfo jp,String nodeid);

	/**
	 * ����no��name��ȡ�����Ϣ
	 * @param no
	 * @param name
	 * @return
	 */
	public List<TPartTypeInfo> getPartTypeInfo(String no,String name);

	/**
	 * ��Wis_Transfer��д������
	 * @param eventId
	 */
	public void insertWisTransfer(long eventId);
	
	/**
	 * �ֶ�������Wis_Transfer��д������
	 * @param eventId
	 * @author YT
	 */
	public void insertWisTransfer(Integer eventId);

	/**
	 * �쳵WIS�Ƿ����δ����Erp�еı���
	 */
	public void chaseLeaks();
	
	/**
	 * ��Ʒ���������Wis_Transfer��д������
	 * @param inStockNum
	 * @param jobplanNo
	 * @param instockNo
	 * @author FW
	 */
	public void inStockinsertWisTransfer(Integer inStockNum,String jobplanNo,String instockNo);
	/**
	 * ��������Ų����ӿ���
	 */
	public List<Map<String,Object>> getInventoryCode(String no);
	
	/**
	 * �ֶ�����400�������Ϣ����
	 * @author FW
	 */
	public void insertWisQaCheck(int num,String partNo,String partName,String dispatchNo,String isGood,
			String depName,String jgCheckUser,String zpCheckUser,String sjCheckUser,String createUser);
}
