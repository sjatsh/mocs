package smtcl.mocs.services.device;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.ProcessQualityModel;
import smtcl.mocs.model.ProductProcessModel;
import smtcl.mocs.model.TPartBasicInfoModel;
import smtcl.mocs.model.productInProgressModel;
import smtcl.mocs.pojos.job.TPartBasicInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;

/**
 * ��Ʒ��ѯҵ��ӿ�
 * @���ߣ�JiangFeng
 * @����ʱ�䣺2013-04-26
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public interface IProductService {

	/**
	 * ��ѯ��Ʒ�����嵥
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @param nodeIds �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getProductType(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);
	/**
	 * ��Ʒ�������ݲ�ѯ
	 * @param partTypeId
	 * @param partClassNo
	 * @param batchNumber
	 * @param onlineStartTime
	 * @param onlineEndTime
	 * @param offlineStartTime
	 * @param offlineEndTime
	 * @param productSerial
	 * @return
	 */
	public List<TPartBasicInfoModel> getProductBasicList(String partTypeId,String partClassNo,String batchNumber,String onlineStartTime,String onlineEndTime,
			String offlineStartTime,String offlineEndTime,String productSerial);
	/**
	 * ��Ʒ�������ݲ�ѯ
	 * @param productSerial
	 * @return
	 */
	public List<ProductProcessModel> getProductProcessList(String productSerial);
	/**
	 * ����������Ͳ�ѯ�������Ʒ
	 * @param partTypeId
	 * @return
	 */
  public List<TPartBasicInfo> getTPartBasicInfoByPartTypeId(String partTypeId);
  /**
   * ���������Ʒ��ѯ����
   * @param partBasicId
   * @return
   */
  public List<Map<String,Object>> getTProcessInfoByPartBasicId(String partBasicId);
  /**
	 * �����������ݲ�ѯ
	 * @param selectPartType
	 * @param productSerial
	 * @param selectProcess
	 * @param onStartTime
	 * @param onEndTime
	 * @return
	 */
	public List<ProcessQualityModel> getProcessQualityList(String selectPartType,String productSerial,String selectProcess,
			String onStartTime,String onEndTime);
	/**
	   * ���ݽڵ�id ��ѯ�������
	   * @param nodeId
	   * @return
	   */
	  public List<TPartTypeInfo> getTPartTypeInfoByNodeId(String nodeId,String procuct);
	  public List<TPartTypeInfo> getTPartTypeInfoByName(String name);
	/**
	   * ���ݽڵ�id �����������ۺϲ�ѯ���
	   * @param nodeId
	   * @return
	*/
	  public TreeNode getProductInProgress(String startTime,String endTime,String procuct,String batchNo,String nodeId);
	
	/**
	   * �������id ��ȡ���κ�
	   * @param partId
	   * @return
	   */
	  public List<Map<String,Object>> getbatchNo(String partId);
	  
	  /**
	   * ���ݽڵ�id �����������ۺϲ�ѯ���
	   * @param nodeId
	   * @return
	   */
	  public List<productInProgressModel> getProcessstaticesById(int row,int pageSize,String startTime,String endTime,String procuct,String batchNo,String nodeId);
	  
	  /**
		 * ͨ��������ѯ���������ڵ���
		 * @param nodeId 
		 * @param startTime ������ʼʱ��
		 * @param endTime ��������ʱ��
		 * @param taskNum ������
		 * @param jobstatus ����״̬
		 * @param partid ������ƶ�Ӧ�����id
		 * @param equid �豸��Ӧ��id
		 * @return
		 */
	  public TreeNode getBaseJobListTree(String nodeId,String startTime,String endTime,String taskNum,String jobstatus, String partid,String equid);
	  
	  /**
	   * ��ȡ�����б�Ľڵ���
	   * @return
	   */
	  public TreeNode getJobListTree(String nodeId,String startTime,String endTime,String taskNum,String jobstatus, String partid,String equid);
	  
	  /**
	   * ��ȡ������ʾ��Ϣ
	   * @return
	   */
	  public String getJobdispatchTS();
		
}
