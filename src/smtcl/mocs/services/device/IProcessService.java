package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

/**
 * 工序类服务
 * @author songkaiang
 *
 */
public interface IProcessService {

	/**
	 * 通过零件名称获取工序名称信息
	 * @param partTypeName 零件信息
	 * @param nodeid
	 * @return
	 */
	public List<Map<String, Object>> getProcessInfo(String partTypeName, String nodeid);
	
	/**
	 * 通过任务号，获取批准
	 */
	public String getBatchNoByNo(String no);
}
