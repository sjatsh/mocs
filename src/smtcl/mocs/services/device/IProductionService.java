package smtcl.mocs.services.device;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.device.TNodeProductionProfiles;


/**
 * 生产状态查询服务接口
 * @作者：ZhaoHongshi
 * @创建时间：2012-11-13 下午3:00:25
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface IProductionService  extends IGenericService<TNodeProductionProfiles, String> {

	/**
	 * 查询组织节点生产状态信息 
	 * @param pageNo 当前页码
	 * @param pageSize 总页码
	 * @param parameters 参数
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getProductOutput(int pageNo, int pageSize, Collection<Parameter> parameters);

	/**
	 * 查询组织节点生产计划信息
	 * @param pageNo 当前页码
	 * @param pageSize 总页码
	 * @param parameters 参数
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getProductPlan(int pageNo, int pageSize, Collection<Parameter> parameters);
	
	/**
	 * 查询组织节点生产计划信息 
	 * @param pageNo 当前页码
	 * @param pageSize 总页码
	 * @param parameters 参数
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getShiftPlan(int pageNo, int pageSize, Collection<Parameter> parameters);
	
	/**
	 * 根据节点ID获取节点名称
	 * @param nodeid 节点ID
	 * @return List
	 */
	public List getNodeName(String nodeid);
	
	/**
	 * 获取计划编号列表
	 * @param parameters 参数
	 * @return List
	 */
	public List getAllPlanNo(Collection<Parameter> parameters);
	/**
	 * 查询组织节点生产状态信息 
	 * @param parameters 参数
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getAllProductOutput( Collection<Parameter> parameters);
}
