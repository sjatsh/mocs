package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TMemberInfo;
import smtcl.mocs.pojos.job.TPositionInfo;
import smtcl.mocs.pojos.job.TTeamInfo;
/**
 * 
 * 公共接口
 * @作者：cbqiao  
 * @创建时间：2013-7-13 下午11:27:01
 * @修改者： 
 * @修改日期： 
 * @修改说明： 
 * @version V1.0
 */
public interface IMemberService extends IGenericService<TNodes, String>{
	/**
	 * 人员表格查询
	 * @param pageNo
	 * @param pageSize
	 * @param area
	 * @return
	 */
	public List<TMemberInfo> queryMemberList(int pageNo, int pageSize, String area);
	public List<Map<String,Object>> queryMemberList(String area,String name); 
	public List<TMemberInfo> queryList(String search);
	public List<TUser> getUserByMenberId(String MenberId); 
	/**
	 * 地区列表
	 * @return
	 */
	public List<Map<String, Object>> getAreaList();
	
	/**
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getTeamList(String node);
	
	/**
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getPositionList(String node);
	
	
	/**
	 * 插入人员记录
	 * @param tm
	 */
	public void insertRecord(TMemberInfo tm,String userId);
		
	/**
	 * 逻辑删除
	 */
	public void setStatus(String table, String id, long value);
	
	/**
	 * 删除人员记录
	 */
	public void deleteRecord(TMemberInfo tm);
	public void deleteRecord(String table);
	//public void deleteMemberRecord(int id);
	
	/**
	 * 根据id查找人员记录
	 */
	public List<TMemberInfo> getRecordById(int id);
	
	/**
	 * 根据id查找班组记录
	 */
	public List<TTeamInfo> getTeamRecordById(int id);
	
	/**
	 * 更新人员记录
	 * @param tm
	 */
	public void updateRecord(TMemberInfo tm,String userId);
	
	/**
	 * 更新人员部门
	 * @param tm
	 */
	public void updateRecord(String nodeid,int id);
	
	/**
	 * 更新人员班组
	 * @param nodeid
	 * @param id
	 */
	public void updateTeamRecord(String nodeid,int id);
	
	/**
	 * 部门查询表格
	 * @param pageNo
	 * @param pageSize
	 * @param area
	 * @return
	 */
	public List<TPositionInfo> queryPositionList(String nodeid, String part, String name);
	
	/**
	 * 班组维护
	 */
	public List<Map<String,Object>> queryTeamList(String nodeid, String teamName);
	public List<Map<String,Object>> queryTeamList(String nodeid);
	
	public List<Map<String,Object>> queryPositionList1(String nodeid, String type, String name);
	
	
	/**
	 * 人员重复性验证
	 * @param code
	 * @return
	 */
	public List<Map<String, Object>> checkMember(String code, String nodeid);
	
	/**
	 * 职位删除验证
	 * @param code
	 * @return
	 */
	//public List<Map<String, Object>> checkDelePosition(String code);
	
//	/**
//	 * 职位重复性验证
//	 * @param code
//	 * @return
//	 */
//	public List<Map<String, Object>> checkPosition(String code);
	
	/**
	 * 班组重复性验证
	 * @param code
	 * @return
	 */
	public List<Map<String, Object>> checkTeam(String code);
	
	/**
	 * 班组插入记录
	 * @param tm
	 */
	public void insertTeamRecord(TTeamInfo tm);
	
	/**
	 * 班组删除记录
	 */
	public void deleteTeamRecord(int teamid);
	
	/**
	 * 班组更新记录
	 */
	public void updateTeamRecord(TTeamInfo tm);
	
	/**
	 * 部门插入记录
	 * @param tm
	 */
	public void insertPositionRecord(TPositionInfo tm);
		
//	/**
//	 * 部门删除记录
//	 */
//	public void deletePositionRecord(TPositionInfo tm);
	
	/**
	 * 部门更新记录
	 */
	public void updatePositionRecord(TPositionInfo tm);
	
	
	/**
	 * 根据车间刷新班组
	 * @param nodeId
	 * @return
	 */
	public List<Map<String, Object>> getTeamTypesByNodeId(String nodeId);
	
	/**
	 * 根据部门刷新职位类别
	 * @param nodeId
	 * @return
	 */
	public List<Map<String,Object>> getPositionTypesByNodeId(String nodeId);
	/**
	 * 根据职位类别刷新职位名称
	 * @param nodeId
	 * @param positonType
	 * @return
	 */
	public List<Map<String,Object>> getPositionNamesByNodeId(String nodeId,String positonType);
	
}
