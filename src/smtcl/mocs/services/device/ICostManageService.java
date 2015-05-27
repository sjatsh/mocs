package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.pojos.device.TNodes;

/**
 * 成本管理+调度算法接口 
 * @创建时间 2013-6-20
 * @作者 yutao
 * @修改者
 * @修改日期
 * @修改说明
 * @version V1.0
 */
public interface ICostManageService extends IGenericService<TNodes, String> {
	
	/**
	 * 获取成本清单列表
	 * @param pageNo
	 * @param pageSize
	 * @param partTypeNo
	 * @return
	 */
	public List<Map<String, Object>> queryProcessCostList(int pageNo, int pageSize,String partTypeNo);	
	
	/**
	 * 通过时间 机台成本
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
      * 通过产品编号查询	 机台成本
      * @param partTypeNo
      * @param equNo
      * @param startTime
      * @param endTime
      * @return
      */
	public List<Map<String, Object>> queryEquipmentCostListByEquno(String partTypeNo,String equNo,String startTime,String endTime);

	
	/**
	 * 通过类型或者名称得到结果集。成本分析
	 * @param partTypeNo
	 * @return
	 */
	public List<Map<String, Object>> queryProductRealCostAnalysis(String  partTypeNo,String partNo);
	
	/**
	 * 理论值
	 * @param partTypeNo
	 * @return
	 */
	public Map<String,Object> getProductTheoryCostAnalysis(String partTypeNo);
	
	/**
	 * 产品类型列表
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getPartTypeList(String nodeid);
	
	/**
	 * 产品列表
	 */	
	public List<Map<String, Object>> getPartNoList(String partTypeNo);
	
	/**
	 * 加工事件调度--A3 
	 */
	//public Boolean processEquWorkEvent(Long eventId,String partNo);
	
	/**
	 * 加工事件调度--上海展,沿用B21
	 */
	public Boolean processEquWorkEvent(Long eventId);
	
	/**
	 * 获取最新产品的成本
	 */
	public List<Map<String, Object>> queryLastPartCost(String equSerialNo);
	
	/**
	 * 生产概况获取RCR信息
	 * @param nodeId
	 * @return
	 */
	public String getRCR(String nodeId);
	
	/**
	 * 获取设备能耗,设备序列号，逗号分隔
	 * @param equSerialNos
	 * @return
	 */
	public String getEquRCR(String equSerialNos);
}
