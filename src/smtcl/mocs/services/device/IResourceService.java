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
 * 查询资源情况接口
 * 
 * @作者：LiuChuanzhen
 * @创建时间：2012-11-13 下午13:00:25
 * @修改者：JiangFeng
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public interface IResourceService extends
		IGenericService<TUserResource, String> {
	/**
	 * 查询资源库存情况
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            总页码
	 * @param parameters
	 *            参数
	 * @param nodeIds
	 *            节点信息
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getConsumeStorage(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * 查询消耗品使用情况
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            总页码
	 * @param parameters
	 *            参数
	 * @param nodeIds
	 *            节点信息
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getConsumeUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * 获取所有消耗品类型
	 * 
	 * @return List<String>
	 */
	public List<String> getAllConType();

	/**
	 * 用户查询当前的工装夹具清单
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            总页码
	 * @param parameters
	 *            参数
	 * @param nodeIds
	 *            节点信息
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getClampList(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * 查看夹具使用信息
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            总页码
	 * @param parameters
	 *            参数
	 * @param nodeIds
	 *            节点信息
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getClampUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * 用户查询当前的刀具清单
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            总页码
	 * @param parameters
	 *            参数
	 * @param nodeIds
	 *            节点信息
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getCutleryList(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * 查看刀具使用历史
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            总页码
	 * @param parameters
	 *            参数
	 * @param nodeIds
	 *            节点信息
	 * @return IDataCollection<Map<String, Object>>
	 */
	public IDataCollection<Map<String, Object>> getCutleryUse(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds);

	/**
	 * 查询所有刀具类型
	 * 
	 * @return List<String>
	 */
	public List<String> findAllUCutType();

	// /**
	// * 获取当前节点下所有设备名
	// * @param nodeIds
	// * @return
	// */
	// public List<TUserFixture> clafindByMachineName(String nodeIds);
	// public List<TUserCutter> cutfindByMachineName(String nodeIds);

	/**
	 ********************************************** 资源配置*********************************************
	 */

	/**
	 * 根据条件查询相对应的物料节点信息
	 * 
	 * @param search
	 * @return
	 */
	public TreeNode getMaterailTreeNodeOnAll(String search, String nodeid);

	/**
	 * 根据名字查询物料类别
	 * 
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassByName(String search);

	public List<Map<String,Object>> getTMaterialClassByAll();
	/**
	 * 根据id查询物料类别
	 * 
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassById(String id);

	/**
	 * 保存物料类别
	 * 
	 * @param tmc
	 * @return
	 */
	public String saveTMaterialClass(TMaterialClass tmc);

	/**
	 * 更新物料
	 * 
	 * @param tmc
	 * @return
	 */
	public String updateTMaterialClass(TMaterialClass tmc);

	/**
	 * 获取所有物料类别名称
	 * 
	 * @return
	 */
	public List<Map<String, String>> getSelectTMaterialClassForAll(String nodeid);

	/**
	 * 根据父节点查询物料详细信息
	 * 
	 * @param pid
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfoByPid(String pid,
			String nodeid, String search);


	/**
	 * 查询物料信息
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
	 * 保存零件详细
	 * 
	 * @param tmti
	 * @return
	 */
	public String saveTMaterailTypeInfo(TMaterailTypeInfo tmti);

	/**
	 * 更新零件详细
	 * 
	 * @param tmti
	 * @return
	 */
	public String updateTMaterailTypeInfo(TMaterailTypeInfo tmti);

	public void deleteTMaterailTypeInfo(TMaterailTypeInfo tmti);

	/**
	 * 根据类别id获取TfixtureConfig所需的datatable数据
	 * 
	 * @param classId
	 * @return
	 */
	public List<Map<String, Object>> getTfixtureConfigByClassId(String classId,
			String nodeid, String query);

	/**
	 * 夹具树节点模糊查询
	 * 
	 * @param query
	 * @return
	 */
	public List<TFixtureClassInfo> getTFixtureClassInfoByQuery(String query,
			String nodeid);

	/**
	 * 夹具更新
	 * 
	 * @return
	 */
	public String updateTUserFixture(TFixtureModel tfm);

	/**
	 * 根据夹具类别id返回夹具类别
	 * 
	 * @param id
	 * @return
	 */
	public List<TFixtureClassInfo> getTFixtureClassInfoByName(String name);

	/**
	 * 夹具新增
	 * 
	 * @param tuf
	 * @return
	 */
	public String addTUserFixture(TFixtureTypeInfo tuf);

	/**
	 * 夹具删除
	 * 
	 * @param id
	 * @return
	 */
	public String deleteTUserFixture(String id);

	/**
	 * 根据程序编号查询程序信息
	 * 
	 * @param progNo
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByProgNo(String progNo);

	/**
	 * 根据程序名称查询程序信息
	 * 
	 * @param progName
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByProgName(String progName);

	/**
	 * 根据创建人查询程序信息
	 * 
	 * @param creator
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByCreator(String creator);

	/**
	 * 根据条件查询TProgramInfo对象如果条件为空，则查询全部对象
	 * 
	 * @param searchStr
	 * @return List<TProgramInfo>
	 */
	public List<TProgramInfo> getTprogramInfo(String searchStr, String nodeid);

	/**
	 * 添加程序信息
	 * 
	 * @param tpr
	 * @return
	 */
	public String saveTprogramInfo(TProgramInfo tpr);

	/**
	 * 更新程序
	 * 
	 * @param tpr
	 * @return
	 */
	public String updateTprogramInfo(TProgramInfo tpr);

	/**
	 * 删除程序
	 * 
	 * @param tpr
	 * @return
	 */
	public void deleteTprogramInfo(TProgramInfo tpr);

	/**
	 * 获取刀具类别树
	 * 
	 * @param search
	 * @return
	 */
	public List<Map<String, Object>> getCutterClassTree(String search,
			String nodeid);

	/**
	 * 获取刀具信息
	 * 
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getCutterById(Integer pid, String nodeid,
			String search);

	/**
	 * 添加刀具信息
	 * 
	 * @param cutt
	 * @return
	 */
	public String addCutterManage(CuttertypeModel cutt);

	/**
	 * 删除刀具信息
	 * 
	 * @param cutt
	 * @return
	 */
	public String deleteCutterManage(CuttertypeModel cutt);

	/**
	 * 修改刀具信息
	 * 
	 * @param cutt
	 * @return
	 */
	public String updateCutterManage(CuttertypeModel cutt);
	/**
	 * 获取所有单位
	 * @return
	 */
	public List<Map<String,Object>> getUnitInfo(String unitType);
	/**
	 * 获取辅助单位
	 * @param unitId
	 * @return
	 */
	public List<Map<String,Object>> getUnitInfoOnAssis(String unitId);
	/**
	 * 获取子库存
	 * @return
	 */
	public List<Map<String,Object>> getStockOnAll();
	/**
	 * 获取子库存
	 */
	public List<Map<String,Object>> getPostionByStockNo(String stockNo);
	/**
	 * 获取人员
	 * @return
	 */
	public List<Map<String,Object>> getBuyerList();
	/**
	 * 新增组织物料

	 * @param oma
	 * @return
	 */
	public String SaveOrganizeMateriel(OrganizeMaterielAddBean oma);

	/**
	 * 根据Param(字段名)
	 *    materialId(值)
	 * 获取   obj (对象)
	 */
	public Object getObject(String materialId,String Param,Object obj);
	/**
	 * 修改组织物料
	 * @param omm
	 * @return
	 */
	public String UpdateOrganizeMateriel(OrganizeMaterielUpdateBean oma);

	/**
	 * 返回物料版本
	 * 
	 * @param mno
	 * @return
	 */
	public List<Map<String, Object>> getMaterielVersion(String mno);

	/**
	 * 返回程序信息
	 */
	public List<Map<String, Object>> uploadProgram(String progName,
			String progVersion, String progContent, String memName,
			String progDesc);
	

	/**
	 * 返回程序版本信息
	 */
	public List<Map<String, Object>> getProgramVersion(String progName,
			String nodeId);

	/**
	 * 获取程序信息
	 */
	public List<Map<String, Object>> getProgramInfo(String progId);

	/**
	 * 获取物料信息（成品）
	 */
	public List<Map<String, Object>> getMaterialInfo(String nodeId);

	/**
	 * 获取工序信息
	 */
	public List<Map<String, Object>> getProcessInfo(String materialId,
			String nodeId);

	/**
	 * 获取已绑定的程序(包括参数中的程序)
	 */
	public List<Map<String, Object>> getBingProgramInfo(String materialId,
			String processId, String nodeId);

	/**
	 * 获取已绑定的程序(不包括参数中的程序)
	 */
	public List<Map<String, Object>> getBingProgramInfo2(String materialId,
			String processId);

	/**
	 * 获取程序信息List
	 */
	public List<Map<String, Object>> getProgramList(String nodeId);

	/**
	 * 保存绑定程序
	 */
	public String bingProgram(List<Map<String, Object>> dataList,
			String materialId, String processId);

	/**
	 * 获取程序综合信息
	 */
	public List<Map<String, Object>> getProgramData(String searchStr,
			String nodeid);

	/**
	 * 获取已绑定程序
	 */
	public List<Map<String, Object>> getBingProgramData(String programId);

	/**
	 * 获取已绑定程序版本
	 */
	public List<Map<String, Object>> getBingProgramVersion(String programId);

	/**
	 * 删除程序
	 */
	public String deleteProgram(String programId);

	/**
	 * 激活程序
	 * 
	 * @param flag
	 *            1激活程序;2取消激活
	 */
	public String activityProgram(String programId, String flag);

	/**
	 * 程序下载
	 */
	public String downLoadProgram(String programId);

	/**
	 * 根据工单获取相应程序
	 */
	public List<Map<String, Object>> getProgramByJobDispatchNo(
			String jobDispatchNo, String partId);

	/**
	 * 程序下载记录信息
	 */
	public List<Map<String, Object>> getProgramLoadDownInfo(String programId,
			String equSerialNo);

	/**
	 * 获取程序
	 */
	public List<Map<String, Object>> getProgramById(String programId);

	/**
	 * @author zhouxinyi
	 * @date 20150520
	 * @comment 获取关联程序
	 */
	public List<Map<String, Object>> getProgramMappingList(String programId);

	/**
	 * 保存下载信息
	 */
	public String saveProgramLoadDownInfo(String programId, String equSerialNo,
			String userID);

	/**
	 * 获取零件信息
	 */
	public List<Map<String, Object>> getParts(String partName);

	/**
	 * 获取nodeId BY equSerialNo
	 */
	public List<Map<String, Object>> getEquNodeId(String equSerialNo);
	public List<Map<String, Object>> getEquNodeIdBySql(String equSerialNo);

}
