package smtcl.mocs.services.device;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.TFixtureModel;
import smtcl.mocs.pojos.device.TFixtureClassInfo;
import smtcl.mocs.pojos.device.TUserFixture;
import smtcl.mocs.pojos.device.TUserResource;
import smtcl.mocs.pojos.job.TCutterClassInfo;
import smtcl.mocs.pojos.job.TFixtureTypeInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TMaterialClass;
import smtcl.mocs.pojos.job.TProgramInfo;


/**
 * ��ѯ��Դ����ӿ�
 * @���ߣ�LiuChuanzhen
 * @����ʱ�䣺2012-11-13 ����13:00:25
 * @�޸��ߣ�JiangFeng
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public interface IResourceService extends IGenericService<TUserResource, String> {
	/**
	 * ��ѯ��Դ������ 
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @param nodeIds �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getConsumeStorage(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);

	/**
	 * ��ѯ����Ʒʹ�����
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @param nodeIds �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getConsumeUse(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);

	/**
	 * ��ȡ��������Ʒ����
	 * @return List<String>
	 */
	public List<String> getAllConType();
	
	/**
	 * �û���ѯ��ǰ�Ĺ�װ�о��嵥
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @param nodeIds �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getClampList(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);

	/**
	 * �鿴�о�ʹ����Ϣ
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @param nodeIds �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getClampUse(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);

	/**
	 * �û���ѯ��ǰ�ĵ����嵥
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @param nodeIds �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getCutleryList(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);

	/**
	 * �鿴����ʹ����ʷ
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ��ҳ��
	 * @param parameters ����
	 * @param nodeIds �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getCutleryUse(int pageNo, int pageSize, Collection<Parameter> parameters,String nodeIds);
	
	/**
	 * ��ѯ���е�������
	 * @return List<String>
	 */
	public List<String> findAllUCutType();
	
	///**
	// * ��ȡ��ǰ�ڵ��������豸��
	// * @param nodeIds
	//* @return
	// */
	//public List<TUserFixture> clafindByMachineName(String nodeIds);
	//public List<TUserCutter> cutfindByMachineName(String nodeIds);
	
	/**
	 **********************************************��Դ����*********************************************
	 */
	
	/**
	 * ����������ѯ���Ӧ�����Ͻڵ���Ϣ
	 * @param search
	 * @return
	 */
	public TreeNode getMaterailTreeNodeOnAll(String search,String nodeid);
	
	/**
	 * �������ֲ�ѯ�������
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassByName(String search);
	/**
	 * ����id��ѯ�������
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassById(String id);
	/**
	 * �����������
	 * @param tmc
	 * @return
	 */
	public String saveTMaterialClass(TMaterialClass tmc);
	/**
	 * ��������
	 * @param tmc
	 * @return
	 */
	public String updateTMaterialClass(TMaterialClass tmc);
	/**
	 * ��ȡ���������������
	 * @return
	 */
	public List<Map<String,String>> getSelectTMaterialClassForAll(String nodeid);
	/**
	 * ���ݸ��ڵ��ѯ������ϸ��Ϣ
	 * @param pid
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfoByPid(String pid,String nodeid,String search);
	/**
	 * ���������ϸ
	 * @param tmti
	 * @return
	 */
	public String saveTMaterailTypeInfo(TMaterailTypeInfo tmti);
	/**
	 * ���������ϸ
	 * @param tmti
	 * @return
	 */
	public String updateTMaterailTypeInfo(TMaterailTypeInfo tmti);
	public void deleteTMaterailTypeInfo(TMaterailTypeInfo tmti);
	/**
	 * �������id��ȡTfixtureConfig�����datatable����
	 * @param classId
	 * @return
	 */
	public List<Map<String,Object>> getTfixtureConfigByClassId(String classId,String nodeid,String query);
	/**
	 * �о����ڵ�ģ����ѯ
	 * @param query
	 * @return
	 */
	public List<TFixtureClassInfo> getTFixtureClassInfoByQuery(String query,String nodeid);
	/**
	 * �о߸���
	 * @return
	 */
	public String updateTUserFixture(TFixtureModel tfm);
	/**
	 * ���ݼо����id���ؼо����
	 * @param id
	 * @return
	 */
	public List<TFixtureClassInfo> getTFixtureClassInfoByName(String name);
	/**
	 * �о�����
	 * @param tuf
	 * @return
	 */
   public String addTUserFixture(TFixtureTypeInfo tuf);
   /**
	 * �о�ɾ��
	 * @param id
	 * @return
	 */
	public String deleteTUserFixture(String id);
	
	/**���ݳ����Ų�ѯ������Ϣ
	 * @param  progNo
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByProgNo(String progNo);
	
	/**���ݳ������Ʋ�ѯ������Ϣ
	 * @param   progName
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByProgName(String progName);
	
	/**���ݴ����˲�ѯ������Ϣ
	 * @param  creator
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByCreator(String creator);
	
	/**
	 * ����������ѯTProgramInfo�����������Ϊ�գ����ѯȫ������
	 * @param searchStr
	 * @return List<TProgramInfo>
	 */
	public List<TProgramInfo> getTprogramInfo(String searchStr,String nodeid);
	
	/**��ӳ�����Ϣ
	 * @param tpr
	 * @return
	 */
	public String saveTprogramInfo(TProgramInfo tpr);
	
	
	/**
	 * ���³���
	 * @param tpr
	 * @return
	 */
	public String updateTprogramInfo(TProgramInfo tpr);
	
	/**
	 * ɾ������
	 * @param tpr
	 * @return
	 */
	public void deleteTprogramInfo(TProgramInfo tpr);
	
	
	/**
	 * ��ȡ���������
	 * @param search
	 * @return
	 */
	public List<Map<String, Object>> getCutterClassTree(String search,String nodeid);
	

	/**
	 * ��ȡ������Ϣ
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getCutterById(Integer pid,String nodeid,String search);
	/**
	 * ��ӵ�����Ϣ
	 * @param cutt
	 * @return
	 */
	public String addCutterManage(CuttertypeModel cutt);
	
	/**
	 * ɾ��������Ϣ
	 * @param cutt
	 * @return 
	 */
	public String deleteCutterManage(CuttertypeModel cutt);
	
	/**
	 * �޸ĵ�����Ϣ
	 * @param cutt
	 * @return
	 */
	public String updateCutterManage(CuttertypeModel cutt);
	
	
}
