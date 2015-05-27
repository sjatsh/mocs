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
 * 产品查询业务接口
 * @作者：JiangFeng
 * @创建时间：2013-04-26
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public interface IProductService {

	/**
	 * 查询产品类型清单
	 * @param pageNo 当前页码
	 * @param pageSize 总页码
	 * @param parameters 参数
	 * @param nodeIds 节点信息
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getProductType(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);
	/**
	 * 产品基础数据查询
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
	 * 产品过程数据查询
	 * @param productSerial
	 * @return
	 */
	public List<ProductProcessModel> getProductProcessList(String productSerial);
	/**
	 * 根据零件类型查询出零件产品
	 * @param partTypeId
	 * @return
	 */
  public List<TPartBasicInfo> getTPartBasicInfoByPartTypeId(String partTypeId);
  /**
   * 根据零件产品查询工序
   * @param partBasicId
   * @return
   */
  public List<Map<String,Object>> getTProcessInfoByPartBasicId(String partBasicId);
  /**
	 * 过程质量数据查询
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
	   * 根据节点id 查询零件类型
	   * @param nodeId
	   * @return
	   */
	  public List<TPartTypeInfo> getTPartTypeInfoByNodeId(String nodeId,String procuct);
	  public List<TPartTypeInfo> getTPartTypeInfoByName(String name);
	/**
	   * 根据节点id 返回在制数综合查询结果
	   * @param nodeId
	   * @return
	*/
	  public TreeNode getProductInProgress(String startTime,String endTime,String procuct,String batchNo,String nodeId);
	
	/**
	   * 根据零件id 获取批次号
	   * @param partId
	   * @return
	   */
	  public List<Map<String,Object>> getbatchNo(String partId);
	  
	  /**
	   * 根据节点id 返回在制数综合查询结果
	   * @param nodeId
	   * @return
	   */
	  public List<productInProgressModel> getProcessstaticesById(int row,int pageSize,String startTime,String endTime,String procuct,String batchNo,String nodeId);
	  
	  /**
		 * 通过参数查询工单工单节点树
		 * @param nodeId 
		 * @param startTime 工单开始时间
		 * @param endTime 工单结束时间
		 * @param taskNum 任务编号
		 * @param jobstatus 工单状态
		 * @param partid 零件名称对应的零件id
		 * @param equid 设备对应的id
		 * @return
		 */
	  public TreeNode getBaseJobListTree(String nodeId,String startTime,String endTime,String taskNum,String jobstatus, String partid,String equid);
	  
	  /**
	   * 获取工单列表的节点树
	   * @return
	   */
	  public TreeNode getJobListTree(String nodeId,String startTime,String endTime,String taskNum,String jobstatus, String partid,String equid);
	  
	  /**
	   * 获取工单提示信息
	   * @return
	   */
	  public String getJobdispatchTS();
		
}
