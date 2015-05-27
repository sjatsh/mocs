package smtcl.mocs.services.device;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.pojos.device.TMachinesInfo;

import com.swg.cap.runtime.annotation.ICapRemoteService;
import com.swg.cap.runtime.annotation.IParameter;
import com.swg.cap.runtime.configure.entry.FetchType;
import com.swg.cap.runtime.configure.entry.ServiceProtocol;
import com.swg.cap.runtime.configure.entry.ServiceType;

/**
 * �豸����������ṩcap�ӿ� 
 * @���ߣ�YuTao  
 * @����ʱ�䣺2012-11-12 ����1:55:42
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵���� 
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
	 * ����豸����SYMG������ͨ������ID�͹��������ȡ����������Ϣ(JSON��ʽ)
	 * @param machinSeriaNo �������к�
	 * @param password  ��������
	 * @param nodeID  �ڵ�ID
	 * @return String  Json�ַ���    "0"��ʾ �������кŲ�����    "1"��ʾ ���벻��ȷ     "2"��ʾ �Ѿ�����    "3"��ʾ ��ȷ���
	 */
	public String getDeviceRelationMtInfo(@IParameter(name="machinSeriaNo") String machinSeriaNo,@IParameter(name="password") String password,@IParameter(name="nodeID") String nodeID);
	
    /**
     * �豸�����������Ϣ��д����������ϵͳ
     * @param machinSeriaNo �������к�
     * @param nodeID �ڵ�ID
     * @param userId  �û�ID
     * @param operateType �������(1�����   2����̨�豸ɾ��   3����̨�豸ɾ��)
     * @return boolean
     */
	public boolean modifyMtInfo(@IParameter(name="machinSeriaNo") String machinSeriaNo,@IParameter(name="nodeID") String nodeID,@IParameter(name="userId") Long userId,@IParameter(name="operateType") String operateType);
}
