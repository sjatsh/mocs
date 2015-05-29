package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;
/**
 * WebService �û���¼���ýӿ�
 * @������:JiangFeng
 * @�޸���:
 * @��ע:
 * @����ʱ�䣺2013-04-26
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public interface IWSUserService {
	/**
	 * �û���¼
	 * @param username
	 * @param userpwd
	 * @return Map<String, Object>
	 */
	public Map<String, Object> userLogin(String username,String userpwd);
	
	/**
	 * ��ȡ�û���Ϣ
	 * @param userId
	 * @param userName
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getUserInfo(String userId,String userName);
	
	/**
	 * ��ȡ�û��б�
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getUserList();
	
	/**
	 * ��ȡ��������
	 * 
	 */
	public List<Map<String,Object>> getProduceTask(String equSerialNo);
	
	/**
	 * ������������
	 * @param equSerialNo
	 * @param jobDispatchNo �������
	 * @return
	 */
	public boolean updateProduceTask(String equSerialNo,String jobDispatchNo);
	
	/**
	 * �л�����
	 * @param equSerialNo
	 * @param jobDispatchNo �������
	 * @return
	 */
	public Map<String,Object> changeProduceTask(String equSerialNo,String jobDispatchNo);
	
	/**
	 * �û���¼
	 * @param memID(���ţ�������Ա���е�ID)
	 * @param memPass
	 * @param equSerialNo
	 * @return
	 */
	public  Map<String,Object> memberLogin(String memID,String memPass,String equSerialNo);
	
	/**
	 * �л�ģʽ
	 * @param equSerialNo
	 * @return
	 */
	public Boolean switchMode(String equSerialNo,String type);
	
	/**
	 * �жϹ����Ƿ���ɣ���Ҫ�л�
	 * @param equSerialNo
	 * @return
	 */
	public Boolean jobFinished(String equSerialNo,String jobDispatchNo);
	
	/**
	 * �����Ƿ����
	 * @param equSerialNo
	 * @param jobDispatchNo
	 * @return
	 */
	public int JobOnlineCheck(String equSerialNo, String jobDispatchNo);

}
