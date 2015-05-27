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
 * �����ӿ�
 * @���ߣ�cbqiao  
 * @����ʱ�䣺2013-7-13 ����11:27:01
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵���� 
 * @version V1.0
 */
public interface IMemberService extends IGenericService<TNodes, String>{
	/**
	 * ��Ա����ѯ
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
	 * �����б�
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
	 * ������Ա��¼
	 * @param tm
	 */
	public void insertRecord(TMemberInfo tm,String userId);
		
	/**
	 * �߼�ɾ��
	 */
	public void setStatus(String table, String id, long value);
	
	/**
	 * ɾ����Ա��¼
	 */
	public void deleteRecord(TMemberInfo tm);
	public void deleteRecord(String table);
	//public void deleteMemberRecord(int id);
	
	/**
	 * ����id������Ա��¼
	 */
	public List<TMemberInfo> getRecordById(int id);
	
	/**
	 * ����id���Ұ����¼
	 */
	public List<TTeamInfo> getTeamRecordById(int id);
	
	/**
	 * ������Ա��¼
	 * @param tm
	 */
	public void updateRecord(TMemberInfo tm,String userId);
	
	/**
	 * ������Ա����
	 * @param tm
	 */
	public void updateRecord(String nodeid,int id);
	
	/**
	 * ������Ա����
	 * @param nodeid
	 * @param id
	 */
	public void updateTeamRecord(String nodeid,int id);
	
	/**
	 * ���Ų�ѯ���
	 * @param pageNo
	 * @param pageSize
	 * @param area
	 * @return
	 */
	public List<TPositionInfo> queryPositionList(String nodeid, String part, String name);
	
	/**
	 * ����ά��
	 */
	public List<Map<String,Object>> queryTeamList(String nodeid, String teamName);
	public List<Map<String,Object>> queryTeamList(String nodeid);
	
	public List<Map<String,Object>> queryPositionList1(String nodeid, String type, String name);
	
	
	/**
	 * ��Ա�ظ�����֤
	 * @param code
	 * @return
	 */
	public List<Map<String, Object>> checkMember(String code, String nodeid);
	
	/**
	 * ְλɾ����֤
	 * @param code
	 * @return
	 */
	//public List<Map<String, Object>> checkDelePosition(String code);
	
//	/**
//	 * ְλ�ظ�����֤
//	 * @param code
//	 * @return
//	 */
//	public List<Map<String, Object>> checkPosition(String code);
	
	/**
	 * �����ظ�����֤
	 * @param code
	 * @return
	 */
	public List<Map<String, Object>> checkTeam(String code);
	
	/**
	 * ��������¼
	 * @param tm
	 */
	public void insertTeamRecord(TTeamInfo tm);
	
	/**
	 * ����ɾ����¼
	 */
	public void deleteTeamRecord(int teamid);
	
	/**
	 * ������¼�¼
	 */
	public void updateTeamRecord(TTeamInfo tm);
	
	/**
	 * ���Ų����¼
	 * @param tm
	 */
	public void insertPositionRecord(TPositionInfo tm);
		
//	/**
//	 * ����ɾ����¼
//	 */
//	public void deletePositionRecord(TPositionInfo tm);
	
	/**
	 * ���Ÿ��¼�¼
	 */
	public void updatePositionRecord(TPositionInfo tm);
	
	
	/**
	 * ���ݳ���ˢ�°���
	 * @param nodeId
	 * @return
	 */
	public List<Map<String, Object>> getTeamTypesByNodeId(String nodeId);
	
	/**
	 * ���ݲ���ˢ��ְλ���
	 * @param nodeId
	 * @return
	 */
	public List<Map<String,Object>> getPositionTypesByNodeId(String nodeId);
	/**
	 * ����ְλ���ˢ��ְλ����
	 * @param nodeId
	 * @param positonType
	 * @return
	 */
	public List<Map<String,Object>> getPositionNamesByNodeId(String nodeId,String positonType);
	
}
