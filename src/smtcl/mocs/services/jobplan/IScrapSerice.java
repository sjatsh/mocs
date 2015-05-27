package smtcl.mocs.services.jobplan;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.TProductionScrapInfoModel;
import smtcl.mocs.pojos.device.TNodes;

/**
 * ����ERP���ݿ��commonDao�ӿ�
 * @author songkaiang
 *
 */

public interface IScrapSerice extends IGenericService<TNodes, String>{
	
	
	/**
	 *  ������������
	 * @param tps ����ʵ����
	 * @param jobdispatchNo ��ǰѡ�еĹ�����
	 * @param isCurrentProcess �Ƿ��������Ѽӹ�
	 * @param processScrapNum ����
	 * @param materialScrapNum �Ϸ�
	 * @param onlineProcessId ǰ������id
	 * @param selectedPartId �������id
	 * @param  djgs ������ʱ
	 * @param selectedpl ���򼯺�
	 * @return
	 */
	public String saveTProductionScrapInfo(TProductionScrapInfoModel tps,String jobdispatchNo,List isCurrentProcess,String processScrapNum,
			String materialScrapNum,String onlineProcessId,String processPlanID,String selectedPartId,String djgs,List selectedpl);
	/**
	 * ��ȡ������ʾ��Ϣ
	 * @param jobdispatchNo
	 * @param onlineProcessId
	 * @param processScrapNum
	 * @param materialScrapNum
	 * @param isCurrentProcess
	 * @return
	 */
	public String getJobdispatchTSXX(String jobdispatchNo,String onlineProcessId,String processScrapNum,String materialScrapNum,
			List isCurrentProcess);
	/**
	 * ����id�ڵ�id��ȡ����б�
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getTPartTypeInfoByNodeid(String nodeid,String partId);
	/**
	 * ���������Ż�ȡ ���μƻ�
	 * @param partId
	 * @return
	 */
	public List<Map<String,Object>> getJopPlanByPartId(String partId);
}
