package smtcl.mocs.services.device;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.pojos.device.TMachinesInfo;

import com.swg.cap.runtime.annotation.ICapRemoteService;
import com.swg.cap.runtime.annotation.IParameter;
import com.swg.cap.runtime.configure.entry.FetchType;
import com.swg.cap.runtime.configure.entry.ServiceProtocol;
import com.swg.cap.runtime.configure.entry.ServiceType;

/**
 * 设备与机床关联提供cap接口 
 * @作者：YuTao  
 * @创建时间：2012-11-12 下午1:55:42
 * @修改者： 
 * @修改日期： 
 * @修改说明： 
 * @version V1.0
 */
@ICapRemoteService (
	    name = "device",
	    fetchType = FetchType.Fetch_From_Spring,
	    refName="mtService",
	    type = ServiceType.JSON,
	    protocol = ServiceProtocol.Http
)
public interface IMtService extends IGenericService<TMachinesInfo, String>{
			
	/**
	 * 添加设备关联SYMG机床，通过机床ID和关联密码获取机床基本信息(JSON形式)
	 * @param machinSeriaNo 机床序列号
	 * @param password  激活密码
	 * @param nodeID  节点ID
	 * @return String  Json字符串    "0"表示 机床序列号不存在    "1"表示 密码不正确     "2"表示 已经关联    "3"表示 正确情况
	 */
	public String getDeviceRelationMtInfo(@IParameter(name="machinSeriaNo") String machinSeriaNo,@IParameter(name="password") String password,@IParameter(name="nodeID") String nodeID);
	
    /**
     * 设备关联结果，信息回写至机床档案系统
     * @param machinSeriaNo 机床序列号
     * @param nodeID 节点ID
     * @param userId  用户ID
     * @param operateType 操作类别(1、添加   2、单台设备删除   3、多台设备删除)
     * @return boolean
     */
	public boolean modifyMtInfo(@IParameter(name="machinSeriaNo") String machinSeriaNo,@IParameter(name="nodeID") String nodeID,@IParameter(name="userId") Long userId,@IParameter(name="operateType") String operateType);
}
