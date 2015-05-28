package smtcl.mocs.services.erp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.primefaces.model.TreeNode;

import smtcl.mocs.pojos.job.TProductionScrapInfo;
/**
 * ����ERP���ݿ��commonDao�ӿ�
 * @author songkaiang
 *
 */

public interface IERPSerice{
	/**
	 * ���� TProductionScrapInfo����
	 * ���򱨷���Ϣ�ش�
	 * @param tps  				���϶���
	 * @param wy   				Ψһ��ʶ
	 * @param processScrapNum   ��������
	 * @param materialScrapNum	�Ϸ�����
	 * @param totleTime			������ʱ
	 * @param totlePrice		��Э����
	 * @param scrapType			��������
	 * @return
	 */
	public String saveWisQaScrap(TProductionScrapInfo tps,String wy,String processScrapNum,String materialScrapNum,
			String totleTime,String totlePrice,int scrapType);
	/**
	 * ���� �ش���
	 * WisTransfer
	 * @param tps
	 * @param resourceCode  ��Դ����
	 * @param SumScrapNum ��������
	 * @return
	 * @throws Exception 
	 */
	public String saveWisTransfer(TProductionScrapInfo tps,String resourceCode,String SumScrapNum,String wy,String scrapUserName) throws Exception;
	/**
	 * ����ʱ�� �����������ѯ����
	 * @param startTime 
	 * @param endTime
	 * @param ItemCode
	 * @return
	 */
	public List<Map<String,Object>> getErpPartTypeList(Date startTime,Date endTime,String ItemCode);
	/**
	 * �������id ��ȡ����
	 * @param ItemId
	 * @return
	 */
	public List<Map<String,Object>> getErpProcess(String ItemId);
	/**
	 * ��ȡ�ɱ�
	 * @param ItemId ���id
	 * @param processNum �����
	 * @return
	 */
	public Map<String,Object> getErpCost(String ItemId,String processNum);
	/**
	 * ���ݹ����ȡ����
	 * @param partId
	 * @return
	 */
	public List<Map<String,Object>> getErpMaterail(String partId,String processNum);
	/**
	 * ��ȡ��̨����
	 * @param ItemId
	 * @param processNum
	 * @return
	 */
	public List<Map<String,Object>> getErpmachine (String ItemId,String processNum);
	/**
	 * ���������� ����� ��ȡ �ӹ�ʱ�� �������ֵ���Ϣ
	 * @param ItemCode
	 * @param processNum
	 * @return
	 */
	public List<Map<String,Object>> getProcessData(String ItemCode,String processNum);
	/**
	 * �������ݵ�wis
	 * @param selectedErpRoot
	 * @param nodeid
	 * @return
	 */
	public String saveWisData(TreeNode[] selectedErpRoot,String nodeid);
	/**
	 * ��ȡ��������
	 */
	public List<Map<String,Object>> getErpMaterailSelect(String isselect,String search,int pOrm);
	/**
	 * ���м�⵼����������
	 * @param milist
	 * @return
	 */
	public String saveMaterailData(List<Map<String,Object>> milist,String nodeid);
	/**
	 * ���м�⵼���������
	 * @param milist
	 * @return
	 */
	public String savePartData(List<Map<String,Object>> milist,String nodeid);
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
	public String saveTProductionScrapInfo(TProductionScrapInfo tps,String jobdispatchNo,List isCurrentProcess,String processScrapNum,
			String materialScrapNum,String onlineProcessId,String processPlanID,String selectedPartId,String djgs,List selectedpl,
			String scrapUserName);
	/**
	 * ��ȡ���γ���
	 * @return
	 */
	public List<Map<String,Object>> getWisVendorListMapForAll();
	/**
	 * ��ȡ�����˻��б�
	 * @return
	 */
	public List<Map<String,Object>> getScrapUser();
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
