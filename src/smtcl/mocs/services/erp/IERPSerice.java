package smtcl.mocs.services.erp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.primefaces.model.TreeNode;

import smtcl.mocs.pojos.job.TProductionScrapInfo;
/**
 * 操作ERP数据库的commonDao接口
 * @author songkaiang
 *
 */

public interface IERPSerice{
	/**
	 * 保存 TProductionScrapInfo对象
	 * 工序报废信息回传
	 * @param tps  				报废对象
	 * @param wy   				唯一标识
	 * @param processScrapNum   工废数量
	 * @param materialScrapNum	料废数量
	 * @param totleTime			单件工时
	 * @param totlePrice		外协费用
	 * @param scrapType			报废类型
	 * @return
	 */
	public String saveWisQaScrap(TProductionScrapInfo tps,String wy,String processScrapNum,String materialScrapNum,
			String totleTime,String totlePrice,int scrapType);
	/**
	 * 保存 回传表
	 * WisTransfer
	 * @param tps
	 * @param resourceCode  资源代码
	 * @param SumScrapNum 报废总数
	 * @return
	 * @throws Exception 
	 */
	public String saveWisTransfer(TProductionScrapInfo tps,String resourceCode,String SumScrapNum,String wy,String scrapUserName) throws Exception;
	/**
	 * 根据时间 零件编码来查询数据
	 * @param startTime 
	 * @param endTime
	 * @param ItemCode
	 * @return
	 */
	public List<Map<String,Object>> getErpPartTypeList(Date startTime,Date endTime,String ItemCode);
	/**
	 * 根据零件id 获取工序
	 * @param ItemId
	 * @return
	 */
	public List<Map<String,Object>> getErpProcess(String ItemId);
	/**
	 * 获取成本
	 * @param ItemId 零件id
	 * @param processNum 工序号
	 * @return
	 */
	public Map<String,Object> getErpCost(String ItemId,String processNum);
	/**
	 * 根据工序获取物料
	 * @param partId
	 * @return
	 */
	public List<Map<String,Object>> getErpMaterail(String partId,String processNum);
	/**
	 * 获取机台数据
	 * @param ItemId
	 * @param processNum
	 * @return
	 */
	public List<Map<String,Object>> getErpmachine (String ItemId,String processNum);
	/**
	 * 根据零件编号 工序号 获取 加工时间 工序名字等信息
	 * @param ItemCode
	 * @param processNum
	 * @return
	 */
	public List<Map<String,Object>> getProcessData(String ItemCode,String processNum);
	/**
	 * 保存数据到wis
	 * @param selectedErpRoot
	 * @param nodeid
	 * @return
	 */
	public String saveWisData(TreeNode[] selectedErpRoot,String nodeid);
	/**
	 * 获取物料数据
	 */
	public List<Map<String,Object>> getErpMaterailSelect(String isselect,String search,int pOrm);
	/**
	 * 从中间库导入物料数据
	 * @param milist
	 * @return
	 */
	public String saveMaterailData(List<Map<String,Object>> milist,String nodeid);
	/**
	 * 从中间库导入零件数据
	 * @param milist
	 * @return
	 */
	public String savePartData(List<Map<String,Object>> milist,String nodeid);
	/**
	 *  保存生产报废
	 * @param tps 保存实体类
	 * @param jobdispatchNo 当前选中的工单号
	 * @param isCurrentProcess 是否至工序已加工
	 * @param processScrapNum 工废
	 * @param materialScrapNum 料废
	 * @param onlineProcessId 前道工序id
	 * @param selectedPartId 零件类型id
	 * @param  djgs 当件工时
	 * @param selectedpl 工序集合
	 * @return
	 */
	public String saveTProductionScrapInfo(TProductionScrapInfo tps,String jobdispatchNo,List isCurrentProcess,String processScrapNum,
			String materialScrapNum,String onlineProcessId,String processPlanID,String selectedPartId,String djgs,List selectedpl,
			String scrapUserName);
	/**
	 * 获取责任厂家
	 * @return
	 */
	public List<Map<String,Object>> getWisVendorListMapForAll();
	/**
	 * 获取报废账户列表
	 * @return
	 */
	public List<Map<String,Object>> getScrapUser();
	/**
	 * 获取工单提示信息
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
	 * 根据id节点id获取零件列表
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getTPartTypeInfoByNodeid(String nodeid,String partId);
	/**
	 * 根据零件编号获取 批次计划
	 * @param partId
	 * @return
	 */
	public List<Map<String,Object>> getJopPlanByPartId(String partId);
}
