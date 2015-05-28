package smtcl.mocs.services.erp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.model.erp.JobPlanImportInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;

/**
 * 任务批次导入功能service接口
 * @author songkaiang
 *
 */
public interface IImportService extends IGenericService<Object, String>{

	/**
	 * 获取要导入到IJobPlanInfo表中的信息
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<JobPlanImportInfo> getJobPlanImportInfo(Date startTime,Date endTime,int flag);
	
	/**
	 * 将WIS_Entity表中的flag更改状态
	 * @param map
	 * @param flag
	 * @return
	 */
	public boolean updateWISEntity(String assemblyItemId,String uploaddate, int flag);
	
	/**
	 * 将jp信息保存到TJobplanInfo（t_jobplan_info）表中
	 * @param jp
	 * @param nodeid
	 * @return 保存成功返回true，否则返回false
	 */
	public boolean saveInfoToPartType(JobPlanImportInfo jp,String nodeid);

	/**
	 * 根据no和name获取零件信息
	 * @param no
	 * @param name
	 * @return
	 */
	public List<TPartTypeInfo> getPartTypeInfo(String no,String name);

	/**
	 * 向Wis_Transfer表写入数据
	 * @param eventId
	 */
	public void insertWisTransfer(long eventId);
	
	/**
	 * 手动报工向Wis_Transfer表写入数据
	 * @param eventId
	 * @author YT
	 */
	public void insertWisTransfer(Integer eventId);

	/**
	 * 检车WIS是否包含未导入Erp中的报工
	 */
	public void chaseLeaks();
	
	/**
	 * 产品成套入库向Wis_Transfer表写入数据
	 * @param inStockNum
	 * @param jobplanNo
	 * @param instockNo
	 * @author FW
	 */
	public void inStockinsertWisTransfer(Integer inStockNum,String jobplanNo,String instockNo);
	/**
	 * 根据任务号查找子库存号
	 */
	public List<Map<String,Object>> getInventoryCode(String no);
	
	/**
	 * 手动报工400序检验信息保存
	 * @author FW
	 */
	public void insertWisQaCheck(int num,String partNo,String partName,String dispatchNo,String isGood,
			String depName,String jgCheckUser,String zpCheckUser,String sjCheckUser,String createUser);
}
