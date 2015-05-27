/**
 * com.swg.authority.cap.invoke IOnLineService.java
 */
package smtcl.mocs.services.authority;

import smtcl.mocs.beans.authority.cache.DeviceRelationMtInfo;
import smtcl.mocs.common.authority.exception.OnLineDevException;

/**
 * @author gaokun
 * @create Jan 10, 2013 11:49:50 AM
 */
public interface IOnLineService {

	/**
	 * У���豸��Ϣ;
	 * @param devId
	 * @param password
	 * @return
	 * @throws OnLineDevException
	 */
	public DeviceRelationMtInfo verifyDev(String nodeId, String devId, String password)throws OnLineDevException;
	
	/**
	 * ����豸������Ϣ;
	 * @param userId
	 * @param devId
	 * @param nodeId
	 * @return
	 * @throws OnLineDevException
	 */
	public boolean sendDev(String userId, String devId, String nodeId)throws OnLineDevException;
	
	/**
	 * �Ӵ��豸������Ϣ;
	 * @param userId
	 * @param devId
	 * @param nodeId
	 * @return
	 * @throws OnLineDevException
	 */
	public boolean unbindDev(String userId, String devId, String nodeId)throws OnLineDevException;
	
	/**
	 * �Ӵ��ڵ������Ϣ;
	 * @param userId
	 * @param devId ֵΪnull
	 * @param nodeId
	 * @return
	 * @throws OnLineDevException
	 */
	public boolean unbindNode(String userId, String nodeId)throws OnLineDevException;
}
