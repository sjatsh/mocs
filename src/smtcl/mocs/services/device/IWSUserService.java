package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;
/**
 * WebService 用户登录调用接口
 * @创建者:JiangFeng
 * @修改者:
 * @备注:
 * @创建时间：2013-04-26
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public interface IWSUserService {
	/**
	 * 用户登录
	 * @param username
	 * @param userpwd
	 * @return Map<String, Object>
	 */
	public Map<String, Object> userLogin(String username,String userpwd);
	
	/**
	 * 获取用户信息
	 * @param userId
	 * @param userName
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getUserInfo(String userId,String userName);
	
	/**
	 * 获取用户列表
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getUserList();
	
	/**
	 * 获取生产任务
	 * 
	 */
	public List<Map<String,Object>> getProduceTask(String equSerialNo);
	
	/**
	 * 更新生产任务
	 * @param equSerialNo
	 * @param jobDispatchNo 工单编号
	 * @return
	 */
	public boolean updateProduceTask(String equSerialNo,String jobDispatchNo);
	
	/**
	 * 切换工单
	 * @param equSerialNo
	 * @param jobDispatchNo 工单编号
	 * @return
	 */
	public Map<String,Object> changeProduceTask(String equSerialNo,String jobDispatchNo);
	
	/**
	 * 用户登录
	 * @param memID(工号，不是人员表中的ID)
	 * @param memPass
	 * @param equSerialNo
	 * @return
	 */
	public  Map<String,Object> memberLogin(String memID,String memPass,String equSerialNo);
	
	/**
	 * 切换模式
	 * @param equSerialNo
	 * @return
	 */
	public Boolean switchMode(String equSerialNo,String type);
	
	/**
	 * 判断工单是否完成，需要切换
	 * @param equSerialNo
	 * @return
	 */
	public Boolean jobFinished(String equSerialNo,String jobDispatchNo);
	
	/**
	 * 工单是否合理
	 * @param equSerialNo
	 * @param jobDispatchNo
	 * @return
	 */
	public int JobOnlineCheck(String equSerialNo, String jobDispatchNo);

}
