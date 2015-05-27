package smtcl.mocs.services.jobplan;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.TProductionScrapInfoModel;
import smtcl.mocs.pojos.device.TNodes;

/**
 * 操作ERP数据库的commonDao接口
 * @author songkaiang
 *
 */

public interface IScrapSerice extends IGenericService<TNodes, String>{
	
	
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
	public String saveTProductionScrapInfo(TProductionScrapInfoModel tps,String jobdispatchNo,List isCurrentProcess,String processScrapNum,
			String materialScrapNum,String onlineProcessId,String processPlanID,String selectedPartId,String djgs,List selectedpl);
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
