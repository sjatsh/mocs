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
	 * 返回组织结构数据;
	 * @return
	 */
	public List<NodeData> queryOrgList(Parameter... parameters);
	
	/**
	 * 查询指定组织架构的儿子节点;
	 * @param orgId
	 * @return
	 */
	public List<Organization> queryOrgChildren(String orgId);
	
	/**
	 * 扁平化组织结构临时表;
	 * @param list
	 */
	public void flatOrgTemp(List<NodeData> list);
	
	public void flatOrgTemp(String nodeId, String oldPId, String newPId);
	
	/**
	 * 插入数据;
	 */
	public void updateFlatOrg();
	
	public void saveNode(User user, Organization org, boolean judge)throws Exception;
	
	public Organization getNode(String id);
	
	public void devLog(String devId, Integer symgId);
	
	public Device getDeviceByNodeId(String nodeId);
	
}
