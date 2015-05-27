package smtcl.mocs.services.authority;

import java.util.List;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.beans.authority.cache.NodeData;
import smtcl.mocs.pojos.authority.Device;
import smtcl.mocs.pojos.authority.Organization;
import smtcl.mocs.pojos.authority.User;

/**
 * 
 * @author gaokun
 * @create Jul 11, 2011 6:52:44 PM
 */
public interface ITreeService extends IGenericService<Object, String>{
	/**
	 * ������֯�ṹ����;
	 * @return
	 */
	public List<NodeData> queryOrgList(Parameter... parameters);
	
	/**
	 * ��ѯָ����֯�ܹ��Ķ��ӽڵ�;
	 * @param orgId
	 * @return
	 */
	public List<Organization> queryOrgChildren(String orgId);
	
	/**
	 * ��ƽ����֯�ṹ��ʱ��;
	 * @param list
	 */
	public void flatOrgTemp(List<NodeData> list);
	
	public void flatOrgTemp(String nodeId, String oldPId, String newPId);
	
	/**
	 * ��������;
	 */
	public void updateFlatOrg();
	
	public void saveNode(User user, Organization org, boolean judge)throws Exception;
	
	public Organization getNode(String id);
	
	public void devLog(String devId, Integer symgId);
	
	public Device getDeviceByNodeId(String nodeId);
	
}
