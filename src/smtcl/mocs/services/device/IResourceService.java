package smtcl.mocs.services.device;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;
import org.primefaces.model.TreeNode;

import smtcl.mocs.beans.storage.OrganizeMaterielAddBean;
import smtcl.mocs.beans.storage.OrganizeMaterielManageBean;
import smtcl.mocs.beans.storage.OrganizeMaterielUpdateBean;
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
 * 
 * @���ߣ�LiuChuanzhen
 * @����ʱ�䣺2012-11-13 ����13:00:25
 * @�޸��ߣ�JiangFeng
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public interface IResourceService extends
		IGenericService<TUserResource, String> {
	/**
	 * ��ѯ��Դ������
	 * 
	 * @param pageNo
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ��ҳ��
	 * @param parameters
	 *            ����
	 * @param nodeIds
	 *            �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getConsumeStorage(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * ��ѯ����Ʒʹ�����
	 * 
	 * @param pageNo
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ��ҳ��
	 * @param parameters
	 *            ����
	 * @param nodeIds
	 *            �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getConsumeUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * ��ȡ��������Ʒ����
	 * 
	 * @return List<String>
	 */
	public List<String> getAllConType();

	/**
	 * �û���ѯ��ǰ�Ĺ�װ�о��嵥
	 * 
	 * @param pageNo
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ��ҳ��
	 * @param parameters
	 *            ����
	 * @param nodeIds
	 *            �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getClampList(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * �鿴�о�ʹ����Ϣ
	 * 
	 * @param pageNo
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ��ҳ��
	 * @param parameters
	 *            ����
	 * @param nodeIds
	 *            �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getClampUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * �û���ѯ��ǰ�ĵ����嵥
	 * 
	 * @param pageNo
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ��ҳ��
	 * @param parameters
	 *            ����
	 * @param nodeIds
	 *            �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getCutleryList(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * �鿴����ʹ����ʷ
	 * 
	 * @param pageNo
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ��ҳ��
	 * @param parameters
	 *            ����
	 * @param nodeIds
	 *            �ڵ���Ϣ
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getCutleryUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * ��ѯ���е�������
	 * 
	 * @return List<String>
	 */
	public List<String> findAllUCutType();

	// /**
	// * ��ȡ��ǰ�ڵ��������豸��
	// * @param nodeIds
	// * @return
	// */
	// public List<TUserFixture> clafindByMachineName(String nodeIds);
	// public List<TUserCutter> cutfindByMachineName(String nodeIds);

	/**
	 ********************************************** ��Դ����*********************************************
	 */

	/**
	 * ����������ѯ���Ӧ�����Ͻڵ���Ϣ
	 * 
	 * @param search
	 * @return
	 */
	public TreeNode getMaterailTreeNodeOnAll(String search, String nodeid);

	/**
	 * �������ֲ�ѯ�������
	 * 
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassByName(String search);

	public List<Map<String,Object>> getTMaterialClassByAll();
	/**
	 * ����id��ѯ�������
	 * 
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassById(String id);

	/**
	 * �����������
	 * 
	 * @param tmc
	 * @return
	 */
	public String saveTMaterialClass(TMaterialClass tmc);

	/**
	 * ��������
	 * 
	 * @param tmc
	 * @return
	 */
	public String updateTMaterialClass(TMaterialClass tmc);

	/**
	 * ��ȡ���������������
	 * 
	 * @return
	 */
	public List<Map<String, String>> getSelectTMaterialClassForAll(String nodeid);

	/**
	 * ���ݸ��ڵ��ѯ������ϸ��Ϣ
	 * 
	 * @param pid
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfoByPid(String pid,
			String nodeid, String search);


	/**
	 * ��ѯ������Ϣ
	 * @param pid
	 * @param nodeid
	 * @param type
	 * @param no
	 * @param desc
	 * @param status
	 * @param unit
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String,Object>> getTMaterailTypeInfo(String pid,String nodeid,String type,String no,String desc,
			String status,String unit,Date startTime,Date endTime);
	/**
	 * ���������ϸ
	 * 
	 * @param tmti
	 * @return
	 */
	public String saveTMaterailTypeInfo(TMaterailTypeInfo tmti);

	/**
	 * ���������ϸ
	 * 
	 * @param tmti
	 * @return
	 */
	public String updateTMaterailTypeInfo(TMaterailTypeInfo tmti);

	public void deleteTMaterailTypeInfo(TMaterailTypeInfo tmti);

	/**
	 * �������id��ȡTfixtureConfig�����datatable����
	 * 
	 * @param classId
	 * @return
	 */
	public List<Map<String, Object>> getTfixtureConfigByClassId(String classId,
			String nodeid, String query);

	/**
	 * �о����ڵ�ģ����ѯ
	 * 
	 * @param query
	 * @return
	 */
	public List<TFixtureClassInfo> getTFixtureClassInfoByQuery(String query,
			String nodeid);

	/**
	 * �о߸���
	 * 
	 * @return
	 */
	public String updateTUserFixture(TFixtureModel tfm);

	/**
	 * ���ݼо����id���ؼо����
	 * 
	 * @param id
	 * @return
	 */
	public List<TFixtureClassInfo> getTFixtureClassInfoByName(String name);

	/**
	 * �о�����
	 * 
	 * @param tuf
	 * @return
	 */
	public String addTUserFixture(TFixtureTypeInfo tuf);

	/**
	 * �о�ɾ��
	 * 
	 * @param id
	 * @return
	 */
	public String deleteTUserFixture(String id);

	/**
	 * ���ݳ����Ų�ѯ������Ϣ
	 * 
	 * @param progNo
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByProgNo(String progNo);

	/**
	 * ���ݳ������Ʋ�ѯ������Ϣ
	 * 
	 * @param progName
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByProgName(String progName);

	/**
	 * ���ݴ����˲�ѯ������Ϣ
	 * 
	 * @param creator
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByCreator(String creator);

	/**
	 * ����������ѯTProgramInfo�����������Ϊ�գ����ѯȫ������
	 * 
	 * @param searchStr
	 * @return List<TProgramInfo>
	 */
	public List<TProgramInfo> getTprogramInfo(String searchStr, String nodeid);

	/**
	 * ��ӳ�����Ϣ
	 * 
	 * @param tpr
	 * @return
	 */
	public String saveTprogramInfo(TProgramInfo tpr);

	/**
	 * ���³���
	 * 
	 * @param tpr
	 * @return
	 */
	public String updateTprogramInfo(TProgramInfo tpr);

	/**
	 * ɾ������
	 * 
	 * @param tpr
	 * @return
	 */
	public void deleteTprogramInfo(TProgramInfo tpr);

	/**
	 * ��ȡ���������
	 * 
	 * @param search
	 * @return
	 */
	public List<Map<String, Object>> getCutterClassTree(String search,
			String nodeid);

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getCutterById(Integer pid, String nodeid,
			String search);

	/**
	 * ��ӵ�����Ϣ
	 * 
	 * @param cutt
	 * @return
	 */
	public String addCutterManage(CuttertypeModel cutt);

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param cutt
	 * @return
	 */
	public String deleteCutterManage(CuttertypeModel cutt);

	/**
	 * �޸ĵ�����Ϣ
	 * 
	 * @param cutt
	 * @return
	 */
	public String updateCutterManage(CuttertypeModel cutt);
	/**
	 * ��ȡ���е�λ
	 * @return
	 */
	public List<Map<String,Object>> getUnitInfo(String unitType);
	/**
	 * ��ȡ������λ
	 * @param unitId
	 * @return
	 */
	public List<Map<String,Object>> getUnitInfoOnAssis(String unitId);
	/**
	 * ��ȡ�ӿ��
	 * @return
	 */
	public List<Map<String,Object>> getStockOnAll();
	/**
	 * ��ȡ�ӿ��
	 */
	public List<Map<String,Object>> getPostionByStockNo(String stockNo);
	/**
	 * ��ȡ��Ա
	 * @return
	 */
	public List<Map<String,Object>> getBuyerList();
	/**
	 * ������֯����

	 * @param oma
	 * @return
	 */
	public String SaveOrganizeMateriel(OrganizeMaterielAddBean oma);

	/**
	 * ����Param(�ֶ���)
	 *    materialId(ֵ)
	 * ��ȡ   obj (����)
	 */
	public Object getObject(String materialId,String Param,Object obj);
	/**
	 * �޸���֯����
	 * @param omm
	 * @return
	 */
	public String UpdateOrganizeMateriel(OrganizeMaterielUpdateBean oma);

	/**
	 * �������ϰ汾
	 * 
	 * @param mno
	 * @return
	 */
	public List<Map<String, Object>> getMaterielVersion(String mno);

	/**
	 * ���س�����Ϣ
	 */
	public List<Map<String, Object>> uploadProgram(String progName,
			String progVersion, String progContent, String memName,
			String progDesc);
	

	/**
	 * ���س���汾��Ϣ
	 */
	public List<Map<String, Object>> getProgramVersion(String progName,
			String nodeId);

	/**
	 * ��ȡ������Ϣ
	 */
	public List<Map<String, Object>> getProgramInfo(String progId);

	/**
	 * ��ȡ������Ϣ����Ʒ��
	 */
	public List<Map<String, Object>> getMaterialInfo(String nodeId);

	/**
	 * ��ȡ������Ϣ
	 */
	public List<Map<String, Object>> getProcessInfo(String materialId,
			String nodeId);

	/**
	 * ��ȡ�Ѱ󶨵ĳ���(���������еĳ���)
	 */
	public List<Map<String, Object>> getBingProgramInfo(String materialId,
			String processId, String nodeId);

	/**
	 * ��ȡ�Ѱ󶨵ĳ���(�����������еĳ���)
	 */
	public List<Map<String, Object>> getBingProgramInfo2(String materialId,
			String processId);

	/**
	 * ��ȡ������ϢList
	 */
	public List<Map<String, Object>> getProgramList(String nodeId);

	/**
	 * ����󶨳���
	 */
	public String bingProgram(List<Map<String, Object>> dataList,
			String materialId, String processId);

	/**
	 * ��ȡ�����ۺ���Ϣ
	 */
	public List<Map<String, Object>> getProgramData(String searchStr,
			String nodeid);

	/**
	 * ��ȡ�Ѱ󶨳���
	 */
	public List<Map<String, Object>> getBingProgramData(String programId);

	/**
	 * ��ȡ�Ѱ󶨳���汾
	 */
	public List<Map<String, Object>> getBingProgramVersion(String programId);

	/**
	 * ɾ������
	 */
	public String deleteProgram(String programId);

	/**
	 * �������
	 * 
	 * @param flag
	 *            1�������;2ȡ������
	 */
	public String activityProgram(String programId, String flag);

	/**
	 * ��������
	 */
	public String downLoadProgram(String programId);

	/**
	 * ���ݹ�����ȡ��Ӧ����
	 */
	public List<Map<String, Object>> getProgramByJobDispatchNo(
			String jobDispatchNo, String partId);

	/**
	 * �������ؼ�¼��Ϣ
	 */
	public List<Map<String, Object>> getProgramLoadDownInfo(String programId,
			String equSerialNo);

	/**
	 * ��ȡ����
	 */
	public List<Map<String, Object>> getProgramById(String programId);

	/**
	 * @author zhouxinyi
	 * @date 20150520
	 * @comment ��ȡ��������
	 */
	public List<Map<String, Object>> getProgramMappingList(String programId);

	/**
	 * ����������Ϣ
	 */
	public String saveProgramLoadDownInfo(String programId, String equSerialNo,
			String userID);

	/**
	 * ��ȡ�����Ϣ
	 */
	public List<Map<String, Object>> getParts(String partName);

	/**
	 * ��ȡnodeId BY equSerialNo
	 */
	public List<Map<String, Object>> getEquNodeId(String equSerialNo);
	public List<Map<String, Object>> getEquNodeIdBySql(String equSerialNo);

}
