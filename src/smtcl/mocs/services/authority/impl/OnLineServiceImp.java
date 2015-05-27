/**
 * com.swg.authority.cap.invoke.imp OnLineServiceImp.java
 */
package smtcl.mocs.services.authority.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dreamwork.persistence.ServiceFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import smtcl.mocs.services.authority.ITreeService;
import smtcl.mocs.services.authority.IOnLineService;
import smtcl.mocs.beans.authority.cache.DeviceRelationMtInfo;
import smtcl.mocs.common.authority.exception.OnLineDevException;
import smtcl.mocs.utils.authority.IBusConsant;
import com.swg.cap.runtime.configure.entry.ServiceType;
import com.swg.cap.runtime.stub.CapProxyFactory;
import com.swg.cap.runtime.stub.ICapProxy;

/**
 * @author gaokun
 * @create Jan 10, 2013 12:00:40 PM
 */
public class OnLineServiceImp implements IOnLineService {
	/**
	 * OnLineServiceImp 日志?
	 */
	private final static Logger logger = Logger.getLogger(OnLineServiceImp.class);
	
	@Override
	public DeviceRelationMtInfo verifyDev(String nodeId, String devId, String password)
			throws OnLineDevException {
		DeviceRelationMtInfo result = null;
		ITreeService s = (ITreeService)ServiceFactory.getBean("treeService");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Integer symgPk = null;
		try{
			ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("machinSeriaNo", devId);
			map.put("password", password);
			map.put("nodeID", nodeId);
	
			String msg = proxy.execute(String.class, "machinearchive",
					"device/getDeviceRelationMtInfo", map);
			result = gson.fromJson(msg, DeviceRelationMtInfo.class);
			
			if(IBusConsant.SYMG_RETURN_SUCCESS.equals(result.getReturnType())){
				symgPk = result.getMachineId();
			}
			logger.debug("com.swg.authority:verifyDev finished!" + String.format("[devId:%s, result:(%s)]", devId, msg));
		}catch(Exception e){
			logger.debug("com.swg.authority:verifyDev error!" + String.format("[devId:%s]", devId) + e.getMessage());
			throw new OnLineDevException("设备关联发生异常!请通知管理员!");
		}finally{
			s.devLog(nodeId, symgPk);
		}
		return result;
	}

	@Override
	public boolean sendDev(String userId, String devId, String nodeId) throws OnLineDevException{
		boolean result = false;
		try {
			ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("machinSeriaNo", devId);
			map.put("nodeID", nodeId);
			map.put("userId", userId);
			map.put("operateType", "1");

			result = proxy.execute(Boolean.class, "machinearchive",
					"device/modifyMtInfo", map);
			logger.debug("com.swg.authority:sendDev finished!" + String.format("[userId:%s, devId:%s, nodeId:%s, result:%s]", userId, devId, nodeId, result));
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			logger.error("com.swg.authority:sendDev error!" + String.format("[userId:%s, devId:%s, nodeId:%s, result:%s]", userId, devId, nodeId, result) + e.getMessage());
			throw new OnLineDevException("设备联机保存异常!请通知管理员!");
		}
		return result;
	}

	@Override
	public boolean unbindDev(String userId, String devId, String nodeId)
			throws OnLineDevException {
		boolean result = false;
		try {
			ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("machinSeriaNo", devId);
			map.put("nodeID", nodeId);
			map.put("userId", userId);
			map.put("operateType", "2");

			result = proxy.execute(Boolean.class, "machinearchive",
					"device/modifyMtInfo", map);
			logger.debug("com.swg.authority:unbindDev finished!" + String.format("[userId:%s, devId:%s, nodeId:%s, result:%s]", userId, devId, nodeId, result));
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			logger.error("com.swg.authority:unbindDev error!" + String.format("[userId:%s, devId:%s, nodeId:%s, result:%s]", userId, devId, nodeId, result) + e.getMessage());
			throw new OnLineDevException("解除设备联机关系异常!请通知管理员!");
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.swg.authority.cap.invoke.IOnLineService#unbindNode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean unbindNode(String userId, String nodeId)
			throws OnLineDevException {
		boolean result = false;
		try {
			ICapProxy proxy = CapProxyFactory.newProxy(ServiceType.JSON);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("machinSeriaNo", "");
			map.put("nodeID", nodeId);
			map.put("userId", userId);
			map.put("operateType", "3");

			result = proxy.execute(Boolean.class, "machinearchive",
					"device/modifyMtInfo", map);
			logger.debug("com.swg.authority:unbindDev finished!" + String.format("[userId:%s, devId:%s, nodeId:%s, result:%s]", userId, "", nodeId, result));
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			logger.error("com.swg.authority:unbindDev error!" + String.format("[userId:%s, devId:%s, nodeId:%s, result:%s]", userId, "", nodeId, result) + e.getMessage());
			throw new OnLineDevException("解除节点下设备联机关系异常!请通知管理员!");
		}
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
