package smtcl.mocs.services.device;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.device.TNodeProductionProfiles;


/**
 * ����״̬��ѯ����ӿ�
 * @���ߣ�ZhaoHongshi
 * @����ʱ�䣺2012-11-13 ����3:00:25
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface IProductionService  extends IGenericService<TNodeProductionProfiles, String> {

	/**
	 * ��ѯ��֯�ڵ�����״̬��Ϣ 
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getProductOutput(int pageNo, int pageSize, Collection<Parameter> parameters);

	/**
	 * ��ѯ��֯�ڵ������ƻ���Ϣ
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getProductPlan(int pageNo, int pageSize, Collection<Parameter> parameters);
	
	/**
	 * ��ѯ��֯�ڵ������ƻ���Ϣ 
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @return IDataCollection<Map<String,Object>>
	 */
	public IDataCollection<Map<String, Object>> getShiftPlan(int pageNo, int pageSize, Collection<Parameter> parameters);
	
	/**
	 * ���ݽڵ�ID��ȡ�ڵ�����
	 * @param nodeid �ڵ�ID
	 * @return List
	 */
	public List getNodeName(String nodeid);
	
	/**
	 * ��ȡ�ƻ�����б�
	 * @param parameters ����
	 * @return List
	 */
	public List getAllPlanNo(Collection<Parameter> parameters);
	/**
	 * ��ѯ��֯�ڵ�����״̬��Ϣ 
	 * @param parameters ����
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getAllProductOutput( Collection<Parameter> parameters);
}
